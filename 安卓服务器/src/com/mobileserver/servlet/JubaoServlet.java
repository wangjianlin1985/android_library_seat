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

	/*����ٱ�ҵ������*/
	private JubaoDAO jubaoDAO = new JubaoDAO();

	/*Ĭ�Ϲ��캯��*/
	public JubaoServlet() {
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
			/*��ȡ��ѯ�ٱ��Ĳ�����Ϣ*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String jubaoTime = request.getParameter("jubaoTime");
			jubaoTime = jubaoTime == null ? "" : new String(request.getParameter(
					"jubaoTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�оٱ���ѯ*/
			List<Jubao> jubaoList = jubaoDAO.QueryJubao(title,userObj,jubaoTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��Ӿٱ�����ȡ�ٱ��������������浽�½��ľٱ����� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = jubaoDAO.AddJubao(jubao);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���ٱ�����ȡ�ٱ��ľٱ�id*/
			int jubaoId = Integer.parseInt(request.getParameter("jubaoId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = jubaoDAO.DeleteJubao(jubaoId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¾ٱ�֮ǰ�ȸ���jubaoId��ѯĳ���ٱ�*/
			int jubaoId = Integer.parseInt(request.getParameter("jubaoId"));
			Jubao jubao = jubaoDAO.GetJubao(jubaoId);

			// �ͻ��˲�ѯ�ľٱ����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¾ٱ�����ȡ�ٱ��������������浽�½��ľٱ����� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = jubaoDAO.UpdateJubao(jubao);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
