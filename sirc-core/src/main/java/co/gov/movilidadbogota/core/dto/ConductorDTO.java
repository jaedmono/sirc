package co.gov.movilidadbogota.core.dto;

public class ConductorDTO extends AbstractDTO {

    private long id;
    private String grupoSanguineo;
    private String factorRh;
    private long idEps;
    private long idArl;
    private long idAfp = -1;
    private String uriFoto;
    private String foto;
    private PersonaDTO persona;

    public ConductorDTO() {
    }

    public ConductorDTO(long id, String grupoSanguineo, String factorRh, long idEps, long idArl, long idAfp,
            String uriFoto) {
        super();
        this.id = id;
        this.grupoSanguineo = grupoSanguineo;
        this.factorRh = factorRh;
        this.idEps = idEps;
        this.idArl = idArl;
        this.idAfp = idAfp;
        this.uriFoto = uriFoto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getFactorRh() {
        return factorRh;
    }

    public void setFactorRh(String factorRh) {
        this.factorRh = factorRh;
    }

    public long getIdEps() {
        return idEps;
    }

    public void setIdEps(long idEps) {
        this.idEps = idEps;
    }

    public long getIdArl() {
        return idArl;
    }

    public void setIdArl(long idArl) {
        this.idArl = idArl;
    }

    public long getIdAfp() {
        return idAfp;
    }

    public void setIdAfp(long idAfp) {
        this.idAfp = idAfp;
    }

    public String getUriFoto() {
        return uriFoto;
    }

    public void setUriFoto(String uriFoto) {
        this.uriFoto = uriFoto;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
