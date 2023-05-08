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

    /*界面层需要查询的属性: 奖惩类型*/
    private String jcType;
    public void setJcType(String jcType) {
        this.jcType = jcType;
    }
    public String getJcType() {
        return this.jcType;
    }

    /*界面层需要查询的属性: 奖惩标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 奖惩用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 奖惩时间*/
    private String jcTime;
    public void setJcTime(String jcTime) {
        this.jcTime = jcTime;
    }
    public String getJcTime() {
        return this.jcTime;
    }

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

    private int jcId;
    public void setJcId(int jcId) {
        this.jcId = jcId;
    }
    public int getJcId() {
        return jcId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource JcDAO jcDAO;

    /*待操作的Jc对象*/
    private Jc jc;
    public void setJc(Jc jc) {
        this.jc = jc;
    }
    public Jc getJc() {
        return this.jc;
    }

    /*跳转到添加Jc视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加Jc信息*/
    @SuppressWarnings("deprecation")
    public String AddJc() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(jc.getUserObj().getUser_name());
            jc.setUserObj(userObj);
            jcDAO.AddJc(jc);
            ctx.put("message",  java.net.URLEncoder.encode("Jc添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jc添加失败!"));
            return "error";
        }
    }

    /*查询Jc信息*/
    public String QueryJc() {
        if(currentPage == 0) currentPage = 1;
        if(jcType == null) jcType = "";
        if(title == null) title = "";
        if(jcTime == null) jcTime = "";
        List<Jc> jcList = jcDAO.QueryJcInfo(jcType, title, userObj, jcTime, currentPage);
        /*计算总的页数和总的记录数*/
        jcDAO.CalculateTotalPageAndRecordNumber(jcType, title, userObj, jcTime);
        /*获取到总的页码数目*/
        totalPage = jcDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryJcOutputToExcel() { 
        if(jcType == null) jcType = "";
        if(title == null) title = "";
        if(jcTime == null) jcTime = "";
        List<Jc> jcList = jcDAO.QueryJcInfo(jcType,title,userObj,jcTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Jc信息记录"; 
        String[] headers = { "奖惩id","奖惩类型","奖惩标题","奖惩用户","信用分数","奖惩时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Jc.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Jc信息*/
    public String FrontQueryJc() {
        if(currentPage == 0) currentPage = 1;
        if(jcType == null) jcType = "";
        if(title == null) title = "";
        if(jcTime == null) jcTime = "";
        List<Jc> jcList = jcDAO.QueryJcInfo(jcType, title, userObj, jcTime, currentPage);
        /*计算总的页数和总的记录数*/
        jcDAO.CalculateTotalPageAndRecordNumber(jcType, title, userObj, jcTime);
        /*获取到总的页码数目*/
        totalPage = jcDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Jc信息*/
    public String ModifyJcQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键jcId获取Jc对象*/
        Jc jc = jcDAO.GetJcByJcId(jcId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jc",  jc);
        return "modify_view";
    }

    /*查询要修改的Jc信息*/
    public String FrontShowJcQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键jcId获取Jc对象*/
        Jc jc = jcDAO.GetJcByJcId(jcId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jc",  jc);
        return "front_show_view";
    }

    /*更新修改Jc信息*/
    public String ModifyJc() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(jc.getUserObj().getUser_name());
            jc.setUserObj(userObj);
            jcDAO.UpdateJc(jc);
            ctx.put("message",  java.net.URLEncoder.encode("Jc信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jc信息更新失败!"));
            return "error";
       }
   }

    /*删除Jc信息*/
    public String DeleteJc() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jcDAO.DeleteJc(jcId);
            ctx.put("message",  java.net.URLEncoder.encode("Jc删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jc删除失败!"));
            return "error";
        }
    }

}
