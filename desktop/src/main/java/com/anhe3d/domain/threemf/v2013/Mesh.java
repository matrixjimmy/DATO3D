
package com.anhe3d.domain.threemf.v2013;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/2013/01}vertices"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/2013/01}triangles"/&gt;
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
    "triangles"
})
@XmlRootElement(name = "mesh")
public class Mesh {

    @XmlElement(required = true)
    protected Vertices vertices;
    @XmlElement(required = true)
    protected Triangles triangles;

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

}
