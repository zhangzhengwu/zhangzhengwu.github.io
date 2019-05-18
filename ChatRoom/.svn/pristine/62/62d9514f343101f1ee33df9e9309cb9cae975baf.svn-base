<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../plug/common.inc" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线成员信息页面</title>
<script type="text/javascript">
$(function(){
	$(".back").click(function(){
		window.location.href="ChatroomServlet?method=backtohomepage";
	});
});

</script>
<style type="text/css">
	.box{
		width:800px;
		margin:50px auto;
	}

</style>
</head>
<body>
	<div class="box">
		<table class="table table-hover">
			<tr>
				<th>姓名</th>
				<th>昵称</th>
				<th>头像</th>
			</tr>
			<c:forEach items="${applicationScope.mapInfo }" var="ui" >
				<tr>
					<td>${ui.value.userName }</td>
					<td>${ui.value.userNick }</td>
					<td>${ui.value.userImg }</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="4" align="right" >
					<button class="back btn btn-default" type="button">返回</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>