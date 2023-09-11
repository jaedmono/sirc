package co.gov.movilidadbogota.core.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SMI_USUARIO")
public class UsuarioEntity {

	@Id
	@Column(name = "ID_USUARIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_usuario")
	@SequenceGenerator(name = "sq_usuario", sequenceName = "sq_usuario", allocationSize=1)
	private long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ID_PERSONA", nullable = false)
	private PersonaEntity persona;
	
	@Column(name = "LOGIN_USUARIO", nullable = false)
	private String loginUsuario;

	@Column(name = "CLAVE_USUARIO")
	private String claveUsuario;
	
	@ManyToOne
	@JoinColumn(name = "ID_PERFIL")//, nullable = false
	private PerfilEntity perfil;
	
	@Column(name = "ID_ESTADO", nullable = false)
	private boolean idEstado;
	
	@Column(name = "FECHA_MODIFICA", nullable = false)
	private Date fechaModifica;
	
	@Column(name = "USR_MODIFICA") //, nullable = false
	private String usrModifica;
	
	@Column(name = "URL_SAFE_TOKEN") 
	private String urlSafeToken;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_USUARIO", nullable = false)
	private TipoUsuarioEntity idTipoUsuario;
	
	@ManyToMany
	@JoinTable(name = "SMI_EMPRESA_USUARIO", joinColumns = @JoinColumn(name = "ID_USUARIO"), inverseJoinColumns = @JoinColumn(name = "ID_EMPRESA"))
	private List<EmpresaEntity> empresaEntityList;
	
	public UsuarioEntity() {
	}
	
	public UsuarioEntity(long id, String loginUsuario) {
		this.id = id;
		this.loginUsuario = loginUsuario;
	}

	public UsuarioEntity(long id, PersonaEntity persona, String loginUsuario, String claveUsuario,  PerfilEntity perfil,
			boolean idEstado,  Date fechaModifica, String usrModifica, TipoUsuarioEntity idTipoUsuario) {
		this.id = id;
		this.persona = persona;
		this.loginUsuario = loginUsuario;
		this.claveUsuario = claveUsuario;
		this.perfil = perfil;
		this.idEstado = idEstado;
		this.fechaModifica = fechaModifica;
		this.usrModifica = usrModifica;
		this.idTipoUsuario = idTipoUsuario;
	}
	
	public UsuarioEntity(long id, PersonaEntity persona, String loginUsuario, String claveUsuario, PerfilEntity perfil,
			boolean idEstado, Date fechaModifica, String usrModifica, TipoUsuarioEntity idTipoUsuario,
			List<EmpresaEntity> empresaEntityList) {
		super();
		this.id = id;
		this.persona = persona;
		this.loginUsuario = loginUsuario;
		this.claveUsuario = claveUsuario;
		this.perfil = perfil;
		this.idEstado = idEstado;
		this.fechaModifica = fechaModifica;
		this.usrModifica = usrModifica;
		this.idTipoUsuario = idTipoUsuario;
		this.empresaEntityList = empresaEntityList;
	}

	public List<EmpresaEntity> getEmpresaEntityList() {
		return empresaEntityList;
	}

	public void setEmpresaEntityList(List<EmpresaEntity> empresaEntityList) {
		this.empresaEntityList = empresaEntityList;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public PersonaEntity getPersona() {
		return persona;
	}

	public void setPersona(PersonaEntity persona) {
		this.persona = persona;
	}

	public PerfilEntity getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEntity perfil) {
		this.perfil = perfil;
	}

	public boolean isIdEstado() {
		return idEstado;
	}

	public void setIdEstado(boolean idEstado) {
		this.idEstado = idEstado;
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

	public TipoUsuarioEntity getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public void setIdTipoUsuario(TipoUsuarioEntity idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}

	public String getUrlSafeToken() {
		return urlSafeToken;
	}

	public void setUrlSafeToken(String urlSafeToken) {
		this.urlSafeToken = urlSafeToken;
	}	
	
}
