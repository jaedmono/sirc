package co.gov.movilidadbogota.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "SMI_PERSONA")
public class PersonaEntity {

    @Id
    @Column(name = "ID_PERSONA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_persona")
    @SequenceGenerator(name = "sq_persona", sequenceName = "sq_persona", allocationSize = 1)
    private long id;

    @Column(name = "NUMERO_DOCUMENTO")
    private Long numeroDocumento;

    @ManyToOne
    @JoinColumn(name = "TIPO_DOCUMENTO")
    private TipoDocumentoEntity tipoDocumento;

    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    @Column(name = "ID_GENERO")
    private Long idGenero;

    @Column(name = "EXTENSION")
    private Long extension;

    @Column(name = "NOMBRES")
    private String nombres;

    @Column(name = "APELLIDOS")
    private String apellidos;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "CELULAR")
    private Long celular;

    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;

    @Column(name = "ID_ESTADO")
    private boolean idEstado;

    @Column(name = "FECHA_EXPEDICION_DOCUMENTO")
    private Date fechaExpedicionDocumento;
    
    @Column(name = "FECHA_MODIFICACION")
    private Date fechaModifica;

    public PersonaEntity() {
    }

    public PersonaEntity(long id, Long numeroDocumento, TipoDocumentoEntity tipoDocumento, Date fechaModificacion, Long idGenero,
            Long extension, String nombres, String apellidos, String direccion, Long celular, String correoElectronico,
            boolean idEstado) {
        super();
        this.id = id;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.fechaModifica = fechaModificacion;
        this.idGenero = idGenero;
        this.extension = extension;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.celular = celular;
        this.correoElectronico = correoElectronico;
        this.idEstado = idEstado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Long getIdGenero() {
        return idGenero;
    }

    public Long getExtension() {
        return extension;
    }

    public Long getCelular() {
        return celular;
    }

    public TipoDocumentoEntity getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoEntity tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public boolean isIdEstado() {
        return idEstado;
    }

    public void setIdEstado(boolean idEstado) {
        this.idEstado = idEstado;
    }

    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }

    public void setExtension(Long extension) {
        this.extension = extension;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public Date getFechaExpedicionDocumento() {
        return fechaExpedicionDocumento;
    }

    public void setFechaExpedicionDocumento(Date fechaExpedicionDocumento) {
        this.fechaExpedicionDocumento = fechaExpedicionDocumento;
    }

    public Date getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(Date fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

}
