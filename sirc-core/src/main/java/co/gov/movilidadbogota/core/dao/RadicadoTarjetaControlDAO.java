package co.gov.movilidadbogota.core.dao;


import co.gov.movilidadbogota.core.entity.RadicadoTarjetaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RadicadoTarjetaControlDAO extends AbstractDAO<RadicadoTarjetaEntity>{

    public RadicadoTarjetaControlDAO(){
        super(RadicadoTarjetaEntity.class);
    }
}
