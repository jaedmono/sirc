package co.gov.movilidadbogota.util;

public enum OrigenTransaccionEnum {

	WEB_SERVICE("WEB SERVICE"), WEB("WEB"), SYSTEM("SYSTEM");

	private String pk;

	private OrigenTransaccionEnum(String pk) {
		this.pk = pk;
	}

	public String getPk() {
		return pk;
	}

        
}
