
package co.gov.movilidadbogota.web.servicioreceptorpersonaduups;

import co.gov.movilidadbogota.ws.duups.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonaJuridica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonaJuridica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="o_razonSocial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_nombreRepresentante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_nombreApoderado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaJuridica", propOrder = {
    "oRazonSocial",
    "oNombreRepresentante",
    "oNombreApoderado"
})
public class PersonaJuridica {

    @XmlElement(name = "o_razonSocial")
    protected String oRazonSocial;
    @XmlElement(name = "o_nombreRepresentante")
    protected String oNombreRepresentante;
    @XmlElement(name = "o_nombreApoderado")
    protected String oNombreApoderado;

    /**
     * Gets the value of the oRazonSocial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORazonSocial() {
        return oRazonSocial;
    }

    /**
     * Sets the value of the oRazonSocial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORazonSocial(String value) {
        this.oRazonSocial = value;
    }

    /**
     * Gets the value of the oNombreRepresentante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getONombreRepresentante() {
        return oNombreRepresentante;
    }

    /**
     * Sets the value of the oNombreRepresentante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setONombreRepresentante(String value) {
        this.oNombreRepresentante = value;
    }

    /**
     * Gets the value of the oNombreApoderado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getONombreApoderado() {
        return oNombreApoderado;
    }

    /**
     * Sets the value of the oNombreApoderado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setONombreApoderado(String value) {
        this.oNombreApoderado = value;
    }

}
