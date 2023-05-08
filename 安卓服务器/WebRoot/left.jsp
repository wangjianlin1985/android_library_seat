<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//basePath = "http://localhost:8080/SalarySystem/"
%>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	font-size: 12px;
	color: #FFFFFF;
}
.STYLE3 {
	font-size: 12px;
	color: #033d61;
}
-->
</style>
<style type="text/css">
.menu_title SPAN {
	FONT-WEIGHT: bold; LEFT: 3px; COLOR: #ffffff; POSITION: relative; TOP: 2px 
}
.menu_title2 SPAN {
	FONT-WEIGHT: bold; LEFT: 3px; COLOR: #FFCC00; POSITION: relative; TOP: 2px
}

</style>
<script>
//var he=document.body.clientHeight-105;
var he = document.documentElement.clientHeight;// - 105;
document.write("<div id=tt style=height:"+he+";overflow:hidden>");
</script>

<table width="165" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="28" background="images/main_40.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="19%">&nbsp;</td>
        <td width="81%" height="20"><span class="STYLE1">管理菜单</span></td>
      </tr>
    </table></td>
  </tr>
   
<tr>
  <td valign="top"><table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   <td height="23" background="images/main_47.gif" id="imgmenu1" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(1)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
              <td width="18%">&nbsp;</td>
              <td width="82%" class="STYLE1">班级管理</td>
        </tr>
          </table></td>
        </tr>
        <tr>
          <td background="images/main_51.gif" id="submenu1">
		 <div class="sec_menu" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>ClassInfo/ClassInfo_AddView.action';">添加班级</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>ClassInfo/ClassInfo_QueryClassInfo.action';" >班级管理</span></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
            </tr>
          </table></div></td>
        </tr>
      </table></td>
    </tr>

<tr>
  <td valign="top"><table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   <td height="23" background="images/main_47.gif" id="imgmenu2" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(2)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
              <td width="18%">&nbsp;</td>
              <td width="82%" class="STYLE1">用户管理</td>
        </tr>
          </table></td>
        </tr>
        <tr>
          <td background="images/main_51.gif" id="submenu2">
		 <div class="sec_menu" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>UserInfo/UserInfo_AddView.action';">添加用户</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>UserInfo/UserInfo_QueryUserInfo.action';" >用户管理</span></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
            </tr>
          </table></div></td>
        </tr>
      </table></td>
    </tr>

<tr>
  <td valign="top"><table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   <td height="23" background="images/main_47.gif" id="imgmenu3" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(3)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
              <td width="18%">&nbsp;</td>
              <td width="82%" class="STYLE1">用户类型管理</td>
        </tr>
          </table></td>
        </tr>
        <tr>
          <td background="images/main_51.gif" id="submenu3">
		 <div class="sec_menu" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>UserType/UserType_AddView.action';">添加用户类型</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>UserType/UserType_QueryUserType.action';" >用户类型管理</span></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
            </tr>
          </table></div></td>
        </tr>
      </table></td>
    </tr>

<tr>
  <td valign="top"><table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   <td height="23" background="images/main_47.gif" id="imgmenu4" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(4)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
              <td width="18%">&nbsp;</td>
              <td width="82%" class="STYLE1">阅览室管理</td>
        </tr>
          </table></td>
        </tr>
        <tr>
          <td background="images/main_51.gif" id="submenu4">
		 <div class="sec_menu" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>Room/Room_AddView.action';">添加阅览室</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>Room/Room_QueryRoom.action';" >阅览室管理</span></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
            </tr>
          </table></div></td>
        </tr>
      </table></td>
    </tr>

<tr>
  <td valign="top"><table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   <td height="23" background="images/main_47.gif" id="imgmenu5" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(5)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
              <td width="18%">&nbsp;</td>
              <td width="82%" class="STYLE1">座位管理</td>
        </tr>
          </table></td>
        </tr>
        <tr>
          <td background="images/main_51.gif" id="submenu5">
		 <div class="sec_menu" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>Seat/Seat_AddView.action';">添加座位</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>Seat/Seat_QuerySeat.action';" >座位管理</span></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
            </tr>
          </table></div></td>
        </tr>
      </table></td>
    </tr>

<tr>
  <td valign="top"><table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   <td height="23" background="images/main_47.gif" id="imgmenu6" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(6)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
              <td width="18%">&nbsp;</td>
              <td width="82%" class="STYLE1">座位状态管理</td>
        </tr>
          </table></td>
        </tr>
        <tr>
          <td background="images/main_51.gif" id="submenu6">
		 <div class="sec_menu" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>SeatState/SeatState_AddView.action';">添加座位状态</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>SeatState/SeatState_QuerySeatState.action';" >座位状态管理</span></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
            </tr>
          </table></div></td>
        </tr>
      </table></td>
    </tr>

<tr>
  <td valign="top"><table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   <td height="23" background="images/main_47.gif" id="imgmenu7" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(7)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
              <td width="18%">&nbsp;</td>
              <td width="82%" class="STYLE1">座位预约管理</td>
        </tr>
          </table></td>
        </tr>
        <tr>
          <td background="images/main_51.gif" id="submenu7">
		 <div class="sec_menu" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>SeatOrder/SeatOrder_AddView.action';">添加座位预约</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>SeatOrder/SeatOrder_QuerySeatOrder.action';" >座位预约管理</span></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
            </tr>
          </table></div></td>
        </tr>
      </table></td>
    </tr>

<tr>
  <td valign="top"><table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   <td height="23" background="images/main_47.gif" id="imgmenu8" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(8)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
              <td width="18%">&nbsp;</td>
              <td width="82%" class="STYLE1">选座管理</td>
        </tr>
          </table></td>
        </tr>
        <tr>
          <td background="images/main_51.gif" id="submenu8">
		 <div class="sec_menu" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>SelectSeat/SelectSeat_AddView.action';">添加选座</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>SelectSeat/SelectSeat_QuerySelectSeat.action';" >选座管理</span></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
            </tr>
          </table></div></td>
        </tr>
      </table></td>
    </tr>

<tr>
  <td valign="top"><table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   <td height="23" background="images/main_47.gif" id="imgmenu9" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(9)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
              <td width="18%">&nbsp;</td>
              <td width="82%" class="STYLE1">举报管理</td>
        </tr>
          </table></td>
        </tr>
        <tr>
          <td background="images/main_51.gif" id="submenu9">
		 <div class="sec_menu" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>Jubao/Jubao_AddView.action';">添加举报</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>Jubao/Jubao_QueryJubao.action';" >举报管理</span></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
            </tr>
          </table></div></td>
        </tr>
      </table></td>
    </tr>

<tr>
  <td valign="top"><table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   <td height="23" background="images/main_47.gif" id="imgmenu10" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(10)" onMouseOut="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
              <td width="18%">&nbsp;</td>
              <td width="82%" class="STYLE1">奖惩管理</td>
        </tr>
          </table></td>
        </tr>
        <tr>
          <td background="images/main_51.gif" id="submenu10">
		 <div class="sec_menu" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>Jc/Jc_AddView.action';">添加奖惩</span></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                  <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>Jc/Jc_QueryJc.action';" >奖惩管理</span></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
            </tr>
          </table></div></td>
        </tr>
      </table></td>
    </tr>


      
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="23" background="images/main_47.gif" id="imgmenu500" class="menu_title" onmouseover="this.className='menu_title2';" onclick="showsubmenu(500)" onmouseout="this.className='menu_title';" style="cursor:hand"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="18%">&nbsp;</td>
                  <td width="82%" class="STYLE1">系统管理</td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td background="images/main_51.gif" id="submenu500"><div class="sec_menu" >
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
<!--
                         <tr>
                          <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                          <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='<%=basePath %>User/User_UserQuery.action';">用户管理</span></td>
                              </tr>
                          </table></td>
                        </tr>-->
                        <tr>
                          <td width="16%" height="25"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                          <td width="84%" height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='password_modify.jsp';">修改密码</span></td>
                              </tr>
                          </table></td>
                        </tr>
                        <tr>
                          <td height="23"><div align="center"><img src="images/left.gif" width="10" height="10" /></div></td>
                          <td height="23"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'"><span class="STYLE3" onclick="javascript:parent.document.getElementById('ContentFrame').src='logout.jsp';">退出系统</span></td>
                              </tr>
                          </table></td>
                        </tr>
                        
                    </table></td>
                  </tr>
                  <tr>
                    <td height="5"><img src="images/main_52.gif" width="151" height="5" /></td>
                  </tr>
                </table>
            </div></td>
          </tr>
        </table>          </td>
      </tr>
      
    </table></td>
  </tr>
  <tr>
    <td height="18" background="images/main_58.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="18" valign="bottom"><div align="center" class="STYLE3">版本：2017V1.0</div></td>
      </tr>
    </table></td>
  </tr>
</table>
<script>




function showsubmenu(sid)
{
whichEl = eval("submenu" + sid);
imgmenu = eval("imgmenu" + sid);
if (whichEl.style.display == "none")
{
eval("submenu" + sid + ".style.display=\"\";");
imgmenu.background="images/main_47.gif";
}
else
{
eval("submenu" + sid + ".style.display=\"none\";");
imgmenu.background="images/main_48.gif";
}
}

</script>
