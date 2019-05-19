<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'queryAdditional_modify.jsp' starting page</title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
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
  </head>
  
<body>
<div class="cont-info">
	<div class="form-table">
		<form id="myform">
		<table class="col-2-table">
			<tr>
				<td class="tagName">顾问编号: </td>
				<td class="tagcont">
					<input type="text" readonly="readonly" name="StaffNo" id="StaffNo" >
				</td>
			</tr>
			<tr>
				<td class="tagName">顾问姓名: </td>
				<td class="tagcont">
					<input type="text" name="Name" id="staffName" readonly="readonly"  >
				</td>
			</tr>
			<tr>
				<td class="tagName">添加数量: </td>
				<td class="tagcont">
					<input id="number" type="text" name="num" >
				</td>
			</tr>
			<tr>
				<td class="tagName">原因: </td>
				<td class="tagcont">
					<input name="Remark" id="Remark" type="text"  size="35">
				</td>
			</tr>
			<tr>
				<td class="tagName">是否有效:</td>
				<td class="tagcont">
					<select name="select" id="select"> 
			            <option value="Y" >有效</option>
			            <option value="N">无效</option>
			        </select>
				</td>
			</tr>
		</table>
		<input type="hidden" name="reremark" id="reremark"/>
		<input type="hidden" name="method" value="midify" id="method"/>
		</form>
	</div>
</div>
</body>
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
var data = api.data;

api.button({
	name : '返回'
},{
	name : '保存',
	callback : save
});
$(function(){
	$('#StaffNo').val(data.staffNo);
	$('#Remark,#reremark').val(data.remark);
	$('#staffName').val(data.name);
	$('#number').val(data.num);
	$('#select').val(data.sfyx);
	
});
function save()
{
	if($("#number").val()%100 !=0){
		W.error('Additional必須是100的整數倍！');
		$("#number").focus();
		return false;
	}
	else{
		mylhgAjax("additionquota/AdditionalQuotaServlet",$("#myform").serialize());
		return false;
	}
	return false;
}
</script>
</html>
