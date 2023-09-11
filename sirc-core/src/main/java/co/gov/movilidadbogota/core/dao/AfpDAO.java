package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.AfpEntity;

@Repository
public class AfpDAO extends AbstractDAO<AfpEntity> {

	public AfpDAO() {
		super(AfpEntity.class);
	}
}
