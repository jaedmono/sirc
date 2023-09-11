package co.gov.movilidadbogota.core.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import co.gov.movilidadbogota.core.entity.PersonaEntity;
import co.gov.movilidadbogota.core.entity.PersonaEntity_;
import co.gov.movilidadbogota.core.entity.TipoDocumentoEntity_;

@Repository
public class PersonaDAO extends AbstractDAO<PersonaEntity> {
	
	@PersistenceContext
	private EntityManager entityManager;

	public PersonaDAO() {
		super(PersonaEntity.class);
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonaDAO.class+LOG_PREFIX);
	
	public PersonaEntity findByNumeroDocumentoIdTipoDocumento(Long numeroDocumento, Long idTipoDocumento) {

		PersonaEntity personaEntity = null;

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<PersonaEntity> criteriaQuery = criteriaBuilder
					.createQuery(PersonaEntity.class);
			Root<PersonaEntity> root = criteriaQuery.from(PersonaEntity.class);
			criteriaQuery.where(
					criteriaBuilder.equal(root.get(PersonaEntity_.numeroDocumento),
							numeroDocumento),
					criteriaBuilder.equal(root.get(PersonaEntity_.tipoDocumento).get(TipoDocumentoEntity_.id),
							idTipoDocumento));
			try {
				personaEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
				return personaEntity;
			} catch (NoResultException arg6) {
				return null;
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return personaEntity;
	}
}
