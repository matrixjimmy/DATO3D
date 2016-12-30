
package com.anhe3d.domain.threemf.v2013;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Component complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Component"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="objectid" use="required" type="{http://schemas.microsoft.com/3dmanufacturing/2013/01}ResourceID" /&gt;
 *       &lt;attribute name="transform" type="{http://schemas.microsoft.com/3dmanufacturing/2013/01}Matrix3D" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Component")
@XmlRootElement(name = "component")
public class Component {

    @XmlAttribute(name = "objectid", required = true)
    protected int objectid;
    @XmlAttribute(name = "transform")
    protected String transform;

    /**
     * Gets the value of the objectid property.
     * 
     */
    public int getObjectid() {
        return objectid;
    }

    /**
     * Sets the value of the objectid property.
     * 
     */
    public void setObjectid(int value) {
        this.objectid = value;
    }

    /**
     * Gets the value of the transform property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransform() {
        return transform;
    }

    /**
     * Sets the value of the transform property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransform(String value) {
        this.transform = value;
    }

}
