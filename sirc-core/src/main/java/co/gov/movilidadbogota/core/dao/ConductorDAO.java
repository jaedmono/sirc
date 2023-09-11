package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.ConductorEntity;

@Repository
public class ConductorDAO extends AbstractDAO<ConductorEntity> {

	public ConductorDAO() {
		super(ConductorEntity.class);
	}
}
