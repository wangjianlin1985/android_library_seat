package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Seat;
import com.mobileserver.util.DB;

public class SeatDAO {

	public List<Seat> QuerySeat(int roomObj,String seatCode,int seatStateObj) {
		List<Seat> seatList = new ArrayList<Seat>();
		DB db = new DB();
		String sql = "select * from Seat where 1=1";
		if (roomObj != 0)
			sql += " and roomObj=" + roomObj;
		if (!seatCode.equals(""))
			sql += " and seatCode like '%" + seatCode + "%'";
		if (seatStateObj != 0)
			sql += " and seatStateObj=" + seatStateObj;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Seat seat = new Seat();
				seat.setSeatId(rs.getInt("seatId"));
				seat.setRoomObj(rs.getInt("roomObj"));
				seat.setSeatCode(rs.getString("seatCode"));
				seat.setSeatStateObj(rs.getInt("seatStateObj"));
				seatList.add(seat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return seatList;
	}
	/* ������λ���󣬽�����λ�����ҵ�� */
	public String AddSeat(Seat seat) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������λ */
			String sqlString = "insert into Seat(roomObj,seatCode,seatStateObj) values (";
			sqlString += seat.getRoomObj() + ",";
			sqlString += "'" + seat.getSeatCode() + "',";
			sqlString += seat.getSeatStateObj() ;
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��λ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����λ */
	public String DeleteSeat(int seatId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Seat where seatId=" + seatId;
			db.executeUpdate(sqlString);
			result = "��λɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ������λid��ȡ����λ */
	public Seat GetSeat(int seatId) {
		Seat seat = null;
		DB db = new DB();
		String sql = "select * from Seat where seatId=" + seatId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				seat = new Seat();
				seat.setSeatId(rs.getInt("seatId"));
				seat.setRoomObj(rs.getInt("roomObj"));
				seat.setSeatCode(rs.getString("seatCode"));
				seat.setSeatStateObj(rs.getInt("seatStateObj"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return seat;
	}
	/* ������λ */
	public String UpdateSeat(Seat seat) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Seat set ";
			sql += "roomObj=" + seat.getRoomObj() + ",";
			sql += "seatCode='" + seat.getSeatCode() + "',";
			sql += "seatStateObj=" + seat.getSeatStateObj();
			sql += " where seatId=" + seat.getSeatId();
			db.executeUpdate(sql);
			result = "��λ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
