
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
 * <p>Java class for Texture2DGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Texture2DGroup"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/material/2015/02}tex2coord" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceID" /&gt;
 *       &lt;attribute name="texid" use="required" type="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}ResourceID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Texture2DGroup", propOrder = {
    "tex2Coords"
})
@XmlRootElement(name = "texture2dgroup")
public class Texture2Dgroup {

    @XmlElement(name = "tex2coord")
    protected List<Tex2Coord> tex2Coords;
    @XmlAttribute(name = "id", required = true)
    protected int id;
    @XmlAttribute(name = "texid", required = true)
    protected int texid;

    /**
     * Gets the value of the tex2Coords property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tex2Coords property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTex2Coords().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Tex2Coord }
     * 
     * 
     */
    public List<Tex2Coord> getTex2Coords() {
        if (tex2Coords == null) {
            tex2Coords = new ArrayList<Tex2Coord>();
        }
        return this.tex2Coords;
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
     * Gets the value of the texid property.
     * 
     */
    public int getTexid() {
        return texid;
    }

    /**
     * Sets the value of the texid property.
     * 
     */
    public void setTexid(int value) {
        this.texid = value;
    }

}
