package co.gov.movilidadbogota.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "SMB_TIPO_APLICACION")
public class TipoAplicacionEntity {

	@Id
	@Column(name = "ID_TIPO_APLICACION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_tipo_aplicacion")
	@SequenceGenerator(name = "sq_tipo_aplicacion", sequenceName = "sq_tipo_aplicacion", allocationSize=1)
	private long id;

	@Column(name = "DESCRIPCION_TIPOAPLICACION")
	private String descripcionTipoAplicacion;

	public TipoAplicacionEntity() {
	}

	public TipoAplicacionEntity(long id, String descripcionTipoAplicacion) {
		super();
		this.id = id;
		this.descripcionTipoAplicacion = descripcionTipoAplicacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcionTipoAplicacion() {
		return descripcionTipoAplicacion;
	}

	public void setDescripcionTipoAplicacion(String descripcionTipoAplicacion) {
		this.descripcionTipoAplicacion = descripcionTipoAplicacion;
	}

}
