package co.gov.movilidadbogota.util;

public enum FactorRHEnum {

	POSITIVO("+"), NEGATIVO("-");

	private String pk;

	private FactorRHEnum(String pk) {
		this.pk = pk;
	}

	public String getPk() {
		return pk;
	}

        
}
