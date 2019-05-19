<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'location_info.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body>
<div class="form-table">
	<table class="col-2-table">
		<tr>
			<td class="tagName">Name:</td>
			<td class="tagCont">
				<input id="name" type="text" class="txt" must="true">
			</td>
		</tr>
		<tr>
			<td class="tagName">Info:</td>
			<td class="tagCont">
				<input id="locationInfo" type="text" class="txt">
			</td>
		</tr>
		<tr>
			<td class="tagName">Remark:</td>
			<td class="tagCont">
				<input id="remark" type="text" class="txt">
			</td>
		</tr>
		<tr>
			<td class="tagName"></td>
			<td class="tagCont">
				<span id="errorMsg"></span>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="plug/js/Common.js"></script>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
api.button({
	name : '保存',
	callback : save
},{
	name : '取消'
});

function save()
{
	if($("#name").val()!=""){
		var data = {
			'name' : $("#name").val(),
			'locationInfo' : $("#locationInfo").val(),
			'remark' : $("#remark").val(),
			'method' : 'save'
		};
		mylhgAjax('EntryLocationServlet',data);
	}else{
		W.error("Name不能为空！");
	}	
	return false;
}
</script>
</body>
</html>
