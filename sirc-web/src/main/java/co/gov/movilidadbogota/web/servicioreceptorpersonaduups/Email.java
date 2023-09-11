
package co.gov.movilidadbogota.web.servicioreceptorpersonaduups;

import co.gov.movilidadbogota.ws.duups.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Email complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Email">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="o_correoElectronico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_comentariosEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Email", propOrder = {
    "oCorreoElectronico",
    "oComentariosEmail"
})
public class Email {

    @XmlElement(name = "o_correoElectronico")
    protected String oCorreoElectronico;
    @XmlElement(name = "o_comentariosEmail")
    protected String oComentariosEmail;

    /**
     * Gets the value of the oCorreoElectronico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOCorreoElectronico() {
        return oCorreoElectronico;
    }

    /**
     * Sets the value of the oCorreoElectronico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOCorreoElectronico(String value) {
        this.oCorreoElectronico = value;
    }

    /**
     * Gets the value of the oComentariosEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOComentariosEmail() {
        return oComentariosEmail;
    }

    /**
     * Sets the value of the oComentariosEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOComentariosEmail(String value) {
        this.oComentariosEmail = value;
    }

}
