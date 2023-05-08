package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.UserInfoDAO;
import com.mobileserver.domain.UserInfo;

import org.json.JSONStringer;

public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造用户业务层对象*/
	private UserInfoDAO userInfoDAO = new UserInfoDAO();

	/*默认构造函数*/
	public UserInfoServlet() {
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
			/*获取查询用户的参数信息*/
			String user_name = request.getParameter("user_name");
			user_name = user_name == null ? "" : new String(request.getParameter(
					"user_name").getBytes("iso-8859-1"), "UTF-8");
			int userTypeObj = 0;
			if (request.getParameter("userTypeObj") != null)
				userTypeObj = Integer.parseInt(request.getParameter("userTypeObj"));
			String classObj = "";
			if (request.getParameter("classObj") != null)
				classObj = request.getParameter("classObj");
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			Timestamp birthDate = null;
			if (request.getParameter("birthDate") != null)
				birthDate = Timestamp.valueOf(request.getParameter("birthDate"));
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");
			String blackFlag = request.getParameter("blackFlag");
			blackFlag = blackFlag == null ? "" : new String(request.getParameter(
					"blackFlag").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行用户查询*/
			List<UserInfo> userInfoList = userInfoDAO.QueryUserInfo(user_name,userTypeObj,classObj,name,birthDate,telephone,blackFlag);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<UserInfos>").append("\r\n");
			for (int i = 0; i < userInfoList.size(); i++) {
				sb.append("	<UserInfo>").append("\r\n")
				.append("		<user_name>")
				.append(userInfoList.get(i).getUser_name())
				.append("</user_name>").append("\r\n")
				.append("		<password>")
				.append(userInfoList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<userTypeObj>")
				.append(userInfoList.get(i).getUserTypeObj())
				.append("</userTypeObj>").append("\r\n")
				.append("		<classObj>")
				.append(userInfoList.get(i).getClassObj())
				.append("</classObj>").append("\r\n")
				.append("		<name>")
				.append(userInfoList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<gender>")
				.append(userInfoList.get(i).getGender())
				.append("</gender>").append("\r\n")
				.append("		<birthDate>")
				.append(userInfoList.get(i).getBirthDate())
				.append("</birthDate>").append("\r\n")
				.append("		<userPhoto>")
				.append(userInfoList.get(i).getUserPhoto())
				.append("</userPhoto>").append("\r\n")
				.append("		<telephone>")
				.append(userInfoList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<email>")
				.append(userInfoList.get(i).getEmail())
				.append("</email>").append("\r\n")
				.append("		<address>")
				.append(userInfoList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("		<blackFlag>")
				.append(userInfoList.get(i).getBlackFlag())
				.append("</blackFlag>").append("\r\n")
				.append("		<creditScore>")
				.append(userInfoList.get(i).getCreditScore())
				.append("</creditScore>").append("\r\n")
				.append("		<regTime>")
				.append(userInfoList.get(i).getRegTime())
				.append("</regTime>").append("\r\n")
				.append("	</UserInfo>").append("\r\n");
			}
			sb.append("</UserInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(UserInfo userInfo: userInfoList) {
				  stringer.object();
			  stringer.key("user_name").value(userInfo.getUser_name());
			  stringer.key("password").value(userInfo.getPassword());
			  stringer.key("userTypeObj").value(userInfo.getUserTypeObj());
			  stringer.key("classObj").value(userInfo.getClassObj());
			  stringer.key("name").value(userInfo.getName());
			  stringer.key("gender").value(userInfo.getGender());
			  stringer.key("birthDate").value(userInfo.getBirthDate());
			  stringer.key("userPhoto").value(userInfo.getUserPhoto());
			  stringer.key("telephone").value(userInfo.getTelephone());
			  stringer.key("email").value(userInfo.getEmail());
			  stringer.key("address").value(userInfo.getAddress());
			  stringer.key("blackFlag").value(userInfo.getBlackFlag());
			  stringer.key("creditScore").value(userInfo.getCreditScore());
			  stringer.key("regTime").value(userInfo.getRegTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加用户：获取用户参数，参数保存到新建的用户对象 */ 
			UserInfo userInfo = new UserInfo();
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUser_name(user_name);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setPassword(password);
			int userTypeObj = Integer.parseInt(request.getParameter("userTypeObj"));
			userInfo.setUserTypeObj(userTypeObj);
			String classObj = new String(request.getParameter("classObj").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setClassObj(classObj);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setName(name);
			String gender = new String(request.getParameter("gender").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setGender(gender);
			Timestamp birthDate = Timestamp.valueOf(request.getParameter("birthDate"));
			userInfo.setBirthDate(birthDate);
			String userPhoto = new String(request.getParameter("userPhoto").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUserPhoto(userPhoto);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setEmail(email);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setAddress(address);
			String blackFlag = new String(request.getParameter("blackFlag").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setBlackFlag(blackFlag);
			float creditScore = Float.parseFloat(request.getParameter("creditScore"));
			userInfo.setCreditScore(creditScore);
			String regTime = new String(request.getParameter("regTime").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setRegTime(regTime);

			/* 调用业务层执行添加操作 */
			String result = userInfoDAO.AddUserInfo(userInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除用户：获取用户的用户名*/
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = userInfoDAO.DeleteUserInfo(user_name);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新用户之前先根据user_name查询某个用户*/
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			UserInfo userInfo = userInfoDAO.GetUserInfo(user_name);

			// 客户端查询的用户对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("user_name").value(userInfo.getUser_name());
			  stringer.key("password").value(userInfo.getPassword());
			  stringer.key("userTypeObj").value(userInfo.getUserTypeObj());
			  stringer.key("classObj").value(userInfo.getClassObj());
			  stringer.key("name").value(userInfo.getName());
			  stringer.key("gender").value(userInfo.getGender());
			  stringer.key("birthDate").value(userInfo.getBirthDate());
			  stringer.key("userPhoto").value(userInfo.getUserPhoto());
			  stringer.key("telephone").value(userInfo.getTelephone());
			  stringer.key("email").value(userInfo.getEmail());
			  stringer.key("address").value(userInfo.getAddress());
			  stringer.key("blackFlag").value(userInfo.getBlackFlag());
			  stringer.key("creditScore").value(userInfo.getCreditScore());
			  stringer.key("regTime").value(userInfo.getRegTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新用户：获取用户参数，参数保存到新建的用户对象 */ 
			UserInfo userInfo = new UserInfo();
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUser_name(user_name);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setPassword(password);
			int userTypeObj = Integer.parseInt(request.getParameter("userTypeObj"));
			userInfo.setUserTypeObj(userTypeObj);
			String classObj = new String(request.getParameter("classObj").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setClassObj(classObj);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setName(name);
			String gender = new String(request.getParameter("gender").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setGender(gender);
			Timestamp birthDate = Timestamp.valueOf(request.getParameter("birthDate"));
			userInfo.setBirthDate(birthDate);
			String userPhoto = new String(request.getParameter("userPhoto").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUserPhoto(userPhoto);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setTelephone(telephone);
			String email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setEmail(email);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setAddress(address);
			String blackFlag = new String(request.getParameter("blackFlag").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setBlackFlag(blackFlag);
			float creditScore = Float.parseFloat(request.getParameter("creditScore"));
			userInfo.setCreditScore(creditScore);
			String regTime = new String(request.getParameter("regTime").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setRegTime(regTime);

			/* 调用业务层执行更新操作 */
			String result = userInfoDAO.UpdateUserInfo(userInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
