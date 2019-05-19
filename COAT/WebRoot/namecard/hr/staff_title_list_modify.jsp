<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'staff_title_list_modify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">
	<style>
  .tagName{width: 36%!important;}
  .tagCont{width: 64%!important;}
  </style>
  </head>
  
<body>
<div class="form-table">
	<table class="col-2-table">
		<tr>
	   		<td class="tagName">Position Title(Eng):</td>
	   		<td class="tagCont">
	   			<input type="text" id="modify_positionename" />
	   			<span class="ins">*</span>
	   		</td>
   		</tr>
   		<tr>
	   		<td class="tagName">Position Title(Chi):</td>
	   		<td class="tagCont">
	   			<input type="text" id="modify_positioncname" />
	   			<span class="ins">*</span>
	   		</td>
   		</tr>
   		<tr>
	   		<td class="tagName">Sumbit Name:</td>
	   		<td class="tagCont">
	   			<input type="text" class="readonly" readonly="readonly"  id="modify_addName" />
	   		</td>
   		</tr>
   		<tr>
   			<td class="tagName">Sumbit Date:</td>
	   		<td class="tagCont">
	   			<input type="text"  class="readonly" readonly="readonly" id="modify_addDate" />
	   		</td>
   		</tr>
   		<tr>
	   		<td class="tagName">Effective:</td>
	   		<td class="tagCont">
		   		<select id="modify_sfyx">
			   		<option value="Y">Effective</option>
			   		<option value="N">Invalid</option>
		   		</select>
		   		<input type="hidden" id="modify_save"/>
	   		</td>
   		</tr>
	</table>
</div>
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<script type="text/javascript" src="plug/js/Common.js"></script>
<script type="text/javascript">
var api = frameElement.api, data = api.data, W = api.opener;
api.button({
	name : 'Cancel'
},{
	name : 'Save',
	callback : saveModify
});
$(function(){
	$("#modify_positionename").val(data.position_ename);
	$("#modify_positioncname").val(data.position_cname);
	$("#modify_addName").val(data.add_name);
	$("#modify_addDate").val(data.add_date);
	$("#modify_sfyx").val(data.sfyx);
	$("#modify_save").val(data.id);
});
function saveModify(){
	if($("#modify_positionename").val()==""){
		W.error("please input Position Title(Eng)!");
		$("#modify_positionename").focus();
		return false;
	}else if($("#modify_positioncname").val()==""){
		W.error("Please Input Position Title(Chi)!");
		$("#modify_positioncname").focus();
		return false;
	}
	if(confirm("OK to Save the information?")){
		mylhgAjax("StaffPositionServlet",{"method":"modify","position_ename":$("#modify_positionename").val(),"position_cname":$("#modify_positioncname").val(),"sfyx":$("#modify_sfyx").val(),"positionId":$('#modify_save').val(),"modify_add_date":$("#modify_addDate").val()});
		return false;
	}
}
</script>
</body>
</html>
