package co.gov.movilidadbogota.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "SMB_TIPO_ASIGNA")
public class TipoAsignaEntity {

	@Id
	@Column(name = "ID_TIPO_ASIGNA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_tipo_asigna")
	@SequenceGenerator(name = "sq_tipo_asigna", sequenceName = "sq_tipo_asigna", allocationSize=1)
	private long id;

	@Column(name = "DESCRIPCION_ASIGNA")
	private String descripcionAsigna;

	public TipoAsignaEntity() {
	}

	public TipoAsignaEntity(long id, String descripcionAsigna) {
		super();
		this.id = id;
		this.descripcionAsigna = descripcionAsigna;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcionAsigna() {
		return descripcionAsigna;
	}

	public void setDescripcionAsigna(String descripcionAsigna) {
		this.descripcionAsigna = descripcionAsigna;
	}

}
