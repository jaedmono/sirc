package co.gov.movilidadbogota.notification.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import co.gov.movilidadbogota.web.controller.NotificationController;

public class NotificationEmailJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(NotificationEmailJob.class);
	private static final String LOG_PREFIX = "[SIRC-WEB]";

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info(LOG_PREFIX+"Iniciando el job.");

			WebApplicationContext springContext = ContextLoader.getCurrentWebApplicationContext();
			NotificationController notificationController = springContext.getBean(NotificationController.class);

			notificationController.sendNotifications();

			logger.info(LOG_PREFIX+"El job finalizo satisfactoriamente.");
		} catch (Exception e) {
			logger.error(LOG_PREFIX+"Ha ocurrido un error ejecutando el job", e);
		}
	}

}
