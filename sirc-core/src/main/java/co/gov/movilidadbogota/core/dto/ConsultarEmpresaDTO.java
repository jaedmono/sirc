package co.gov.movilidadbogota.core.dto;

public class ConsultarEmpresaDTO {
	
	private long id;
	private String nombreRazonSocial;
	
	public ConsultarEmpresaDTO(long id, String nombreRazonSocial) {
		this.id = id;
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	
	

}
