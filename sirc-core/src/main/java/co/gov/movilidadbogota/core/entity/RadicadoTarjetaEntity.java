package co.gov.movilidadbogota.core.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SMB_RADICADO_TARJETA_CONTROL")
public class RadicadoTarjetaEntity implements Serializable {

    @Id
    @Column(name = "ID_RADICADO_TARJETA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_radicado_tarjeta")
    @SequenceGenerator(name = "sq_radicado_tarjeta", sequenceName = "sq_radicado_tarjeta", allocationSize = 1)
    private long id;

    @Column(name = "ID_RADICADO")
    private long idRadicado;

    @Column(name = "TARJETA_CONTROL")
    private String tarjetaControl;

    public RadicadoTarjetaEntity() {}

    public RadicadoTarjetaEntity(long id, long idRadicado, String tarjetaControl) {
        super();
        this.id = id;
        this.idRadicado = idRadicado;
        this.tarjetaControl = tarjetaControl;
    }

    public long getId() {
        return id;
    }

    public String getTarjetaControl() {
        return tarjetaControl;
    }

    public long getIdRadicado() {
        return idRadicado;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTarjetaControl(String tarjetaControl) {
        this.tarjetaControl = tarjetaControl;
    }

    public void setIdRadicado(long idRadicado) {
        this.idRadicado = idRadicado;
    }
}
