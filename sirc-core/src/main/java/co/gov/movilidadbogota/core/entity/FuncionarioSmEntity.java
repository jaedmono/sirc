package co.gov.movilidadbogota.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SMB_FUNCIONARIO_SM")
public class FuncionarioSmEntity {

	@Id
	@Column(name = "ID_FUNCIONARIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_funcionario")
	@SequenceGenerator(name = "sq_funcionario", sequenceName = "sq_funcionario", allocationSize=1)
	private long id;

	@OneToOne
	@JoinColumn(name = "ID_PERSONA")
	private PersonaEntity persona;

	@OneToOne
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioEntity usuario;

	@Column(name = "ID_DEPENDENCIA")
	private long idDependencia;

	@Column(name = "NOMBRE_DEPENDENCIA")
	private String nombreDependencia;

	@Column(name = "FECHA_MODIFICA")
	private Date fechaModifica;
	
	@Column(name = "USR_MODIFICA")
	private String usrModifica;
	
	@Column(name = "ID_ESTADO")
	private long idEstado;


	public FuncionarioSmEntity() {
	}

	public FuncionarioSmEntity(long id, PersonaEntity persona, UsuarioEntity usuario,
			long idDependencia, String nombreDependencia, Date fechaModifica,
			String usrModifica, long idEstado ) {
		super();
		this.id = id;
		this.persona = persona;
		this.usuario = usuario;
		this.idDependencia = idDependencia;
		this.nombreDependencia = nombreDependencia;
		this.fechaModifica = fechaModifica;
		this.usrModifica = usrModifica;
		this.idEstado = idEstado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PersonaEntity getPersona() {
		return persona;
	}

	public void setPersona(PersonaEntity persona) {
		this.persona = persona;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public long getIdDependencia() {
		return idDependencia;
	}

	public void setIdDependencia(long idDependencia) {
		this.idDependencia = idDependencia;
	}

	public String getNombreDependencia() {
		return nombreDependencia;
	}

	public void setNombreDependencia(String nombreDependencia) {
		this.nombreDependencia = nombreDependencia;
	}

	public Date getFechaModifica() {
		return fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	public String getUsrModifica() {
		return usrModifica;
	}

	public void setUsrModifica(String usrModifica) {
		this.usrModifica = usrModifica;
	}

	public long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}

}
