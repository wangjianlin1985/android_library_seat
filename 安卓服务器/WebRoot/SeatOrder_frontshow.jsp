<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.SeatOrder" %>
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
    SeatOrder seatOrder = (SeatOrder)request.getAttribute("seatOrder");

%>
<HTML><HEAD><TITLE>查看座位预约</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>预约id:</td>
    <td width=70%><%=seatOrder.getOrderId() %></td>
  </tr>

  <tr>
    <td width=30%>预约座位:</td>
    <td width=70%>
      <%=seatOrder.getSeatObj().getSeatCode() %>
    </td>
  </tr>

  <tr>
    <td width=30%>预约日期:</td>
        <% java.text.DateFormat orderDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=orderDateSDF.format(seatOrder.getOrderDate()) %></td>
  </tr>

  <tr>
    <td width=30%>开始时间:</td>
    <td width=70%><%=seatOrder.getStartTime() %></td>
  </tr>

  <tr>
    <td width=30%>结束时间:</td>
    <td width=70%><%=seatOrder.getEndTime() %></td>
  </tr>

  <tr>
    <td width=30%>提交预约时间:</td>
    <td width=70%><%=seatOrder.getAddTime() %></td>
  </tr>

  <tr>
    <td width=30%>预约用户:</td>
    <td width=70%>
      <%=seatOrder.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>预约状态:</td>
    <td width=70%><%=seatOrder.getOrderState() %></td>
  </tr>

  <tr>
    <td width=30%>管理回复:</td>
    <td width=70%><%=seatOrder.getReplyContent() %></td>
  </tr>

  <tr>
    <td width=30%>预约备注:</td>
    <td width=70%><%=seatOrder.getOrderMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
