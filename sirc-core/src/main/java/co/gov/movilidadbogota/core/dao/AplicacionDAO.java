package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.AplicacionEntity;

@Repository
public class AplicacionDAO extends AbstractDAO<AplicacionEntity> {

	public AplicacionDAO() {
		super(AplicacionEntity.class);
	}
}
