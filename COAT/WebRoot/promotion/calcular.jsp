<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>promotion计算</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <script type="text/javascript" src="css/jquery-1.4.4.js"></script>
 
		<link type="text/css" rel="stylesheet" href="css/style.css"/>
 
<style type="text/css">
body{
width:100%;
overflow: hidden;
}
 #Layer2 {
	position:absolute;
	width:100%;
	height:100%;
	z-index:5;
	background-color:#999999;
	display:none;
	opacity:0.1;
	top: 0px;
	left: 0px;
}
.STYLE3 {font-size: 24px}
#login {
	position:absolute;
	width:500px;
	height:71px;
	z-index:100;
	left:430px;
	top: 350px;
	border-color:#99CCCC;
	display:none;
}
#data{
width: 100%;
height:80%;
overflow: auto;
border: 1px;
border-style:solid;
font-size: 16px;
 
font-weight: lighter;


}
#condition{ 
 width: 100%;
height:20%;
border: 1px;
border-style:solid;
font-size: 16px;
}<%--
#data td{
 border: 1px;
 border-top-style
 border--style: solid;
 border-color:#87cefa;
 height: 25px;
 
}
 
--%></style>

<script type="text/javascript">
$(function(){
	var selectedIndex=0;
 	$("#data").attr("scrollTop",10);//由于样式原因，把滚动条高度默认为10
	$("#data").scroll(function(){//当滚动条高度发生改变时
		if($("#data").attr("scrollTop")<=10){//当滚动条高度小于等于10
		$("#data").attr("scrollTop",10);//将滚动条高度默认为初始值10
	}
	})
	$("#code").blur(function(){
		$("#positionSelect").change();
	});
	$("#name").blur(function(){
		$("#positionSelect").change();
	});
	
/**
 * 职位select值发生改变时触发事件 
 *  */

$("#positionSelect").change(function(){
	var html="";
	$("tr[title='dataTr']").remove();
	if($("#positionSelect").val()==""){
		html+="<tr title='dataTr'><td colspan='43' align='center' ><strong>对不起，数据不存在!</strong></td></tr>";
	$("#dataTable:last").append(html);
	}else{
		$.ajax({
			url:"PositionInformationServlet",
			type:"post",
			data:{"position":$("#positionSelect").val(),"staffcode":$("#code").val(),"staffname":$("#name").val()},
			success:function(date){
				var dataRole=eval(date);
				if(dataRole.length>0){
					for(var i=0;i<dataRole.length;i++){
					html+="<tr title='dataTr'><td>"+dataRole[i].direct_Leader+"</td><td>"+
					dataRole[i].staffcode+"</td><td align='center'> "+dataRole[i].DD_VP+"</td><td>"+
					dataRole[i].joinTitle+"</td><td> "+dataRole[i].title+"</td><td>"+
					dataRole[i].trainnee+"</td><td> "+dataRole[i].staffName+"</td><td>"+
					dataRole[i].groupJoin+"</td><td>"+
					dataRole[i].trAppDate+"</td><td> "+dataRole[i].tr_Issue_Date+"</td><td>"+
					dataRole[i].lrAppDate+"</td><td> "+dataRole[i].lrIssueDate+"</td><td>"+
					dataRole[i].LR_Normal_License+"</td><td>"+dataRole[i].ITCompleted+"</td><td>"+
					dataRole[i].ITPhaseTwo+"</td><td align='center'>"+dataRole[i].addition_bv+"</td></tr>";
}
				}else{
					html+="<tr title='dataTr'><td colspan='43' align='center' ><strong>对不起，数据不存在!</strong></td></tr>";
				}
				$("#dataTable:last").append(html);
	  $("tr[title='dataTr']:odd").css("background","#COCOCO");
		                 $("tr[title='dataTr']:even").css("background","#F0F0F0");
	 
			},error:function(){
				alert("网络连接失败，请联系管理员或稍后重试....");
			}
		});
		
		
	}
	
	
});


$("#ok").click(function(){
	if($("#positionSelect").val()==""){
		alert("筛选条件不能为空!");
		return false;
	}
	if($("#positionSelect").get(0).selectedIndex < $("#positionSelect").get(0).length-1){//控制用户每次只能计算一轮
	$("#positionSelect").get(0).selectedIndex++; 
	}
	selectedIndex++;
	
});





/**
 * 开始按钮单机事件（向临时表中插入相应数据）
 * @return {TypeName} 
 */
$("#start").click(function(){
 
		op();
		$.ajax({
			url:"CalcularPromotionServlet",
			type:"post",
			data:null,
			success:function(date){
			cl();
			var i=parseFloat(date);
			if(i>0){
			$("#ok").attr("disabled","");
			$("#start").attr("disabled","disabled");	
				alert("初始化数据成功，请进行条件筛选!");
			}
			
			},error:function(){
				cl();
				alert("网络连接失败，请联系管理员或请稍后重试....");
				return false;
			}
				
		});
	
 
	
});









 function op(){
$("#login").show();
$("#Layer2").show().css("width",$("body").width());
$("#Layer2").css("opacity",0.6);
}
function cl(){
$("#login").hide();
$("#Layer2").hide();
}
 function getPosition(){
 $.ajax({
 url:"PositionServlet",
 type:"post",
 data:null,
 success:function(date){
 	var dataRole=eval(date); 
 	var html="<option value=''>请选择</option>";
 if(dataRole!=null){
 
 	for(var i=0;i<dataRole.length;i++){
 	html+="<option value='"+dataRole[i]+"'>"+dataRole[i]+"</option>";
 	}
 $("#positionSelect").empty().append(html);
 /**
  * 控制select控件的seletedIndex
  */
  if($("#positionSelect").get(0).selectedIndex==0){
	$("#positionSelect").get(0).selectedIndex=1; 
 }
 }
 
 },error:function(){
 alert("网络连接失败,请联系管理员或请稍后重试....");
 }
 });
  

 }
 
 
});
</script>
  </head>
  
  <body> 
  <center><strong style="font-size: 24px;color: blue;">Promotion Calcular </strong></center>
    <div id="data"  >
    
    <table width="100%" cellpadding="0" cellspacing="0" border="1" class="table-ss"  id="dataTable">
<tr style="POSITION: relative;TOP: expression(this.offsetParent.scrollTop-2);background:#C0C0C0;">
<%--<td align="center">Direct Team</td>
--%><th align="center">Direct Leader</th>
<%--<td align="center">Introducer</td>
--%><th align="center">Staff Code</th>
<th align="center">DD/VP</th>
<th align="center">Join Title</th>
<th align="center">Title</th>
<th align="center">Trainee</th>
<th align="center">Staff Name</th>
<th align="center">Date of Group Join</th>
<%--<td align="center">Resign Date</td>
--%><th align="center">TR App Date</th>
<th align="center">TR Issue Date</th>
<th align="center">LR App Date</th>
<th align="center">LR Issue Date</th>
<th align="center">LR Normal License</th>
<th align="center">IT Completed Date</th>
<th align="center">IT Phase Two</th>
<%--<td align="center">Half-Consultant</td>
<td align="center">Consultant</td>
<td align="center">S Consultant 1</td>
<td align="center">S Consultant 2</td>
<td align="center">Principal Consultant</td>
<td align="center">Wealth Manager</td>
<td align="center">S Wealth Manager</td>
<td align="center">S Wealth Manager 2</td>
<td align="center">P Wealth Manager</td>
<td align="center">Associate</td>
<td align="center">Ass Director</td>
<td align="center">Deputy Director</td>
<td align="center">Regional Director</td>
<td align="center">Ass Vice President</td>
<td align="center">Vice President</td>
<td align="center">Sr Vice President</td>
<td align="center">Adjustment</td>
 <td align="center">2003 BV</td>
<td align="center">2004 BV</td>
<td align="center">2005 BV</td>
<td align="center">2006 BV</td>
<td align="center">2007 BV</td>
--%><th align="center">Addition BV</th>
<%--<td align="center">BV Enough To CO1A</td>
<td align="center">BV Enough To CO2</td>
--%></tr>


    </table>
    </div> 
    <div id="condition">
    <table width="100%" cellpadding="0" cellspacing="0" >
    <tr>
    <td><input value="开始" type="button" id="start"  /></td>
    <td align="right">职位筛选：</td>
    <td align="left">
    <select id="positionSelect">
    <option>请选择</option>
    <option value="Half-Consultant">Half-Consultant</option>
    <option value="CON1">CON</option>
    <option value="CON">WMA</option>
    <option value="WMA">SC1</option>
    <option value="SC1">SWMA1</option>
    <option value="SWMA1">SC2</option>
    <option value="SC2">SWMA2</option>
    <option value="SWMA2">PC</option>
    <option value="PC">CWMA</option>
    <option value="CWMA">AAD</option>
    <option value="AAD">AD</option>
    <option value="AD">DD</option>
    <option value="DD">VP</option>
    <option value="VP">SVP</option>
    </select>
    </td>
     <td><input id="ok" type="button" value="计算"  /></td>
    <td align="right">员工编号：</td>
    <td align="left"><input id="code" type="text"/></td>
    <td align="right">员工姓名：</td>
    <td><input id="name" type="text"/></td>
   
    </tr>
    </table>
    </div>
     <div id="Layer2">
 <div id="login">
    <p align="center"><img src="images/022.gif" width="32" height="32"></p>
    <p><span style="color:blue; font-family:'隶书';"><span class="STYLE3"><b>正在處理數據,請不要強行關閉網頁，謝謝！</b></span></span></p>
 </div> 
 </div>
    
  </body>
</html>
