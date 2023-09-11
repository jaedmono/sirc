package co.gov.movilidadbogota.services;

import static co.gov.movilidadbogota.util.Conexion.getConnection;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import co.gov.movilidadbogota.comun.srvaplregistrarconductor.v1.FactorCalidadRespuesta;
import co.gov.movilidadbogota.comun.srvaplregistrarconductor.v1.MensajeRegistrarConductorError;
import co.gov.movilidadbogota.comun.srvaplregistrarconductor.v1.RegistrarConductorError;
import co.gov.movilidadbogota.comun.srvaplregistrarconductor.v1.RegistrarConductorRespuesta;
import co.gov.movilidadbogota.comun.srvaplregistrarconductor.v1.RegistrarConductorSolicitud;
import co.gov.movilidadbogota.dto.TarjetaControlDTO;
import co.gov.movilidadbogota.dto.UsuarioDTO;
import co.gov.movilidadbogota.esb.schema.conductor.v1.Conductor;
import co.gov.movilidadbogota.esb.schema.contactopersona.v1.ContactoPersona;
import co.gov.movilidadbogota.esb.schema.empresa.v1.Empresa;
import co.gov.movilidadbogota.esb.schema.errorsirc.v1.ErrorSIRC;
import co.gov.movilidadbogota.esb.schema.identificacion.v1.Identificacion;
import co.gov.movilidadbogota.esb.schema.licenciaconduccion.v1.LicenciaConduccion;
import co.gov.movilidadbogota.esb.schema.listadoplacas.v1.ListadoPlacas;
import co.gov.movilidadbogota.esb.schema.pagopila.v1.PagoPILA;
import co.gov.movilidadbogota.esb.schema.tarjetacontrol.v1.TarjetaControl;
import co.gov.movilidadbogota.esb.schema.telefono.v1.Telefono;
import co.gov.movilidadbogota.esb.schema.transaccionrespuesta.v1.TransaccionRespuesta;
import co.gov.movilidadbogota.esb.schema.transaccionsolicitud.v1.TransaccionSolicitud;
import co.gov.movilidadbogota.esb.schema.ubicacionpersona.v1.UbicacionPersona;
import co.gov.movilidadbogota.esb.schema.vehiculo.v1.Vehiculo;
import co.gov.movilidadbogota.servicioreceptorpersonaduups.ConfirmacionRecibo;
import co.gov.movilidadbogota.servicioreceptorpersonaduups.Direccion;
import co.gov.movilidadbogota.servicioreceptorpersonaduups.Email;
import co.gov.movilidadbogota.servicioreceptorpersonaduups.ErrorRespuesta;
import co.gov.movilidadbogota.servicioreceptorpersonaduups.NotificacionPersona;
import co.gov.movilidadbogota.servicioreceptorpersonaduups.PersonaNatural;
import co.gov.movilidadbogota.util.CodigoRespuesta;
import co.gov.movilidadbogota.util.EstadoTarjetaControlEnum;
import co.gov.movilidadbogota.util.FactorRHEnum;
import co.gov.movilidadbogota.util.OrigenTransaccionEnum;
import co.gov.movilidadbogota.util.SystemParameters;
import co.gov.movilidadbogota.util.TransaccionEstadoEnum;
import co.gov.movilidadbogota.util.Utils;
import co.gov.movilidadbogota.ws.sim.SimUtil;
import co.gov.movilidadbogota.ws.sim.SimurWS;
import co.gov.movilidadbogota.ws.sim.SimurWS_Service;
import co.gov.movilidadbogota.ws.sim.VehiculoSimDTO;
import co.gov.movilidadbogota.wsOLD.duups.ServicioReceptorPersona;
import co.gov.movilidadbogota.wsOLD.duups.ServicioReceptorPersonaDUUPSService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 *
 * @author franzjr
 */
@WebService(serviceName = "SrvAplRegistrarConductor")
public class RegistrarConductorService {

    @Resource
    WebServiceContext wsctx;

	private String username;

    private static final String LOG_PREFIX = "[SIRC-WS]";
    private static final Logger LOG = Logger.getLogger(RegistrarConductorService.class.getName()+LOG_PREFIX);
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String FORMATO_PERIODO = "MM/yyyy";



    /**
     * Permite registrar conductores a SIRC
     *
     * @param mensajeRegistrarConductorSolicitud
     * @return conductor creado
     * @throws MensajeRegistrarConductorError Si no cumple con la obligatoriedad
     * o se encuentra un error de validación
     *
     */
    @WebMethod(operationName = "registrarConductor")
    public RegistrarConductorRespuesta operacionRegistrarConductor(RegistrarConductorSolicitud mensajeRegistrarConductorSolicitud) throws MensajeRegistrarConductorError {
        RegistrarConductorRespuesta respuesta = new RegistrarConductorRespuesta();
        TransaccionRespuesta transaccionRespuesta = new TransaccionRespuesta();
        ErrorSIRC errorSirc = new ErrorSIRC();
        String errores;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdEmpresa(new ArrayList<>());
        boolean usuarioAutenticado = autenticarCliente(wsctx.getMessageContext(), usuarioDTO, errorSirc);
        if (usuarioAutenticado && usuarioDTO.getIdUser() != -1) {
            try {
                TransaccionSolicitud solicitud = mensajeRegistrarConductorSolicitud.getSolicitud();
                if (solicitud != null) {
                    if (!Utils.isNullOrEmpty(solicitud.getTipoTransaccion())) {
                        List<TarjetaControlDTO> tarjetasControl = null;
                        switch (solicitud.getTipoTransaccion()) {
                            case "1":
                                //Expedir
                                LOG.log(Level.INFO, "########## Expedición tarjeta control ##########");
                                transaccionRespuesta.setTipoTransaccion("Expedición");
                                transaccionRespuesta.setIdentificadorTransaccion("1");
                                if (validarCamposExpedicion(mensajeRegistrarConductorSolicitud, usuarioDTO, transaccionRespuesta)) {
                                    transaccionRespuesta.setEstadoTransaccion(crearTarjetaControl(mensajeRegistrarConductorSolicitud, transaccionRespuesta));
                                } else if (transaccionRespuesta.getListError() != null && !transaccionRespuesta.getListError().isEmpty()) {
                                    transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                                }
                                break;
                            case "2":
                                //Refrendar
                                LOG.log(Level.INFO, "########## Refrendación tarjeta control ##########");
                                transaccionRespuesta.setTipoTransaccion("Refrendación");
                                transaccionRespuesta.setIdentificadorTransaccion("2");
                                tarjetasControl = new ArrayList<>();
                                validarCamposRefrendacion(mensajeRegistrarConductorSolicitud, transaccionRespuesta, tarjetasControl, usuarioDTO);
                                if (transaccionRespuesta.getListError() != null && !transaccionRespuesta.getListError().isEmpty()) {
                                    transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                                } else {
                                    transaccionRespuesta.setEstadoTransaccion(createExternalUserRefrendacion(transaccionRespuesta, tarjetasControl));
                                }
                                break;
                            case "3":
                                //Cancelar
                                LOG.log(Level.INFO, "########## Cancelación tarjeta control ##########");
                                transaccionRespuesta.setTipoTransaccion("Cancelación");
                                transaccionRespuesta.setIdentificadorTransaccion("3");
                                tarjetasControl = new ArrayList<>();
                                validarCamposCancelacion(mensajeRegistrarConductorSolicitud, transaccionRespuesta, tarjetasControl, usuarioDTO);
                                if (tarjetasControl.isEmpty() || (transaccionRespuesta.getListError() != null && !transaccionRespuesta.getListError().isEmpty())) {
                                    transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                                } else {
                                    transaccionRespuesta.setEstadoTransaccion(cancelarTarjeta(transaccionRespuesta, tarjetasControl));
                                }
                                break;
                            default:
                                errores = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                                errores = String.format(errores, "Tipo transacción");
                                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                                errorSirc.setDescripcionError(errores);
                                transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                                transaccionRespuesta.getListError().add(errorSirc);
                                break;
                        }
                    } else {
                        errores = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                        errores = String.format(errores, "Tipo transacción");
                        transaccionRespuesta.setEstadoTransaccion(errores);
                    }
                } else {
                    errores = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                    errores = String.format(errores, "Datos solicitud");
                    transaccionRespuesta.setEstadoTransaccion(errores);
                }
                respuesta.setRespuesta(transaccionRespuesta);
                List<Conductor> conductores = mensajeRegistrarConductorSolicitud.getConductor();
                conductores.stream().forEach((conductor) -> {
                    if (conductor.getFotoConductor() != null) {
                        conductor.setFotoConductor(null);
                    }
                });
                respuesta.getConductor().addAll(conductores);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
                RegistrarConductorError error = new RegistrarConductorError();
                transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                transaccionRespuesta.setObservaciones(e.getMessage());
                error.setRespuesta(transaccionRespuesta);
                throw new MensajeRegistrarConductorError("", error, e);
            }
        } else {
            transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
            transaccionRespuesta.getListError().add(errorSirc);
            respuesta.setRespuesta(transaccionRespuesta);
        }
        return respuesta;
    }

    /**
     * Permite registrar conductores a SIRC
     *
     * @param mensajeRegistrarConductorSolicitud
     * @return conductor creado
     * @throws MensajeRegistrarConductorError Si no cumple con la obligatoriedad
     * o se encuentra un error de validación
     *
     */
    @WebMethod(operationName = "expedirTarjetaControl")
    public RegistrarConductorRespuesta expedirTarjetaControl(RegistrarConductorSolicitud mensajeRegistrarConductorSolicitud) throws MensajeRegistrarConductorError {
        RegistrarConductorRespuesta respuesta = new RegistrarConductorRespuesta();
        TransaccionRespuesta transaccionRespuesta = new TransaccionRespuesta();
        ErrorSIRC errorSirc = new ErrorSIRC();
        String errores;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdEmpresa(new ArrayList<>());
        boolean usuarioAutenticado = autenticarCliente(wsctx.getMessageContext(), usuarioDTO, errorSirc);
        if (usuarioAutenticado && usuarioDTO.getIdUser() != -1) {
            try {

                LOG.log(Level.INFO, "########## Expedición tarjeta control ##########");
                transaccionRespuesta.setTipoTransaccion("Expedición");
                transaccionRespuesta.setIdentificadorTransaccion("1");
                if (validarCamposExpedicion(mensajeRegistrarConductorSolicitud, usuarioDTO, transaccionRespuesta)) {
                    transaccionRespuesta.setEstadoTransaccion(crearTarjetaControl(mensajeRegistrarConductorSolicitud, transaccionRespuesta));
                } else if (transaccionRespuesta.getListError() != null && !transaccionRespuesta.getListError().isEmpty()) {
                    transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                }

                respuesta.setRespuesta(transaccionRespuesta);
                List<Conductor> conductores = mensajeRegistrarConductorSolicitud.getConductor();
                conductores.stream().forEach((conductor) -> {
                    if (conductor.getFotoConductor() != null) {
                        conductor.setFotoConductor(null);
                    }
                });
                respuesta.getConductor().addAll(conductores);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
                RegistrarConductorError error = new RegistrarConductorError();
                transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                transaccionRespuesta.setObservaciones(e.getMessage());
                error.setRespuesta(transaccionRespuesta);
                throw new MensajeRegistrarConductorError("", error, e);
            }
        } else {
            transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
            transaccionRespuesta.getListError().add(errorSirc);
            respuesta.setRespuesta(transaccionRespuesta);
        }
        return respuesta;
    }

    /**
     * Permite registrar conductores a SIRC
     *
     * @param mensajeRegistrarConductorSolicitud
     * @return conductor creado
     * @throws MensajeRegistrarConductorError Si no cumple con la obligatoriedad
     * o se encuentra un error de validación
     *
     */
    @WebMethod(operationName = "refrendarTarjetaControl")
    public RegistrarConductorRespuesta refrendarTarjetaControl(RegistrarConductorSolicitud mensajeRegistrarConductorSolicitud) throws MensajeRegistrarConductorError {
        RegistrarConductorRespuesta respuesta = new RegistrarConductorRespuesta();
        TransaccionRespuesta transaccionRespuesta = new TransaccionRespuesta();
        ErrorSIRC errorSirc = new ErrorSIRC();
        String errores;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdEmpresa(new ArrayList<>());
        boolean usuarioAutenticado = autenticarCliente(wsctx.getMessageContext(), usuarioDTO, errorSirc);
        if (usuarioAutenticado && usuarioDTO.getIdUser() != -1) {
            try {


                //Refrendar
                LOG.log(Level.INFO, "########## Refrendación tarjeta control ##########");
                transaccionRespuesta.setTipoTransaccion("Refrendación");
                transaccionRespuesta.setIdentificadorTransaccion("2");
                List<TarjetaControlDTO> tarjetasControl = new ArrayList<>();
                validarCamposRefrendacion(mensajeRegistrarConductorSolicitud, transaccionRespuesta, tarjetasControl, usuarioDTO);
                if (transaccionRespuesta.getListError() != null && !transaccionRespuesta.getListError().isEmpty()) {
                    transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                } else {
                    transaccionRespuesta.setEstadoTransaccion(createExternalUserRefrendacion(transaccionRespuesta, tarjetasControl));
                }

                respuesta.setRespuesta(transaccionRespuesta);
                List<Conductor> conductores = mensajeRegistrarConductorSolicitud.getConductor();
                conductores.stream().forEach((conductor) -> {
                    if (conductor.getFotoConductor() != null) {
                        conductor.setFotoConductor(null);
                    }
                });
                respuesta.getConductor().addAll(conductores);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
                RegistrarConductorError error = new RegistrarConductorError();
                transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                transaccionRespuesta.setObservaciones(e.getMessage());
                error.setRespuesta(transaccionRespuesta);
                throw new MensajeRegistrarConductorError("", error, e);
            }
        } else {
            transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
            transaccionRespuesta.getListError().add(errorSirc);
            respuesta.setRespuesta(transaccionRespuesta);
        }
        return respuesta;
    }

    /**
     * Permite registrar conductores a SIRC
     *
     * @param mensajeRegistrarConductorSolicitud
     * @return conductor creado
     * @throws MensajeRegistrarConductorError Si no cumple con la obligatoriedad
     * o se encuentra un error de validación
     *
     */
    @WebMethod(operationName = "cancelarTarjetaControl")
    public RegistrarConductorRespuesta cancelarTarjetaControl(RegistrarConductorSolicitud mensajeRegistrarConductorSolicitud) throws MensajeRegistrarConductorError {
        RegistrarConductorRespuesta respuesta = new RegistrarConductorRespuesta();
        TransaccionRespuesta transaccionRespuesta = new TransaccionRespuesta();
        ErrorSIRC errorSirc = new ErrorSIRC();
        String errores;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdEmpresa(new ArrayList<>());
        boolean usuarioAutenticado = autenticarCliente(wsctx.getMessageContext(), usuarioDTO, errorSirc);
        if (usuarioAutenticado && usuarioDTO.getIdUser() != -1) {
            try {

                LOG.log(Level.INFO, "########## Cancelación tarjeta control ##########");
                transaccionRespuesta.setTipoTransaccion("Cancelación");
                transaccionRespuesta.setIdentificadorTransaccion("3");
                List<TarjetaControlDTO> tarjetasControl = new ArrayList<>();
                validarCamposCancelacion(mensajeRegistrarConductorSolicitud, transaccionRespuesta, tarjetasControl, usuarioDTO);
                if (tarjetasControl.isEmpty() || (transaccionRespuesta.getListError() != null && !transaccionRespuesta.getListError().isEmpty())) {
                    transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                } else {
                    transaccionRespuesta.setEstadoTransaccion(cancelarTarjeta(transaccionRespuesta, tarjetasControl));
                }

                respuesta.setRespuesta(transaccionRespuesta);
                List<Conductor> conductores = mensajeRegistrarConductorSolicitud.getConductor();
                conductores.stream().forEach((conductor) -> {
                    if (conductor.getFotoConductor() != null) {
                        conductor.setFotoConductor(null);
                    }
                });
                respuesta.getConductor().addAll(conductores);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
                RegistrarConductorError error = new RegistrarConductorError();
                transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
                transaccionRespuesta.setObservaciones(e.getMessage());
                error.setRespuesta(transaccionRespuesta);
                throw new MensajeRegistrarConductorError("", error, e);
            }
        } else {
            transaccionRespuesta.setEstadoTransaccion(TransaccionEstadoEnum.FALLIDO.getPk());
            transaccionRespuesta.getListError().add(errorSirc);
            respuesta.setRespuesta(transaccionRespuesta);
        }
        return respuesta;
    }

    @WebMethod(operationName = "consultarFactorCalidadEmpresa")
    public FactorCalidadRespuesta consultarFactorCalidadEmpresa() throws MensajeRegistrarConductorError {
        Connection conn = null;
        FactorCalidadRespuesta respuesta = new FactorCalidadRespuesta();
        ErrorSIRC errorSirc = new ErrorSIRC();
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdEmpresa(new ArrayList<>());
        boolean usuarioAutenticado = autenticarCliente(wsctx.getMessageContext(), usuarioDTO, errorSirc);
        if (usuarioAutenticado && usuarioDTO.getIdUser() != -1) {
            try {
                String idEmpresa = "";
                for (int i = 0; i < usuarioDTO.getIdEmpresa().size(); i++) {
                    if (!idEmpresa.isEmpty()) {
                        idEmpresa += ", ";
                    }
                    idEmpresa += usuarioDTO.getIdEmpresa().get(i);
                }
                conn = getConnection();
                Statement statement = conn.createStatement();
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT VF.NRO_PLACA, E.CODIGO_EMPRESA, E.NOMBRE_RAZON_SOCIAL ");
                sql.append("FROM SMB_VEHICULO_FACTORCALIDAD VF, SMI_EMPRESA E ");
                sql.append("WHERE VF.ID_EMPRESA = E.ID_EMPRESA ");
                sql.append("AND VF.ID_ESTADO = 1 AND VF.ID_EMPRESA IN (");
                sql.append(idEmpresa);
                sql.append(") ");
                sql.append("ORDER BY E.NOMBRE_RAZON_SOCIAL, VF.NRO_PLACA");
                ResultSet rs = statement.executeQuery(sql.toString());
                ListadoPlacas placas = new ListadoPlacas();
                while (rs.next()) {
                    placas.getPlacas().add(rs.getString(1) + "-" + rs.getString(3));
                }
                placas.setTotal(placas.getPlacas().size());
                placas.setResultado("Total placas con factor de calidad: " + placas.getPlacas().size());
                respuesta.setPlacas(placas);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
                throw new MensajeRegistrarConductorError(e.getMessage(), new RegistrarConductorError());
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                   LOG.log(Level.SEVERE, null, ex);
                }
            }
        } else {
            respuesta.getErrores().add(errorSirc);
        }
        return respuesta;
    }

    private boolean validarCamposExpedicion(RegistrarConductorSolicitud solicitud, UsuarioDTO usuarioDTO, TransaccionRespuesta transaccionRespuesta) throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            for (Conductor conductor : solicitud.getConductor()) {
                validarCamposIdentificacion(conductor.getIdentificacion(), transaccionRespuesta, conn);
                validarLicenciaConduccion(conductor.getLicenciaConduccion(), transaccionRespuesta);
                validarDatosPersona(conductor, transaccionRespuesta);
                validarAseguradoras(conductor, transaccionRespuesta, conn);
                validarTelefonos(conductor.getContacto(), transaccionRespuesta);
                validarDatosUbicacion(conductor.getContacto(), transaccionRespuesta);
                validarEmpresa(conductor.getEmpresa(), usuarioDTO, transaccionRespuesta, conn);
                validarPagoPila(conductor.getPagopila(), transaccionRespuesta, conn);
                validarVehiculoPlaca(conductor.getVehiculo(), transaccionRespuesta, conn);
                //validarDatosVehiculoSIM(conductor.getVehiculo(), conductor.getEmpresa(), transaccionRespuesta);
                //validarDispositivosMovil(conductor.getVehiculo(), conductor.getInfoDispositivos(), usuarioDTO, transaccionRespuesta, conn);
                validarConductorVehiculo(conductor, transaccionRespuesta, conn);
                
                if (conductor.getTarjetaControl() != null && !Utils.isNullOrEmpty(conductor.getTarjetaControl().getNumeroTarjetaControl())) {
                    String parametro = CodigoRespuesta.ERROR_EXPEDICION_DATOS_TARJETA_CONTROL.getDescripcionRespuesta();
                    parametro = String.format(parametro, conductor.getTarjetaControl().getNumeroTarjetaControl());
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_EXPEDICION_DATOS_TARJETA_CONTROL.getCodigoRespuesta());
                    errorSirc.setDescripcionError(parametro);
                    transaccionRespuesta.getListError().add(errorSirc);
                }
                
                if(transaccionRespuesta.getListError() == null || transaccionRespuesta.getListError().isEmpty()){
                    return true;
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new Exception(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    private void validarCamposRefrendacion(RegistrarConductorSolicitud solicitud, TransaccionRespuesta transaccionRespuesta, List<TarjetaControlDTO> tarjetasControl, UsuarioDTO usuarioDTO) throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            for (Conductor conductor : solicitud.getConductor()) {
                if (validarTarjetaControl(conductor.getTarjetaControl(), transaccionRespuesta) && validarCamposIdentificacion(conductor.getIdentificacion(), transaccionRespuesta, conn)
                        && validarLicenciaConduccion(conductor.getLicenciaConduccion(), transaccionRespuesta) && validarTelefonos(conductor.getContacto(), transaccionRespuesta)
                        && validarDatosUbicacion(conductor.getContacto(), transaccionRespuesta) && validarEmpresa(conductor.getEmpresa(), usuarioDTO, transaccionRespuesta, conn)
                        && validarAseguradoras(conductor, transaccionRespuesta, conn) && validarPagoPila(conductor.getPagopila(), transaccionRespuesta, conn)
                        && validarDatosVehiculo(conductor.getVehiculo(), transaccionRespuesta, conn)) {
                    TarjetaControlDTO tarjetaControlDTO = buscarPorTarjetaControlRef(conductor.getTarjetaControl().getNumeroTarjetaControl(),
                            conductor.getEmpresa().getCodigoEmpresa().longValue(),
                            conductor.getIdentificacion().getTipoIdentificacion(), conductor.getIdentificacion().getNumeroIdentificacion(), conn);
                    if (tarjetaControlDTO == null || tarjetaControlDTO.getIdVehiculo() == 0) {
                        String parametro = CodigoRespuesta.ERROR_NO_EXISTE_DATOS_TARJETA_CONTROL.getDescripcionRespuesta();
                        parametro = String.format(parametro, conductor.getTarjetaControl().getNumeroTarjetaControl());
                        ErrorSIRC errorSirc = new ErrorSIRC();
                        errorSirc.setCodigoError(CodigoRespuesta.ERROR_NO_EXISTE_DATOS_TARJETA_CONTROL.getCodigoRespuesta());
                        errorSirc.setDescripcionError(parametro);
                        transaccionRespuesta.getListError().add(errorSirc);
                    } else {
                        tarjetaControlDTO.setConductor(conductor);
                        tarjetasControl.add(tarjetaControlDTO);
                    }
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new Exception(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    private void validarCamposCancelacion(RegistrarConductorSolicitud solicitud, TransaccionRespuesta transaccionRespuesta, List<TarjetaControlDTO> tarjetasControl, UsuarioDTO usuarioDTO) throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            for (Conductor conductor : solicitud.getConductor()) {
                if (validarTarjetaControl(conductor.getTarjetaControl(), transaccionRespuesta) && validarEmpresa(conductor.getEmpresa(), usuarioDTO, transaccionRespuesta, conn)) {
                    TarjetaControlDTO tarjetaControlDTO = buscarPorTarjetaControl(conductor.getTarjetaControl().getNumeroTarjetaControl(), conductor.getEmpresa().getCodigoEmpresa().longValue(), conn);
                    if (tarjetaControlDTO == null || tarjetaControlDTO.getIdVehiculo() == 0) {
                        String parametro = CodigoRespuesta.ERROR_NO_EXISTE_DATOS_TARJETA_CONTROL.getDescripcionRespuesta();
                        parametro = String.format(parametro, conductor.getTarjetaControl().getNumeroTarjetaControl());
                        ErrorSIRC errorSirc = new ErrorSIRC();
                        errorSirc.setCodigoError(CodigoRespuesta.ERROR_NO_EXISTE_DATOS_TARJETA_CONTROL.getCodigoRespuesta());
                        errorSirc.setDescripcionError(parametro);
                        transaccionRespuesta.getListError().add(errorSirc);
                    } else {
                        tarjetaControlDTO.setConductor(conductor);
                        tarjetasControl.add(tarjetaControlDTO);
                    }
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new Exception(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    private boolean validarCamposIdentificacion(Identificacion identificacion, TransaccionRespuesta transaccionRespuesta, Connection conn) throws Exception {
        boolean validaciones = true;
        Statement statement = conn.createStatement();
        String sql;
        String parametro;
        if (identificacion == null) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Identificación");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validaciones = false;
        } else {
            if (Utils.isNullOrEmpty(identificacion.getTipoIdentificacion())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Tipo Identificación");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validaciones = false;
            } else {
                int idTipoDocu = 0;
                sql = "SELECT ID_TIPO_DOCUMENTO FROM SMB_TIPO_DOCUMENTO WHERE HOMOLOGA_DUUPS = '" + identificacion.getTipoIdentificacion() + "'";
                statement.execute(sql);
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    idTipoDocu = rs.getInt(1);
                }
                if (idTipoDocu != 0) {
                    identificacion.setTipoIdentificacion(String.valueOf(idTipoDocu));
                } else {
                    parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                    parametro = String.format(parametro, "Tipo Identificación");
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(parametro);
                    transaccionRespuesta.getListError().add(errorSirc);
                    validaciones = false;
                }
                if (Utils.isNullOrEmpty(identificacion.getFechaExpedicionDoc())) {
                    parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                    parametro = String.format(parametro, "Fecha expedición documento");
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(parametro);
                    transaccionRespuesta.getListError().add(errorSirc);
                    validaciones = false;
                } else if (!Utils.validarFecha(identificacion.getFechaExpedicionDoc(), FORMATO_FECHA)) {
                    parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                    parametro = String.format(parametro, "Fecha expedición documento");
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(parametro);
                    transaccionRespuesta.getListError().add(errorSirc);
                    validaciones = false;
                }
            }
            if (Utils.isNullOrEmpty(identificacion.getNumeroIdentificacion())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Número Identificación");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validaciones = false;
            }
        }
        return validaciones;
    }

    private boolean validarLicenciaConduccion(LicenciaConduccion licenciaConduccion, TransaccionRespuesta transaccionRespuesta) {
        boolean validacion = true;
        String parametro;
        if (licenciaConduccion == null) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Licencia conducción");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validacion = false;
        } else {
            if (Utils.isNullOrEmpty(licenciaConduccion.getCategoria())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Categoría licencia conducción");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validacion = false;
            }
            if (Utils.isNullOrEmpty(licenciaConduccion.getFechaVencimiento())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Fecha vencimiento licencia conducción");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validacion = false;
            } else if (!Utils.validarFecha(licenciaConduccion.getFechaVencimiento(), FORMATO_FECHA)) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                parametro = String.format(parametro, "Fecha vencimiento licencia conducción");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validacion = false;
            }
        }
        return validacion;
    }

    private void validarDatosPersona(Conductor conductor, TransaccionRespuesta transaccionRespuesta) {
        List<String> grupoSanguineo = Arrays.asList("A", "B", "AB", "O");
        String parametro;
        if (conductor == null) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Datos persona");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
        } else {
            if (Utils.isNullOrEmpty(conductor.getPrimerNombre()) || Utils.isNullOrEmpty(conductor.getPrimerApellido())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Nombres y apellidos");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            }
            if (Utils.isNullOrEmpty(conductor.getFechaNacimiento())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Fecha nacimiento");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            } else if (!Utils.validarFecha(conductor.getFechaNacimiento(), FORMATO_FECHA)) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                parametro = String.format(parametro, "Fecha nacimiento");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            }
            if (Utils.isNullOrEmpty(conductor.getGrupoSanguineo())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Grupo Sanguineo");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            } else if (!grupoSanguineo.contains(conductor.getGrupoSanguineo())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                parametro = String.format(parametro, "Grupo sanguineo");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            }
            if (Utils.isNullOrEmpty(conductor.getFactorRH())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Factor RH");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            } else if (!(conductor.getFactorRH().equals("positivo") || conductor.getFactorRH().equals("negativo"))) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                parametro = String.format(parametro, "Factor RH");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            } else if (conductor.getFactorRH().equals("positivo")) {
                conductor.setFactorRH(FactorRHEnum.POSITIVO.getPk());
            } else if (conductor.getFactorRH().equals("negativo")) {
                conductor.setFactorRH(FactorRHEnum.NEGATIVO.getPk());
            }
            if (conductor.getFotoConductor() == null || conductor.getFotoConductor().getFoto() == null) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Foto conductor");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            }
        }
    }

    private boolean validarAseguradoras(Conductor conductor, TransaccionRespuesta transaccionRespuesta, Connection conn) throws Exception {
        boolean validar = true;
        Statement statement = conn.createStatement();
        String sql;
        String parametro;
        if (conductor == null) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Datos persona");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        } else {
            if (Utils.isNullOrEmpty(conductor.getEps())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Eps");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validar = false;
            } else {
                int idEps = 0;
                sql = "SELECT ID_EPS FROM SMB_EPS WHERE CODIGO_EPS = '" + conductor.getEps() + "'";
                statement.execute(sql);
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    idEps = rs.getInt(1);
                }
                if (idEps != 0) {
                    conductor.setEps(String.valueOf(idEps));
                } else {
                    parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                    parametro = String.format(parametro, "Eps");
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(parametro);
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                }
            }
            if (Utils.isNullOrEmpty(conductor.getArl())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Arl");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validar = false;
            } else {
                int idArl = 0;
                sql = "SELECT ID_ARL FROM SMB_ARL WHERE CODIGO_ARL = '" + conductor.getArl() + "'";
                statement.execute(sql);
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    idArl = rs.getInt(1);
                }
                if (idArl != 0) {
                    conductor.setArl(String.valueOf(idArl));
                } else {
                    parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                    parametro = String.format(parametro, "ARL");
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(parametro);
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                }
            }
            if (Utils.isNullOrEmpty(conductor.getPensiones())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Pensiones");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validar = false;
            } else {
                int idPensiones = -1;
                sql = "SELECT ID_AFP FROM SMB_AFP WHERE CODIGO_AFP = '" + conductor.getPensiones() + "'";
                statement.execute(sql);
                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    idPensiones = rs.getInt(1);
                }
                if (idPensiones != -1) {
                    conductor.setPensiones(String.valueOf(idPensiones));
                } else {
                    parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                    parametro = String.format(parametro, "Pensiones");
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(parametro);
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                }
            }
        }
        return validar;
    }

    private boolean validarTelefonos(ContactoPersona contacto, TransaccionRespuesta transaccionRespuesta) {
        String parametro;
        if (contacto == null) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Contacto");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            return false;
        } else {
            List<Telefono> telefonos = contacto.getTelefonos();
            if (telefonos == null || telefonos.isEmpty()) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "teléfono");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                return false;
            }
            if (telefonos.get(0) == null || telefonos.get(0).getNumeroTelefono() == 0) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "teléfono");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                return false;
            }
            long telefono = telefonos.get(0).getNumeroTelefono();
            if (!(7 == String.valueOf(telefono).length() || String.valueOf(telefono).length() == 10)) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                parametro = String.format(parametro, "teléfono");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                return false;
            }
        }
        return true;
    }

    private boolean validarDatosUbicacion(ContactoPersona contacto, TransaccionRespuesta transaccionRespuesta) {
        String parametro;
        if (contacto == null) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Contacto");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            return false;
        } else {
            List<UbicacionPersona> ubicaciones = contacto.getUbicaciones();
            if (ubicaciones == null || ubicaciones.isEmpty() || Utils.isNullOrEmpty(ubicaciones.get(0).getDireccion())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "dirección");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                return false;
            }
        }
        return true;
    }

    private boolean validarTarjetaControl(TarjetaControl tarjetaControl, TransaccionRespuesta transaccionRespuesta) {
        String parametro;
        if (tarjetaControl == null) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Tarjeta control");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            return false;
        } else if (Utils.isNullOrEmpty(tarjetaControl.getNumeroTarjetaControl())) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Número tarjeta control");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            return false;
        }
        return true;
    }

    private boolean validarEmpresa(Empresa empresa, UsuarioDTO usuarioDTO, TransaccionRespuesta transaccionRespuesta, Connection conn) throws Exception {
        boolean validacion = true;
        Statement statement = conn.createStatement();
        String sql;
        String parametro;
        if (empresa == null) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Empresa");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validacion = false;
        } else if (empresa.getCodigoEmpresa() == null) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Código empresa");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validacion = false;
        } else {
            long codigoSDM = empresa.getCodigoEmpresa().longValue();
            sql = "SELECT ID_EMPRESA, ID_NIT_EMPRESA FROM SMI_EMPRESA WHERE CODIGO_EMPRESA = " + codigoSDM;
            statement.execute(sql);
            long idEmpresa = 0;
            String nitEmpresa = "";
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                idEmpresa = rs.getLong(1);
                nitEmpresa = rs.getString("ID_NIT_EMPRESA");
            }
            long _idEmpresa = idEmpresa;
            Optional<Long> resultEmpresa = usuarioDTO.getIdEmpresa().stream().filter(id -> id == _idEmpresa).findFirst();
            if (idEmpresa == 0 || resultEmpresa == null || !resultEmpresa.isPresent()) {
                parametro = CodigoRespuesta.ERROR_CODIGO_EMPRESA_NO_COINCIDE.getDescripcionRespuesta();
                parametro = String.format(parametro, "Código empresa SDM");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_CODIGO_EMPRESA_NO_COINCIDE.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validacion = false;
            }
            if (Utils.isNullOrEmpty(empresa.getNumeroIdentificacionTributaria())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Nit empresa");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validacion = false;
            } else if (!nitEmpresa.equals(empresa.getNumeroIdentificacionTributaria())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                parametro = String.format(parametro, "Nit empresa");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validacion = false;
            }
        }
        return validacion;
    }

    private boolean validarPagoPila(List<PagoPILA> pagoPila, TransaccionRespuesta transaccionRespuesta, Connection conn) throws Exception {
        boolean validar = true;
        Statement statement = conn.createStatement();
        String sql;
        String parametro;
        if (pagoPila == null || pagoPila.isEmpty()) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Pago pila");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        } else {
            sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.CANTIDAD_MAX_PLANILLAS.getValue() + "'";
            ResultSet rs = statement.executeQuery(sql);
            Integer cantMaxPlanillas = 1;
            while (rs.next()) {
                cantMaxPlanillas = Integer.parseInt(rs.getString(1));
            }
            
            if (pagoPila.size() > cantMaxPlanillas) {
                parametro = CodigoRespuesta.ERROR_PLANILLAS.getDescripcionRespuesta();
                parametro = String.format(parametro, cantMaxPlanillas.toString());
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PLANILLAS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
                validar = false;
            } else {
                for (PagoPILA pagoPILA : pagoPila) {
                    if (Utils.isNullOrEmpty(pagoPILA.getNumeroAprobacionPagoPILA())) {
                        parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                        parametro = String.format(parametro, "Número aprobaciób pila");
                        ErrorSIRC errorSirc = new ErrorSIRC();
                        errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                        errorSirc.setDescripcionError(parametro);
                        transaccionRespuesta.getListError().add(errorSirc);
                        validar = false;
                    }
                    if (Utils.isNullOrEmpty(pagoPILA.getPeriodoPagoPILA())) {
                        parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                        parametro = String.format(parametro, "Periodo pago pila");
                        ErrorSIRC errorSirc = new ErrorSIRC();
                        errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                        errorSirc.setDescripcionError(parametro);
                        transaccionRespuesta.getListError().add(errorSirc);
                        validar = false;
                    } else if (!Utils.validarFecha(pagoPILA.getPeriodoPagoPILA(), FORMATO_PERIODO)) {
                        parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                        parametro = String.format(parametro, "Periodo pago pila");
                        ErrorSIRC errorSirc = new ErrorSIRC();
                        errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                        errorSirc.setDescripcionError(parametro);
                        transaccionRespuesta.getListError().add(errorSirc);
                        validar = false;
                    } else if (!validarPeriodoPagoSeguridad(pagoPILA.getPeriodoPagoPILA())) {
                        parametro = CodigoRespuesta.ERROR_PERIODO_PAGO_SEGURIDAD.getDescripcionRespuesta();
                        parametro = String.format(parametro, "Periodo pago pila");
                        ErrorSIRC errorSirc = new ErrorSIRC();
                        errorSirc.setCodigoError(CodigoRespuesta.ERROR_PERIODO_PAGO_SEGURIDAD.getCodigoRespuesta());
                        errorSirc.setDescripcionError(parametro);
                        transaccionRespuesta.getListError().add(errorSirc);
                        validar = false;
                    }
                    if (Utils.isNullOrEmpty(pagoPILA.getOperador())) {
                        parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                        parametro = String.format(parametro, "Operador recaudo");
                        ErrorSIRC errorSirc = new ErrorSIRC();
                        errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                        errorSirc.setDescripcionError(parametro);
                        transaccionRespuesta.getListError().add(errorSirc);
                        validar = false;
                    } else {
                        sql = "SELECT ID_OPERADOR FROM SMB_OPERADORES_PILA WHERE CODIGO_OPERADOR = '" + pagoPILA.getOperador() + "'";
                        statement.execute(sql);
                        long idOperador = 0;
                        rs = statement.getResultSet();
                        while (rs.next()) {
                            idOperador = rs.getInt(1);
                        }
                        if (idOperador == 0) {
                            parametro = CodigoRespuesta.ERROR_CODIGO_EMPRESA_NO_COINCIDE.getDescripcionRespuesta();
                            parametro = String.format(parametro, "Operador recaudo");
                            ErrorSIRC errorSirc = new ErrorSIRC();
                            errorSirc.setCodigoError(CodigoRespuesta.ERROR_CODIGO_EMPRESA_NO_COINCIDE.getCodigoRespuesta());
                            errorSirc.setDescripcionError(parametro);
                            transaccionRespuesta.getListError().add(errorSirc);
                            validar = false;
                        } else {
                            pagoPILA.setOperador(String.valueOf(idOperador));
                        }
                    }
                }
            }
                   
        }
        return validar;
    }

    private boolean validarPeriodoPagoSeguridad(String periodoPago) {
        try {
            if (periodoPago != null && !periodoPago.isEmpty()) {
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Calendar fechaSeguridad = Calendar.getInstance();
                fechaSeguridad.setTime(format.parse("01/" + periodoPago));
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
                return !fechaSeguridad.getTime().before(fecha.getTime()) || fechaSeguridad.getTime().equals(fecha.getTime());
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    private void validarVehiculoPlaca(Vehiculo vehiculo, TransaccionRespuesta transaccionRespuesta, Connection conn) throws Exception {
        String parametro;
        if (vehiculo == null) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Vehículo");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
        } else {
        	vehiculo.setPlaca(vehiculo.getPlaca().trim());
            if (Utils.isNullOrEmpty(vehiculo.getPlaca())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Placa");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            } else if (!Utils.validarPlaca(vehiculo.getPlaca())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
                parametro = String.format(parametro, "Placa");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            }
            if (Utils.isNullOrEmpty(vehiculo.getNumeroOrden())) {
                parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                parametro = String.format(parametro, "Número orden");
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                errorSirc.setDescripcionError(parametro);
                transaccionRespuesta.getListError().add(errorSirc);
            }
            validarDatosVehiculo(vehiculo, transaccionRespuesta, conn);
        }
    }

    private boolean validarDatosVehiculo(Vehiculo vehiculo, TransaccionRespuesta transaccionRespuesta, Connection conn) throws Exception {
        boolean validar = true;
        Statement statement = conn.createStatement();
        String sql;
        String parametro;
        if (Utils.isNullOrEmpty(vehiculo.getCobro())) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Método de cobro");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        } else if (!vehiculo.getCobro().equals("1") && !vehiculo.getCobro().equals("2")) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
            parametro = String.format(parametro, "Método de cobro");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        } else if (vehiculo.getCobro().equals("2")) {
            sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.FECHA_FIN_METODO_COBRO_UNIDADES.getValue() + "'";
            ResultSet rs = statement.executeQuery(sql);
            String fechaFin = "";
            while (rs.next()) {
                fechaFin = rs.getString(1);
            }
            if (!Utils.isNullOrEmpty(fechaFin) && Utils.validarFecha(fechaFin, FORMATO_FECHA)) {
                Date fecha = Utils.cadenaAFecha(fechaFin, FORMATO_FECHA);
                if (fecha != null && Calendar.getInstance().getTime().after(fecha)) {
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_METODO_COBRO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(CodigoRespuesta.ERROR_METODO_COBRO.getDescripcionRespuesta());
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                }
            }
        }
        if (Utils.isNullOrEmpty(vehiculo.getTipoServicio())) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Tipo de servicio");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        } else if (!vehiculo.getTipoServicio().equals("1") && !vehiculo.getTipoServicio().equals("2")) {
            parametro = CodigoRespuesta.ERROR_CODIGO_EMPRESA_NO_COINCIDE.getDescripcionRespuesta();
            parametro = String.format(parametro, "Tipo de servicio");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        } else if (vehiculo.getTipoServicio().equals("2")) {
            sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.FECHA_INICIO_NIVEL_SERVICIO_LUJO.getValue() + "'";
            ResultSet rs = statement.executeQuery(sql);
            String fechaInicio = "";
            while (rs.next()) {
                fechaInicio = rs.getString(1);
            }
            if (!Utils.isNullOrEmpty(fechaInicio) && Utils.validarFecha(fechaInicio, FORMATO_FECHA)) {
                Date fecha = Utils.cadenaAFecha(fechaInicio, FORMATO_FECHA);
                if (fecha != null && Calendar.getInstance().getTime().before(fecha)) {
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_TIPO_SERVICIO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(CodigoRespuesta.ERROR_TIPO_SERVICIO.getDescripcionRespuesta());
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                }
            }
        }
        if (Utils.isNullOrEmpty(vehiculo.getNumeroSoat())) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Número SOAT");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        }
        if (Utils.isNullOrEmpty(vehiculo.getVencimientoSoat())) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Vencimiento SOAT");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        } else if (!Utils.validarFecha(vehiculo.getVencimientoSoat(), FORMATO_FECHA)) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
            parametro = String.format(parametro, "Vencimiento SOAT");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        }
        if (Utils.isNullOrEmpty(vehiculo.getNumeroRtm())) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Número RTM");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        }
        if (Utils.isNullOrEmpty(vehiculo.getVencimientoRtm())) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Vencimiento RTM");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        } else if (!Utils.validarFecha(vehiculo.getVencimientoRtm(), FORMATO_FECHA)) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
            parametro = String.format(parametro, "Vencimiento RTM");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        }
        if (Utils.isNullOrEmpty(vehiculo.getNumeroTarjOperacion())) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Número tarjeta operación");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        }
        if (Utils.isNullOrEmpty(vehiculo.getVencimientoTarjOperacion())) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
            parametro = String.format(parametro, "Vencimiento tarjeta operación");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        } else if (!Utils.validarFecha(vehiculo.getVencimientoTarjOperacion(), FORMATO_FECHA)) {
            parametro = CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getDescripcionRespuesta();
            parametro = String.format(parametro, "Vencimiento tarjeta operación");
            ErrorSIRC errorSirc = new ErrorSIRC();
            errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
            errorSirc.setDescripcionError(parametro);
            transaccionRespuesta.getListError().add(errorSirc);
            validar = false;
        }
        return validar;
    }

    private boolean validarDatosVehiculoSIM(Vehiculo vehiculo, Empresa empresa, TransaccionRespuesta transaccionRespuesta) throws Exception {
        boolean validar = false;
        SimurWS_Service service = new SimurWS_Service();
        SimurWS port = service.getSimurWSPort();
        String result = port.consultarSimur("01WSSIMUR", "7287", vehiculo.getPlaca().toUpperCase());
        if (result != null && !result.isEmpty()) {
            validar = true;
            VehiculoSimDTO vehiculoSimDTO = SimUtil.getVehiculoSimDTO(result);
            if (vehiculoSimDTO != null && vehiculoSimDTO.getNroPlaca() != null) {
                if (vehiculoSimDTO.getIdEstado() != 1) {
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.SIM_ERROR_ESTADO_VEHICULO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(CodigoRespuesta.SIM_ERROR_ESTADO_VEHICULO.getDescripcionRespuesta());
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                }
                if (vehiculoSimDTO.getIdServicio() != 2) {
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.SIM_ERROR_SERVICIO_VEHICULO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(CodigoRespuesta.SIM_ERROR_SERVICIO_VEHICULO.getDescripcionRespuesta());
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                }
                if (empresa == null) {
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.SIM_ERROR_EMPRESA_VEHICULO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(CodigoRespuesta.SIM_ERROR_EMPRESA_VEHICULO.getDescripcionRespuesta());
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                } else {
                    String nitEmpresa = empresa.getNumeroIdentificacionTributaria() != null
                            && !empresa.getNumeroIdentificacionTributaria().isEmpty() ? empresa.getNumeroIdentificacionTributaria() : "";
                    nitEmpresa = nitEmpresa.replace("-", "");
                    nitEmpresa = nitEmpresa.length() <= 9 ? nitEmpresa : nitEmpresa.substring(0, 9);
                    String nitSIM = vehiculoSimDTO.getNumeroDocEmpresa() != null
                            && !vehiculoSimDTO.getNumeroDocEmpresa().isEmpty() ? vehiculoSimDTO.getNumeroDocEmpresa() : "";
                    nitSIM = nitSIM.replace("-", "");
                    nitSIM = nitSIM.length() <= 9 ? nitSIM : nitSIM.substring(0, 9);
                    if (!nitEmpresa.equals(nitSIM)) {
                        ErrorSIRC errorSirc = new ErrorSIRC();
                        errorSirc.setCodigoError(CodigoRespuesta.SIM_ERROR_EMPRESA_VEHICULO.getCodigoRespuesta());
                        errorSirc.setDescripcionError(CodigoRespuesta.SIM_ERROR_EMPRESA_VEHICULO.getDescripcionRespuesta());
                        transaccionRespuesta.getListError().add(errorSirc);
                        validar = false;
                    }
                }
                //Se inactiva la validación de TO contra el SIM mientras se valida el ingreso de TO refrendadas
                if (!vehiculoSimDTO.getNroTarjetaOperacion().equals(vehiculo.getNumeroTarjOperacion()) && false) {
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.SIM_ERROR_NRO_TARJETA_OPERACION_VEHICULO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(CodigoRespuesta.SIM_ERROR_NRO_TARJETA_OPERACION_VEHICULO.getDescripcionRespuesta());
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                }
                String fechaVenTO = Utils.fechaACadena(Utils.cadenaAFecha(vehiculoSimDTO.getFechaVenTarjetaOperacion(), FORMATO_FECHA), FORMATO_FECHA);
                //Se inactiva la validación de TO contra el SIM mientras se valida el ingreso de TO refrendadas
                if (!fechaVenTO.equals(vehiculo.getVencimientoTarjOperacion()) && false) {
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.SIM_ERROR_FECHA_VEN_TARJETA_OPERACION_VEHICULO.getCodigoRespuesta());
                    errorSirc.setDescripcionError(CodigoRespuesta.SIM_ERROR_FECHA_VEN_TARJETA_OPERACION_VEHICULO.getDescripcionRespuesta());
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                }
                if (!"Activa".equals(vehiculoSimDTO.getEstadoTarjetaOperacion())) {
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.SIM_ERROR_TARJETA_OPERACION_INACTIVA.getCodigoRespuesta());
                    errorSirc.setDescripcionError(CodigoRespuesta.SIM_ERROR_TARJETA_OPERACION_INACTIVA.getDescripcionRespuesta());
                    transaccionRespuesta.getListError().add(errorSirc);
                    validar = false;
                }
            } else {
                ErrorSIRC errorSirc = new ErrorSIRC();
                errorSirc.setCodigoError(CodigoRespuesta.SIM_ERROR_VEHICULO_NOEXISTE.getCodigoRespuesta());
                errorSirc.setDescripcionError(CodigoRespuesta.SIM_ERROR_VEHICULO_NOEXISTE.getDescripcionRespuesta());
                transaccionRespuesta.getListError().add(errorSirc);
                validar = false;
            }
        }
        return validar;
    }

    /**
     * private boolean validarDispositivosMovil(Vehiculo vehiculo,
     * InfoDispositivos dispositivos, UsuarioDTO usuarioDTO,
     * TransaccionRespuesta transaccionRespuesta, Connection conn) throws
     * Exception { boolean validar = true; Statement statement =
     * conn.createStatement(); String sql; String parametro; if (vehiculo ==
     * null) { parametro =
     * CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
     * parametro = String.format(parametro, "Vehículo"); ErrorSIRC errorSirc =
     * new ErrorSIRC();
     * errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
     * errorSirc.setDescripcionError(parametro);
     * transaccionRespuesta.getListError().add(errorSirc); validar = false; }
     * else if (Utils.isNullOrEmpty(vehiculo.getPlaca())) { parametro =
     * CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
     * parametro = String.format(parametro, "Placa"); ErrorSIRC errorSirc = new
     * ErrorSIRC();
     * errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
     * errorSirc.setDescripcionError(parametro);
     * transaccionRespuesta.getListError().add(errorSirc); validar = false; }
     * else if (Utils.isNullOrEmpty(vehiculo.getCobro())) { parametro =
     * CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
     * parametro = String.format(parametro, "Método de cobro"); ErrorSIRC
     * errorSirc = new ErrorSIRC();
     * errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
     * errorSirc.setDescripcionError(parametro);
     * transaccionRespuesta.getListError().add(errorSirc); validar = false; }
     * else if (!vehiculo.getCobro().equals("1") &&
     * !vehiculo.getCobro().equals("2")) { parametro =
     * CodigoRespuesta.ERROR_CODIGO_EMPRESA_NO_COINCIDE.getDescripcionRespuesta();
     * parametro = String.format(parametro, "Método de cobro"); ErrorSIRC
     * errorSirc = new ErrorSIRC();
     * errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_FORMATO.getCodigoRespuesta());
     * errorSirc.setDescripcionError(parametro);
     * transaccionRespuesta.getListError().add(errorSirc); validar = false; }
     * else if (vehiculo.getCobro().equals("1")) { if (dispositivos == null) {
     * parametro =
     * CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
     * parametro = String.format(parametro, "Información dispositivos");
     * ErrorSIRC errorSirc = new ErrorSIRC();
     * errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
     * errorSirc.setDescripcionError(parametro);
     * transaccionRespuesta.getListError().add(errorSirc); validar = false; }
     * else { if (Utils.isNullOrEmpty(dispositivos.getMacid1()) ||
     * Utils.isNullOrEmpty(dispositivos.getImei1())) { parametro =
     * CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
     * parametro = String.format(parametro, "Información dispositivos: mac1 y/0
     * imei1"); ErrorSIRC errorSirc = new ErrorSIRC();
     * errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
     * errorSirc.setDescripcionError(parametro);
     * transaccionRespuesta.getListError().add(errorSirc); validar = false; }
     * ResultSet rs; sql = "SELECT ID_FACTORCALIDAD FROM
     * SMB_VEHICULO_FACTORCALIDAD WHERE ID_ESTADO = 1 AND ID_EMPRESA =" +
     * usuarioDTO.getIdEmpresa() + " AND NRO_PLACA = '" +
     * vehiculo.getPlaca().toUpperCase() + "'"; statement.execute(sql); long
     * idFactorCalidad = 0; rs = statement.getResultSet(); while (rs.next()) {
     * idFactorCalidad = rs.getInt(1); } if (idFactorCalidad != 0) { sql =
     * "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO =
     * '" +
     * SystemParameters.DISPOSITIVO_MOVIL_REQUERIDO_FACTOR_CALIDAD.getValue() +
     * "'"; rs = statement.executeQuery(sql); String requerido = ""; while
     * (rs.next()) { requerido = rs.getString(1); } if ("true".equals(requerido)
     * && (Utils.isNullOrEmpty(dispositivos.getMacid2()) ||
     * Utils.isNullOrEmpty(dispositivos.getImei3()))) { parametro =
     * CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
     * parametro = String.format(parametro, "Información dispositivos: mac2 y/0
     * imei3"); ErrorSIRC errorSirc = new ErrorSIRC();
     * errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
     * errorSirc.setDescripcionError(parametro);
     * transaccionRespuesta.getListError().add(errorSirc); validar = false; } }
     * } } return validar; }*
     */
    private boolean validarConductorVehiculo(Conductor conductor, TransaccionRespuesta transaccionRespuesta, Connection conn) throws Exception {
        Statement statement = conn.createStatement();
        String sql;
        String parametro;
        ResultSet rs;
        Identificacion identificacion = conductor.getIdentificacion();
        Vehiculo vehiculo = conductor.getVehiculo();
        int idPersona = 0;
        sql = "SELECT ID_PERSONA FROM SMI_PERSONA WHERE TIPO_DOCUMENTO = "
                + identificacion.getTipoIdentificacion() + " AND NUMERO_DOCUMENTO = "
                + new BigDecimal(identificacion.getNumeroIdentificacion());
        statement.execute(sql);
        rs = statement.getResultSet();
        while (rs.next()) {
            idPersona = rs.getInt(1);
        }
        if (idPersona != 0) {
            int idConductor = 0;
            sql = "SELECT ID_CONDUCTOR FROM SMI_CONDUCTOR WHERE ID_PERSONA = " + idPersona;
            statement.execute(sql);
            rs = statement.getResultSet();
            while (rs.next()) {
                idConductor = rs.getInt(1);
            }
            if (idConductor != 0) {
                int idVehiculo = 0;
                sql = "SELECT ID_VEHICULO FROM SMB_CONDUCTOR_VEHICULO WHERE ID_ESTADO IN (1, 2) and ID_CONDUCTOR = "
                        + idConductor + " and PLACA_SERIAL_VEHICULO = '" + vehiculo.getPlaca().toUpperCase() + "'";
                statement.execute(sql);
                rs = statement.getResultSet();
                while (rs.next()) {
                    idVehiculo = rs.getInt(1);
                }
                if (idVehiculo != 0) {
                    parametro = CodigoRespuesta.ERROR_COMBINACION_CONDUCTOR_VEHICULO_EXISTENTE.getDescripcionRespuesta();
                    parametro = String.format(parametro, identificacion.getNumeroIdentificacion(), vehiculo.getPlaca().toUpperCase());
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_COMBINACION_CONDUCTOR_VEHICULO_EXISTENTE.getCodigoRespuesta());
                    errorSirc.setDescripcionError(parametro);
                    transaccionRespuesta.getListError().add(errorSirc);
                    return true;
                }
            }
        }
        return false;
    }

    private String crearTarjetaControl(RegistrarConductorSolicitud solicitud, TransaccionRespuesta transaccionRespuesta) throws Exception {
        Connection conn = getConnection();
        try {
            Statement statement = conn.createStatement();
            for (Conductor conductor : solicitud.getConductor()) {
                String validez = getSystemParam(SystemParameters.VALIDEZ_TARJETA.getValue(), conn);
                String vigencia = getSystemParam(SystemParameters.VIGENCIA_TARJETA.getValue(), conn);
                long idEmpresa = conductor.getEmpresa().getCodigoEmpresa().longValue();
                String sql = "SELECT ID_EMPRESA FROM SMI_EMPRESA WHERE CODIGO_EMPRESA = " + conductor.getEmpresa().getCodigoEmpresa().longValue();
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    idEmpresa = rs.getLong(1);
                }
                
                Calendar validezFecha = Calendar.getInstance();
                validezFecha.add(Calendar.DATE, Integer.valueOf(validez));
                validezFecha.set(Calendar.HOUR_OF_DAY, 23);
                validezFecha.set(Calendar.MINUTE, 59);
                validezFecha.set(Calendar.SECOND, 59);
                validezFecha.set(Calendar.MILLISECOND, 0);
                
                Calendar vigenciaFecha = Calendar.getInstance();
                vigenciaFecha.add(Calendar.DATE, Integer.valueOf(vigencia));
                vigenciaFecha.set(Calendar.HOUR_OF_DAY, 23);
                vigenciaFecha.set(Calendar.MINUTE, 59);
                vigenciaFecha.set(Calendar.SECOND, 59);
                vigenciaFecha.set(Calendar.MILLISECOND, 0);
                Date fechaMenor = validarFechaVigencia(conductor);
                if (vigenciaFecha.getTime().after(fechaMenor)) {
                	vigenciaFecha.setTime(fechaMenor);
                }
                if (validezFecha.getTime().after(fechaMenor)) {
                	validezFecha.setTime(fechaMenor);
                }
                int idPersona = almacenarPersona(conductor, conn);
                int idConductor = almacenarConductor(conductor, idPersona, conn);
                TarjetaControlDTO tarjetaControlDTO = new TarjetaControlDTO();
                tarjetaControlDTO.setNroTarjetaControl(getTarjetaControlNumber(conductor.getVehiculo().getPlaca(),conductor.getIdentificacion().getNumeroIdentificacion()));
                int idVehiculo = almacenarVehiculo(conductor.getVehiculo(), idConductor, idEmpresa, validezFecha, vigenciaFecha, tarjetaControlDTO, conn);
                BufferedImage qrImage = createQR(tarjetaControlDTO.getNroTarjetaControl(), conn);
                //Almacenamiento auditoria
                registrarAuditoria(conductor,idConductor,idEmpresa,EstadoTarjetaControlEnum.EXPEDIDA.getPk(), tarjetaControlDTO.getNroTarjetaControl(), conn);
                almacenarPagoPila(conductor.getPagopila(), idConductor, idVehiculo, idEmpresa, null, vigenciaFecha, conn);
                //almacenarDispositivosMovil(conductor.getInfoDispositivos(), idVehiculo, conn);
                transaccionRespuesta.setImageQR(qrImage);
                transaccionRespuesta.setTarjetaControl(tarjetaControlDTO.getNroTarjetaControl());
                transaccionRespuesta.setFechaValidez(getXMLCalendar(validezFecha.getTime()));
                transaccionRespuesta.setFechaVigencia(getXMLCalendar(vigenciaFecha.getTime()));
                LOG.log(Level.INFO, "########## enviarDuups WS ##########");
                new Thread(() -> enviarDuups(conductor)).start();
            }
            //conn.commit();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            conn.rollback();
        } finally {
            conn.close();
        }
        return TransaccionEstadoEnum.EXITOSO.getPk();
    }

	private Date validarFechaVigencia(Conductor conductor) {
		Date fechaSOAT = setFechaHora(Utils.cadenaAFecha(conductor.getVehiculo().getVencimientoSoat(), FORMATO_FECHA));
		Date fechaRTM = setFechaHora(Utils.cadenaAFecha(conductor.getVehiculo().getVencimientoRtm(), FORMATO_FECHA));
		Date fechaTO = setFechaHora(Utils.cadenaAFecha(conductor.getVehiculo().getVencimientoTarjOperacion(), FORMATO_FECHA));
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

    private String createExternalUserRefrendacion(TransaccionRespuesta transaccionRespuesta, List<TarjetaControlDTO> tarjetasControl) throws NamingException, SQLException, MensajeRegistrarConductorError, Exception {
        Connection conn = null;
        String parametro = null;
        try {
            conn = getConnection();
            for (TarjetaControlDTO tarjetaControlDTO : tarjetasControl) {
                transaccionRespuesta.setTarjetaControl(tarjetaControlDTO.getNroTarjetaControl());
                if (tarjetaControlDTO.getIdVehiculo() != 0 && tarjetaControlDTO.getConductor() != null
                        && tarjetaControlDTO.getConductor().getVehiculo() != null) {
                    Conductor conductor = tarjetaControlDTO.getConductor();
                    Vehiculo vehiculo = conductor.getVehiculo();
                    Statement statement = conn.createStatement();
                    long idPersona = tarjetaControlDTO.getIdPersona();
                    long idConductor = tarjetaControlDTO.getIdConductor();
                    long idEmpresa = tarjetaControlDTO.getIdEmpresa();
                    long idVehiculo = tarjetaControlDTO.getIdVehiculo();
                    String sql = "SELECT FECHA_VIGENCIA  FROM SMB_CONDUCTOR_VEHICULO WHERE TARJETA_CONTROL = '" + tarjetaControlDTO.getNroTarjetaControl() +"'";
                    ResultSet rs = statement.executeQuery(sql);
                    Date vigencia = Calendar.getInstance().getTime();
                    while (rs.next()) {
                        vigencia = rs.getDate(1);
                    }
                    Calendar vigenciaFechaInicial = Calendar.getInstance();
                    vigenciaFechaInicial.setTime(vigencia);
                    if(!validMultiRefrendation(vigenciaFechaInicial)) {
//                    	SimpleDateFormat format = new SimpleDateFormat(FORMATO_FECHA);
//                    	ErrorSIRC errorSirc = new ErrorSIRC();
//                    	errorSirc.setCodigoError(CodigoRespuesta.ERROR_VIGENCIA_AFTER_DATE.getCodigoRespuesta());
//                    	errorSirc.setDescripcionError(CodigoRespuesta.ERROR_VIGENCIA_AFTER_DATE_DETAIL.getDescripcionRespuesta()+ format.format(vigenciaFechaInicial.getTime()));
//                    	transaccionRespuesta.getListError().add(errorSirc);
                    	transaccionRespuesta.setFechaValidez(getXMLCalendar(tarjetaControlDTO.getFechaValidez()));
                        transaccionRespuesta.setFechaVigencia(getXMLCalendar(vigenciaFechaInicial.getTime()));
                    	return TransaccionEstadoEnum.EXITOSO.getPk();
                    }
                    vigenciaFechaInicial.add(Calendar.MONTH, 1);
                    tarjetaControlDTO.setFechaVigencia(vigenciaFechaInicial.getTime());
                    String tarjetaControNumber = getTarjetaControlNumber(
                            tarjetaControlDTO.getConductor().getVehiculo().getPlaca(),
                            tarjetaControlDTO.getConductor().getIdentificacion().getNumeroIdentificacion() );
                    if(!tarjetaControNumber.equals(tarjetaControlDTO.getNroTarjetaControl())) {
                        tarjetaControlDTO.setNroTarjetaControl(tarjetaControNumber);
                        transaccionRespuesta.setTarjetaControl(tarjetaControlDTO.getNroTarjetaControl());
                    }
                    /*if (new Date().after(tarjetaControlDTO.getFechaValidez())) {
                    	parametro = CodigoRespuesta.ERROR_REFRENDACION_ESTADO_5.getDescripcionRespuesta();
                        parametro = String.format(parametro, tarjetaControlDTO.getNroTarjetaControl(), 
                        		Utils.getFormatDate(tarjetaControlDTO.getFechaValidez()));
                        ErrorSIRC errorSirc = new ErrorSIRC();
                        errorSirc.setCodigoError(CodigoRespuesta.ERROR_REFRENDACION_ESTADO_5.getCodigoRespuesta());
                        errorSirc.setDescripcionError(parametro);
                        transaccionRespuesta.getListError().add(errorSirc);
                        return TransaccionEstadoEnum.FALLIDO.getPk();
                    }*/
                    if (tarjetaControlDTO.getFechaVigencia().after(tarjetaControlDTO.getFechaValidez())) {
                        vigenciaFechaInicial.setTime(tarjetaControlDTO.getFechaValidez());
                    }
                    vigenciaFechaInicial.set(Calendar.HOUR_OF_DAY, 23);
                    vigenciaFechaInicial.set(Calendar.MINUTE, 59);
                    vigenciaFechaInicial.set(Calendar.SECOND, 59);
                    vigenciaFechaInicial.set(Calendar.MILLISECOND, 0);
                    Date fechaMenor = validarFechaVigencia(tarjetaControlDTO.getConductor());
                    Calendar fechaMenorCalendar = Calendar.getInstance();
                    fechaMenorCalendar.setTime(fechaMenor);
                    vigenciaFechaInicial = vigenciaFechaInicial.getTime().after(fechaMenor) ? fechaMenorCalendar : vigenciaFechaInicial;
                    sql = "UPDATE SMI_PERSONA SET DIRECCION = '" + conductor.getContacto().getUbicaciones().get(0).getDireccion()
                            + "', CELULAR = " + conductor.getContacto().getTelefonos().get(0).getNumeroTelefono() + " WHERE ID_PERSONA = " + idPersona;
                    statement.executeUpdate(sql);
                    sql = "UPDATE SMI_CONDUCTOR SET ID_ARL = " + conductor.getArl() + ", ID_EPS = " + conductor.getEps() + ", ID_AFP = " + conductor.getPensiones() + " WHERE ID_CONDUCTOR = " + idConductor;
                    statement.executeUpdate(sql);
                    sql = "UPDATE SMI_PAGOPILA_COND_VEHI SET ID_ESTADO = 0 WHERE ID_PAGOPILA_COND_VEHI = " + tarjetaControlDTO.getIdPagoPila();
                    statement.executeUpdate(sql);
                    Calendar vigenciaFechaFinal = Calendar.getInstance();
                    vigenciaFechaFinal.setTime(tarjetaControlDTO.getFechaFinVigenciaPagoPila());
                    vigenciaFechaFinal.add(Calendar.MONTH, 1);
                    almacenarPagoPila(conductor.getPagopila(), Long.valueOf(idConductor).intValue(), Long.valueOf(idVehiculo).intValue(), idEmpresa, vigenciaFechaInicial, vigenciaFechaFinal, conn);
                    /*sql = "SELECT ID_FACTORCALIDAD FROM SMB_VEHICULO_FACTORCALIDAD WHERE ID_ESTADO = 1 AND ID_EMPRESA = " + idEmpresa + " AND NRO_PLACA = '" + vehiculo.getPlaca().toUpperCase() + "'";
                    statement.execute(sql);
                    long idFactorCalidad = 0;
                    rs = statement.getResultSet();
                    while (rs.next()) {
                        idFactorCalidad = rs.getInt(1);
                    }
                    String factorCalidad = idFactorCalidad != 0 ? String.valueOf(idFactorCalidad) : "NULL";*/
                    String factorCalidad = getFactorCalidad(vehiculo, statement);
                    setRTOVigencia(vehiculo,statement);

                    sql = "UPDATE SMB_CONDUCTOR_VEHICULO SET ID_ESTADO = 2, TIPO_TRANSACCION = 2, "
                            + "FECHA_VIGENCIA = TO_TIMESTAMP('" + Utils.getFormatDateWithHour(vigenciaFechaInicial.getTime()) + "', 'dd/MM/yyyy  HH24:mi:ss'), "
                            + "ID_METODOCOBRO = " + Long.valueOf(vehiculo.getCobro()) + ", "
                            + "ID_TIPOSERVICIO = " + Long.valueOf(conductor.getVehiculo().getTipoServicio()) + ", "
                            + "TARJETA_CONTROL = '" + tarjetaControlDTO.getNroTarjetaControl() + "', "
                            + "ID_FACTORCALIDAD = " + factorCalidad + " "
                            + "WHERE ID_VEHICULO = " + idVehiculo;
                    statement.executeUpdate(sql);
                    //Registro de auditoria
                    registrarAuditoria(conductor,Long.valueOf(idConductor).intValue(),idEmpresa,EstadoTarjetaControlEnum.REFRENDADA.getPk(), tarjetaControlDTO.getNroTarjetaControl(), conn);
                    
                    transaccionRespuesta.setFechaValidez(getXMLCalendar(tarjetaControlDTO.getFechaValidez()));
                    transaccionRespuesta.setFechaVigencia(getXMLCalendar(vigenciaFechaInicial.getTime()));
                    BufferedImage qrImage = createQR(tarjetaControlDTO.getNroTarjetaControl(), conn);
                    transaccionRespuesta.setImageQR(qrImage);
                } else {
                    parametro = CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getDescripcionRespuesta();
                    parametro = String.format(parametro, "vehículo y/o conductor");
                    ErrorSIRC errorSirc = new ErrorSIRC();
                    errorSirc.setCodigoError(CodigoRespuesta.ERROR_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
                    errorSirc.setDescripcionError(parametro);
                    transaccionRespuesta.getListError().add(errorSirc);
                    return TransaccionEstadoEnum.FALLIDO.getPk();
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new MensajeRegistrarConductorError("Error refrendación: ", null, e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return TransaccionEstadoEnum.EXITOSO.getPk();
    }

    private void setRTOVigencia(Vehiculo vehiculo, Statement statement) throws SQLException {
        ResultSet rs;
        String sql;
        sql = "SELECT * FROM SMB_RTO_VIGENCIA WHERE  PLACA = '" + vehiculo.getPlaca().toUpperCase() + "' ORDER  BY FECHA_VENCIMIENTO DESC";
        statement.execute(sql);
        rs = statement.getResultSet();
        java.sql.Date fechaVencimiento ;
        Long nroRTO ;
        while (rs.next()) {
            fechaVencimiento = rs.getDate("FECHA_VENCIMIENTO");
            nroRTO = rs.getLong("NRO_TO");
            if(fechaVencimiento != null
                    && LocalDate.now().isBefore(fechaVencimiento.toLocalDate())) {
                vehiculo.setVencimientoTarjOperacion(fechaVencimiento.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                vehiculo.setNumeroTarjOperacion(nroRTO.toString());
                break;
            }

        }


    }

    private String getFactorCalidad(Vehiculo vehiculo, Statement statement) throws SQLException {
        ResultSet rs;
        String sql;
        sql = "SELECT COUNT(1) FROM SMB_RTO_VIGENCIA WHERE  PLACA = '" + vehiculo.getPlaca().toUpperCase() + "' GROUP BY PLACA";
        statement.execute(sql);
        long idFactorCalidad = 0;
        rs = statement.getResultSet();
        while (rs.next()) {
            idFactorCalidad = rs.getInt(1);
        }
        String factorCalidad = idFactorCalidad == 0 ? "null" : "1";
        return factorCalidad;
    }

    private boolean validMultiRefrendation(Calendar vigencia) {
    	Calendar valid = Calendar.getInstance();
    	valid.setTime(vigencia.getTime());
    	valid.add(Calendar.DATE, -15);
        return valid.before(Calendar.getInstance());
	}
    
	private void almacenarAuditoriaRefrendacion(Long idVehiculoCertificado)
			throws MensajeRegistrarConductorError, SQLException {

		Connection conn = null;
		String sql;
		try {
			conn = getConnection();
			Statement statement = conn.createStatement();
			if (idVehiculoCertificado != null) {
				sql = "INSERT INTO SMB_REFRENDACION_HISTORICO (FECHA_REFRENDACION,ID_VEHICULO) "
						+ "VALUES ( " + "TO_TIMESTAMP('" + Utils.getFormatDateWithHour(new Date())
						+ "', 'dd/MM/yyyy  HH24:mi:ss'), " + idVehiculoCertificado + " ) ";
				System.out.println(sql);
				statement.executeUpdate(sql);
			}

		} catch (SQLException | NamingException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			throw new MensajeRegistrarConductorError("Error refrendación: ", null, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

	}

    private String cancelarTarjeta(TransaccionRespuesta transaccionRespuesta, List<TarjetaControlDTO> tarjetasControl) throws MensajeRegistrarConductorError, SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            for (TarjetaControlDTO tarjetaControlDTO : tarjetasControl) {
                if (tarjetaControlDTO != null && tarjetaControlDTO.getIdVehiculo() != 0) {
                    Statement statement = conn.createStatement();
                    String sql = "UPDATE SMB_CONDUCTOR_VEHICULO SET TIPO_TRANSACCION = 3, ID_ESTADO = " + EstadoTarjetaControlEnum.CANCELADO.getPk() + " WHERE ID_VEHICULO = " + tarjetaControlDTO.getIdVehiculo();
                    statement.executeUpdate(sql);
                    //Registro de auditoria
                    registrarAuditoria(tarjetaControlDTO.getConductor(),Long.valueOf(tarjetaControlDTO.getIdConductor()).intValue(),tarjetaControlDTO.getIdEmpresa(),EstadoTarjetaControlEnum.CANCELADO.getPk(), tarjetaControlDTO.getNroTarjetaControl(), conn);
                    transaccionRespuesta.setTarjetaControl(tarjetaControlDTO.getNroTarjetaControl());
                }
            }
            //conn.commit();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            if (conn != null) {
                conn.rollback();
            }
            throw new MensajeRegistrarConductorError("", null, e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return TransaccionEstadoEnum.EXITOSO.getPk();
    }

    private int almacenarPersona(Conductor conductor, Connection conn) throws Exception {
        Statement statement = conn.createStatement();
        int idPersona = 0;
        Identificacion identificacion = conductor.getIdentificacion();
        ContactoPersona contacto = conductor.getContacto();
        String sql = "SELECT ID_PERSONA FROM SMI_PERSONA WHERE TIPO_DOCUMENTO = "
                + identificacion.getTipoIdentificacion() + " AND NUMERO_DOCUMENTO = "
                + new BigDecimal(identificacion.getNumeroIdentificacion());
        statement.execute(sql);
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            idPersona = rs.getInt(1);
        }
        String nombres = conductor.getPrimerNombre() + " " + (conductor.getSegundoNombre()!=null?conductor.getSegundoNombre():"");
        nombres = nombres.trim();
        String apellidos = conductor.getPrimerApellido() + " " + (conductor.getSegundoApellido()!=null?conductor.getSegundoApellido():"");
        apellidos = apellidos.trim();
        if (idPersona == 0) {
            idPersona = Long.valueOf(nextVal("sq_persona.nextval", conn)).intValue();
            sql = "INSERT INTO SMI_PERSONA (ID_PERSONA, NOMBRES, APELLIDOS, NUMERO_DOCUMENTO, CELULAR, DIRECCION, TIPO_DOCUMENTO, ID_ESTADO, FECHA_NACIMIENTO, FECHA_EXPEDICION_DOCUMENTO) VALUES ( "
                    + idPersona + ", '" + nombres + "', '" + apellidos + "', "
                    + new BigDecimal(identificacion.getNumeroIdentificacion()) + ", "
                    + new BigDecimal(contacto.getTelefonos().get(0).getNumeroTelefono()) + ", '"
                    + contacto.getUbicaciones().get(0).getDireccion() + "', " + identificacion.getTipoIdentificacion() + ", 0"
                    + ", TO_TIMESTAMP('" + conductor.getFechaNacimiento() + "','dd/MM/yyyy')"
                    + ", TO_TIMESTAMP('" + identificacion.getFechaExpedicionDoc() + "','dd/MM/yyyy'))";
            statement.executeUpdate(sql);
        } else {
            sql = "UPDATE SMI_PERSONA SET DIRECCION = '"
                    + contacto.getUbicaciones().get(0).getDireccion()
                    + "', CELULAR = " + new BigDecimal(contacto.getTelefonos().get(0).getNumeroTelefono())
                    + ", NOMBRES = '" + nombres
                    + "', APELLIDOS = '" + apellidos
                    + "' WHERE ID_PERSONA = " + idPersona;
            statement.executeUpdate(sql);
        }
        return idPersona;
    }

    private int almacenarConductor(Conductor conductor, int idPersona, Connection conn) throws Exception {
        Statement statement = conn.createStatement();
        String sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.URI_IMG.getValue() + "'";
        ResultSet rs = statement.executeQuery(sql);
        String rutaImg = "";
        while (rs.next()) {
            rutaImg = rs.getString(1);
        }
        int idConductor = 0;
        sql = "SELECT ID_CONDUCTOR FROM SMI_CONDUCTOR WHERE ID_PERSONA = " + idPersona;
        statement.execute(sql);
        rs = statement.getResultSet();
        while (rs.next()) {
            idConductor = rs.getInt(1);
        }
        String imgIdentifier = String.valueOf(System.currentTimeMillis());
        String filePath = rutaImg + imgIdentifier + ".jpg";
        java.awt.Image image = conductor.getFotoConductor() != null
                && conductor.getFotoConductor().getFoto() != null ? conductor.getFotoConductor().getFoto() : null;
        if (image != null) {
            File outputfile = new File(filePath);
            ImageIO.write(toBufferedImage(image), "jpg", outputfile);
        }
        if (idConductor == 0) {
            idConductor = Long.valueOf(nextVal("sq_conductor.nextval", conn)).intValue();
            sql = "INSERT INTO SMI_CONDUCTOR (ID_CONDUCTOR, GRUPO_SANGUINEO, FACTOR_RH, ID_AFP, ID_EPS, ID_ARL, ID_PERSONA , RUTA_FOTO) VALUES (" + idConductor + ", '"
                    + conductor.getGrupoSanguineo() + "', '" + conductor.getFactorRH() + "', "
                    + new BigDecimal(conductor.getPensiones()) + ", "
                    + new BigDecimal(conductor.getEps()) + ", "
                    + new BigDecimal(conductor.getArl()) + ", " + idPersona + ", '" + imgIdentifier + "')";
            statement.executeUpdate(sql);
        } else {
            sql = "UPDATE SMI_CONDUCTOR SET ID_ARL = " + new BigDecimal(conductor.getArl())
                    + ", ID_EPS = " + new BigDecimal(conductor.getEps())
                    + ", ID_AFP = " + new BigDecimal(conductor.getPensiones())
                    + ", RUTA_FOTO = '" + imgIdentifier
                    + "' WHERE ID_CONDUCTOR = " + idConductor;
            statement.executeUpdate(sql);
        }
        return idConductor;
    }

    private int almacenarVehiculo(Vehiculo vehiculo, int idConductor, long idEmpresa, Calendar validezFecha, Calendar vigenciaFecha, TarjetaControlDTO tarjetaControlDTO, Connection conn) throws Exception {
        int idVehiculo = 0;
        Statement statement = conn.createStatement();
        String sql;
        /*String sql = "SELECT ID_FACTORCALIDAD FROM SMB_VEHICULO_FACTORCALIDAD WHERE ID_ESTADO = 1 AND ID_EMPRESA =" + idEmpresa + " AND NRO_PLACA = '" + vehiculo.getPlaca().toUpperCase() + "'";
        statement.execute(sql);
        long idFactorCalidad = 0;
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            idFactorCalidad = rs.getInt(1);
        }
        String factorCalidad = idFactorCalidad != 0 ? ", ID_FACTORCALIDAD" : "";*/
        String factorCalidad = getFactorCalidad(vehiculo, statement);
        setRTOVigencia(vehiculo,statement);
        idVehiculo = Long.valueOf(nextVal("sq_vehiculo.nextval", conn)).intValue();
        if (vigenciaFecha.after(validezFecha)) {
            vigenciaFecha = validezFecha;
        }
        sql = "INSERT INTO SMB_CONDUCTOR_VEHICULO "
                + "(ID_EMPRESA, ID_VEHICULO, ID_CONDUCTOR, TARJETA_CONTROL, PLACA_SERIAL_VEHICULO, FECHA_VALIDEZ, FECHA_VIGENCIA, FECHA_EXPEDICION, ID_ESTADO, "
                + "ID_METODOCOBRO, ID_TIPOSERVICIO, FECHA_VENCIMIENTO_SOAT, NRO_SOAT, FECHA_VENCIMIENTO_RTM, NRO_RTM, FECHA_VENCIMIENTO_TO, NRO_TARJETA_OPERACION, TIPO_TRANSACCION, ID_FACTORCALIDAD) "
                + "VALUES (" + idEmpresa + ", " + idVehiculo + ", " + idConductor + ", '" + tarjetaControlDTO.getNroTarjetaControl() + "', '" + vehiculo.getPlaca().toUpperCase() + "', "
                + "TO_TIMESTAMP('" + Utils.getFormatDateWithHour(validezFecha.getTime()) + "', 'dd/MM/yyyy HH24:mi:ss'), "
                + "TO_TIMESTAMP('" + Utils.getFormatDateWithHour(vigenciaFecha.getTime()) + "', 'dd/MM/yyyy HH24:mi:ss'), "
                + "TO_TIMESTAMP('" + Utils.getFormatDateWithHour(new Date()) + "', 'dd/MM/yyyy HH24:mi:ss'), 1, "
                + vehiculo.getCobro() + ", " + vehiculo.getTipoServicio() + ", "
                + "TO_TIMESTAMP('" + vehiculo.getVencimientoSoat() + "', 'dd/MM/yyyy'), '" + vehiculo.getNumeroSoat() + "', "
                + "TO_TIMESTAMP('" + vehiculo.getVencimientoRtm() + "', 'dd/MM/yyyy'), '" + vehiculo.getNumeroRtm() + "', "
                + "TO_TIMESTAMP('" + vehiculo.getVencimientoTarjOperacion() + "', 'dd/MM/yyyy'), '" + vehiculo.getNumeroTarjOperacion() + "', 1"
                + ", " + factorCalidad + ")";
        statement.executeUpdate(sql);
        return idVehiculo;
    }

    private void almacenarPagoPila(List<PagoPILA> pagoPila, int idConductor, int idVehiculo, long idEmpresa, Calendar vigenciaInicio, Calendar vigenciaFecha, Connection conn) throws Exception {
        Statement statement = conn.createStatement();
        for (PagoPILA pagoPILA : pagoPila) {
            Long periodoPago = Long.valueOf(pagoPILA.getPeriodoPagoPILA().replace("/", ""));
            if (vigenciaInicio == null) {
                vigenciaInicio = Calendar.getInstance();
            }
            String sql = "INSERT INTO SMI_PAGOPILA_COND_VEHI"
                    + " (ID_PAGOPILA_COND_VEHI, FECHA_INICIO_VIGENCIA, FECHA_FIN_VIGENCIA, ID_EMPRESA, ID_CONDUCTOR, ID_VEHICULO, NRO_APROBACION_PAGO, ID_ESTADO, PERIODO_PAGO_PILA, ID_OPERADOR)"
                    + " VALUES (sq_pago_pila.nextval, TO_TIMESTAMP('" + Utils.getFormatDate(vigenciaInicio.getTime()) + "', 'dd/MM/yyyy'),"
                    + " TO_TIMESTAMP('" + Utils.getFormatDate(vigenciaFecha.getTime()) + "', 'dd/MM/yyyy'), "
                    + idEmpresa + ", " + idConductor + ", " + idVehiculo + ", '" + pagoPILA.getNumeroAprobacionPagoPILA() + "', 1, "
                    + periodoPago + ", " + pagoPILA.getOperador() + ")";
            statement.executeUpdate(sql);
        }
        
    }

    /**
     * private void almacenarDispositivosMovil(InfoDispositivos dispositivos,
     * int idVehiculo, Connection conn) throws Exception { if (dispositivos !=
     * null) { Statement statement = conn.createStatement(); String sqlDispUno =
     * ""; String sqlDispDos = ""; String dispUno = ""; String dispDos = ""; if
     * (!Utils.isNullOrEmpty(dispositivos.getMacid1())) { sqlDispUno += ",
     * MACID_UNO"; dispUno += ", '" + dispositivos.getMacid1() + "'"; } if
     * (!Utils.isNullOrEmpty(dispositivos.getImei1())) { sqlDispUno += ",
     * IMEI_UNO"; dispUno += ", '" + dispositivos.getImei1() + "'"; } if
     * (!Utils.isNullOrEmpty(dispositivos.getImei2())) { sqlDispUno += ",
     * IMEI_DOS"; dispUno += ", '" + dispositivos.getImei2() + "'"; } if
     * (!Utils.isNullOrEmpty(dispositivos.getMacid2())) { sqlDispDos += ",
     * MACID_DOS"; dispDos += ", '" + dispositivos.getMacid2() + "'"; } if
     * (!Utils.isNullOrEmpty(dispositivos.getImei3())) { sqlDispDos += ",
     * IMEI_TRES"; dispDos += ", '" + dispositivos.getImei3() + "'"; } if
     * (!Utils.isNullOrEmpty(dispositivos.getImei4())) { sqlDispDos += ",
     * IMEI_CUATRO"; dispDos += ", '" + dispositivos.getImei4() + "'"; } String
     * sql = "INSERT INTO SMB_DISPOSITIVO_MOVIL_VEHICULO (ID_DISPOSITIVO,
     * ID_VEHICULO" + sqlDispUno + sqlDispDos + ")" + "VALUES
     * (SQ_DISPOSITIVO_MOVIL_VEHICULO.nextval, " + idVehiculo + dispUno +
     * dispDos + ")"; statement.executeUpdate(sql); } }
     */
    /**
     * private void actualizarDispositivosMovil(InfoDispositivos dispositivos,
     * int idVehiculo, Connection conn) throws Exception { if (dispositivos !=
     * null) { String sqlDispUno = ""; String sqlDispDos = ""; if
     * (!Utils.isNullOrEmpty(dispositivos.getMacid1())) { sqlDispUno +=
     * "MACID_UNO = '" + dispositivos.getMacid1() + "'"; } if
     * (!Utils.isNullOrEmpty(dispositivos.getImei1())) { if
     * (!sqlDispUno.isEmpty()) { sqlDispUno += ", "; } sqlDispUno += "IMEI_UNO =
     * '" + dispositivos.getImei1() + "'"; } if
     * (!Utils.isNullOrEmpty(dispositivos.getImei2())) { if
     * (!sqlDispUno.isEmpty()) { sqlDispUno += ", "; } sqlDispUno += "IMEI_DOS =
     * '" + dispositivos.getImei2() + "'"; } if
     * (!Utils.isNullOrEmpty(dispositivos.getMacid2())) { if
     * (!sqlDispUno.isEmpty()) { sqlDispDos += ", "; } sqlDispDos += "MACID_DOS
     * = '" + dispositivos.getMacid2() + "'"; } if
     * (!Utils.isNullOrEmpty(dispositivos.getImei3())) { if
     * (!sqlDispDos.isEmpty()) { sqlDispDos += ", "; } sqlDispDos += "IMEI_TRES
     * = '" + dispositivos.getImei3() + "'"; } if
     * (!Utils.isNullOrEmpty(dispositivos.getImei4())) { if
     * (!sqlDispDos.isEmpty()) { sqlDispDos += ", "; } sqlDispDos +=
     * "IMEI_CUATRO = '" + dispositivos.getImei4() + "'"; } if
     * (!sqlDispUno.isEmpty() || !sqlDispDos.isEmpty()) { Statement statement =
     * conn.createStatement(); String sql = "UPDATE
     * SMB_DISPOSITIVO_MOVIL_VEHICULO SET " + sqlDispUno + sqlDispDos + " WHERE
     * ID_VEHICULO = " + idVehiculo; statement.executeUpdate(sql); } } }
     */
    /**
     * Enviar datos DUUPS
     *
     * @param conductor
     */
    private void enviarDuups(Conductor conductor) {
        Connection conn = null;
        try {
            conn = getConnection();
            Statement statement = conn.createStatement();
            String sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.ENDPOINT_DUUPS.getValue() + "'";
            ResultSet rs = statement.executeQuery(sql);
            String endpointDuups = "";
            while (rs.next()) {
                endpointDuups = rs.getString(1);
            }
            LOG.log(Level.INFO, ("endpointDuups: " + endpointDuups));
            sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.CABECERA_USUARIO_DUUPS.getValue() + "'";
            rs = statement.executeQuery(sql);
            String usuario = "";
            while (rs.next()) {
                usuario = rs.getString(1);
            }
            sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.CABECERA_PASSWORD_DUUPS.getValue() + "'";
            rs = statement.executeQuery(sql);
            String password = "";
            while (rs.next()) {
                password = rs.getString(1);
            }
            sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.CABECERA_IPCLIENTE_DUUPS.getValue() + "'";
            rs = statement.executeQuery(sql);
            String ipCliente = "";
            while (rs.next()) {
                ipCliente = rs.getString(1);
            }
            LOG.log(Level.INFO, ("ipCliente: " + ipCliente));
            NotificacionPersona wsRequest = new NotificacionPersona();
            String homologaDuups = "";
            sql = "SELECT HOMOLOGA_DUUPS FROM SMB_TIPO_DOCUMENTO WHERE ID_TIPO_DOCUMENTO = '" + conductor.getIdentificacion().getTipoIdentificacion() + "'";
            statement.execute(sql);
            rs = statement.getResultSet();
            while (rs.next()) {
                homologaDuups = rs.getString(1);
            }
            LOG.log(Level.INFO, ("homologaDuups: " + homologaDuups));
            wsRequest.setOTipoDocumento(homologaDuups);
            wsRequest.setONumeroDocumento(conductor.getIdentificacion().getNumeroIdentificacion());
            Direccion d = new Direccion();
            ContactoPersona contacto = conductor.getContacto();
            if (contacto.getUbicaciones() != null) {
                List<UbicacionPersona> ubicaciones = contacto.getUbicaciones();
                d.setODireccion(ubicaciones.get(0).getDireccion());
            }
            sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.MUNICIPIO_DUUPS.getValue() + "'";
            rs = statement.executeQuery(sql);
            String municipioDuups = "";
            while (rs.next()) {
                municipioDuups = rs.getString(1);
            }
            LOG.log(Level.INFO, ("municipioDuups: " + municipioDuups));
            sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.DEPARTAMENTO_DUUPS.getValue() + "'";
            rs = statement.executeQuery(sql);
            String departamentoDuups = "";
            while (rs.next()) {
                departamentoDuups = rs.getString(1);
            }
            LOG.log(Level.INFO, ("departamentoDuups: " + departamentoDuups));
            sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + SystemParameters.CODIGO_ORIGEN_SIRC_DUUPS.getValue() + "'";
            rs = statement.executeQuery(sql);
            String codigoOrigenDuups = "";
            while (rs.next()) {
                codigoOrigenDuups = rs.getString(1);
            }
            LOG.log(Level.INFO, ("codigoOrigenDuups: " + codigoOrigenDuups));
            d.setODepartamento(departamentoDuups);
            d.setOMunicipioCiudad(municipioDuups);
            wsRequest.getODireccion().add(d);
            Email oemail = new Email();
            wsRequest.getOEmail().add(oemail);
            List<co.gov.movilidadbogota.servicioreceptorpersonaduups.Telefono> listOTelefono = new ArrayList<>();
            co.gov.movilidadbogota.servicioreceptorpersonaduups.Telefono otelefono = new co.gov.movilidadbogota.servicioreceptorpersonaduups.Telefono();
            if (conductor.getContacto() != null) {
                ContactoPersona contactoP = conductor.getContacto();
                if (contactoP.getTelefonos() != null) {
                    List<Telefono> telefonos = contactoP.getTelefonos();
                    if (telefonos.get(0).getNumeroTelefono() != 0) {
                        otelefono.setOTelefono(String.valueOf(telefonos.get(0).getNumeroTelefono()));
                        otelefono.setOTipoTelefono("1");
                        listOTelefono.add(otelefono);
                    }
                }
            }
            wsRequest.getOTelefono().addAll(listOTelefono);
            PersonaNatural oPersonaNatural = new PersonaNatural();
            String nombres = conductor.getPrimerNombre() + " " + (conductor.getSegundoNombre()!=null?conductor.getSegundoNombre():"");
            String apellidos = conductor.getPrimerApellido() + " " + (conductor.getSegundoApellido()!=null?conductor.getSegundoApellido():"");
            oPersonaNatural.setOPrimerNombre(nombres);
            oPersonaNatural.setOPrimerApellido(apellidos);
            wsRequest.setOPersonaNatural(oPersonaNatural);
            wsRequest.setOAutorizacionContactoEmail("S");
            wsRequest.setOOrigen(codigoOrigenDuups);
            ConfirmacionRecibo response = null;
            LOG.log(Level.INFO, "########## Creando WS DUUPS ##########");
            ServicioReceptorPersonaDUUPSService service = new ServicioReceptorPersonaDUUPSService(new URL(endpointDuups));
            ServicioReceptorPersona receptorPersona = service.getServicioReceptorPersonaDUUPSPort();
            Map<String, Object> req_ctx = ((BindingProvider) receptorPersona).getRequestContext();
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Usuario", Collections.singletonList(usuario));
            headers.put("Password", Collections.singletonList(password));
            headers.put("IpCliente", Collections.singletonList(ipCliente));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            LOG.log(Level.INFO, "########## inicio llamado DUUPS ##########");
            response = receptorPersona.registroPersonaUbicabilidad(wsRequest);
            receptorPersona.testAutenticacion();
            
            if (response.getODetalle() != null) {
                LOG.log(Level.INFO, "Finalizo llamado a web service");
                for (ErrorRespuesta error : response.getODetalle()) {
                    LOG.log(Level.INFO, ("Codigo error : " + error.getOCodigoError() + " Mensaje: " + error.getOMensajeError()));
                }
            } else {
                LOG.log(Level.INFO, ("Finalizo llamado a web service exitoso " + response.getOCodigo()));
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    private TarjetaControlDTO buscarPorTarjetaControlRef(String tarjeta, long codigoSDM, String tipoDOc, String nroDoc, Connection conn) throws Exception {
        boolean existe = false;
        TarjetaControlDTO tarjetaControlDTO = new TarjetaControlDTO();
        Statement statement = conn.createStatement();
        String sql = "SELECT CV.ID_VEHICULO, CV.FECHA_VALIDEZ, CV.FECHA_VIGENCIA, CV.TARJETA_CONTROL, CV.ID_CONDUCTOR, CV.ID_FACTORCALIDAD, CV.ID_EMPRESA, C.ID_PERSONA, PP.ID_PAGOPILA_COND_VEHI, PP.FECHA_FIN_VIGENCIA "
                + "FROM SMB_CONDUCTOR_VEHICULO CV, SMI_CONDUCTOR C, SMI_PAGOPILA_COND_VEHI PP, SMI_EMPRESA E, SMI_PERSONA P "
                + "WHERE CV.ID_CONDUCTOR = C.ID_CONDUCTOR "
                + "AND C.ID_PERSONA = P.ID_PERSONA "
                + "AND CV.ID_VEHICULO = PP.ID_VEHICULO "
                + "AND CV.ID_CONDUCTOR = PP.ID_CONDUCTOR "
                + "AND CV.ID_EMPRESA = E.ID_EMPRESA "
                + "AND PP.ID_ESTADO = 1 AND CV.ID_ESTADO IN (1, 2) "
                + "AND P.TIPO_DOCUMENTO = " + tipoDOc + " AND NUMERO_DOCUMENTO = " + nroDoc + " "
                + "AND CV.TARJETA_CONTROL = '" + tarjeta + "' AND E.CODIGO_EMPRESA = " + codigoSDM;
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
            existe = true;
            tarjetaControlDTO.setIdVehiculo(rs.getLong("ID_VEHICULO"));
            tarjetaControlDTO.setFechaValidez(rs.getDate("FECHA_VALIDEZ"));
            tarjetaControlDTO.setFechaVigencia(rs.getDate("FECHA_VIGENCIA"));
            tarjetaControlDTO.setNroTarjetaControl(rs.getString("TARJETA_CONTROL"));
            tarjetaControlDTO.setIdConductor(rs.getLong("ID_CONDUCTOR"));
            tarjetaControlDTO.setFactorCalidad(rs.getLong("ID_FACTORCALIDAD"));
            tarjetaControlDTO.setIdPersona(rs.getLong("ID_PERSONA"));
            tarjetaControlDTO.setIdPagoPila(rs.getLong("ID_PAGOPILA_COND_VEHI"));
            tarjetaControlDTO.setFechaFinVigenciaPagoPila(rs.getDate("FECHA_FIN_VIGENCIA"));
            tarjetaControlDTO.setIdEmpresa(rs.getLong("ID_EMPRESA"));
        }
        return existe ? tarjetaControlDTO : null;
    }

    private TarjetaControlDTO buscarPorTarjetaControl(String tarjeta, long codigoSDM, Connection conn) throws Exception {
        boolean existe = false;
        TarjetaControlDTO tarjetaControlDTO = new TarjetaControlDTO();
        Statement statement = conn.createStatement();
        String sql = "SELECT CV.ID_VEHICULO, CV.FECHA_VALIDEZ, CV.FECHA_VIGENCIA, CV.TARJETA_CONTROL, CV.ID_CONDUCTOR, CV.ID_FACTORCALIDAD, CV.ID_EMPRESA, C.ID_PERSONA, PP.ID_PAGOPILA_COND_VEHI, PP.FECHA_FIN_VIGENCIA "
                + "FROM SMB_CONDUCTOR_VEHICULO CV, SMI_CONDUCTOR C, SMI_PAGOPILA_COND_VEHI PP, SMI_EMPRESA E "
                + "WHERE CV.ID_CONDUCTOR = C.ID_CONDUCTOR AND CV.ID_VEHICULO = PP.ID_VEHICULO AND CV.ID_CONDUCTOR = PP.ID_CONDUCTOR AND CV.ID_EMPRESA = E.ID_EMPRESA "
                + "AND PP.ID_ESTADO = 1 AND CV.ID_ESTADO IN (1, 2, 5) "
                + "AND CV.TARJETA_CONTROL = '" + tarjeta + "' AND E.CODIGO_EMPRESA = " + codigoSDM;
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
            existe = true;
            tarjetaControlDTO.setIdVehiculo(rs.getLong("ID_VEHICULO"));
            tarjetaControlDTO.setFechaValidez(rs.getDate("FECHA_VALIDEZ"));
            tarjetaControlDTO.setFechaVigencia(rs.getDate("FECHA_VIGENCIA"));
            tarjetaControlDTO.setNroTarjetaControl(rs.getString("TARJETA_CONTROL"));
            tarjetaControlDTO.setIdConductor(rs.getLong("ID_CONDUCTOR"));
            tarjetaControlDTO.setFactorCalidad(rs.getLong("ID_FACTORCALIDAD"));
            tarjetaControlDTO.setIdPersona(rs.getLong("ID_PERSONA"));
            tarjetaControlDTO.setIdPagoPila(rs.getLong("ID_PAGOPILA_COND_VEHI"));
            tarjetaControlDTO.setFechaFinVigenciaPagoPila(rs.getDate("FECHA_FIN_VIGENCIA"));
            tarjetaControlDTO.setIdEmpresa(rs.getLong("ID_EMPRESA"));
        }
        return existe ? tarjetaControlDTO : null;
    }

    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            // Return image unchanged if it is already a BufferedImage.
            return (BufferedImage) image;
        }

        // Ensure image is loaded.
        image = new ImageIcon(image).getImage();

        int type = hasAlpha(image) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }

    /**
     * Determines if an image has an alpha channel.
     *
     * @param image the <code>Image</code>
     * @return true if the image has an alpha channel
     */
    private static boolean hasAlpha(Image image) {
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return pg.getColorModel().hasAlpha();
    }

    private XMLGregorianCalendar getXMLCalendar(Date date) throws Exception {
        try {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(date);
            XMLGregorianCalendar gregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            return gregorianCalendar;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    private boolean autenticarCliente(MessageContext mctx, UsuarioDTO usuarioDTO, ErrorSIRC errorSirc) {
        boolean usuarioAutenticado = false;
        Connection conn = null;
        try {
            conn = getConnection();
            Statement statement = conn.createStatement();
            StringBuilder sql = new StringBuilder();
            Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
            List userList = (List) http_headers.get("Username");
            List passList = (List) http_headers.get("Password");
            LOG.info(userList.toString());
            String password = "";
            if (userList != null) {
                username = userList.get(0).toString();
            }
            if (passList != null) {
                password = passList.get(0).toString();
            }
            sql.append("SELECT U.ID_USUARIO, U.ID_PERSONA, EU.ID_EMPRESA, U.ID_ESTADO, U.ID_TIPO_USUARIO, U.ID_PERFIL ");
            sql.append("FROM SMI_USUARIO U, SMI_EMPRESA_USUARIO EU ");
            sql.append("WHERE U.ID_USUARIO = EU.ID_USUARIO AND LOGIN_USUARIO = '");
            sql.append(username);
            sql.append("' AND CLAVE_USUARIO = '");
            sql.append(Utils.claveUsuario(password));
            sql.append("'");
            ResultSet rs = statement.executeQuery(sql.toString());
            while (rs.next()) {
                usuarioDTO.setIdUser(rs.getLong("ID_USUARIO"));
                usuarioDTO.setIdPersona(rs.getLong("ID_PERSONA"));
                usuarioDTO.getIdEmpresa().add(rs.getLong("ID_EMPRESA"));
                usuarioDTO.setIdEstado(rs.getLong("ID_ESTADO"));
                usuarioDTO.setIdTipoUsuario(rs.getLong("ID_TIPO_USUARIO"));
                usuarioDTO.setIdPerfil(rs.getLong("ID_PERFIL"));
                usuarioAutenticado = true;
            }
            if (!usuarioAutenticado) {
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_AUTENTICACION_USUARIO.getCodigoRespuesta());
                errorSirc.setDescripcionError(CodigoRespuesta.ERROR_AUTENTICACION_USUARIO.getDescripcionRespuesta());
            } else if (usuarioDTO.getIdEstado() != 1) {
                usuarioDTO.setIdUser(-1);
                usuarioAutenticado = false;
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_AUTENTICACION_USUARIO_ESTADO.getCodigoRespuesta());
                errorSirc.setDescripcionError(CodigoRespuesta.ERROR_AUTENTICACION_USUARIO_ESTADO.getDescripcionRespuesta());
            } else if (usuarioDTO.getIdTipoUsuario() != 1 || usuarioDTO.getIdTipoUsuario() != 1) {
                usuarioDTO.setIdUser(-1);
                usuarioAutenticado = false;
                errorSirc.setCodigoError(CodigoRespuesta.ERROR_AUTENTICACION_TIPO_USUARIO.getCodigoRespuesta());
                errorSirc.setDescripcionError(CodigoRespuesta.ERROR_AUTENTICACION_TIPO_USUARIO.getDescripcionRespuesta());
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        return usuarioAutenticado;
    }

    /**
     * Obtiene el id del registro a almacenar
     *
     * @param secuencia
     * @param conn
     * @return
     * @throws Exception
     */
    private String nextVal(String secuencia, Connection conn) throws Exception {
        String id = "";
        Statement statement = conn.createStatement();
        String sql = "SELECT " + secuencia + " FROM DUAL";
        statement.execute(sql);
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            id = rs.getString("NEXTVAL");
        }
        return id;
    }

    /**
     * Obtiene el id del registro a almacenar
     *
     * @param placa
     * @param idNumber
     * @return
     * @throws Exception
     */
    private String getTarjetaControlNumber(String placa, String idNumber) {
        return placa.toUpperCase() + idNumber.substring(idNumber.length() - 6);
    }
    
    /**
     * @param conductor 
     * @param idEmpresa 
     * @param idConductor
     * @param transaccion 
     * @param numTControl 
     * @param conn 
     */
    private void registrarAuditoria(Conductor conductor, int idConductor, long idEmpresa, long transaccion, String numTControl, Connection conn) throws Exception {
    	String sql = "INSERT INTO SMB_AUDITORIA_SIRC (ID_AUDITORIA_SIRC,FECHA_EVENTO,OBSERVACION,ID_TRANSACCION,USUARIO,"
    			+ "ID_CONDUCTOR,ID_EMPRESA,ORIGEN,TARJETA_CONTROL,PLACA) "
    			+ "values (SQ_AUDITORIA.nextval,?,null,?,?,?,?,?,?,?)";
    	PreparedStatement statement = conn.prepareStatement(sql);

        String sqlRadicado = "INSERT INTO SMB_RADICADO_TARJETA_CONTROL (ID_RADICADO,TARJETA_CONTROL,ID_RADICADO_TARJETA) "
                + "values (SQ_RADICADO_TARJETA.nextval,?,?)";
        PreparedStatement statementRadicado = conn.prepareStatement(sqlRadicado);
        String radicado = nextVal("SEQ_RADICADO_CODUCTOR.nextval", conn);
    	try {
			statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			statement.setLong(2, transaccion);
			statement.setString(3, username);
			statement.setInt(4, idConductor);
			statement.setLong(5, idEmpresa);
			statement.setString(6, OrigenTransaccionEnum.WEB_SERVICE.getPk());
			statement.setLong(7, Long.valueOf(radicado));
			statement.setString(8, conductor.getVehiculo().getPlaca());

			statement.execute();

            statementRadicado.setLong(1, Long.valueOf(radicado));
            statementRadicado.setString(2, numTControl);

            statementRadicado.execute();
			
    	}catch (Exception e) {
    		LOG.log(Level.SEVERE, e.getMessage(), e);
		}finally {
			statement.close();
            statementRadicado.close();
		}
	}

    private BufferedImage createQR(String tarjetaControl, Connection conn ) throws Exception {
        String URL_SIMUR = getSystemParam(SystemParameters.URL_SIMUR_QR.getValue(), conn);
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(URL_SIMUR.concat(tarjetaControl), BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        String rutaImg = getSystemParam(SystemParameters.URI_QR_IMG.getValue(), conn);
        Path basePath = Paths.get(rutaImg);
        File path = basePath.toFile();
        String qrFileLocation = path.getAbsolutePath()
                .concat(File.separator)
                .concat(tarjetaControl)
                .concat(".png");
        File outfile = new File(qrFileLocation);
        ImageIO.write(bufferedImage, "png", outfile);
        return bufferedImage;
    }

    private String getSystemParam(String keyParam, Connection conn) throws SQLException {
        Statement statement = null;
        String value = "";
        try {
            statement = conn.createStatement();
            String sql = "SELECT VALOR_PARAMETRO FROM SMB_PARAMETRO_SIMUR WHERE CODIGO_PARAMETRO = '" + keyParam + "'";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                value = rs.getString(1);
            }
        } catch (SQLException e) {
            throw e;
        }finally {
            statement.close();
        }

        return value;
    }
    
}
