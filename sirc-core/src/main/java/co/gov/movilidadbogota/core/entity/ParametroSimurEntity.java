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
@Table(name = "SMB_PARAMETRO_SIMUR")
public class ParametroSimurEntity {

	@Id
	@Column(name = "ID_PARAMETRO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_parametro")
	@SequenceGenerator(name = "sq_parametro", sequenceName = "sq_parametro", allocationSize=1)
	private long id;

	@ManyToOne
	@JoinColumn(name = "ID_APLICACION")
	private AplicacionEntity aplicacion;

	@Column(name = "CODIGO_PARAMETRO")
	private String codigoParametro;

	@Column(name = "VALOR_PARAMETRO")
	private String valorParametro;

	@Column(name = "DESCRIPCION_PARAMETRO")
	private String descripcionParametro;

	@Column(name = "ID_ESTADO")
	private boolean estado;

	@Column(name = "USR_MODIFICA")
	private String usrModifica;

	@Column(name = "FECHA_MODIFICA")
	private Date fechaModifica;

	public ParametroSimurEntity() {
	}

	public ParametroSimurEntity(long id, AplicacionEntity aplicacion, String codigoParametro, String valorParametro,
			String descripcionParametro, boolean estado, String usrModifica, Date fechaModifica) {
		super();
		this.id = id;
		this.aplicacion = aplicacion;
		this.codigoParametro = codigoParametro;
		this.valorParametro = valorParametro;
		this.descripcionParametro = descripcionParametro;
		this.estado = estado;
		this.usrModifica = usrModifica;
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

	public String getCodigoParametro() {
		return codigoParametro;
	}

	public void setCodigoParametro(String codigoParametro) {
		this.codigoParametro = codigoParametro;
	}

	public String getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}

	public String getDescripcionParametro() {
		return descripcionParametro;
	}

	public void setDescripcionParametro(String descripcionParametro) {
		this.descripcionParametro = descripcionParametro;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getUsrModifica() {
		return usrModifica;
	}

	public void setUsrModifica(String usrModifica) {
		this.usrModifica = usrModifica;
	}

	public Date getFechaModifica() {
		return fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

}
