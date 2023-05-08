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
import com.chengxusheji.dao.SelectSeatDAO;
import com.chengxusheji.domain.SelectSeat;
import com.chengxusheji.dao.SeatDAO;
import com.chengxusheji.domain.Seat;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SelectSeatAction extends BaseAction {

    /*界面层需要查询的属性: 座位编号*/
    private Seat seatObj;
    public void setSeatObj(Seat seatObj) {
        this.seatObj = seatObj;
    }
    public Seat getSeatObj() {
        return this.seatObj;
    }

    /*界面层需要查询的属性: 选座用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 选座开始时间*/
    private String startTime;
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getStartTime() {
        return this.startTime;
    }

    /*界面层需要查询的属性: 选座结束时间*/
    private String endTime;
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getEndTime() {
        return this.endTime;
    }

    /*界面层需要查询的属性: 选座状态*/
    private String seatState;
    public void setSeatState(String seatState) {
        this.seatState = seatState;
    }
    public String getSeatState() {
        return this.seatState;
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

    private int selectId;
    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }
    public int getSelectId() {
        return selectId;
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
    @Resource SeatDAO seatDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource SelectSeatDAO selectSeatDAO;

    /*待操作的SelectSeat对象*/
    private SelectSeat selectSeat;
    public void setSelectSeat(SelectSeat selectSeat) {
        this.selectSeat = selectSeat;
    }
    public SelectSeat getSelectSeat() {
        return this.selectSeat;
    }

    /*跳转到添加SelectSeat视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Seat信息*/
        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加SelectSeat信息*/
    @SuppressWarnings("deprecation")
    public String AddSelectSeat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seat seatObj = seatDAO.GetSeatBySeatId(selectSeat.getSeatObj().getSeatId());
            selectSeat.setSeatObj(seatObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(selectSeat.getUserObj().getUser_name());
            selectSeat.setUserObj(userObj);
            selectSeatDAO.AddSelectSeat(selectSeat);
            ctx.put("message",  java.net.URLEncoder.encode("SelectSeat添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectSeat添加失败!"));
            return "error";
        }
    }

    /*查询SelectSeat信息*/
    public String QuerySelectSeat() {
        if(currentPage == 0) currentPage = 1;
        if(startTime == null) startTime = "";
        if(endTime == null) endTime = "";
        if(seatState == null) seatState = "";
        List<SelectSeat> selectSeatList = selectSeatDAO.QuerySelectSeatInfo(seatObj, userObj, startTime, endTime, seatState, currentPage);
        /*计算总的页数和总的记录数*/
        selectSeatDAO.CalculateTotalPageAndRecordNumber(seatObj, userObj, startTime, endTime, seatState);
        /*获取到总的页码数目*/
        totalPage = selectSeatDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = selectSeatDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("selectSeatList",  selectSeatList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("seatObj", seatObj);
        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("startTime", startTime);
        ctx.put("endTime", endTime);
        ctx.put("seatState", seatState);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySelectSeatOutputToExcel() { 
        if(startTime == null) startTime = "";
        if(endTime == null) endTime = "";
        if(seatState == null) seatState = "";
        List<SelectSeat> selectSeatList = selectSeatDAO.QuerySelectSeatInfo(seatObj,userObj,startTime,endTime,seatState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SelectSeat信息记录"; 
        String[] headers = { "选座id","座位编号","选座用户","选座开始时间","选座结束时间","选座状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<selectSeatList.size();i++) {
        	SelectSeat selectSeat = selectSeatList.get(i); 
        	dataset.add(new String[]{selectSeat.getSelectId() + "",selectSeat.getSeatObj().getSeatCode(),
selectSeat.getUserObj().getName(),
selectSeat.getStartTime(),selectSeat.getEndTime(),selectSeat.getSeatState()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SelectSeat.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SelectSeat信息*/
    public String FrontQuerySelectSeat() {
        if(currentPage == 0) currentPage = 1;
        if(startTime == null) startTime = "";
        if(endTime == null) endTime = "";
        if(seatState == null) seatState = "";
        List<SelectSeat> selectSeatList = selectSeatDAO.QuerySelectSeatInfo(seatObj, userObj, startTime, endTime, seatState, currentPage);
        /*计算总的页数和总的记录数*/
        selectSeatDAO.CalculateTotalPageAndRecordNumber(seatObj, userObj, startTime, endTime, seatState);
        /*获取到总的页码数目*/
        totalPage = selectSeatDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = selectSeatDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("selectSeatList",  selectSeatList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("seatObj", seatObj);
        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("startTime", startTime);
        ctx.put("endTime", endTime);
        ctx.put("seatState", seatState);
        return "front_query_view";
    }

    /*查询要修改的SelectSeat信息*/
    public String ModifySelectSeatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键selectId获取SelectSeat对象*/
        SelectSeat selectSeat = selectSeatDAO.GetSelectSeatBySelectId(selectId);

        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("selectSeat",  selectSeat);
        return "modify_view";
    }

    /*查询要修改的SelectSeat信息*/
    public String FrontShowSelectSeatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键selectId获取SelectSeat对象*/
        SelectSeat selectSeat = selectSeatDAO.GetSelectSeatBySelectId(selectId);

        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("selectSeat",  selectSeat);
        return "front_show_view";
    }

    /*更新修改SelectSeat信息*/
    public String ModifySelectSeat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seat seatObj = seatDAO.GetSeatBySeatId(selectSeat.getSeatObj().getSeatId());
            selectSeat.setSeatObj(seatObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(selectSeat.getUserObj().getUser_name());
            selectSeat.setUserObj(userObj);
            selectSeatDAO.UpdateSelectSeat(selectSeat);
            ctx.put("message",  java.net.URLEncoder.encode("SelectSeat信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectSeat信息更新失败!"));
            return "error";
       }
   }

    /*删除SelectSeat信息*/
    public String DeleteSelectSeat() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            selectSeatDAO.DeleteSelectSeat(selectId);
            ctx.put("message",  java.net.URLEncoder.encode("SelectSeat删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectSeat删除失败!"));
            return "error";
        }
    }

}
