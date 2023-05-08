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
import com.chengxusheji.dao.JubaoDAO;
import com.chengxusheji.domain.Jubao;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class JubaoAction extends BaseAction {

    /*界面层需要查询的属性: 举报标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 举报用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 举报时间*/
    private String jubaoTime;
    public void setJubaoTime(String jubaoTime) {
        this.jubaoTime = jubaoTime;
    }
    public String getJubaoTime() {
        return this.jubaoTime;
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

    private int jubaoId;
    public void setJubaoId(int jubaoId) {
        this.jubaoId = jubaoId;
    }
    public int getJubaoId() {
        return jubaoId;
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
    @Resource JubaoDAO jubaoDAO;

    /*待操作的Jubao对象*/
    private Jubao jubao;
    public void setJubao(Jubao jubao) {
        this.jubao = jubao;
    }
    public Jubao getJubao() {
        return this.jubao;
    }

    /*跳转到添加Jubao视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加Jubao信息*/
    @SuppressWarnings("deprecation")
    public String AddJubao() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(jubao.getUserObj().getUser_name());
            jubao.setUserObj(userObj);
            jubaoDAO.AddJubao(jubao);
            ctx.put("message",  java.net.URLEncoder.encode("Jubao添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jubao添加失败!"));
            return "error";
        }
    }

    /*查询Jubao信息*/
    public String QueryJubao() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(jubaoTime == null) jubaoTime = "";
        List<Jubao> jubaoList = jubaoDAO.QueryJubaoInfo(title, userObj, jubaoTime, currentPage);
        /*计算总的页数和总的记录数*/
        jubaoDAO.CalculateTotalPageAndRecordNumber(title, userObj, jubaoTime);
        /*获取到总的页码数目*/
        totalPage = jubaoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = jubaoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jubaoList",  jubaoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jubaoTime", jubaoTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryJubaoOutputToExcel() { 
        if(title == null) title = "";
        if(jubaoTime == null) jubaoTime = "";
        List<Jubao> jubaoList = jubaoDAO.QueryJubaoInfo(title,userObj,jubaoTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Jubao信息记录"; 
        String[] headers = { "举报id","举报标题","举报用户","举报时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<jubaoList.size();i++) {
        	Jubao jubao = jubaoList.get(i); 
        	dataset.add(new String[]{jubao.getJubaoId() + "",jubao.getTitle(),jubao.getUserObj().getName(),
jubao.getJubaoTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Jubao.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Jubao信息*/
    public String FrontQueryJubao() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(jubaoTime == null) jubaoTime = "";
        List<Jubao> jubaoList = jubaoDAO.QueryJubaoInfo(title, userObj, jubaoTime, currentPage);
        /*计算总的页数和总的记录数*/
        jubaoDAO.CalculateTotalPageAndRecordNumber(title, userObj, jubaoTime);
        /*获取到总的页码数目*/
        totalPage = jubaoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = jubaoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jubaoList",  jubaoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jubaoTime", jubaoTime);
        return "front_query_view";
    }

    /*查询要修改的Jubao信息*/
    public String ModifyJubaoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键jubaoId获取Jubao对象*/
        Jubao jubao = jubaoDAO.GetJubaoByJubaoId(jubaoId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jubao",  jubao);
        return "modify_view";
    }

    /*查询要修改的Jubao信息*/
    public String FrontShowJubaoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键jubaoId获取Jubao对象*/
        Jubao jubao = jubaoDAO.GetJubaoByJubaoId(jubaoId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jubao",  jubao);
        return "front_show_view";
    }

    /*更新修改Jubao信息*/
    public String ModifyJubao() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(jubao.getUserObj().getUser_name());
            jubao.setUserObj(userObj);
            jubaoDAO.UpdateJubao(jubao);
            ctx.put("message",  java.net.URLEncoder.encode("Jubao信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jubao信息更新失败!"));
            return "error";
       }
   }

    /*删除Jubao信息*/
    public String DeleteJubao() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jubaoDAO.DeleteJubao(jubaoId);
            ctx.put("message",  java.net.URLEncoder.encode("Jubao删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jubao删除失败!"));
            return "error";
        }
    }

}
