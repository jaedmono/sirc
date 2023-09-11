package co.gov.movilidadbogota.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextListenerImpl implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextListenerImpl.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        
    }
}
