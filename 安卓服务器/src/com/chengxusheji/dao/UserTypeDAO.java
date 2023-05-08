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
import com.chengxusheji.domain.UserType;

@Service @Transactional
public class UserTypeDAO {

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
    public void AddUserType(UserType userType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(userType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<UserType> QueryUserTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From UserType userType where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List userTypeList = q.list();
    	return (ArrayList<UserType>) userTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<UserType> QueryUserTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From UserType userType where 1=1";
    	Query q = s.createQuery(hql);
    	List userTypeList = q.list();
    	return (ArrayList<UserType>) userTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<UserType> QueryAllUserTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From UserType";
        Query q = s.createQuery(hql);
        List userTypeList = q.list();
        return (ArrayList<UserType>) userTypeList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From UserType userType where 1=1";
        Query q = s.createQuery(hql);
        List userTypeList = q.list();
        recordNumber = userTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public UserType GetUserTypeByUserTypeId(int userTypeId) {
        Session s = factory.getCurrentSession();
        UserType userType = (UserType)s.get(UserType.class, userTypeId);
        return userType;
    }

    /*����UserType��Ϣ*/
    public void UpdateUserType(UserType userType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(userType);
    }

    /*ɾ��UserType��Ϣ*/
    public void DeleteUserType (int userTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object userType = s.load(UserType.class, userTypeId);
        s.delete(userType);
    }

}
