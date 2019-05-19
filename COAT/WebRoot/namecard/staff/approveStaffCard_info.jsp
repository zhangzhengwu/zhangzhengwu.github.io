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
	<script src="css/date.js" language="JavaScript"></script>
	<link rel="stylesheet" href="plug/css/bootstrap.css">
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<style type="text/css">
	.tagName{width: 37%!important; }
	.tagCont{width: 63%!important; }
	</style>
  </head>
  
<body>
<div class="cont-info">
	<div class="form-table">
		<table class="col-2-table">
			<tr>
				<td class="tagName">Approve State：</td>
  				<td class="tagCont">
  					<input id="approveState" type="text" readonly="readonly" />
  				</td>
  			</tr>
  			<tr>
				<td class="tagName">Approve Date：</td>
  				<td class="tagCont">
  					<input id="approveDate" type="text" readonly="readonly" onClick="return Calendar('approveDate')" />
  					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#approveDate').val('');"></i>
  				</td>
  				<td><input type="hidden" id="refno" name="refno" /></td>
			</tr>
		</table>
	</div>
</div>
<span id="test"></span>
</body>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	var dataRole = api.data; 
	var result="";
	api.button({
		name : '返回'
	},{
		name : '保存',
		callback : save
	});
	
	$(function(){
		$('#refno').val(dataRole[0]);
		 if(dataRole[1]=="S")
		  	result="Pending";
	 	  else if(dataRole[1]=="E")//等待Department Head 审核
			result="Dept Approved";
		  else if(dataRole[1]=="R")//等待HR 审核
			result="HR Approved";
		  else if(dataRole[1]=="Y")
			result="Approved";
		  else if(dataRole[1]=="N")
			result="Rejected";
		  else if(dataRole[1]=="D")
			result="Delivery";
		  else if(dataRole[1]=="G")
			result="Receive";
		$('#approveState').val(result);
		
	});
	
	function save(){
		if($("#approveDate").val()==""){
			W.error("日期不能為空！");
			$("#approveDate").focus();
			return false;
		}
		var data = {"method":"addState",'refno':$("#refno").val(),'state':dataRole[1],'approveDate':$("#approveDate").val()};
		mylhgAjax2("StaffNameCardWriteServlet",data);
		
		W.queryProcess2($("#refno").val(),dataRole[2],1000);//--<<方法延迟1秒加载
		
		return false;
	}

</script>
</html>
