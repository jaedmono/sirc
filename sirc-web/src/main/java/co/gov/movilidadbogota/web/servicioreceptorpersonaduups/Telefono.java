
package co.gov.movilidadbogota.web.servicioreceptorpersonaduups;

import co.gov.movilidadbogota.ws.duups.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Telefono complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Telefono">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="o_telefono" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="o_tipoTelefono" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="o_comentarios" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Telefono", propOrder = {
    "oTelefono",
    "oTipoTelefono",
    "oComentarios"
})
public class Telefono {

    @XmlElement(name = "o_telefono", required = true)
    protected String oTelefono;
    @XmlElement(name = "o_tipoTelefono", required = true)
    protected String oTipoTelefono;
    @XmlElement(name = "o_comentarios")
    protected String oComentarios;

    /**
     * Gets the value of the oTelefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTelefono() {
        return oTelefono;
    }

    /**
     * Sets the value of the oTelefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTelefono(String value) {
        this.oTelefono = value;
    }

    /**
     * Gets the value of the oTipoTelefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTipoTelefono() {
        return oTipoTelefono;
    }

    /**
     * Sets the value of the oTipoTelefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTipoTelefono(String value) {
        this.oTipoTelefono = value;
    }

    /**
     * Gets the value of the oComentarios property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOComentarios() {
        return oComentarios;
    }

    /**
     * Sets the value of the oComentarios property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOComentarios(String value) {
        this.oComentarios = value;
    }

}
