<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'approveStaffCard_details.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<link rel="stylesheet" href="plug/css/bootstrap.css">
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	
  </head>
  
<body>

<div id="body_modify">

		</div>


<div class="form-table">
<form action="" id="myform" method="post" onSubmit="return modify_staff">
<table class="col-4-table">
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
			<select name="locatins" id="location_modify">
      		</select>
		</td>
		<td class="tagName">Layout: </td>
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
		<td class="tagName">Position (Eng): </td>
		<td class="tagCont">
			<select name="EnglishPosition" id="EnglishPosition" >
		        <option value="positionName">Please Select PositionName</option>
		    </select>
		</td>
		<td class="tagName">Position (Chi): </td>
		<td class="tagCont">
			<input id="ChinesePosition" name="ChinesePositions" type="text" size="35"  class="txt" />
		</td>
	</tr>
	<tr>
		<td class="tagName">Department (Eng):</td>
		<td class="tagCont">
			<select id="EnglishStaffDepartment" name="EnglishStaffDepartments">
	          <option value="department">Please Select Department</option>
	        </select><br />
			<input id="EnglishStaffDepartmentText" name="EnglishStaffDepartmentTexts" onClick="javascript:$('#EnglishStaffDepartment').show();" type="text" size="35"  class="txt"/>
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
			<select id="EnglishProfessionalTitle" name="EnglishProfessional">
		        <option value="professional">Please Select ProfessionalTitle</option>
		    </select><br />
			<input id="EnglishProfessionalTitleText" onClick="javascript:$('#EnglishProfessionalTitle').show();" name="EnglishProfessionalTitles" type="text" size="35"  class="txt"/>
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
		<td class="tagName">
			<input id="pay" type="hidden" name="pays"/> 
			<a id="downs"  target="_Blank" ></a>
			<input id="EnglishPositions" type="hidden" name="EnglishPositions"/>
			Company:
		</td>
		<td class="tagCont" colspan="3">
			<ul>
				<li>
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
				</li>
			</ul>
			<div style="clear:both;"></div>
		</td>
	</tr>
	<tr>
		<td class="tagName">RemarkCons:</td>
		<td class="tagCont" colspan="3">
			<textarea name="remarkcons" id="remarkcons" cols="78" rows="5"></textarea>
		</td>
	</tr>
	<tr style="display: ;">
		<td class="tagName"></td>
		<td class="tagCont">
			
		</td>
		<td class="tagName"></td>
		<td class="tagCont" style="text-align: right; padding-right: 10px;">
			<input id="AddNameCard" type="button" class="btn" name="Submit" value="Approve"/>
			<input id="Reject_NameCard" type="button" class="btn" name="Submit" value="Reject" onClick="reject_staff();" />
			<input id="back" type="button" class="btn" value="Back" onclick="reback();"/>
			<input id="urgentDate" class="btn" name="UrgentDate" type="hidden"/>
			<input id="upd_dates" class="btn" name="upd_date" type="hidden"/>
			<input id="refno" class="btn" name="refno" type="hidden"/>
			
		</td>
	</tr>
</table>
</form>
</div>
</body>
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript">
$(function(){
	var api = frameElement.api, W = api.opener;
	var data = api.data;
	
	var title_e_number=data.title_english.indexOf(";");
		var title_c_number=data.title_chinese.indexOf(";");
	  	$("#body_view").hide();
	  	$("#body_modify").show();
	  	$("#refno").val(data.refno);
		$("#StaffNo_modify").val(data.staff_code);
		$("#num").val(data.quantity);
		$("#type_modify").val(data.layout_type);
		$("#EnglishName_modify").val(data.name);
		$("#ChineseName_modify").val(data.name_chinese);
		$("#EnglishPosition").val($.trim(data.title_english.substring(0,title_e_number)));
		$("#ChinesePosition").val(data.title_chinese.substring(0,title_c_number));
		$("#EnglishStaffDepartmentText").val($.trim(data.title_english.substring(title_e_number+1))); 
		$("#ChineseStaffDepartmentText").val($.trim(data.title_chinese.substring(title_c_number+1)));
		$("#EnglishProfessionalTitleText").val(data.profess_title_e);
		$("#ChineseProfessionalTitle").val(data.profess_title_c);
		$("#TR_RegNo").val(data.tr_reg_no);
		$("#CENO").val(data.ce_no);
		$("#MPFA_NO").val(data.mpf_no);
		$("#Mobile").val(data.bobile_number);
		$("#FAX").val(data.fax);
		$("#DirectLine").val(data.direct_line);
		$("#Email").val(data.e_mail);
		$("#upd_dates").val(data.upd_date);
		$("#urgentDate").val(data.UrgentDate);
		$("#location_modify").val(data.location);
		$("#remarkcons").val(data.remarkcons);
		$("#EnglishAcademicTitle").val(data.academic_title_e);
		$("#ChineseAcademicTitle").val(data.academic_title_c);
		$("#englishExternal").val(data.external_english);
		$("#chineseExternal").val(data.external_chinese);
	if(data.urgent=="Y"){
		$("#urgent").attr("checked","checked").val("Y");
	}else{
		$("#urgent").attr("checked","").val("N");
	}
	 if(data.eliteTeam=="Y"){
		$("#ET").attr("checked","checked").val("Y");
	}else{
		$("#ET").attr("checked","").val("N");
	}
	
	if(data.CFS_only=="Y"){
		$("#CC").attr("checked","checked").val("Y");
	}else{
		$("#CC").attr("checked","").val("N");
		}
	if(data.CAM_only=="Y"){
		$("#C").attr("checked","checked").val("Y");
	}else{
		$("#C").attr("checked","").val("N");
	}
	if(data.CIS_only=="Y"){
			$("#CCC").attr("checked","checked").val("Y");
	}else{
		$("#CCC").attr("checked","").val("N");
	}
	if(data.CCL_only=="Y"){
		$("#C2").attr("checked","checked").val("Y");
	}else{
		$("#C2").attr("checked","").val("N");
	}
	if(data.CFSH_only=="Y"){
		$("#C3").attr("checked","checked").val("Y");
	}else{
		$("#C3").attr("checked","").val("N");
	}
	if(data.CMS_only=="Y"){
		$("#C4").attr("checked","checked").val("Y");
	}else{
		$("#C4").attr("checked","").val("N");
	}
	if(data.CFG_only=="Y"){
		$("#C22").attr("checked","checked").val("Y");
	}else{
		$("#C22").attr("checked","").val("N");
	}
	if(data.Blank_only=="Y"){
		$("#C23").attr("checked","checked").val("Y");
	}else{
		$("#C23").attr("checked","").val("N");
	}
	if(data.CCIA_only=="Y"){
		$("#C24").attr("checked","checked").val("Y");
	}else{
		$("#C24").attr("checked","").val("N");
	}
	if(data.CCFSH_only=="Y"){
		$("#C25").attr("checked","checked").val("Y");
	}else{
		$("#C25").attr("checked","").val("N");
	}
	if(data.CWMC_only=="Y"){
		$("#C26").attr("checked","checked").val("Y");
	}else{
		$("#C26").attr("checked","").val("N");
	}
	if(data.shzt=="R"){
		$("#AddNameCard,#Reject_NameCard").show();
	}else if(data.shzt=="S"){
			$("#AddNameCard").hide();
			$("#Reject_NameCard").show();
	}else{
		$("#AddNameCard,#Reject_NameCard").hide();
	}
	 fujianlist();
});
function reback(){
		var api = frameElement.api;
		api.close();
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
					html+="<option>请选择</option>";
					for(var i=0;i<dataRole.length;i++){
							html+="<option  class='oladf'>"+dataRole[i]+"</option>";
					}
				}else{
					html+="<option  class='oladf'>没有任何上传记录</option>"
				}
				$("#fujian_list").append(html);
			},error:function(){
				alert("网络连接失败，请稍后重试...");
				return false;
			}
			
		});
}
function reject_staff(){
	var api = frameElement.api;
		
	
	if(confirm("確定拒绝该申请?")){
	$.ajax({
		//url:"RejectRequestStaffConvoyServlet",
		url:"StaffNameCardWriteServlet",
		type:"post",
		data:{"method":"reject","role":"SZADM","staffcode":$("#StaffNo_modify").val(),"urgentDate":$("#urgentDate").val(),"refno":$("#refno").val()},
		success:function(date){
			var result=eval(date);
			if(result[0].state="success"){
			  $("#body_view").show();$("#body_modify").hide();
			  $("#AddNameCard").attr("disabled","");
			  $("#searchs").click();
			  
			}
			alert(result[0].msg);
		},error:function(){
			alert("网络连接失败，请稍后重试...");
			return false;
		}
		
	});
	}
	api.close();
}
</script>
</html>
