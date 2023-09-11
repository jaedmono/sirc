package co.gov.movilidadbogota.core.dto;

import co.gov.movilidadbogota.core.dao.ConductorVehiculoDAO;
import co.gov.movilidadbogota.core.entity.EmpresaEntity;

public class NotificationDTO {

	private String tarjetaControl;
	private Long numeroDocumento;
	private String nombres;
	private String placa;
	private String fecha;
	private Integer dias;
	private Long idEmpresa;
	private Long idVehiculo;

	public NotificationDTO() {
	}

	public NotificationDTO(String tarjetaControl, Long numeroDocumento, String nombres, String placa, String fecha,
			Integer dias) {
		this.tarjetaControl = tarjetaControl;
		this.numeroDocumento = numeroDocumento;
		this.nombres = nombres;
		this.placa = placa;
		this.fecha = fecha;
		this.dias = dias;
	}

	public NotificationDTO(String tarjetaControl) {
		super();
		this.tarjetaControl = tarjetaControl;
	}

	public String getTarjetaControl() {
		return tarjetaControl;
	}

	public void setTarjetaControl(String tarjetaControl) {
		this.tarjetaControl = tarjetaControl;
	}

	public Long getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(Long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

}
