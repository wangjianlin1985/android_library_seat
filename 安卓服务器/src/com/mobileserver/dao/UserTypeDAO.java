package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.UserType;
import com.mobileserver.util.DB;

public class UserTypeDAO {

	public List<UserType> QueryUserType() {
		List<UserType> userTypeList = new ArrayList<UserType>();
		DB db = new DB();
		String sql = "select * from UserType where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				UserType userType = new UserType();
				userType.setUserTypeId(rs.getInt("userTypeId"));
				userType.setUserTypeName(rs.getString("userTypeName"));
				userTypeList.add(userType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userTypeList;
	}
	/* �����û����Ͷ��󣬽����û����͵����ҵ�� */
	public String AddUserType(UserType userType) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в������û����� */
			String sqlString = "insert into UserType(userTypeName) values (";
			sqlString += "'" + userType.getUserTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�û�������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�û��������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���û����� */
	public String DeleteUserType(int userTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from UserType where userTypeId=" + userTypeId;
			db.executeUpdate(sqlString);
			result = "�û�����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�û�����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �����û�����id��ȡ���û����� */
	public UserType GetUserType(int userTypeId) {
		UserType userType = null;
		DB db = new DB();
		String sql = "select * from UserType where userTypeId=" + userTypeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				userType = new UserType();
				userType.setUserTypeId(rs.getInt("userTypeId"));
				userType.setUserTypeName(rs.getString("userTypeName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userType;
	}
	/* �����û����� */
	public String UpdateUserType(UserType userType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update UserType set ";
			sql += "userTypeName='" + userType.getUserTypeName() + "'";
			sql += " where userTypeId=" + userType.getUserTypeId();
			db.executeUpdate(sql);
			result = "�û����͸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�û����͸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
