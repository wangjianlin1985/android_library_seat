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
<HTML><HEAD><TITLE>��Ӱ༶</TITLE> 
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
    var classNo = document.getElementById("classInfo.classNo").value;
    if(classNo=="") {
        alert('������༶���!');
        return false;
    }
    var className = document.getElementById("classInfo.className").value;
    if(className=="") {
        alert('������༶����!');
        return false;
    }
    var mainTeacher = document.getElementById("classInfo.mainTeacher").value;
    if(mainTeacher=="") {
        alert('�����������!');
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
    <s:form action="ClassInfo/ClassInfo_AddClassInfo.action" method="post" id="classInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>�༶���:</td>
    <td width=70%><input id="classInfo.classNo" name="classInfo.classNo" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>�༶����:</td>
    <td width=70%><input id="classInfo.className" name="classInfo.className" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="classInfo.bornDate"  name="classInfo.bornDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="classInfo.mainTeacher" name="classInfo.mainTeacher" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�༶��ע:</td>
    <td width=70%><textarea id="classInfo.classMemo" name="classInfo.classMemo" rows="5" cols="50"></textarea></td>
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
