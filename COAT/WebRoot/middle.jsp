<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	width:100%;
	height:100%;
}

  
.navPoint { 
COLOR: white; CURSOR: hand; FONT-FAMILY: Webdings; FONT-SIZE: 9pt 
} 
.div_conver{
 position:absolute;
border:1px solid silver;
 width:100%;
 height:105%;
 z-index:999; 
 background-color:silver;

 font-family: 'Arial Narrow';
	font-size: 14px;
 filter:alpha(opacity=95);/*IE*/
opacity:0.9;/*FF*/
}
#login{
position:absolute;
z-index:9999;
top:200px;
left:500px;
 width:360px;
 height:200px;
background-color: whiteSmoke;
 table-index:0;
 
}

#login td{
 font-family: 'Arial Narrow';
	font-size: 14px;
}
 #messages{
  font-family: 'Arial Narrow';
	font-size: 14px;
 }
 

 -->
</style> 
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>

<script type="text/javascript">
var show_div=parent.document.frames["topFrame"].document.getElementById('id_conver');
var loginNum=3;
$(function(){
	$("#login ").live("blur",function(){
		$("#password").focus();
	});
	$("#bodyIframe,#menuIframe").height($(this).height());
});

function loginUser(){
	if($("#password").val()==""){
		$("#password").focus();
		$("#messages").empty().append("密码不能为空!");
		return false;
	}
		$.ajax({
		url:"VailLoginServlet",
		type:"post",
		data:{"LoginName":$("#userlogin").val(),"password":$("#password").val()},
		success:function(data){
			if(data=="success"){
 				show_div.style.display="none";
				$(".div_conver").hide();
			}else{
					loginNum--;
				if(loginNum<=0){
					top.location.href='logout.jsp';
					return false;
				}
					$("#messages").empty().append("用户名或密码错误,您还剩余"+loginNum+"次机会!")
			}
		},error:function(){
			alert("网络连接失败,请稍后重试或联系管理员!");
			return false;
		}
	});
}
window.onload = tick;


/*
function switchSysBar(){ 
var locate=location.href.replace('middel.html','');
var ssrc=document.all("img1").src.replace(locate,'');
if (ssrc=="images/main_55.gif")
{ 
document.all("img1").src="images/main_55_1.gif";
document.all("frmTitle").style.display="none" 
} 
else
{ 
document.all("img1").src="images/main_55.gif";
document.all("frmTitle").style.display="" 
}
} */
function switchSysBar() {

  if(document.all("frmTitle").style.display == "") {
    document.all("img1").src = "images/main_55_1.gif";
    document.all("frmTitle").style.display = "none";
  } else {
    document.all("img1").src = "images/main_55.gif";
    document.all("frmTitle").style.display = "";
  } 
}
</script>

</head>

<body  style="overflow:hidden;">


<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
  <tr style="line-height: 100%;">
    <td width="171" id="frmTitle" height="100%" align="center" valign="top" ><table width="171"  border="0" height="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed;">
   <tr>
     <td  bgcolor="#1873aa" style="width:6px;">&nbsp;</td>
        <td width="165"><iframe id="menuIframe" name="I1" height="100%" width="165" src="menu.jsp" frameborder="0" scrolling="no"> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe></td>
      </tr>
    </table>		</td>
    <td width="6"  style="width:6px;" valign="middle" bgcolor="1873aa" onclick=switchSysBar()><SPAN class=navPoint 
id=switchPoint title=关闭/打开左栏><img src="images/main_55.gif" name="img1" width=6 height=40 id=img1></SPAN></td>
    <td width="100%" height="100%" align="center" valign="top"><iframe name="I2" id="bodyIframe" height="100%" width="100%"  frameborder="0" src="desk.jsp"> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe></td>
  </tr>
</table>






 <div class="div_conver"  style="display: none;">
  <div id="login" >
				  <table tableIndex=0 width="100%" height="100%">
					  <tr height="28" style="background-image:url(css/officeAdmin-menu-bar.gif); " ><td colspan="3" align="center"><strong style="color:yellow;" >登录验证</strong></td></tr>
					  <tr height="40"><td  align="right"  ><strong>用户名:</strong></td><td align="center"  colspan="2" style="width:150px"><input type="text" id="userlogin" value="${adminUsername}" readonly="readonly"/></td></tr>
					  <tr height="40"><td  align="right"><strong>密码:</strong></td><td align="center" style="width:150px" colspan="2"><input  id="password" type="password" /></td></tr>
					  <tr height="30%"><td width="55"></td><td align="center"><input type="button" value="验证" onClick="loginUser();"/>&nbsp;&nbsp;<input type="button" onclick="if (confirm('你确定要退出系统吗？')){top.location.href='logout.jsp';}" value="退出"/></td></tr>
					  <tr height="30"><td colspan="3"><span style="color: red;" id="messages"></span> </td></tr>
				  </table>
			  </div>
 </div>
<script type="text/javascript">
if("${flag}"!="true"){
	show_div.style.display="";
				if($(".div_conver").is(":hidden")){
					$(".div_conver").show();
					$("#password").val("").focus();
				}
}


</script>
</body>
</html>
