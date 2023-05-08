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
import com.chengxusheji.domain.ClassInfo;

@Service @Transactional
public class ClassInfoDAO {

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
    public void AddClassInfo(ClassInfo classInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(classInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ClassInfo> QueryClassInfoInfo(String classNo,String className,String bornDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ClassInfo classInfo where 1=1";
    	if(!classNo.equals("")) hql = hql + " and classInfo.classNo like '%" + classNo + "%'";
    	if(!className.equals("")) hql = hql + " and classInfo.className like '%" + className + "%'";
    	if(!bornDate.equals("")) hql = hql + " and classInfo.bornDate like '%" + bornDate + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List classInfoList = q.list();
    	return (ArrayList<ClassInfo>) classInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ClassInfo> QueryClassInfoInfo(String classNo,String className,String bornDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ClassInfo classInfo where 1=1";
    	if(!classNo.equals("")) hql = hql + " and classInfo.classNo like '%" + classNo + "%'";
    	if(!className.equals("")) hql = hql + " and classInfo.className like '%" + className + "%'";
    	if(!bornDate.equals("")) hql = hql + " and classInfo.bornDate like '%" + bornDate + "%'";
    	Query q = s.createQuery(hql);
    	List classInfoList = q.list();
    	return (ArrayList<ClassInfo>) classInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ClassInfo> QueryAllClassInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ClassInfo";
        Query q = s.createQuery(hql);
        List classInfoList = q.list();
        return (ArrayList<ClassInfo>) classInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String classNo,String className,String bornDate) {
        Session s = factory.getCurrentSession();
        String hql = "From ClassInfo classInfo where 1=1";
        if(!classNo.equals("")) hql = hql + " and classInfo.classNo like '%" + classNo + "%'";
        if(!className.equals("")) hql = hql + " and classInfo.className like '%" + className + "%'";
        if(!bornDate.equals("")) hql = hql + " and classInfo.bornDate like '%" + bornDate + "%'";
        Query q = s.createQuery(hql);
        List classInfoList = q.list();
        recordNumber = classInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ClassInfo GetClassInfoByClassNo(String classNo) {
        Session s = factory.getCurrentSession();
        ClassInfo classInfo = (ClassInfo)s.get(ClassInfo.class, classNo);
        return classInfo;
    }

    /*����ClassInfo��Ϣ*/
    public void UpdateClassInfo(ClassInfo classInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(classInfo);
    }

    /*ɾ��ClassInfo��Ϣ*/
    public void DeleteClassInfo (String classNo) throws Exception {
        Session s = factory.getCurrentSession();
        Object classInfo = s.load(ClassInfo.class, classNo);
        s.delete(classInfo);
    }

}
