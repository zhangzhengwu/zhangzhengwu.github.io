<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'departHead_info.jsp' starting page</title>
    
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
				<input type="text" id="add_dpt" />
	   			<span class="ins">*</span>
			</td>
		</tr>
		<tr>
			<td class="tagName">Department:</td>
			<td class="tagCont">
				<input type="text" id="add_department" />
			</td>
		</tr>
		<tr>
			<td class="tagName">Department Head:</td>
			<td class="tagCont">
				<input type="text" id="add_dpt_head" onkeyup="javascript:this.value=this.value.toUpperCase();"/>
	   			<span class="ins">*</span>
			</td>
		</tr>
		<tr>
			<td class="tagName">Department Head2:</td>
			<td class="tagCont">
				<input type="text" id="add_dpt_head2" onkeyup="javascript:this.value=this.value.toUpperCase();"/>
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
			<td class="tagName">Effective</td>
			<td class="tagCont">
				<select id="add_sfyx">
			   		<option value="Y">Effective</option>
		   		</select>
		   		<span class="ins">*</span>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
api.button({
	name : 'Cancel'
},{
	name : 'Save',
	callback : save
});

function save()
{
	if($("#add_dpt").val()==""){
		W.error("please input Department Abbreviation!");
		$("#add_dpt").focus();
		return false;
	}else if($("#add_dpt_head").val()==""){
		W.error("Please Input Depart_Head!");
		$("#add_dpt_head").focus();
		return false;
	}
	if(confirm("OK to Save the information?")){
		$.ajax({
			url:"StaffDepartHeadServlet",
			type:"post",
			data:{"method":"save","dpt":$("#add_dpt").val(),"department":$("#add_department").val(),"depart_head":$("#add_dpt_head").val(),"depart_head2":$("#add_dpt_head2").val(),"sfyx":$("#add_sfyx").val()},
			success:function(date){
				alert(date);
				W.selects(1);
				api.close();
			},error:function(){
				W.error("Network connection is failed, please try later...");
				return false;
			}
		});
		return false;
	}
}
</script>
</body>
</html>
