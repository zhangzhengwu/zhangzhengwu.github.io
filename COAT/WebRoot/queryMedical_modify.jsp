<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'queryMedical_modify.jsp' starting page</title>
    
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

</head>
  
<body>
<div class="form-table">
	<table class="col-4-table">
		<tr>
			<td class="tagName">Staff Code:</td>
			<td class="tagCont">
				<input name="text" type="text" class="txts" id="StaffNo" disabled="disabled"/>
             		<font color="#FF0000"><span id="Staff"></span></font>
			</td>
			<td class="tagName">Special:</td>
			<td class="tagCont">
				<input name="checkbox" type="checkbox" id="special" >
				<input id="s" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td class="tagName">Medical Fee:</td>
			<td class="tagCont">
				<input name="text3" type="text" id="MedicalFee" class="txts" onKeyPress="return check1(this);" onKeyUp="check2(this);"/>
			</td>
			<td class="tagName">Medical Date:</td>
			<td class="tagCont">
				<input name="text"   type="text" class="txts" id="MedicalDate" readonly="readonly" onClick="return Calendar('MedicalDate')" />
			</td>
		</tr>
		<tr>
			<td class="tagName">EnglishName:</td>
			<td class="tagCont">
				<input type="text" name="Input" id="EnglishName"   disabled="disabled" class="txt"/>
			</td>
			<td class="tagName">Half_Consultant:</td>
			<td class="tagCont">
				<input name="text" type="text" disabled="disabled" class="txt" id="HalfConsultant"/>
			</td>
		</tr>
		<tr>
			<td class="tagName">Consultant Amount:</td>
			<td class="tagCont">
				<input name="text4" type="text" disabled="disabled" id="Amount"  class="txt"/>
			</td>
			<td class="tagName">Staff_Date:</td>
			<td class="tagCont">
				<input name="text9" type="text" disabled="disabled" id="StaffNoDate"  class="txt"/>
			</td>
		</tr>
		<tr>
			<td class="tagName">Total of Year:</td>
			<td class="tagCont">
				<input name="text5" type="text" disabled="disabled" id="used"  class="txt"/>
			</td>
			<td class="tagName">Settlement Month:</td>
			<td class="tagCont">
				<input name="text6" type="text" id="MonenyMonth"  class="txt"/>
			</td>
		</tr>
		<tr>
			<td class="tagName">Normal Number:</td>
			<td class="tagCont">
				<input name="text52" type="text" disabled="disabled" id="Normal"  class="txt"/>
			</td>
			<td class="tagName">Special Number:</td>
			<td class="tagCont">
				<input name="text522" type="text" disabled="disabled" id="Special"  class="txt"/>
				<input name="hidden" type="hidden" id="AD">
	          	<font color="#FF0000"><span id="resignDate"></span></font>
	            <input name="hidden" type="hidden" id="updateDate" class="txts" >
	            <input id="SameDaye" type="hidden"/>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<script src="plug/js/Common.js" language="JavaScript"></script>
<script src="css/date.js" language="JavaScript"></script>
<script type="text/javascript">
var api = frameElement.api, data = api.data, W = api.opener;
api.button({
	name : '返回'
},{
	name : '修改',
	callback : save
});
$(function(){
	var spe=data.SP_type;
	$("#StaffNo").val(data.staffcode);
	$("#MedicalFee").val(data.medical_Fee);
	$("#MedicalDate").val(data.medical_date);
	$("#EnglishName").val(data.name);
	$("#HalfConsultant").val(data.half_Consultant);
	$("#Amount").val(data.entitled_Fee);
	$("#StaffNoDate").val(data.staff_CodeDate);
	$("#used").val(data.terms_year);
	$("#MonenyMonth").val(data.medical_month);
	$("#Normal").val(data.medical_Normal);
	$("#Special").val(data.medical_Special);
	$("#updateDate").val(data.upd_Date);
	$("#AD").val(data.AD_type);
	$("#SameDaye").val(data.sameDaye);
	$("#s").val(spe);
	if(spe!=""){
		$("#special").val("S");
		$("#special").attr("checked","checked");
	}else{
		$("#special").val("");
		$("#special").removeAttr("checked");
	}
	$("#MedicalFee").blur(function(){
	   if(isNaN($("#MedicalFee").val())){
		  alert("金額必須是數字！");
	   	  $("#MedicalFee").val("");
		  $("#MedicalFee").focus();
		  return false;
	   }
		moeny();
   });
	$("#special").change(function(){
	 moeny(); 
   });
		
		
		
});
function save(){
	if($("#StaffNo").val()==""){
		W.error("StaffNo不能为空！");
		$("#StaffNo").focus();
		return false;
	}
	if($("#MedicalFee").val()==""){
		alert("MedicalFee不能为空！");
		$("#MedicalFee").focus();
		return false;
	}
	if($("#MedicalDate").val()==""){
		alert("MedicalDate不能为空！");
		$("#MedicalDate").focus();
		return false;
	}if($("#MedicalDate").val()!=""){
		changeDates();
	}
	var data = {'special':$("#special").val(),'StaffNo':$("#StaffNo").val(),'Amount':$("#Amount").val(),'MedicalFee':$("#MedicalFee").val(),'MedicalDate':$("#MedicalDate").val(),'upd_Date':$("#updateDate").val(),'EnglishName':$("#EnglishName").val(),'HalfConsultant':$("#HalfConsultant").val(),'StaffNoDate':$("#StaffNoDate").val(),'used':$("#used").val(),'MonenyMonth':$("#MonenyMonth").val(),'medical_Normal':$("#Normal").val(),'medical_Special':$("#Special").val(),'AD':$("#AD").val(),'SameDaye':$("#SameDaye").val(),'S':$("#s").val()};
	mylhgAjax("MotifyMedicalServlet",data);
	return false;
}
 /*****************************************************格式化時間************************************/
function changeDates(){
	var date=$("#MedicalDate").val();
	if(date==""){
		return "";
	}
	var pattern=/^(19|20)\d{2}-(0?\d|1[012])-(0?\d|[12]\d|3[01])$/;
	var year=date.substr(0,4);
	var index1=date.indexOf("-"); 
	var index2=date.lastIndexOf("-"); 
	var cha=parseFloat(index2)-(parseFloat(index1)+1); 
	var month=date.substr((parseFloat(index1)+1),cha);
	var day=date.substr(index2+1);
	var ca,ba;
	if(parseFloat(month)<10){
		ca="-0";
	}
	else{
		ca="-";
	}
	if(parseFloat(day)<10){
		ba="-0";
	}else{
		ba="-";
	}
	if(pattern.test($("#MedicalDate").val())){
		$("#MedicalDate").val(year+ca+parseFloat(month)+ba+parseFloat(day));
	}
}
 /***************************************金額模式***************************************************/
function check1(obj)
{
	var v = obj.value;
	var reg = /^\d*\.(\d{2})$/gi;

	if (reg.test(obj.value)){
		return false;
	}
	return true;
}
function check2(obj)
{
	var v = obj.value; 
	obj.value = v.replace(/[^\d\.]/g,'');
}
function moeny(){
	  if($("#MedicalFee").val()=="" || isNaN($("#MedicalFee").val())){
		  return false;
	  }
	   if($("#AD").val()=="D"){//职位在AD或AD以上
		 if($("#special").attr("checked")==true){//专科上限240
			   $("#special").val("S");
			 if((parseFloat($("#MedicalFee").val())*0.8)>=480){
				 $("#Amount").val("480.00");
			 }else{
				 $("#Amount").val(changeTwoDecimal_f(parseFloat($("#MedicalFee").val())*0.8));
			 }
		 }else{//普科上限120
			  $("#special").val("");
			 if((parseFloat($("#MedicalFee").val())*0.8)>=240){
				 $("#Amount").val("240.00");
			 }else{
				  $("#Amount").val(changeTwoDecimal_f(parseFloat($("#MedicalFee").val())*0.8));
			 }
		 }
	 }else{//职位在AD以下
		    if($("#special").attr("checked")==true){//专科上限240
		    	 $("#special").val("S");
			 if((parseFloat($("#MedicalFee").val())*0.8)>=240){
				 $("#Amount").val("240.00");
			 }else{
				  $("#Amount").val(changeTwoDecimal_f(parseFloat($("#MedicalFee").val())*0.8));
			 }
		 }else{//普科上限120
			  $("#special").val("");
			 if((parseFloat($("#MedicalFee").val())*0.8)>=120){
				 $("#Amount").val("120.00");
			 }else{
				  $("#Amount").val(changeTwoDecimal_f(parseFloat($("#MedicalFee").val())*0.8));
			 }
		 }
	   }
  }
/***********************************************保留兩位小數********************************************/
  function changeTwoDecimal_f(x){  
		var f_x = parseFloat(x);  
		if (isNaN(f_x)){  
			alert('function:changeTwoDecimal->parameter error');  
			return false;  
		}  
		var f_x = Math.round(x*100)/100;  
		var s_x = f_x.toString();  
		var pos_decimal = s_x.indexOf('.');  
		if (pos_decimal < 0){  
			pos_decimal = s_x.length;  
			s_x += '.';  
		}  
		while (s_x.length <= pos_decimal + 2){  
			s_x += '0';  
		}  
		return s_x;  
}
</script>
</body>
</html>
