
package co.gov.movilidadbogota.servicioreceptorpersonaduups;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonaNatural complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonaNatural">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="o_primerNombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_segundoNombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_primerApellido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_segundoApellido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaNatural", propOrder = {
    "oPrimerNombre",
    "oSegundoNombre",
    "oPrimerApellido",
    "oSegundoApellido"
})
public class PersonaNatural {

    @XmlElement(name = "o_primerNombre")
    protected String oPrimerNombre;
    @XmlElement(name = "o_segundoNombre")
    protected String oSegundoNombre;
    @XmlElement(name = "o_primerApellido")
    protected String oPrimerApellido;
    @XmlElement(name = "o_segundoApellido")
    protected String oSegundoApellido;

    /**
     * Gets the value of the oPrimerNombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOPrimerNombre() {
        return oPrimerNombre;
    }

    /**
     * Sets the value of the oPrimerNombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOPrimerNombre(String value) {
        this.oPrimerNombre = value;
    }

    /**
     * Gets the value of the oSegundoNombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOSegundoNombre() {
        return oSegundoNombre;
    }

    /**
     * Sets the value of the oSegundoNombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOSegundoNombre(String value) {
        this.oSegundoNombre = value;
    }

    /**
     * Gets the value of the oPrimerApellido property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOPrimerApellido() {
        return oPrimerApellido;
    }

    /**
     * Sets the value of the oPrimerApellido property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOPrimerApellido(String value) {
        this.oPrimerApellido = value;
    }

    /**
     * Gets the value of the oSegundoApellido property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOSegundoApellido() {
        return oSegundoApellido;
    }

    /**
     * Sets the value of the oSegundoApellido property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOSegundoApellido(String value) {
        this.oSegundoApellido = value;
    }

}
