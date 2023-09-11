package co.gov.movilidadbogota.core.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class CreateDriverDTO extends AbstractDTO {

    // DATOS GENERALES
    private Long id;
    private Long tipoTransaccion;
    private String numeroTarjetaControl;

    // DATOS CONDUCTOR
    private Long tipoIdentificacion;
    private Long numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private String grupoSanguineo;
    private String rh;
    private String eps;
    private String arl;
    private String fondoPensiones;
    private String numeroTlf;
    private String direccion;
    private Long idEstado;
    private Long idConductor;

    // DATOS VEHICULOS
    private Long idEmpresa;
    private String codigoEmpresa;
    private String razonSocial;
    private String nit;
    private Long dv;
    private String placa;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaValidez;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaVigencia;
    private Long idMetodoCobro;
    private Long idTipoServicio;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaVencimientoSoat;
    private String nroSOAT;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaVencimientoRtm;
    private String nroRTM;
    private String nroTarjetaOperacion;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaVencimientoTO;

    private List<PagoPilaDTO> planillas;

    private String imgNombre;

    private ConductorDTO conductorDTO;
    private EmpresaDTO empresaDTO;
    private MetodoCobroDTO metodoCobroDTO;
    private TipoServicioDTO tipoServicioDTO;

    private boolean tipoTelFijo = true;

    private long idFactorCalidad = 0;
    private boolean factorCalidad = false;

    private DispositivoMovilDTO dispositivosMovil;
    private boolean planillaAdicional;

    @JsonIgnore
    private MultipartFile file;

    public CreateDriverDTO(Long tipoTransaccion, String numeroTarjetaControl, Long tipoIdentificacion,
            Long numeroIdentificacion, String nombres, String apellidos, String grupoSanguineo, String rh, String eps,
            String arl, String fondoPensiones, String numeroTlf, String direccion, Long idEmpresa, String codigoEmpresa,
            String razonSocial, Long dv, String placa, Long numeroOrden, String nroPagoPila, String periodoPago, Long idOperadorPila) {
        super();
        this.tipoTransaccion = tipoTransaccion;
        this.numeroTarjetaControl = numeroTarjetaControl;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.grupoSanguineo = grupoSanguineo;
        this.rh = rh;
        this.eps = eps;
        this.arl = arl;
        this.fondoPensiones = fondoPensiones;
        this.numeroTlf = numeroTlf;
        this.direccion = direccion;
        this.idEmpresa = idEmpresa;
        this.codigoEmpresa = codigoEmpresa;
        this.razonSocial = razonSocial;
        this.dv = dv;
        this.placa = placa;
    }

    public CreateDriverDTO() {
        super();
    }

    public String getNumeroTarjetaControl() {
        return numeroTarjetaControl;
    }

    public void setNumeroTarjetaControl(String numeroTarjetaControl) {
        this.numeroTarjetaControl = numeroTarjetaControl;
    }

    public Long getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(Long numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getArl() {
        return arl;
    }

    public void setArl(String arl) {
        this.arl = arl;
    }

    public String getFondoPensiones() {
        return fondoPensiones;
    }

    public void setFondoPensiones(String fondoPensiones) {
        this.fondoPensiones = fondoPensiones;
    }

    public String getNumeroTlf() {
        return numeroTlf;
    }

    public void setNumeroTlf(String numeroTlf) {
        this.numeroTlf = numeroTlf;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Long getDv() {
        return dv;
    }

    public void setDv(Long dv) {
        this.dv = dv;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getImgNombre() {
        return imgNombre;
    }

    public void setImgNombre(String imgNombre) {
        this.imgNombre = imgNombre;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(Long tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public Long getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(Long tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public Long getIdMetodoCobro() {
        return idMetodoCobro;
    }

    public void setIdMetodoCobro(Long idMetodoCobro) {
        this.idMetodoCobro = idMetodoCobro;
    }

    public Long getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(Long idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public Long getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Long idConductor) {
        this.idConductor = idConductor;
    }

    public ConductorDTO getConductorDTO() {
        return conductorDTO;
    }

    public void setConductorDTO(ConductorDTO conductorDTO) {
        this.conductorDTO = conductorDTO;
    }

    public EmpresaDTO getEmpresaDTO() {
        return empresaDTO;
    }

    public void setEmpresaDTO(EmpresaDTO empresaDTO) {
        this.empresaDTO = empresaDTO;
    }

    public boolean isTipoTelFijo() {
        return tipoTelFijo;
    }

    public void setTipoTelFijo(boolean tipoTelFijo) {
        this.tipoTelFijo = tipoTelFijo;
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

    public MetodoCobroDTO getMetodoCobroDTO() {
        return metodoCobroDTO;
    }

    public void setMetodoCobroDTO(MetodoCobroDTO metodoCobroDTO) {
        this.metodoCobroDTO = metodoCobroDTO;
    }

    public TipoServicioDTO getTipoServicioDTO() {
        return tipoServicioDTO;
    }

    public void setTipoServicioDTO(TipoServicioDTO tipoServicioDTO) {
        this.tipoServicioDTO = tipoServicioDTO;
    }

    public DispositivoMovilDTO getDispositivosMovil() {
        return dispositivosMovil;
    }

    public void setDispositivosMovil(DispositivoMovilDTO dispositivosMovil) {
        this.dispositivosMovil = dispositivosMovil;
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

    public List<PagoPilaDTO> getPlanillas() {
        return planillas;
    }

    public void setPlanillas(List<PagoPilaDTO> planillas) {
        this.planillas = planillas;
    }
    
    
}
