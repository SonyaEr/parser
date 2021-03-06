//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.11.09 at 03:40:04 AM EET 
//


package ua.silentium.entity.vouchers;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ua.silentium.entity.Entity;


/**
 * <p>Java class for Tour_date complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Tour_date">
 *   &lt;complexContent>
 *     &lt;extension base="{http://silentium.ua/entity}Entity">
 *       &lt;sequence>
 *         &lt;element name="date_arrival" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="price" type="{http://silentium.ua/entity/vouchers}Price"/>
 *         &lt;element name="tour_date_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tour" type="{http://silentium.ua/entity/vouchers}Tour"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tour_date", propOrder = {
    "dateArrival",
    "price",
    "tourDateName",
    "tour"
})
public class TourDate
    extends Entity
{

    @XmlElement(name = "date_arrival", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateArrival;
    @XmlElement(required = true)
    protected BigDecimal price;
    @XmlElement(name = "tour_date_name")
    protected String tourDateName;
    @XmlElement(name = "Tour", required = true)
    protected Tour tour;

    /**
     * Gets the value of the dateArrival property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateArrival() {
        return dateArrival;
    }

    /**
     * Sets the value of the dateArrival property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateArrival(XMLGregorianCalendar value) {
        this.dateArrival = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    /**
     * Gets the value of the tourDateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTourDateName() {
        return tourDateName;
    }

    /**
     * Sets the value of the tourDateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTourDateName(String value) {
        this.tourDateName = value;
    }

    /**
     * Gets the value of the tour property.
     * 
     * @return
     *     possible object is
     *     {@link Tour }
     *     
     */
    public Tour getTour() {
        return tour;
    }

    /**
     * Sets the value of the tour property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tour }
     *     
     */
    public void setTour(Tour value) {
        this.tour = value;
    }
    
    @Override
   	public String toString() {
   		StringBuilder builder = new StringBuilder();
   		builder.append("TourDate(id=");
   		builder.append(id);
   		builder.append(", dateArrival=");
   		builder.append(dateArrival);
   		builder.append(", price=");
   		builder.append(price);
   		builder.append(", tourDateName=");
   		builder.append(tourDateName);
   		builder.append(", \n\t\t\t\tTour:");
   		builder.append(tour);
   		builder.append(")");
   		return builder.toString();
   	}
}
