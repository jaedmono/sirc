package co.gov.movilidadbogota.core.dto;

import java.util.List;

public class EmpresaDTO extends AbstractDTO implements Comparable<EmpresaDTO> {

    private Long id;
    private String codigoEmpresa;
    private String razonSocial;
    private String nit;
    private List<NotificationDTO> notificationDTOs;
    private List<UsuarioDTO> usuarioDTOs;

    public EmpresaDTO() {
    }

    public EmpresaDTO(Long id, String codigoEmpresa) {
        super();
        this.id = id;
        this.codigoEmpresa = codigoEmpresa;
    }

    public EmpresaDTO(Long id, String codigoEmpresa, List<NotificationDTO> notificationDTOs,
            List<UsuarioDTO> usuarioDTOs) {
        super();
        this.id = id;
        this.codigoEmpresa = codigoEmpresa;
        this.notificationDTOs = notificationDTOs;
        this.usuarioDTOs = usuarioDTOs;
    }

    public EmpresaDTO(Long id, String codigoEmpresa, String razonSocial, String nit) {
        super();
        this.id = id;
        this.codigoEmpresa = codigoEmpresa;
        this.razonSocial = razonSocial;
        this.nit = nit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<NotificationDTO> getNotificationDTOs() {
        return notificationDTOs;
    }

    public void setNotificationDTOs(List<NotificationDTO> notificationDTOs) {
        this.notificationDTOs = notificationDTOs;
    }

    public List<UsuarioDTO> getUsuarioDTOs() {
        return usuarioDTOs;
    }

    public void setUsuarioDTOs(List<UsuarioDTO> usuarioDTOs) {
        this.usuarioDTOs = usuarioDTOs;
    }

    @Override
    public int compareTo(EmpresaDTO o) {
        return razonSocial.compareTo(o.razonSocial);
    }

}
