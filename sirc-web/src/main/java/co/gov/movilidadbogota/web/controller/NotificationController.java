package co.gov.movilidadbogota.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.gov.movilidadbogota.core.dao.ConductorVehiculoDAO;
import co.gov.movilidadbogota.core.dao.EmpresaDAO;
import co.gov.movilidadbogota.core.dao.ParametroSimurDAO;
import co.gov.movilidadbogota.core.dao.UsuarioDAO;
import co.gov.movilidadbogota.core.dto.EmpresaDTO;
import co.gov.movilidadbogota.core.dto.NotificationDTO;
import co.gov.movilidadbogota.core.dto.ParametroDTO;
import co.gov.movilidadbogota.core.dto.UsuarioDTO;
import co.gov.movilidadbogota.core.util.SystemParameters;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.web.constants.NotificationsConstants;
import co.gov.movilidadbogota.web.constants.UserConstants;
import co.gov.movilidadbogota.core.util.NotificationSender;
import co.gov.movilidadbogota.web.view.breadcrumb.BreadcrumbView;

@Controller
public class NotificationController extends GenericController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ConductorVehiculoDAO conductorVehiculoDAO;

	@Autowired
	private ParametroSimurDAO parametroSimurDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private EmpresaDAO empresaDAO;

	@Autowired
	private NotificationSender notificationSender;

	@RequestMapping(value = NotificationsConstants.NOTIFICATIONS, method = RequestMethod.GET)
	public String notificationsGet(HttpSession session, Locale locale, Model model) {

		initbreadcrumb(locale, Arrays.asList(new BreadcrumbView(UserConstants.CONSULT_EXTERNAL_USER,
				messageSource.getMessage("header.administration.notifications", null, locale))));

		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);

		UsuarioDTO uSession = (UsuarioDTO) session.getAttribute("uSession");

		UsuarioDTO u = usuarioDAO.findById(uSession.getId());

		List<Long> empresas = new ArrayList<>();
		for (EmpresaDTO empresa : u.getEmpresas()) {
			empresas.add(empresa.getId());
		}

		if (empresas.size() > 0) {
			Long numeroTarjetasControlSinVigencia = conductorVehiculoDAO.countByStateOutOfLifeDate(new Date(),
					empresas);

			Long numeroTarjetasControlSinValidez = conductorVehiculoDAO.countByStateOutOfValidateDate(new Date(),
					empresas);

			ParametroDTO vencimiento = parametroSimurDAO.findByKey(SystemParameters.VENCIMIENTO_TARJETA.getValue());

			Calendar validezFecha = Calendar.getInstance();
			validezFecha.add(Calendar.DAY_OF_YEAR, Integer.valueOf(vencimiento.getValorParametro()));

			Long numeroTarjetasControlProximosVencimientos = conductorVehiculoDAO.countByStateBeforeToExpire(true,
					validezFecha.getTime(), empresas);

			model.addAttribute("tarjetasControlSinVigencia", numeroTarjetasControlSinVigencia);
			model.addAttribute("tarjetasControlSinValidez", numeroTarjetasControlSinValidez);
			model.addAttribute("tarjetasControlProximosVencimientos", numeroTarjetasControlProximosVencimientos);
		}

		return "notifications/notifications";
	}

	@RequestMapping(value = NotificationsConstants.NOTIFICATIONS_SIN_VALIDEZ, method = RequestMethod.GET)
	public String notificationsGetSinValidez(HttpSession session, Locale locale, Model model) {

		initbreadcrumb(locale, Arrays.asList(
				new BreadcrumbView(NotificationsConstants.NOTIFICATIONS,
						messageSource.getMessage("header.administration.notifications", null, locale)),
				new BreadcrumbView(NotificationsConstants.NOTIFICATIONS_SIN_VALIDEZ,
						messageSource.getMessage("header.administration.notifications.validez", null, locale))));

		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);

		List<NotificationDTO> tarjetasControlSinValidez = new ArrayList<>();

		UsuarioDTO uSession = (UsuarioDTO) session.getAttribute("uSession");

		UsuarioDTO u = usuarioDAO.findById(uSession.getId());

		List<Long> empresas = new ArrayList<>();
		for (EmpresaDTO empresa : u.getEmpresas()) {
			empresas.add(empresa.getId());
		}

		if (empresas.size() > 0) {

			tarjetasControlSinValidez = conductorVehiculoDAO.findByStateOutOfValidateDate(new Date(), empresas);

			model.addAttribute("tarjetasControlSinValidez", tarjetasControlSinValidez);
		}
		return "notifications/notifications-sin-validez";
	}

	@RequestMapping(value = NotificationsConstants.NOTIFICATIONS_SIN_VIGENCIA, method = RequestMethod.GET)
	public String notificationsGetSinVigencia(HttpSession session, Locale locale, Model model) {

		initbreadcrumb(locale, Arrays.asList(
				new BreadcrumbView(NotificationsConstants.NOTIFICATIONS,
						messageSource.getMessage("header.administration.notifications", null, locale)),
				new BreadcrumbView(NotificationsConstants.NOTIFICATIONS_SIN_VIGENCIA,
						messageSource.getMessage("header.administration.notifications.vigencia", null, locale))));

		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);

		List<NotificationDTO> tarjetasControlSinVigencia = new ArrayList<>();

		UsuarioDTO uSession = (UsuarioDTO) session.getAttribute("uSession");

		UsuarioDTO u = usuarioDAO.findById(uSession.getId());

		List<Long> empresas = new ArrayList<>();
		for (EmpresaDTO empresa : u.getEmpresas()) {
			empresas.add(empresa.getId());
		}
		if (empresas.size() > 0) {

			tarjetasControlSinVigencia = conductorVehiculoDAO.findByStateOutOflifeDate(new Date(), empresas);

			model.addAttribute("tarjetasControlSinVigencia", tarjetasControlSinVigencia);

			if (tarjetasControlSinVigencia != null && tarjetasControlSinVigencia.size() > 0) {

				Long countDiasSinvalidez = Long.valueOf(0);
				Long countTarjetas = Long.valueOf(tarjetasControlSinVigencia.size());

				for (NotificationDTO n : tarjetasControlSinVigencia) {
					countDiasSinvalidez += n.getDias().longValue();
				}

				Long promedio = Long.valueOf(countDiasSinvalidez / countTarjetas);
				model.addAttribute("promedio", promedio);

				Long totalNot = conductorVehiculoDAO.countByStateOutOflifeDate(new Date(), empresas);

				Long porcentaje = Long.valueOf((countTarjetas * 100) / totalNot);

				model.addAttribute("porcentaje", porcentaje);
			}

		}

		return "notifications/notifications-sin-vigencia";
	}

	@RequestMapping(value = NotificationsConstants.NOTIFICATIONS_PROXIMOS_VENCIMIENTOS, method = RequestMethod.GET)
	public String notificationsGetProximosVencimientos(HttpSession session, Locale locale, Model model) {

		initbreadcrumb(locale, Arrays.asList(
				new BreadcrumbView(NotificationsConstants.NOTIFICATIONS,
						messageSource.getMessage("header.administration.notifications", null, locale)),
				new BreadcrumbView(NotificationsConstants.NOTIFICATIONS_PROXIMOS_VENCIMIENTOS,
						messageSource.getMessage("header.administration.notifications.proximos", null, locale))));

		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);

		List<NotificationDTO> tarjetasControlProximosVencimientos = new ArrayList<>();

		ParametroDTO vencimiento = parametroSimurDAO.findByKey(SystemParameters.VENCIMIENTO_TARJETA.getValue());

		Calendar validezFecha = Calendar.getInstance();
		validezFecha.add(Calendar.DAY_OF_YEAR, Integer.valueOf(vencimiento.getValorParametro()));

		UsuarioDTO uSession = (UsuarioDTO) session.getAttribute("uSession");

		UsuarioDTO u = usuarioDAO.findById(uSession.getId());

		List<Long> empresas = new ArrayList<>();
		for (EmpresaDTO empresa : u.getEmpresas()) {
			empresas.add(empresa.getId());
		}
		if (empresas.size() > 0) {

			tarjetasControlProximosVencimientos = conductorVehiculoDAO.findByStateBeforeToExpire(true,
					validezFecha.getTime(), empresas);

			model.addAttribute("tarjetasControlProximosVencimientos", tarjetasControlProximosVencimientos);
		}

		return "notifications/notifications-proximos-vencimientos";
	}

	@RequestMapping(value = NotificationsConstants.NOTIFICATIONS_COUNT, method = RequestMethod.GET)
	@ResponseBody
	public Integer getAlerts(HttpSession session, Locale locale, Model model) {
		try {
			UsuarioDTO uSession = (UsuarioDTO) session.getAttribute("uSession");

			if (uSession != null) {
				UsuarioDTO u = usuarioDAO.findById(uSession.getId());

				List<Long> empresas = new ArrayList<>();
				for (EmpresaDTO empresa : u.getEmpresas()) {
					empresas.add(empresa.getId());
				}

				if (empresas.size() > 0) {

					Long numeroTarjetasControlSinVigencia = conductorVehiculoDAO.countByStateOutOfLifeDate(new Date(),
							empresas);

					Long numeroTarjetasControlSinValidez = conductorVehiculoDAO.countByStateOutOfValidateDate(new Date(),
							empresas);

					ParametroDTO vencimiento = parametroSimurDAO.findByKey(SystemParameters.VENCIMIENTO_TARJETA.getValue());

					Long numeroTarjetasControlProximosVencimientos = new Long(0);

					if (vencimiento != null) {

						Calendar validezFecha = Calendar.getInstance();
						validezFecha.add(Calendar.DAY_OF_YEAR, Integer.valueOf(vencimiento.getValorParametro()));

						numeroTarjetasControlProximosVencimientos = conductorVehiculoDAO.countByStateBeforeToExpire(true,
								validezFecha.getTime(), empresas);

					}

					Integer total = numeroTarjetasControlSinVigencia.intValue() + numeroTarjetasControlSinValidez.intValue()
							+ numeroTarjetasControlProximosVencimientos.intValue();
					return total;
				}
			}
		}catch (Exception e){

		}

		return 0;
	}

	@RequestMapping(value = NotificationsConstants.SEND_NOTIFICATIONS, method = RequestMethod.GET)
	public void sendNotifications() throws NoSuchMessageException, MessagingException {

		List<EmpresaDTO> sinVigencia = empresaDAO.stateOutOfLifeDate(new Date());

		List<EmpresaDTO> sinValidez = empresaDAO.stateOutOfValidateDate(new Date());

		ParametroDTO vencimiento = parametroSimurDAO.findByKey(SystemParameters.VENCIMIENTO_TARJETA.getValue());

		List<EmpresaDTO> proxVencimientos = null;
		if (vencimiento != null) {

			Calendar validezFecha = Calendar.getInstance();
			validezFecha.add(Calendar.DAY_OF_YEAR, Integer.valueOf(vencimiento.getValorParametro()));

			proxVencimientos = empresaDAO.stateBeforeToExpire(true, validezFecha.getTime());

		}

		// ENVIAR NITIFICACIONES DE TARJETAS SIN VIGENCIA
		if (sinVigencia != null && sinVigencia.size() > 0) {

			ParametroDTO spMessage = parametroSimurDAO.findByKey(SystemParameters.NOT_TARJEAS_SIN_VIGENCIA.getValue());
			for (EmpresaDTO e : sinVigencia) {
				String messageSinVigencia = spMessage.getValorParametro();
				StringBuilder emails = null;
				for (UsuarioDTO u : e.getUsuarioDTOs()) {
					if (emails == null)
						emails = new StringBuilder(u.getLoginUsuario());
					else
						emails.append(",".concat(u.getLoginUsuario()));
				}
				StringBuilder notifications = new StringBuilder();
				for (NotificationDTO n : e.getNotificationDTOs()) {
					notifications.append("<tr><td>" + n.getTarjetaControl() + "</td></tr>");
				}
				messageSinVigencia = messageSinVigencia.replaceAll("\\$\\{tarjetas\\}", notifications.toString());
				
				System.err.println(messageSinVigencia);

				notificationSender.sendEmail(emails.toString(),
						messageSource.getMessage("not.email.subject.sin.vingencia", null, Locale.ENGLISH),
						messageSinVigencia);
			}
		}

		// ENVIAR NITIFICACIONES DE TARJETAS SIN VALIDEZ
		if (sinValidez != null && sinValidez.size() > 0) {
			ParametroDTO spMessage = parametroSimurDAO.findByKey(SystemParameters.NOT_TARJEAS_SIN_VALIDEZ.getValue());
			for (EmpresaDTO e : sinValidez) {
				String messageSinnvalidez = spMessage.getValorParametro();
				StringBuilder emails = null;
				for (UsuarioDTO u : e.getUsuarioDTOs()) {
					if (emails == null)
						emails = new StringBuilder(u.getLoginUsuario());
					else
						emails.append(",".concat(u.getLoginUsuario()));
				}
				StringBuilder notifications = new StringBuilder();
				for (NotificationDTO n : e.getNotificationDTOs()) {
					notifications.append("<tr><td>" + n.getTarjetaControl() + "</td></tr>");
				}
				messageSinnvalidez = messageSinnvalidez.replaceAll("\\$\\{tarjetas\\}", notifications.toString());
				notificationSender.sendEmail(emails.toString(),
						messageSource.getMessage("not.email.subject.sin.validez", null, Locale.ENGLISH),
						messageSinnvalidez);
			}

		}

		// ENVIAR NITIFICACIONES DE TARJETAS PROXIMAS A VENCER
		if (proxVencimientos != null && proxVencimientos.size() > 0) {
			ParametroDTO spMessage = parametroSimurDAO.findByKey(SystemParameters.NOT_TARJEAS_PROX_VENCERSE.getValue());
			for (EmpresaDTO e : proxVencimientos) {
				String messageProxvencimientos = spMessage.getValorParametro();
				StringBuilder emails = null;
				for (UsuarioDTO u : e.getUsuarioDTOs()) {
					if (emails == null)
						emails = new StringBuilder(u.getLoginUsuario());
					else
						emails.append(",".concat(u.getLoginUsuario()));
				}
				StringBuilder notifications = new StringBuilder();
				for (NotificationDTO n : e.getNotificationDTOs()) {
					notifications.append("<tr><td>" + n.getTarjetaControl() + "</td></tr>");
				}
				messageProxvencimientos = messageProxvencimientos.replaceAll("\\$\\{tarjetas\\}",
						notifications.toString());
				notificationSender.sendEmail(emails.toString(),
						messageSource.getMessage("not.email.subject.prox.vencer", null, Locale.ENGLISH),
						messageProxvencimientos);
			}

		}

	}

}