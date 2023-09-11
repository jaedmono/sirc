package co.gov.movilidadbogota.notification.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import co.gov.movilidadbogota.core.dao.ParametroSimurDAO;
import co.gov.movilidadbogota.core.dto.ParametroDTO;
import co.gov.movilidadbogota.core.util.SystemParameters;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionQuarzJob.
 */
public class ActionQuarzJob {

    /**
     * The Constant logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionQuarzJob.class);
    /**
     * The scheduler.
     */
    private static Scheduler scheduler;
    /**
     * The loaded.
     */
    private static boolean loaded = false;
    /**
     * The Constant JOB_NAME.
     */
    private static final String NOTIFICATIONEMAIL_JOB = NotificationEmailJob.class.getName() + "_JOB";
    private static final String CANCELARTARJETACONTROLJOB_JOB = CancelarTarjetaControlJob.class.getName() + "_JOB";
    /**
     * The Constant TRIGGER_NAME.
     */
    private static final String NOTIFICATIONEMAIL_TRIGGER = NotificationEmailJob.class.getName() + "_TRIGGER";
    private static final String CANCELARTARJETACONTROLJOB_TRIGGER = CancelarTarjetaControlJob.class.getName() + "_TRIGGER";
    /**
     * The Constant GROUP_NAME.
     */
    private static final String GROUP_NAME = Scheduler.DEFAULT_GROUP;
    public static final String CRON_EXPRESSION = "0 0 23 ${intervalo} * ?";

    /**
     * Creates the job.
     */
    public static void createJob() {
        try {
            // schedule it
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            createJobNotificationEmail();
            createJobCancelarTarjetaControl();
        } catch (Exception e) {
            LOGGER.error("Ha ocurrido un error al crear el job: ", e);
            loaded = false;
        }
    }

    /**
     * Inicia la tarea de notificaciones
     *
     * @throws Exception
     */
    private static void createJobNotificationEmail() throws Exception {
        String cronExpression;
        try {
            WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
            ParametroSimurDAO parametroSimurDAO = context.getBean(ParametroSimurDAO.class);
            ParametroDTO sp = parametroSimurDAO.findByKey(SystemParameters.NOT_EMAIL_TIMER.getValue());
            if (sp != null) {
                cronExpression = CRON_EXPRESSION.replaceAll("\\$\\{intervalo\\}", "1/" + sp.getValorParametro());
            } else {
                cronExpression = CRON_EXPRESSION.replace("\\$\\{intervalo\\}", "1/1");
            }
        } catch (Exception e) {
            cronExpression = "0 0 23 1/1 * ?";
            LOGGER.error("ha ocurrido un error al intentar optener los parametros para la configuracion del cron:", e);
        }
        JobDetail job = JobBuilder.newJob(NotificationEmailJob.class).withIdentity(NOTIFICATIONEMAIL_JOB, GROUP_NAME).build();
        // Fire at 00:01 AM every day
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(NOTIFICATIONEMAIL_TRIGGER, GROUP_NAME)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
        scheduler.scheduleJob(job, trigger);
        loaded = true;
    }

    /**
     * Inicia la tarea de cancelación de tarjetas de control vencidas
     *
     * @throws Exception
     */
    private static void createJobCancelarTarjetaControl() throws Exception {
        JobDetail job = JobBuilder.newJob(CancelarTarjetaControlJob.class).withIdentity(CANCELARTARJETACONTROLJOB_JOB, GROUP_NAME).build();
        //Trigger trigger = TriggerBuilder.newTrigger().withIdentity(CANCELARTARJETACONTROLJOB_TRIGGER, GROUP_NAME).startNow().build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(CANCELARTARJETACONTROLJOB_TRIGGER, GROUP_NAME).withSchedule(CronScheduleBuilder.cronSchedule("59 59 23 1/1 * ?")).build();
        scheduler.scheduleJob(job, trigger);
        loaded = true;
    }

    /**
     * Kill job.
     */
    public static void killJob() {
        try {
            scheduler.shutdown();
            loaded = false;
        } catch (Exception e) {
            LOGGER.error("Ha ocurrido un error al intentar apagar el job: ", e);
        }
    }

    /**
     * Reschedule trigger.
     *
     * @param cronExpression the cron expression
     */
    public static void rescheduleTrigger(String cronExpression) {
        try {
            TriggerKey triggerKey = new TriggerKey(NOTIFICATIONEMAIL_TRIGGER, GROUP_NAME);
            // retrieve the trigger
            Trigger oldTrigger = scheduler.getTrigger(triggerKey);

            // obtain a builder that would produce the trigger
            TriggerBuilder tb = oldTrigger.getTriggerBuilder();

            // update the schedule associated with the builder, and build the
            // new trigger
            // (other builder methods could be called, to change the trigger in
            // any desired way)
            Trigger newTrigger = tb.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

            scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
            loaded = true;
        } catch (Exception ex) {
            LOGGER.error("Ha ocurrido un error al intentar actualizar el trigger: ", ex);
            loaded = false;
        }
    }

    /**
     * Gets the cron expression.
     *
     * @return the cron expression
     */
    public static String getCronExpression() {
        try {
            String cronExpr = null;
            TriggerKey triggerKey = new TriggerKey(NOTIFICATIONEMAIL_TRIGGER, GROUP_NAME);
            Trigger oldTrigger = scheduler.getTrigger(triggerKey);
            if (oldTrigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) oldTrigger;
                cronExpr = cronTrigger.getCronExpression();
            }
            return cronExpr;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets the scheduler.
     *
     * @return the scheduler
     */
    public static Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * Sets the scheduler.
     *
     * @param scheduler the new scheduler
     */
    public static void setScheduler(Scheduler scheduler) {
        ActionQuarzJob.scheduler = scheduler;
    }

    /**
     * Checks if is loaded.
     *
     * @return true, if is loaded
     */
    public static boolean isLoaded() {
        return loaded;
    }

    /**
     * Sets the loaded.
     *
     * @param loaded the new loaded
     */
    public static void setLoaded(boolean loaded) {
        ActionQuarzJob.loaded = loaded;
    }

}
