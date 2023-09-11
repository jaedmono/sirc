package co.gov.movilidadbogota.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "SMB_TIPO_USUARIO")
public class TipoUsuarioEntity {

	@Id
	@Column(name = "ID_TIPO_USUARIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_tipo_usuario")
	@SequenceGenerator(name = "sq_tipo_usuario", sequenceName = "sq_tipo_usuario", allocationSize=1)
	private long id;

	@Column(name = "DESCRIPCION_TIPO")
	private String descripcionTipo;

	public TipoUsuarioEntity() {
	}

	public TipoUsuarioEntity(long id,  String descripcionTipo) {
		super();
		this.id = id;
		this.descripcionTipo = descripcionTipo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcionTipo() {
		return descripcionTipo;
	}

	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo= descripcionTipo;
	}
	
}
