<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Room" %>
<%@ page import="com.chengxusheji.domain.SeatState" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Room信息
    List<Room> roomList = (List<Room>)request.getAttribute("roomList");
    //获取所有的SeatState信息
    List<SeatState> seatStateList = (List<SeatState>)request.getAttribute("seatStateList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加座位</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var seatCode = document.getElementById("seat.seatCode").value;
    if(seatCode=="") {
        alert('请输入座位编号!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="Seat/Seat_AddSeat.action" method="post" id="seatAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>所在阅览室:</td>
    <td width=70%>
      <select name="seat.roomObj.roomId">
      <%
        for(Room room:roomList) {
      %>
          <option value='<%=room.getRoomId() %>'><%=room.getRoomName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>座位编号:</td>
    <td width=70%><input id="seat.seatCode" name="seat.seatCode" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>当前状态:</td>
    <td width=70%>
      <select name="seat.seatStateObj.stateId">
      <%
        for(SeatState seatState:seatStateList) {
      %>
          <option value='<%=seatState.getStateId() %>'><%=seatState.getStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
