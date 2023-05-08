package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.SeatOrder;
import com.mobileclient.util.HttpUtil;

/*座位预约管理业务逻辑层*/
public class SeatOrderService {
	/* 添加座位预约 */
	public String AddSeatOrder(SeatOrder seatOrder) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", seatOrder.getOrderId() + "");
		params.put("seatObj", seatOrder.getSeatObj() + "");
		params.put("orderDate", seatOrder.getOrderDate().toString());
		params.put("startTime", seatOrder.getStartTime());
		params.put("endTime", seatOrder.getEndTime());
		params.put("addTime", seatOrder.getAddTime());
		params.put("userObj", seatOrder.getUserObj());
		params.put("orderState", seatOrder.getOrderState());
		params.put("replyContent", seatOrder.getReplyContent());
		params.put("orderMemo", seatOrder.getOrderMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatOrderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询座位预约 */
	public List<SeatOrder> QuerySeatOrder(SeatOrder queryConditionSeatOrder) throws Exception {
		String urlString = HttpUtil.BASE_URL + "SeatOrderServlet?action=query";
		if(queryConditionSeatOrder != null) {
			urlString += "&seatObj=" + queryConditionSeatOrder.getSeatObj();
			if(queryConditionSeatOrder.getOrderDate() != null) {
				urlString += "&orderDate=" + URLEncoder.encode(queryConditionSeatOrder.getOrderDate().toString(), "UTF-8");
			}
			urlString += "&addTime=" + URLEncoder.encode(queryConditionSeatOrder.getAddTime(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionSeatOrder.getUserObj(), "UTF-8") + "";
			urlString += "&orderState=" + URLEncoder.encode(queryConditionSeatOrder.getOrderState(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		SeatOrderListHandler seatOrderListHander = new SeatOrderListHandler();
		xr.setContentHandler(seatOrderListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<SeatOrder> seatOrderList = seatOrderListHander.getSeatOrderList();
		return seatOrderList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<SeatOrder> seatOrderList = new ArrayList<SeatOrder>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SeatOrder seatOrder = new SeatOrder();
				seatOrder.setOrderId(object.getInt("orderId"));
				seatOrder.setSeatObj(object.getInt("seatObj"));
				seatOrder.setOrderDate(Timestamp.valueOf(object.getString("orderDate")));
				seatOrder.setStartTime(object.getString("startTime"));
				seatOrder.setEndTime(object.getString("endTime"));
				seatOrder.setAddTime(object.getString("addTime"));
				seatOrder.setUserObj(object.getString("userObj"));
				seatOrder.setOrderState(object.getString("orderState"));
				seatOrder.setReplyContent(object.getString("replyContent"));
				seatOrder.setOrderMemo(object.getString("orderMemo"));
				seatOrderList.add(seatOrder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seatOrderList;
	}

	/* 更新座位预约 */
	public String UpdateSeatOrder(SeatOrder seatOrder) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", seatOrder.getOrderId() + "");
		params.put("seatObj", seatOrder.getSeatObj() + "");
		params.put("orderDate", seatOrder.getOrderDate().toString());
		params.put("startTime", seatOrder.getStartTime());
		params.put("endTime", seatOrder.getEndTime());
		params.put("addTime", seatOrder.getAddTime());
		params.put("userObj", seatOrder.getUserObj());
		params.put("orderState", seatOrder.getOrderState());
		params.put("replyContent", seatOrder.getReplyContent());
		params.put("orderMemo", seatOrder.getOrderMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatOrderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除座位预约 */
	public String DeleteSeatOrder(int orderId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatOrderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "座位预约信息删除失败!";
		}
	}

	/* 根据预约id获取座位预约对象 */
	public SeatOrder GetSeatOrder(int orderId)  {
		List<SeatOrder> seatOrderList = new ArrayList<SeatOrder>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "SeatOrderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				SeatOrder seatOrder = new SeatOrder();
				seatOrder.setOrderId(object.getInt("orderId"));
				seatOrder.setSeatObj(object.getInt("seatObj"));
				seatOrder.setOrderDate(Timestamp.valueOf(object.getString("orderDate")));
				seatOrder.setStartTime(object.getString("startTime"));
				seatOrder.setEndTime(object.getString("endTime"));
				seatOrder.setAddTime(object.getString("addTime"));
				seatOrder.setUserObj(object.getString("userObj"));
				seatOrder.setOrderState(object.getString("orderState"));
				seatOrder.setReplyContent(object.getString("replyContent"));
				seatOrder.setOrderMemo(object.getString("orderMemo"));
				seatOrderList.add(seatOrder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = seatOrderList.size();
		if(size>0) return seatOrderList.get(0); 
		else return null; 
	}
}
