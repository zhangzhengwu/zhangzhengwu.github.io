<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<html>
<head>
<title>NameCard信息管理_用户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<!-- 清除缓存防止页面后退安全隐患,使用时直接包含即可 -->
<link rel="stylesheet" href="plug/css/bootstrap.min.css" />
<link rel="stylesheet" href="plug/css/site.css" />
<link rel="stylesheet" href="plug/css/layout.css" />
<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>
<!--[if IE 7]>
<link rel="stylesheet" href="plug/css/layout-ie7.css">
<![endif]-->
<style type="text/css">
.btn-primary {
	color: #fff;
	background: #428bca;
	border-color: #357ebd;
}
</style>
</head>
<script type="text/javascript">
$('body').ready(function(){
	var height=0, lg_height=0;
	lg_height = $(document).height() - 800 - 0 - 3; 

	$('#lg-footer').css('height',lg_height);
});

    function changeVcode() {
        document.getElementById('valiCode').src = "code.jsp";
    }  
   document.onkeydown = function(e){
        if(!e) e = window.event;//火狐中是 window.event
        if((e.keyCode || e.which) == 13){
   			checkSubmit();
        }
    } 
   
   
   function checkSubmit(){
	   if(document.getElementById('Username').value==""){
		   	alert("用户名不能为空!");document.getElementById('Username').focus();
	   }else if(document.getElementById('Password').value==""){
			 alert("密码输入不能为空!");document.getElementById('Password').focus();
	   }else if (document.getElementById('Password').value.indexOf("'") != -1){
			alert("非法字符，请重新输入!");
			document.getElementById('Password').value="";
			document.getElementById('Password').focus();
		}else{
		   document.getElementById('loginForm').submit();
	   }
		   			
   }
</script>
<body onload="javascript:document.getElementById('Username').focus();">

	<%
		if (request.getAttribute("errMessage") != null &&request.getAttribute("errMessage") != "") {
	%>
	<script>
	         alert('<%=request.getAttribute("errMessage")%>');
			location.href = "signin.jsp";
	</script>
	<%
		}
	%>
	<div class="container-fluid">
		<div class="lg-title"></div>
		<div class="lg-cont">
			<div class="cont-center">
				<div class="lg-board">
					<div class="lg-title">
						<h2>用户登录</h2>
					</div>
					<div class="lg-input">
						<form method="post" action="CheckLoginServlet" id="loginForm">
							<table>
								<tr>
									<td class="tagName">用户名</td>
									<td class="tagCont"><input type="text" name="username"
										id="Username" maxlength="20" class="txt" /></td>
								</tr>
								<tr>
									<td class="tagName">密码:</td>
									<td class="tagCont"><input type="password" name="password"
										id="Password" maxlength="50" class="txt" /></td>
								</tr>
								<tr style="height:15px;">
								</tr>
								<tr>
									<td colspan="2">
										<!-- <label class="checkbox inline"><input type="checkbox" name="rememberme" />下次自动登录</label> -->
										<input type="button" value=" 登录 " class="btn btn-primary"
										onclick="checkSubmit();"></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<div style="clear:both;"></div>
			</div>
		</div>
		<div class="lg-footer" id="lg-footer"></div>
	</div>
</body>
</html>