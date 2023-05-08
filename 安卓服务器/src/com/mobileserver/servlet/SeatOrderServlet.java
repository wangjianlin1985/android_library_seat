package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SeatOrderDAO;
import com.mobileserver.domain.SeatOrder;

import org.json.JSONStringer;

public class SeatOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*������λԤԼҵ������*/
	private SeatOrderDAO seatOrderDAO = new SeatOrderDAO();

	/*Ĭ�Ϲ��캯��*/
	public SeatOrderServlet() {
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
			/*��ȡ��ѯ��λԤԼ�Ĳ�����Ϣ*/
			int seatObj = 0;
			if (request.getParameter("seatObj") != null)
				seatObj = Integer.parseInt(request.getParameter("seatObj"));
			Timestamp orderDate = null;
			if (request.getParameter("orderDate") != null)
				orderDate = Timestamp.valueOf(request.getParameter("orderDate"));
			String addTime = request.getParameter("addTime");
			addTime = addTime == null ? "" : new String(request.getParameter(
					"addTime").getBytes("iso-8859-1"), "UTF-8");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String orderState = request.getParameter("orderState");
			orderState = orderState == null ? "" : new String(request.getParameter(
					"orderState").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ����λԤԼ��ѯ*/
			List<SeatOrder> seatOrderList = seatOrderDAO.QuerySeatOrder(seatObj,orderDate,addTime,userObj,orderState);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SeatOrders>").append("\r\n");
			for (int i = 0; i < seatOrderList.size(); i++) {
				sb.append("	<SeatOrder>").append("\r\n")
				.append("		<orderId>")
				.append(seatOrderList.get(i).getOrderId())
				.append("</orderId>").append("\r\n")
				.append("		<seatObj>")
				.append(seatOrderList.get(i).getSeatObj())
				.append("</seatObj>").append("\r\n")
				.append("		<orderDate>")
				.append(seatOrderList.get(i).getOrderDate())
				.append("</orderDate>").append("\r\n")
				.append("		<startTime>")
				.append(seatOrderList.get(i).getStartTime())
				.append("</startTime>").append("\r\n")
				.append("		<endTime>")
				.append(seatOrderList.get(i).getEndTime())
				.append("</endTime>").append("\r\n")
				.append("		<addTime>")
				.append(seatOrderList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("		<userObj>")
				.append(seatOrderList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<orderState>")
				.append(seatOrderList.get(i).getOrderState())
				.append("</orderState>").append("\r\n")
				.append("		<replyContent>")
				.append(seatOrderList.get(i).getReplyContent())
				.append("</replyContent>").append("\r\n")
				.append("		<orderMemo>")
				.append(seatOrderList.get(i).getOrderMemo())
				.append("</orderMemo>").append("\r\n")
				.append("	</SeatOrder>").append("\r\n");
			}
			sb.append("</SeatOrders>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SeatOrder seatOrder: seatOrderList) {
				  stringer.object();
			  stringer.key("orderId").value(seatOrder.getOrderId());
			  stringer.key("seatObj").value(seatOrder.getSeatObj());
			  stringer.key("orderDate").value(seatOrder.getOrderDate());
			  stringer.key("startTime").value(seatOrder.getStartTime());
			  stringer.key("endTime").value(seatOrder.getEndTime());
			  stringer.key("addTime").value(seatOrder.getAddTime());
			  stringer.key("userObj").value(seatOrder.getUserObj());
			  stringer.key("orderState").value(seatOrder.getOrderState());
			  stringer.key("replyContent").value(seatOrder.getReplyContent());
			  stringer.key("orderMemo").value(seatOrder.getOrderMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����λԤԼ����ȡ��λԤԼ�������������浽�½�����λԤԼ���� */ 
			SeatOrder seatOrder = new SeatOrder();
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			seatOrder.setOrderId(orderId);
			int seatObj = Integer.parseInt(request.getParameter("seatObj"));
			seatOrder.setSeatObj(seatObj);
			Timestamp orderDate = Timestamp.valueOf(request.getParameter("orderDate"));
			seatOrder.setOrderDate(orderDate);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setEndTime(endTime);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setAddTime(addTime);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setUserObj(userObj);
			String orderState = new String(request.getParameter("orderState").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setOrderState(orderState);
			String replyContent = new String(request.getParameter("replyContent").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setReplyContent(replyContent);
			String orderMemo = new String(request.getParameter("orderMemo").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setOrderMemo(orderMemo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = seatOrderDAO.AddSeatOrder(seatOrder);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����λԤԼ����ȡ��λԤԼ��ԤԼid*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = seatOrderDAO.DeleteSeatOrder(orderId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������λԤԼ֮ǰ�ȸ���orderId��ѯĳ����λԤԼ*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			SeatOrder seatOrder = seatOrderDAO.GetSeatOrder(orderId);

			// �ͻ��˲�ѯ����λԤԼ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("orderId").value(seatOrder.getOrderId());
			  stringer.key("seatObj").value(seatOrder.getSeatObj());
			  stringer.key("orderDate").value(seatOrder.getOrderDate());
			  stringer.key("startTime").value(seatOrder.getStartTime());
			  stringer.key("endTime").value(seatOrder.getEndTime());
			  stringer.key("addTime").value(seatOrder.getAddTime());
			  stringer.key("userObj").value(seatOrder.getUserObj());
			  stringer.key("orderState").value(seatOrder.getOrderState());
			  stringer.key("replyContent").value(seatOrder.getReplyContent());
			  stringer.key("orderMemo").value(seatOrder.getOrderMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������λԤԼ����ȡ��λԤԼ�������������浽�½�����λԤԼ���� */ 
			SeatOrder seatOrder = new SeatOrder();
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			seatOrder.setOrderId(orderId);
			int seatObj = Integer.parseInt(request.getParameter("seatObj"));
			seatOrder.setSeatObj(seatObj);
			Timestamp orderDate = Timestamp.valueOf(request.getParameter("orderDate"));
			seatOrder.setOrderDate(orderDate);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setEndTime(endTime);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setAddTime(addTime);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setUserObj(userObj);
			String orderState = new String(request.getParameter("orderState").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setOrderState(orderState);
			String replyContent = new String(request.getParameter("replyContent").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setReplyContent(replyContent);
			String orderMemo = new String(request.getParameter("orderMemo").getBytes("iso-8859-1"), "UTF-8");
			seatOrder.setOrderMemo(orderMemo);

			/* ����ҵ���ִ�и��²��� */
			String result = seatOrderDAO.UpdateSeatOrder(seatOrder);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
