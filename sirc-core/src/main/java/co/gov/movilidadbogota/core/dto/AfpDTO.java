package co.gov.movilidadbogota.core.dto;

public class AfpDTO extends AbstractDTO implements Comparable<AfpDTO> {

    private long idAfp;
    private String nombreAfp;

    public AfpDTO() {
    }

    public AfpDTO(long idAfp, String nombreAfp) {
        this.idAfp = idAfp;
        this.nombreAfp = nombreAfp;
    }

    public long getIdAfp() {
        return idAfp;
    }

    public void setIdAfp(long idAfp) {
        this.idAfp = idAfp;
    }

    public String getNombreAfp() {
        return nombreAfp;
    }

    public void setNombreAfp(String nombreAfp) {
        this.nombreAfp = nombreAfp;
    }

    @Override
    public int compareTo(AfpDTO o) {
        return nombreAfp.compareTo(o.nombreAfp);
    }

}
