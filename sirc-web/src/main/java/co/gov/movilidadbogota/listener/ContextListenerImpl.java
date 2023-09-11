package co.gov.movilidadbogota.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.movilidadbogota.notification.job.ActionQuarzJob;

public class ContextListenerImpl implements ServletContextListener {

	private static final String LOG_PREFIX = "[SIRC-WEB]";
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextListenerImpl.class+LOG_PREFIX);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        ActionQuarzJob.killJob();
        if (!ActionQuarzJob.isLoaded()) {
            LOGGER.info("Se ha apagado el job de manera exitosa.");
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ActionQuarzJob.createJob();
        if (ActionQuarzJob.isLoaded()) {
            LOGGER.info("Se ha creado el job de manera exitosa.");
        }
    }
}
