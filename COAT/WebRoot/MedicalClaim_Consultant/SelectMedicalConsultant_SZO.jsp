<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>QueryMedicalConsultant</title>
 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">

	<meta http-equiv="description" content="This is my page">  
	
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="./css/style.css">

    <style type="text/css">
<!--
#add {
	position:absolute;
	z-index:-1;
	width: 100%;
	height: 713px;
	left: 4px;
	top: 10px;
	display:none;
	
}
#Medical {
	position:absolute;
	width:100%;
	z-index:-1;
	left: 3px;
	top: 3px;
}
.txt{
    color:#0000FF;
    border:0px;
   border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
 background-color:transparent; /* 顏色透明*/
	size:35px;
	font-style:oblique;
	font-family: '仿宋';
	font-size: 18px;

    }
	.txts{
    color:#005aa7;
    border:0px;
     border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:35px;
	font-family: '仿宋';
	font-size: 16px;

    }
.STYLE1 {
	font-size: 36px;
	font-weight: bold;
	color: #0000FF;
}
#content{
	position:absolute;
	width:100%;
	z-index:-1;
	left:3px;
	
}
-->
    </style>
  </head>
  <script type="text/javascript" >
  var downs=null;
  var k=0;
  $(function(){
	 
     $("#content").css("top",$("#Medical").height());
     $("#start_date").val(CurentTime());
     $("#end_date").val(CurentTime());
       $("#MedicalFee").bind("focus",function(){
		//  changeDates();
	 if($("#MedicalDate").val()>CurentTime2()){//报销单日期大于当前
		 alert("该员工报销单日期不能大于当天");
  $("#MedicalDate").focus();
			  return false;
	 }
		  if($("#HalfConsultant").val()!="Completed"){
		  if($("#MedicalDate").val()<$("#HalfConsultant").val()){
			  alert("该员工的报销单不在许可范围内！");
		
			  return false;
			  
		   }
		 }
	  });
	 
	  var vail="";
	 
	  $("#StaffNo").focus();
	var dates=null;
  /************************************StaffNo失去焦点****************************************************/
$("#StaffNo").blur(function(){
	  $.ajax({
		 type:"post",
		 async: false,
		 url:"QueryByStaffNoServlet",
		 data:{'StaffNo':$("#StaffNo").val()},
		 success:function(date){
		var Med=eval(date);
		
		 $("#resignDate").empty();
				  $("#messages").empty();
				   $("#Staff").empty();
				   	$("#save").removeAttr("disabled");
			 if(Med.length>0){
				  if(Med[0].staff_CodeDate==$("#StaffNoDate").val()){
					 //$("#messages").append("警告！該員工當前已有報銷記錄！");
					 $("#sameDay").val("Y");
				 }
				dates=(Med[0].resignDate);
				$("#StaffNo").val(Med[0].staffcode);
				 $("#EnglishName").val(Med[0].name);
				 $("#HalfConsultant").val(Med[0].half_Consultant);
				 $("#AD").val(Med[0].AD_type);
				 	 $("#sameDay").val("N");
				 if(Med[0].half_Consultant==""){
					 alert("该员工不能报销！");
				 $("#save").attr("disabled","disabled");
				 }if(Med[0].half_Consultant!=""){
					 $("#save").removeAttr("disabled");
				 }
				 if(dates==""){
					 $("#resignDate").append("在职");
				 }else{
					  $("#resignDate").append("离职("+changeDate(dates)+")");
				 }
				if(Med[0].terms_year==""){
					$("#used").val("0");
				}else if(Med[0].terms_year!=""){
					$("#used").val(Med[0].terms_year);
				}
				 if(Med[0].medical_Normal==""){
					$("#Normal").val("0");
				 }else if(Med[0].medical_Normal!=""){
					$("#Normal").val(Med[0].medical_Normal); 
				 }
				  if(Med[0].medical_Special==""){
					$("#Special").val("0");
				 }else if(Med[0].medical_Special!=""){
					$("#Special").val(Med[0].medical_Special); 
				 }
				 $("#StaffNoDate").val($("#StaffNo").val().toLocaleUpperCase()+CurentTime());
				 $("#MonenyMonth").val(getMon());
			
				 if(Med[0].staff_CodeDate==$("#StaffNoDate").val()){
					//  $("#messages").empty();
					// $("#messages").append("警告！該員工當前已有報銷記錄！");
					 $("#sameDay").val("Y");
				 }
				 if(parseFloat(Med[0].terms_year)==25){//当Normal和Special都超出限额
							alert("該員工在本年度的報銷限額已滿！");
							$("#save").attr("disabled","disabled");
							return false;
				}
				if(parseInt(Med[0].medical_Normal)>=15){//当Normal>=15时
						alert("該員工在本年度Normal報銷限額已滿！");
						$("#special").attr("checked","checked");
						return false;
				}else if(parseInt(Med[0].medical_Normal)<15){//当Normal<15时
						$("#special").removeAttr("checked");
				return false;
						 }
				if(parseInt(Med[0].medical_special)>=10){//当Special>=10时
						alert("該員工在本年度Spercial報銷限額已滿！");
						$("#special").removeAttr("checked");
						$("#special").attr("disabled",true);
						return false;
				}else if(parseInt(Med[0].medical_special)<10){//当Special<10时
						$("#special").attr("checked","checked");
						$("#special").removeAttr("disabled");
						return false;
					     }
				
			 }else{
				 $("#Staff").append("StaffNo不存在！");
				 $("#StaffNo").val("");
				 $("#StaffNo").focus();
			 }  
			   
		 }
	  });
  });
  /*******************************************************************************************************/
  /******************************************MedicalFee失去焦点时*****************************************/
  $("#MedicalFee").blur(function(){
	  if(isNaN($("#MedicalFee").val())){
		  alert("金額必須是數字！");
		   $("#MedicalFee").val("");
		 $("#MedicalFee").focus();
		  return;
	  }
	moeny();
  });
  /******************************************************************************************************/
  /*********************************************special框发生改变时**************************************/
  $("#special").change(function(){
	 moeny(); 
	 if( $("#special").attr("checked")==false){
	 if(parseInt($("#Normal").val())>=15){//当Normal>=15时
						alert("該員工在本年度Normal報銷限額已滿！");
						$("#special").attr("checked","checked");
				}else if(parseInt($("#Normal").val())<15){//当Normal<15时
						$("#special").removeAttr("checked");
						 }
	 }else{
	 	if(parseInt($("#Special").val())>=10){//当Special>=10时
						alert("該員工在本年度Spercial報銷限額已滿！");
						$("#special").removeAttr("checked");
						$("#special").attr("disabled",true);
				}else if(parseInt($("#Special").val())<10){//当Special<10时
						$("#special").attr("checked","checked");
						$("#special").removeAttr("disabled");
					     }
	 	}
	 	if(parseInt($("#Normal").val())>=15 && parseInt($("#Special").val())>=10){//当Normal和Special都超出限额
							alert("該員工在本年度的報銷限額已滿！");
							$("#save").attr("disabled","disabled");
				}
	
  });
  /****************************************************************************************************/
  /*****************************************************************************/
  $("body").keydown(function(e){
	if(e.keyCode==13){
$("#StaffNo").blur();
	}
	});
  /**************************************************************************************************/
  function moeny(){
	  if($("#MedicalFee").val()=="" || isNaN($("#MedicalFee").val())){
		  return;
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
	 } 
	   else{//职位在AD以下
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
  }/****************************************************************************************************/

  /**************************************保存事件*********************************************************/
  
  $("#save").click(function(){
	 // $("#messages").empty();
	  /******************************************判断是否允许提交****************************************/
	 if($("#StaffNo").val()==""){
		 alert("StaffNo不能为空！");
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
		 //changeDates();
		  
		var resignDate=changeDate(dates)
		 if(resignDate!=""){
			 
		 if(resignDate < $("#MedicalDate").val()){
			  $("#messages").append("警告！該職工在之前已經離職！");
			if(!confirm("                            警告！該員工已離職！\n StaffCode: "+$("#StaffNo").val()+
				"                Medical Fee: "+$("#MedicalFee").val()+"\n 離職日期： " +
			resignDate+"             Medical Date: "+$("#MedicalDate").val()+"\n                         確定要提交？")){
				return false;
			}
		 }
		
		 }
		
	 }
	 /*******************************************验证日期*******************************************/
	 
	  $.ajax({
			  type:"post",
			  url:"VailMedicalServlet",
			  data:{'StaffNo':$("#StaffNo").val(),'MedicalDate':$("#MedicalDate").val(),'Special':$("#special").val()},
			  success:function(date){
			  var num=eval(date);
		
				  if(parseFloat(num)>=1){
					   $("#MedicalDate").val("").html("");
						   if($("#special").attr("checked")==false){
							alert("该员工在当天已有Normal报销记录！");
						return false;
						}if($("#special").attr("checked")==true){
							alert("该员工在当天已有Special报销记录");
							return false;
							}
				  }if(parseFloat(num)<=0){
							  	 /*************************************************************************************************/
			 $.ajax({
				 type:"post",
				 url:"AddMedicalServlet",
				 data:{'StaffNo':$("#StaffNo").val(),'special':$("#special").val(),
				 'MedicalFee':$("#MedicalFee").val(),'MedicalDate':$("#MedicalDate").val(),
				 'EnglishName':$("#EnglishName").val(),'HalfConsultant':$("#HalfConsultant").val(),
				 'Amount':$("#Amount").val(),'StaffNoDate':$("#StaffNoDate").val(),
				 'used':$("#used").val(),'MonenyMonth':$("#MonenyMonth").val(),
				 'sameDay':$("#sameDay").val(),'AD':$("#AD").val(),'MedicalNormal':$("#Normal").val(),'MedicalSpecial':$("#Special").val(),"add_date":downs[k].add_date},
				 success:function(date){
					 var num=eval(date);
					 if(parseInt(num)>0){ 
						 alert("操作成功!");
				$("#Exit").click();
				$("#searchs").click();
			 
					 }else{
						 alert("操作失敗!");
					 }
					 
				 }
			 }) ;
	 
			
				  }
			  }
		  });

/**************************************************************************************************/


  });
 function changeDate(dates){
	   if(dates==""){
		   return "";
	   }
	var date=dates;
	var pattern=/^(19|20)\d{2}-(0?\d|1[012])-(0?\d|[12]\d|3[01])$/;
	var year=date.substr(0,4);
	var index1=date.indexOf("-"); 
	var index2=date.lastIndexOf("-"); 
	var cha=parseFloat(index2)-(parseFloat(index1)+1); 
	var month=date.substr((parseFloat(index1)+1),cha);
	var day=date.substr(index2+1);
	var ca,ba;
	if(month<10){
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
	if(pattern.test(dates)){
	date=year+ca+parseFloat(month)+ba+parseFloat(day);
	}
	return date;
}
  /*************************************************************获取当前时间***************************/
  function CurentTime()
    { 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
       //  var hh = now.getHours();            //时
       // var mm = now.getMinutes();          //分
        var clock = year;
        if(month < 10)
            clock += "0";
        clock += month;
        if(day < 10)
            clock += "0";
        clock += day + " ";
       //  if(hh < 10)
       //     clock += "0";
       //  clock += hh + ":";
       // if (mm < 10) clock += '0'; 
       //  clock += mm; 
        return(clock); 
    } 
   function CurentTime2()
    { 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
       //  var hh = now.getHours();            //时
       // var mm = now.getMinutes();          //分
        var clock = year+"-";
        if(month < 10)
            clock += "0";
        clock += month+"-";
        if(day < 10)
            clock += "0";
        clock += day + " ";
       //  if(hh < 10)
       //     clock += "0";
       //  clock += hh + ":";
       // if (mm < 10) clock += '0'; 
       //  clock += mm; 
        return(clock); 
    } 
  /***************************************************************************************************/
 function getMon(){
	  var now =new Date();
	  var day=now.getDate();
	  if(day<26){
		  return now.getMonth()+1;
	  }else{
		  return now.getMonth()+2;
	  }
 }
  /*******************************************************************************************************/
  /***********************************************保留兩位小數********************************************/
  function changeTwoDecimal_f(x)  
{  
var f_x = parseFloat(x);  
if (isNaN(f_x))  
{  
alert('function:changeTwoDecimal->parameter error');  
return false;  
}  
var f_x = Math.round(x*100)/100;  
var s_x = f_x.toString();  
var pos_decimal = s_x.indexOf('.');  
if (pos_decimal < 0)  
{  
pos_decimal = s_x.length;  
s_x += '.';  
}  
while (s_x.length <= pos_decimal + 2)  
{  
s_x += '0';  
}  
return s_x;  
}
  /*****************************************************************************************************/
     
     
     
     
     
     
/*********************************改变时间格式*********************************************/
	 	function CurentTime()
    { 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        var clock = year + "-";
         if(month < 10)
            clock += "0";
        clock += month + "-";
        if(day < 10)
           clock += "0";
        clock += day + "";
        return clock; 
    } 
/*****************************************************Down click**********************************/
	
	$("#down").click(function(){ 
		if(downs!=null){
			window.location.href="<%=basePath%>DownForConsServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
			}
	
	});
	/******************************************************Down END***********************************/
	
	/**************************************************selects Click*****************************************************/
	$("#searchs").click(function(){
	selects();
	});
/******************************************************************************************************************/
 /***********************************************select function************************************/
	function selects(){
				if($("#start_date").val()==""){
					alert("開始日期不能為空！");
					$("#start_date").focus();
					return false;
				}	
				if($("#end_date").val()==""){
					alert("結束日期不能為空！");
					$("#end_date").focus();
					return false;
				}
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("開始日期不能大於結束日期!");
						$("#start_date").focus();
						return false;
					}
				}
		 
				$.ajax({
				type: "post",
				url:"SelectMedicalConsultantServlet",
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'code':$("#staffcodes").val(),'name':$("#name").val()},
				success:function(data){
					var totals=0;
					var dataRole=eval(data);
					var html="";
					$("tr[id='select']").remove();
					downs=null;
					if(dataRole.length>0){
					downs=dataRole;
						for(var i=0;i<dataRole.length;i++){
												var state="";
												var colors="";
												if(dataRole[i].shzt=="Y"){
													state="已審核";
													colors="<tr id='select' title='"+i+"' style='background:#D5D5D5;'> ";
												}else if(dataRole[i].shzt=="N"){
													state="待審核";
															colors="<tr id='select' title='"+i+"' style='background:gray;'> ";
												}else{
													state="已拒絕";
															colors="<tr id='select' title='"+i+"' style='background:red;'> ";
												}
							html+=colors+"<td align='center'><a href='javascript:void(0);' onclick='roles("+i+");' >"+dataRole[i].staffcode+"</a></td><td align='center'>"
							+dataRole[i].name +"</td><td align='center'>"+dataRole[i].SP_type+
							"</td><td align='center'>"+dataRole[i].medical_date+"</td><td align='center'>"+dataRole[i].medical_Fee+
							"</td><td align='center'>"+dataRole[i].entitled_Fee+"</td><td align='center'>"
							+dataRole[i].medical_month +"</td><td align='center'>"+state+"</td><td>"+dataRole[i].remark+"</td></tr>";		 
						}
					}
					else{
						html+="<tr id='select'><td colspan='15' style='color:blue;size=5px' align='center'><b>"+"對不起，沒有您想要的數據!"+"</b></td></tr>";
					}
						 $("#jqajax:last").append(html);
					 	// $("tr[id='select']:odd").css("background","#COCOCO");
		                 //$("tr[id='select']:even").css("background","#D5D5D5");
				 }
			 });
	}
	/**************************************************************************************************/
	
	$("#Exit").click(function(){
		$("#select_div").show();
	  $("#add").hide();
	});
	
  });
  
  function updateMedical(){//拒絕
	  if($("#remark").val()==""){
		  alert("請填寫拒絕理由!");
		  $("#remark").focus();
		  return false;
	  }else{
	  $.ajax({
				type: "post",
				url:"UpdateMedicalState",
				data: {'staffcode':downs[k].staffcode,'add_date':downs[k].add_date,'remark':$("#remark").val()},
				success:function(data){
					if(data=="success"){
									 alert("操作成功!");
				$("#Exit").click();
				$("#searchs").click();
					}else{
						alert("操作失敗!");
					}
				}
				
				});
	  }
  }
  function roles(j){
	  k=j;
	  var sp_type=downs[j].SP_type;
	  $("#select_div").hide();
	  $("#add").show();
	  $("#StaffNo").val(downs[j].staffcode).blur();
  		if(downs[j].SP_type=="S"){
	    	  //  alert(0);
		  $("#special").attr("checked","checked").change();
		
	  }else{
		  $("#special").attr("checked","");
	  }
  		$("#special").attr("disabled","disabled");
	   
	  $("#MedicalDate").val(downs[j].medical_date);
	  $("#MedicalFee").val(downs[j].medical_Fee);
	  //alert(downs[j].SP_type);

	  $("#MedicalFee").blur();
	  $("#special").attr("disabled",true);
	  $("#remark").empty();
	  if(downs[j].shzt!="N"){
		  	$("#remark").empty().append(downs[j].remark);
		  $(".sh").attr("disabled","disabled");
	  }else{
		   $(".sh").attr("disabled","");
	  }
	$("#fujian").empty().append(downs[j].file).attr("href","upload/"+downs[j].file);
	  
  }
  
  
  </script>
  <body>
  <div id="select_div">
  <div id="Medical">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-ss">
    
            <tr>
              <td width="100%"   colspan="4" align="left"><div align="center" class="STYLE1">QueryMedicalConsultant<br>
              </div></td>
            </tr>
            <tr>
              <td   height="10" align="left">Start Date：
              <input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" readonly="readonly" />              
              </td>
              <td   height="10" align="left"> End Date：
              <input id="end_date" type="text" name="it" readonly="readonly" onClick="return Calendar('end_date')" /></td>
              <td   align="left" style="color:red">
             </td>
              <td  >&nbsp;</td>
            </tr>
            <tr >
              <td height="23" align="left">Staff Code ：
              <input type="text" name="staffcode" id="staffcodes"></td>
              <td height="23" align="left">Full&nbsp;Name ：
              <input type="text" name="names" id="name"></td>
              <td align="left" style="color:red"></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              
              <td colspan="4" align="left" style="color:red"> </td>
            </tr>
           
            <tr>
              <td colspan="3" align="right"><input id="searchs" name="search" type="submit" value="Search"/>             
               </td>
              <td ></td>
            </tr>
    </table>
    
  </div>
  <div id="content">
  <table width="100%"  border="1" cellpadding="0" cellspacing="0" class="table-ss" id="jqajax" >
      <tr id="title">
        <td width="8%"   align="center"><b>Staff Code</b></td>
        <td   align="center"><b>Name</b></td>
        <td   align="center"><b>SP</b></td>
        <td  align="center"><strong>Date</strong></td>
        <td   align="center"><strong>Consultaing Fee </strong></td>
        <td   align="center"><strong>Claimed Amount </strong></td>
   
        <td  align="center"><strong>Claimed Month </strong></td>
     
        <td   align="center"><strong>State</strong></td>
        <td width="35%"  align="center"><strong>Remark</strong></td>
      </tr>
    </table>
  </div></div>
  
   <div id="add">
  <table width="100%" height="773" border="0" cellpadding="0" cellspacing="0" class="table-ss">
  <tr><td height="89" colspan="6" align="center"><strong><font color="blue" size="+4">Medical Claim </font></strong></td>
  </tr>
  <tr><td height="55"><div align="right"><strong>Staff Code:</strong></div></td>
  <td  ><input id="StaffNo" type="text" disabled="disabled" class="txts"/><font color="#FF0000"><span id="Staff"></span></font></td>
  <td  > <div  align="right"><strong>Special:</strong></div></td>
    <td  ><input id="special" disabled="disabled"  type="checkbox" value="N"> </td>
    <td align="left"></td>
    <td></td>
  </tr>
  <tr>
  
    <td><div align="right"><strong>Medical Date:</strong></div></td>
    <td><input   type="text" class="txts" disabled="disabled" id="MedicalDate" onClick="return Calendar('MedicalDate')" /></td>
      <td height="46"><div align="right"><strong>Medical Fee:</strong></div></td>
    <td><input name="text3" type="text" id="MedicalFee" disabled="disabled" class="txts" onKeyPress="return check1(this);" onKeyUp="check2(this);"/></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="67"><div align="right"><strong>EnglishName:</strong></div></td>
    <td><input name="Input" id="EnglishName"   disabled="disabled" class="txt"/></td>
    <td><div align="right"><strong>Half_Consultant:</strong></div></td>
    <td><input name="text" type="text" disabled="disabled" class="txt" id="HalfConsultant"/></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="64"><div align="right"><strong>Amount Entitle:</strong></div></td>
    <td><input name="text4" type="text" disabled="disabled" id="Amount"  class="txt"/></td>
    <td><div align="right"><strong>Claim Month:</strong></div>      <strong> </strong></td>
    <td><input name="text6" type="text" disabled="disabled" id="MonenyMonth"  class="txt"/></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="20" colspan="5"><div align="center" class="txt"><strong>本年度的使用記錄</strong></div></td>
    <td>&nbsp;</td>
  </tr>
  <tr><td height="68"><div align="right"><strong>Total claims this year:</strong></div></td>
  <td><input name="text5" type="text" disabled="disabled" id="used"  class="txt"/></td>
  <td><input id="Medical" class="txt" /></td>
  <td><input name="text9" type="text" disabled="disabled" style="display:none" id="StaffNoDate"  class="txt"/></td><td>&nbsp;</td></tr>
  <tr>
    <td height="68"><div align="right"><strong>Amount of Normal: </strong></div></td>
    <td><input name="text52" type="text" disabled="disabled" id="Normal"  class="txt"/></td>
    <td><div align="right"><strong>Amount of Special: </strong></div></td>
    <td><input name="text522" type="text" disabled="disabled" id="Special"  class="txt"/></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="33"><div align="right">  <input id="sameDay" type="hidden" value="N"><strong>狀態:</strong>
      
    </div></td>
    <td><font color="#FF0000"><span id="resignDate"></span></font><input id="AD" type="hidden" ></td>
    <td  align="right"><strong>附件鏈接：</strong></td>
    <td colspan="2" ><a id="fujian"></a> </td>
  </tr>
  <tr>
   
  <td  align="right"><strong >拒絕理由:</strong></td>
  <td  > <textarea id="remark" cols="30" rows="4"></textarea></td>
    <td colspan="3" align="right"><input name="button" type="button" id="save" value="通過" class="sh"> &nbsp;&nbsp;
     <input name="button" type="button" onClick="updateMedical();"  id="fault" value="拒絕" class="sh">    &nbsp;&nbsp;
     <input name="button" type="button" id="Exit" value="返回">&nbsp;&nbsp;</td></tr>
  <tr><td height="263" colspan="6"><font size="20" color="#FF0000" face="仿宋"><span id="messages"></span></font></td></tr>
  </table>
  </div>
  </body>
</html>
