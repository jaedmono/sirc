/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.core.dao;

import co.gov.movilidadbogota.core.dto.DispositivoMovilDTO;
import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity_;
import co.gov.movilidadbogota.core.entity.DispositivosMovilEntity;
import co.gov.movilidadbogota.core.entity.DispositivosMovilEntity_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mbogota
 */
@Repository
public class DispositivosMovilDAO extends AbstractDAO<DispositivosMovilEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispositivosMovilDAO.class+LOG_PREFIX);

    @PersistenceContext
    private EntityManager entityManager;

    public DispositivosMovilDAO() {
        super(DispositivosMovilEntity.class);
    }

    public List<DispositivoMovilDTO> consultarDispositivosMovil(long idVehiculo) {
        List<DispositivoMovilDTO> dispositivosMovilListDTO = new ArrayList();
        try {
            CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<DispositivosMovilEntity> criteriaQuery = criteriaBuilder.createQuery(DispositivosMovilEntity.class);
            Root<DispositivosMovilEntity> root = criteriaQuery.from(DispositivosMovilEntity.class);
            List<Predicate> predicates = new ArrayList();
            predicates.add(criteriaBuilder.equal(root.get(DispositivosMovilEntity_.vehiculo).get(ConductorVehiculoEntity_.id), idVehiculo));
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            List<DispositivosMovilEntity> dispositivosList = (List<DispositivosMovilEntity>) entityManager.createQuery(criteriaQuery).getResultList();
            for (DispositivosMovilEntity dispositivo : dispositivosList) {
                DispositivoMovilDTO dispositivoDTO = new DispositivoMovilDTO();
                dispositivoDTO.setId(dispositivo.getId());
                dispositivoDTO.setMacIdUno(dispositivo.getMacIdUno());
                dispositivoDTO.setMacIdDos(dispositivo.getMacIdDos());
                dispositivoDTO.setImeiUno(dispositivo.getImeiUno());
                dispositivoDTO.setImeiDos(dispositivo.getImeiDos());
                dispositivoDTO.setImeiTres(dispositivo.getImeiTres());
                dispositivoDTO.setImeiCuatro(dispositivo.getImeiCuatro());
                dispositivosMovilListDTO.add(dispositivoDTO);
            }
        } catch (Exception e) {
            LOGGER.error("Excepcion ", e);
        }
        return dispositivosMovilListDTO;
    }
}
