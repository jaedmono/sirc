package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.OpcionMenuEntity;

@Repository
public class OpcionMenuDAO extends AbstractDAO<OpcionMenuEntity> {

	public OpcionMenuDAO() {
		super(OpcionMenuEntity.class);
	}
}
