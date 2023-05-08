package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Room;
import com.mobileserver.util.DB;

public class RoomDAO {

	public List<Room> QueryRoom(String roomPlace) {
		List<Room> roomList = new ArrayList<Room>();
		DB db = new DB();
		String sql = "select * from Room where 1=1";
		if (!roomPlace.equals(""))
			sql += " and roomPlace like '%" + roomPlace + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Room room = new Room();
				room.setRoomId(rs.getInt("roomId"));
				room.setRoomName(rs.getString("roomName"));
				room.setRoomPhoto(rs.getString("roomPhoto"));
				room.setRoomPlace(rs.getString("roomPlace"));
				room.setSeatNum(rs.getInt("seatNum"));
				roomList.add(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return roomList;
	}
	/* ���������Ҷ��󣬽��������ҵ����ҵ�� */
	public String AddRoom(Room room) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����������� */
			String sqlString = "insert into Room(roomName,roomPhoto,roomPlace,seatNum) values (";
			sqlString += "'" + room.getRoomName() + "',";
			sqlString += "'" + room.getRoomPhoto() + "',";
			sqlString += "'" + room.getRoomPlace() + "',";
			sqlString += room.getSeatNum();
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�������� */
	public String DeleteRoom(int roomId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Room where roomId=" + roomId;
			db.executeUpdate(sqlString);
			result = "������ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����������id��ȡ�������� */
	public Room GetRoom(int roomId) {
		Room room = null;
		DB db = new DB();
		String sql = "select * from Room where roomId=" + roomId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				room = new Room();
				room.setRoomId(rs.getInt("roomId"));
				room.setRoomName(rs.getString("roomName"));
				room.setRoomPhoto(rs.getString("roomPhoto"));
				room.setRoomPlace(rs.getString("roomPlace"));
				room.setSeatNum(rs.getInt("seatNum"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return room;
	}
	/* ���������� */
	public String UpdateRoom(Room room) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Room set ";
			sql += "roomName='" + room.getRoomName() + "',";
			sql += "roomPhoto='" + room.getRoomPhoto() + "',";
			sql += "roomPlace='" + room.getRoomPlace() + "',";
			sql += "seatNum=" + room.getSeatNum();
			sql += " where roomId=" + room.getRoomId();
			db.executeUpdate(sql);
			result = "�����Ҹ��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����Ҹ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
