package co.gov.movilidadbogota.core.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SMI_EMPRESA")
public class EmpresaEntity {

	@Id
	@Column(name = "ID_EMPRESA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_empresa")
	@SequenceGenerator(name = "sq_empresa", sequenceName = "sq_empresa", allocationSize = 1)
	private long id;

	@Column(name = "ID_NIT_EMPRESA")
	private String idNitEmpresa;

	@Column(name = "NOMBRE_RAZON_SOCIAL")
	private String nombreRazonSocial;

	@Column(name = "ID_ESTADO")
	private long idEstado;

	@Column(name = "USUARIO_MODIFICA")
	private String usuarioModifica;

	@Column(name = "FECHA_MODIFICA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModifica;

	@Column(name = "SIGLA_EMPRESA")
	private String siglaEmpresa;

	@Column(name = "CODIGO_EMPRESA", nullable = true, unique = true)
	private String codigoEmpresa;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	private List<ConductorVehiculoEntity> conductorVehiculoEntity;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "empresaEntityList")
	private List<UsuarioEntity> usuarioEntities;

	public EmpresaEntity() {
	}

	public EmpresaEntity(long id, String nombreRazonSocial) {
		this.id = id;
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public EmpresaEntity(long id, String idNitEmpresa, String nombreRazonSocial, long idEstado, String usuarioModifica,
			Date fechaModifica, String siglaEmpresa) {
		super();
		this.id = id;
		this.idNitEmpresa = idNitEmpresa;
		this.nombreRazonSocial = nombreRazonSocial;
		this.fechaModifica = fechaModifica;
		this.idEstado = idEstado;
		this.usuarioModifica = usuarioModifica;
		this.siglaEmpresa = siglaEmpresa;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}

	public Date getFechaModifica() {
		return fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	public String getIdNitEmpresa() {
		return idNitEmpresa;
	}

	public void setIdNitEmpresa(String idNitEmpresa) {
		this.idNitEmpresa = idNitEmpresa;
	}

	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public String getUsuarioModifica() {
		return usuarioModifica;
	}

	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}

	public String getSiglaEmpresa() {
		return siglaEmpresa;
	}

	public void setSiglaEmpresa(String siglaEmpresa) {
		this.siglaEmpresa = siglaEmpresa;
	}

	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public List<ConductorVehiculoEntity> getConductorVehiculoEntity() {
		return conductorVehiculoEntity;
	}

	public void setConductorVehiculoEntity(List<ConductorVehiculoEntity> conductorVehiculoEntity) {
		this.conductorVehiculoEntity = conductorVehiculoEntity;
	}

	public List<UsuarioEntity> getUsuarioEntities() {
		return usuarioEntities;
	}

	public void setUsuarioEntities(List<UsuarioEntity> usuarioEntities) {
		this.usuarioEntities = usuarioEntities;
	}

}
