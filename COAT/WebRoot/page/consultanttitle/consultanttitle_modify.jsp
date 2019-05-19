<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'consultanttitle_modify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
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
					<input type="hidden" id="position_id"/>
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
var api = frameElement.api, W = api.opener, data = api.data.list;
$(function(){
	$('#position_ename').val(data.position_ename);
	$('#position_cname').val(data.position_cname);
	$('#position_sfyx').val(data.sfyx);
	$('#position_id').val(data.Id);
	
	api.button({
		name: '确定',
		callback: function(){
			var ename = $('#position_ename').val(),
				cname = $('#position_cname').val(),
				sfyx = $('#position_sfyx').val(),
				data = {
					'position_ename': ename,
					'position_cname': cname,
					'position_sfyx': sfyx,
					'id': $('#position_id').val(),
					'method': 'modify'
				};
				
			mylhgAjaxs("QueryConsultantTitleServlet",data);
			return false;
		}
	},{
		name: '取消',
		callback: function(){}
	});
});
</script>
</html>
