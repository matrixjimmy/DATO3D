
package com.anhe3d.domain.threemf.v2015.material;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CompositeMaterials complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompositeMaterials"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/material/2015/02}composite" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceID" /&gt;
 *       &lt;attribute name="matid" use="required" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceID" /&gt;
 *       &lt;attribute name="matindices" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompositeMaterials", propOrder = {
    "composites"
})
@XmlRootElement(name = "compositematerials")
public class Compositematerials {

    @XmlElement(name = "composite")
    protected List<Composite> composites;
    @XmlAttribute(name = "id", required = true)
    protected int id;
    @XmlAttribute(name = "matid", required = true)
    protected int matid;
    @XmlAttribute(name = "matindices", required = true)
    protected String matindices;

    /**
     * Gets the value of the composites property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the composites property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComposites().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Composite }
     * 
     * 
     */
    public List<Composite> getComposites() {
        if (composites == null) {
            composites = new ArrayList<Composite>();
        }
        return this.composites;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the matid property.
     * 
     */
    public int getMatid() {
        return matid;
    }

    /**
     * Sets the value of the matid property.
     * 
     */
    public void setMatid(int value) {
        this.matid = value;
    }

    /**
     * Gets the value of the matindices property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatindices() {
        return matindices;
    }

    /**
     * Sets the value of the matindices property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatindices(String value) {
        this.matindices = value;
    }

}
