package co.gov.movilidadbogota.core.dto;

public class EpsDTO extends AbstractDTO implements Comparable<EpsDTO> {

    private long idEps;
    private String nombreEps;

    public EpsDTO() {
    }

    public EpsDTO(long idEps, String nombreEps) {
        this.idEps = idEps;
        this.nombreEps = nombreEps;
    }

    public long getIdEps() {
        return idEps;
    }

    public void setIdEps(long idEps) {
        this.idEps = idEps;
    }

    public String getNombreEps() {
        return nombreEps;
    }

    public void setNombreEps(String nombreEps) {
        this.nombreEps = nombreEps;
    }

    @Override
    public int compareTo(EpsDTO o) {
        return nombreEps.compareTo(o.nombreEps);
    }

}
