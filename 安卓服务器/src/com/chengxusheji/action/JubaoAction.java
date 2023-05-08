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

    /*�������Ҫ��ѯ������: �ٱ�����*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: �ٱ��û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: �ٱ�ʱ��*/
    private String jubaoTime;
    public void setJubaoTime(String jubaoTime) {
        this.jubaoTime = jubaoTime;
    }
    public String getJubaoTime() {
        return this.jubaoTime;
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

    private int jubaoId;
    public void setJubaoId(int jubaoId) {
        this.jubaoId = jubaoId;
    }
    public int getJubaoId() {
        return jubaoId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource JubaoDAO jubaoDAO;

    /*��������Jubao����*/
    private Jubao jubao;
    public void setJubao(Jubao jubao) {
        this.jubao = jubao;
    }
    public Jubao getJubao() {
        return this.jubao;
    }

    /*��ת�����Jubao��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���Jubao��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddJubao() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(jubao.getUserObj().getUser_name());
            jubao.setUserObj(userObj);
            jubaoDAO.AddJubao(jubao);
            ctx.put("message",  java.net.URLEncoder.encode("Jubao��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jubao���ʧ��!"));
            return "error";
        }
    }

    /*��ѯJubao��Ϣ*/
    public String QueryJubao() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(jubaoTime == null) jubaoTime = "";
        List<Jubao> jubaoList = jubaoDAO.QueryJubaoInfo(title, userObj, jubaoTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jubaoDAO.CalculateTotalPageAndRecordNumber(title, userObj, jubaoTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jubaoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryJubaoOutputToExcel() { 
        if(title == null) title = "";
        if(jubaoTime == null) jubaoTime = "";
        List<Jubao> jubaoList = jubaoDAO.QueryJubaoInfo(title,userObj,jubaoTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Jubao��Ϣ��¼"; 
        String[] headers = { "�ٱ�id","�ٱ�����","�ٱ��û�","�ٱ�ʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Jubao.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯJubao��Ϣ*/
    public String FrontQueryJubao() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(jubaoTime == null) jubaoTime = "";
        List<Jubao> jubaoList = jubaoDAO.QueryJubaoInfo(title, userObj, jubaoTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jubaoDAO.CalculateTotalPageAndRecordNumber(title, userObj, jubaoTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jubaoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Jubao��Ϣ*/
    public String ModifyJubaoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������jubaoId��ȡJubao����*/
        Jubao jubao = jubaoDAO.GetJubaoByJubaoId(jubaoId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jubao",  jubao);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Jubao��Ϣ*/
    public String FrontShowJubaoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������jubaoId��ȡJubao����*/
        Jubao jubao = jubaoDAO.GetJubaoByJubaoId(jubaoId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("jubao",  jubao);
        return "front_show_view";
    }

    /*�����޸�Jubao��Ϣ*/
    public String ModifyJubao() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(jubao.getUserObj().getUser_name());
            jubao.setUserObj(userObj);
            jubaoDAO.UpdateJubao(jubao);
            ctx.put("message",  java.net.URLEncoder.encode("Jubao��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jubao��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Jubao��Ϣ*/
    public String DeleteJubao() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jubaoDAO.DeleteJubao(jubaoId);
            ctx.put("message",  java.net.URLEncoder.encode("Jubaoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Jubaoɾ��ʧ��!"));
            return "error";
        }
    }

}
