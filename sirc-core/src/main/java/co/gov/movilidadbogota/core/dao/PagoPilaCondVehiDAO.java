package co.gov.movilidadbogota.core.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import co.gov.movilidadbogota.core.dto.NotificationDTO;
import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity_;
import co.gov.movilidadbogota.core.entity.PagoPilaCondVehiEntity;
import co.gov.movilidadbogota.core.entity.PagoPilaCondVehiEntity_;
import co.gov.movilidadbogota.core.util.DateUtil;
import javax.persistence.criteria.Path;

@Repository
public class PagoPilaCondVehiDAO extends AbstractDAO<PagoPilaCondVehiEntity> {

	public PagoPilaCondVehiDAO() {
		super(PagoPilaCondVehiEntity.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(PagoPilaCondVehiDAO.class+LOG_PREFIX);

	public List<NotificationDTO> findByStateOutOfValidateDate(boolean state, Date date) {

		List<NotificationDTO> empresaVehiculoList = new ArrayList<>();

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<PagoPilaCondVehiEntity> criteriaQuery = criteriaBuilder
					.createQuery(PagoPilaCondVehiEntity.class);
			Root<PagoPilaCondVehiEntity> root = criteriaQuery.from(PagoPilaCondVehiEntity.class);
			Path<Date> path = root.get(PagoPilaCondVehiEntity_.fechaFinVigencia);
                        criteriaQuery.where(criteriaBuilder.equal(root.get(PagoPilaCondVehiEntity_.idEstado), state),
					criteriaBuilder.lessThan(path, date));
			try {
				List<PagoPilaCondVehiEntity> PagoPilaCondVehiList = (List<PagoPilaCondVehiEntity>) entityManager
						.createQuery(criteriaQuery).getResultList();

				empresaVehiculoList = entityToDto(PagoPilaCondVehiList, date);

				return empresaVehiculoList;
			} catch (NoResultException arg6) {
				LOGGER.error("Excepcion de Persistencia ", arg6);
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("Excepcion ", e);
			return null;
		}
	}

	public Long countByStateOutOfValidateDate(boolean state, Date date) {

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<PagoPilaCondVehiEntity> root = criteriaQuery.from(PagoPilaCondVehiEntity.class);
			criteriaQuery.select(criteriaBuilder.count(root));
			Path<Date> path = root.get(PagoPilaCondVehiEntity_.fechaFinVigencia);
                        criteriaQuery.where(criteriaBuilder.equal(root.get(PagoPilaCondVehiEntity_.idEstado), state),
					criteriaBuilder.lessThan(path, date));
			try {
				Long numberResults = entityManager.createQuery(criteriaQuery).getSingleResult();
				return numberResults;
			} catch (NoResultException arg6) {
				LOGGER.error("Excepcion de Persistencia ", arg6);
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("Excepcion ", e);
			return null;
		}
	}

	public List<NotificationDTO> findByStateBeforeToExpire(boolean state, Date date) {

		List<NotificationDTO> empresaVehiculoList = new ArrayList<>();

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<PagoPilaCondVehiEntity> criteriaQuery = criteriaBuilder
					.createQuery(PagoPilaCondVehiEntity.class);
			Root<PagoPilaCondVehiEntity> root = criteriaQuery.from(PagoPilaCondVehiEntity.class);
			Path<Date> path = root.get(PagoPilaCondVehiEntity_.fechaFinVigencia);
                        criteriaQuery.where(criteriaBuilder.equal(root.get(PagoPilaCondVehiEntity_.idEstado), state),
					criteriaBuilder.lessThanOrEqualTo(path, date),
					criteriaBuilder.greaterThanOrEqualTo(path,
							new Date()));
			try {
				List<PagoPilaCondVehiEntity> PagoPilaCondVehiList = (List<PagoPilaCondVehiEntity>) entityManager
						.createQuery(criteriaQuery).getResultList();

				empresaVehiculoList = entityToDto(PagoPilaCondVehiList, date);

				return empresaVehiculoList;
			} catch (NoResultException arg6) {

				LOGGER.error("Excepcion de Persistencia ", arg6);
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("Excepcion ", e);
			return null;
		}
	}

	public Long countByStateBeforeToExpire(boolean state, Date date) {

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<PagoPilaCondVehiEntity> root = criteriaQuery.from(PagoPilaCondVehiEntity.class);
			criteriaQuery.select(criteriaBuilder.count(root));
			Path<Date> path = root.get(PagoPilaCondVehiEntity_.fechaFinVigencia);
                        criteriaQuery.where(criteriaBuilder.equal(root.get(PagoPilaCondVehiEntity_.idEstado), state),
					criteriaBuilder.lessThanOrEqualTo(path, date),
					criteriaBuilder.greaterThanOrEqualTo(path,
							new Date()));
			try {
				Long numberResults = entityManager.createQuery(criteriaQuery).getSingleResult();
				return numberResults;
			} catch (NoResultException arg6) {
				LOGGER.error("Excepcion de Persistencia ", arg6);
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("Excepcion ", e);
			return null;
		}
	}

	public List<NotificationDTO> entityToDto(List<PagoPilaCondVehiEntity> PagoPilaCondVehiList, Date date) {

		List<NotificationDTO> empresaVehiculoList = new ArrayList<>();

		try {

			for (PagoPilaCondVehiEntity pagoPilaCondVehi : PagoPilaCondVehiList) {

				String nombres = pagoPilaCondVehi.getVehiculo().getConductor().getPersona().getNombres() + " "
						+ pagoPilaCondVehi.getVehiculo().getConductor().getPersona().getApellidos();

				int days = Math.abs(DateUtil.daysBetween(pagoPilaCondVehi.getFechaFinVigencia(), new Date()));

				String formattedDate = new SimpleDateFormat("dd/MM/yyyy")
						.format(pagoPilaCondVehi.getFechaFinVigencia());

				NotificationDTO tarjetaControlSinVigencia = new NotificationDTO();
				tarjetaControlSinVigencia.setDias(days);
				tarjetaControlSinVigencia.setFecha(formattedDate);
				tarjetaControlSinVigencia.setNombres(nombres);
				tarjetaControlSinVigencia.setNumeroDocumento(
						pagoPilaCondVehi.getVehiculo().getConductor().getPersona().getNumeroDocumento());
				tarjetaControlSinVigencia.setPlaca(pagoPilaCondVehi.getVehiculo().getPlacaSerialVehiculo());
				tarjetaControlSinVigencia.setTarjetaControl(pagoPilaCondVehi.getVehiculo().getTarjetaControl());

				empresaVehiculoList.add(tarjetaControlSinVigencia);
			}

			return empresaVehiculoList;
		} catch (NoResultException arg6) {

			LOGGER.error("Excepcion de Persistencia ", arg6);
			return null;
		}

	}
}
