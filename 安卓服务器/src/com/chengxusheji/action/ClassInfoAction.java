package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.ClassInfoDAO;
import com.chengxusheji.domain.ClassInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ClassInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: �༶���*/
    private String classNo;
    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }
    public String getClassNo() {
        return this.classNo;
    }

    /*�������Ҫ��ѯ������: �༶����*/
    private String className;
    public void setClassName(String className) {
        this.className = className;
    }
    public String getClassName() {
        return this.className;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String bornDate;
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }
    public String getBornDate() {
        return this.bornDate;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource ClassInfoDAO classInfoDAO;

    /*��������ClassInfo����*/
    private ClassInfo classInfo;
    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
    public ClassInfo getClassInfo() {
        return this.classInfo;
    }

    /*��ת�����ClassInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���ClassInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�༶����Ƿ��Ѿ�����*/
        String classNo = classInfo.getClassNo();
        ClassInfo db_classInfo = classInfoDAO.GetClassInfoByClassNo(classNo);
        if(null != db_classInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�ð༶����Ѿ�����!"));
            return "error";
        }
        try {
            classInfoDAO.AddClassInfo(classInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ClassInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ClassInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯClassInfo��Ϣ*/
    public String QueryClassInfo() {
        if(currentPage == 0) currentPage = 1;
        if(classNo == null) classNo = "";
        if(className == null) className = "";
        if(bornDate == null) bornDate = "";
        List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfoInfo(classNo, className, bornDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        classInfoDAO.CalculateTotalPageAndRecordNumber(classNo, className, bornDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = classInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = classInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("classNo", classNo);
        ctx.put("className", className);
        ctx.put("bornDate", bornDate);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryClassInfoOutputToExcel() { 
        if(classNo == null) classNo = "";
        if(className == null) className = "";
        if(bornDate == null) bornDate = "";
        List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfoInfo(classNo,className,bornDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ClassInfo��Ϣ��¼"; 
        String[] headers = { "�༶���","�༶����","��������","������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<classInfoList.size();i++) {
        	ClassInfo classInfo = classInfoList.get(i); 
        	dataset.add(new String[]{classInfo.getClassNo(),classInfo.getClassName(),new SimpleDateFormat("yyyy-MM-dd").format(classInfo.getBornDate()),classInfo.getMainTeacher()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"ClassInfo.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯClassInfo��Ϣ*/
    public String FrontQueryClassInfo() {
        if(currentPage == 0) currentPage = 1;
        if(classNo == null) classNo = "";
        if(className == null) className = "";
        if(bornDate == null) bornDate = "";
        List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfoInfo(classNo, className, bornDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        classInfoDAO.CalculateTotalPageAndRecordNumber(classNo, className, bornDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = classInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = classInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("classNo", classNo);
        ctx.put("className", className);
        ctx.put("bornDate", bornDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ClassInfo��Ϣ*/
    public String ModifyClassInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classNo��ȡClassInfo����*/
        ClassInfo classInfo = classInfoDAO.GetClassInfoByClassNo(classNo);

        ctx.put("classInfo",  classInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ClassInfo��Ϣ*/
    public String FrontShowClassInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classNo��ȡClassInfo����*/
        ClassInfo classInfo = classInfoDAO.GetClassInfoByClassNo(classNo);

        ctx.put("classInfo",  classInfo);
        return "front_show_view";
    }

    /*�����޸�ClassInfo��Ϣ*/
    public String ModifyClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            classInfoDAO.UpdateClassInfo(classInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ClassInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ClassInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ClassInfo��Ϣ*/
    public String DeleteClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            classInfoDAO.DeleteClassInfo(classNo);
            ctx.put("message",  java.net.URLEncoder.encode("ClassInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ClassInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
