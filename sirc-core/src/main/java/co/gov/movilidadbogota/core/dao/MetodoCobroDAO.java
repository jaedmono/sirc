package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.MetodoCobroEntity;

@Repository
public class MetodoCobroDAO extends AbstractDAO<MetodoCobroEntity> {

    public MetodoCobroDAO() {
        super(MetodoCobroEntity.class);
    }
}
