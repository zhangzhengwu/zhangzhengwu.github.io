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
<script type="text/javascript">
top.location.href="signin.jsp";
</script>

<!--[if IE 7]>
<link rel="stylesheet" href="plug/css/layout-ie7.css">
<![endif]-->
<style type="text/css">
body{
}
.btn-primary {
	color: #fff;
	background: #428bca;
	border-color: #357ebd;
}
</style>
<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>
</head>
<script type="text/javascript">
$('body').ready(function(){
	var height=0, lg_height=0;
	lg_height = $(document).height() - 800 - 0 - 3; 

	//$('#lg-footer').css('height',lg_height);
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
            	<form method="post" action="CheckLoginServlet" id="loginForm">
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
        <!-- <p>Copyright © ICS System </p> -->
    </div>
</div>
</body>
</html>