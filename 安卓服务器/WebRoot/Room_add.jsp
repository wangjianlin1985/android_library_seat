<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加阅览室</TITLE> 
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
    var roomName = document.getElementById("room.roomName").value;
    if(roomName=="") {
        alert('请输入阅览室名称!');
        return false;
    }
    var roomPlace = document.getElementById("room.roomPlace").value;
    if(roomPlace=="") {
        alert('请输入阅览室位置!');
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
    <s:form action="Room/Room_AddRoom.action" method="post" id="roomAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>阅览室名称:</td>
    <td width=70%><input id="room.roomName" name="room.roomName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>阅览室照片:</td>
    <td width=70%><input id="roomPhotoFile" name="roomPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>阅览室位置:</td>
    <td width=70%><input id="room.roomPlace" name="room.roomPlace" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>总座位数:</td>
    <td width=70%><input id="room.seatNum" name="room.seatNum" type="text" size="8" /></td>
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
