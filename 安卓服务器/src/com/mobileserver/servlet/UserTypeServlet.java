package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.UserTypeDAO;
import com.mobileserver.domain.UserType;

import org.json.JSONStringer;

public class UserTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造用户类型业务层对象*/
	private UserTypeDAO userTypeDAO = new UserTypeDAO();

	/*默认构造函数*/
	public UserTypeServlet() {
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
			/*获取查询用户类型的参数信息*/

			/*调用业务逻辑层执行用户类型查询*/
			List<UserType> userTypeList = userTypeDAO.QueryUserType();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<UserTypes>").append("\r\n");
			for (int i = 0; i < userTypeList.size(); i++) {
				sb.append("	<UserType>").append("\r\n")
				.append("		<userTypeId>")
				.append(userTypeList.get(i).getUserTypeId())
				.append("</userTypeId>").append("\r\n")
				.append("		<userTypeName>")
				.append(userTypeList.get(i).getUserTypeName())
				.append("</userTypeName>").append("\r\n")
				.append("	</UserType>").append("\r\n");
			}
			sb.append("</UserTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(UserType userType: userTypeList) {
				  stringer.object();
			  stringer.key("userTypeId").value(userType.getUserTypeId());
			  stringer.key("userTypeName").value(userType.getUserTypeName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加用户类型：获取用户类型参数，参数保存到新建的用户类型对象 */ 
			UserType userType = new UserType();
			int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
			userType.setUserTypeId(userTypeId);
			String userTypeName = new String(request.getParameter("userTypeName").getBytes("iso-8859-1"), "UTF-8");
			userType.setUserTypeName(userTypeName);

			/* 调用业务层执行添加操作 */
			String result = userTypeDAO.AddUserType(userType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除用户类型：获取用户类型的用户类型id*/
			int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = userTypeDAO.DeleteUserType(userTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新用户类型之前先根据userTypeId查询某个用户类型*/
			int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
			UserType userType = userTypeDAO.GetUserType(userTypeId);

			// 客户端查询的用户类型对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("userTypeId").value(userType.getUserTypeId());
			  stringer.key("userTypeName").value(userType.getUserTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新用户类型：获取用户类型参数，参数保存到新建的用户类型对象 */ 
			UserType userType = new UserType();
			int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
			userType.setUserTypeId(userTypeId);
			String userTypeName = new String(request.getParameter("userTypeName").getBytes("iso-8859-1"), "UTF-8");
			userType.setUserTypeName(userTypeName);

			/* 调用业务层执行更新操作 */
			String result = userTypeDAO.UpdateUserType(userType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
