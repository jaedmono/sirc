package co.gov.movilidadbogota.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "SMB_EPS")
public class EpsEntity {

	@Id
	@Column(name = "ID_EPS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_eps")
	@SequenceGenerator(name = "sq_eps", sequenceName = "sq_eps", allocationSize=1)
	private long id;

	@Column(name = "CODIGO_EPS")
	private String codigoEps;

	@Column(name = "NIT_EPS")
	private String nitEps;

	@Column(name = "NOMBRE_EPS")
	private String nombreEps;

	@Column(name = "DIRECCION_EPS")
	private String direccionEps;

	@Column(name = "TELEFONO_EPS")
	private String telefonoEps;


	public EpsEntity() {
	}

	public EpsEntity(long id, String codigoEps, String nitEps,
			String nombreEps, String direccionEps, String telefonoEps) {
		super();
		this.id = id;
		this.codigoEps = codigoEps;
		this.nitEps = nitEps;
		this.nombreEps = nombreEps;
		this.direccionEps = direccionEps;
		this.telefonoEps = telefonoEps;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigoEps() {
		return codigoEps;
	}

	public void setCodigoEps(String codigoEps) {
		this.codigoEps = codigoEps;
	}

	public String getNitEps() {
		return nitEps;
	}

	public void setNitEps(String nitEps) {
		this.nitEps = nitEps;
	}

	public String getNombreEps() {
		return nombreEps;
	}

	public void setNombreEps(String nombreEps) {
		this.nombreEps = nombreEps;
	}

	public String getDireccionEps() {
		return direccionEps;
	}

	public void setDireccionEps(String direccionEps) {
		this.direccionEps = direccionEps;
	}

	public String getTelefonoEps() {
		return telefonoEps;
	}

	public void setTelefonoEps(String telefonoEps) {
		this.telefonoEps = telefonoEps;
	}
}
