
package com.anhe3d.domain.threemf.v2013;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Object complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Object"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/2013/01}mesh"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/2013/01}components"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="id" use="required" type="{http://schemas.microsoft.com/3dmanufacturing/2013/01}ResourceID" /&gt;
 *       &lt;attribute name="type" type="{http://schemas.microsoft.com/3dmanufacturing/2013/01}ObjectType" default="model" /&gt;
 *       &lt;attribute name="matid" type="{http://schemas.microsoft.com/3dmanufacturing/2013/01}ResourceID" /&gt;
 *       &lt;attribute name="materialid" type="{http://schemas.microsoft.com/3dmanufacturing/2013/01}ResourceID" /&gt;
 *       &lt;attribute name="matindex" type="{http://schemas.microsoft.com/3dmanufacturing/2013/01}ResourceIndex" /&gt;
 *       &lt;attribute name="thumbnail" type="{http://schemas.microsoft.com/3dmanufacturing/2013/01}UriReference" /&gt;
 *       &lt;attribute name="partnumber" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Object", propOrder = {
    "components",
    "mesh"
})
@XmlRootElement(name = "object")
public class Object {

    protected Components components;
    protected Mesh mesh;
    @XmlAttribute(name = "id", required = true)
    protected int id;
    @XmlAttribute(name = "type")
    protected ObjectType type;
    @XmlAttribute(name = "matid")
    protected Integer matid;
    @XmlAttribute(name = "materialid")
    protected Integer materialid;
    @XmlAttribute(name = "matindex")
    protected Integer matindex;
    @XmlAttribute(name = "thumbnail")
    protected String thumbnail;
    @XmlAttribute(name = "partnumber")
    protected String partnumber;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the components property.
     * 
     * @return
     *     possible object is
     *     {@link Components }
     *     
     */
    public Components getComponents() {
        return components;
    }

    /**
     * Sets the value of the components property.
     * 
     * @param value
     *     allowed object is
     *     {@link Components }
     *     
     */
    public void setComponents(Components value) {
        this.components = value;
    }

    /**
     * Gets the value of the mesh property.
     * 
     * @return
     *     possible object is
     *     {@link Mesh }
     *     
     */
    public Mesh getMesh() {
        return mesh;
    }

    /**
     * Sets the value of the mesh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mesh }
     *     
     */
    public void setMesh(Mesh value) {
        this.mesh = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectType }
     *     
     */
    public ObjectType getType() {
        if (type == null) {
            return ObjectType.MODEL;
        } else {
            return type;
        }
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectType }
     *     
     */
    public void setType(ObjectType value) {
        this.type = value;
    }

    /**
     * Gets the value of the matid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMatid() {
        return matid;
    }

    /**
     * Sets the value of the matid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMatid(Integer value) {
        this.matid = value;
    }

    /**
     * Gets the value of the materialid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaterialid() {
        return materialid;
    }

    /**
     * Sets the value of the materialid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaterialid(Integer value) {
        this.materialid = value;
    }

    /**
     * Gets the value of the matindex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMatindex() {
        return matindex;
    }

    /**
     * Sets the value of the matindex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMatindex(Integer value) {
        this.matindex = value;
    }

    /**
     * Gets the value of the thumbnail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets the value of the thumbnail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumbnail(String value) {
        this.thumbnail = value;
    }

    /**
     * Gets the value of the partnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnumber() {
        return partnumber;
    }

    /**
     * Sets the value of the partnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnumber(String value) {
        this.partnumber = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
