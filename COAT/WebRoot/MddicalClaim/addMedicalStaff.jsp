<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Medical Claim for Staff </title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script src="css/date.js" type="text/javascript"></script>
	<script src="css/Util.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="css/style.css">
    <style type="text/css">
<!--
body{
overflow: hidden;
}
#add {
	position:absolute;
	z-index:-1;
	width: 100%;
	height: 100%;
	left: 4px;
	top: 10px;
}
 .txt{
    color:#0000FF;
    border:0px;
   border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
 background-color:transparent; /* 顏色透明*/
	size:40px;
	font-style:oblique;
	font-family: '仿宋';
	font-size: 18px;

    }
 .txt_staff{
   color:#005aa7;
    border:0px;
     border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:40px;
	font-family: '仿宋';
	font-size: 16px;
    }
    
 .txt_readonly{
    color:#0000FF;
    border:0px;
   border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
 	background-color:transparent; /* 顏色透明*/
	size:40px;
	font-style:oblique;
	font-family: '仿宋';
	font-size: 16px;


    }
.txts{
    color:#005aa7;
    border:0px;
     border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:40px;
	font-family: '仿宋';
	font-size: 16px;

    }
    
    -->
</style>
<script type="text/javascript" >
var staffType=null;
var staffmail=null;
var SameDay="N";

/**
 * 只读文本框禁用退格键
 * 
 * */
function BackSpace(e){
	if(window.event.keyCode==8)//屏蔽退格键
 {
    var type=window.event.srcElement.type;//获取触发事件的对象类型
  //var tagName=window.event.srcElement.tagName;
  var reflag=window.event.srcElement.readOnly;//获取触发事件的对象是否只读
  var disflag=window.event.srcElement.disabled;//获取触发事件的对象是否可用
	  if(type=="text"||type=="textarea")//触发该事件的对象是文本框或者文本域
	  {
		   if(reflag||disflag)//只读或者不可用
		   {
		    //window.event.stopPropagation();
		    window.event.returnValue=false;//阻止浏览器默认动作的执行
		   }
	  }
	  else{ 
	   window.event.returnValue=false;//阻止浏览器默认动作的执行
	  }
 }
}
$(function(){
	document.onkeydown=BackSpace;
$(".txt_readonly").attr("readonly","readonly");
//$("#staffcode").focus();
	  getReject_information("Medical","Staff","query");
	    $("#reject_information").change(function(){
	var vals=$("#reject_information").find("option:selected").val();
			var rep=$("#remarks").val();
	if($("#remarks").html().indexOf(vals)<0){
			var num=(rep.split("%0b").length-1);
 if(rep==""){
	 rep="1.";
 }else{
	 rep+=(num+1)+".";
 }
		$("#remarks").val(rep+(vals+"\r\n"));
	 
	}else{
 
		//$("#remarks").val(rep.replace((vals),''));
	}
});
/**
 * 报销金额框离开事件 
 * 
 * */
  $("#MedicalFee").blur(function(){
	  if($("#MedicalDate").val()!=""){
	  if($("#Medical_type").find("option:selected").val()==""){
		  $("#MedicalFee").val("");
		  alert("请先选择类型!");
		 
		  return false;
		  }
	  if(parseFloat($("#MedicalFee").val())<=0 || $("#MedicalFee").val().length<=0){
		  alert("Medical Fee 不能等于0或为空!");
		   $("#MedicalFee").focus();
		  return false;
	  }
	  if(isNaN($("#MedicalFee").val())){
		   alert("金額必須是數字！");
		   $("#MedicalFee").val("").focus();
		  return false;
	  } if($("#MedicalFee").val().length>0 && $("#MedicalFee").val().indexOf('.')==($("#MedicalFee").val().length-1)){
		   $("#MedicalFee").val($("#MedicalFee").val()+"00");  
	  }
	
		moeny();
		}
  });
/**
 * 
 * 报销金额框光标聚焦时间
 * */

$("#MedicalFee").focus(function(){
	 checkymd_all($("#MedicalDate"));
	 if("${systemTime}"==""){
		 alert("获取服务端数据出错，请重新登录!");
		 	top.location.href='../signin.jsp';
		 return false;
	 }
	 if($("#MedicalDate").val()>"${systemTime}"){//报销单日期大于当前
			 alert("该员工报销单日期不能大于当天");
			 $("#MedicalDate").focus();
			 return false;
	 }else {
		     var dateStart=new Date($("#MedicalDate").val().replace(/-/,"/"));
			 var dateEnd=new Date("${systemTime}".replace(/-/,"/")); 
			 var y_gap=dateEnd.getYear()-dateStart.getYear();
			 var m_gap=0;
			if(y_gap>0){
				 m_gap=dateEnd.getMonth()-dateStart.getMonth()+(12*y_gap);
			}else{
			 	 m_gap=dateEnd.getMonth()-dateStart.getMonth();
			}
				 var d_gap=dateEnd.getDate()-dateStart.getDate();
			if (y_gap>=0 && ((m_gap==3&&d_gap>15)||m_gap>3)) {  
				 alert("该报销单日期不在三个半月有效期内!");
				  //Amy需求 修改
		 		 //$("#MedicalDate").focus();
				  $("#save").attr("disabled","disabled");
				 return false;
			} 
	 }
 	
	 if(($("#enrollmentDate").val()!="" && compareDate($("#enrollmentDate").val(),$("#MedicalDate").val())==1) ||($("#terminationDate").val()!="" && compareDate($("#terminationDate").val(),$("#MedicalDate").val())==3)){
				 // if($("#MedicalDate").val()<$("#HalfConsultant").val()){
					  alert("该员工的报销单不在许可范围内！");
					   //Amy需求 修改
		  			//$("#MedicalDate").val("").focus();
					   $("#save").attr("disabled","disabled");
					  return false;
				  // }
			 }
	if($("#Medical_type").find("option:selected").val()==""){
		$("#Medical_type").focus();return false;
		}
	getNormal();
});
/**
 * 根据staffcode MedicalDate所对应的 MedicalDate的年份的限额信息
 * 调用处 MedicalFee blur事件之后
 * */
function getNormal(){
	  
	  $.ajax({
		 url:"SelectStaffMedicalServlet",
		 type:"post",
		 async:false,
		 data:{"staffcode":$("#staffcode").val(),"MedicalDate":$("#MedicalDate").val()},
		 success:function(date){
			 var dataRole=eval(date);
			 if(dataRole==null || dataRole==""){
				 $("#Normal_Number,#Special_Number").val(0);
			} else{
				// alert(dataRole[0].packages.substring(dataRole[0].packages.lastIndexOf('-')+1,dataRole[0].packages.length));
				//  for(var i=0;i<dataRole.length;i++){
				 //  var typs=dataRole[0].packages;
				   var NormalNumber=parseFloat(dataRole[0].total_Normal);
				   var SpecialNumber=parseFloat(dataRole[0].total_Special);
				   var RegularNumber=parseFloat(dataRole[0].total_Regular);
					// alert(dataRole[i].packages.substring(dataRole[i].packages.lastindexOf('-')));
					// if(typs.substring(typs.lastIndexOf('-')+1,typs.length)=="GP"){//判断选择的报销类型是否是普科
						 $("#Normal_Number").val(NormalNumber==0?0:NormalNumber);
					// }
					// else if(typs.substring(typs.lastIndexOf('-')+1,typs.length)=="SP"){//判断选择的报销类型是否是专科
						 $("#Special_Number").val(SpecialNumber==0?0:dataRole[0].total_Special);
					// }
					// else if(typs.substring(typs.lastIndexOf('-')+1,typs.length)=="Dental"){//判断选择的报销类型是否是牙科
						 $("#Dental_Number").val(dataRole[0].total_amount);
					// }
					// else if(typs.substring(typs.lastIndexOf('-')+1,typs.length)=="Regular"){//判断选择的报销类型是否是口腔科
						 $("#Oral_Number").val(RegularNumber==""?0:RegularNumber);
					// }
				// } 
			 }
	
		 },error:function(){
			 alert("网络连接失败，请稍后重试...");
			 return false;
		 }
	  });
	  

	  
}
 
$("#entitle").blur(function(){
	checkMoney();
});

});
/**
 * 根据MedicalFee计算Amount Entitle
 *  
 * 调用处  MedicalFee blur事件
 * */
 
  function moeny(){
	   //Amy需求 修改
		  //if($("#MedicalFee").val()=="" || isNaN($("#MedicalFee").val())){  //这里不给于无数据提示是因为避免计算MedicalFee出现空指针
		  // return ;
	  // }
	  	var num=$("#Medical_type").find("option:selected").val();
	  	var moneys=parseFloat($("#MedicalFee").val())*(staffType[num].per);
	  	var entitle=parseFloat(staffType[num].money);
	  	$("#entitle").val(moneys>entitle?entitle:moneys).blur();
	  	
	  			 	  $.ajax({
		  url:"VailSameDayServlet",
		  type:"post",
		  async:false,
		  data:{"staffcode":$("#staffcode").val(),"type":$("#Medical_type").find("option:selected").text(),"medicalDate":$("#MedicalDate").val()},
		  success:function(date){
			 var MedicalTypes=eval(date);
			 
			 var typess=$("#Medical_type").find("option:selected").text().substring($("#Medical_type").find("option:selected").text().lastIndexOf('-')+1,$("#Medical_type").find("option:selected").text().length);
			
		
			 if(MedicalTypes!=""||MedicalTypes!=null){
				 for(var i=0;i<MedicalTypes.length;i++){
					 if(MedicalTypes[i]==typess){
						 alert("在"+$("#MedicalDate").val()+"这天已经有过"+MedicalTypes[i]+"的报销记录");
						$("#save").attr("disabled","disabled");
						 SameDay="Y";
						 return false;
					 }else{
						 $("#save").attr("disabled","");
					 }
				 }
			 }else{
				 SameDay="N";
			 }
		  },error:function(){
			  alert("网络连接失败，请稍后重试....");
			  return false;
		  }
		  
	  });
	  	
  } 

/***
 * 根据staffcode查询staff的基本信息
 * 
 * 调用处staffcode blur事件
 * */


function selectStaff(){
if($("#staffcode").val()==""){
	alert("staffcode 不能为空!");
	return false;
}
	$.ajax({
		url:"SelectStaffMedicalServlet",
		type:"post",
		async:false,
		data:{"staffcode":$("#staffcode").val()},
		success:function(date){
			if(date!=="[null]" && date!==""){
			var dataRole=eval(date);
			$("#staffname").val(dataRole[0].staffname);
			$("#company").val(dataRole[0].company);
			$("#deptid").val(dataRole[0].deptid);
			$("#grade").val(dataRole[0].grade);
			$("#pplan").val(dataRole[0].pplan);
			$("#enrollmentDate").val(dataRole[0].enrollmentDate);
			$("#terminationDate").val(dataRole[0].terminationDate);
			staffmail=dataRole[0].email;
			selectType();
			}else{
				alert("staffcode 不存在!");
				$("#staffcode").val("");
				return false;
			}
		},error:function(){
			alert("网络连接失败，请稍后重试...");
			return false;
		}
	});

}
  /**
   * 及时清除缓存信息  例如 当改变staffcode时首先清除其他信息然后再次添加相应的信息
   * 
   * 调用处staffcode 的blur事件
   * */
function clear_txt(){
	$(".txt,.txts,.txt_readonly,.txt_readonly,#remarks,#subject,#reject_information").val("");
 	$("#Oral_Number,#Normal_Number,#Special_Number,#Dental_Number").val(0); 
}

/**
 * 根据staff的信息查询可供选择的报销类型
 * 
 * 调用处通过staffcode blur事件之后
 * */

function selectType(){
	$.ajax({
	url:"SelectStaffMedicalServlet",
	type:"post",
	async:false,
	data:{"type":$("#pplan").val()},
	success:function(date){
		if(date!==null || d!==""){
			var dataRole=eval(date);
			var html="";
			staffType=dataRole;
			$("#Medical_type option:gt(0)").remove();
		for(var i=0;i<dataRole.length;i++){
			html+="<option value='"+i+"'>"+dataRole[i].type+"</option>";
		}
			$("#Medical_type").append(html);
		}
	},error:function(){
		alert("网络连接失败，请稍后重试...");
		return false;
	}
	});

}

/**
 * 根据类型获取对应的次数，金额的上限
 *   
 *   调用处type的change事件
 */

function changeType(){
	$("#package").val($("#Medical_type").find("option:selected").text());
	var num=$("#Medical_type").find("option:selected").val();
	$("#max_amount").val(staffType[num].number);
	$("#max_amount_money").val(staffType[num].money);
	$("#Normal,#Special").val("");
	if($("#MedicalFee").val()!=""){
		moeny();
	}
}

 function checkMoney(){
	 var nums=$("#Medical_type").find("option:selected").val();
	 var typs=staffType[nums].type;
	  if(typs.substring(typs.lastIndexOf('-')+1,typs.length)=="GP"){
			 if(parseFloat($("#Normal_Number").val())+1>parseFloat($("#max_amount").val())){
								 alert(typs+"-报销次数已满!");
								 $("#Medical_type").focus();
								  //Amy需求 修改
		 						 //$("#MedicalFee").val("");
								  $("#save").attr("disabled","disabled");
								 return false;
			 }
		 }
	  else if(typs.substring(typs.lastIndexOf('-')+1,typs.length)=="SP"){
			 if(parseFloat($("#Special_Number").val())+1>parseFloat($("#max_amount").val())){
								 alert(typs+"-报销次数已满!");
								 $("#Medical_type").focus();
								  //Amy需求 修改
		 						 //$("#MedicalFee").val("");
								  $("#save").attr("disabled","disabled");
								 return false;
			 }
		 }
	  else if(typs.substring(typs.lastIndexOf('-')+1,typs.length)=="Regular"){
			 if(parseFloat($("#Oral_Number").val())+1>parseFloat($("#max_amount").val())){
								 alert(typs+"-报销次数已满!");
								 $("#Medical_type").focus();
								 //Amy需求 修改
		 						 //$("#MedicalFee").val("");
								 $("#save").attr("disabled","disabled");
								 return false;
			 }
		 }
	  else if(typs.substring(typs.lastIndexOf('-')+1,typs.length)=="Dental"){
		  var numbers=(parseFloat($("#max_amount_money").val())-parseFloat($("#Dental_Number").val()))/staffType[nums].per;
			  if(parseFloat($("#Dental_Number").val())+parseFloat($("#entitle").val())>parseFloat($("#max_amount_money").val())){
					if(numbers<=0){
							 alert(typs+"-报销金额不足0元!");
							  //Amy需求 修改
		  						//$("#MedicalFee").val("");
							 $("#Medical_type").focus();
							 return false;
					}else  if(confirm(typs+"-可报销金额不足,还可报销"+numbers*staffType[nums].per+" 是否确定?")){
							 $("#MedicalFee").val(numbers).blur();
					}else{
							 $("#Medical_type").focus();
							 return false;
					 }
			 }
		  }
	  $("#save").attr("disabled","");
} 

/**
 * 金额小数点控制
 * @param {Object} obj
 * 调用处 MedicalFee的按键操作
 */
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
		}else{
			 obj.value = v.replace(/[^\d\.]/g,'');
		}
	}else{
			 obj.value = v.replace(/[^\d\.]/g,'');
	}
}

}
/**
 * 选择是否返回报销凭证原件
 * 
 * 调用处Return_orginal选中或取消事件
 */
function choose(){
	if($("#return_oraginal").attr("checked"))
		$("#return_oraginal").val("Y");
	else
		$("#return_oraginal").val("Y");
}
/**
 * 保存Medical Claim for Staff 数据
 * 
 * save 按钮的单击事件
 */
function save(){
	if(checkSave()){
	$.ajax({
		url:"SaveStaffMedicalServlet",
		type:"post",
		async:false,
		data:{
				"staffcode":$("#staffcode").val(),
				"type":$("#Medical_type").find("option:selected").text(),
				"MedicalDate":$("#MedicalDate").val(),
				"MedicalFee":$("#MedicalFee").val(),
				"staffname":$("#staffname").val(),
				"Company":$("#company").val(),
				"Department":$("#deptid").val(),
				"grade":$("#grade").val(),
				"entitle":$("#entitle").val(),
				"return_oraginal":$("#return_oraginal").val(),
				"pplan":$("#pplan").val(),
				"package":$("#package").val(),
				"max_amount":$("#max_amount").val(),
				"max_amount_money":$("#max_amount_money").val(),
				"Normal_Number":$("#Normal_Number").val(),
				"Special_Number":$("#Special_Number").val(),
				"Oral_Number":$("#Oral_Number").val(),
				"Dental_Number":$("#Dental_Number").val(),
				"email":staffmail,"SameDay":SameDay},
		success:function(date){
				alert(date);
				clear_txt();
				$("#staffcode").val("");
				},error:function(){
					alert("网络连接失败,请稍后重试...");
					return false;
				}
		});
	}
}
/**
 *保存之前的验证方法
 * @return {TypeName}
 * 
 *  调用处save方法
 */

function checkSave(){
	if($("#staffcode").val()==""){
		alert("staffcode 不能为空!");
		$("#staffcode").focus();
		return false;
	}
	else if($("#MedicalFee").val()==""){
		alert("MedicalFee不能为空!");
		$("#MedicalFee").focus();
		return false;
	}else if($("#Medical_type").val()==""){
		alert("请选择MedicalType!");
		$("#Medical_type").focus();
		return false;
	}
	else{
		return true;
	}
}
function rejected(){
	if($("#subject").val()==""){
		alert("请选择Subject!");
		return false;
	}else if($("#remarks").val()==""){
			alert("请输入拒绝原因!");
			$("#reject_information").focus();
			return false;
	}else if($("#staffcode").val()==""){
		alert("staffcode 不能为空!");
		$("#staffcode").focus();
		return false;
	}
	else if($("#MedicalFee").val()==""){
		alert("MedicalFee不能为空!");
		$("#MedicalFee").focus();
		return false;
	}else if($("#Medical_type").val()==""){
		alert("请选择MedicalType!");
		$("#Medical_type").focus();
		return false;
	}
	reject_bak();
		
	/**var recontext="";
	recontext+="Staff Code: "+$("#staffcode").val()+"%0d";
	recontext+="Consulting date: "+$("#MedicalDate").val()+"%0d";
	recontext+="Consulting Fee:"+$("#MedicalFee").val()+"%0d";
	recontext+="Reject Reason:%0d";
	recontext+="1.	Receipts must be dropped into the drop-in box within 3 months from the consulting date.%0d";
	recontext+="2.	More than 1 medical claim at the same day unless the doctor refer to see the specialist.%0d";
	recontext+="3.	Exceed year quota for medical claim. (12 times for normal Visit and 8 times for special Visit)%0d";
	recontext+="4.	Exceed year quota for medical claim. (15 times for normal Visit and 10 times for special Visit)%0d";
	recontext+="5.	Exceed year quota for medical claim. (1 times for Routine Oral Examination per year)%0d";
	recontext+="6.	Exceed year quota for medical claim. (Overall Maximum HK$5000/HK$7000 per Year)%0d";
	recontext+="7.	Your plan of medical claim is not included Dental.%0d";
	recontext+="8.	To claim as specialist items, referral letter and receipt must be logged in with specialist stated on the receipt.%0d";
	recontext+="%0d";
	recontext+="If you have inquiries please email to SZOAdm@convoy.com.hk%0d";
	recontext+="%0d";
 	window.open("mailto:"+$.trim(staffmail)+"?subject="+$("#subject").val()+"&body="+recontext); 
			**/}
function reject_bak(){
		$.ajax({
		url:"SaveRejectBakServlet",
		type:"post",
		data:{
				"staffcode":$("#staffcode").val(),
				"type":$("#Medical_type").find("option:selected").text(),
				"MedicalDate":$("#MedicalDate").val(),
				"MedicalFee":$("#MedicalFee").val(),
				"staffname":$("#staffname").val(),
				"Company":$("#company").val(),
				"Department":$("#deptid").val(),
				"grade":$("#grade").val(),
				"entitle":$("#entitle").val(),
				"return_oraginal":"Y",
				"pplan":$("#pplan").val(),
				"package":$("#package").val(),
				"max_amount":$("#max_amount").val(),
				"max_amount_money":$("#max_amount_money").val(),
				"Normal_Number":$("#Normal_Number").val(),
				"Special_Number":$("#Special_Number").val(),
				"Oral_Number":$("#Oral_Number").val(),
				"Dental_Number":$("#Dental_Number").val(),
				"email":staffmail,"SameDay":SameDay,"subject":$("#subject").val(),"remark":$("#remarks").val()},
		success:function(date){
				if(date=="success"){
					var head="Dear "+$("#staffname").val()+",%0b%0b"
			+$("#subject").val()+", details as follows:%0b%0b"
			+"Staff Code: "+$("#staffcode").val()+"%0b"
			+"Consulting Fee: "+$("#MedicalFee").val()+"%0b"
			+"Consulting Date: "+$("#MedicalDate").val()+"%0b"
				if($("#subject").get(0).selectedIndex==1)
			head+="Reject Reason:%0b"
			else
				head+="Pending Reason:%0b";
			
			var qianming="%0b%0bRegards%0b%0bSZOADM%0bAdmin Team, Shared Service Centre%0bConvoy Financial Services Limited%0b(D)(852)3601 3856%0b(E)SZOAdm@convoy.com.hk%0b39/F,@CONVOY,169 Electric Road, North Point, Hong Kong.%0b%0bVisit us at www.convoyfinancial.com";
					window.open("mailto:"+$.trim(staffmail)+"?subject="+$("#subject").val()+"&body="+head+$("#remarks").val()+"%0d"+reject_info+qianming);
				$("#save").attr("disabled","disabled");
				}else if(date=="error"){
					alert("数据备份失败，请稍后重试...");
				}else{
					alert("用户信息丢失，请重新登录!");
					top.location.href='../signin.jsp';
					return false;
				}
		},error:function(){
			alert("网络连接失败,请稍后重试....");
			return false;
			}
		});
	
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
  </head>
  
  <body>
  <div id="add">
  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="table-ss">
  <tr>
  <td height="5%" colspan="8" align="center"><strong><font color="blue" size="+4">Medical Claim for Staff </font></strong></td>
  </tr>
  
  <tr height="5%" ><!-- 第一行 -->
	  <td width="20%"  align="right">Staff Code:</td >
	  <td width="20%"  ><input  id="staffcode" class="txt_staff" onBlur="clear_txt();selectStaff();" type="text"/></td >
	  <td width="20%"></td>
	  <td width="20%" align="right"> Type: </td>
	  <td width="20%" align="left"><select name="Medical_type" id="Medical_type" onChange="changeType();">
	    <option value="">请选择</option>
      </select></td>
  </tr>
  
  <tr height="5%" ><!-- 第二行 -->
	  <td width="20%" align="right">Medical Date:</td >
	  <td width="20%" ><input  id="MedicalDate" class="txts" type="text"   onClick="javascript:$('#MedicalFee,#MedicalDate').val(''); return Calendar('MedicalDate');" /></td >
	  <td width="20%"> </td>
	  <td width="20%" align="right">Medical Fee:	  </td>
	  <td width="20%" align="left"><input type="text" class="txts" id="MedicalFee" onKeyPress="return check2(this);" onKeyUp="check2(this);"/></td>
  </tr>
  
  <tr height="5%" ><!-- 第三行 -->
	  <td width="20%" align="right">Staff Name:</td >
	  <td width="20%" ><input id="staffname" class="txt_readonly" type="text"/></td >
	  <td width="20%"></td>
	  <td width="20%" align="right">Company:	  </td>
	  <td width="20%" align="left"><input id="company" class="txt_readonly" type="text"/></td>
  </tr>
  
 
  <tr height="5%" ><!-- 第四行 -->
	  <td width="20%" align="right">Department:</td >
	  <td width="20%" ><input id="deptid" class="txt_readonly" type="text"/></td >
	  <td width="20%"></td>
	  <td width="20%" align="right">Grade:	  </td>
	  <td width="20%" align="left"><input id="grade" class="txt_readonly" type="text"/></td>
  </tr>
  
  <tr height="5%" ><!-- 第五行 -->
	  <td width="20%" align="right">Amount Entitle:</td >
	  <td width="20%" ><input id="entitle" class="txt_readonly"  onBlur="return check2(this);"  type="text"/></td >
	  <td width="20%"></td>
	  <td width="20%"  align="right" >Return Oraginal:	  </td>
	  <td width="20%"  align="left" ><input  type="checkBox" id="return_oraginal" onChange="choose();" value="N"/></td>
  </tr>
  <tr height="5%" ><!-- 第五行 -->
	  <td width="20%" align="right">EnrollmentDate:</td >
	  <td width="20%" ><input id="enrollmentDate" class="txt_readonly"    type="text"/></td >
	  <td width="20%"></td>
	  <td width="20%"  align="right" >TerminationDate:	  </td>
	  <td width="20%"  align="left" ><input  type="text" id="terminationDate" class="txt_readonly"  /></td>
  </tr>
  
   <tr height="5%"><!-- 第六行 -->
    <td height="20" colspan="7" align="center"><div  class="txt"><strong id="mymessage">--------------------------------</strong></div></td>
  </tr>
  
  <tr height="5%" ><!-- 第七行 -->
	  <td width="20%" align="right">Plan:</td >
	  <td width="20%" ><input id="pplan" class="txt_readonly" type="text"/></td >
	  <td width="20%"></td>
	  <td width="20%" align="right">Package:	  </td>
	  <td width="20%" align="left"><input id="package" class="txt_readonly" type="text"/></td>
  </tr>
  
   <tr height="5%" ><!-- 第八行 -->
	  <td width="20%" align="right">Max Number:</td >
	  <td width="20%" ><input id="max_amount" class="txt_readonly" type="text"/></td >
	  <td width="20%"></td>
	  <td width="20%" align="right">Consultlting Fee:</td>
	  <td width="20%" align="left"><input id="max_amount_money" class="txt_readonly" type="text"/></td>
   </tr>
   <tr height="5%" ><!-- 第九行 -->
	  <td width="20%" align="right">Number of Normal:</td >
	  <td width="20%" ><input id="Normal_Number" class="txt_readonly" type="text" value="0"/></td >
	  <td width="20%"></td>
	  <td width="20%" align="right">Number of Special:</td>
	  <td width="20%" align="left"><input id="Special_Number" class="txt_readonly" type="text" value="0"/></td>
   </tr>
   <tr height="5%" ><!-- 第九行 -->
	  <td width="20%" align="right">Number of Regular:</td >
	  <td width="20%" ><input id="Oral_Number" class="txt_readonly" type="text" value="0"/></td >
	  <td width="20%"></td>
	  <td width="20%" align="right">Amount of Dental:</td>
	  <td width="20%" align="left"><input id="Dental_Number" class="txt_readonly" type="text" value="0"/></td>
   </tr>
  <tr height="5%" >
  <td align="right">  Subject:
  </td>
  <td ><select name="subject" id="subject">
    <option value="">请选择</option>
    <option value="Your medical claim was rejected">Your medical claim was rejected</option>
    <option value="Your medical claim was pending">Your medical claim was pending</option>
  </select></td>
  <td></td>
  </tr>
  
  <tr>
  <td align="right" height="5%">remarks:
   <br />
       </td>
  <td height="8%"   colspan="4" align="left"><select name="reject_information" id="reject_information">
    <option value="">请选择....</option>
  </select>  <br>  <textarea id="remarks" name ="remark" rows="5" cols="88" ></textarea></td>
  </tr>
 <tr>
   <td colspan="2"></td>
   <td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="reject" onClick="rejected();"/></td>
   <td colspan="2" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Save" id="save" onClick="save();"/></td>
 </tr>
 </table>
  </div>
  
  
  </body>
</html>
