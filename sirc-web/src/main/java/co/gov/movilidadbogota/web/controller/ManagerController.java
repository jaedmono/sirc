package co.gov.movilidadbogota.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.gov.movilidadbogota.core.dao.ParametroSimurDAO;
import co.gov.movilidadbogota.core.dao.PerfilDAO;
import co.gov.movilidadbogota.core.dao.PersonaDAO;
import co.gov.movilidadbogota.core.dao.TipoUsuarioDAO;
import co.gov.movilidadbogota.core.dao.UsuarioDAO;
import co.gov.movilidadbogota.core.dto.ManagerUserDTO;
import co.gov.movilidadbogota.core.dto.UsuarioDTO;
import co.gov.movilidadbogota.core.entity.PersonaEntity;
import co.gov.movilidadbogota.core.entity.UsuarioEntity;
import co.gov.movilidadbogota.core.entity.UsuarioEntity_;
import co.gov.movilidadbogota.core.security.LdapFacadeImpl;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.web.constants.ManagerConstants;
import co.gov.movilidadbogota.web.constants.UserConstants;
import co.gov.movilidadbogota.web.util.PerfilUsuarioEnum;
import co.gov.movilidadbogota.web.util.TipoUsuarioEnum;
import co.gov.movilidadbogota.web.util.dto.ResponseDto;
import co.gov.movilidadbogota.web.view.breadcrumb.BreadcrumbView;

@Controller
public class ManagerController extends GenericController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ParametroSimurDAO parametroDAO;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private PersonaDAO personaDAO;
	
	@Autowired
	private TipoUsuarioDAO tipoUsuarioDAO;
	
	@Autowired
	private PerfilDAO perfilDAO;

	private static String URL_CONSULT_MANAGER_USER = "manager/consult-manager-user";

	@RequestMapping(value = { ManagerConstants.CONSULT_MANAGER_USER }, method = RequestMethod.GET)
	@Secured({ "ROLE_SDM" })
	public String consultManagerUserGet(HttpSession session, Locale locale, Model model) {

		initbreadcrumb(locale, Arrays.asList(
				new BreadcrumbView(UserConstants.CONSULT_EXTERNAL_USER,
						messageSource.getMessage("header.administration.users", null, locale)),
				new BreadcrumbView(UserConstants.CONSULT_EXTERNAL_USER,
						messageSource.getMessage("header.administration.manager.users.consult", null, locale))));

		model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);

		List<ManagerUserDTO> managerUsers = new ArrayList<>();
		
		List<UsuarioEntity> usuarios = usuarioDAO.findAll();
		
		for (UsuarioEntity usuarioEntity : usuarios) {
			if (usuarioEntity.getIdTipoUsuario().getId() == TipoUsuarioEnum.INTERNO.getCode()) {
				managerUsers.add(new ManagerUserDTO(usuarioEntity.getId(), usuarioEntity.getLoginUsuario()));
			}
		}

		model.addAttribute("users", managerUsers);

		return URL_CONSULT_MANAGER_USER;
	}

	@RequestMapping(value = { ManagerConstants.CONSULT_MANAGER_USER }, method = RequestMethod.POST)
	@ResponseBody
	@Secured({ "ROLE_SDM" })
	public ResponseDto<UsuarioDTO> searchLdapUser(@RequestParam() String username, HttpSession session,
			Locale locale, Model model) {

		try {
			LdapFacadeImpl facadeLdap = new LdapFacadeImpl();
			UsuarioDTO user = facadeLdap.connectLDAP(username, parametroDAO.getLdapParameters());

			if (user == null || StringUtils.isEmpty(user.getLoginUsuario())) {
				return ResponseDto
						.error(messageSource.getMessage("error.message.add.ldap.user", null, Locale.getDefault()));
			} else {
				return ResponseDto.success(user);
			}
		} catch (Exception e) {
			return ResponseDto.error(messageSource.getMessage("common.error.generic", null, Locale.getDefault()));
		}

	}
	
	@RequestMapping(value = { ManagerConstants.ADD_MANAGER_USER }, method = RequestMethod.POST)
	@ResponseBody
	@Secured({ "ROLE_SDM" })
	public ResponseDto<UsuarioDTO> addManagerUser(@RequestParam() String username, HttpSession session,
			Locale locale, Model model) {

		try {
			LdapFacadeImpl facadeLdap = new LdapFacadeImpl();
			UsuarioDTO user = facadeLdap.connectLDAP(username, parametroDAO.getLdapParameters());

			if (user == null || StringUtils.isEmpty(user.getLoginUsuario())) {
				return ResponseDto
						.error(messageSource.getMessage("error.message.add.ldap.user", null, Locale.getDefault()));
			} else {
				
				UsuarioEntity usuarioExiste = usuarioDAO.findOneByAttribute(UsuarioEntity_.loginUsuario, username);
				if (usuarioExiste != null) {
					return ResponseDto.error(messageSource.getMessage("error.message.add.ldap.user.exist", null, Locale.getDefault()));
				}
				
				UsuarioDTO loginUser = (UsuarioDTO) session.getAttribute("uSession");
				
				PersonaEntity persona = new PersonaEntity();
                persona.setApellidos(user.getApellido() != null ? user.getApellido() : "");
				persona.setCorreoElectronico(user.getEmail());
				persona.setFechaModifica(new Date());
				persona.setIdEstado(true);
				persona.setNombres(user.getNombre());
				
				personaDAO.create(persona);
				
				UsuarioEntity entity = new UsuarioEntity();
				entity.setIdEstado(true);
				entity.setFechaModifica(new Date());
				entity.setIdTipoUsuario(tipoUsuarioDAO.findByPrimaryKey(TipoUsuarioEnum.INTERNO.getCode()));
				entity.setLoginUsuario(user.getLoginUsuario());
				entity.setPerfil(perfilDAO.findByPrimaryKey(PerfilUsuarioEnum.ROLE_SDM.getCode()));
				entity.setUsrModifica(loginUser.getLoginUsuario());
				entity.setPersona(persona);
				
				usuarioDAO.create(entity);
				user.setId(entity.getId());
				
				ResponseDto response = ResponseDto.success(messageSource.getMessage("message.add.ldap.user", new Object[]{username}, Locale.getDefault()));
				response.setObject(user);
				return response;
			}
		} catch (Exception e) {
			return ResponseDto.error(messageSource.getMessage("common.error.generic", null, Locale.getDefault()));
		}

	}

	@RequestMapping(value = ManagerConstants.REMOVE_MANAGER_USER, method = RequestMethod.POST)
	@ResponseBody
	@Secured({ "ROLE_SDM" })
	public ResponseDto<String> removeUser(@RequestParam long idUser) {

		try {
			
			UsuarioEntity entity = usuarioDAO.findByPrimaryKey(idUser);
			String username = entity.getLoginUsuario();
			Long personaId = entity.getPersona().getId();
			usuarioDAO.delete(idUser);
			personaDAO.delete(personaId);
			
			return ResponseDto.success(messageSource.getMessage("message.remove.ldap.user",
					new Object[] { username }, Locale.getDefault()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseDto.error(messageSource.getMessage("common.error.generic", null, Locale.getDefault()));
		}

	}

}