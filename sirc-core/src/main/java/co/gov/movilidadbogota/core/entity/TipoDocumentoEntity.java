package co.gov.movilidadbogota.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "SMB_TIPO_DOCUMENTO")
public class TipoDocumentoEntity {

	@Id
	@Column(name = "ID_TIPO_DOCUMENTO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_tipo_documento")
	@SequenceGenerator(name = "sq_tipo_documento", sequenceName = "sq_tipo_documento", allocationSize=1)
	private long id;

	@Column(name = "DESCRIPCION_TIPODOC")
	private String descripcionTipoDoc;

	@Column(name = "HOMOLOGA_DUUPS")
	private String homologaDuups;
	
	public TipoDocumentoEntity() {
	}

	public TipoDocumentoEntity(long id,  String descripcionTipoDoc) {
		super();
		this.id = id;
		this.descripcionTipoDoc = descripcionTipoDoc;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcionTipoDoc() {
		return descripcionTipoDoc;
	}

	public void setDescripcionTipoDoc(String descripcionTipoDoc) {
		this.descripcionTipoDoc = descripcionTipoDoc;
	}

	public String getHomologaDuups() {
		return homologaDuups;
	}

	public void setHomologaDuups(String homologaDuups) {
		this.homologaDuups = homologaDuups;
	}
	
	
	
}
