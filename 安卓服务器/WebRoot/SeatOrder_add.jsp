<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Seat" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Seat信息
    List<Seat> seatList = (List<Seat>)request.getAttribute("seatList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加座位预约</TITLE> 
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
    var startTime = document.getElementById("seatOrder.startTime").value;
    if(startTime=="") {
        alert('请输入开始时间!');
        return false;
    }
    var endTime = document.getElementById("seatOrder.endTime").value;
    if(endTime=="") {
        alert('请输入结束时间!');
        return false;
    }
    var addTime = document.getElementById("seatOrder.addTime").value;
    if(addTime=="") {
        alert('请输入提交预约时间!');
        return false;
    }
    var orderState = document.getElementById("seatOrder.orderState").value;
    if(orderState=="") {
        alert('请输入预约状态!');
        return false;
    }
    var replyContent = document.getElementById("seatOrder.replyContent").value;
    if(replyContent=="") {
        alert('请输入管理回复!');
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
    <s:form action="SeatOrder/SeatOrder_AddSeatOrder.action" method="post" id="seatOrderAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>预约座位:</td>
    <td width=70%>
      <select name="seatOrder.seatObj.seatId">
      <%
        for(Seat seat:seatList) {
      %>
          <option value='<%=seat.getSeatId() %>'><%=seat.getSeatCode() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>预约日期:</td>
    <td width=70%><input type="text" readonly id="seatOrder.orderDate"  name="seatOrder.orderDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>开始时间:</td>
    <td width=70%><input id="seatOrder.startTime" name="seatOrder.startTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>结束时间:</td>
    <td width=70%><input id="seatOrder.endTime" name="seatOrder.endTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>提交预约时间:</td>
    <td width=70%><input id="seatOrder.addTime" name="seatOrder.addTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>预约用户:</td>
    <td width=70%>
      <select name="seatOrder.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
      %>
          <option value='<%=userInfo.getUser_name() %>'><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>预约状态:</td>
    <td width=70%><input id="seatOrder.orderState" name="seatOrder.orderState" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>管理回复:</td>
    <td width=70%><textarea id="seatOrder.replyContent" name="seatOrder.replyContent" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>预约备注:</td>
    <td width=70%><textarea id="seatOrder.orderMemo" name="seatOrder.orderMemo" rows="5" cols="50"></textarea></td>
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
