package co.gov.movilidadbogota.core.security;


import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.movilidadbogota.core.dto.UsuarioDTO;

public class LdapFacadeImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(LdapFacadeImpl.class);

	@SuppressWarnings("rawtypes")
	public UsuarioDTO connectLDAP(String username, Hashtable<String, String> ldapParameters) {

		DirContext ldapContext = null;

		
		final String user = ldapParameters.get(SecurityConstants.LDAP_USUARIO); 
		final String password = ldapParameters.get(SecurityConstants.LDAP_CLAVE); 

		LdapAuthentication ldapAuthentication = new LdapAuthentication();
		UsuarioDTO userObj = new UsuarioDTO();
		try {
			ldapContext = ldapAuthentication.createLdapConnection(user, password, ldapParameters);
			final SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchControls.setReturningObjFlag(true);
			searchControls.setReturningAttributes(LdapEnum.ATTR_IDS_LDAP);

			userObj = findAccountByAccountName(ldapContext, ldapParameters.get(SecurityConstants.LDAP_BASE), username, ldapParameters);

		} catch (AuthenticationException ex) {
			LOGGER.info("1", ex);
		} catch (NamingException ex) {
			LOGGER.info("2", ex);
		} catch (Exception ex) {
			LOGGER.info("3", ex);
		} finally {
			ldapAuthentication.closeLdapConnection(ldapContext);
		}
		return userObj;
	}
	

	public UsuarioDTO findAccountByAccountName(DirContext ctx, String ldapSearchBase, String accountName, Hashtable<String, String> ldapParameters)
			throws NamingException {

		String searchFilter = "(" + ldapParameters.get(SecurityConstants.LDAP_FILTER) + accountName + "))";

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

		UsuarioDTO userDto = new UsuarioDTO();

		if (results.hasMoreElements()) {
			SearchResult searchResult = (SearchResult) results.nextElement();
			final Attributes attributes = searchResult.getAttributes();
			userDto.setLoginUsuario((attributes.get("samaccountname") != null
					? splitStringSp((String) attributes.get("samaccountname").get(), "1") : null));
			userDto.setEmail((attributes.get("mail") != null ? splitStringSp((String) attributes.get("mail").get(), "1")
					: null));
			// String identification = attributes.get("employeeid") != null ?
			// splitStringSp((String) attributes.get("employeeid").get(), "1")
			// :"0";
			// userDto.(Double.parseDouble(identification));
			// userDto.((attributes.get("department") != null ?
			// splitStringSp((String) attributes.get("department").get(), "1") :
			// null));
			if (attributes.get("givenName") != null) {
				String givenName = attributes.get("givenName").toString().replace("givenName: ", "");
				String nombres[] = givenName.split(" ");

				try {
					userDto.setNombre((nombres[0] != null ? nombres[0] : null));
				} catch (Exception e) {
					LOGGER.info("No tiene primer nombre registrado en el LDAP", e);
				}

				try {
					userDto.setNombre(userDto.getNombre() + " " + (nombres[1] != null ? nombres[1] : null));
				} catch (Exception e) {
					LOGGER.info("No tiene segundo nombre registrado en el LDAP", e);
				}
			}

			if (attributes.get("sn") != null) {
				String sn = attributes.get("sn").toString().replace("sn: ", "");
				String apellidos[] = sn.split(" ");

				try {
					userDto.setApellido((apellidos[0] != null ? sn : null));
				} catch (Exception e) {
					LOGGER.info("No tiene primer apellido registrado en el LDAP", e);
				}

				try {
					userDto.setApellido(userDto.getApellido() + " " + (apellidos[1] != null ? apellidos[1] : null));
				} catch (Exception e) {
					LOGGER.info("No tiene segundo apellido registrado en el LDAP", e);
				}
			}

			// userDto.(attributes.get("displayName") != null ?
			// attributes.get("displayName").toString().replace("displayName: ",
			// "") : null);
		}

		return userDto;
	}
	
	@SuppressWarnings("rawtypes")
	public String validateLDAPUser(String loginUserName, String loginUserPassword, Hashtable<String, String> ldapParameters) {

		DirContext ldapContext = null;

		final String user = ldapParameters.get(SecurityConstants.LDAP_USUARIO);
		final String password = ldapParameters.get(SecurityConstants.LDAP_CLAVE);

		LdapAuthentication ldapAuthentication = new LdapAuthentication();
		String isUserExist = null;
		try {
			ldapContext = ldapAuthentication.createLdapConnection(user, password, ldapParameters);
			final SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchControls.setReturningObjFlag(true);
			searchControls.setReturningAttributes(LdapEnum.ATTR_IDS_LDAP);

			isUserExist = validatePassword(ldapContext, ldapParameters.get(SecurityConstants.LDAP_BASE), loginUserName, loginUserPassword, ldapParameters);

		} catch (AuthenticationException ex) {
			isUserExist = "False";
			LOGGER.info("Error Autenticacion el ["+loginUserName+"]  en el ldap"  , ex);
		} catch (NamingException ex) {
			isUserExist = "False";
			LOGGER.info("No existe el ["+loginUserName+"]  en el ldap"  , ex);
		} catch (Exception ex) {
			isUserExist = "False";
			LOGGER.info("No existe el ["+loginUserName+"]  en el ldap"  , ex);
		} finally {
			ldapAuthentication.closeLdapConnection(ldapContext);
		}
		return isUserExist;
	}

	public String validatePassword(DirContext ctx, String ldapSearchBase, String accountName, String password, Hashtable<String, String> ldapParameters) throws NamingException {

		String searchFilter = "(" + ldapParameters.get(SecurityConstants.LDAP_FILTER) + accountName + "))";

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

		SearchResult searchResult = null;
		if (results.hasMoreElements()) {
			searchResult = (SearchResult) results.nextElement();
			final Attributes attributes = searchResult.getAttributes();

			Attribute dnAttr = attributes.get("distinguishedName");
			String dn = (String) dnAttr.get();
			Attribute accountControlAttr =  attributes.get("UserAccountControl");
			String accountControl = (String) accountControlAttr.get();
			if(accountControl.equalsIgnoreCase("514") || accountControl.equalsIgnoreCase("66050")){
				return "userInActive";	
			}
			// User Exists, Validate the Password
			DirContext ldapContext = null;
			LdapAuthentication ldapAuthentication = new LdapAuthentication();
			try {

				ldapContext = ldapAuthentication.createLdapConnection(dn, password, ldapParameters);

			} catch (Exception e) {
				LOGGER.info("Error inesperado validando usuario en el ldap", e);
				return "False";
				
			} finally {
				ldapAuthentication.closeLdapConnection(ldapContext);
			}
			return "True";
		}

		return "False";
	}

	private String splitStringSp(String text, String val) {
		String value = "";
		String split[];
		if ("1".equals(val)) {
			split = text.split(" ");
			if (split.length >= 0) {
				value = split[0];
			} else {
				value = "";
			}
		} else if (("2").equals(val)) {
			split = text.split(" ");
			if (split.length == 2) {
				value = split[1];
			} else {
				value = "";
			}
		}
		return value;
	}
}
