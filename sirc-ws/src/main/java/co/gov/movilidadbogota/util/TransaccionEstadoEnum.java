package co.gov.movilidadbogota.util;

public enum TransaccionEstadoEnum {

	FALLIDO("FALLIDO"), EXITOSO("EXITOSO");

	private String pk;

	private TransaccionEstadoEnum(String pk) {
		this.pk = pk;
	}

	public String getPk() {
		return pk;
	}

        
}
