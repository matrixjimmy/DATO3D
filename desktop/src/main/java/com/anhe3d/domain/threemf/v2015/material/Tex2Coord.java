
package com.anhe3d.domain.threemf.v2015.material;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Tex2Coord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Tex2Coord"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="u" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}Number" /&gt;
 *       &lt;attribute name="v" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}Number" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tex2Coord")
@XmlRootElement(name = "tex2coord")
public class Tex2Coord {

    @XmlAttribute(name = "u")
    protected Double u;
    @XmlAttribute(name = "v")
    protected Double v;

    /**
     * Gets the value of the u property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getU() {
        return u;
    }

    /**
     * Sets the value of the u property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setU(Double value) {
        this.u = value;
    }

    /**
     * Gets the value of the v property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getV() {
        return v;
    }

    /**
     * Sets the value of the v property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setV(Double value) {
        this.v = value;
    }

}
