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

    /*�������Ҫ��ѯ������: ԤԼ��λ*/
    private Seat seatObj;
    public void setSeatObj(Seat seatObj) {
        this.seatObj = seatObj;
    }
    public Seat getSeatObj() {
        return this.seatObj;
    }

    /*�������Ҫ��ѯ������: ԤԼ����*/
    private String orderDate;
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getOrderDate() {
        return this.orderDate;
    }

    /*�������Ҫ��ѯ������: �ύԤԼʱ��*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
    }

    /*�������Ҫ��ѯ������: ԤԼ�û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ԤԼ״̬*/
    private String orderState;
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
    public String getOrderState() {
        return this.orderState;
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

    private int orderId;
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
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
    @Resource SeatOrderDAO seatOrderDAO;

    /*��������SeatOrder����*/
    private SeatOrder seatOrder;
    public void setSeatOrder(SeatOrder seatOrder) {
        this.seatOrder = seatOrder;
    }
    public SeatOrder getSeatOrder() {
        return this.seatOrder;
    }

    /*��ת�����SeatOrder��ͼ*/
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

    /*���SeatOrder��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSeatOrder() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seat seatObj = seatDAO.GetSeatBySeatId(seatOrder.getSeatObj().getSeatId());
            seatOrder.setSeatObj(seatObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(seatOrder.getUserObj().getUser_name());
            seatOrder.setUserObj(userObj);
            seatOrderDAO.AddSeatOrder(seatOrder);
            ctx.put("message",  java.net.URLEncoder.encode("SeatOrder��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatOrder���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSeatOrder��Ϣ*/
    public String QuerySeatOrder() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        if(addTime == null) addTime = "";
        if(orderState == null) orderState = "";
        List<SeatOrder> seatOrderList = seatOrderDAO.QuerySeatOrderInfo(seatObj, orderDate, addTime, userObj, orderState, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        seatOrderDAO.CalculateTotalPageAndRecordNumber(seatObj, orderDate, addTime, userObj, orderState);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = seatOrderDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QuerySeatOrderOutputToExcel() { 
        if(orderDate == null) orderDate = "";
        if(addTime == null) addTime = "";
        if(orderState == null) orderState = "";
        List<SeatOrder> seatOrderList = seatOrderDAO.QuerySeatOrderInfo(seatObj,orderDate,addTime,userObj,orderState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SeatOrder��Ϣ��¼"; 
        String[] headers = { "ԤԼid","ԤԼ��λ","ԤԼ����","��ʼʱ��","����ʱ��","�ύԤԼʱ��","ԤԼ�û�","ԤԼ״̬","����ظ�"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"SeatOrder.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSeatOrder��Ϣ*/
    public String FrontQuerySeatOrder() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        if(addTime == null) addTime = "";
        if(orderState == null) orderState = "";
        List<SeatOrder> seatOrderList = seatOrderDAO.QuerySeatOrderInfo(seatObj, orderDate, addTime, userObj, orderState, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        seatOrderDAO.CalculateTotalPageAndRecordNumber(seatObj, orderDate, addTime, userObj, orderState);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = seatOrderDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�SeatOrder��Ϣ*/
    public String ModifySeatOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡSeatOrder����*/
        SeatOrder seatOrder = seatOrderDAO.GetSeatOrderByOrderId(orderId);

        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("seatOrder",  seatOrder);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SeatOrder��Ϣ*/
    public String FrontShowSeatOrderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡSeatOrder����*/
        SeatOrder seatOrder = seatOrderDAO.GetSeatOrderByOrderId(orderId);

        List<Seat> seatList = seatDAO.QueryAllSeatInfo();
        ctx.put("seatList", seatList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("seatOrder",  seatOrder);
        return "front_show_view";
    }

    /*�����޸�SeatOrder��Ϣ*/
    public String ModifySeatOrder() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Seat seatObj = seatDAO.GetSeatBySeatId(seatOrder.getSeatObj().getSeatId());
            seatOrder.setSeatObj(seatObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(seatOrder.getUserObj().getUser_name());
            seatOrder.setUserObj(userObj);
            seatOrderDAO.UpdateSeatOrder(seatOrder);
            ctx.put("message",  java.net.URLEncoder.encode("SeatOrder��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatOrder��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SeatOrder��Ϣ*/
    public String DeleteSeatOrder() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            seatOrderDAO.DeleteSeatOrder(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("SeatOrderɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SeatOrderɾ��ʧ��!"));
            return "error";
        }
    }

}
