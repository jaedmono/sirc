package co.gov.movilidadbogota.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.gov.movilidadbogota.core.dao.ParametroSimurDAO;
import co.gov.movilidadbogota.core.dao.PerfilDAO;
import co.gov.movilidadbogota.core.dao.PersonaDAO;
import co.gov.movilidadbogota.core.dao.TipoUsuarioDAO;
import co.gov.movilidadbogota.core.dao.UsuarioDAO;
import co.gov.movilidadbogota.core.dto.CreateUserDTO;
import co.gov.movilidadbogota.core.dto.ParametroDTO;
import co.gov.movilidadbogota.core.dto.UsuarioDTO;
import co.gov.movilidadbogota.core.dto.validator.CreateEditUserValidator;
import co.gov.movilidadbogota.core.entity.PersonaEntity;
import co.gov.movilidadbogota.core.entity.UsuarioEntity;
import co.gov.movilidadbogota.core.entity.UsuarioEntity_;
import co.gov.movilidadbogota.core.util.SecurityUtils;
import co.gov.movilidadbogota.core.util.SystemParameters;
import co.gov.movilidadbogota.web.constants.AdministrationConstants;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.web.constants.UserConstants;
import co.gov.movilidadbogota.core.util.NotificationSender;
import co.gov.movilidadbogota.web.util.dto.ResponseDto;
import co.gov.movilidadbogota.web.view.breadcrumb.BreadcrumbView;

@Controller
public class UserController extends GenericController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private PersonaDAO personaDAO;
	@Autowired
	private PerfilDAO perfilDAO;

	@Autowired
	private TipoUsuarioDAO tipoUsuarioDAO;
	
	@Autowired
	private NotificationSender notificationSender;
	
	@Autowired
	private ParametroSimurDAO parametroSimurDAO;

	private static String URL_CREATE_EXTERNAL_USER = "user/create-external-user";
	private static String URL_CONSULT_EXTERNAL_USER = "user/consult-external-user";

	private static String COMMAND_NAME = "userDTO";

	@Secured({ "ROLE_SDM" })
	@RequestMapping(value = { UserConstants.CREATE_EXTERNAL_USER }, method = RequestMethod.GET)
	public String createExternalUserGet(HttpSession session, Locale locale, Model model) {

		setBreadcrumbCreateExternalUser(model, locale);
		model.addAttribute(COMMAND_NAME, new CreateUserDTO());

		return URL_CREATE_EXTERNAL_USER;
	}

	@Secured({ "ROLE_SDM" })
	@RequestMapping(value = { UserConstants.CREATE_EXTERNAL_USER }, method = RequestMethod.POST)
	public String createExternalUserPost(@ModelAttribute CreateUserDTO userDTO, HttpSession session, Locale locale,
			Model model, BindingResult result) {

		setBreadcrumbCreateExternalUser(model, locale);

		if (!genericValidator(new CreateEditUserValidator(), model, result, userDTO, COMMAND_NAME)) {

			String email = userDTO.getEmail();
			String password = SecurityUtils.generateUrlSafeToken();
			String token = SecurityUtils.generateUrlSafeToken();
			ParametroDTO contextUrl = parametroSimurDAO.findByKey(SystemParameters.ADMINISTRATION_CONTEXTURL.getValue());

			try {
				// VALIDAR QUE NO EXISTE PREVIAMENTE Y ENVIAR EN CASO DE QUE
				// EXISTA
				if (usuarioDAO.findOneByAttribute(UsuarioEntity_.loginUsuario, userDTO.getEmail()) != null) {
					
					model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
							messageSource.getMessage("error.message.add.user.exist", null, locale));
					return URL_CREATE_EXTERNAL_USER;
				}
				
				
				UsuarioEntity entity = new UsuarioEntity();
                                PersonaEntity persona = new PersonaEntity();
                                personaDAO.create(persona);

				entity.setClaveUsuario(SecurityUtils.hashPassword(password));
				entity.setFechaModifica(new Date());
				entity.setIdEstado(false);
				entity.setLoginUsuario(email);
				entity.setIdTipoUsuario(tipoUsuarioDAO.findByPrimaryKey(new Long(1)));
				entity.setPersona(persona);
				entity.setPerfil(perfilDAO.findByPrimaryKey(new Long(1)));
				entity.setUrlSafeToken(token);
                                usuarioDAO.crearUsuario(entity);
				// TODO Autogenerar código de validar cuenta de correo
				
				StringBuilder message = new StringBuilder();
				message.append(messageSource.getMessage("message.welcome", null, locale));
				message.append(messageSource.getMessage("message.msg.user.create",
						new Object[] { email, password, token, entity.getId(), contextUrl.getValorParametro() }, locale));
				message.append(messageSource.getMessage("message.footer", null, locale));

				String subject = messageSource.getMessage("header.administration", null, locale)
						+ messageSource.getMessage("message.subject.user.create", null, locale);
				
                                notificationSender.sendEmail(entity.getLoginUsuario(), subject, message.toString());

				model.addAttribute(CommonConstants.PAGE_MESSAGE_SUCCESS,
						messageSource.getMessage("form.user.create.message", new Object[] { email }, locale));
			
			} catch (Exception e) {
				LOGGER.error("ERROR:", e);
				model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
						messageSource.getMessage("message.msg.error", null, locale));
			}
		}

		return URL_CREATE_EXTERNAL_USER;
	}

	private void setBreadcrumbCreateExternalUser(Model model, Locale locale) {
		initbreadcrumb(locale,
				Arrays.asList(
						new BreadcrumbView(UserConstants.CONSULT_EXTERNAL_USER,
								messageSource.getMessage("header.administration.users", null, locale)),
						new BreadcrumbView(UserConstants.CREATE_EXTERNAL_USER,
								messageSource.getMessage("header.administration.users.create", null, locale))));

		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);
	}

	@Secured({ "ROLE_SDM" })
	@RequestMapping(value = { UserConstants.CONSULT_EXTERNAL_USER }, method = RequestMethod.GET)
	public String consultExternalUserGet(HttpSession session, Locale locale, Model model) {

		initbreadcrumb(locale,
				Arrays.asList(
						new BreadcrumbView(UserConstants.CONSULT_EXTERNAL_USER,
								messageSource.getMessage("header.administration.users", null, locale)),
						new BreadcrumbView(UserConstants.CONSULT_EXTERNAL_USER,
								messageSource.getMessage("header.administration.users.consult", null, locale))));

		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);

		// TODO BUSCAR TODOS LOS USUARIOS FIND ALL
		List<UsuarioEntity> usersEntity = usuarioDAO.findByState(true);
		List<UsuarioDTO> usersDTO = new ArrayList<>();

		for (UsuarioEntity userEntity : usersEntity) {
			UsuarioDTO userDTO = new UsuarioDTO(userEntity.getId(), userEntity.getLoginUsuario());
			usersDTO.add(userDTO);
		}

		model.addAttribute("users", usersDTO);
		model.addAttribute(COMMAND_NAME, new CreateUserDTO());

		return URL_CONSULT_EXTERNAL_USER;
	}

	@Secured({ "ROLE_SDM" })
	@RequestMapping(value = { UserConstants.CONSULT_EXTERNAL_USER }, method = RequestMethod.POST)
	public String searchUserByEmail(@ModelAttribute CreateUserDTO userDTO, HttpSession session, Locale locale,
			Model model, BindingResult result) {

		if (!genericValidator(new CreateEditUserValidator(), model, result, userDTO, COMMAND_NAME)) {
			// TODO BUSCAR POR CORREO

			List<UsuarioEntity> users = new ArrayList<>();

			List<UsuarioEntity> resultado = new ArrayList<>();
			for (UsuarioEntity usuarioEntity : users) {
				if (usuarioEntity.getLoginUsuario().contains(userDTO.getEmail())) {
					resultado.add(usuarioEntity);
				}
			}

			// TODO ENVIAR DTOS
			model.addAttribute("users", resultado);
		}

		return URL_CONSULT_EXTERNAL_USER;
	}

	@Secured({ "ROLE_SDM" })
	@RequestMapping(value = UserConstants.REMOVE_EXTERNAL_USER, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto<String> removeUser(@RequestParam long idUser, Locale locale) {

		try {
			usuarioDAO.disableUser(idUser);
			return ResponseDto.success(messageSource.getMessage("message.remove.user", null, locale));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseDto.error(messageSource.getMessage("common.error.generic", null, locale));
		}

	}

	@RequestMapping(value = { UserConstants.ACTIVATE_EXTERNAL_USER }, method = RequestMethod.GET)
	public String activateExternalUserGet(HttpSession session, Locale locale, Model model, @RequestParam long user,
			@RequestParam String token, RedirectAttributes redirectAttributes) {

		UsuarioEntity u = usuarioDAO.findByPrimaryKey(user);

		if (u != null && !u.isIdEstado() && u.getUrlSafeToken().equals(token)) {
			u.setIdEstado(true);
			usuarioDAO.update(u);
			redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_SUCCESS,
					messageSource.getMessage("common.activated.user.succesfull", null, locale));
		} else {
			redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
					messageSource.getMessage("common.activated.user.error", null, locale));
		}

		return "redirect:" + AdministrationConstants.LOGIN;
	}

}