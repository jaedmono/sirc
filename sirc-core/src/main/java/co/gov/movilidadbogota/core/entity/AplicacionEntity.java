package co.gov.movilidadbogota.core.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SMB_APLICACION")
public class AplicacionEntity {

	@Id
	@Column(name = "ID_APLICACION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_aplicacion")
	@SequenceGenerator(name = "sq_aplicacion", sequenceName = "sq_aplicacion", allocationSize=1)
	private long id;

	@Column(name = "ABREVIATURA_APLICACION")
	private String abreviaturaAplicacion;

	@Column(name = "NOMBRE_APLICACION")
	private String nombreAplicacion;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_APLICACION")
	private TipoAplicacionEntity tipoAplicacion;

	@Column(name = "ID_ESTADO")
	private long idEstado;
	
	public AplicacionEntity() {
	}

	public AplicacionEntity(long id, String abreviaturaAplicacion, String nombreAplicacion, TipoAplicacionEntity tipoAplicacion, long idEstado) {
		super();
		this.id = id;
		this.abreviaturaAplicacion = abreviaturaAplicacion;
		this.nombreAplicacion = nombreAplicacion;
		this.tipoAplicacion = tipoAplicacion;
		this.idEstado = idEstado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAbreviaturaAplicacion() {
		return abreviaturaAplicacion;
	}

	public void setAbreviaturaAplicacion(String abreviaturaAplicacion) {
		this.abreviaturaAplicacion = abreviaturaAplicacion;
	}

	public String getNombreAplicacion() {
		return nombreAplicacion;
	}

	public void setNombreAplicacion(String nombreAplicacion) {
		this.nombreAplicacion = nombreAplicacion;
	}

	public TipoAplicacionEntity getTipoAplicacion() {
		return tipoAplicacion;
	}

	public void setTipoAplicacion(TipoAplicacionEntity tipoAplicacion) {
		this.tipoAplicacion = tipoAplicacion;
	}

	public long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}


	
}
