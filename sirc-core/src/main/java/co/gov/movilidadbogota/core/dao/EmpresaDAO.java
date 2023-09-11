
package co.gov.movilidadbogota.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import co.gov.movilidadbogota.core.dto.EmpresaDTO;
import co.gov.movilidadbogota.core.dto.NotificationDTO;
import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity;
import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity_;
import co.gov.movilidadbogota.core.entity.EmpresaEntity;
import co.gov.movilidadbogota.core.entity.EmpresaEntity_;
import javax.persistence.criteria.Path;

@Repository
public class EmpresaDAO extends AbstractDAO<EmpresaEntity> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaDAO.class+LOG_PREFIX);

	@PersistenceContext
	private EntityManager entityManager;

	public EmpresaDAO() {
		super(EmpresaEntity.class);
	}

	public static List<EmpresaDTO> entityListToBasicDtoList(List<EmpresaEntity> entityList) {
		return entityList == null ? null
				: entityList.stream().map(EmpresaDAO::entityToDtoBasic).collect(Collectors.toList());
	}

	public static EmpresaDTO entityToDtoBasic(EmpresaEntity entity) {
		return new EmpresaDTO(entity.getId(), entity.getCodigoEmpresa(), entity.getNombreRazonSocial(),
				entity.getIdNitEmpresa());
	}

	public static List<EmpresaDTO> entityListToDtoList(List<EmpresaEntity> entityList) {
		return entityList == null ? null
				: entityList.stream().map(EmpresaDAO::entityToDto).collect(Collectors.toList());
	}

	public static EmpresaDTO entityToDto(EmpresaEntity entity) {
		return new EmpresaDTO(entity.getId(), entity.getCodigoEmpresa(),
				ConductorVehiculoDAO.conductorVehiculoToNotificationDTOList(entity.getConductorVehiculoEntity()),
				UsuarioDAO.entityListToBasicDtoList(entity.getUsuarioEntities()));
	}

	@Transactional
	public List<EmpresaDTO> stateOutOfLifeDate(Date date) {

		try {

			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			List<Order> orderList = new ArrayList<>();
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			Path<Date> path = root.get(ConductorVehiculoEntity_.fechaVigencia);
                        criteriaQuery.where(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
					criteriaBuilder.lessThan(path, date));
			orderList.add(criteriaBuilder.desc(root.get(ConductorVehiculoEntity_.empresa)));

			criteriaQuery.orderBy(orderList);

			try {
				TypedQuery<ConductorVehiculoEntity> query = getEntityManager().createQuery(criteriaQuery);

				List<ConductorVehiculoEntity> results = query.getResultList();

				Map<String, EmpresaDTO> noti = new HashMap<>();

				for (ConductorVehiculoEntity c : results) {
					if (noti.containsKey(String.valueOf(c.getEmpresa().getId()))) {
						((EmpresaDTO) noti.get(String.valueOf(c.getEmpresa().getId()))).getNotificationDTOs()
								.add(new NotificationDTO(c.getTarjetaControl()));
					} else {
						List<NotificationDTO> n = new ArrayList<>();
						n.add(new NotificationDTO(c.getTarjetaControl()));
						EmpresaDTO e = new EmpresaDTO(c.getEmpresa().getId(), c.getEmpresa().getCodigoEmpresa(), n,
								UsuarioDAO.entityListToBasicDtoList(c.getEmpresa().getUsuarioEntities()));
						noti.put(e.getId().toString(), e);
					}
				}

				List<EmpresaDTO> empresas = new ArrayList<>();
				empresas.addAll(noti.values());

				return empresas;
				// return entityListToDtoList(query.getResultList());
			} catch (NoResultException arg6) {
				LOGGER.error("Excepcion de Persistencia ", arg6);
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("Excepcion ", e);
			return null;
		}
	}

	@Transactional
	public List<EmpresaDTO> stateOutOfValidateDate(Date date) {

		try {

			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			List<Order> orderList = new ArrayList<>();
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			Path<Date> path = root.get(ConductorVehiculoEntity_.fechaValidez);
                        criteriaQuery.where(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
					criteriaBuilder.lessThan(path, date));
			orderList.add(criteriaBuilder.desc(root.get(ConductorVehiculoEntity_.empresa)));

			criteriaQuery.orderBy(orderList);

			try {
				TypedQuery<ConductorVehiculoEntity> query = getEntityManager().createQuery(criteriaQuery);

				List<ConductorVehiculoEntity> results = query.getResultList();

				Map<String, EmpresaDTO> noti = new HashMap<>();

				for (ConductorVehiculoEntity c : results) {
					if (noti.containsKey(String.valueOf(c.getEmpresa().getId()))) {
						((EmpresaDTO) noti.get(String.valueOf(c.getEmpresa().getId()))).getNotificationDTOs()
								.add(new NotificationDTO(c.getTarjetaControl()));
					} else {
						List<NotificationDTO> n = new ArrayList<>();
						n.add(new NotificationDTO(c.getTarjetaControl()));
						EmpresaDTO e = new EmpresaDTO(c.getEmpresa().getId(), c.getEmpresa().getCodigoEmpresa(), n,
								UsuarioDAO.entityListToBasicDtoList(c.getEmpresa().getUsuarioEntities()));
						noti.put(e.getId().toString(), e);
					}
				}

				List<EmpresaDTO> empresas = new ArrayList<>();
				empresas.addAll(noti.values());

				return empresas;
				// return entityListToDtoList(query.getResultList());
			} catch (NoResultException arg6) {
				LOGGER.error("Excepcion de Persistencia ", arg6);
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("Excepcion ", e);
			return null;
		}
	}

	@Transactional
	public List<EmpresaDTO> stateBeforeToExpire(boolean state, Date date) {
		try {

			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			List<Order> orderList = new ArrayList<>();
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			Path<Date> path = root.get(ConductorVehiculoEntity_.fechaVigencia);
                        Path<Date> path1 = root.get(ConductorVehiculoEntity_.fechaVigencia);
                        criteriaQuery.where(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), state),
					criteriaBuilder.lessThanOrEqualTo(path, date),
					criteriaBuilder.greaterThanOrEqualTo(path1, new Date()));
			orderList.add(criteriaBuilder.desc(root.get(ConductorVehiculoEntity_.empresa)));

			criteriaQuery.orderBy(orderList);

			try {
				TypedQuery<ConductorVehiculoEntity> query = getEntityManager().createQuery(criteriaQuery);

				List<ConductorVehiculoEntity> results = query.getResultList();

				Map<String, EmpresaDTO> noti = new HashMap<>();

				for (ConductorVehiculoEntity c : results) {
					if (noti.containsKey(String.valueOf(c.getEmpresa().getId()))) {
						((EmpresaDTO) noti.get(String.valueOf(c.getEmpresa().getId()))).getNotificationDTOs()
								.add(new NotificationDTO(c.getTarjetaControl()));
					} else {
						List<NotificationDTO> n = new ArrayList<>();
						n.add(new NotificationDTO(c.getTarjetaControl()));
						EmpresaDTO e = new EmpresaDTO(c.getEmpresa().getId(), c.getEmpresa().getCodigoEmpresa(), n,
								UsuarioDAO.entityListToBasicDtoList(c.getEmpresa().getUsuarioEntities()));
						noti.put(e.getId().toString(), e);
					}
				}

				List<EmpresaDTO> empresas = new ArrayList<>();
				empresas.addAll(noti.values());

				return empresas;
				// return entityListToDtoList(query.getResultList());
			} catch (NoResultException arg6) {
				LOGGER.error("Excepcion de Persistencia ", arg6);
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("Excepcion ", e);
			return null;
		}
	}

}
