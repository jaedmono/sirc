package co.gov.movilidadbogota.wsOLD.duups;

import java.net.URL;

import co.gov.movilidadbogota.servicioreceptorpersonaduups.ConfirmacionRecibo;
import co.gov.movilidadbogota.servicioreceptorpersonaduups.NotificacionPersona;
import java.net.MalformedURLException;

public class WSDuupsClient {

	//private static final Logger LOGGER = LoggerFactory.getLogger(WSDuupsClient.class);
    public ConfirmacionRecibo registroPersonaUbicabilidad(String endPoint, NotificacionPersona notificacionPersona) {
        try {
            ServicioReceptorPersonaDUUPSService service = new ServicioReceptorPersonaDUUPSService(new URL(endPoint));
            ServicioReceptorPersona receptorPersona = service.getServicioReceptorPersonaDUUPSPort();
            return receptorPersona.registroPersonaUbicabilidad(notificacionPersona);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
