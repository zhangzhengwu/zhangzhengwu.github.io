<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
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
<html>
  <head>
     	<base href="<%=basePath%>">
	   	<title>name card checking</title>
	   	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css">
		<link rel="stylesheet"href="<%=basePath%>plug/css/font-awesome.min.css">
		<!--[if lte IE 7]> 
		<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
		<![endif]--> 
			
		<link rel="stylesheet" href="<%=basePath%>css/layout.css">
		<link rel="stylesheet" href="<%=basePath%>plug/css/site.css">
		<script type="text/javascript" src="<%=basePath%>css/jquery-1.4.4.js"></script>
			<script type="text/javascript" src="<%=basePath%>css/Util.js"></script>
		<script src="css/date.js" language="JavaScript"></script>
		<script type="text/javascript" src="<%=basePath%>plug/js/Common.js"></script>
 	<script type="text/javascript">
	var downs=null;
	var pagenow =1;
	var totalpage=1;
	var total=0;
	var company=namecard_consultant_company.split(",");
	var comstring="";
	for ( var i = 0; i < company.length; i++) {
		comstring+="<label class='inline checkbox'><input type='checkbox'  name='companys' value='"+company[i]+"' />"+company[i]+"</label>";
	}

 
	
	
	 /*****************************************************WindowForm Load *************************************/
	 $(function(){
		$("#companys").empty().append(comstring);
	    //设定staffcode 为TW5956，BL4776，AT6466, 
		$("#staffcodes").val("${convoy_username}").attr("readonly","readonly");
		$("#EnglishEducationTitle").hide();
 		$("#start_date").val((new Date()).getFullYear()+"-01-01");
     	$("#end_date").val((new Date()).getFullYear()+"-12-31");	
		 getlocation();
	/******************************************退出詳細頁面*******************************/
	$("#quit").click(function(){
		$("#jqajax_div").show();
		$("#details").hide();
	});
	/*************************Search click***********************************/
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
		
		$("#aeConsultant").click(function(){
			if($(this).attr("checked")){
				$(this).val("Y");
			}else{
				$(this).val("N");
			}
		});
	 
});
	
</script>
  </head>
<body>
<div class="cont-info" id="jqajax_div">
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
				<td class="tagName">Location：</td>
				<td class="tagCont">
					<select name="select" id="location"  >
                		<option value="">Please Select Location</option>
              		</select>
				</td>
				<td class="tagName">Urgent Case：</td>
				<td class="tagCont">
					<select name="urgent" id="urgentCase">
		                <option value="">Please Select</option>
		                <option value="Y">Yes</option>
		                <option value="N">No</option>
              		</select>
				</td>
			</tr>
				<tr>
					<td class="tagName">Isverify：</td>
	  				<td class="tagCont">
	  					<select name="select" id="shzt"  >
			                <option value="">Please Select Isverify</option>
			                <option value="S">Pending</option>
			                <option value="Y">Approve</option>
			                <option value="N">Reject</option>
			            </select>
	  				</td>
					<td class="tagName"></td>
					<td class="tagCont"></td>
			</tr>
			
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
					<a class="btn" id="searchs" name="searchs">
						<i class="icon-search"></i>
						Search
					</a><%--
           			<input id="add" name="search" type="hidden" value="Add Name Card"/>
           			--%><input type="hidden" name="names" id="name">
          			<input type="hidden" name="staffcode" id="staffcodes">
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_10">Details</th>
				<th class="width_8">Staff Code</th>
				<th class="width_10">English Name</th>
				<th class="width_10">Chinese Name</th>
				<th class="width_15">Title_Dept(Eng)</th>
				<th class="width_10">Title_Dept(CHI)</th>
				<th class="width_5">Quantity</th>
				<th class="width_5">Urgent?</th>
				<th class="width_8">Elite Team?</th>
				<th class="width_10">Submit Date</th>
				<th class="width_5">Status</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;" >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">First Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a> Total
						<SPAN style="color: red;" id="total">
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<script type="text/javascript">


function selects(pagenow){
	if($("#start_date").val()!="" && $("#end_date").val()!=""){
		if($("#start_date").val()>$("#end_date").val()){
			alert("Start Date can’t be later than End Date.");
			$("#start_date").focus();
			return false;
		}
	}
	$.ajax({
		type: "post",
		//url:"QueryConvoyCardServlet",
		url:"namecard/NameCardConvoyReaderServlet",
		data: {"method":"selectnamecardbyconsultant",'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'location':$("#location").val(),'urgentCase':$("#urgentCase").val(),'shzt':$("#shzt").val(),"pagenow":pagenow},
		beforeSend:function(){
			parent.showLoad();
		},
		complete: function(){
			parent.closeLoad();
		},
		success:function(data){
			var totals=0;
			var dataRole=eval(data);
			var html="";
			
			var result=null;
			if(data.indexOf("{")==0){
				result=$.parseJSON(data);
				alert(result.msg);
			}else{
				$("tr[id='select']").remove();
				downs=null;
				if(dataRole[3]>0){
					total=dataRole[3];
					pagenow =dataRole[2];
				    totalpage=dataRole[1];
					downs=dataRole[0];
					for(var i=0;i<dataRole[0].length;i++){
						var status="";
					var Details="<a href='javascript:void(0);' onclick='detail("+i+")'>Detail</a>";
						if(dataRole[0][i].shzt =="S"){
							status="<td align='center'>Pending</td>";
							Details="<a href='javascript:void(0);' onclick='detail("+i+")'>Detail</a> &nbsp; <a href='javascript:void(0);' onclick='del("+i+")'>Delete</a>";
						}else if(dataRole[0][i].shzt =="N"){
							status="<td align='center'>Reject</td>";
						}else{
							status="<td align='center'>Approve</td>";
						}
						html+="<tr id='select'><td>"+Details+"</td><td align='center'>"+dataRole[0][i].staff_code+"</td><td align='center'>"
						+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].name_chinese +"</td><td style='text-align:left;'>"
						+dataRole[0][i].title_english +"</td><td align='center'>"+dataRole[0][i].title_chinese +"</td><td align='center'>"
						+dataRole[0][i].quantity +"</td><td align='center'>"+dataRole[0][i].urgent +"</td><td align='center'>"+dataRole[0][i].eliteTeam 
							+"</td><td align='center'>"+dataRole[0][i].urgentDate.substring(0,10) +"</td>"+status+"</tr>";	 
					}
					   $(".page_and_btn").show();
				   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
				}else{
					html+="<tr id='select'><td colspan='18'align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
					 $(".page_and_btn").hide();
				}
					 $("#jqajax:last").append(html);
				 	 $("tr[id='select']:even").css("background","#COCOCO");
	                 $("tr[id='select']:odd").css("background","#F0F0F0");
                  	 page(pagenow,totalpage);
			}
		 },error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
	 });
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
/***************************************加载location下拉框***************************************************/
function getappend(id){
	var html="";
	$(id).empty();
	if(locations.length>0){
		html+="<option value=''>Please Select Location...</option>"
			for(var i=0;i<locations.length;i++){
				html+="<option value='"+locations[i].realName+"' >"+locations[i].name+"</option>";
			}
		}else{
				html+="<option value=''>Load Error</option>";
		}
		$(id).append(html);
	}

/*******************************************************************************************************************/
function detail(num){
	if(num>=0){
		
    	$("#jqajax_div").hide();
		$("#details").show();
	
		$("#StaffNo").attr("disabled","disabled");
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
		$("#HKCIB").val(downs[num].remark1);//HKCIB
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
	/**	if(downs[num].CFS_only=="Y"){
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
		}else{$("#C24").attr("checked","").val("N");}**/
		$("input[name='companys']").removeAttr("checked");
		 if(""!=downs[num].company){
			 	var company=downs[num].company.split("+");
			 	for(var i=0;i<company.length;i++){
			 		$("input[name='companys'][value='"+company[i]+"']").attr("checked","checked");
			 	}
			 }
			 
	//	$("#urgent").val(downs[num].urgent);
		$("#layout_type").val(downs[num].layout_type);
		$("#remarks").val(downs[num].remarkcons);
		$("[title='1']").attr("disabled", "disabled");
		
	}
	/**显示marquee 字体滚动
	$.ajax({
		type: "post",
		url: "QueryConsMarqueeServlet",
		data: {'code':downs[num].staff_code},
		success: function(date){
		
			$("#agun").html("<strong>"+date+"</strong>");
		 
		}
	});
	*/

 }
/**
 * 删除Namecard
 * 
 * */
function del(obj){
	/**if(comptime(downs[obj].urgentDate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1 && parseFloat(("${SystemAcceTime}").substring(11,13))<12){
	}else if(comptime(downs[obj].urgentDate,"${SystemAcceTime}".substring(0,10)+" 12:00:00")==1 && parseFloat(("${SystemAcceTime}").substring(11,13))>=12){
	}else{
		alert("These record can be “Delete” before 12:00 pm.");
		return false;
	}**/ 
	if(confirm("Sure to Delete?")){
		$.ajax({
			url:"DeleteNameCardServlet",
			type:"post",
			data:{"staffcode":downs[obj].staff_code,"urgentDate":downs[obj].urgentDate,"table":"request_new_convoy"},
			success:function(date){
				alert(date);
				$("#searchs").click();
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
		});
	}
}

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
}
</script>
<div class="cont-info" id="details" style="display: none;">
	<div class="info-title">
		<div class="title-info">
			<table>
				<tr>
					<td class="tagName">StaffCode:</td>
					<td class="tagCont">
						<input name="StaffCode" type="text" class="ss" id="StaffNo" size="15" disabled="disabled" />
					</td>
					<td class="tagName"></td>
					<td class="tagCont">
						
					</td>
				</tr>
				<tr>
					<td class="tagName">AE Consultant:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input name="checkbox2"  type="checkbox" id="aeConsultant" title="1" value="N"/>
							Yes
						</label>
					</td>
					<td class="tagName">UrgentCase：</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input name="checkbox" type="checkbox" id="urgent" title="1" value="N">
							Yes
						</label>
					</td>
				</tr>
				<tr>
					<td class="tagName">Elite Team:</td>
					<td class="tagCont" colspan="3">
						<label class="inline checkbox">
							<input name="checkbox2"  type="checkbox" id="ET" title="1" value="N"/>
							Yes
						</label>
					</td>
				</tr>
				<tr>
					<td class="tagName">Layout:</td>
					<td class="tagCont">
						<select id="layout_type" title="1" >
			      	 		<option value="S">Standard Layout</option>
			        	</select>
					</td>
					<td class="tagName">Location：</td>
					<td class="tagCont">
						<select name="select2" id="upselect" title="1" >
					        <option value="O">OIE</option>
					        <option value="C">CP3</option>
					        <option value="W">CWC</option>
					        <option value="M">Macau</option>
				        </select>
					</td>
				</tr>
				<tr>
					<td class="tagName">EnglishName:</td>
					<td class="tagCont">
						<input type="text" name="EnglishNames" id="EnglishName" class="ss"  title="1">
          
					</td>
					<td class="tagName">ChineseName：</td>
					<td class="tagCont">
						<input type="text" name="ChineseNames" id="ChineseName" class="ss"  title="1">
					</td>
				</tr>
				<tr>
					<td class="tagName">Department in Chinese:</td>
					<td class="tagCont">
						<input name="ChineseTitle_Departments" type="text" class="ss" title="1" id="ChineseTitle_Department" size="30">
					</td>
					<td class="tagName">Department in English：</td>
					<td class="tagCont">
						<input name="EnglishTitle_Departments"   type="text"  class="ss"  title="1" id="EnglishTitle_Department" >
         	 			</input> 
					</td>
				</tr>
				<tr>
					<td class="tagName">ExternalTitle_Department_Chinese:</td>
					<td class="tagCont">
						<input name="ChineseExternalTitle_Departments" type="text" class="ss"  title="1" id="ChineseExternalTitle_Department" size="30">
          
					</td>
					<td class="tagName">ExternalTitle_Department_English:</td>
					<td class="tagCont">
						<input name="EnglishExternalTitle_Departments" type="text" class="ss"  title="1" id="EnglishExternalTitle_Department" size="30">
					</td>
				</tr>
				<tr>
					<td class="tagName">English Academic & Professional Title:</td>
					<td class="tagCont" colspan="3">
						<input name="EnglishProfessions" type="text" id="EnglishProfession" title="1" size="66" class="ss">
			            <select  name="EnglishEducationTitles"  class="ss"   id="EnglishEducationTitle"  title="1">
				        </select>
					</td>
				</tr>
				<tr>
					<td class="tagName">Chinese Academic & Professional Title:</td>
					<td class="tagCont" colspan="3">
						<input name="ChineseEducationTitles" type="text" class="ss"  title="1" id="ChineseEducationTitle" size="66">
					</td>
				</tr>
				<tr>
					<td class="tagName">T.R.Reg.No:</td>
					<td class="tagCont">
						 <input name="trgs" type="text" class="ss"  title="1" id="trg" size="30">
					</td>
					<td class="tagName">CE No：</td>
					<td class="tagCont">
						 <input name="CEs" type="text" class="ss"  title="1" id="CE" size="35">
					</td>
				</tr>
				<tr>
					<td class="tagName">MPF No:</td>
					<td class="tagCont">
						 <input name="MPFs" type="text" class="ss"  title="1" id="MPF" size="30">
					</td>
					<td class="tagName">CIB Reg. No:</td>
					<td class="tagCont">
						<input id="HKCIB" name="HKCIBs" type="text" title="1" size="30" class="ss" />
					</td>
				</tr>
				<tr>					
					<td class="tagName">E-mail：</td>
					<td class="tagCont">
						 <input name="Emails" type="text"  title="1" class="ss" id="Email" size="35">
					</td>

					<td class="tagName">DirectLine:</td>
					<td class="tagCont">
						 <input name="DirectLines" type="text"  title="1" class="ss" id="DirectLine" size="30">
					</td>
				</tr>
				<tr>					
					
					<td class="tagName">FAX：</td>
					<td class="tagCont">
						 <input name="FAXs" type="text" class="ss"  title="1" id="FAX" size="35">
					</td>

					<td class="tagName">MobilePhone:</td>
					<td class="tagCont">
						 <input name="MobilePhones" type="text" title="1" class="ss" id="MobilePhone" size="30">
					</td>
				</tr>
				<tr>
					<td class="tagName">Quantity：</td>
					<td class="tagCont">
						 <select name="Quantitys"  id="Quantity" class="ss"  title="1">
				          <option value="0">0</option>
				              <option value="100">100</option>
				              <option value="200">200</option>
				              <option value="300">300</option>
				              <option value="400">400</option>
				          </select>
					</td>
					<td class="tagName"></td>
					<td class="tagCont"></td>
				</tr>
				<tr>
						<td class="tagName">Company:</td>
						<td class="tagCont" colspan="3" id="companys"></td>
				</tr>
				<%--
				<tr>
					<td class="tagName"></td>
					<td class="tagCont" colspan="3">
						<label class="inline checkbox">
							<input id="CC" type="checkbox" name="CFS" value="N"  title="1"/>CFS
						</label>
						<label class="inline checkbox">
							<input id="C" type="checkbox" name="CAM" value="N"   title="1"/>CAM
						</label>
						<label class="inline checkbox">
							<input id="CCC" type="checkbox" name="CIS" value="N"   title="1"/>CIS
						</label>
					</td>
				</tr>
				--%><tr>
					<td class="tagName">Remark:</td>
					<td class="tagCont" colspan="3">
						 <textarea id="remarks" name ="remark" rows="3" cols="50" style="width: 70%;"></textarea>
					</td>
				</tr>
				<tr>
					<td class="tagBtn" colspan="4">
						<input id="reQuantity" title="1" type="hidden"/>
				        <input id="reStaffNo" type="hidden"/>            
				        <input id="rePayer" type="hidden"/>
				        <input id="Payer" title="1" type="hidden"/>
				        <button class="btn" id="quit" name="exit">Back</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>