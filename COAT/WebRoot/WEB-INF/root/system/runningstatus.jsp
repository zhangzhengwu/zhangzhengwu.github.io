<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>
	<%
  Runtime run = Runtime.getRuntime();

  long max = run.maxMemory();

  long total = run.totalMemory();

  long free = run.freeMemory();

  long usable = max - total + free;

 
  
  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'runningstatus.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=basePath%>plug/css/font-awesome.min.css">
		<!--[if lte IE 7]> 
		<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
		<style>
			.cont-info{float:right;width:99%;padding-left:12px;}
			.info-search table input[type="text"]{height:14px;padding-top:0px;width:165px;}
			</style>
		<![endif]-->
		<link rel="stylesheet" href="<%=basePath%>css/layout.css">
		<link rel="stylesheet" href="<%=basePath%>plug/css/site.css">
		<link rel="stylesheet" href="<%=basePath%>plug/css/pager.css">
</head>

<body>





	<div class="cont-info">

		<div class="info-table">
			<table id="table_result">
				<thead>
					<tr>
						<th class="wid_5">Name</th>
						<th class="wid_10">Value</th>
						<th class="wid_85"></th>
					</tr>
				</thead>
					<tr >
						<td class="wid_5">最大内存</td>
						<td class="wid_10"><%=max/1024000f +" MB"%></td>
						<td class="wid_85"></td>
					</tr>
					<tr>
						<td class="wid_5">已分配内存</td>
						<td class="wid_10"><%=total/1024000f +" MB"%></td>
						<td class="wid_85"></td>
					</tr>
					<tr>
						<td class="wid_5">已分配内存中的剩余空间</td>
						<td class="wid_10"><%= free/1024000f +" MB"%></td>
						<td class="wid_85"></td>
					</tr>
					<tr>
						<td class="wid_5">最大可用内存</td>
						<td class="wid_10"><%= usable/1024000f +" MB"%></td>
						<td class="wid_85"></td>
					</tr>
					
			
			</table>



		</div>


	</div>


</body>
</html>
