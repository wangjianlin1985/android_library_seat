package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SeatStateDAO;
import com.mobileserver.domain.SeatState;

import org.json.JSONStringer;

public class SeatStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������λ״̬ҵ������*/
	private SeatStateDAO seatStateDAO = new SeatStateDAO();

	/*Ĭ�Ϲ��캯��*/
	public SeatStateServlet() {
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
			/*��ȡ��ѯ��λ״̬�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ����λ״̬��ѯ*/
			List<SeatState> seatStateList = seatStateDAO.QuerySeatState();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SeatStates>").append("\r\n");
			for (int i = 0; i < seatStateList.size(); i++) {
				sb.append("	<SeatState>").append("\r\n")
				.append("		<stateId>")
				.append(seatStateList.get(i).getStateId())
				.append("</stateId>").append("\r\n")
				.append("		<stateName>")
				.append(seatStateList.get(i).getStateName())
				.append("</stateName>").append("\r\n")
				.append("	</SeatState>").append("\r\n");
			}
			sb.append("</SeatStates>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SeatState seatState: seatStateList) {
				  stringer.object();
			  stringer.key("stateId").value(seatState.getStateId());
			  stringer.key("stateName").value(seatState.getStateName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����λ״̬����ȡ��λ״̬�������������浽�½�����λ״̬���� */ 
			SeatState seatState = new SeatState();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			seatState.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			seatState.setStateName(stateName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = seatStateDAO.AddSeatState(seatState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����λ״̬����ȡ��λ״̬��״̬id*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = seatStateDAO.DeleteSeatState(stateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������λ״̬֮ǰ�ȸ���stateId��ѯĳ����λ״̬*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			SeatState seatState = seatStateDAO.GetSeatState(stateId);

			// �ͻ��˲�ѯ����λ״̬���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("stateId").value(seatState.getStateId());
			  stringer.key("stateName").value(seatState.getStateName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������λ״̬����ȡ��λ״̬�������������浽�½�����λ״̬���� */ 
			SeatState seatState = new SeatState();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			seatState.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			seatState.setStateName(stateName);

			/* ����ҵ���ִ�и��²��� */
			String result = seatStateDAO.UpdateSeatState(seatState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
