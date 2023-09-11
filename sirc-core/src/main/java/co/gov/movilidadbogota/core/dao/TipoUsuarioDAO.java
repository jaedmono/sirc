package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.TipoUsuarioEntity;

@Repository
public class TipoUsuarioDAO extends AbstractDAO<TipoUsuarioEntity> {

	public TipoUsuarioDAO() {
		super(TipoUsuarioEntity.class);
	}
}
