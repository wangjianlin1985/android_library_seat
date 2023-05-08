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

	/*����������ҵ������*/
	private RoomDAO roomDAO = new RoomDAO();

	/*Ĭ�Ϲ��캯��*/
	public RoomServlet() {
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
			/*��ȡ��ѯ�����ҵĲ�����Ϣ*/
			String roomPlace = request.getParameter("roomPlace");
			roomPlace = roomPlace == null ? "" : new String(request.getParameter(
					"roomPlace").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�������Ҳ�ѯ*/
			List<Room> roomList = roomDAO.QueryRoom(roomPlace);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��������ң���ȡ�����Ҳ������������浽�½��������Ҷ��� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = roomDAO.AddRoom(room);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�������ң���ȡ�����ҵ�������id*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = roomDAO.DeleteRoom(roomId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����������֮ǰ�ȸ���roomId��ѯĳ��������*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			Room room = roomDAO.GetRoom(roomId);

			// �ͻ��˲�ѯ�������Ҷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���������ң���ȡ�����Ҳ������������浽�½��������Ҷ��� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = roomDAO.UpdateRoom(room);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
