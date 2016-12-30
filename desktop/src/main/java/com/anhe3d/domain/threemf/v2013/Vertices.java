
package com.anhe3d.domain.threemf.v2013;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Vertices complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Vertices"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/2013/01}vertex" maxOccurs="unbounded" minOccurs="3"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vertices", propOrder = {
    "vertices"
})
@XmlRootElement(name = "vertices")
public class Vertices {

    @XmlElement(name = "vertex", required = true)
    protected List<Vertex> vertices;

    /**
     * Gets the value of the vertices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vertices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVertices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Vertex }
     * 
     * 
     */
    public List<Vertex> getVertices() {
        if (vertices == null) {
            vertices = new ArrayList<Vertex>();
        }
        return this.vertices;
    }

}
