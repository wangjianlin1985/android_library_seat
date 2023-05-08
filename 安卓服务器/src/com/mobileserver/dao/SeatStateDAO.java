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
	/* 传入座位状态对象，进行座位状态的添加业务 */
	public String AddSeatState(SeatState seatState) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新座位状态 */
			String sqlString = "insert into SeatState(stateName) values (";
			sqlString += "'" + seatState.getStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "座位状态添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "座位状态添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除座位状态 */
	public String DeleteSeatState(int stateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SeatState where stateId=" + stateId;
			db.executeUpdate(sqlString);
			result = "座位状态删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "座位状态删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据状态id获取到座位状态 */
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
	/* 更新座位状态 */
	public String UpdateSeatState(SeatState seatState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SeatState set ";
			sql += "stateName='" + seatState.getStateName() + "'";
			sql += " where stateId=" + seatState.getStateId();
			db.executeUpdate(sql);
			result = "座位状态更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "座位状态更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
