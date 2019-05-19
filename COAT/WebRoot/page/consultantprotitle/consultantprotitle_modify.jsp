<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'consultantprotitle_modify.jsp' starting page</title>
    
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
					<input type="text" name="" id="ename" />
				</td>
				
			</tr>
			<tr>
				<td class="tagName">中文专业名称：</td>
				<td class="tagCont">
					<input type="text" name="" id="cname" />
				</td>
			</tr>
			<tr>
				
				<td class="tagName">状态：</td>
				<td class="tagCont">
					<select id="sfyx">
						<option value="Y">有效</option>
						<option value="N">失效</option>
					</select>
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
var api = frameElement.api, W = api.opener, data = api.data.list;
$(function(){
	$('#ename').val(data.prof_title_ename);
	$('#cname').val(data.prof_title_cname);
	$('#sfyx').val(data.sfyx);
	
	api.button({
		name: '确定',
		callback: function(){
			var ename = $('#ename').val(),
				cname = $('#cname').val(),
				sfyx = $('#sfyx').val(),
				data = {
					'pro_title_ename': ename,
					'pro_title_cname': cname,
					'pro_title_sfyx': sfyx,
					'id': api.data.id,
					'method': 'modify'
				};
				
			mylhgAjaxs("QueryConsultantProTitleServlet",data);
			return false;
		}
	},{
		name: '取消',
		callback: function(){}
	});
});
</script>
</body>
</html>
