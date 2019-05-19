<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'queryStaffCard_modify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<style type="text/css">
	.inline.checkbox{*margin-bottom: 10px!important;}
	</style>
  </head>
  
<body>
<div class="form-table">
	<form action="" id="myform" method="post" onSubmit="return modify_staff">
	<table class="col-4-table">
		<tr>
			<td class="tagName">Staff Code:</td>
			<td class="tagCont">
				<input id="StaffNo_modify"  readonly="readonly" style="background-color:silver" name="StaffNos"    type="text" size="28" class="txt" />
			</td>
			<td class="tagName">quantity:</td>
			<td class="tagCont">
				<select id="num" name="nums"   onChange=" $('#num').val($(this).val()) ;">
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
				<input  name="locatins" id="location_modify" type="text"/>
			</td>
			<td class="tagName">Layout:</td>
			<td class="tagCont">
				<select name="types" id="type_modify">
			        <option value="S">Standard Layout</option>
			        <option value="P">Premium Layout</option>
		    	</select>
			</td>
		</tr>
		<tr>
			<td class="tagName">New Name Card:</td>
			<td class="tagCont">
				<label class="inline checkbox">
					<input name="ET"  type="checkbox" id="ET" title="1" value="N"/>YES
				</label>
			</td>
			<td class="tagName">UrgentCase:</td>
			<td class="tagCont">
				<label class="inline checkbox">
					<input name="urgent" type="checkbox" id="urgent" title="1" value="N">YES
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
			<td class="tagName">Position in (Eng):</td>
			<td class="tagCont">
				<%--<select name="EnglishPositions" id="EnglishPosition" >
			        <option value="positionName">請選擇PositionName</option>
			    </select>
			--%>
			<input type="text" name="EnglishPositions" id="EnglishPosition"/>
			
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
			<td class="tagCont">
				<input id="Email" name="Emails" type="text" onKeyUp="this.value=this.value.toLowerCase();" size="35" class="txt" />
			</td>
			<td class="tagName"></td>
			<td class="tagCont">
				
			</td>
		</tr>
		<tr>
			<td class="tagName">Company:</td>
			<td class="tagCont" colspan="3">
				<ul>
					  <c:forEach items="${getCompany}" var="co" >
						<li>
					      	<label class="inline checkbox">
								<input class="check_company" type="checkbox" name="${co.company_Type}" />${co.company_Type}
							</label>
						</li>
				      </c:forEach>
				      <input type="hidden" name="company_Type" id="company_Type">
					<%--<li>
						<label class="inline checkbox">
							<input id="CC" type="checkbox" name="CFS" value="Y"   />CFS
						</label>
					</li>
					<li>
						<label class="inline checkbox">
							<input id="C" type="checkbox" name="CAM" value="N" />CAM
						</label>
					</li>
					<li>
						<label class="inline checkbox">
							<input id="CCC" type="checkbox" name="CIS" value="N" />CIS
						</label>
					</li>
					<li>
						<label class="inline checkbox">
							<input id="C2" type="checkbox" name="CCL" value="N" />CCL
						</label>
					</li>
					<li>
						<label class="inline checkbox">
							<input id="C3" type="checkbox" name="CFSH" value="N" />CFSH
						</label>
					</li>
					<li>
						<label class="inline checkbox">
							<input id="C4" type="checkbox" name="CMS" value="N" />CMS
						</label>
					</li>
					<li>
						<label class="inline checkbox">
							<input id="C22" type="checkbox" name="CFG" value="N" />CFG
						</label>
					</li>
					<li>
						<label class="inline checkbox">
							<input id="C23" type="checkbox" name="Blank" value="N" />Blank
						</label>
					</li>
					<li>
						<label class="inline checkbox">
							<input  id="C24" type="checkbox" name="CCIA" value="N" />CCIA
						</label>
					</li>
					<li>
						<label class="inline checkbox">
							<input  id="C25" type="checkbox" name="CCFSH" value="N" />CCFSH
						</label>
					</li>
					<li>
						<label class="inline checkbox">
							<input  id="C26" type="checkbox" name="CWMC" value="N" />CWMC
						</label>
					</li>--%>
				</ul>
			</td>
		</tr>
	</table>
	<input type="hidden" name="UrgentDate" id="urgentDate"/>
	<input id="upd_dates" class="btn" name="upd_date" type="hidden"/>
	<input id="refno" class="btn" name="refno" type="hidden"/>
	</form>
</div>
<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
var data = api.data;
var downs = data.downs;


api.button({
	name : 'Back'
},{
	name : 'Save',
	callback : vailed
});
$(function(){
	
	//position();
	professional();
	department();
	//getlocation("location_modify");
	
	
	
	$("#body_view").hide();$("#body_modify").show();
	
	
	window.setTimeout(function(){
		$("#StaffNo_modify").val(downs.staff_code);
		$("#num").val(downs.quantity);
		$("#type_modify").val(downs.layout_type);
		$("#EnglishName_modify").val(downs.name);
		$("#ChineseName_modify").val(downs.name_chinese);
		var title_e_number=downs.title_english.indexOf(";");
		var title_c_number=downs.title_chinese.indexOf(";");
		//if($.trim(downs.external_english)==""){
			$("#EnglishPosition").val($.trim(downs.title_english.substring(0,title_e_number)));
			$("#ChinesePosition").val($.trim(downs.title_chinese.substring(0,title_c_number)));
		//}else{	
		//	$("#EnglishPosition").val($.trim(downs.external_english));
		//	$("#ChinesePosition").val($.trim(downs.external_chinese));
		//}
	
		$("#EnglishStaffDepartmentText").val($.trim(downs.title_english.substring(title_e_number+1))); 
		$("#ChineseStaffDepartmentText").val($.trim(downs.title_chinese.substring(title_c_number+1)));
		$("#EnglishProfessionalTitleText").val(downs.profess_title_e);
		$("#ChineseProfessionalTitle").val(downs.profess_title_c);
	 	$("#EnglishAcademicTitle").val(downs.academic_title_e);
		$("#ChineseAcademicTitle").val(downs.academic_title_c);
		$("#englishExternal").val(downs.external_english);
		$("#chineseExternal").val(downs.external_chinese);
		 
		$("#TR_RegNo").val(downs.tr_reg_no);
		$("#CENO").val(downs.ce_no);
		$("#MPFA_NO").val(downs.mpf_no);
		$("#Mobile").val(downs.bobile_number);
		$("#FAX").val(downs.fax);
		$("#DirectLine").val(downs.direct_line);
		$("#Email").val(downs.e_mail);
		$("#upd_dates").val(downs.upd_date);
		$("#urgentDate").val(downs.urgentDate);
		$("#location_modify").val(downs.location);
		$("#urgent").val(downs.urgent);
		if(downs.urgent=="Y"){
			$("#urgent").attr("checked","checked");
		}else{
			$("#urgent").attr("checked",false);
		}
		if(downs.eliteTeam=="Y"){
			$("#ET").attr("checked","checked").val("Y");
		}else{
			$("#ET").attr("checked","").val("N");
		}
		$("input[type='checkbox'][class='check_company']").removeAttr("checked");
		var companys=downs.company.split("+");
		for(var i=0;i<companys.length;i++){
			$("input[type='checkbox'][class='check_company'][name='"+companys[i]+"']").attr("checked",true);
		}
		
		
		/**if(downs.CFS_only=="Y"){
			$("#CC").attr("checked","checked").val("Y");
		}else{
			$("#CC").attr("checked","").val("N");
		}
		if(downs.CAM_only=="Y"){
			$("#C").attr("checked","checked").val("Y");
		}else{
			$("#C").attr("checked","").val("N");
		}
		if(downs.CIS_only=="Y"){
				$("#CCC").attr("checked","checked").val("Y");
		}else{
			$("#CCC").attr("checked","").val("N");
		}
		if(downs.CCL_only=="Y"){
			$("#C2").attr("checked","checked").val("Y");
		}else{
			$("#C2").attr("checked","").val("N");
		}
		if(downs.CFSH_only=="Y"){
			$("#C3").attr("checked","checked").val("Y");
		}else{
			$("#C3").attr("checked","").val("N");
		}
		if(downs.CMS_only=="Y"){
			$("#C4").attr("checked","checked").val("Y");
		}else{
			$("#C4").attr("checked","").val("N");
		}
		if(downs.CFG_only=="Y"){
			$("#C22").attr("checked","checked").val("Y");
		}else{
			$("#C22").attr("checked","").val("N");
		}
		if(downs.Blank_only=="Y"){
			$("#C23").attr("checked","checked").val("Y");
		}else{
			$("#C23").attr("checked","").val("N");
		}
		if(downs.CCIA_only=="Y"){
			$("#C24").attr("checked","checked").val("Y");
		}else{
			$("#C24").attr("checked","").val("N");
		}
		if(downs.CCFSH_only=="Y"){
			$("#C25").attr("checked","checked").val("Y");
		}else{
			$("#C25").attr("checked","").val("N");
		}
		if(downs.CWMC_only=="Y"){
			$("#C26").attr("checked","checked").val("Y");
		}else{
			$("#C26").attr("checked","").val("N");
		}*/
		
	}, 200);
	
	
	
	$("[type='checkbox'][name!='companys']").click(function(){
		if($(this).attr("checked")){
			$(this).val("Y");
		}else{
			$(this).val("N");
		}
	});
	
	
});
function vailed(){
	var nums=$("#num").val();                                                      
	if($("#StaffNo_modify").val()==""){																 
		W.error("Staff Code不能為空！");	
		$("#StaffNo").focus();
		return false;																			 
	}		
		if($("#ET").attr("checked")==true){
	$("#ET").val("Y");
	}else{
	$("#ET").val("N");
	}
	if($("#num").val()==""){															 
		W.error("Quantity不能為空！");	
			$("#num").focus();
		return false;																				 
	}	 																				 
	if(isNaN(nums)){		
		W.error("Quantity 必須是100的整数倍!");	
		$("#num").focus();
		return false;															 
	}else{
			if($("#num").val()%100 !=0){
			W.error("quantity必须是100的整数倍！");
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

	 
		if($("#urgent").attr("checked")==true){//當urgentCase選中時
				$("#urgent").val("Y");
				if(parseInt($("#num").val())<100 && parseInt($("#num").val())>0){
					W.error("在Urgent情况下，Quantity不能低于100!");
					$("#num").focus();
					return false;
				}
		}
		if($("#location_modify").val()==""){
			W.error("请选择location!");
			$("#location_modify").focus();
			return false;
		}
		//貌似trim()方法不支持
		 
		if(parseInt($("#num").val())<=0){
			W.error("Quantity不能小于0!"); 
			$("#num").focus();
				return false;
		}
		 
		if($("#EnglishPosition").val()=="positionName" || $("#EnglishPosition").val()==null || $("#EnglishPosition").val()==""){
			W.error("請選擇Title_Department in English ！");
			$("#EnglishPosition").focus();
			return false;
			
		}else{
			//var positiontext = $("#EnglishPosition").find("option:selected").text();
			//$("#EnglishPosition").val(positiontext);
		}
		$("#AddNameCard").attr("disabled","disabled");

		modify_staff();
		return false;
}

function modify_staff(){
	var company_Type="";
	$("input[class='check_company']").each(function(){
		if($(this).attr("checked")){
			if(company_Type==""){
				company_Type+=this.name;
			}else{
				company_Type+="+"+this.name;
			}
		}
		
	});
	$("#company_Type").val(company_Type);
	$.ajax({
		url:"ModifyStaffRequestServlet",
		type:"post",
		data:$("#myform").serialize(),
		success:function(date){
		  alert(date);
		  $("#body_view").show();
		  $("#body_modify").hide();
		  W.selects(1);
	      api.close();
		  
		},error:function(){
			W.error("网络连接失败，请稍后重试...");
			return false;
		}
	});
	return false;
}
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
	});return false;
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
	});return false;
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
	});return false;
	
});
function position(){
	$.ajax({
		type: "post",
		url: "QueryStaffPositionServlet",
		data: null,
		success: function(date){

		var d=eval(date);
		var html="";
		if(d.length>0){
			for(var i=0;i<d.length;i++){
			html+="<option value='"+d[i].position_ename+"'>"+d[i].position_ename+"</option>";
			}
			$("#EnglishPosition:last").append(html);
			}
		}
	});
	return false;
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
		var html="";
		if(d.length>0){
			for(var i=0;i<d.length;i++){
			html+="<option value='"+d[i].prof_title_ename+"'>"+d[i].prof_title_ename+"</option>";
			}
			$("#EnglishStaffDepartment:last").append(html);
			}
		}
	});return false;
	
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
	return false;
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
		 
			html+="<option value='' >Select Location...</option>";
			for(var i=0;i<location.length;i++){
				html+="<option value='"+location[i].realName+"' >"+location[i].name+"</option>";
			}
		}else{
			html+="<option value=''>加载异常</option>";
		}
		$("#"+select+"").append(html);
		},error:function(){
			W.error("网络连接失败,请返回登录页面重新登录!");
			return false;
		}
	});
	return false;
}
</script>

</body>
</html>
