package co.gov.movilidadbogota.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "SMI_CONDUCTOR")
public class ConductorEntity {

	@Id
	@Column(name = "ID_CONDUCTOR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_conductor")
	@SequenceGenerator(name = "sq_conductor", sequenceName = "sq_conductor", allocationSize=1)
	private long id;

	@OneToOne
	@JoinColumn(name = "ID_PERSONA")
	private PersonaEntity persona;
	
	@Column(name = "GRUPO_SANGUINEO")
	private String grupoSanguineo;

	@Column(name = "FACTOR_RH")
	private String factorRh;
	
	@Column(name = "ID_EPS")
	private long idEps;
	
	@Column(name = "ID_ARL")
	private long idArl;
	
	@Column(name = "ID_AFP")
	private long idAfp;
	
	@Column(name = "USUARIO_MODIFICA")
	private String usuarioModifica;

	@Column(name = "FECHA_MODIFICA")
	private Date fechaModifica;
	
	@Column(name = "RUTA_FOTO")
	private String rutaFoto;

	public ConductorEntity() {
	}

	public ConductorEntity(long id, PersonaEntity persona, String grupoSanguineo,
			String factorRh, long idEps, long idArl, long idAfp, 
			String usuarioModifica, Date fechaModifica, String rutaFoto ) {
		super();
		this.id = id;
		this.persona = persona;
		this.grupoSanguineo = grupoSanguineo;
		this.factorRh = factorRh;
		this.idEps = idEps;
		this.idArl = idArl;
		this.idAfp = idAfp;
		this.usuarioModifica = usuarioModifica;
		this.fechaModifica = fechaModifica;
		this.rutaFoto = rutaFoto;
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

	public String getGrupoSanguineo() {
		return grupoSanguineo;
	}

	public void setGrupoSanguineo(String grupoSanguineo) {
		this.grupoSanguineo = grupoSanguineo;
	}

	public String getFactorRh() {
		return factorRh;
	}

	public void setFactorRh(String factorRh) {
		this.factorRh = factorRh;
	}

	public long getIdEps() {
		return idEps;
	}

	public void setIdEps(long idEps) {
		this.idEps = idEps;
	}

	public long getIdArl() {
		return idArl;
	}

	public void setIdArl(long idArl) {
		this.idArl = idArl;
	}

	public long getIdAfp() {
		return idAfp;
	}

	public void setIdAfp(long idAfp) {
		this.idAfp = idAfp;
	}

	public String getUsuarioModifica() {
		return usuarioModifica;
	}

	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}

	public Date getFechaModifica() {
		return fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	public String getRutaFoto() {
		return rutaFoto;
	}

	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

}
