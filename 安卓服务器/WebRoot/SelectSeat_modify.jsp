<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.SelectSeat" %>
<%@ page import="com.chengxusheji.domain.Seat" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Seat信息
    List<Seat> seatList = (List<Seat>)request.getAttribute("seatList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    SelectSeat selectSeat = (SelectSeat)request.getAttribute("selectSeat");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改选座</TITLE>
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
    var startTime = document.getElementById("selectSeat.startTime").value;
    if(startTime=="") {
        alert('请输入选座开始时间!');
        return false;
    }
    var endTime = document.getElementById("selectSeat.endTime").value;
    if(endTime=="") {
        alert('请输入选座结束时间!');
        return false;
    }
    var seatState = document.getElementById("selectSeat.seatState").value;
    if(seatState=="") {
        alert('请输入选座状态!');
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
    <TD align="left" vAlign=top ><s:form action="SelectSeat/SelectSeat_ModifySelectSeat.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>选座id:</td>
    <td width=70%><input id="selectSeat.selectId" name="selectSeat.selectId" type="text" value="<%=selectSeat.getSelectId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>座位编号:</td>
    <td width=70%>
      <select name="selectSeat.seatObj.seatId">
      <%
        for(Seat seat:seatList) {
          String selected = "";
          if(seat.getSeatId() == selectSeat.getSeatObj().getSeatId())
            selected = "selected";
      %>
          <option value='<%=seat.getSeatId() %>' <%=selected %>><%=seat.getSeatCode() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>选座用户:</td>
    <td width=70%>
      <select name="selectSeat.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(selectSeat.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>选座开始时间:</td>
    <td width=70%><input id="selectSeat.startTime" name="selectSeat.startTime" type="text" size="20" value='<%=selectSeat.getStartTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>选座结束时间:</td>
    <td width=70%><input id="selectSeat.endTime" name="selectSeat.endTime" type="text" size="20" value='<%=selectSeat.getEndTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>选座状态:</td>
    <td width=70%><input id="selectSeat.seatState" name="selectSeat.seatState" type="text" size="20" value='<%=selectSeat.getSeatState() %>'/></td>
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
