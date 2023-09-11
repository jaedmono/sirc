package co.gov.movilidadbogota.core.security;

public class LdapEnum {
    
        public enum Parametros
    {
        INITIAL_CONTEXT_FACTORY,
        PROVIDER_URL,
        SECURITY_AUTHENTICATION,
        SECURITY_PRINCIPAL,
        SECURITY_CREDENTIALS,
        SESSION_TIMEOUT,
        SMTP
    }

        public final static String[] ATTR_IDS_LDAP ={"sAMAccountName", "displayName","givenname","sn","telephonenumber","st","l","title","description","department","physicalDeliveryOfficeName","mail"}; 
}
