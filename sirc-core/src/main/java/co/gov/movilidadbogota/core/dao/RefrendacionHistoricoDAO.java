package co.gov.movilidadbogota.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import co.gov.movilidadbogota.core.entity.RefrendacionHistoricoEntity;

@Repository
public class RefrendacionHistoricoDAO extends AbstractDAO<RefrendacionHistoricoEntity>{

	private static final Logger LOGGER = LoggerFactory.getLogger(RefrendacionHistoricoDAO.class+LOG_PREFIX);

	public RefrendacionHistoricoDAO() {
		super(RefrendacionHistoricoEntity.class);
	}
}
