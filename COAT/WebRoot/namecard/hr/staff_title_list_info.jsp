<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'staff_title_list_info.jsp' starting page</title>
    
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
	   			<input type="text" id="add_positionename" />
	   			<span class="ins">*</span>
	   		</td>
   		</tr>
   		<tr>
	   		<td class="tagName">Position Title(Chi):</td>
	   		<td class="tagCont">
	   			<input type="text" id="add_positioncname" />
	   			<span class="ins">*</span>
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
<script type="text/javascript" src="plug/js/Common.js"></script>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
api.button({
	name : 'Cancel'
},{
	name : 'Save',
	callback : save
});
$("#add_positionename").focus();

function save(){
	if($("#add_positionename").val()==""){
		W.error("please input Position Title(Eng)!");
		$("#add_positionename").focus();
		return false;
	}else if($("#add_positioncname").val()==""){
		W.error("Please Input Position Title(Chi)!");
		$("#add_positioncname").focus();
		return false;
	}
	if(confirm("OK to Save the information?")){
		mylhgAjax("StaffPositionServlet",{"method":"save","position_ename":$("#add_positionename").val(),"position_cname":$("#add_positioncname").val(),"sfyx":$("#add_sfyx").val()});
		return false;
	}
}
</script>
</body>
</html>
