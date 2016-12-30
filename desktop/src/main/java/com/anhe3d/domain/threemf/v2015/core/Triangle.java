
package com.anhe3d.domain.threemf.v2015.core;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for Triangle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Triangle"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="v1" use="required" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceIndex" /&gt;
 *       &lt;attribute name="v2" use="required" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceIndex" /&gt;
 *       &lt;attribute name="v3" use="required" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceIndex" /&gt;
 *       &lt;attribute name="p1" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceIndex" /&gt;
 *       &lt;attribute name="p2" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceIndex" /&gt;
 *       &lt;attribute name="p3" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceIndex" /&gt;
 *       &lt;attribute name="pid" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceID" /&gt;
 *       &lt;anyAttribute processContents='lax'/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Triangle")
@XmlRootElement(name = "triangle")
public class Triangle {

    @XmlAttribute(name = "v1", required = true)
    protected int v1;
    @XmlAttribute(name = "v2", required = true)
    protected int v2;
    @XmlAttribute(name = "v3", required = true)
    protected int v3;
    @XmlAttribute(name = "p1")
    protected Integer p1;
    @XmlAttribute(name = "p2")
    protected Integer p2;
    @XmlAttribute(name = "p3")
    protected Integer p3;
    @XmlAttribute(name = "pid")
    protected Integer pid;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the v1 property.
     * 
     */
    public int getV1() {
        return v1;
    }

    /**
     * Sets the value of the v1 property.
     * 
     */
    public void setV1(int value) {
        this.v1 = value;
    }

    /**
     * Gets the value of the v2 property.
     * 
     */
    public int getV2() {
        return v2;
    }

    /**
     * Sets the value of the v2 property.
     * 
     */
    public void setV2(int value) {
        this.v2 = value;
    }

    /**
     * Gets the value of the v3 property.
     * 
     */
    public int getV3() {
        return v3;
    }

    /**
     * Sets the value of the v3 property.
     * 
     */
    public void setV3(int value) {
        this.v3 = value;
    }

    /**
     * Gets the value of the p1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getP1() {
        return p1;
    }

    /**
     * Sets the value of the p1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setP1(Integer value) {
        this.p1 = value;
    }

    /**
     * Gets the value of the p2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getP2() {
        return p2;
    }

    /**
     * Sets the value of the p2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setP2(Integer value) {
        this.p2 = value;
    }

    /**
     * Gets the value of the p3 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getP3() {
        return p3;
    }

    /**
     * Sets the value of the p3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setP3(Integer value) {
        this.p3 = value;
    }

    /**
     * Gets the value of the pid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * Sets the value of the pid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPid(Integer value) {
        this.pid = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
