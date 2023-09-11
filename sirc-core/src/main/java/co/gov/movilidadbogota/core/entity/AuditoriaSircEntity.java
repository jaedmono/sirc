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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SMB_AUDITORIA_SIRC")
public class AuditoriaSircEntity {

	@Id
	@Column(name = "ID_AUDITORIA_SIRC")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sq_auditoria")
	@SequenceGenerator(name = "sq_auditoria", sequenceName = "sq_auditoria", allocationSize=1)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "ID_TRANSACCION")
	private TarjetacontrolEstadoEntity idTransaccion;

	@ManyToOne
	@JoinColumn(name = "ID_EMPRESA")
	private EmpresaEntity empresa;

	@ManyToOne
	@JoinColumn(name = "ID_CONDUCTOR")
	private ConductorEntity conductor;

	@Column(name = "USUARIO")
	private String usuario;

	@Column(name = "FECHA_EVENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEvento;

	@Column(name = "OBSERVACION")
	private String observacion;
	
	@Column(name= "ORIGEN")
	private String origen;
	
	@Column(name= "TARJETA_CONTROL")
	private long tarjetaControl;
	
	@Column(name= "PLACA")
	private String placa;

	public AuditoriaSircEntity() {
	}

	public AuditoriaSircEntity(long id, TarjetacontrolEstadoEntity idTransaccion, EmpresaEntity empresa,
			ConductorEntity conductor, String usuario, Date fechaEvento,
			String observacion, String origen, long tarjetaControl, String placa) {
		super();
		this.id = id;
		this.idTransaccion = idTransaccion;
		this.empresa = empresa;
		this.conductor = conductor;
		this.usuario = usuario;
		this.fechaEvento = fechaEvento;
		this.observacion = observacion;
		this.origen = origen;
		this.tarjetaControl = tarjetaControl;
		this.placa = placa;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EmpresaEntity getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaEntity empresa) {
		this.empresa = empresa;
	}

	public TarjetacontrolEstadoEntity getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(TarjetacontrolEstadoEntity idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public ConductorEntity getConductor() {
		return conductor;
	}

	public void setConductor(ConductorEntity conductor) {
		this.conductor = conductor;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public long getTarjetaControl() {
		return tarjetaControl;
	}

	public void setTarjetaControl(long tarjetaControl) {
		this.tarjetaControl = tarjetaControl;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
}
