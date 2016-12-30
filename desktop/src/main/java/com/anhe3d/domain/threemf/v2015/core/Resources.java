
package com.anhe3d.domain.threemf.v2015.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import com.anhe3d.domain.threemf.v2015.material.Color;
import com.anhe3d.domain.threemf.v2015.material.Colorgroup;
import com.anhe3d.domain.threemf.v2015.material.Compositematerials;
import com.anhe3d.domain.threemf.v2015.material.Material;
import com.anhe3d.domain.threemf.v2015.material.Multiproperties;
import com.anhe3d.domain.threemf.v2015.material.Texture2Dgroup;


/**
 * <p>Java class for Resources complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Resources"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/material/2015/02}material" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/material/2015/02}color" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/material/2015/02}colorgroup" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/material/2015/02}texture2dgroup" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/material/2015/02}compositematerials" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/material/2015/02}multiproperties" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}basematerials" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/3dmanufacturing/core/2015/02}object" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;anyAttribute processContents='lax'/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Resources", propOrder = {
    "materials",
    "colors",
    "colorgroups",
    "texture2Dgroups",
    "compositematerials",
    "multiproperties",
    "basematerials",
    "objects"
})
@XmlRootElement(name = "resources")
public class Resources {

    @XmlElement(name = "material", namespace = "http://schemas.microsoft.com/3dmanufacturing/material/2015/02")
    protected List<Material> materials;
    @XmlElement(name = "color", namespace = "http://schemas.microsoft.com/3dmanufacturing/material/2015/02")
    protected List<Color> colors;
    @XmlElement(name = "colorgroup", namespace = "http://schemas.microsoft.com/3dmanufacturing/material/2015/02")
    protected List<Colorgroup> colorgroups;
    @XmlElement(name = "texture2dgroup", namespace = "http://schemas.microsoft.com/3dmanufacturing/material/2015/02")
    protected List<Texture2Dgroup> texture2Dgroups;
    @XmlElement(namespace = "http://schemas.microsoft.com/3dmanufacturing/material/2015/02")
    protected List<Compositematerials> compositematerials;
    @XmlElement(namespace = "http://schemas.microsoft.com/3dmanufacturing/material/2015/02")
    protected List<Multiproperties> multiproperties;
    protected List<Basematerials> basematerials;
    @XmlElement(name = "object")
    protected List<Object> objects;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the materials property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the materials property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaterials().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Material }
     * 
     * 
     */
    public List<Material> getMaterials() {
        if (materials == null) {
            materials = new ArrayList<Material>();
        }
        return this.materials;
    }

    /**
     * Gets the value of the colors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the colors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getColors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Color }
     * 
     * 
     */
    public List<Color> getColors() {
        if (colors == null) {
            colors = new ArrayList<Color>();
        }
        return this.colors;
    }

    /**
     * Gets the value of the colorgroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the colorgroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getColorgroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Colorgroup }
     * 
     * 
     */
    public List<Colorgroup> getColorgroups() {
        if (colorgroups == null) {
            colorgroups = new ArrayList<Colorgroup>();
        }
        return this.colorgroups;
    }

    /**
     * Gets the value of the texture2Dgroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the texture2Dgroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTexture2Dgroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Texture2Dgroup }
     * 
     * 
     */
    public List<Texture2Dgroup> getTexture2Dgroups() {
        if (texture2Dgroups == null) {
            texture2Dgroups = new ArrayList<Texture2Dgroup>();
        }
        return this.texture2Dgroups;
    }

    /**
     * Gets the value of the compositematerials property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compositematerials property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompositematerials().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Compositematerials }
     * 
     * 
     */
    public List<Compositematerials> getCompositematerials() {
        if (compositematerials == null) {
            compositematerials = new ArrayList<Compositematerials>();
        }
        return this.compositematerials;
    }

    /**
     * Gets the value of the multiproperties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the multiproperties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMultiproperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Multiproperties }
     * 
     * 
     */
    public List<Multiproperties> getMultiproperties() {
        if (multiproperties == null) {
            multiproperties = new ArrayList<Multiproperties>();
        }
        return this.multiproperties;
    }

    /**
     * Gets the value of the basematerials property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the basematerials property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBasematerials().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Basematerials }
     * 
     * 
     */
    public List<Basematerials> getBasematerials() {
        if (basematerials == null) {
            basematerials = new ArrayList<Basematerials>();
        }
        return this.basematerials;
    }

    /**
     * Gets the value of the objects property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objects property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjects().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getObjects() {
        if (objects == null) {
            objects = new ArrayList<Object>();
        }
        return this.objects;
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
