
package com.anhe3d.domain.threemf.v2013;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Unit.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Unit"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="micron"/&gt;
 *     &lt;enumeration value="millimeter"/&gt;
 *     &lt;enumeration value="centimeter"/&gt;
 *     &lt;enumeration value="inch"/&gt;
 *     &lt;enumeration value="foot"/&gt;
 *     &lt;enumeration value="meter"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Unit")
@XmlEnum
public enum Unit {

    @XmlEnumValue("micron")
    MICRON("micron"),
    @XmlEnumValue("millimeter")
    MILLIMETER("millimeter"),
    @XmlEnumValue("centimeter")
    CENTIMETER("centimeter"),
    @XmlEnumValue("inch")
    INCH("inch"),
    @XmlEnumValue("foot")
    FOOT("foot"),
    @XmlEnumValue("meter")
    METER("meter");
    private final String value;

    Unit(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Unit fromValue(String v) {
        for (Unit c: Unit.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
