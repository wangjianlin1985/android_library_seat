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

    /*�������Ҫ��ѯ������: ����������*/
    private Room roomObj;
    public void setRoomObj(Room roomObj) {
        this.roomObj = roomObj;
    }
    public Room getRoomObj() {
        return this.roomObj;
    }

    /*�������Ҫ��ѯ������: ��λ���*/
    private String seatCode;
    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }
    public String getSeatCode() {
        return this.seatCode;
    }

    /*�������Ҫ��ѯ������: ��ǰ״̬*/
    private SeatState seatStateObj;
    public void setSeatStateObj(SeatState seatStateObj) {
        this.seatStateObj = seatStateObj;
    }
    public SeatState getSeatStateObj() {
        return this.seatStateObj;
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

    private int seatId;
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
    public int getSeatId() {
        return seatId;
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
    @Resource RoomDAO roomDAO;
    @Resource SeatStateDAO seatStateDAO;
    @Resource SeatDAO seatDAO;

    /*��������Seat����*/
    private Seat seat;
    public void setSeat(Seat seat) {
        this.seat = seat;
    }
    public Seat getSeat() {
        return this.seat;
    }

    /*��ת�����Seat��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Room��Ϣ*/
        List<Room> roomList = roomDAO.QueryAllRoomInfo();
        ctx.put("roomList", roomList);
        /*��ѯ���е�SeatState��Ϣ*/
        List<SeatState> seatStateList = seatStateDAO.QueryAllSeatStateInfo();
        ctx.put("seatStateList", seatStateList);
        return "add_view";
    }

    /*���Seat��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSeat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Room roomObj = roomDAO.GetRoomByRoomId(seat.getRoomObj().getRoomId());
            seat.setRoomObj(roomObj);
            SeatState seatStateObj = seatStateDAO.GetSeatStateByStateId(seat.getSeatStateObj().getStateId());
            seat.setSeatStateObj(seatStateObj);
            seatDAO.AddSeat(seat);
            ctx.put("message",  java.net.URLEncoder.encode("Seat��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seat���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSeat��Ϣ*/
    public String QuerySeat() {
        if(currentPage == 0) currentPage = 1;
        if(seatCode == null) seatCode = "";
        List<Seat> seatList = seatDAO.QuerySeatInfo(roomObj, seatCode, seatStateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        seatDAO.CalculateTotalPageAndRecordNumber(roomObj, seatCode, seatStateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = seatDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QuerySeatOutputToExcel() { 
        if(seatCode == null) seatCode = "";
        List<Seat> seatList = seatDAO.QuerySeatInfo(roomObj,seatCode,seatStateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Seat��Ϣ��¼"; 
        String[] headers = { "��λid","����������","��λ���","��ǰ״̬"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Seat.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSeat��Ϣ*/
    public String FrontQuerySeat() {
        if(currentPage == 0) currentPage = 1;
        if(seatCode == null) seatCode = "";
        List<Seat> seatList = seatDAO.QuerySeatInfo(roomObj, seatCode, seatStateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        seatDAO.CalculateTotalPageAndRecordNumber(roomObj, seatCode, seatStateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = seatDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Seat��Ϣ*/
    public String ModifySeatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������seatId��ȡSeat����*/
        Seat seat = seatDAO.GetSeatBySeatId(seatId);

        List<Room> roomList = roomDAO.QueryAllRoomInfo();
        ctx.put("roomList", roomList);
        List<SeatState> seatStateList = seatStateDAO.QueryAllSeatStateInfo();
        ctx.put("seatStateList", seatStateList);
        ctx.put("seat",  seat);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Seat��Ϣ*/
    public String FrontShowSeatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������seatId��ȡSeat����*/
        Seat seat = seatDAO.GetSeatBySeatId(seatId);

        List<Room> roomList = roomDAO.QueryAllRoomInfo();
        ctx.put("roomList", roomList);
        List<SeatState> seatStateList = seatStateDAO.QueryAllSeatStateInfo();
        ctx.put("seatStateList", seatStateList);
        ctx.put("seat",  seat);
        return "front_show_view";
    }

    /*�����޸�Seat��Ϣ*/
    public String ModifySeat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Room roomObj = roomDAO.GetRoomByRoomId(seat.getRoomObj().getRoomId());
            seat.setRoomObj(roomObj);
            SeatState seatStateObj = seatStateDAO.GetSeatStateByStateId(seat.getSeatStateObj().getStateId());
            seat.setSeatStateObj(seatStateObj);
            seatDAO.UpdateSeat(seat);
            ctx.put("message",  java.net.URLEncoder.encode("Seat��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seat��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Seat��Ϣ*/
    public String DeleteSeat() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            seatDAO.DeleteSeat(seatId);
            ctx.put("message",  java.net.URLEncoder.encode("Seatɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Seatɾ��ʧ��!"));
            return "error";
        }
    }

}
