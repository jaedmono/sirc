package co.gov.movilidadbogota.core.util;

public enum TipoTransaccion {

	EXPEDICION(1), REFRENDACION(2), CANCELACION(3);

	private int value;

	public int getValue() {
		return value;
	}

	private TipoTransaccion(int value) {
		this.value = value;
	}

}
