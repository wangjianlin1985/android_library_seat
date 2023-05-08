<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.SeatOrder" %>
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
    SeatOrder seatOrder = (SeatOrder)request.getAttribute("seatOrder");

%>
<HTML><HEAD><TITLE>�鿴��λԤԼ</TITLE>
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
    <td width=30%>ԤԼid:</td>
    <td width=70%><%=seatOrder.getOrderId() %></td>
  </tr>

  <tr>
    <td width=30%>ԤԼ��λ:</td>
    <td width=70%>
      <%=seatOrder.getSeatObj().getSeatCode() %>
    </td>
  </tr>

  <tr>
    <td width=30%>ԤԼ����:</td>
        <% java.text.DateFormat orderDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=orderDateSDF.format(seatOrder.getOrderDate()) %></td>
  </tr>

  <tr>
    <td width=30%>��ʼʱ��:</td>
    <td width=70%><%=seatOrder.getStartTime() %></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><%=seatOrder.getEndTime() %></td>
  </tr>

  <tr>
    <td width=30%>�ύԤԼʱ��:</td>
    <td width=70%><%=seatOrder.getAddTime() %></td>
  </tr>

  <tr>
    <td width=30%>ԤԼ�û�:</td>
    <td width=70%>
      <%=seatOrder.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>ԤԼ״̬:</td>
    <td width=70%><%=seatOrder.getOrderState() %></td>
  </tr>

  <tr>
    <td width=30%>����ظ�:</td>
    <td width=70%><%=seatOrder.getReplyContent() %></td>
  </tr>

  <tr>
    <td width=30%>ԤԼ��ע:</td>
    <td width=70%><%=seatOrder.getOrderMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
