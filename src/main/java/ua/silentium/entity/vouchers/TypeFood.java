//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.11.09 at 03:40:04 AM EET 
//


package ua.silentium.entity.vouchers;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Type_food.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Type_food">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="All inclusive"/>
 *     &lt;enumeration value="Breakfast"/>
 *     &lt;enumeration value="Breakfast and dinner"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Type_food")
@XmlEnum
public enum TypeFood {

    @XmlEnumValue("All inclusive")
    ALL_INCLUSIVE("All inclusive"),
    @XmlEnumValue("Breakfast")
    BREAKFAST("Breakfast"),
    @XmlEnumValue("Breakfast and dinner")
    BREAKFAST_AND_DINNER("Breakfast and dinner");
    private final String value;

    TypeFood(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeFood fromValue(String v) {
        for (TypeFood c: TypeFood.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
