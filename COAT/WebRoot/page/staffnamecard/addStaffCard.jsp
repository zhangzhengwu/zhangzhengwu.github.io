<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
	<%
		if (session.getAttribute("adminUsername")==null || session.getAttribute("adminUsername")=="")
			out.println("<script>alert('未登录系统！');top.location.href='signin.jsp';</script>");
	%>
	<head>
	<base href="<%=basePath%>" />
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
	<title>信息錄入</title>

<style type="text/css">
<!--
#information {
	position:absolute;
	width:100%;
	height:726px;
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
	size:35px;
	font-family: 'Arial Narrow';
	font-size: 16px;

    }
-->
</style>

<script type="text/javascript">
/*窗体加载事件 */
var location_zs="";
var pos_english="";


function getOldRecord(code){
	if($("#StaffNo").val==""){
		return false;
	}
	$.ajax({
		type: "post",
		url: "StaffNameCardReaderServlet",
		data: {"method":"getOldRecord","staffcode":code},
		success: function(date){
			var d=eval(date);
			if(date.indexOf("{")==0){
				var result=$.parseJSON(data);
				alert(result.msg);
			}else{
				if(d[0]!="" && d[0]!=null){
					var strs= new Array(); //定义一数组 
					strs=d[0].title_english.split(";");
					
					$("#StaffNo").val(d[0].staff_code);
				
					$("#num").find("option[value='"+d[0].quantity+"']").attr("selected",true);
					//$("#location").val(d[0].location);
					location_zs=d[0].location;
					$("#location_sz").val(d[0].location);
					        if($("#location_sz").val().indexOf("17/F, Cubus, 1 Hoi Ping Road, Causeway Bay") > -1){
					        	$("#location").val("17/F, Cubus, 1 Hoi Ping Road, Causeway Bay, HK");					
					        }else if($("#location_sz").val().indexOf("Nan Fung") > -1){
					        	$("#location").val("Nan Fung");
					        }else if($("#location_sz").val().indexOf("CP3") > -1){
					        	$("#location").val("CP3");					        	
					        }else{
					        	$("#location").val("@CONVOY");
					        }
					
					//$("#type").val(d[0].layout_type);
					$("#type").find("option[value='"+d[0].layout_type+"']").attr("selected",true);
					
					//$("#ET").val(d[0].eliteTeam);
					//$("#urgentCase").val(d[0].urgent);
					if(d[0].eliteTeam=="Y"){
						$("#ET").attr("checked",true);
					}
					if(d[0].urgent=="Y"){
						$("#urgentCase").attr("checked",true);
					}
					$("#EnglishName").val(d[0].name);
					$("#ChineseName").val(d[0].name_chinese);
				
					//alert(strs[0]+"---"+strs[1]);
					pos_english=strs[0];
					$("#EnglishPosition").val(d[0].title_english);
					$("#ChinesePosition").val(d[0].title_chinese);
					$("#EnglishStaffDepartmentText").val(strs[1]);
					$("#ChineseStaffDepartmentText").val(d[0].external_chinese);
					$("#EnglishAcademicTitle").val(d[0].academic_title_e);
					$("#ChineseAcademicTitle").val(d[0].academic_title_c);
					$("#EnglishProfessionalTitleText").val(d[0].profess_title_e);
					$("#ChineseProfessionalTitle").val(d[0].profess_title_c);
					$("#englishExternal").val(d[0].external_english);
					$("#chineseExternal").val(d[0].external_chinese);
					$("#TR_RegNo").val(d[0].tr_reg_no);
					$("#CENO").val(d[0].ce_no);
					$("#MPFA_NO").val(d[0].mpf_no);
					$("#Mobile").val(d[0].bobile_number);
					$("#FAX").val(d[0].fax);
					$("#DirectLine").val(d[0].direct_line);
					$("#Email").val(d[0].e_mail);
					$("#Company").val(d[0].Company);
				}else{
					alert("没找到该StaffCode的历史记录!");
				}
			}
		},error:function(){
			alert("网络连接失败!");
			return false;
		}
	});
}
$(function(){
	/* getlocation(); */
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
			$("#EnglishPosition").find("option[value='"+pos_english+"']").attr("selected",true);
		}
		}
		
	});
	
}
/* function getlocation(){
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
				html+="<option value='' >Select Location...</option>";
				for(var i=0;i<location.length;i++){
					html+="<option value='"+location[i].realName+"' >"+location[i].name+"</option>";
				}
			}else{
				html+="<option value=''>加载异常</option>";
			}
			$("#location").append(html);
			$("#location").find("option[value='"+location_zs+"']").attr("selected",true);
			$("#location").val("Y");//默认选择@Convoy
			},error:function(){
				alert("网络连接失败,请返回登录页面重新登录!");
				return false;
			}
		});
	} */
function department(){
	$.ajax({
		type: "post",
		//url: "QueryStaffProfessionalServlet",
		//data: null,
		url: "<%=basePath%>common/CommonReaderServlet",
		data:{"method":"getstaffprofessional"},
		success: function(date){
		var d=eval(date);
		var html="";
		if(d.length>0){
			for(var i=0;i<d.length;i++){
			html+="<option value='"+d[i].prof_title_ename+"'>"+d[i].prof_title_ename+"</option>";
			}
			$("#EnglishStaffDepartment:last").append(html);
			}
		}
		
	});
	
}
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
$("#message").hide();
//$(".txt").val("");

$("#urgentCase").change(function(){
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
			$("#num").val("100");
		}else{
			$("#num").val("100");
		}
});

/*****************************************************/
$("#EnglishStaffDepartment").change(function(){
	$.ajax({
		type:"post",
		url:"SelectStaffProfessionalServlet",
		data:{'EnglishProfessionalTitle':$("#EnglishStaffDepartment").val()},
		success :function(date){
			//$("#ChineseStaffDepartmentText").val("");
			//$("#EnglishStaffDepartmentText").val("");
			if(date!="[]" || date!="null" || date){
				departmentCText = $("#ChineseStaffDepartmentText").val()+(date==""?"":";")+date+";";
				departmentEText = $("#EnglishStaffDepartmentText").val()+($("#EnglishStaffDepartmentText").val()==""?"":";")+$("#EnglishStaffDepartment").val()+";";
				$("#ChineseStaffDepartmentText").val(departmentCText);
				$("#EnglishStaffDepartmentText").val(departmentEText);

		 	}
			
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
									$("#EnglishProfessionalTitleText").val(professionalEText).change();
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

//CE NO blur事件
$("#CENO").blur(function(){
	if($("#CENO").val()=="")
		$("#C").attr("checked","").val("N");
	else
		$("#C").attr("checked","checked").val("Y");
});
/****************************************************StaffNo 失去焦点时触发的时间**************,QueryStaffProfessionalServlet**************************************/
/**	$("#StaffNo").blur(function(){
		
		if($("#StaffNo").val().length>=5){
				$.ajax({ 
				type: "post", 
				url: "StaffEditServlet",
				data: {'StaffNo':$("#StaffNo").val()}, 
				success: function(date){
							 if(date!=null &&date!="[null]"){
							 $("#message").hide();
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
									//$("#ChinesePosition").val(master[0].c_Title_Department);
									$("#EnglishPosition").val(master[0].e_Title_Department.substring(0,master[0].e_Title_Department.indexOf(";"))).change();
									$("#ChineseExternalTitle").val(master[0].c_ExternalTitle_Department);
									$("#EnglishExternalTitleText").val(master[0].e_ExternalTitle_Department);
									$("#EnglishProfessionalTitleText").val(master[0].e_EducationTitle);
									$("#ChineseProfessionalTitle").val(master[0].c_EducationTitle);
									$("#EnglishStaffDepartmentText").val($.trim(master[0].e_Title_Department.substring(master[0].e_Title_Department.indexOf(";")+1)))
									$("#ChineseStaffDepartmentText").val($.trim(master[0].c_Title_Department.substring(master[0].c_Title_Department.indexOf(";")+1)))
									}else{							 
								$("#message").show(200);
								//$(".txt").val("").html("");
								// $("#StaffNo").val("").focus();
							} 
						 } 
				  });
 
		 }
		 
		 else {
			 if($("#StaffNo").val().length !=0){
				error("Staff Code為非法數據！");
				$(".txt").val("");
				$("#StaffNo").focus();
				return false;
			 }
		}
		
});**/


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
});		
$("body").keydown(function(e){
	if(e.keyCode==13){
	vailed();
	}
	});
/*****************************表单结束********************************/	
 
function vailed(){
	var nums=$("#num").val();                                                      
	if($("#StaffNo").val()==""){																 
		error("Staff Code不能為空！");	
		$("#StaffNo").focus();
		return false;																			 
	}																							 
	if($("#num").val()==""){															 
		error("Quantity不能為空！");	
			$("#num").focus();
		return false;																				 
	}	 																				 
	if(isNaN(nums)){		
		error("Quantity 必須是100的整数倍!");	
		$("#num").focus();
		return false;															 
	}else{
			if($("#num").val()%100 !=0){
			error("quantity必须是100的整数倍！");
			$("#num").focus();
			return false;
		}	
	}		
	/**																					 
	if(!isNaN(nums)){																						 
		if(parseInt($("#num").val())>400){													   
		alert(" Quantity 數量單次不能大於 400！"); 
		$("#num").focus();
		return false;																			 
	}	
													 
}	**/ 
/**************************判断事件是否合法*****************/
var datetime=(new Date()).getFullYear()+"-"+((new Date()).getMonth()+1)+"-"+(new Date()).getDate()
/**********************************************************/
/*****************判断复选框是否被选中**********************/
	/**	if($("#AE").attr("checked")==true){
		
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
		}
		else{
		$("#C").val("N");
		}
			if($("#C2").attr("checked")==true){
		$("#C2").val("Y");
		}
		else{
		$("#C2").val("N");
		}
			if($("#CCC").attr("checked")==true){
		$("#CCC").val("Y");
		}
		else{
		$("#CCC").val("N");
		}
			if($("#C3").attr("checked")==true){
		$("#C3").val("Y");
		}
		else{
		$("#C3").val("N");
		}
			if($("#C4").attr("checked")==true){
		$("#C4").val("Y");
		}
		else{
			$("#C4").val("N");
		}
		
		if($("#C22").attr("checked")==true){
			$("#C22").val("Y");
		}
		else{
			$("#C22").val("N");
		}
		
		if($("#C23").attr("checked")==true){
			$("#C23").val("Y");
		}
		else{
			$("#C23").val("N");
		}
		
		if($("#C24").attr("checked")==true){
			$("#C24").val("Y");
		}
		else{
			$("#C24").val("N");
		}
		
		if($("#C25").attr("checked")==true){
			$("#C25").val("Y");
		}
		else{
			$("#C25").val("N");
		}
		
		if($("#C26").attr("checked")==true){
			$("#C26").val("Y");
		}
		else{
			$("#C26").val("N");
		}**/
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
				//changeDate();
				$("#urgentCase").val("Y");
				if(parseInt($("#num").val())<100 && parseInt($("#num").val())>0){
					error("在Urgent情况下，Quantity不能低于100!");
					$("#num").focus();
					return false;
				}
		}
		//貌似trim()方法不支持
		 
		if(parseInt($("#num").val())<0){
			error("Quantity不能小于0!"); 
				return false;
			
		}
		 
		if($("#EnglishPosition").val()=="positionName" || $("#EnglishPosition").val()==null || $("#EnglishPosition").val()==""){
			error("請選擇Title_Department in English ！");
			$("#EnglishPosition").focus();
			return false;
			
		}else{
			var positiontext = $("#EnglishPosition").find("option:selected").text();
			$("#EnglishPosition").val(positiontext);
		}
		
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
		$("#company_val").val(company_Type);
		$("#AddNameCard").attr("disabled","disabled");
	if($("#ET").attr("checked")==true){
	$.ajax({
		url:"VailEliteServlet",
		type:"post",
		data:{"staffcode":$("#StaffNo").val(),"table":"request_staff"},
		success:function(date){
			 
			if(date=="true"){
					error("已经有过Elite Team提交记录!");
					$("#ET").attr("checked","");
					$("#AddNameCard").attr("disabled","");
					return false;
			}
			$("#myform").submit();	
		},error:function(){
				$("#AddNameCard").attr("disabled","");
			
			error("网络连接失败，请稍后重试...");
			return false;
		}
	});
	}else{
		$("#myform").submit();	
	}
}

});
</script>
</head>
  
<body>
<div class="cont-info">
	<div class="info-title">
		<div class="title-info">
			<form action="AddStaffRequestServlet" id="myform" method="post">
			<table>
				<tr>
					<td class="tagName">Staff Code:</td>
					<td class="tagCont">
						<input id="StaffNo" name="StaffNos" onBlur="getOldRecord(this.value)" onkeyup="this.value=this.value.toUpperCase();"  type="text" size="28" class="txt" />
						<span style="color:#FF0000; font-family:'仿宋'" id="message">Staff_NO不存在!</span>
					</td>
					<td class="tagName">quantity:</td>
					<td class="tagCont">
						<select id="num" name="nums"   >
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
						<input type="text" name="locatins" id="location" />
					    <input type="text" name="location_zs" id="location_sz" style="width:220px" />
					</td>
					<td class="tagName">Layout:</td>
					<td class="tagCont">
						<select name="types" id="type">
					        <option value="S">Standard Layout</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tagName">New Name Card:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input type="checkbox" name="ETs" value="N" id="ET" />YES
						</label>
					</td>
					<td class="tagName">Urgent Case:</td>
					<td class="tagCont">
						<input id="urgentCase" type="checkbox" name="urgent" value="N" />
					</td>
				</tr>
				<tr>
					<td class="tagName">English Name:</td>
					<td class="tagCont">
						<input name="EnglishNames" type="text" size="35" class="txt" id="EnglishName" />
					</td>
					<td class="tagName">Chinese Name:</td>
					<td class="tagCont">
						<input id="ChineseName" name="ChineseNames" type="text" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Position in (Eng):</td>
					<td class="tagCont">
						<select name="EnglishPositions" id="EnglishPosition" >
					        <option value="positionName">請選擇PositionName</option>
					    </select>
					</td>
					<td class="tagName">Position in (Chi):</td>
					<td class="tagCont">
						<input id="ChinesePosition" name="ChinesePositions" type="text" size="35"  class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Department in (Eng):</td>
					<td class="tagCont">
						<input id="EnglishStaffDepartmentText" name="EnglishStaffDepartmentTexts" type="text" size="35"  class="txt"/>
				        <select id="EnglishStaffDepartment" name="EnglishStaffDepartments">
				          <option value="department">請選擇Department</option>
				        </select>
					</td>
					<td class="tagName">Department in (Chi):</td>
					<td class="tagCont">
						<input id="ChineseStaffDepartmentText" name="ChineseStaffDepartmentTexts" type="text" size="35"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Academic Title (Eng):</td>
					<td class="tagCont">
						<input id="EnglishAcademicTitle" name="EnglishAcademicTitles" type="text" size="35"  class="txt"/>
					</td>
					<td class="tagName">Academic Title (Chi):</td>
					<td class="tagCont">
						<input id="ChineseAcademicTitle" name="ChineseAcademicTitles" type="text" size="35"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Professional Title (Eng):</td>
					<td class="tagCont">
						<input id="EnglishProfessionalTitleText" name="EnglishProfessionalTitles" type="text" size="35"  class="txt"/>
						<select id="EnglishProfessionalTitle" name="EnglishProfessional">
						  <option value="professional">請選擇ProfessionalTitle</option>
						</select>
					</td>
					<td class="tagName">Professional Title (Chi):</td>
					<td class="tagCont">
						<input id="ChineseProfessionalTitle" name="ChineseProfessionalTitles" type="text" size="35"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">External Title (Eng):</td>
					<td class="tagCont">
						<input type="text" class="txt" size="35" name="englishExternal" id="englishExternal"/>
					</td>
					<td class="tagName">External Title (Chi):</td>
					<td class="tagCont">
						<input type="text" class="txt" name="chineseExternal" id="chineseExternal" size="35" />
					</td>
				</tr>
				<tr>
					<td class="tagName">TR Reg NO:</td>
					<td class="tagCont">
						<input id="TR_RegNo" name="TR_RegNos" type="text" size="35" class="txt" />
					</td>
					<td class="tagName">CE NO:</td>
					<td class="tagCont">
						<input id="CENO" name="CENOs" type="text" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">MPFA NO:</td>
					<td class="tagCont">
						<input id="MPFA_NO" name="MPFA_NOs" type="text" size="35" class="txt" />
					</td>
					<td class="tagName">Mobile:</td>
					<td class="tagCont">
						<input id="Mobile" name="Mobiles" type="text" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">FAX:</td>
					<td class="tagCont">
						<input id="FAX" name="FAXs" type="text" size="35" class="txt" />
					</td>
					<td class="tagName">Direct Line:</td>
					<td class="tagCont">
						<input id="DirectLine" name="DirectLines" type="text" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">E-Mail:</td>
					<td class="tagCont" colspan="3">
						<input id="Email" name="Emails" type="text" onkeyup="this.value=this.value.toLowerCase();" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Company:</td>
					<td class="tagCont" colspan="3" id="check_company">
					  <c:forEach items="${getCompany}" var="co" >
				      	<label class="inline checkbox">
							<input class="check_company" type="checkbox" title="${co.englishName}" name="${co.company_Type}" <c:if test="${co.company_Type=='CFS'}"> checked="checked"</c:if> />${co.company_Type}
						</label>
				      </c:forEach>
				      <input type="hidden" name="company_val" id="company_val">
				      <%--<label class="inline checkbox">
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
						<label class="inline checkbox">
							<input  id="C24" type="checkbox" name="CCIA" value="N" />CCIA
						</label>
						<label class="inline checkbox">
							<input  id="C25" type="checkbox" name="CCFSH" value="N" />CCFSH
						</label>
						<label class="inline checkbox">
							<input  id="C26" type="checkbox" name="CWMC" value="N" />CWMC
						</label>--%>
					</td>
				</tr>
				<tr style="height: 30px;">
					
				</tr>
				<tr>
					<td class="tagBtn" colspan="4">
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