package co.gov.movilidadbogota.web.controller;

import java.util.Arrays;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.gov.movilidadbogota.core.dao.ParametroSimurDAO;
import co.gov.movilidadbogota.core.dao.UsuarioDAO;
import co.gov.movilidadbogota.core.dto.ParametroDTO;
import co.gov.movilidadbogota.core.dto.UsuarioDTO;
import co.gov.movilidadbogota.core.entity.UsuarioEntity;
import co.gov.movilidadbogota.core.entity.UsuarioEntity_;
import co.gov.movilidadbogota.core.util.SecurityUtils;
import co.gov.movilidadbogota.core.util.SystemParameters;
import co.gov.movilidadbogota.web.constants.AdministrationConstants;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.core.util.NotificationSender;
import co.gov.movilidadbogota.web.util.TipoUsuarioEnum;
import co.gov.movilidadbogota.web.view.breadcrumb.BreadcrumbView;

@Controller
public class AdministrationController extends GenericController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdministrationController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private NotificationController notificationController;

	@Autowired
	private NotificationSender notificationSender;

	@Autowired
	private ParametroSimurDAO parametroSimurDAO;

	@RequestMapping(value = { "/", AdministrationConstants.HOME_MAPPING }, method = RequestMethod.GET)
	@Secured({ "ROLE_EXTERNAL", "ROLE_SDM" })
	public String homeGet(HttpSession session, Locale locale, Model model) {
		initbreadcrumb(locale, Arrays.asList(new BreadcrumbView(AdministrationConstants.HOME_MAPPING,
				messageSource.getMessage("header.administration.home", null, locale))));

		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			UsuarioEntity u = usuarioDAO.findOneByAttribute(UsuarioEntity_.loginUsuario,
					auth.getPrincipal().toString());

			String rol = null;
			for (GrantedAuthority g : auth.getAuthorities()) {
				rol = g.getAuthority();
			}
			UsuarioDTO dto = new UsuarioDTO(u.getId(), u.getLoginUsuario(), rol);
			dto.setNombre(u.getLoginUsuario().split("@")[0]);
			session.setAttribute("uSession", dto);
			if (u.getPerfil().getId() == TipoUsuarioEnum.EXTERNO.getCode()) {

				Integer i = notificationController.getAlerts(session, locale, model);
				if (i > 0) {
					model.addAttribute("alert",
							messageSource.getMessage("form.administration.popup.alert", new Object[]{i}, locale));
				}

			}
			session.setAttribute("uSession", dto);

			if (u.getUrlSafeToken() != null) {
				return "administration/change-password";
			}

		}

		return "administration/home";
	}

	@RequestMapping(value = AdministrationConstants.LOGIN, method = RequestMethod.GET)
	public ModelAndView login(Locale locale, @RequestParam(value = "exception", required = false) String exception,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject(CommonConstants.PAGE_MESSAGE_ERROR,
					messageSource.getMessage("common.login.error", null, locale));
		}

		if (StringUtils.isNoneBlank(exception)) {
			model.addObject(CommonConstants.PAGE_MESSAGE_ERROR, exception);
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("common/login");
		initbreadcrumb(locale, Arrays.asList(new BreadcrumbView(AdministrationConstants.CHANGE_PASS,
				messageSource.getMessage("header.administration.login", null, locale))));

		model.addObject(CommonConstants.VIEW_BREADCRUMB, breadcrumb);

		return model;

	}

	@RequestMapping(value = { AdministrationConstants.CHANGE_PASS }, method = RequestMethod.GET)
	@Secured({ "ROLE_EXTERNAL" })
	public String changePasswordGet(HttpSession session, Locale locale, Model model) {

		initbreadcrumb(locale, Arrays.asList(new BreadcrumbView(AdministrationConstants.HOME_MAPPING,
				messageSource.getMessage("header.administration.password.change", null, locale))));

		model.addAttribute(CommonConstants.PAGE_MESSAGE_INFO,
				messageSource.getMessage("information.change.password", null, locale));

		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);

		return "administration/change-password";
	}

	@RequestMapping(value = { AdministrationConstants.CHANGE_PASS }, method = RequestMethod.POST)
	@Secured({ "ROLE_EXTERNAL" })
	public String changePasswordPost(HttpSession session, Locale locale, Model model, @ModelAttribute UsuarioDTO form) {

		UsuarioDTO dto = (UsuarioDTO) session.getAttribute("uSession");
		UsuarioEntity u = usuarioDAO.findByPrimaryKey(dto.getId());

		u.setClaveUsuario(SecurityUtils.hashPassword(form.getNuevaClave()));
		u.setUrlSafeToken(null);

		usuarioDAO.update(u);

		model.addAttribute(CommonConstants.PAGE_MESSAGE_INFO,
				messageSource.getMessage("message.password.chage.successful", null, locale));

		return homeGet(session, locale, model);
	}

	@RequestMapping(value = { AdministrationConstants.SEND_NEW_PASS }, method = RequestMethod.POST)
	public String sendChangePasswordGet(HttpSession session, Locale locale, @ModelAttribute UsuarioDTO form,
			Model model, RedirectAttributes redirectAttributes) {

		UsuarioEntity u = usuarioDAO.findOneByAttribute(UsuarioEntity_.loginUsuario, form.getEmail());
		ParametroDTO contextUrl = parametroSimurDAO.findByKey(SystemParameters.ADMINISTRATION_CONTEXTURL.getValue());

		if (u != null) {
			String password = SecurityUtils.generateUrlSafeToken();
			u.setClaveUsuario(SecurityUtils.hashPassword(password));
			u.setUrlSafeToken(SecurityUtils.hashPassword(password));

			StringBuilder message = new StringBuilder();
			message.append(messageSource.getMessage("message.welcome", null, locale));
			message.append(messageSource.getMessage("message.msg.user.new.password",
					new Object[] { u.getLoginUsuario(), password, contextUrl.getValorParametro() }, locale));
			message.append(messageSource.getMessage("message.footer", null, locale));

			String subject = messageSource.getMessage("header.administration", null, locale) + " "
					+ messageSource.getMessage("message.subject.user.new.password", null, locale);

			try {
				usuarioDAO.actualizarUsuario(u,message, subject );
				
				redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_SUCCESS,
						messageSource.getMessage("form.change.password.successful", null, locale));
			} catch (Exception e) {
				LOGGER.error("ERROR:", e);

				return "redirect:/login?exception=" + messageSource.getMessage("common.error.generic", null, locale);
			}

		} else {
			return "redirect:/login?exception="
					+ messageSource.getMessage("error.message.password.forgot", null, locale);
		}

		return "redirect:" + AdministrationConstants.LOGIN;
	}

}