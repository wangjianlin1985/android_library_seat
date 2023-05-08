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
	/* 传入用户类型对象，进行用户类型的添加业务 */
	public String AddUserType(UserType userType) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新用户类型 */
			String sqlString = "insert into UserType(userTypeName) values (";
			sqlString += "'" + userType.getUserTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "用户类型添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用户类型添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除用户类型 */
	public String DeleteUserType(int userTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from UserType where userTypeId=" + userTypeId;
			db.executeUpdate(sqlString);
			result = "用户类型删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用户类型删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据用户类型id获取到用户类型 */
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
	/* 更新用户类型 */
	public String UpdateUserType(UserType userType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update UserType set ";
			sql += "userTypeName='" + userType.getUserTypeName() + "'";
			sql += " where userTypeId=" + userType.getUserTypeId();
			db.executeUpdate(sql);
			result = "用户类型更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "用户类型更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
