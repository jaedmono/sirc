package co.gov.movilidadbogota.core.dto;

import java.io.Serializable;
import java.util.Date;

public class ReportRefrenderDto implements Serializable {

	private String tarjetaControl;

	private String placaSerialVehiculo;

	private Date fechaExpedicion;

	private Date fechaValidez;

	private Date fechaVigencia;

	private String nombreRazonSocial;

	private String nombreConductor;

	private String apellidosConductor;

	private String estado;

	private Date fechaRefrendacion;

	public ReportRefrenderDto(String tarjetaControl, String placaSerialVehiculo, String nombreRazonSocial,
			String nombreConductor, String apellidosConductor, String estado, Date fechaExpedicion, Date fechaValidez,
			Date fechaVigencia, Date fechaRefrendacion) {
		super();
		this.tarjetaControl = tarjetaControl;
		this.placaSerialVehiculo = placaSerialVehiculo;
		this.fechaExpedicion = fechaExpedicion;
		this.fechaValidez = fechaValidez;
		this.fechaVigencia = fechaVigencia;
		this.nombreRazonSocial = nombreRazonSocial;
		this.nombreConductor = nombreConductor;
		this.apellidosConductor = apellidosConductor;
		this.estado = estado;
		this.fechaRefrendacion = fechaRefrendacion;
	}

	public String getTarjetaControl() {
		return tarjetaControl;
	}

	public void setTarjetaControl(String tarjetaControl) {
		this.tarjetaControl = tarjetaControl;
	}

	public String getPlacaSerialVehiculo() {
		return placaSerialVehiculo;
	}

	public void setPlacaSerialVehiculo(String placaSerialVehiculo) {
		this.placaSerialVehiculo = placaSerialVehiculo;
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

	public Date getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public String getNombreConductor() {
		return nombreConductor;
	}

	public void setNombreConductor(String nombreConductor) {
		this.nombreConductor = nombreConductor;
	}

	public String getApellidosConductor() {
		return apellidosConductor;
	}

	public void setApellidosConductor(String apellidosConductor) {
		this.apellidosConductor = apellidosConductor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaRefrendacion() {
		return fechaRefrendacion;
	}

	public void setFechaRefrendacion(Date fechaRefrendacion) {
		this.fechaRefrendacion = fechaRefrendacion;
	}

}
