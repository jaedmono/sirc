/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.movilidadbogota.notification.job;

import co.gov.movilidadbogota.core.dao.ConductorVehiculoDAO;
import co.gov.movilidadbogota.core.entity.ConductorVehiculoEntity;
import java.util.Calendar;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author mbogota
 */
public class CancelarTarjetaControlJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelarTarjetaControlJob.class);

    /**
     * La tarjeta de control debe vencer automáticamente si no se ha realizado
     * su refrendación como máximo el día correspondiente a la fecha de vigencia
     * de la misma. En caso que se sobrepase la fecha de vigencia el sistema
     * debe cambiar el estado de la tarjeta de control a Cancelada y el motivo
     * de dicha cancelación es: No se renovó la tarjeta de control dentro del
     * período estipulado.
     *
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        try {
            WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
            ConductorVehiculoDAO conductorVehiculoDAO = context.getBean(ConductorVehiculoDAO.class);
            Calendar hoy = Calendar.getInstance();
            List<ConductorVehiculoEntity> result = conductorVehiculoDAO.tarjetasControlVencidas(hoy.getTime());
            if (result != null && !result.isEmpty()) {
                for (ConductorVehiculoEntity conductorVehiculoEntity : result) {
                    conductorVehiculoDAO.cancelarTarjetaControlVencida(conductorVehiculoEntity);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Se ha presentado un error ejecutando la cancelación de las tarjetas de contro.", e);
        }
    }
}
