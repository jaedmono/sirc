package co.gov.movilidadbogota.core.dao;

import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity;
import co.gov.movilidadbogota.core.entity.RtoVigenciaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RtoVigenciaDAO extends AbstractDAO<RtoVigenciaEntity>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ConductorVehiculoDAO.class+LOG_PREFIX);

    @PersistenceContext
    private EntityManager entityManager;

    public RtoVigenciaDAO(){
        super(RtoVigenciaEntity.class);
    }

    public List<RtoVigenciaEntity> findAllByPlate(String plate) {
        try {

            Query query = this.entityManager.createQuery("SELECT DISTINCT x FROM RtoVigenciaEntity x "
                    + "WHERE x.placa = :plate ");

            query.setParameter("plate", plate);

            return (List<RtoVigenciaEntity>) query.getResultList();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
