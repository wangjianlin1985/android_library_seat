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

	/*�����û�����ҵ������*/
	private UserTypeDAO userTypeDAO = new UserTypeDAO();

	/*Ĭ�Ϲ��캯��*/
	public UserTypeServlet() {
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
			/*��ȡ��ѯ�û����͵Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ���û����Ͳ�ѯ*/
			List<UserType> userTypeList = userTypeDAO.QueryUserType();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ����û����ͣ���ȡ�û����Ͳ������������浽�½����û����Ͷ��� */ 
			UserType userType = new UserType();
			int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
			userType.setUserTypeId(userTypeId);
			String userTypeName = new String(request.getParameter("userTypeName").getBytes("iso-8859-1"), "UTF-8");
			userType.setUserTypeName(userTypeName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = userTypeDAO.AddUserType(userType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���û����ͣ���ȡ�û����͵��û�����id*/
			int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = userTypeDAO.DeleteUserType(userTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����û�����֮ǰ�ȸ���userTypeId��ѯĳ���û�����*/
			int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
			UserType userType = userTypeDAO.GetUserType(userTypeId);

			// �ͻ��˲�ѯ���û����Ͷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �����û����ͣ���ȡ�û����Ͳ������������浽�½����û����Ͷ��� */ 
			UserType userType = new UserType();
			int userTypeId = Integer.parseInt(request.getParameter("userTypeId"));
			userType.setUserTypeId(userTypeId);
			String userTypeName = new String(request.getParameter("userTypeName").getBytes("iso-8859-1"), "UTF-8");
			userType.setUserTypeName(userTypeName);

			/* ����ҵ���ִ�и��²��� */
			String result = userTypeDAO.UpdateUserType(userType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
