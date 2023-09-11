package co.gov.movilidadbogota.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SMB_RTO_VIGENCIA")
public class RtoVigenciaEntity implements Serializable {

    @Id
    @Column(name = "ID_RTO_VIGENCIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_rto_vigencia")
    @SequenceGenerator(name = "sq_rto_vigencia", sequenceName = "sq_rto_vigencia", allocationSize = 1)
    private long id;

    @Column(name = "PLACA")
    private String placa;

    @Column(name = "EMPRESA")
    private long empresa;

    @Column(name = "RADIO")
    private long radio;

    @Column(name = "NIVEL")
    private String nivel;

    @Column(name = "MODALIDAD")
    private String modalidad;

    @Column(name = "NRO_ORDEN")
    private String nroOrden;

    @Column(name = "NRO_TO")
    private String nroTO;

    @Column(name = "FECHA_EXPEDICION")
    private Date fechaExpedicion;

    @Column(name = "FECHA_VENCIMIENTO")
    private Date fechaVencimiento;

    @Column(name = "TRAMITE")
    private long tramite;

    @Column(name = "TRANSFORMA_REPOTENCIA")
    private String transformaRepotencia;

    @Column(name = "MODELO_TRANSFORMACION")
    private String modeloTransformacion;

    @Column(name = "DESCRIPCION_TRANSFORMACION")
    private String descripcionTransformacion;

    @Column(name = "NRO_CANCELACION")
    private String nroCancelacion;

    @Column(name = "FECHA_CANCELACION")
    private Date fechaCancelacion;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "CLASE")
    private String clase;

    @Column(name = "ALIMENTADOR")
    private String alimentador;

    @Column(name = "VIDA_UTIL")
    private String vidaUtil;

    @Column(name = "FECHA_INSTALACION")
    private Date fechaInstalacion;

    @Column(name = "SERIAL_TO")
    private String serialTO;

    @Column(name = "FECHA_ULTIMO_TRAMITE")
    private Date fechaUltimoTramite;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public long getEmpresa() {
        return empresa;
    }

    public void setEmpresa(long empresa) {
        this.empresa = empresa;
    }

    public long getRadio() {
        return radio;
    }

    public void setRadio(long radio) {
        this.radio = radio;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getNroOrden() {
        return nroOrden;
    }

    public void setNroOrden(String nroOrden) {
        this.nroOrden = nroOrden;
    }

    public String getNroTO() {
        return nroTO;
    }

    public void setNroTO(String nroTO) {
        this.nroTO = nroTO;
    }

    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public long getTramite() {
        return tramite;
    }

    public void setTramite(long tramite) {
        this.tramite = tramite;
    }

    public String getTransformaRepotencia() {
        return transformaRepotencia;
    }

    public void setTransformaRepotencia(String transformaRepotencia) {
        this.transformaRepotencia = transformaRepotencia;
    }

    public String getModeloTransformacion() {
        return modeloTransformacion;
    }

    public void setModeloTransformacion(String modeloTransformacion) {
        this.modeloTransformacion = modeloTransformacion;
    }

    public String getDescripcionTransformacion() {
        return descripcionTransformacion;
    }

    public void setDescripcionTransformacion(String descripcionTransformacion) {
        this.descripcionTransformacion = descripcionTransformacion;
    }

    public String getNroCancelacion() {
        return nroCancelacion;
    }

    public void setNroCancelacion(String nroCancelacion) {
        this.nroCancelacion = nroCancelacion;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getAlimentador() {
        return alimentador;
    }

    public void setAlimentador(String alimentador) {
        this.alimentador = alimentador;
    }

    public String getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(String vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public Date getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(Date fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public String getSerialTO() {
        return serialTO;
    }

    public void setSerialTO(String serialTO) {
        this.serialTO = serialTO;
    }

    public Date getFechaUltimoTramite() {
        return fechaUltimoTramite;
    }

    public void setFechaUltimoTramite(Date fechaUltimoTramite) {
        this.fechaUltimoTramite = fechaUltimoTramite;
    }
}
