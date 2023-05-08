<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Jubao" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    Jubao jubao = (Jubao)request.getAttribute("jubao");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸ľٱ�</TITLE>
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
    var title = document.getElementById("jubao.title").value;
    if(title=="") {
        alert('������ٱ�����!');
        return false;
    }
    var content = document.getElementById("jubao.content").value;
    if(content=="") {
        alert('������ٱ�����!');
        return false;
    }
    var jubaoTime = document.getElementById("jubao.jubaoTime").value;
    if(jubaoTime=="") {
        alert('������ٱ�ʱ��!');
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
    <TD align="left" vAlign=top ><s:form action="Jubao/Jubao_ModifyJubao.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>�ٱ�id:</td>
    <td width=70%><input id="jubao.jubaoId" name="jubao.jubaoId" type="text" value="<%=jubao.getJubaoId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�ٱ�����:</td>
    <td width=70%><input id="jubao.title" name="jubao.title" type="text" size="60" value='<%=jubao.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ٱ�����:</td>
    <td width=70%><textarea id="jubao.content" name="jubao.content" rows=5 cols=50><%=jubao.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>�ٱ��û�:</td>
    <td width=70%>
      <select name="jubao.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(jubao.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ٱ�ʱ��:</td>
    <td width=70%><input id="jubao.jubaoTime" name="jubao.jubaoTime" type="text" size="20" value='<%=jubao.getJubaoTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����ظ�:</td>
    <td width=70%><textarea id="jubao.replyContent" name="jubao.replyContent" rows=5 cols=50><%=jubao.getReplyContent() %></textarea></td>
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
