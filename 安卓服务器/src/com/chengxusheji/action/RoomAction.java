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
import com.chengxusheji.dao.RoomDAO;
import com.chengxusheji.domain.Room;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class RoomAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�roomPhoto��������*/
	private File roomPhotoFile;
	private String roomPhotoFileFileName;
	private String roomPhotoFileContentType;
	public File getRoomPhotoFile() {
		return roomPhotoFile;
	}
	public void setRoomPhotoFile(File roomPhotoFile) {
		this.roomPhotoFile = roomPhotoFile;
	}
	public String getRoomPhotoFileFileName() {
		return roomPhotoFileFileName;
	}
	public void setRoomPhotoFileFileName(String roomPhotoFileFileName) {
		this.roomPhotoFileFileName = roomPhotoFileFileName;
	}
	public String getRoomPhotoFileContentType() {
		return roomPhotoFileContentType;
	}
	public void setRoomPhotoFileContentType(String roomPhotoFileContentType) {
		this.roomPhotoFileContentType = roomPhotoFileContentType;
	}
    /*�������Ҫ��ѯ������: ������λ��*/
    private String roomPlace;
    public void setRoomPlace(String roomPlace) {
        this.roomPlace = roomPlace;
    }
    public String getRoomPlace() {
        return this.roomPlace;
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

    private int roomId;
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public int getRoomId() {
        return roomId;
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

    /*��������Room����*/
    private Room room;
    public void setRoom(Room room) {
        this.room = room;
    }
    public Room getRoom() {
        return this.room;
    }

    /*��ת�����Room��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Room��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddRoom() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*������������Ƭ�ϴ�*/
            String roomPhotoPath = "upload/noimage.jpg"; 
       	 	if(roomPhotoFile != null)
       	 		roomPhotoPath = photoUpload(roomPhotoFile,roomPhotoFileContentType);
       	 	room.setRoomPhoto(roomPhotoPath);
            roomDAO.AddRoom(room);
            ctx.put("message",  java.net.URLEncoder.encode("Room��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Room���ʧ��!"));
            return "error";
        }
    }

    /*��ѯRoom��Ϣ*/
    public String QueryRoom() {
        if(currentPage == 0) currentPage = 1;
        if(roomPlace == null) roomPlace = "";
        List<Room> roomList = roomDAO.QueryRoomInfo(roomPlace, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        roomDAO.CalculateTotalPageAndRecordNumber(roomPlace);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = roomDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = roomDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomList",  roomList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("roomPlace", roomPlace);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryRoomOutputToExcel() { 
        if(roomPlace == null) roomPlace = "";
        List<Room> roomList = roomDAO.QueryRoomInfo(roomPlace);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Room��Ϣ��¼"; 
        String[] headers = { "������id","����������","��������Ƭ","������λ��","����λ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<roomList.size();i++) {
        	Room room = roomList.get(i); 
        	dataset.add(new String[]{room.getRoomId() + "",room.getRoomName(),room.getRoomPhoto(),room.getRoomPlace(),room.getSeatNum() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"Room.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯRoom��Ϣ*/
    public String FrontQueryRoom() {
        if(currentPage == 0) currentPage = 1;
        if(roomPlace == null) roomPlace = "";
        List<Room> roomList = roomDAO.QueryRoomInfo(roomPlace, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        roomDAO.CalculateTotalPageAndRecordNumber(roomPlace);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = roomDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = roomDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomList",  roomList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("roomPlace", roomPlace);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Room��Ϣ*/
    public String ModifyRoomQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������roomId��ȡRoom����*/
        Room room = roomDAO.GetRoomByRoomId(roomId);

        ctx.put("room",  room);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Room��Ϣ*/
    public String FrontShowRoomQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������roomId��ȡRoom����*/
        Room room = roomDAO.GetRoomByRoomId(roomId);

        ctx.put("room",  room);
        return "front_show_view";
    }

    /*�����޸�Room��Ϣ*/
    public String ModifyRoom() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*������������Ƭ�ϴ�*/
            if(roomPhotoFile != null) {
            	String roomPhotoPath = photoUpload(roomPhotoFile,roomPhotoFileContentType);
            	room.setRoomPhoto(roomPhotoPath);
            }
            roomDAO.UpdateRoom(room);
            ctx.put("message",  java.net.URLEncoder.encode("Room��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Room��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Room��Ϣ*/
    public String DeleteRoom() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            roomDAO.DeleteRoom(roomId);
            ctx.put("message",  java.net.URLEncoder.encode("Roomɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Roomɾ��ʧ��!"));
            return "error";
        }
    }

}
