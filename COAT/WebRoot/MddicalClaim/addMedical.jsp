<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title> addMedical Claim</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">

	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
<style type="text/css">
.info-form .tagName{width: 15%!important;}
.info-form .tagCont{width: 35%!important;}
</style>
</head>
  <script type="text/javascript">
var Consultantemail=null;
  /***************************************金額模式***************************************************/
 function check2(obj)
{
var v = obj.value; 
if(v.indexOf('.')==0){
	obj.value="";
}else if(v.indexOf('.')>0 && v.lastIndexOf('.')<v.length-2 && v.length-2>=0){
	obj.value=v.substring(0,v.lastIndexOf('.')+3).replace(/[^\d\.]/g,'');
}else if(v.indexOf("..")==v.length-2 && v.length-2>=0){//判断小数点是不是最后一位
	 obj.value=v.substring(0,v.length-1).replace(/[^\d\.]/g,'');
}
else{
	if(v.indexOf('.')>0 && v.indexOf('.')<v.length-2 && v.length-2>=0){
		if(v.lastIndexOf('.')==v.length-1){
			obj.value=v.substring(0,v.length-1).replace(/[^\d\.]/g,'');
		}
		else{
			 obj.value = v.replace(/[^\d\.]/g,'');
		}
	}else{
		 obj.value = v.replace(/[^\d\.]/g,'');
	}
}

}
/***************************************************************************************************/
  
  $(function(){
	  getReject_information("Medical","Consultant","query");;
	  $('#StaffNo').focus();
	  $("#MedicalFee").bind("focus",function(){
		  if($("#MedicalDate").val()==""){
			  error("Medical Date不能为空!");
			  $('#MedicalDate').focus();
			  return false;
		  }else{
			  $("#messages").empty();
		  }
		 if($("#MedicalDate").val()>CurentTime2()){//报销单日期大于当前
				 error("该员工报销单日期不能大于当天");
				 $("#MedicalDate").focus();
				 return false;
		 }else {
			     var dateStart=new Date($("#MedicalDate").val().replace(/-/,"/"));
				 var dateEnd=new Date(); 
				 var y_gap=dateEnd.getYear()-dateStart.getYear();
				 var m_gap=0;
			if(y_gap>0){
				 m_gap=dateEnd.getMonth()-dateStart.getMonth()+(12*y_gap);
			}else{
			 	 m_gap=dateEnd.getMonth()-dateStart.getMonth();
			}
				 var d_gap=dateEnd.getDate()-dateStart.getDate();
			if (y_gap>=0 && ((m_gap==3&&d_gap>0)||m_gap>3)) {  
				 error("该报销单日期不在三个月有效期内!");
				 return false;
			} 
		 }
	 
		  if($("#HalfConsultant").val()!="Completed"){
				  if($("#MedicalDate").val()<$("#HalfConsultant").val()){
					  error("该员工的报销单不在许可范围内！");
					  return false;
				   }
			 }
		  
	  });
 	var vail="";
   	var dates=null;
	//$("#StaffNo").focus();
  /************************************StaffNo失去焦点****************************************************/
  
$("#StaffNo").blur(function(){
	$("[id!='StaffNo'][type='text'],#remarks,#subject,#reject_information").val("");


	legal();//排除Out Put 中存在的staffcode
	

	$("#save").attr("disabled","");
  });
  
  $("#reject_information").change(function(){
	var vals=$("#reject_information").find("option:selected").text().substring(2);
			var rep=$("#remarks").val();
	if($("#remarks").html().indexOf(vals)<0){
		var num=(rep.split("%0b").length-1);
 if(rep==""){
	 rep="1.";
 }else{
	 rep+=(num+1)+".";
 }
		$("#remarks").val(rep+vals+"\r\n");
	 
	}else{
 
		//$("#remarks").val(rep.replace((vals),''));   取消重复选择为删除的功能
	}
});



function legal(){
	$.ajax({
		type:"post",
		url: "MedicalServlet",
		data:{"method":"legal",
			  "staffcode":$("#StaffNo").val()},
		success: function(date){
		var dataRole = eval(date);
		console.log(dataRole);
			if(dataRole > 0){
				error("该顾问不能进行医疗费用申请 !");
				$("#save").attr("disabled","disabled");//使保存按钮处于可提交状态						
			}else{
				findByStaffcode("staffcode");
			}
		},error:function(){
  			  alert("Network connection is failed, please try later...");
 			  return false;
  		  }
	});
	return false;
}

  function findByStaffcode(ty){
	   $.ajax({
		 type:"post",
		 url:"QueryByStaffNoServlet",
		 async:false,
		 data:{'StaffNo':$("#StaffNo").val(),'MedicalDate':$("#MedicalDate").val()},
		 success:function(date){
		 var Med=eval(date);
		 $("#resignDate").empty();
				 	$("#messages,#Staff").empty();
				   	$("#save").removeAttr("disabled");
			 if(Med.length>0){
				  if(Med[0].staff_CodeDate==$("#StaffNoDate").val()){
					$("#sameDay").val("Y");//$("#messages").append("警告！該員工當前已有報銷記錄！");
				 }
					dates=(Med[0].resignDate);
				if(ty=="staffcode"){//staffcode blur
					$("#StaffNo").val(Med[0].staffcode);//获取staffcode
					$("#EnglishName").val(Med[0].name);//获取staffName
					$("#AD").val(Med[0].AD_type);//获取是否是AD的标志
					$("#sameDay").val("N");//初始化同一天出现两条单
					 if(Med[0].half_Consultant==""){//判断Half_Consultant为空
						 error("請核實申請!");
						 $("#save").attr("disabled","disabled");//使保存按钮处于禁用状态
					 }else if(Med[0].half_Consultant!=""){//判断Half_Consultant不为空
						 $("#HalfConsultant").val(Med[0].half_Consultant);//获取HalfConsultant
						 $("#save").attr("disabled",false);//使保存按钮处于可提交状态
					 }
				 }else{//MedicalDate Fee blur
					$("#AD").val(Med[0].AD_type);//获取是否是AD的标志
					$("#mymessage").empty().append("The use of the "+$("#MedicalDate").val().substring(0,4)+" record");
					 if(dates==""){//判断离职日期为空
						$("#resignDate").append("在职");
					 }else{//判断离职日期不为空
						$("#resignDate").append("离职("+changeDate(dates)+")");
					 }
					if(Med[0].terms_year==""){//判断当前年份办理的数量为空
						$("#used").val("0");
					}else if(Med[0].terms_year!=""){//判断当前年份办理的数量不为空
						$("#used").val(Med[0].terms_year);
					}
					 if(Med[0].medical_Normal==""){//判断当前年份办理普科的数量为空
						$("#Normal").val("0");
					 }else if(Med[0].medical_Normal!=""){//判断当前年份办理普科的数量不为空
						$("#Normal").val(Med[0].medical_Normal); 
					 }
					  if(Med[0].medical_Special==""){//判断当前年份办理专科的数量为空
						$("#Special").val("0");
					 }else if(Med[0].medical_Special!=""){//判断当前年份办理专科的数量不为空
						$("#Special").val(Med[0].medical_Special); 
					 }
					    $("#StaffNoDate").val($("#StaffNo").val().toLocaleUpperCase()+CurentTime());
						$("#MonenyMonth").val(Med[0].medical_month);
					 if(Med[0].staff_CodeDate==$("#StaffNoDate").val()){
						 $("#sameDay").val("Y");
					 }
					 if(parseFloat(Med[0].terms_year)==25 || (parseFloat(Med[0].medical_Normal)+parseFloat(Med[0].medical_Special))>=25){//当Normal和Special都超出限额
						error("該員工在本年度的報銷限額已滿！");//为了防止系统计算异常，故增加一层判断
						$("#save").attr("disabled","disabled");
						return false;
					}
					if(parseFloat(Med[0].medical_Normal)>=15){//当Normal>=15时
						error("該員工在本年度Normal報銷限額已滿！");
						$("#special").attr("checked","checked").val("S");
					}else if(parseFloat(Med[0].medical_Normal)<15){//当Normal<15时
						$("#special").removeAttr("checked");
							 }
					if(parseFloat(Med[0].medical_Special)>=10){//当Special>=10时
						error("該員工在本年度Spercial報銷限額已滿！");
						$("#special").removeAttr("checked");
						$("#special").attr("disabled",true);
					}else if(parseFloat(Med[0].medical_Special)<10){//当Special<10时
						//$("#special").attr("checked","checked").val("S");
						$("#special").removeAttr("disabled");
						     }
				}
			 }else{
				 error("Staff Code不存在！");
				 if(ty=="staffcode")
				 	 $("#StaffNo").val("").focus();
				 else
					 $("#MedicalFee").val("").focus(); 
			 }
		 }
	  });
  }
 
 
 
  
  
  /*******************************************************************************************************/
  /******************************************MedicalFee失去焦点时*****************************************/
  $("#MedicalFee").blur(function(){
	  findByStaffcode("MedicalFee");
	  if(parseFloat($("#MedicalFee").val())<=0 || $("#MedicalFee").val().length<=0){
		  error("Medical Fee不能为空");
		   $("#MedicalFee").focus();
		  return false;
	  }
	  if(isNaN($("#MedicalFee").val())){
		   error("金額必須是數字！");
		   $("#MedicalFee").val("").focus();
		  return false;
	  } if($("#MedicalFee").val().length>0 && $("#MedicalFee").val().indexOf('.')==($("#MedicalFee").val().length-1)){
		   $("#MedicalFee").val($("#MedicalFee").val()+"00");  
	  }
		moeny();
  });
  /******************************************************************************************************/
  /*********************************************special框发生改变时**************************************/
  $("#special").change(function(){
		 moeny(); 
		 
	 if( $("#special").attr("checked")==false){
		 if(parseFloat($("#Normal").val())>=15){//当Normal>=15时
				error("該員工在本年度Normal報銷限額已滿！");
				$("#special").attr("checked","checked").val("S");
				return false;
		}else if(parseFloat($("#Normal").val())<15){//当Normal<15时
		
				$("#special").attr("checked","");
						 }
	 }else{
	 	if(parseFloat($("#Special").val())>=10){//当Special>=10时
				error("該員工在本年度Spercial報銷限額已滿！");
				$("#special").removeAttr("checked").attr("disabled",true);
				return false;
		}else if(parseFloat($("#Special").val())<10){//当Special<10时
				$("#special").removeAttr("disabled").attr("checked","checked");
					     }
	 	}
	 	if(parseFloat($("#Normal").val())>=15 && parseFloat($("#Special").val())>=10){//当Normal和Special都超出限额
				error("該員工在本年度的報銷限額已滿！");
				$("#save").attr("disabled","disabled");
				return false;
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
	  if($("#MedicalFee").val()=="" || isNaN($("#MedicalFee").val())){  //这里不给于无数据提示是因为避免计算MedicalFee出现空指针
		//  alert("MedicalFee 数据非法!");
		  return ;
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
	   	  
  }/****************************************************************************************************/

  /**************************************保存事件*********************************************************/
  
  $("#save").click(function(){
	   findByStaffcode("MedicalFee");
	  /******************************************判断是否允许提交****************************************/
	 if($("#StaffNo").val()==""){
		 error("Staff Code不能为空！");
		 $("#StaffNo").focus();
		 return false;
	 }
	 if($("#MedicalFee").val()==""){
		 error("Medical Fee不能为空！");
		 $("#MedicalFee").focus();
		 return false;
	 }
	 if($("#MedicalDate").val()==""){
		 error("Medical Date不能为空！");
		 $("#MedicalDate").focus();
		 return false;
	 }if($("#MedicalDate").val()!=""){
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
			   async:false,
			  success:function(date){
			  var num=eval(date);
			  if(parseFloat(num)>=1){
					$("#MedicalDate").val("").html("");
				   if($("#special").attr("checked")==false){
					error("该员工在当天已有Normal报销记录！");
					return false;
				}if($("#special").attr("checked")==true){
					error("该员工在当天已有Special报销记录");
					return false;
					}
				  }
		  if(parseFloat(num)<=0){
			  	 $("#save").attr("disabled","disabled");
							  	 /*************************************************************************************************/
				 $.ajax({
					 type:"post",
					 url:"AddMedicalServlet",
					 data:{'StaffNo':$("#StaffNo").val(),'special':$("#special").val(),
					 'MedicalFee':$("#MedicalFee").val(),'MedicalDate':$("#MedicalDate").val(),
					 'EnglishName':$("#EnglishName").val(),'HalfConsultant':$("#HalfConsultant").val(),
					 'Amount':$("#Amount").val(),'StaffNoDate':$("#StaffNoDate").val(),
					 'used':$("#used").val(),'MonenyMonth':$("#MonenyMonth").val(),
					 'sameDay':$("#sameDay").val(),'AD':$("#AD").val(),'MedicalNormal':$("#Normal").val(),'MedicalSpecial':$("#Special").val()},
					 success:function(date){
						 var num=eval(date);
						 if(parseFloat(num)>0){ 
							 success("添加成功");
							 location.reload(); 
						 }else{
							 error("添加失败！");
						 }
					 }
				 });
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
  /**************************************************************************************************/
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
        return(clock); 
    } 
  /***************************************************************************************************/
 
  /*******************************************************************************************************/
  /***********************************************保留兩位小數********************************************/
  function changeTwoDecimal_f(x)  
{  
var f_x = parseFloat(x);  
if (isNaN(f_x))  
{  
error('function:changeTwoDecimal->parameter error');  
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
  });
function rejected(){
	if($("#subject").val()==""){
		error("请选择Subject!");
		$("#subject").focus();
		return false;
	}else if($("#remarks").val()==""){
			error("请输入拒绝原因!");
			$("#reject_information").focus();
			return false;
	}else if($("#StaffNo").val()==""){
		error("StaffNo 不能为空!");
		$("#StaffNo").focus();
		return false;
	}
	else if($("#MedicalFee").val()==""){
		error("MedicalFee不能为空!");
		$("#MedicalFee").focus();
		return false;
	} else{
 
	
	$.ajax({
		url:"QueryEmailByConsultant",
		type:"post",
		async:false,
		data:{"staffcode":$("#StaffNo").val()},
		success:function(date){
		var em=eval(date);
			if(em!="" && em!=null){
				Consultantemail=em;
			}else{
				error("该Consultant没有Email，请及时更新ConsultantList");
				return false;
			}
		},error:function(){
			error("网络连接失败，请稍后重试...");
			return false;
		}
	});
 
	var head="Dear "+$("#EnglishName").val()+",%0b%0b"
			+$("#subject").val()+",details as follows:%0b%0b"
			+"Staff Code: "+$("#StaffNo").val()+"%0b"
			+"Consulting Fee: "+$("#MedicalFee").val()+"%0b"
			+"Consulting Date: "+$("#MedicalDate").val()+"%0b";
			if($("#subject").get(0).selectedIndex==1)
			head+="Reject Reason:%0b"
			else
				head+="Pending Reason:%0b";
			var qianming="%0b%0bRegards%0b%0bAdmin Team, Shared Service Centre%0bConvoy Financial Services Limited%0b(E)SZOAdm@convoy.com.hk%0b39/F,@CONVOY,169 Electric Road,North Point,Hong Kong.%0b%0bVisit us at www.convoyfinancial.com";
			var cc="?cc=adminfo@convoy.com.hk";//;roy.lai@convoy.com.hk 2014-04-24 King 注释 Macy.chong@convoy.com.hk;2018-10-29 注释 Peter.KP.Chan@convoy.com.hk  注释 Jo.wong@convoy.com.hk;2019-01-10 orlando
					window.open("mailto:"+$.trim(Consultantemail)+cc+"&subject="+($("#subject").val()+"-"+$("#StaffNo").val())+"&body="+head+$("#remarks").val()+"%0b"+reject_info+qianming);
				$("#save").attr("disabled","disabled");
	/**var recontext="";
	recontext+="Staff Code: "+$("#StaffNo").val()+"%0d";
	recontext+="Consulting date: "+$("#MedicalDate").val()+"%0d";
	recontext+="Consulting Fee:"+$("#MedicalFee").val()+"%0d";
	recontext+="Reject Reason:%0d";
	recontext+="1. Receipts must be dropped into the drop-in box within 3 months from the consulting date.%0d";
	recontext+="2. Without the Registration No. on the receipt which is provided by the Chinese Doctor.%0d";
	recontext+="3. More than 1 medical claim at the same day unless the doctor refer to see the specialist.%0d";
	recontext+="4. Exceed year quota for medical claim. (15 times for normal Visit and 10 times for special Visit)%0d";
	recontext+="5. No medical claim quota for consultant Trainee.%0d";
	recontext+="6. The consulting date is during your training period.%0d";
	recontext+="7. Your plan of medical claim is not included Dental.%0d";
	recontext+="8. X-Ray, Lab-Test, physiotherapy treatment and sonic sound claims only can be considered as General visit items.%0d";
	recontext+="9. To claim as specialist items, referral letter and receipt must be logged in with specialist stated on the receipt.%0d";
	recontext+="%0d";
	recontext+="If you have inquiries please email to SZOAdm@convoy.com.hk%0d";
 	window.open("mailto:"+$.trim(Consultantemail)+"?subject="+$("#subject").val()+"&body="+recontext); 
 	**/}
			}
 function getReject_information(model,type,mothedName){
	 $.ajax({
		url:"Reject_informationServlet",
		type:"post",
		async:false,
		data:{"model":model,"type":type,"mothedName":mothedName},
		success:function(dates){
			var dataRole=eval(dates);
			var html;
			if(dataRole.length>0){
			html+="<option value=''>Select Location...</option>"
				for(var i=0;i<dataRole.length;i++){
					if(dataRole[i].id!=0)
					html+="<option value='"+dataRole[i].resultName+"' >"+dataRole[i].id+". "+dataRole[i].resultName+"</option>";
				else
				reject_info=dataRole[i].resultName;	
				}
			}else{
					html+="<option value=''>加载异常</option>";
			}
			$("#reject_information").empty().append(html);
	},error:function(){
		
	}
	 });
 }
  
  </script>
<body>
<div class="e-container">
	<div class="info-form">
		<h4>Medical Claim</h4>
		<table>
			<tr>
				<td class="tagName">Staff Code:</td>
				<td class="tagCont">
					<input id="StaffNo" type="text" class="txts"/>
					<span id="Staff"></span>
				</td>
				<td class="tagName">Special:</td>
				<td class="tagCont">
					<input id="special" type="checkbox" value="N">
				</td>
			</tr>
			<tr>
				<td class="tagName">Medical Date:</td>
				<td class="tagCont">
					<input   type="text" class="txts" id="MedicalDate" onClick="javascript:$('#MedicalFee,#MedicalDate').val('');return Calendar('MedicalDate');" />
				</td>
				<td class="tagName">Medical Fee:</td>
				<td class="tagCont">
					<input name="text3" type="text" id="MedicalFee" class="txts" onKeyPress="return check2(this);" onKeyUp="check2(this);"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">EnglishName:</td>
				<td class="tagCont">
					<input type="text" name="Input" id="EnglishName"   disabled="disabled" class="txt"/>
				</td>
				<td class="tagName">Half_Consultant:</td>
				<td class="tagCont">
					<input type="text" name="text" type="text" disabled="disabled" class="txt" id="HalfConsultant"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Amount Entitle:</td>
				<td class="tagCont">
					<input name="text4" type="text" disabled="disabled" id="Amount"  class="txt"/>
				</td>
				<td class="tagName">Claim Month:</td>
				<td class="tagCont">
					<input name="text6" type="text" disabled="disabled" id="MonenyMonth"  class="txt"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form">
		<h4>This year's use of record</h4>
		<table>
			<tr>
				<td class="tagName">Total claims this year:</td>
				<td class="tagCont" colspan="3">
					<input name="text5" type="text" disabled="disabled" id="used"  class="txt"/>
					<input name="text9" type="text" disabled="disabled" style="display:none" id="StaffNoDate"  class="txt"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Number of Normal: </td>
				<td class="tagCont">
					<input name="text52" type="text" disabled="disabled" id="Normal"  class="txt"/>
				</td>
				<td class="tagName">Number of Special:</td>
				<td class="tagCont">
					<input name="text522" type="text" disabled="disabled" id="Special"  class="txt"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">State:</td>
				<td class="tagCont">
					<input id="sameDay" type="hidden" value="N">
					<span id="resignDate"></span><input id="AD" type="hidden" >
				</td>
				<td class="tagName">Subject:</td>
				<td class="tagCont">
					<select id="subject">
						<option value="">请选择</option>
						<option value="Your medical claim was rejected">Your medical claim was rejected</option>
						<option value="Your medical claim was pending">Your medical claim was pending</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tagName" style="vertical-align: top; padding-top: 10px;">Remark:</td>
				<td class="tagCont" colspan="3">
					<select id="reject_information" style="width: 60%;margin-bottom: 5px;"><option value="">请选择....</option></select><br />
					<textarea id="remarks"  name ="remark" rows="5" cols="88" style="width: 70%;" ></textarea>
				</td>
			</tr>
			<tr>
				<td  class="tagName">温馨提示：</td>
				<td class="tagCont" colspan="3">
					1.每年普科医疗报销次数不能超过15次.<br />
 						2.每年专科医疗报销次数不能超过10次.
				</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
	<c:if test="${roleObj.audit==1}">
		<a class="btn" onclick="rejected();">
			Rejected
		</a>
	</c:if>
	<c:if test="${roleObj.add==1}">
		<a class="btn" id="save">
			Save
		</a>
	</c:if>
	</div>
</div>
</body>
</html>
