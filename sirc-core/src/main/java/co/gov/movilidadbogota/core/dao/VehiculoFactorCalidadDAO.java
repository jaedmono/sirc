package co.gov.movilidadbogota.core.dao;

import co.gov.movilidadbogota.core.dto.FactorCalidadDTO;
import co.gov.movilidadbogota.core.entity.EmpresaEntity_;
import org.springframework.stereotype.Repository;
import co.gov.movilidadbogota.core.entity.VehiculoFactorCalidadEntity;
import co.gov.movilidadbogota.core.entity.VehiculoFactorCalidadEntity_;
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

@Repository
public class VehiculoFactorCalidadDAO extends AbstractDAO<VehiculoFactorCalidadEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehiculoFactorCalidadDAO.class+LOG_PREFIX);

    @PersistenceContext
    private EntityManager entityManager;

    public VehiculoFactorCalidadDAO() {
        super(VehiculoFactorCalidadEntity.class);
    }

    /**
     * Permite consultar el factor de calidad por los filtros de empresa, nro
     * placa y estado
     *
     * @param factorCalidadDTO
     * @return
     */
    public List<FactorCalidadDTO> consultarFactorCalidad(FactorCalidadDTO factorCalidadDTO) {
        List<FactorCalidadDTO> factorCalidadListDTO = new ArrayList();
        try {
            CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<VehiculoFactorCalidadEntity> criteriaQuery = criteriaBuilder.createQuery(VehiculoFactorCalidadEntity.class);
            Root<VehiculoFactorCalidadEntity> root = criteriaQuery.from(VehiculoFactorCalidadEntity.class);
            List<Predicate> predicates = new ArrayList();
            //Filtro por id factor calidad
            if (factorCalidadDTO.getId() != 0) {
                predicates.add(criteriaBuilder.equal(root.get(VehiculoFactorCalidadEntity_.id),
                        factorCalidadDTO.getId()));
            }
            //Filtro por id empresa
            if (factorCalidadDTO.getIdEmpresa() != 0) {
                predicates.add(criteriaBuilder.equal(root.get(VehiculoFactorCalidadEntity_.empresa).get(EmpresaEntity_.id),
                        factorCalidadDTO.getIdEmpresa()));
            }
            //Filtro por nro placa
            if (factorCalidadDTO.getPlaca() != null && !factorCalidadDTO.getPlaca().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get(VehiculoFactorCalidadEntity_.nroPlaca),
                        factorCalidadDTO.getPlaca()));
            }
            //Filtro estado
            if (factorCalidadDTO.getIdEstado() != -1) {
                predicates.add(criteriaBuilder.equal(root.get(VehiculoFactorCalidadEntity_.idEstado),
                        factorCalidadDTO.getIdEstado()));
            }
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            List<VehiculoFactorCalidadEntity> factorCalidadList = (List<VehiculoFactorCalidadEntity>) entityManager.createQuery(criteriaQuery).getResultList();
            for (VehiculoFactorCalidadEntity factorCalidad : factorCalidadList) {
                FactorCalidadDTO factorDTO = new FactorCalidadDTO();
                factorDTO.setFechaNovedad(factorCalidad.getFechaNovedad());
                factorDTO.setFechaRegistro(factorCalidad.getFechaRegistro());
                factorDTO.setId(factorCalidad.getId());
                factorDTO.setIdEmpresa(factorCalidad.getEmpresa() != null ? factorCalidad.getEmpresa().getId() : 0);
                factorDTO.setRazonSocialEmpresa(factorCalidad.getEmpresa() != null ? factorCalidad.getEmpresa().getNombreRazonSocial() : "");
                factorDTO.setNitEmpresa(factorCalidad.getEmpresa() != null ? factorCalidad.getEmpresa().getIdNitEmpresa() : "");
                factorDTO.setCodSDMEmpresa(factorCalidad.getEmpresa() != null ? factorCalidad.getEmpresa().getCodigoEmpresa() : "");
                factorDTO.setIdEstado(factorCalidad.getIdEstado());
                factorDTO.setIdUsuario(factorCalidad.getUsuario() != null ? factorCalidad.getUsuario().getId() : 0);
                factorDTO.setPlaca(factorCalidad.getNroPlaca());
                factorCalidadListDTO.add(factorDTO);
            }
        } catch (Exception e) {
            LOGGER.error("Excepcion ", e);
        }
        return factorCalidadListDTO;
    }

}
