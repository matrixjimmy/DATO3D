
package com.anhe3d.domain.threemf.v2015.core;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * <p>Java class for Item complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Item"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="objectid" use="required" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceID" /&gt;
 *       &lt;attribute name="transform" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}Matrix3D" /&gt;
 *       &lt;attribute name="itemref" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;anyAttribute processContents='lax'/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Item")
@XmlRootElement(name = "item")
public class Item {

    @XmlAttribute(name = "objectid", required = true)
    protected int objectid;
    @XmlAttribute(name = "transform")
    protected String transform;
    @XmlAttribute(name = "itemref")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String itemref;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

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

    /**
     * Gets the value of the itemref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemref() {
        return itemref;
    }

    /**
     * Sets the value of the itemref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemref(String value) {
        this.itemref = value;
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
