package co.gov.movilidadbogota.core.dto;

public class ArlDTO extends AbstractDTO implements Comparable<ArlDTO> {

    private long idArl;
    private String nombreArl;

    public ArlDTO() {
    }

    public ArlDTO(long idArl, String nombreArl) {
        this.idArl = idArl;
        this.nombreArl = nombreArl;
    }

    public long getIdArl() {
        return idArl;
    }

    public void setIdArl(long idArl) {
        this.idArl = idArl;
    }

    public String getNombreArl() {
        return nombreArl;
    }

    public void setNombreArl(String nombreArl) {
        this.nombreArl = nombreArl;
    }

    @Override
    public int compareTo(ArlDTO o) {
        return nombreArl.compareTo(o.nombreArl);
    }

}
