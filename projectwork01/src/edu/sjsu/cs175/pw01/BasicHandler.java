package edu.sjsu.cs175.pw01;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BasicHandler extends DefaultHandler {
    private StringBuffer lastStr = new StringBuffer();

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        lastStr.setLength(0);    
    }

    public void characters(char[] ch, int start, int length) throws SAXException {       
        lastStr.append(ch, start, length);
    }
    
    public String lastString() {
        return lastStr.toString();
    }
}