package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ClassInfo;
import com.mobileserver.util.DB;

public class ClassInfoDAO {

	public List<ClassInfo> QueryClassInfo(String classNo,String className,Timestamp bornDate) {
		List<ClassInfo> classInfoList = new ArrayList<ClassInfo>();
		DB db = new DB();
		String sql = "select * from ClassInfo where 1=1";
		if (!classNo.equals(""))
			sql += " and classNo like '%" + classNo + "%'";
		if (!className.equals(""))
			sql += " and className like '%" + className + "%'";
		if(bornDate!=null)
			sql += " and bornDate='" + bornDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ClassInfo classInfo = new ClassInfo();
				classInfo.setClassNo(rs.getString("classNo"));
				classInfo.setClassName(rs.getString("className"));
				classInfo.setBornDate(rs.getTimestamp("bornDate"));
				classInfo.setMainTeacher(rs.getString("mainTeacher"));
				classInfo.setClassMemo(rs.getString("classMemo"));
				classInfoList.add(classInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return classInfoList;
	}
	/* ����༶���󣬽��а༶�����ҵ�� */
	public String AddClassInfo(ClassInfo classInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����°༶ */
			String sqlString = "insert into ClassInfo(classNo,className,bornDate,mainTeacher,classMemo) values (";
			sqlString += "'" + classInfo.getClassNo() + "',";
			sqlString += "'" + classInfo.getClassName() + "',";
			sqlString += "'" + classInfo.getBornDate() + "',";
			sqlString += "'" + classInfo.getMainTeacher() + "',";
			sqlString += "'" + classInfo.getClassMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�༶��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���༶ */
	public String DeleteClassInfo(String classNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ClassInfo where classNo='" + classNo + "'";
			db.executeUpdate(sqlString);
			result = "�༶ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݰ༶��Ż�ȡ���༶ */
	public ClassInfo GetClassInfo(String classNo) {
		ClassInfo classInfo = null;
		DB db = new DB();
		String sql = "select * from ClassInfo where classNo='" + classNo + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				classInfo = new ClassInfo();
				classInfo.setClassNo(rs.getString("classNo"));
				classInfo.setClassName(rs.getString("className"));
				classInfo.setBornDate(rs.getTimestamp("bornDate"));
				classInfo.setMainTeacher(rs.getString("mainTeacher"));
				classInfo.setClassMemo(rs.getString("classMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return classInfo;
	}
	/* ���°༶ */
	public String UpdateClassInfo(ClassInfo classInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ClassInfo set ";
			sql += "className='" + classInfo.getClassName() + "',";
			sql += "bornDate='" + classInfo.getBornDate() + "',";
			sql += "mainTeacher='" + classInfo.getMainTeacher() + "',";
			sql += "classMemo='" + classInfo.getClassMemo() + "'";
			sql += " where classNo='" + classInfo.getClassNo() + "'";
			db.executeUpdate(sql);
			result = "�༶���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
