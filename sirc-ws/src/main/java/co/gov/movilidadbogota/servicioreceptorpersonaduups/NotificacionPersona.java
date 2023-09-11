
package co.gov.movilidadbogota.servicioreceptorpersonaduups;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NotificacionPersona complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificacionPersona">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="o_tipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="o_numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="o_direccion" type="{http://servicioreceptorpersonaduups.sdm.com/}Direccion" maxOccurs="unbounded"/>
 *         &lt;element name="o_email" type="{http://servicioreceptorpersonaduups.sdm.com/}Email" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="o_telefono" type="{http://servicioreceptorpersonaduups.sdm.com/}Telefono" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="o_personaNatural" type="{http://servicioreceptorpersonaduups.sdm.com/}PersonaNatural" minOccurs="0"/>
 *         &lt;element name="o_personaJuridica" type="{http://servicioreceptorpersonaduups.sdm.com/}PersonaJuridica" minOccurs="0"/>
 *         &lt;element name="o_comentario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_autorizacionContactoEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_direccionArchivoAutorizacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="o_origen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificacionPersona", propOrder = {
    "oTipoDocumento",
    "oNumeroDocumento",
    "oDireccion",
    "oEmail",
    "oTelefono",
    "oPersonaNatural",
    "oPersonaJuridica",
    "oComentario",
    "oAutorizacionContactoEmail",
    "oDireccionArchivoAutorizacion",
    "oOrigen"
})
public class NotificacionPersona {

    @XmlElement(name = "o_tipoDocumento", required = true)
    protected String oTipoDocumento;
    @XmlElement(name = "o_numeroDocumento", required = true)
    protected String oNumeroDocumento;
    @XmlElement(name = "o_direccion", required = true)
    protected List<Direccion> oDireccion;
    @XmlElement(name = "o_email")
    protected List<Email> oEmail;
    @XmlElement(name = "o_telefono")
    protected List<Telefono> oTelefono;
    @XmlElement(name = "o_personaNatural")
    protected PersonaNatural oPersonaNatural;
    @XmlElement(name = "o_personaJuridica")
    protected PersonaJuridica oPersonaJuridica;
    @XmlElement(name = "o_comentario")
    protected String oComentario;
    @XmlElement(name = "o_autorizacionContactoEmail")
    protected String oAutorizacionContactoEmail;
    @XmlElement(name = "o_direccionArchivoAutorizacion", required = true)
    protected String oDireccionArchivoAutorizacion;
    @XmlElement(name = "o_origen")
    protected String oOrigen;

    /**
     * Gets the value of the oTipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTipoDocumento() {
        return oTipoDocumento;
    }

    /**
     * Sets the value of the oTipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTipoDocumento(String value) {
        this.oTipoDocumento = value;
    }

    /**
     * Gets the value of the oNumeroDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getONumeroDocumento() {
        return oNumeroDocumento;
    }

    /**
     * Sets the value of the oNumeroDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setONumeroDocumento(String value) {
        this.oNumeroDocumento = value;
    }

    /**
     * Gets the value of the oDireccion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oDireccion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getODireccion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Direccion }
     * 
     * 
     */
    public List<Direccion> getODireccion() {
        if (oDireccion == null) {
            oDireccion = new ArrayList<Direccion>();
        }
        return this.oDireccion;
    }

    /**
     * Gets the value of the oEmail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oEmail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOEmail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Email }
     * 
     * 
     */
    public List<Email> getOEmail() {
        if (oEmail == null) {
            oEmail = new ArrayList<Email>();
        }
        return this.oEmail;
    }

    /**
     * Gets the value of the oTelefono property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oTelefono property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOTelefono().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Telefono }
     * 
     * 
     */
    public List<Telefono> getOTelefono() {
        if (oTelefono == null) {
            oTelefono = new ArrayList<Telefono>();
        }
        return this.oTelefono;
    }

    /**
     * Gets the value of the oPersonaNatural property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaNatural }
     *     
     */
    public PersonaNatural getOPersonaNatural() {
        return oPersonaNatural;
    }

    /**
     * Sets the value of the oPersonaNatural property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaNatural }
     *     
     */
    public void setOPersonaNatural(PersonaNatural value) {
        this.oPersonaNatural = value;
    }

    /**
     * Gets the value of the oPersonaJuridica property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaJuridica }
     *     
     */
    public PersonaJuridica getOPersonaJuridica() {
        return oPersonaJuridica;
    }

    /**
     * Sets the value of the oPersonaJuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaJuridica }
     *     
     */
    public void setOPersonaJuridica(PersonaJuridica value) {
        this.oPersonaJuridica = value;
    }

    /**
     * Gets the value of the oComentario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOComentario() {
        return oComentario;
    }

    /**
     * Sets the value of the oComentario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOComentario(String value) {
        this.oComentario = value;
    }

    /**
     * Gets the value of the oAutorizacionContactoEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOAutorizacionContactoEmail() {
        return oAutorizacionContactoEmail;
    }

    /**
     * Sets the value of the oAutorizacionContactoEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOAutorizacionContactoEmail(String value) {
        this.oAutorizacionContactoEmail = value;
    }

    /**
     * Gets the value of the oDireccionArchivoAutorizacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getODireccionArchivoAutorizacion() {
        return oDireccionArchivoAutorizacion;
    }

    /**
     * Sets the value of the oDireccionArchivoAutorizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setODireccionArchivoAutorizacion(String value) {
        this.oDireccionArchivoAutorizacion = value;
    }

    /**
     * Gets the value of the oOrigen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOOrigen() {
        return oOrigen;
    }

    /**
     * Sets the value of the oOrigen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOOrigen(String value) {
        this.oOrigen = value;
    }

}
