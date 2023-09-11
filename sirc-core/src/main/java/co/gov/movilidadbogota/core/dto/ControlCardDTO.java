package co.gov.movilidadbogota.core.dto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ControlCardDTO extends AbstractDTO {
    
    private String tarjetaControl;
    private String estado;
    private LocalDate fechaVigencia;
    private LocalDate fechaExpedicion;
    private String tipoTransaccion;
    private String empresa;
    private String nombres;
    private String apellidos;
    private Long numeroDocumento;
    private Long telefono;
    private String placa;
    private String numeroSoat;
    private LocalDate fechaVigenciaSOAT;
    private String numeroRTM;
    private LocalDate fechaVigenciaRTM;
    private String numeroTarjetaOperacion;
    private LocalDate fechaVigenciaTarjetaOperacion;

    public String getTarjetaControl() {
        return tarjetaControl;
    }

    public void setTarjetaControl(String tarjetaControl) {
        this.tarjetaControl = tarjetaControl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDate getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNumeroSoat() {
        return numeroSoat;
    }

    public void setNumeroSoat(String numeroSoat) {
        this.numeroSoat = numeroSoat;
    }

    public LocalDate getFechaVigenciaSOAT() {
        return fechaVigenciaSOAT;
    }

    public void setFechaVigenciaSOAT(Date fechaVigenciaSOAT) {
        this.fechaVigenciaSOAT = fechaVigenciaSOAT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public String getNumeroRTM() {
        return numeroRTM;
    }

    public void setNumeroRTM(String numeroRTM) {
        this.numeroRTM = numeroRTM;
    }

    public LocalDate getFechaVigenciaRTM() {
        return fechaVigenciaRTM;
    }

    public void setFechaVigenciaRTM(Date fechaVigenciaRTM) {
        this.fechaVigenciaRTM = fechaVigenciaRTM.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public String getNumeroTarjetaOperacion() {
        return numeroTarjetaOperacion;
    }

    public void setNumeroTarjetaOperacion(String numeroTarjetaOperacion) {
        this.numeroTarjetaOperacion = numeroTarjetaOperacion;
    }

    public LocalDate getFechaVigenciaTarjetaOperacion() {
        return fechaVigenciaTarjetaOperacion;
    }

    public void setFechaVigenciaTarjetaOperacion(Date fechaVigenciaTarjetaOperacion) {
        this.fechaVigenciaTarjetaOperacion = fechaVigenciaTarjetaOperacion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
