package co.gov.movilidadbogota.web.util;

public enum TipoUsuarioEnum {
	
	EXTERNO(Long.valueOf(1)),
	INTERNO(Long.valueOf(2));
	
	private Long code;

	private TipoUsuarioEnum(Long code) {
		this.setCode(code);
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}
	
	
}
