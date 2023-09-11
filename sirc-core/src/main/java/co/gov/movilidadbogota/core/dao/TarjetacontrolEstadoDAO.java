package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.TarjetacontrolEstadoEntity;

@Repository
public class TarjetacontrolEstadoDAO extends AbstractDAO<TarjetacontrolEstadoEntity> {

	public TarjetacontrolEstadoDAO() {
		super(TarjetacontrolEstadoEntity.class);
	}
}
