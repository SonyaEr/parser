package ua.parser;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import ua.silentium.entity.vouchers.VoucherList;

public class JAXBParser {

    /**
     * XML --> Java
     */
    public static VoucherList XMLToJava(final String xml_file_name, final String xsd_file_name, Class<?> objectFactory)
	    throws JAXBException, SAXException {
        System.out.println("JAXB XMLToJava:" + xml_file_name + "- to Java" );
	JAXBContext jc = JAXBContext.newInstance(objectFactory);
	Unmarshaller unmarshaller = jc.createUnmarshaller();
	SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

	Schema schema = null;
	if ("".equals(xsd_file_name)) {
	    schema = sf.newSchema();
	} else {
	    schema = sf.newSchema(new File(xsd_file_name));
	}

	unmarshaller.setSchema(schema);
	unmarshaller.setEventHandler(new ValidationEventHandler() {
	    @Override
	    public boolean handleEvent(ValidationEvent event) {
		System.err.println(xml_file_name + " is NOT valid against " + xsd_file_name + ":\n" + event.getMessage());
		return false;
	    }
	});

	try {
	    VoucherList vouchers = (VoucherList) unmarshaller.unmarshal(new File(xml_file_name));
            return vouchers;
        }
        catch (Exception ex)
        {
            return null;
        }
    }


    /**
     * Java --> XML
     */
    public static void JavaToXML(VoucherList vouchers, final String xml_file_name, final String xsd_file_name,
	    Class<?> objectFactory) throws JAXBException, SAXException {
	    System.out.println("JAXB JavaToXML: Java to " + xml_file_name );
	JAXBContext jc = JAXBContext.newInstance(objectFactory);
	Marshaller marshaller = jc.createMarshaller();

	// obtain schema
	SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	
	Schema schema = sf.newSchema(new File(xsd_file_name));

	marshaller.setSchema(schema);
	marshaller.setEventHandler(new ValidationEventHandler() {
	    @Override
	    public boolean handleEvent(ValidationEvent event) {
		System.err.println(xml_file_name + " is NOT valid against " + xsd_file_name + ":\n" + event.getMessage());
		return false;
	    }
	});
	
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, XMLConst.SCHEMA_LOCATION__URI);
	try {
            marshaller.marshal(vouchers, new File(xml_file_name));
            System.out.println("Success new  " + xml_file_name );
        }
        catch (Exception ex)
        {
            System.out.println("Parsing exception: \n" + ex);
        }
    }


}
