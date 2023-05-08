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
import com.chengxusheji.dao.SeatOrderDAO;
import com.chengxusheji.domain.SeatOrder;
import com.chengxusheji.dao.SeatDAO;
import com.chengxusheji.domain.Seat;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SeatOrderAction extends BaseAction {

    /*界面层需要查询的属性: 预约座位*/
    private Seat seatObj;
    public void setSeatObj(Seat seatObj) {
        this.seatObj = seatObj;
    }
    public Seat getSeatObj() {
        return this.seatObj;
    }

    /*界面层需要查询的属性: 预约日期*/
    private String orderDate;
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getOrderDate() {
        return this.orderDate;
    }

    /*界面层需要查询的属性: 提交预约时间*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
    }

    /*界面层需要查询的属性: 预约用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 预约状态*/
    private String orderState;
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
    public String getOrderState() {
        return this.orderState;
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

    private int orderId;
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
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
    @Resource SeatOrderDAO seatOrderDAO;

    /*待操作的SeatOrder对象*/
    private SeatOrder seatOrder;
    public void setSeatOrder(SeatOrder seatOrder) {
        this.seatOrder = seatOrder;
    }
    public SeatOrder getSeatOrder() {
        return this.seatOrder;
    }

    /*跳转到添加SeatOrder视图*/
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

    /*添加SeatOrder信息*/
    @SuppressWarnings("deprecation")
    public String AddSeatOrder() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seat seatObj = seatDAO.GetSeatBySeatId(seatOrder.getSeatObj().getSeatId());
            seatOrder.setSeatObj(seatObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(seatOrder.getUserObj().getUser_name());
            seatOrder.setUserObj(userObj);
            seatOrderDAO.AddSeatOrder(seatOrder);
            ctx.put("message",  java.net.URLEncoder.encode("SeatOrder添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatOrder添加失败!"));
            return "error";
        }
    }

    /*查询SeatOrder信息*/
    public String QuerySeatOrder() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        if(addTime == null) addTime = "";
        if(orderState == null) orderState = "";
        List<SeatOrder> seatOrderList = seatOrderDAO.QuerySeatOrderInfo(seatObj, orderDate, addTime, userObj, orderState, currentPage);
        /*计算总的页数和总的记录数*/
        seatOrderDAO.CalculateTotalPageAndRecordNumber(seatObj, orderDate, addTime, userObj, orderState);
        /*获取到总的页码数目*/
        totalPage = seatOrderDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = seatOrderDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("seatOrderList",  seatOrderList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("seatObj", seatObj);
        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        ctx.put("orderDate", orderDate);
        ctx.put("addTime", addTime);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("orderState", orderState);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySeatOrderOutputToExcel() { 
        if(orderDate == null) orderDate = "";
        if(addTime == null) addTime = "";
        if(orderState == null) orderState = "";
        List<SeatOrder> seatOrderList = seatOrderDAO.QuerySeatOrderInfo(seatObj,orderDate,addTime,userObj,orderState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SeatOrder信息记录"; 
        String[] headers = { "预约id","预约座位","预约日期","开始时间","结束时间","提交预约时间","预约用户","预约状态","管理回复"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<seatOrderList.size();i++) {
        	SeatOrder seatOrder = seatOrderList.get(i); 
        	dataset.add(new String[]{seatOrder.getOrderId() + "",seatOrder.getSeatObj().getSeatCode(),
new SimpleDateFormat("yyyy-MM-dd").format(seatOrder.getOrderDate()),seatOrder.getStartTime(),seatOrder.getEndTime(),seatOrder.getAddTime(),seatOrder.getUserObj().getName(),
seatOrder.getOrderState(),seatOrder.getReplyContent()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SeatOrder.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SeatOrder信息*/
    public String FrontQuerySeatOrder() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        if(addTime == null) addTime = "";
        if(orderState == null) orderState = "";
        List<SeatOrder> seatOrderList = seatOrderDAO.QuerySeatOrderInfo(seatObj, orderDate, addTime, userObj, orderState, currentPage);
        /*计算总的页数和总的记录数*/
        seatOrderDAO.CalculateTotalPageAndRecordNumber(seatObj, orderDate, addTime, userObj, orderState);
        /*获取到总的页码数目*/
        totalPage = seatOrderDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = seatOrderDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("seatOrderList",  seatOrderList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("seatObj", seatObj);
        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        ctx.put("orderDate", orderDate);
        ctx.put("addTime", addTime);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("orderState", orderState);
        return "front_query_view";
    }

    /*查询要修改的SeatOrder信息*/
    public String ModifySeatOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取SeatOrder对象*/
        SeatOrder seatOrder = seatOrderDAO.GetSeatOrderByOrderId(orderId);

        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("seatOrder",  seatOrder);
        return "modify_view";
    }

    /*查询要修改的SeatOrder信息*/
    public String FrontShowSeatOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取SeatOrder对象*/
        SeatOrder seatOrder = seatOrderDAO.GetSeatOrderByOrderId(orderId);

        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("seatOrder",  seatOrder);
        return "front_show_view";
    }

    /*更新修改SeatOrder信息*/
    public String ModifySeatOrder() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seat seatObj = seatDAO.GetSeatBySeatId(seatOrder.getSeatObj().getSeatId());
            seatOrder.setSeatObj(seatObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(seatOrder.getUserObj().getUser_name());
            seatOrder.setUserObj(userObj);
            seatOrderDAO.UpdateSeatOrder(seatOrder);
            ctx.put("message",  java.net.URLEncoder.encode("SeatOrder信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatOrder信息更新失败!"));
            return "error";
       }
   }

    /*删除SeatOrder信息*/
    public String DeleteSeatOrder() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            seatOrderDAO.DeleteSeatOrder(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("SeatOrder删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatOrder删除失败!"));
            return "error";
        }
    }

}
