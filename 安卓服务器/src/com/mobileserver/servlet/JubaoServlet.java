package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.JubaoDAO;
import com.mobileserver.domain.Jubao;

import org.json.JSONStringer;

public class JubaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造举报业务层对象*/
	private JubaoDAO jubaoDAO = new JubaoDAO();

	/*默认构造函数*/
	public JubaoServlet() {
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
			/*获取查询举报的参数信息*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String jubaoTime = request.getParameter("jubaoTime");
			jubaoTime = jubaoTime == null ? "" : new String(request.getParameter(
					"jubaoTime").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行举报查询*/
			List<Jubao> jubaoList = jubaoDAO.QueryJubao(title,userObj,jubaoTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Jubaos>").append("\r\n");
			for (int i = 0; i < jubaoList.size(); i++) {
				sb.append("	<Jubao>").append("\r\n")
				.append("		<jubaoId>")
				.append(jubaoList.get(i).getJubaoId())
				.append("</jubaoId>").append("\r\n")
				.append("		<title>")
				.append(jubaoList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<content>")
				.append(jubaoList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<userObj>")
				.append(jubaoList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<jubaoTime>")
				.append(jubaoList.get(i).getJubaoTime())
				.append("</jubaoTime>").append("\r\n")
				.append("		<replyContent>")
				.append(jubaoList.get(i).getReplyContent())
				.append("</replyContent>").append("\r\n")
				.append("	</Jubao>").append("\r\n");
			}
			sb.append("</Jubaos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Jubao jubao: jubaoList) {
				  stringer.object();
			  stringer.key("jubaoId").value(jubao.getJubaoId());
			  stringer.key("title").value(jubao.getTitle());
			  stringer.key("content").value(jubao.getContent());
			  stringer.key("userObj").value(jubao.getUserObj());
			  stringer.key("jubaoTime").value(jubao.getJubaoTime());
			  stringer.key("replyContent").value(jubao.getReplyContent());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加举报：获取举报参数，参数保存到新建的举报对象 */ 
			Jubao jubao = new Jubao();
			int jubaoId = Integer.parseInt(request.getParameter("jubaoId"));
			jubao.setJubaoId(jubaoId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			jubao.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			jubao.setContent(content);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			jubao.setUserObj(userObj);
			String jubaoTime = new String(request.getParameter("jubaoTime").getBytes("iso-8859-1"), "UTF-8");
			jubao.setJubaoTime(jubaoTime);
			String replyContent = new String(request.getParameter("replyContent").getBytes("iso-8859-1"), "UTF-8");
			jubao.setReplyContent(replyContent);

			/* 调用业务层执行添加操作 */
			String result = jubaoDAO.AddJubao(jubao);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除举报：获取举报的举报id*/
			int jubaoId = Integer.parseInt(request.getParameter("jubaoId"));
			/*调用业务逻辑层执行删除操作*/
			String result = jubaoDAO.DeleteJubao(jubaoId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新举报之前先根据jubaoId查询某个举报*/
			int jubaoId = Integer.parseInt(request.getParameter("jubaoId"));
			Jubao jubao = jubaoDAO.GetJubao(jubaoId);

			// 客户端查询的举报对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("jubaoId").value(jubao.getJubaoId());
			  stringer.key("title").value(jubao.getTitle());
			  stringer.key("content").value(jubao.getContent());
			  stringer.key("userObj").value(jubao.getUserObj());
			  stringer.key("jubaoTime").value(jubao.getJubaoTime());
			  stringer.key("replyContent").value(jubao.getReplyContent());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新举报：获取举报参数，参数保存到新建的举报对象 */ 
			Jubao jubao = new Jubao();
			int jubaoId = Integer.parseInt(request.getParameter("jubaoId"));
			jubao.setJubaoId(jubaoId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			jubao.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			jubao.setContent(content);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			jubao.setUserObj(userObj);
			String jubaoTime = new String(request.getParameter("jubaoTime").getBytes("iso-8859-1"), "UTF-8");
			jubao.setJubaoTime(jubaoTime);
			String replyContent = new String(request.getParameter("replyContent").getBytes("iso-8859-1"), "UTF-8");
			jubao.setReplyContent(replyContent);

			/* 调用业务层执行更新操作 */
			String result = jubaoDAO.UpdateJubao(jubao);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
