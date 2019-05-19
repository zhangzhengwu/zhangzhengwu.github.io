<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
if(session.getAttribute("convoy_username")==null || session.getAttribute("convoy_username")==""){
	out.println("<script>parent.location.href='../../error.jsp';</script>");
}
%>
	<head>
		    <base href="<%=basePath%>"></base>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<script type="text/javascript" src="css/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="./plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<script language="javascript" type="text/javascript" src="css/date.js"></script>
	<script language="javascript" type="text/javascript" src="css/Util.js"></script>
		<script type="text/javascript" src="./css/ajaxfileupload.js"></script> 
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css"> 
	<title>Information input</title>

<script type="text/javascript">
/**
 * 只读文本框禁用退格键
 * 
 * */
function BackSpace(e){
	if(window.event.keyCode==8){//屏蔽退格键
	    var type=window.event.srcElement.type;//获取触发事件的对象类型
	  //var tagName=window.event.srcElement.tagName;
	 	var reflag=window.event.srcElement.readOnly;//获取触发事件的对象是否只读
	  	var disflag=window.event.srcElement.disabled;//获取触发事件的对象是否可用
		  if(type=="text"||type=="textarea"){//触发该事件的对象是文本框或者文本域
			   if(reflag||disflag){//只读或者不可用
			    //window.event.stopPropagation();
			    window.event.returnValue=false;//阻止浏览器默认动作的执行
			   }
		  }else{ 
		  	 window.event.returnValue=false;//阻止浏览器默认动作的执行
		  }
 }
}
/*窗体加载事件 */
$(function(){
		document.onkeydown=BackSpace;
	getlocation();
/**
 * 加载Position
 * */
function position(){
	$.ajax({
		type: "post",
		url: "QueryStaffPositionServlet",
		data: null,
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
/**
 * 加载Location
 * */
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
				html+="<option value='' >Please Select Location</option>";
				for(var i=0;i<location.length;i++){
					html+="<option value='"+location[i].realName+"' >"+location[i].name+"</option>";
				}
			}else{
				html+="<option value=''>loading error</option>";
			}
			$("#location").append(html);
			$("#location").val("Y");//默认选择@Convoy
			},error:function(){
				alert("Network connection failure, please return to the login page to log in!");
				return false;
			}
		});
	}
/**
 * 加载department
 * */
function department(){
	$.ajax({
		type: "post",
		//url: "QueryStaffProfessionalServlet",
		//data: null,
		url: "<%=basePath%>common/CommonReaderServlet",
		data:{"method":"getstaffprofessional"},
		success: function(date){
		var d=eval(date);
		var html;
		if(d.length>0){
			for(var i=0;i<d.length;i++){
			html+="<option value='"+d[i].prof_title_ename+"'>"+d[i].prof_title_ename+"</option>";
			}
			$("#EnglishStaffDepartment:last").append(html);
			}
		}
	});
}
/**
 * 加载professional
 * */
function professional(){
	$.ajax({
		type: "post",
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
/**********************************窗體加載************************************************/
position();
professional();
department();
/******************************************************************************************/
var professionalCText="";
var professionalEText="";
var departmentCText="";
var departmentEText="";
$("#urgentCase").val("N").removeAttr("checked");	
var pay=false;
/*****************************************************/
$("#EnglishStaffDepartment").hide();
$("#EnglishProfessionalTitle").hide();
$("#urgentDate").empty;								//清空日期控件里面的残余值
$("#urgentDate").hide();	
$("#urgentCase").change(function(){
	if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
		$(this).val("Y")//$("#num").val("100");
	}else{
		$(this).val("N")//$("#num").val("100");
	}
});

/*****************************************************/
$("#EnglishStaffDepartment").change(function(){
	$.ajax({
		type:"post",
		url:"SelectStaffProfessionalServlet",
		data:{'EnglishProfessionalTitle':$("#EnglishStaffDepartment").val()},
		success :function(date){
			if(date!="[]" || date!="null" || date!=null){
				$("#ChineseStaffDepartmentText").val(date);
				$("#EnglishStaffDepartmentText").val( $("#EnglishStaffDepartment").val());
		 	}
		},error:function(){
			alert("Network connection is failed, please try later...");
			return false;
		}
	});
});
 
$("#EnglishProfessionalTitle").change(function(){
		if($("#EnglishProfessionalTitleText").val().indexOf($(this).val())>=0){
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
								professionalEText = $("#EnglishProfessionalTitleText").val()+($("#EnglishProfessionalTitleText").val()==""?"":";")+ $("#EnglishProfessionalTitle").val();
								professionalCText = $("#ChineseProfessionalTitle").val()+($("#ChineseProfessionalTitle").val()==""?date:((date==""?"":";")+date));
								$("#ChineseProfessionalTitle").val(professionalCText);
								$("#EnglishProfessionalTitleText").val(professionalEText).change();
					}else{
						professionalEText = $("#EnglishProfessionalTitleText").val()+($("#EnglishProfessionalTitleText").val()==""?"":";")+ $("#EnglishProfessionalTitle").val();
						$("#EnglishProfession").val(professionalEText).change();
					}
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
			});
		}
});
function payers(){
		$.ajax({ 
			type: "post", 
			url: "PayerServlet", 
			data: {'payer':$("#payer").val()}, 
			success: function(date){
			if(date=="" || date==null){
				pay=false;
			}else{
				$("#pay").val(date);
				pay=true;
			}
		 }
	 });
}
/************************************验证payer是否存在*************************/
$("#payer").blur(function (){
	payers();
});
$("#EnglishProfessionalTitleText").click(function (){
	$("#EnglishProfessionalTitle").show();
});
$("#EnglishStaffDepartmentText").click(function (){
	$("#EnglishStaffDepartment").show();
});
 
/****************************************pater验证结束***********************/
$("#EnglishPosition").change(function(){
	$.ajax({
		type:"post",
		url:"SelectStaffPositionServlet",
		data:{'EnglishName':$("#EnglishPosition").val().replace('\r\n','').replace('\r\n','').replace('\r\n','')},
		success:function (date){
			if(date!="" || date!="null" || date)
				$("#ChinesePosition").val(date);
				if($("#ChinesePosition").val()=="null" || $("#ChinesePosition").val()==null){
					$("#ChinesePosition").val("");
			 }
		}
	});
});

$("#EnglishProfessionalTitleText").change(function(){
	if($(this).val()=="")	
		$("#upload_view,#upload").hide();
	else
		$("#upload_view,#upload").show();
});

//CE NO blur事件
$("#CENO").blur(function(){
	if($("#CENO").val()=="")
		$("#C").attr("checked","").val("N");
	else
		$("#C").attr("checked","checked").val("Y");
});
/****************************************************StaffNo 失去焦点时触发的时间**************,QueryStaffProfessionalServlet**************************************/
	$("#StaffNo").blur(function(){
		if($("#StaffNo").val().length>=5){
			$.ajax({ 
				type: "post", 
				url: "StaffEditServlet",
				data: {'StaffNo':$("#StaffNo").val()}, 
				success: function(date){
					 if(date!=null &&date!="[null]"){
						 	$("#message").empty().append("*");
							var master=eval(date);
							$("#ChineseName").val(master[0].c_Name);
							$("#EnglishName").val(master[0].name); 
							$("#TR_RegNo").val(master[0].tr_RegNo);
							$("#CENO").val(master[0].ce_No).blur();
							$("#MPFA_NO").val(master[0].MPF_No);
							$("#Mobile").val(master[0].mobilePhone);
							$("#FAX").val(master[0].fax);
							$("#DirectLine").val(master[0].directLine);
							$("#Email").val(master[0].email.toLowerCase());
							$("#ChineseExternalTitle").val(master[0].c_ExternalTitle_Department);
							$("#EnglishExternalTitleText").val(master[0].e_ExternalTitle_Department);
							$("#EnglishProfessionalTitleText").val(master[0].e_EducationTitle).change();
							$("#ChineseProfessionalTitle").val(master[0].c_EducationTitle);
							$("#EnglishStaffDepartmentText").val($.trim(master[0].e_Title_Department.substring(master[0].e_Title_Department.indexOf(";")+1)))
							$("#ChineseStaffDepartmentText").val($.trim(master[0].c_Title_Department.substring(master[0].c_Title_Department.indexOf(";")+1)))
							fujianlist();
							$("#EnglishPosition").focus();
						}else{
							$("#message").empty().append("* Staff Code does not exist!");
						} 
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				} 
		  });
		 }else {
			 if($("#StaffNo").val().length !=0){
				alert("The staff code is wrong.");
				$(".txt").val("");
				$("#StaffNo").focus();
				return false;
			 }
		}
});


/**
 * Elite Team change事件
 * 
 * */
$("#ET,#num,#numId").change(function(){
	if($("#ET").attr("checked")==true){
		$("#ET").val("Y");
		$("#num,#numId").val(100);
	}else{
		$("#ET").val("N");
	}
});
	$("[type='checkbox'][name!='companys']").click(function(){
		if($(this).attr("checked")){
			$(this).val("Y");
		}else{
			$(this).val("N");
		}
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
 
function vailed(){
	var nums=$("#num").val();                                                      
	if($("#StaffNo").val()==""){																 
		error(" Please input the Staff Code.");	
		$("#StaffNo").focus();
		return false;																			 
	}																							 
	if($("#num").val()==""){															 
		error("Please choose 100,200,300 or 400 for “Quantity”.");	
			$("#num").focus();
		return false;																				 
	}	 																				 
	if(isNaN(nums)){		
		error("Please choose 100,200,300 or 400 for “Quantity”.");	
		$("#num").focus();
		return false;															 
	}else{
			if($("#num").val()%100 !=0){
			error("Please choose 100,200,300 or 400 for “Quantity”.");
			$("#num").focus();
			return false;
		}	
	}	
	
	if($("#location").val()==""){
		error("Please choose Location!");
		$("#location").focus();
		return false;
	}else if($("#EnglishName").val()==""){
		error("Please input EnglishName!");
		$("#EnglishName").focus();
		return false;
	}else if($("#ChineseName").val()==""){
		error("Please input ChineseName!");
		$("#ChineseName").focus();
		return false;
	}else if($("#Email").val()==""){
		error("Please input Email!");
		$("#Email").focus();
		return false;
	}else if(!vailcompany()){
		error("Please choose Company!");
		return false;
	}
	


/**************************判断事件是否合法*****************/
var datetime=(new Date()).getFullYear()+"-"+((new Date()).getMonth()+1)+"-"+(new Date()).getDate()
/**********************************************************/
/*****************判断复选框是否被选中**********************/
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
				//changeDate();
				$("#urgentCase").val("Y");
				if(parseInt($("#num").val())<100 && parseInt($("#num").val())>0){
					error("Please choose 100,200,300 or 400 for “Quantity”.");
					$("#num").focus();
					return false;
				}
		}
		//貌似trim()方法不支持
		if(parseInt($("#num").val())<0){
			error(" Please choose 100,200,300 or 400 for “Quantity”."); 
				return false;
		}
	 	if($("#EnglishProfessionalTitleText").val().split(";").length-1>3){
			error('Only less than 4 professional titles are allowed for “English Professional Title”.');
			return false;
		}
		if($("#EnglishPosition").val()=="positionName" || $("#EnglishPosition").val()==null || $("#EnglishPosition").val()==""){
			error("Please Choose Position in English !");
			$("#EnglishPosition").focus();
			return false;
			
		}else{
			var positiontext = $("#EnglishPosition").find("option:selected").text();
			$("#EnglishPositions").val(positiontext);
		}
		 if($("#EnglishStaffDepartmentText").val()==""){
 			 error("Please Choose Department in English !");
			 $("#EnglishStaffDepartmentText").focus();
			 return false;
		 }
		$("#AddNameCard").attr("disabled","disabled");

	if(fileName!=""){
		$.ajax({
			url:"SaveUploadFileServlet",
			type:"post",
			async:false,
			data:{"staffcode":$("#StaffNo").val(),"profession":$("#EnglishProfessionalTitleText").val(),"filename":fileName},
			success:function(date){
					if(date=="success"){
						if(confirm("Sure to Submit?")){
							$("#myform").submit();
						}else{
							$("#AddNameCard").attr("disabled","");
							return false;
						}
					}else{
						errorAlert("The upload is failed.");
						return false;
					}
				},error:function(){
					$("#AddNameCard").attr("disabled","");
					errorAlert("Network connection is failed, please try later...");
					return false;
				}
		});
	}else{
		if($("#EnglishProfessionalTitleText").val()!=""){
			if(confirm(" Please make sure all documents have been provided for “Professional Title”,Please confirm if submitted.")){
				$("#myform").submit();	
			}else{
				$("#AddNameCard").attr("disabled","");
			}
		}else{
			if(confirm("Sure to Submit?")){
				$("#myform").submit();
			}else{
				$("#AddNameCard").attr("disabled","");
				return false;
			}
		}
	}
}
});

 function vailcompany(){
	 var f=false;
	 	$(".company").each(function(){
			if($(this).attr("checked")){
				f= true;
			}
		});
	 	return f;
 }
</script>
  <script type="text/javascript">
   var fileType="";
   var fileName="";
   var oldfileName="";
       var rightFileType= new Array("jpg", "bmp", "gif", "png","jpeg");  
           function fileupload(){
               if($("#filePaths").val()==""){  
                   return false;  
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
                       },error: function (data, status, e){  
                           alert("fail");  
                       }  
                   });  
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
</script>
</head>
  
<body onload="$('#StaffNo').blur();">
<div class="cont-info">
	<div class="info-title">
		<div class="title-info">
			<form action="AddHRRequestConvoyServlet" id="myform" method="post">
			<table>
				<tr>
					<td class="tagName">Staff Code:</td>
					<td class="tagCont">
						<input id="StaffNo" name="StaffNos" value="${convoy_username}" onkeyup="this.value=this.value.toUpperCase();"  type="text" size="48" class="txt" />
						<span class="red" id="message">*</span>
					</td>
					<td class="tagName">Quantity:</td>
					<td class="tagCont">
						<select id="num" name="nums" >
					        <option value="100">100</option>
					        <option value="200">200</option>
					        <option value="300">300</option>
					        <option value="400">400</option>
			        	</select> 
			        	<span class="red">*</span>
					</td>
				</tr>
				<tr>
					<td class="tagName">Location:</td>
					<td class="tagCont">
						<select name="locatins" id="location">
						    <option value="O">OIE</option>
						    <option value="C">CP3</option>
						    <option value="W">CWC</option>
					    </select>
					    <span class="red">*</span>
					</td>
					<td class="tagName">Layout:</td>
					<td class="tagCont">
						<select name="types" id="type">
        					<option value="S">Standard Layout</option>
        				</select>
        				<span class="red">*</span>
					</td>
				</tr>
				<tr>
					<td class="tagName">New Name Card:</td>
					<td class="tagCont">
						<label class="inline radio">
							<input name="ETs" type="radio" value="Y" checked="checked"/>YES
						</label>
						<label class="inline radio">
							<input name="ETs" type="radio" value="N"/>
							NO
						</label>
					</td>
					<td class="tagName">Urgent Case:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input id="urgentCase" type="checkbox" name="urgent" value="N" />YES	
						</label>  
        				<input name="urgentDate" id="urgentDate"   type="text" readonly="readonly" title="輸入日期最好大於當前日期" onclick="return Calendar('urgentDate');"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">English Name:</td>
					<td class="tagCont">
						<input name="EnglishNames" type="text" size="48" class="txt" id="EnglishName" />
	       				<span class="red">*</span> 
					</td>
					<td class="tagName">Chinese Name:</td>
					<td class="tagCont">
						<input id="ChineseName" name="ChineseNames" type="text" size="48" class="txt" />
     					<span class="red">*</span> 
					</td>
				</tr>
				<tr>
					<td class="tagName">Position (Eng):</td>
					<td class="tagCont">
						<select id="EnglishPosition" >
	      					<option value="">Please Select Position</option>
	      				</select>  
					</td>
					<td class="tagName">Position (Chi):</td>
					<td class="tagCont">
						<input id="ChinesePosition" name="ChinesePositions" type="text" size="48"  class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Department (Eng):</td>
					<td class="tagCont">
						<input id="EnglishStaffDepartmentText" name="EnglishStaffDepartmentTexts" type="text" size="48"  class="txt"/>
					    <select id="EnglishStaffDepartment" name="EnglishStaffDepartments">
					        <option value="">Please Select Department</option>
					    </select>
					</td>
					<td class="tagName">Department (Chi):</td>
					<td class="tagCont">
						<input id="ChineseStaffDepartmentText" name="ChineseStaffDepartmentTexts" type="text" size="48"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Academic Title (Eng):</td>
					<td class="tagCont">
						<input id="EnglishAcademicTitle" name="EnglishAcademicTitles" type="text" size="48"  class="txt"/>
					</td>
					<td class="tagName">Academic Title (Chi):</td>
					<td class="tagCont">
						<input id="ChineseAcademicTitle" name="ChineseAcademicTitles" type="text" size="48"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Professional Title (Eng):</td>
					<td class="tagCont">
						<input id="EnglishProfessionalTitleText" name="EnglishProfessionalTitles" type="text" size="48"  class="txt"/>
					</td>
					<td class="tagName">Professional Title (Chi):</td>
					<td class="tagCont">
						<input id="ChineseProfessionalTitle" name="ChineseProfessionalTitles" type="text" size="48"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">External Title (Eng):</td>
					<td class="tagCont">
						<input type="text" class="txt" size="48" name="englishExternal" />
					</td>
					<td class="tagName">External Title (Chi):</td>
					<td class="tagCont">
						<input type="text" class="txt" name="chineseExternal" size="48" />
					</td>
				</tr>
				<tr>
					<td class="tagName">TR Reg NO:</td>
					<td class="tagCont">
						<input id="TR_RegNo" name="TR_RegNos" type="text" size="48" class="txt" />
      
					</td>
					<td class="tagName">CE NO:</td>
					<td class="tagCont">
						<input id="CENO" name="CENOs" type="text" size="48" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">MPFA NO:</td>
					<td class="tagCont">
						<input id="MPFA_NO" name="MPFA_NOs" type="text" size="48" class="txt" />
					</td>
					<td class="tagName">Mobile:</td>
					<td class="tagCont">
						<input id="Mobile" name="Mobiles" type="text" size="48" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">FAX:</td>
					<td class="tagCont">
						<input id="FAX" name="FAXs" type="text" size="48" class="txt" />
					</td>
					<td class="tagName">Direct Line:</td>
					<td class="tagCont">
						<input id="DirectLine" name="DirectLines" type="text" size="48" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">E-Mail:</td>
					<td class="tagCont" colspan="3">
						<input id="Email" name="Emails" type="text" onkeyup="this.value=this.value.toLowerCase();" size="48" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Company:</td>
					<td class="tagCont" colspan="3">
						<label class="inline checkbox">
							<input id="CC" type="checkbox" name="CFS" value="N" class="company"/>CFS
						</label>
						<label class="inline checkbox">
							<input id="C" type="checkbox" name="CAM" value="N"  class="company"/>CAM
						</label>
						<label class="inline checkbox">
							<input id="CCC" type="checkbox" name="CIS" value="N"  class="company"/>CIS
						</label>
						<label class="inline checkbox">
							<input id="C2" type="checkbox" name="CCL" value="N"  class="company"/>CCL
						</label>
						<label class="inline checkbox">
							<input id="C3" type="checkbox" name="CFSH" value="N"  class="company"/>CFSH
						</label>
						<label class="inline checkbox">
							<input id="C4" type="checkbox" name="CMS" value="N"  class="company"/>CMS
						</label>
						<label class="inline checkbox">
							<input id="C22" type="checkbox" name="CFG" value="N"  class="company"/>CFG
						</label>
						<label class="inline checkbox">
							<input  id="C24" type="checkbox" name="CCIA" value="N"  class="company"/>CCIA
						</label>
						<label class="inline checkbox">
							<input  id="C25" type="checkbox" name="CCFSH" value="N"  class="company"/>CCFSH
						</label>
						<label class="inline checkbox">
							<input  id="C26" type="checkbox" name="CWMC" value="N"  class="company"/>CWMC
						</label>
				        <input id="pay" type="hidden" name="pays"/>
				        <input id="EnglishPositions" type="hidden" name="EnglishPositions"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">The attachment:</td>
					<td class="tagCont" colspan="3">
						<div id="upload" style="display: none;">
							<span style="color:red;font-size:14px;font-family: 'Arial Narrow';">
    						Please upload the documents if there is any update on "professional title" and only one file is accepted when uploading.
    						</span><br/>
			        		<div id="upload_view" class="STYLE1">
					            <input type="file" name="filePaths" id="filePaths"/>
					            <input type="button" name="fileLoad" id="fileLoad" value="Upload" onclick="fileupload()"/>
				            </div>
          					<a id="downs" target="_Blank" style="font-size: 16px;"></a>
          					<a style="display:none;text-decoration: none;" id="quitFujian" href="javascript:void(0);" onclick="javascript:$(this).hide();fileName='';$('#downs').empty();$('#upload_view').show();">删除附件</a>
          				</div>
					</td>
				</tr>
				<tr>
					<td class="tagName">Remarks:</td>
					<td class="tagCont" colspan="3">
						<textarea name="remarks" id="remarks" cols="74" rows="3" style="width: 70%;" onfocus="if (value=='Please state your special request in this box'){value ='';this.style.color='#000000'}" onblur="if (value==''){value='Please state your special request in this box';this.style.color='#AAAAAA'}" style='color:#AAAAAA;'>Please state your special request in this box</textarea>
					</td>
				</tr>
				<tr>
					<td class="tagBtn" colspan="4">
						<button class="btn" id="AddNameCard" name="Submit">Submit</button>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>