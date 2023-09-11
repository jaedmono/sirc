package co.gov.movilidadbogota.web.controller;

import co.gov.movilidadbogota.core.dao.ConductorVehiculoDAO;
import co.gov.movilidadbogota.core.dao.EmpresaDAO;
import co.gov.movilidadbogota.core.dao.UsuarioDAO;
import co.gov.movilidadbogota.core.dao.VehiculoFactorCalidadDAO;
import co.gov.movilidadbogota.core.dto.ConductorVehiculoDTO;
import co.gov.movilidadbogota.core.dto.EmpresaDTO;
import co.gov.movilidadbogota.core.dto.FactorCalidadDTO;
import co.gov.movilidadbogota.core.dto.UsuarioDTO;
import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity;
import co.gov.movilidadbogota.core.entity.EmpresaEntity;
import co.gov.movilidadbogota.core.entity.EmpresaEntity_;
import co.gov.movilidadbogota.core.entity.UsuarioEntity;
import co.gov.movilidadbogota.core.entity.VehiculoFactorCalidadEntity;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.web.constants.FactorCalidadConstants;
import co.gov.movilidadbogota.web.view.breadcrumb.BreadcrumbView;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Secured({"ROLE_SDM"})
public class EliminarFactorCalidadController extends GenericController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private EmpresaDAO empresaDAO;

    @Autowired
    private VehiculoFactorCalidadDAO factorCalidadDAO;
    @Autowired
    private ConductorVehiculoDAO conductorVehiculoDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(EliminarFactorCalidadController.class);

    private static final String URL_ELIMINAR_FACTOR_CALIDAD = "cargar/eliminar-factor-calidad";
    private static final String COMMAND_NAME = "factorCalidadDTO";

    @RequestMapping(value = {FactorCalidadConstants.MODIFICAR_FACTOR_CALIDAD}, method = RequestMethod.GET)
    public String createExternalUser(HttpSession session, Locale locale, Model model,
            RedirectAttributes redirectAttributes) {
        model.addAttribute(COMMAND_NAME, null);
        setBreadcrumbCreateExternalUser(model, locale);
        return URL_ELIMINAR_FACTOR_CALIDAD;
    }

    @RequestMapping(value = {FactorCalidadConstants.CONSULTAR_FACTOR_CALIDAD_VEHICULO}, method = RequestMethod.GET)
    public String consultExternalUserGet(HttpSession session, Locale locale, Model model, @RequestParam String nroPlaca,
            RedirectAttributes redirectAttributes) {
        try {
            FactorCalidadDTO factorCalidadDTO = new FactorCalidadDTO();
            factorCalidadDTO.setPlaca(nroPlaca.isEmpty() ? "" : nroPlaca.toUpperCase());
            factorCalidadDTO.setIdEstado(1);
            List<FactorCalidadDTO> result = factorCalidadDAO.consultarFactorCalidad(factorCalidadDTO);
            if (!result.isEmpty()) {
                factorCalidadDTO = result.get(0);
                model.addAttribute(COMMAND_NAME, factorCalidadDTO);
            } else {
                model.addAttribute(CommonConstants.PAGE_MESSAGE_INFO,
                        messageSource.getMessage("header.administration.factorcalidad.consultasinresultados", new Object[]{nroPlaca.toUpperCase()}, locale));
            }
        } catch (Exception e) {
            LOGGER.error("Error consultar factor calidad vehículo:", e);
            model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                    messageSource.getMessage("header.administration.factorcalidad.errorconsulta", new Object[]{nroPlaca.toUpperCase()}, locale));
        }
        setBreadcrumbCreateExternalUser(model, locale);
        return URL_ELIMINAR_FACTOR_CALIDAD;
    }

    @RequestMapping(value = {FactorCalidadConstants.ELIMINAR_FACTOR_CALIDAD_VEHICULO}, method = RequestMethod.POST)
    public String eliminarFactorCalidadVehiculo(@ModelAttribute FactorCalidadDTO factorCalidadDTO, HttpSession session,
            Locale locale, Model model, BindingResult result, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        setBreadcrumbCreateExternalUser(model, locale);
        List<FactorCalidadDTO> resultado = factorCalidadDAO.consultarFactorCalidad(factorCalidadDTO);
        if (!resultado.isEmpty()) {
            factorCalidadDTO = resultado.get(0);
            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("uSession");
            UsuarioEntity usuarioEntity = usuarioDAO.findByPrimaryKey(usuario.getId());
            EmpresaEntity empresa = empresaDAO.findOneByAttribute(EmpresaEntity_.codigoEmpresa, factorCalidadDTO.getCodSDMEmpresa());
            VehiculoFactorCalidadEntity vehiculoFactorCalidadEntity = new VehiculoFactorCalidadEntity();
            vehiculoFactorCalidadEntity.setId(factorCalidadDTO.getId());
            vehiculoFactorCalidadEntity.setEmpresa(empresa);
            vehiculoFactorCalidadEntity.setFechaRegistro(factorCalidadDTO.getFechaRegistro());
            vehiculoFactorCalidadEntity.setFechaNovedad(Calendar.getInstance().getTime());
            vehiculoFactorCalidadEntity.setIdEstado(0);
            vehiculoFactorCalidadEntity.setNroPlaca(factorCalidadDTO.getPlaca());
            vehiculoFactorCalidadEntity.setUsuario(usuarioEntity);
            factorCalidadDAO.update(vehiculoFactorCalidadEntity);
            ConductorVehiculoDTO conductorVehiculoDTO = new ConductorVehiculoDTO();
            conductorVehiculoDTO.setPlaca(factorCalidadDTO.getPlaca());
            EmpresaDTO empresaDTO = new EmpresaDTO(empresa.getId(), empresa.getCodigoEmpresa());
            conductorVehiculoDTO.setEmpresaDTO(empresaDTO);
            conductorVehiculoDTO.setTarjetaControlEstado(1);
            List<ConductorVehiculoEntity> conductorVehiculoList = conductorVehiculoDAO.consultarConductorVehiculoEntity(conductorVehiculoDTO);
            if (conductorVehiculoList != null && !conductorVehiculoList.isEmpty()) {
                for (ConductorVehiculoEntity conductorVehiculoEntity : conductorVehiculoList) {
                    conductorVehiculoEntity.setFactorCalidad(null);
                    conductorVehiculoDAO.update(conductorVehiculoEntity);
                }
            }
            model.addAttribute(COMMAND_NAME, null);
            model.addAttribute(CommonConstants.PAGE_MESSAGE_SUCCESS,
                    messageSource.getMessage("header.administration.factorcalidad.eliminarsuccess", new Object[]{factorCalidadDTO.getPlaca()}, locale));
            return URL_ELIMINAR_FACTOR_CALIDAD;
        }
        model.addAttribute(COMMAND_NAME, factorCalidadDTO);
        model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                messageSource.getMessage("header.administration.factorcalidad.eliminarerror", new Object[]{}, locale));
        return URL_ELIMINAR_FACTOR_CALIDAD;
    }

    private void setBreadcrumbCreateExternalUser(Model model, Locale locale) {
        initbreadcrumb(
                locale, Arrays.asList(
                        new BreadcrumbView(FactorCalidadConstants.CARGAR_FACTOR_CALIDAD,
                                messageSource.getMessage("header.administration.factorcalidad", null, locale)),
                        new BreadcrumbView(FactorCalidadConstants.CARGAR_FACTOR_CALIDAD,
                                messageSource.getMessage("header.administration.factorcalidad.modificar", null, locale)))
        );
        model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);
    }

}
