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
import com.chengxusheji.dao.UserTypeDAO;
import com.chengxusheji.domain.UserType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class UserTypeAction extends BaseAction {

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

    private int userTypeId;
    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }
    public int getUserTypeId() {
        return userTypeId;
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
    @Resource UserTypeDAO userTypeDAO;

    /*��������UserType����*/
    private UserType userType;
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public UserType getUserType() {
        return this.userType;
    }

    /*��ת�����UserType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���UserType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddUserType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            userTypeDAO.AddUserType(userType);
            ctx.put("message",  java.net.URLEncoder.encode("UserType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯUserType��Ϣ*/
    public String QueryUserType() {
        if(currentPage == 0) currentPage = 1;
        List<UserType> userTypeList = userTypeDAO.QueryUserTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        userTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = userTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = userTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("userTypeList",  userTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryUserTypeOutputToExcel() { 
        List<UserType> userTypeList = userTypeDAO.QueryUserTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "UserType��Ϣ��¼"; 
        String[] headers = { "�û�����id","�û���������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<userTypeList.size();i++) {
        	UserType userType = userTypeList.get(i); 
        	dataset.add(new String[]{userType.getUserTypeId() + "",userType.getUserTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"UserType.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯUserType��Ϣ*/
    public String FrontQueryUserType() {
        if(currentPage == 0) currentPage = 1;
        List<UserType> userTypeList = userTypeDAO.QueryUserTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        userTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = userTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = userTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("userTypeList",  userTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�UserType��Ϣ*/
    public String ModifyUserTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������userTypeId��ȡUserType����*/
        UserType userType = userTypeDAO.GetUserTypeByUserTypeId(userTypeId);

        ctx.put("userType",  userType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�UserType��Ϣ*/
    public String FrontShowUserTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������userTypeId��ȡUserType����*/
        UserType userType = userTypeDAO.GetUserTypeByUserTypeId(userTypeId);

        ctx.put("userType",  userType);
        return "front_show_view";
    }

    /*�����޸�UserType��Ϣ*/
    public String ModifyUserType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            userTypeDAO.UpdateUserType(userType);
            ctx.put("message",  java.net.URLEncoder.encode("UserType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��UserType��Ϣ*/
    public String DeleteUserType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            userTypeDAO.DeleteUserType(userTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("UserTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
