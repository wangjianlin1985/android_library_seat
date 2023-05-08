package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Seat;
public class SeatListHandler extends DefaultHandler {
	private List<Seat> seatList = null;
	private Seat seat;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (seat != null) { 
            String valueString = new String(ch, start, length); 
            if ("seatId".equals(tempString)) 
            	seat.setSeatId(new Integer(valueString).intValue());
            else if ("roomObj".equals(tempString)) 
            	seat.setRoomObj(new Integer(valueString).intValue());
            else if ("seatCode".equals(tempString)) 
            	seat.setSeatCode(valueString); 
            else if ("seatStateObj".equals(tempString)) 
            	seat.setSeatStateObj(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Seat".equals(localName)&&seat!=null){
			seatList.add(seat);
			seat = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		seatList = new ArrayList<Seat>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Seat".equals(localName)) {
            seat = new Seat(); 
        }
        tempString = localName; 
	}

	public List<Seat> getSeatList() {
		return this.seatList;
	}
}
