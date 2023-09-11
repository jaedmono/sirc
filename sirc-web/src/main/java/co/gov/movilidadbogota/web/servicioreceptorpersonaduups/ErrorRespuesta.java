
package co.gov.movilidadbogota.web.servicioreceptorpersonaduups;

import co.gov.movilidadbogota.ws.duups.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for errorRespuesta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="errorRespuesta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="o_CodigoError" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="o_MensajeError" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "errorRespuesta", propOrder = {
    "oCodigoError",
    "oMensajeError"
})
public class ErrorRespuesta {

    @XmlElement(name = "o_CodigoError", required = true)
    protected String oCodigoError;
    @XmlElement(name = "o_MensajeError", required = true)
    protected String oMensajeError;

    /**
     * Gets the value of the oCodigoError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOCodigoError() {
        return oCodigoError;
    }

    /**
     * Sets the value of the oCodigoError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOCodigoError(String value) {
        this.oCodigoError = value;
    }

    /**
     * Gets the value of the oMensajeError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOMensajeError() {
        return oMensajeError;
    }

    /**
     * Sets the value of the oMensajeError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOMensajeError(String value) {
        this.oMensajeError = value;
    }

}
