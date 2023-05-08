<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Jc" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    Jc jc = (Jc)request.getAttribute("jc");

%>
<HTML><HEAD><TITLE>查看奖惩</TITLE>
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
    <td width=30%>奖惩id:</td>
    <td width=70%><%=jc.getJcId() %></td>
  </tr>

  <tr>
    <td width=30%>奖惩类型:</td>
    <td width=70%><%=jc.getJcType() %></td>
  </tr>

  <tr>
    <td width=30%>奖惩标题:</td>
    <td width=70%><%=jc.getTitle() %></td>
  </tr>

  <tr>
    <td width=30%>奖惩内容:</td>
    <td width=70%><%=jc.getContent() %></td>
  </tr>

  <tr>
    <td width=30%>奖惩用户:</td>
    <td width=70%>
      <%=jc.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>信用分数:</td>
    <td width=70%><%=jc.getCreditScore() %></td>
  </tr>

  <tr>
    <td width=30%>奖惩时间:</td>
    <td width=70%><%=jc.getJcTime() %></td>
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
