package co.gov.movilidadbogota.core.dto;

public class ParametroDTO {

	private long id;

	private String codigoParametro;

	private String valorParametro;

	private String descripcionParametro;

	private long idEstado;

	public ParametroDTO() {
	}

	public ParametroDTO(String codigoParametro, String valorParametro, String descripcionParametro) {
		super();
		this.codigoParametro = codigoParametro;
		this.valorParametro = valorParametro;
		this.descripcionParametro = descripcionParametro;
	}

	public ParametroDTO(long id, String codigoParametro, String valorParametro, String descripcionParametro) {
		super();
		this.id = id;
		this.codigoParametro = codigoParametro;
		this.valorParametro = valorParametro;
		this.descripcionParametro = descripcionParametro;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigoParametro() {
		return codigoParametro;
	}

	public void setCodigoParametro(String codigoParametro) {
		this.codigoParametro = codigoParametro;
	}

	public String getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}

	public String getDescripcionParametro() {
		return descripcionParametro;
	}

	public void setDescripcionParametro(String descripcionParametro) {
		this.descripcionParametro = descripcionParametro;
	}

	public long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}

}
