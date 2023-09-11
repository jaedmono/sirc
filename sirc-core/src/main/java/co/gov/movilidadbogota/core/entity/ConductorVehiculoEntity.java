package co.gov.movilidadbogota.core.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "SMB_CONDUCTOR_VEHICULO")
public class ConductorVehiculoEntity {

	@Id
	@Column(name = "ID_VEHICULO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_vehiculo")
	@SequenceGenerator(name = "sq_vehiculo", sequenceName = "sq_vehiculo", allocationSize = 1)
	private long id;

	@ManyToOne
	@JoinColumn(name = "ID_CONDUCTOR")
	private ConductorEntity conductor;

	@Column(name = "TARJETA_CONTROL")
	private String tarjetaControl;

	@ManyToOne
	@JoinColumn(name = "ID_EMPRESA")
	private EmpresaEntity empresa;

	@Column(name = "TIPO_TRANSACCION")
	private String tipoTransaccion;

	@Column(name = "FECHA_EXPEDICION")
	private Date fechaExpedicion;

	@Column(name = "FECHA_VALIDEZ")
	private Date fechaValidez;

	@Column(name = "FECHA_VIGENCIA")
	private Date fechaVigencia;

	@Column(name = "PLACA_SERIAL_VEHICULO")
	private String placaSerialVehiculo;

	@ManyToOne
	@JoinColumn(name = "ID_ESTADO")
	private TarjetacontrolEstadoEntity idEstado;

	@Column(name = "NOTIFADO", nullable = false)
	private boolean notificated;

	@OneToOne
	@JoinColumn(name = "ID_FACTORCALIDAD")
	private VehiculoFactorCalidadEntity factorCalidad;

	@OneToOne
	@JoinColumn(name = "ID_METODOCOBRO")
	private MetodoCobroEntity metodoPago;

	@OneToOne
	@JoinColumn(name = "ID_TIPOSERVICIO")
	private TipoServicioEntity tipoServicio;

	@Column(name = "FECHA_VENCIMIENTO_SOAT")
	private Date fechaVencimientoSoat;

	@Column(name = "NRO_SOAT")
	private String nroSOAT;

	@Column(name = "FECHA_VENCIMIENTO_RTM")
	private Date fechaVencimientoRtm;

	@Column(name = "NRO_RTM")
	private String nroRTM;

	@Column(name = "NRO_TARJETA_OPERACION")
	private String nroTarjetaOperacion;

	@Column(name = "FECHA_VENCIMIENTO_TO")
	private Date fechaVencimientoTO;

	@OneToMany
	@JoinColumn(name = "ID_VEHICULO")
	private List<RefrendacionHistoricoEntity> refrendacionHistorico;

	public ConductorVehiculoEntity() {
	}

	public ConductorVehiculoEntity(long id, ConductorEntity conductor, String tarjetaControl, EmpresaEntity empresa,
			String tipoTransaccion, Date fechaExpedicion, Date fechaValidez, String placaSerialVehiculo,
			TarjetacontrolEstadoEntity idEstado) {
		super();
		this.id = id;
		this.conductor = conductor;
		this.tarjetaControl = tarjetaControl;
		this.empresa = empresa;
		this.tipoTransaccion = tipoTransaccion;
		this.fechaExpedicion = fechaExpedicion;
		this.fechaValidez = fechaValidez;
		this.placaSerialVehiculo = placaSerialVehiculo;
		this.idEstado = idEstado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ConductorEntity getConductor() {
		return conductor;
	}

	public void setConductor(ConductorEntity conductor) {
		this.conductor = conductor;
	}

	public String getTarjetaControl() {
		return tarjetaControl;
	}

	public void setTarjetaControl(String tarjetaControl) {
		this.tarjetaControl = tarjetaControl;
	}

	public EmpresaEntity getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaEntity empresa) {
		this.empresa = empresa;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public Date getFechaValidez() {
		return fechaValidez;
	}

	public void setFechaValidez(Date fechaValidez) {
		this.fechaValidez = fechaValidez;
	}

	public String getPlacaSerialVehiculo() {
		return placaSerialVehiculo;
	}

	public void setPlacaSerialVehiculo(String placaSerialVehiculo) {
		this.placaSerialVehiculo = placaSerialVehiculo;
	}

	public TarjetacontrolEstadoEntity getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(TarjetacontrolEstadoEntity idEstado) {
		this.idEstado = idEstado;
	}

	public Date getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public boolean isNotificated() {
		return notificated;
	}

	public void setNotificated(boolean notificated) {
		this.notificated = notificated;
	}

	public VehiculoFactorCalidadEntity getFactorCalidad() {
		return factorCalidad;
	}

	public void setFactorCalidad(VehiculoFactorCalidadEntity factorCalidad) {
		this.factorCalidad = factorCalidad;
	}

	public MetodoCobroEntity getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoCobroEntity metodoPago) {
		this.metodoPago = metodoPago;
	}

	public TipoServicioEntity getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(TipoServicioEntity tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public Date getFechaVencimientoSoat() {
		return fechaVencimientoSoat;
	}

	public void setFechaVencimientoSoat(Date fechaVencimientoSoat) {
		this.fechaVencimientoSoat = fechaVencimientoSoat;
	}

	public String getNroSOAT() {
		return nroSOAT;
	}

	public void setNroSOAT(String nroSOAT) {
		this.nroSOAT = nroSOAT;
	}

	public Date getFechaVencimientoRtm() {
		return fechaVencimientoRtm;
	}

	public void setFechaVencimientoRtm(Date fechaVencimientoRtm) {
		this.fechaVencimientoRtm = fechaVencimientoRtm;
	}

	public String getNroRTM() {
		return nroRTM;
	}

	public void setNroRTM(String nroRTM) {
		this.nroRTM = nroRTM;
	}

	public String getNroTarjetaOperacion() {
		return nroTarjetaOperacion;
	}

	public void setNroTarjetaOperacion(String nroTarjetaOperacion) {
		this.nroTarjetaOperacion = nroTarjetaOperacion;
	}

	public Date getFechaVencimientoTO() {
		return fechaVencimientoTO;
	}

	public void setFechaVencimientoTO(Date fechaVencimientoTO) {
		this.fechaVencimientoTO = fechaVencimientoTO;
	}

	public List<RefrendacionHistoricoEntity> getRefrendacionHistorico() {
		return refrendacionHistorico;
	}

	public void setRefrendacionHistorico(List<RefrendacionHistoricoEntity> refrendacionHistorico) {
		this.refrendacionHistorico = refrendacionHistorico;
	}

}
