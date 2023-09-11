package co.gov.movilidadbogota.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConductorVehiculoDTO extends AbstractDTO {

    private long id;
    private String numeroTarjetaControl;
    private String tipoTransaccion;
    private Date fechaExpedicion;
    private Date fechaValidez;
    private Date fechaVigencia;
    private String placa;
    private long idConductor;
    private EmpresaDTO empresaDTO;
    private ConductorDTO conductorDTO;
    private long tarjetaControlEstado;
    private boolean tipoTelFijo;
    private long idFactorCalidad = 0;
    private boolean factorCalidad = false;
    private long idMetodoCobro;
    private long idTipoServicio;
    private Date fechaVencimientoSoat;
    private String nroSOAT;
    private Date fechaVencimientoRtm;
    private String nroRTM;
    private String nroTarjetaOperacion;
    private Date fechaVencimientoTO;
    private String eps;
    private String nombreEmpresa;
    private String nitEmpresa;
    private String arl;
    private String tipoSangre;
    private String rutaFoto;
    private String foto;
    private String nombreMetodoCobro;
    
    private List<PagoPilaDTO> planillas;

    public List<PagoPilaDTO> getPlanillas() {
        return planillas;
    }

    public void setPlanillas(List<PagoPilaDTO> planillas) {
        this.planillas = planillas;
    }

    public String getNombreMetodoCobro() {
        return nombreMetodoCobro;
    }

    public void setNombreMetodoCobro(String nombreMetodoCobro) {
        this.nombreMetodoCobro = nombreMetodoCobro;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }


    // DATOS PAGO
    private String nroPagoPila;
    private String periodoPago;
    private Long idOperadorPila;

    private DispositivoMovilDTO dispositivosMovil;

    public ConductorVehiculoDTO() {
    }

    public ConductorVehiculoDTO(long id, String numeroTarjetaControl, String tipoTransaccion, Date fechaExpedicion,
            Date fechaValidez, Date fechaVigencia, String placa, long tarjetaControlEstado, Long idConductor) {
        super();
        this.id = id;
        this.numeroTarjetaControl = numeroTarjetaControl;
        this.tipoTransaccion = tipoTransaccion;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaValidez = fechaValidez;
        this.fechaVigencia = fechaVigencia;
        this.placa = placa;
        this.tarjetaControlEstado = tarjetaControlEstado;
        this.idConductor = idConductor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumeroTarjetaControl() {
        return numeroTarjetaControl;
    }

    public void setNumeroTarjetaControl(String numeroTarjetaControl) {
        this.numeroTarjetaControl = numeroTarjetaControl;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public Date getFechaValidez() {
        return fechaValidez;
    }

    public void setFechaValidez(Date fechaValidez) {
        this.fechaValidez = fechaValidez;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public EmpresaDTO getEmpresaDTO() {
        return empresaDTO;
    }

    public void setEmpresaDTO(EmpresaDTO empresaDTO) {
        this.empresaDTO = empresaDTO;
    }

    public ConductorDTO getConductorDTO() {
        return conductorDTO;
    }

    public void setConductorDTO(ConductorDTO conductorDTO) {
        this.conductorDTO = conductorDTO;
    }

    public long getTarjetaControlEstado() {
        return tarjetaControlEstado;
    }

    public void setTarjetaControlEstado(long tarjetaControlEstado) {
        this.tarjetaControlEstado = tarjetaControlEstado;
    }

    public Long getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(long idConductor) {
        this.idConductor = idConductor;
    }

    public boolean isTipoTelFijo() {
        return tipoTelFijo;
    }

    public void setTipoTelFijo(boolean tipoTelFijo) {
        this.tipoTelFijo = tipoTelFijo;
    }

    public String getNroPagoPila() {
        return nroPagoPila;
    }

    public void setNroPagoPila(String nroPagoPila) {
        this.nroPagoPila = nroPagoPila;
    }

    public String getPeriodoPago() {
        return periodoPago;
    }

    public void setPeriodoPago(String periodoPago) {
        this.periodoPago = periodoPago;
    }

    public long getIdFactorCalidad() {
        return idFactorCalidad;
    }

    public void setIdFactorCalidad(long idFactorCalidad) {
        this.idFactorCalidad = idFactorCalidad;
    }

    public boolean isFactorCalidad() {
        return factorCalidad;
    }

    public void setFactorCalidad(boolean factorCalidad) {
        this.factorCalidad = factorCalidad;
    }

    public long getIdMetodoCobro() {
        return idMetodoCobro;
    }

    public void setIdMetodoCobro(long idMetodoCobro) {
        this.idMetodoCobro = idMetodoCobro;
    }

    public long getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(long idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public Date getFechaVencimientoSoat() {
        return fechaVencimientoSoat;
    }

    public void setFechaVencimientoSoat(Date fechaVencimientoSoat) {
        this.fechaVencimientoSoat = fechaVencimientoSoat;
    }

    public String getNroSOAT() {
        return nroSOAT;
    }

    public void setNroSOAT(String nroSOAT) {
        this.nroSOAT = nroSOAT;
    }

    public Date getFechaVencimientoRtm() {
        return fechaVencimientoRtm;
    }

    public void setFechaVencimientoRtm(Date fechaVencimientoRtm) {
        this.fechaVencimientoRtm = fechaVencimientoRtm;
    }

    public String getNroRTM() {
        return nroRTM;
    }

    public void setNroRTM(String nroRTM) {
        this.nroRTM = nroRTM;
    }

    public String getNroTarjetaOperacion() {
        return nroTarjetaOperacion;
    }

    public void setNroTarjetaOperacion(String nroTarjetaOperacion) {
        this.nroTarjetaOperacion = nroTarjetaOperacion;
    }

    public Date getFechaVencimientoTO() {
        return fechaVencimientoTO;
    }

    public void setFechaVencimientoTO(Date fechaVencimientoTO) {
        this.fechaVencimientoTO = fechaVencimientoTO;
    }

    public Long getIdOperadorPila() {
        return idOperadorPila;
    }

    public void setIdOperadorPila(Long idOperadorPila) {
        this.idOperadorPila = idOperadorPila;
    }

    public DispositivoMovilDTO getDispositivosMovil() {
        return dispositivosMovil;
    }

    public void setDispositivosMovil(DispositivoMovilDTO dispositivosMovil) {
        this.dispositivosMovil = dispositivosMovil;
    }
     public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }
    
      public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getArl() {
        return arl;
    }

    public void setArl(String arl) {
        this.arl = arl;
    }
    
    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }
    
     public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
