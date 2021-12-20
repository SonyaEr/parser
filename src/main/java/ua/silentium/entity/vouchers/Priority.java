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
 * <p>Java class for Priority.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Priority">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="High-priority"/>
 *     &lt;enumeration value="low-priority"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Priority")
@XmlEnum
public enum Priority {

    @XmlEnumValue("High-priority")
    HIGH_PRIORITY("High-priority"),
    @XmlEnumValue("low-priority")
    LOW_PRIORITY("low-priority");
    private final String value;

    Priority(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Priority fromValue(String v) {
        for (Priority c: Priority.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}