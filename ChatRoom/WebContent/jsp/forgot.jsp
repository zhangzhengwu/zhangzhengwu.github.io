<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../plug/common.inc" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>忘记密码页面</title>
<script type="text/javascript">
	/* 补充js验证或者调用自己写的公共js插件 */
	
$(function(){
	$(".back").click(function(){
		window.location.href="ChatroomServlet?method=backtoindex";
	});
});		
</script>
<style type="text/css">
	.form-horizontal{
		margin:50px auto;
		width:500px;
	}
	.title{
		text-align:center;
	}
</style>

</head>
<body>

	<form id="myform" class="form-horizontal" action="ChatroomServlet?method=forgot" method="post" >
		<div class="form-group title">
			<h3>&nbsp;&nbsp;找&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;</h3>
		</div>
		<div class="form-group">
			<label for="username" class="col-sm-2 control-label">账户</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="username" 
						name="username"
						data-bv-require = "true"
					    data-bv-require-message = "* 账户为必填项"
					    placeholder="账户">${requestScope.usernamemsg }
			</div>
		</div>
		<div class="form-group">
			<label for="email" class="col-sm-2 control-label">邮箱</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="email" 
						name="email"
					    placeholder="邮箱">
			</div>
		</div>
		<div class="form-group">
			<label for="phone" class="col-sm-2 control-label">联系方式</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="phone" 
						name="phone"
					    placeholder="联系方式">
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
				<input class="btn btn-default" type="submit" value="找回密码">
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