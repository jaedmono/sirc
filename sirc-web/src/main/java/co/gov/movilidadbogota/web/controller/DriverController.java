package co.gov.movilidadbogota.web.controller;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.gov.movilidadbogota.core.dao.*;
import co.gov.movilidadbogota.core.entity.*;
import co.gov.movilidadbogota.core.exception.QRGeneratorException;
import co.gov.movilidadbogota.core.service.DriverService;
import co.gov.movilidadbogota.core.service.UtilityService;
import co.gov.movilidadbogota.web.util.QRGenerator;
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

import co.gov.movilidadbogota.core.dto.AfpDTO;
import co.gov.movilidadbogota.core.dto.ArlDTO;
import co.gov.movilidadbogota.core.dto.ConductorVehiculoDTO;
import co.gov.movilidadbogota.core.dto.CreateDriverDTO;
import co.gov.movilidadbogota.core.dto.EmpresaDTO;
import co.gov.movilidadbogota.core.dto.EpsDTO;
import co.gov.movilidadbogota.core.dto.FactorCalidadDTO;
import co.gov.movilidadbogota.core.dto.MetodoCobroDTO;
import co.gov.movilidadbogota.core.dto.OperadoresPilaDTO;
import co.gov.movilidadbogota.core.dto.PagoPilaDTO;
import co.gov.movilidadbogota.core.dto.ParametroDTO;
import co.gov.movilidadbogota.core.dto.TipoDocumentoDTO;
import co.gov.movilidadbogota.core.dto.TipoServicioDTO;
import co.gov.movilidadbogota.core.util.DateUtil;
import co.gov.movilidadbogota.core.util.EstadoTarjetaControlEnum;
import co.gov.movilidadbogota.core.util.SystemParameters;
import co.gov.movilidadbogota.core.util.TipoTransaccion;
import co.gov.movilidadbogota.core.util.Utils;
import co.gov.movilidadbogota.web.constants.CommonConstants;
import co.gov.movilidadbogota.web.constants.DriverConstants;
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
import co.gov.movilidadbogota.ws.sim.VehiculoSimDTO;

import static co.gov.movilidadbogota.web.constants.DriverConstants.*;

@Controller
@Secured({"ROLE_EXTERNAL"})
public class DriverController extends GenericController {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AfpDAO afpDAO;
    @Autowired
    private EpsDAO epsDAO;
    @Autowired
    private ArlDAO arlDAO;
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private ConductorDAO conductorDAO;

    @Autowired
    private ConductorVehiculoDAO conductorVehiculoDAO;
    @Autowired
    private TipoDocumentoDAO tipoDocumentoDAO;
    @Autowired
    private PagoPilaDAO pagoPilaDAO;
    @Autowired
    private WSDuupsClient wsDuupsClient;
    @Autowired
    private ParametroSimurDAO parametroSimurDAO;
    @Autowired
    private MetodoCobroDAO metodoCobroDAO;
    @Autowired
    private TipoServicioDAO tipoServicioDAO;
    @Autowired
    private OperadoresPilaDAO operadoresPilaDAO;
    @Autowired
    private VehiculoFactorCalidadDAO vehiculoFactorCalidadDAO;
    @Autowired
    private RefrendacionHistoricoDAO refrendacionHistoricoDAO;
    @Autowired
    private AuditoriaSircDAO auditoriaSircDao;
    @Autowired
    private RadicadoTarjetaControlDAO radicadoTarjetaControlDAO;
    @Autowired
    private DriverService driverService;
    @Autowired
    private UtilityService utilityService;
    /**
     * @Autowired private DispositivosMovilDAO dispositivosMovilDAO;*
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverController.class+LOG_PREFIX);
    private ParametroDTO fechaFinMetodoCobroUnidades;
    private ParametroDTO fechaInicioNivelServicioLujo;

    private static final String URL_CREATE_DRIVER = "driver/create-driver";
    private static final String URL_CREATE_CARD_DRIVER = "driver/create-control-card";
    private static final String URL_MANAGE_CONTROL_CARD = "driver/control-card-management";


    @RequestMapping(value = {DriverConstants.CREATE_DRIVER}, method = RequestMethod.GET)
    public String createExternalUser(HttpSession session, Locale locale, Model model,
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
        
        session.setAttribute(COMMAND_NAME, driver);
        
        armarcombos(model, session, locale);

        return URL_CREATE_DRIVER;
    }
    
    /**
     * @param driverDTO
     */
    private void registrarLogRefrendacionCancelacion(CreateDriverDTO driverDTO) {
    	long radicado = Long.valueOf(conductorVehiculoDAO.obtenerNroRadicado().toString());
    	String user = SecurityContextHolder.getContext().getAuthentication().getName();
		AuditoriaSircEntity auditoriaSircEntity = new AuditoriaSircEntity();
		auditoriaSircEntity.setConductor(new ConductorEntity());
		auditoriaSircEntity.getConductor().setId(driverDTO.getIdConductor());
		auditoriaSircEntity.setFechaEvento(new Date());
		auditoriaSircEntity.setObservacion("");
		auditoriaSircEntity.setOrigen(OrigenTransaccionEnum.WEB.getPk());
		auditoriaSircEntity.setPlaca(driverDTO.getPlaca());
		auditoriaSircEntity.setIdTransaccion(new TarjetacontrolEstadoEntity());
		auditoriaSircEntity.getIdTransaccion().setId(3);
		auditoriaSircEntity.setTarjetaControl(radicado);
		auditoriaSircEntity.setUsuario(user);
        if(driverDTO.getEmpresaDTO() != null) {
            auditoriaSircEntity.setEmpresa(new EmpresaEntity());
            auditoriaSircEntity.getEmpresa().setId(driverDTO.getIdEmpresa());
        }

		auditoriaSircDao.crearRegistroLog(auditoriaSircEntity);		
		
		RadicadoTarjetaEntity radicadoTarjetaEntity = new RadicadoTarjetaEntity();
        radicadoTarjetaEntity.setTarjetaControl(driverDTO.getNumeroTarjetaControl());
        radicadoTarjetaEntity.setIdRadicado(radicado);

        radicadoTarjetaControlDAO.create(radicadoTarjetaEntity);
	}

    @RequestMapping(value = {DriverConstants.CLEAN_DRIVER}, method = RequestMethod.GET)
    public String limpiarCombos(HttpSession session, Locale locale, Model model,
            RedirectAttributes redirectAttributes) {

        CreateDriverDTO driver = (CreateDriverDTO) model.asMap().get(COMMAND_NAME);

        if (driver == null) {

            driver = (CreateDriverDTO) session.getAttribute(COMMAND_NAME);

            if (driver == null) {
                driver = new CreateDriverDTO();

                driver.setTipoTelFijo(true);
                List<PagoPilaDTO> listaPlanillas = new ArrayList<>();
                listaPlanillas.add(new PagoPilaDTO());
                driver.setPlanillas(listaPlanillas);             
            }
            
            model.addAttribute("tipoTramite", "1");

            model.addAttribute(COMMAND_NAME, driver);
        }

        session.setAttribute(COMMAND_NAME, driver);
        armarcombos(model, session, locale);

        return URL_CREATE_DRIVER;
    }

    @RequestMapping(value = {DriverConstants.CREATE_DRIVER_REEXPEDICION}, method = RequestMethod.POST)
    public String createExternalUserReExpedicion(@ModelAttribute CreateDriverDTO driverDTO, HttpSession session,
                                               Locale locale, Model model, BindingResult result, RedirectAttributes redirectAttributes,
                                               HttpServletRequest request) {
        try {
        	model.addAttribute("tipoTramite", EstadoTarjetaControlEnum.CANCELADO.name());
            if (driverDTO.getConductorDTO().getPersona().getCelular() != null
                    && driverDTO.getConductorDTO().getPersona().getCelular() != 0
                    && driverDTO.getConductorDTO().getPersona().getCelular().toString().length() <= 7) {
                driverDTO.setTipoTelFijo(true);
            } else {
                driverDTO.setTipoTelFijo(false);
            }
            if (!validarPeriodoPagoSeguridad(driverDTO.getPlanillas())) {
                model.addAttribute("driverDTO", driverDTO);
                model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource
                        .getMessage("error.message.perido.pago", new Object[]{driverDTO.getPlaca()}, locale));
                return createExternalUser(session, locale, model, redirectAttributes);
            }
            if ( driverDTO.getConductorDTO() == null
                    || driverDTO.getEmpresaDTO() == null || driverDTO.getConductorDTO().getPersona() == null
                    || driverDTO.getConductorDTO().getIdEps() == 0 || driverDTO.getConductorDTO().getIdArl() == 0
                    || driverDTO.getConductorDTO().getIdAfp() == -1
                    || driverDTO.getPlanillas() == null || driverDTO.getPlanillas().isEmpty()
                    || driverDTO.getFechaVencimientoSoat() == null || Utils.isNullOrEmpty(driverDTO.getNroSOAT())
                    || driverDTO.getFechaVencimientoRtm() == null || Utils.isNullOrEmpty(driverDTO.getNroRTM())
                    || driverDTO.getFechaVencimientoTO() == null
                    || driverDTO.getIdTipoServicio() == null || driverDTO.getIdTipoServicio() == 0
                    || driverDTO.getConductorDTO().getPersona().getCelular() == null
                    || driverDTO.getConductorDTO().getPersona().getCelular() == 0
                    || Utils.isNullOrEmpty(driverDTO.getConductorDTO().getPersona().getDireccion())) {
                model.addAttribute("driverDTO", driverDTO);
                model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                        messageSource.getMessage("common.error.required.fields", null, locale));
                return createExternalUser(session, locale, model, redirectAttributes);
            }
            if (driverDTO.getPlanillas()!=null && !driverDTO.getPlanillas().isEmpty()) {
                for (PagoPilaDTO planilla : driverDTO.getPlanillas()) {
                    if (!Utils.periodoMenorMesActual(planilla.getStringPeriodoPago())) {
                        model.addAttribute("driverDTO", driverDTO);
                        model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage(
                                "error.periodo.after.actual.month", new Object[]{planilla.getStringPeriodoPago()}, locale));
                        return createExternalUser(session, locale, model, redirectAttributes);
                    }
                }
            }

            if(new IssueDriverCardDtoValidator(driverDTO).validateDto()) {
            	String user = SecurityContextHolder.getContext().getAuthentication().getName();
            	ConductorVehiculoEntity conductorVehiculo = driverService.reExpedirTarjeta(driverDTO, user, OrigenTransaccionEnum.WEB.getPk());

                LOGGER.info("########## enviarDatosDuups WEB ##########");
                new Thread(() -> enviarDatosDuups(driverDTO)).start();
                redirectAttributes.addFlashAttribute(CommonConstants.QR_LINK, conductorVehiculo.getTarjetaControl());
                redirectAttributes.addFlashAttribute("fechaValidezTarjeta", DateUtil.dateToStringFormat(conductorVehiculo.getFechaValidez()));
                redirectAttributes.addFlashAttribute("fechaVigenciaTarjeta", DateUtil.dateToStringFormat(conductorVehiculo.getFechaVigencia()));
                redirectAttributes.addFlashAttribute("typeTransaction", EstadoTarjetaControlEnum.EXPEDIDA.name());
                return "redirect:" + DriverConstants.SUCCESS_CREATE_CONTROL_CARD;
            }else {
                model.addAttribute("driverDTO", driverDTO);
                model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage(
                        "common.error.required.fields", null, locale));
                return createExternalUser(session, locale, model, redirectAttributes);
            }
        } catch (QRGeneratorException e) {
            LOGGER.error(messageSource.getMessage("common.error.generic.qr", null, locale), e);
            model.addAttribute("driverDTO", driverDTO);
            model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                    messageSource.getMessage("common.error.generic.qr", null, locale));
            return URL_MANAGE_CONTROL_CARD;
        } catch (Exception e) {
            LOGGER.error(messageSource.getMessage("common.error.generic", null, locale), e);
            model.addAttribute("driverDTO", driverDTO);
            model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                    messageSource.getMessage("common.error.generic", null, locale));
            return URL_MANAGE_CONTROL_CARD;
        }
    }



    private boolean validarPeriodoPagoSeguridad(List<PagoPilaDTO> pagos) {
        boolean fallido = true;
        try {
            
            if (pagos != null && !pagos.isEmpty()) {
                for (PagoPilaDTO pago : pagos) {
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar fechaSeguridad = Calendar.getInstance();
                    fechaSeguridad.setTime(format.parse("01/" + pago.getStringPeriodoPago()));
                    fechaSeguridad.set(Calendar.DAY_OF_MONTH, 1);
                    fechaSeguridad.set(Calendar.HOUR, 12);
                    fechaSeguridad.set(Calendar.HOUR_OF_DAY, 12);
                    fechaSeguridad.set(Calendar.MINUTE, 0);
                    fechaSeguridad.set(Calendar.SECOND, 0);
                    fechaSeguridad.set(Calendar.MILLISECOND, 0);
                    Calendar fecha = Calendar.getInstance();
                    fecha.set(Calendar.DAY_OF_MONTH, 1);
                    fecha.add(Calendar.MONTH, -2);
                    fecha.set(Calendar.HOUR, 12);
                    fecha.set(Calendar.HOUR_OF_DAY, 12);
                    fecha.set(Calendar.MINUTE, 0);
                    fecha.set(Calendar.SECOND, 0);
                    fecha.set(Calendar.MILLISECOND, 0);
                    fallido &= !fechaSeguridad.getTime().before(fecha.getTime()) || fechaSeguridad.getTime().equals(fecha.getTime());
                }        
            }
        } catch (Exception e) {
            LOGGER.error("Error validarPeriodoPagoSeguridad: ", e);
        }
        return fallido;
    }

    @RequestMapping(value = {DriverConstants.CREATE_DRIVER_REFRENDACION}, method = RequestMethod.POST)
    public String createExternalUserRefrendacion(@ModelAttribute CreateDriverDTO driverDTO, HttpSession session,
            Locale locale, Model model, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            if (!validarPeriodoPagoSeguridad(driverDTO.getPlanillas())) {
                model.addAttribute("driverDTO", driverDTO);
                model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource
                        .getMessage("error.message.perido.pago", new Object[]{driverDTO.getPlaca()}, locale));
                return createExternalUser(session, locale, model, redirectAttributes);
            }

            if (driverDTO.getConductorDTO() == null
                    || driverDTO.getConductorDTO().getPersona() == null
                    || Utils.isNullOrEmpty(driverDTO.getConductorDTO().getPersona().getDireccion())
                    || driverDTO.getConductorDTO().getIdEps() == 0 || driverDTO.getConductorDTO().getIdArl() == 0
                    || driverDTO.getConductorDTO().getIdAfp() == -1
                    || driverDTO.getPlanillas() == null || driverDTO.getPlanillas().isEmpty()
                    || driverDTO.getIdTipoServicio() == null || driverDTO.getIdTipoServicio() == 0
                    || driverDTO.getConductorDTO().getPersona().getCelular() == null
                    || driverDTO.getConductorDTO().getPersona().getCelular() == 0) {
                model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage("common.error.required.fields", null, locale));
                return consultExternalUserGet(session, locale, model, driverDTO.getNumeroTarjetaControl(),
                        driverDTO.getTipoTransaccion().toString(), redirectAttributes);
            }
            ConductorVehiculoDTO cvDTO = conductorVehiculoDAO.buscarPorId(driverDTO.getId());
            //Validar si la tarjeta de control esta vencida
            if (new Date().after(cvDTO.getFechaValidez())) {
                if (cvDTO.getTarjetaControlEstado() == EstadoTarjetaControlEnum.SIN_RENOVACION.getPk()) {
                    model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage(
                            "error.refrendacion.estado.5", new Object[]{driverDTO.getNumeroTarjetaControl(),
                                    Utils.getFormatDate(driverDTO.getFechaVigencia())}, locale));
                } else if (cvDTO.getTarjetaControlEstado() == EstadoTarjetaControlEnum.CANCELADO.getPk()) {
                    model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage(
                            "error.refrendacion.estado.3", new Object[]{driverDTO.getNumeroTarjetaControl()}, locale));
                } else {
                    model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage(
                            "error.fecha.vencimiento.tarjeta.control", new Object[]{}, locale));
                }
                return consultExternalUserGet(session, locale, model, driverDTO.getNumeroTarjetaControl(),
                        driverDTO.getTipoTransaccion().toString(), redirectAttributes);
            }

            if (driverDTO.getPlanillas() != null && !driverDTO.getPlanillas().isEmpty()) {
                for (PagoPilaDTO planilla : driverDTO.getPlanillas()) {
                    if (!Utils.periodoMenorMesActual(planilla.getStringPeriodoPago())) {
                        redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage(
                                "error.periodo.after.actual.month", new Object[]{planilla.getStringPeriodoPago()}, locale));
                        return "redirect:" + DriverConstants.CONTROL_CARD_MANAGEMENT;
                    }
                }
            }

            ParametroDTO vigencia = parametroSimurDAO.findByKey(SystemParameters.VIGENCIA_TARJETA.getValue());
            Date fechaMenor = validarVigencia(driverDTO);
            Calendar vigenciaTC = Calendar.getInstance();
            vigenciaTC.setTime(conductorVehiculoDAO.consultarConductorPortarjeta(cvDTO.getNumeroTarjetaControl()).getFechaVigencia());
            if (!validMultiRefrendation(vigenciaTC)) {
                SimpleDateFormat format = new SimpleDateFormat(FORMATO_FECHA);
                redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage(
                        "error.vigencia.after.actual.date", new Object[]{format.format(vigenciaTC.getTime())}, locale));
                return "redirect:" + DriverConstants.CONTROL_CARD_MANAGEMENT;
            }
            vigenciaTC.add(Calendar.MONTH, 1);
            driverDTO.setFechaVigencia(vigenciaTC.getTime().after(fechaMenor) ? fechaMenor : vigenciaTC.getTime());
            if (vigenciaTC.after(cvDTO.getFechaValidez())) {
                driverDTO.setFechaVigencia(cvDTO.getFechaValidez());
            }
            driverDTO.setIdEstado(EstadoTarjetaControlEnum.REFRENDADA.getPk());
            driverDTO.setTipoTransaccion(new Long(TipoTransaccion.REFRENDACION.getValue()));
            ConductorVehiculoEntity conductorVehiculoEntity = conductorVehiculoDAO.update(driverDTO);
            driverDTO.setIdEmpresa(cvDTO.getEmpresaDTO().getId());
            registrarLogRefrendacionCancelacion(driverDTO);
            if (driverDTO.getTipoTransaccion() == 2L) {
                RefrendacionHistoricoEntity historico = new RefrendacionHistoricoEntity();
                historico.setFechaRefrendacion(new Date());
                historico.setIdVehiculo(driverDTO.getId());
                refrendacionHistoricoDAO.create(historico);
            }
            try {
                pagoPilaDAO.updateCreatePagoPilaRefrendacion(driverDTO, Integer.valueOf(vigencia.getValorParametro()));
            } catch (Exception e) {
                driverService.createPagoPila(driverDTO, conductorVehiculoEntity, vigenciaTC);
            }
            //setBreadcrumbCreateExternalUser(model, locale);
            ParametroDTO rutaImg = parametroSimurDAO.findByKey(SystemParameters.URI_IMG.getValue());
            Path basePath = Paths.get(rutaImg.getValorParametro());

            try {
                ParametroDTO urlSimur = parametroSimurDAO.findByKey(SystemParameters.URL_SIMUR_QR.getValue());
                BufferedImage bufferedImage = QRGenerator.generateQRCodeImage(cvDTO.getNumeroTarjetaControl(), urlSimur.getValorParametro());
                QRGenerator.createQRFile(bufferedImage, basePath, cvDTO.getNumeroTarjetaControl());
            } catch (Exception e) {
                e.printStackTrace();
            }

            redirectAttributes.addFlashAttribute(CommonConstants.QR_LINK, cvDTO.getNumeroTarjetaControl());
            redirectAttributes.addFlashAttribute("fechaVigenciaTarjeta", DateUtil.dateToStringFormat(driverDTO.getFechaVigencia()));
            redirectAttributes.addFlashAttribute("typeTransaction", EstadoTarjetaControlEnum.REFRENDADA.name());
            return "redirect:" + DriverConstants.SUCCESS_CREATE_CONTROL_CARD;
        }catch (Exception e){
            LOGGER.error(messageSource.getMessage("common.error.generic", null, locale), e);
            model.addAttribute("driverDTO", driverDTO);
            model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
                    messageSource.getMessage("common.error.generic.refrendacion", null, locale));
            return URL_MANAGE_CONTROL_CARD;
        }
    }

    private boolean validMultiRefrendation(Calendar vigencia) {
    	Calendar valid = Calendar.getInstance();
    	valid.setTime(vigencia.getTime());
    	valid.add(Calendar.DATE, -15);
        return valid.before(Calendar.getInstance());
	}

    public void enviarDatosDuups(CreateDriverDTO driverDTO) {
        LOGGER.info("########## enviarDatosDuups ##########");
        ParametroDTO endPoint = parametroSimurDAO.findByKey(SystemParameters.ENDPOINT_DUUPS.getValue());
        LOGGER.info("endPoint: " + endPoint.getValorParametro());
        ParametroDTO municipioDuups = parametroSimurDAO.findByKey(SystemParameters.MUNICIPIO_DUUPS.getValue());
        LOGGER.info("municipioDuups: " + municipioDuups.getValorParametro());
        ParametroDTO departamentoDuups = parametroSimurDAO.findByKey(SystemParameters.DEPARTAMENTO_DUUPS.getValue());
        LOGGER.info("departamentoDuups: " + departamentoDuups.getValorParametro());
        ParametroDTO codigoOrigenDuups = parametroSimurDAO.findByKey(SystemParameters.CODIGO_ORIGEN_SIRC_DUUPS.getValue());
        LOGGER.info("codigoOrigenDuups: " + codigoOrigenDuups.getValorParametro());
        NotificacionPersona wsRequest = new NotificacionPersona();
        TipoDocumentoEntity tipoDocumento = new TipoDocumentoEntity();
        Direccion d = new Direccion();
        List<Telefono> listOTelefono = new ArrayList<Telefono>();
        Telefono otelefono = new Telefono();
        Email oemail = new Email();
        PersonaNatural oPersonaNatural = new PersonaNatural();
        if (driverDTO.getId() != null) {
            ConductorVehiculoDTO dto = conductorVehiculoDAO.buscarPorId(driverDTO.getId());
            tipoDocumento = tipoDocumentoDAO.findByPrimaryKey(dto.getConductorDTO().getPersona().getTipoIdentificacion());
            wsRequest.setONumeroDocumento(dto.getConductorDTO().getPersona().getNumeroIdentificacion().toString());
            d.setODireccion(driverDTO.getConductorDTO().getPersona().getDireccion());
            otelefono.setOTelefono(driverDTO.getConductorDTO().getPersona().getCelular().toString());
            oPersonaNatural.setOPrimerNombre(dto.getConductorDTO().getPersona().getNombres());
            oPersonaNatural.setOPrimerApellido(dto.getConductorDTO().getPersona().getApellidos());
        } else {
            tipoDocumento = tipoDocumentoDAO
                    .findByPrimaryKey(driverDTO.getConductorDTO().getPersona().getTipoIdentificacion());
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

    private String getTarjetaControl(CreateDriverDTO driverDTO) {
        String tarjetaControl = driverDTO.getPlaca().toUpperCase() + driverDTO.getConductorDTO().getPersona().getNumeroIdentificacion();
        return tarjetaControl.substring(0,12);
    }

    private Date validarVigencia(CreateDriverDTO driverDTO) {
		Date fechaSOAT = setFechaHora(driverDTO.getFechaVencimientoSoat());
		Date fechaRTM = setFechaHora(driverDTO.getFechaVencimientoRtm());
		Date fechaTO = setFechaHora(driverDTO.getFechaVencimientoTO());
		Date fechaMenor = fechaSOAT.before(fechaRTM) ? fechaSOAT : fechaRTM;
		fechaMenor = fechaMenor.before(fechaTO) ? fechaMenor : fechaTO;
		return fechaMenor;
	}

    private Date setFechaHora(Date fecha) {
        Calendar _fecha = Calendar.getInstance();
        _fecha.setTime(fecha);
        _fecha.set(Calendar.HOUR_OF_DAY, 23);
        _fecha.set(Calendar.MINUTE, 59);
        _fecha.set(Calendar.SECOND, 59);
        _fecha.set(Calendar.MILLISECOND, 0);
        return _fecha.getTime();
    }

    @Secured({"ROLE_EXTERNAL"})
    @RequestMapping(value = {DriverConstants.MANAGE_CONTROL_CARD}, method = RequestMethod.GET)
    public String consultExternalUserGet(HttpSession session, Locale locale, Model model,
            @RequestParam String numeroTarjetaControl, @RequestParam String tipoTransaccion,
            RedirectAttributes redirectAttributes) {
        armarcombos(model, session, locale);
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

        List<EmpresaDTO> listEmpresaDTO = usuarioDAO.findCompanysByUserName(usuario);

        model.addAttribute("numeroTarjetaControl", numeroTarjetaControl);
        List<Long> empresas = new ArrayList<>();
        for (EmpresaDTO empresa : listEmpresaDTO) {
            empresas.add(empresa.getId());
        }
        ConductorVehiculoDTO response = driverService.findByControlCard(numeroTarjetaControl, tipoTransaccion);


        if (response == null) {
            redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage("error.message.consult.targetcontrol", new Object[]{numeroTarjetaControl}, locale));
            return "redirect:" + DriverConstants.CONTROL_CARD_MANAGEMENT;
        } else if (response.getTarjetaControlEstado() == EstadoTarjetaControlEnum.CANCELADO.getPk()) {
            model.addAttribute("tipoTramite", EstadoTarjetaControlEnum.CANCELADO.name());
        } else {
            model.addAttribute("tipoTramite", EstadoTarjetaControlEnum.REFRENDADA.name());
            model.addAttribute("fechaValidezTC", new SimpleDateFormat("dd/MM/yyyy").format(response.getFechaValidez()));
        }
        model.addAttribute("driverDTO", utilityService.mapDriverDTO(response));
        return URL_CREATE_DRIVER;
    }
    

    
    @Secured({"ROLE_EXTERNAL"})
    @RequestMapping(value = {DriverConstants.ADD_PLANILLA}, method = RequestMethod.POST)
    public String agregar(@ModelAttribute CreateDriverDTO driverDTO, HttpSession session, Locale locale, Model model,
            @RequestParam String action, @RequestParam String t, BindingResult result, 
            RedirectAttributes redirectAttributes) {
        String cantidadPlanillas = parametroSimurDAO.findByKey(SystemParameters.CANTIDAD_MAX_PLANILLAS.getValue()).getValorParametro();
        Integer cantMaxPlanillas = Integer.valueOf(cantidadPlanillas);
        
        if (driverDTO != null) {
            if (action.equals(DriverConstants.ADD_ACTION)) {

                if (driverDTO.getPlanillas().size() == cantMaxPlanillas) {
                    model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage("error.message.maxCantidadPlanillas", new Object[]{cantMaxPlanillas}, locale));
                } else {
                    driverDTO.getPlanillas().add(new PagoPilaDTO());
                }
            } else if (action.equals(DriverConstants.DELETE_ACTION)) {
                if (driverDTO.getPlanillas().size() == 1) {
                    model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage("error.message.minCantidadPlanillas", new Object[]{cantMaxPlanillas}, locale));
                } else {
                    driverDTO.getPlanillas().remove(driverDTO.getPlanillas().size() - 1);
                }
            }
        }
        model.addAttribute(COMMAND_NAME, driverDTO);
        session.setAttribute(COMMAND_NAME, driverDTO);
        model.addAttribute("tipoTramite", t);
        armarcombos(model, session, locale);
        if(EstadoTarjetaControlEnum.EXPEDIDA.name().equals(t)) {
            return URL_CREATE_CARD_DRIVER;
        }else{
            return URL_CREATE_DRIVER;
        }
    }

    @RequestMapping(value = DriverConstants.GET_IMG, method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImg(@RequestParam("id") long id, HttpServletResponse response, Locale locale) {
        ParametroDTO rutaImg = parametroSimurDAO.findByKey(SystemParameters.URI_IMG.getValue());
        try {
            byte[] data = null;
            ConductorEntity c = null;
            if (id != 0) {
                c = conductorDAO.findByPrimaryKey(id);
            }
            String filename = c.getRutaFoto();
            if (filename != null && !filename.isEmpty()) {
                if (!filename.contains(".jpg")) {
                    filename += ".jpg";
                }
                if (!filename.contains(rutaImg.getValorParametro())) {
                    filename = rutaImg.getValorParametro().concat(filename);
                }
                Path path = Paths.get(filename);
                data = Files.readAllBytes(path);
                return data;
            }
        } catch (Exception e) {
            LOGGER.error("ha ocurrido un error al intentar consultar el documento:", e);
        }
        return null;

    }

    @RequestMapping(value = {DriverConstants.CREATE_DRIVER_CANCELACION}, method = RequestMethod.POST)
    public String cancelCardManagement(@ModelAttribute CreateDriverDTO driverDTO, HttpSession session, Locale locale, Model model,
                                       RedirectAttributes redirectAttributes) {
        if (driverDTO.getId() == 0) {
            redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_ERROR, messageSource.getMessage("common.error.required.fields", null, locale));
            return "redirect:" + DriverConstants.CONTROL_CARD_MANAGEMENT;
        }
        setBreadcrumbCreateExternalUser(model, locale);
        conductorVehiculoDAO.cancelCardManagement(driverDTO.getId());
        registrarLogRefrendacionCancelacion(driverDTO);
        redirectAttributes.addFlashAttribute(CommonConstants.PAGE_MESSAGE_SUCCESS, messageSource.getMessage("form.driver.cancelar.successfull", null, locale));
        return "redirect:" + DriverConstants.CONTROL_CARD_MANAGEMENT;
    }



    @RequestMapping(value = {DriverConstants.CONSULT_FACTOR_QA}, method = RequestMethod.POST)
    public String consultarFactorCalidad(@ModelAttribute CreateDriverDTO driverDTO, HttpSession session,
            Locale locale, Model model, BindingResult result, RedirectAttributes redirectAttributes) {
    	if (driverDTO != null && driverDTO.getPlaca() != null) {
            if (driverDTO.getEmpresaDTO() != null && driverDTO.getEmpresaDTO().getId() != null) {
                FactorCalidadDTO factorCalidadDTO = new FactorCalidadDTO();
                factorCalidadDTO.setIdEstado(1);
                factorCalidadDTO.setPlaca(driverDTO.getPlaca().toUpperCase());
                factorCalidadDTO.setIdEmpresa(driverDTO.getEmpresaDTO().getId());
                List<FactorCalidadDTO> resultado = vehiculoFactorCalidadDAO.consultarFactorCalidad(factorCalidadDTO);
                if (resultado != null && !resultado.isEmpty()) {
                    driverDTO.setFactorCalidad(true);
                    driverDTO.setIdFactorCalidad(resultado.get(0).getId());
                }
            }
            /**
             * driverDTO.setNroTarjetaOperacion("");
             * driverDTO.setFechaVencimientoTO(null); VehiculoSimDTO
             * vehiculoSimDTO = consultarVehiculoSim(driverDTO.getPlaca());
             * String mensajesError = validarVehiculoSim(driverDTO,
             * vehiculoSimDTO, locale); if (mensajesError != null &&
             * !mensajesError.isEmpty()) {
             * model.addAttribute(CommonConstants.PAGE_MESSAGE_ERROR,
             * mensajesError); } else {
             * driverDTO.setNroTarjetaOperacion(vehiculoSimDTO.getNroTarjetaOperacion());
             * driverDTO.setFechaVencimientoTO(Utils.cadenaAFecha(vehiculoSimDTO.getFechaVenTarjetaOperacion(),
             * "dd/MM/yyyy")); }*
             */
        }
        model.addAttribute("tipoTramite", "1");
        model.addAttribute(COMMAND_NAME, driverDTO);
        armarcombos(model, session, locale);
        return URL_CREATE_DRIVER;
    }

    private String validarVehiculoSim(CreateDriverDTO driverDTO, VehiculoSimDTO vehiculoSimDTO, Locale locale) {
        String mensajesError = "";
        if (vehiculoSimDTO == null) {
            mensajesError += messageSource.getMessage("form.driver.sim.error.noexiste", null, locale) + "<br />";
        } else {
            if (vehiculoSimDTO.getIdEstado() != 1) {
                mensajesError += messageSource.getMessage("form.driver.sim.error.estado", null, locale) + "<br />";
            }
            if (vehiculoSimDTO.getIdServicio() != 2) {
                mensajesError += messageSource.getMessage("form.driver.sim.error.servicio", null, locale) + "<br />";
            }
            String nitEmpresa = driverDTO.getEmpresaDTO() != null && driverDTO.getEmpresaDTO().getNit() != null
                    && !driverDTO.getEmpresaDTO().getNit().isEmpty() ? driverDTO.getEmpresaDTO().getNit() : "";
            nitEmpresa = nitEmpresa.replace("-", "");
            nitEmpresa = nitEmpresa.length() <= 9 ? nitEmpresa : nitEmpresa.substring(0, 9);
            String nitSIM = vehiculoSimDTO.getNumeroDocEmpresa() != null
                    && !vehiculoSimDTO.getNumeroDocEmpresa().isEmpty() ? vehiculoSimDTO.getNumeroDocEmpresa() : "";
            nitSIM = nitSIM.replace("-", "");
            nitSIM = nitSIM.length() <= 9 ? nitSIM : nitSIM.substring(0, 9);
            if (!nitEmpresa.equals(nitSIM)) {
                mensajesError += messageSource.getMessage("form.driver.sim.error.empresa", null, locale) + "<br />";
            }
            //Se inactiva la validación de TO contra el SIM mientras se valida el ingreso de TO refrendadas
            if (driverDTO.getNroTarjetaOperacion() != null && !driverDTO.getNroTarjetaOperacion().isEmpty()
                    && !vehiculoSimDTO.getNroTarjetaOperacion().equals(driverDTO.getNroTarjetaOperacion()) && false) {
                mensajesError += messageSource.getMessage("form.driver.sim.error.nroto", null, locale) + "<br />";
            }
            String fechaVenTO = Utils.fechaACadena(Utils.cadenaAFecha(vehiculoSimDTO.getFechaVenTarjetaOperacion(), "dd/MM/yyyy"), "dd/MM/yyyy");
            //Se inactiva la validación de TO contra el SIM mientras se valida el ingreso de TO refrendadas
            if (driverDTO.getFechaVencimientoTO() != null && !fechaVenTO.equals(Utils.fechaACadena(driverDTO.getFechaVencimientoTO(), "dd/MM/yyyy")) && false) {
                mensajesError += messageSource.getMessage("form.driver.sim.error.vento", null, locale) + "<br />";
            }
            if (!"Activa".equals(vehiculoSimDTO.getEstadoTarjetaOperacion())) {
                mensajesError += messageSource.getMessage("form.driver.sim.error.estadoto", null, locale) + "<br />";
            }
        }
        return mensajesError;
    }

    /**
     * private VehiculoSimDTO consultarVehiculoSim(String nroPlaca) { if
     * (nroPlaca != null && !nroPlaca.isEmpty()) { SimurWS_Service service = new
     * SimurWS_Service(); SimurWS port = service.getSimurWSPort(); String result
     * = port.consultarSimur("01WSSIMUR", "7287", nroPlaca.toUpperCase()); if
     * (result != null && !result.isEmpty()) { VehiculoSimDTO vehiculoSimDTO =
     * SimUtil.getVehiculoSimDTO(result); return vehiculoSimDTO; } } return
     * null; }*
     */
    private void armarcombos(Model model, HttpSession session, Locale locale) {
        /**
         * model.addAttribute(DISPOSITIVO_MOVIL_REQUERIDO, false); if
         * (dispositivoMovilRequerido == null) { dispositivoMovilRequerido =
         * parametroSimurDAO.findByKey(SystemParameters.DISPOSITIVO_MOVIL_REQUERIDO_FACTOR_CALIDAD.getValue());
         * if (dispositivoMovilRequerido != null &&
         * "true".equals(dispositivoMovilRequerido.getValorParametro())) {
         * model.addAttribute(DISPOSITIVO_MOVIL_REQUERIDO, true); } } else if
         * (dispositivoMovilRequerido != null &&
         * "true".equals(dispositivoMovilRequerido.getValorParametro())) {
         * model.addAttribute(DISPOSITIVO_MOVIL_REQUERIDO, true); }*
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar fecha = Calendar.getInstance();
        Calendar finMetodoCobroUnidades = null;
        Calendar inicioTipoServicioLujo = null;
        if (fechaFinMetodoCobroUnidades == null) {
            fechaFinMetodoCobroUnidades = parametroSimurDAO.findByKey(SystemParameters.FECHA_FIN_METODO_COBRO_UNIDADES.getValue());
            if (fechaFinMetodoCobroUnidades != null && !fechaFinMetodoCobroUnidades.getValorParametro().isEmpty()) {
                try {
                    finMetodoCobroUnidades = Calendar.getInstance();
                    finMetodoCobroUnidades.setTime(sdf.parse(fechaFinMetodoCobroUnidades.getValorParametro()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else if (fechaFinMetodoCobroUnidades != null && !fechaFinMetodoCobroUnidades.getValorParametro().isEmpty()) {
            try {
                finMetodoCobroUnidades = Calendar.getInstance();
                finMetodoCobroUnidades.setTime(sdf.parse(fechaFinMetodoCobroUnidades.getValorParametro()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (fechaInicioNivelServicioLujo == null) {
            fechaInicioNivelServicioLujo = parametroSimurDAO.findByKey(SystemParameters.FECHA_INICIO_NIVEL_SERVICIO_LUJO.getValue());
            if (fechaInicioNivelServicioLujo != null && !fechaInicioNivelServicioLujo.getValorParametro().isEmpty()) {
                try {
                    inicioTipoServicioLujo = Calendar.getInstance();
                    inicioTipoServicioLujo.setTime(sdf.parse(fechaInicioNivelServicioLujo.getValorParametro()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else if (fechaInicioNivelServicioLujo != null && !fechaInicioNivelServicioLujo.getValorParametro().isEmpty()) {
            try {
                inicioTipoServicioLujo = Calendar.getInstance();
                inicioTipoServicioLujo.setTime(sdf.parse(fechaInicioNivelServicioLujo.getValorParametro()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List<MetodoCobroEntity> listMetodoCobroEntity = metodoCobroDAO.findAll();
        List<MetodoCobroDTO> listMetodoCobroDTO = new ArrayList<>();
        for (MetodoCobroEntity metodoCobroEntity : listMetodoCobroEntity) {
            if (metodoCobroEntity.getIdEstado() == 1) {
                MetodoCobroDTO metodoCobroDTO = new MetodoCobroDTO();
                metodoCobroDTO.setDescripcion(metodoCobroEntity.getDescripcion());
                metodoCobroDTO.setIdEstado(metodoCobroEntity.getId());
                metodoCobroDTO.setIdMetodoCobro(metodoCobroEntity.getId());
                listMetodoCobroDTO.add(metodoCobroDTO);
                if (metodoCobroEntity.getId() == ID_METODO_COBRO_UNIDADES && finMetodoCobroUnidades != null && finMetodoCobroUnidades.before(fecha)) {
                    listMetodoCobroDTO.remove(metodoCobroDTO);
                }
            }
        }

        List<TipoServicioEntity> listTipoServicioEntity = tipoServicioDAO.findAll();
        List<TipoServicioDTO> listTipoServicioDTO = new ArrayList<>();
        for (TipoServicioEntity tipoServicioEntity : listTipoServicioEntity) {
            if (tipoServicioEntity.getIdEstado() == 1) {
                TipoServicioDTO tipoServicioDTO = new TipoServicioDTO();
                tipoServicioDTO.setDescripcion(tipoServicioEntity.getDescripcion());
                tipoServicioDTO.setIdEstado(tipoServicioEntity.getId());
                tipoServicioDTO.setIdTipoServicio(tipoServicioEntity.getId());
                listTipoServicioDTO.add(tipoServicioDTO);
                if (tipoServicioEntity.getId() == ID_TIPO_SERVICIO_LUJO && inicioTipoServicioLujo != null && inicioTipoServicioLujo.after(fecha)) {
                    listTipoServicioDTO.remove(tipoServicioDTO);
                }
            }
        }

        List<AfpEntity> listAfpEntity = afpDAO.findAll();
        List<AfpDTO> listAfpDTO = new ArrayList<>();

        for (AfpEntity afpEntity : listAfpEntity) {
            AfpDTO afpDTO = new AfpDTO();
            afpDTO.setIdAfp(afpEntity.getId());
            afpDTO.setNombreAfp(afpEntity.getNombreAfp());
            listAfpDTO.add(afpDTO);
        }

        List<EpsEntity> listEpsEntity = epsDAO.findAll();
        List<EpsDTO> listEpsDTO = new ArrayList<>();

        for (EpsEntity epsEntity : listEpsEntity) {
            EpsDTO epsDTO = new EpsDTO();
            epsDTO.setIdEps(epsEntity.getId());
            epsDTO.setNombreEps(epsEntity.getNombreEps());
            listEpsDTO.add(epsDTO);
        }

        List<ArlEntity> listArlEntity = arlDAO.findAll();
        List<ArlDTO> listArlDTO = new ArrayList<>();

        for (ArlEntity arlEntity : listArlEntity) {
            ArlDTO arlDTO = new ArlDTO();
            arlDTO.setIdArl(arlEntity.getId());
            arlDTO.setNombreArl(arlEntity.getNombreArl());
            listArlDTO.add(arlDTO);
        }

        List<TipoDocumentoEntity> listTipoDocumentoEntity = tipoDocumentoDAO.findAll();
        List<TipoDocumentoDTO> listTipoDocumentoDTO = new ArrayList<>();

        for (TipoDocumentoEntity tipoDocumentoEntity : listTipoDocumentoEntity) {
            TipoDocumentoDTO tipoDocumentoDTO = new TipoDocumentoDTO();
            tipoDocumentoDTO.setId(tipoDocumentoEntity.getId());
            tipoDocumentoDTO.setDescripcionTipoDocumento(tipoDocumentoEntity.getDescripcionTipoDoc());

            listTipoDocumentoDTO.add(tipoDocumentoDTO);
        }

        List<OperadoresPilaEntity> listOperadoresPilaEntity = operadoresPilaDAO.findAll();
        List<OperadoresPilaDTO> listOperadoresPilaDTO = new ArrayList<>();

        for (OperadoresPilaEntity operadoresPilaEntity : listOperadoresPilaEntity) {
            OperadoresPilaDTO operadoresPilaDTO = new OperadoresPilaDTO();
            operadoresPilaDTO.setCodigoOperador(operadoresPilaEntity.getCodigo());
            operadoresPilaDTO.setIdOperador(operadoresPilaEntity.getId());
            operadoresPilaDTO.setNombre(operadoresPilaEntity.getNombre());
            operadoresPilaDTO.setOperador(operadoresPilaEntity.getOperador());
            listOperadoresPilaDTO.add(operadoresPilaDTO);
        }

        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        List<EmpresaDTO> listEmpresaDTO = usuarioDAO.findCompanysByUserName(usuario);

        setBreadcrumbCreateExternalUser(model, locale);
        Collections.sort(listAfpDTO);
        AfpDTO AfpDTO = listAfpDTO.stream().filter(afp -> afp.getIdAfp() == 1).findFirst().get();
        if (AfpDTO != null) {
            listAfpDTO.remove(AfpDTO);
            listAfpDTO.add(0, AfpDTO);
        }
        model.addAttribute(AFP_LIST_NAME, listAfpDTO);
        Collections.sort(listEpsDTO);
        model.addAttribute(EPS_LIST_NAME, listEpsDTO);
        Collections.sort(listArlDTO);
        model.addAttribute(ARL_LIST_NAME, listArlDTO);
        Collections.sort(listTipoDocumentoDTO);
        model.addAttribute(TIPO_DOCUMENTO_LIST_NAME, listTipoDocumentoDTO);
        Collections.sort(listEmpresaDTO);
        model.addAttribute(EMPRESA_LIST_NAME, listEmpresaDTO);
        Collections.sort(listMetodoCobroDTO);
        model.addAttribute(METODO_COBRO_LIST_NAME, listMetodoCobroDTO);
        Collections.sort(listTipoServicioDTO);
        model.addAttribute(TIPO_SERVICIO_LIST_NAME, listTipoServicioDTO);
        Collections.sort(listOperadoresPilaDTO);
        model.addAttribute(OPERADORES_PILA_LIST_NAME, listOperadoresPilaDTO);
    }
}
