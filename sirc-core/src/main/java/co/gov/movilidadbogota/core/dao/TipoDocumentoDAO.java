package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.TipoDocumentoEntity;

@Repository
public class TipoDocumentoDAO extends AbstractDAO<TipoDocumentoEntity> {

	public TipoDocumentoDAO() {
		super(TipoDocumentoEntity.class);
	}
}
