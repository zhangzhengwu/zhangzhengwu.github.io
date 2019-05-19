<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查詢頁面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="plug/js/Common.js"></script>
 	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="<%=basePath%>/css/layout.css">
	<link rel="stylesheet" href="<%=basePath%>/plug/css/site.css">
	<script src="<%=basePath%>/css/date.js" language="JavaScript"></script>
	<script src="<%=basePath%>/css/Util.js" language="JavaScript"></script>
	<style type="text/css">
 	/* -------->临时添加 */
 	body{
 		background: #fff!important;
 	}
 	table{
 		width: 99%;
 		height:555px;
 		margin: 0px auto;
 	}
 	table hr{
 		margin: 10px 0px!important;
 	}
 	table h4{
 		padding-left: 5px;
 	}
 	
 	.mailTag{
 		width: 60px;
 		font-size: 12px;
 		padding-left: 5px;
 	}
 	.mailCont{
 		font-size: 12px;
 		padding-left: 5px;
 	}
 	.mailCont span{
 		color: #428bca!important;
 		font-size: 0.9em!important;
 	}
 	/* <--------临时添加 */
	
	.viable_span{
		border-radius: 4px;
		border: 1px solid rgb(223, 223, 223);
		height: 22px;
		vertical-align: middle;
		line-height: 20px;
		min-width: 50px;
		text-align: center;
		margin: 0px 5px 0px 0px;
		padding: 0px 5px;
		background-color: rgb(252, 252, 252);
		cursor: pointer;
	}
	
	</style>
</head>
	<body>
	<table>
		<tr>
			<td colspan="3">
				<h4 id="subject"></h4>
			</td>
		</tr>
		<tr>
			<td class="mailTag">发件人：</td>
			<td class="mailCont" colspan="2">
				<div id="sender"></div>
			</td>
		</tr>
		<tr>
			<td class="mailTag">时&#12288;间：</td>
			<td class="mailCont" colspan="2">
				<div id="sendTime"></div>
			</td>
		</tr>
		<tr><td class="mailTag">收件人：</td><td class="mailCont" colspan="2"><div id="to"></div></td></tr>
		
		<tr><td colspan="3"><hr /></td></tr>
		<tr><td colspan="3" style="height:90%;text-align:left;"><div id="content" style="height:100%;width:100%; padding: 0px 5px;"></div></td></tr>
		<tr><td colspan="3"><hr /></td></tr>
		<tr><td class="mailTag">附件:</td><td class="mailCont"><div id="attr"></div></td></tr>
	</table>
<script type="text/javascript" >

var api = frameElement.api, W = api.opener;
var data = api.data;
var Roledate=eval(data);
api.button({
	name : '取消'
});
	$(function(){
		$("#subject").append(Roledate[0].subject);
		
		$("#content").append(Roledate[0].content);
		$("#attr").append(Roledate[0].attr);
		$("#sender").append(Roledate[0].creator);
		$("#sendTime").append(Roledate[0].createdate);
		var to=Roledate[0].recipient.split(",");
		var to1=Roledate[0].remark4.split(",");
		var tohtml="";

		for(var i=0;i<to.length;i++){
			if(to[i] != ""){
				tohtml+="<span class='viable_span'>"+to[i]+"</span>";	
			}else{
				tohtml += "";
			}
			
		}
		for(var i=0;i<to1.length;i++){
			if(to1[i] != ""){
				tohtml+="<span class='viable_span'>"+to1[i]+"</span>";
			}else{
				tohtml +="";
			}
		}
		$("#to").append(tohtml);
		/**
		$("#type").val(Roledate[0].type);
		$("#subject").val(Roledate[0].subject);
		$("#attr").val(Roledate[0].attr);
		$("#company").val(Roledate[0].company);
		$("#creator").val(Roledate[0].creator);
		$("#createdate").val(Roledate[0].createdate);
		
		$("#content").html(Roledate[0].content);**/
	
	
	
	});
	

</script>

	</body>
</html>
