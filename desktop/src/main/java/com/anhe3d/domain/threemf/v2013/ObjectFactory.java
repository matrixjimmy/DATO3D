
package com.anhe3d.domain.threemf.v2013;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.anhe3d.domain.threemf.v2013 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Entry_QNAME = new QName("http://schemas.microsoft.com/3dmanufacturing/2013/01", "entry");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.anhe3d.domain.threemf.v2013
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Model }
     * 
     */
    public Model createModel() {
        return new Model();
    }

    /**
     * Create an instance of {@link Metadata }
     * 
     */
    public Metadata createMetadata() {
        return new Metadata();
    }

    /**
     * Create an instance of {@link Resources }
     * 
     */
    public Resources createResources() {
        return new Resources();
    }

    /**
     * Create an instance of {@link Material }
     * 
     */
    public Material createMaterial() {
        return new Material();
    }

    /**
     * Create an instance of {@link Color }
     * 
     */
    public Color createColor() {
        return new Color();
    }

    /**
     * Create an instance of {@link Basematerials }
     * 
     */
    public Basematerials createBasematerials() {
        return new Basematerials();
    }

    /**
     * Create an instance of {@link Base }
     * 
     */
    public Base createBase() {
        return new Base();
    }

    /**
     * Create an instance of {@link Object }
     * 
     */
    public Object createObject() {
        return new Object();
    }

    /**
     * Create an instance of {@link Components }
     * 
     */
    public Components createComponents() {
        return new Components();
    }

    /**
     * Create an instance of {@link Component }
     * 
     */
    public Component createComponent() {
        return new Component();
    }

    /**
     * Create an instance of {@link Mesh }
     * 
     */
    public Mesh createMesh() {
        return new Mesh();
    }

    /**
     * Create an instance of {@link Vertices }
     * 
     */
    public Vertices createVertices() {
        return new Vertices();
    }

    /**
     * Create an instance of {@link Vertex }
     * 
     */
    public Vertex createVertex() {
        return new Vertex();
    }

    /**
     * Create an instance of {@link Triangles }
     * 
     */
    public Triangles createTriangles() {
        return new Triangles();
    }

    /**
     * Create an instance of {@link Triangle }
     * 
     */
    public Triangle createTriangle() {
        return new Triangle();
    }

    /**
     * Create an instance of {@link Build }
     * 
     */
    public Build createBuild() {
        return new Build();
    }

    /**
     * Create an instance of {@link Item }
     * 
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/3dmanufacturing/2013/01", name = "entry")
    public JAXBElement<String> createEntry(String value) {
        return new JAXBElement<String>(_Entry_QNAME, String.class, null, value);
    }

}
