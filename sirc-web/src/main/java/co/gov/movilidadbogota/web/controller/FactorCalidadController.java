package co.gov.movilidadbogota.web.controller;

import co.gov.movilidadbogota.core.dao.EmpresaDAO;
import co.gov.movilidadbogota.core.dao.UsuarioDAO;
import co.gov.movilidadbogota.core.dao.VehiculoFactorCalidadDAO;
import co.gov.movilidadbogota.core.dto.FactorCalidadDTO;
import co.gov.movilidadbogota.core.dto.UsuarioDTO;
import co.gov.movilidadbogota.core.entity.EmpresaEntity;
import co.gov.movilidadbogota.core.entity.EmpresaEntity_;
import co.gov.movilidadbogota.core.entity.UsuarioEntity;
import co.gov.movilidadbogota.core.entity.VehiculoFactorCalidadEntity;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.web.constants.FactorCalidadConstants;
import co.gov.movilidadbogota.web.view.breadcrumb.BreadcrumbView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Secured({"ROLE_SDM"})
public class FactorCalidadController extends GenericController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private EmpresaDAO empresaDAO;

    @Autowired
    private VehiculoFactorCalidadDAO factorCalidadDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(FactorCalidadController.class);

    private static String URL_FACTOR_CALIDAD = "cargar/factor-calidad";

        @RequestMapping(value = {FactorCalidadConstants.CARGAR_FACTOR_CALIDAD}, method = RequestMethod.GET)
    public String createExternalUser(HttpSession session, Locale locale, Model model,
            RedirectAttributes redirectAttributes) {
        setBreadcrumbCreateExternalUser(model, locale);
        return URL_FACTOR_CALIDAD;
    }

    @RequestMapping(value = {FactorCalidadConstants.CARGAR_ARCHIVO}, method = RequestMethod.POST)
    public String cargarArchivo(HttpSession session, Locale locale, Model model, @ModelAttribute FactorCalidadDTO factorCalidadDTO) {
        try {
            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("uSession");
            UsuarioEntity usuarioEntity = usuarioDAO.findByPrimaryKey(usuario.getId());
            String _registros = factorCalidadDTO.getRegistros();
            int regExitosos = 0;
            int regFallidos = 0;
            String detalleFallidos = "";
            String detRegFallidos = "";
            if (_registros != null && !_registros.isEmpty()) {
                Pattern pattern = Pattern.compile("^[a-zA-Z]{3}[0-9]{3}$");
                Map empresas = new HashMap();
                List<String> placas = new ArrayList();
                List<String> registros = Arrays.asList(_registros.split(","));
                for (String registro : registros) {
                    String[] datos = registro.split("-");
                    if (datos != null && datos.length == 2) {
                        String placa = datos[0];
                        if (!placas.contains(placa)) {
                            Matcher matcher = pattern.matcher(placa);
                            if (matcher.find()) {
                                String idEmpresa = datos[1];
                                idEmpresa = idEmpresa.replaceAll("[\n\r]", "").trim();
                                boolean isEmpresa = true;
                                EmpresaEntity empresa = (EmpresaEntity) empresas.get(idEmpresa);
                                if (empresa == null) {
                                    isEmpresa = false;
                                    empresa = empresaDAO.findOneByAttribute(EmpresaEntity_.codigoEmpresa, idEmpresa);
                                    if (empresa != null) {
                                        isEmpresa = true;
                                        empresas.put(idEmpresa, empresa);
                                    }
                                }
                                if (isEmpresa) {
                                    factorCalidadDTO.setIdEmpresa(empresa.getId());
                                    factorCalidadDTO.setIdEstado(1);
                                    factorCalidadDTO.setPlaca(placa);
                                    List<FactorCalidadDTO> result = factorCalidadDAO.consultarFactorCalidad(factorCalidadDTO);
                                    if (result.isEmpty()) {
                                        placas.add(placa);
                                        VehiculoFactorCalidadEntity vehiculoFactorCalidadEntity = new VehiculoFactorCalidadEntity();
                                        vehiculoFactorCalidadEntity.setEmpresa(empresa);
                                        vehiculoFactorCalidadEntity.setFechaRegistro(Calendar.getInstance().getTime());
                                        vehiculoFactorCalidadEntity.setIdEstado(1);
                                        vehiculoFactorCalidadEntity.setNroPlaca(placa);
                                        vehiculoFactorCalidadEntity.setUsuario(usuarioEntity);
                                        factorCalidadDAO.create(vehiculoFactorCalidadEntity);
                                        regExitosos++;
                                    } else {
                                        detRegFallidos += registro.replaceAll("[\n\r]", "") + ": Número de placa registrada.\n\r";
                                        regFallidos++;
                                    }
                                } else {
                                    detRegFallidos += registro.replaceAll("[\n\r]", "") + ": Error código empresa.\n\r";
                                    regFallidos++;
                                }
                            } else {
                                detRegFallidos += registro.replaceAll("[\n\r]", "") + ": Error formato placa.\n\r";
                                regFallidos++;
                            }
                        }

                    }
                }
            }
            if (regFallidos > 0) {
                detalleFallidos = "<div id=\"detCargaFallidos\">\n"
                        + "<a id=\"descargarFallidos\" style=\"cursor: pointer; text-decoration: underline; color: #0000FF;\">Descargar detalle registros fallidos</a>\n"
                        + "<p id=\"detalleFallidos\" style=\"display: none;\">"
                        + detRegFallidos
                        + "</p>\n"
                        + "</div>";
            }
            model.addAttribute(CommonConstants.PAGE_MESSAGE_SUCCESS,
                    messageSource.getMessage("header.administration.factorcalidad.success", new Object[]{regExitosos, regFallidos, detalleFallidos}, locale));
        } catch (Exception e) {
            LOGGER.error("Id de empresa invalido:", e);
            model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                    messageSource.getMessage("header.administration.factorcalidad.error", new Object[]{}, locale));
        }
        return URL_FACTOR_CALIDAD;
    }

    private void setBreadcrumbCreateExternalUser(Model model, Locale locale) {
        initbreadcrumb(
                locale, Arrays.asList(
                        new BreadcrumbView(FactorCalidadConstants.CARGAR_FACTOR_CALIDAD,
                                messageSource.getMessage("header.administration.factorcalidad", null, locale)),
                        new BreadcrumbView(FactorCalidadConstants.CARGAR_FACTOR_CALIDAD,
                                messageSource.getMessage("header.administration.factorcalidad.cargararchivo", null, locale)))
        );
        model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);
    }

}
