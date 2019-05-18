<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="plug/common.inc" %>    
<%
	String path1=request.getServletContext().getRealPath("/");
	// D:\Prosay\eclipse_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\20180915WebProject\
	String path2 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	// http://localhost:28080/20180919WebProject/
	String path3 = request.getServletContext().getContextPath();
	// /20180919WebProject
	String path4 = request.getRequestURI();
	// /20180919WebProject/index.jsp
	String path5 = request.getRequestURI().substring(request.getContextPath().length());
	// /index.jsp		
	String path6 = request.getContextPath();
	// /20180919WebProject
%>      
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<script type="text/javascript">

	$(function(){
	   document.onkeyup = showEnter;//控制按钮显示
	   document.onkeydown=BackSpace;//只读文本框禁用退格键
	   document.onkeydown = function(e){
	        if(!e) e = window.event;//火狐中是 window.event
	        if((e.keyCode || e.which) == 13){
	        	//敲击键盘Enter要触发的事件
	        	window.location.href="ChatroomServlet?method=login";
	        }
	    } 
		
		$(".press").click(function(){
			window.location.href="ChatroomServlet?method=toregedit";
		});
		
		$(".forgot").click(function(){
			window.location.href="ChatroomServlet?method=toforgot";
		});
	});
	

	function showEnter(e) {
		e = e ? e : event;// 兼容FF 
		if (e.shiftKey && e.altKey && e.keyCode == 76) {//组合快捷键   shift+alt+L
			$(".jok").show();
		} 
		if (e.shiftKey && e.altKey && e.keyCode == 75) {//组合快捷键   shift+alt+K
			$(".jok").hide();
		} 
	}
	
	
	function BackSpace(e){
		if(window.event.keyCode==8){//屏蔽退格键
		    var type=window.event.srcElement.type;//获取触发事件的对象类型
		  	//var tagName=window.event.srcElement.tagName;
		  	var reflag=window.event.srcElement.readOnly;//获取触发事件的对象是否只读
		  	var disflag=window.event.srcElement.disabled;//获取触发事件的对象是否可用
				if(type=="text"||type=="textarea"){//触发该事件的对象是文本框或者文本域
				    if(reflag||disflag){//只读或者不可用
				        //window.event.stopPropagation();
				        window.event.returnValue=false;//阻止浏览器默认动作的执行
				    }
				}else{ 
				    window.event.returnValue=false;//阻止浏览器默认动作的执行
				}
		 }
	}	
	

</script>
<style type="text/css">
	.form-horizontal{
		margin:200px auto;
		width:500px;
	}
	.title{
		text-align:center;
	}
</style>
</head>
<body>
	<form id="myform" class="form-horizontal" action="LoginServlet?method=login" method="post">
		<div class="form-group title">
			<h3>登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</h3>
		</div>
		<div class="form-group">
			<label for="loginmsg" class="col-sm-2 control-label">账户：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
					id="loginmsg"
					name="loginmsg"
					data-bv-require = "true"
					data-bv-require-message = "* 账户信息为必填项"
					placeholder="请输入用户名/手机号码/个人邮箱">${requestScope["userinfo"] }
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="col-sm-2 control-label">密码：</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" 
						id="password"
						name="password"
						data-bv-require = "true"
				   	    data-bv-require-message = "* 密码为必填项"
				   	    data-bv-password = "^\w{6,}$"
				   	    data-bv-password-message = "* 密码不能少于6位"
						placeholder="请输入密码">${requestScope["passinfo"] }
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="press btn btn-default" type="button">注册</button>
				<input class="btn btn-default" type="submit" value="登陆">
				<button class="forgot btn btn-default" type="button">忘记密码</button>
				<button class="jok btn btn-default" type="button" style="display: none;" >我出来了</button>
			</div>
		</div>
	</form>

<script>
$("#myform").bootstrapValidator({
		raise:"keyup",
		lang: "EN" 
	});
</script>	
</body>
</html>