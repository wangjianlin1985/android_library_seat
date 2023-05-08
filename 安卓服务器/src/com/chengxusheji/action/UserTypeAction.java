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

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource UserTypeDAO userTypeDAO;

    /*待操作的UserType对象*/
    private UserType userType;
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public UserType getUserType() {
        return this.userType;
    }

    /*跳转到添加UserType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加UserType信息*/
    @SuppressWarnings("deprecation")
    public String AddUserType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            userTypeDAO.AddUserType(userType);
            ctx.put("message",  java.net.URLEncoder.encode("UserType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserType添加失败!"));
            return "error";
        }
    }

    /*查询UserType信息*/
    public String QueryUserType() {
        if(currentPage == 0) currentPage = 1;
        List<UserType> userTypeList = userTypeDAO.QueryUserTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        userTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = userTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = userTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("userTypeList",  userTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryUserTypeOutputToExcel() { 
        List<UserType> userTypeList = userTypeDAO.QueryUserTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "UserType信息记录"; 
        String[] headers = { "用户类型id","用户类型名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"UserType.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询UserType信息*/
    public String FrontQueryUserType() {
        if(currentPage == 0) currentPage = 1;
        List<UserType> userTypeList = userTypeDAO.QueryUserTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        userTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = userTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = userTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("userTypeList",  userTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的UserType信息*/
    public String ModifyUserTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键userTypeId获取UserType对象*/
        UserType userType = userTypeDAO.GetUserTypeByUserTypeId(userTypeId);

        ctx.put("userType",  userType);
        return "modify_view";
    }

    /*查询要修改的UserType信息*/
    public String FrontShowUserTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键userTypeId获取UserType对象*/
        UserType userType = userTypeDAO.GetUserTypeByUserTypeId(userTypeId);

        ctx.put("userType",  userType);
        return "front_show_view";
    }

    /*更新修改UserType信息*/
    public String ModifyUserType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            userTypeDAO.UpdateUserType(userType);
            ctx.put("message",  java.net.URLEncoder.encode("UserType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserType信息更新失败!"));
            return "error";
       }
   }

    /*删除UserType信息*/
    public String DeleteUserType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            userTypeDAO.DeleteUserType(userTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("UserType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserType删除失败!"));
            return "error";
        }
    }

}
