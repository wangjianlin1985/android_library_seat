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
    public void AddSeatState(SeatState seatState) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(seatState);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SeatState> QuerySeatStateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SeatState seatState where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SeatState GetSeatStateByStateId(int stateId) {
        Session s = factory.getCurrentSession();
        SeatState seatState = (SeatState)s.get(SeatState.class, stateId);
        return seatState;
    }

    /*����SeatState��Ϣ*/
    public void UpdateSeatState(SeatState seatState) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(seatState);
    }

    /*ɾ��SeatState��Ϣ*/
    public void DeleteSeatState (int stateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object seatState = s.load(SeatState.class, stateId);
        s.delete(seatState);
    }

}
