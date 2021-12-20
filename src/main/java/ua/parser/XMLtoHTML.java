package ua.parser;

import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XMLtoHTML {
    public void convertXMLToHTML(String xml, String xsl, String html) {
	System.out.println("XMLToHTML" + xml + "," + xsl + " to " + html);
	StringWriter sw = new StringWriter();
	try {
	    FileWriter fw = new FileWriter(html);
	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer trasformer = factory.newTransformer(new StreamSource(xsl));
	    trasformer.transform(new StreamSource(xml), new StreamResult(sw));
	    fw.write(sw.toString());
	    fw.close();
	    System.out.println(html + " is done");
	} catch (Exception e) {
	    System.out.println("Exception during XML to HTML:" + e);
	}
    }
}
