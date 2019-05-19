<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title> addMedical Claim</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">

	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./css/ajaxfileupload.js"></script>  
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="./css/styles.css">
    <style type="text/css">
<!--
#add {
	position:absolute;
	z-index:-1;
	width: 100%;
	height:100%;
	left: 4px;
	display:none;
	
	
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
-->
    </style>
</head>
  <script type="text/javascript">
   var fileType="";
   var fileName="";
       var rightFileType= new Array("jpg", "bmp", "gif", "png","jpeg");  
           function fileupload(){  
           
               if($("#filePath").val()==""){  
                   return false;  
               }  
               $.ajaxFileUpload({  
                       url:'FileUploadServlet', 
                       secureuri:false,  
                       fileElementId:'filePath',  
                       dataType: 'text/xml',             
                       success: function (data) { 
            	  fileName=data;
             
                       fileType=fileName.substring((fileName.lastIndexOf(".")+1)); 
                       $("#add").show();
                         $("#images").hide().attr("src","");
                   $("#downs").attr("href","upload//"+data).empty().append("无法预览非图片格式文件，如需下载请点此!");
                    $.each(rightFileType,function(n,value){//遍历Array数组，n为数组下标 ，value为数组下标对应的值
                    	if(value==fileType){
                    		   $("#downs").attr("href","").empty().append("");
                    		 $("#images").show().attr("src","upload//"+fileName);
                    		   return;
                    	}
                    });
                   
                       },error: function (data, status, e){  
                           alert("fail");  
                       }  
                   }  
               );  
           }  
        
  
  

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
	  $("#MedicalFee").bind("focus",function(){
		  changeDates();
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
				}else if(parseInt(Med[0].medical_Normal)<15){//当Normal<15时
						$("#special").removeAttr("checked");
						 }
				if(parseInt(Med[0].medical_special)>=10){//当Special>=10时
						alert("該員工在本年度Spercial報銷限額已滿！");
						$("#special").removeAttr("checked");
						$("#special").attr("disabled",true);
				}else if(parseInt(Med[0].medical_special)<10){//当Special<10时
						$("#special").attr("checked","checked");
						$("#special").removeAttr("disabled");
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
	  $("#save").attr("disabled","disabled");
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
		 changeDates();
		  
		var resignDate=changeDate(dates)
		 if(resignDate!=""){
			 
		 if(resignDate < $("#MedicalDate").val()){
			  $("#messages").show().append("警告！該職工在之前已經離職！");
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
				 url:"AddConsultantMedicalServlet",
				 data:{'StaffNo':$("#StaffNo").val(),'special':$("#special").val(),
				 'MedicalFee':$("#MedicalFee").val(),'MedicalDate':$("#MedicalDate").val(),
				 'EnglishName':$("#EnglishName").val(),'HalfConsultant':$("#HalfConsultant").val(),
				 'Amount':$("#Amount").val(),'StaffNoDate':$("#StaffNoDate").val(),
				 'used':$("#used").val(),'MonenyMonth':$("#MonenyMonth").val(),
				 'sameDay':$("#sameDay").val(),'AD':$("#AD").val(),'MedicalNormal':$("#Normal").val(),'MedicalSpecial':$("#Special").val(),'photo':fileName,'addName':"userName"},
				 success:function(date){
					 var num=eval(date);
					 if(parseFloat(num)>0){ 
						 	  $("#save").attr("disabled","");
						 alert("添加成功");
				location.reload(); 
					 }else{
						 alert("添加失败！");
					 }
					 
				 }
			 }) ;
	 
			
				  }
			  }
		  });

/**************************************************************************************************/


  });
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
  });
  </script>
  <body>
   <center>
    <div id="upload">
       <input type="file" name="filePath" id="filePath"/>  
       <input type="button" name="fileLoad" id="fileLoad" value="上  傳" onClick="fileupload()"/>  
       <br/>
       </div>
       </center><center>
  <div id="add" align="center">
  
 <img alt="" width="600" height="400" id="images"  style="display: none;">
       <a id="downs" ></a>
  

 
  </div>
  
 
  </center>
  </body>
</html>
