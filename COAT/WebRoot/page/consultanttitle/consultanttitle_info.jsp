<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'consultanttitle_info.jsp' starting page</title>
    
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
	<style>
	.col-4-table .tagName{
		width: 18%!important;
	}
	</style>
  </head>
  
<body>
<div class="cont-info">
	<div class="form-table">
		<table class="col-2-table">
			<tr>
				<td class="tagName">职位英文名称：</td>
				<td class="tagCont">
					<input type="text" name="" id="position_ename" />
				</td>
				
			</tr>
			<tr>
				<td class="tagName">职位中文名称：</td>
				<td class="tagCont">
					<input type="text" name="" id="position_cname" />
				</td>
			</tr>
			<tr>
				
				<td class="tagName">状态：</td>
				<td class="tagCont">
					<select id="position_sfyx">
						<option value="Y">有效</option>
						<option value="N">失效</option>
					</select>
				</td>
				
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;

$(function(){
	api.button({
		name: '确定',
		callback: function(){
			var data = {
				'position_ename': $('#position_ename').val(),
				'position_cname': $('#position_cname').val(),
				'position_sfyx': $('#position_sfyx').val(),
				'method': 'add'
			};
   			mylhgAjaxs("QueryConsultantTitleServlet",data);
   			return false;
		}
	},{
		name: '取消',
		callback: function(){
			
		}
	});
});
</script> 
</body>
</html>
