package co.gov.movilidadbogota.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import co.gov.movilidadbogota.core.dao.EmpresaDAO;
import co.gov.movilidadbogota.core.dao.UsuarioDAO;
import co.gov.movilidadbogota.core.dto.ConsultarEmpresaDTO;
import co.gov.movilidadbogota.core.entity.EmpresaEntity;
import co.gov.movilidadbogota.core.entity.UsuarioEntity;
import co.gov.movilidadbogota.web.constants.CompanyConstants;
import co.gov.movilidadbogota.web.util.dto.ResponseDto;

@Controller
@Secured({ "ROLE_SDM" })
public class CompanyController extends GenericController {

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private EmpresaDAO empresaDAO;

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private MessageSource messageSource;

	public List<EmpresaEntity> getEmpresas() {
		List<EmpresaEntity> empresas = empresaDAO.findAll();
		return empresas;
	}

	@RequestMapping(value = CompanyConstants.CONSULT_COMPANIES_BY_USER, method = RequestMethod.GET)
	@ResponseBody
	public List<ConsultarEmpresaDTO> getCompaniesByIdUser(@RequestParam String idUser) {

		List<ConsultarEmpresaDTO> resultado = usuarioDAO.findCompanysByUserId(Long.parseLong(idUser));

		try {
			return resultado;
		} catch (Exception e) {
			return null;
		}

	}

	@RequestMapping(value = CompanyConstants.CONSULT_COMPANIES, method = RequestMethod.GET)
	@ResponseBody
	public List<ConsultarEmpresaDTO> searchCompaniesByText(@RequestParam String text) {

		// TODO BUSCAR EMPRESAS POR TEXTO

		List<ConsultarEmpresaDTO> resultado = new ArrayList<>();
		for (EmpresaEntity empresaEntity : getEmpresas()) {
			if (empresaEntity.getNombreRazonSocial().contains(text)) {
				resultado.add(new ConsultarEmpresaDTO(empresaEntity.getId(), empresaEntity.getNombreRazonSocial()));
			}
		}

		try {
			return resultado;
		} catch (Exception e) {
			return null;
		}

	}

	@RequestMapping(value = CompanyConstants.ADD_COMPANY_TO_USER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto<String> addCompanyToUser(@RequestParam long idUser, long idCompany) {

		EmpresaEntity company = empresaDAO.findByPrimaryKey(idCompany);
		UsuarioEntity usuario = null;

		if (company == null) {
			return ResponseDto.error(messageSource.getMessage("message.add.user.company.company.non.existent",
					new Object[] { idCompany }, Locale.getDefault()));
		} else {
			usuario = usuarioDAO.findByUserIdCompanyId(idUser, idCompany);
			if (usuario != null) {
				return ResponseDto.error(messageSource.getMessage("message.add.user.company.warning",
						new Object[] { idCompany }, Locale.getDefault()));
			} else {
				usuario = usuarioDAO.findUserByCompanyId(idCompany);
				if (usuario != null) {
					return ResponseDto.error(messageSource.getMessage("message.add.user.company.already.associated.warning",
							new Object[] { usuario.getLoginUsuario() }, Locale.getDefault()));
				}
			}
		}

		try {
			String result = usuarioDAO.associateCompany(company, idUser);
			if (result.equals("error")) {
				return ResponseDto.error(messageSource.getMessage("message.add.user.company.user.non.existent",
						new Object[] { idUser }, Locale.getDefault()));
			} else {
				return ResponseDto
						.success(messageSource.getMessage("message.add.user.company", null, Locale.getDefault()));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseDto.error(messageSource.getMessage("common.error.generic", null, Locale.getDefault()));
		}

	}

	@RequestMapping(value = CompanyConstants.REMOVE_COMPANY_TO_USER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto<String> removeCompanyToUser(@RequestParam long idUser, long idCompany) {

		EmpresaEntity company = empresaDAO.findByPrimaryKey(idCompany);
		UsuarioEntity usuario = null;
		try {
			if (company == null) {
				return ResponseDto.error(messageSource.getMessage("message.add.user.company.company.non.existent",
						new Object[] { idCompany }, Locale.getDefault()));
			}

			usuario = usuarioDAO.findByUserIdCompanyId(idUser, idCompany);
			if (usuario != null) {
				String result = usuarioDAO.disassociateCompany(company, idUser);
				if (result.equals("error")) {
					return ResponseDto
							.error(messageSource.getMessage("common.error.generic", null, Locale.getDefault()));
				} else {
					return ResponseDto.success(
							messageSource.getMessage("message.remove.user.company", null, Locale.getDefault()));
				}
			} else {
				return ResponseDto.success(messageSource.getMessage("message.add.user.company.relation.non.existent",
						new Object[] { idCompany }, Locale.getDefault()));

			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseDto.error(messageSource.getMessage("common.error.generic", null, Locale.getDefault()));

		}

	}

}
