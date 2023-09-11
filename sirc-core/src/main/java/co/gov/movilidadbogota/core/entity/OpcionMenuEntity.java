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
@Table(name = "SMB_OPCIONMENU")
public class OpcionMenuEntity {

	@Id
	@Column(name = "ID_OPCION_MENU")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_opcion_menu")
	@SequenceGenerator(name = "sq_opcion_menu", sequenceName = "sq_opcion_menu", allocationSize=1)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "ID_APLICACION")
	private AplicacionEntity aplicacion;

	@Column(name = "DESCRIPCION_OPCION")
	private String descripcionOpcion;

	@Column(name = "TIPO_OPCION_MENU")
	private long tipoOpcionMenu;
	
	@Column(name = "ID_ESTADO")
	private long idEstado;

	@Column(name = "ENLACE_OPCION_MENU")
	private String enlaceOpcionMenu;
	
	@Column(name = "USUARIO_MODIFICA")
	private String usuarioModifica;
	
	@Column(name = "FECHA_MODIFICA")
	private Date fechaModifica;

	public OpcionMenuEntity() {
	}

	public OpcionMenuEntity(long id, AplicacionEntity aplicacion, String descripcionOpcion, long tipoOpcionMenu, long idEstado,
			String enlaceOpcionMenu, String usuarioModifica, Date fechaModifica) {
		super();
		this.id = id;
		this.aplicacion = aplicacion;
		this.descripcionOpcion = descripcionOpcion;
		this.tipoOpcionMenu = tipoOpcionMenu;
		this.idEstado = idEstado;
		this.enlaceOpcionMenu = enlaceOpcionMenu;
		this.usuarioModifica = usuarioModifica;
		this.fechaModifica = fechaModifica;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AplicacionEntity getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionEntity aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getDescripcionOpcion() {
		return descripcionOpcion;
	}

	public void setDescripcionOpcion(String descripcionOpcion) {
		this.descripcionOpcion = descripcionOpcion;
	}

	public long getTipoOpcionMenu() {
		return tipoOpcionMenu;
	}

	public void setTipoOpcionMenu(long tipoOpcionMenu) {
		this.tipoOpcionMenu = tipoOpcionMenu;
	}

	public long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}

	public String getEnlaceOpcionMenu() {
		return enlaceOpcionMenu;
	}

	public void setEnlaceOpcionMenu(String enlaceOpcionMenu) {
		this.enlaceOpcionMenu = enlaceOpcionMenu;
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
