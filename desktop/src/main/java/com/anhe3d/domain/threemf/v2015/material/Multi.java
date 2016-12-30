
package com.anhe3d.domain.threemf.v2015.material;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Multi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Multi"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="pindices" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Multi")
@XmlRootElement(name = "multi")
public class Multi {

    @XmlAttribute(name = "pindices", required = true)
    protected String pindices;

    /**
     * Gets the value of the pindices property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPindices() {
        return pindices;
    }

    /**
     * Sets the value of the pindices property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPindices(String value) {
        this.pindices = value;
    }

}
