<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'consultantprotitle_info.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body>
<div class="cont-info">
	<div class="form-table">
		<table class="col-2-table">
			<tr>
				<td class="tagName">英文专业名称：</td>
				<td class="tagCont">
					<input type="text" name="" id="pro_title_ename" />
				</td>
				
			</tr>
			<tr>
				<td class="tagName">中文专业名称：</td>
				<td class="tagCont">
					<input type="text" name="" id="pro_title_cname" />
				</td>
			</tr>
			<tr>
				<td class="tagName">状态：</td>
				<td class="tagCont">
					<select id="pro_title_sfyx">
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
				'pro_title_ename': $('#pro_title_ename').val(),
				'pro_title_cname': $('#pro_title_cname').val(),
				'pro_title_sfyx': $('#pro_title_sfyx').val(),
				'method': 'add'
			};
   			mylhgAjaxs("QueryConsultantProTitleServlet",data);
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
