package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.PerfilOpcionMenuEntity;

@Repository
public class PerfilOpcionMenuDAO extends AbstractDAO<PerfilOpcionMenuEntity> {

	public PerfilOpcionMenuDAO() {
		super(PerfilOpcionMenuEntity.class);
	}
}
