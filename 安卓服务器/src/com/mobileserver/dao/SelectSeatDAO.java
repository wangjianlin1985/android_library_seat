package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SelectSeat;
import com.mobileserver.util.DB;

public class SelectSeatDAO {

	public List<SelectSeat> QuerySelectSeat(int seatObj,String userObj,String startTime,String endTime,String seatState) {
		List<SelectSeat> selectSeatList = new ArrayList<SelectSeat>();
		DB db = new DB();
		String sql = "select * from SelectSeat where 1=1";
		if (seatObj != 0)
			sql += " and seatObj=" + seatObj;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!startTime.equals(""))
			sql += " and startTime like '%" + startTime + "%'";
		if (!endTime.equals(""))
			sql += " and endTime like '%" + endTime + "%'";
		if (!seatState.equals(""))
			sql += " and seatState like '%" + seatState + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SelectSeat selectSeat = new SelectSeat();
				selectSeat.setSelectId(rs.getInt("selectId"));
				selectSeat.setSeatObj(rs.getInt("seatObj"));
				selectSeat.setUserObj(rs.getString("userObj"));
				selectSeat.setStartTime(rs.getString("startTime"));
				selectSeat.setEndTime(rs.getString("endTime"));
				selectSeat.setSeatState(rs.getString("seatState"));
				selectSeatList.add(selectSeat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return selectSeatList;
	}
	/* ����ѡ�����󣬽���ѡ�������ҵ�� */
	public String AddSelectSeat(SelectSeat selectSeat) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ѡ�� */
			String sqlString = "insert into SelectSeat(seatObj,userObj,startTime,endTime,seatState) values (";
			sqlString += selectSeat.getSeatObj() + ",";
			sqlString += "'" + selectSeat.getUserObj() + "',";
			sqlString += "'" + selectSeat.getStartTime() + "',";
			sqlString += "'" + selectSeat.getEndTime() + "',";
			sqlString += "'" + selectSeat.getSeatState() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ѡ����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѡ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ѡ�� */
	public String DeleteSelectSeat(int selectId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SelectSeat where selectId=" + selectId;
			db.executeUpdate(sqlString);
			result = "ѡ��ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѡ��ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ѡ��id��ȡ��ѡ�� */
	public SelectSeat GetSelectSeat(int selectId) {
		SelectSeat selectSeat = null;
		DB db = new DB();
		String sql = "select * from SelectSeat where selectId=" + selectId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				selectSeat = new SelectSeat();
				selectSeat.setSelectId(rs.getInt("selectId"));
				selectSeat.setSeatObj(rs.getInt("seatObj"));
				selectSeat.setUserObj(rs.getString("userObj"));
				selectSeat.setStartTime(rs.getString("startTime"));
				selectSeat.setEndTime(rs.getString("endTime"));
				selectSeat.setSeatState(rs.getString("seatState"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return selectSeat;
	}
	/* ����ѡ�� */
	public String UpdateSelectSeat(SelectSeat selectSeat) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SelectSeat set ";
			sql += "seatObj=" + selectSeat.getSeatObj() + ",";
			sql += "userObj='" + selectSeat.getUserObj() + "',";
			sql += "startTime='" + selectSeat.getStartTime() + "',";
			sql += "endTime='" + selectSeat.getEndTime() + "',";
			sql += "seatState='" + selectSeat.getSeatState() + "'";
			sql += " where selectId=" + selectSeat.getSelectId();
			db.executeUpdate(sql);
			result = "ѡ�����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѡ������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
