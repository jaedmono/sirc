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
@Table(name = "SMB_PERFILXOPCION_MENU")
public class PerfilOpcionMenuEntity {

	@Id
	@Column(name = "ID_PERFILXOPCION_MENU")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_perfil_menu")
	@SequenceGenerator(name = "sq_perfil_menu", sequenceName = "sq_perfil_menu", allocationSize=1)
	private long id;

	@ManyToOne
	@JoinColumn(name = "ID_PERFIL")
	private PerfilEntity perfil;

	@ManyToOne
	@JoinColumn(name = "ID_OPCION_MENU")
	private OpcionMenuEntity opcionMenu;

	@Column(name = "ID_ESTADO")
	private long idEstado;
	
	@Column(name = "FECHA_INICIA_PERMISO")
	private Date fechaIniciaPermiso;
	
	@Column(name = "FECHA_FINAL_PERMISO")
	private Date fechaFinalPermiso;
	
	@Column(name = "FECHA_MODIFICA")
	private Date fechaModifica;

	@Column(name = "USR_MODIFICA")
	private String usrModifica;

	public PerfilOpcionMenuEntity() {
	}

	public PerfilOpcionMenuEntity(long id, PerfilEntity perfil, OpcionMenuEntity opcionMenu ,long idEstado,Date fechaIniciaPermiso, Date fechaFinalPermiso,
			Date fechaModifica , String usrModifica) {
		super();
		this.id = id;
		this.perfil = perfil;
		this.opcionMenu = opcionMenu;
		this.idEstado = idEstado;
		this.fechaIniciaPermiso = fechaIniciaPermiso;
		this.fechaFinalPermiso = fechaFinalPermiso;
		this.fechaModifica = fechaModifica;
		this.usrModifica = usrModifica;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PerfilEntity getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEntity perfil) {
		this.perfil = perfil;
	}

	public OpcionMenuEntity getOpcionMenu() {
		return opcionMenu;
	}

	public void setOpcionMenu(OpcionMenuEntity opcionMenu) {
		this.opcionMenu = opcionMenu;
	}

	

	public long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}

	public Date getFechaIniciaPermiso() {
		return fechaIniciaPermiso;
	}

	public void setFechaIniciaPermiso(Date fechaIniciaPermiso) {
		this.fechaIniciaPermiso = fechaIniciaPermiso;
	}

	public Date getFechaFinalPermiso() {
		return fechaFinalPermiso;
	}

	public void setFechaFinalPermiso(Date fechaFinalPermiso) {
		this.fechaFinalPermiso = fechaFinalPermiso;
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

}
