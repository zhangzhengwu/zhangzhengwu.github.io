<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
if(session.getAttribute("convoy_username")==null || session.getAttribute("convoy_username")==""){
	out.println("<script>parent.location.href='../error.jsp';</script>");
}
%>
<!DOCTYPE HTML>
<html >
	<head>
	    <base href="<%=basePath%>">
    
    	<title>add name card</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta http-equiv="expires" content="0"/>
	
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<script language="javascript" type="text/javascript" src="./css/date.js"></script>
	<script type="text/javascript" src="./css/ajaxfileupload.js"></script>
	<script type="text/javascript" src="./css/Util.js"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">  
	<style type="text/css">
	.red{color: red;}
	</style>
<script type="text/javascript" >
 var base="<%=basePath%>";

   var fileType="";
   var fileName="";
   var oldfileName="";
       var rightFileType= new Array("jpg", "bmp", "gif", "png","jpeg");  
/**
 * 只读文本框禁用退格键
 * 
 * */
function BackSpace(e){
	if(window.event.keyCode==8){//屏蔽退格键
    var type=window.event.srcElement.type;//获取触发事件的对象类型
 	var reflag=window.event.srcElement.readOnly;//获取触发事件的对象是否只读
  	var disflag=window.event.srcElement.disabled;//获取触发事件的对象是否可用
	if(type=="text"||type=="textarea"){//触发该事件的对象是文本框或者文本域
		   if(reflag||disflag){//只读或者不可用
		    window.event.returnValue=false;//阻止浏览器默认动作的执行
		   }
	  }else{ 
	   	window.event.returnValue=false;//阻止浏览器默认动作的执行
	  }
 }
}
function vailEmail(obj){  
	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(!reg.test($(obj).val())){
			alert("Email address is not legal!");
			$("#AddNameCard").attr("disabled",true);
			return false;
		}else{
			$("#AddNameCard").removeAttr("disabled");
		} 
}

var DD=true;
var AECode=true;
var Elite=true;
/*窗体加载事件 */
$(function(){
	
	
/* 	$("input[name='companys'][value='CAM']").click(function(){
		if($("#CENO").val()==""){
			this.checked=false;
		}
	});
	$("#CENO").blur(function(){
		if(this.value==""){
			$("input[name='companys'][value='CAM']").removeAttr("checked");
		}else{
			$("input[name='companys'][value='CAM']").attr("checked","checked");
		}
	}); */
	
	
		 
	document.onkeydown=BackSpace;
/* 	function getlocation(){
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
				html+="<option value=''>Please Select Location</option>";
				for(var i=0;i<location.length;i++){
					html+="<option value='"+location[i].realName+"' >"+location[i].name+"</option>";
				}
			}else{
				html+="<option value=''>load error</option>";
			}
			$("#location").append(html);
			},error:function(){
				alert(" Network connection is failed, please try later...");//网络连接失败
				return false;
			}
		});
	} */
	function position(){
		$.ajax({
			type: "post",
			//url: "QueryPositionServlet",
			//data:null,
			url: "<%=basePath%>common/CommonReaderServlet",
			data:{"method":"getposition"},
			success: function(date){
				var d=eval(date);
				var html;
				if(d.length>0){
					for(var i=0;i<d.length;i++){
						html+="<option value='"+d[i].position_ename+"'>"+d[i].position_ename+"</option>";
					}
						$("#EnglishPosition:last").append(html);
				}
			}
		});
	}
	function getLocation(){
		$.ajax({
			type: "post",
			//url: "QueryPositionServlet",
			//data:null,
			url: "<%=basePath%>common/CommonReaderServlet",
			data:{"method":"getLocation","staffcode":$("#StaffNo").val()},
			success: function(dat){
				$("#locationflag").val(dat);
		        if($("#locationflag").val().indexOf("Nan Fung") > -1){
		        	$("#location").val("Nan Fung");
		        }else if($("#locationflag").val().indexOf("Cityplaza 3") > -1){
		        	$("#location").val("CP3");
		        }else{
		        	$("#location").val("@CONVOY");
		        }
			},error:function(){
				alert("Network connection is failed, please try later…");
				return false;
			}
		});
	}

    //获取牌照集合 ，包括PIBA+HKCIB+SFC+MPF	
	function licenseplate(){
		$.ajax({
			type:"post",
			url:"<%=basePath%>common/CommonReaderServlet",
			data:{"method":"getlicenseplate","staffcode":$("#StaffNo").val()},		
			dataType:"json",	
			success: function(date){
				if(date!=null && date!="[null]" && date!="null" && date!=""){
					$("#TR_RegNo").val(date.PIBA);
					$("#CENO").val(date.SFC);
					$("#MPFA_NO").val(date.MPF);
					$("#HKCIB_NO").val(date.HKCIB);
					checklist();
				}
			}
		});
/* 		
		if($("#StaffNo").val()=='AC0101'){
			$("#TR_RegNo").val("PIBA-0066-000238");
		}
		if($("#StaffNo").val()=='AC0150'){
			$("#HKCIB_NO").val("HKCIB1234");
		}
		if($("#StaffNo").val()=='AC5076'){
			$("#TR_RegNo").val("PIBA-0066-000380");
			$("#MPFA_NO").val("016973");
		}
		if($("#StaffNo").val()=='AH0112'){
			$("#TR_RegNo").val("PIBA-0066-000238");
			$("#CENO").val("AGO107");
		}
		if($("#StaffNo").val()=='AK6603'){
			$("#TR_RegNo").val("PIBA-0066-000238");
			$("#CENO").val("AGO157");
			$("#MPFA_NO").val("016975");
		}
		if($("#StaffNo").val()=='AL0156'){
			$("#CENO").val("AGO757");
			$("#MPFA_NO").val("016977");
			$("#HKCIB_NO").val("HKCIB1278");
		}
		if($("#StaffNo").val()=='AL0169'){
			$("#TR_RegNo").val("PIBA-0066-001238");
			$("#CENO").val("AGO777");
			$("#MPFA_NO").val("016777");
			$("#HKCIB_NO").val("HKCIB3278");
		}
		 
		checklist();
*/
		
	}
	
	function professional(){
		$.ajax({
			type: "post",
			//url: "QueryProfessionalServlet",
			//data:null,
			url: "<%=basePath%>common/CommonReaderServlet",
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
			$("[type='checkbox'][name!='companys'][id!='CC2'][id!='sf']").attr("checked","").val("N");
				$("#EnglishName,#ChineseName,#ChinesePosition,#HKCIB_NO,#TR_RegNo,#CENO,#Email,#DirectLine,#MPFA_NO,#payer").attr("readonly","").css("color","").attr("title","");
				$("#sf").val("Y");
				$(".txt[id!='StaffNo'][id!='payer'][id!='DirectLine'],#EnglishPosition").val("");
				$("#StaffNo").val(formatTime());
				$("#StaffNo").attr("readonly","readonly");
				$("#message,#email_span").hide();
				$("#agun_staffcode,#agun").empty();
				payers();
				 
		}else{
			$("#EnglishName,#ChineseName,#ChinesePosition,#HKCIB_NO,#TR_RegNo,#CENO,#Email,#DirectLine,#MPFA_NO,#payer").attr("readonly","readonly").css("color","DarkGray").attr("title","Please state the different in “Remark”.");
			$("#email_span").show();
			$("#sf").val("N");
		 	$("#StaffNo").val(parent.staffcode).blur();

			//$("#StaffNo").removeAttr("readonly");
		}
	});
/**************************************EnglishProfession单击事件*****************************************/
$("#EnglishProfession").focus(function(){
	$("#EnglishProfessionalTitle").show();
});
$("#EnglishProfession").change(function(){
if($(this).val()=="")	
$("#upload_view,#upload").hide();
	else
$("#upload_view,#upload").show();
});
/**********************************窗體加載************************************************/
position();
professional();
/* getlocation(); */
$("#urgentDate").val(CurentTime());
$("#EnglishProfessionalTitle").hide();
/******************************************************************************************/
var pay=false;
/*****************************************************/
$("#message").hide();
$(".txt").val("");
//初始值
$("#StaffNo").val("${convoy_username}"); //BL4776,RT5772，AT6466,BL4776
//BL4776,RT5772，AT6466,BL4776
$("#payer").val("${convoy_username}").blur();


getLocation(); 
$("#urgentDate").empty;								//清空日期控件里面的残余值
$("#urgentDate").hide();							//隐藏日期控件
/*****************************************************/
var professionalCText="";
var professionalEText="";
/********************************EnglishProfessionalTitle值發生改變時*******************/
$("#EnglishProfessionalTitle").change(function(){
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
			},error:function(){
				alert("Network connection is failed, please try later…");
				return false;
			}
		});
	}
	});
	/**************************************************************************************/
	function payers(){
		$.ajax({ 
			type: "post", 
			url: "PayerServlet", 
			async:false,
			data: {'payer':$("#payer").val(),"type":"position"}, 
			success: function(date){
		 
			if(date=="" || date==null){
				alert("Please input the payer.");
				pay=false;
				//$("#payer").val("");
				return false;
			}else{
				//$("#pay").val(date);
				//pay=true;
				
				//判断NO code 的情况 提示“code不存在，或无权限录入。”
			
				if(date=="TRUE" ){
					if($("#sf").attr("checked")){
					alert("Sorry, you can’t submit the request for others.");//您暂时无权限帮他人办理名片
					$("#sf").removeAttr("checked");		
				//	$("#payer").val("");
					$("#StaffNo").val(parent.staffcode).blur();
					 pay=false;
					 }else{
						 $(".txt[id!='payer'][id!='StaffNo']").val("");
						pay=true;
					 }
					}else{
						pay=true;
						$.ajax({
							type: "post", 
							url: "PayerServlet", 
							data: {'payer':$("#payer").val(),"type":"name"}, 
							success: function(date){
									$("#pay").val(date);
								},error:function(){
										alert("There is wrong on payer.");
										//$("#payer").val("");
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
					$("#ChinesePosition").val(" ");
				}
			}
		}
	});
});

	/**
 * Elite Team change事件
 * 
 * */
$("#ET").change(function(){
		if(Elite){//如果是Elite成员
			if($(this).attr("checked")){
					if($("#sf").attr("checked")==true){//nocode
							$("#ET").val("N").removeAttr("checked");
							//$("#type").val("P");
							alert("“No Code” is not allowed to choose “Elite Team”");
							return false;
					}else{//有code
						$(this).attr("checked",true).val("Y");
					}
				}else{
						$(this).val("N").removeAttr("checked");
				}
		}else{//不是Elite成员
			alert("Sorry,you have no access.");
			$("#type").val("S");
			$(this).val("N").removeAttr("checked");
			return false;
		
		}
});
 

$("#AE").change(function(){
	 if(AECode){
		if($("#AE").attr("checked")==true){//如果选择AEConsultant
				if($("#sf").attr("checked")==true){//如果nocode
					alert("“No Code” is not allowed to choose “Elite Team”");
					$("#AE").val("N").removeAttr("checked");
					$("[name='companys'][value='CIS']").removeAttr("checked");
					return false;
				}else{ //有code
					$("#AE").attr("checked",true).val("Y");
					$("[name='companys'][value='CIS']").attr("checked","checked");
				}
		}else{//取消AECode
			$("#AE").val("N").removeAttr("checked");
			$("[name='companys'][value='CIS']").removeAttr("checked");
			}
	}else{
		alert("Sorry,you have no access.");
		$("#AE").val("N").removeAttr("checked");
		$("[name='companys'][value='CIS']").removeAttr("checked");
		return false;
		
	}
});

function getLayout(){
	//$("#type").empty().append("<option value='S'>Standard Layout</option><option value='P'>Premium Layout</option>");
	//2014-04-14 jackie 提出屏蔽Premium选项
	$("#type").empty().append("<option value='S'>Standard Layout</option>");
	
}

function fujianlist(){
		$.ajax({
				url:"QueryFileNameServlet",
				type:"post",
				data:{"staffcode":$("#StaffNo").val()},
				success:function(date){
					var dataRole=eval(date);
					var html="";$(".oldf").remove();
					if(dataRole!="" && dataRole!=null){
						for(var i=0;i<dataRole.length;i++){
							var fileNames=dataRole[i].substring(dataRole[i].indexOf("_")+1,dataRole[i].lastIndexOf("_"));
							 
							if(fileNames.split(";").length-1>0){
								var arr=new Array();
								arr=fileNames.split(";");
								for(var j=0;j<arr.length-1;j++){
									if(html.indexOf(arr[j])<0){
								html+="<option class='oladf'>"+arr[j]+"</option>";
								}
									
								}
							}else{
								if(html.indexOf(fileNames)<0){
								html+="<option  class='oladf'>"+fileNames+"</option>";
								}
							}
						}
					}else{
						html+="<option  class='oladf'>No upload record.</option>"
					}
					$("#fujian_list").append(html);
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
				
			});
}

$("[type='checkbox'][name!='companys']").click(function(){
		if($(this).attr("checked")){
			$(this).val("Y");
		}else{
			$(this).val("N");
		}
	});

/****************************************************StaffNo 失去焦点时触发的时间****************************************************/
$("#StaffNo").blur(function(){
		$("#EnglishPosition").get(0).selectedIndex=0;
		$("#EnglishProfessionalTitle").get(0).selectedIndex=0;
		$(".txt[id!='StaffNo'][id!='payer']").val("");
		if($("#sf").attr("checked")==true){
			return;
		}
		if($("#StaffNo").val().length>=5 ){
				$.ajax({ 
				type: "post", 
				url: "StaffNoServlet",
				 data: {'StaffNo':$("#StaffNo").val(),'eip':"convoy"},
				 beforeSend: function(){
						parent.showLoad();
					},
					complete: function(){
						parent.closeLoad();
					},
				 success: function(date){
					 if(date!=null && date!="[null]" && date!="null" && date!=""){
						
						$("#message").hide();
							 var master=eval(date);
						 	 if(master[0].sfcf=="Y"){
										 if(!confirm("Your previous request was submitted during last 7 days, please confirm if continue the request.")){
											 $("#StaffNo").val("");//7天之内有过提交记录
											 return false;
										 }
								 }
							$("#EnglishName").val(modifyString(master[0].name));
							$("#ChineseName").val(modifyString(master[0].c_Name));
//							$("#TR_RegNo").val(modifyString(master[0].TR_RegNo));
//							$("#CENO").val(modifyString(master[0].CENo)).blur();
//							$("#MPFA_NO").val(modifyString(master[0].MPFNo));
							$("#Mobile").val(modifyString(master[0].mobilePhone));
							$("#FAX").val(modifyString(master[0].fax));
							$("#DirectLine").val(modifyString(master[0].directLine));
							$("#Email").val(modifyString(master[0].email.toLowerCase()));
							$("#EnglishPosition").val(modifyString($.trim(master[0].e_DepartmentTitle))).change();
							$("#ChineseExternalTitle").val(modifyString(master[0].c_ExternalTitle));
							$("#ChineseProfessionalTitle").val(modifyString(master[0].c_EducationTitle));
							if(master[0].e_EducationTitle=="")
								$("#upload_view,#upload").hide();
							else
								$("#upload_view,#upload").show();				
							$("#EnglishProfession").val(modifyString(master[0].e_EducationTitle));
							use();
							vailDDs();
							setTimeout(vailETORAE(),200);
							licenseplate();
							
						 }else{
							$("#message").show(200);
							$(".txt[id!='payer']").val("").html("");
									//$(".txt").val("").html("");
							$("#StaffNo").focus();
							return false;
						} 
						
							
							
					 },error:function(){
						 alert("Network connection is failed, please try later...");
						 return false;
					 } 
				  });
		  } else{
				if($("#StaffNo").val().length!=0){
				alert("The staff code is wrong.");
				$(".txt,[id!='payer']").val("");
				$("#StaffNo").focus();
				return false;
				}
				
				}

	
 			
		 		
	});
	/*****************************************表单提交***************************/
	$("#AddNameCard").click(function(){ 
		if($("#sf").attr("checked")==true){
			if(confirm('The cost will be charged on you if no valid staff code for "No Code" is received on or before the last working days of this month.')){
				vailed();
				return false;
			}else{
				return false;
			}
		}else{
				vailed();
				return false;
		}
	
	});		
	 	
	$("body").keydown(function(e){
		if(e.keyCode==13){
			if($("#sf").attr("checked")==true){
			if(confirm('The cost will be charged on you if no valid staff code for "No Code" is received on or before the last working days of this month.')){
				vailed();
				return false;
			}else{
				return false;
			}
		}else{
				vailed();
				return false;
		}
		}
	});
	/*****************************表单结束********************************/	
	/********************************UrgentCase值发生改变时*********************/
	$("#urgentCase").change(function(){
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
			$("#num").val("200");
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
        var clock = year + "-";
        if(month < 10)
            clock += "0";
        clock += month + "-";
        if(day < 10)
            clock += "0";
        clock += day;
        return(clock); 
    } 
    $(".txt").change(function(){
    	$(this).css("background","");
    });
/**************************queryNameCard*****************/ 
	function query(){
		document.location.href("namecard/queryNameCard.jsp");
	}
/**************************判断事件是否合法*****************/ 
function vailed(){
	if($("#remarkcons").val()=="Please state here if any other special request"){
		$("#remarkcons").val("");
	}
		
	var nums=$("#num").val();  
	var flag=true;
$("#location,#EnglishPosition,#EnglishName,#Email,#Mobile,#ChinesePosition").each(function(){
		if($(this).val()==""){
			if($(this).attr("id")=="Email"){
				if($("#sf").val()=="N"){
					$(this).focus().select();
					$(this).css("background","LightSkyBlue");
					flag=false;
				}
			}else{
				$(this).focus().select();
					$(this).css("background","LightSkyBlue");
					flag=false;
			}
		}
			 
		
	});
		if(flag==false){
		error("You have the required fields not completed.");
		return false;
		}
		
	 
	/**if($("#StaffNo").val()==""){																 
		alert(" Please input the Staff Code.");	
		$("#StaffNo").focus();
		return false;																			 
	}																							 
	if($("#Mobile").val()==""){																 
		alert(" Please input the Mobile Phone.");	
		$("#Mobile").focus();
		return false;																			 
	}																							 
	if($("#FAX").val()==""){																 
		alert(" Please input the FAX.");	
		$("#FAX").focus();
		return false;																			 
	}	**/																						 
	if($("#num").val()==""){															 
		error("Please choose 100,200,300 or 400 for “Quantity”.");	
			$("#num").focus();
		return false;																				 
	}	 																				 
	if(isNaN(nums)){		
		error("Please choose 100,200,300 or 400 for “Quantity”.");	
		$("#num").focus();
		return false;															 
	}		
	if(!isNaN(nums)){																						 
		if(parseInt($("#num").val())>400){													   
			error("Please choose 100,200,300 or 400 for “Quantity”."); 
			$("#num").focus();
			return false;																			 
		}	
		if($("#num").val()%100 !=0){
			error("Please choose 100,200,300 or 400 for “Quantity”.");
			$("#num").focus();
			return false;
		}													 
	}	 
	
	if(getCompany()==""){
		error("Company 不能为空!");
		return false;
	}else{
		$("#company").val(getCompany());
	}
/*****************判断复选框是否被选中**********************/
		 
		if($("#EnglishProfession").val().split(";").length-1>4){
			error('Only less than 4 professional titles and 1 education title are allowed for “English Academic & Professional Title”.');
			return false;
			}
		
		if($("#urgentCase").attr("checked")){//當urgentCase選中時
				$("#urgentCase").val("Y");
				if(parseInt($("#num").val())<200 && parseInt($("#num").val())>0){
				 	error("Quantity must be 200 or more for “Urgent Case”.");
					$("#num").focus();
					return false;
				}
			/*	else{
					if(!confirm("StaffNo:"+$("#StaffNo").val()+"    Quantity:"+$("#num").val()+"\n            您確定要提交嗎？")){
					 	return false;
					}
				}*/
		}
		//貌似trim()方法不支持
		if($("#payer").val() != null){
			if($("#StaffNo").val().toLowerCase().replace(" ","")!=$("#payer").val().toLowerCase().replace(" ","")){
			 	if(pay!=true){
					error("Please input the payor.");
					$("#payer").focus();
					return false;
				}else{ 
					//if(!confirm("StaffNo:"+$("#StaffNo").val()+"與Payer:"+$("#payer").val()+"不同 "+"\n          您確定要提交嗎？")){
					if(!confirm(" Staff Code and Payer are different, please confirm if continue the request?")){
					 	return false;
					}
				 }
			}
		}else{
			error("Please input the payer.");
			return false;
		}
		if(parseInt($("#num").val())<0){
			if(!confirm("Are you sure to delete this record?")){
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
		var remainNumber=parseFloat($("#agun").text().substring($("#agun").text().lastIndexOf("is ")+3));
		if($("#type").val()=="S" && $("#urgentCase").attr("checked")==false){
			if(nums>parseFloat(remainNumber)){
				if(parseFloat(remainNumber)>0){
					if(!confirm("Your remaining free quota is "+remainNumber+", the excess part will be charged on you if continue.")){
						$("#num").focus();
						return false;
					}
				}else{
					if(!confirm("Your remaining free quota has been used up and the excess part will be charged on you if continue.")){
						$("#num").focus();
						return false;
					}
				}
			}
		}
		$("#AddNameCard").attr("disabled","disabled");
	if($("#ET").attr("checked")){
		var isubelite=vailElite();
	
		if(null==isubelite){
			$("#AddNameCard").removeAttr("disabled");
		}else if(true==isubelite){
			if(!confirm("Your free quota for Elite Team has been used up and the excess part will be charged on you if continue.")){
				$("#ET").removeAttr("checked");
				$("#AddNameCard").removeAttr("disabled");
				return false;
			}
		}else{
			if(parseFloat($("#num").val())>100){
				if(!confirm("Your free quota for Elite Team has been used up and the excess part will be charged on you if continue.")){
						$("#AddNameCard").attr("#disabled","");
					return false;
				}
			}
		}
		/**
		$.ajax({
			url:"VailEliteServlet",
			type:"post",
			data:{"staffcode":$("#StaffNo").val(),"table":"request_new"},
			success:function(date){
				if(date=="true"){
						if(!confirm("Your free quota for Elite Team has been used up and the excess part will be charged on you if continue.")){
							$("#ET").attr("checked","");
							$("#AddNameCard").attr("disabled","");
							return false;
						}
				}else{
					if(parseFloat($("#num").val())>100){
									if(!confirm("Your free quota for Elite Team has been used up and the excess part will be charged on you if continue.")){
											$("#AddNameCard").attr("#disabled","");
										return false;
									}
								}
				}
			},error:function(){
				$("#AddNameCard").attr("disabled","");
				alert("Network connection is failed, please try later…");
				return false;
			}
		});
		**/
		
		
	} 
	
	if($("#urgentCase").val()=="Y"){
		var price=0;
		if($("#ET").attr("checked")){
			price=parseFloat($("#num").val())*2.15;
		}else{
			price=parseFloat($("#num").val())*1.65;
		}
		
		if(!confirm("Your have chosed 'Urgent' and this will cost HK$"+price+".would you want to continue?")){
			$("#AddNameCard").removeAttr("disabled");
			return false;
		}
		
	}
	
	
	/**
	 * 
	 * 保存附件 
	 * */
	if(fileName!=""){
				$.ajax({
				url:"SaveUploadFileServlet",
				type:"post",
				async:false,
				data:{"staffcode":$("#StaffNo").val(),"profession":$("#EnglishProfession").val(),"filename":fileName},
				success:function(date){
					if(date=="success"){
						//$("#myform").submit();
						submitForm();
					}else{
						alert("The upload is failed.");
						return false;
					}
				},error:function(){
					$("#AddNameCard").removeAttr("disabled");
						alert("Network connection is failed, please try later...");
						return false;
				}
			});
		}else{
			if($("#EnglishProfession").val()!=""){
				if(confirm(" Please make sure all documents have been provided for “Professional Title”.")){
					submitForm();
				}else{
					$("#AddNameCard").removeAttr("disabled");
				}
			}else{
				//$("#myform").submit();
				submitForm();
			}
		}
		//
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
/**
 * 验证是否办理过eliteteam
 */
function vailElite(){
	$.ajax({
		url:"VailEliteServlet",
		type:"post",
		data:{"staffcode":$("#StaffNo").val(),"table":"request_new"},
		success:function(date){
			return date;
		},error:function(){
			alert("Connection Error");
			return null;
		}
		
	});
}
	
	

 function vailETORAE(){
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
								//$("#AE").attr("checked",true).val("Y");
								//$("[name='companys'][value='CIS']").attr("checked","checked");
							}else{
								AECode=false;
								//$("#AE").attr("checked",false).val("N");
								//$("[name='companys'][value='CIS']").attr("checked","");
							}
							if(dataRole[1]!="" && dataRole[1]!=null){
								Elite=true;
								//$("#ET").attr("checked",true).val("Y");
							}else{
								Elite=false;
								//$("#ET").attr("checked",false).val("N");
							}
					}
				}else{
					alert("System ERROR, please try later...");
					return false;
				}
			},error:function(){
				alert("Network connection is failed, please try later...");
					return false;
			}
	});
 }
 function vailDDs(){
	 	$.ajax({
			url:"VialDDServlet",
			type:"post",
			data:{"staffcode":$("#StaffNo").val(),"eip":"econvoy"},
			success:function(date){
				 if(date=="SUCCESS"){
					DD=true;
					$("#DD_result").val("true");
					getLayout();
				 }else{
					 DD=false;
					 //$("#type option[value='P']").remove();
					 $("#DD_result").val("false");
				 }
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
		});
 }
 
 function marqu(){
	 				//显示marquee 字体滚动
			$.ajax({
				type: "post",
				url: "QueryConsMarqueeServlet",
				data: {'code':$("#StaffNo").val(),'DD': $("#DD_result").val()},
				success: function(date){
					
				
					var temp="";
						if(date.indexOf(",")>-1){
							temp=date.split(",");	
							$("#agun_staffcode").html(temp[0]+":");
							$("#agun").html("<strong>"+temp[1]+"</strong>");
						}else{
							$("#agun").html("<strong>"+date+"</strong>");
						}
				
//					$("#agun").html("<strong>"+date+"</strong>");
					
				//	$("#agun").html("<p class='alert alert-info'>"+date+"</p>");
				
				 fujianlist();
				},error:function(){
					  alert(" Network connection is failed, please try later...");
					  return false;
				  }
			});
 }
	function use(){
		$.ajax({
			type : "post",
			//url : "QueryConsMarqueeServlet",
			url : base+"namecard/NameCardReaderServlet",
			data : {
				'code' : $("#StaffNo").val(),"method":"finduserUsage"
			},
			success : function(date) {
				if(date.indexOf("{")==0){
					var result=$.parseJSON(date);
					alert(result.msg);
				}else{
					var temp="";
					if(date.indexOf(",")>-1){
						temp=date.replace(".0","").replace(".0","").replace(".0","").split(",");	
						$("#agun_staffcode").html(temp[0]+":");
						$("#agun").html("<strong>"+temp[1]+"</strong>");
					}else{
						$("#agun").html("<strong>"+date+"</strong>");
					}
				}
			},
			error : function() {
				errorAlert("网络连接失败，请联系管理员或稍后重试...");
				return false;
			}
		});
	}
	
 
	
	
});

 function fileupload(){
               if($("#filePaths").val()==""){  
                   return false;  
               }  
                if($("#EnglishProfession").val()==""){
        		//   alert("ProfessionTitle為空,無需上傳附件");
        		   //return false;
        	   }
               oldfileName=$("#filePaths").val().substring($("#filePaths").val().lastIndexOf('\\')+1);
            $.ajaxFileUpload({  
                       url:'FileTitleUploadServlet', 
                       secureuri:false,  
                       fileElementId:'filePaths',  
                       dataType: 'text/xml',
                       date:"sdf",
                       success: function (data) { 
            	 		 fileName=data;
                       fileType=fileName.substring((fileName.lastIndexOf(".")+1)); 
                   $("#downs").attr("href","upload/temp/"+data).empty().append(oldfileName);
                   $("#quitFujian").show();
                   $("#upload_view").hide();
                  /**  
                   * $.each(rightFileType,function(n,value){//遍历Array数组，n为数组下标 ，value为数组下标对应的值
                    	if(value==fileType){
                    		   $("#downs").attr("href","").empty().append("");
                    		 $("#images").show().attr("src","upload//"+fileName);
                    		   return;
                    	}
                    });
                   **/
                       },error: function (data, status, e){  
                           alert("fail");  
                       }  
                   });  
           }  
 
 
 function submitForm()
 {
 	$.ajax({
 		//url:"AddStaffRequestConvoyServlet",
 		url:base+"namecard/NameCardConvoyWriteServlet",
 		type:"post",
 		async:false,
 		data:$('#myform').serialize(), 
		beforeSend: function(){
			parent.showLoad();
			$("#AddNameCard").attr("disabled",true);
		},
		complete: function(){
			parent.closeLoad();
			$("#AddNameCard").removeAttr("disabled");
		},
 		success:function(date){
 			var result=$.parseJSON(date);
 			if(result.state=="success"){
 				successAlert('Successfully submitted! The Cut-off date is Every Friday at 12:00 !',function(){
 					location.href='namecard/queryNameCard.jsp';
 				});
 			}else if(result.state=="error"){
 				errorAlert("Save error");
 			}else{
 				errorAlert(result.msg);
 			}
 		},error:function(){
 			errorAlert("Network connection is failed, please try later...");
 			return false;
 		}
 	});
 	return false;
 }
 
 
 
 	function checklist(){
//	alert("PIBA："+$("#TR_RegNo").val()+"SFC："+$("#CENO").val()+"MPF："+$("#MPFA_NO").val()+"HKCIB："+$("#HKCIB_NO").val());
		//	PIBA SFC MPF HKCIB
		if($("#TR_RegNo").val()!="" && $("#CENO").val()!="" && $("#MPFA_NO").val()!="" && $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CAM']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA SFC HKCIB	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CAM']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA MPF HKCIB	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()==""&& $("#MPFA_NO").val()!=""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA SFC MPF 	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()!=""&& $("#HKCIB_NO").val()==""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CAM']").attr("checked","checked");
		//	SFC MPF HKCIB	
		}else if($("#TR_RegNo").val()==""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()!=""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CAM']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA SFC  	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()==""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CAM']").attr("checked","checked");
		//	PIBA HKCIB	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()==""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	SFC HKCIB	
		}else if($("#TR_RegNo").val()==""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CAM']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA MPF 	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()==""&& $("#MPFA_NO").val()!=""&& $("#HKCIB_NO").val()==""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
		//  HKCIB	
		}else if($("#TR_RegNo").val()==""&& $("#CENO").val()==""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA   	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()==""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()==""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
		//	SFC   	
		}else if($("#TR_RegNo").val()==""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()==""){
			$("input[name='companys'][value='CAM']").attr("checked","checked");
		}else{
//			alert(" Failure to meet the conditions of application...");//不符合申请条件
			$("input[name='companys'][value='CFS']").attr("checked","checked");
		}
	}
</script>
</head>
 
<body onload="$('#StaffNo').blur();">
<div class="cont-info">
	<div class="info-title">
		<div class="title-info">
			<form action="namecard/NameCardConvoyWriteServlet" id="myform" method="post">
			<table>
				<tr>
					<td style="text-align: center;" colspan="4">
						<p class="alert alert-info">
							1.The gray wordings are  not allowed to edit and please state the difference in &quot;Remark&quot;. &nbsp;&nbsp;
      						2.The items with * must be provided.
						</p>
					</td>
				</tr>
				<tr>
					<td class="tagName">Staff Code:</td>
					<td class="tagCont">
						<input id="StaffNo" name="StaffNos"  type="text"  readonly="readonly" style="color:DarkGray;" title="Please state the different in “Remark”."  size="20" class="txt" />
    					<label class="inline checkbox">
    						<input type="checkbox" id="sf" name="sfs" value="N" />No Code
    					</label>
						<span style="color:#FF0000; font-family:'Arial Narrow'" id="message">Staff_NO不存在!</span>
					</td>
					<td class="tagName">Quantity:</td>
					<td class="tagCont">
						<select id="num" name="nums">
					        <option value="100">100</option>
					        <option value="200">200</option>
					        <option value="300">300</option>
					        <option value="400">400</option>
					    </select>
					    <span class="red">* </span>
					</td>
				</tr>
				<tr>
					<td class="tagName">English Name:</td>
					<td class="tagCont">
						<input name="EnglishNames" readonly="readonly" style="color:DarkGray;" title="Please state the different in “Remark”."  type="text" size="30" class="txt" id="EnglishName" />
      					<span class="red">*</span>
					</td>
					<td class="tagName">Chinese Name:</td>
					<td class="tagCont">
						<input id="ChineseName" name="ChineseNames" readonly="readonly" style="color:DarkGray;" title="Please state the different in “Remark”."  type="text" size="30" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">E-mail:</td>
					<td class="tagCont">
						<input id="Email" name="Emails" type="text" readonly="readonly" onBlur="vailEmail(this);" style="color:DarkGray;" title="Please state the different in “Remark”."  onKeyUp="this.value=this.value.toLowerCase();" size="30" class="txt" />
      					<span class="red" id="email_span">*</span>
					</td>
					<td class="tagName">Direct Line:</td>
					<td class="tagCont">
						<input id="DirectLine" name="DirectLines" readonly="readonly" style="color:DarkGray;" title="Please state the different in “Remark”."  type="text" size="30" class="txt" />
      					<span class="red">*</span>
					</td>
				</tr>
				<tr>
					<td class="tagName">Payer:</td>
					<td class="tagCont">
						<input id="payer" type="text" class="txt" name="payers"  size="30" style="color:DarkGray;" />
   						<span class="red">*</span>
					</td>
					<td class="tagName">TR Reg NO:</td>
					<td class="tagCont">
						<input id="TR_RegNo" name="TR_RegNos" type="text" readonly="readonly" size="30" class="txt"  title="Please state the different in “Remark”." />
					</td>
				</tr>
				<tr>
					<td class="tagName">CE NO:</td>
					<td class="tagCont">
						<input id="CENO" name="CENOs" type="text" size="30"  readonly="readonly" title="Please state the different in “Remark”."  class="txt" />
					</td>
					<td class="tagName">MPFA NO:</td>
					<td class="tagCont">
						<input id="MPFA_NO" name="MPFA_NOs" type="text"   readonly="readonly" title="Please state the different in “Remark”."  size="30" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">AE Consultant:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input type="checkbox" name="AEs" value="N" id="AE" disabled="disabled" onChange="javascript:if(AECode==false){return false;}" onClick="javascript:if(AECode==false){return false;}" />
    						YES 
						</label>
					</td>
					<td class="tagName">CIB Reg. No:</td>
					<td class="tagCont">
						<input id="HKCIB_NO" name="HKCIB_NOs" type="text"   readonly="readonly" title="Please state the different in “Remark”."  size="30" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Urgent Case:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input id="urgentCase" type="checkbox" name="urgent" value="N" />
    						YES 
						</label>
						<span style="font-size: 16px;">(At one's own expense)</span>
      					<input name="urgentDate" id="urgentDate" style='cursor:hand' type="text" readonly="readonly" title="輸入日期最好大於當前日期" onClick="Calendar('urgentDate');"/>
					</td>
					<td class="tagName">Elite Team:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input type="checkbox" name="ETs" value="N" id="ET" onChange="javascript:if(Elite==false){return false;}" onClick="javascript:if(Elite==false){return false;}" />
    						YES 
						</label>
					</td>
				</tr>
				<tr>
					<td class="tagName">Layout:</td>
					<td class="tagCont">
						<select name="types" id="type" >
        					<option value="S">Standard Layout</option>
						</select>
					</td>
					<td class="tagName">Location:</td>
					<td class="tagCont">
						<input type="text" id="location" readonly="readonly" name="locations" />
						<input type="text" id="locationflag" readonly="readonly" name="locationflag" />
        				<span class="red">*</span>
					</td>
				</tr>
				<tr>
					<td class="tagName">FAX:</td>
					<td class="tagCont">
						<input id="FAX" name="FAXs" type="text" size="30" class="txt" />
					</td>
					<td class="tagName">Mobile:</td>
					<td class="tagCont">
						<input id="Mobile" name="Mobiles" title=" Please separate the mobile numbers with “;”." type="text" size="30" class="txt" />
      					<span class="red">*</span>
					</td>
				</tr>
				<tr>
					<td class="tagName">Title (Eng):</td>
					<td class="tagCont">
						<select id="EnglishPosition" name="EnglishPositions">
					        <option value="">Please Select Position</option>
					    </select>
					    <span  class="red" >*</span>
					</td>
					<td class="tagName">Title (Chi):</td>
					<td class="tagCont">
						<input id="ChinesePosition" name="ChinesePositions" readonly="readonly" style="color:DarkGray;" title="Please state the different in “Remark”."  type="text" size="30"  class="txt" />
      					<span class="red">*</span>
					</td>
				</tr>
				<tr>
					<td class="tagName">Academic &amp; Professional Title (Eng):</td>
					<td class="tagCont" colspan="3">
						<input type="text" name="EnglishProfessionalTitles" class="txt" maxlength="255" id="EnglishProfession"  title=" Maximum title amount is 4 professional titles or 1 education title." size="120"/>
					    
					    <select id="EnglishProfessionalTitle" name="EnglishProfessionalTitle">
							<option value="">Please Select ProfessionalTitle</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tagName">Academic &amp; Professional Title (Chi):</td>
					<td class="tagCont" colspan="3">
						<input id="ChineseProfessionalTitle" name="ChineseProfessionalTitles" maxlength="255" type="text" size="120" class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">The attachment:</td>
					<td class="tagCont" colspan="3">
						<div id="upload" style="display:none;"> 
							<span style="color:red;font-size:14px;font-family: 'Arial Narrow';">
								Please upload the documents if there is any update on "professional title" and only one file is accepted when uploading.
							</span><br/>
      						<div id="upload_view" class="STYLE1">
        						<input type="file" name="filePaths" id="filePaths"/>
        						<input type="button" name="fileLoad" id="fileLoad" value="Upload" onClick="fileupload()"/>
        					</div>
      						<a id="downs" target="_Blank" style="font-size: 16px;"></a>&nbsp;&nbsp;&nbsp;&nbsp;
      						<a style="display:none;text-decoration: none;" id="quitFujian" href="javascript:void(0);" onClick="javascript:$(this).hide();fileName='';$('#downs').empty();$('#upload_view').show();">删除附件</a>
      					</div>
					</td>
				</tr>
					<tr>
							<td class="tagName">Company:</td>
							<td class="tagCont" colspan="3" id="companys"></td>
						</tr>
				
				
				<%--<tr>
					<td class="tagName">Company:</td>
					<td class="tagCont" colspan="3">
						<label class="inline checkbox">
							<input id="CC2" type="checkbox" name="CFS" value="Y" onClick="javascript:return false;" checked="checked" />
							CFS
						</label>
						<label class="inline checkbox">
							<input id="C2" type="checkbox" name="CAM" value="N" onClick="javascript:if($('#CENO').val()!=''){$(this).attr('checked','checked');}else{$(this).attr('checked','');}"/>
							  CAM
						</label>
						<label class="inline checkbox">
							 <input id="CCC2" type="checkbox" name="CIS" onClick="javascript:if($('#AE').attr('checked')){$(this).attr('checked','checked');}else{$(this).attr('checked','');}" value="N" />
							  CIS
						</label>
					    <input id="pay2" type="hidden" name="pay"/>
					</td>
				</tr>
				--%><tr>
					<td class="tagName">Remark:</td>
					<td class="tagCont" colspan="3">
						<textarea id="remarkcons" name ="remarkcons" rows="3" cols="74" style="width: 70%;"   onFocus="if (value=='Please state here if any other special request'){value ='';this.style.color='#000000'}" onBlur="if (value==''){value='Please state here if any other special request';this.style.color='#AAAAAA'}" style='color:#AAAAAA;'>Please state here if any other special request</textarea>
					</td>
				</tr>
				<tr>
				<td class="tagName" >
						<span id="agun_staffcode" style="font-size: 14px; font-weight: bold;"></span>
					</td>
					<td class="tagCont" colspan="3">
						<span id ="agun" style="font-size:14px; display: block; margin-top:5px; "> </span>
					</td>
				</tr>
				<tr>
					<td class="tagBtn" colspan="4">
					<input name="method" value="saveconvoyrequest" type="hidden" />
						<input id="company" name="company" type="hidden" />
						<button class="btn" id="AddNameCard"  name="Submit">Submit</button>
						<button class="btn" name="Submit" type="button" onClick="$('#StaffNo').blur();">Reset</button>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
var company=namecard_consultant_company.split(",");//CFS,CAM,CFSO
var comstring="";
for ( var i = 0; i < company.length; i++) {
//	comstring+="<label class='inline checkbox'><input "+(company[i]=="CIS"?"disabled='disabled'":"")+" type='checkbox' "+(company[i]=="CFS"?"checked='checked'":"")+" name='companys' value='"+company[i]+"' />"+company[i]+"</label>";
	comstring+="<label class='inline checkbox'><input "+(company[i]=="CIS"?"disabled='disabled'":"")+" disabled='disabled' type='checkbox' name='companys' value='"+company[i]+"' />"+company[i]+"</label>";
}
$("#companys").html(comstring);





/**
*获取选中的Company
*以"+"拼接
**/
function getCompany(){
	var com="";
	$("input[name='companys']:checked").each(function(n){
		if(n==0){
			com+=this.value;
		}else{
			com+="+"+this.value;
		}
	});
	return com;
	
}



</script>
</body>
</html>
