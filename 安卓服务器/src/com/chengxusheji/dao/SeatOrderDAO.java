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
import com.chengxusheji.domain.SeatOrder;

@Service @Transactional
public class SeatOrderDAO {

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
    public void AddSeatOrder(SeatOrder seatOrder) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(seatOrder);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatOrder> QuerySeatOrderInfo(Seat seatObj,String orderDate,String addTime,UserInfo userObj,String orderState,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SeatOrder seatOrder where 1=1";
    	if(null != seatObj && seatObj.getSeatId()!=0) hql += " and seatOrder.seatObj.seatId=" + seatObj.getSeatId();
    	if(!orderDate.equals("")) hql = hql + " and seatOrder.orderDate like '%" + orderDate + "%'";
    	if(!addTime.equals("")) hql = hql + " and seatOrder.addTime like '%" + addTime + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and seatOrder.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!orderState.equals("")) hql = hql + " and seatOrder.orderState like '%" + orderState + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List seatOrderList = q.list();
    	return (ArrayList<SeatOrder>) seatOrderList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatOrder> QuerySeatOrderInfo(Seat seatObj,String orderDate,String addTime,UserInfo userObj,String orderState) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SeatOrder seatOrder where 1=1";
    	if(null != seatObj && seatObj.getSeatId()!=0) hql += " and seatOrder.seatObj.seatId=" + seatObj.getSeatId();
    	if(!orderDate.equals("")) hql = hql + " and seatOrder.orderDate like '%" + orderDate + "%'";
    	if(!addTime.equals("")) hql = hql + " and seatOrder.addTime like '%" + addTime + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and seatOrder.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!orderState.equals("")) hql = hql + " and seatOrder.orderState like '%" + orderState + "%'";
    	Query q = s.createQuery(hql);
    	List seatOrderList = q.list();
    	return (ArrayList<SeatOrder>) seatOrderList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatOrder> QueryAllSeatOrderInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SeatOrder";
        Query q = s.createQuery(hql);
        List seatOrderList = q.list();
        return (ArrayList<SeatOrder>) seatOrderList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Seat seatObj,String orderDate,String addTime,UserInfo userObj,String orderState) {
        Session s = factory.getCurrentSession();
        String hql = "From SeatOrder seatOrder where 1=1";
        if(null != seatObj && seatObj.getSeatId()!=0) hql += " and seatOrder.seatObj.seatId=" + seatObj.getSeatId();
        if(!orderDate.equals("")) hql = hql + " and seatOrder.orderDate like '%" + orderDate + "%'";
        if(!addTime.equals("")) hql = hql + " and seatOrder.addTime like '%" + addTime + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and seatOrder.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!orderState.equals("")) hql = hql + " and seatOrder.orderState like '%" + orderState + "%'";
        Query q = s.createQuery(hql);
        List seatOrderList = q.list();
        recordNumber = seatOrderList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SeatOrder GetSeatOrderByOrderId(int orderId) {
        Session s = factory.getCurrentSession();
        SeatOrder seatOrder = (SeatOrder)s.get(SeatOrder.class, orderId);
        return seatOrder;
    }

    /*更新SeatOrder信息*/
    public void UpdateSeatOrder(SeatOrder seatOrder) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(seatOrder);
    }

    /*删除SeatOrder信息*/
    public void DeleteSeatOrder (int orderId) throws Exception {
        Session s = factory.getCurrentSession();
        Object seatOrder = s.load(SeatOrder.class, orderId);
        s.delete(seatOrder);
    }

}
