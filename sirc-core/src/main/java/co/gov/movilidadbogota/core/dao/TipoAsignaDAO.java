package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.TipoAsignaEntity;

@Repository
public class TipoAsignaDAO extends AbstractDAO<TipoAsignaEntity> {

	public TipoAsignaDAO() {
		super(TipoAsignaEntity.class);
	}
}
