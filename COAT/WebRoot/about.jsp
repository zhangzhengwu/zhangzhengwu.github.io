<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>about system</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>plug/css/font-awesome.min.css">
<!--[if lte IE 7]> 
	<link rel="stylesheet"href="<%=basePath%>plug/css/font-awesome-ie7.css">
<![endif]-->
<link rel="stylesheet" href="<%=basePath%>css/layout.css">
<link rel="stylesheet" href="<%=basePath%>plug/css/site.css">
</HEAD>

<BODY bgcolor="" text="#000000" leftmargin="2" topmargin="2">
	<div class="cont-info">
		<div class="info-title" style="padding: 0px 0px!important;">
			<div class="title-head-bgcolor" style="position:relative;">
				<h1>
					<i class="icon-info-sign"></i> 关于系统
				</h1>
				<a class="btn" alt="打印"
					style="position:absolute;right:10px;top:3px;cursor:hand;"
					onclick="window.print()"> <i class="icon-print"></i> </a>
			</div>
			<div class="title-info" id="pPrint">
				<table style="margin-top:20px; width: 100%;">
					<tbody>
						<tr>
							<td class="tagName-col-2">系统名称：</td>
							<td class="tagCont-col-fluid">康宏深圳运营管理中心</td>
						</tr>
						<tr>
							<td class="tagName-col-2">系统版本：</td>
							<td class="tagCont-col-fluid">V 1.3.0</td>
						</tr>
						<tr>
							<td class="tagName-col-2">系统开发：</td>
							<td class="tagCont-col-fluid">CCIA ITD Development Team</td>
						</tr>
						<!--  暂时隐藏2015年8月21日15:30:23
				<tr>
					<td class="tagName-col-2">联系人：</td>
					<td class="tagCont-col-fluid">Wilson</td>
				</tr>
				<tr>
					<td class="tagName-col-2">联系电话：</td>
					<td class="tagCont-col-fluid">0755-61319896</td>
				</tr>
				<tr>
					<td class="tagName-col-2">OICQ：</td>
					<td class="tagCont-col-fluid"></td>
				</tr>
				<tr>
					<td class="tagName-col-2">Email：</td>
					<td class="tagCont-col-fluid">Wilson.shen@convoychina.com</td>
				</tr>
				 -->
						<tr>
							<td class="" colspan="2"
								style="text-align: right; padding-right: 20px;">Copyright ©
								2012. All Rights Reserved.</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
