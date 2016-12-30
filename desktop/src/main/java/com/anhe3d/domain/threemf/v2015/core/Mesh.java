
package com.anhe3d.domain.threemf.v2015.core;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for Mesh complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Mesh"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}vertices"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}triangles"/&gt;
 *         &lt;any processContents='lax' maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Mesh", propOrder = {
    "vertices",
    "triangles",
    "anies"
})
@XmlRootElement(name = "mesh")
public class Mesh {

    @XmlElement(required = true)
    protected Vertices vertices;
    @XmlElement(required = true)
    protected Triangles triangles;
    @XmlAnyElement
    protected List<Element> anies;

    /**
     * Gets the value of the vertices property.
     * 
     * @return
     *     possible object is
     *     {@link Vertices }
     *     
     */
    public Vertices getVertices() {
        return vertices;
    }

    /**
     * Sets the value of the vertices property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vertices }
     *     
     */
    public void setVertices(Vertices value) {
        this.vertices = value;
    }

    /**
     * Gets the value of the triangles property.
     * 
     * @return
     *     possible object is
     *     {@link Triangles }
     *     
     */
    public Triangles getTriangles() {
        return triangles;
    }

    /**
     * Sets the value of the triangles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Triangles }
     *     
     */
    public void setTriangles(Triangles value) {
        this.triangles = value;
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

}
