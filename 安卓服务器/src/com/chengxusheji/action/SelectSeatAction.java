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

    /*�������Ҫ��ѯ������: ��λ���*/
    private Seat seatObj;
    public void setSeatObj(Seat seatObj) {
        this.seatObj = seatObj;
    }
    public Seat getSeatObj() {
        return this.seatObj;
    }

    /*�������Ҫ��ѯ������: ѡ���û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ѡ����ʼʱ��*/
    private String startTime;
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getStartTime() {
        return this.startTime;
    }

    /*�������Ҫ��ѯ������: ѡ������ʱ��*/
    private String endTime;
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getEndTime() {
        return this.endTime;
    }

    /*�������Ҫ��ѯ������: ѡ��״̬*/
    private String seatState;
    public void setSeatState(String seatState) {
        this.seatState = seatState;
    }
    public String getSeatState() {
        return this.seatState;
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

    private int selectId;
    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }
    public int getSelectId() {
        return selectId;
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
    @Resource SeatDAO seatDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource SelectSeatDAO selectSeatDAO;

    /*��������SelectSeat����*/
    private SelectSeat selectSeat;
    public void setSelectSeat(SelectSeat selectSeat) {
        this.selectSeat = selectSeat;
    }
    public SelectSeat getSelectSeat() {
        return this.selectSeat;
    }

    /*��ת�����SelectSeat��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Seat��Ϣ*/
        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���SelectSeat��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSelectSeat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seat seatObj = seatDAO.GetSeatBySeatId(selectSeat.getSeatObj().getSeatId());
            selectSeat.setSeatObj(seatObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(selectSeat.getUserObj().getUser_name());
            selectSeat.setUserObj(userObj);
            selectSeatDAO.AddSelectSeat(selectSeat);
            ctx.put("message",  java.net.URLEncoder.encode("SelectSeat��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectSeat���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSelectSeat��Ϣ*/
    public String QuerySelectSeat() {
        if(currentPage == 0) currentPage = 1;
        if(startTime == null) startTime = "";
        if(endTime == null) endTime = "";
        if(seatState == null) seatState = "";
        List<SelectSeat> selectSeatList = selectSeatDAO.QuerySelectSeatInfo(seatObj, userObj, startTime, endTime, seatState, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        selectSeatDAO.CalculateTotalPageAndRecordNumber(seatObj, userObj, startTime, endTime, seatState);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = selectSeatDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QuerySelectSeatOutputToExcel() { 
        if(startTime == null) startTime = "";
        if(endTime == null) endTime = "";
        if(seatState == null) seatState = "";
        List<SelectSeat> selectSeatList = selectSeatDAO.QuerySelectSeatInfo(seatObj,userObj,startTime,endTime,seatState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SelectSeat��Ϣ��¼"; 
        String[] headers = { "ѡ��id","��λ���","ѡ���û�","ѡ����ʼʱ��","ѡ������ʱ��","ѡ��״̬"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"SelectSeat.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSelectSeat��Ϣ*/
    public String FrontQuerySelectSeat() {
        if(currentPage == 0) currentPage = 1;
        if(startTime == null) startTime = "";
        if(endTime == null) endTime = "";
        if(seatState == null) seatState = "";
        List<SelectSeat> selectSeatList = selectSeatDAO.QuerySelectSeatInfo(seatObj, userObj, startTime, endTime, seatState, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        selectSeatDAO.CalculateTotalPageAndRecordNumber(seatObj, userObj, startTime, endTime, seatState);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = selectSeatDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�SelectSeat��Ϣ*/
    public String ModifySelectSeatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������selectId��ȡSelectSeat����*/
        SelectSeat selectSeat = selectSeatDAO.GetSelectSeatBySelectId(selectId);

        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("selectSeat",  selectSeat);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SelectSeat��Ϣ*/
    public String FrontShowSelectSeatQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������selectId��ȡSelectSeat����*/
        SelectSeat selectSeat = selectSeatDAO.GetSelectSeatBySelectId(selectId);

        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("selectSeat",  selectSeat);
        return "front_show_view";
    }

    /*�����޸�SelectSeat��Ϣ*/
    public String ModifySelectSeat() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seat seatObj = seatDAO.GetSeatBySeatId(selectSeat.getSeatObj().getSeatId());
            selectSeat.setSeatObj(seatObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(selectSeat.getUserObj().getUser_name());
            selectSeat.setUserObj(userObj);
            selectSeatDAO.UpdateSelectSeat(selectSeat);
            ctx.put("message",  java.net.URLEncoder.encode("SelectSeat��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectSeat��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SelectSeat��Ϣ*/
    public String DeleteSelectSeat() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            selectSeatDAO.DeleteSelectSeat(selectId);
            ctx.put("message",  java.net.URLEncoder.encode("SelectSeatɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SelectSeatɾ��ʧ��!"));
            return "error";
        }
    }

}
