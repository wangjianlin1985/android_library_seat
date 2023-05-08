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

	/*构造选座业务层对象*/
	private SelectSeatDAO selectSeatDAO = new SelectSeatDAO();

	/*默认构造函数*/
	public SelectSeatServlet() {
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
			/*获取查询选座的参数信息*/
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

			/*调用业务逻辑层执行选座查询*/
			List<SelectSeat> selectSeatList = selectSeatDAO.QuerySelectSeat(seatObj,userObj,startTime,endTime,seatState);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加选座：获取选座参数，参数保存到新建的选座对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = selectSeatDAO.AddSelectSeat(selectSeat);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除选座：获取选座的选座id*/
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			/*调用业务逻辑层执行删除操作*/
			String result = selectSeatDAO.DeleteSelectSeat(selectId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新选座之前先根据selectId查询某个选座*/
			int selectId = Integer.parseInt(request.getParameter("selectId"));
			SelectSeat selectSeat = selectSeatDAO.GetSelectSeat(selectId);

			// 客户端查询的选座对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新选座：获取选座参数，参数保存到新建的选座对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = selectSeatDAO.UpdateSelectSeat(selectSeat);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
