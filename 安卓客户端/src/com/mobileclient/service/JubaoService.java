package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Jubao;
import com.mobileclient.util.HttpUtil;

/*举报管理业务逻辑层*/
public class JubaoService {
	/* 添加举报 */
	public String AddJubao(Jubao jubao) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jubaoId", jubao.getJubaoId() + "");
		params.put("title", jubao.getTitle());
		params.put("content", jubao.getContent());
		params.put("userObj", jubao.getUserObj());
		params.put("jubaoTime", jubao.getJubaoTime());
		params.put("replyContent", jubao.getReplyContent());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JubaoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询举报 */
	public List<Jubao> QueryJubao(Jubao queryConditionJubao) throws Exception {
		String urlString = HttpUtil.BASE_URL + "JubaoServlet?action=query";
		if(queryConditionJubao != null) {
			urlString += "&title=" + URLEncoder.encode(queryConditionJubao.getTitle(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionJubao.getUserObj(), "UTF-8") + "";
			urlString += "&jubaoTime=" + URLEncoder.encode(queryConditionJubao.getJubaoTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		JubaoListHandler jubaoListHander = new JubaoListHandler();
		xr.setContentHandler(jubaoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Jubao> jubaoList = jubaoListHander.getJubaoList();
		return jubaoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Jubao> jubaoList = new ArrayList<Jubao>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Jubao jubao = new Jubao();
				jubao.setJubaoId(object.getInt("jubaoId"));
				jubao.setTitle(object.getString("title"));
				jubao.setContent(object.getString("content"));
				jubao.setUserObj(object.getString("userObj"));
				jubao.setJubaoTime(object.getString("jubaoTime"));
				jubao.setReplyContent(object.getString("replyContent"));
				jubaoList.add(jubao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jubaoList;
	}

	/* 更新举报 */
	public String UpdateJubao(Jubao jubao) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jubaoId", jubao.getJubaoId() + "");
		params.put("title", jubao.getTitle());
		params.put("content", jubao.getContent());
		params.put("userObj", jubao.getUserObj());
		params.put("jubaoTime", jubao.getJubaoTime());
		params.put("replyContent", jubao.getReplyContent());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JubaoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除举报 */
	public String DeleteJubao(int jubaoId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jubaoId", jubaoId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JubaoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "举报信息删除失败!";
		}
	}

	/* 根据举报id获取举报对象 */
	public Jubao GetJubao(int jubaoId)  {
		List<Jubao> jubaoList = new ArrayList<Jubao>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jubaoId", jubaoId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JubaoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Jubao jubao = new Jubao();
				jubao.setJubaoId(object.getInt("jubaoId"));
				jubao.setTitle(object.getString("title"));
				jubao.setContent(object.getString("content"));
				jubao.setUserObj(object.getString("userObj"));
				jubao.setJubaoTime(object.getString("jubaoTime"));
				jubao.setReplyContent(object.getString("replyContent"));
				jubaoList.add(jubao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = jubaoList.size();
		if(size>0) return jubaoList.get(0); 
		else return null; 
	}
}
