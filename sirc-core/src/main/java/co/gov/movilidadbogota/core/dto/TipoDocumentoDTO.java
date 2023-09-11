package co.gov.movilidadbogota.core.dto;

public class TipoDocumentoDTO extends AbstractDTO implements Comparable<TipoDocumentoDTO> {

    private long id;
    private String descripcionTipoDocumento;

    public TipoDocumentoDTO() {
    }

    public TipoDocumentoDTO(long id, String descripcionTipoDocumento) {
        this.id = id;
        this.descripcionTipoDocumento = descripcionTipoDocumento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcionTipoDocumento() {
        return descripcionTipoDocumento;
    }

    public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
        this.descripcionTipoDocumento = descripcionTipoDocumento;
    }

    @Override
    public int compareTo(TipoDocumentoDTO o) {
        return descripcionTipoDocumento.compareTo(o.descripcionTipoDocumento);
    }

}
