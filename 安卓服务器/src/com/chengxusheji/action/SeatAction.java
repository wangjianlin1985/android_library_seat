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
import com.chengxusheji.dao.SeatDAO;
import com.chengxusheji.domain.Seat;
import com.chengxusheji.dao.RoomDAO;
import com.chengxusheji.domain.Room;
import com.chengxusheji.dao.SeatStateDAO;
import com.chengxusheji.domain.SeatState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SeatAction extends BaseAction {

    /*界面层需要查询的属性: 所在阅览室*/
    private Room roomObj;
    public void setRoomObj(Room roomObj) {
        this.roomObj = roomObj;
    }
    public Room getRoomObj() {
        return this.roomObj;
    }

    /*界面层需要查询的属性: 座位编号*/
    private String seatCode;
    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }
    public String getSeatCode() {
        return this.seatCode;
    }

    /*界面层需要查询的属性: 当前状态*/
    private SeatState seatStateObj;
    public void setSeatStateObj(SeatState seatStateObj) {
        this.seatStateObj = seatStateObj;
    }
    public SeatState getSeatStateObj() {
        return this.seatStateObj;
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

    private int seatId;
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
    public int getSeatId() {
        return seatId;
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
    @Resource RoomDAO roomDAO;
    @Resource SeatStateDAO seatStateDAO;
    @Resource SeatDAO seatDAO;

    /*待操作的Seat对象*/
    private Seat seat;
    public void setSeat(Seat seat) {
        this.seat = seat;
    }
    public Seat getSeat() {
        return this.seat;
    }

    /*跳转到添加Seat视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Room信息*/
        List<Room> roomList = roomDAO.QueryAllRoomInfo();
        ctx.put("roomList", roomList);
        /*查询所有的SeatState信息*/
        List<SeatState> seatStateList = seatStateDAO.QueryAllSeatStateInfo();
        ctx.put("seatStateList", seatStateList);
        return "add_view";
    }

    /*添加Seat信息*/
    @SuppressWarnings("deprecation")
    public String AddSeat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Room roomObj = roomDAO.GetRoomByRoomId(seat.getRoomObj().getRoomId());
            seat.setRoomObj(roomObj);
            SeatState seatStateObj = seatStateDAO.GetSeatStateByStateId(seat.getSeatStateObj().getStateId());
            seat.setSeatStateObj(seatStateObj);
            seatDAO.AddSeat(seat);
            ctx.put("message",  java.net.URLEncoder.encode("Seat添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seat添加失败!"));
            return "error";
        }
    }

    /*查询Seat信息*/
    public String QuerySeat() {
        if(currentPage == 0) currentPage = 1;
        if(seatCode == null) seatCode = "";
        List<Seat> seatList = seatDAO.QuerySeatInfo(roomObj, seatCode, seatStateObj, currentPage);
        /*计算总的页数和总的记录数*/
        seatDAO.CalculateTotalPageAndRecordNumber(roomObj, seatCode, seatStateObj);
        /*获取到总的页码数目*/
        totalPage = seatDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = seatDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("seatList",  seatList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("roomObj", roomObj);
        List<Room> roomList = roomDAO.QueryAllRoomInfo();
        ctx.put("roomList", roomList);
        ctx.put("seatCode", seatCode);
        ctx.put("seatStateObj", seatStateObj);
        List<SeatState> seatStateList = seatStateDAO.QueryAllSeatStateInfo();
        ctx.put("seatStateList", seatStateList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySeatOutputToExcel() { 
        if(seatCode == null) seatCode = "";
        List<Seat> seatList = seatDAO.QuerySeatInfo(roomObj,seatCode,seatStateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Seat信息记录"; 
        String[] headers = { "座位id","所在阅览室","座位编号","当前状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<seatList.size();i++) {
        	Seat seat = seatList.get(i); 
        	dataset.add(new String[]{seat.getSeatId() + "",seat.getRoomObj().getRoomName(),
seat.getSeatCode(),seat.getSeatStateObj().getStateName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"Seat.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Seat信息*/
    public String FrontQuerySeat() {
        if(currentPage == 0) currentPage = 1;
        if(seatCode == null) seatCode = "";
        List<Seat> seatList = seatDAO.QuerySeatInfo(roomObj, seatCode, seatStateObj, currentPage);
        /*计算总的页数和总的记录数*/
        seatDAO.CalculateTotalPageAndRecordNumber(roomObj, seatCode, seatStateObj);
        /*获取到总的页码数目*/
        totalPage = seatDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = seatDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("seatList",  seatList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("roomObj", roomObj);
        List<Room> roomList = roomDAO.QueryAllRoomInfo();
        ctx.put("roomList", roomList);
        ctx.put("seatCode", seatCode);
        ctx.put("seatStateObj", seatStateObj);
        List<SeatState> seatStateList = seatStateDAO.QueryAllSeatStateInfo();
        ctx.put("seatStateList", seatStateList);
        return "front_query_view";
    }

    /*查询要修改的Seat信息*/
    public String ModifySeatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键seatId获取Seat对象*/
        Seat seat = seatDAO.GetSeatBySeatId(seatId);

        List<Room> roomList = roomDAO.QueryAllRoomInfo();
        ctx.put("roomList", roomList);
        List<SeatState> seatStateList = seatStateDAO.QueryAllSeatStateInfo();
        ctx.put("seatStateList", seatStateList);
        ctx.put("seat",  seat);
        return "modify_view";
    }

    /*查询要修改的Seat信息*/
    public String FrontShowSeatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键seatId获取Seat对象*/
        Seat seat = seatDAO.GetSeatBySeatId(seatId);

        List<Room> roomList = roomDAO.QueryAllRoomInfo();
        ctx.put("roomList", roomList);
        List<SeatState> seatStateList = seatStateDAO.QueryAllSeatStateInfo();
        ctx.put("seatStateList", seatStateList);
        ctx.put("seat",  seat);
        return "front_show_view";
    }

    /*更新修改Seat信息*/
    public String ModifySeat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Room roomObj = roomDAO.GetRoomByRoomId(seat.getRoomObj().getRoomId());
            seat.setRoomObj(roomObj);
            SeatState seatStateObj = seatStateDAO.GetSeatStateByStateId(seat.getSeatStateObj().getStateId());
            seat.setSeatStateObj(seatStateObj);
            seatDAO.UpdateSeat(seat);
            ctx.put("message",  java.net.URLEncoder.encode("Seat信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seat信息更新失败!"));
            return "error";
       }
   }

    /*删除Seat信息*/
    public String DeleteSeat() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            seatDAO.DeleteSeat(seatId);
            ctx.put("message",  java.net.URLEncoder.encode("Seat删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seat删除失败!"));
            return "error";
        }
    }

}
