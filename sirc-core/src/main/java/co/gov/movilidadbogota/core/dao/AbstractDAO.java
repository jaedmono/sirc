package co.gov.movilidadbogota.core.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class AbstractDAO<T> {

	public final static String LOG_PREFIX="[SIRC]";
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDAO.class+LOG_PREFIX);

	@PersistenceContext
	private EntityManager entityManager;
	private final Class<T> entityClass;

	public AbstractDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	// Create
	@Transactional
	public void create(T t) {
		entityManager.persist(t);
	}

	// Retrieve
	@Transactional
	public T findByPrimaryKey(Long primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {

		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(entityClass);
			Root<T> rootEntry = cq.from(entityClass);
			CriteriaQuery<T> all = cq.select(rootEntry);
			TypedQuery<T> allQuery = entityManager.createQuery(all);
			return allQuery.getResultList();

		} catch (NoResultException e) {
			LOGGER.error("Excepcion de Persistencia", e);

		} catch (PersistenceException pe) {
			LOGGER.error("Excepcion de Persistencia", pe);
		}
		return Collections.emptyList();
	}

	// Update
	@Transactional
	public void update(T t) {
		entityManager.merge(t);
	}

	// Delete
	@Transactional
	public void delete(long id) {
		entityManager.remove(entityManager.find(entityClass, id));
	}

	public <V> T findOneByAttribute(SingularAttribute<T, V> attribute, V value) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
		Root type = criteriaQuery.from(this.entityClass);
		criteriaQuery.where(criteriaBuilder.equal(type.get(attribute), value));

		try {
			TypedQuery<T> allQuery = entityManager.createQuery(criteriaQuery);
			return allQuery.getSingleResult();
		} catch (NoResultException arg6) {// 135
			return null;// 136
		}
	}

}
