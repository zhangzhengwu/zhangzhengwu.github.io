<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- 清除缓存防止页面后退安全隐患,使用时直接包含即可 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
		<title>NameCard信息管理_用户登录</title>
		<script type="text/javascript" src="../plug/js/jquery.min.js">
</script>
		<script type="text/javascript" src="../js/main.js">
</script>
		<link rel="stylesheet" href="../plug/css/bootstrap.min.css">
		<link rel="stylesheet" href="../plug/css/site.css">
		<link rel="stylesheet" href="../plug/css/layout.css">
		<!--[if IE 7]>
<link rel="stylesheet" href="plug/css/layout-ie7.css">
<![endif]-->
	</head>
	<script type="text/javascript">
$('body').ready(function() {
	var height = 0, lg_height = 0;
	lg_height = $(document).height() - 800 - 0 - 3;

	$('#lg-footer').css('height', lg_height);
});
function changeVcode() {
	document.getElementById('valiCode').src = "code.jsp";
}
document.onkeydown = function(e) {
	if (!e)
		e = window.event;//火狐中是 window.event
	if ((e.keyCode || e.which) == 13) {
		checkSubmit();
	}
}

function checkSubmit() {
	if (document.getElementById('Username').value == "") {
		alert("用户名不能为空!");
		document.getElementById('Username').focus();
	} else if (document.getElementById('Password').value == "") {
		alert("密码输入不能为空!");
		document.getElementById('Password').focus();
	} else if (document.getElementById('Password').value.indexOf("'") != -1) {
		alert("非法字符，请重新输入!");
		document.getElementById('Password').value = "";
		document.getElementById('Password').focus();
	} else {
		_ajax('post', 'json', "../SystemUserServlet", $('#loginForm')
				.serialize(), function(obj) {
			;

			if (obj.Stu == '1') {
				location.href = "main.jsp";
			} else {
				alert(obj.Msg);
			}
		}, function() {
		}, function() {
		}, function() {
		})

		//document.getElementById('loginForm').submit();
	}

}
</script>
	<body onload="javascript:document.getElementById('Username').focus();">
		<c:if test="${errMessage}">
			<script>
alert('${errMessage}');
location.href = "login2.jsp";
</script>
		</c:if>
		<div class="container-fluid">
			<div class="lg-title"></div>
			<div class="lg-cont">
				<div class="cont-center">
					<div class="lg-board">
						<div class="lg-title">
							<h2>
								User Login ${errMessage}
							</h2>
						</div>
						<div class="lg-input">
							<form method="post" action="../SystemUserServlet" id="loginForm">
								<input type="hidden" name="method" value="login" />
								<table>
									<tr>
										<td class="tagName">
											Account
										</td>
										<td class="tagCont">
											<input type="text" name="username" id="Username"
												maxlength="20" />
										</td>
									</tr>
									<tr>
										<td class="tagName">
											Password
										</td>
										<td class="tagCont">
											<input type="password" name="password" id="Password"
												maxlength="50" />
										</td>
									</tr>
									<tr>
										<td class="tagName">
											Code
										</td>
										<td class="tagCont">
											<input type="text" name="code" id="code" maxlength="50" />
											<br />
											<img style="cursor:pointer" id="code_Img" src="<%=basePath%>getVerifyCodeServlet"
												onclick="document.getElementById('code_Img').src=this.src+'?'+Math.random();"
												alt="Verification code load failure!"   title="Click on the image to refresh"/>
												
										</td>
									</tr>
									<tr style="height: 15px;">
									</tr>
									<tr>
										<td colspan="2">
											<label>
												<input type="checkbox" name="rememberme" />
												Remember me
											</label>
											<input type="button" value=" Login " class="btn btn-primary"
												onclick="checkSubmit()">
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
					<div style="clear: both;"></div>
				</div>
			</div>
			<div class="lg-footer" id="lg-footer"></div>
		</div>
	</body>
</html>