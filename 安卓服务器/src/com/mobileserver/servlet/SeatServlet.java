package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SeatDAO;
import com.mobileserver.domain.Seat;

import org.json.JSONStringer;

public class SeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������λҵ������*/
	private SeatDAO seatDAO = new SeatDAO();

	/*Ĭ�Ϲ��캯��*/
	public SeatServlet() {
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
			/*��ȡ��ѯ��λ�Ĳ�����Ϣ*/
			int roomObj = 0;
			if (request.getParameter("roomObj") != null)
				roomObj = Integer.parseInt(request.getParameter("roomObj"));
			String seatCode = request.getParameter("seatCode");
			seatCode = seatCode == null ? "" : new String(request.getParameter(
					"seatCode").getBytes("iso-8859-1"), "UTF-8");
			int seatStateObj = 0;
			if (request.getParameter("seatStateObj") != null)
				seatStateObj = Integer.parseInt(request.getParameter("seatStateObj"));

			/*����ҵ���߼���ִ����λ��ѯ*/
			List<Seat> seatList = seatDAO.QuerySeat(roomObj,seatCode,seatStateObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Seats>").append("\r\n");
			for (int i = 0; i < seatList.size(); i++) {
				sb.append("	<Seat>").append("\r\n")
				.append("		<seatId>")
				.append(seatList.get(i).getSeatId())
				.append("</seatId>").append("\r\n")
				.append("		<roomObj>")
				.append(seatList.get(i).getRoomObj())
				.append("</roomObj>").append("\r\n")
				.append("		<seatCode>")
				.append(seatList.get(i).getSeatCode())
				.append("</seatCode>").append("\r\n")
				.append("		<seatStateObj>")
				.append(seatList.get(i).getSeatStateObj())
				.append("</seatStateObj>").append("\r\n")
				.append("	</Seat>").append("\r\n");
			}
			sb.append("</Seats>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Seat seat: seatList) {
				  stringer.object();
			  stringer.key("seatId").value(seat.getSeatId());
			  stringer.key("roomObj").value(seat.getRoomObj());
			  stringer.key("seatCode").value(seat.getSeatCode());
			  stringer.key("seatStateObj").value(seat.getSeatStateObj());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����λ����ȡ��λ�������������浽�½�����λ���� */ 
			Seat seat = new Seat();
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			seat.setSeatId(seatId);
			int roomObj = Integer.parseInt(request.getParameter("roomObj"));
			seat.setRoomObj(roomObj);
			String seatCode = new String(request.getParameter("seatCode").getBytes("iso-8859-1"), "UTF-8");
			seat.setSeatCode(seatCode);
			int seatStateObj = Integer.parseInt(request.getParameter("seatStateObj"));
			seat.setSeatStateObj(seatStateObj);

			/* ����ҵ���ִ����Ӳ��� */
			String result = seatDAO.AddSeat(seat);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����λ����ȡ��λ����λid*/
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = seatDAO.DeleteSeat(seatId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������λ֮ǰ�ȸ���seatId��ѯĳ����λ*/
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			Seat seat = seatDAO.GetSeat(seatId);

			// �ͻ��˲�ѯ����λ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("seatId").value(seat.getSeatId());
			  stringer.key("roomObj").value(seat.getRoomObj());
			  stringer.key("seatCode").value(seat.getSeatCode());
			  stringer.key("seatStateObj").value(seat.getSeatStateObj());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������λ����ȡ��λ�������������浽�½�����λ���� */ 
			Seat seat = new Seat();
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			seat.setSeatId(seatId);
			int roomObj = Integer.parseInt(request.getParameter("roomObj"));
			seat.setRoomObj(roomObj);
			String seatCode = new String(request.getParameter("seatCode").getBytes("iso-8859-1"), "UTF-8");
			seat.setSeatCode(seatCode);
			int seatStateObj = Integer.parseInt(request.getParameter("seatStateObj"));
			seat.setSeatStateObj(seatStateObj);

			/* ����ҵ���ִ�и��²��� */
			String result = seatDAO.UpdateSeat(seat);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
