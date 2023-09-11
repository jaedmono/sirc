package co.gov.movilidadbogota.core.util;

public enum EstadoTarjetaControlEnum {
    
    EXPEDIDA(1), REFRENDADA(2), CANCELADO(3), SIN_VIGENCIA(4), SIN_RENOVACION(5), EXEDE_FECHA_VALIDEZ(6);
    
    private long pk;

    private EstadoTarjetaControlEnum(long pk) {
        this.pk = pk;
    }

    public long getPk() {
        return pk;
    }
}
