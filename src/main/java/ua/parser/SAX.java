package ua.parser;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import ua.silentium.entity.vouchers.Contact;
import ua.silentium.entity.vouchers.ContactManager;
import ua.silentium.entity.vouchers.Order;
import ua.silentium.entity.vouchers.Person;
import ua.silentium.entity.vouchers.Priority;
import ua.silentium.entity.vouchers.Role;
import ua.silentium.entity.vouchers.StatusOrder;
import ua.silentium.entity.vouchers.StatusVoucher;
import ua.silentium.entity.vouchers.Tour;
import ua.silentium.entity.vouchers.TourDate;
import ua.silentium.entity.vouchers.TypeFood;
import ua.silentium.entity.vouchers.TypeTour;
import ua.silentium.entity.vouchers.TypeTransport;
import ua.silentium.entity.vouchers.Voucher;
import ua.silentium.entity.vouchers.VoucherList;

public class SAX extends DefaultHandler {

    private VoucherList voucherList;
    private Voucher voucher;
    private ContactManager contactManager;
    private Order order;
    private Contact contact;
    private Person manager;
    private Person client;
    private Tour tour;
    private TourDate tourDate;

    private String currentNode;

    private Boolean person_voucher;    
    
    
    public VoucherList XmlToJava(String xml_file_name) {
	System.out.println("SAX XmlToJava: " + xml_file_name + " to Java ");
	this.voucherList = new VoucherList();
	InputStream file;
	try {
	    file = new FileInputStream(xml_file_name);
	    SAXParserFactory factory = SAXParserFactory.newInstance();

	    factory.setNamespaceAware(true);

	    factory.setFeature(XMLConst.FEATURE__TURN_VALIDATION_ON, true);
	    factory.setFeature(XMLConst.FEATURE__TURN_SCHEMA_VALIDATION_ON, true);

	    SAXParser saxParser = factory.newSAXParser();
	    saxParser.parse(file, this);
	} catch (Exception ex) {
            ex.printStackTrace();
	    return null;
	}
	return this.voucherList;
    }

    @Override
    public void error(SAXParseException ex) throws SAXException {
	throw ex;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	currentNode = localName;
	int atributesLength = attributes.getLength();
	if (XMLConst.TAG_VOUCHER.equals(currentNode)) {
	    voucher = new Voucher();
	    if (atributesLength > 0) 
		voucher.setId(Integer.parseInt(attributes.getValue("id")));
		if (atributesLength > 1) 
	        voucher.setPriority(Priority.fromValue(attributes.getValue("priority")));
	} else if (XMLConst.TAG_PERSON.equals(currentNode)) {
	    if(person_voucher) {manager = new Person();
	    if (atributesLength > 0)
		       manager.setId(Integer.parseInt(attributes.getValue("id")));
	    }
	    else {client = new Person();
	    if (atributesLength > 0)
		       client.setId(Integer.parseInt(attributes.getValue("id")));
	    }
	} else if (XMLConst.TAG_ORDER.equals(currentNode)) {
	    order = new Order();
	    if (atributesLength > 0)
		order.setId(Integer.parseInt(attributes.getValue("id")));
	} else if (XMLConst.TAG_TOUR_DATE.equals(currentNode)) {
	    tourDate = new TourDate();
	    if (atributesLength > 0)
		tourDate.setId(Integer.parseInt(attributes.getValue("id")));
	} else if (XMLConst.TAG_TOUR.equals(currentNode)) {
	    tour = new Tour();
	    if (atributesLength > 0)
		tour.setId(Integer.parseInt(attributes.getValue("id")));

	} else if (XMLConst.TAG_CONTACT.equals(currentNode)) {
	    contact = new Contact();
	} else if (XMLConst.TAG_CONTACT_MANAGER.equals(currentNode)) {
	    contactManager = new ContactManager();
	}
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
	if (XMLConst.TAG_VOUCHER_COUNT.equals(currentNode)) {
	    voucher.setVoucherCount(Integer.parseInt(new String(ch, start, length)));
	    person_voucher = true;
	} else if (XMLConst.TAG_VOUCHER_AMOUNT.equals(currentNode)) {
	    voucher.setAmount(new BigDecimal(new String(ch, start, length)));
	} else if (XMLConst.TAG_PERSON_NAME.equals(currentNode)  ) {
		if (person_voucher) 
		    manager.setName(new String(ch, start, length));
		else client.setName(new String(ch, start, length));
	} else if (XMLConst.TAG_PERSON_SURNAME.equals(currentNode)) {
	    if (person_voucher) 
		    manager.setSurname(new String(ch, start, length));
		else client.setSurname(new String(ch, start, length));
	} else if (XMLConst.TAG_PERSON_PATRONYMIC.equals(currentNode)) {
	    if (person_voucher) 
		    manager.setPatronymic(new String(ch, start, length));
		else client.setPatronymic(new String(ch, start, length));
	} else if (XMLConst.TAG_PERSON_EMAIL.equals(currentNode)) {
	    if (person_voucher) 
		    manager.setEmail(new String(ch, start, length));
		else client.setEmail(new String(ch, start, length));
	} else if (XMLConst.TAG_PERSON_TELNUM.equals(currentNode)) {
	    if (person_voucher) 
		    manager.setTelNum(new String(ch, start, length));
		else client.setTelNum(new String(ch, start, length));
	} else if (XMLConst.TAG_PERSON_ADDRESS.equals(currentNode)) { 
	    if (person_voucher) 
		    manager.setAddress(new String(ch, start, length));
		else client.setAddress(new String(ch, start, length));
	} else if (XMLConst.TAG_PERSON_BIRTHDATE.equals(currentNode)) {
	    try {
		 if (person_voucher) 
		     manager.setBirthDate(
				DatatypeFactory.newInstance().newXMLGregorianCalendar(new String(ch, start, length)));
		else client.setBirthDate(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(new String(ch, start, length)));
	    } catch (DatatypeConfigurationException e) {
		System.out.println("Exception in parsing manager birth_date: " + e);
	    }
	} else if (XMLConst.TAG_PERSON_DOCUMENT.equals(currentNode)) {
	    if (person_voucher) 
		    manager.setDocument(new String(ch, start, length));
		else client.setDocument(new String(ch, start, length));
	} else if (XMLConst.TAG_ROLE.equals(currentNode)) {
	    if (person_voucher) 
		    manager.setRole(Role.fromValue(new String(ch, start, length)));
		else client.setRole(Role.fromValue(new String(ch, start, length)));
	    
	} else if (XMLConst.TAG_ORDER_COUNT.equals(currentNode)) {
	    order.setOrderCount(Integer.parseInt(new String(ch, start, length)));
	    person_voucher = false;
	} else if (XMLConst.TAG_TOURDATE_DATE_ARRIVAL.equals(currentNode)) {
	    try {
		tourDate.setDateArrival(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(new String(ch, start, length)));
	    } catch (DatatypeConfigurationException e) {
		System.out.println("Exception in parsing date_arrival: " + e);
	    }
	} else if (XMLConst.TAG_TOURDATE_PRICE.equals(currentNode)) {
	    tourDate.setPrice(new BigDecimal(new String(ch, start, length)));

	} else if (XMLConst.TAG_TOURDATE_NAME.equals(currentNode)) {
	    tourDate.setTourDateName(new String(ch, start, length));
	} else if (XMLConst.TAG_TOUR_NAME.equals(currentNode)) {
	    tour.setTourName(new String(ch, start, length));
	} else if (XMLConst.TAG_TOUR_DESCRIPTION.equals(currentNode)) {
	    tour.setTourDescription(new String(ch, start, length));
	} else if (XMLConst.TAG_TOUR_QUANTITY_NIGHT.equals(currentNode)) {
	    tour.setQuantityNight(new BigInteger(new String(ch, start, length)));
	} else if (XMLConst.TAG_TOUR_OPERATOR.equals(currentNode)) {
	    tour.setTourOperator(new String(ch, start, length));
	} else if (XMLConst.TAG_TYPE_FOOD.equals(currentNode)) {
	    tour.setTypeFood(TypeFood.fromValue(new String(ch, start, length)));
	} else if (XMLConst.TAG_TYPE_TRANSPORT.equals(currentNode)) {
	    tour.setTypeTransport(TypeTransport.fromValue(new String(ch, start, length)));
	} else if (XMLConst.TAG_TYPE_TOUR.equals(currentNode)) {
	    tour.setTypeTour(TypeTour.fromValue(new String(ch, start, length)));
	    
	} else if (XMLConst.TAG_ORDER_DATE.equals(currentNode)) {
	    try {
		order.setOrderDate(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(new String(ch, start, length)));
	    } catch (DatatypeConfigurationException e) {
		System.out.println("Exception in parsing order_date: " + e);
	    }
	} else if (XMLConst.TAG_ORDER_DATE_UPDATE.equals(currentNode)) {
	    try {
		order.setDateUpdatedOrder(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(new String(ch, start, length)));
	    } catch (DatatypeConfigurationException e) {
		System.out.println("Exception in parsing order_date_update:" + e);
	    }
	} else if (XMLConst.TAG_STATUS_ORDER.equals(currentNode)) {
	    order.setStatusOrder(StatusOrder.fromValue(new String(ch, start, length)));
	} else if (XMLConst.TAG_CONTACT_ORDER_EMAIL.equals(currentNode)) {
	    contact.setOrderEmail(new String(ch, start, length));;
	} else if (XMLConst.TAG_CONTACT_ORDER_TELNUM.equals(currentNode)) {
	    contact.setOrderTelNum(new String(ch, start, length));;
	} else if (XMLConst.TAG_VOUCHER_DATE.equals(currentNode)) {
	    try {
		voucher.setVoucherDate(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(new String(ch, start, length)));
	    } catch (DatatypeConfigurationException e) {
		System.out.println("Exception in parsing voucher_date: " + e);
	    }
	} else if (XMLConst.TAG_VOUCHER_DATE_UPDATE.equals(currentNode)) {
	    try {
		voucher.setDateUpdatedVoucher(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(new String(ch, start, length)));
	    } catch (DatatypeConfigurationException e) {
		System.out.println("Exception in parsing voucher_date_update:" + e);
	    }
	} else if (XMLConst.TAG_STATUS_VOUCHER.equals(currentNode)) {
	    voucher.setStatusVoucher(StatusVoucher.fromValue(new String(ch, start, length)));
	
	} else if (XMLConst.TAG_CONTACTMANAGER_VOUCHER_EMAIL.equals(currentNode)) {
	    contactManager.setVoucherEmail(new String(ch, start, length));;
	} else if (XMLConst.TAG_CONTACTMANAGER_VOUCHER_TELNUM.equals(currentNode)) {
	    contactManager.setVoucherTelNum(new String(ch, start, length));;
	}

    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
	if (XMLConst.TAG_VOUCHER.equals(localName)) {
	    voucherList.getVoucher().add(voucher);
	} else if (XMLConst.TAG_PERSON.equals(localName)) {
	    if (person_voucher)
	    voucher.setPerson(manager);
	    else
	    order.setPerson(client);
        } else if (XMLConst.TAG_ORDER.equals(localName)) {
            voucher.setOrder(order);
        } else if (XMLConst.TAG_TOUR_DATE.equals(localName)) {
            order.setTourDate(tourDate);
	} else if (XMLConst.TAG_TOUR.equals(localName)) {
	    tourDate.setTour(tour);
	} else if (XMLConst.TAG_CONTACT.equals(localName)) {
	    order.setOrderContact(contact);
	} else if (XMLConst.TAG_CONTACT_MANAGER.equals(localName)) {
	    voucher.setVoucherContact(contactManager);
	}
    }
}
