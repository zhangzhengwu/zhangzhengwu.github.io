<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'department_info.jsp' starting page</title>
    
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
	<style type="text/css">
	.tagName{width: 40%!important;}
	.tagCont{width: 60%!important;}
	</style>
  </head>
  
<body>
<div class="form-table">
	<table class="col-2-table">
		<tr>
			<td class="tagName">Department(Eng):</td>
			<td class="tagCont">
				<input type="text" id="add_dpt" /><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tagName">Department(Chi):</td>
			<td class="tagCont">
				<input type="text" id="add_department" /><font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="tagName">Sumbit Name:</td>
			<td class="tagCont">
				<input type="text" class="readonly" readonly="readonly"  id="add_addName" />
			</td>
		</tr>
		<tr>
			<td class="tagName">Sumbit Date:</td>
			<td class="tagCont">
				<input type="text" class="readonly" readonly="readonly" id="add_addDate" />
			</td>
		</tr>
		<tr>
			<td class="tagName">Effective:</td>
			<td class="tagCont">
				<select id="add_sfyx">
			   		<option value="Y">Effective</option>
		   		</select>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
$("#add_dpt").focus();

api.button({
	name : 'Cancel'
},{
	name : 'Save',
	callback : save
});

function save(){
	var dpt = $("#add_dpt").val();
	if(dpt == null || dpt == ""){
		W.error("please input Department(Eng)!");
		$("#add_dpt").focus();
		return false;
	}
	var department = $("#add_department").val();
	if(department == null || department == ""){
		W.error("please input Department(Chi)!");
		$("#add_department").focus();
		return false;
	}
 
	if(confirm("OK to Save the information?")){
		$.ajax({
			url:"DepartmentServlet",
			type:"post",
			data:{"method":"save","dpt":dpt,"department":department,"sfyx":$("#add_sfyx").val()},
			success:function(date){
				alert(date);
				W.selects(1);
				api.close();
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
		});
		return false;
	}
}
</script>
</body>
</html>