package co.gov.movilidadbogota.web.util;

public enum PerfilUsuarioEnum {
	
	ROLE_EXTERNAL(Long.valueOf(1)),
	ROLE_SDM(Long.valueOf(2));
	
	private Long code;

	private PerfilUsuarioEnum(Long code) {
		this.setCode(code);
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}
	
	
}
