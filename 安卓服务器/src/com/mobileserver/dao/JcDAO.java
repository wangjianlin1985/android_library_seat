package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Jc;
import com.mobileserver.util.DB;

public class JcDAO {

	public List<Jc> QueryJc(String jcType,String title,String userObj,String jcTime) {
		List<Jc> jcList = new ArrayList<Jc>();
		DB db = new DB();
		String sql = "select * from Jc where 1=1";
		if (!jcType.equals(""))
			sql += " and jcType like '%" + jcType + "%'";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!jcTime.equals(""))
			sql += " and jcTime like '%" + jcTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Jc jc = new Jc();
				jc.setJcId(rs.getInt("jcId"));
				jc.setJcType(rs.getString("jcType"));
				jc.setTitle(rs.getString("title"));
				jc.setContent(rs.getString("content"));
				jc.setUserObj(rs.getString("userObj"));
				jc.setCreditScore(rs.getFloat("creditScore"));
				jc.setJcTime(rs.getString("jcTime"));
				jcList.add(jc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return jcList;
	}
	/* ���뽱�Ͷ��󣬽��н��͵����ҵ�� */
	public String AddJc(Jc jc) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����½��� */
			String sqlString = "insert into Jc(jcType,title,content,userObj,creditScore,jcTime) values (";
			sqlString += "'" + jc.getJcType() + "',";
			sqlString += "'" + jc.getTitle() + "',";
			sqlString += "'" + jc.getContent() + "',";
			sqlString += "'" + jc.getUserObj() + "',";
			sqlString += jc.getCreditScore() + ",";
			sqlString += "'" + jc.getJcTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������ */
	public String DeleteJc(int jcId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Jc where jcId=" + jcId;
			db.executeUpdate(sqlString);
			result = "����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݽ���id��ȡ������ */
	public Jc GetJc(int jcId) {
		Jc jc = null;
		DB db = new DB();
		String sql = "select * from Jc where jcId=" + jcId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				jc = new Jc();
				jc.setJcId(rs.getInt("jcId"));
				jc.setJcType(rs.getString("jcType"));
				jc.setTitle(rs.getString("title"));
				jc.setContent(rs.getString("content"));
				jc.setUserObj(rs.getString("userObj"));
				jc.setCreditScore(rs.getFloat("creditScore"));
				jc.setJcTime(rs.getString("jcTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return jc;
	}
	/* ���½��� */
	public String UpdateJc(Jc jc) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Jc set ";
			sql += "jcType='" + jc.getJcType() + "',";
			sql += "title='" + jc.getTitle() + "',";
			sql += "content='" + jc.getContent() + "',";
			sql += "userObj='" + jc.getUserObj() + "',";
			sql += "creditScore=" + jc.getCreditScore() + ",";
			sql += "jcTime='" + jc.getJcTime() + "'";
			sql += " where jcId=" + jc.getJcId();
			db.executeUpdate(sql);
			result = "���͸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���͸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
