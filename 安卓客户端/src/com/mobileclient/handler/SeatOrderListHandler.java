package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.SeatOrder;
public class SeatOrderListHandler extends DefaultHandler {
	private List<SeatOrder> seatOrderList = null;
	private SeatOrder seatOrder;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (seatOrder != null) { 
            String valueString = new String(ch, start, length); 
            if ("orderId".equals(tempString)) 
            	seatOrder.setOrderId(new Integer(valueString).intValue());
            else if ("seatObj".equals(tempString)) 
            	seatOrder.setSeatObj(new Integer(valueString).intValue());
            else if ("orderDate".equals(tempString)) 
            	seatOrder.setOrderDate(Timestamp.valueOf(valueString));
            else if ("startTime".equals(tempString)) 
            	seatOrder.setStartTime(valueString); 
            else if ("endTime".equals(tempString)) 
            	seatOrder.setEndTime(valueString); 
            else if ("addTime".equals(tempString)) 
            	seatOrder.setAddTime(valueString); 
            else if ("userObj".equals(tempString)) 
            	seatOrder.setUserObj(valueString); 
            else if ("orderState".equals(tempString)) 
            	seatOrder.setOrderState(valueString); 
            else if ("replyContent".equals(tempString)) 
            	seatOrder.setReplyContent(valueString); 
            else if ("orderMemo".equals(tempString)) 
            	seatOrder.setOrderMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("SeatOrder".equals(localName)&&seatOrder!=null){
			seatOrderList.add(seatOrder);
			seatOrder = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		seatOrderList = new ArrayList<SeatOrder>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("SeatOrder".equals(localName)) {
            seatOrder = new SeatOrder(); 
        }
        tempString = localName; 
	}

	public List<SeatOrder> getSeatOrderList() {
		return this.seatOrderList;
	}
}
