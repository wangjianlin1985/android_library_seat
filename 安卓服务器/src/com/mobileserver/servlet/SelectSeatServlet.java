package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SelectSeatDAO;
import com.mobileserver.domain.SelectSeat;

import org.json.JSONStringer;

public class SelectSeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ѡ��ҵ������*/
	private SelectSeatDAO selectSeatDAO = new SelectSeatDAO();

	/*Ĭ�Ϲ��캯��*/
	public SelectSeatServlet() {
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
			/*��ȡ��ѯѡ���Ĳ�����Ϣ*/
			int seatObj = 0;
			if (request.getParameter("seatObj") != null)
				seatObj = Integer.parseInt(request.getParameter("seatObj"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String startTime = request.getParameter("startTime");
			startTime = startTime == null ? "" : new String(request.getParameter(
					"startTime").getBytes("iso-8859-1"), "UTF-8");
			String endTime = request.getParameter("endTime");
			endTime = endTime == null ? "" : new String(request.getParameter(
					"endTime").getBytes("iso-8859-1"), "UTF-8");
			String seatState = request.getParameter("seatState");
			seatState = seatState == null ? "" : new String(request.getParameter(
					"seatState").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ��ѡ����ѯ*/
			List<SelectSeat> selectSeatList = selectSeatDAO.QuerySelectSeat(seatObj,userObj,startTime,endTime,seatState);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SelectSeats>").append("\r\n");
			for (int i = 0; i < selectSeatList.size(); i++) {
				sb.append("	<SelectSeat>").append("\r\n")
				.append("		<selectId>")
				.append(selectSeatList.get(i).getSelectId())
				.append("</selectId>").append("\r\n")
				.append("		<seatObj>")
				.append(selectSeatList.get(i).getSeatObj())
				.append("</seatObj>").append("\r\n")
				.append("		<userObj>")
				.append(selectSeatList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<startTime>")
				.append(selectSeatList.get(i).getStartTime())
				.append("</startTime>").append("\r\n")
				.append("		<endTime>")
				.append(selectSeatList.get(i).getEndTime())
				.append("</endTime>").append("\r\n")
				.append("		<seatState>")
				.append(selectSeatList.get(i).getSeatState())
				.append("</seatState>").append("\r\n")
				.append("	</SelectSeat>").append("\r\n");
			}
			sb.append("</SelectSeats>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SelectSeat selectSeat: selectSeatList) {
				  stringer.object();
			  stringer.key("selectId").value(selectSeat.getSelectId());
			  stringer.key("seatObj").value(selectSeat.getSeatObj());
			  stringer.key("userObj").value(selectSeat.getUserObj());
			  stringer.key("startTime").value(selectSeat.getStartTime());
			  stringer.key("endTime").value(selectSeat.getEndTime());
			  stringer.key("seatState").value(selectSeat.getSeatState());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ѡ������ȡѡ���������������浽�½���ѡ������ */ 
			SelectSeat selectSeat = new SelectSeat();
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			selectSeat.setSelectId(selectId);
			int seatObj = Integer.parseInt(request.getParameter("seatObj"));
			selectSeat.setSeatObj(seatObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			selectSeat.setUserObj(userObj);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			selectSeat.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			selectSeat.setEndTime(endTime);
			String seatState = new String(request.getParameter("seatState").getBytes("iso-8859-1"), "UTF-8");
			selectSeat.setSeatState(seatState);

			/* ����ҵ���ִ����Ӳ��� */
			String result = selectSeatDAO.AddSelectSeat(selectSeat);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ѡ������ȡѡ����ѡ��id*/
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = selectSeatDAO.DeleteSelectSeat(selectId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ѡ��֮ǰ�ȸ���selectId��ѯĳ��ѡ��*/
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			SelectSeat selectSeat = selectSeatDAO.GetSelectSeat(selectId);

			// �ͻ��˲�ѯ��ѡ�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("selectId").value(selectSeat.getSelectId());
			  stringer.key("seatObj").value(selectSeat.getSeatObj());
			  stringer.key("userObj").value(selectSeat.getUserObj());
			  stringer.key("startTime").value(selectSeat.getStartTime());
			  stringer.key("endTime").value(selectSeat.getEndTime());
			  stringer.key("seatState").value(selectSeat.getSeatState());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ѡ������ȡѡ���������������浽�½���ѡ������ */ 
			SelectSeat selectSeat = new SelectSeat();
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			selectSeat.setSelectId(selectId);
			int seatObj = Integer.parseInt(request.getParameter("seatObj"));
			selectSeat.setSeatObj(seatObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			selectSeat.setUserObj(userObj);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			selectSeat.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			selectSeat.setEndTime(endTime);
			String seatState = new String(request.getParameter("seatState").getBytes("iso-8859-1"), "UTF-8");
			selectSeat.setSeatState(seatState);

			/* ����ҵ���ִ�и��²��� */
			String result = selectSeatDAO.UpdateSelectSeat(selectSeat);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
