package ejemplos;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

//Basado en:
//https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html

public class Main extends DefaultHandler {

	private Hashtable tags;
	int nodosImpresos = 0;
	int waysImpresos = 0;
	int maxNodos = 5;
	int maxWays = 5;

	static public void main(String[] args) throws Exception{
		String filename = "./data/map.osm.txt";
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		    
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new Main());
		xmlReader.parse(filename);
	}

	
	public void startDocument() throws SAXException {
		tags = new Hashtable();
	}

	public boolean insideWay = false;
	
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

		String tag = localName;
		Object value = tags.get(tag);

		if (value == null) {
			tags.put(tag, new Integer(1));
		} else {
			int count = ((Integer) value).intValue();
			count++;
			tags.put(tag, new Integer(count));
		}
		
		
		if(tag.equals("node") && nodosImpresos < maxNodos) {// && !printedFirstNode) {
			System.out.println("Node");
			for(int n = 0; n < atts.getLength(); n++) {
				System.out.println("QName del atributo: " + atts.getQName(n)+ ": value del atributo " + atts.getValue(n) + ""
						+ " local name del atributo " + atts.getLocalName(n));
			}
			nodosImpresos += 1;
			
		}
		
		if (tag.equals("way") && waysImpresos < maxWays) {
			System.out.println("Way");
			insideWay = true;
			for(int n = 0; n < atts.getLength(); n++) {
				System.out.println("QName del atributo: " + atts.getQName(n)+ ": value del atributo " + atts.getValue(n) + ""
						+ " local name del atributo " + atts.getLocalName(n));
			}
		}
		
		if (tag.equals("nd")  && waysImpresos < maxWays) {
			System.out.print("ND\t ");
			for(int n = 0; n < atts.getLength(); n++) {
				System.out.println("QName del atributo: " + atts.getQName(n)+ ": value del atributo " + atts.getValue(n) + ""
						+ " local name del atributo " + atts.getLocalName(n));
			}
		}
		
		if (tag.equals("tag") && insideWay) {
			if (atts.getValue("k").equals("highway")) {
				//System.out.println("QName del atributo: " + atts.getQName(n)+ ": value del atributo " + atts.getValue(n) + ""
				//		+ " local name del atributo " + atts.getLocalName(n));
				System.out.println("Found a highway " + tag + atts.getType("k"));
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
            throws SAXException {
		if (localName.equals("way") && waysImpresos < maxWays) {
			insideWay = false;
			System.out.println("END--" +localName + " " + qName);
			waysImpresos += 1;
		}
	}

	public void endDocument() throws SAXException {
		Enumeration e = tags.keys();
		
		for(Object o: tags.keySet()) {
			String tag = (String) o;
			int count = ((Integer) tags.get(tag)).intValue();
			System.out.println("Local Name \"" + tag + "\" occurs " + count + " times");
		}
	}
}
