package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.RoomDAO;
import com.mobileserver.domain.Room;

import org.json.JSONStringer;

public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造阅览室业务层对象*/
	private RoomDAO roomDAO = new RoomDAO();

	/*默认构造函数*/
	public RoomServlet() {
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
			/*获取查询阅览室的参数信息*/
			String roomPlace = request.getParameter("roomPlace");
			roomPlace = roomPlace == null ? "" : new String(request.getParameter(
					"roomPlace").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行阅览室查询*/
			List<Room> roomList = roomDAO.QueryRoom(roomPlace);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Rooms>").append("\r\n");
			for (int i = 0; i < roomList.size(); i++) {
				sb.append("	<Room>").append("\r\n")
				.append("		<roomId>")
				.append(roomList.get(i).getRoomId())
				.append("</roomId>").append("\r\n")
				.append("		<roomName>")
				.append(roomList.get(i).getRoomName())
				.append("</roomName>").append("\r\n")
				.append("		<roomPhoto>")
				.append(roomList.get(i).getRoomPhoto())
				.append("</roomPhoto>").append("\r\n")
				.append("		<roomPlace>")
				.append(roomList.get(i).getRoomPlace())
				.append("</roomPlace>").append("\r\n")
				.append("		<seatNum>")
				.append(roomList.get(i).getSeatNum())
				.append("</seatNum>").append("\r\n")
				.append("	</Room>").append("\r\n");
			}
			sb.append("</Rooms>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Room room: roomList) {
				  stringer.object();
			  stringer.key("roomId").value(room.getRoomId());
			  stringer.key("roomName").value(room.getRoomName());
			  stringer.key("roomPhoto").value(room.getRoomPhoto());
			  stringer.key("roomPlace").value(room.getRoomPlace());
			  stringer.key("seatNum").value(room.getSeatNum());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加阅览室：获取阅览室参数，参数保存到新建的阅览室对象 */ 
			Room room = new Room();
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			room.setRoomId(roomId);
			String roomName = new String(request.getParameter("roomName").getBytes("iso-8859-1"), "UTF-8");
			room.setRoomName(roomName);
			String roomPhoto = new String(request.getParameter("roomPhoto").getBytes("iso-8859-1"), "UTF-8");
			room.setRoomPhoto(roomPhoto);
			String roomPlace = new String(request.getParameter("roomPlace").getBytes("iso-8859-1"), "UTF-8");
			room.setRoomPlace(roomPlace);
			int seatNum = Integer.parseInt(request.getParameter("seatNum"));
			room.setSeatNum(seatNum);

			/* 调用业务层执行添加操作 */
			String result = roomDAO.AddRoom(room);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除阅览室：获取阅览室的阅览室id*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			/*调用业务逻辑层执行删除操作*/
			String result = roomDAO.DeleteRoom(roomId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新阅览室之前先根据roomId查询某个阅览室*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			Room room = roomDAO.GetRoom(roomId);

			// 客户端查询的阅览室对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("roomId").value(room.getRoomId());
			  stringer.key("roomName").value(room.getRoomName());
			  stringer.key("roomPhoto").value(room.getRoomPhoto());
			  stringer.key("roomPlace").value(room.getRoomPlace());
			  stringer.key("seatNum").value(room.getSeatNum());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新阅览室：获取阅览室参数，参数保存到新建的阅览室对象 */ 
			Room room = new Room();
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			room.setRoomId(roomId);
			String roomName = new String(request.getParameter("roomName").getBytes("iso-8859-1"), "UTF-8");
			room.setRoomName(roomName);
			String roomPhoto = new String(request.getParameter("roomPhoto").getBytes("iso-8859-1"), "UTF-8");
			room.setRoomPhoto(roomPhoto);
			String roomPlace = new String(request.getParameter("roomPlace").getBytes("iso-8859-1"), "UTF-8");
			room.setRoomPlace(roomPlace);
			int seatNum = Integer.parseInt(request.getParameter("seatNum"));
			room.setSeatNum(seatNum);

			/* 调用业务层执行更新操作 */
			String result = roomDAO.UpdateRoom(room);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
