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
 * <p>Java class for Type_tour.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Type_tour">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Excursion"/>
 *     &lt;enumeration value="Gastro tour"/>
 *     &lt;enumeration value="Rest day"/>
 *     &lt;enumeration value="Cruise"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Type_tour")
@XmlEnum
public enum TypeTour {

    @XmlEnumValue("Excursion")
    EXCURSION("Excursion"),
    @XmlEnumValue("Gastro tour")
    GASTRO_TOUR("Gastro tour"),
    @XmlEnumValue("Rest day")
    REST_DAY("Rest day"),
    @XmlEnumValue("Cruise")
    CRUISE("Cruise");
    private final String value;

    TypeTour(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeTour fromValue(String v) {
        for (TypeTour c: TypeTour.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
