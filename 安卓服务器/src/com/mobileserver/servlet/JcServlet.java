package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.JcDAO;
import com.mobileserver.domain.Jc;

import org.json.JSONStringer;

public class JcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���콱��ҵ������*/
	private JcDAO jcDAO = new JcDAO();

	/*Ĭ�Ϲ��캯��*/
	public JcServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ���͵Ĳ�����Ϣ*/
			String jcType = request.getParameter("jcType");
			jcType = jcType == null ? "" : new String(request.getParameter(
					"jcType").getBytes("iso-8859-1"), "UTF-8");
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String jcTime = request.getParameter("jcTime");
			jcTime = jcTime == null ? "" : new String(request.getParameter(
					"jcTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�н��Ͳ�ѯ*/
			List<Jc> jcList = jcDAO.QueryJc(jcType,title,userObj,jcTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Jcs>").append("\r\n");
			for (int i = 0; i < jcList.size(); i++) {
				sb.append("	<Jc>").append("\r\n")
				.append("		<jcId>")
				.append(jcList.get(i).getJcId())
				.append("</jcId>").append("\r\n")
				.append("		<jcType>")
				.append(jcList.get(i).getJcType())
				.append("</jcType>").append("\r\n")
				.append("		<title>")
				.append(jcList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<content>")
				.append(jcList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<userObj>")
				.append(jcList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<creditScore>")
				.append(jcList.get(i).getCreditScore())
				.append("</creditScore>").append("\r\n")
				.append("		<jcTime>")
				.append(jcList.get(i).getJcTime())
				.append("</jcTime>").append("\r\n")
				.append("	</Jc>").append("\r\n");
			}
			sb.append("</Jcs>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Jc jc: jcList) {
				  stringer.object();
			  stringer.key("jcId").value(jc.getJcId());
			  stringer.key("jcType").value(jc.getJcType());
			  stringer.key("title").value(jc.getTitle());
			  stringer.key("content").value(jc.getContent());
			  stringer.key("userObj").value(jc.getUserObj());
			  stringer.key("creditScore").value(jc.getCreditScore());
			  stringer.key("jcTime").value(jc.getJcTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӽ��ͣ���ȡ���Ͳ������������浽�½��Ľ��Ͷ��� */ 
			Jc jc = new Jc();
			int jcId = Integer.parseInt(request.getParameter("jcId"));
			jc.setJcId(jcId);
			String jcType = new String(request.getParameter("jcType").getBytes("iso-8859-1"), "UTF-8");
			jc.setJcType(jcType);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			jc.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			jc.setContent(content);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			jc.setUserObj(userObj);
			float creditScore = Float.parseFloat(request.getParameter("creditScore"));
			jc.setCreditScore(creditScore);
			String jcTime = new String(request.getParameter("jcTime").getBytes("iso-8859-1"), "UTF-8");
			jc.setJcTime(jcTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = jcDAO.AddJc(jc);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����ͣ���ȡ���͵Ľ���id*/
			int jcId = Integer.parseInt(request.getParameter("jcId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = jcDAO.DeleteJc(jcId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���½���֮ǰ�ȸ���jcId��ѯĳ������*/
			int jcId = Integer.parseInt(request.getParameter("jcId"));
			Jc jc = jcDAO.GetJc(jcId);

			// �ͻ��˲�ѯ�Ľ��Ͷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("jcId").value(jc.getJcId());
			  stringer.key("jcType").value(jc.getJcType());
			  stringer.key("title").value(jc.getTitle());
			  stringer.key("content").value(jc.getContent());
			  stringer.key("userObj").value(jc.getUserObj());
			  stringer.key("creditScore").value(jc.getCreditScore());
			  stringer.key("jcTime").value(jc.getJcTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���½��ͣ���ȡ���Ͳ������������浽�½��Ľ��Ͷ��� */ 
			Jc jc = new Jc();
			int jcId = Integer.parseInt(request.getParameter("jcId"));
			jc.setJcId(jcId);
			String jcType = new String(request.getParameter("jcType").getBytes("iso-8859-1"), "UTF-8");
			jc.setJcType(jcType);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			jc.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			jc.setContent(content);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			jc.setUserObj(userObj);
			float creditScore = Float.parseFloat(request.getParameter("creditScore"));
			jc.setCreditScore(creditScore);
			String jcTime = new String(request.getParameter("jcTime").getBytes("iso-8859-1"), "UTF-8");
			jc.setJcTime(jcTime);

			/* ����ҵ���ִ�и��²��� */
			String result = jcDAO.UpdateJc(jc);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
