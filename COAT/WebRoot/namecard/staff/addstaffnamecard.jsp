 <%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
if(session.getAttribute("convoy_username")==null && session.getAttribute("convoy_username")==""){
	out.println("<script>parent.location.href='../../error.jsp';</script>");
}
%>
	<head>
		    <base href="<%=basePath%>"></base>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<script type="text/javascript" src="css/jquery-1.4.2.min.js"></script>
	<script language="javascript" type="text/javascript" src="css/Util.js"></script>
	<script language="javascript" type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
	<script language="javascript" type="text/javascript" src="plug/js/Common.js"></script>
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

/** 
 * 替换字符串中所有 
 * @param obj   原字符串 
 * @param str1  替换规则 
 * @param str2  替换成什么 
 * @return  替换后的字符串 
 */  
function replaceAll(obj,str1,str2){         
      var result  = obj.replace(eval("/"+str1+"/gi"),str2);        
      return result;  
}  
/*窗体加载事件 */
$(function(){
		document.onkeydown=BackSpace;


var professionalCText="";
var professionalEText="";
var departmentCText="";
var departmentEText="";
$("#urgentCase").val("N").removeAttr("checked");	
var pay=false;
/*****************************************************/
$("#EnglishStaffDepartment").hide();
$("#EnglishProfessionalTitle").hide();
$("#message").hide();
//$("#urgentDate").empty;								//清空日期控件里面的残余值
//$("#urgentDate").hide();	
//$("#StaffNo").val("${convoy_username}");
$("#urgentCase").change(function(){
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
			$(this).val("Y");//$("#num").val("100");
			}else{
				$(this).val("N");//$("#num").val("100");
			}
});
	
$("#remark").blur(function(){
	this.value=replaceAll(this.value,"'","‘");
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

 

function equals(str1, str2)    
{    
    if(str1 == str2)    
    {    
        return true;    
    }    
    return false;    
}    
function companycheck(COMPANY_CODE){
//list =COMPANY_CODE.split(",");
   var arr = new Array("CFS","CAM","CIS","CCL","CFSH","CMS","CFG","CCIA","CCFSH","CWMC");
   $("input[class='check_company']").attr("checked","").val("N");
   for ( var i = 0; i < arr.length; i++) {
	   if(equals(COMPANY_CODE,arr[i])){
	    	$("input[name='"+COMPANY_CODE+"']").attr("checked",true).val("Y");
	    	break;
	   }
    }

}
$("#CC").click(function(){return false;});

/****************************************************StaffNo 失去焦点时触发的时间**************,QueryStaffProfessionalServlet**************************************/
	$("#StaffNo").blur(function(){
		if($("#StaffNo").val().length>=5){
				$.ajax({ 
				type: "post", 
				url: "StaffNameCardReaderServlet",
				data: {"method":"staffrequestconvoyselect",'StaffNo':$("#StaffNo").val()}, 
				beforeSend: function(){
					parent.showLoad();
				},
				complete:function(){
					parent.closeLoad();
				},
				success: function(date){
					 if(date!=null && date!="null" && date!="[null]" && date!="[{}]"){
						//$("#message").hide();
						var master=eval(date);
						//js 解析JSONArray用var r=eval(date); 解析的结果通常为 r[0].xx;
						//js 解析JSONObject 用var r=$.JSONObject(date); r.xx;
				        $("#num").val("100");
				        $("#locationflag").val(master[0].LOCATION_DESC);
					        
					        //17/F, Cubus, 1 Hoi Ping Road, Causeway Bay, HK
					        if($("#locationflag").val().indexOf("17/F, Cubus, 1 Hoi Ping Road, Causeway Bay") > -1){
					        	$("#location").val("17/F, Cubus, 1 Hoi Ping Road, Causeway Bay, HK");
					        }else if($("#locationflag").val().indexOf("Nan Fung") > -1){
					        	$("#location").val("Nan Fung");
					        }else if($("#locationflag").val().indexOf("Cityplaza 3") > -1){
					        	$("#location").val("CP3");
					        }else{
					        	$("#location").val("@CONVOY");
					        }
					        
				        
				        $("#locationchi").val(master[0].LOCATION_CHI_DESC);
				        
				        $("#type").val("S");
						$("#EnglishName").val(master[0].FULL_NAME); 
						$("#ChineseName").val(master[0].CHINESE_NAME);
						$("#EnglishPosition").val(master[0].Position_Eng);
						$("#ChinesePosition").val(master[0].Position_Chi);
	     			   	$("#EnglishStaffDepartmentText").val(master[0].Department_Eng);
						$("#ChineseStaffDepartmentText").val(master[0].Department_CHI);
						$("#englishExternal").val(master[0].EXTERNAL_POSITION_ENG_DESC);
						$("#chineseExternal").val(master[0].EXTERNAL_POSITION_CHI_DESC);
												
						$("#TR_RegNo").val(master[0].TR_REG_NO);
						$("#CENO").val(master[0].CE_NO);
						$("#MPFA_NO").val(master[0].MPFA_NO);
						//$("#Mobile").val(master[0].MOBILE);
						//2016-05-27 10:57:53 When users click the name card request screen, the mobile no change to show blank value, and allow users to input when in need
						$("#Mobile").val("");
						$("#FAX").val(master[0].FAX_NO);
						$("#DirectLine").val(master[0].DIRECT_LINE);
						//alert(master[0].DEPARTMENT_CODE);
						//companycheck(master[0].COMPANY_CODE);
						
						
						//company_code=master[0].COMPANY_CODE;
						
						var deparment_code=master[0].DEPARTMENT_CODE;
						var html="";
						
						var specialDepartment = "FAD,ITD,HRD,LCD,CCD,ADM,CMD,DTD";
						var specialStaff = "AW6628,CC0107,IL1910,LC0103,AC0176,CC0199,IY2406,DC0141";
						
						if(specialDepartment.indexOf(deparment_code)>-1 || specialStaff.indexOf($("#StaffNo").val())>-1){
						   html="<input class='check_company' type='checkbox' name='CXX' checked='checked' readonly='readonly' />Convoy Financial Group";
						}else{
						   html="<input class='check_company' type='checkbox' name='"+master[0].COMPANY_CODE+"' checked='checked' readonly='readonly' />"+master[0].ENGLISH_NAME+"";
						}
						$("#CC").empty().append(html);
						
						
						$("#Email").val(master[0].EMAIL_ADDRESS.toLowerCase());
						 fujianlist();
						$("#EnglishPosition").focus();
						$("#AddNameCard").attr("disabled","");
					 }else if(date=="exception"){
						 	error("数据获取异常!");
							$(".txt").val("").html("");
							$("#StaffNo").val("").focus();
							return false;
					 }else if(date=="timeout"){
						 location.reload();
					 }else{
						//$("#message").show(200);
						error("Staff Code不存在!");
						$(".txt").val("").html("");
						$("#StaffNo").val("").focus();
						return false;
					} 
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				} 
			});
	 }else{
	 	error("Please input the Staff Code. ");	
	 	$(".txt").val("").html("");
	 	$("#StaffNo").val("").focus();
	 	
	 	return false;
	 }
});
	$("#StaffNo").blur();
	
	
	
	
	
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
	

$("#AddNameCard").click(function(){ 
	vailed();
	return false;
});	
	 
function vailed(){
                                                    
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

	if($("#location").val()==""){
		error("Please choose Location!");
		$("#location").focus();
		return false;
	}
    if($("#EnglishName").val()==""){
		error("Please input EnglishName!");
		$("#EnglishName").focus();
		return false;
	}
    if($("#ChineseName").val()==""){
		error("Please input ChineseName!");
		$("#ChineseName").focus();
		return false;
	}
    if(!vailcompany()){
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
		/**
		 if($("#EnglishStaffDepartmentText").val()==""){
 			 error("Please Choose Department in English !");
			 $("#EnglishStaffDepartmentText").focus();
			 return false;
		 }**/
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
							submitForm();
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
		});return false;
	}else{
		if($("#EnglishProfessionalTitleText").val()!=""){
			if(confirm(" Please make sure all documents have been provided for “Professional Title”,Please confirm if submitted.")){
				submitForm();
			}else{
				$("#AddNameCard").attr("disabled","");
			}
		}else{
			if(confirm("Sure to Submit?")){
				submitForm();
			}else{
				$("#AddNameCard").attr("disabled","");
				return false;
			}
		}
	}
}
	 	
});
function submitForm(){
	var company_Type="";
	$("input[class='check_company']").each(function(){
		if($(this).attr("checked")){
			//alert(this.name);
			if(company_Type==""){
				company_Type+=this.name;
			}else{
				company_Type+="+"+this.name;
			}
		}
		
	});
	$("#company_Type").val(company_Type);
	
	$.ajax({
		//url:"AddStaffRequestConvoyServlet",
		url:"StaffNameCardWriteServlet",
		type:"post",
		data:$('#myform').serialize(), 
		beforeSend: function(){
			parent.showLoad();
		},
		complete:function(){
			parent.closeLoad();
			$("#AddNameCard").attr("disabled","");
		},
		success:function(date){
			var result=$.parseJSON(date);
			if(result.state=="success"){
				successAlert("Successfully Submitted!",function(){
					location.reload();
				});
			}else if(result.state=="error"){
				errorAlert("Save error!");
			}else{
				errorAlert(result.msg);
			}
				/**if(date == "1"){
					alert('We will notify you by email to collect the name card in 7 working days. \r\n  Cut-off date: Every Friday at 12:00noon!');
					location.reload();
				}else{
					alert('Email Send Error!');
				}**/
		},error:function(){
			errorAlert("Network connection is failed, please try later...");
			return false;
		}
	});
	return false;
}

 function vailcompany(){
	 var f=false;
	 	$(".check_company").each(function(){
			if($(this).attr("checked")){
				f= true;
			}
		});
	 	return f;
 }

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
                   }  
               );  
            return false;
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
  
<body >

<div class="cont-info">
	<div class="info-title">
		<div class="title-info">
			<form action="" id="myform" method="post">
			<table>
				<tr>
					<td class="tagName">Staff Code:</td>
					<td class="tagCont">
						<input id="StaffNo" name="StaffNo" readonly="readonly"   value="${convoy_username}" onkeyup="this.value=this.value.toUpperCase();"  type="text" size="48" class="txt" />
						<span class="red">*</span>        
						<span style="color:#FF0000;" id="message">Staff Code不存在!</span>
					</td>
					
					<td class="tagName">Quantity:</td>
					<td class="tagCont">
						<select id="num" name="num" >
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
						<input type="text"  readonly="readonly" class="txt" id="location" name="locatin" />
						<input name="flag" readonly="readonly" style="width: 200px;" type="text" class="txt" id="locationflag" />
						<input type="hidden" id="locationchi"/>
						<span class="red">*</span> 
					</td>
					<td class="tagName">Layout:</td>
					<td class="tagCont">
						<select name="type" id="type">
        					<option value="S">Standard Layout</option>
        				</select>
        				<span class="red">*</span>
					</td>				
				</tr>
				<tr>
					<td class="tagName">New Name Card:</td>
					<td class="tagCont">
						<label class="inline radio">
							<input name="ET" type="radio" value="Y" checked="checked"/>
							YES
						</label>
						<label class="inline radio">
							<input name="ET" type="radio" value="N"/>
							NO
						</label>
					</td>
					<td class="tagName">Urgent Case:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input id="urgentCase" type="checkbox"  name="urgentCase"  value="N" />
							YES
						</label>
        				<%--<input name="urgentDate" id="urgentDate"   type="text" readonly="readonly" title="輸入日期最好大於當前日期" onclick="return Calendar('urgentDate');"/>
					--%></td>
				</tr>
				<tr>
					<td class="tagName">English Name:</td>
					<td class="tagCont">
						<input name="EnglishName" type="text" readonly="readonly" size="48" class="txt" id="EnglishName" />
						<span class="red">*</span> 
      
					</td>
					<td class="tagName">Chinese Name:</td>
					<td class="tagCont">
						<input id="ChineseName" name="ChineseName" readonly="readonly" type="text" size="48" class="txt" />
     					<span class="red">*</span>
					</td>
				</tr>
				<tr>
					<td class="tagName">Position (Eng):</td>
					<td class="tagCont">
						<input id="EnglishPosition" name="EnglishPosition" type="text" readonly="readonly" size="48" class="txt" />
					</td>

					<td class="tagName">Position (Chi):</td>
					<td class="tagCont">
						<input id="ChinesePosition" name="ChinesePosition" readonly="readonly" type="text" size="48"  class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Department (Eng):</td>
					<td class="tagCont">
      					<input id="EnglishStaffDepartmentText" readonly="readonly" name="EnglishStaffDepartmentText" type="text" size="48"  class="txt"/>
					</td>
					<td class="tagName">Department (Chi):</td>
					<td class="tagCont">
						<input id="ChineseStaffDepartmentText" readonly="readonly" name="ChineseStaffDepartmentText" type="text" size="48"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Academic Title (Eng):</td>
					<td class="tagCont">
						<input id="EnglishAcademicTitle" name="EnglishAcademicTitle" type="text" size="48"  class="txt"/>
					</td>
					<td class="tagName">Academic Title (Chi):</td>
					<td class="tagCont">
						<input id="ChineseAcademicTitle" name="ChineseAcademicTitle" type="text" size="48"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Professional Title (Eng):</td>
					<td class="tagCont">
						<input id="EnglishProfessionalTitleText" name="EnglishProfessionalTitle" type="text" size="48"  class="txt"/>
					</td>
					<td class="tagName">Professional Title (Chi):</td>
					<td class="tagCont">
						<input id="ChineseProfessionalTitle" name="ChineseProfessionalTitle" type="text" size="48"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">External Title (Eng):</td>
					<td class="tagCont">
						<input type="text" class="txt" size="48" readonly="readonly" name="englishExternal" id="englishExternal" />
					</td>
					<td class="tagName">External Title (Chi):</td>
					<td class="tagCont">
						<input type="text" class="txt" readonly="readonly" name="chineseExternal" size="48" id="chineseExternal" />
					</td>
				</tr>
				<tr>
					<td class="tagName"></td>
					<td class="tagCont" colspan="3">
						<span>Please alert the external title will be printed on your name card.</span>
					</td>
				 
				</tr>
				<tr>
					<td class="tagName">TR Reg NO:</td>
					<td class="tagCont">
						<input id="TR_RegNo" name="TR_RegNo" readonly="readonly" type="text" size="48" class="txt" />
					</td>
					<td class="tagName">CE NO:</td>
					<td class="tagCont">
						<input id="CENO" name="CENO" readonly="readonly" type="text" size="48" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">MPFA NO:</td>
					<td class="tagCont">
						<input id="MPFA_NO" name="MPFA_NO" readonly="readonly" type="text" size="48" class="txt" />
					</td>
					<td class="tagName">Mobile:</td>
					<td class="tagCont">
						<input id="Mobile" name="Mobile" type="text" size="48" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">FAX:</td>
					<td class="tagCont">
						<input id="FAX" name="FAX" type="text" size="48" class="txt" />
					</td>
					<td class="tagName">Direct Line:</td>
					<td class="tagCont">
						<input id="DirectLine" name="DirectLine" readonly="readonly" type="text" size="48" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">E-Mail:</td>
					<td class="tagCont" colspan="3">
						<input id="Email" name="Email" type="text" readonly="readonly" onkeyup="this.value=this.value.toLowerCase();" size="48" class="txt" />
					</td>
				</tr>
				<tr >
					<td class="tagName">Company:</td>
					<td class="tagCont" colspan="3" >
					
						<label class="inline checkbox" id="CC">
							
						</label>
					
					 	<%--<c:forEach items="${getCompany}" var="co" >
					 		<c:if test="">
						      	<label class="inline checkbox">
									<input class="check_company" type="checkbox" name="${co.company_Type}" />${co.englishName}
								</label>
							</c:if>
					    </c:forEach>--%>
						<input id="company_Type" type="hidden" name="company_Type" />
						
						
						<%--<label class="inline checkbox">
							<input id="CC" type="checkbox" name="CFS"  class="company"/>CFS
						</label>
						<label class="inline checkbox">
							<input id="C" type="checkbox" name="CAM" value="N" class="company"/>CAM
						</label>
						<label class="inline checkbox">
							<input id="CCC" type="checkbox" name="CIS" value="N" class="company"/>CIS
						</label>
						<label class="inline checkbox">
							<input id="C2" type="checkbox" name="CCL" value="N" class="company"/>CCL
						</label>
						<label class="inline checkbox">
							<input id="C3" type="checkbox" name="CFSH" value="N" class="company"/>CFSH
						</label>
						<label class="inline checkbox">
							<input id="C4" type="checkbox" name="CMS" value="N" class="company"/>CMS
						</label>
						<label class="inline checkbox">
							<input id="C22" type="checkbox" name="CFG" value="N" class="company"/>CFG
						</label>
						<label class="inline checkbox">
							<input  id="C24" type="checkbox" name="CCIA" value="N" class="company"/>CCIA
						</label>
						<label class="inline checkbox">
							<input  id="C25" type="checkbox" name="CCFSH" value="N" class="company"/>CCFSH
						</label>
						<label class="inline checkbox">
							<input  id="C26" type="checkbox" name="CWMC" value="N" class="company"/>CWMC
						</label>--%>
						
						<input id="pay" type="hidden" value="${adminUsername}" name="pay"/>
        				<input id="EnglishPosition" type="hidden" name="EnglishPosition"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">The attachment:</td>
					<td class="tagCont" colspan="3">
						<div id="upload" style="DISPLAY:NONE;"> 
							<span style="color:red;font-size:14px;font-family: 'Arial Narrow';"><strong>&nbsp;</strong>
    						Please upload the documents if there is any update on "professional title" and only one file is accepted when uploading.
    						</span><br/>
				          	<div id="upload_view" class="STYLE1">
					            <input type="file" name="filePath" id="filePaths"/>
					            <%--<input type="button" name="fileLoad" id="fileLoad" value="Upload" onclick="fileupload()"/>
					            
					            --%><button class="btn" name="fileLoad" id="fileLoad"  onclick="return fileupload()">Upload</button>
				            </div>
          					<a id="downs" target="_Blank" style="font-size: 16px;"></a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="display:none;text-decoration: none;" id="quitFujian" href="javascript:void(0);" onclick="javascript:$(this).hide();fileName='';$('#downs').empty();$('#upload_view').show();">Delete</a>
          				</div>
					</td>
				</tr>
				<tr>
					<td class="tagName">Remark:</td>
					<td class="tagCont" colspan="3">
						Should you have any queries on the above information, please contact Human Resources Department on <a href="mailto:HRD.info@convoy.com.hk">HRD.info@convoy.com.hk</a>
						</br></br>
						<textarea name="remark" id="remark" cols="74" rows="3" style="width: 70%; " onfocus="if (this.value=='Please state your special request in this box'){this.value ='';this.style.color='#000000';}" onblur="if (this.value==''){this.value='Please state your special request in this box';this.style.color='#AAAAAA';}" style='color:#AAAAAA;'>Please state your special request in this box</textarea>
					</td>
				</tr>
				<tr>
					<td class="tagName"></td>
					<td class="tagCont" colspan="3" style="text-align: right; padding-right: 30%;">
						<input name="method" value="saverequestconvoy1" type="hidden" />
						<button class="btn" id="AddNameCard" name="Submit" >Submit</button>
						<a class="btn" onclick="showLayer(this)" style="display: none;">预览</a>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</div>
<div id="ncContainer" style="display: none;">
	<div target="_blank" href="javascript: void(0)" class="namecard" id="namecard">
		<div class="version_bgimg">
			<img src="images/bg.png"/>
		</div>
		<div class="version_ch" id="version_ch" > 
			<div class="version_container">
				<div class="nameinfo">
					<div class="infos">
						<h4 id="ch_name"></h4>
						<p id="ch_position"></p>
						<p id="ch_department"></p>
						<p id="ch_academicTitle"></p>
						<p id="ch_professionalTitleText"></p>
						<p class="remark">
							<span id="ch_trreg_tag">T.R.Reg.No.</span>
							<em id="ch_trreg"></em>
						</p>
						<p class="remark">
							<span id="ch_mpfa_tag">MPFA No.</span>
							<em id="ch_mpfa"></em>
							<span id="ch_andsign"> | </span>
							<span id="ch_ce_tag">CE No.</span>
							<em id="ch_ce"></em>
						</p>
					</div>
				</div>
				<div class="otherinfo">
					<div class="companyinfo" id="ch_company">
					</div>
					<div class="contactinfo">
						<p class="address" id="ch_location"></p>
						<p class="phonecall">
							<span class="tagname">直線·</span>
							<span class="tagcont" id="ch_direct"></span>
						</p>
						<p class="phonecall">
							<span class="tagname">手提·</span>
							<span class="tagcont" id="ch_mobile"></span>
						</p>
						
						<p class="phonecall">
							<span class="tagname">電郵·</span>
							<span class="tagcont" id="ch_email"></span>
						</p>
					</div>
				</div>
				<div style="clear: both;"></div>
			</div>
			<div class="urlinfo">
				<p class="remark">仅供预览</p>
				<p class="url">www.convoyfinancial.com</p>
			</div>
		</div>
		<div class="version_en" id="version_en">
			<div class="version_container">
				<div class="nameinfo">
					<div class="infos">
						<h4 id="en_name"></h4>
						<p id="en_position"></p>
						<p id="en_department"></p>
						<p id="en_academicTitle"></p>
						<p id="en_professionalTitleText"></p>
						<p class="remark">
							<span>T.R.Reg.No.</span>
							<em id="en_trreg"></em>
						</p>
						<p class="remark">
							<span>MPFA No.</span>
							<em id="en_mpfa"></em>
							<span id="en_andsign"> | </span>
							<span>CE No.</span>
							<em id="en_ce"></em>
						</p>
					</div>
				</div>
				<div class="otherinfo">
					<div class="companyinfo" id="en_company">
	
					</div>
					<div class="contactinfo">
						<p class="address" id="en_location"></p>
						<p class="phonecall">
							<span class="tagname">Direct</span>
							<span class="tagcont" id="en_direct"></span>
						</p>
						<p class="phonecall">
							<span class="tagname">Mobile</span>
							<span class="tagcont" id="en_mobile"></span>
						</p>
						<p class="phonecall">
							<span class="tagname">Email</span>
							<span class="tagcont" id="en_email"></span>
						</p>
					</div>
				</div>
			</div>
			
			<div class="urlinfo">
				<p class="remark">Preview Information Only</p>
				<p class="url">www.convoyfinancial.com</p>
			</div>
		</div>
		<div class="closeBtn">
			<a href="javascript: void(0)" id="close" alt="关闭">x</a>
		</div>
		<div class="functionLine">
			<div class="bg"></div>
			<div class="tool">
				<span class="remark" id="ver_remark" style="color: red;">僅供資料預覽</span>
				<input type="button" value="En" curAlt="CH" id="mybtn"/>
			</div>
		</div>
		<div style="clear: both;"></div>
	</div>
</div>
<script type="text/javascript">
 
$(document).keydown(function(e){
	switch(e.keyCode){
		case 27:	
			if($('#namecard',parent.document).length > 0){
				parent.closeLayer();
			}
			break;
		default: break;
	}
});
//将公司简称转为全称, 由于全称数据不全【停用】
function getFullName(name,language)
{
	
	 var result = '', tempStr = name.split(',');
	/**var comStr = [{name:'CFS',fullName:'Convoy Financial Services Limited'},
				  {name:'CAM',fullName:'Convoy Asset Management Limited'},{name:'CIS',fullName:''},
		          {name:'CCL',fullName:''},{name:'CFSH',fullName:''},{name:'CMS',fullName:''},
		          {name:'CFG',fullName:''},{name:'CCIA',fullName:''},{name:'CCFSH',fullName:''},
		          {name:'CWMC',fullName:''}];**/
	
	var comStr=${namecardComanyList};
	for(var i=0; i<comStr.length; i++){
		for(var j=0; j<tempStr.length; j++){
			if(comStr[i].company_Type == tempStr[j]){
				switch(language){
					case 'en':
						result += '<p>' + comStr[i].englishName + '</p>';
						break;
					case 'ch':
						result += '<p>' + comStr[i].chineseName + '</p>';
						break;
					default: break;
				}
				continue;
			}
		}
	}
	return result;
}
//名片预览
function setNCInfo(staffno){
 
	//ch版
	setDisplay($("#ChinesePosition").val(),'ch_position');
	setDisplay($("#ChineseStaffDepartmentText").val(),'ch_department');//Department_Eng
	setDisplay($("#ChineseAcademicTitle").val(),'ch_academicTitle');
	setDisplay($("#ChineseProfessionalTitle").val(),'ch_professionalTitleText');
	setDisplayEm($("#TR_RegNo").val(),'ch_trreg');
	setDisplayEm($("#CENO").val(),'ch_ce');
	setDisplayEm($("#MPFA_NO").val(),'ch_mpfa');
	if($("#locationchi").val()==""){
		setDisplay($("#location").val(),'ch_location');
	}else{
		setDisplay($("#locationchi").val(),'ch_location');
	}
 
	setDisplay($("#ChineseName").val(),'ch_name');
	setDisplay($("#Mobile").val(),'ch_mobile',1);
	setDisplay($("#DirectLine").val(),'ch_direct',1);
	var company_name="";
	$(".check_company").each(function (){
		if(this.value=="Y"){
			if(company_name==""){
				company_name+=this.name;
			}else{
				company_name+=","+this.name;
			}
		}
	});
	$("#ch_company").empty().html(getFullName(company_name,'ch'));
	$("#ch_email").text($("#Email").val().toLowerCase());
	
	//en版
	setDisplay($("#location").val(),'en_location');
	setDisplay($("#EnglishName").val(),'en_name');
	setDisplay($("#EnglishPosition").val(),'en_position');
	setDisplay($("#EnglishStaffDepartmentText").val(),'en_department');
	setDisplay($("#EnglishAcademicTitle").val(),'en_academicTitle');
	setDisplay($("#EnglishProfessionalTitleText").val(),'en_professionalTitleText');
	setDisplayEm($("#TR_RegNo").val(),'en_trreg');
	setDisplayEm($("#CENO").val(),'en_ce');
	setDisplayEm($("#MPFA_NO").val(),'en_mpfa');
	setDisplay($("#Mobile").val(),'en_mobile',1);
	setDisplay($("#DirectLine").val(),'en_direct',1);
	
	
	$("#en_company").empty().html(getFullName(company_name,'en'));
	$("#en_email").text($("#Email").val().toLowerCase());

	var html = $('#ncContainer').html();
	parent.showNameCard(html);
}
function transFormat(val){
	var tempVal = val, result = '';
	for(var i=0;i<val.length;i=i+4){
		if(i < val.length-4){
			result += tempVal.substring(i,i+4)+' ';	
		}else{
			result += tempVal.substring(i,i+4);	
		}
	}
	return result;
}
function setDisplay(val,objId,sign){
	var tagName = $('#'+objId)[0].tagName;
	if(val == null || val == '' || val == 'undefined'){
		
		switch(tagName){
			case 'SPAN':
				$('#'+objId).parent().hide();
				break;
			default:
				$('#'+objId).hide();
				break;
		}
		$('#'+objId).text('');
	}else{
		$('#'+objId).parent().show();
		$('#'+objId).show();
		if(sign == 1){
			$('#'+objId).text("+(852) "+transFormat(val));
		}else{
			$('#'+objId).text(val);
		}
	}
}
function setDisplayEm(val, objId){
	if(val == null || val == '' || val == 'undefined'){
		if(objId == 'ch_ce' || objId =='ch_mpfa'){
			$('#ch_andsign').hide();
		}else if(objId == 'en_ce' || objId =='en_mpfa'){
			$('#en_andsign').hide();
		}
		$('#'+objId).prev().hide();
		$('#'+objId).hide();
		$('#'+objId).text('');
	}else{
		if(objId == 'ch_ce' || objId =='ch_mpfa'){
			if($('#CENO').val() !='' && $('#MPFA_NO').val() != ''){
				$('#ch_andsign').show();
			}
		}else if(objId == 'en_ce' || objId =='en_mpfa'){
			if($('#CENO').val() !='' && $('#MPFA_NO').val() != ''){
				$('#en_andsign').show();
			}
		}
		$('#'+objId).prev().show();
		$('#'+objId).show();
		$('#'+objId).text(val);
	}
}
function showLayer() {
	//var staffno = 'RC0106';
	var staffno = $("#StaffNo").val();
	setNCInfo(staffno);
	return false;
} 
</script>
</body>
</html>