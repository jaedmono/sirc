package co.gov.movilidadbogota.core.dto;

import java.util.Date;

public class PagoPilaDTO extends AbstractDTO {

	private String numeroAprobacion;
	private String periodoPago;
	private Long operadorPila;

	public PagoPilaDTO() {
	}

	public PagoPilaDTO(String numeroAprobacion, Date periodoPago, Long operadorPila) {
		this.numeroAprobacion = numeroAprobacion;
		this.periodoPago = periodoPago.toString();
                this.operadorPila = operadorPila;
	}

	public String getNumeroAprobacion() {
		return numeroAprobacion;
	}

	public void setNumeroAprobacion(String numeroAprobacion) {
		this.numeroAprobacion = numeroAprobacion;
	}

	public Date getPeriodoPago() {
		return new Date(periodoPago);
	}
        
        public String getStringPeriodoPago() {
		return periodoPago;
	}
        
        public void setStringPeriodoPago(String periodoPago) {
		this.periodoPago = periodoPago;
	}

	public void setPeriodoPago(String periodoPago) {
		this.periodoPago = periodoPago;
	}

        public Long getOperadorPila() {
            return operadorPila;
        }

        public void setOperadorPila(Long operadorPila) {
            this.operadorPila = operadorPila;
        }

}