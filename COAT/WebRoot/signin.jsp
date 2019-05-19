<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
 <base href="<%=basePath%>"> 
<title>NameCard信息管理_用户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<!-- 清除缓存防止页面后退安全隐患,使用时直接包含即可 -->
<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basePath%>plug/css/site.css" />
<link rel="stylesheet" href="<%=basePath%>plug/css/layout.css" />
<!--[if IE 7]>
<link rel="stylesheet" href="<%=basePath%>plug/css/layout-ie7.css">
<![endif]-->
<style type="text/css">
 
.btn-primary {
	color: #fff;
	background: #428bca;
	border-color: #357ebd;
}

</style>
<script type="text/javascript">
	if(self!=top){
	top.location.href=self.location.href;
	}
</script>
	

	</head>
	
	<body >
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
	
	<div class="lg-container">
    <div class="lg-header">
        <div class="h-logo">
            <img src="images/coat_systemname.png" />
        </div>
        <div class="h-title"></div>
    </div>
    <div class="lg-content">
        <div class="lgBoard">
            <div class="lg-title">
                <h4>用户登录</h4>
            </div>
            <div class="lg-input">
            	<form method="post" action="<%=basePath %>UserLoginServlet" id="loginForm">
                <table>
                    <tr>
                        <td class="tagName">用户名:</td>
                        <td class="tagCont">
                            <input type="text" name="username" id="Username" maxlength="20" class="txt" style="width: 150px; *width: 150px;" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tagName">密 码:</td>
                        <td class="tagCont">
                            <input type="password" name="password" id="Password" maxlength="50" class="txt" style="width: 150px; *width: 150px;" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="padding-left: 14px;">
                            <span id="" style="color: red;"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="tagName"></td>
                        <td class="tagCont">
                            <input type="button" class="btn" value="登 录" onclick="checkSubmit();"/>
                        </td>
                    </tr>
                </table>
                </form>
            </div>
        </div>
        <div class="function-remark">
            <p></p>

        </div>
    </div>
    <div class="lg-footer">
        
    </div>
</div>
		
		
		
		<script type="text/javascript" src="<%=basePath%>plug/js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>plug/js/UtilCookie.js"></script>
		<script type="text/javascript">
$(function() {
	var height = 0, lg_height = 0;
	lg_height = $(document).height() - 800 - 0 - 3;
	$('#lg-footer').css('height', lg_height);
	
	//选中记住我时
	$("#rememberme").click(function(){
		if(this.checked){
			SetCookieDays("loginName",$("#Username").val(),14);//記住用戶名兩周
		}else{
			delCookie("loginName");
		}
	});

	//当cookie不为空时，自动赋值用户名
	if(getCookies("loginName")!=null&&getCookies("loginName")!=undefined){
		$("#Username").val(getCookies("loginName"));
		$("#rememberme").attr("checked",true);
		$("#Password").focus();
	}
	$('#Username').focus();
});
function changeVcode() {
	document.getElementById('code_Img').src = "<%=basePath%>getVerifyCodeServlet?v="+Math.random();
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
		return false;
	} else if (document.getElementById('Password').value == "") {
		alert("密码输入不能为空!");
		document.getElementById('Password').focus();
		return false;
	} else if (document.getElementById('Password').value.indexOf("'") != -1) {
		alert("非法字符，请重新输入!");
		document.getElementById('Password').value = "";
		document.getElementById('Password').focus();
		return false;
	} else {
		document.getElementById('loginForm').submit();
		return true;
	}

}

</script>
	</body>
</html>