<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>310ͼ���ռ��app-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<li><a href="<%=basePath %>ClassInfo/ClassInfo_FrontQueryClassInfo.action" target="OfficeMain">�༶</a></li> 
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">�û�</a></li> 
			<li><a href="<%=basePath %>UserType/UserType_FrontQueryUserType.action" target="OfficeMain">�û�����</a></li> 
			<li><a href="<%=basePath %>Room/Room_FrontQueryRoom.action" target="OfficeMain">������</a></li> 
			<li><a href="<%=basePath %>Seat/Seat_FrontQuerySeat.action" target="OfficeMain">��λ</a></li> 
			<li><a href="<%=basePath %>SeatState/SeatState_FrontQuerySeatState.action" target="OfficeMain">��λ״̬</a></li> 
			<li><a href="<%=basePath %>SeatOrder/SeatOrder_FrontQuerySeatOrder.action" target="OfficeMain">��λԤԼ</a></li> 
			<li><a href="<%=basePath %>SelectSeat/SelectSeat_FrontQuerySelectSeat.action" target="OfficeMain">ѡ��</a></li> 
			<li><a href="<%=basePath %>Jubao/Jubao_FrontQueryJubao.action" target="OfficeMain">�ٱ�</a></li> 
			<li><a href="<%=basePath %>Jc/Jc_FrontQueryJc.action" target="OfficeMain">����</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>˫������� QQ:287307421��254540457 &copy;��Ȩ���� <a href="http://www.shuangyulin.com" target="_blank">˫���������</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
