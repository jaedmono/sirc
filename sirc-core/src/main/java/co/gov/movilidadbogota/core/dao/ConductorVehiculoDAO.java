package co.gov.movilidadbogota.core.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.gov.movilidadbogota.core.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.gov.movilidadbogota.core.dto.ConductorDTO;
import co.gov.movilidadbogota.core.dto.ConductorVehiculoDTO;
import co.gov.movilidadbogota.core.dto.CreateDriverDTO;
import co.gov.movilidadbogota.core.dto.EmpresaDTO;
import co.gov.movilidadbogota.core.dto.NotificationDTO;
import co.gov.movilidadbogota.core.dto.PagoPilaDTO;
import co.gov.movilidadbogota.core.dto.PersonaDTO;
import co.gov.movilidadbogota.core.dto.ReportRefrenderDto;
import co.gov.movilidadbogota.core.util.DateUtil;
import co.gov.movilidadbogota.core.util.EstadoTarjetaControlEnum;
import co.gov.movilidadbogota.core.util.TipoTransaccion;

@Repository
public class ConductorVehiculoDAO extends AbstractDAO<ConductorVehiculoEntity> {

	public ConductorVehiculoDAO() {
		super(ConductorVehiculoEntity.class);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ConductorVehiculoDAO.class+LOG_PREFIX);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PagoPilaDAO pagoPilaDAO;

	@Autowired
	private ParametroSimurDAO parametroSimurDAO;

	@Autowired
	private DispositivosMovilDAO dispositivosMovilDAO;

	@Autowired
	private MetodoCobroDAO metodoCobroDAO;

	@Autowired
	private TipoServicioDAO tipoServicioDAO;

	public static List<NotificationDTO> conductorVehiculoToNotificationDTOList(
			List<ConductorVehiculoEntity> entityList) {
		return entityList == null ? null
				: entityList.stream().map(ConductorVehiculoDAO::conductorVehiculoToNotificationDTO)
						.collect(Collectors.toList());
	}

	public static NotificationDTO conductorVehiculoToNotificationDTO(ConductorVehiculoEntity entity) {
		return new NotificationDTO(entity.getTarjetaControl());
	}

	@Transactional
	public List<NotificationDTO> findByStateOutOfValidateDate(Date date, List<Long> empresas) {

		List<NotificationDTO> empresaVehiculoList = new ArrayList<>();

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			Path<Date> path = root.get(ConductorVehiculoEntity_.fechaValidez);
			criteriaQuery.where(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
					criteriaBuilder.lessThan(path, date),
					root.get(ConductorVehiculoEntity_.empresa).get(EmpresaEntity_.id).in(empresas));
			try {
				List<ConductorVehiculoEntity> conductorVehiculoList = (List<ConductorVehiculoEntity>) entityManager
						.createQuery(criteriaQuery).getResultList();

				for (ConductorVehiculoEntity conductorVehiculo : conductorVehiculoList) {
					conductorVehiculo.setNotificated(true);
					super.update(conductorVehiculo);

					String nombres = conductorVehiculo.getConductor().getPersona().getNombres() + " "
							+ conductorVehiculo.getConductor().getPersona().getApellidos();

					int days = DateUtil.daysBetween(conductorVehiculo.getFechaValidez(), new Date());

					String formattedDate = new SimpleDateFormat("dd/MM/yyyy")
							.format(conductorVehiculo.getFechaValidez());

					NotificationDTO tarjetaControlSinValidez = new NotificationDTO();
					tarjetaControlSinValidez.setDias(days);
					tarjetaControlSinValidez.setFecha(formattedDate);
					tarjetaControlSinValidez.setNombres(nombres);
					tarjetaControlSinValidez
							.setNumeroDocumento(conductorVehiculo.getConductor().getPersona().getNumeroDocumento());
					tarjetaControlSinValidez.setPlaca(conductorVehiculo.getPlacaSerialVehiculo());
					tarjetaControlSinValidez.setTarjetaControl(conductorVehiculo.getTarjetaControl());

					empresaVehiculoList.add(tarjetaControlSinValidez);
				}

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

	@Transactional
	public List<NotificationDTO> findByStateOutOflifeDate(Date date, List<Long> empresas) {

		List<NotificationDTO> empresaVehiculoList = new ArrayList<>();

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			Path<Date> path = root.get(ConductorVehiculoEntity_.fechaVigencia);
			criteriaQuery.where(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
					criteriaBuilder.lessThan(path, date),
					root.get(ConductorVehiculoEntity_.empresa).get(EmpresaEntity_.id).in(empresas));
			try {
				List<ConductorVehiculoEntity> conductorVehiculoList = (List<ConductorVehiculoEntity>) entityManager
						.createQuery(criteriaQuery).getResultList();

				for (ConductorVehiculoEntity conductorVehiculo : conductorVehiculoList) {
					conductorVehiculo.setNotificated(true);
					super.update(conductorVehiculo);

					String nombres = conductorVehiculo.getConductor().getPersona().getNombres() + " "
							+ conductorVehiculo.getConductor().getPersona().getApellidos();

					int days = DateUtil.daysBetween(conductorVehiculo.getFechaVigencia(), new Date());

					String formattedDate = new SimpleDateFormat("dd/MM/yyyy")
							.format(conductorVehiculo.getFechaVigencia());

					NotificationDTO tarjetaControlSinValidez = new NotificationDTO();
					tarjetaControlSinValidez.setDias(days);
					tarjetaControlSinValidez.setFecha(formattedDate);
					tarjetaControlSinValidez.setNombres(nombres);
					tarjetaControlSinValidez
							.setNumeroDocumento(conductorVehiculo.getConductor().getPersona().getNumeroDocumento());
					tarjetaControlSinValidez.setPlaca(conductorVehiculo.getPlacaSerialVehiculo());
					tarjetaControlSinValidez.setTarjetaControl(conductorVehiculo.getTarjetaControl());

					empresaVehiculoList.add(tarjetaControlSinValidez);
				}

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

	@Transactional
	public Long countByStateOutOflifeDate(Date date, List<Long> idEmpresa) {
		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			criteriaQuery.select(criteriaBuilder.count(root));

			criteriaQuery.where(
					criteriaBuilder.or(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
							criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(2))),
					root.get(ConductorVehiculoEntity_.empresa).get(EmpresaEntity_.id).in(idEmpresa));
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

	@Transactional
	public List<NotificationDTO> findByStateBeforeToExpire(boolean state, Date date, List<Long> empresas) {

		List<NotificationDTO> empresaVehiculoList = new ArrayList<>();

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			Path<Date> path = root.get(ConductorVehiculoEntity_.fechaVigencia);
			Path<Date> path1 = root.get(ConductorVehiculoEntity_.fechaVigencia);
			criteriaQuery.where(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), state),
					criteriaBuilder.lessThanOrEqualTo(path, date),
					criteriaBuilder.greaterThanOrEqualTo(path1, new Date()),
					root.get(ConductorVehiculoEntity_.empresa).get(EmpresaEntity_.id).in(empresas));
			try {
				List<ConductorVehiculoEntity> PagoPilaCondVehiList = (List<ConductorVehiculoEntity>) entityManager
						.createQuery(criteriaQuery).getResultList();

				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				new SimpleDateFormat("dd/MM/yyyy");

				for (ConductorVehiculoEntity conductorVehiculo : PagoPilaCondVehiList) {

					conductorVehiculo.setNotificated(true);
					super.update(conductorVehiculo);

					String nombres = conductorVehiculo.getConductor().getPersona().getNombres() + " "
							+ conductorVehiculo.getConductor().getPersona().getApellidos();

					int days = DateUtil.daysBetween(formatter.parse(formatter.format(new Date())),
							formatter.parse(formatter.format(conductorVehiculo.getFechaVigencia())));

					String formattedDate = new SimpleDateFormat("dd/MM/yyyy")
							.format(conductorVehiculo.getFechaVigencia());

					NotificationDTO tarjetaControlSinValidez = new NotificationDTO();
					tarjetaControlSinValidez.setDias(days);
					tarjetaControlSinValidez.setFecha(formattedDate);
					tarjetaControlSinValidez.setNombres(nombres);
					tarjetaControlSinValidez
							.setNumeroDocumento(conductorVehiculo.getConductor().getPersona().getNumeroDocumento());
					tarjetaControlSinValidez.setPlaca(conductorVehiculo.getPlacaSerialVehiculo());
					tarjetaControlSinValidez.setTarjetaControl(conductorVehiculo.getTarjetaControl());

					empresaVehiculoList.add(tarjetaControlSinValidez);
				}

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

	public Long countByStateOutOfValidateDate(Date date, List<Long> empresas) {

		try {

			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			criteriaQuery.select(criteriaBuilder.count(root));
			Path<Date> path = root.get(ConductorVehiculoEntity_.fechaValidez);
			criteriaQuery.where(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.notificated), new Long(0)),
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
					criteriaBuilder.lessThan(path, date),
					root.get(ConductorVehiculoEntity_.empresa).get(EmpresaEntity_.id).in(empresas));
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

	public Long countByStateOutOfLifeDate(Date date, List<Long> empresas) {

		try {

			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			criteriaQuery.select(criteriaBuilder.count(root));
			Path<Date> path = root.get(ConductorVehiculoEntity_.fechaVigencia);
			criteriaQuery.where(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.notificated), new Long(0)),
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
					criteriaBuilder.lessThan(path, date),
					root.get(ConductorVehiculoEntity_.empresa).get(EmpresaEntity_.id).in(empresas));
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

	public Long countByStateBeforeToExpire(boolean state, Date date, List<Long> empresas) {

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			criteriaQuery.select(criteriaBuilder.count(root));

			Path<Date> path = root.get(ConductorVehiculoEntity_.fechaVigencia);
			Path<Date> path1 = root.get(ConductorVehiculoEntity_.fechaVigencia);
			criteriaQuery.where(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.notificated), new Long(0)),
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
					criteriaBuilder.lessThanOrEqualTo(path, date),
					criteriaBuilder.greaterThanOrEqualTo(path1, new Date()),
					root.get(ConductorVehiculoEntity_.empresa).get(EmpresaEntity_.id).in(empresas));
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

	@Transactional
	public ConductorVehiculoDTO buscarPorTarjetaControl(String tarjeta, String tipoTransaccion) {
		try{
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			criteriaQuery.where(
					criteriaBuilder.or(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
							criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(2)),
							criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(3)),
							criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(5))),
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.tarjetaControl), tarjeta));
			ConductorVehiculoDTO cvDTO = getConductorVehiculoDTO(tipoTransaccion, criteriaQuery);
			if (cvDTO != null) return cvDTO;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	private ConductorVehiculoDTO getConductorVehiculoDTO(String tipoTransaccion, CriteriaQuery<ConductorVehiculoEntity> criteriaQuery) {
		List<ConductorVehiculoEntity> entityList = entityManager.createQuery(criteriaQuery).getResultList();
		if (entityList != null && entityList.size() > 0) {
			ConductorVehiculoEntity conductorVehiculoEntity = entityList.stream().max(Comparator.comparing(ConductorVehiculoEntity::getFechaVigencia)).get();
			ConductorVehiculoDTO cvDTO = new ConductorVehiculoDTO(
					conductorVehiculoEntity.getId(),
					conductorVehiculoEntity.getTarjetaControl(),
					conductorVehiculoEntity.getTipoTransaccion(),
					conductorVehiculoEntity.getFechaExpedicion(),
					conductorVehiculoEntity.getFechaValidez(),
					conductorVehiculoEntity.getFechaVigencia(),
					conductorVehiculoEntity.getPlacaSerialVehiculo(),
					conductorVehiculoEntity.getIdEstado().getId(),
					conductorVehiculoEntity.getConductor().getId()
			);

			ConductorEntity conductor = conductorVehiculoEntity.getConductor();
			cvDTO.setConductorDTO(new ConductorDTO(
					conductor.getId(),
					conductor.getGrupoSanguineo(),
					conductor.getFactorRh(),
					conductor.getIdEps(),
					conductor.getIdArl(),
					conductor.getIdAfp(),
					conductor.getRutaFoto()));

			cvDTO.getConductorDTO().setPersona(new PersonaDTO(
					conductor.getPersona().getTipoDocumento().getId(),
					conductor.getPersona().getNumeroDocumento(),
					conductor.getPersona().getNombres(),
					conductor.getPersona().getApellidos(),
					conductor.getPersona().getCelular(),
					conductor.getPersona().getDireccion()));

			cvDTO.getConductorDTO().setFoto(conductor.getRutaFoto());

			if (conductor.getPersona().getCelular().toString().length() <= 7) {
				cvDTO.setTipoTelFijo(true);
			} else {
				cvDTO.setTipoTelFijo(false);
			}
			if (tipoTransaccion.equals("3")) {

				List<PagoPilaCondVehiEntity> pagoPilaAnterior = pagoPilaDAO
						.findByIdVehiculoIdConductor(cvDTO.getId(), cvDTO.getConductorDTO().getId());

				List<PagoPilaDTO> listaPagos = new ArrayList<>();
				if (pagoPilaAnterior != null && !pagoPilaAnterior.isEmpty()) {
					for (PagoPilaCondVehiEntity pagoPilaCondVehiEntity : pagoPilaAnterior) {
						PagoPilaDTO pagoPila = new PagoPilaDTO();
						pagoPila.setNumeroAprobacion(pagoPilaCondVehiEntity.getNroAprobacionPago());
						String periodo = String.valueOf(pagoPilaCondVehiEntity.getPeriodoPagoPila());
						if (periodo.length() != 7) {
							periodo = "0" + periodo;
						}
						pagoPila.setOperadorPila(pagoPilaCondVehiEntity.getOperador().getId());
						String periodoFormat = periodo.substring(0, 2) + "/" + periodo.substring(2);
						pagoPila.setStringPeriodoPago(periodoFormat);
						pagoPila.setPeriodoPago(periodoFormat);
						listaPagos.add(pagoPila);
					}
					cvDTO.setPlanillas(listaPagos);
				}
			} else {
				cvDTO.setPlanillas(new ArrayList<>());
				cvDTO.getPlanillas().add(new PagoPilaDTO());
			}
			cvDTO.setEmpresaDTO(new EmpresaDTO(conductorVehiculoEntity.getEmpresa().getId(), conductorVehiculoEntity.getEmpresa().getCodigoEmpresa(),
					conductorVehiculoEntity.getEmpresa().getNombreRazonSocial(), conductorVehiculoEntity.getEmpresa().getIdNitEmpresa()));
			cvDTO.getConductorDTO().getPersona()
					.setFechaNacimiento(conductorVehiculoEntity.getConductor().getPersona().getFechaNacimiento());
			cvDTO.getConductorDTO().getPersona()
					.setFechaExpedicionDocumento(conductorVehiculoEntity.getConductor().getPersona().getFechaExpedicionDocumento());
			cvDTO.setFechaVencimientoSoat(conductorVehiculoEntity.getFechaVencimientoSoat());
			cvDTO.setNroSOAT(conductorVehiculoEntity.getNroSOAT());
			cvDTO.setFechaVencimientoRtm(conductorVehiculoEntity.getFechaVencimientoRtm());
			cvDTO.setNroRTM(conductorVehiculoEntity.getNroRTM());
			cvDTO.setNroTarjetaOperacion(conductorVehiculoEntity.getNroTarjetaOperacion());
			cvDTO.setFechaVencimientoTO(conductorVehiculoEntity.getFechaVencimientoTO());
			cvDTO.setFactorCalidad(false);
			cvDTO.setId(conductorVehiculoEntity.getId());
			if (conductorVehiculoEntity.getFactorCalidad() != null && conductorVehiculoEntity.getFactorCalidad().getIdEstado() == 1) {
				cvDTO.setIdFactorCalidad(conductorVehiculoEntity.getFactorCalidad().getId());
				cvDTO.setFactorCalidad(true);
			}
			cvDTO.setIdMetodoCobro(conductorVehiculoEntity.getMetodoPago() != null ? conductorVehiculoEntity.getMetodoPago().getId() : 0);
			cvDTO.setIdTipoServicio(conductorVehiculoEntity.getTipoServicio() != null ? conductorVehiculoEntity.getTipoServicio().getId() : 0);
			/**
			 * if (cvDTO.getIdMetodoCobro() == 1) { List<DispositivoMovilDTO> dispositivos =
			 * dispositivosMovilDAO.consultarDispositivosMovil(cvDTO.getId()); if
			 * (dispositivos != null && !dispositivos.isEmpty()) {
			 * cvDTO.setDispositivosMovil(dispositivos.get(dispositivos.size() - 1)); } }*
			 */
			LOGGER.info(cvDTO.getConductorDTO().getGrupoSanguineo() + " " + cvDTO.getConductorDTO().getFactorRh());
			return cvDTO;
		}
		return null;
	}


	@Transactional
	public ConductorVehiculoDTO buscarPorTarjetaControl(String tarjeta, String tipoTransaccion, List<Long> empresas) {
		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			criteriaQuery.where(
					criteriaBuilder.or(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
							criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(2)),
							criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(3))),
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.tarjetaControl), tarjeta),
					root.get(ConductorVehiculoEntity_.empresa).get(EmpresaEntity_.id).in(empresas)
			);
			ConductorVehiculoDTO cvDTO = getConductorVehiculoDTO(tipoTransaccion, criteriaQuery);
			if (cvDTO != null) return cvDTO;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Transactional
	public ConductorVehiculoDTO buscarPorId(long pk) {

		ConductorVehiculoEntity cv = findByPrimaryKey(pk);

		if (cv != null) {
			ConductorVehiculoDTO cvDTO = new ConductorVehiculoDTO(cv.getId(), cv.getTarjetaControl(),
					cv.getTipoTransaccion(), cv.getFechaExpedicion(), cv.getFechaValidez(), cv.getFechaVigencia(),
					cv.getPlacaSerialVehiculo(), cv.getIdEstado().getId(), cv.getConductor().getId());

			ConductorEntity c = cv.getConductor();

			cvDTO.setConductorDTO(new ConductorDTO(c.getId(), c.getGrupoSanguineo(), c.getFactorRh(), c.getIdEps(),
					c.getIdArl(), c.getIdAfp(), c.getRutaFoto()));

			cvDTO.getConductorDTO()
					.setPersona(new PersonaDTO(c.getPersona().getTipoDocumento().getId(),
							c.getPersona().getNumeroDocumento(), c.getPersona().getNombres(),
							c.getPersona().getApellidos(), c.getPersona().getCelular(), c.getPersona().getDireccion()));
			cvDTO.getConductorDTO().getPersona()
					.setTipoIdentificacionDesc(c.getPersona().getTipoDocumento().getDescripcionTipoDoc());

			cvDTO.setEmpresaDTO(new EmpresaDTO(cv.getEmpresa().getId(), cv.getEmpresa().getCodigoEmpresa(),
					cv.getEmpresa().getNombreRazonSocial(), cv.getEmpresa().getIdNitEmpresa()));

			return cvDTO;
		} else {
			return null;
		}

	}

	public ConductorVehiculoEntity findActivByPlacaConductor(ConductorEntity conductor, String placa) {

		ConductorVehiculoEntity conductorEntity = null;

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			criteriaQuery.where(
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.conductor).get(ConductorEntity_.id),
							conductor.getId()),
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.placaSerialVehiculo), placa),
					criteriaBuilder.or(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), 1),
							criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), 2)));
			try {
				conductorEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
				return conductorEntity;
			} catch (NoResultException arg6) {
				return null;
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return conductorEntity;
	}

	public ConductorVehiculoEntity findActivByPlacaConductorExpedir(Long tipoDocumento, Long numeroDocumento,
			String placa) {

		ConductorVehiculoEntity conductorEntity = null;

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			criteriaQuery.where(
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.conductor).get(ConductorEntity_.persona)
							.get(PersonaEntity_.tipoDocumento).get(TipoDocumentoEntity_.id), tipoDocumento),
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.conductor).get(ConductorEntity_.persona)
							.get(PersonaEntity_.numeroDocumento), numeroDocumento),
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.placaSerialVehiculo), placa),
					criteriaBuilder.or(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), 1),
							criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), 2)));
			try {
				conductorEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
				return conductorEntity;
			} catch (NoResultException arg6) {
				return null;
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return conductorEntity;
	}

	@Transactional
	public ConductorVehiculoEntity update(CreateDriverDTO driverDTO) {
		//ParametroDTO vigencia = parametroSimurDAO.findByKey(SystemParameters.VIGENCIA_TARJETA.getValue());
		ConductorVehiculoEntity conductorVehiculoEntity = findByPrimaryKey(driverDTO.getId());
		conductorVehiculoEntity.setTarjetaControl(driverDTO.getNumeroTarjetaControl());
		conductorVehiculoEntity.setTipoTransaccion(String.valueOf(driverDTO.getTipoTransaccion()));
		conductorVehiculoEntity.getConductor().setIdAfp(Long.valueOf(driverDTO.getConductorDTO().getIdAfp()));
		conductorVehiculoEntity.getConductor().setIdArl(Long.valueOf(driverDTO.getConductorDTO().getIdArl()));
		conductorVehiculoEntity.getConductor().setIdEps(Long.valueOf(driverDTO.getConductorDTO().getIdEps()));
		conductorVehiculoEntity.getConductor().getPersona().setDireccion(driverDTO.getConductorDTO().getPersona().getDireccion());
		conductorVehiculoEntity.getConductor().getPersona().setCelular(Long.valueOf(driverDTO.getConductorDTO().getPersona().getCelular()));
		conductorVehiculoEntity.setIdEstado(new TarjetacontrolEstadoEntity());
		conductorVehiculoEntity.getIdEstado().setId(driverDTO.getIdEstado());
		conductorVehiculoEntity.setFechaVigencia(driverDTO.getFechaVigencia());
		/*MetodoCobroEntity metodoCobro = metodoCobroDAO.findByPrimaryKey(driverDTO.getIdMetodoCobro());
		conductorVehiculoEntity.setMetodoPago(metodoCobro);*/
		TipoServicioEntity tipoServicio = tipoServicioDAO.findByPrimaryKey(driverDTO.getIdTipoServicio());
		conductorVehiculoEntity.setTipoServicio(tipoServicio);
		
		update(conductorVehiculoEntity);

		return conductorVehiculoEntity;

	}

	@Transactional
	public void cancelCardManagement(long id) {
		ConductorVehiculoEntity conductorVehiculoEntity = findByPrimaryKey(id);
		conductorVehiculoEntity.setTipoTransaccion(String.valueOf(TipoTransaccion.CANCELACION.getValue()));
		conductorVehiculoEntity.setIdEstado(new TarjetacontrolEstadoEntity());
		conductorVehiculoEntity.getIdEstado().setId(EstadoTarjetaControlEnum.CANCELADO.getPk());
		update(conductorVehiculoEntity);
	}

	public Long obtenerNroRadicado() {
		BigDecimal integer = (BigDecimal) entityManager
				.createNativeQuery("SELECT SEQ_RADICADO_CODUCTOR.nextval FROM dual").getSingleResult();

		return integer.longValue();
	}

	/**
	 * Consulta los conductores vehiculos
	 *
	 * @param conductorVehiculoDTO
	 * @return List ConductorVehiculoEntity
	 */
	public List<ConductorVehiculoEntity> consultarConductorVehiculoEntity(ConductorVehiculoDTO conductorVehiculoDTO) {
		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			List<Predicate> predicates = new ArrayList<>();
			// Filtro por placa
			if (conductorVehiculoDTO.getPlaca() != null && !conductorVehiculoDTO.getPlaca().isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.placaSerialVehiculo),
						conductorVehiculoDTO.getPlaca()));
			}
			// Filtro por id empresa
			if (conductorVehiculoDTO.getEmpresaDTO() != null && conductorVehiculoDTO.getEmpresaDTO().getId() != null) {
				predicates.add(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.empresa).get(EmpresaEntity_.id),
						conductorVehiculoDTO.getEmpresaDTO().getId()));
			}
			// Filtro estado
			if (conductorVehiculoDTO.getTarjetaControlEstado() != -1) {
				predicates.add(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado),
						conductorVehiculoDTO.getTarjetaControlEstado()));
			}
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
			return (List<ConductorVehiculoEntity>) entityManager.createQuery(criteriaQuery).getResultList();

		} catch (Exception e) {
			LOGGER.error("Excepcion ", e);
		}
		return null;
	}

	/**
	 * Consulta los conductores vehiculos
	 *
	 * @param conductorVehiculoDTO
	 * @return List ConductorVehiculoDTO
	 */
	public List<ConductorVehiculoDTO> consultarConductorVehiculo(ConductorVehiculoDTO conductorVehiculoDTO) {
		List<ConductorVehiculoDTO> conductorVehiculoListDTO = new ArrayList<>();
		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			List<Predicate> predicates = new ArrayList<>();
			// Filtro por placa
			if (conductorVehiculoDTO.getPlaca() != null && !conductorVehiculoDTO.getPlaca().isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.placaSerialVehiculo),
						conductorVehiculoDTO.getPlaca()));
			}
			// Filtro por id empresa
			if (conductorVehiculoDTO.getEmpresaDTO() != null && conductorVehiculoDTO.getEmpresaDTO().getId() != null) {
				predicates.add(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.empresa).get(EmpresaEntity_.id),
						conductorVehiculoDTO.getEmpresaDTO().getId()));
			}
			// Filtro estado
			if (conductorVehiculoDTO.getTarjetaControlEstado() != -1) {
				predicates.add(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado),
						conductorVehiculoDTO.getTarjetaControlEstado()));
			}
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
			List<ConductorVehiculoEntity> conductorVehiculoList = (List<ConductorVehiculoEntity>) entityManager
					.createQuery(criteriaQuery).getResultList();
			for (ConductorVehiculoEntity conductorVehiculo : conductorVehiculoList) {
				ConductorVehiculoDTO conductorVehiDTO = new ConductorVehiculoDTO();
				if (conductorVehiculo.getConductor() != null) {
					ConductorDTO conductorDTO = new ConductorDTO(conductorVehiculo.getConductor().getId(),
							conductorVehiculo.getConductor().getGrupoSanguineo(),
							conductorVehiculo.getConductor().getFactorRh(), conductorVehiculo.getConductor().getIdEps(),
							conductorVehiculo.getConductor().getIdArl(), conductorVehiculo.getConductor().getIdAfp(),
							conductorVehiculo.getConductor().getRutaFoto());
					conductorVehiDTO.setConductorDTO(conductorDTO);
				}
				if (conductorVehiculo.getEmpresa() != null) {
					EmpresaDTO empresaDTO = new EmpresaDTO(conductorVehiculo.getEmpresa().getId(),
							conductorVehiculo.getEmpresa().getCodigoEmpresa());
					conductorVehiDTO.setEmpresaDTO(empresaDTO);
				}
				conductorVehiDTO.setFactorCalidad(false);
				if (conductorVehiculo.getFactorCalidad() != null) {
					conductorVehiDTO.setIdFactorCalidad(conductorVehiculo.getFactorCalidad().getId());
					conductorVehiDTO.setFactorCalidad(true);
				}
				conductorVehiDTO.setFechaExpedicion(conductorVehiculo.getFechaExpedicion());
				conductorVehiDTO.setFechaValidez(conductorVehiculo.getFechaValidez());
				conductorVehiDTO.setFechaVigencia(conductorVehiculo.getFechaVigencia());
				conductorVehiDTO.setId(conductorVehiculo.getId());
				if (conductorVehiculo.getMetodoPago() != null) {
					conductorVehiDTO.setIdMetodoCobro(conductorVehiculo.getMetodoPago().getId());
				}
				if (conductorVehiculo.getTipoServicio() != null) {
					conductorVehiDTO.setIdTipoServicio(conductorVehiculo.getTipoServicio().getId());
				}
				conductorVehiDTO.setNumeroTarjetaControl(conductorVehiculo.getTarjetaControl());
				conductorVehiDTO.setPlaca(conductorVehiculo.getPlacaSerialVehiculo());
				if (conductorVehiculo.getIdEstado() != null) {
					conductorVehiDTO.setTarjetaControlEstado(conductorVehiculo.getIdEstado().getId());
				}
				conductorVehiDTO.setTipoTransaccion(conductorVehiculo.getTipoTransaccion());
				conductorVehiculoListDTO.add(conductorVehiDTO);
			}
		} catch (Exception e) {
			LOGGER.error("Excepcion ", e);
		}
		return conductorVehiculoListDTO;
	}

	/**
	 * Tarjetas de control que estan vencidas y no se refrendaron
	 *
	 * @param date
	 * @return
	 */
	public List<ConductorVehiculoEntity> tarjetasControlVencidas(Date date) {
		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			Path<Date> path = root.get(ConductorVehiculoEntity_.fechaVigencia);
			criteriaQuery.where(
					criteriaBuilder.or(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), 1),
							criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), 2)),
					criteriaBuilder.lessThan(path, date));
			try {
				List<ConductorVehiculoEntity> conductorVehiculoList = (List<ConductorVehiculoEntity>) entityManager
						.createQuery(criteriaQuery).getResultList();
				return conductorVehiculoList;
			} catch (NoResultException arg6) {
				LOGGER.error("Excepcion de Persistencia ", arg6);
				return null;
			}
		} catch (Exception e) {
			LOGGER.error("Excepcion ", e);
			return null;
		}
	}

	/**
	 * Se encarga de cancelar la tarjeta de control que esta vencida
	 *
	 * @param cvEntity
	 */
	@Transactional
	public void cancelarTarjetaControlVencida(ConductorVehiculoEntity cvEntity) {
		cvEntity.setIdEstado(new TarjetacontrolEstadoEntity());
		cvEntity.getIdEstado().setId(EstadoTarjetaControlEnum.SIN_RENOVACION.getPk());
		update(cvEntity);
	}

	public ConductorVehiculoEntity consultarConductorPortarjeta(String nroTarjetaControl) {
		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			criteriaQuery.where(
					criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.tarjetaControl), nroTarjetaControl),
					criteriaBuilder.or(
							criteriaBuilder.equal(
									root.get(ConductorVehiculoEntity_.idEstado).get(TarjetacontrolEstadoEntity_.id),
									new Long(1)),
							criteriaBuilder.equal(
									root.get(ConductorVehiculoEntity_.idEstado).get(TarjetacontrolEstadoEntity_.id),
									new Long(2)),
							criteriaBuilder.equal(
									root.get(ConductorVehiculoEntity_.idEstado).get(TarjetacontrolEstadoEntity_.id),
									new Long(3)),
							criteriaBuilder.equal(
									root.get(ConductorVehiculoEntity_.idEstado).get(TarjetacontrolEstadoEntity_.id),
									new Long(5))));
			try {
				List<ConductorVehiculoEntity> entityList = entityManager.createQuery(criteriaQuery).getResultList();
				if(entityList != null && entityList.size() > 0) {
					return entityList.stream().max(Comparator.comparing(ConductorVehiculoEntity::getFechaVigencia)).get();
				}
			} catch (NoResultException arg6) {
				LOGGER.error(arg6.getMessage());
				return null;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
		return new ConductorVehiculoEntity();
	}

	public List<ConductorVehiculoEntity> consultarTaxiPorPlaca(String placa) {
		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<ConductorVehiculoEntity> criteriaQuery = criteriaBuilder
					.createQuery(ConductorVehiculoEntity.class);
			Root<ConductorVehiculoEntity> root = criteriaQuery.from(ConductorVehiculoEntity.class);
			criteriaQuery.where(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.placaSerialVehiculo), placa),
					criteriaBuilder.or(criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(1)),
							criteriaBuilder.equal(root.get(ConductorVehiculoEntity_.idEstado), new Long(2))));
			try {
				return entityManager.createQuery(criteriaQuery).getResultList();
			} catch (NoResultException arg6) {
				LOGGER.error(arg6.getMessage());
				return null;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	public List<ConductorVehiculoEntity> consultarReporteRefrendacion(String fechaInicial, String fechaFinal) {
		try {

			Query query = this.entityManager.createQuery("SELECT DISTINCT x FROM ConductorVehiculoEntity x "
					+ "JOIN FETCH x.empresa e JOIN FETCH x.conductor c JOIN FETCH c.persona p JOIN FETCH x.idEstado tce "
					+ "JOIN FETCH x.refrendacionHistorico rh "
					+ "WHERE x.fechaExpedicion >= to_timestamp( :fechaInicio , 'yyyy/mm/dd HH24-MI-SS') "
					+ "AND x.fechaExpedicion <= to_timestamp( :fechaFinal , 'yyyy/mm/dd HH24-MI-SS') ");

			query.setParameter("fechaInicio", fechaInicial+" 00:00:00");
			query.setParameter("fechaFinal", fechaFinal+" 23:59:59");

			return (List<ConductorVehiculoEntity>) query.getResultList();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	public List<ConductorVehiculoEntity> queryReportByPassedParameters(Date issueDate,
																	   Date expiryDate,
																	   String plate,
																	   Long companyId,
																	   Long stateId,
																	   Long document) {
		try {

			DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

			String sentence = "SELECT  x FROM ConductorVehiculoEntity x "
					+ "LEFT JOIN FETCH x.empresa e LEFT JOIN FETCH x.conductor c LEFT JOIN FETCH c.persona p LEFT JOIN FETCH x.idEstado tce "
					+ "LEFT JOIN FETCH x.refrendacionHistorico rh ";

			String whereClause = "WHERE ";
			if(issueDate != null){
				whereClause = whereClause + " x.fechaExpedicion >= to_timestamp( :fechaInicioExpedicion , 'yyyy/mm/dd HH24-MI-SS') "
						+ "AND x.fechaExpedicion <= to_timestamp( :fechaFinalExpedicion , 'yyyy/mm/dd HH24-MI-SS') AND ";
			}

			if(expiryDate != null){
				whereClause = whereClause + " x.fechaValidez >= to_timestamp( :fechaInicioValidez , 'yyyy/mm/dd HH24-MI-SS') "
						+ "AND x.fechaValidez <= to_timestamp( :fechaFinalValidez , 'yyyy/mm/dd HH24-MI-SS') AND ";
			}

			if(!plate.isEmpty()){
				whereClause = whereClause + " x.placaSerialVehiculo = :plate AND ";
			}

			if(companyId != null){
				whereClause = whereClause + " e.id = :companyId AND ";
			}

			if(stateId != null){
				whereClause = whereClause + " x.idEstado = :stateId AND ";
			}

			if(document != null){
				whereClause = whereClause + " p.numeroDocumento = :document AND ";
			}

			whereClause = whereClause.substring(0, whereClause.length() - 4);

			Query query = this.entityManager.createQuery(sentence + whereClause);

			if(issueDate != null){
				String formattedDate = formatter.format(issueDate);
				query.setParameter("fechaInicioExpedicion", formattedDate+" 00:00:00");
				query.setParameter("fechaFinalExpedicion", formattedDate+" 23:59:59");
			}

			if(expiryDate != null){
				String formattedDate = formatter.format(expiryDate);
				query.setParameter("fechaInicioValidez", formattedDate+" 00:00:00");
				query.setParameter("fechaFinalValidez", formattedDate+" 23:59:59");
			}

			if(!plate.isEmpty()){
				query.setParameter("plate", plate);
			}

			if(companyId != null){
				query.setParameter("companyId", companyId);
			}

			if(stateId != null){
				TarjetacontrolEstadoEntity entity = new TarjetacontrolEstadoEntity();
				entity.setId(stateId);
				query.setParameter("stateId", entity);
			}

			if(document != null){
				query.setParameter("document", document);
			}

			return (List<ConductorVehiculoEntity>) query.getResultList();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	public List<ReportRefrenderDto> consultarReporteRefrendacionDTO(String fechaInicial, String fechaFinal) {
		try {

			Query query = this.entityManager.createQuery("SELECT DISTINCT "
					+ "NEW co.gov.movilidadbogota.core.dto.ReportRefrenderDto(x.tarjetaControl as tarjetaControl ,x.placaSerialVehiculo as placaSerialVehiculo,e.nombreRazonSocial as nombreRazonSocial,"
					+ "p.nombres as nombreConductor ,p.apellidos as apellidosConductor ,tce.descripcion as estado,x.fechaExpedicion as fechaExpedicion,"
					+ "x.fechaValidez as fechaValidez,x.fechaVigencia as fechaVigencia, rh.fechaRefrendacion as fechaRefrendacion) "
					+ "FROM ConductorVehiculoEntity x " + "JOIN x.empresa e " + "JOIN x.conductor c "
					+ "JOIN c.persona p " + "JOIN x.idEstado tce "+ "JOIN x.refrendacionHistorico rh "
					+ "WHERE x.fechaExpedicion >= to_timestamp( :fechaInicio , 'yyyy/mm/dd') "
					+ "AND x.fechaExpedicion <= to_timestamp( :fechaFinal , 'yyyy/mm/dd') ");
			query.setParameter("fechaInicio", fechaInicial);
			query.setParameter("fechaFinal", fechaFinal);

			return (List<ReportRefrenderDto>) query.getResultList();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}
	
	public List<ReportRefrenderDto> consultarReporteRefrendacionAllDTO() {
		try {

			Query query = this.entityManager.createQuery("SELECT DISTINCT "
					+ "NEW co.gov.movilidadbogota.core.dto.ReportRefrenderDto(x.tarjetaControl as tarjetaControl ,x.placaSerialVehiculo as placaSerialVehiculo,e.nombreRazonSocial as nombreRazonSocial,"
					+ "p.nombres as nombreConductor ,p.apellidos as apellidosConductor ,tce.descripcion as estado,x.fechaExpedicion as fechaExpedicion,"
					+ "x.fechaValidez as fechaValidez,x.fechaVigencia as fechaVigencia, rh.fechaRefrendacion as fechaRefrendacion) "
					+ "FROM ConductorVehiculoEntity x " + "JOIN x.empresa e " + "JOIN x.conductor c "
					+ "JOIN c.persona p " + "JOIN x.idEstado tce "+ "JOIN x.refrendacionHistorico rh ");

			return (List<ReportRefrenderDto>) query.getResultList();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}
}
