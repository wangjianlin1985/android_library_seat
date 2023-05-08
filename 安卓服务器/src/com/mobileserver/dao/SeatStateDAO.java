package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SeatState;
import com.mobileserver.util.DB;

public class SeatStateDAO {

	public List<SeatState> QuerySeatState() {
		List<SeatState> seatStateList = new ArrayList<SeatState>();
		DB db = new DB();
		String sql = "select * from SeatState where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SeatState seatState = new SeatState();
				seatState.setStateId(rs.getInt("stateId"));
				seatState.setStateName(rs.getString("stateName"));
				seatStateList.add(seatState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return seatStateList;
	}
	/* ������λ״̬���󣬽�����λ״̬�����ҵ�� */
	public String AddSeatState(SeatState seatState) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������λ״̬ */
			String sqlString = "insert into SeatState(stateName) values (";
			sqlString += "'" + seatState.getStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��λ״̬��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λ״̬���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����λ״̬ */
	public String DeleteSeatState(int stateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SeatState where stateId=" + stateId;
			db.executeUpdate(sqlString);
			result = "��λ״̬ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λ״̬ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����״̬id��ȡ����λ״̬ */
	public SeatState GetSeatState(int stateId) {
		SeatState seatState = null;
		DB db = new DB();
		String sql = "select * from SeatState where stateId=" + stateId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				seatState = new SeatState();
				seatState.setStateId(rs.getInt("stateId"));
				seatState.setStateName(rs.getString("stateName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return seatState;
	}
	/* ������λ״̬ */
	public String UpdateSeatState(SeatState seatState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SeatState set ";
			sql += "stateName='" + seatState.getStateName() + "'";
			sql += " where stateId=" + seatState.getStateId();
			db.executeUpdate(sql);
			result = "��λ״̬���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λ״̬����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
