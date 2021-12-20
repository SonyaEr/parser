package ua;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import ua.parser.DOMParser;
import ua.parser.JAXBParser;
import ua.parser.SAX;
import ua.parser.XMLConst;
import ua.parser.XMLtoHTML;
import ua.silentium.entity.vouchers.VoucherList;

public class ParserTest {

    private final IOData ioData;
    JAXBParser jaxbParser = new JAXBParser();

    public ParserTest(IOData ioData) {
	this.ioData = ioData;
    }

    public void start() throws TransformerException, JAXBException, SAXException, DOMException {
	do {
	 // String xml_file_name = XMLConst.XML_FILE_Voucherlist_invalid;
	    String xsd_file_name = XMLConst.XSD_FILE_Voucherlist;
	    String xml_file_name = XMLConst.XML_FILE_Voucherlist;
	    ioData.printData(Const.INPUT_TYPE_ACTION);
	    DOMParser dom_parser = new DOMParser();
	    XMLtoHTML xmltohtml = new XMLtoHTML();
	    SAX sax_parser = new SAX();
	    String type = ioData.inputData();
	    switch (type) {
	    case "0":
		VoucherList jaxb = JAXBParser.XMLToJava(xml_file_name, xsd_file_name, XMLConst.OBJECT_FACTORY);
		ioData.printData(jaxb.toString());
		break;
	    case "1":
		VoucherList jaxbforclass = JAXBParser.XMLToJava(xml_file_name, xsd_file_name, XMLConst.OBJECT_FACTORY);
		JAXBParser.JavaToXML(jaxbforclass, "voucherlist.jaxb.xml", xsd_file_name, XMLConst.OBJECT_FACTORY); 
		break;
	    case "2":
		VoucherList dom = dom_parser.XMLToJava(xml_file_name);
		ioData.printData(dom.toString());
		break;
	    case "3":
		VoucherList domforclass = JAXBParser.XMLToJava(xml_file_name, xsd_file_name, XMLConst.OBJECT_FACTORY);
		dom_parser.JavaToXML(domforclass, "voucherlist.dom.xml", false);
		break;
	    case "4":
		VoucherList sax = sax_parser.XmlToJava(xml_file_name);
		ioData.printData(sax.toString());
		break;
	    case "5":
		xmltohtml.convertXMLToHTML(xml_file_name, "vouchers.xsl", "vouchers.html");
		break;
	    default:
		break;
	    }
	    ioData.printData(Const.INPUT_STOP);
	} while (!ioData.inputData().equals("stop"));
    }
}
