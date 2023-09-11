package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.TipoAplicacionEntity;

@Repository
public class TipoAplicacionDAO extends AbstractDAO<TipoAplicacionEntity> {

	public TipoAplicacionDAO() {
		super(TipoAplicacionEntity.class);
	}
}
