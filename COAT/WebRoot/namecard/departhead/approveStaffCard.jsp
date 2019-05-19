<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查詢頁面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

 	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script src="css/date.js" language="JavaScript"></script>
	<script src="css/Util.js" language="JavaScript"></script>
	<script type="text/javascript" src="plug/js/Common.js"></script>
 	<script type="text/javascript">
 
 
 	
 	 /***********************************************************delete staff message************************/
   		var downs=null;
 	 var basepath="<%=basePath%>";
 	 var pagenow =1;
		var totalpage=1;
		var total=0;
  
 /*****************************************************WindowForm Load *************************************/
 $(function(){
 $("#CCC").click(function(){return false;});
/**********************************窗體加載************************************************/
	professional();
	department();
	selects(1);	
	var professionalCText="";
	var professionalEText="";
	var departmentCText="";
	var departmentEText="";
 	$("#EnglishStaffDepartment").hide();
	$("#EnglishProfessionalTitle").hide();
/****************************************************Search click***********************************/
		$("#searchs").click(function(){
		selects(1);	
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
						alert("Grace can not be greater than the start date end date!");
						$("#start_date").focus();
						return false;
					}
				}
				$.ajax({
				type: "post",
				//url:"QueryDeptRequestConvoyServlet",
				url:"StaffNameCardReaderServlet",
				data: {"method":"deptapprovalselect",'name':$("#name").val(),'code':$("#staffcodes").val(),
					'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),
					'location':$("#location").val(),'urgentCase':$("#urgentCase").val(),
					'ET':$("#ET_select").val(),"layout":$("#layout_select").val(),'payer':"${convoy_username}","status":$("#status").val(),
					'pagenow':pagenow},
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
						var shzt="";
						for(var i=0;i<dataRole[0].length;i++){
						if(dataRole[0][i].shzt=="S")
							shzt="Pending";
						else if(dataRole[0][i].shzt=="E")//等待Department Head 审核
							shzt="Dept Approved";
						else if(dataRole[0][i].shzt=="R")//等待HR 审核
							shzt="HR Approved";
						else if(dataRole[0][i].shzt=="Y")
							shzt="ADM approved";
						else if(dataRole[0][i].shzt=="N")
							shzt="Rejected"
						//&nbsp;<a href='javascript:void(0);' onclick='shanchu("+i+")'>"Delete"+"</a>
							html+="<tr id='select' title='"+i+"'><td align='center'><a href='javascript:void(0);' onclick='modify("+i+")'>"
							+"Detailed"+"</a> </td><td align='center'>"+dataRole[0][i].staff_code+"</td><td align='center'>"
							+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].name_chinese +"</td><td align='center'>"
							+dataRole[0][i].title_english +"</td><td align='center'>"+dataRole[0][i].title_chinese +"</td><td align='center'>"
							//+dataRole[0][i].external_english +"</td><td align='center'>"+dataRole[0][i].external_chinese +"</td><td align='center'>"
							+dataRole[0][i].profess_title_e +"</td><td align='center'>"+dataRole[0][i].profess_title_c +"</td>" 
							//+"<td align='center'>"+dataRole[0][i].tr_reg_no +"</td><td align='center'>"+dataRole[0][i].ce_no +"</td><td align='center'>"+dataRole[0][i].mpf_no 
							//+"</td><td align='center'>"+dataRole[0][i].e_mail +"</td><td align='center'>"+dataRole[0][i].direct_line 
							//+"</td><td align='center'>"+dataRole[0][i].fax +"</td><td align='center'>"+dataRole[0][i].bobile_number 
							+"</td><td align='center'>"+dataRole[0][i].quantity +"</td><td align='center'>"+(dataRole[0][i].UrgentDate.substring(0,10))+"</td><td align='center'>"+shzt+"</td></tr>";		 
						}
							$(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}
					else{
						html+="<tr id='select'><td colspan='18' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
						$(".page_and_btn").hide();
					}
						 $("#jqajax:last").append(html);
					 	 $("tr[id='select']:even").css("background","#COCOCO");
		                 $("tr[id='select']:odd").css("background","#F0F0F0");
		                 	 page(pagenow,totalpage);
				 },error:function(){
					alert("Network connection fails, please try again later ...");
					return false;
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
	
	$("#down").click(function(){
if(downs!=null){
	/**window.location.href="DownStaffRequestServlet?startDate="+
$("#start_date").val()+"&endDate="+$("#end_date").val()+"&name="+$("#name").val()+"&code="+
$("#staffcodes").val()+"&location="+$("#location").val()+"&urgentCase="+$("#urgentCase").val()+"&ET="+$("#ET_select").val();
	**/
	var param="?method=deptdownload&startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&"+
		"code="+$("#staffcodes").val()+"&name="+$("#name").val()+"&location="+$("#location").val()+"&"+
		"urgentCase="+$("#urgentCase").val()+"&nocode="+$("#nocode").val()+"&"+
		"ET="+$("#ET_select").val()+"&payer="+$("#Namepayer").val()+"&layout="+$("#layout_select").val();
	window.location.href=basepath+"/StaffNameCardReaderServlet"+param;
/**	
	$.ajax({
				type: "post",
				url:"DownStaffRequestLocationServlet",
				async:false,
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),
				'code':$("#staffcodes").val(),'name':$("#name").val(),'location':$("#location").val(),
				'urgentCase':$("#urgentCase").val(),'nocode':$("#nocode").val(),
				'ET':$("#ET_select").val(),'payer':$("#Namepayer").val(),"layout":$("#layout_select").val()},
				success:function(date){
					clipboardData.setData('Text',date.substring(date.indexOf(':')+1));
					alert(date);
				},error:function(){
					alert("Export Error!");
				}
			}); **/
}
else{
	alert("Please check the data, do the export related operations!");
	}
	
	});
	/******************************************************Down END***********************************/
$("#back").click(function(){
		  $("#body_view").show();
		  $("#body_modify").hide();
		  return false;
});
	/****************************************pater验证结束***********************/
$("#EnglishPosition").change(function(){
 
	$.ajax({
		type:"post",
		url:"SelectStaffPositionServlet",
		data:{'EnglishName':$("#EnglishPosition").val().replace('\r\n','').replace('\r\n','').replace('\r\n','')},
		success:function (date){
			if(date!="" && date!="null" && date !=null)
				$("#ChinesePosition").val(date);
			else
				$("#ChinesePosition").val("");
		}
		
	});
		
});
	$("#EnglishProfessionalTitle").change(function(){
	$.ajax({
		type:"post",
		url:"SelectProfessionalServlet",
		data:{'EnglishProfessionalTitle':$("#EnglishProfessionalTitle").val()},
		success :function(date){
			 
			if(date!="[]" || date!="null" || date){
				var csd= $("#ChineseProfessionalTitle").val();
				var esd=$("#EnglishProfessionalTitleText").val();
				professionalCText = csd==""?date:(csd+(csd.substring(csd.length-1)==";"?"":";")+date);
				professionalEText = esd==""?$("#EnglishProfessionalTitle").val():(esd+(esd.substring(esd.length-1)==";"?"":";")+$("#EnglishProfessionalTitle").val());
				
				$("#ChineseProfessionalTitle").val(professionalCText);
				$("#EnglishProfessionalTitleText").val(professionalEText);
		 	}
		}
	});
});
	
	/*****************************************************/
$("#EnglishStaffDepartment").change(function(){
	$.ajax({
		type:"post",
		url:"SelectStaffProfessionalServlet",
		data:{'EnglishProfessionalTitle':$("#EnglishStaffDepartment").val()},
		success :function(date){
			if(date!="[]" || date!="null" || date){
				var csd= $("#ChineseStaffDepartmentText").val();
				var esd=$("#EnglishStaffDepartmentText").val();
				departmentCText = csd==""?date:(csd+(csd.substring(csd.length-1)==";"?"":";")+date);
				departmentEText = esd==""?$("#EnglishStaffDepartment").val():(esd+(esd.substring(esd.length-1)==";"?"":";")+$("#EnglishStaffDepartment").val());
				
				$("#ChineseStaffDepartmentText").val(departmentCText);
				$("#EnglishStaffDepartmentText").val(departmentEText);

		 	}
			
		}
		
	});
	
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
/**	
$("#urgentCase").change(function(){
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
				$("#urgentCase").val("Y");
				}else{
						$("#urgentCase").val("N");
				}
});
**/


$("#fujian_list").change(function(){
//	 if(confirm("确定下载查看?")){
			   $("#downs").attr("href","upload/"+$("#fujian_list").val()).empty().append(($("#fujian_list").val().length)>50?($("#fujian_list").val().substring(0,30)+"..."+$("#fujian_list").val().substring($("#fujian_list").val().length-16)):$("#fujian_list").val());
		
			  //window.open("../upload/"+$("#fujian_list").val());
			  //	$("downs").attr("",("../upload/"+$("#fujian_list").val());
		// }
	});


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
  
  function modify(deom){
	  // $("#EnglishPosition option[text='"+downs[deom].title_english.substring(0,title_e_number)+"']").attr("selected",true);
	var title_e_number=downs[deom].title_english.indexOf(";");
	var title_c_number=downs[deom].title_chinese.indexOf(";");
  	$("#body_view").hide();
  	$("#body_modify").show();
  	$("#refno").val(downs[deom].refno);
	$("#StaffNo_modify").val(downs[deom].staff_code);
	$("#num").val(downs[deom].quantity);
	$("#type_modify").val(downs[deom].layout_type);
	$("#EnglishName_modify").val(downs[deom].name);
	$("#ChineseName_modify").val(downs[deom].name_chinese);
	$("#EnglishPosition").val($.trim(downs[deom].title_english.substring(0,title_e_number)));
	$("#ChinesePosition").val(downs[deom].title_chinese.substring(0,title_c_number));
	$("#EnglishStaffDepartmentText").val($.trim(downs[deom].title_english.substring(title_e_number+1))); 
	$("#ChineseStaffDepartmentText").val($.trim(downs[deom].title_chinese.substring(title_c_number+1)));
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
	$("#remarkcons").val(downs[deom].remarkcons);
	$("#EnglishAcademicTitle").val(downs[deom].academic_title_e);
	$("#ChineseAcademicTitle").val(downs[deom].academic_title_c);
	$("#englishExternal").val(downs[deom].external_english);
	$("#chineseExternal").val(downs[deom].external_chinese);
	$("#refno").val(downs[deom].refno);
	if(downs[deom].urgent=="Y"){
		$("#urgent").attr("checked","checked").val("Y");
	}else{
		$("#urgent").val("N").removeAttr("checked");
	}
	 if(downs[deom].eliteTeam=="Y"){
		$("#ET").attr("checked","checked").val("Y");
	}else{
		$("#ET").val("N").removeAttr("checked");
	}
	 
	 
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
		$("input[type='checkbox'][class='check_company'][name='"+companys[i]+"']").attr("checked",true);
	}**/
	/**
	if(downs[deom].CFS_only=="Y"){
		$("#CC").attr("checked","checked").val("Y");
	}else{
		$("#CC").val("N").removeAttr("checked");
		}
	if(downs[deom].CAM_only=="Y"){
		$("#C").attr("checked","checked").val("Y");
	}else{
		$("#C").val("N").removeAttr("checked");
	}
	if(downs[deom].CIS_only=="Y"){
			$("#CCC").attr("checked","checked").val("Y");
	}else{
		$("#CCC").val("N").removeAttr("checked");
	}
	if(downs[deom].CCL_only=="Y"){
		$("#C2").attr("checked","checked").val("Y");
	}else{
		$("#C2").val("N").removeAttr("checked");
	}
	if(downs[deom].CFSH_only=="Y"){
		$("#C3").attr("checked","checked").val("Y");
	}else{
		$("#C3").val("N").removeAttr("checked");
	}
	if(downs[deom].CMS_only=="Y"){
		$("#C4").attr("checked","checked").val("Y");
	}else{
		$("#C4").val("N").removeAttr("checked");
	}
	if(downs[deom].CFG_only=="Y"){
		$("#C22").attr("checked","checked").val("Y");
	}else{
		$("#C22").val("N").removeAttr("checked");
	}
	if(downs[deom].Blank_only=="Y"){
		$("#C23").attr("checked","checked").val("Y");
	}else{
		$("#C23").val("N").removeAttr("checked");
	}
	if(downs[deom].CCIA_only=="Y"){
		$("#C24").attr("checked","checked").val("Y");
	}else{
		$("#C24").val("N").removeAttr("checked");
	}
	if(downs[deom].CCFSH_only=="Y"){
		$("#C25").attr("checked","checked").val("Y");
	}else{
		$("#C25").val("N").removeAttr("checked");
	}
	if(downs[deom].CWMC_only=="Y"){
		$("#C26").attr("checked","checked").val("Y");
	}else{
		$("#C26").val("N").removeAttr("checked");
	}
	**/
	
	
	if(downs[deom].shzt=="S"){
		$("#AddNameCard,#Reject_NameCard").show();
	}else{
		$("#AddNameCard,#Reject_NameCard").hide();
	}
	 fujianlist();
	 /** 2015-07-29 12:40:23
	  *if( parseFloat(("${SystemAcceTime}").substring(11,13))<12){
			alert("Normal processing time is after 12:00 each day.");
			$("[name='Submit']").attr("disabled","disabled");
		 }
		**/	 
  }
  
function vailed(){
	var nums=$("#num").val();                                                      
	if($("#StaffNo_modify").val()==""){																 
		alert("Please input the Staff Code.");	
		$("#StaffNo").focus();
		return false;																			 
	}		
	if($("#ET").attr("checked")==true){
		$("#ET").val("Y");
	}else{
		$("#ET").val("N");
	}
	if($("#num").val()==""){															 
		alert("Please choose 100,200,300 or 400 for “Quantity”.");	
		$("#num").focus();
		return false;																				 
	}	 																				 
	if(isNaN(nums)){		
		alert("Please choose 100,200,300 or 400 for “Quantity”.");	
		$("#num").focus();
		return false;															 
	}else{
		if($("#num").val()%100 !=0){
			alert("Please choose 100,200,300 or 400 for “Quantity”.");
			$("#num").focus();
			return false;
		}	
	}		
/**************************判断事件是否合法*****************/
var datetime=(new Date()).getFullYear()+"-"+((new Date()).getMonth()+1)+"-"+(new Date()).getDate()
/**********************************************************/
/*****************判断复选框是否被选中**********************/
	 
		if($("#urgent").attr("checked")==true){//當urgentCase選中時
				$("#urgent").val("Y");
				if(parseInt($("#num").val())<100 && parseInt($("#num").val())>0){
					alert("In Urgent cases, Quantity can not be less than 100!");
					$("#num").focus();
					return false;
				}
		}
		if($("#location_modify").val()==""){
			alert("Please Choose location!");
			$("#location_modify").focus();
			return false;
		}
		//貌似trim()方法不支持
		 
		if(parseInt($("#num").val())<=0){
			alert("Please choose 100,200,300 or 400 for “Quantity”."); 
			$("#num").focus();
				return false;
		}
		if($("#EnglishPosition").val()=="positionName" || $("#EnglishPosition").val()==null || $("#EnglishPosition").val()==""){
			alert("Please Choose Title_Department in English ！");
			$("#EnglishPosition").focus();
			return false;
		}else{
			var positiontext = $("#EnglishPosition").find("option:selected").text();
			$("#EnglishPositions").val(positiontext);
		}
		if(confirm("Determined by the application?")){
			$("#AddNameCard").attr("disabled","disabled");
			modify_staff();		
		}
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
				html+="<option value='"+d[i].position_ename+"'>"+d[i].position_ename+"</option>";
			}
			$("#EnglishPosition:last").append(html);
			}
		},error:function(){
			alert("Network connection fails, please try again later ...");
			return false;
		}
	});
}
 
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
		},error:function(){
			alert("Network connection fails, please try again later ...");
			return false;
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
		},error:function(){
			alert("Network connection fails, please try again later ...");
			return false;
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
				html+="<option value=''>Abnormal Load</option>";
			}
			$("#"+select+"").append(html);
			},error:function(){
				alert("Network connection fails, please try again later ...");
				return false;
			}
		});
	}
		
		function modify_staff(){
			
				$.ajax({
					//url:"ApproveDepartRequestServlet",
					url:"StaffNameCardWriteServlet",
					type:"post",
					async:false,
					data:$("#myform").serialize(),
					success:function(date){
						var result=$.parseJSON(date);
						if(result.state=="success"){
							 $("#body_view").show();$("#body_modify").hide();
							 $("#AddNameCard").attr("disabled","");
							 $("#searchs").click();
						}
						alert(result.msg);
					},error:function(){
						alert("Network connection fails, please try again later ...");
						return false;
					}
				});
		}
		function reject_staff(){
			  $("#Reject_NameCard").attr("disabled","disabled");
			if(confirm("OK refuse the application?")){
				$.ajax({
					//url:"RejectRequestStaffConvoyServlet",
					url:"StaffNameCardWriteServlet",
					type:"post",
					async:false,
					data:{"method":"reject","role":"Dept","refno":$("#refno").val(),"staffcode":$("#StaffNo_modify").val(),"urgentDate":$("#urgentDate").val(),"remark":$("#remarkcons").val()},
					success:function(date){
					  	var result=$.parseJSON(date);
						if(result.state=="success"){
						  $("#body_view").show();$("#body_modify").hide();
						  $("#Reject_NameCard").attr("disabled","");
						  $("#searchs").click();
					  	}
						alert(result.msg);
					},error:function(){
						alert("Network connection fails, please try again later ...");
						return false;
					}
				});
			}
		}
 
function fujianlist(){
		$.ajax({
				url:"QueryFileNameServlet",
				type:"post",
				data:{"staffcode":$("#StaffNo_modify").val()},
				success:function(date){
					var dataRole=eval(date);
					var html="";
					$("#fujian_list option").remove();
					if(dataRole!="" && dataRole!=null){
						html+="<option>Please Choose option</option>";
						for(var i=0;i<dataRole.length;i++){
								html+="<option  class='oladf'>"+dataRole[i]+"</option>";
						}
					}else{
						html+="<option  class='oladf'>There is no upload records</option>"
					}
					$("#fujian_list").append(html);
				},error:function(){
					alert("Network connection fails, please try again later ...");
					return false;
				}
			});
}
function shanchu(i){
	if(confirm("您确定要删除该记录？不可恢复！")){

			$.ajax({
			type:"post",
			url:"DeleteStaffServlet",
			data:{'StaffNo':downs[i].staff_code,'Date':downs[i].urgentDate},
			success:function(date){
				alert(date);
				$("tr[title='"+i+"']").remove();
			},error:function(){
				alert("Network connection fails, please try again later ...");
				return false;
			}
		 });
	 }
 }
 </script>
  </head>
<body>
<div class="cont-info" id="body_view">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input id="start_date" type="text" readonly="readonly" onClick="Calendar('start_date')" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it" readonly="readonly" onClick="Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input type="text" name="staffcode" id="staffcodes">
				</td>
				<td class="tagName">Full Name：</td>
				<td class="tagCont">
					<input type="text" name="names" id="name">
				</td>
			</tr>
			<tr>
				<td class="tagName">Layout：</td>
				<td class="tagCont">
					<select name="layout_select" id="layout_select">
		                <option value="">Select Layout....</option>
		                <option value="S">Standard layout</option>
		                <option value="P">premium layout</option>
		            </select>
				</td>
				<td class="tagName">New Name Card：</td>
				<td class="tagCont">
					<select name="ET_select" id="ET_select">
					    <option value="">Please Select Elite</option>
					    <option value="Y">Yes</option>
					    <option value="">No</option>
				    </select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location：</td>
				<td class="tagCont"><%--
					<select name="select" id="location"  >
		                <option value="">Please Select Location</option>
		                <option value="O">OIE</option>
		                <option value="C">CP3</option>
		                <option value="W">CWC</option>
		            </select>
				--%>
					<input  name="select" id="location" type="text"/>
				</td>
				<td class="tagName">Urgent Case：</td>
				<td class="tagCont">
					<select name="urgentCase" id="urgentCase">
		                <option value="">Please Select Urgent</option>
		                <option value="Y">Yes</option>
		                <option value="N">No</option>
		            </select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Status:</td>
				<td class="tagCont">
					<select id="status">
            			<option value="">Please Select Status</option>
            			<option value="S" selected="selected">Pending</option><!-- 待审核 -->
            			<option value="E">Dept Approved</option><!-- dept head 已审核 -->
            			<option value="R">HR Approved</option><!-- HR 已审核 -->
            			<option value="Y">Approved</option><!-- SZADM 已审核，已录入正式表 -->
            			<option value="N">Rejected</option><!-- 任何一个环节被rejected都是这个状态 -->
            		</select>
            	</td>
				<td class="tagName"  style="text-align: center;">
					<a class="btn" id="searchs" name="search">
						<i class="icon-search"></i>
						Search
					</a>
					<%--<a class="btn" id="down" name="downs">
						<i class="icon-search"></i>
						Export
					</a>
				--%></td>
				<td class="tagCont"></td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr><%--
				<th class="width_">Operation</th>
				<th class="width_">Staff Code</th>
				<th class="width_">Name</th>
				<th class="width_">Name in Chinese</th>
				<th class="width_">Title with Department in English</th>
				<th class="width_">Title with Department in Chinese</th>
				<th class="width_">English Professional Title</th>
				<th class="width_">Chinese Professional Title</th>
				<th class="width_">Quantity</th>
				<th class="width_">Submit Date</th>
				<th class="width_">Isverify</th>
			--%>
				<th class="width_5">Operation</th>
						<th class="width_5">Staff Code</th>
						<th class="width_10">Name</th>
						<th class="width_8">ChineseName</th>
						<th class="width_15">Position & Department in (Eng)</th>
						<th class="width_15">Position & Department in (Chi)</th>
						<th class="width_8">Professional Title (Eng)</th>
						<th class="width_8">Professional Title (Chi)</th>
						<th class="width_5">Quantity</th>
						<th class="width_8">Submit Date</th>
						<th class="width_15">Isverify</th>
			
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;" >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">First</a>
						<a id="pre" href="javascript:void(0);">Previous</a> Total
						<SPAN style="color: red;" id="total">
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next</a>
						<a id="end" href="javascript:void(0);">End</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<div class="cont-info" id="body_modify" style="display: none;">
	<div class="info-title">
		<div class="title-info">
			<form action="" id="myform" method="post" >
			<table>
				<tr>
					<td class="tagName">Staff Code:</td>
					<td class="tagCont">
						<input id="StaffNo_modify"  readonly="readonly"  name="StaffNos"    type="text" size="35" class="txt" />
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
						<%--<select name="locatins" id="location_modify"></select>
					--%>
					<input type="text" name="locatins" id="location_modify"/>
					</td>
					<td class="tagName">Layout:</td>
					<td class="tagCont">
						<select name="types" id="type_modify">
				        	<option value="S">Standard Layout</option>
				        </select>
					</td>
				</tr>
				<tr>
					<td class="tagName">New Name Card:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input name="ET"  type="checkbox" id="ET" title="1" value="N"/>Yes	
						</label>
					</td>
					<td class="tagName">UrgentCase:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input name="urgent" type="checkbox" id="urgent" title="1" value="N">Yes
						</label>
					</td>
				</tr>
				<tr>
					<td class="tagName">English Name:</td>
					<td class="tagCont">
						<input name="EnglishNames" type="text" size="35" class="txt" id="EnglishName_modify" />
					</td>
					<td class="tagName">Chinese Name:</td>
					<td class="tagCont">
						<input id="ChineseName_modify" name="ChineseNames" type="text" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Position (Eng):</td>
					<td class="tagCont">
						<%--<select name="EnglishPosition" id="EnglishPosition" >
			        <option value="positionName">Please Select PositionName</option>
			        </select>
					--%>
						<input  name="EnglishPosition" id="EnglishPosition" type="text"/>
					
					</td>
					<td class="tagName">Position (Chi):</td>
					<td class="tagCont">
						<input id="ChinesePosition" name="ChinesePositions" type="text" size="35"  class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Department (Eng):</td>
					<td class="tagCont">
						<input id="EnglishStaffDepartmentText" name="EnglishStaffDepartmentTexts" onClick="javascript:$('#EnglishStaffDepartment').show();" type="text" size="35"  class="txt"/>
				        <select id="EnglishStaffDepartment" name="EnglishStaffDepartments">
				        	<option value="department">Please Select Department</option>
				        </select>
					</td>
					<td class="tagName">Department (Chi):</td>
					<td class="tagCont">
						<input id="ChineseStaffDepartmentText" name="ChineseStaffDepartmentTexts" type="text" size="35"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Academic  Title (Eng):</td>
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
						<input id="EnglishProfessionalTitleText" onClick="javascript:$('#EnglishProfessionalTitle').show();" name="EnglishProfessionalTitles" type="text" size="35"  class="txt"/>
				        <select id="EnglishProfessionalTitle" name="EnglishProfessional">
				        	<option value="professional">Please Select ProfessionalTitle</option>
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
					<td class="tagCont">
						<input id="Email" name="Emails" type="text" onKeyUp="this.value=this.value.toLowerCase();" size="35" class="txt" />
					</td>
					<td class="tagName">Attachment:</td>
					<td class="tagCont">
						<select id="fujian_list"></select>
					</td>
				</tr>
				<tr>
					<td class="tagName">Company：</td>
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
					     
						<%--<label class="inline checkbox">
							<input id="CC" type="checkbox" name="CFS" value="Y"   />CFS
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
			 
				
				<tr>
					<td class="tagName">RemarkCons:</td>
					<td class="tagCont" colspan="3">
						<textarea name="remarkcons" id="remarkcons" cols="78" rows="5" style="width: 70%;"></textarea>
					</td>
				</tr>
				<tr>
					<td class="tagBtn" colspan="4">
						<a id="downs"  target="_Blank" ></a>
						<input id="urgentDate" name="UrgentDate" type="hidden"/>
 						<input id="upd_dates" name="upd_date" type="hidden"/>
 						<input id="refno" name="refno" type="hidden"/>
						<input id="pay" type="hidden" name="pays"/> 
						<input name="method" value="deptapproval" type="hidden"/>
						<input id="refno"  name="refno" value="" type="hidden"/>
  						<input id="EnglishPositions" type="hidden" name="EnglishPositions"/>
  						<button class="btn" id="AddNameCard" type="button" >Approve</button>
  						<button class="btn" id="Reject_NameCard" type="button" onclick="reject_staff()" >Reject</button>
  						<button class="btn" id="back" type="button">Back</button>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>