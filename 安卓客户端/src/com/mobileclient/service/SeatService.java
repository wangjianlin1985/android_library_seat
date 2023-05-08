package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Seat;
import com.mobileclient.util.HttpUtil;

/*座位管理业务逻辑层*/
public class SeatService {
	/* 添加座位 */
	public String AddSeat(Seat seat) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seat.getSeatId() + "");
		params.put("roomObj", seat.getRoomObj() + "");
		params.put("seatCode", seat.getSeatCode());
		params.put("seatStateObj", seat.getSeatStateObj() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询座位 */
	public List<Seat> QuerySeat(Seat queryConditionSeat) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SeatServlet?action=query";
		if(queryConditionSeat != null) {
			urlString += "&roomObj=" + queryConditionSeat.getRoomObj();
			urlString += "&seatCode=" + URLEncoder.encode(queryConditionSeat.getSeatCode(), "UTF-8") + "";
			urlString += "&seatStateObj=" + queryConditionSeat.getSeatStateObj();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SeatListHandler seatListHander = new SeatListHandler();
		xr.setContentHandler(seatListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Seat> seatList = seatListHander.getSeatList();
		return seatList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Seat> seatList = new ArrayList<Seat>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Seat seat = new Seat();
				seat.setSeatId(object.getInt("seatId"));
				seat.setRoomObj(object.getInt("roomObj"));
				seat.setSeatCode(object.getString("seatCode"));
				seat.setSeatStateObj(object.getInt("seatStateObj"));
				seatList.add(seat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seatList;
	}

	/* 更新座位 */
	public String UpdateSeat(Seat seat) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seat.getSeatId() + "");
		params.put("roomObj", seat.getRoomObj() + "");
		params.put("seatCode", seat.getSeatCode());
		params.put("seatStateObj", seat.getSeatStateObj() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除座位 */
	public String DeleteSeat(int seatId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seatId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "座位信息删除失败!";
		}
	}

	/* 根据座位id获取座位对象 */
	public Seat GetSeat(int seatId)  {
		List<Seat> seatList = new ArrayList<Seat>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("seatId", seatId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Seat seat = new Seat();
				seat.setSeatId(object.getInt("seatId"));
				seat.setRoomObj(object.getInt("roomObj"));
				seat.setSeatCode(object.getString("seatCode"));
				seat.setSeatStateObj(object.getInt("seatStateObj"));
				seatList.add(seat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = seatList.size();
		if(size>0) return seatList.get(0); 
		else return null; 
	}
}
