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
	 

	<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.css">
	<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="<%=basePath%>plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="<%=basePath%>/plug/css/font-awesome-ie7.css">
	<![endif]--> 
	<link rel="stylesheet" href="<%=basePath%>css/layout.css">
	<link rel="stylesheet" href="<%=basePath%>plug/css/site.css">
	
	<script type="text/javascript" src="<%=basePath%>css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="<%=basePath%>plug/js/Common.js"></script>
	<script src="<%=basePath%>css/date.js"  type="text/javascript" ></script>
	<script src="<%=basePath%>css/Util.js"  type="text/javascript" ></script>
  </head>
  <style>
	.form-table table.col-2-table2 .tagName{text-align: right;padding-right: 10px;width: 15%;}
	.form-table table.col-2-table2 .tagCont{text-align: left;padding-left: 10px;width: 30%;}
  </style>
<body>
<div class="cont-info">
	<div class="form-table">
		<table class="col-2-table2">
			<tr>
				<td class="tagName">Staff Code：</td>
  				<td class="tagCont">
  					<input id="staffcode" type="text" name="staffcode" />&nbsp;<font color="red">*</font>
  				</td>
  				<td class="tagName">Client Name：</td>
  				<td class="tagCont">
  					<input id="clientname" type="text" name="clientname" />&nbsp;
  				</td>
  			</tr>
  			<tr>
  				<td class="tagName">Location：</td>
  				<td class="tagCont">
  					<select name="location" id="location">
			      			<option value="@CONVOY">@CONVOY</option>
			      			<option value="CP3">CP3</option>
			      	</select>&nbsp;<font color="red">*</font>
  				</td>
  				<td class="tagName">Request Date：</td>
  				<td class="tagCont">
  					<input id="scandate" name="scandate" type="text" readonly="readonly" onClick="Calendar('scandate')" />&nbsp;<font color="red">*</font>
  					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#scandate').val('');"></i>
  				</td>
  			</tr>
  			<tr>
	  			<td class="tagName">Document ID：</td>
  				<td class="tagCont">
  					<input type="text" id="documentId" />&nbsp;
  				</td>
  				<td class="tagName">Document Type：</td>
  				<td class="tagCont">
  					<input type="text" id="documentType" />&nbsp;<font color="red">*</font>
  				</td>
			</tr>
			<tr>
				<td class="tagName">Sender：</td>
  				<td class="tagCont">
  					<input type="text" id="sender"/>&nbsp;<font color="red">*</font>
  				</td>
			</tr>
			<tr>
				<td class="tagName">RemarkCons:</td>
				<td class="tagCont" colspan="3">
					<textarea name="result" id="result" cols="78" rows="5" style="width: 80%;"></textarea>
				</td>
			</tr>
		</table>
	</div>
</div>
<span id="test"></span>
</body>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	api.button({
		name : '返回'
	},{
		name : '保存',
		callback : save
	});
	
	function save(){
		if($("#staffcode").val()==""){
			W.error("Staffcode不能為空！");
			$("#staffcode").focus();
			return false;
		}
		if($("#location").val()==""){
			W.error("location不能為空！");
			$("#location").focus();
			return false;
		}
		if($("#documentType").val()==""){
			W.error("documentType不能為空！");
			$("#documentType").focus();
			return false;
		}
		if($("#scandate").val()==""){
			W.error("scandate不能為空！");
			$("#scandate").focus();
			return false;
		}
		if($("#sender").val()==""){
			W.error("sender不能為空！");
			$("#sender").focus();
			return false;
		}
		var data = {"method":"addList",'staffcode':$("#staffcode").val(),'clientname':$("#clientname").val(),
			'location':$("#location").val(),'documentType':$("#documentType").val(),'scandate':$("#scandate").val(),
			'sender':$("#sender").val(),'result':$("#result").val(),'documentId':$("#documentId").val()};
		mylhgAjax2("pickuprecord/PickUpRecordServlet",data);
		return false;
	}
	var d = new Date()
	var vYear = d.getFullYear()
	var vMon = d.getMonth() + 1
	var vDay = d.getDate()
	s=vYear.toString()+ '-' +(vMon<10 ? "0" + vMon : vMon).toString()+ '-' +(vDay<10 ? "0"+ vDay : vDay).toString();
	$('#scandate').val(s);
</script>
</html>
