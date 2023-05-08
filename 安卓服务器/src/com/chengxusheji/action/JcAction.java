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
import com.chengxusheji.dao.JcDAO;
import com.chengxusheji.domain.Jc;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class JcAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��������*/
    private String jcType;
    public void setJcType(String jcType) {
        this.jcType = jcType;
    }
    public String getJcType() {
        return this.jcType;
    }

    /*�������Ҫ��ѯ������: ���ͱ���*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: �����û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String jcTime;
    public void setJcTime(String jcTime) {
        this.jcTime = jcTime;
    }
    public String getJcTime() {
        return this.jcTime;
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

    private int jcId;
    public void setJcId(int jcId) {
        this.jcId = jcId;
    }
    public int getJcId() {
        return jcId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource JcDAO jcDAO;

    /*��������Jc����*/
    private Jc jc;
    public void setJc(Jc jc) {
        this.jc = jc;
    }
    public Jc getJc() {
        return this.jc;
    }

    /*��ת�����Jc��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���Jc��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddJc() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(jc.getUserObj().getUser_name());
            jc.setUserObj(userObj);
            jcDAO.AddJc(jc);
            ctx.put("message",  java.net.URLEncoder.encode("Jc��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jc���ʧ��!"));
            return "error";
        }
    }

    /*��ѯJc��Ϣ*/
    public String QueryJc() {
        if(currentPage == 0) currentPage = 1;
        if(jcType == null) jcType = "";
        if(title == null) title = "";
        if(jcTime == null) jcTime = "";
        List<Jc> jcList = jcDAO.QueryJcInfo(jcType, title, userObj, jcTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jcDAO.CalculateTotalPageAndRecordNumber(jcType, title, userObj, jcTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jcDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = jcDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jcList",  jcList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("jcType", jcType);
        ctx.put("title", title);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jcTime", jcTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryJcOutputToExcel() { 
        if(jcType == null) jcType = "";
        if(title == null) title = "";
        if(jcTime == null) jcTime = "";
        List<Jc> jcList = jcDAO.QueryJcInfo(jcType,title,userObj,jcTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Jc��Ϣ��¼"; 
        String[] headers = { "����id","��������","���ͱ���","�����û�","���÷���","����ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<jcList.size();i++) {
        	Jc jc = jcList.get(i); 
        	dataset.add(new String[]{jc.getJcId() + "",jc.getJcType(),jc.getTitle(),jc.getUserObj().getName(),
jc.getCreditScore() + "",jc.getJcTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Jc.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯJc��Ϣ*/
    public String FrontQueryJc() {
        if(currentPage == 0) currentPage = 1;
        if(jcType == null) jcType = "";
        if(title == null) title = "";
        if(jcTime == null) jcTime = "";
        List<Jc> jcList = jcDAO.QueryJcInfo(jcType, title, userObj, jcTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jcDAO.CalculateTotalPageAndRecordNumber(jcType, title, userObj, jcTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jcDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = jcDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jcList",  jcList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("jcType", jcType);
        ctx.put("title", title);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jcTime", jcTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Jc��Ϣ*/
    public String ModifyJcQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������jcId��ȡJc����*/
        Jc jc = jcDAO.GetJcByJcId(jcId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jc",  jc);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Jc��Ϣ*/
    public String FrontShowJcQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������jcId��ȡJc����*/
        Jc jc = jcDAO.GetJcByJcId(jcId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jc",  jc);
        return "front_show_view";
    }

    /*�����޸�Jc��Ϣ*/
    public String ModifyJc() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(jc.getUserObj().getUser_name());
            jc.setUserObj(userObj);
            jcDAO.UpdateJc(jc);
            ctx.put("message",  java.net.URLEncoder.encode("Jc��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jc��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Jc��Ϣ*/
    public String DeleteJc() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jcDAO.DeleteJc(jcId);
            ctx.put("message",  java.net.URLEncoder.encode("Jcɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jcɾ��ʧ��!"));
            return "error";
        }
    }

}
