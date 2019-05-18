<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../plug/common.inc" %>  
				 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<script type="text/javascript">
	$(function(){
		$(".back").click(function(){
			window.location.href="ChatroomServlet?method=backtohomepage";
		});	
		$(".firstpage").click(function(){
			window.location.href="ChatroomServlet?method=findpagemsg&str=first";
		});	
		$(".prevpage").click(function(){
			window.location.href="ChatroomServlet?method=findpagemsg&str=prev&pagenum=${requestScope.pagenum}";
		});	
		$(".nextpage").click(function(){
			window.location.href="ChatroomServlet?method=findpagemsg&str=next&pagenum=${requestScope.pagenum}";
		});	
		$(".lastpage").click(function(){
			window.location.href="ChatroomServlet?method=findpagemsg&str=last";
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
	<h2 align="right" > <img alt="个人头像" src="${sessionScope.u_img }"> 欢迎你！${sessionScope["u_nick"] }  <font color="blue" style="font-size:12px"><a href="ChatroomServlet?method=detail" style="font-size:16px" >修改资料</a> &nbsp;<a href="ChatroomServlet?method=logout" style="font-size:16px" >退出账号</a> 当前在线人数 <a href="ChatroomServlet?method=showonlines" style="font-size:16px" >${applicationScope.onlinenum }</a> 人  网页浏览共  ${applicationScope.countnum } 人次  </font> </h2>
	
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
	<form id="myform" class="form-horizontal" method="post" >
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="firstpage btn btn-default" type="button">首页</button>
				<c:if test="${requestScope.pagenum ne 1 }">
					<button class="prevpage btn btn-default" type="button">上一页</button>
				</c:if>
				<input class="btn btn-default" type="button" value="${requestScope.pagenum }">
				<c:if test="${requestScope.pagenum ne requestScope.lastpagenum }">
					<button class="nextpage btn btn-default" type="button">下一页</button>
				</c:if>
				<button class="lastpage btn btn-default" type="button">末页</button>
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