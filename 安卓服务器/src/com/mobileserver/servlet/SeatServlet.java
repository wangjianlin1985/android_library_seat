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

	/*构造座位业务层对象*/
	private SeatDAO seatDAO = new SeatDAO();

	/*默认构造函数*/
	public SeatServlet() {
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
			/*获取查询座位的参数信息*/
			int roomObj = 0;
			if (request.getParameter("roomObj") != null)
				roomObj = Integer.parseInt(request.getParameter("roomObj"));
			String seatCode = request.getParameter("seatCode");
			seatCode = seatCode == null ? "" : new String(request.getParameter(
					"seatCode").getBytes("iso-8859-1"), "UTF-8");
			int seatStateObj = 0;
			if (request.getParameter("seatStateObj") != null)
				seatStateObj = Integer.parseInt(request.getParameter("seatStateObj"));

			/*调用业务逻辑层执行座位查询*/
			List<Seat> seatList = seatDAO.QuerySeat(roomObj,seatCode,seatStateObj);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加座位：获取座位参数，参数保存到新建的座位对象 */ 
			Seat seat = new Seat();
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			seat.setSeatId(seatId);
			int roomObj = Integer.parseInt(request.getParameter("roomObj"));
			seat.setRoomObj(roomObj);
			String seatCode = new String(request.getParameter("seatCode").getBytes("iso-8859-1"), "UTF-8");
			seat.setSeatCode(seatCode);
			int seatStateObj = Integer.parseInt(request.getParameter("seatStateObj"));
			seat.setSeatStateObj(seatStateObj);

			/* 调用业务层执行添加操作 */
			String result = seatDAO.AddSeat(seat);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除座位：获取座位的座位id*/
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			/*调用业务逻辑层执行删除操作*/
			String result = seatDAO.DeleteSeat(seatId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新座位之前先根据seatId查询某个座位*/
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			Seat seat = seatDAO.GetSeat(seatId);

			// 客户端查询的座位对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新座位：获取座位参数，参数保存到新建的座位对象 */ 
			Seat seat = new Seat();
			int seatId = Integer.parseInt(request.getParameter("seatId"));
			seat.setSeatId(seatId);
			int roomObj = Integer.parseInt(request.getParameter("roomObj"));
			seat.setRoomObj(roomObj);
			String seatCode = new String(request.getParameter("seatCode").getBytes("iso-8859-1"), "UTF-8");
			seat.setSeatCode(seatCode);
			int seatStateObj = Integer.parseInt(request.getParameter("seatStateObj"));
			seat.setSeatStateObj(seatStateObj);

			/* 调用业务层执行更新操作 */
			String result = seatDAO.UpdateSeat(seat);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
