package ua.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import ua.silentium.entity.Entity;
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

public class DOMParser {

    private VoucherList voucherList;
    private boolean log;

    private Document getDocument(String xml_file, InputStream in)
	    throws ParserConfigurationException, IOException, SAXException {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	dbf.setNamespaceAware(true);

	dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");

	dbf.setFeature(XMLConst.FEATURE__TURN_VALIDATION_ON, true);
	dbf.setFeature(XMLConst.FEATURE__TURN_SCHEMA_VALIDATION_ON, true);

	
        DocumentBuilder db = dbf.newDocumentBuilder();
	in = new FileInputStream(xml_file);
	    db.setErrorHandler(new DefaultHandler() {
		@Override
		public void error(SAXParseException e) throws SAXParseException {
		    throw e;
		}
	    });
	    
	return db.parse(in);
	
    }

    private void SetEntityId(Entity entity, Node node) {
	if (node.hasAttributes()) {
	    NamedNodeMap attrs = node.getAttributes();
	    Node entity_id = attrs.getNamedItem(XMLConst.ATTR_ID);
	    entity.setId(Integer.parseInt(entity_id.getTextContent()));
	}
    }

    private Tour TourParse(Node node) {
	Tour tour = new Tour();
	SetEntityId(tour, node);

	NodeList nodes = node.getChildNodes();
	Node currentNode;
	String localName;

	for (int i = 0; i < nodes.getLength(); i++) {
	    currentNode = nodes.item(i);
	    localName = currentNode.getLocalName();
	    if (Objects.isNull(localName))
		continue;

	    switch (localName) {
	    case XMLConst.TAG_TOUR_NAME:
		tour.setTourName(currentNode.getTextContent());
		break;
	    case XMLConst.TAG_TOUR_DESCRIPTION:
		tour.setTourDescription(currentNode.getTextContent());
		break;
	    case XMLConst.TAG_TOUR_QUANTITY_NIGHT:
		tour.setQuantityNight(new BigInteger(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_TOUR_OPERATOR:
		tour.setTourOperator(currentNode.getTextContent());
		break;
	    case XMLConst.TAG_TYPE_FOOD:
		tour.setTypeFood(TypeFood.fromValue(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_TYPE_TOUR:
		tour.setTypeTour(TypeTour.fromValue(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_TYPE_TRANSPORT:
		tour.setTypeTransport(TypeTransport.fromValue(currentNode.getTextContent()));
		break;
	    default:
		break;
	    }
	}
	return tour;
    }

    private TourDate TourDateParse(Node node) throws DOMException, DatatypeConfigurationException {
	TourDate tourdate = new TourDate();
	SetEntityId(tourdate, node);

	NodeList nodes = node.getChildNodes();
	Node currentNode;
	String localName;

	for (int i = 0; i < nodes.getLength(); i++) {
	    currentNode = nodes.item(i);
	    localName = currentNode.getLocalName();
	    if (Objects.isNull(localName))
		continue;

	    switch (localName) {
	    case XMLConst.TAG_TOURDATE_DATE_ARRIVAL:
		tourdate.setDateArrival(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_TOURDATE_PRICE:
		tourdate.setPrice(new BigDecimal(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_TOURDATE_NAME:
		tourdate.setTourDateName(currentNode.getTextContent());
		break;

	    case XMLConst.TAG_TOUR:
		tourdate.setTour(this.TourParse(currentNode));
		break;
	    default:
		break;
	    }
	}
	return tourdate;
    }

    private Person PersonParse(Node node) throws DOMException, DatatypeConfigurationException {
	Person person = new Person();
	NodeList nodes = node.getChildNodes();

	SetEntityId(person, node);

	Node currentNode;
	String localName;
	for (int i = 0; i < nodes.getLength(); i++) {
	    currentNode = nodes.item(i);
	    localName = currentNode.getLocalName();
	    if (Objects.isNull(localName))
		continue;

	    switch (localName) {
	    case XMLConst.TAG_PERSON_NAME:
		person.setName(currentNode.getTextContent());
		break;
	    case XMLConst.TAG_PERSON_SURNAME:
		person.setSurname(currentNode.getTextContent());
		break;
	    case XMLConst.TAG_PERSON_PATRONYMIC:
		person.setPatronymic(currentNode.getTextContent());
		break;
	    case XMLConst.TAG_PERSON_EMAIL:
		person.setEmail(currentNode.getTextContent());
		break;
	    case XMLConst.TAG_PERSON_TELNUM:
		person.setTelNum(currentNode.getTextContent());
		break;
	    case XMLConst.TAG_PERSON_ADDRESS:
		person.setAddress(currentNode.getTextContent());
		break;
	    case XMLConst.TAG_PERSON_BIRTHDATE:
		person.setBirthDate(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_PERSON_DOCUMENT:
		person.setDocument(currentNode.getTextContent());
		break;
	    case XMLConst.TAG_ROLE:
		person.setRole(Role.fromValue(currentNode.getTextContent()));
		break;
	    default:
		break;
	    }
	}
	return person;
    }

    private Order OrderParse(Node node) throws DOMException, DatatypeConfigurationException {
	Order order = new Order();
	NodeList nodes = node.getChildNodes();

	SetEntityId(order, node);

	Node currentNode;
	String localName;
	for (int i = 0; i < nodes.getLength(); i++) {
	    currentNode = nodes.item(i);
	    localName = currentNode.getLocalName();
	    if (Objects.isNull(localName))
		continue;

	    switch (localName) {
	    case XMLConst.TAG_ORDER_COUNT:
		order.setOrderCount(Integer.parseInt(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_TOUR_DATE:
		order.setTourDate(this.TourDateParse(currentNode));
		break;
	    case XMLConst.TAG_PERSON:
		order.setPerson(this.PersonParse(currentNode));
		break;
	    case XMLConst.TAG_ORDER_DATE:
		order.setOrderDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_ORDER_DATE_UPDATE:
		order.setDateUpdatedOrder(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_STATUS_ORDER:
		order.setStatusOrder(StatusOrder.fromValue(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_CONTACT:
		Contact contact = new Contact();
		NodeList childNodes = currentNode.getChildNodes();

		for (int j = 0; j < childNodes.getLength(); j++) {
		    currentNode = childNodes.item(j);
		    localName = currentNode.getLocalName();
		    if (Objects.isNull(localName))
			continue;
		    if (Objects.equals(localName, XMLConst.TAG_CONTACT_ORDER_EMAIL)) {
			contact.setOrderEmail(currentNode.getTextContent());
		    } else if (Objects.equals(localName, XMLConst.TAG_CONTACT_ORDER_TELNUM)) {
			contact.setOrderTelNum(currentNode.getTextContent());
		    }
		}
		order.setOrderContact(contact);
		break;
	    default:
		break;
	    }
	}
	return order;
    }

    private Voucher VoucherParse(Node node) throws DatatypeConfigurationException {
	Voucher voucher = new Voucher();
	NodeList nodes = node.getChildNodes();
	if (node.hasAttributes()) {
	    NamedNodeMap attrs = node.getAttributes();
	    Node priority = attrs.getNamedItem(XMLConst.ATTR_PRIORITY);
	    if (!Objects.isNull(priority))
		voucher.setPriority(Priority.fromValue(priority.getTextContent()));
	}

	SetEntityId(voucher, node);

	Node currentNode;
	String localName;
	for (int i = 0; i < nodes.getLength(); i++) {
	    currentNode = nodes.item(i);
	    localName = currentNode.getLocalName();
	    if (Objects.isNull(localName))
		continue;

	    switch (localName) {
	    case XMLConst.TAG_VOUCHER_COUNT:
		voucher.setVoucherCount(Integer.parseInt(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_VOUCHER_AMOUNT:
		voucher.setAmount(new BigDecimal(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_PERSON:
		voucher.setPerson(this.PersonParse(currentNode));
		break;
	    case XMLConst.TAG_ORDER:
		voucher.setOrder(this.OrderParse(currentNode));
		break;
	    case XMLConst.TAG_VOUCHER_DATE:
		voucher.setVoucherDate(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_VOUCHER_DATE_UPDATE:
		voucher.setDateUpdatedVoucher(
			DatatypeFactory.newInstance().newXMLGregorianCalendar(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_STATUS_VOUCHER:
		voucher.setStatusVoucher(StatusVoucher.fromValue(currentNode.getTextContent()));
		break;
	    case XMLConst.TAG_CONTACT_MANAGER:
		ContactManager contactManager = new ContactManager();
		NodeList childNodes = currentNode.getChildNodes();

		for (int j = 0; j < childNodes.getLength(); j++) {
		    currentNode = childNodes.item(j);
		    localName = currentNode.getLocalName();
		    if (Objects.isNull(localName))
			continue;
		    if (Objects.equals(localName, XMLConst.TAG_CONTACTMANAGER_VOUCHER_EMAIL)) {
			contactManager.setVoucherEmail(currentNode.getTextContent());
		    } else if (Objects.equals(localName, XMLConst.TAG_CONTACTMANAGER_VOUCHER_TELNUM)) {
			contactManager.setVoucherTelNum(currentNode.getTextContent());
		    }
		}
		voucher.setVoucherContact(contactManager);
		break;
	    default:
		break;
	    }
	}
	return voucher;
    }

    public VoucherList XMLToJava(String xml_file) {
	System.out.println("DOM XMLToJava: " + xml_file + " - to Java");
	Document doc;
	this.voucherList = new VoucherList();
	try {
	    InputStream in = new FileInputStream(xml_file);
	    doc = this.getDocument(xml_file, in);
	    Element root = doc.getDocumentElement();
	    NodeList xmlVouchers = root.getChildNodes();
	    for (int i = 0; i < xmlVouchers.getLength(); i++) {
		if (XMLConst.TAG_VOUCHER.equals(xmlVouchers.item(i).getLocalName())) {
		    this.voucherList.getVoucher().add(VoucherParse(xmlVouchers.item(i)));
		}
	    }
	} catch (Exception e) {
	    System.out.println("Exception during DOM parsing: " + e);
	}
	return this.voucherList;
    }

    public void JavaToXML(VoucherList vouchers, String xml_file, boolean log) {
	this.log = log;
	System.out.println("DOM JavaToXml: Java to " + xml_file);
	Document doc;
	this.voucherList = vouchers;
	try {
	    File file = new File(XMLConst.XSD_FILE_Voucherlist);
            String constant = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory xsdFactory = SchemaFactory.newInstance(constant);
            Schema schema = xsdFactory.newSchema(file);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setSchema(schema);
            DocumentBuilder db = dbf.newDocumentBuilder();
	    doc =  db.newDocument();
	    if (Objects.isNull(doc))
		throw new Exception("Wrong document");
	} catch (Exception e) {
	    System.out.println("Exception:" + e);
	    return;
	}

	try {
	    log("VoucherList");

	    Element voucher_list = doc.createElement(XMLConst.TAG_VOUCHERS);
	    doc.appendChild(voucher_list);

	    voucher_list.setAttribute("xmlns", XMLConst.SMV__URI);
	    voucher_list.setAttribute(String.format("xmlns:%s", XMLConst.XSI_SPACE__PREFIX),
		    "http://www.w3.org/2001/XMLSchema-instance");
	    voucher_list.setAttribute(String.format("%s:schemaLocation", XMLConst.XSI_SPACE__PREFIX),
		    XMLConst.SCHEMA_LOCATION__URI);

	    for (Voucher voucher : vouchers.getVoucher()) {
		log("Voucher id = " + voucher.getId());
		Element voucherElement = doc.createElement(XMLConst.TAG_VOUCHER);
		voucherElement.setAttribute(XMLConst.ATTR_ID, Integer.toString(voucher.getId()));
		if (!Objects.isNull(voucher.getPriority()))
		    voucherElement.setAttribute(XMLConst.ATTR_PRIORITY, voucher.getPriority().value());
		voucher_list.appendChild(voucherElement);

		log("voucher_count");
		Element voucherChild = doc.createElement(XMLConst.TAG_VOUCHER_COUNT);
		voucherChild.setTextContent(Integer.toString(voucher.getVoucherCount()));
		voucherElement.appendChild(voucherChild);

		log("amount");
		voucherChild = doc.createElement(XMLConst.TAG_VOUCHER_AMOUNT);
		voucherChild.setTextContent(voucher.getAmount().toString());
		voucherElement.appendChild(voucherChild);

		log("Person");
		Person manager = voucher.getPerson();
		voucherChild = doc.createElement(XMLConst.TAG_PERSON);
		voucherChild.setAttribute(XMLConst.ATTR_ID, Integer.toString(manager.getId()));
		voucherElement.appendChild(voucherChild);

		log("name");
		Element userChild = doc.createElement(XMLConst.TAG_PERSON_NAME);
		userChild.setTextContent(manager.getName());
		voucherChild.appendChild(userChild);

		log("surname");
		if (!Objects.isNull(manager.getSurname())) {
		    userChild = doc.createElement(XMLConst.TAG_PERSON_SURNAME);
		    userChild.setTextContent(manager.getSurname());
		    voucherChild.appendChild(userChild);
		}

		log("patronymic");
		if (!Objects.isNull(manager.getPatronymic())) {
		    userChild = doc.createElement(XMLConst.TAG_PERSON_PATRONYMIC);
		    userChild.setTextContent(manager.getPatronymic());
		    voucherChild.appendChild(userChild);
		}

		log("email");
		userChild = doc.createElement(XMLConst.TAG_PERSON_EMAIL);
		userChild.setTextContent(manager.getEmail());
		voucherChild.appendChild(userChild);

		log("telNum");
		userChild = doc.createElement(XMLConst.TAG_PERSON_TELNUM);
		userChild.setTextContent(manager.getTelNum());
		voucherChild.appendChild(userChild);

		log("address");
		userChild = doc.createElement(XMLConst.TAG_PERSON_ADDRESS);
		userChild.setTextContent(manager.getAddress());
		voucherChild.appendChild(userChild);

		log("birthDate");
		userChild = doc.createElement(XMLConst.TAG_PERSON_BIRTHDATE);
		userChild.setTextContent(manager.getBirthDate().toString());
		voucherChild.appendChild(userChild);

		log("document");
		userChild = doc.createElement(XMLConst.TAG_PERSON_DOCUMENT);
		userChild.setTextContent(manager.getDocument());
		voucherChild.appendChild(userChild);

		log("Role");
		userChild = doc.createElement(XMLConst.TAG_ROLE);
		userChild.setTextContent(manager.getRole().value());
		voucherChild.appendChild(userChild);

		log("Order");
		Order order = voucher.getOrder();

		Element orderElement = doc.createElement(XMLConst.TAG_ORDER);
		orderElement.setAttribute(XMLConst.ATTR_ID, Integer.toString(order.getId()));
		voucherElement.appendChild(orderElement);

		log("orderCount");
		Element orderChild = doc.createElement(XMLConst.TAG_PERSON_NAME);
		orderChild = doc.createElement(XMLConst.TAG_ORDER_COUNT);
		orderChild.setTextContent(Integer.toString(order.getOrderCount()));
		orderElement.appendChild(orderChild);

		log("TourDate");
		TourDate tourDate = order.getTourDate();

		Element tourDateElement = doc.createElement(XMLConst.TAG_TOUR_DATE);
		tourDateElement.setAttribute(XMLConst.ATTR_ID, Integer.toString(tourDate.getId()));
		orderElement.appendChild(tourDateElement);

		log("dateArrival");
		Element tourDateChild = doc.createElement(XMLConst.TAG_TOURDATE_DATE_ARRIVAL);
		tourDateChild.setTextContent(tourDate.getDateArrival().toString());
		tourDateElement.appendChild(tourDateChild);

		log("price");
		tourDateChild = doc.createElement(XMLConst.TAG_TOURDATE_PRICE);
		tourDateChild.setTextContent(tourDate.getPrice().toString());
		tourDateElement.appendChild(tourDateChild);

		log("tourDateName");
		if (!Objects.isNull(tourDate.getTourDateName())) {
		    tourDateChild = doc.createElement(XMLConst.TAG_TOURDATE_NAME);
		    tourDateChild.setTextContent(tourDate.getTourDateName());
		    tourDateElement.appendChild(tourDateChild);
		}

		log("Tour");
		Tour tour = tourDate.getTour();
		Element tourElement = doc.createElement(XMLConst.TAG_TOUR);
		tourElement.setAttribute(XMLConst.ATTR_ID, Integer.toString(tour.getId()));
		tourDateElement.appendChild(tourElement);

		log("tourName");
		Element tourChild = doc.createElement(XMLConst.TAG_TOUR_NAME);
		tourChild.setTextContent(tour.getTourName().toString());
		tourElement.appendChild(tourChild);

		log("tourDescription");
		tourChild = doc.createElement(XMLConst.TAG_TOUR_DESCRIPTION);
		tourChild.setTextContent(tour.getTourDescription().toString());
		tourElement.appendChild(tourChild);

		log("quantityNight");
		tourChild = doc.createElement(XMLConst.TAG_TOUR_QUANTITY_NIGHT);
		tourChild.setTextContent(tour.getQuantityNight().toString());
		tourElement.appendChild(tourChild);

		log("tourOperator");
		tourChild = doc.createElement(XMLConst.TAG_TOUR_OPERATOR);
		tourChild.setTextContent(tour.getTourOperator().toString());
		tourElement.appendChild(tourChild);

		log("TypeFood");
		tourChild = doc.createElement(XMLConst.TAG_TYPE_FOOD);
		tourChild.setTextContent(tour.getTypeFood().value());
		tourElement.appendChild(tourChild);

		log("TypeTransport");
		tourChild = doc.createElement(XMLConst.TAG_TYPE_TRANSPORT);
		tourChild.setTextContent(tour.getTypeTransport().value());
		tourElement.appendChild(tourChild);

		log("TypeTour");
		tourChild = doc.createElement(XMLConst.TAG_TYPE_TOUR);
		tourChild.setTextContent(tour.getTypeTour().value());
		tourElement.appendChild(tourChild);

		log("Person");
		Person client = order.getPerson();
		Element personElement = doc.createElement(XMLConst.TAG_PERSON);
		personElement.setAttribute(XMLConst.ATTR_ID, Integer.toString(client.getId()));
		orderElement.appendChild(personElement);

		log("name");
		Element clientChild = doc.createElement(XMLConst.TAG_PERSON_NAME);
		clientChild.setTextContent(client.getName().toString());
		personElement.appendChild(clientChild);

		log("surname");
		if (!Objects.isNull(client.getSurname())) {
		    clientChild = doc.createElement(XMLConst.TAG_PERSON_SURNAME);
		    clientChild.setTextContent(client.getSurname());
		    personElement.appendChild(clientChild);
		}

		log("patronymic");
		if (!Objects.isNull(client.getPatronymic())) {
		    clientChild = doc.createElement(XMLConst.TAG_PERSON_PATRONYMIC);
		    clientChild.setTextContent(client.getPatronymic());
		    personElement.appendChild(clientChild);
		}

		log("email");
		clientChild = doc.createElement(XMLConst.TAG_PERSON_EMAIL);
		clientChild.setTextContent(client.getEmail());
		personElement.appendChild(clientChild);

		log("telNum");
		clientChild = doc.createElement(XMLConst.TAG_PERSON_TELNUM);
		clientChild.setTextContent(client.getTelNum());
		personElement.appendChild(clientChild);

		log("address");
		clientChild = doc.createElement(XMLConst.TAG_PERSON_ADDRESS);
		clientChild.setTextContent(client.getAddress());
		personElement.appendChild(clientChild);

		log("birthDate");
		clientChild = doc.createElement(XMLConst.TAG_PERSON_BIRTHDATE);
		clientChild.setTextContent(client.getBirthDate().toString());
		personElement.appendChild(clientChild);

		log("document");
		clientChild = doc.createElement(XMLConst.TAG_PERSON_DOCUMENT);
		clientChild.setTextContent(client.getDocument());
		personElement.appendChild(clientChild);

		log("Role");
		clientChild = doc.createElement(XMLConst.TAG_ROLE);
		clientChild.setTextContent(client.getRole().value());
		personElement.appendChild(clientChild);

		log("orderDate");
		orderChild = doc.createElement(XMLConst.TAG_ORDER_DATE);
		orderChild.setTextContent(order.getOrderDate().toString());
		orderElement.appendChild(orderChild);

		log("dateUpdatedOrder");
		orderChild = doc.createElement(XMLConst.TAG_ORDER_DATE_UPDATE);
		orderChild.setTextContent(order.getDateUpdatedOrder().toString());
		orderElement.appendChild(orderChild);

		log("statusOrder");
		orderChild = doc.createElement(XMLConst.TAG_STATUS_ORDER);
		orderChild.setTextContent(order.getStatusOrder().value());
		orderElement.appendChild(orderChild);

		log("Contact");
		orderChild = doc.createElement(XMLConst.TAG_CONTACT);
		Element Contact;

		Contact contact = order.getOrderContact();
		if (contact.getOrderEmail() != null) {
		    log("orderTelnum");
		    Contact = doc.createElement(XMLConst.TAG_CONTACT_ORDER_EMAIL);
		    Contact.setTextContent(contact.getOrderEmail());
		} else {
		    log("orderEmail");
		    Contact = doc.createElement(XMLConst.TAG_CONTACT_ORDER_TELNUM);
		    Contact.setTextContent(contact.getOrderTelNum());
		}
		orderChild.appendChild(Contact);
		orderElement.appendChild(orderChild);

		log("voucherDate");
		voucherChild = doc.createElement(XMLConst.TAG_VOUCHER_DATE);
		voucherChild.setTextContent(voucher.getVoucherDate().toString());
		voucherElement.appendChild(voucherChild);

		log("dateUpdatedVoucher");
		voucherChild = doc.createElement(XMLConst.TAG_VOUCHER_DATE_UPDATE);
		voucherChild.setTextContent(voucher.getDateUpdatedVoucher().toString());
		voucherElement.appendChild(voucherChild);

		log("StatusVoucher");
		voucherChild = doc.createElement(XMLConst.TAG_STATUS_VOUCHER);
		voucherChild.setTextContent(voucher.getStatusVoucher().value());
		voucherElement.appendChild(voucherChild);

		log("Contact_manager");
		voucherChild = doc.createElement(XMLConst.TAG_CONTACT_MANAGER);
		Element ContactManager;

		ContactManager contact_manager = voucher.getVoucherContact();

		log("voucherEmail");
		ContactManager = doc.createElement(XMLConst.TAG_CONTACTMANAGER_VOUCHER_EMAIL);
		ContactManager.setTextContent(contact_manager.getVoucherEmail());
		voucherChild.appendChild(ContactManager);
		voucherElement.appendChild(voucherChild);
		
		log("voucherTelnum");
		ContactManager = doc.createElement(XMLConst.TAG_CONTACTMANAGER_VOUCHER_TELNUM);
		ContactManager.setTextContent(contact_manager.getVoucherTelNum());
		voucherChild.appendChild(ContactManager);
		voucherElement.appendChild(voucherChild);


	    }
	} catch (Exception e) {
	    System.out.println("Exception during DOM parsing Java to XML: \n" + e);
	}

	if (!this.validate(doc)) {
	    return;
	} else {
	    try {
		this.save(doc, xml_file);
	    } catch (Exception e) {
		System.out.println("Exception during file saving: \n" + e);
	    }
	}
    }

    private void save(Document doc, String xml_file) throws IOException, TransformerException {

	TransformerFactory tf = TransformerFactory.newInstance();
	Transformer transformer = tf.newTransformer();
	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	transformer.transform(new DOMSource(doc), 
		     new StreamResult(new File(xml_file)));
    }

    private void log(String str) {
	if (this.log)
	    System.out.println("Parsing " + str);
    }

    private boolean validate(Document doc) {
	try {
	    Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(XMLConst.XSD_FILE_Voucherlist));
	   
	    TransformerFactory transformerfactory  = TransformerFactory.newInstance();
	    Transformer transformer = transformerfactory.newTransformer();
	   
	    DOMSource domSource = new DOMSource(doc);
	    StringWriter stringwriter = new StringWriter();
	    StreamResult result = new StreamResult(stringwriter); 
	    transformer.transform(domSource, result);
	   
	    Validator validator = schema.newValidator();
	    validator.validate(new StreamSource(new StringReader(stringwriter.toString())));
	    
	    return true;
	} catch (Exception e) {
	    System.out.println("File" + doc + " is not valid because ");
	    System.out.println(e.getMessage());
	    return false;
	}
    }
}
