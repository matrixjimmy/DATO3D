
package com.anhe3d.domain.threemf.v2015.material;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.anhe3d.domain.threemf.v2015.material package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.anhe3d.domain.threemf.v2015.material
     * 
     */
    public ObjectFactory() {
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
     * Create an instance of {@link Colorgroup }
     * 
     */
    public Colorgroup createColorgroup() {
        return new Colorgroup();
    }

    /**
     * Create an instance of {@link Texture2Dgroup }
     * 
     */
    public Texture2Dgroup createTexture2Dgroup() {
        return new Texture2Dgroup();
    }

    /**
     * Create an instance of {@link Tex2Coord }
     * 
     */
    public Tex2Coord createTex2Coord() {
        return new Tex2Coord();
    }

    /**
     * Create an instance of {@link Compositematerials }
     * 
     */
    public Compositematerials createCompositematerials() {
        return new Compositematerials();
    }

    /**
     * Create an instance of {@link Composite }
     * 
     */
    public Composite createComposite() {
        return new Composite();
    }

    /**
     * Create an instance of {@link Multiproperties }
     * 
     */
    public Multiproperties createMultiproperties() {
        return new Multiproperties();
    }

    /**
     * Create an instance of {@link Multi }
     * 
     */
    public Multi createMulti() {
        return new Multi();
    }

}
