package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Jubao;
import com.mobileserver.util.DB;

public class JubaoDAO {

	public List<Jubao> QueryJubao(String title,String userObj,String jubaoTime) {
		List<Jubao> jubaoList = new ArrayList<Jubao>();
		DB db = new DB();
		String sql = "select * from Jubao where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!jubaoTime.equals(""))
			sql += " and jubaoTime like '%" + jubaoTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Jubao jubao = new Jubao();
				jubao.setJubaoId(rs.getInt("jubaoId"));
				jubao.setTitle(rs.getString("title"));
				jubao.setContent(rs.getString("content"));
				jubao.setUserObj(rs.getString("userObj"));
				jubao.setJubaoTime(rs.getString("jubaoTime"));
				jubao.setReplyContent(rs.getString("replyContent"));
				jubaoList.add(jubao);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return jubaoList;
	}
	/* 传入举报对象，进行举报的添加业务 */
	public String AddJubao(Jubao jubao) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新举报 */
			String sqlString = "insert into Jubao(title,content,userObj,jubaoTime,replyContent) values (";
			sqlString += "'" + jubao.getTitle() + "',";
			sqlString += "'" + jubao.getContent() + "',";
			sqlString += "'" + jubao.getUserObj() + "',";
			sqlString += "'" + jubao.getJubaoTime() + "',";
			sqlString += "'" + jubao.getReplyContent() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "举报添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "举报添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除举报 */
	public String DeleteJubao(int jubaoId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Jubao where jubaoId=" + jubaoId;
			db.executeUpdate(sqlString);
			result = "举报删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "举报删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据举报id获取到举报 */
	public Jubao GetJubao(int jubaoId) {
		Jubao jubao = null;
		DB db = new DB();
		String sql = "select * from Jubao where jubaoId=" + jubaoId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				jubao = new Jubao();
				jubao.setJubaoId(rs.getInt("jubaoId"));
				jubao.setTitle(rs.getString("title"));
				jubao.setContent(rs.getString("content"));
				jubao.setUserObj(rs.getString("userObj"));
				jubao.setJubaoTime(rs.getString("jubaoTime"));
				jubao.setReplyContent(rs.getString("replyContent"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return jubao;
	}
	/* 更新举报 */
	public String UpdateJubao(Jubao jubao) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Jubao set ";
			sql += "title='" + jubao.getTitle() + "',";
			sql += "content='" + jubao.getContent() + "',";
			sql += "userObj='" + jubao.getUserObj() + "',";
			sql += "jubaoTime='" + jubao.getJubaoTime() + "',";
			sql += "replyContent='" + jubao.getReplyContent() + "'";
			sql += " where jubaoId=" + jubao.getJubaoId();
			db.executeUpdate(sql);
			result = "举报更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "举报更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
