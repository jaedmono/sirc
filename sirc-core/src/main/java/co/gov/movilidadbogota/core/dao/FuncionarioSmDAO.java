package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.FuncionarioSmEntity;

@Repository
public class FuncionarioSmDAO extends AbstractDAO<FuncionarioSmEntity> {

	public FuncionarioSmDAO() {
		super(FuncionarioSmEntity.class);
	}
}
