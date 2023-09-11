package co.gov.movilidadbogota.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SMB_AFP")
public class AfpEntity {

	@Id
	@Column(name = "ID_AFP")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_afp")
	@SequenceGenerator(name = "sq_afp", sequenceName = "sq_afp", allocationSize=1)
	private long id;

	@Column(name = "CODIGO_AFP")
	private String codigoAfp;

	@Column(name = "NIT_AFP")
	private String nitAfp;

	@Column(name = "NOMBRE_AFP")
	private String nombreAfp;

	@Column(name = "DIRECCION_AFP")
	private String direccionAfp;

	@Column(name = "TELEFONO_AFP")
	private String telefonoAfp;
	


	public AfpEntity() {
	}

	public AfpEntity(long id, String codigoAfp, String nitAfp, String nombreAfp, String direccionAfp,
			String telefonoAfp) {
		super();
		this.id = id;
		this.codigoAfp = codigoAfp;
		this.nitAfp = nitAfp;
		this.nombreAfp = nombreAfp;
		this.direccionAfp = direccionAfp;
		this.telefonoAfp = telefonoAfp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigoAfp() {
		return codigoAfp;
	}

	public void setCodigoAfp(String codigoAfp) {
		this.codigoAfp = codigoAfp;
	}

	public String getNitAfp() {
		return nitAfp;
	}

	public void setNitAfp(String nitAfp) {
		this.nitAfp = nitAfp;
	}

	public String getNombreAfp() {
		return nombreAfp;
	}

	public void setNombreAfp(String nombreAfp) {
		this.nombreAfp = nombreAfp;
	}

	public String getDireccionAfp() {
		return direccionAfp;
	}

	public void setDireccionAfp(String direccionAfp) {
		this.direccionAfp = direccionAfp;
	}

	public String getTelefonoAfp() {
		return telefonoAfp;
	}

	public void setTelefonoAfp(String telefonoAfp) {
		this.telefonoAfp = telefonoAfp;
	}

}
