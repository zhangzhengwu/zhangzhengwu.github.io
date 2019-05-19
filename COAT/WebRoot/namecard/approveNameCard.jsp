<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
 <%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML>
<html>
  <head>
     	<base href="<%=basePath%>">
	   	<title>name card checking</title>
	   	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
	tr.closed{display:none;}
	.detailContainer{ margin: 10px auto; }
	.detailContainer .stepBox{float: left;}
	.detailContainer .step{float:left; margin: 0px 5px;  text-align: center;}
	.detailContainer .step span{font-size: 0.75em; display: block; height: 36px;}
	
	.detailContainer div.arrow{float: left;width: 30px; padding: 4px 5px;}
	.detailContainer a:hover{text-decoration: none;}
	.detailContainer a.arrow{color: #ddd;}
	.detailContainer a.done, .detailContainer a.notdo{display: block; width: 100%; padding: 6px 8px;}
	.detailContainer a.done{background: rgb(99, 197, 162); color: #fff;}
	.detailContainer a.notdo{background: #ddd; color: #fff;}
	
</style>
 <script type="text/javascript">
 	var base="<%=basePath%>";
	var downs=null;
	var reject_info=null;
	var sumbitDate=null;
	var pagenow =1;
	var totalpage=1;
	var total=0;
	var DD=true;
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

	 /*****************************************************WindowForm Load *************************************/
	 $(function(){
		document.onkeydown=BackSpace;
	 	getlocation();
	    professional();
		$("#EnglishEducationTitle").hide();
 		$("#start_date").val(CurentTime());	
 		$("#end_date").val(CurentTime());	
		/******************************************Motify click***************************
		$("#motify").click(function(){
			$("#messages").empty();
			$("#messages").val("信息修改").html("信息修改");
			$("[title='1']").removeAttr("disabled");
			$("#save").show();
		});		**/
	/******************************************退出詳細頁面************************************/
	$("#quit").click(function(){
		$("#jqajax,#lists").show();
		$("#details").hide();
	});
	/*************************Search click***********************************/
	$("#searchs").click(function(){
		selects(1);
	});
	/*************************nogo click***********************************/
	$("#nogo").click(function(){
		if($("#remarks").val()==""){
			alert("请输入拒绝原因!");
			$("#reject_information").focus();
			return false;
		}
		if($("#subject").val()==""){
			alert("请选择subject!");
			$("#subject").focus();
			return false;
		}		
		if(!confirm("確定拒绝该申请?")){
			return false;
		}
		$.ajax({
			type:"post",
			url:"UpdNewRequestconvoyServlet", 
			data:{'StaffNos':$("#StaffNo").val(),'urgentDate':$("#UrgentDate").val(),'remarks':$("#remarks").val()},
			success:function(data){
						//alert("审核拒绝成功！");
			var head="Dear "+$("#EnglishName").val()+",%0b%0b"
			+$("#subject").val()+",detail as follow:%0b%0b"
			+"Staff Code: "+$("#StaffNo").val()+"%0b"
			+"Submit date: "+sumbitDate+"%0dReason:%0b";
			
			var cc="?cc=Peter.KP.Chan@convoy.com.hk;adminfo@convoy.com.hk";//;roy.lai@convoy.com.hk  2014-04-24 King 注释Macy.chong@convoy.com.hk;
			var qianming="%0b%0bRegards%0b%0bAdmin Team%0bShared Service Centre(SZ)%0bConvoy Financial Services Limited%0b(E)SZOAdm@convoy.com.hk%0b39/F,@CONVOY,169 Electric Road,North Point,Hong Kong.%0b%0b";
				window.open("mailto:"+$.trim($("#Email").val())+cc+"&subject="+$("#subject").val()+" "+$("#StaffNo").val()+"&body="+head+$("#remarks").val()+"%0d"+reject_info+qianming);
	 
				
				//document.location.href="../namecard/approveNameCard.jsp";
				/**$("#start_date").val(CurentTime());	
		 		$("#end_date").val(CurentTime());	
		 		$("#details").hide();
		 		$("#jqajax").show();
		 		*/
		 		alert("Reject Success!");
		 		//查询
		 		$("#details").hide();
				$("#lists,#jqajax").show();
		 		$("#searchs").click();
		 		return false;
			},error:function(){
				alert("Reject Error!");
			}
		});
	});
/*************************************EnglishProfession获取光标事件*************************************************/
$("#EnglishProfession").focus(function(){
	$("#EnglishEducationTitle").show();
});
/*****************************************************************************************************************************************************/
/**************************************EnglishEducationTitle值发生改变时*********************************/
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
	/***************************************保存方法*****************************************************/
	$("#save").click(function(){
		
		var t=false;//判断是否存在确认提示框
		if(parseFloat($("#Quantity").val())<100){
			alert("Quantity不能低於100!");
				$("#Quantity").focus();
				return false;
		}
		if($("#reStaffNo").val()!= $("#StaffNo").val()){
			t=true;
			if(!confirm("警告！您正在修改員工編號！確定繼續嗎？")){
				return false;
			}
				$("#Payer").val($("#StaffNo").val());
		}  
		if(getCompany()==""){
			alert("Company 不能为空!");
			return false;
		}else{
			$("#company").val(getCompany());
		}
		if(t==false){
			if(!confirm("確定通过申请?")){
				return false;
			}
		}
		 
		
		$.ajax({
			type:"post",
			//url:"AddNewRequestconvoyServlet", 
			url:"namecard/NameCardConvoyWriteServlet",
			data:{"method":"approveRequest",'StaffNos':$("#StaffNo").val(),'payers':$("#Payer").val(),'pays':$("#pay").val(),'EnglishNames':$("#EnglishName").val(),
			'ChineseNames':$("#ChineseName").val(),'EnglishPositions':$("#EnglishTitle_Department").val(),'ChinesePositions':$("#ChineseTitle_Department").val(),
			'EnglishExternalTitles':$("#EnglishExternalTitle_Department").val(),'ChineseExternalTitles':$("#ChineseExternalTitle_Department").val(),
			'EnglishAcademicTitles':$("#AcademicTitle_e_Department").val(),
			'ChineseAcademicTitles':$("#AcademicTitle_c_Department").val(),
			'EnglishProfessionalTitles':$("#EnglishProfession").val(),
			'ChineseProfessionalTitles':$("#ChineseEducationTitle").val(),'TR_RegNos':$("#trg").val(),
			'CENOs':$("#CE").val(),'MPFA_NOs':$("#MPF").val(),'HKCIB_NOs':$("#HKCIB").val(),'Emails':$("#Email").val(),'DirectLines':$("#DirectLine").val(),
			'FAXs':$("#FAX").val(),'Mobiles':$("#MobilePhone").val(),'urgent':$("#urgent").val(),'UrgentDate':$("#UrgentDate").val(),
			'nums':$("#Quantity").val(),'locations':$("#upselect").val(),'types':$("#layout_type").val(),
			'AEs':$("#aeConsultant").val(),'CFS':$("#CC").val(),'CAM':$("#C").val(),
			'CIS':$("#CCC").val(),'CCL':$("#C2").val(),'CFSH':$("#C3").val(),
			'CMS':$("#C4").val(),'CFG':$("#C22").val(),'Blank':$("#C23").val(),'remarks':$("#remarks").val(),"ETs":$("#ET").val(),"DD":$("#DD_result").val(),"company":$("#company").val()},
			success:function(data){
				var result=$.parseJSON(data);
				if(result.state=="success"){
			 		$("#lists,#jqajax").show();
			 		$("#details").hide();
			 		//查询
			 		window.setTimeout(selects(1),200);
				}
				alert(result.msg);
		 	//	var head="Dear "+$("#EnglishName").val()+":%0d"+" "
		var bodys="";
		 	var cc="?cc=Peter.KP.Chan@convoy.com.hk;adminfo@convoy.com.hk";//;roy.lai@convoy.com.hk 2014-04-24 King 注释Macy.chong@convoy.com.hk;
		 	if($("#subject").get(0).selectedIndex>1){
		 		var qianming="%0b%0bRegards%0b%0bAdmin Team%0bShared Service Centre(SZ)%0bConvoy Financial Services Limited%0b(E)SZOAdm@convoy.com.hk%0b39/F,@CONVOY,169 Electric Road,North Point,Hong Kong.%0b%0b";
				bodys=cc+"&subject="+$("#subject").val()+" "+$("#StaffNo").val()+"&body=Dear "+$("#EnglishName").val()+",%0b%0b"+$("#remarks").val()+qianming;
			}else{
				bodys="?subject=Your name card request of this week has been submitted successfully.&body= %0b";
			}
		 		window.open("mailto:"+$.trim($("#Email").val())+bodys);
				
		 		
		 		
		 	
			},error:function(){
				alert("网络连接失败,请稍后重试....");
				return false;
			}
		});
	});
	//'CIB':$("#C24").val(), 'rePayer':$("#rePayer").val(),'reStaffNo':$("#reStaffNo").val(),'reQuantity':$("#reQuantity").val(), 
			 
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
	/********************************修改code*****************************
	$("#read").change(function(){
		if($("#read").attr("checked")==false){
			$("#StaffNo").attr("disabled","disabled");
		}else{
			$("#StaffNo").removeAttr("disabled");
		}
	});*****/

/************************************复选框的值发生改变时**********************************************************************/
	/****************判断复选框是否被选中********************/
/**$("[type='checkbox'][name!='companys']").change(function(){
	if($("#aeConsultant").attr("checked")==true){
		$("#aeConsultant").val("Y");
	}else{
		$("#aeConsultant").val("N");
	}
		
});**/
	$("#aeConsultant").click(function(){
		if($(this).attr("checked")){
			$(this).val("Y");
		}else{
			$(this).val("N");
		}
	});
	
	/**************************************************************************************/
	
	/*****************************************************Down click********************************/
	$("#down").click(function(){
		if(downs!=null){
			window.location.href=base+"/DownConvoyRequestServlet?name="+$("#name").val()+"&code="+$("#staffcodes").val()+"&location="+$("#location").val()+"&startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&urgentCase="+$("#urgentCase").val()+"&shzt="+$("#shzt").val();
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
		}
	});
	/****************************************************Down END***********************************/
	
	    $("#reject_information").change(function(){
	var vals=$("#reject_information").find("option:selected").val();
			var rep=$("#remarks").val();
	if($("#remarks").html().indexOf(vals)<0){
		var num=(rep.split("%0b").length-1);
		 if(rep==""){
			 rep="1.";
		 }else{
			 rep=rep+(num+1)+".";
		 }
		$("#remarks").val(rep+(vals+"\r\n"));
	 
	}else{
 
		//$("#remarks").val(rep.replace((vals),''));
	}
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
	
	
	
	$("#fujian_list").change(function(){
//	 if(confirm("确定下载查看?")){
			   $("#downs").attr("href","upload/"+$("#fujian_list").val()).empty().append(($("#fujian_list").val().length)>50?($("#fujian_list").val().substring(0,30)+"..."+$("#fujian_list").val().substring($("#fujian_list").val().length-16)):$("#fujian_list").val());
		
			  //window.open("../upload/"+$("#fujian_list").val());
			  //	$("downs").attr("",("../upload/"+$("#fujian_list").val());
		// }
	});
});
	 
  
</script>
<style type="text/css">
<!--
#details td{
	height: 36px;
}
#details input[type="text"], #details select{
	width:170px!important;
	*padding: 0px 6px;
	
}
#details select{
	*width:184px!important;
	
}
-->
  </style>
  </head>
<body>
<input type="hidden" id="username" value="<%=session.getAttribute("adminUsername")%>"/>
<div class="cont-info" id="lists">
  	<div class="info-search">
  		<table>
  			<tr>
  				<td class="tagName">Start Date：</td>
  				<td class="tagCont">
  					<input id="start_date" type="text" readonly="readonly" onClick="Calendar('start_date')"  />
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
  				<td class="tagName">Location：</td>
  				<td class="tagCont">
  					<select name="locations" id="location"  >
		                <option value="">Please Select Location</option>
		            </select>
  				</td>
  				<td class="tagName">Urgent Case：</td>
  				<td class="tagCont">
  					<select id="urgentCase" name="urgent">
		                <option value="">Please Select Urgent</option>
		                <option value="Y">是</option>
		                <option value="N">否</option>
		            </select>
  				</td>
  			</tr>
  			<tr>
  				<td class="tagName">Isverify：</td>
  				<td class="tagCont">
  					<select name="select" id="shzt"  >
		                <option value="">Please Select Isverify</option>
		                <option value="S">待审核</option>
		                <option value="Y">已批准</option>
		                <option value="N">已拒绝</option>
		            </select>
  				</td>
  				<td class="tagName"></td>
  				<td class="tagCont">
  					
  				</td>
  			</tr>
  			<tr>
  				<td class="tagCont" colspan="2"></td>
  				<td class="tagCont" colspan="2">
  					<c:if test="${roleObj.search==1}">
	 					<a class="btn" id="searchs" name="search"><i class="icon-search"></i>Search</a>
  					</c:if>
  					<c:if test="${roleObj.export==1}">
  						<a class="btn" id="down" name="downs"><i class="icon-download"></i>Export</a>
  					</c:if>
  				</td>
  			</tr>
  		</table>
  	</div>
  	<div class="info-table">
  		<table id="jqajax">
  			<thead>
  			<tr>
  				<th class="width_">Refno</th>
  				<th class="width_">Staff Code</th>
  				<th class="width_">Name</th>
  				<th class="width_">Name(Ch)</th>
  				<th class="width_">Title with Department(En)</th>
  				<th class="width_">Title with Department(Ch)</th><%--
  				<th class="width_">AcademicTitle & Professional Title(En)</th>
  				<th class="width_">AcademicTitle & Professional Title(Ch)</th>
  				--%><th class="width_">Quantity</th>
  				<th class="width_">Urgent Case?</th>
  				<th class="width_">Elite Team?</th>
  				<th class="width_">Submit Date</th>
  				<th class="width_">Isverify</th>
  				<th class="width_">Detail</th>
  			</tr>
  			</thead>
  		</table>
  		<div align="center" class="page_and_btn" style="display: none;" >
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
<div class="cont-info" id="details" style="display:none;">
	<div class="info-title">
		<div class="title-info">
			<table>
				<tr>
					<td class="tagName">Staff Code:</td>
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
						<input name="checkbox2"  type="checkbox" id="aeConsultant" title="1" value="N"/>
					</td>
					<td class="tagName">UrgentCase ：</td>
					<td class="tagCont">
						<input name="checkbox" type="checkbox" id="urgent" title="1" value="N">&nbsp;&nbsp;&nbsp;<a id="downs"  target="_Blank" ></a>
					</td>
				</tr>
				<tr>
					<td class="tagName">Elite Team:</td>
					<td class="tagCont">
						<input name="checkbox2"  type="checkbox" id="ET" title="1" value="N"/>
					</td>
					<td class="tagName">attachment:</td>
					<td class="tagCont">
						<select id="fujian_list"></select>
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
					<td class="tagName">English Name:</td>
					<td class="tagCont">
						<input type="text" name="EnglishNames" id="EnglishName" class="ss"  title="1">
					</td>
					<td class="tagName">Chinese Name ：</td>
					<td class="tagCont">
						<input type="text" name="ChineseNames" id="ChineseName" class="ss"  title="1">
					</td>
				</tr>
				<tr>
					<td class="tagName">Department in Chinese:</td>
					<td class="tagCont">
						<input name="ChineseTitle_Departments" type="text" class="ss" title="1" id="ChineseTitle_Department" size="30">
					</td>
					<td class="tagName">Department in English ：</td>
					<td class="tagCont">
						<%--<select name="EnglishTitle_Departments"  class="ss"  title="1" id="EnglishTitle_Department" ></select>
						--%><input name="EnglishTitle_Departments"  type="text" class="ss"  title="1" id="EnglishTitle_Department"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">ExternalTitle_Department(Chi):</td>
					<td class="tagCont">
						<input name="ChineseExternalTitle_Departments" type="text" class="ss"  title="1" id="ChineseExternalTitle_Department" size="30">
					</td>
					<td class="tagName">ExternalTitle_Department(Eng):</td>
					<td class="tagCont">
						<input name="EnglishExternalTitle_Departments" type="text" class="ss"  title="1" id="EnglishExternalTitle_Department" size="30">
					</td>
				</tr>
				<tr>
					<td class="tagName">Academic & Professional Title(Eng):</td>
					<td class="tagCont">
						<input name="EnglishProfessions" type="text" id="EnglishProfession" title="1" size="66" class="ss">
						<br>
						<select  name="EnglishEducationTitles"  class="ss"   id="EnglishEducationTitle"  title="1"></select>
					</td>
					<td class="tagName"></td>
					<td class="tagCont">
						
					</td>
				</tr>
				<tr>
					<td class="tagName">Academic & Professional Title(Chi):</td>
					<td class="tagCont">
						<input name="ChineseEducationTitles" type="text" class="ss"  title="1" id="ChineseEducationTitle" size="66">
					</td>
					<td class="tagName"></td>
					<td class="tagCont">
						
					</td>
				</tr>
				<tr>
					<td class="tagName">T.R.Reg.No:</td>
					<td class="tagCont">
						<input name="trgs" type="text" class="ss"  title="1" id="trg" size="30">
					</td>
					<td class="tagName">CE No ：</td>
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
						<input name="HKCIBs" type="text" class="ss"  title="1" id="HKCIB" size="30">
					</td>
				</tr>
				<tr>	
					
					<td class="tagName">E-Mail ：</td>
					<td class="tagCont">
						<input name="Emails" type="text"  title="1" class="ss" id="Email" size="35">
					</td>
				
					<td class="tagName">Direct Line:</td>
					<td class="tagCont">
						<input name="DirectLines" type="text"  title="1" class="ss" id="DirectLine" size="30">
					</td>
				</tr>
				<tr>
					<td class="tagName">FAX ：</td>
					<td class="tagCont">
						<input name="FAXs" type="text" class="ss"  title="1" id="FAX" size="35">
					</td>
				
					<td class="tagName">Mobile Phone:</td>
					<td class="tagCont">
						<input name="MobilePhones" type="text" title="1" class="ss" id="MobilePhone" size="30">
					</td>
				</tr>
				<tr>
					<td class="tagName">Quantity ：</td>
					<td class="tagCont">
						<select name="Quantitys"  id="Quantity" class="ss"  title="1">
				              <option value="100">100</option>
				              <option value="200">200</option>
				              <option value="300">300</option>
				              <option value="400">400</option>
			        	</select>
					</td>
				</tr>
				<tr>
						<td class="tagName">Company:</td>
						<td class="tagCont" id="companys"></td>
						<td class="tagName">Payer:</td>
						<td class="tagCont">
							<input id="Payer" title="1" type="text"/>
						</td>
				</tr>
				<%--
				<tr>
					<td class="tagName"></td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input id="CC" type="checkbox" name="CFS" value="N"  title="1"/>CFS
						</label>
						<label class="inline checkbox">
							<input id="C" type="checkbox" name="CAM" value="N"   title="1"/>CAM
						</label>
						<label class="inline checkbox">
							<input id="CCC" type="checkbox" name="CIS" value="N"   title="1"/>CIS
						</label>
						<label class="inline checkbox">
							<input id="C2" type="checkbox" name="CCL" value="N"   title="1"/>CCL
						</label> 
						<input id="pay" type="hidden" name="pays"/>
					</td>
					<td class="tagName">Payer:</td>
					<td class="tagCont">
						<input id="Payer" title="1" type="text"/>
					</td>
				</tr>
				--%><tr>
					<td class="tagName">RemarkCons:</td>
					<td class="tagCont" colspan="3">
						<textarea id="remarkcons" name ="remark" readonly="readonly" rows="3" cols="74" style="width: 75%;" ></textarea>
					</td>
				</tr>
				<tr class="reject_con" class="reject_con">
					<td class="tagName">Subject:</td>
					<td class="tagCont" colspan="3">
						<select id="subject"  class="reject_con">
					        <option value="">Please Select Subject</option>
					        <option value="Your name card request was reject"> Your name card request was reject</option>
							<option value="Name card request notice"> Name card request notice </option>
				        </select>
					</td>
				</tr>
				<tr>
					<td class="tagName">Remarks:</td>
					<td class="tagCont" colspan="3">
						<div class="reject_con"><select id="reject_information"  class="reject_con"><option value="">Please Select Remarks</option></select><br /></div>
        				<textarea id="remarks" name ="remark" rows="5" cols="74" style="width: 75%;" ></textarea>
					</td>
				</tr>
				<tr>
					<td class="tagBtn" colspan="4">
						<input id="DD_result" name="DD" type="hidden"/>
				        <input id="reQuantity" title="1" type="hidden"/>
				        <input id="UrgentDate" title="1" type="hidden"/>
				        <input id="reStaffNo" type="hidden"/> 
				        <input id="rePayer" type="hidden"/>
				        <input id="company" type="hidden"/>
				        <input id="pay" type="hidden" name="pays"/>
				        
				       <c:if test="${roleObj.audit==1}"><button class="btn" name="Submit" id="save">通  過</button></c:if>
				       <c:if test="${roleObj.audit==1}"><button class="btn" name="Submit" id="nogo">拒  絕</button></c:if>
				        <button class="btn" name="exit" id="quit">返  回</button>
				        <input id="agun" type="hidden" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
 	<script type="text/javascript" src="plug/js/Common.js"></script>
<script type="text/javascript">
var basepath = "<%=basePath%>";
var company=namecard_consultant_company.split(",");
var comstring="";
for ( var i = 0; i < company.length; i++) {
	comstring+="<label class='inline checkbox'><input type='checkbox'  name='companys' value='"+company[i]+"' />"+company[i]+"</label>";
}
$("#companys").empty().append(comstring);

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

function fujianlist(){
$.ajax({
		url:"QueryFileNameServlet",
		type:"post",
		data:{"staffcode":$("#StaffNo").val()},
		success:function(date){
			var dataRole=eval(date);
			var html="";$(".oldf").remove();
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



function selects(pagenow){
	if($("#start_date").val()!="" && $("#end_date").val()!=""){
		if($("#start_date").val()>$("#end_date").val()){
			alert("開始日期不能大於結束日期!");
			$("#start_date").focus();
			return false;
		}
	}
	$.ajax({
		type: "post",
		url:"namecard/NameCardConvoyReaderServlet",
		data: {"method":"selectnamecardbyapprove","name":$("#name").val(),'code':$("#staffcodes").val(),'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'location':$("#location").val(),'urgentCase':$("#urgentCase").val(),'shzt':$("#shzt").val(),'pagenow':pagenow},
		beforeSend: function(){
			parent.showLoad();
		},
		complete: function(){
			parent.closeLoad();
		},
		success:function(data){
			var result=null;
			if(data.indexOf("{")==0){
				result=$.parseJSON(data);
				alert(result.msg);
			}else{
			var dataRole=eval(data);
			$("tr[id='select'],tr[id='selectstate']").remove();
			var totals=0;
			var html="";
				downs=null;
				if(dataRole[3]>0){
					total=dataRole[3];
					pagenow =dataRole[2];
				    totalpage=dataRole[1];
					downs=dataRole[0];
					for(var i=0;i<dataRole[0].length;i++){
						html+="<tr id='select'><td align='center'>"+dataRole[0][i].refno+"</td><td align='center'>"+dataRole[0][i].staff_code+"</td><td align='center'>"
							+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].name_chinese +"</td><td align='center'>"
							+dataRole[0][i].title_english +"</td><td align='center'>"+dataRole[0][i].title_chinese +"</td><td align='center'>"
						//	+dataRole[0][i].profess_title_e +"</td><td align='center'>"+dataRole[0][i].profess_title_c +"</td><td align='center'>"
							+dataRole[0][i].quantity +"</td><td align='center'>"+dataRole[0][i].urgent +"</td><td align='center'>"+dataRole[0][i].eliteTeam 
							+"</td><td align='center'>"+dataRole[0][i].urgentDate 
							+"</td>";		 
						if(dataRole[0][i].shzt =="S"){
							html+="<td align='center'>待审核</td>";
						}else if(dataRole[0][i].shzt =="N"){
							html+="<td align='center'>已拒絕</td>";
						}else{
							html+="<td align='center'>已批准</td>";
						}
						html+="<td><c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='detail("+i+")'>"+"详细"+"</a></c:if>" +
						"&nbsp;<c:if test='${roleObj.search==1}'><a onclick=\"queryProcess('"+dataRole[0][i].refno+"',"+i+")\">流程</a></c:if></td></tr>";
						}
					$(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}
				 else{
					html+="<tr id='select'><td colspan='18' size='5px' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
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
	

	
function getReject_information(model,type,mothedName){
	 $.ajax({
		url:"Reject_informationServlet",
		type:"post",
		async:false,
		data:{"model":model,"type":type,"mothedName":mothedName},
		success:function(dates){
			var dataRole=eval(dates);
			var html;
			if(dataRole.length>0){
			html+="<option value=''>Select Location...</option>"
				for(var i=0;i<dataRole.length;i++){
					if(dataRole[i].id!=0)
					html+="<option value='"+dataRole[i].resultName+"' >"+dataRole[i].id+". "+dataRole[i].resultName+"</option>";
				else
				reject_info=dataRole[i].resultName;	
				}
			}else{
					html+="<option value=''>加载异常</option>";
			}
			$("#reject_information").empty().append(html);
	},error:function(){
		
	}
	 });
}

/*************************************************获取弹出层的位置*************************/
function popupDiv() {  
 	// 设计弹出层的位置
	var screenwidth, screenheight, mytop, getPosLeft, getPosTop,lh
	screenwidth = $(window).width();
    screenheight = $(window).height();
    mytop = $(document).scrollTop();
    
   
   // $("#details").hide().css({ "left": 0, "top": 150 });
   // $("#moti").css({"left":0,"top":0});
}  
/***************************************加载location下拉框***************************************************/
function getappend(id){
	var html="";
	$(id).empty();
	if(locations.length>0){
		html+="<option value=''>Please Select Location</option>"
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
			//url:base+"QueryLocationServlet",
			url: base+"common/CommonReaderServlet",
			data:{"method":"getlocation"},
			success:function(date){
				var location=eval(date);
				locations=location;
				getappend("#location");
				getappend("#upselect");
				
			},error:function(date){
				alert("网络连接失败,请联系管理员!");
				return false;
			}
		});
	}
/***********************************************************获取PositionName以及ProfessionalTitle********************/
function professional(){
	$("#EnglishEducationTitle").empty();
	var html="";
	html+="<option value=''></option>";
	
	$.ajax({
		type: "post",
		//url: base+"QueryProfessionalServlet",
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
		//通过隐藏值 判断是否已经登录系统
	if ($("#username").val()=="null" || $("#username").val()==""){
		$("#save").hide();
		$("#nogo").hide();
	}else{
		if(downs[num].shzt == "S"){
			//都显示
			$("#save,#nogo,.reject_con").show();
			$("#remarks").val("").removeAttr("disabled");
		}else{
			//审核后 不能再审
			$("#save,#nogo,.reject_con").hide();
			$("#remarks").attr("disabled","disabled");
			//填充备注
			$("#remarks").val(downs[num].remark);
		}
	}
	if(num>=0){

	
	 //   popupDiv(); 
		$("#details").show();
		$("#lists,#jqajax").hide();
		$("[name='Submit']").removeAttr("disabled");
		//$("#read").attr("checked","");
		$("#StaffNo").attr("disabled","disabled");
		$("#EnglishEducationTitle").get(0).selectedIndex=0;
		//$("body").css("overflow","hidden");
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
		$("#HKCIB").val(downs[num].remark1);
		$("#Email").val(downs[num].e_mail);
		$("#DirectLine").val(downs[num].direct_line);
		$("#FAX").val(downs[num].fax);
		$("#MobilePhone").val(downs[num].bobile_number);
		$("#Quantity").val(downs[num].quantity);
		$("#reQuantity").val(downs[num].quantity);
		$("#Payer").val(downs[num].Payer);
		$("#rePayer").val(downs[num].Payer);
		$("#reStaffNo").val(downs[num].staff_code);
		$("#pay").val(downs[num].staff_code);
		sumbitDate=downs[num].add_date;
		//$("#remarks").val(downs[num].remark);
		$("#upselect").val(downs[num].location);
		if(downs[num].urgent=="Y"){
			$("#urgent").val("Y").attr("checked","checked");
			if(!confirm("该顾问有选择'Urgent Case',这将花费HK$"+(parseFloat($("#Quantity").val())/100*165)+".是否要继续?")){
				$("#save").attr("disabled","");
				return false;
			}
		}else{
			$("#urgent").val("N").removeAttr("checked");
			
			use(); //-->获取到$("#agun").val()
			var remainNumber=parseFloat($.trim($("#agun").val().substring($("#agun").val().lastIndexOf("is")+2)).replace(".0",""));
			if($("#layout_type").val()=="S"){
				if($("#Quantity").val()>parseFloat(remainNumber)){
					if(parseFloat(remainNumber)>0){
						t=true;
						if(!confirm("Your remaining free quota is "+remainNumber+", the excess part will be charged on you if continue.")){
							$("#Quantity").focus();
							return false;
						}
					}else{
						t=true;
						if(!confirm("Your remaining free quota has been used up and the excess part will be charged on you if continue.")){
							$("#Quantity").focus();
							return false;
						}
					}
				}
			}
			
		}
		 if(downs[num].ae_consultant=="Y"){
			$("#aeConsultant").attr("checked","checked").val("Y");
		}else{
			$("#aeConsultant").val("N").removeAttr("checked");
		}
		 if(downs[num].eliteTeam=="Y"){
			$("#ET").attr("checked","checked").val("Y");
			$.ajax({
				url:"VailEliteServlet",
				type:"post",
				async:false,
				data:{"staffcode":$("#StaffNo").val(),"table":"request_new"},
				success:function(date){
					if(date=="true"){
							if(!confirm("该顾问Elite Team 的免费限额已满,继续办理需支付额外费用")){
								//$("#ET").attr("checked","");
								$("#save").attr("#disabled","disabled");
								return false;
							}else{
								//等待处理
								
							}
					}else{
						if(parseFloat($("#Quantity").val())>100){
									if(!confirm("该顾问Elite Team 的免费限额只有100,超出部分将正常收费，是否继续?")){
										$("#save").attr("#disabled","disabled");
										return false;
									}
								}
					}
				},error:function(){
					alert("Elite_系统数据异常,请暂停审核并联系管理员!");
					$("[name='Submit']").attr("disabled","disabled");
					return false;
				}
			});
			 
		 }else{
			 $("#ET").val("N").removeAttr("checked");
		 }
		
		$("#layout_type").val(downs[num].layout_type);
		$("[title='1']").attr("disabled", "disabled");
		$("#remarkcons").val(downs[num].remarkcons);
		$("input[name='companys']").removeAttr("checked");
		 if(""!=downs[num].company){
			 	var company=downs[num].company.split("+");
			 	for(var i=0;i<company.length;i++){
			 		$("input[name='companys'][value='"+company[i]+"']").attr("checked","checked");
			 	}
			 }
		if(downs[num].remarkcons!=""){
			$("#EnglishName,#ChineseName,#ChineseTitle_Department,#trg,#CE,#Email,#MPF,#DirectLine").attr("disabled","");
		}
		fujianlist();
		getReject_information("NameCard","Consultant","query");
		vailDD();
	}
	
	$.ajax({
		type : "post",
		//url : "StaffNoServlet",
		url : base+"/namecard/NameCardReaderServlet",
		data : {"StaffNo" : $("#StaffNo").val(),"method":"findbystaffcode"
		},	beforeSend: function(){
			parent.showLoad();
		},
		complete: function(){
			parent.closeLoad();
		},
		success : function(data) {
			if (data != null && data != "{null}" && data != "{}") {
				var result=$.parseJSON(data);
				if (result.sfcf == "Y") {
					if (!confirm("该顾问在一周内已有过提交记录，是否继续?")) {
						$("#StaffNo").val("").focus();
						return false;
					}
				}
				if(("null"==result.e_EducationTitle?"":result.e_EducationTitle)!=downs[num].profess_title_e){
					 alert("Profession Title 与历史提交记录有所区别,请仔细确认!");
				 }
			}
			},error:function(){
				alert("Master_系统数据异常,请暂停审核并联系管理员!");
				$("[name='Submit']").attr("disabled","disabled");
			}
		});
	
	//if($("#ET").attr("checked")==true){
	//} 
	//if($("#urgent").attr("checked")==true){
	//}
	
/**if( parseFloat(("${SystemAcceTime}").substring(11,13))<12){
	alert("正常审核时间为每天中午12点以后.");
	$("[name='Submit']").attr("disabled","disabled");
 }**/
	
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

function use(){
	$.ajax({
		type : "post",
		//url : "QueryConsMarqueeServlet",
		url : base+"namecard/NameCardReaderServlet",
		async:false,
		data : {
			'code' : $("#StaffNo").val(),"method":"finduserUsage"
		},
		success : function(date) {
			if(date.indexOf("{")==0){
				var result=$.parseJSON(date);
				alert(result.msg);
			}else{
				$("#agun").val(date.replace("<font style='color:red;'>","").replace("</font>",""));
			}
		},
		error : function() {
			errorAlert("网络连接失败，请联系管理员或稍后重试...");
			return false;
		}
	});
}
	
	
/**
*
* 验证是否为DD
**/
function vailDD(){
	$.ajax({
		url : base+"VialDDServlet",
		type : "post",
		data : {
			"staffcode" : $("#StaffNo").val()
		},
		success : function(date) {
			if (date == "SUCCESS") {
				DD = true;
				$("#DD_result").val("true");
				//getLayout();
			} else {
				DD = false;
				//2014-04-14 King 注释 $("#type option[value='P']").remove();
				$("#DD_result").val("false");
			}
		},
		error : function() {
			errorAlert("网络连接失败");
			return false;
		}
	});

	
}
function newChart(state){
	  if(typeof(state) == 'undefined' || state == null){
		  state = '';
	  }
	  switch(typeof(state))
	  {
	  	case 'object':
	  		var _obj = state;
	  		this.id = _obj.id;
			this.creator = _obj.creator;
			this.createdate = _obj.createdate;
			this.state = _obj.state;
			this.done = 'Y';
			this.edit = 'N';
	  		break;
	  	default: 
	  		this.id = 0;
			this.creator = '';
			this.craetedate = '';
			this.state = state;
			this.done = 'N';
			this.edit = 'N';
	  		break;
	  }
  }
  function makeChartAry(){
	  var chartAry = ['Submitted','Approval_SZOADM','Processed_Vender','Reject','Delivery','Receive'];
	  //var chartAry = ['S','E','R','Y','N'];  //D&G根据Reject状态决定是否追加
	  var tempAry = new Array();
	  
	  for(var i=0; i<chartAry.length; i++)
	  {
		  var _obj = new newChart(chartAry[i]);
		  tempAry.push(_obj);
	  }
	  return tempAry;
  }
	function initProcess(ary){
		var tempAry = new Array(), newAry = new Array();
		var chartAry = makeChartAry();
		var yFlag = false, nFlag = false, index = -1, yIndex = -1, nIndex = -1, dIndex = -1, gIndex = -1, lastIndex = -1;
		
		for(var i=0; i<ary.length; i++)
		{
			for(var j=0; j<chartAry.length; j++){
				//将后台返回的流程进行标记
				if(ary[i].state == chartAry[j].state)
				{
					chartAry[j] = new newChart(ary[i]);
				}
			}
			//判断后台返回的流程中是否有Reject, 进行标记
			if(ary[i].state == 'Reject'){
				nFlag = true;
			}
		}
		for(var i=0; i<chartAry.length; i++){
			if(chartAry[i].state == 'Reject'){
				nIndex = i;
			}else if(chartAry[i].state == 'Delivery'){
				dIndex = i;
			}else if(chartAry[i].state == 'Receive'){
				gIndex = i;
			}
		}
		//对Approved和Rejected共存进行处理
		if(yFlag == false && nFlag == false){
			chartAry.splice(nIndex,1);
		}else if(yFlag == true){
			chartAry.splice(nIndex,1);
		}else if(nFlag == true){ 
			//Reject存在的话, 那么Delivery和Receiver不需要显示
			//注意这里的删除顺序：从后到前
			chartAry.splice(gIndex,1);
			chartAry.splice(dIndex,i);
		}
		//将最后一个流程前未处理的流程进行删除
		for(var i=chartAry.length-1; i>=0; i--){
			if(chartAry[i].done == 'Y'){
				if(lastIndex == -1){
					lastIndex = i;	
				}
			}else{
				if(i < lastIndex){
					chartAry.splice(i,1);
					i++;
				}
			}
		}
		//只为标记处最后一个已操作流程的下标
		for(var i=chartAry.length-1; i>=0; i--){
			if(chartAry[i].done == 'Y'){
				lastIndex = i;
				break;
			}
		}
		//设置可编辑的单个流程
		if(lastIndex < chartAry.length-1){
			chartAry[lastIndex+1].edit = 'Y';
		}
		tempAry = chartAry;
		
		return tempAry;
	}
	/* 查看操作流程
	 * Jimmy
	 * 2015年9月10日10:37:53
	 *  */
	 function queryProcess(code,index){
		var nextChild = $('#jqajax tr[id=select]:eq('+index+')').next();
					//判断流程是否已存在
		if($(nextChild).attr('target') == "details")
		{
			$(nextChild).remove();
		}else{
			$.ajax({
			type: "post",
			url:"namecard/NameCardConvoyReaderServlet",
			data:{method: 'detail', "staffrefno":code},
			success:function(data){
				if(typeof(data) == 'undefined' || data == null){
					error("暂无记录");
					return false;
				}
				//alert(data.indexOf('{'));
				if(data.indexOf("{")=="0"){
					var result=$.parseJSON(data);
					alert(result.msg);
					return false;
				}
				var flagStr = new Array();
				var datalist = eval(data);
				$("tr[id='selectstate']").remove();
				if(datalist.length > 0){
					//var tempStr = makeChartAry();
					var tempStr = initProcess(datalist); //自定义对象数组
					tempChartAry = tempStr;
					var html = '<tr target="details" id="selectstate"><td colspan="12" style="padding: 20px 20px 10px 5%;"><div class="detailContainer">';
		
					for(var i=0; i<tempStr.length; i++){
						if(tempStr[i].done == "Y")
						{
							html += '<div class="stepBox"><div class="step"><a class="done">'+tempStr[i].state+'('+tempStr[i].creator+')</a><span>'+tempStr[i].createdate+'</span></div>';
						}else{
							html += '<div class="stepBox"><div class="step"><a class="notdo">'+tempStr[i].state+'</a><span></span></div>';
						} 
						
						if(i == tempStr.length-1){
							html += '<div style="clear: both;"></div></div>';
						}else{
							html += '<div class="arrow"><a class="arrow"><i class="icon-arrow-right icon-1x"></i></a></div><div style="clear: both;"></div></div>';
						}
					}
					html += '<div style="clear: both;"></div></div></td></tr>';
					
					$('#jqajax tr[id=select]:eq('+index+')').after(html);
				}else{
					error("暂无数据");
					return false;
				}
				
			 }
		 });
		}
	 }
	
</script>
</body>
</html>