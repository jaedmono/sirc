package co.gov.movilidadbogota.core.service;

import co.gov.movilidadbogota.core.dao.*;
import co.gov.movilidadbogota.core.dto.*;
import co.gov.movilidadbogota.core.entity.*;
import co.gov.movilidadbogota.core.util.EstadoTarjetaControlEnum;
import co.gov.movilidadbogota.core.util.SystemParameters;
import co.gov.movilidadbogota.core.util.Utils;
import co.gov.movilidadbogota.web.util.QRGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static co.gov.movilidadbogota.core.dao.AbstractDAO.LOG_PREFIX;

@Service
public class DriverService {

    @Autowired
    private ConductorVehiculoDAO conductorVehiculoDAO;
    @Autowired
    private ConductorDAO conductorDAO;
    @Autowired
    private PersonaDAO personaDAO;
    @Autowired
    private ParametroSimurDAO parametroSimurDAO;
    @Autowired
    private TipoServicioDAO tipoServicioDAO;
    @Autowired
    private VehiculoFactorCalidadDAO vehiculoFactorCalidadDAO;
    @Autowired
    private TipoDocumentoDAO tipoDocumentoDAO;
    @Autowired
    private EmpresaDAO empresaDAO;
    @Autowired
    private MetodoCobroDAO metodoCobroDAO;
    @Autowired
    private OperadoresPilaDAO operadoresPilaDAO;
    @Autowired
    private PagoPilaDAO pagoPilaDAO;
    @Autowired
    private RadicadoTarjetaControlDAO radicadoTarjetaControlDAO;
    @Autowired
    private AuditoriaSircDAO auditoriaSircDAO;
    @Autowired
    private RtoVigenciaDAO rtoVigenciaDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverService.class+LOG_PREFIX);

	public ConductorVehiculoEntity expedirTarjetaControl(Model model, CreateDriverDTO driverDTO, Locale locale,
                                         RedirectAttributes redirectAttributes, String user, String origenTransaction) throws Exception {

        String fileName = String.valueOf(System.currentTimeMillis());
        TipoDocumentoEntity tipoDocumento = tipoDocumentoDAO.findByPrimaryKey(driverDTO.getConductorDTO().getPersona().getTipoIdentificacion());
        PersonaEntity persona = personaDAO.findByNumeroDocumentoIdTipoDocumento(driverDTO.getConductorDTO().getPersona().getNumeroIdentificacion(),
                tipoDocumento.getId());
        if (persona == null) {
            persona = new PersonaEntity();
            persona.setNombres(driverDTO.getConductorDTO().getPersona().getNombres());
            persona.setApellidos(driverDTO.getConductorDTO().getPersona().getApellidos() != null ? driverDTO.getConductorDTO().getPersona().getApellidos() : "");
            persona.setNumeroDocumento(driverDTO.getConductorDTO().getPersona().getNumeroIdentificacion());
            persona.setCelular(Long.valueOf(driverDTO.getConductorDTO().getPersona().getCelular()));
            persona.setDireccion(driverDTO.getConductorDTO().getPersona().getDireccion());
            persona.setTipoDocumento(tipoDocumento);
            persona.setFechaExpedicionDocumento(driverDTO.getConductorDTO().getPersona().getFechaExpedicionDocumento());
            persona.setFechaNacimiento(driverDTO.getConductorDTO().getPersona().getFechaNacimiento());
            personaDAO.create(persona);
        } else {
            persona.setNombres(driverDTO.getConductorDTO().getPersona().getNombres());
            persona.setApellidos(driverDTO.getConductorDTO().getPersona().getApellidos() != null ? driverDTO.getConductorDTO().getPersona().getApellidos() : "");
            persona.setCelular(Long.valueOf(driverDTO.getConductorDTO().getPersona().getCelular()));
            persona.setDireccion(driverDTO.getConductorDTO().getPersona().getDireccion());
            personaDAO.update(persona);
        }
        ConductorEntity conductor = conductorDAO.findOneByAttribute(ConductorEntity_.persona, persona);
        if (conductor == null) {
            conductor = new ConductorEntity();
            conductor.setGrupoSanguineo(driverDTO.getConductorDTO().getGrupoSanguineo());
            conductor.setFactorRh(driverDTO.getConductorDTO().getFactorRh());
            conductor.setIdAfp(Long.valueOf(driverDTO.getConductorDTO().getIdAfp()));
            conductor.setIdEps(Long.valueOf(driverDTO.getConductorDTO().getIdEps()));
            conductor.setIdArl(Long.valueOf(driverDTO.getConductorDTO().getIdArl()));
            conductor.setPersona(persona);
            conductor.setRutaFoto(fileName);
            conductorDAO.create(conductor);
        } else {
            conductor.setIdAfp(Long.valueOf(driverDTO.getConductorDTO().getIdAfp()));
            conductor.setIdEps(Long.valueOf(driverDTO.getConductorDTO().getIdEps()));
            conductor.setIdArl(Long.valueOf(driverDTO.getConductorDTO().getIdArl()));
            conductor.setRutaFoto(fileName);
            conductorDAO.update(conductor);
        }
        // CREANDO TARJETA CONTROL
        Date fechaMenor = validarVigencia(driverDTO);
        ParametroDTO validez = parametroSimurDAO.findByKey(SystemParameters.VALIDEZ_TARJETA.getValue());
        ParametroDTO vigencia = parametroSimurDAO.findByKey(SystemParameters.VIGENCIA_TARJETA.getValue());
        
        Calendar validezFecha = getCalendar(validez.getValorParametro());
        Calendar vigenciaFecha = getCalendar(vigencia.getValorParametro());


        ConductorVehiculoEntity conductorVehiculo = new ConductorVehiculoEntity();
        EmpresaEntity empresa = empresaDAO.findByPrimaryKey(driverDTO.getEmpresaDTO().getId());
        conductorVehiculo.setTipoTransaccion(Long.toString(EstadoTarjetaControlEnum.EXPEDIDA.getPk()));
        conductorVehiculo.setEmpresa(empresa);
        conductorVehiculo.setConductor(conductor);
        conductorVehiculo.setTarjetaControl(getTarjetaControl(driverDTO));
        conductorVehiculo.setPlacaSerialVehiculo(driverDTO.getPlaca().toUpperCase());
        conductorVehiculo.setFechaValidez(validezFecha.getTime().after(fechaMenor) ? fechaMenor : validezFecha.getTime());
        conductorVehiculo.setFechaVigencia(vigenciaFecha.getTime().after(fechaMenor) ? fechaMenor : vigenciaFecha.getTime());
        conductorVehiculo.setFechaExpedicion(new Date());
        conductorVehiculo.setIdEstado(new TarjetacontrolEstadoEntity());
        conductorVehiculo.getIdEstado().setId(EstadoTarjetaControlEnum.EXPEDIDA.getPk());
        conductorVehiculo.setFechaVencimientoSoat(driverDTO.getFechaVencimientoSoat());
        conductorVehiculo.setNroSOAT(driverDTO.getNroSOAT());
        conductorVehiculo.setFechaVencimientoRtm(driverDTO.getFechaVencimientoRtm());
        conductorVehiculo.setNroRTM(driverDTO.getNroRTM());
        conductorVehiculo.setNroTarjetaOperacion(driverDTO.getNroTarjetaOperacion());
        conductorVehiculo.setFechaVencimientoTO(driverDTO.getFechaVencimientoTO());
        MetodoCobroEntity metodoCobro = metodoCobroDAO.findByPrimaryKey(driverDTO.getIdMetodoCobro());
        conductorVehiculo.setMetodoPago(metodoCobro);
        TipoServicioEntity tipoServicio = tipoServicioDAO.findByPrimaryKey(driverDTO.getIdTipoServicio());
        conductorVehiculo.setTipoServicio(tipoServicio);
        if (driverDTO.getIdFactorCalidad() != 0) {
            VehiculoFactorCalidadEntity factorCalidad = vehiculoFactorCalidadDAO.findByPrimaryKey(driverDTO.getIdFactorCalidad());
            conductorVehiculo.setFactorCalidad(factorCalidad);
        }
        if (conductorVehiculo.getFechaVigencia().after(conductorVehiculo.getFechaValidez())) {
            conductorVehiculo.setFechaVigencia(conductorVehiculo.getFechaValidez());
        }
        conductorVehiculoDAO.create(conductorVehiculo);

        registrarLog(conductorVehiculo, user, origenTransaction);
        createPagoPila(driverDTO, conductorVehiculo, vigenciaFecha );

        if (!fileName.contains(".jpg")) {
            fileName += ".jpg";
        }
        ParametroDTO rutaImg = parametroSimurDAO.findByKey(SystemParameters.URI_IMG.getValue());
        Path basePath = Paths.get(rutaImg.getValorParametro());
        Utils.createFile(fileName, driverDTO, basePath);
        ParametroDTO urlSimur = parametroSimurDAO.findByKey(SystemParameters.URL_SIMUR_QR.getValue());
        BufferedImage bufferedImage = QRGenerator.generateQRCodeImage(conductorVehiculo.getTarjetaControl(), urlSimur.getValorParametro());
        QRGenerator.createQRFile(bufferedImage, basePath, conductorVehiculo.getTarjetaControl());
        return conductorVehiculo;


    }

    public ConductorVehiculoDTO buscarPorTarjetaControl(String numeroTarjetaControl, String tipoTransaccion){
        return conductorVehiculoDAO.buscarPorTarjetaControl(numeroTarjetaControl, tipoTransaccion);
    }
   
    public ConductorVehiculoEntity reExpedirTarjeta(CreateDriverDTO driverDTO, String user, String origenTransaction) throws Exception{
        ConductorVehiculoEntity conductorVehiculo = conductorVehiculoDAO.consultarConductorPortarjeta(driverDTO.getNumeroTarjetaControl());
        PersonaEntity persona = conductorVehiculo.getConductor().getPersona();
        if (persona == null) {
            //TODO: Retornar mensaje no se encontro la persona
        } else {
            persona.setCelular(Long.valueOf(driverDTO.getConductorDTO().getPersona().getCelular()));
            persona.setDireccion(driverDTO.getConductorDTO().getPersona().getDireccion());
            personaDAO.update(persona);
        }
        ConductorEntity conductor = conductorVehiculo.getConductor();
        if (conductor == null) {
            //TODO: Retornar mensaje no se encontro el conductor
        } else {
            conductor.setIdAfp(Long.valueOf(driverDTO.getConductorDTO().getIdAfp()));
            conductor.setIdEps(Long.valueOf(driverDTO.getConductorDTO().getIdEps()));
            conductor.setIdArl(Long.valueOf(driverDTO.getConductorDTO().getIdArl()));
            conductorDAO.update(conductor);
        }
        // CREANDO TARJETA CONTROL
        Date fechaMenor = validarVigencia(driverDTO);
        ParametroDTO validez = parametroSimurDAO.findByKey(SystemParameters.VALIDEZ_TARJETA.getValue());
        ParametroDTO vigencia = parametroSimurDAO.findByKey(SystemParameters.VIGENCIA_TARJETA.getValue());
        
        Calendar validezFecha = getCalendar(validez.getValorParametro());
        Calendar vigenciaFecha = getCalendar(vigencia.getValorParametro());

        conductorVehiculo.setTipoTransaccion(String.valueOf(driverDTO.getTipoTransaccion()));
        conductorVehiculo.setTarjetaControl(getTarjetaControl(driverDTO));
        conductorVehiculo.setFechaValidez(validezFecha.getTime().after(fechaMenor) ? fechaMenor : validezFecha.getTime());
        conductorVehiculo.setFechaVigencia(vigenciaFecha.getTime().after(fechaMenor) ? fechaMenor : vigenciaFecha.getTime());
        conductorVehiculo.setFechaExpedicion(new Date());
        conductorVehiculo.setIdEstado(new TarjetacontrolEstadoEntity());
        conductorVehiculo.getIdEstado().setId(EstadoTarjetaControlEnum.EXPEDIDA.getPk());
        conductorVehiculo.setFechaVencimientoSoat(driverDTO.getFechaVencimientoSoat());
        conductorVehiculo.setNroSOAT(driverDTO.getNroSOAT());
        conductorVehiculo.setFechaVencimientoRtm(driverDTO.getFechaVencimientoRtm());
        conductorVehiculo.setNroRTM(driverDTO.getNroRTM());
        conductorVehiculo.setFechaVencimientoTO(driverDTO.getFechaVencimientoTO());
        conductorVehiculo.setNroTarjetaOperacion(driverDTO.getNroTarjetaOperacion());
        TipoServicioEntity tipoServicio = tipoServicioDAO.findByPrimaryKey(driverDTO.getIdTipoServicio());
        conductorVehiculo.setTipoServicio(tipoServicio);
        if (driverDTO.getIdFactorCalidad() != 0) {
            VehiculoFactorCalidadEntity factorCalidad = vehiculoFactorCalidadDAO.findByPrimaryKey(driverDTO.getIdFactorCalidad());
            conductorVehiculo.setFactorCalidad(factorCalidad);
        }
        if (conductorVehiculo.getFechaVigencia().after(conductorVehiculo.getFechaValidez())) {
            conductorVehiculo.setFechaVigencia(conductorVehiculo.getFechaValidez());
        }
        conductorVehiculoDAO.update(conductorVehiculo);
        
        registrarLog(conductorVehiculo, user, origenTransaction);
        createPagoPila(driverDTO, conductorVehiculo, vigenciaFecha );
        
        ParametroDTO rutaImg = parametroSimurDAO.findByKey(SystemParameters.URI_IMG.getValue());
        Path basePath = Paths.get(rutaImg.getValorParametro());
        ParametroDTO urlSimur = parametroSimurDAO.findByKey(SystemParameters.URL_SIMUR_QR.getValue());
        BufferedImage bufferedImage = QRGenerator.generateQRCodeImage(conductorVehiculo.getTarjetaControl(), urlSimur.getValorParametro());
        QRGenerator.createQRFile(bufferedImage, basePath, conductorVehiculo.getTarjetaControl());
        return conductorVehiculo;

    }
    
    public ConductorVehiculoDTO getConductorVehiculoDTO(Long id){
        return conductorVehiculoDAO.buscarPorId(id);
    }

    public List<ControlCardDTO> getControlCards(CreateDriverDTO driverDTO){

        List<ControlCardDTO> controlCardDTOS =  new ArrayList<>();

        Long companyId = driverDTO.getEmpresaDTO() != null?driverDTO.getEmpresaDTO().getId(): null;
        Long documentNumber = driverDTO.getConductorDTO() != null && driverDTO.getConductorDTO().getPersona() != null?
                driverDTO.getConductorDTO().getPersona().getNumeroIdentificacion(): null;

        List<ConductorVehiculoEntity> conductorVehiculoEntities =
                conductorVehiculoDAO.queryReportByPassedParameters(
                        driverDTO.getFechaValidez(),
                        driverDTO.getFechaVigencia(),
                        driverDTO.getPlaca(),
                        companyId,
                        driverDTO.getIdEstado(),
                        documentNumber
                );

        if(conductorVehiculoEntities != null && conductorVehiculoEntities.size() > 0) {
            controlCardDTOS = conductorVehiculoEntities.stream().map(entity -> {
                ControlCardDTO controlCardDTO = new ControlCardDTO();
                controlCardDTO.setApellidos(entity.getConductor().getPersona().getApellidos());
                controlCardDTO.setEmpresa(entity.getEmpresa().getNombreRazonSocial());
                controlCardDTO.setTarjetaControl(entity.getTarjetaControl());
                controlCardDTO.setFechaVigencia(entity.getFechaVigencia());
                controlCardDTO.setNombres(entity.getConductor().getPersona().getNombres());
                controlCardDTO.setFechaExpedicion(entity.getFechaExpedicion());
                controlCardDTO.setEstado(entity.getIdEstado().getDescripcion());
                controlCardDTO.setFechaVigenciaRTM(entity.getFechaVencimientoRtm());
                controlCardDTO.setNumeroRTM(entity.getNroRTM());
                controlCardDTO.setFechaVigenciaSOAT(entity.getFechaVencimientoSoat());
                controlCardDTO.setNumeroSoat(entity.getNroSOAT());
                controlCardDTO.setNumeroTarjetaOperacion(entity.getNroTarjetaOperacion());
                controlCardDTO.setFechaVigenciaTarjetaOperacion(entity.getFechaVencimientoTO());
                controlCardDTO.setNumeroDocumento(entity.getConductor().getPersona().getNumeroDocumento());
                controlCardDTO.setPlaca(entity.getPlacaSerialVehiculo());
                controlCardDTO.setTelefono(entity.getConductor().getPersona().getCelular());
                controlCardDTO.setTipoTransaccion(entity.getTipoTransaccion());

                return controlCardDTO;
            }).collect(Collectors.toList());
        }
        return controlCardDTOS;
    }


    public boolean validarPeriodoPagoSeguridad(List<PagoPilaDTO> pagos) {
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

    public ConductorVehiculoEntity findByPlateAndDriver(String plate, Long typeIdNumber, Long idNumber) {
        return conductorVehiculoDAO.findActivByPlacaConductorExpedir(typeIdNumber, idNumber, plate);
    }
    
    private Calendar getCalendar(String date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, Integer.valueOf(date));
    	calendar.set(Calendar.HOUR_OF_DAY, 23);
    	calendar.set(Calendar.MINUTE, 59);
    	calendar.set(Calendar.SECOND, 59);
    	calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
    
    public void createPagoPila(CreateDriverDTO driverDTO, ConductorVehiculoEntity conductorVehiculo, Calendar vigenciaFecha) {
    	for (PagoPilaDTO planilla : driverDTO.getPlanillas()) {
            PagoPilaCondVehiEntity pagoPila = new PagoPilaCondVehiEntity();
            pagoPila.setEmpresa(conductorVehiculo.getEmpresa());
            pagoPila.setConductor(conductorVehiculo.getConductor());
            pagoPila.setVehiculo(conductorVehiculo);
            pagoPila.setNroAprobacionPago(planilla.getNumeroAprobacion());
            pagoPila.setIdEstado(true);
            pagoPila.setFechaIniciaVigencia(new Date());
            pagoPila.setFechaFinVigencia(vigenciaFecha.getTime());
            OperadoresPilaEntity operadorPila = operadoresPilaDAO.findByPrimaryKey(planilla.getOperadorPila());
            pagoPila.setOperador(operadorPila);
            planilla.setPeriodoPago(planilla.getStringPeriodoPago().replaceAll("/", ""));
            pagoPila.setPeriodoPagoPila(Long.valueOf(planilla.getStringPeriodoPago()));
            // hay que hacer la conversion de date a numeric formato mmyyyy
            pagoPilaDAO.create(pagoPila);
        }
    	
    	
    }

    private void registrarLog(ConductorVehiculoEntity conductorVehiculo, String user, String origenTransaction) {
        long radicado = Long.valueOf(conductorVehiculoDAO.obtenerNroRadicado().toString());

        AuditoriaSircEntity auditoriaSircEntity = new AuditoriaSircEntity();
        auditoriaSircEntity.setConductor(conductorVehiculo.getConductor());
        auditoriaSircEntity.setFechaEvento(new Date());
        auditoriaSircEntity.setObservacion("");
        auditoriaSircEntity.setOrigen(origenTransaction);
        auditoriaSircEntity.setPlaca(conductorVehiculo.getPlacaSerialVehiculo());
        auditoriaSircEntity.setIdTransaccion(conductorVehiculo.getIdEstado());
        auditoriaSircEntity.setTarjetaControl(radicado);
        auditoriaSircEntity.setUsuario(user);
        auditoriaSircEntity.setEmpresa(conductorVehiculo.getEmpresa());

        auditoriaSircDAO.crearRegistroLog(auditoriaSircEntity);

        RadicadoTarjetaEntity radicadoTarjetaEntity = new RadicadoTarjetaEntity();
        radicadoTarjetaEntity.setTarjetaControl(conductorVehiculo.getTarjetaControl());
        radicadoTarjetaEntity.setIdRadicado(radicado);

        radicadoTarjetaControlDAO.create(radicadoTarjetaEntity);
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

    private String getTarjetaControl(CreateDriverDTO driverDTO) {
        String documentID = driverDTO.getConductorDTO().getPersona().getNumeroIdentificacion().toString();
        return driverDTO.getPlaca().toUpperCase() + documentID.substring(documentID.length() - 6);
    }


    public List<RtoVigenciaEntity> findRtoByPlate(String plate) {
        if(plate != null) {
            return rtoVigenciaDAO.findAllByPlate(plate.toUpperCase());
        }
        return new ArrayList<>();
    }

    public ConductorVehiculoDTO findByControlCard(String numeroTarjetaControl, String tipoTransaccion) {
        ConductorVehiculoDTO conductorVehiculoDTO = conductorVehiculoDAO.buscarPorTarjetaControl(numeroTarjetaControl, tipoTransaccion);
        List<RtoVigenciaEntity> rtoVigenciaEntities = this.findRtoByPlate(conductorVehiculoDTO.getPlaca());
        if(rtoVigenciaEntities.size() > 0){
            conductorVehiculoDTO.setFactorCalidad(true);
            Date dueDate = rtoVigenciaEntities.stream().map(entity -> entity.getFechaVencimiento()).max(Date::compareTo).get();
            conductorVehiculoDTO.setFechaVencimientoTO(dueDate);
        }
        return conductorVehiculoDTO;
    }

    public void setBasicVehicleData(CreateDriverDTO driverDTO){
        try {
            List<RtoVigenciaEntity> rtoVigenciaEntities = this.findRtoByPlate(driverDTO.getPlaca());
            if (rtoVigenciaEntities.size() > 0) {
                driverDTO.setFactorCalidad(true);
                RtoVigenciaEntity entity = rtoVigenciaEntities.stream().max(Comparator.comparing(RtoVigenciaEntity::getFechaVencimiento)).get();
                if (entity != null && entity.getFechaVencimiento() != null
                        && LocalDateTime.now().isBefore(entity.getFechaVencimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
                    driverDTO.setFechaVencimientoTO(entity.getFechaVencimiento());
                    driverDTO.setNroTarjetaOperacion(entity.getNroTO());
                } else {
                    driverDTO.setFechaVencimientoTO(null);
                    driverDTO.setNroTarjetaOperacion("");
                }
            }else {
                driverDTO.setFechaVencimientoTO(null);
                driverDTO.setNroTarjetaOperacion("");
            }
        }catch (Exception e){
            LOGGER.error("Error buscando el vehiculo en la tabla RTO", e);
            driverDTO.setFechaVencimientoTO(null);
            driverDTO.setNroTarjetaOperacion("");
        }
        setMetodoCobroDao(driverDTO);
    }

    private void setMetodoCobroDao(CreateDriverDTO driverDTO)  {
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            ParametroDTO urlApiTaxis = parametroSimurDAO.findByKey(SystemParameters.URL_APITAXIS_METODO_COBRO.getValue());
            URIBuilder builder = new URIBuilder(urlApiTaxis.getValorParametro());
            builder.setParameter("placa", driverDTO.getPlaca().toUpperCase())
                    .setParameter("fechaInicio", LocalDate.now().minusMonths(1).toString())
                    .setParameter("fechaFin", LocalDate.now().toString());

            HttpGet request = new HttpGet(builder.build());
            LOGGER.info("Servicio Apitaxis consultado con los siguientes parametros, placa: "+ driverDTO.getPlaca().toUpperCase()
                    + "Fecha Inicio: "+ LocalDate.now().minusMonths(1)
                    + "Fecha Fin: " + LocalDate.now());
            ApitaxiResponse response = client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), ApitaxiResponse.class));

            LOGGER.info("Respuesta del servicio: "+ response.isRespuesta());
            if(response.isRespuesta()){
                driverDTO.setIdMetodoCobro(1l);
            }else{
                driverDTO.setIdMetodoCobro(2l);
            }
        } catch (Exception e) {
            LOGGER.error("Error consultando el servicio de Apitaxi", e);
            driverDTO.setIdMetodoCobro(2l);
        }
    }
}
