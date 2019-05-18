<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../plug/common.inc" %>    
<%
	String rootPath = request.getContextPath();
	String uploadPath = rootPath + "/upload/";
%>	

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户详细页面</title>
<script type="text/javascript">
	$(function(){
		$(".back").click(function(){
			window.location.href="ChatroomServlet?method=backtohomepage";
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
	
	.myimg{
		width:31px;
		height:31px;
		background: url("<%=uploadPath%>${sessionScope.u_img }") no-repeat 0px 0px/cover;
	}
	
	.img{
		width: 100%;
		height: 100%;
		opacity: 0;
	}
</style>
</head>
<body>

	<form id="myform" class="form-horizontal" action="ChatroomServlet?method=update" method="post" enctype="multipart/form-data" >
		<div class="form-group title">
			<h3>修&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;改</h3>
		</div>
		<div class="form-group">
			<label for="username" class="col-sm-2 control-label">用户名</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="username" 
						name="username"
						data-bv-require = "true"
					    data-bv-require-message = "* 用户名为必填项"
					    placeholder="用户名" value="${sessionScope.u_name }" >
			    <input type="hidden" name="uid" value="${sessionScope.u_id }" >
			</div>
		</div>
		<div class="form-group">
			<label for="nick" class="col-sm-2 control-label">昵称</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="nick" 
						name="nick"
					    placeholder="昵称" value="${sessionScope.u_nick }" >
			</div>
		</div>
		<div class="form-group">
			<label for="img" class="col-sm-2 control-label">头像</label>
			<div class="col-sm-10">
				<div class="myimg">
					<%-- <input type="text" class="form-control" 
							id="img" 
							name="img"
						    placeholder="头像" value="${sessionScope.u_img }" > --%>
					<input type="file" id="img" name="img" class="col-sm-2 img">
				</div>	    
			</div>
		</div>
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
					    placeholder="密码" value="${sessionScope.u_pwd }" >
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
					    placeholder="邮箱" value="${sessionScope.u_email }" >
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
					    placeholder="联系方式" value="${sessionScope.u_phone }" >
			</div>
		</div>
		<div class="form-group">
			<label for="cardid" class="col-sm-2 control-label">身份证</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="cardid" 
						name="cardid"
					    placeholder="身份证号码" value="${sessionScope.u_card_id }" >
			</div>
		</div>
		
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<input class="btn btn-default" type="submit" value="修改">
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