
package co.gov.movilidadbogota.web.servicioreceptorpersonaduups;

import co.gov.movilidadbogota.ws.duups.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Direccion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Direccion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="o_direccion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="o_departamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="o_municipioCiudad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="o_zona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_localidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_tipoVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_nombreVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_numeroNomenclaturaPrincipal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="o_letraNomenclaturaPrincipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_subFijoNomenclaturaPrincipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_cuadrantePrincipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_numeroNomenclaturaGeneradora" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="o_letraNomenclaturaGeneradora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_subFijoNomenclaturaGeneradora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_numeroPlaca" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="o_cuadranteGeneradora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_tipoPredio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_NumeroTipoPredio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_Predio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_NumeroPredio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_adicional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="o_estado" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Direccion", propOrder = {
    "oDireccion",
    "oDepartamento",
    "oMunicipioCiudad",
    "oZona",
    "oLocalidad",
    "oTipoVia",
    "oNombreVia",
    "oNumeroNomenclaturaPrincipal",
    "oLetraNomenclaturaPrincipal",
    "oSubFijoNomenclaturaPrincipal",
    "oCuadrantePrincipal",
    "oNumeroNomenclaturaGeneradora",
    "oLetraNomenclaturaGeneradora",
    "oSubFijoNomenclaturaGeneradora",
    "oNumeroPlaca",
    "oCuadranteGeneradora",
    "oTipoPredio",
    "oNumeroTipoPredio",
    "oPredio",
    "oNumeroPredio",
    "oAdicional",
    "oEstado"
})
public class Direccion {

    @XmlElement(name = "o_direccion", required = true)
    protected String oDireccion;
    @XmlElement(name = "o_departamento", required = true)
    protected String oDepartamento;
    @XmlElement(name = "o_municipioCiudad", required = true)
    protected String oMunicipioCiudad;
    @XmlElement(name = "o_zona")
    protected String oZona;
    @XmlElement(name = "o_localidad")
    protected String oLocalidad;
    @XmlElement(name = "o_tipoVia")
    protected String oTipoVia;
    @XmlElement(name = "o_nombreVia")
    protected String oNombreVia;
    @XmlElement(name = "o_numeroNomenclaturaPrincipal")
    protected int oNumeroNomenclaturaPrincipal;
    @XmlElement(name = "o_letraNomenclaturaPrincipal")
    protected String oLetraNomenclaturaPrincipal;
    @XmlElement(name = "o_subFijoNomenclaturaPrincipal")
    protected String oSubFijoNomenclaturaPrincipal;
    @XmlElement(name = "o_cuadrantePrincipal")
    protected String oCuadrantePrincipal;
    @XmlElement(name = "o_numeroNomenclaturaGeneradora")
    protected int oNumeroNomenclaturaGeneradora;
    @XmlElement(name = "o_letraNomenclaturaGeneradora")
    protected String oLetraNomenclaturaGeneradora;
    @XmlElement(name = "o_subFijoNomenclaturaGeneradora")
    protected String oSubFijoNomenclaturaGeneradora;
    @XmlElement(name = "o_numeroPlaca")
    protected int oNumeroPlaca;
    @XmlElement(name = "o_cuadranteGeneradora")
    protected String oCuadranteGeneradora;
    @XmlElement(name = "o_tipoPredio")
    protected String oTipoPredio;
    @XmlElement(name = "o_NumeroTipoPredio")
    protected String oNumeroTipoPredio;
    @XmlElement(name = "o_Predio")
    protected String oPredio;
    @XmlElement(name = "o_NumeroPredio")
    protected String oNumeroPredio;
    @XmlElement(name = "o_adicional")
    protected String oAdicional;
    @XmlElement(name = "o_estado")
    protected int oEstado;

    /**
     * Gets the value of the oDireccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getODireccion() {
        return oDireccion;
    }

    /**
     * Sets the value of the oDireccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setODireccion(String value) {
        this.oDireccion = value;
    }

    /**
     * Gets the value of the oDepartamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getODepartamento() {
        return oDepartamento;
    }

    /**
     * Sets the value of the oDepartamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setODepartamento(String value) {
        this.oDepartamento = value;
    }

    /**
     * Gets the value of the oMunicipioCiudad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOMunicipioCiudad() {
        return oMunicipioCiudad;
    }

    /**
     * Sets the value of the oMunicipioCiudad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOMunicipioCiudad(String value) {
        this.oMunicipioCiudad = value;
    }

    /**
     * Gets the value of the oZona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOZona() {
        return oZona;
    }

    /**
     * Sets the value of the oZona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOZona(String value) {
        this.oZona = value;
    }

    /**
     * Gets the value of the oLocalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOLocalidad() {
        return oLocalidad;
    }

    /**
     * Sets the value of the oLocalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOLocalidad(String value) {
        this.oLocalidad = value;
    }

    /**
     * Gets the value of the oTipoVia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTipoVia() {
        return oTipoVia;
    }

    /**
     * Sets the value of the oTipoVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTipoVia(String value) {
        this.oTipoVia = value;
    }

    /**
     * Gets the value of the oNombreVia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getONombreVia() {
        return oNombreVia;
    }

    /**
     * Sets the value of the oNombreVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setONombreVia(String value) {
        this.oNombreVia = value;
    }

    /**
     * Gets the value of the oNumeroNomenclaturaPrincipal property.
     * 
     */
    public int getONumeroNomenclaturaPrincipal() {
        return oNumeroNomenclaturaPrincipal;
    }

    /**
     * Sets the value of the oNumeroNomenclaturaPrincipal property.
     * 
     */
    public void setONumeroNomenclaturaPrincipal(int value) {
        this.oNumeroNomenclaturaPrincipal = value;
    }

    /**
     * Gets the value of the oLetraNomenclaturaPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOLetraNomenclaturaPrincipal() {
        return oLetraNomenclaturaPrincipal;
    }

    /**
     * Sets the value of the oLetraNomenclaturaPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOLetraNomenclaturaPrincipal(String value) {
        this.oLetraNomenclaturaPrincipal = value;
    }

    /**
     * Gets the value of the oSubFijoNomenclaturaPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOSubFijoNomenclaturaPrincipal() {
        return oSubFijoNomenclaturaPrincipal;
    }

    /**
     * Sets the value of the oSubFijoNomenclaturaPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOSubFijoNomenclaturaPrincipal(String value) {
        this.oSubFijoNomenclaturaPrincipal = value;
    }

    /**
     * Gets the value of the oCuadrantePrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOCuadrantePrincipal() {
        return oCuadrantePrincipal;
    }

    /**
     * Sets the value of the oCuadrantePrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOCuadrantePrincipal(String value) {
        this.oCuadrantePrincipal = value;
    }

    /**
     * Gets the value of the oNumeroNomenclaturaGeneradora property.
     * 
     */
    public int getONumeroNomenclaturaGeneradora() {
        return oNumeroNomenclaturaGeneradora;
    }

    /**
     * Sets the value of the oNumeroNomenclaturaGeneradora property.
     * 
     */
    public void setONumeroNomenclaturaGeneradora(int value) {
        this.oNumeroNomenclaturaGeneradora = value;
    }

    /**
     * Gets the value of the oLetraNomenclaturaGeneradora property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOLetraNomenclaturaGeneradora() {
        return oLetraNomenclaturaGeneradora;
    }

    /**
     * Sets the value of the oLetraNomenclaturaGeneradora property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOLetraNomenclaturaGeneradora(String value) {
        this.oLetraNomenclaturaGeneradora = value;
    }

    /**
     * Gets the value of the oSubFijoNomenclaturaGeneradora property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOSubFijoNomenclaturaGeneradora() {
        return oSubFijoNomenclaturaGeneradora;
    }

    /**
     * Sets the value of the oSubFijoNomenclaturaGeneradora property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOSubFijoNomenclaturaGeneradora(String value) {
        this.oSubFijoNomenclaturaGeneradora = value;
    }

    /**
     * Gets the value of the oNumeroPlaca property.
     * 
     */
    public int getONumeroPlaca() {
        return oNumeroPlaca;
    }

    /**
     * Sets the value of the oNumeroPlaca property.
     * 
     */
    public void setONumeroPlaca(int value) {
        this.oNumeroPlaca = value;
    }

    /**
     * Gets the value of the oCuadranteGeneradora property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOCuadranteGeneradora() {
        return oCuadranteGeneradora;
    }

    /**
     * Sets the value of the oCuadranteGeneradora property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOCuadranteGeneradora(String value) {
        this.oCuadranteGeneradora = value;
    }

    /**
     * Gets the value of the oTipoPredio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOTipoPredio() {
        return oTipoPredio;
    }

    /**
     * Sets the value of the oTipoPredio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTipoPredio(String value) {
        this.oTipoPredio = value;
    }

    /**
     * Gets the value of the oNumeroTipoPredio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getONumeroTipoPredio() {
        return oNumeroTipoPredio;
    }

    /**
     * Sets the value of the oNumeroTipoPredio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setONumeroTipoPredio(String value) {
        this.oNumeroTipoPredio = value;
    }

    /**
     * Gets the value of the oPredio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOPredio() {
        return oPredio;
    }

    /**
     * Sets the value of the oPredio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOPredio(String value) {
        this.oPredio = value;
    }

    /**
     * Gets the value of the oNumeroPredio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getONumeroPredio() {
        return oNumeroPredio;
    }

    /**
     * Sets the value of the oNumeroPredio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setONumeroPredio(String value) {
        this.oNumeroPredio = value;
    }

    /**
     * Gets the value of the oAdicional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOAdicional() {
        return oAdicional;
    }

    /**
     * Sets the value of the oAdicional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOAdicional(String value) {
        this.oAdicional = value;
    }

    /**
     * Gets the value of the oEstado property.
     * 
     */
    public int getOEstado() {
        return oEstado;
    }

    /**
     * Sets the value of the oEstado property.
     * 
     */
    public void setOEstado(int value) {
        this.oEstado = value;
    }

}
