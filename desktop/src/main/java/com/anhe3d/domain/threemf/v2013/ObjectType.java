
package com.anhe3d.domain.threemf.v2013;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjectType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ObjectType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="model"/&gt;
 *     &lt;enumeration value="support"/&gt;
 *     &lt;enumeration value="other"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ObjectType")
@XmlEnum
public enum ObjectType {

    @XmlEnumValue("model")
    MODEL("model"),
    @XmlEnumValue("support")
    SUPPORT("support"),
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    ObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ObjectType fromValue(String v) {
        for (ObjectType c: ObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
