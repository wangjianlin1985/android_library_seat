package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.UserType;
public class UserTypeListHandler extends DefaultHandler {
	private List<UserType> userTypeList = null;
	private UserType userType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (userType != null) { 
            String valueString = new String(ch, start, length); 
            if ("userTypeId".equals(tempString)) 
            	userType.setUserTypeId(new Integer(valueString).intValue());
            else if ("userTypeName".equals(tempString)) 
            	userType.setUserTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("UserType".equals(localName)&&userType!=null){
			userTypeList.add(userType);
			userType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		userTypeList = new ArrayList<UserType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("UserType".equals(localName)) {
            userType = new UserType(); 
        }
        tempString = localName; 
	}

	public List<UserType> getUserTypeList() {
		return this.userTypeList;
	}
}
