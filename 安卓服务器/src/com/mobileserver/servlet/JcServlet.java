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

	/*构造奖惩业务层对象*/
	private JcDAO jcDAO = new JcDAO();

	/*默认构造函数*/
	public JcServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询奖惩的参数信息*/
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

			/*调用业务逻辑层执行奖惩查询*/
			List<Jc> jcList = jcDAO.QueryJc(jcType,title,userObj,jcTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加奖惩：获取奖惩参数，参数保存到新建的奖惩对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = jcDAO.AddJc(jc);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除奖惩：获取奖惩的奖惩id*/
			int jcId = Integer.parseInt(request.getParameter("jcId"));
			/*调用业务逻辑层执行删除操作*/
			String result = jcDAO.DeleteJc(jcId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新奖惩之前先根据jcId查询某个奖惩*/
			int jcId = Integer.parseInt(request.getParameter("jcId"));
			Jc jc = jcDAO.GetJc(jcId);

			// 客户端查询的奖惩对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新奖惩：获取奖惩参数，参数保存到新建的奖惩对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = jcDAO.UpdateJc(jc);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
