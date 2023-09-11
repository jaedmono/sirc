package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.ControlAlertaEntity;

@Repository
public class ControlAlertaDAO extends AbstractDAO<ControlAlertaEntity> {

	public ControlAlertaDAO() {
		super(ControlAlertaEntity.class);
	}
}
