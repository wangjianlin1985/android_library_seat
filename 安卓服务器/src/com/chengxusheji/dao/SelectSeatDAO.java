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
import com.chengxusheji.domain.Seat;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.SelectSeat;

@Service @Transactional
public class SelectSeatDAO {

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
    public void AddSelectSeat(SelectSeat selectSeat) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(selectSeat);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SelectSeat> QuerySelectSeatInfo(Seat seatObj,UserInfo userObj,String startTime,String endTime,String seatState,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SelectSeat selectSeat where 1=1";
    	if(null != seatObj && seatObj.getSeatId()!=0) hql += " and selectSeat.seatObj.seatId=" + seatObj.getSeatId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and selectSeat.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!startTime.equals("")) hql = hql + " and selectSeat.startTime like '%" + startTime + "%'";
    	if(!endTime.equals("")) hql = hql + " and selectSeat.endTime like '%" + endTime + "%'";
    	if(!seatState.equals("")) hql = hql + " and selectSeat.seatState like '%" + seatState + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List selectSeatList = q.list();
    	return (ArrayList<SelectSeat>) selectSeatList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SelectSeat> QuerySelectSeatInfo(Seat seatObj,UserInfo userObj,String startTime,String endTime,String seatState) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SelectSeat selectSeat where 1=1";
    	if(null != seatObj && seatObj.getSeatId()!=0) hql += " and selectSeat.seatObj.seatId=" + seatObj.getSeatId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and selectSeat.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!startTime.equals("")) hql = hql + " and selectSeat.startTime like '%" + startTime + "%'";
    	if(!endTime.equals("")) hql = hql + " and selectSeat.endTime like '%" + endTime + "%'";
    	if(!seatState.equals("")) hql = hql + " and selectSeat.seatState like '%" + seatState + "%'";
    	Query q = s.createQuery(hql);
    	List selectSeatList = q.list();
    	return (ArrayList<SelectSeat>) selectSeatList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SelectSeat> QueryAllSelectSeatInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SelectSeat";
        Query q = s.createQuery(hql);
        List selectSeatList = q.list();
        return (ArrayList<SelectSeat>) selectSeatList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Seat seatObj,UserInfo userObj,String startTime,String endTime,String seatState) {
        Session s = factory.getCurrentSession();
        String hql = "From SelectSeat selectSeat where 1=1";
        if(null != seatObj && seatObj.getSeatId()!=0) hql += " and selectSeat.seatObj.seatId=" + seatObj.getSeatId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and selectSeat.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!startTime.equals("")) hql = hql + " and selectSeat.startTime like '%" + startTime + "%'";
        if(!endTime.equals("")) hql = hql + " and selectSeat.endTime like '%" + endTime + "%'";
        if(!seatState.equals("")) hql = hql + " and selectSeat.seatState like '%" + seatState + "%'";
        Query q = s.createQuery(hql);
        List selectSeatList = q.list();
        recordNumber = selectSeatList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SelectSeat GetSelectSeatBySelectId(int selectId) {
        Session s = factory.getCurrentSession();
        SelectSeat selectSeat = (SelectSeat)s.get(SelectSeat.class, selectId);
        return selectSeat;
    }

    /*更新SelectSeat信息*/
    public void UpdateSelectSeat(SelectSeat selectSeat) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(selectSeat);
    }

    /*删除SelectSeat信息*/
    public void DeleteSelectSeat (int selectId) throws Exception {
        Session s = factory.getCurrentSession();
        Object selectSeat = s.load(SelectSeat.class, selectId);
        s.delete(selectSeat);
    }

}
