package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.EpsEntity;

@Repository
public class EpsDAO extends AbstractDAO<EpsEntity> {

	public EpsDAO() {
		super(EpsEntity.class);
	}
}
