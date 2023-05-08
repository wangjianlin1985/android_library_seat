package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SelectSeat;
public class SelectSeatListHandler extends DefaultHandler {
	private List<SelectSeat> selectSeatList = null;
	private SelectSeat selectSeat;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (selectSeat != null) { 
            String valueString = new String(ch, start, length); 
            if ("selectId".equals(tempString)) 
            	selectSeat.setSelectId(new Integer(valueString).intValue());
            else if ("seatObj".equals(tempString)) 
            	selectSeat.setSeatObj(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	selectSeat.setUserObj(valueString); 
            else if ("startTime".equals(tempString)) 
            	selectSeat.setStartTime(valueString); 
            else if ("endTime".equals(tempString)) 
            	selectSeat.setEndTime(valueString); 
            else if ("seatState".equals(tempString)) 
            	selectSeat.setSeatState(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SelectSeat".equals(localName)&&selectSeat!=null){
			selectSeatList.add(selectSeat);
			selectSeat = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		selectSeatList = new ArrayList<SelectSeat>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SelectSeat".equals(localName)) {
            selectSeat = new SelectSeat(); 
        }
        tempString = localName; 
	}

	public List<SelectSeat> getSelectSeatList() {
		return this.selectSeatList;
	}
}
