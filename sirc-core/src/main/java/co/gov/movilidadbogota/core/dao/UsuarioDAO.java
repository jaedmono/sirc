package co.gov.movilidadbogota.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.gov.movilidadbogota.core.dto.ConsultarEmpresaDTO;
import co.gov.movilidadbogota.core.dto.EmpresaDTO;
import co.gov.movilidadbogota.core.dto.UsuarioDTO;
import co.gov.movilidadbogota.core.entity.EmpresaEntity;
import co.gov.movilidadbogota.core.entity.EmpresaEntity_;
import co.gov.movilidadbogota.core.entity.UsuarioEntity;
import co.gov.movilidadbogota.core.entity.UsuarioEntity_;
import co.gov.movilidadbogota.core.util.NotificationSender;

@Repository
public class UsuarioDAO extends AbstractDAO<UsuarioEntity> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDAO.class+LOG_PREFIX);

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private NotificationSender notificationSender;
	

	public UsuarioDAO() {
		super(UsuarioEntity.class);
	}

	public static List<UsuarioDTO> entityListToBasicDtoList(List<UsuarioEntity> entityList) {
		return entityList == null ? null
				: entityList.stream().map(UsuarioDAO::entityToDtoBasic).collect(Collectors.toList());
	}

	public static UsuarioDTO entityToDtoBasic(UsuarioEntity entity) {
		return new UsuarioDTO(entity.getLoginUsuario(),
				entity.getPersona() != null ? entity.getPersona().getCorreoElectronico() : null);
	}

	public static UsuarioDTO entityToDto(UsuarioEntity entity) {
		return new UsuarioDTO(entity.getLoginUsuario(),
				entity.getPersona() != null ? entity.getPersona().getCorreoElectronico() : null,
				EmpresaDAO.entityListToBasicDtoList(entity.getEmpresaEntityList()));
	}

	@Transactional
	public UsuarioEntity findByUserIdCompanyId(Long userId, Long companyId) {

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioEntity> criteriaQuery = criteriaBuilder.createQuery(UsuarioEntity.class);
			Root<UsuarioEntity> root = criteriaQuery.from(UsuarioEntity.class);
			criteriaQuery.where(criteriaBuilder.equal(root.get(UsuarioEntity_.id), userId), criteriaBuilder
					.equal(root.join(UsuarioEntity_.empresaEntityList).get(EmpresaEntity_.id), companyId));
			try {
				UsuarioEntity usuario = (UsuarioEntity) entityManager.createQuery(criteriaQuery).getSingleResult();
				return usuario;
			} catch (NoResultException arg6) {// 135
				return null;// 136
			}
		} catch (NoResultException e) {
			LOGGER.error("Excepcion de Persistencia", e);

		} catch (PersistenceException pe) {
			LOGGER.error("Excepcion de Persistencia", pe);
		}
		return null;
	}
        
	@Transactional
	public UsuarioEntity findByUserName(String userName) {

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioEntity> criteriaQuery = criteriaBuilder.createQuery(UsuarioEntity.class);
			Root<UsuarioEntity> root = criteriaQuery.from(UsuarioEntity.class);
			criteriaQuery.where(criteriaBuilder.equal(root.get(UsuarioEntity_.loginUsuario), userName));
			try {
				UsuarioEntity usuario = (UsuarioEntity) entityManager.createQuery(criteriaQuery).getSingleResult();
				return usuario;
			} catch (NoResultException arg6) {// 135
				return null;// 136
			}
		} catch (NoResultException e) {
			LOGGER.error("Excepcion de Persistencia", e);

		} catch (PersistenceException pe) {
			LOGGER.error("Excepcion de Persistencia", pe);
		}
		return null;
	}
	
	@Transactional
	public UsuarioEntity findUserByCompanyId(Long companyId) {

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioEntity> criteriaQuery = criteriaBuilder.createQuery(UsuarioEntity.class);
			Root<UsuarioEntity> root = criteriaQuery.from(UsuarioEntity.class);
			criteriaQuery.where(criteriaBuilder
					.equal(root.join(UsuarioEntity_.empresaEntityList).get(EmpresaEntity_.id), companyId));
			try {
				UsuarioEntity usuario = (UsuarioEntity) entityManager.createQuery(criteriaQuery).getSingleResult();
				return usuario;
			} catch (NoResultException arg6) {// 135
				return null;// 136
			}
		} catch (NoResultException e) {
			LOGGER.error("Excepcion de Persistencia", e);

		} catch (PersistenceException pe) {
			LOGGER.error("Excepcion de Persistencia", pe);
		}
		return null;
	}

	@Transactional
	public List<ConsultarEmpresaDTO> findCompanysByUserId(Long userId) {

		try {

			UsuarioEntity usuario = findByPrimaryKey(userId);
			List<ConsultarEmpresaDTO> resultado = new ArrayList<>();

			for (EmpresaEntity empresaEntity : usuario.getEmpresaEntityList()) {
				resultado.add(new ConsultarEmpresaDTO(empresaEntity.getId(), empresaEntity.getNombreRazonSocial()));
			}

			return resultado;

		} catch (NoResultException e) {
			LOGGER.error("Excepcion de Persistencia", e);

		} catch (PersistenceException pe) {
			LOGGER.error("Excepcion de Persistencia", pe);
		}
		return null;
	}

	@Transactional
	public List<EmpresaDTO> findCompanysByUserpk(long userId) {
		try {
			UsuarioEntity usuario = findByPrimaryKey(userId);
			return EmpresaDAO.entityListToBasicDtoList(usuario.getEmpresaEntityList());
		} catch (NoResultException e) {
			LOGGER.error("Excepcion de Persistencia", e);
		} catch (PersistenceException pe) {
			LOGGER.error("Excepcion de Persistencia", pe);
		}
		return null;
	}
        
        @Transactional
	public List<EmpresaDTO> findCompanysByUserName(String userName) {
		try {
			UsuarioEntity usuario = findByUserName(userName);
			return EmpresaDAO.entityListToBasicDtoList(usuario.getEmpresaEntityList());
		} catch (NoResultException e) {
			LOGGER.error("Excepcion de Persistencia", e);
		} catch (PersistenceException pe) {
			LOGGER.error("Excepcion de Persistencia", pe);
		}
		return null;
	}

	@Transactional
	public String associateCompany(EmpresaEntity company, long userId) {
		try {
			UsuarioEntity usuario = findByPrimaryKey(userId);
			if (usuario != null) {
				usuario.getEmpresaEntityList().add(company);
				update(usuario);

				return "success";
			}
		} catch (NoResultException e) {
			LOGGER.error("Excepcion de Persistencia", e);
			return "error";
		} catch (PersistenceException pe) {
			LOGGER.error("Excepcion de Persistencia", pe);
			return "error";
		}

		return "error";
	}

	@Transactional
	public String disassociateCompany(EmpresaEntity company, long userId) {
		try {
			UsuarioEntity usuario = findByPrimaryKey(userId);
			if (usuario != null) {
				for (EmpresaEntity empresaEntity : usuario.getEmpresaEntityList()) {
					if (empresaEntity.getId() == company.getId()) {
						usuario.getEmpresaEntityList().remove(empresaEntity);
						update(usuario);
						return "success";
					}
				}
			}
		} catch (NoResultException e) {
			LOGGER.error("Excepcion de Persistencia", e);
			return "error";
		} catch (PersistenceException pe) {
			LOGGER.error("Excepcion de Persistencia", pe);
			return "error";
		}

		return "error";
	}

	@Transactional
	public UsuarioDTO findById(long id) {
		UsuarioEntity entity = findByPrimaryKey(id);

		List<EmpresaDTO> eDto = EmpresaDAO.entityListToDtoList(entity.getEmpresaEntityList());
		UsuarioDTO u = new UsuarioDTO(entity.getId(), entity.getLoginUsuario());
		u.setEmpresas(eDto);
		return u;
	}

	@Transactional
	public void disableUser(long id) {
		UsuarioEntity entity = findByPrimaryKey(id);
		entity.setIdEstado(false);
		update(entity);
	}

	@Transactional
	public List<UsuarioEntity> findByState(boolean state) {

		try {
			CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioEntity> criteriaQuery = criteriaBuilder.createQuery(UsuarioEntity.class);
			Root<UsuarioEntity> root = criteriaQuery.from(UsuarioEntity.class);
			criteriaQuery.where(criteriaBuilder.equal(root.get(UsuarioEntity_.idEstado), state));
			try {
				List<UsuarioEntity> usuario = (List<UsuarioEntity>) entityManager.createQuery(criteriaQuery)
						.getResultList();
				return usuario;
			} catch (NoResultException arg6) {// 135
				return null;// 136
			}
		} catch (NoResultException e) {
			LOGGER.error("Excepcion de Persistencia", e);

		} catch (PersistenceException pe) {
			LOGGER.error("Excepcion de Persistencia", pe);
		}
		return null;
	}

	@Transactional
	public void crearUsuario (UsuarioEntity entity) throws MessagingException{
		
		create(entity);
	}
	
	
	@Transactional
	public void actualizarUsuario (UsuarioEntity entity, StringBuilder message, String subject ) throws MessagingException{
		
		update(entity);

		notificationSender.sendEmail(entity.getLoginUsuario(), subject, message.toString());
	}
}
