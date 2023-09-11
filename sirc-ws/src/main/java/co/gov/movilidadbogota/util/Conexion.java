/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author USER-LENOVO
 */
public class Conexion {

    public static void main(String[] args) throws NamingException {
        try {
        	
            String dbURL2 = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "SYSTEM";
            String password = "123456";

           // Connection conn = (Connection) getConnection(dbURL2, username, password);

           // Statement stmt = conn.createStatement();

            //String sql = "select max(ID) from T_USER";
            Connection conn = getConnection();
             
            Statement statement = conn.createStatement();
            String sql = "select username, email from users";
            ResultSet rs = statement.executeQuery(sql);
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (rs.next()) {
                for (int j = 1; j <= columnCount; j++) {
                    System.out.println("" + metaData.getColumnLabel(j) + " = " + rs.getObject(j));
                }

                //System.out.println("Id max " + rs.getInt(1));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws NamingException {

        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/sircDS");

            // Create connection to the database
            Connection conn = ds.getConnection();          

            if (conn != null) {
                System.out.println("Conexion Establecida " + new Date());
                return conn;
            } else {
                System.out.println("No se pudo realizar la Conexion");

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("No se pudo realizar la Conexion Error" + e);
            System.out.println("Ha ocurrido un error, No se pudo realizar la Conexion Error " + e);
            e.printStackTrace();
        }

        return null;
    }

}
