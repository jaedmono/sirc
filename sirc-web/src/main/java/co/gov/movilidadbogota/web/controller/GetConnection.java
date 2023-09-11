package co.gov.movilidadbogota.web.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class GetConnection {
	/** Uses JNDI and Datasource (preferred style). */
	public static Connection getJNDIConnection() {
		String DATASOURCE_CONTEXT = "java:/sircDS";
		Connection result = null;
		try {
			Context initialContext = new InitialContext();
			if (initialContext == null) {
				log("JNDI problem. Cannot get InitialContext.");
			}
			DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
			if (datasource != null) {
				result = datasource.getConnection();
			} else {
				log("Failed to lookup datasource.");
			}
		} catch (NamingException ex) {
			log("Cannot get connection: " + ex);
		} catch (SQLException ex) {
			log("Cannot get connection: " + ex);
		}
		return result;
	}

	private static void log(Object aObject) {
		System.out.println(aObject);
	}
}
