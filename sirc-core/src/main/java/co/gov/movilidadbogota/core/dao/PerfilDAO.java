package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.PerfilEntity;

@Repository
public class PerfilDAO extends AbstractDAO<PerfilEntity> {

	public PerfilDAO() {
		super(PerfilEntity.class);
	}
}
