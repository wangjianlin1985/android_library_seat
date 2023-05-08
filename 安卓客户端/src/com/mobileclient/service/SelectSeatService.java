package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SelectSeat;
import com.mobileclient.util.HttpUtil;

/*选座管理业务逻辑层*/
public class SelectSeatService {
	/* 添加选座 */
	public String AddSelectSeat(SelectSeat selectSeat) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectId", selectSeat.getSelectId() + "");
		params.put("seatObj", selectSeat.getSeatObj() + "");
		params.put("userObj", selectSeat.getUserObj());
		params.put("startTime", selectSeat.getStartTime());
		params.put("endTime", selectSeat.getEndTime());
		params.put("seatState", selectSeat.getSeatState());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SelectSeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询选座 */
	public List<SelectSeat> QuerySelectSeat(SelectSeat queryConditionSelectSeat) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SelectSeatServlet?action=query";
		if(queryConditionSelectSeat != null) {
			urlString += "&seatObj=" + queryConditionSelectSeat.getSeatObj();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionSelectSeat.getUserObj(), "UTF-8") + "";
			urlString += "&startTime=" + URLEncoder.encode(queryConditionSelectSeat.getStartTime(), "UTF-8") + "";
			urlString += "&endTime=" + URLEncoder.encode(queryConditionSelectSeat.getEndTime(), "UTF-8") + "";
			urlString += "&seatState=" + URLEncoder.encode(queryConditionSelectSeat.getSeatState(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SelectSeatListHandler selectSeatListHander = new SelectSeatListHandler();
		xr.setContentHandler(selectSeatListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SelectSeat> selectSeatList = selectSeatListHander.getSelectSeatList();
		return selectSeatList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SelectSeat> selectSeatList = new ArrayList<SelectSeat>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SelectSeat selectSeat = new SelectSeat();
				selectSeat.setSelectId(object.getInt("selectId"));
				selectSeat.setSeatObj(object.getInt("seatObj"));
				selectSeat.setUserObj(object.getString("userObj"));
				selectSeat.setStartTime(object.getString("startTime"));
				selectSeat.setEndTime(object.getString("endTime"));
				selectSeat.setSeatState(object.getString("seatState"));
				selectSeatList.add(selectSeat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectSeatList;
	}

	/* 更新选座 */
	public String UpdateSelectSeat(SelectSeat selectSeat) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectId", selectSeat.getSelectId() + "");
		params.put("seatObj", selectSeat.getSeatObj() + "");
		params.put("userObj", selectSeat.getUserObj());
		params.put("startTime", selectSeat.getStartTime());
		params.put("endTime", selectSeat.getEndTime());
		params.put("seatState", selectSeat.getSeatState());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SelectSeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除选座 */
	public String DeleteSelectSeat(int selectId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectId", selectId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SelectSeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "选座信息删除失败!";
		}
	}

	/* 根据选座id获取选座对象 */
	public SelectSeat GetSelectSeat(int selectId)  {
		List<SelectSeat> selectSeatList = new ArrayList<SelectSeat>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("selectId", selectId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SelectSeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SelectSeat selectSeat = new SelectSeat();
				selectSeat.setSelectId(object.getInt("selectId"));
				selectSeat.setSeatObj(object.getInt("seatObj"));
				selectSeat.setUserObj(object.getString("userObj"));
				selectSeat.setStartTime(object.getString("startTime"));
				selectSeat.setEndTime(object.getString("endTime"));
				selectSeat.setSeatState(object.getString("seatState"));
				selectSeatList.add(selectSeat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = selectSeatList.size();
		if(size>0) return selectSeatList.get(0); 
		else return null; 
	}
}
