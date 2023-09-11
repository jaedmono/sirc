package co.gov.movilidadbogota.core.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class PersonaDTO extends AbstractDTO {

    private Long id;
    private Long tipoIdentificacion;
    private String tipoIdentificacionDesc;
    private Long numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private Long celular;
    private String direccion;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaNacimiento;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaExpedicionDocumento;

    public PersonaDTO() {
    }

    public PersonaDTO(Long tipoIdentificacion, Long numeroIdentificacion, String nombres, String apellidos,
            Long celular, String direccion) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(Long tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
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

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoIdentificacionDesc() {
        return tipoIdentificacionDesc;
    }

    public void setTipoIdentificacionDesc(String tipoIdentificacionDesc) {
        this.tipoIdentificacionDesc = tipoIdentificacionDesc;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaExpedicionDocumento() {
        return fechaExpedicionDocumento;
    }

    public void setFechaExpedicionDocumento(Date fechaExpedicionDocumento) {
        this.fechaExpedicionDocumento = fechaExpedicionDocumento;
    }

}
