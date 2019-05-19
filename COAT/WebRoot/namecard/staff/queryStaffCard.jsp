<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>QueryStaffCard</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
<link rel="stylesheet" href="./plug/css/font-awesome.min.css">
<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]-->

<link rel="stylesheet" href="./css/layout.css">
<link rel="stylesheet" href="./plug/css/site.css">
<script src="css/date.js" language="JavaScript"></script>
<script src="css/Util.js" language="JavaScript"></script>
<script type="text/javascript">
 	 /***********************************************************delete staff message************************/
   		var downs=null;
 	 var pagenow =1;
		var totalpage=1; 
		var total=0;
    function shanchu(i){
		if(confirm("Are you sure ?")){
	
				$.ajax({
				type:"post",
				url:"DeleteStaffServlet",
				data:{'StaffNo':downs[i].staff_code,'Date':downs[i].UrgentDate},
				success:function(date){
					alert(date);
					$("tr[title='"+i+"']").remove();
				}
			 });
		 }
	 }
    
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


/*窗体加载事件 */
		
 /*****************************************************WindowForm Load *************************************/
 $(function(){
	 $("#CCC").click(function(){return false;});
	 document.onkeydown=BackSpace;
	$("#information [id!='back']").attr("disabled","disabled"); 
	
//$("body").css("width",$("#head_div table",parent.document).width()-$("#left_div",parent.document).width());
//$("#jqajax,#body_view").css("width",$("body").width());
	 window.onresize=function(){
//$("body").css("width",$("#head_div table",parent.document).width()-$("#left_div",parent.document).width());
//$("#jqajax,#body_view").css("width",$("body").width());
	 }
/**********************************窗體加載************************************************/
	position();
	//professional();
	//department();
	var professionalCText="";
	var professionalEText="";
	var departmentCText="";
	var departmentEText="";
 	//getlocation("location");
 	// 	getlocation("location_modify");
	$("#start_date").val(CurentTime());	
	$("#end_date").val(CurentTime());	
 
 		
 		

/****************************************************Search click***********************************/
		$("#searchs").click(function(){
		selects(1);	
		});
/**
 
				if($("#start_date").val()==""){
					alert("開始日期不能為空！");
					$("#start_date").focus();
					return false;
				}	
				if($("#end_date").val()==""){
					alert("結束日期不能為空！");
					$("#end_date").focus();
					return false;
				}**/
				
				
	
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
		$("[type='checkbox'][name!='companys']").click(function(){
		if($(this).attr("checked")){
			$(this).val("Y");
		}else{
			$(this).val("N");
		}
	});
function selects(pagenow){
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("Start Date can’t be later than End Date.");
						$("#start_date").focus();
						return false;
					}
				}
		 	//當ET_select選中時
		 
				$.ajax({
				type: "post",
				//url:"QueryStaffRequestConvoyServlet",
				url:"StaffNameCardReaderServlet",
				data: {"method":"staffselect",'payer':"${convoy_username}",
					'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),
					'location':$("#location").val(),'urgentCase':$("#urgentCase").val(),
					'ET':$("#ET_select").val(),"layout":$("#layout_select").val(),
					'pagenow':pagenow,"status":$("#status").val()},
				beforeSend: function(){
						parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				success:function(data){
					var totals=0;
					var dataRole=eval(data);
					var html="";
					$("tr[id='select']").remove();
					downs=null;
					if(dataRole[3]>0){
						total=dataRole[3];
						pagenow =dataRole[2];
					    totalpage=dataRole[1];
						downs=dataRole[0];
					 
						for(var i=0;i<dataRole[0].length;i++){
							var status="";
							var Details="<a href='javascript:void(0);' onclick='modify("+i+")'>Detail</a>&nbsp;";
							if(dataRole[0][i].shzt =="S"){
								status="<td align='center'>Pending</td>";
								if("STAFF"=="${roleType}"){
									Details+=" &nbsp; <a href='javascript:void(0);' onclick='del("+i+")'>Delete</a>";
								}
							}else if(dataRole[0][i].shzt =="N"){
								status="<td align='center'>Reject</td>";
							}else if(dataRole[0][i].shzt =="E"){
								status="<td align='center'>Dpt Approved</td>";
								
								if("DEPT"=="${roleType}"){
									Details+=" &nbsp; <a href='javascript:void(0);' onclick='del("+i+")'>Delete</a>";
								}
							}else if(dataRole[0][i].shzt =="R"){
								status="<td align='center'>HR Approved</td>";
								if("HR"=="${roleType}"){
									Details+=" &nbsp; <a href='javascript:void(0);' onclick='del("+i+")'>Delete</a>";
								}
							}else if(dataRole[0][i].shzt =="Y"){
								status="<td align='center'>Approved</td>";
							}else if(dataRole[0][i].shzt =="D"){
								status="<td align='center'>Deleted</td>";
							}
							html+="<tr id='select' title='"+i+"'><td align='center'>"+Details+"</td><td align='center'>"+dataRole[0][i].staff_code+"</td><td align='center'>"
							+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].name_chinese +"</td><td align='center'>"
							+dataRole[0][i].title_english +"</td><td align='center'>"+dataRole[0][i].title_chinese +"</td><td align='center'>"
							+dataRole[0][i].profess_title_e +"</td><td align='center'>"+dataRole[0][i].profess_title_c +"</td><td align='center'>"
							+dataRole[0][i].quantity 
							//+"</td><td align='center'>"+dataRole[0][i].Payer
							+"</td><td align='center'>"+(dataRole[0][i].UrgentDate==""?"":(dataRole[0][i].UrgentDate.substring(0,10)))
							+"</td>"+status+"</tr>";		 
						}
						
										html+="<tr  id='select' style='display:none;'><td colspan='12'><span  style='color:red'><strong>Remark:&nbsp;&nbsp;1.These record can be “Delete” before 12:00 pm." +
					"&nbsp;&nbsp;2.“Submit Date” is less than today's record has been freenzer and can't be deleted.</strong></span><strong></td></tr>";
			 $(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}
					else{
						html+="<tr id='select'><td colspan='12' align='center'>"+" Sorry, there is no matching record."+"</td></tr>";
					 $(".page_and_btn").hide();
					}
						 $("#jqajax:last").append(html);
					 	 $("tr[id='select']:even").css("background","#COCOCO");
		                 $("tr[id='select']:odd").css("background","#F0F0F0");
		                 	 page(pagenow,totalpage);
				 }
			 });
					
		}
		/****************************************************************************************************************/

	/***********************************************************Search Click end************************/
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
	
 
	/******************************************************Down END***********************************/
$("#back").click(function(){
		  $("#body_view").show();$("#body_modify").hide();
});
	
	/*****************************************表单提交***************************/
$("#AddNameCard").click(function(){ 
	vailed();
});		
$("body").keydown(function(e){
	if(e.keyCode==13){
	selects(1);
	}
	});
/*****************************表单结束********************************/	
	
 


});
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
  	/**
	 * 删除Namecard request
	 * 2015-08-07 11:45:31
	 * */
	function del(obj){
  		/**	if(comptime(downs[obj].urgentDate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1 && parseFloat(("${SystemAcceTime}").substring(11,13))<12){
		}else if(comptime(downs[obj].urgentDate,"${SystemAcceTime}".substring(0,10)+" 12:00:00")==1 && parseFloat(("${SystemAcceTime}").substring(11,13))>=12){
		}else{
			alert("These record can be “Delete” before 12:00 pm.");
			return false;
		}**/
		if(confirm("Sure to Delete?")){
			$.ajax({
				//url:"DeleteNameCardServlet",
				url:"StaffNameCardWriteServlet",
				type:"post",
				data:{"method":"delstaffnamecard","refno":downs[obj].refno,"urgentDate":downs[obj].UrgentDate,"type":"${roleType}"},
				success:function(date){
					var result=$.parseJSON(date);
					if(result.state=="success"){
						$("#searchs").click();
				  	}
					alert(result.msg);
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
			});
		}
	}
  	
  function modify(deom){
	$("#body_view").hide();$("#body_modify").show();
	$("#StaffNo_modify").val(downs[deom].staff_code);
	$("numId").val(downs[deom].quantity);
	$("#type_modify").val(downs[deom].layout_type);
	$("#EnglishName_modify").val(downs[deom].name);
	$("#ChineseName_modify").val(downs[deom].name_chinese);
	var title_e_number=downs[deom].title_english.indexOf(";");
	var title_c_number=downs[deom].title_chinese.indexOf(";");
	var ep=downs[deom].title_english.substring(0,title_e_number);
	//$("#EnglishPosition").empty();  // 6.清空下拉列表 
	//$("#EnglishPosition option[text='"+ep+"']").attr("selected",true);
	$("#EnglishPosition").val(ep); 
	$("#ChinesePosition").val(downs[deom].title_chinese.substring(0,title_c_number));
	 $("#EnglishStaffDepartmentText").val(downs[deom].title_english.substring(title_e_number+1)); 
	 $("#ChineseStaffDepartmentText").val(downs[deom].title_chinese.substring(title_c_number+1));
	 $("#ChineseAcademicTitle").val(downs[deom].academic_title_c);
	 $("#EnglishAcademicTitle").val(downs[deom].academic_title_e);
	 
	$("#chineseExternal").val(downs[deom].external_chinese);
	$("#englishExternal").val(downs[deom].external_english);
	 $("#remarks").val(downs[deom].remarkcons);
	$("#EnglishProfessionalTitleText").val(downs[deom].profess_title_e);
	 $("#ChineseProfessionalTitle").val(downs[deom].profess_title_c);
	$("#TR_RegNo").val(downs[deom].tr_reg_no);
	$("#CENO").val(downs[deom].ce_no);
	$("#MPFA_NO").val(downs[deom].mpf_no);
	$("#Mobile").val(downs[deom].bobile_number);
	$("#FAX").val(downs[deom].fax);
	$("#DirectLine").val(downs[deom].direct_line);
	$("#Email").val(downs[deom].e_mail);
	$("#upd_dates").val(downs[deom].upd_date);
	$("#urgentDate").val(downs[deom].UrgentDate);
	$("#location_modify").val(downs[deom].location);
	if(downs[deom].urgent=="Y"){
				$("#urgent").attr("checked","checked");
			}else{
				$("#urgent").attr("checked",false);
			}
	 if(downs[deom].eliteTeam=="Y"){
			$("#ET_0").attr("checked","checked").val("Y");
			}else{$("#ET_1").attr("checked","").val("N"); }
	
	
	$.ajax({
			url:"StaffNameCardReaderServlet",
			type:"post",
			data:{"method":"selectCompany","company_code":downs[deom].Company},
			success:function(date){
				var roleDate=eval(date);
				var html="";
				for(var i=0;i<roleDate.length;i++){
					if(html==""){
				    	html+="<input class='check_company' type='checkbox' name='"+downs[deom].Company+"' checked='checked' readonly='readonly' />"+roleDate[i]+"";
					}else{
				    	html+="&nbsp;&nbsp;<input class='check_company' type='checkbox' name='"+downs[deom].Company+"' checked='checked' readonly='readonly' />"+roleDate[i]+"";
					}
				}
				$("#CCC").empty().append(html);
				
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
	});
	 
    
	/**$("input[type='checkbox'][class='check_company']").removeAttr("checked");
	var companys=downs[deom].Company.split("+");
	for(var i=0;i<companys.length;i++){
		$("input[type='checkbox'][class='check_company'][name='"+companys[i]+"']").attr("checked","checked");
	}**/
	
	 /**
	if(downs[deom].CFS_only=="Y"){
				$("#CC").attr("checked","checked").val("Y");
			}else{
				$("#CC").attr("checked","").val("N");
				}
			if(downs[deom].CAM_only=="Y"){
				$("#C").attr("checked","checked").val("Y");
			}else{
				$("#C").attr("checked","").val("N");
				}
			if(downs[deom].CIS_only=="Y"){
					$("#CCC").attr("checked","checked").val("Y");
			}else{
				$("#CCC").attr("checked","").val("N");
				}
			if(downs[deom].CCL_only=="Y"){
				$("#C2").attr("checked","checked").val("Y");
			}else{
				$("#C2").attr("checked","").val("N");
				}
			if(downs[deom].CFSH_only=="Y"){
				$("#C3").attr("checked","checked").val("Y");
			}else{
				$("#C3").attr("checked","").val("N");
				}
			if(downs[deom].CMS_only=="Y"){
				$("#C4").attr("checked","checked").val("Y");
			}else{
				$("#C4").attr("checked","").val("N");
				}
			if(downs[deom].CFG_only=="Y"){
				$("#C22").attr("checked","checked").val("Y");
			}else{
				$("#C22").attr("checked","").val("N");
				}
			if(downs[deom].Blank_only=="Y"){
				$("#C23").attr("checked","checked").val("Y");
			}else{
				$("#C23").attr("checked","").val("N");
				}
			if(downs[deom].CCIA_only=="Y"){
				$("#C24").attr("checked","checked").val("Y");
			}else{
				$("#C24").attr("checked","").val("N");
				}
			if(downs[deom].CCFSH_only=="Y"){
				$("#C25").attr("checked","checked").val("Y");
			}else{
				$("#C25").attr("checked","").val("N");
				}
			if(downs[deom].CWMC_only=="Y"){
				$("#C26").attr("checked","checked").val("Y");
			}else{
				$("#C26").attr("checked","").val("N");
				}
			***/
			
  }
  
 
	 
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
			html+="<option value='"+d[i].id+"'>"+d[i].position_ename+"</option>";
			}
			$("#EnglishPosition:last").append(html);
			}
		}
		
	});
	
}
 
function department(){
	$.ajax({
		type: "post",
		//url: "QueryStaffProfessionalServlet",
		//data: null,
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
function professional(){
	$.ajax({
		type: "post",
		//url: "QueryProfessionalServlet",
		//data: null,
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
		function getlocation(select){
		$.ajax({
			type:"post",
			//url:"QueryLocationServlet",
			//data:null,
			url: "common/CommonReaderServlet",
			data:{"method":"getlocation"},
			async:false,
			success:function(date){
			var location=eval(date);
			var html="";
			$("#"+select+"").empty();
			if(location.length>0){
			 
				html+="<option value='' >Please Select Location</option>";
				for(var i=0;i<location.length;i++){
					html+="<option value='"+location[i].realName+"' >"+location[i].name+"</option>";
			 
				}
			}else{
				html+="<option value=''>Loading Error</option>";
			}
			$("#"+select+"").append(html);
			},error:function(){
				alert("Network connection failure, please return to the login page to log in!");
				return false;
			}
		});
	}
		
 
		

 </script>

</head>
<body>
	<div class="cont-info" id="body_view">
		<div class="info-search">
			<table>
				<tr>
					<td class="tagName">Start Date：</td>
					<td class="tagCont"><input id="start_date" type="text"
						readonly="readonly" onClick="Calendar('start_date')" /> <i
						class="icon-trash icon-large i-trash" id="clear1" align="middle"
						onclick="javascript:$('#start_date').val('');"></i></td>
					<td class="tagName">End Date：</td>
					<td class="tagCont"><input id="end_date" type="text" name="it"
						readonly="readonly" onClick="Calendar('end_date')" /> <i
						class="icon-trash icon-large i-trash" id="clear2" align="middle"
						onclick="javascript:$('#end_date').val('');"></i></td>
				</tr>
				<tr>
					<td class="tagName">Layout：</td>
					<td class="tagCont"><select name="layout_select"
						id="layout_select">
							<option value="">Please Select Layout</option>
							<option value="S">Standard layout</option>
							<option value="P">premium layout</option>
					</select></td>
					<td class="tagName">New Name Card：</td>
					<td class="tagCont"><select name="ET_select" id="ET_select">
							<option value="">Please Select Card Type</option>
							<option value="Y">YES</option>
							<option value="N">NO</option>
					</select></td>
				</tr>
				<tr><%--
					<td class="tagName">Location：</td>
					<td class="tagCont">
					
					<select name="select" id="location">
							<option value="">Please Select Location</option>
							<option value="O">OIE</option>
							<option value="C">CP3</option>
							<option value="W">CWC</option>
					</select>
					
					<input type="text" name="select" id="location"/>
					
					</td>
					--%><td class="tagName">Urgent Case：</td>
					<td class="tagCont"><select name="urgentCase" id="urgentCase">
							<option value="">Please Select Urgent</option>
							<option value="Y">YES</option>
							<option value="N">NO</option>
					</select></td>
					
					
					<td class="tagName">Status:</td>
				<td class="tagCont">
					<select id="status">
            			<option value="">Please Select Status</option>
            			<option value="S" >Pending</option><!-- 待审核 -->
            			<option value="E">Dept Approved</option><!-- dept head 已审核 -->
            			<option value="R">HR Approved</option><!-- HR 已审核 -->
            			<option value="Y">Approved</option><!-- SZADM 已审核，已录入正式表 -->
            			<option value="N">Rejected</option><!-- 任何一个环节被rejected都是这个状态 -->
            		</select>
            	</td>
				</tr>
				<tr>
				<td class="tagName"></td>
				<td class="tagCont">
					
            	</td>
					<td class="tagName" style="text-align: center;"><a
						class="btn" id="searchs" name="search"> <i class="icon-search"></i>
							Search </a></td>
					<td class="tagCont"></td>
				</tr>
			</table>
		</div>
		<div class="info-table">
			<table id="jqajax">
				<thead id="title">
					<tr>
						<th class="width_5">Details</th>
						<th class="width_8">Staff Code</th>
						<th class="width_8">Name</th>
						<th class="width_8">ChineseName</th>
						<th class="width_15">Position & Department in (Eng)</th>
						<th class="width_15">Position & Department in (Chi)</th>
						<th class="width_8">Professional Title (Eng)</th>
						<th class="width_8">Professional Title (Chi)</th>
						<th class="width_5">Quantity</th><%--
						<th class="width_5">Submitter</th>--%>
						<th class="width_8">Submit Date</th>
						<th class="width_15">Status</th>
					</tr>
				</thead>
			</table>
			<div align="center" class="page_and_btn" style="display: none;">
				<table class="main_table" width="100%" border="0" cellpadding="0"
					cellspacing="0" id="select_table">
					<tr class="main_head">
						<td colspan="6" align="center"><a id="first"
							href="javascript:void(0);">first Page</a> <a id="pre"
							href="javascript:void(0);">Previous Page</a> Total <SPAN
							style="color: red;" id="total"> </SPAN>Page <a id="next"
							href="javascript:void(0);">Next Page</a> <a id="end"
							href="javascript:void(0);">Last Page</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="cont-info" id="body_modify" style="display: none;">
		<div class="info-title">
			<div class="title-info">
				<table>
					<tr>
						<td class="tagName">Staff Code:</td>
						<td class="tagCont"><input id="StaffNo_modify"
							readonly="readonly" name="StaffNos" type="text" size="35"
							class="txt" /></td>
						<td class="tagName">Quantity:</td>
						<td class="tagCont"><select id="nums" name="nums">
								<option value="100">100</option>
								<option value="200">200</option>
								<option value="300">300</option>
								<option value="400">400</option>
						</select></td>
					</tr>
					<tr>
						<td class="tagName">Location:</td>
						<td class="tagCont"><%--<select name="locatins"
							id="location_modify"></select>
							--%><input name="locatins"
							id="location_modify" type="text"/>
							</td>
						<td class="tagName">Layout:</td>
						<td class="tagCont"><select name="types" id="type_modify">
								<option value="S">Standard Layout</option>
						</select></td>
					</tr>

					<tr>
						<td class="tagName">New Name Card:</td>
						<td class="tagCont"><label class="inline radio"> <input
								type="radio" name="ET" value="Y" id="ET_0"> YES </label> <label
							class="inline radio"> <input type="radio" name="ET"
								value="N" id="ET_1"> NO </label></td>
						<td class="tagName">Urgent Case:</td>
						<td class="tagCont"><label style="inline checkbox"> <input
								name="urgent2" type="checkbox" id="urgent" title="1" value="N">
						</label></td>
					</tr>
					<tr>
						<td class="tagName">English Name:</td>
						<td class="tagCont"><input name="EnglishNames" type="text"
							size="35" class="txt" id="EnglishName_modify" /></td>
						<td class="tagName">Chinese Name:</td>
						<td class="tagCont"><input id="ChineseName_modify"
							name="ChineseNames" type="text" size="33" class="txt" /></td>
					</tr>
					<tr>
						<td class="tagName">Position (Eng):</td>
						<td class="tagCont">
						
						<%--<select name="EnglishPosition"
							id="EnglishPosition">
								<option value="positionName">Please Select PositionName</option>
						</select>
						
						--%>
							<input name="EnglishPosition" id="EnglishPosition" type="text"/>
						</td>
						<td class="tagName">Position (Chi):</td>
						<td class="tagCont"><input id="ChinesePosition"
							name="ChinesePositions" type="text" size="33" class="txt" /></td>
					</tr>
					<tr>
						<td class="tagName">Department (Eng):</td>
						<td class="tagCont"><input id="EnglishStaffDepartmentText"
							name="EnglishStaffDepartmentTexts" type="text" size="35"
							class="txt" /></td>
						<td class="tagName">Department (Chi):</td>
						<td class="tagCont"><input id="ChineseStaffDepartmentText"
							name="ChineseStaffDepartmentTexts" type="text" size="33"
							class="txt" /></td>
					</tr>

					<tr>
						<td class="tagName">Professional Title (Eng):</td>
						<td class="tagCont"><input id="EnglishProfessionalTitleText"
							name="EnglishProfessionalTitles" type="text" size="35"
							class="txt" /></td>
						<td class="tagName">Professional Title (Chi):</td>
						<td class="tagCont"><input id="ChineseProfessionalTitle"
							name="ChineseProfessionalTitles" type="text" size="33"
							class="txt" /></td>
					</tr>
					<tr>
						<td class="tagName">Academic Title (Eng):</td>
						<td class="tagCont"><input id="EnglishAcademicTitle"
							name="EnglishAcademicTitles" type="text" size="35" class="txt" />
						</td>
						<td class="tagName">Academic Title (Chi):</td>
						<td class="tagCont"><input id="ChineseAcademicTitle"
							name="ChineseAcademicTitles" type="text" size="33" class="txt" />
						</td>
					</tr>
					<tr>
						<td class="tagName">External Title (Eng):</td>
						<td class="tagCont"><input type="text" class="txt" size="35"
							name="englishExternal" id="englishExternal" /></td>
						<td class="tagName">External Title (Chi):</td>
						<td class="tagCont"><input type="text" class="txt"
							name="chineseExternal" id="chineseExternal" size="33" /></td>
					</tr>
					<tr>
						<td class="tagName">TR Reg NO:</td>
						<td class="tagCont"><input id="TR_RegNo" name="TR_RegNos"
							type="text" size="35" class="txt" /></td>
						<td class="tagName">CE NO:</td>
						<td class="tagCont"><input id="CENO" name="CENOs" type="text"
							size="33" class="txt" /></td>
					</tr>
					<tr>
						<td class="tagName">MPFA NO:</td>
						<td class="tagCont"><input id="MPFA_NO" name="MPFA_NOs"
							type="text" size="35" class="txt" /></td>
						<td class="tagName">Mobile:</td>
						<td class="tagCont"><input id="Mobile" name="Mobiles"
							type="text" size="33" class="txt" /></td>
					</tr>
					<tr>
						<td class="tagName">FAX:</td>
						<td class="tagCont"><input id="FAX" name="FAXs" type="text"
							size="35" class="txt" /></td>
						<td class="tagName">Direct Line:</td>
						<td class="tagCont"><input id="DirectLine" name="DirectLines"
							type="text" size="33" class="txt" /></td>
					</tr>
					<tr>
						<td class="tagName">E-Mail:</td>
						<td class="tagCont" colspan="3"><input id="Email"
							name="Emails" type="text"
							onKeyUp="this.value=this.value.toLowerCase();" size="35"
							class="txt" /></td>
					</tr>
					<tr>
						<td class="tagName">Company:</td>
						<td class="tagCont" colspan="3" id="CCC">
							
							<%--<ul>
							  <c:forEach items="${getCompany}" var="co" >
								<li>
							      	<label class="inline checkbox">
										<input class="check_company" type="checkbox" name="${co.company_Type}" />${co.company_Type}
									</label>
								</li>
						      </c:forEach>
						     </ul>--%>
						     
							<%--<label
							class="inline checkbox"> <input id="CC" type="checkbox"
								name="CFS" value="Y" />CFS 
							</label> <label class="inline checkbox">
								<input id="C" type="checkbox" name="CAM" value="N" />CAM </label> <label
							class="inline checkbox"> <input id="CCC" type="checkbox"
								name="CIS" value="N" />CIS </label> <label class="inline checkbox">
								<input id="C2" type="checkbox" name="CCL" value="N" />CCL </label> <label
							class="inline checkbox"> <input id="C3" type="checkbox"
								name="CFSH" value="N" />CFSH </label> <label class="inline checkbox">
								<input id="C4" type="checkbox" name="CMS" value="N" />CMS </label> <label
							class="inline checkbox"> <input id="C22" type="checkbox"
								name="CFG" value="N" />CFG </label> <label class="inline checkbox">
								<input id="C24" type="checkbox" name="CCIA" value="N" />CCIA </label> <label
							class="inline checkbox"> <input id="C25" type="checkbox"
								name="CCFSH" value="N" />CCFSH </label> <label class="inline checkbox">
								<input id="C26" type="checkbox" name="CWMC" value="N" />CWMC </label> <input
							id="pay" type="hidden" name="pays" /> <input
							id="EnglishPositions" type="hidden" name="EnglishPositions" />--%>
						</td>
					</tr>
					<tr>
						<td class="tagName">Remarks:</td>
						<td class="tagCont" colspan="3"><textarea name="remarks"
								id="remarks" cols="70" rows="5" style="width: 70%;"></textarea>
						</td>
					</tr>
					<tr>
						<td class="tagBtn" colspan="4"><input
							id="urgentDate" name="UrgentDate" type="hidden" /> <input
							id="upd_dates" name="upd_date" type="hidden" />
							<button class="btn" id="back">Back</button></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>