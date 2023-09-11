package co.gov.movilidadbogota.core.dao;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import co.gov.movilidadbogota.core.dto.ParametroDTO;
import co.gov.movilidadbogota.core.entity.ParametroSimurEntity;
import co.gov.movilidadbogota.core.entity.ParametroSimurEntity_;
import co.gov.movilidadbogota.core.security.SecurityConstants;

@Repository
public class ParametroSimurDAO extends AbstractDAO<ParametroSimurEntity> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParametroSimurDAO.class+LOG_PREFIX);

	public ParametroSimurDAO() {
		super(ParametroSimurEntity.class);
	}

	public ParametroDTO findByKey(String key) {
		ParametroSimurEntity entity = findOneByAttribute(ParametroSimurEntity_.codigoParametro, key);
		if (entity != null) {
			return new ParametroDTO(entity.getCodigoParametro(), entity.getValorParametro(),
					entity.getDescripcionParametro());
		} else {
			return null;
		}
	}

	public Hashtable<String, String> getLdapParameters() {

		String ldapURL = "";
		String ldapUsuarioDN = "";
		String ldapPrefijo = "";
		String ldapClave = "";
		String ldapAuth = "";
		String ldapBase = "";
		String ldapFilter = "";

		Hashtable<String, String> ldapParameters = new Hashtable<String, String>();

		try {

			// ldapParameters.put(SecurityConstants.LDAP_URL, ldapURL =
			// "ldap://ldap.minminas.gov.co:389");
			// ldapParameters.put(SecurityConstants.LDAP_USUARIO, ldapUsuarioDN
			// = "ldap_sith");
			// ldapParameters.put(SecurityConstants.LDAP_PREFIJO, ldapPrefijo =
			// "MINMINAS\\");
			// ldapParameters.put(SecurityConstants.LDAP_CLAVE, ldapClave =
			// "Q1w2e3r4t5!");
			// ldapParameters.put(SecurityConstants.LDAP_SECURITY_AUTHENTICATION,
			// ldapAuth = "simple");
			// ldapParameters.put(SecurityConstants.LDAP_BASE, ldapBase =
			// "DC=minminas,DC=gov,DC=co");
			// ldapParameters.put(SecurityConstants.LDAP_FILTER, ldapFilter =
			// "&(objectClass=user)(sAMAccountName=");

			ldapParameters.put(SecurityConstants.LDAP_URL,
					ldapURL = findByKey(SecurityConstants.LDAP_URL).getValorParametro());
			ldapParameters.put(SecurityConstants.LDAP_USUARIO,
					ldapUsuarioDN = findByKey(SecurityConstants.LDAP_USUARIO).getValorParametro());
			ldapParameters.put(SecurityConstants.LDAP_PREFIJO,
					ldapPrefijo = findByKey(SecurityConstants.LDAP_PREFIJO).getValorParametro());
			ldapParameters.put(SecurityConstants.LDAP_CLAVE,
					ldapClave = findByKey(SecurityConstants.LDAP_CLAVE).getValorParametro());
			ldapParameters.put(SecurityConstants.LDAP_SECURITY_AUTHENTICATION,
					ldapAuth = findByKey(SecurityConstants.LDAP_SECURITY_AUTHENTICATION).getValorParametro());
			ldapParameters.put(SecurityConstants.LDAP_BASE,
					ldapBase = findByKey(SecurityConstants.LDAP_BASE).getValorParametro());
			ldapParameters.put(SecurityConstants.LDAP_FILTER,
					ldapFilter = findByKey(SecurityConstants.LDAP_FILTER).getValorParametro());

			LOGGER.debug("LDAP_URL :" + ldapURL + ", LDAP_USUARIO_DN :" + ldapUsuarioDN + "," + " LDAP_PREFIJO : "
					+ ldapPrefijo + ", LDAP_CLAVE : " + ldapClave + ", LDAP_SECURITY_AUTHENTICATION : " + ldapAuth
					+ ", LDAP_BASE : " + ldapBase + ", LDAP_FILTER : " + ldapFilter);

		} catch (Exception exception) {
			LOGGER.error(exception.getMessage(), exception);
		}

		return ldapParameters;

	}
}
