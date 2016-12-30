
package com.anhe3d.domain.threemf.v2015.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * <p>Java class for Model complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Model"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}metadata" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}resources"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}build"/&gt;
 *         &lt;any processContents='lax' maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="unit" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}Unit" default="millimeter" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/&gt;
 *       &lt;attribute name="requiredextensions" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;anyAttribute processContents='lax'/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Model", propOrder = {
    "metadatas",
    "resources",
    "build",
    "anies"
})
@XmlRootElement(name = "model")
public class Model {

    @XmlElement(name = "metadata")
    protected List<Metadata> metadatas;
    @XmlElement(required = true)
    protected Resources resources;
    @XmlElement(required = true)
    protected Build build;
    @XmlAnyElement
    protected List<Element> anies;
    @XmlAttribute(name = "unit")
    protected Unit unit;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;
    @XmlAttribute(name = "requiredextensions")
    protected String requiredextensions;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the metadatas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metadatas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetadatas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Metadata }
     * 
     * 
     */
    public List<Metadata> getMetadatas() {
        if (metadatas == null) {
            metadatas = new ArrayList<Metadata>();
        }
        return this.metadatas;
    }

    /**
     * Gets the value of the resources property.
     * 
     * @return
     *     possible object is
     *     {@link Resources }
     *     
     */
    public Resources getResources() {
        return resources;
    }

    /**
     * Sets the value of the resources property.
     * 
     * @param value
     *     allowed object is
     *     {@link Resources }
     *     
     */
    public void setResources(Resources value) {
        this.resources = value;
    }

    /**
     * Gets the value of the build property.
     * 
     * @return
     *     possible object is
     *     {@link Build }
     *     
     */
    public Build getBuild() {
        return build;
    }

    /**
     * Sets the value of the build property.
     * 
     * @param value
     *     allowed object is
     *     {@link Build }
     *     
     */
    public void setBuild(Build value) {
        this.build = value;
    }

    /**
     * Gets the value of the anies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * 
     * 
     */
    public List<Element> getAnies() {
        if (anies == null) {
            anies = new ArrayList<Element>();
        }
        return this.anies;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link Unit }
     *     
     */
    public Unit getUnit() {
        if (unit == null) {
            return Unit.MILLIMETER;
        } else {
            return unit;
        }
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Unit }
     *     
     */
    public void setUnit(Unit value) {
        this.unit = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the requiredextensions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiredextensions() {
        return requiredextensions;
    }

    /**
     * Sets the value of the requiredextensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiredextensions(String value) {
        this.requiredextensions = value;
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
