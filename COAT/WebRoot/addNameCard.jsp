<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
	<head>
    <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="plug/js/Common.js"></script>
	<script language="javascript" type="text/javascript" src="css/date.js"></script>
	<script language="javascript" type="text/javascript" src="css/Util.js"></script>
	
	<title>信息錄入</title>
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
<style type="text/css">
<!--
#information {
	position:absolute;
	width:100%;
	height:100%;
	z-index:1;
	border: 1px solid #999999;
	left: 1px;
	top: 1px;
}
 .txt{
    color:#005aa7;
    border:0px;
    border-bottom:1px solid #005aa7; /* 下划线 */
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:21px;
	font-family: 'Arial Narrow';
	font-size: 16px;

    }
-->
</style>

<script type="text/javascript">
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

var DD=true;
var AECode=true;
var Elite=true;
/*窗体加载事件 */
$(function(){
	document.onkeydown=BackSpace;
function position(){
	$.ajax({
		type: "post",
		//url: "QueryPositionServlet",
		//data: null,
		url: "common/CommonReaderServlet",
		data:{"method":"getposition"},
		success: function(date){
		var d=eval(date);
		var html;
		if(d.length>0){
			for(var i=0;i<d.length;i++){
			html+="<option value='"+d[i].position_ename.replace('\r\n','')+"' >"+d[i].position_ename+"</option>";
			}
			$("#EnglishPosition:last").append(html);
			}
		}
	});
}
/**
 * Elite Team change事件
 * 
 * */
$("#ET,#num,#numId").change(function(){
	if($("#ET").attr("checked")==true){
	
			//$("#type").val("P");
		if($("#sf").attr("checked")==true){
			$("#ET").attr("checked","").val("N");
			error("No Code 不能选择Elite Team!");
		}else{
			$("#ET").val("Y");
			//$("#num").val(100);
			
		}
	
	}else{
		$("#ET").val("N");
			$("#type").val("S");
	}
});
$("#type").change(function(){
	if($("#ET").attr("checked")==true){
		//$(this).val("P");
	}else{
		/**if(DD==false){
			$(this).val("S");
		}else{
			$(this).val("P");
		}**/
	}
});
 

function professional(){
	$.ajax({
		type: "post",
		//url: "QueryProfessionalServlet",
		//data: null,
		url: "common/CommonReaderServlet",
		data:{"method":"getprofessional"},
		success: function(date){
		var d=eval(date);
		var html;
		if(d.length>0){
			for(var i=0;i<d.length;i++){
			html+="<option value='"+d[i].prof_title_ename+"'>"+d[i].prof_title_ename+"</option>";
			}
			$("#EnglishProfessionalTitle:last").append(html);
			}
		}
	});
}
/*******************************************获取随机Code**********************************************************/
	function formatTime(){
	
		var d=new Date();
		var date="NC"+""+d.getFullYear()+""+(d.getMonth()+1)+""+d.getDate()+""+d.getHours()+""+d.getMinutes()+""+d.getSeconds();
		return date;
	}
/***************************************************************************************************************/
$("#sf").change(function(){
	if($("#sf").attr("checked")==true){
		$("#sf").val("Y");
		$("#StaffNo").val(formatTime());
		$("#StaffNo").attr("readonly","readonly");
		$("#message").hide();
		$(".txt[id!='StaffNo']").val("");
	}else{
		$("#sf").val("N");
		$("#StaffNo").val("");
		$("#StaffNo").removeAttr("readonly");
	}
});
/**************************************EnglishProfession单击事件*****************************************/
$("#EnglishProfession").focus(function(){
	$("#EnglishProfessionalTitle").show();
});
/*********************************************************************************************************/
/**********************************窗體加載************************************************/
position();
professional();
getlocation();
getLayout()
$("#urgentDate").val(CurentTime());
$("#EnglishProfessionalTitle").hide();
/******************************************************************************************/
var pay=false;
/*****************************************************/
$("#message").hide();
 
$("#urgentDate").empty;								//清空日期控件里面的残余值
$("#urgentDate").hide();							//隐藏日期控件
$("#urgentCase").removeAttr("checked");	
/*****************************************************/
var professionalCText="";
var professionalEText=""
/********************************EnglishProfessionalTitle值發生改變時*******************/
$("#EnglishProfessionalTitle").change(function(){

	//alert($("#EnglishProfession").val().indexOf($(this).val())>0);
		if($("#EnglishProfession").val().indexOf($(this).val())>=0){
		return false;					
		}else{
				$.ajax({
					type:"post",
					url:"SelectProfessionalServlet",
					data:{'EnglishProfessionalTitle':$("#EnglishProfessionalTitle").val()},
					success :function(date){
						if(date!="[]" && date!="null"){//没有中文
					if($("#ChineseProfessionalTitle").val()=="null" || $("#ChineseProfessionalTitle").val()==null){//中文框为null
									$("#ChineseProfessionalTitle").val("");
								}
				 
									
											professionalEText = $("#EnglishProfession").val()+($("#EnglishProfession").val()==""?"":";")+ $("#EnglishProfessionalTitle").val();
											professionalCText = $("#ChineseProfessionalTitle").val()+($("#ChineseProfessionalTitle").val()==""?date:((date==""?"":";")+date));
																	
									$("#ChineseProfessionalTitle").val(professionalCText);
									$("#EnglishProfession").val(professionalEText).change();
								}else{
									professionalEText = $("#EnglishProfession").val()+($("#EnglishProfession").val()==""?"":";")+ $("#EnglishProfessionalTitle").val();
									$("#EnglishProfession").val(professionalEText).change();
								}
					}
				});
	}
});



function getLayout(){
	//2014-04-14 King 注释   $("#type").empty().append("<option value='S'>Standard Layout</option><option value='P'>Premium Layout</option>");
	$("#type").empty().append("<option value='S'>Standard Layout</option>");
	
	
}

function getlocation(){
		$.ajax({
			type:"post",
			//url:"QueryLocationServlet",
			//data:null,
			url: "common/CommonReaderServlet",
			data:{"method":"getlocation"},
			success:function(date){
			var location=eval(date);
			var html="";
			$("#location").empty();
			if(location.length>0){
				
				for(var i=0;i<location.length;i++){
					html+="<option value='"+location[i].realName+"' >"+location[i].name+"</option>";
				}
			}else{
				html+="<option value=''>加载异常</option>";
			}
			$("#location").append(html);
			},error:function(){
				errorAlert("网络连接失败,请返回登录页面重新登录!",function(){
				});
				
				return false;
			}
		});
	}
/**************************************************************************************/
function payers(){
		$.ajax({ 
			type: "post", 
			url: "PayerServlet", 
			data: {'payer':$("#payer").val(),"type":"position"}, 
			success: function(date){
			if(date=="" || date==null){
				error("支付人不存在,请重新录入!");
				pay=false;
				$("#payer").val("");
				$("#payer").focus();
				return false;
			}else{
				if(date=="TRUE" &&$("#sf").attr("checked")){
					error("该顾问暂时没有权限为他人办理名片!");//您暂时无权限帮他人办理名片
					$("#sf").attr("checked","");		
					$("#payer").val("");
					$("#StaffNo").val(parent.staffcode).blur();
					 pay=false;
					}else{
						pay=true;
						$.ajax({
							type: "post", 
							url: "PayerServlet", 
							data: {'payer':$("#payer").val(),"type":"name"}, 
							success: function(date){
									$("#pay").val(date);
								},error:function(){
										error("支付人信息异常!");
										$("#payer").val("");
										$("#payer").focus();
									return false;
								}
							});
					}
			}
		 }
	 });
}
/************************************验证payer是否存在*************************/
$("#payer").blur(function (){
payers();
});
/****************************************pater验证结束***********************/
$("#EnglishPosition").change(function(){
	$.ajax({
		type:"post",
		url:"SelectPositionServlet",
		data:{'EnglishName':$("#EnglishPosition").val().replace('\r\n','').replace('\r\n','').replace('\r\n','')},
		success:function (date){
			if(date!="[]" || date!="null" || date==null){
				$("#ChinesePosition").val(date);
				if($("#ChinesePosition").val()=="null" || $("#ChinesePosition").val()==null){
					$("#ChinesePosition").val("");
					}
			}
		}
	});
});

//CE NO blur事件
$("#CENO").blur(function(){
	if($("#CENO").val()=="")
		$("#C").attr("checked","").val("N");
	else
		$("#C").attr("checked","checked").val("Y");
});

$("#AE").change(function(){
 if(AECode){
	if($("#AE").attr("checked")==true){
			if($("#sf").attr("checked")==true){
			error("No Code 不能选择AE Consultant!");
				return false;
			}else{ 
					$("#AE").val("Y");
					$("#CCC").attr("checked","checked").val("Y");
			}
		}else{
			$("#AE").val("N");
			$("#CCC").attr("checked","").val("N");
		}
	}else{
			$("#AE").attr("checked","").val("N");
			$("#CCC").attr("checked","").val("N");
	}
});
/****************************************************StaffNo 失去焦点时触发的时间****************************************************/
$("#StaffNo").blur(function(){

		 $("#EnglishPosition").get(0).selectedIndex=0;
		 $("#ET,#AE,#urgentCase").attr("checked","").val("N");
		$("#EnglishProfessionalTitle").get(0).selectedIndex=0;
		$(".txt[id!='StaffNo']").val("");
		if($("#sf").attr("checked")==true){
			return ;
		}
		if($("#StaffNo").val().length>=5){
				$.ajax({ 
				type: "post", 
				url: "StaffNoServlet",
				 data: {'StaffNo':$("#StaffNo").val()}, 
				 async:false,
					 success: function(date){
									 if(date!=null && date!="[null]" && date!="null"){
											 $("#message").hide();
											 var master=eval(date);
											 if(master[0].sfcf=="Y"){
												 if(!confirm("该顾问在一周内已有过提交记录，是否继续?")){
													 $("#StaffNo").val("").focus();
													 return false;
												 }
											 }
											 $("#num").val(100);
											$("#EnglishName").val(master[0].name);
											$("#ChineseName").val(master[0].c_Name);
											$("#TR_RegNo").val(master[0].TR_RegNo);
											$("#CENO").val(master[0].CENo);
											if(master[0].CENo!=""){
												$("#C").attr("checked","checked").val("Y");
											}
											$("#MPFA_NO").val(master[0].MPFNo);
											$("#Mobile").val(master[0].mobilePhone);
											$("#FAX").val(master[0].fax);
											$("#DirectLine").val(master[0].directLine);
											$("#Email").val(master[0].email.toLowerCase());
											$("#EnglishPosition").val(master[0].e_DepartmentTitle.replace('\r\n','').replace('\r\n','').replace('\r\n','')).change();
											//$("#EnglishPosition").get(0).selectedIndex=10;
							 
											$("#EnglishPostion1").val(master[0].lastPosition_E.replace('\r\n','').replace('\r\n','').replace('\r\n',''));
											$("#ChinesePostion1").val(master[0].lastPosition_C.replace('\r\n','').replace('\r\n','').replace('\r\n',''));
											//$("#ChinesePosition").val(master[0].c_DepartmentTitle);
											//if(master[0].c_DepartmentTitle=="")
											//$('#EnglishPosition').change();
											$("#ChineseExternalTitle").val(master[0].c_ExternalTitle);
											$("#EnglishExternalTitle").val(master[0].e_ExternalTitle);
											$("#ChineseProfessionalTitle").val(master[0].c_EducationTitle);
											$("#EnglishProfession").val(master[0].e_EducationTitle);
											if(master[0].grade=="PA"||master[0].grade=="AC"||master[0].grade=="CA")
												$("#payer").val(master[0].recruiterId);	
											else
												$("#payer").val(master[0].staffNo);
											payers();
										
									 }
									if(date=="[null]"||date==null){
										$("#message").show(200);
										$(".txt").val("").html("");
										$("#StaffNo").focus();
										return false;
									} 
			$.ajax({
				type: "post",
				url: "QueryConsMarqueeServlet",
				data: {'code':$("#StaffNo").val(),'DD': $("#DD_result").val()},
				success: function(date){
					$("#agun").html("<strong>"+date+"</strong>");
				
				},error:function(){
					  errorAlert("网络连接失败，请联系管理员或稍后重试...");
					  return false;
				  }
			});
				
							 } 
				  });
		  }
		else{
			if($("#StaffNo").val().length!=0){
			error("Staff Code為非法數據！");
			//$(".txt,[id!='payer']").val("");
			$(".txt").val("").html("");
			$("#StaffNo").focus();
			return false;
			}
			
		}
		
		$.ajax({
			url:"VialDDServlet",
			type:"post",
			data:{"staffcode":$("#StaffNo").val()},
			success:function(date){
				 if(date=="SUCCESS"){
					DD=true;
					$("#DD_result").val("true");
					getLayout();
				 }else{
					 DD=false;
					 	 //2014-04-14 King 注释 $("#type option[value='P']").remove();
					 $("#DD_result").val("false");
				 }
			},error:function(){
				errorAlert("网络连接失败");
				return false;
			}
		});
		
			$.ajax({
			url:"VailAEorConsultant",
			type:"post",
			data:{"staffcode":$("#StaffNo").val()},
			success:function(date){
				var dataRole=eval(date);
				if(dataRole!=null  && dataRole!="null"){
					
					 if(dataRole==""){
						 AECode=false;Elite=false;
					 }else{
							if(dataRole[0]!="" && dataRole[0]!=null){
								AECode=true;
							}else{
								AECode=false;
							}
								if(dataRole[1]!="" && dataRole[1]!=null){
								Elite=true;
							}else{
								Elite=false;
							}
					}
				}else{
					errorAlert("System ERROR, please try later...");
					return false;
				}
			},error:function(){
				errorAlert("Network connection is failed, please try later...");
					return false;
			}
	});
		
});
/*****************************************表单提交***************************/
$("#AddNameCard").click(function(){ 
	vailed();
	return false;
});		
$("body").keydown(function(e){
	if(e.keyCode==13){
	vailed();
	return false;
	}
	});
/*****************************表单结束********************************/	
/********************************UrgentCase值发生改变时*********************/
$("#urgentCase").change(function(){
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
			$("#num").val("100");
			}else{
				$("#num").val("100");
			}
});
/**************************************************************************/
function CurentTime()
    { 
        var now = new Date();
       
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
       //  var hh = now.getHours();            //时
       // var mm = now.getMinutes();          //分
        var clock = year + "-";
        if(month < 10)
            clock += "0";
        clock += month + "-";
        if(day < 10)
            clock += "0";
        clock += day;
       //  if(hh < 10)
       //     clock += "0";
       //  clock += hh + ":";
       // if (mm < 10) clock += '0'; 
       //  clock += mm; 
        return(clock); 
    } 
function vailed(){
	
	var nums=$("#num").val();                                                      
	if($("#StaffNo").val()==""){																 
		error("Staff Code不能為空!");	
		$("#StaffNo").focus();
		return false;																			 
	}																							 
	if($("#num").val()==""){															 
		error("Quantity不能為空！");	
			$("#num").focus();
		return false;																				 
	}	 																				 
	if(isNaN(nums)){		
		error("Quantity 必須在 1~400 之間的數字!");	
		$("#num").focus();
		return false;															 
	}		
	if(!isNaN(nums)){																						 
		if(parseInt($("#num").val())>400){													   
		error(" Quantity 數量單次不能大於 400！"); 
		$("#num").focus();
		return false;																			 
	}	
	if($("#num").val()%100 !=0){
		error("quantity必须是100的整数倍！");
		$("#num").focus();
		return false;
	}													 
}	

/**************************判断事件是否合法*****************/
/**********************************************************/


/*****************判断复选框是否被选中**********************/
		/**if($("#AE").attr("checked")==true){
			$("#AE").val("Y");
		}
		else{
			$("#AE").val("N");
		}
		if($("#ET").attr("checked")==true){
			$("#ET").val("Y");
		}
		else{
			$("#ET").val("N");
		}
		if($("#C").attr("checked")==true){
			$("#C").val("Y");
	
		}else{
			$("#C").val("N");
		}
		if($("#C2").attr("checked")==true){
			$("#C2").val("Y");
	
		}else{
			$("#C2").val("N");
		}
		if($("#CCC").attr("checked")==true){
			$("#CCC").val("Y");
		
		}else{
			$("#CCC").val("N");
		}
		if($("#C3").attr("checked")==true){
			$("#C3").val("Y");
		
		}else{
			$("#C3").val("N");
		}
		if($("#C4").attr("checked")==true){
			$("#C4").val("Y");
		
		}else{
			$("#C4").val("N");
		}
		
		if($("#C22").attr("checked")==true){
			$("#C22").val("Y");
		
		}else{
			$("#C22").val("N");
		}
		
		if($("#C23").attr("checked")==true){
			$("#C23").val("Y");
		}else{
			$("#C23").val("N");
		}**/
	if($("#EnglishProfession").val().split(";").length-1>4){
			error("English Academic & Professional Title只能包含4个专业title和一个学历title!");
			return false;
			}
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
				$("#urgentCase").val("Y");
				if(parseInt($("#num").val())<100 && parseInt($("#num").val())>0){
				 error("在选择Urgent的情况下，Quantity不能低于100!");
					$("#num").focus();
					return false;
				}
				/**else{
					if(!confirm("StaffNo:"+$("#StaffNo").val()+"    Quantity:"+$("#num").val()+"\n            您確定要提交嗎？")){
						 	return false;
						}
				}**/
		}
		//貌似trim()方法不支持
		if($("#payer").val() != null){
			if($("#StaffNo").val().toLowerCase().replace(" ","")!=$("#payer").val().toLowerCase().replace(" ","")){
			 	if(pay!=true){
					error("支付人不存在，請重新填寫！");
					$("#payer").focus();
					return false;
				}else{ 
						if(!confirm("StaffNo:"+$("#StaffNo").val()+"與Payer:"+$("#payer").val()+"不同 "+"\n          您確定要提交嗎？")){
						 	return false;
						}
				 }
			}
		}else{
			error("支付人不存在，請重新填寫！");
			return false;
		}
		if(parseInt($("#num").val())<0){
			if(!confirm("您正在進行刪除操作，確定要刪除該數據嗎？")){
				return false;
			}
		}
		if($("#EnglishPosition").val()=="" || $("#EnglishPosition").val()==null || $("#EnglishPosition").val()==""){
			error("Please Select Title_Department in English !");
			$("#EnglishPosition").focus();
			return false;
			
		}
		if($("#EnglishProfessionalTitle").val()=="professional"){
			$("#EnglishProfessionalTitle").val("");
		}
			var remainNumber=parseFloat($("#agun").text().substring($("#agun").text().lastIndexOf(":")+1,$("#agun").text().length-2));
		if($("#type").val()=="S" && $("#urgentCase").attr("checked")==false){
			if(nums>parseFloat(remainNumber)){
			/**	if(!confirm("您的免费限额还剩"+remainNumber+",如果继续，超出部分将按正常收费!")){
					$("#num").focus();
					return false;
				}	**/
				if(parseFloat(remainNumber)>0){
					if(!confirm("您的免费限额还剩"+remainNumber+",如果继续，超出部分将按正常收费!")){
						$("#num").focus();
						return false;
					}
				}else {
					if(!confirm("您的免费限额已用完,如果继续，超出部分将按正常收费!")){
						$("#num").focus();
						return false;
					}
				}
			}
		}
	$("#AddNameCard").attr("disabled","disabled");
	if($("#ET").attr("checked")==true){
		$.ajax({
			url:"VailEliteServlet",
			type:"post",
			data:{"staffcode":$("#StaffNo").val(),"table":"request_new"},
			success:function(date){
				if(date=="true"){
						if(!confirm("Elite Team 的免费限额已满,继续办理需支付额外费用")){
							$("#ET").attr("checked","");$("#type").val("S");
							$("#AddNameCard").attr("disabled","");
							return false;
						}
				}else{
					if(parseFloat($("#num").val())>100){
									if(!confirm("该顾问的Elite Team 的免费限额只有100,超出部分将正常收费，是否继续?")){
											$("#save").attr("#disabled","disabled");
										return false;
									}
								}
				}
					$("#myform").submit();	
			},error:function(){
					$("#AddNameCard").attr("disabled","");
				
				errorAlert("网络连接失败，请稍后重试...");
				return false;
			}
		});
	}else{
		$("#myform").submit();	
	}
	
}
	function changeDate(){
		var date=$("#urgentDate").val();
		if(date!=""){
		var year=date.substr(0,4);
		var index1=date.indexOf("-"); 
		var index2=date.lastIndexOf("-"); 
		var cha=parseInt(index2)-(parseInt(index1)+1); 
		var month=date.substr((parseInt(index1)+1),cha);
		var day=date.substr(index2+1);
		var ca,ba;
		if(month<10){
		ca="-0";
		}
		else{
		ca="-";
		}
		if(day<10){
		ba="-0";
		}else{
		ba="-";
		}
		$("#urgentDate").val(year+ca+parseInt(month)+ba+parseInt(day));
		}
	}					




});
</script>
</head>
<body  style="overflow: auto;">
<div class="cont-info">
	<div class="info-title">
		<div class="title-info">
			<!--  <div class="alert alert-error">
				<i class="icon-info-sign"></i>
				<span id="errortext">hello, this is jimi's test!</span>
			</div>-->
			<form action="AddNewRequestServlet" id="myform" method="post">
			<table>
				<tr>
					<td class="tagName">Cons Code:</td>
					<td class="tagCont">
						<input id="StaffNo" name="StaffNos" onkeyup="this.value=this.value.toUpperCase();"  type="text" size="20" class="txt" />
						<label class="inline checkbox">
							<input type="checkbox" id="sf" name="sfs" value="N" />
							No Code 
						</label>
						<span id="message">Staff_NO不存在!</span>
					</td>
					<td class="tagName">quantity:</td>
					<td class="tagCont">
						<input name="nums" type="text" class="txt" id="num" size="5" maxlength="3" value="100"  onkeyup="return checkNum(this);" onkeypress="return checkNum(this);" onfocus="javascript:$('#numId').show();"/>
			     		<select id="numId" name="nums" style="display: none;" onchange=" $('#num').val($(this).val()) ;">
			              <option value="100">100</option>
			              <option value="200">200</option>
			              <option value="300">300</option>
			              <option value="400">400</option>
			            </select> 
					</td>
				</tr>
				<tr>
					<td class="tagName">Location:</td>
					<td class="tagCont">
						<select name="locatins" id="location"></select>
					</td>
					<td class="tagName">Layout:</td>
					<td class="tagCont">
						<select name="types" id="type">
          					<option value="S">Standard Layout</option>
          				</select>
					</td>
				</tr>
				<tr>
					<td class="tagName">AE Consultant:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input type="checkbox" name="AEs" value="N" id="AE" onchange="javascript:if(AECode==false){return false;}" onclick="javascript:if(AECode==false){return false;}" />YES
						</label>
						<span>（如無AE Code請勿勾選）</span>
					</td>
					<td class="tagName">Urgent Case:</td>
					<td class="tagCont">
						<label class="inlune checkbox">
							<input id="urgentCase" type="checkbox" name="urgent" value="N" />YES
						</label>
						<input name="urgentDate" id="urgentDate" style='cursor:hand' type="text" readonly="readonly" title="輸入日期最好大於當前日期" onclick="Calendar('urgentDate');"/>
      					<span>（勾選此項後將產生額外費用）</span></div>
					</td>
				</tr>
				<tr>
					<td class="tagName">Elite Team:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input type="checkbox" name="ETs" value="N" id="ET" onchange="javascript:if(Elite==false){return false;}" onclick="javascript:if(Elite==false){return false;}"/>YES
						</label>
						<span>（非Elite Team 成員請勿勾選）</span>
					</td>
					<td class="tagName"></td>
					<td class="tagCont">
						
					</td>
				</tr>
				<tr>
					<td class="tagName">English Name:</td>
					<td class="tagCont">
						<input name="EnglishNames" type="text" size="25" class="txt" id="EnglishName" />
					</td>
					<td class="tagName">Chinese Name:</td>
					<td class="tagCont">
						<input id="ChineseName" name="ChineseNames" type="text" size="25" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Last print Title dept in English:</td>
					<td class="tagCont">
						<input type="text" id="EnglishPostion1" size="25" class="txt" /> 
					</td>
					<td class="tagName">Last print Title dept in Chinese</td>
					<td class="tagCont">
						<input type="text" id="ChinesePostion1" size="25" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Title Department in English:</td>
					<td class="tagCont">
						<select id="EnglishPosition" name="EnglishPositions">
				            <option value="">Please Select PositionName</option>
				        </select>
					</td>
					<td class="tagName">Title Department in Chinese:</td>
					<td class="tagCont">
						<input id="ChinesePosition" name="ChinesePositions" type="text" size="25"  class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">ExternalTitle Department in English:</td>
					<td class="tagCont">
						<input id="EnglishExternalTitle" name="EnglishExternalTitles" type="text" size="25"  class="txt" />
					</td>
					<td class="tagName">ExternalTitle Department in Chinese:</td>
					<td class="tagCont">
						<input id="ChineseExternalTitle" name="ChineseExternalTitles" type="text" size="25"  class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">English Academic & Professional Title:</td>
					<td class="tagCont" colspan="3">
						<input type="text" name="EnglishProfessionalTitles" class="txt" id="EnglishProfession" size="70"/>
						<select id="EnglishProfessionalTitle" name="">
				          <option value="">Please Select ProfessionalTitle</option>
				        </select>
					</td>
				</tr>
				<tr>
					<td class="tagName">Chinese Academic & Professional Title:</td>
					<td class="tagCont" colspan="3">
						<input id="ChineseProfessionalTitle" name="ChineseProfessionalTitles" type="text" size="70"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">TR Reg NO:</td>
					<td class="tagCont">
						<input id="TR_RegNo" name="TR_RegNos" type="text" size="25" class="txt" />
					</td>
					<td class="tagName">CE NO:</td>
					<td class="tagCont">
						<input id="CENO" name="CENOs" type="text" size="25" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">MPFA NO:</td>
					<td class="tagCont">
						<input id="MPFA_NO" name="MPFA_NOs" type="text" size="25" class="txt" />
					</td>
					<td class="tagName">Mobile:</td>
					<td class="tagCont">
						<input id="Mobile" name="Mobiles" type="text" size="25" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">FAX:</td>
					<td class="tagCont">
						<input id="FAX" name="FAXs" type="text" size="25" class="txt" />
					</td>
					<td class="tagName">Direct Line:</td>
					<td class="tagCont">
						<input id="DirectLine" name="DirectLines" type="text" size="25" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">E-Mail:</td>
					<td class="tagCont">
						<input id="Email" name="Emails" onkeyup="this.value=this.value.toLowerCase();" type="text" size="25" class="txt" />
					</td>
					<td class="tagName">Payer:</td>
					<td class="tagCont">
						<input id="payer" type="text" class="txt" name="payers" size="25" />
					</td>
				</tr>
				<tr>
					<td class="tagCont" colspan="4" style="padding-left: 30px;">
						<label class="inline checkbox">
							<input id="CC" type="checkbox" name="CFS" value="Y" checked="checked" />CFS
						</label>
						<label class="inline checkbox">
							<input id="C" type="checkbox" name="CAM" value="N" />CAM
						</label>
						<label class="inline checkbox">
							<input id="CCC" type="checkbox" name="CIS" value="N" />CIS
						</label>
						<label class="inline checkbox">
							<input id="C2" type="checkbox" name="CCL" value="N" />CCL
						</label>
						<label class="inline checkbox">
							<input id="C3" type="checkbox" name="CFSH" value="N" />CFSH
						</label>
						<label class="inline checkbox">
							<input id="C4" type="checkbox" name="CMS" value="N" />CMS
						</label>
						<label class="inline checkbox">
							<input id="C22" type="checkbox" name="CFG" value="N" />CFG
						</label>
						<label class="inline checkbox">
							<input id="C23" type="checkbox" name="Blank" value="N" />Blank
						</label>
						<input id="pay" type="hidden" name="pays"/><input id="DD_result" name="DD" type="hidden"/>
					</td>
				</tr>
				<tr>
					<td class="tagBtn" colspan="4">
						<div id ="agun" style="color:#00305A;font-size:18px;font-family: 'Arial Narrow';">  </div>
						<a class="btn" id="AddNameCard" name="Submit">
							Submit
						</a>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
