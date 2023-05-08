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
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
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
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SelectSeat GetSelectSeatBySelectId(int selectId) {
        Session s = factory.getCurrentSession();
        SelectSeat selectSeat = (SelectSeat)s.get(SelectSeat.class, selectId);
        return selectSeat;
    }

    /*����SelectSeat��Ϣ*/
    public void UpdateSelectSeat(SelectSeat selectSeat) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(selectSeat);
    }

    /*ɾ��SelectSeat��Ϣ*/
    public void DeleteSelectSeat (int selectId) throws Exception {
        Session s = factory.getCurrentSession();
        Object selectSeat = s.load(SelectSeat.class, selectId);
        s.delete(selectSeat);
    }

}
