<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Seat" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Seat��Ϣ
    List<Seat> seatList = (List<Seat>)request.getAttribute("seatList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�����λԤԼ</TITLE> 
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
/*��֤��*/
function checkForm() {
    var startTime = document.getElementById("seatOrder.startTime").value;
    if(startTime=="") {
        alert('�����뿪ʼʱ��!');
        return false;
    }
    var endTime = document.getElementById("seatOrder.endTime").value;
    if(endTime=="") {
        alert('���������ʱ��!');
        return false;
    }
    var addTime = document.getElementById("seatOrder.addTime").value;
    if(addTime=="") {
        alert('�������ύԤԼʱ��!');
        return false;
    }
    var orderState = document.getElementById("seatOrder.orderState").value;
    if(orderState=="") {
        alert('������ԤԼ״̬!');
        return false;
    }
    var replyContent = document.getElementById("seatOrder.replyContent").value;
    if(replyContent=="") {
        alert('���������ظ�!');
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
    <td width=30%>ԤԼ��λ:</td>
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
    <td width=30%>ԤԼ����:</td>
    <td width=70%><input type="text" readonly id="seatOrder.orderDate"  name="seatOrder.orderDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>��ʼʱ��:</td>
    <td width=70%><input id="seatOrder.startTime" name="seatOrder.startTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="seatOrder.endTime" name="seatOrder.endTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�ύԤԼʱ��:</td>
    <td width=70%><input id="seatOrder.addTime" name="seatOrder.addTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>ԤԼ�û�:</td>
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
    <td width=30%>ԤԼ״̬:</td>
    <td width=70%><input id="seatOrder.orderState" name="seatOrder.orderState" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>����ظ�:</td>
    <td width=70%><textarea id="seatOrder.replyContent" name="seatOrder.replyContent" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>ԤԼ��ע:</td>
    <td width=70%><textarea id="seatOrder.orderMemo" name="seatOrder.orderMemo" rows="5" cols="50"></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
