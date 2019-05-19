<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
 <base href="<%=basePath%>"> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>NameCard信息管理系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<link rel="stylesheet" href="plug/css/bootstrap.css">
<link rel="stylesheet"href="plug/css/font-awesome.min.css">
<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
<link rel="stylesheet" href="plug/css/layout.css">
<style type="text/css">
	.jstring{width: 100%;}
	.jstring span{display: block; width: 100%;}
	</style>
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<script type="text/javascript">
$(function() {
	
		var boardHeight = 0;
		var fixedHeight = 92-$('.footer').height(); //90+2
		boardHeight = $(window).height() - fixedHeight;

		$('#menuIframe,#bodyIframe').css('height', boardHeight);
		//$('').css('height', boardHeight);
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		$('#menuBtn').bind('click', function() {
			var menu = $('#menuIframe');
			var width = $(menu).width();

			if (width != 0) {
				$(menu).animate( {
					width : '0'
				}, 200);
				$('#bar-title').animate( {
					width : '45'
				}, 200);
				$('#bar-title').parent().animate( {
					width : '45'
				}, 200);
			} else {
				$(menu).animate( {
					width : '200'
				}, 200);
				$('#bar-title').animate( {
					width : '200'
				}, 200);
				$('#bar-title').parent().animate( {
					width : '200'
				}, 200);
			}
		});
	});

window.onresize = function() {
	var boardHeight = 0;
	var fixedHeight = 92-$('.footer').height(); //90+2

	boardHeight = $(window).height() - fixedHeight;
	$('#menuIframe').css('height', boardHeight);
	$('#bodyIframe').css('height', boardHeight);
	
	if($(window).width() < 1000){
		$('#bar-title').parent().css({
			width : 45
		});
		$('#bar-title').css({
			width : 45
		});
		$('#menuIframe').css({
			width : 0
		});
	}else{
		$('#bar-title').parent().css({
			width : 200
		});
		$('#bar-title').css({
			width : 200
		});
		$('#menuIframe').css({
			width : 200
		});
	}
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
}
function showLoad(tipInfo) {
	if (tipInfo == undefined || tipInfo == '') {
		tipInfo = 'Loading...';
	}
	if($('#tipDiv').length > 0){
		$('#tipDiv').fadeIn();
		return false;
	}
	var eTip = document.createElement('div');
	eTip.setAttribute('id', 'tipDiv');	
	eTip.innerHTML = '<div style="position:absolute;left: 50%; top: 35%; text-align: center; padding:5px 15px;"><img src=\'./css/022.gif\' />&nbsp;&nbsp;<span style=\'color:black; margin: 8px auto; font-size:1.1em;font-weight:bolder;\'>' + tipInfo + '</span></div>';
	try {
		document.body.appendChild(eTip);
	} catch (e) {
	}
	$('#tipDiv').fadeIn();
} 

function closeLoad() {
	$('#tipDiv').fadeOut();
}


function quitLogin(){
if (confirm('你确定要退出系统吗？')){
top.location.href='<%=basePath%>LoginoutServlet';
}

}








 
</script>
</head>

<body>
<div class="e-container-fluid">
	<div class="header">
		<iframe id="topFrame" name="topFrame" src="top.jsp" height="100%" width="100%" frameborder="0" scrolling="no"> </iframe>
	</div>
	<div class="cont-bar">
		<table class="contet-bar-table">
			<tr>
				<td id="menuTd" class="tagName" width="200px">
					<div id="bar-title" class="bar-title">
						<i class="icon-reorder icon-large" id="menuBtn" title="Show/Hide"
							style="float: right; display: block; margin: 5px 15px 0px 0px; margin: 0px 10px 3px 0px\9; color: #fff; cursor: pointer;"></i>
					</div>
				</td>
				<td class="tagCont" id="tagCont" >
					<div id="bar-statu" class="bar-statu">
						<div class="statu-position">
							<p>
								<em>Position:</em>
								<i class="icon-angle-right"></i>
								<em id="cur-position">System Function</em>
							</p>
						</div>
					 
						<div class="bar-btn-group">
							<div class="btn-group">
								
								<a class="btn" id="logoutBtn" href="javascript:void(0);" onclick="quitLogin()"> 
									<i class="icon-off"></i>
									Logout
								</a>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div class="content">
		<table id="content-table">
			<tr>
				<td class="tagName" width="200px">
					<iframe id="menuIframe" height="100%" width="200" src="menu.jsp" frameborder="0" scrolling="no"></iframe>
				</td>
				<td class="info" width="100%">
					<iframe id="bodyIframe" height="100%" width="100%"  frameborder="0" src="desk.jsp"> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>
				</td>
			</tr>
		</table>
	</div>
	<!-- 2015年6月2日15:56:17Jimmy注释
	<div class="footer">
		<iframe src="down.html" name="bottomFrame" noresize="noresize" id="bottomFrame" height="100%" width="100%" frameborder="0" scrolling="no"></iframe>
	</div>
	 -->
</div>
</body>
</html>
