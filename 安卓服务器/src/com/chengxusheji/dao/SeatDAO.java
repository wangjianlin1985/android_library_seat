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
import com.chengxusheji.domain.Room;
import com.chengxusheji.domain.SeatState;
import com.chengxusheji.domain.Seat;

@Service @Transactional
public class SeatDAO {

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
    public void AddSeat(Seat seat) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(seat);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Seat> QuerySeatInfo(Room roomObj,String seatCode,SeatState seatStateObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Seat seat where 1=1";
    	if(null != roomObj && roomObj.getRoomId()!=0) hql += " and seat.roomObj.roomId=" + roomObj.getRoomId();
    	if(!seatCode.equals("")) hql = hql + " and seat.seatCode like '%" + seatCode + "%'";
    	if(null != seatStateObj && seatStateObj.getStateId()!=0) hql += " and seat.seatStateObj.stateId=" + seatStateObj.getStateId();
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List seatList = q.list();
    	return (ArrayList<Seat>) seatList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Seat> QuerySeatInfo(Room roomObj,String seatCode,SeatState seatStateObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Seat seat where 1=1";
    	if(null != roomObj && roomObj.getRoomId()!=0) hql += " and seat.roomObj.roomId=" + roomObj.getRoomId();
    	if(!seatCode.equals("")) hql = hql + " and seat.seatCode like '%" + seatCode + "%'";
    	if(null != seatStateObj && seatStateObj.getStateId()!=0) hql += " and seat.seatStateObj.stateId=" + seatStateObj.getStateId();
    	Query q = s.createQuery(hql);
    	List seatList = q.list();
    	return (ArrayList<Seat>) seatList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Seat> QueryAllSeatInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Seat";
        Query q = s.createQuery(hql);
        List seatList = q.list();
        return (ArrayList<Seat>) seatList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Room roomObj,String seatCode,SeatState seatStateObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Seat seat where 1=1";
        if(null != roomObj && roomObj.getRoomId()!=0) hql += " and seat.roomObj.roomId=" + roomObj.getRoomId();
        if(!seatCode.equals("")) hql = hql + " and seat.seatCode like '%" + seatCode + "%'";
        if(null != seatStateObj && seatStateObj.getStateId()!=0) hql += " and seat.seatStateObj.stateId=" + seatStateObj.getStateId();
        Query q = s.createQuery(hql);
        List seatList = q.list();
        recordNumber = seatList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Seat GetSeatBySeatId(int seatId) {
        Session s = factory.getCurrentSession();
        Seat seat = (Seat)s.get(Seat.class, seatId);
        return seat;
    }

    /*����Seat��Ϣ*/
    public void UpdateSeat(Seat seat) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(seat);
    }

    /*ɾ��Seat��Ϣ*/
    public void DeleteSeat (int seatId) throws Exception {
        Session s = factory.getCurrentSession();
        Object seat = s.load(Seat.class, seatId);
        s.delete(seat);
    }

}
