package co.gov.movilidadbogota.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropiedadesUtil {

    private static String PROPERTIES = "tarjeta.properties";
    private static Properties prop;
    private final static String LOG_PREFIX = "[SIRC-WS]";
    private static final Logger LOG = Logger.getLogger(PropiedadesUtil.class.getName()+LOG_PREFIX);

    public static String leerPropiedades(String propiedad) throws IOException {
        return leer(propiedad);
    }

    private static String leer(String propiedad) {

        try {
            if (prop == null) {
                prop = new Properties();
                prop.load(PropiedadesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES));
            }
            return prop.getProperty(propiedad);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e.getMessage());
        }
        return null;
    }
    
    public static void guardar(String propiedad, String valor) {

        try {
            if (prop == null) {
                prop = new Properties();
                prop.load(PropiedadesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES));
            }
            FileOutputStream out = new FileOutputStream(PROPERTIES);
            prop.setProperty(propiedad, valor);
            prop.store(out, null);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e.getMessage());
        }
    }

}
