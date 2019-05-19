<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'UserManage_info.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
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
<div class="cont-info">
	<div class="form-table">
		<table class="col-2-table">
			<tr>
				<td class="tagName">用户名</td>
				<td class="tagCont">
					<input type="text" id="user" class="text"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">密码</td>
				<td class="tagCont">
					<input id="password"  class="text" type="password"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">确认密码</td>
				<td class="tagCont">
					<input id="againPassword"  class="text" type="password"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">身份</td>
				<td class="tagCont">
					<select name=isRoot id=isRoot>
				      <option value='0'>一般用户</option>
				      <option value='1'>管理员</option>
				       <option value='4'>Iris Team</option>
				       <option value='5'>Chloe Team</option>
				       <option value='98'>System UAT人员</option>
				      <option value='99'>COAT UAT人员</option>
				       <option value='100'>SZO UAT人员</option>
				    </select>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont">
					<span id="errorMsg" style="color: red;"></span>
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript">
var api = frameElement.api;

api.button({
	name : '保存',
	callback : save
},{
	name : '取消'
});
function save()
{	
	if($("#user").val()=="") {
		$('#errorMsg').text("用户名不能为空!");
		return false;
	}
	if($("#password").val().length < 6){
		$('#errorMsg').text("不能小于6个字符!");
		return false;
	}
	if($("#againPassword").val()!= $("#password").val()){
		$('#errorMsg').text("两次输入密码不一致!");
		return false;
	}else{
		$('#errorMsg').text('');
		if(confirm("是否保存？")){
		   $.ajax({
			type: "post",			
			url: "AdminServlet",
			data:{'adminUsername':$("#user").val(),'adminPassword':$("#password").val(),'isRoot':$("#isRoot").val(),"method":"add"},
			success:function(date){
				   if(date!=null){
					   alert(date);
					   //selects();
					   //$("#overs").hide();
					   window.location.href = window.location.href;
				   }else{
					   alert("连接失败");
				   }
				}			
			});
		   return false;
		}		 
 	 }
}
</script>
</body>
</html>
