package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Jc;
import com.mobileclient.util.HttpUtil;

/*奖惩管理业务逻辑层*/
public class JcService {
	/* 添加奖惩 */
	public String AddJc(Jc jc) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jcId", jc.getJcId() + "");
		params.put("jcType", jc.getJcType());
		params.put("title", jc.getTitle());
		params.put("content", jc.getContent());
		params.put("userObj", jc.getUserObj());
		params.put("creditScore", jc.getCreditScore() + "");
		params.put("jcTime", jc.getJcTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JcServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询奖惩 */
	public List<Jc> QueryJc(Jc queryConditionJc) throws Exception {
		String urlString = HttpUtil.BASE_URL + "JcServlet?action=query";
		if(queryConditionJc != null) {
			urlString += "&jcType=" + URLEncoder.encode(queryConditionJc.getJcType(), "UTF-8") + "";
			urlString += "&title=" + URLEncoder.encode(queryConditionJc.getTitle(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionJc.getUserObj(), "UTF-8") + "";
			urlString += "&jcTime=" + URLEncoder.encode(queryConditionJc.getJcTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		JcListHandler jcListHander = new JcListHandler();
		xr.setContentHandler(jcListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Jc> jcList = jcListHander.getJcList();
		return jcList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Jc> jcList = new ArrayList<Jc>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Jc jc = new Jc();
				jc.setJcId(object.getInt("jcId"));
				jc.setJcType(object.getString("jcType"));
				jc.setTitle(object.getString("title"));
				jc.setContent(object.getString("content"));
				jc.setUserObj(object.getString("userObj"));
				jc.setCreditScore((float) object.getDouble("creditScore"));
				jc.setJcTime(object.getString("jcTime"));
				jcList.add(jc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jcList;
	}

	/* 更新奖惩 */
	public String UpdateJc(Jc jc) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jcId", jc.getJcId() + "");
		params.put("jcType", jc.getJcType());
		params.put("title", jc.getTitle());
		params.put("content", jc.getContent());
		params.put("userObj", jc.getUserObj());
		params.put("creditScore", jc.getCreditScore() + "");
		params.put("jcTime", jc.getJcTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JcServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除奖惩 */
	public String DeleteJc(int jcId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jcId", jcId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JcServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "奖惩信息删除失败!";
		}
	}

	/* 根据奖惩id获取奖惩对象 */
	public Jc GetJc(int jcId)  {
		List<Jc> jcList = new ArrayList<Jc>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jcId", jcId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JcServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Jc jc = new Jc();
				jc.setJcId(object.getInt("jcId"));
				jc.setJcType(object.getString("jcType"));
				jc.setTitle(object.getString("title"));
				jc.setContent(object.getString("content"));
				jc.setUserObj(object.getString("userObj"));
				jc.setCreditScore((float) object.getDouble("creditScore"));
				jc.setJcTime(object.getString("jcTime"));
				jcList.add(jc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = jcList.size();
		if(size>0) return jcList.get(0); 
		else return null; 
	}
}
