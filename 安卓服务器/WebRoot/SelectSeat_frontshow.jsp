<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.SelectSeat" %>
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
    SelectSeat selectSeat = (SelectSeat)request.getAttribute("selectSeat");

%>
<HTML><HEAD><TITLE>查看选座</TITLE>
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
    <td width=30%>选座id:</td>
    <td width=70%><%=selectSeat.getSelectId() %></td>
  </tr>

  <tr>
    <td width=30%>座位编号:</td>
    <td width=70%>
      <%=selectSeat.getSeatObj().getSeatCode() %>
    </td>
  </tr>

  <tr>
    <td width=30%>选座用户:</td>
    <td width=70%>
      <%=selectSeat.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>选座开始时间:</td>
    <td width=70%><%=selectSeat.getStartTime() %></td>
  </tr>

  <tr>
    <td width=30%>选座结束时间:</td>
    <td width=70%><%=selectSeat.getEndTime() %></td>
  </tr>

  <tr>
    <td width=30%>选座状态:</td>
    <td width=70%><%=selectSeat.getSeatState() %></td>
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
