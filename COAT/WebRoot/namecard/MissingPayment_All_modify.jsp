<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MissingPayment_All_info.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">

	<style type="text/css">
	.tagCont input[type="text"]{width: 160px!important;}
	</style>
  </head>
  
<body>
<div class="form-table">
	<table class="col-4-table">
		<tr>
			<td class="tagName">Staff Code: </td>
			<td class="tagCont">
				<input id="updcode" type="text" class="txt">
				<input id="missid" type="hidden" name="missingid">
			</td>
			<td class="tagName">Staff Code2: </td>
			<td class="tagCont">
				<input id="updcode2" type="text" class="txt">
			</td>
		</tr>
		<tr>
			<td class="tagName">Policy Owner Name:</td>
			<td class="tagCont">
				<input id="updclientname" type="text" class="txt">
			</td>
			<td class="tagName">Policy Number:</td>
			<td class="tagCont">
				<input id="updpolicyno" type="text" class="txt">
			</td>
		</tr>
		<tr>
			<td class="tagName">Modal Premium:</td>
			<td class="tagCont">
				<input id="updpremiumsum" type="text" class="txt">
			</td>
			<td class="tagName">Last Update Date:</td>
			<td class="tagCont">
				<input id="updreceived" type="text" class="txt">
			</td>
		</tr>
		<tr>
			<td class="tagName">Principal:</td>
			<td class="tagCont">
				<input id="updprincipal" type="text" class="txt">
			</td>
			<td class="tagName">Premium Due Date:</td>
			<td class="tagCont">
				<input id="updpremiumdate" type="text" class="txt">
			</td>
		</tr>
		<tr>
			<td class="tagName">Next Collection Date:</td>
			<td class="tagCont">
				<input id="upddatafrom" type="text" class="txt">
			</td>
			<td class="tagName"> </td>
			<td class="tagCont">
				 
			</td>
		</tr>
		<tr>
			<td class="tagName">Remark: </td>
			<td class="tagCont" colspan="3">
				<textarea name="textarea" rows="2" cols="40" id="updremark" style="width: 94%; opacity:0.8"></textarea>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
var api = frameElement.api, data = api.data, W = api.opener;
api.button({
	name : '取消'
},{
	name : '保存',
	callback : save
});
$("#updcode").val(data.staffcode);
$("#updcode2").val(data.staffcode2);
$("#updprincipal").val(data.principal);
$("#updclientname").val(data.clientname);
$("#updpolicyno").val(data.policyno);
$("#updpremiumsum").val(data.missingsum);
$("#updreceived").val(data.receivedDate);
$("#updpremiumdate").val(data.premiumDate);
$("#upddatafrom").val(data.datafrom);
$("#updremark").val(data.reason);
$("#missid").val(data.id);

function save()
{
	if($("#missid").val() !=""){
		$.ajax({
		type: "post",
		url: "ModifyMissingPaymentServlet",
		data:{'Id':$("#missid").val(),'staffcode':$("#updcode").val(),'staffcode2':$("#updcode2").val(),
		'principal':$("#updprincipal").val(),'clientname':$("#updclientname").val(),
		'policyno':$("#updpolicyno").val(),'missingsum':$("#updpremiumsum").val(),
		'premiumdate':$("#updpremiumdate").val(),'reason':$("#updremark").val(),
		'updreceived':$("#updreceived").val(),'datefrom':$("#upddatafrom").val()},
			success:function(date){
				if(date!=null){
					alert(date);
					W.$("#policyno").val($("#updpolicyno").val());
					W.selects(1);
					api.close();
				}
			}
		
		});return false;
	}
	else{
		return false;
	}
}
</script>
</body>
</html>
