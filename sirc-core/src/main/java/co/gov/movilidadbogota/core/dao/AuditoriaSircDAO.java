package co.gov.movilidadbogota.core.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.gov.movilidadbogota.core.entity.AuditoriaSircEntity;

@Repository
public class AuditoriaSircDAO extends AbstractDAO<AuditoriaSircEntity> {

	public AuditoriaSircDAO() {
		super(AuditoriaSircEntity.class);
	}
	
	@Transactional
	public void crearRegistroLog (AuditoriaSircEntity entity){
		create(entity);
	}
}
