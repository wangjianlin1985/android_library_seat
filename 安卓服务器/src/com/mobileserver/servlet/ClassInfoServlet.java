package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.ClassInfoDAO;
import com.mobileserver.domain.ClassInfo;

import org.json.JSONStringer;

public class ClassInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����༶ҵ������*/
	private ClassInfoDAO classInfoDAO = new ClassInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public ClassInfoServlet() {
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
			/*��ȡ��ѯ�༶�Ĳ�����Ϣ*/
			String classNo = request.getParameter("classNo");
			classNo = classNo == null ? "" : new String(request.getParameter(
					"classNo").getBytes("iso-8859-1"), "UTF-8");
			String className = request.getParameter("className");
			className = className == null ? "" : new String(request.getParameter(
					"className").getBytes("iso-8859-1"), "UTF-8");
			Timestamp bornDate = null;
			if (request.getParameter("bornDate") != null)
				bornDate = Timestamp.valueOf(request.getParameter("bornDate"));

			/*����ҵ���߼���ִ�а༶��ѯ*/
			List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfo(classNo,className,bornDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<ClassInfos>").append("\r\n");
			for (int i = 0; i < classInfoList.size(); i++) {
				sb.append("	<ClassInfo>").append("\r\n")
				.append("		<classNo>")
				.append(classInfoList.get(i).getClassNo())
				.append("</classNo>").append("\r\n")
				.append("		<className>")
				.append(classInfoList.get(i).getClassName())
				.append("</className>").append("\r\n")
				.append("		<bornDate>")
				.append(classInfoList.get(i).getBornDate())
				.append("</bornDate>").append("\r\n")
				.append("		<mainTeacher>")
				.append(classInfoList.get(i).getMainTeacher())
				.append("</mainTeacher>").append("\r\n")
				.append("		<classMemo>")
				.append(classInfoList.get(i).getClassMemo())
				.append("</classMemo>").append("\r\n")
				.append("	</ClassInfo>").append("\r\n");
			}
			sb.append("</ClassInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(ClassInfo classInfo: classInfoList) {
				  stringer.object();
			  stringer.key("classNo").value(classInfo.getClassNo());
			  stringer.key("className").value(classInfo.getClassName());
			  stringer.key("bornDate").value(classInfo.getBornDate());
			  stringer.key("mainTeacher").value(classInfo.getMainTeacher());
			  stringer.key("classMemo").value(classInfo.getClassMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӱ༶����ȡ�༶�������������浽�½��İ༶���� */ 
			ClassInfo classInfo = new ClassInfo();
			String classNo = new String(request.getParameter("classNo").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassNo(classNo);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassName(className);
			Timestamp bornDate = Timestamp.valueOf(request.getParameter("bornDate"));
			classInfo.setBornDate(bornDate);
			String mainTeacher = new String(request.getParameter("mainTeacher").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setMainTeacher(mainTeacher);
			String classMemo = new String(request.getParameter("classMemo").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassMemo(classMemo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = classInfoDAO.AddClassInfo(classInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���༶����ȡ�༶�İ༶���*/
			String classNo = new String(request.getParameter("classNo").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = classInfoDAO.DeleteClassInfo(classNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���°༶֮ǰ�ȸ���classNo��ѯĳ���༶*/
			String classNo = new String(request.getParameter("classNo").getBytes("iso-8859-1"), "UTF-8");
			ClassInfo classInfo = classInfoDAO.GetClassInfo(classNo);

			// �ͻ��˲�ѯ�İ༶���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("classNo").value(classInfo.getClassNo());
			  stringer.key("className").value(classInfo.getClassName());
			  stringer.key("bornDate").value(classInfo.getBornDate());
			  stringer.key("mainTeacher").value(classInfo.getMainTeacher());
			  stringer.key("classMemo").value(classInfo.getClassMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���°༶����ȡ�༶�������������浽�½��İ༶���� */ 
			ClassInfo classInfo = new ClassInfo();
			String classNo = new String(request.getParameter("classNo").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassNo(classNo);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassName(className);
			Timestamp bornDate = Timestamp.valueOf(request.getParameter("bornDate"));
			classInfo.setBornDate(bornDate);
			String mainTeacher = new String(request.getParameter("mainTeacher").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setMainTeacher(mainTeacher);
			String classMemo = new String(request.getParameter("classMemo").getBytes("iso-8859-1"), "UTF-8");
			classInfo.setClassMemo(classMemo);

			/* ����ҵ���ִ�и��²��� */
			String result = classInfoDAO.UpdateClassInfo(classInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
