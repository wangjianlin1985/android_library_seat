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

	/*构造座位状态业务层对象*/
	private SeatStateDAO seatStateDAO = new SeatStateDAO();

	/*默认构造函数*/
	public SeatStateServlet() {
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
			/*获取查询座位状态的参数信息*/

			/*调用业务逻辑层执行座位状态查询*/
			List<SeatState> seatStateList = seatStateDAO.QuerySeatState();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加座位状态：获取座位状态参数，参数保存到新建的座位状态对象 */ 
			SeatState seatState = new SeatState();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			seatState.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			seatState.setStateName(stateName);

			/* 调用业务层执行添加操作 */
			String result = seatStateDAO.AddSeatState(seatState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除座位状态：获取座位状态的状态id*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			/*调用业务逻辑层执行删除操作*/
			String result = seatStateDAO.DeleteSeatState(stateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新座位状态之前先根据stateId查询某个座位状态*/
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			SeatState seatState = seatStateDAO.GetSeatState(stateId);

			// 客户端查询的座位状态对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新座位状态：获取座位状态参数，参数保存到新建的座位状态对象 */ 
			SeatState seatState = new SeatState();
			int stateId = Integer.parseInt(request.getParameter("stateId"));
			seatState.setStateId(stateId);
			String stateName = new String(request.getParameter("stateName").getBytes("iso-8859-1"), "UTF-8");
			seatState.setStateName(stateName);

			/* 调用业务层执行更新操作 */
			String result = seatStateDAO.UpdateSeatState(seatState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
