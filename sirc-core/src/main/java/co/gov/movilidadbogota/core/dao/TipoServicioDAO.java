package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.TipoServicioEntity;

@Repository
public class TipoServicioDAO extends AbstractDAO<TipoServicioEntity> {

    public TipoServicioDAO() {
        super(TipoServicioEntity.class);
    }
}
