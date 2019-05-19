<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>pageInfo</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="<%=basePath%>/plug/css/pager.css">
<script type="text/javascript" src="plug/js/jquery-pager-1.0.js"></script>
  </head>
  
  <body>
 <div id="pageResult" class="pageinfo"></div>
 
 <script type="text/javascript">
 $(function(){
	 
 InitPager(50, 1);
 });
 
 </script>
  </body>
</html>
