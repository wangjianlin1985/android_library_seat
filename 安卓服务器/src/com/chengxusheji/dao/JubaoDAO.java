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
import com.chengxusheji.domain.Jubao;

@Service @Transactional
public class JubaoDAO {

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
    public void AddJubao(Jubao jubao) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(jubao);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Jubao> QueryJubaoInfo(String title,UserInfo userObj,String jubaoTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Jubao jubao where 1=1";
    	if(!title.equals("")) hql = hql + " and jubao.title like '%" + title + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and jubao.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!jubaoTime.equals("")) hql = hql + " and jubao.jubaoTime like '%" + jubaoTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List jubaoList = q.list();
    	return (ArrayList<Jubao>) jubaoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Jubao> QueryJubaoInfo(String title,UserInfo userObj,String jubaoTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Jubao jubao where 1=1";
    	if(!title.equals("")) hql = hql + " and jubao.title like '%" + title + "%'";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and jubao.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!jubaoTime.equals("")) hql = hql + " and jubao.jubaoTime like '%" + jubaoTime + "%'";
    	Query q = s.createQuery(hql);
    	List jubaoList = q.list();
    	return (ArrayList<Jubao>) jubaoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Jubao> QueryAllJubaoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Jubao";
        Query q = s.createQuery(hql);
        List jubaoList = q.list();
        return (ArrayList<Jubao>) jubaoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String title,UserInfo userObj,String jubaoTime) {
        Session s = factory.getCurrentSession();
        String hql = "From Jubao jubao where 1=1";
        if(!title.equals("")) hql = hql + " and jubao.title like '%" + title + "%'";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and jubao.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!jubaoTime.equals("")) hql = hql + " and jubao.jubaoTime like '%" + jubaoTime + "%'";
        Query q = s.createQuery(hql);
        List jubaoList = q.list();
        recordNumber = jubaoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Jubao GetJubaoByJubaoId(int jubaoId) {
        Session s = factory.getCurrentSession();
        Jubao jubao = (Jubao)s.get(Jubao.class, jubaoId);
        return jubao;
    }

    /*����Jubao��Ϣ*/
    public void UpdateJubao(Jubao jubao) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(jubao);
    }

    /*ɾ��Jubao��Ϣ*/
    public void DeleteJubao (int jubaoId) throws Exception {
        Session s = factory.getCurrentSession();
        Object jubao = s.load(Jubao.class, jubaoId);
        s.delete(jubao);
    }

}
