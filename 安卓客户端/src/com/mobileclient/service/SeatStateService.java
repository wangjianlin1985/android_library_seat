package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SeatState;
import com.mobileclient.util.HttpUtil;

/*座位状态管理业务逻辑层*/
public class SeatStateService {
	/* 添加座位状态 */
	public String AddSeatState(SeatState seatState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", seatState.getStateId() + "");
		params.put("stateName", seatState.getStateName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询座位状态 */
	public List<SeatState> QuerySeatState(SeatState queryConditionSeatState) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SeatStateServlet?action=query";
		if(queryConditionSeatState != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SeatStateListHandler seatStateListHander = new SeatStateListHandler();
		xr.setContentHandler(seatStateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SeatState> seatStateList = seatStateListHander.getSeatStateList();
		return seatStateList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SeatState> seatStateList = new ArrayList<SeatState>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SeatState seatState = new SeatState();
				seatState.setStateId(object.getInt("stateId"));
				seatState.setStateName(object.getString("stateName"));
				seatStateList.add(seatState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seatStateList;
	}

	/* 更新座位状态 */
	public String UpdateSeatState(SeatState seatState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", seatState.getStateId() + "");
		params.put("stateName", seatState.getStateName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除座位状态 */
	public String DeleteSeatState(int stateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "座位状态信息删除失败!";
		}
	}

	/* 根据状态id获取座位状态对象 */
	public SeatState GetSeatState(int stateId)  {
		List<SeatState> seatStateList = new ArrayList<SeatState>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("stateId", stateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SeatState seatState = new SeatState();
				seatState.setStateId(object.getInt("stateId"));
				seatState.setStateName(object.getString("stateName"));
				seatStateList.add(seatState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = seatStateList.size();
		if(size>0) return seatStateList.get(0); 
		else return null; 
	}
}
