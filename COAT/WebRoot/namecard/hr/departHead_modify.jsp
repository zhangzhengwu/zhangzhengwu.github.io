<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'departHead_modify.jsp' starting page</title>
    
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
.tagName{width: 40%!important;}
.tagCont{width: 60%!important;}
</style>
</head>
  
<body>
<div class="form-table">
   	<table class="col-2-table">
   		<tr>
	   		<td class="tagName">Department Abbreviation:</td>
	   		<td class="tagCont">
	   			<input type="text" id="modify_dpt" onkeyup="javascript:this.value=this.value.toUpperCase();"/>
	   			<span class="ins">*</span>
	   			<input type="hidden" id="modify_save" />
	   		</td>
   		</tr>
   		<tr>
	   		<td class="tagName">Department:</td>
	   		<td class="tagCont">
	   			<input type="text" id="modify_department" />
	   		</td>
   		</tr>
   			<tr>
	   		<td class="tagName">Department Head:</td>
	   		<td class="tagCont">
	   			<input type="text" id="modify_dpt_head" onkeyup="javascript:this.value=this.value.toUpperCase();"/>
	   			<span class="ins">*</span>
	   		</td>
   		</tr>
   		<tr>
	   		<td class="tagName">Department Head2:</td>
	   		<td class="tagCont">
	   			<input type="text" id="modify_dpt_head2" onkeyup="javascript:this.value=this.value.toUpperCase();"/>
	   		</td>
   		</tr>
   		<tr>
	   		<td class="tagName">Operating Name:</td>
	   		<td class="tagCont">
	   			<input type="text" class="readonly" readonly="readonly"  id="modify_addName" />
	   		</td>
   		</tr>
   		<tr>
   			<td class="tagName">Operating Date:</td>
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
	   			<span class="ins">*</span>
	   		</td>
   		</tr>
   	</table>
</div>
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<script type="text/javascript">
var api = frameElement.api, data = api.data, W = api.opener;
api.button({
	name : 'Cancel'
},{
	name : 'Save',
	callback : saveModify
});
$(function(){
	$("#modify_dpt").val(data.dpt);
	$("#modify_department").val(32312);
	$("#modify_dpt_head").val(data.depart_head);
	$("#modify_dpt_head2").val(data.depart_head_bak);
	
	
	$("#modify_addName").val(data.add_name);
	$("#modify_addDate").val(data.add_date);
	$("#modify_sfyx").val(data.sfyx);
	$("#modify_save").val(data.id);
});
function saveModify(){
	
	if($("#modify_dpt").val()==""){
		W.error("please input Department Abbreviation!");
		$("#modify_dpt").focus();
		return false;
	}else if($("#modify_dpt_head").val()==""){
		W.error("Please Input Depart_Head!");
		$("#modify_dpt_head").focus();
		return false;
	}
	
	if(confirm("OK to Save the information?")){
		$.ajax({
			url:"StaffDepartHeadServlet",
			type:"post",
			data:{"method":"modify","dpt":$("#modify_dpt").val(),"department":$("#modify_department").val(),"depart_head":$("#modify_dpt_head").val(),"depart_head2":$("#modify_dpt_head2").val(),"sfyx":$("#modify_sfyx").val(),"dptId":$('#modify_save').val(),"modify_add_date":$("#modify_addDate").val()},
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
