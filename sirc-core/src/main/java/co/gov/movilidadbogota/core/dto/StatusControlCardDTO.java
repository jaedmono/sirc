package co.gov.movilidadbogota.core.dto;

public class StatusControlCardDTO extends AbstractDTO implements Comparable<StatusControlCardDTO>{

    private long id;
    private String description;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(StatusControlCardDTO o) {
        return description.compareTo(o.description);
    }
}
