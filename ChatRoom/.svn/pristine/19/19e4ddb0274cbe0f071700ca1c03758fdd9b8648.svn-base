<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../plug/common.inc" %>  
<%
	String rootPath = request.getContextPath();
	String uploadPath = rootPath + "/upload/";
%>				 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<script type="text/javascript">
	$(function(){
		$(".findall").click(function(){
			window.location.href="ChatroomServlet?method=findallmsg";
		});
	});


</script>

<style type="text/css">
	img{
		width:50px;
		height:50px;
	}
	.box,.form-horizontal{
		width:800px;
		margin:50px auto;
	}
</style>
</head>
<body>
	<h2 align="right" > <img alt="个人头像" src="<%=uploadPath %>${sessionScope.u_img }"> 欢迎你！${sessionScope["u_nick"] }  <font color="blue" style="font-size:12px"><a href="ChatroomServlet?method=detail" style="font-size:16px" >修改资料</a> &nbsp;<a href="ChatroomServlet?method=logout" style="font-size:16px" >退出账号</a> 当前在线人数 <a href="ChatroomServlet?method=showonlines" style="font-size:16px" >${applicationScope.onlinenum }</a> 人  网页浏览共  ${applicationScope.countnum } 人次  </font> </h2>
	
	<div class="box">
		<table class="table table-hover">
			<c:if test="${not empty requestScope.list}">
				<tr>
					<th>发送人</th>
					<th>信息内容</th>
					<th>发送时间</th>
					<th>接收人</th>
				</tr>
				<c:forEach items="${requestScope.list}" var="lg">
					<tr>
						<td>${lg.sendName}</td>
						<td>${lg.sendContent}</td>
						<td>${lg.sendTime}</td>
						
						<c:if test="${lg.receiverName eq null}">
							<td>
								ALL	
							</td>
						</c:if>
						<c:if test="${lg.receiverName ne null}">
							<td>
								${lg.receiverName}
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</c:if>	
		</table>
	</div>
	<form id="myform" class="form-horizontal" action="ChatroomServlet?method=sendmessage" method="post" >
		<div class="form-group">
			<label for="sendcontent" class="col-sm-2 control-label">发送内容</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="sendcontent" 
						name="sendcontent"
						data-bv-require = "true"
						data-bv-require-message = "* 发送内容不能为空"
					    placeholder="发送内容">
			</div>
		</div>
		<div class="form-group">
			<label for="receivername" class="col-sm-2 control-label">接收人</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" 
						id="receivername" 
						name="receivername"
					    placeholder="接收人">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<input class="btn btn-default" type="submit" value="发送">${requestScope.logmsg}
				<button class="findall btn btn-default" type="button">查询所有聊天记录</button>
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