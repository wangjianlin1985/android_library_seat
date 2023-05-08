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
import com.chengxusheji.dao.SeatStateDAO;
import com.chengxusheji.domain.SeatState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SeatStateAction extends BaseAction {

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

    private int stateId;
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public int getStateId() {
        return stateId;
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
    @Resource SeatStateDAO seatStateDAO;

    /*��������SeatState����*/
    private SeatState seatState;
    public void setSeatState(SeatState seatState) {
        this.seatState = seatState;
    }
    public SeatState getSeatState() {
        return this.seatState;
    }

    /*��ת�����SeatState��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���SeatState��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSeatState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            seatStateDAO.AddSeatState(seatState);
            ctx.put("message",  java.net.URLEncoder.encode("SeatState��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatState���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSeatState��Ϣ*/
    public String QuerySeatState() {
        if(currentPage == 0) currentPage = 1;
        List<SeatState> seatStateList = seatStateDAO.QuerySeatStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        seatStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = seatStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = seatStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("seatStateList",  seatStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QuerySeatStateOutputToExcel() { 
        List<SeatState> seatStateList = seatStateDAO.QuerySeatStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SeatState��Ϣ��¼"; 
        String[] headers = { "״̬id","״̬����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<seatStateList.size();i++) {
        	SeatState seatState = seatStateList.get(i); 
        	dataset.add(new String[]{seatState.getStateId() + "",seatState.getStateName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SeatState.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSeatState��Ϣ*/
    public String FrontQuerySeatState() {
        if(currentPage == 0) currentPage = 1;
        List<SeatState> seatStateList = seatStateDAO.QuerySeatStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        seatStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = seatStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = seatStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("seatStateList",  seatStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�SeatState��Ϣ*/
    public String ModifySeatStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡSeatState����*/
        SeatState seatState = seatStateDAO.GetSeatStateByStateId(stateId);

        ctx.put("seatState",  seatState);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SeatState��Ϣ*/
    public String FrontShowSeatStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡSeatState����*/
        SeatState seatState = seatStateDAO.GetSeatStateByStateId(stateId);

        ctx.put("seatState",  seatState);
        return "front_show_view";
    }

    /*�����޸�SeatState��Ϣ*/
    public String ModifySeatState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            seatStateDAO.UpdateSeatState(seatState);
            ctx.put("message",  java.net.URLEncoder.encode("SeatState��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatState��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SeatState��Ϣ*/
    public String DeleteSeatState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            seatStateDAO.DeleteSeatState(stateId);
            ctx.put("message",  java.net.URLEncoder.encode("SeatStateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatStateɾ��ʧ��!"));
            return "error";
        }
    }

}
