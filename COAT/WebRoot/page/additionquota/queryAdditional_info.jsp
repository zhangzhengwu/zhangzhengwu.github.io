<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'queryAdditional_info.jsp' starting page</title>
    
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
		<table class="col-2-table">
			<tr>
				<td class="tagName">顾问编号:</td>
				<td class="tagCont">
					<input id="ins" type="text" class="txt" onblur="findStaffcode()">
				</td>
			</tr>
			<tr>
				<td class="tagName">顾问姓名:</td>
				<td class="tagCont">
					<input id="n" type="text" class="txt">
				</td>
			</tr>
			<tr>
				<td class="tagName">添加數量:</td>
				<td class="tagCont">100</td>
			</tr>
			<tr>
				<td class="tagName">原  因:</td>
				<td class="tagCont">
					<textarea name="textarea" rows="4" id="Re" style="opacity:0.6" onblur="findRemark()"></textarea>
				</td>
			</tr>
			<tr>
				<td class="tagName">是否有效:</td>
				<td class="tagCont">有效</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont">
					<span id="errormsg" style="color: red;"></span>
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	
	api.button({
		name : '返回'
	},{
		name : '保存',
		callback : save
	});
	/**
	 * 根据staffcode判断staff是否存在并且自动带出名字
	 * */
	function findStaffcode(){
		$.ajax({
			url:"StaffNoServlet",
			type:"post",
			data:{"StaffNo":$("#ins").val()},
			async:false,
			success:function(date){
				var dataRole=eval(date);
				if(dataRole==null || dataRole==""){
					W.error("顾问编号不存在");
					$("#ins").val("").focus();
				}else{
					$("#ins").val(dataRole[0].staffNo);
					$("#n").val(dataRole[0].name).blur();
				}
				return false;
			},error:function(){
				W.error("网络连接失败!");
		 		$("#ins").focus();
				return false;
			}
		});
	}
	
 /********************判断原因是否为空****************************************/
	function findRemark(){
		$.ajax({
			type: "post",
			url: "RepeatServlet",
			data:{'StaffNo':$("#ins").val(),'Remark':$("#Re").val()},
				success:function(date){
					if(date>0){
						W.error("数据已存在!");
						$("#Re").val("");
					}else{
						$("#errormsg").text("");
					}
					return false;
				}
			});
	}
 /********************判断原因是否为空结束****************************************/
	function save(){
		if($("#ins").val()==""){
			W.error("顾问编号不能為空！");
			$('#ins').focus();
			return false;
		}
		if($("#n").val()==""){
			W.error("顾问姓名不能為空！");
			$("#n").focus();
			return false;
		}
		if($("#Re").val()==""){
			W.error("原因不能為空！");
			$("#Re").focus();
			return false;
		}
		var data = {"method":"add",'ins':$("#ins").val(),'n':$("#n").val(),'Re':$("#Re").val()};
		mylhgAjax("additionquota/AdditionalQuotaServlet",data);
		return false;
	}

</script>
</html>
