package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.SeatOrder;
import com.mobileserver.util.DB;

public class SeatOrderDAO {

	public List<SeatOrder> QuerySeatOrder(int seatObj,Timestamp orderDate,String addTime,String userObj,String orderState) {
		List<SeatOrder> seatOrderList = new ArrayList<SeatOrder>();
		DB db = new DB();
		String sql = "select * from SeatOrder where 1=1";
		if (seatObj != 0)
			sql += " and seatObj=" + seatObj;
		if(orderDate!=null)
			sql += " and orderDate='" + orderDate + "'";
		if (!addTime.equals(""))
			sql += " and addTime like '%" + addTime + "%'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!orderState.equals(""))
			sql += " and orderState like '%" + orderState + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				SeatOrder seatOrder = new SeatOrder();
				seatOrder.setOrderId(rs.getInt("orderId"));
				seatOrder.setSeatObj(rs.getInt("seatObj"));
				seatOrder.setOrderDate(rs.getTimestamp("orderDate"));
				seatOrder.setStartTime(rs.getString("startTime"));
				seatOrder.setEndTime(rs.getString("endTime"));
				seatOrder.setAddTime(rs.getString("addTime"));
				seatOrder.setUserObj(rs.getString("userObj"));
				seatOrder.setOrderState(rs.getString("orderState"));
				seatOrder.setReplyContent(rs.getString("replyContent"));
				seatOrder.setOrderMemo(rs.getString("orderMemo"));
				seatOrderList.add(seatOrder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return seatOrderList;
	}
	/* ������λԤԼ���󣬽�����λԤԼ�����ҵ�� */
	public String AddSeatOrder(SeatOrder seatOrder) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������λԤԼ */
			String sqlString = "insert into SeatOrder(seatObj,orderDate,startTime,endTime,addTime,userObj,orderState,replyContent,orderMemo) values (";
			sqlString += seatOrder.getSeatObj() + ",";
			sqlString += "'" + seatOrder.getOrderDate() + "',";
			sqlString += "'" + seatOrder.getStartTime() + "',";
			sqlString += "'" + seatOrder.getEndTime() + "',";
			sqlString += "'" + seatOrder.getAddTime() + "',";
			sqlString += "'" + seatOrder.getUserObj() + "',";
			sqlString += "'" + seatOrder.getOrderState() + "',";
			sqlString += "'" + seatOrder.getReplyContent() + "',";
			sqlString += "'" + seatOrder.getOrderMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��λԤԼ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λԤԼ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����λԤԼ */
	public String DeleteSeatOrder(int orderId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from SeatOrder where orderId=" + orderId;
			db.executeUpdate(sqlString);
			result = "��λԤԼɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λԤԼɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ԤԼid��ȡ����λԤԼ */
	public SeatOrder GetSeatOrder(int orderId) {
		SeatOrder seatOrder = null;
		DB db = new DB();
		String sql = "select * from SeatOrder where orderId=" + orderId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				seatOrder = new SeatOrder();
				seatOrder.setOrderId(rs.getInt("orderId"));
				seatOrder.setSeatObj(rs.getInt("seatObj"));
				seatOrder.setOrderDate(rs.getTimestamp("orderDate"));
				seatOrder.setStartTime(rs.getString("startTime"));
				seatOrder.setEndTime(rs.getString("endTime"));
				seatOrder.setAddTime(rs.getString("addTime"));
				seatOrder.setUserObj(rs.getString("userObj"));
				seatOrder.setOrderState(rs.getString("orderState"));
				seatOrder.setReplyContent(rs.getString("replyContent"));
				seatOrder.setOrderMemo(rs.getString("orderMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return seatOrder;
	}
	/* ������λԤԼ */
	public String UpdateSeatOrder(SeatOrder seatOrder) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update SeatOrder set ";
			sql += "seatObj=" + seatOrder.getSeatObj() + ",";
			sql += "orderDate='" + seatOrder.getOrderDate() + "',";
			sql += "startTime='" + seatOrder.getStartTime() + "',";
			sql += "endTime='" + seatOrder.getEndTime() + "',";
			sql += "addTime='" + seatOrder.getAddTime() + "',";
			sql += "userObj='" + seatOrder.getUserObj() + "',";
			sql += "orderState='" + seatOrder.getOrderState() + "',";
			sql += "replyContent='" + seatOrder.getReplyContent() + "',";
			sql += "orderMemo='" + seatOrder.getOrderMemo() + "'";
			sql += " where orderId=" + seatOrder.getOrderId();
			db.executeUpdate(sql);
			result = "��λԤԼ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��λԤԼ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
