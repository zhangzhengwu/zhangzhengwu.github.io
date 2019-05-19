<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML>
<html>
  <head>
      <base href="<%=basePath%>">
    <title>查詢頁面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

 <script type="text/javascript">
		var downs=null;
		var locations=null;
		var pagenow =1;
		var totalpage=1;
		var total=0;
		/*************************************************获取弹出层的位置*************************/
		      function popupDiv() {  
     // 设计弹出层的位置
		var screenwidth, screenheight, mytop, getPosLeft, getPosTop,lh
		screenwidth = $(window).width();
        screenheight = $(window).height();
        mytop = $(document).scrollTop();
        getPosLeft = screenwidth / 2 - 555;
        getPosTop = screenheight / 2 - 500;
        ll=screenwidth / 2 - 545;
        lh= screenheight / 2 ;
      //  $("#details").css({ "left": getPosLeft, "top": getPosTop + mytop });
     //   $("#moti").css({"left":ll,"top":getPosTop+lh});
        }  
		/******************************************************************************************/
		/***************************************加载location下拉框***************************************************/
		function getappend(id){
			var html="";
			$(id).empty();
			if(locations.length>0){
			html+="<option value=''>Select Location...</option>"
				for(var i=0;i<locations.length;i++){
					html+="<option value='"+locations[i].realName+"' >"+locations[i].name+"</option>";
				}
			}else{
					html+="<option value=''>加载异常</option>";
			}
			$(id).append(html);
		}
		/******************************************************************************************/
		function getlocation(){
		$.ajax({
			type:"post",
			//url:"QueryLocationServlet",
			//data:null,
			url: "common/CommonReaderServlet",
			data:{"method":"getlocation"},
			success:function(date){
			var location=eval(date);
					locations=location;
			 getappend("#location");
			},error:function(date){
					alert(date);
					return false;
			}
		});
	}
	/***********************************************************获取PositionName以及ProfessionalTitle********************/
	function position(){
			$("#EnglishTitle_Department").empty();
	$.ajax({
		type: "post",
		//url: "QueryPositionServlet",
		//data: null,
		url: "<%=basePath%>common/CommonReaderServlet",
		data:{"method":"getposition"},
		success: function(date){
		var d=eval(date);
		var html="";
		if(d.length>0){
			for(var i=0;i<d.length;i++){
				html+="<option value='"+d[i].position_ename+"'>"+d[i].position_ename+"</option>";
			}
			$("#EnglishTitle_Department:last").append(html);
			}
		}
	});
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
	/*******************************************************************************************************************/
	function detail(num){
	if(num>=0){
	       // popupDiv(); 
	        getappend("#upselect");
		    $("#details").show();
		    $('#lists').hide();
		    $("#jqajax").hide();
		    $("#read").attr("checked","");
		    $("#StaffNo").attr("disabled","disabled");
		    $("#EnglishTitle_Department").get(0).selectedIndex=0;
		    $("#EnglishEducationTitle").get(0).selectedIndex=0;
			$("#StaffNo").val(downs[num].staff_code);
			$("#UrgentDate").val(downs[num].urgentDate);
			$("#EnglishName").val(downs[num].name);
			$("#ChineseName").val(downs[num].name_chinese);
			$("#EnglishTitle_Department").val(downs[num].title_english);
			$("#ChineseTitle_Department").val(downs[num].title_chinese);
			$("#EnglishExternalTitle_Department").val(downs[num].external_english);
			$("#ChineseExternalTitle_Department").val(downs[num].external_chinese);
			$("#AcademicTitle_e_Department").val(downs[num].academic_title_e);
			$("#AcademicTitle_c_Department").val(downs[num].academic_title_c);
			$("#EnglishProfession").val(downs[num].profess_title_e);
			$("#ChineseEducationTitle").val(downs[num].profess_title_c);
			$("#trg").val(downs[num].tr_reg_no);
			$("#CE").val(downs[num].ce_no);
			$("#MPF").val(downs[num].mpf_no);
			$("#Email").val(downs[num].e_mail);
			$("#DirectLine").val(downs[num].direct_line);
			$("#FAX").val(downs[num].fax);
			$("#MobilePhone").val(downs[num].bobile_number);
			$("#Quantity").val(downs[num].quantity);
			$("#reQuantity").val(downs[num].quantity);
			$("#Payer").val(downs[num].payer);
			$("#rePayer").val(downs[num].payer);
			$("#reStaffNo").val(downs[num].staff_code);
			$("#upselect").val(downs[num].location);
		if(downs[num].urgent=="Y"){
			$("#urgent").attr("checked","checked");
		}else{
			$("#urgent").attr("checked",false);
			}
	   if(downs[num].ae_consultant=="Y"){
			$("#aeConsultant").attr("checked","checked").val("Y");
		}else{$("#aeConsultant").attr("checked","").val("N");}
	   	 if(downs[num].eliteTeam=="Y"){
			$("#ET").attr("checked","checked").val("Y");
			}else{$("#ET").attr("checked","").val("N");}
	   if(downs[num].CFS_only=="Y"){
 		    $("#CC").attr("checked","checked").val("Y"); 
		}else{$("#CC").attr("checked","").val("N");}
	   if(downs[num].CAM_only=="Y"){
			$("#C").attr("checked","checked").val("Y");
		}else{$("#C").attr("checked","").val("N");}
	   if(downs[num].CIS_only=="Y"){
			$("#CCC").attr("checked","checked").val("Y");
		}else{$("#CCC").attr("checked","").val("N");}
	   if(downs[num].CCL_only=="Y"){
			$("#C2").attr("checked","checked").val("Y");
		}else{$("#C2").attr("checked","").val("N");}
	   if(downs[num].CFSH_only=="Y"){
			$("#C3").attr("checked","checked").val("Y");
		}else{$("#C3").attr("checked","").val("N");}
	   if(downs[num].CMS_only=="Y"){
			$("#C4").attr("checked","checked").val("Y");
		}else{$("#C4").attr("checked","").val("N");}
	   if(downs[num].CFG_only=="Y"){
			$("#C22").attr("checked","checked").val("Y");
		}else{$("#C22").attr("checked","").val("N");}
	   if(downs[num].Blank_only=="Y"){
			$("#C23").attr("checked","checked").val("Y");
		}else{$("#C23").attr("checked","").val("N");}
	   if(downs[num].CIB_only=="Y"){
			$("#C24").attr("checked","checked").val("Y");
		}else{$("#C24").attr("checked","").val("N");}
			$("#layout_type").val(downs[num].layout_type);
			$("[title='1']").attr("disabled", "disabled");
			$("#save").hide();
	}
 }
 /*****************************************************WindowForm Load *************************************/
 $(function(){
	 	position();
     	professional();
     	getlocation();
	    $("#EnglishEducationTitle").hide();
 		$("#start_date").val(CurentTime());	
 		$("#end_date").val(CurentTime());	
		/****************************************************************Motify click*****************************/
		$("#motify").click(function(){
		$("#messages").empty();
		$("#messages").val("信息修改").html("信息修改");
		$("[title='1']").removeAttr("disabled");
		$("#save").show();
		});		
/****************************************************************************************************************/
/******************************************退出詳細頁面*************************************************************/
$("#quit").click(function(){
		//$("body").css("overflow","");
		$("#jqajax").show();
		$("#details").hide();
		$('#lists').show();
});
/****************************************************************************************************************/
/****************************************************Search click***********************************/
		$("#searchs").click(function(){
		selects(1);
			});
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
var professionalCText="";
var professionalEText=""
	$("#EnglishEducationTitle").change(function(){
	$.ajax({
		type:"post",
		url:"SelectProfessionalServlet",
		data: {'EnglishProfessionalTitle':$("#EnglishEducationTitle").val()},
		success :function(date){
			if(date!="" || date!="null" || date){
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
		if($("#urgent").attr("checked")==true){
			urgent="Y";
			if(parseInt($("#Quantity").val())<200){
				if(!confirm("在UrgentCase情況下Quantity不能低於200!确定要提交？")){
					$("#Quantity").focus();
					return false;
				}
			}
		}else{urgent="N";}
if($("#reStaffNo").val()!= $("#StaffNo").val()){
	if(!confirm("警告！您正在修改顾问編號！確定繼續嗎？")){
		return false;
	}
		$("#Payer").val($("#StaffNo").val());
}  
if(parseInt($("#Quantity").val())==0){
	if("${roleObj.delete}"!=1){
		alert("对不起，您目前还没有删除数据的权限或登录信息已丢失!");
		return false;
	}
}
		$.ajax({
			type:"post",
			url:"MotifyMacauServlet",
			data:{'aeConsultant':$("#aeConsultant").val(),'location':$("#upselect").val(),'rePayer':$("#rePayer").val(),'reStaffNo':$("#reStaffNo").val(),'reQuantity':$("#reQuantity").val(),'urgent':urgent,'Payer':$("#Payer").val(),'layout_type':$("#layout_type").val(),'StaffNo':$("#StaffNo").val(),'EnglishName':$("#EnglishName").val(),'ChineseName':$("#ChineseName").val(),'EnglishTitle_Department':$("#EnglishTitle_Department").val(),'ChineseTitle_Department':$("#ChineseTitle_Department").val(),'EnglishExternalTitle_Department':$("#EnglishExternalTitle_Department").val(),'ChineseExternalTitle_Department':$("#ChineseExternalTitle_Department").val(),'AcademicTitle_e_Department':$("#AcademicTitle_e_Department").val(),'AcademicTitle_c_Department':$("#AcademicTitle_c_Department").val(),'EnglishEducationTitle':$("#EnglishProfession").val(),'ChineseEducationTitle':$("#ChineseEducationTitle").val(),'trg':$("#trg").val(),'CE':$("#CE").val(),'MPF':$("#MPF").val(),'Email':$("#Email").val(),'DirectLine':$("#DirectLine").val(),'FAX':$("#FAX").val(),'MobilePhone':$("#MobilePhone").val(),'UrgentDate':$("#UrgentDate").val(),'Quantity':$("#Quantity").val(),
			'CFS':$("#CC").val(),'CAM':$("#C").val(),'CIS':$("#CCC").val(),'CCL':$("#C2").val(),'CFSH':$("#C3").val(),'CMS':$("#C4").val(),'CFG':$("#C22").val(),'Blank':$("#C23").val(),'CIB':$("#C24").val(),'ET':$("#ET").val()},
			success:function(data){
				alert(data);
				$("#start_date").val(CurentTime());	
	 			$("#end_date").val(CurentTime());	
	 			$("#details").hide();
	 			$('#lists').show();
	 		    $("#jqajax").show();
 				selects(pagenow);
			}
		});
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
		$("#StaffNo").attr("disabled","disabled");
	}else{
		$("#StaffNo").removeAttr("disabled");
	}
});
	/*********************************************************************************************/
/*********************************改变时间格式*********************************************/
	function changeDate2(){

	var date=$("#end_date").val();
	var pattern=/^(19|20)\d{2}-(0?\d|1[012])-(0?\d|[12]\d|3[01])$/;
	var year=date.substr(0,4);
	var index1=date.indexOf("-"); 
	var index2=date.lastIndexOf("-");  
	var cha=parseInt(index2)-(parseInt(index1)+1); 
	var month=date.substr((parseInt(index1)+1),cha);
	var day=date.substr(index2+1,date.length);
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
	if(pattern.test($("#end_date").val())){
		$("#end_date").val(year+ca+parseInt(month)+ba+parseFloat(day));
	}
}
function changeDate1(){

	var date=$("#start_date").val();
	var pattern=/^(19|20)\d{2}-(0?\d|1[012])-(0?\d|[12]\d|3[01])$/;
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
	if(parseFloat(day)<10){
		ba="-0";
	}else{
		ba="-";
	}
	if(pattern.test($("#start_date").val())){
		$("#start_date").val(year+ca+parseInt(month)+ba+parseFloat(day));
	}
}
/************************************复选框的值发生改变时**********************************************************************/
$("[type='checkbox']").change(function(){
	/****************判断复选框是否被选中********************/
	if($("#aeConsultant").attr("checked")==true){
		$("#aeConsultant").val("Y");
	}else{
		$("#aeConsultant").val("N");
	}
	if($("#ET").attr("checked")==true){
		$("#ET").val("Y");
	}else{
		$("#ET").val("N");
	}
	if($("#CC").attr("checked")==true){
		$("#CC").val("Y");
	}else{
		$("#CC").val("N");
		}
	if($("#C").attr("checked")==true){
		$("#C").val("Y");
	}else{
		$("#C").val("N");
		}
	if($("#CCC").attr("checked")==true){
		$("#CCC").val("Y");
	}else{
		$("#CCC").val("N");
		}
	if($("#C2").attr("checked")==true){
		$("#C2").val("Y");
	}else{
		$("#C2").val("N");
		}
	if($("#C3").attr("checked")==true){
		$("#C3").val("Y");
		}else{
			$("#C3").val("N");
	}if($("#C4").attr("checked")==true){
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
		}
 });
/***************************************************************************************************************************/

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


/**************************************************************************************/
function selects(pagenow){
		/**if($("#start_date").val()==""){
			alert("開始日期不能為空！");
			$("#start_date").focus();
			return false;
		}	
		if($("#end_date").val()==""){
			alert("結束日期不能為空！");
			$("#end_date").focus();
			return false;
		}
			changeDate1();
			changeDate2();**/
		if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("SubmitDate is not greater than EndDate!");
				$("#start_date").focus();
				return false;
			}
		}
	 
					$.ajax({
					type: "post",
					url:"QueryMacauServlet",
					data: {'name':$("#name").val(),'code':$("#staffcodes").val(),'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'location':$("#location").val(),'urgentCase':$("#urgentCase").val(),'ET':$("#ET_select").val(),'layout_select':$("#layout_select").val(),'pageNow':pagenow},
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
							html+="<tr id='select'><td align='center'>"+dataRole[0][i].staff_code+"</td><td align='center'>"
							+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].name_chinese +"</td><td align='center'>"
							+dataRole[0][i].title_english +"</td><td align='center'>"+dataRole[0][i].title_chinese +"</td><td align='center'>"

							+dataRole[0][i].tr_reg_no +"</td><td align='center'>"+dataRole[0][i].ce_no +"</td><td align='center'>"+dataRole[0][i].mpf_no 
							+"</td><td align='center'>"+dataRole[0][i].e_mail +"</td><td align='center'>"+dataRole[0][i].direct_line 
							+"</td><td align='center'>"+dataRole[0][i].fax +"</td><td align='center'>"+ dataRole[0][i].quantity
							+"</td><td align='center'>"+ dataRole[0][i].bobile_number +"</td><td>" +
							"<c:if test='${roleObj.upd==1}'><a href='javascript:void(0);' onclick='detail("+i+")'>"+"详细"+"</a></c:if></td></tr>";		 
							}
							 $(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
						}else{
							html+="<tr id='select'><td colspan='18' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
						 $(".page_and_btn").hide();
					}
							 $("#jqajax:last").append(html);
						 	 $("tr[id='select']:even").css("background","#COCOCO");
			                 $("tr[id='select']:odd").css("background","#F0F0F0");
			                 	 page(pagenow,totalpage);
					 }
				 });
}
	/*****************************************************Down click**********************************/
	$("#down").click(function(){
		if(downs!=null){
			window.location.href="<%=basePath%>DownMacauServlet?name="+$("#name").val()+"&code="+$("#staffcodes").val()+"&location="+$("#location").val()+"&startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&urgentCase="+$("#urgentCase").val()+"&ET="+$("#ET_select").val()+"&layout_select="+$("#layout_select").val();
		}else{
			alert("请先查询数据，再做导出相关操作！");
			}
	});
	/******************************************************Down END***********************************/
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
 </script>
  </head>
<body style="width: 100%;height: 100%;">
<div class="cont-info" id="lists">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Submit Date：</td>
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
				<td class="tagName">Staff Name：</td>
				<td class="tagCont">
					<input type="text" name="names" id="name">
				</td>
			</tr>
			<tr>
				<td class="tagName">Location：</td>
				<td class="tagCont">
					<select name="select" id="location"  >
		                <option value=" ">Select Location...</option>
		                <option value="O">OIE</option>
		                <option value="C">CP3</option>
		                <option value="W">CWC</option>
		            </select>
				</td>
				<td class="tagName">Urgent Case：</td>
				<td class="tagCont">
					<select id="urgentCase">
						<option value="">请选择...</option>
						<option value="Y">YES</option>
						<option value="N">NO</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Layout：</td>
				<td class="tagCont">
					<select name="layout_select" id="layout_select">
		                <option value="">请选择....</option>
		                <option value="S">Standard layout</option>
		                <option value="P">premium layout</option>
		            </select>
				</td>
				<td class="tagName">Elite Team：</td>
				<td class="tagCont">
					<select id="ET_select">
						<option value="">请选择...</option>
						<option value="Y">YES</option>
						<option value="N">NO</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tagCont" colspan="2"></td>
				<td class="tagCont" colspan="2">
					<c:if test='${roleObj.search==1}'>
						<a class="btn" id="searchs" name="search">
							<i class="icon-search"></i>
							Search
						</a>
					</c:if>
					<c:if test='${roleObj.export==1}'>
						<a class="btn" id="down" name="downs">
							<i class="icon-download"></i>
							Export
						</a>
					</c:if>	
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead>
			<tr id="title">
				<th class="width_">Staff Code</th>
				<th class="width_">Name</th>
				<th class="width_">Name in Chinese</th>
				<th class="width_">Title with Dept in English</th>
				<th class="width_">Title with Dept in Chinese</th>
				<th class="width_">T.R. Reg. No.</th>
				<th class="width_">CE No.</th>
				<th class="width_">MPF No.</th>
				<th class="width_">E-mail</th>
				<th class="width_">Direct Line</th>
				<th class="width_">Fax</th>
				<th class="width_">Quantity</th>
				<th class="width_">Mobile</th>
				<th class="width_">Detail</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;"  >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">首页</a>
						<a id="pre" href="javascript:void(0);">上一页</a> 总共
						<SPAN style="color: red;" id="total">
						</SPAN>页
						<a id="next" href="javascript:void(0);">下一页</a>
						<a id="end" href="javascript:void(0);">尾页</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<div class="cont-info" id="details" style="display: none;">
	<div class="info-title">
		<div class="title-info">
			<table>
				<tr>
					<td class="tagName">Staff Code:</td>
					<td class="tagCont">
						<input name="StaffCode" type="text" class="ss" id="StaffNo" size="15" disabled="disabled" />
						<label class="inline checkbox">
							<input id="read" type="checkbox" title="1"/>修改code	
						</label>
						<input id="UrgentDate" name="UrgentDates" type="hidden"/>
					</td>
					<td class="tagName"><a href="javascript:void(0);" id="motify" style="size='16px'; font-family:'仿宋'; color:#FF0000">修改</a></td>
					<td class="tagCont">
						
					</td>
				</tr>
				<tr>
					<td class="tagName">AE Consultant:</td>
					<td class="tagCont">
						<input name="checkbox2"  type="checkbox" id="aeConsultant" title="1" value="N"/>
					</td>
					<td class="tagName">UrgentCase:</td>
					<td class="tagCont">
						<input name="checkbox" type="checkbox" id="urgent" title="1" value="N">
					</td>
				</tr>
				<tr>
					<td class="tagName">Elite Team:</td>
					<td class="tagCont" colspan="3">
						<input name="checkbox2"  type="checkbox" id="ET" title="1" value="N"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Layout:</td>
					<td class="tagCont">
						<select id="layout_type" title="1" >
					        <option value="S">Standard Layout</option>
					        <option value="P">Perminm Layout</option>
			        	</select>
					</td>
					<td class="tagName">Location:</td>
					<td class="tagCont">
						<select name="select2" id="upselect" title="1" >
		        			<option value="">Select Location...</option>
				            <option value="O">OIE</option>
				            <option value="C">CP3</option>
				            <option value="W">CWC</option>
                        </select>
					</td>
				</tr>
				<tr>
					<td class="tagName">EnglishName:</td>
					<td class="tagCont">
						<input type="text" name="EnglishNames" id="EnglishName" class="ss"  title="1">
					</td>
					<td class="tagName">ChineseName:</td>
					<td class="tagCont">
						<input type="text" name="ChineseNames" id="ChineseName" class="ss"  title="1">
					</td>
				</tr>
				<tr>
					<td class="tagName">Department in English:</td>
					<td class="tagCont">
						<select name="EnglishTitle_Departments"  class="ss"  title="1" id="EnglishTitle_Department" ></select>
					</td>
					<td class="tagName">Department in Chinese:</td>
					<td class="tagCont">
						<input name="ChineseTitle_Departments" type="text" class="ss"  title="1" id="ChineseTitle_Department" size="35">
					</td>
				</tr>
				<tr>
					<td class="tagName">ExternalTitle_Department_English:</td>
					<td class="tagCont">
						<input name="EnglishExternalTitle_Departments" type="text" class="ss"  title="1" id="EnglishExternalTitle_Department" size="35">
					</td>
					<td class="tagName">ExternalTitle_Department_Chinese:</td>
					<td class="tagCont">
						<input name="ChineseExternalTitle_Departments" type="text" class="ss"  title="1" id="ChineseExternalTitle_Department" size="35">
					</td>
				</tr>
				<tr>
					<td class="tagName">AcademicTitle_Department_English:</td>
					<td class="tagCont">
						<input name="EnglishExternalTitle_Departments" type="text" class="ss"  title="1" id="AcademicTitle_e_Department" size="35">
					</td>
					<td class="tagName">AcademicTitle_Department_Chinese:</td>
					<td class="tagCont">
						<input name="ChineseExternalTitle_Departments" type="text" class="ss"  title="1" id="AcademicTitle_c_Department" size="35">
					</td>
				</tr>
				<tr>
					<td class="tagName">Education_Title_English:</td>
					<td class="tagCont" colspan="3">
						<input name="EnglishProfessions" type="text" id="EnglishProfession" title="1" size="66" class="ss">
						<select  name="EnglishEducationTitles"  class="ss"   id="EnglishEducationTitle"  title="1">
         				</select>
					</td>
				</tr>
				<tr>
					<td class="tagName">Education_Title_Chinese:</td>
					<td class="tagCont" colspan="3">
						<input name="ChineseEducationTitles" type="text" class="ss"  title="1" id="ChineseEducationTitle" size="66">
					</td>
				</tr>
				<tr>
					<td class="tagName">T.R.Reg.No:</td>
					<td class="tagCont">
						<input name="trgs" type="text" class="ss"  title="1" id="trg" size="30">
					</td>
					<td class="tagName">CENo:</td>
					<td class="tagCont">
						<input name="CEs" type="text" class="ss"  title="1" id="CE" size="35">
					</td>
				</tr>
				<tr>
					<td class="tagName">MPFNo:</td>
					<td class="tagCont">
						<input name="MPFs" type="text" class="ss"  title="1" id="MPF" size="30">
					</td>
					<td class="tagName">E-Mail:</td>
					<td class="tagCont">
						<input name="Emails" type="text"  title="1" class="ss" id="Email" size="35">
					</td>
				</tr>
				<tr>
					<td class="tagName">DirectLine:</td>
					<td class="tagCont">
						<input name="DirectLines" type="text"  title="1" class="ss" id="DirectLine" size="30">
					</td>
					<td class="tagName">FAX:</td>
					<td class="tagCont">
						<input name="FAXs" type="text" class="ss"  title="1" id="FAX" size="35">
					</td>
				</tr>
				<tr>
					<td class="tagName">MobilePhone:</td>
					<td class="tagCont">
						<input name="MobilePhones" type="text" title="1" class="ss" id="MobilePhone" size="30">
					</td>
					<td class="tagName">Quantity:</td>
					<td class="tagCont">
						<input name="Quantitys" type="text" class="ss" id="Quantity" size="5" onKeyUp="checkNum(this);" onKeyPress="checkNum(this);" onFocus="$('#numId').show();" title="1">
			         	<select id="numId" name="nums" style="display: none;" onChange=" $('#Quantity').val($(this).val()) ;">
				            <option value="100">100</option>
				            <option value="200">200</option>
				            <option value="300">300</option>
				            <option value="400">400</option>
			            </select>
					</td>
				</tr>
				<tr>
					<td class="tagName"></td>
					<td class="tagCont" colspan="3">
						<label class="checkbox inline">
							<input id="C24" type="checkbox" name="CIB" value="N"   title="1"/>CIB
						</label>
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
					</td>
				</tr>
				<tr style="height: 30px;"></tr>
				<tr>
					<td class="tagBtn" colspan="4">
						<input id="Payer" title="1" type="hidden"/>
			          	<input id="reQuantity" title="1" type="hidden"/>
			         	<input id="reStaffNo" type="hidden"/>
			         	<input id="rePayer" type="hidden"/>
			         	<button class="btn" name="Submit" id="save">保 存</button>
			         	<button class="btn" id="quit" name="exit">返 回</button>            
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>