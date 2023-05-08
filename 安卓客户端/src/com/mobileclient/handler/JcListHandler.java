package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Jc;
public class JcListHandler extends DefaultHandler {
	private List<Jc> jcList = null;
	private Jc jc;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (jc != null) { 
            String valueString = new String(ch, start, length); 
            if ("jcId".equals(tempString)) 
            	jc.setJcId(new Integer(valueString).intValue());
            else if ("jcType".equals(tempString)) 
            	jc.setJcType(valueString); 
            else if ("title".equals(tempString)) 
            	jc.setTitle(valueString); 
            else if ("content".equals(tempString)) 
            	jc.setContent(valueString); 
            else if ("userObj".equals(tempString)) 
            	jc.setUserObj(valueString); 
            else if ("creditScore".equals(tempString)) 
            	jc.setCreditScore(new Float(valueString).floatValue());
            else if ("jcTime".equals(tempString)) 
            	jc.setJcTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Jc".equals(localName)&&jc!=null){
			jcList.add(jc);
			jc = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		jcList = new ArrayList<Jc>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Jc".equals(localName)) {
            jc = new Jc(); 
        }
        tempString = localName; 
	}

	public List<Jc> getJcList() {
		return this.jcList;
	}
}
