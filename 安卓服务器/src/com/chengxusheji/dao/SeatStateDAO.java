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
import com.chengxusheji.domain.SeatState;

@Service @Transactional
public class SeatStateDAO {

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
    public void AddSeatState(SeatState seatState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(seatState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatState> QuerySeatStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SeatState seatState where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List seatStateList = q.list();
    	return (ArrayList<SeatState>) seatStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatState> QuerySeatStateInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SeatState seatState where 1=1";
    	Query q = s.createQuery(hql);
    	List seatStateList = q.list();
    	return (ArrayList<SeatState>) seatStateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatState> QueryAllSeatStateInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SeatState";
        Query q = s.createQuery(hql);
        List seatStateList = q.list();
        return (ArrayList<SeatState>) seatStateList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From SeatState seatState where 1=1";
        Query q = s.createQuery(hql);
        List seatStateList = q.list();
        recordNumber = seatStateList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SeatState GetSeatStateByStateId(int stateId) {
        Session s = factory.getCurrentSession();
        SeatState seatState = (SeatState)s.get(SeatState.class, stateId);
        return seatState;
    }

    /*更新SeatState信息*/
    public void UpdateSeatState(SeatState seatState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(seatState);
    }

    /*删除SeatState信息*/
    public void DeleteSeatState (int stateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object seatState = s.load(SeatState.class, stateId);
        s.delete(seatState);
    }

}
