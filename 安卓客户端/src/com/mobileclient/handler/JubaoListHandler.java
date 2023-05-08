package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Jubao;
public class JubaoListHandler extends DefaultHandler {
	private List<Jubao> jubaoList = null;
	private Jubao jubao;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (jubao != null) { 
            String valueString = new String(ch, start, length); 
            if ("jubaoId".equals(tempString)) 
            	jubao.setJubaoId(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	jubao.setTitle(valueString); 
            else if ("content".equals(tempString)) 
            	jubao.setContent(valueString); 
            else if ("userObj".equals(tempString)) 
            	jubao.setUserObj(valueString); 
            else if ("jubaoTime".equals(tempString)) 
            	jubao.setJubaoTime(valueString); 
            else if ("replyContent".equals(tempString)) 
            	jubao.setReplyContent(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Jubao".equals(localName)&&jubao!=null){
			jubaoList.add(jubao);
			jubao = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		jubaoList = new ArrayList<Jubao>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Jubao".equals(localName)) {
            jubao = new Jubao(); 
        }
        tempString = localName; 
	}

	public List<Jubao> getJubaoList() {
		return this.jubaoList;
	}
}
