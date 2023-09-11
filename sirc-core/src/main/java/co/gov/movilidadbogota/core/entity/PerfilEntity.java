package co.gov.movilidadbogota.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "SMB_PERFIL")
public class PerfilEntity {

	@Id
	@Column(name = "ID_PERFIL")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_perfil")
	@SequenceGenerator(name = "sq_perfil", sequenceName = "sq_perfil", allocationSize=1)
	private long id;

	@Column(name = "DESCRIPCION_PERFIL")
	private String descripcionPerfil;

	@Column(name = "ID_ESTADO")
	private boolean idEstado;

	@Column(name = "USUARIO_MODIFICA")
	private String usuarioModifica;

	@Column(name = "FECHA_MODIFICA")
	private Date fechaModifica;

	public PerfilEntity() {
	}

	public PerfilEntity(long id, String descripcionPerfil, boolean idEstado, String usuarioModifica,
			Date fechaModifica) {
		super();
		this.id = id;
		this.descripcionPerfil = descripcionPerfil;
		this.idEstado = idEstado;
		this.usuarioModifica = usuarioModifica;
		this.fechaModifica = fechaModifica;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcionPerfil() {
		return descripcionPerfil;
	}

	public void setDescripcionPerfil(String descripcionPerfil) {
		this.descripcionPerfil = descripcionPerfil;
	}

	public boolean getIdEstado() {
		return idEstado;
	}

	public void isIdEstado(boolean idEstado) {
		this.idEstado = idEstado;
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

}
