<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>查詢頁面</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<script type="text/javascript">
 		var base="<%=basePath%>";
		var downs=null;
		var pagenow =1;
		var totalpage=1;
		var total=0;
		var DD=true;
		/*************************************************获取弹出层的位置*************************/
   
 /*****************************************************WindowForm Load *************************************/
 $(function(){
	 $("[title='1'][type='text'],select").not("StaffNo").width(280);
	 
/* 		$("#CE").blur(function(){
			if(this.value==""){
				$("input[name='companys'][value='CAM']").attr("checked","");
			}else{
				$("input[name='companys'][value='CAM']").attr("checked","checked");
			}
		});
		
		$("input[name='companys'][value='CAM']").click(function(){
			if($("#CE").val()==""){
				this.checked=false;
			}
		});
		 */
		
		
		
		
		
		
		
		
 	 position();
     professional();
     getlocation("upselect");
     getlocation("location");
	 $("#EnglishEducationTitle").hide();
	 $("#start_date,#end_date").val(CurentTime());	
	/****************************************************************Motify click*****************************/
	 $("#motify").click(function(){
		$("[title='1']").removeAttr("disabled");
		$("#save").show();
	 });		
/****************************************************************************************************************/


/******************************************退出詳細頁面*************************************************************/
$("#quit").click(function(){
	$("#jqajax,#lists").show();
	$("#details").hide();
	return false;
});
/****************************************************************************************************************/
/****************************************************Search click***********************************/
		$("#searchs").click(function(){selects(1);});
		$("#StaffNo").blur(function(){ checkStaff();});
	/***********************************************************Search Click end************************/
	/*********************************************************PositionName以及Profession值发生改变****************************/
	$("#EnglishTitle_Department").change(function(){
	$.ajax({
		type :"post",
		url :"SelectPositionServlet",
			data :{'EnglishName': $("#EnglishTitle_Department").val()},
		success:function (date){
			if(date!="" || date!="null" || date){
				$("#ChineseTitle_Department").val(date);
				if($("#ChineseTitle_Department").val()=="null" || $("#ChineseTitle_Department").val()==null){
					$("#ChineseTitle_Department").val("");
					}
				}
		}
	});
});
/*************************************************************************EnglishProfession获取光标事件*************************************************/
$("#EnglishProfession").focus(function(){
$("#EnglishEducationTitle").show();
});
/*****************************************************************************************************************************************************/
/*******************************************************************EnglishEducationTitle值发生改变时*********************************/
	$("#EnglishEducationTitle").change(function(){
	$.ajax({
		type:"post",
		url:"SelectProfessionalServlet",
		data: {'EnglishProfessionalTitle':$("#EnglishEducationTitle").val()},
		success :function(date){
			if(date!="" || date!="null" || date){
			//	$("#ChineseEducationTitle").val(date);
				if($("#ChineseEducationTitle").val()=="null" || $("#ChineseEducationTitle").val()==null){
					$("#ChineseEducationTitle").val("");
					}
					departmentCText = $("#ChineseEducationTitle").val()+date+";";
				departmentEText = $("#EnglishProfession").val()+ $("#EnglishEducationTitle").val()+";";
				$("#ChineseEducationTitle").val(departmentCText);
				$("#EnglishProfession").val(departmentEText);
					
		}
		
		}
	});
});
	/*************************************************************************************************************************/
	/***************************************保存方法*****************************************************/
	$("#save").click(function(){
		var urgent="N";
		if($("input[name='checkbox']").is(':checked')==true){
			urgent="Y";
			if(parseFloat($("#Quantity").val())<100){
				if(!confirm("Urgent Case 情况下Quantity不能低於100,是否继续?")){
					$("#Quantity").focus();
					return false;
				}
			}
		}else{
			urgent="N";
		}
		if($("#reStaffNo").val()!= $("#StaffNo").val()){
			if(!confirm("警告！您正在修改顾问編號！確定繼續嗎？")){
				return false;
			}
				$("#Payer").val($("#StaffNo").val());
		}  

		if(getCompany()==""){
			error("Company 不能为空!");
			return false;
		}else{
			$("#company").val(getCompany());
		}
		if(parseInt($("#Quantity").val())==0){
			if("${roleObj.delete}"!=1){
				alert("对不起，您目前还没有删除数据的权限或系統身份信息已丟失!");
				return false;
			}
		}
	 
		$.ajax({
			type:"post",
			//url:"MotifyNameCardServlet",
			url:"namecard/NameCardWriteServlet",
			data:{"method":"modifynamecardrequest",
				"aeConsultant":$("#aeConsultant").val(),
				"location":$("#upselect").val(),
				"rePayer":$("#rePayer").val(),
				"reStaffNo":$("#reStaffNo").val(),
				"reQuantity":$("#reQuantity").val(),
				"urgent":urgent,
				"Payer":$("#Payer").val(),
				"layout_type":$("#layout_type").val(),
				"StaffNo":$("#StaffNo").val(),
				"EnglishName":$("#EnglishName").val(),
				"ChineseName":$("#ChineseName").val(),
				"EnglishTitle_Department":$("#EnglishTitle_Department").val(),
				"ChineseTitle_Department":$("#ChineseTitle_Department").val(),
				"EnglishExternalTitle_Department":$("#EnglishExternalTitle_Department").val(),
				"ChineseExternalTitle_Department":$("#ChineseExternalTitle_Department").val(),
				"AcademicTitle_e_Department":$("#AcademicTitle_e_Department").val(),
				"AcademicTitle_c_Department":$("#AcademicTitle_c_Department").val(),
				"EnglishEducationTitle":$("#EnglishProfession").val(),
				"ChineseEducationTitle":$("#ChineseEducationTitle").val(),
				"trg":$("#trg").val(),
				"CE":$("#CE").val(),
				"MPF":$("#MPF").val(),
				"HKCIB":$("#HKCIB").val(),
				"Email":$("#Email").val(),
				"DirectLine":$("#DirectLine").val(),
				"FAX":$("#FAX").val(),
				"MobilePhone":$("#MobilePhone").val(),
				"UrgentDate":$("#UrgentDate").val(),
				"Quantity":$("#Quantity").val(),
				"CFS":$("#CC").val(),
				"CAM":$("#C").val(),
				"CIS":$("#CCC").val(),
				"CCL":$("#C2").val(),
				"CFSH":$("#C3").val(),
				"CMS":$("#C4").val(),
				"CFG":$("#C22").val(),
				"Blank":$("#C23").val(),
				"ET":$("#ET").val(),
				"DD":DD,
				"reUrgent":$("#reUrgent").val(),
				"company":$("#company").val(),
				"refno":$("#refno").val()},
				beforeSend: function(){
					parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
			success:function(data){
				var result=$.parseJSON(data);
				alert(result.msg);
				if(result.state=="success"){
					$("#details").hide();
	 				$("#lists,#jqajax").show();
	 				selects(1);
				}
 				
			},error:function(){
				alert("网络连接失败,请稍后重试...");
				return false;
			}
			
		});
		 
	 return false;
	});
	
 

$("#aeConsultant").change(function(){
	if($("#aeConsultant").attr("checked")==true){
		$("#aeConsultant").val("Y");
		$("input[name='companys'][value='CIS']").attr("checked","checked");
		}
		else{
		$("#AE").val("N");
		$("input[name='companys'][value='CIS']").attr("checked","");
		}
});
	/***************************************************************************************************/
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
	/*****************************************************修改code**********************************/
$("#read").change(function(){
	if($("#read").attr("checked")==false){
		 $("#save").attr("disabled","");
		$("#StaffNo").attr("disabled","disabled");
	}else{
		$("#StaffNo").removeAttr("disabled").focus();
	}
});
	/*********************************************************************************************/
 
	
/************************************复选框的值发生改变时**********************************************************************/
$("[type='checkbox'][name!='companys']").change(function(){

	/****************判断复选框是否被选中********************/
	if($("#aeConsultant").attr("checked")){
		$("#aeConsultant").val("Y");
	}else{
		$("#aeConsultant").val("N");
	}
	if($("#ET").attr("checked")){
		$("#ET").val("Y");
	}else{
		$("#ET").val("N");
	}
	/**
		if($("#CC").attr("checked")==true){
		$("#CC").val("Y");
		}else{$("#CC").val("N");}
		if($("#C").attr("checked")==true){
		$("#C").val("Y");
		}else{$("#C").val("N");}
			if($("#CCC").attr("checked")==true){
		$("#CCC").val("Y");
		}else{	$("#CCC").val("N");}
			if($("#C2").attr("checked")==true){
		$("#C2").val("Y");
		}else{$("#C2").val("N");}
			if($("#C3").attr("checked")==true){
		$("#C3").val("Y");
		}else{$("#C3").val("N");}
			if($("#C4").attr("checked")==true){
		$("#C4").val("Y");
		}else{$("#C4").val("N");}
			if($("#C22").attr("checked")==true){
		$("#C22").val("Y");
		}else{$("#C22").val("N");}
			if($("#C23").attr("checked")==true){
		$("#C23").val("Y");
		}else{$("#C23").val("N");}**/
		
		});
	
	
			//注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				selects(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				selects(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				selects(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				selects(pagenow);
			});
/***************************************************************************************************************************/
	/**************************************************************************************/

	/*****************************************************Down click**********************************/
	$("#down").click(function(){
		if(downs!=null){
			$.ajax({
				type: "post",
				url:"DownRequestLocationServlet",
				async:false,
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),
				'code':$("#staffcodes").val(),'name':$("#name").val(),'location':$("#location").val(),
				'urgentCase':$("#urgentCase").val(),'payer':$("#Namepayer").val(),'nocode':$("#nocode").val(),
				'ET':$("#ET_select").val(),'layout_select':$("#layout_select").val()},
				success:function(date){
				//	$("#login").hide();
				clipboardData.setData('Text',date.substring(date.indexOf(':')+1));//把内容复制到粘贴板
				alert(date);
				},error:function(){
					alert("导出失败!");
				}
				});
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
		}
	
	});
	/******************************************************Down END***********************************/
});

 </script>
<style type="text/css">
<!--
#login {
	position: absolute;
	width: 165px;
	height: 71px;
	z-index: 999;
	left: 525px;
	top: 200px;
	border-color: #99CCCC;
	display: none;
}
</style>
</head>
<body style="width:100%">
	<div class="cont-info" id="lists">
		<div class="info-search">
			<table>
				<tr>
					<td class="tagName">Start Date:</td>
					<td class="tagCont"><input id="start_date" type="text"
						readonly="readonly" onClick="Calendar('start_date');" /> <i
						class="icon-trash icon-large i-trash" id="clear1" align="middle"
						onclick="javascript:$('#start_date').val('');"></i></td>
					<td class="tagName">End Date:</td>
					<td class="tagCont"><input id="end_date" type="text" name="it"
						readonly="readonly" onClick="Calendar('end_date');" /> <i
						class="icon-trash icon-large i-trash" id="clear2" align="middle"
						onclick="javascript:$('#end_date').val('');"></i></td>
				</tr>
				<tr>
					<td class="tagName">Staff Code:</td>
					<td class="tagCont"><input type="text" name="staffcodes"
						id="staffcodes"></td>
					<td class="tagName">Full Name:</td>
					<td class="tagCont"><input type="text" name="names" id="name">
					</td>
				</tr>
				<tr>
					<td class="tagName">Payer:</td>
					<td class="tagCont"><input type="text" name="staffcode"
						id="Namepayer"></td>
					<td class="tagName">No Code:</td>
					<td class="tagCont"><select id="nocode">
							<option value="">Select Nocode</option>
							<option value="Y">YES</option>
							<option value="N">NO</option>
					</select></td>
				</tr>

				<tr>
					<td class="tagName">Location:</td>
					<td class="tagCont"><select name="select" id="location">
							<option value="">Select Location...</option>
							
					</select><%--
					
						<input type="text" name="select" id="location"/>
					--%></td>
					<td class="tagName">Urgent Case:</td>
					<td class="tagCont"><select id="urgentCase">
							<option value="">Select Urgent...</option>
							<option value="Y">YES</option>
							<option value="N">NO</option>
					</select></td>
				</tr>
				<tr>
					<td class="tagName">Layout:</td>
					<td class="tagCont"><select name="layout_select"
						id="layout_select">
							<option value="">Select Layout....</option>
							<option value="S">Standard layout</option>
							<option value="P">premium layout</option>
					</select></td>
					<td class="tagName">Elite Team:</td>
					<td class="tagCont"><select id="ET_select">
							<option value="">Select Elite Team</option>
							<option value="Y">YES</option>
							<option value="N">NO</option>
					</select></td>
				</tr>
				<tr>
					<td class="tagCont" colspan="2"></td>
					<td class="tagCont" colspan="2">
					<c:if test="${roleObj.search==1}">
						<a class="btn" id="searchs" name="search"> <i class="icon-search"></i> Search </a> 
					</c:if>
					<c:if test="${roleObj.export==1}">
						<a class="btn" id="down" name="downs"> <i class="icon-download"></i>Export </a>
					</c:if>
				</td>
				</tr>
			</table>
		</div>
		<div class="info-table">
			<table id="jqajax">
				<thead>
					<tr>
						<th class="width_">Detail</th>
						<th class="width_">Staff Code</th>
						<th class="width_">Name</th>
						<th class="width_">ChineseName</th>
						<th class="width_">Title in (Eng)</th>
						<th class="width_">Title in (Chi)</th>
						<th class="width_">CIB Reg. No.</th>
						<th class="width_">T.R. Reg. No.</th>
						<th class="width_">CE No.</th>
						<th class="width_">MPF No.</th>
						<th class="width_">Direct Line</th>
						<th class="width_">Quantity</th>
						<th class="width_">Sumbit Date</th>
					</tr>
				</thead>
			</table>
			<div align="center" class="page_and_btn" style="display: none;">
				<table class="main_table" width="100%" border="0" cellpadding="0"
					cellspacing="0" id="select_table">
					<tr class="main_head">
						<td colspan="6" align="center"><a id="first"
							href="javascript:void(0);">首页</a> <a id="pre"
							href="javascript:void(0);">上一页</a> 总共 <SPAN style="color: red;"
							id="total"> </SPAN>页 <a id="next" href="javascript:void(0);">下一页</a>
							<a id="end" href="javascript:void(0);">尾页</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="cont-info" id="details" style="display: none;">
		<div class="info-title">
			<div class="title-info">
			<form id="myform">
				<table>
					<tr>
						<td class="tagName">Staff Code:</td>
						<td class="tagCont"><input name="StaffCode" type="text"
							class="ss" id="StaffNo" size="15" disabled="disabled" /> <label
							class="checkbox inline"
							style="vertical-align: top; *vertical-align: middle;"> <input
								id="read" type="checkbox" title="1" />Modify code </label> <input
							id="UrgentDate" name="UrgentDates" type="hidden" /></td>
						<td class="tagName"><a href="javascript:void(0);" id="motify"
							style="font-size:16px; font-family:'仿宋'; color:#FF0000">Modify</a>
						</td>
						<td class="tagCont"></td>
					</tr>
					<tr>
						<td class="tagName">AE Consultant:</td>
						<td class="tagCont"><input name="checkbox2" type="checkbox"
							id="aeConsultant" title="1" value="N" /></td>
						<td class="tagName">UrgentCase:</td>
						<td class="tagCont"><input name="checkbox" type="checkbox"
							id="urgent" title="1" value="N"></td>
					</tr>
					<tr>
						<td class="tagName">Elite Team:</td>
						<td class="tagCont" colspan="3"><input name="checkbox2"
							type="checkbox" id="ET" title="1" value="N" /></td>
					</tr>
					<tr>
						<td class="tagName">Layout:</td>
						<td class="tagCont"><select id="layout_type" title="1">
								<option value="S">Standard Layout</option>
								<option value="P">Perminm Layout</option>
						</select></td>
						<td class="tagName">Location:</td>
						<td class="tagCont"><select name="select2" id="upselect"
							title="1">
							
						</select></td>
					</tr>
					<tr>
						<td class="tagName">EnglishName:</td>
						<td class="tagCont"><input type="text" name="EnglishNames"
							id="EnglishName" class="ss" title="1"></td>
						<td class="tagName">ChineseName:</td>
						<td class="tagCont"><input type="text" name="ChineseNames"
							id="ChineseName" class="ss" title="1"></td>
					</tr>
					<tr>
						<td class="tagName">Title in (Eng):</td>
						<td class="tagCont"><select name="EnglishTitle_Departments"
							class="ss" title="1" id="EnglishTitle_Department">
						</select></td>
						<td class="tagName">Title in (Chi):</td>
						<td class="tagCont"><input name="ChineseTitle_Departments"
							type="text" class="ss" title="1" id="ChineseTitle_Department"
							size="35"></td>
					</tr>
					<tr>
						<td class="tagName">External Title (Eng):</td>
						<td class="tagCont"><input
							name="EnglishExternalTitle_Departments" type="text" class="ss"
							title="1" id="EnglishExternalTitle_Department" size="35">
						</td>
						<td class="tagName">External Title (Chi):</td>
						<td class="tagCont"><input
							name="ChineseExternalTitle_Departments" type="text" class="ss"
							title="1" id="ChineseExternalTitle_Department" size="35">
						</td>
					</tr>
					<tr>
						<td class="tagName">Academic & Professional Title(Eng):</td>
						<td class="tagCont" colspan="3"><input
							name="EnglishProfessions" type="text" id="EnglishProfession"
							title="1" size="66" class="ss"> <select
							name="EnglishEducationTitles" class="ss"
							id="EnglishEducationTitle" title="1"></select></td>
					</tr>
					<tr>
						<td class="tagName">Academic & Professional Title (Chi):</td>
						<td class="tagCont" colspan="3"><input
							name="ChineseEducationTitles" type="text" class="ss" title="1"
							id="ChineseEducationTitle" size="66"></td>
					</tr>
					<tr>
						<td class="tagName">T.R.Reg.No:</td>
						<td class="tagCont"><input name="trgs" type="text" class="ss"
							title="1" id="trg" size="30"></td>
						<td class="tagName">CENo:</td>
						<td class="tagCont"><input name="CEs" type="text" class="ss"
							title="1" id="CE" size="35"></td>
					</tr>
					<tr>
						<td class="tagName">MPFNo:</td>
						<td class="tagCont"><input name="MPFs" type="text" class="ss"
							title="1" id="MPF" size="30"></td>
						<td class="tagName">CIB Reg. No:</td>
						<td class="tagCont">
							<input id="HKCIB" name="HKCIBs" type="text" title="1" size="30" class="ss" />
						</td>	
					</tr>
					<tr>
						<td class="tagName">E-Mail:</td>
						<td class="tagCont"><input name="Emails" type="text"
							title="1" class="ss" id="Email" size="35"></td>

						<td class="tagName">DirectLine:</td>
						<td class="tagCont"><input name="DirectLines" type="text"
							title="1" class="ss" id="DirectLine" size="30"></td>
					</tr>
					<tr>
						<td class="tagName">FAX:</td>
						<td class="tagCont"><input name="FAXs" type="text" class="ss"
							title="1" id="FAX" size="35"></td>

						<td class="tagName">MobilePhone:</td>
						<td class="tagCont"><input name="MobilePhones" type="text"
							title="1" class="ss" id="MobilePhone" size="30"></td>
					</tr>
					<tr>		
						<td class="tagName">Quantity:</td>
						<td class="tagCont"><%--<input name="Quantitys" type="text"
							class="ss" id="Quantity" size="5" onKeyUp="checkNum(this);"
							onKeyPress="checkNum(this);" onfocus="$('#numId').show();"
							title="1">--%> 
							<select id="Quantity" name="Quantitys" title="1">
								<option value="0">0--相当于删除</option>
								<option value="100">100</option>
								<option value="200">200</option>
								<option value="300">300</option>
								<option value="400">400</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tagName">Company:</td>
						<td class="tagCont" colspan="3" id="companys"></td>
					</tr>
					<%--<tr>
					<td class="tagCont" colspan="4" style="padding-left: 30px;">
			            <label class="checkbox inline">
			            	<input id="CC" type="checkbox" name="CFS" value="N"  title="1"/>CFS
			            </label>
			            <label class="checkbox inline">
			            	<input id="C" type="checkbox" name="CAM" value="N"   title="1"/>CAM
			            </label>
			            <label class="checkbox inline">
			            	<input id="CCC" type="checkbox" name="CIS" value="N"   title="1"/>CIS
			            </label>
			            <label class="checkbox inline">
			            	<input id="C2" type="checkbox" name="CCL" value="N"   title="1"/>CCL
			            </label>
			            <label class="checkbox inline">
			            	<input id="C3" type="checkbox" name="CFSH" value="N"   title="1"/>CFSH
			            </label>
			            <label class="checkbox inline">
			            	<input id="C4" type="checkbox" name="CMS" value="N"   title="1"/>CMS
			            </label>
			            <label class="checkbox inline">
			            	<input id="C22" type="checkbox" name="CFG" value="N"   title="1"/>CFG
			            </label>
			             <label class="checkbox inline">
			            	<input id="C23" type="checkbox" name="Blank" value="N"   title="1"/>Blank
			            </label>
			            <input id="DD_result" name="DD" type="hidden"/>
					</td>
				</tr>
				--%>
					<tr>
						<td class="tagBtn" colspan="4">
							
							<input id="reQuantity" name="reQuantity" title="1" type="hidden" /> 
							<input id="Payer" name="Payer" title="1" type="hidden" /> 
							<input id="reStaffNo" name="reStaffNo" type="hidden" /> 
							<input id="reUrgent" name="reUrgent" type="hidden" />
							<input id="rePayer" name="rePayer" type="hidden" />
							<input id="refno"  name="refno" type="hidden" />
							<input id="company" name="company" type="hidden" />
							<button class="btn" name="Submit" id="save">Save</button>
							<button class="btn" id="quit" name="exit">Back</button></td>
					</tr>
				</table>
				</form>
			</div>
		</div>
	</div>
	<%--
  			<center> 
    <div id="login" style="top: 143px; left: 525px; width: 199px; height: 75px;">
    <p><img src="css/022.gif" width="32" height="32"></p>
    <p><span style="color:blue; font-family:'隶书';"><span class="STYLE3"><b>正在處理數據，請稍候...</b></span></span></p>
  </div></center>

  --%>

	<script type="text/javascript">
  var company=namecard_consultant_company.split(",");
	var comstring="";
	for ( var i = 0; i < company.length; i++) {
		comstring+="<label class='inline checkbox'><input type='checkbox'  name='companys' value='"+company[i]+"' />"+company[i]+"</label>";
	}
	$("#companys").empty().append(comstring);
	
	function detail(num){
		if(num>=0){
		 	$("#companys").empty().append(comstring);
			$("#details").show();
			$("#lists,#jqajax").hide();
			$("#read").removeAttr("checked");
			$("#StaffNo").attr("disabled","disabled");
			$("#EnglishTitle_Department").get(0).selectedIndex=0;
			$("#EnglishEducationTitle").get(0).selectedIndex=0;
			$("#StaffNo").val(modifyString(downs[num].staff_code));
			$("#UrgentDate").val(modifyString(downs[num].UrgentDate));
			$("#EnglishName").val(modifyString(downs[num].name));
			$("#ChineseName").val(modifyString(downs[num].name_chinese));
			$("#EnglishTitle_Department").val(modifyString(downs[num].title_english));
			$("#ChineseTitle_Department").val(modifyString(downs[num].title_chinese));
			$("#EnglishExternalTitle_Department").val(modifyString(downs[num].external_english));
			$("#ChineseExternalTitle_Department").val(modifyString(downs[num].external_chinese));
			$("#AcademicTitle_e_Department").val(modifyString(downs[num].academic_title_e));
			$("#AcademicTitle_c_Department").val(modifyString(downs[num].academic_title_c));
			$("#EnglishProfession").val(modifyString(downs[num].profess_title_e));
			$("#ChineseEducationTitle").val(modifyString(downs[num].profess_title_c));
			$("#trg").val(modifyString(downs[num].tr_reg_no));
			$("#CE").val(modifyString(downs[num].ce_no)).blur();
			$("#MPF").val(modifyString(downs[num].mpf_no));
			$("#HKCIB").val(modifyString(downs[num].remark1));//HKCIB
			$("#Email").val(modifyString(downs[num].e_mail));
			$("#DirectLine").val(modifyString(downs[num].direct_line));
			$("#FAX").val(modifyString(downs[num].fax));
			$("#MobilePhone").val(modifyString(downs[num].bobile_number));
			$("#Quantity").val(modifyString(downs[num].quantity));
			$("#reQuantity").val(modifyString(downs[num].quantity));
			$("#Payer").val(modifyString(downs[num].Payer));
			$("#rePayer").val(modifyString(downs[num].Payer));
			$("#reStaffNo").val(modifyString(downs[num].staff_code));
			$("#upselect").val(modifyString(downs[num].location));
			$("#reUrgent").val(modifyString(downs[num].urgent));
			$("#urgent").val(modifyString(downs[num].urgent));
			$("#refno").val(modifyString(downs[num].refno));
				if(downs[num].urgent=="Y"){
					$("#urgent").attr("checked","checked");
				}else{
					$("#urgent").removeAttr("checked");
				}
				if(downs[num].ae_consultant=="Y"){
					$("#aeConsultant").attr("checked","checked").val("Y");
				}else{
					$("#aeConsultant").val("N").removeAttr("checked");
				}
				if(downs[num].eliteTeam=="Y"){
					$("#ET").attr("checked","checked").val("Y");
				}else{
					$("#ET").val("N").removeAttr("checked");
				}
				$("input[name='companys']").removeAttr("checked");
				 if(""!=downs[num].company){
				 	var company=downs[num].company.split("+");
				 	for(var i=0;i<company.length;i++){
				 		$("input[name='companys'][value='"+company[i]+"']").attr("checked","checked");
				 	}
				 }
				 
				$("#layout_type").val(downs[num].layout_type);
				$("[title='1']").attr("disabled", "disabled");
				vailDD();
				$("#save").hide();
		}
	 }
	
	
	
	
	function selects(pagenow){
		if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("开始日期不能大于结束日期!");
				$("#start_date").focus();
				return false;
			}
		}
		$.ajax({
			type: "post",
			//url:"QueryRequstServlet",
			url:base + "namecard/NameCardReaderServlet",
			data: {"method":"selectnamecard",'name':$("#name").val(),'code':$("#staffcodes").val(),'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'location':$("#location").val(),'urgentCase':$("#urgentCase").val(),'nocode':$("#nocode").val(),'payer':$("#Namepayer").val(),'ET':$("#ET_select").val(),'layout_select':$("#layout_select").val(),'pagenow':pagenow},
			beforeSend: function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
			success:function(data){
				var dataRole=null;
				var totals=0;
				var html="";
				downs=null;
				var result=null;
				if(data.indexOf("{")==0){
					result=$.parseJSON(data);
					alert(result.msg);
				}else{
					dataRole=eval(data);
					$("tr.select").remove();
					if(dataRole[3]>0){
						total=dataRole[3];
						pagenow =dataRole[2];
					    totalpage=dataRole[1];
						downs=dataRole[0];
					for(var i=0;i<dataRole[0].length;i++){
						html+="<tr class='select'><td><c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='detail("+i+")'>"+"Detail"+
						"</a></c:if></td><td  align='center' >"+dataRole[0][i].staff_code+"</td><td >"
						+dataRole[0][i].name +"</td><td  align='center' >"+dataRole[0][i].name_chinese +"</td><td  >"
						+dataRole[0][i].title_english +"</td><td  >"+dataRole[0][i].title_chinese +"</td><td  >"+dataRole[0][i].remark1 +"</td><td >"
						+dataRole[0][i].tr_reg_no +"</td><td align='center'>"+dataRole[0][i].ce_no +"</td><td align='center'>"+dataRole[0][i].mpf_no 
						+"</td><td align='center'>"+dataRole[0][i].direct_line 
						+"</td><td align='center'>"+ dataRole[0][i].quantity
						+"</td><td  >"+dataRole[0][i].upd_date.substring(0,10)+"</td></tr>";		 
					}
					   $(".page_and_btn").show();
					   $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}else{
						html+="<tr class='select'><td colspan='18' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
						 $(".page_and_btn").hide();
					}
					 $("#jqajax:last").append(html);
				 	 $("tr[id='select']:even").css("background","#COCOCO");
		             $("tr[id='select']:odd").css("background","#F0F0F0");
		             page(pagenow,totalpage);
				}
		 }
	 });
}
	
	function getlocation(select){
		$.ajax({
			type:"post",
			//url:"QueryLocationServlet",
			//data:null,
			url: "common/CommonReaderServlet",
			data:{"method":"getlocation"},
			success:function(date){
			var location=eval(date);
			var html="";
			$("#"+select+"").empty();
			if(location.length>0){
				html+="<option value='' >Select Location...</option>";
				for(var i=0;i<location.length;i++){
					html+="<option value='"+location[i].realName+"' >"+location[i].name+"</option>";
				}
			}else{
				html+="<option value=''>加载异常</option>";
			}
			$("#"+select+"").append(html);
			},error:function(){
				alert("网络连接失败,请返回登录页面重新登录!");
				return false;
			}
		});
	}
	function position(){
		$("#EnglishTitle_Department").empty();
		$.ajax({
			type: "post",
			//url: "QueryPositionServlet",
			//data: null,
			url: "common/CommonReaderServlet",
			data: {"method":"getposition"},
			success: function(date){
			var d=eval(date);
			var html="";
			if(d!=null && d!="null"){
				for(var i=0;i<d.length;i++){
					html+="<option value='"+d[i].position_ename+"'>"+d[i].position_ename+"</option>";
				}
				$("#EnglishTitle_Department:last").append(html);
				}
			}
		});
	}

	function checkStaff(){
		if($("#read").attr("checked")==true){
			if($("#StaffNo").val().length>=5){
				$.ajax({ 
				type: "post", 
				//url: "StaffNoServlet",
				url : base+"namecard/NameCardReaderServlet",
				data : {"StaffNo" : $("#StaffNo").val(),"method":"findbystaffcode"},
				//data: {'StaffNo':$("#StaffNo").val()},
				beforeSend: function(){
					parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				success: function(data){
					if (data != null && data != "{null}" && data != "{}") {
						var result=$.parseJSON(data);
						if(result.staffNo==""){
							 alert("StaffCode不存在！");
							 $("#StaffNo").val($("#reStaffNo").val());
							 $("#save").attr("disabled","disabled");
							 return false;
						}else{
						  $("#save").removeAttr("disabled");
							 return true;
						}
					 } else{
						 alert("StaffCode不存在！");
						 $("#StaffNo").val($("#reStaffNo").val());
						 $("#save").attr("disabled","disabled");
						 return false;
					 }
					} 
				});
		  }	else{
			if($("#StaffNo").val().length!=0){
				alert("Staff Code為非法數據！");
				$("#StaffNo").focus();
				 $("#save").attr("disabled","disabled");
				return false;
			}else{
				alert("Staff Code不能為空！");
				$("#StaffNo").val($("#reStaffNo").val()).focus();
				 $("#save").attr("disabled","disabled");
				return false;
			}
		}
				 
	} else{
		 $("#save").removeAttr("disabled");
	return true;
	}
} 

	
function professional(){
	$("#EnglishEducationTitle").empty();
	var html="";
	html+="<option value=''></option>";
	$.ajax({
		type: "post",
		//url: "QueryProfessionalServlet",
		//data: null,
		url: "<%=basePath%>common/CommonReaderServlet",
		data:{"method":"getprofessional"},
		success: function(date){
		var d=eval(date);
		if(d.length>0){
			for(var i=0;i<d.length;i++){
			html+="<option value='"+d[i].prof_title_ename+"'>"+d[i].prof_title_ename+"</option>";
			}
			$("#EnglishEducationTitle:last").append(html);
			}
		}
	});
}

function vailDD(){
	$.ajax({
		url:"VialDDServlet",
		type:"post",
		data:{"staffcode":$("#StaffNo").val()},
		beforeSend: function(){
			parent.showLoad();
		},
		complete: function(){
			parent.closeLoad();
		},
		success:function(date){
			 if(date=="SUCCESS"){
				DD=true;
				$("#DD_result").val("true");
			 }else{
				 DD=false;
				 $("#DD_result").val("false");
			 }
		},error:function(){
			alert("DD_系统数据异常,请暂停审核并联系管理员!");
			$("[name='Submit']").attr("disabled","disabled");
			return false;
		}
	});
}
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
	
	  function page(currt,total){
			if(currt<=1){
					$("#first").hide();
					$("#pre").hide();
				}else{
					$("#first").show();
					$("#pre").show();
				}
				if(currt>=total){
					$("#end").hide();
					$("#next").hide();
				}else{
					$("#end").show();
					$("#next").show();
				}
	}
	  
  </script>










</body>
</html>