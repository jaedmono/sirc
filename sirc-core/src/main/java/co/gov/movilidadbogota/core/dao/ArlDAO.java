package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.ArlEntity;

@Repository
public class ArlDAO extends AbstractDAO<ArlEntity> {

	public ArlDAO() {
		super(ArlEntity.class);
	}
}
