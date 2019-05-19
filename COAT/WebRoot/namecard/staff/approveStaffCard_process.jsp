<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'approveStaffCard_process.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="plug/js/Common.js"></script>
	<link rel="stylesheet" href="plug/css/bootstrap.css">
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<style type="text/css">
	
	.info-table table thead td{color: #333;}
	.info-table table td{height: 30px;}
	</style>
  </head>
  
  <body>
  <div class="cont-info" style="padding-left: 6px!important; ">
    <div class="info-table" style="min-width: 470px; max-width: 520px; border: none; ">
    	<table id="detailList">
    		<thead>
    		<tr>
    			<td class="width_30">操作人</td>
    			<td class="width_30">操作</td>
    			<td class="width_30">操作时间</td>
    		</tr>
    		</thead>
    	</table>
    </div>
  </div>
  </body>
  <script type="text/javascript">
  $(function(){
	  var api = frameElement.api, data = api.data, W = api.opener;
	  var html = '';
	  
	  $("tr.lists").remove();
	  for(var i=0; i<data.length; i++)
	  {
		  var state_en = translateEn(data[i].state);
		  html += '<tr class="lists">';
		  html += '<td>' + data[i].username + '</td>' +
		 		  '<td>' + state_en + '</td>' +
		  		  '<td>' + data[i].createdate + '</td>';
		  html += '</tr>';
	  }
	  $("#detailList").append(html);
	  
	  api.button({
		  name : '返回'
	  });
	 
  });
  
  function translateEn(data)
  {
	  var result = '';
	  if(typeof(data) == 'undefined' || data == '')
	  {
		  result = '';
	  }else{
		  if(data=="S")
		  	result="Pending";
	 	  else if(data=="E")//等待Department Head 审核
			result="Dept Approved";
		  else if(data=="R")//等待HR 审核
			result="HR Approved";
		  else if(data=="Y")
			result="Approved";
		  else if(data=="N")
			result="Rejected"
	  }
	  return result;
  }
  
  </script>
</html>
