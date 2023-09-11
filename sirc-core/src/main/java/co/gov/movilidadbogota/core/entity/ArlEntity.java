package co.gov.movilidadbogota.core.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "SMB_ARL")
public class ArlEntity {

	@Id
	@Column(name = "ID_ARL")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_arl")
	@SequenceGenerator(name = "sq_arl", sequenceName = "sq_arl", allocationSize=1)
	private long id;

	@Column(name = "CODIGO_ARL")
	private String codigoArl;

	@Column(name = "NIT_ARL")
	private String nitArl;

	@Column(name = "NOMBRE_ARL")
	private String nombreArl;

	@Column(name = "DIRECCION_ARL")
	private String direccionArl;

	@Column(name = "TELEFONO_ARL")
	private String telefonoArl;
	


	public ArlEntity() {
	}

	public ArlEntity(long id, String codigoArl, String nitArl, String nombreArl, String direccionArl,
			String telefonoArl) {
		super();
		this.id = id;
		this.codigoArl = codigoArl;
		this.nitArl = nitArl;
		this.nombreArl = nombreArl;
		this.direccionArl = direccionArl;
		this.telefonoArl = telefonoArl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigoArl() {
		return codigoArl;
	}

	public void setCodigoArl(String codigoArl) {
		this.codigoArl = codigoArl;
	}

	public String getNitArl() {
		return nitArl;
	}

	public void setNitArl(String nitArl) {
		this.nitArl = nitArl;
	}

	public String getNombreArl() {
		return nombreArl;
	}

	public void setNombreArl(String nombreArl) {
		this.nombreArl = nombreArl;
	}

	public String getDireccionArl() {
		return direccionArl;
	}

	public void setDireccionArl(String direccionArl) {
		this.direccionArl = direccionArl;
	}

	public String getTelefonoArl() {
		return telefonoArl;
	}

	public void setTelefonoArl(String telefonoArl) {
		this.telefonoArl = telefonoArl;
	}

}
