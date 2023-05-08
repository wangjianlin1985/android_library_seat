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

	/*构造座位预约业务层对象*/
	private SeatOrderDAO seatOrderDAO = new SeatOrderDAO();

	/*默认构造函数*/
	public SeatOrderServlet() {
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
			/*获取查询座位预约的参数信息*/
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

			/*调用业务逻辑层执行座位预约查询*/
			List<SeatOrder> seatOrderList = seatOrderDAO.QuerySeatOrder(seatObj,orderDate,addTime,userObj,orderState);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加座位预约：获取座位预约参数，参数保存到新建的座位预约对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = seatOrderDAO.AddSeatOrder(seatOrder);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除座位预约：获取座位预约的预约id*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			/*调用业务逻辑层执行删除操作*/
			String result = seatOrderDAO.DeleteSeatOrder(orderId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新座位预约之前先根据orderId查询某个座位预约*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			SeatOrder seatOrder = seatOrderDAO.GetSeatOrder(orderId);

			// 客户端查询的座位预约对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新座位预约：获取座位预约参数，参数保存到新建的座位预约对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = seatOrderDAO.UpdateSeatOrder(seatOrder);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
