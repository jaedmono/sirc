package co.gov.movilidadbogota.ws.duups;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.gov.movilidadbogota.core.dao.ParametroSimurDAO;
import co.gov.movilidadbogota.core.dto.ParametroDTO;
import co.gov.movilidadbogota.core.util.SystemParameters;
import co.gov.movilidadbogota.web.servicioreceptorpersonaduups.ConfirmacionRecibo;
import co.gov.movilidadbogota.web.servicioreceptorpersonaduups.NotificacionPersona;

@Component("wsDuupsClient")
public class WSDuupsClient {

	private static final String LOG_PREFIX = "[SIRC-WEB]";
    private static final Logger LOGGER = LoggerFactory.getLogger(WSDuupsClient.class+LOG_PREFIX);

    @Autowired
    private ParametroSimurDAO parametroSimurDAO;

    public ConfirmacionRecibo registroPersonaUbicabilidad(String endPoint, NotificacionPersona notificacionPersona) {
        try {

            ServicioReceptorPersonaDUUPSService service = new ServicioReceptorPersonaDUUPSService(new URL(endPoint));
            ServicioReceptorPersona port = service.getServicioReceptorPersonaDUUPSPort();

            Map<String, Object> req_ctx = ((BindingProvider) port).getRequestContext();
            Map<String, List<String>> headers = new HashMap<String, List<String>>();

            ParametroDTO usuario = parametroSimurDAO.findByKey(SystemParameters.CABECERA_USUARIO_DUUPS.getValue());
            ParametroDTO password = parametroSimurDAO.findByKey(SystemParameters.CABECERA_PASSWORD_DUUPS.getValue());
            ParametroDTO ipCliente = parametroSimurDAO.findByKey(SystemParameters.CABECERA_IPCLIENTE_DUUPS.getValue());

            headers.put("Usuario", Collections.singletonList(usuario.getValorParametro()));
            headers.put("Password", Collections.singletonList(password.getValorParametro()));
            headers.put("IpCliente", Collections.singletonList(ipCliente.getValorParametro()));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

            return port.registroPersonaUbicabilidad(notificacionPersona);
        } catch (Exception e) {
            LOGGER.error("error creando el servicio: ", e);
            return null;
        }
    }

}
