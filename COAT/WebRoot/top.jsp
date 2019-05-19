<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>"> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="plug/css/bootstrap.css">
<link rel="stylesheet"href="plug/css/font-awesome.min.css">
<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
<title>无标题文档</title>
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
	color: black;
}

.swtop {
	background-image: url(images/sw_top.jpg)
}

.STYLE2 {
	font-size: 9px
}

.STYLE3 {
	color: #033d61;
	font-size: 12px;
}

.div_conver {
	position: absolute;
	border: 1px solid white;
	width: 100%;
	height: 100%;
	z-index: 999;
	background-color: silver;
	filter: alpha(opacity = 95); /*IE*/
	opacity: 0.9; /*FF*/
}


#nosession{
	text-align:center;
	line-height: 40px;
	position: absolute;
	width: 100%;
	vertical-align: middle;
	background-color:#FAEB9E;
	display:none;
	
}
-->
</style>

<script type="text/javascript" src="css/jquery-1.4.4.js"></script>

<script type="text/javascript">
var num=-1;
	

	//window.onload = tick;
//-->

$(function() {
	window.setTimeout("ticks();", 6000);
});
function ticks(){
	$.ajax({
	//	url:"VailSessionServlet",
		url:"CheckLoginNameServlet",
		type:"post",
		data:{"staffcode":"${adminUsername}"},
		success:function(data){
			if(data=="warn"){
				if(num==-1){
					nosession();
				}
			}else if(data!="success"){
				top.location.href="signin.jsp";
			}
		},error:function(){
			top.location.href="signin.jsp";
			return false;
		}
	});
//window.setTimeout("tick();", 1800000);
window.setTimeout("ticks();", 6000);
}

function nosession(){
	num=30;
	refers();
	$("#nosession").slideDown();
}



</script>
</head>

<body><%--
,如需继续使用请单击<a href="javascript:void(0);" onclick="reset()"  class="label label-warning">此处</a>.
--%><div  id="nosession" style="backgroud-color:#FAEB9E;"><i class="icon icon-envelope"></i> <strong > 由于您长时间未操作系统,您的登录信息将在<a id="second" >30</a>s后失效.</strong><button class="label label-info"  onclick="reset()">自动登录</button></div><%--

	<a href="javascript:void(0);" onclick="resets()">test</a>
	
	
	--%><table width="100%" height="100%" border="0" cellspacing="0">
  <tbody><tr>
    <td height="59" style="BACKGROUND-REPEAT: repeat-y" background="images/convoy.gif" width="350">
     </td></tr>
  <tr>
    <td height="28" background="images/main_36.gif">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      
</table>
</td>
</tr>
</tbody></table>
<script type="text/javascript">

function resets(){
	$.ajax({
		url:"common/CommonReaderServlet",
		type:"post",
		data:{"method":"visit2"},
		success:function(date){
			//alert(date);
			nosession();
		},error:function(){
			alert("网络连接失败!");
			return false;
		}
	});
}

function reset(){
	$.ajax({
		url:"common/CommonReaderServlet",
		type:"post",
		data:{"method":"visit"},
		success:function(date){
			$("#nosession").slideUp();
		},error:function(){
			alert("网络连接失败!");
			return false;
		}
	});
}
function refers(){
	if(num==-1){
		$("#nosession").slideUp();
		return;
	}else if(num<=0){
		num=0;
	}else{
		num--;
	}
	$("#second").empty().append(num);
	window.setTimeout("refers();", 1000);
}

function tick() {
	var hours, minutes, seconds;
	var intHours, intMinutes, intSeconds;
	var today;
	today = new Date();
	intYear = today.getFullYear();
	intMonth = today.getMonth() + 1;
	intDay = today.getDate();
	intHours = today.getHours();
	intMinutes = today.getMinutes();
	intSeconds = today.getSeconds();

	if (intHours == 0) {
		hours = "00:";
	} else if (intHours < 10) {
		hours = "0" + intHours + ":";
	} else {
		hours = intHours + ":";
	}

	if (intMinutes < 10) {
		minutes = "0" + intMinutes + ":";
	} else {
		minutes = intMinutes + ":";
	}
	if (intSeconds < 10) {
		seconds = "0" + intSeconds + " ";
	} else {
		seconds = intSeconds + " ";
	}
	timeString = intYear + "年" + intMonth + "月" + intDay + "日" + " "
			+ hours + minutes + seconds;
	Clock.innerHTML = timeString;

	window.setTimeout("tick();", 1000);
}
</script>
</body>
</html>