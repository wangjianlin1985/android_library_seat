package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.Jc;

@Service @Transactional
public class JcDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddJc(Jc jc) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(jc);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Jc> QueryJcInfo(String jcType,String title,UserInfo userObj,String jcTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Jc jc where 1=1";
    	if(!jcType.equals("")) hql = hql + " and jc.jcType like '%" + jcType + "%'";
    	if(!title.equals("")) hql = hql + " and jc.title like '%" + title + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and jc.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!jcTime.equals("")) hql = hql + " and jc.jcTime like '%" + jcTime + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List jcList = q.list();
    	return (ArrayList<Jc>) jcList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Jc> QueryJcInfo(String jcType,String title,UserInfo userObj,String jcTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Jc jc where 1=1";
    	if(!jcType.equals("")) hql = hql + " and jc.jcType like '%" + jcType + "%'";
    	if(!title.equals("")) hql = hql + " and jc.title like '%" + title + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and jc.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!jcTime.equals("")) hql = hql + " and jc.jcTime like '%" + jcTime + "%'";
    	Query q = s.createQuery(hql);
    	List jcList = q.list();
    	return (ArrayList<Jc>) jcList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Jc> QueryAllJcInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Jc";
        Query q = s.createQuery(hql);
        List jcList = q.list();
        return (ArrayList<Jc>) jcList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String jcType,String title,UserInfo userObj,String jcTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Jc jc where 1=1";
        if(!jcType.equals("")) hql = hql + " and jc.jcType like '%" + jcType + "%'";
        if(!title.equals("")) hql = hql + " and jc.title like '%" + title + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and jc.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!jcTime.equals("")) hql = hql + " and jc.jcTime like '%" + jcTime + "%'";
        Query q = s.createQuery(hql);
        List jcList = q.list();
        recordNumber = jcList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Jc GetJcByJcId(int jcId) {
        Session s = factory.getCurrentSession();
        Jc jc = (Jc)s.get(Jc.class, jcId);
        return jc;
    }

    /*更新Jc信息*/
    public void UpdateJc(Jc jc) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(jc);
    }

    /*删除Jc信息*/
    public void DeleteJc (int jcId) throws Exception {
        Session s = factory.getCurrentSession();
        Object jc = s.load(Jc.class, jcId);
        s.delete(jc);
    }

}
