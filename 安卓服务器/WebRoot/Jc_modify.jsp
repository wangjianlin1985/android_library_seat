<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Jc" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    Jc jc = (Jc)request.getAttribute("jc");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ľ���</TITLE>
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
    var jcType = document.getElementById("jc.jcType").value;
    if(jcType=="") {
        alert('�����뽱������!');
        return false;
    }
    var title = document.getElementById("jc.title").value;
    if(title=="") {
        alert('�����뽱�ͱ���!');
        return false;
    }
    var content = document.getElementById("jc.content").value;
    if(content=="") {
        alert('�����뽱������!');
        return false;
    }
    var jcTime = document.getElementById("jc.jcTime").value;
    if(jcTime=="") {
        alert('�����뽱��ʱ��!');
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
    <TD align="left" vAlign=top ><s:form action="Jc/Jc_ModifyJc.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����id:</td>
    <td width=70%><input id="jc.jcId" name="jc.jcId" type="text" value="<%=jc.getJcId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="jc.jcType" name="jc.jcType" type="text" size="20" value='<%=jc.getJcType() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���ͱ���:</td>
    <td width=70%><input id="jc.title" name="jc.title" type="text" size="60" value='<%=jc.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><textarea id="jc.content" name="jc.content" rows=5 cols=50><%=jc.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>�����û�:</td>
    <td width=70%>
      <select name="jc.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(jc.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>���÷���:</td>
    <td width=70%><input id="jc.creditScore" name="jc.creditScore" type="text" size="8" value='<%=jc.getCreditScore() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="jc.jcTime" name="jc.jcTime" type="text" size="20" value='<%=jc.getJcTime() %>'/></td>
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
