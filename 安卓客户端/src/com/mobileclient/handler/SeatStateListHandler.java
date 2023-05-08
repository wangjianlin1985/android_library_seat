package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SeatState;
public class SeatStateListHandler extends DefaultHandler {
	private List<SeatState> seatStateList = null;
	private SeatState seatState;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (seatState != null) { 
            String valueString = new String(ch, start, length); 
            if ("stateId".equals(tempString)) 
            	seatState.setStateId(new Integer(valueString).intValue());
            else if ("stateName".equals(tempString)) 
            	seatState.setStateName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SeatState".equals(localName)&&seatState!=null){
			seatStateList.add(seatState);
			seatState = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		seatStateList = new ArrayList<SeatState>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SeatState".equals(localName)) {
            seatState = new SeatState(); 
        }
        tempString = localName; 
	}

	public List<SeatState> getSeatStateList() {
		return this.seatStateList;
	}
}
