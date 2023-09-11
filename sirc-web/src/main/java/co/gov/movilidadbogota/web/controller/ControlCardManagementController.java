package co.gov.movilidadbogota.web.controller;

import co.gov.movilidadbogota.core.dto.*;
import co.gov.movilidadbogota.core.entity.ConductorEntity;
import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity;
import co.gov.movilidadbogota.core.entity.RtoVigenciaEntity;
import co.gov.movilidadbogota.core.entity.TipoDocumentoEntity;
import co.gov.movilidadbogota.core.exception.QRGeneratorException;
import co.gov.movilidadbogota.core.service.DriverService;
import co.gov.movilidadbogota.core.service.UtilityService;
import co.gov.movilidadbogota.core.util.DateUtil;
import co.gov.movilidadbogota.core.util.EstadoTarjetaControlEnum;
import co.gov.movilidadbogota.core.util.SystemParameters;
import co.gov.movilidadbogota.core.util.Utils;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.web.constants.DriverConstants;
import co.gov.movilidadbogota.web.constants.NotificationsConstants;
import co.gov.movilidadbogota.web.constants.QRConstants;
import co.gov.movilidadbogota.web.servicioreceptorpersonaduups.ConfirmacionRecibo;
import co.gov.movilidadbogota.web.servicioreceptorpersonaduups.Direccion;
import co.gov.movilidadbogota.web.servicioreceptorpersonaduups.Email;
import co.gov.movilidadbogota.web.servicioreceptorpersonaduups.ErrorRespuesta;
import co.gov.movilidadbogota.web.servicioreceptorpersonaduups.NotificacionPersona;
import co.gov.movilidadbogota.web.servicioreceptorpersonaduups.PersonaNatural;
import co.gov.movilidadbogota.web.servicioreceptorpersonaduups.Telefono;
import co.gov.movilidadbogota.web.util.OrigenTransaccionEnum;
import co.gov.movilidadbogota.web.validator.IssueDriverCardDtoValidator;
import co.gov.movilidadbogota.web.view.breadcrumb.BreadcrumbView;
import co.gov.movilidadbogota.ws.duups.WSDuupsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static co.gov.movilidadbogota.web.constants.DriverConstants.*;
import static co.gov.movilidadbogota.web.constants.DriverConstants.COMMAND_NAME;

@Controller
@Secured({"ROLE_EXTERNAL"})
public class ControlCardManagementController extends GenericController{

    private static final String COMMAND_NAME = "driverDTO";
    private static final String URL_MANAGE_CONTROL_CARD = "driver/control-card-management";
    private static final String URL_CREATE_CONTROL_CARD= "driver/create-control-card";
    private static final String URL_SUCCESS_CREATE_CONTROL_CARD= "driver/success-card-management";
    private static final String URL_SEARCH_CONTROL_CARD= "driver/search-control-card";
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlCardManagementController.class+LOG_PREFIX);

    @Autowired
    private UtilityService utilityService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private DriverService driverService;
    @Autowired
    private WSDuupsClient wsDuupsClient;

    @RequestMapping(value = {DriverConstants.SUCCESS_CREATE_CONTROL_CARD}, method = RequestMethod.GET)
    public String succesCreateControlCard(HttpSession session, Locale locale, Model model,
                                    RedirectAttributes redirectAttributes) {

        CreateDriverDTO driver = (CreateDriverDTO) model.asMap().get(COMMAND_NAME);

        if (driver == null) {

            if (driver == null) {
                driver = new CreateDriverDTO();

                driver.setTipoTelFijo(true);
                List<PagoPilaDTO> listaPlanillas = new ArrayList<>();
                listaPlanillas.add(new PagoPilaDTO());
                driver.setPlanillas(listaPlanillas);

                model.addAttribute(COMMAND_NAME, driver);
            }

        }
        setBreadcrumbCreateExternalUser(model, locale);

        return URL_SUCCESS_CREATE_CONTROL_CARD;
    }

    @RequestMapping(value = {DriverConstants.CONTROL_CARD_MANAGEMENT}, method = RequestMethod.GET)
    public String manageControlCard(HttpSession session, Locale locale, Model model,
                                     RedirectAttributes redirectAttributes) {

        CreateDriverDTO driver = (CreateDriverDTO) model.asMap().get(COMMAND_NAME);

        if (driver == null) {
            driver = new CreateDriverDTO();

            driver.setTipoTelFijo(true);
            List<PagoPilaDTO> listaPlanillas = new ArrayList<>();
            listaPlanillas.add(new PagoPilaDTO());
            driver.setPlanillas(listaPlanillas);

            model.addAttribute(COMMAND_NAME, driver);
        }

        setBreadcrumbCreateExternalUser(model, locale);

        return URL_MANAGE_CONTROL_CARD;
    }

    @Secured({ "ROLE_SDM" })
    @RequestMapping(value = {DriverConstants.SEARCH_CONTROL_CARD}, method = RequestMethod.GET)
    public String searchControlCard(HttpSession session, Locale locale, Model model,
                                    RedirectAttributes redirectAttributes) {

        initbreadcrumb(locale, Arrays.asList(new BreadcrumbView(DriverConstants.QUERY_CONTROL_CARDS,
                messageSource.getMessage("header.administration.control.card.search", null, locale))));
        model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute(EMPRESA_LIST_NAME, utilityService.getEmpresaDTOS(user));
        model.addAttribute(STATUS_LIST_NAME, utilityService.getStatusControlCardDTOS());
        return URL_SEARCH_CONTROL_CARD;
    }

    @Secured({"ROLE_EXTERNAL"})
    @RequestMapping(value = {DriverConstants.CONSULT_TARJETA}, method = RequestMethod.GET)
    public String consultExternalUserGet(HttpSession session, Locale locale, Model model,
                                         @RequestParam String numeroTarjetaControl, @RequestParam String tipoTransaccion,
                                         RedirectAttributes redirectAttributes) {


        ConductorVehiculoDTO response = driverService.buscarPorTarjetaControl(numeroTarjetaControl, tipoTransaccion);
        if (response == null) {
            redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage("error.message.consult.targetcontrol", new Object[]{numeroTarjetaControl}, locale));
            return "redirect:" + DriverConstants.CONTROL_CARD_MANAGEMENT;
        } else if (response.getTarjetaControlEstado() == EstadoTarjetaControlEnum.CANCELADO.getPk()
        || response.getTarjetaControlEstado() == EstadoTarjetaControlEnum.SIN_RENOVACION.getPk()) {
            model.addAttribute("tipoTramite", EstadoTarjetaControlEnum.CANCELADO.name());
        } else {
            model.addAttribute("tipoTramite", EstadoTarjetaControlEnum.REFRENDADA.name());
            model.addAttribute("fechaValidezTC", new SimpleDateFormat("dd/MM/yyyy").format(response.getFechaValidez()));
        }
        model.addAttribute("driverDTO", utilityService.mapDriverDTO(response));
        setBreadcrumbCreateExternalUser(model, locale);
        return URL_MANAGE_CONTROL_CARD;
    }

    @Secured({ "ROLE_SDM" })
    @RequestMapping(value = {QUERY_CONTROL_CARDS}, method = RequestMethod.GET)
    public String getControlCards(@ModelAttribute CreateDriverDTO driverDTO, HttpSession session, Locale locale, Model model,
                                  RedirectAttributes redirectAttributes) {

        List<ControlCardDTO> controlCardDTOS = driverService.getControlCards(driverDTO);

        if(controlCardDTOS == null || controlCardDTOS.size() == 0){
            redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_INFO,
                    messageSource.getMessage("message.data.not.found", null, locale));
        }else{
            model.addAttribute("controlCards", controlCardDTOS);
        }

        return searchControlCard(session, locale, model, redirectAttributes);
    }

    @RequestMapping(value = {DriverConstants.CREATE_CONTROL_CARD}, method = RequestMethod.GET)
    public String createControlCard(HttpSession session, Locale locale, Model model,
                                     RedirectAttributes redirectAttributes) {

        CreateDriverDTO driver = (CreateDriverDTO) model.asMap().get(COMMAND_NAME);

        if (driver == null) {

            if (driver == null) {
                driver = new CreateDriverDTO();

                driver.setTipoTelFijo(true);
                List<PagoPilaDTO> listaPlanillas = new ArrayList<>();
                listaPlanillas.add(new PagoPilaDTO());
                driver.setPlanillas(listaPlanillas);

                model.addAttribute(COMMAND_NAME, driver);
            }

        }

        buildModelLists(session, model, driver);
        model.addAttribute("tipoTramite", EstadoTarjetaControlEnum.EXPEDIDA.name());

        setBreadcrumbCreateExternalUser(model, locale);

        return URL_CREATE_CONTROL_CARD;
    }



    @RequestMapping(value = {DriverConstants.CHECK_PLATE_DRIVER}, method = RequestMethod.POST)
    public String checkPlateAndDriver(@ModelAttribute CreateDriverDTO driverDTO, HttpSession session,
                                       Locale locale, Model model, BindingResult result, RedirectAttributes redirectAttributes) {
        if (driverDTO != null
                && driverDTO.getPlaca() != null
                && driverDTO.getConductorDTO().getPersona().getTipoIdentificacion()  != null
                && driverDTO.getConductorDTO().getPersona().getNumeroIdentificacion()  != null) {
            ConductorVehiculoEntity conductorVehiculoEntity = driverService.findByPlateAndDriver(
                    driverDTO.getPlaca().toUpperCase(),
                    driverDTO.getConductorDTO().getPersona().getTipoIdentificacion(),
                    driverDTO.getConductorDTO().getPersona().getNumeroIdentificacion());

            if( conductorVehiculoEntity != null){
                redirectAttributes.addFlashAttribute(
                        CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage(
                                "form.driver.sim.error.binomio.existente",
                                new Object[]{
                                        conductorVehiculoEntity.getTarjetaControl(),
                                        conductorVehiculoEntity.getIdEstado().getDescripcion()
                                },
                                locale));
                return "redirect:" + DriverConstants.CONTROL_CARD_MANAGEMENT;
            }
            driverService.setBasicVehicleData(driverDTO);

        }
        model.addAttribute("tipoTramite", "1");
        model.addAttribute("plateValidated", "1");
        model.addAttribute(COMMAND_NAME, driverDTO);
        buildModelLists(session, model, driverDTO);
        setBreadcrumbCreateExternalUser(model, locale);
        return URL_CREATE_CONTROL_CARD;
    }


    @RequestMapping(value = {DriverConstants.CREATE_DRIVER_EXPEDICION}, method = RequestMethod.POST)
    public String createDriverExpedicion(@ModelAttribute CreateDriverDTO driverDTO, HttpSession session, Locale locale, Model model,
                                         RedirectAttributes redirectAttributes) {
        try {

            if (driverDTO.getConductorDTO().getPersona().getCelular() != null
                    && driverDTO.getConductorDTO().getPersona().getCelular() != 0
                    && driverDTO.getConductorDTO().getPersona().getCelular().toString().length() <= 7) {
                driverDTO.setTipoTelFijo(true);
            } else {
                driverDTO.setTipoTelFijo(false);
            }
            if (!Utils.isNullOrEmpty(driverDTO.getPlaca()) && !Utils.validarPlaca(driverDTO.getPlaca())) {
                model.addAttribute("driverDTO", driverDTO);
                model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource
                        .getMessage("error.message.format.placa", new Object[]{driverDTO.getPlaca()}, locale));
                return createControlCard(session, locale, model, redirectAttributes);
            }
            if (!driverService.validarPeriodoPagoSeguridad(driverDTO.getPlanillas())) {
                model.addAttribute("driverDTO", driverDTO);
                model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource
                        .getMessage("error.message.perido.pago", new Object[]{driverDTO.getPlaca()}, locale));
                return createControlCard(session, locale, model, redirectAttributes);
            }
            if ((driverDTO.getFile() == null || driverDTO.getFile().isEmpty()
                    || driverDTO.getFile().getSize() <= 0 || driverDTO.getFile().getBytes() == null)
                    && (driverDTO.getConductorDTO() == null || driverDTO.getConductorDTO().getFoto() == null
                    || driverDTO.getConductorDTO().getFoto().isEmpty())) {
                model.addAttribute("driverDTO", driverDTO);
                model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                        messageSource.getMessage("common.error.required.foto", null, locale));
                return createControlCard(session, locale, model, redirectAttributes);
            }
            if (driverDTO.getConductorDTO() == null
                    || driverDTO.getEmpresaDTO() == null || driverDTO.getConductorDTO().getPersona() == null
                    || driverDTO.getConductorDTO().getPersona().getTipoIdentificacion() == null
                    || driverDTO.getConductorDTO().getPersona().getTipoIdentificacion() == 0
                    || driverDTO.getConductorDTO().getPersona().getNumeroIdentificacion() == null
                    || driverDTO.getConductorDTO().getPersona().getNumeroIdentificacion() == 0
                    || Utils.isNullOrEmpty(driverDTO.getConductorDTO().getPersona().getNombres())
                    || Utils.isNullOrEmpty(driverDTO.getConductorDTO().getPersona().getApellidos())
                    || driverDTO.getConductorDTO().getPersona().getFechaNacimiento() == null
                    || driverDTO.getConductorDTO().getPersona().getFechaExpedicionDocumento() == null
                    || Utils.isNullOrEmpty(driverDTO.getConductorDTO().getGrupoSanguineo())
                    || Utils.isNullOrEmpty(driverDTO.getConductorDTO().getFactorRh())
                    || driverDTO.getConductorDTO().getIdEps() == 0 || driverDTO.getConductorDTO().getIdArl() == 0
                    || driverDTO.getConductorDTO().getIdAfp() == -1
                    || driverDTO.getPlanillas() == null || driverDTO.getPlanillas().isEmpty()
                    || driverDTO.getFechaVencimientoSoat() == null || Utils.isNullOrEmpty(driverDTO.getNroSOAT())
                    || driverDTO.getFechaVencimientoRtm() == null || Utils.isNullOrEmpty(driverDTO.getNroRTM())
                    || Utils.isNullOrEmpty(driverDTO.getNroTarjetaOperacion()) || driverDTO.getFechaVencimientoTO() == null
                    || driverDTO.getIdMetodoCobro() == null || driverDTO.getIdMetodoCobro() == 0
                    || driverDTO.getIdTipoServicio() == null || driverDTO.getIdTipoServicio() == 0
                    || driverDTO.getConductorDTO().getPersona().getCelular() == null
                    || driverDTO.getConductorDTO().getPersona().getCelular() == 0
                    || Utils.isNullOrEmpty(driverDTO.getConductorDTO().getPersona().getDireccion())
                    || driverDTO.getEmpresaDTO().getId() == null || Utils.isNullOrEmpty(driverDTO.getPlaca())
                    || (driverDTO.isFactorCalidad() && driverDTO.getIdFactorCalidad() == 0)) {
                model.addAttribute("driverDTO", driverDTO);
                model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                        messageSource.getMessage("common.error.required.fields", null, locale));
                return createControlCard(session, locale, model, redirectAttributes);
            }
            if (driverDTO.getPlanillas()!=null && !driverDTO.getPlanillas().isEmpty()) {
                for (PagoPilaDTO planilla : driverDTO.getPlanillas()) {
                    if (!Utils.periodoMenorMesActual(planilla.getStringPeriodoPago())) {
                        model.addAttribute("driverDTO", driverDTO);
                        model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage(
                                "error.periodo.after.actual.month", new Object[]{planilla.getStringPeriodoPago()}, locale));
                        return createControlCard(session, locale, model, redirectAttributes);
                    }
                }
            }

            if(new IssueDriverCardDtoValidator(driverDTO).validateDto()) {
                String user = SecurityContextHolder.getContext().getAuthentication().getName();
                ConductorVehiculoEntity conductorVehiculo = driverService.expedirTarjetaControl(model, driverDTO, locale, redirectAttributes, user, OrigenTransaccionEnum.WEB.getPk());
                redirectAttributes.addFlashAttribute(CommonConstants.QR_LINK, conductorVehiculo.getTarjetaControl());
                redirectAttributes.addFlashAttribute("fechaValidezTarjeta", DateUtil.dateToStringFormat(conductorVehiculo.getFechaValidez()));
                redirectAttributes.addFlashAttribute("fechaVigenciaTarjeta", DateUtil.dateToStringFormat(conductorVehiculo.getFechaVigencia()));
                redirectAttributes.addFlashAttribute("typeTransaction", EstadoTarjetaControlEnum.EXPEDIDA.name());
                new Thread(() -> enviarDatosDuups(driverDTO)).start();
                return "redirect:" + DriverConstants.SUCCESS_CREATE_CONTROL_CARD;
            }else {
                model.addAttribute("driverDTO", driverDTO);
                model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage(
                        "common.error.required.fields", null, locale));
                return createControlCard(session, locale, model, redirectAttributes);
            }
        } catch (QRGeneratorException e) {
            LOGGER.error(messageSource.getMessage("common.error.generic.qr", null, locale), e);
            redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                    messageSource.getMessage("common.error.generic.qr", null, locale));
            return URL_MANAGE_CONTROL_CARD;
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("common.error.generic", null, locale), e);
            redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                    messageSource.getMessage("common.error.generic", null, locale));
            return URL_MANAGE_CONTROL_CARD;
        }
    }

    @RequestMapping(value = DriverConstants.GET_QR_IMG, method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImg(@RequestParam("id") String imageName, HttpServletResponse response, Locale locale) {
        ParametroDTO rutaImg = utilityService.getParametroDTO(SystemParameters.URI_IMG.getValue());
        try {
            String filename = rutaImg.getValorParametro()
                    .concat(File.separator)
                    .concat(QRConstants.PATH_QR_IMAGE)
                    .concat((File.separator))
                    .concat(imageName).concat(QRConstants.EXTENSION_QR_IMAGE);
            Path path = Paths.get(filename);
            return Files.readAllBytes(path);
        } catch (Exception e) {
            LOGGER.error("ha ocurrido un error al intentar consultar el documento:", e);
        }
        return null;

    }

    public void enviarDatosDuups(CreateDriverDTO driverDTO) {
        LOGGER.info("########## enviarDatosDuups ##########");
        ParametroDTO endPoint = utilityService.getParametroDTO(SystemParameters.ENDPOINT_DUUPS.getValue());
        LOGGER.info("endPoint: " + endPoint.getValorParametro());
        ParametroDTO municipioDuups = utilityService.getParametroDTO(SystemParameters.MUNICIPIO_DUUPS.getValue());
        LOGGER.info("municipioDuups: " + municipioDuups.getValorParametro());
        ParametroDTO departamentoDuups = utilityService.getParametroDTO(SystemParameters.DEPARTAMENTO_DUUPS.getValue());
        LOGGER.info("departamentoDuups: " + departamentoDuups.getValorParametro());
        ParametroDTO codigoOrigenDuups = utilityService.getParametroDTO(SystemParameters.CODIGO_ORIGEN_SIRC_DUUPS.getValue());
        LOGGER.info("codigoOrigenDuups: " + codigoOrigenDuups.getValorParametro());
        NotificacionPersona wsRequest = new NotificacionPersona();
        TipoDocumentoEntity tipoDocumento ;
        Direccion d = new Direccion();
        List<Telefono> listOTelefono = new ArrayList<Telefono>();
        Telefono otelefono = new Telefono();
        Email oemail = new Email();
        PersonaNatural oPersonaNatural = new PersonaNatural();
        if (driverDTO.getId() != null) {
            ConductorVehiculoDTO dto = driverService.getConductorVehiculoDTO(driverDTO.getId());
            tipoDocumento = utilityService.getTipoDocumentoEntity(dto.getConductorDTO().getPersona().getTipoIdentificacion());
            wsRequest.setONumeroDocumento(dto.getConductorDTO().getPersona().getNumeroIdentificacion().toString());
            d.setODireccion(driverDTO.getConductorDTO().getPersona().getDireccion());
            otelefono.setOTelefono(driverDTO.getConductorDTO().getPersona().getCelular().toString());
            oPersonaNatural.setOPrimerNombre(dto.getConductorDTO().getPersona().getNombres());
            oPersonaNatural.setOPrimerApellido(dto.getConductorDTO().getPersona().getApellidos());
        } else {
            tipoDocumento = utilityService
                    .getTipoDocumentoEntity(driverDTO.getConductorDTO().getPersona().getTipoIdentificacion());
            wsRequest
                    .setONumeroDocumento(driverDTO.getConductorDTO().getPersona().getNumeroIdentificacion().toString());
            d.setODireccion(driverDTO.getConductorDTO().getPersona().getDireccion());
            otelefono.setOTelefono(driverDTO.getConductorDTO().getPersona().getCelular().toString());
            oPersonaNatural.setOPrimerNombre(driverDTO.getConductorDTO().getPersona().getNombres());
            oPersonaNatural.setOPrimerApellido(driverDTO.getConductorDTO().getPersona().getApellidos());
        }
        d.setODepartamento(departamentoDuups.getValorParametro());
        d.setOMunicipioCiudad(municipioDuups.getValorParametro());

        otelefono.setOTipoTelefono("1");
        listOTelefono.add(otelefono);

        wsRequest.getOTelefono().addAll(listOTelefono);
        wsRequest.setOTipoDocumento(tipoDocumento.getHomologaDuups());
        wsRequest.setOAutorizacionContactoEmail("S");
        wsRequest.getODireccion().add(d);
        wsRequest.getOEmail().add(oemail);
        wsRequest.setOPersonaNatural(oPersonaNatural);
        wsRequest.setOOrigen(codigoOrigenDuups.getValorParametro());
        try {
            LOGGER.info("Inicio llamado a web service ");
            ConfirmacionRecibo response = wsDuupsClient.registroPersonaUbicabilidad(endPoint.getValorParametro(),
                    wsRequest);
            if (response.getODetalle() != null) {
                LOGGER.info("Finalizo llamado a web service  ");
                for (ErrorRespuesta error : response.getODetalle()) {
                    LOGGER.info("Codigo error : " + error.getOCodigoError() + " Mensaje: " + error.getOMensajeError());
                }
            } else {
                LOGGER.info("Finalizo llamado a web service exitoso " + response.getOCodigo());
            }
        } catch (Exception e) {
            LOGGER.error("Error enviarDatosDuups: ", e);
        }
    }

    private void setBreadcrumbCreateExternalUser(Model model, Locale locale) {
        initbreadcrumb(locale, Arrays.asList(new BreadcrumbView(DriverConstants.CREATE_DRIVER,
                messageSource.getMessage("header.administration.drivers", null, locale))));
        model.addAttribute(CommonConstants.VIEW_BREADCRUMB, breadcrumb);
    }

    private void buildModelLists(HttpSession session, Model model, CreateDriverDTO driver) {
        session.setAttribute(COMMAND_NAME, driver);
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute(AFP_LIST_NAME, utilityService.getAfpDTOS() );
        model.addAttribute(EPS_LIST_NAME, utilityService.getEpsDTOS());
        model.addAttribute(ARL_LIST_NAME, utilityService.getArlDTOS());
        model.addAttribute(TIPO_DOCUMENTO_LIST_NAME, utilityService.getTipoDocumentoDTOS());
        model.addAttribute(EMPRESA_LIST_NAME, utilityService.getEmpresaDTOS(user));
        model.addAttribute(METODO_COBRO_LIST_NAME, utilityService.getMetodoCobroDTOS());
        model.addAttribute(TIPO_SERVICIO_LIST_NAME, utilityService.getTipoServicioDTOS());
        model.addAttribute(OPERADORES_PILA_LIST_NAME, utilityService.getOperadoresPilaDTOS());
    }

}
