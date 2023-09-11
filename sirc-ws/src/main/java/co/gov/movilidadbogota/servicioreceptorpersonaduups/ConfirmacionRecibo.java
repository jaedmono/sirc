
package co.gov.movilidadbogota.servicioreceptorpersonaduups;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfirmacionRecibo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfirmacionRecibo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="o_codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="o_detalle" type="{http://servicioreceptorpersonaduups.sdm.com/}errorRespuesta" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfirmacionRecibo", propOrder = {
    "oCodigo",
    "oDetalle"
})
public class ConfirmacionRecibo {

    @XmlElement(name = "o_codigo")
    protected int oCodigo;
    @XmlElement(name = "o_detalle")
    protected List<ErrorRespuesta> oDetalle;

    /**
     * Gets the value of the oCodigo property.
     * 
     */
    public int getOCodigo() {
        return oCodigo;
    }

    /**
     * Sets the value of the oCodigo property.
     * 
     */
    public void setOCodigo(int value) {
        this.oCodigo = value;
    }

    /**
     * Gets the value of the oDetalle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oDetalle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getODetalle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorRespuesta }
     * 
     * 
     */
    public List<ErrorRespuesta> getODetalle() {
        if (oDetalle == null) {
            oDetalle = new ArrayList<ErrorRespuesta>();
        }
        return this.oDetalle;
    }

}
