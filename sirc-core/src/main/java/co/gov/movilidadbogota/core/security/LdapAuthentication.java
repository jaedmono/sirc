package co.gov.movilidadbogota.core.security;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.LoggerFactory;

import co.gov.movilidadbogota.core.dto.UsuarioDTO;

public class LdapAuthentication {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(LdapAuthentication.class);

	public void validateLdapAuthentication(String user, String password, Hashtable<String, String> ldapParameters) throws Exception {

		// final Properties properties = obtainProperties();
		DirContext ldapContext = createLdapConnection(user, password, ldapParameters);
		closeLdapConnection(ldapContext);

	}

	public DirContext createLdapConnection(String user, final String password, Hashtable<String, String> ldapParameters) throws Exception {
		DirContext ldapContext = null;
		try {

			final Hashtable<String, String> ldapEnv = new Hashtable<String, String>();
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL, ldapParameters.get(SecurityConstants.LDAP_URL));
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, ldapParameters.get(SecurityConstants.LDAP_SECURITY_AUTHENTICATION));
			ldapEnv.put(Context.SECURITY_PRINCIPAL, user);
			ldapEnv.put(Context.SECURITY_CREDENTIALS, password);

			ldapContext = new InitialDirContext(ldapEnv);

		} catch (AuthenticationException ex) {
			throw new Exception("Error : " + user, ex);
		} catch (NamingException ex) {
			throw new Exception("Error : " + user, ex);
		} catch (Exception e) {
			throw new Exception("Error : " + user, e);
		}
		return ldapContext;
	}

	public void closeLdapConnection(DirContext ldapContext) {
		if (ldapContext != null) {
			try {
				ldapContext.close();
			} catch (NamingException e) {
				LOG.warn("Error cerrando la conexión de LDAP", e);
			}
		}
	}
	
	private static Hashtable<String, String> getLdapParameters() {

		Hashtable<String, String> ldapParameters = new Hashtable<String, String>();

		try {

			ldapParameters.put(SecurityConstants.LDAP_URL, "ldap://192.168.100.120:389");
			ldapParameters.put(SecurityConstants.LDAP_USUARIO, "Prueba Fabrica de Software");
			ldapParameters.put(SecurityConstants.LDAP_PREFIJO, "SDM\\");
			ldapParameters.put(SecurityConstants.LDAP_CLAVE, "movilidad2017");
			ldapParameters.put(SecurityConstants.LDAP_SECURITY_AUTHENTICATION, "simple");
			ldapParameters.put(SecurityConstants.LDAP_BASE,	"DC=movilidadbogota,DC=gov,DC=co");
			ldapParameters.put(SecurityConstants.LDAP_FILTER, "&(objectClass=user)(sAMAccountName=");
			
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
		}

		return ldapParameters;

	}
	
	public static void main(String[] args) {
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("-------------------EJECUTANDO PRUEBAS LDAP USUARIO-------------------");
			System.out.println("---------------------------------------------------------------------");

			String userName = "pruebafabricasof";//"ldap_sith";
			try {
				
				Hashtable<String, String> ldapParameters = getLdapParameters();
				
				LdapFacadeImpl facadeLdap = new LdapFacadeImpl();
				UsuarioDTO user = facadeLdap.connectLDAP(userName, ldapParameters);
				
				System.out.println("------ldapParameters: " + ldapParameters);
				
				//System.out.println("*****RESULT*****" + facadeLdap.validateLDAPUser("pruebafabricasof@movilidadbogota.gov.co", "movilidad2017", ldapParameters) );
				
				if (user.getLoginUsuario() == null) {
					throw new RuntimeException();
				}else{
					System.out.println("SE HA CONSULTADO SATISFACTORIAMENTE EL USUARIO: " + userName + " CON LOGIN " + user.getNombre() +" "+ user.getApellido());
				}
				
				System.out.println(facadeLdap.validateLDAPUser("pruebafabricasof", "movilidad2017", ldapParameters));

			} catch (Exception e) {
				System.err.println("HA FALLADO LA CONSULTAR PARA EL USUARIO INTERNO: " + userName);

			}

			System.out.println("---------------------------------------------------------------------");
			System.out.println("-------------------FINALIZANDO PRUEBAS LDAP USUARIO------------------");
			System.out.println("---------------------------------------------------------------------");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
