package co.gov.movilidadbogota.core.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.gov.movilidadbogota.core.dto.CreateDriverDTO;
import co.gov.movilidadbogota.core.dto.PagoPilaDTO;
import co.gov.movilidadbogota.core.entity.ConductorEntity_;
import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity_;
import co.gov.movilidadbogota.core.entity.OperadoresPilaEntity;
import co.gov.movilidadbogota.core.entity.PagoPilaCondVehiEntity;
import co.gov.movilidadbogota.core.entity.PagoPilaCondVehiEntity_;
import javax.persistence.criteria.Path;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class PagoPilaDAO extends AbstractDAO<PagoPilaCondVehiEntity> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OperadoresPilaDAO operadoresPilaDAO;

    public PagoPilaDAO() {
        super(PagoPilaCondVehiEntity.class);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(PagoPilaDAO.class+LOG_PREFIX);

    @Transactional
    public void updateCreatePagoPilaRefrendacion(CreateDriverDTO driverDTO, Integer vigencia) {
        List<PagoPilaCondVehiEntity> pagoPilaAnterior = findByIdVehiculoIdConductor(driverDTO.getId(),
                driverDTO.getIdConductor());
        if (pagoPilaAnterior!=null && !pagoPilaAnterior.isEmpty()) {
            for (PagoPilaCondVehiEntity pagoPilaCondVehiEntity : pagoPilaAnterior) {
                pagoPilaCondVehiEntity.setIdEstado(false);
                update(pagoPilaCondVehiEntity);
            }         
        }
        
        for (PagoPilaDTO planilla : driverDTO.getPlanillas()) {
            PagoPilaCondVehiEntity pagoPilaNuevo = new PagoPilaCondVehiEntity();
            pagoPilaNuevo.setEmpresa(pagoPilaAnterior.iterator().next().getEmpresa());
            pagoPilaNuevo.setConductor(pagoPilaAnterior.iterator().next().getConductor());
            pagoPilaNuevo.setVehiculo(pagoPilaAnterior.iterator().next().getVehiculo());
            pagoPilaNuevo.setPeriodoPagoPila(Long.valueOf(planilla.getStringPeriodoPago().replaceAll("/", "")));
            pagoPilaNuevo.setNroAprobacionPago(planilla.getNumeroAprobacion());
            pagoPilaNuevo.setIdEstado(true);
            OperadoresPilaEntity operadorPila = operadoresPilaDAO.findByPrimaryKey(planilla.getOperadorPila());
            pagoPilaNuevo.setOperador(operadorPila);
            try {
                Calendar vigenciaFechaInicial = Calendar.getInstance();
                Calendar vigenciaFechaFinal = Calendar.getInstance();
                vigenciaFechaFinal.setTime(driverDTO.getFechaVigencia());
                pagoPilaNuevo.setFechaIniciaVigencia(vigenciaFechaInicial.getTime());
                pagoPilaNuevo.setFechaFinVigencia(vigenciaFechaFinal.getTime());
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
            create(pagoPilaNuevo);
        }
        
        
    }

    public List<PagoPilaCondVehiEntity> findByIdVehiculoIdConductor(Long idVehiculo, Long idConductor) {

        List<PagoPilaCondVehiEntity> PagoPilaCondVehi = new ArrayList<>();

        try {
            CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<PagoPilaCondVehiEntity> criteriaQuery = criteriaBuilder
                    .createQuery(PagoPilaCondVehiEntity.class);
            Root<PagoPilaCondVehiEntity> root = criteriaQuery.from(PagoPilaCondVehiEntity.class);
            criteriaQuery.where(
                    criteriaBuilder.equal(root.get(PagoPilaCondVehiEntity_.vehiculo).get(ConductorVehiculoEntity_.id),
                            idVehiculo),
                    criteriaBuilder.equal(root.get(PagoPilaCondVehiEntity_.conductor).get(ConductorEntity_.id),
                            idConductor),
                    criteriaBuilder.equal(root.get(PagoPilaCondVehiEntity_.idEstado), new Long(1)));
            try {
                PagoPilaCondVehi = entityManager.createQuery(criteriaQuery).getResultList();
                return PagoPilaCondVehi;
            } catch (NoResultException arg6) {
                LOGGER.error("Excepcion de Persistencia ", arg6);
                return null;
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return PagoPilaCondVehi;
    }

    @Transactional
    public List<PagoPilaCondVehiEntity> findAll(PagoPilaCondVehiEntity filter) {

        try {
            CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<PagoPilaCondVehiEntity> criteriaQuery = criteriaBuilder
                    .createQuery(PagoPilaCondVehiEntity.class);
            Root<PagoPilaCondVehiEntity> root = criteriaQuery.from(PagoPilaCondVehiEntity.class);

            List<Predicate> predicateList = new ArrayList<>();

            if (filter.getVehiculo() != null && filter.getVehiculo().getId() != 0) {
                predicateList.add(criteriaBuilder.equal(
                        root.get(PagoPilaCondVehiEntity_.vehiculo).get(ConductorVehiculoEntity_.id),
                        filter.getVehiculo().getId()));
            }

            if (filter.getPeriodoPagoPila() != 0) {
                Path<Long> path = root.get(PagoPilaCondVehiEntity_.periodoPagoPila);
                predicateList.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get(PagoPilaCondVehiEntity_.periodoPagoPila),
                                filter.getPeriodoPagoPila()),
                        criteriaBuilder.greaterThan(path,
                                filter.getPeriodoPagoPila())));
            }

            criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
            List<PagoPilaCondVehiEntity> PagoPilaCondVehiList = (List<PagoPilaCondVehiEntity>) entityManager
                    .createQuery(criteriaQuery).getResultList();
            return PagoPilaCondVehiList;
        } catch (Exception e) {
            LOGGER.error("Excepcion ", e);
            return null;
        }
    }

}
