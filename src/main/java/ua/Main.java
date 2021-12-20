package ua;


import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public final class Main {

    public static void main(String[] args) throws TransformerException, JAXBException, SAXException, DOMException{
	IOData ioDate = new IOData(System.in);
	new ParserTest(ioDate).start();
    }
    
}
