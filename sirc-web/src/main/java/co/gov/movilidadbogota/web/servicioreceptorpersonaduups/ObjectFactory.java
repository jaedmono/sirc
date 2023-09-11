
package co.gov.movilidadbogota.web.servicioreceptorpersonaduups;

import co.gov.movilidadbogota.ws.duups.*;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.gov.movilidadbogota.ws.duups package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ErrorRespuesta_QNAME = new QName("http://servicioreceptorpersonaduups.sdm.com/", "errorRespuesta");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.gov.movilidadbogota.ws.duups
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ErrorRespuesta }
     * 
     */
    public ErrorRespuesta createErrorRespuesta() {
        return new ErrorRespuesta();
    }

    /**
     * Create an instance of {@link ConfirmacionRecibo }
     * 
     */
    public ConfirmacionRecibo createConfirmacionRecibo() {
        return new ConfirmacionRecibo();
    }

    /**
     * Create an instance of {@link PersonaNatural }
     * 
     */
    public PersonaNatural createPersonaNatural() {
        return new PersonaNatural();
    }

    /**
     * Create an instance of {@link Email }
     * 
     */
    public Email createEmail() {
        return new Email();
    }

    /**
     * Create an instance of {@link Telefono }
     * 
     */
    public Telefono createTelefono() {
        return new Telefono();
    }

    /**
     * Create an instance of {@link Direccion }
     * 
     */
    public Direccion createDireccion() {
        return new Direccion();
    }

    /**
     * Create an instance of {@link NotificacionPersona }
     * 
     */
    public NotificacionPersona createNotificacionPersona() {
        return new NotificacionPersona();
    }

    /**
     * Create an instance of {@link PersonaJuridica }
     * 
     */
    public PersonaJuridica createPersonaJuridica() {
        return new PersonaJuridica();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorRespuesta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicioreceptorpersonaduups.sdm.com/", name = "errorRespuesta")
    public JAXBElement<ErrorRespuesta> createErrorRespuesta(ErrorRespuesta value) {
        return new JAXBElement<ErrorRespuesta>(_ErrorRespuesta_QNAME, ErrorRespuesta.class, null, value);
    }

}
