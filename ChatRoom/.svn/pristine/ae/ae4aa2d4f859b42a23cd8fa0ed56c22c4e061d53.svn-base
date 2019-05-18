<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<%@ include file="../plug/common.inc" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>

<script type="text/javascript">
	/* 补充js验证或者调用自己写的公共js插件 */
	
$(function(){
	$(".back").click(function(){
		window.location.href="ChatroomServlet?method=backtoindex";
	});
	
	$(".reset").click(function(){
		$("#username").val("");
		$("#nick").val("");
		$("#password").val("");
		$("#passwordcheck").val("");
		$("#email").val("");
		$("#phone").val("");
		$("#cardid").val("");
	});
	
});	
</script>
<style type="text/css">
	.form-horizontal{
		margin:50px auto;
		width:500px;
	}
	/* img{
		width:36px;
	} */
	.title{
		text-align:center;
	}
</style>
</head>
<body>

	<form id="myform" class="form-horizontal" action="ChatroomServlet?method=regedit" method="post" enctype="multipart/form-data" >
		<div class="form-group title">
			<h3>注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册</h3>
		</div>
		<div class="form-group">
			<label for="username" class="col-sm-2 control-label">用户名</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="username" 
						name="username"
						data-bv-require = "true"
					    data-bv-require-message = "* 用户名为必填项"
					    placeholder="用户名">${requestScope.usernamemsg }
			</div>
		</div>
		<div class="form-group">
			<label for="nick" class="col-sm-2 control-label">昵称</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="nick" 
						name="nick"
					    placeholder="昵称">
			</div>
		</div>
		<div class="form-group">
			<label for="u_img" class="col-sm-2 control-label">头像</label>
    		<input type="file" id="u_img" name="u_img" class="col-sm-2">
		</div>
		<!-- <div class="form-group">
			<label for="u_img" class="col-sm-2 control-label">预览</label>
			<div class="col-sm-10">
				<img src="images/u_icon.png" alt="头像" class="img-rounded"
						id="u_img" 
						name="u_img">
			</div>
		</div> -->
		<div class="form-group">
			<label for="password" class="col-sm-2 control-label">密码</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" 
						id="password" 
						name="password"
						data-bv-require = "true"
				   	    data-bv-require-message = "* 密码为必填项"
				   	    data-bv-password = "^\w{6,}$"
				   	    data-bv-password-message = "* 密码不能少于6位"
					    placeholder="密码">
			</div>
		</div>
		<div class="form-group">
			<label for="passwordcheck" class="col-sm-2 control-label">密码确认</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" 
						id="passwordcheck" 
						name="passwordcheck"
						data-bv-require = "true"
				   	    data-bv-require-message = "* 密码确认为必填项"
				   	    data-bv-password = "^\w{6,}$"
				   	    data-bv-password-message = "* 密码不能少于6位"
					    placeholder="密码确认"><!-- 验证方式在本页执行，该字段不传入下一页面 -->
			</div>
		</div>
		<div class="form-group">
			<label for="email" class="col-sm-2 control-label">邮箱</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="email" 
						name="email"
						data-bv-require = "true"
						data-bv-require-message = "* 邮箱为必填项"
						data-bv-email = "true"
						data-bv-email-message = "* 邮箱格式不合法"
					    placeholder="邮箱">${requestScope.emailmsg }
			</div>
		</div>
		<div class="form-group">
			<label for="phone" class="col-sm-2 control-label">联系方式</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="phone" 
						name="phone"
						data-bv-require = "true"
						data-bv-require-message = "* 联系方式为必填项"
					    placeholder="联系方式">${requestScope.phonemsg }
			</div>
		</div>
		<div class="form-group">
			<label for="cardid" class="col-sm-2 control-label">身份证</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="cardid" 
						name="cardid"
					    placeholder="身份证号码">
			</div>
		</div>
		
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<input class="btn btn-default" type="submit" value="注册">
				<button class="reset btn btn-default" type="button">重置</button>
				<button class="back btn btn-default" type="button">返回</button>
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