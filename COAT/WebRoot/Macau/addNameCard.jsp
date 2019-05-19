<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
	<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
	<title>信息錄入</title>


<script type="text/javascript">
/*窗体加载事件 */
$(function(){
	function getlocation(){
		$.ajax({
			type:"post",
			//url:"../QueryLocationMacauServlet",
			//data:null,
			url: "common/CommonReaderServlet",
			data:{"method":"getmacaulocation"},
			success:function(date){
			var location=eval(date);
			var html="";
			$("#location").empty();
			if(location.length>0){
				
				for(var i=0;i<location.length;i++){
					html+="<option value='"+location[i].realName+"' >"+location[i].name+"</option>";
				}
			}else{
				html+="<option value=''>加载异常</option>";
			}
			$("#location").append(html);
			$("#location").val("M");
			},error:function(){
				alert("网络连接失败!");
				return false;
			}
		});
	}
	
function position(){
	$.ajax({
		type: "post",
		//url: "<%=basePath%>QueryPositionServlet",
		//data: null,
		url: "<%=basePath%>common/CommonReaderServlet",
		data:{"method":"getposition"},
		success: function(date){

		var d=eval(date);
		var html;
		if(d.length>0){
			for(var i=0;i<d.length;i++){
			html+="<option value='"+d[i].position_ename+"'>"+d[i].position_ename+"</option>";
			}
			$("#EnglishPosition:last").append(html);
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
/*******************************************获取随机Code**********************************************************/
	function formatTime(){
	
		var d=new Date();
		var date="MA"+""+d.getFullYear()+""+(d.getMonth()+1)+""+d.getDate()+""+d.getHours()+""+d.getMinutes()+""+d.getSeconds();
		return date;
	}
/***************************************选择nocode时************************************************************************/
$("#sf").change(function(){
	if($("#sf").attr("checked")==true){
		$("#sf").val("Y");
		$("#StaffNo").val(formatTime());
		$("#StaffNo").attr("readonly","readonly");
	}else{
		$("#sf").val("N");
		$("#StaffNo").val("");
		$("#StaffNo").removeAttr("readonly");
	}
});
/**************************************EnglishProfession单击事件*****************************************/
$("#EnglishProfession").focus(function(){
	$("#EnglishProfessionalTitle").show();
});
/*********************************************************************************************************/
/**********************************窗體加載************************************************/
position();
professional();
getlocation();
$("#urgentDate").val(CurentTime());
$("#EnglishProfessionalTitle").hide();
/******************************************************************************************/
var pay=false;
/*****************************************************/
 
$("#urgentDate").empty;								//清空日期控件里面的残余值
$("#urgentDate").hide();							//隐藏日期控件
$("#urgentCase").removeAttr("checked");	
/*****************************************************/
var professionalCText="";
var professionalEText=""
/********************************EnglishProfessionalTitle值發生改變時*******************/
$("#EnglishProfessionalTitle").change(function(){
	$.ajax({
		type:"post",
		url:"<%=basePath%>SelectProfessionalServlet",
		data:{'EnglishProfessionalTitle':$("#EnglishProfessionalTitle").val()},
		success :function(date){
			if(date!="[]" || date!="null" || date){
				//$("#ChineseProfessionalTitle").val(date);
				if($("#ChineseProfessionalTitle").val()=="null" || $("#ChineseProfessionalTitle").val()==null){
					$("#ChineseProfessionalTitle").val("");
					}
					departmentCText = $("#ChineseProfessionalTitle").val()+date+";";
				departmentEText = $("#EnglishProfession").val()+ $("#EnglishProfessionalTitle").val()+";";
				$("#ChineseProfessionalTitle").val(departmentCText);
				$("#EnglishProfession").val(departmentEText);
					}
		}
	});
});
/**************************************************************************************/
/************************************验证payer是否存在的方法*************************/
function payers(){
		$.ajax({ 
			type: "post", 
			url: "<%=basePath%>VailPayer", 
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
$("#payer").blur(function(){
payers();
return false;
});
/****************************************pater验证结束***********************/
$("#EnglishPosition").change(function(){
	$.ajax({
		type:"post",
		url:"<%=basePath%>SelectPositionServlet",
		data:{'EnglishName':$("#EnglishPosition").val().replace('\r\n','').replace('\r\n','').replace('\r\n','')},
		success:function (date){
			if(date!="[]" || date!="null" || date==null){
				$("#ChinesePosition").val(date);
				if($("#ChinesePosition").val()=="null" || $("#ChinesePosition").val()==null){
					$("#ChinesePosition").val("");
					}
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
$("#AE").change(function(){
	if($("#AE").attr("checked")==true){
		$("#AE").val("Y");
		$("#CCC").attr("checked","checked").val("Y");
		}
		else{
		$("#AE").val("N");
		$("#CCC").attr("checked","").val("N");
		}
});

/****************************************************StaffNo 失去焦点时触发的时间****************************************************/
$("#StaffNo").blur(function(){
		 $("#EnglishPosition").get(0).selectedIndex=0
		$("#EnglishProfessionalTitle").get(0).selectedIndex=0
		if($("#sf").attr("checked")==true){
			return ;
		}
		if($("#StaffNo").val().length>=5){
				$.ajax({ 
				type: "post", 
				url: "<%=basePath%>MacauStaffServlet",
				 data: {'StaffNo':$("#StaffNo").val()}, 
					 success: function(date){
									 if(date!=null &&date!="[null]"){
											 var master=eval(date);
											$("#EnglishName").val(master[0].name);
											$("#ChineseName").val(master[0].c_Name);
											$("#TR_RegNo").val(master[0].TR_RegNo);
											$("#CENO").val(master[0].CENo);
												if(master[0].CENo!=""){
												$("#C").attr("checked","checked").val("Y");
											}
											$("#MPFA_NO").val(master[0].MPFNo);
											$("#Mobile").val(master[0].mobilePhone);
											$("#FAX").val(master[0].fax);
											$("#DirectLine").val(master[0].directLine);
											$("#Email").val(master[0].email.toLowerCase());
											$("#EnglishPosition").val(master[0].e_DepartmentTitle.replace('\r\n','').replace('\r\n','').replace('\r\n',''));
											//$("#EnglishPosition").get(0).selectedIndex=10;
							 
											$("#EnglishPostion1").val(master[0].lastPosition_E.replace('\r\n','').replace('\r\n','').replace('\r\n',''));
											$("#ChinesePostion1").val(master[0].lastPosition_C.replace('\r\n','').replace('\r\n','').replace('\r\n',''));
											$("#ChinesePosition").val(master[0].c_DepartmentTitle);
											$("#ChineseExternalTitle").val(master[0].c_ExternalTitle);
											$("#EnglishExternalTitle").val(master[0].e_ExternalTitle);
											$("#ChineseProfessionalTitle").val(master[0].c_EducationTitle);
											$("#EnglishProfession").val(master[0].e_EducationTitle);
											$("#payer").val(master[0].staffNo);
											payers();
									 }
									if(date=="[null]"||date==null){//当code不存在时
										error("Staff_NO不存在!");
										 $(".txt").val("").html("");
										 $("#StaffNo").focus();
									} 
							 } 
				  });
		  }
		else{
			if($("#StaffNo").val().length!=0){
			alert("Staff Code為非法數據！");
			$(".txt").val("");
			$("#StaffNo").focus();
			return false;
			}
			
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
/********************************UrgentCase值发生改变时*********************/
$("#urgentCase").change(function(){
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
			$("#num").val("200");
			}else{
				$("#num").val("100");
			}
});
/**************************************************************************/
function CurentTime()
    { 
        var now = new Date();
       
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
       //  var hh = now.getHours();            //时
       // var mm = now.getMinutes();          //分
        var clock = year + "-";
        if(month < 10)
            clock += "0";
        clock += month + "-";
        if(day < 10)
            clock += "0";
        clock += day + " ";
       //  if(hh < 10)
       //     clock += "0";
       //  clock += hh + ":";
       // if (mm < 10) clock += '0'; 
       //  clock += mm; 
        return(clock); 
    } 
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
		error("Quantity 必須在 1~400 之間的數字!");	
		$("#num").focus();
		return false;															 
	}		
	if(!isNaN(nums)){																						 
		if(parseInt($("#num").val())>400){													   
		error(" Quantity 數量單次不能大於 400！"); 
		$("#num").focus();
		return false;																			 
	}	
	if($("#num").val()%100 !=0){
		error("quantity必须是100的整数倍！");
		$("#num").focus();
		return false;
	}													 
}	 
/**************************判断事件是否合法*****************/
/**********************************************************/
/*****************判断复选框是否被选中**********************/
	if($("#AE").attr("checked")==true){
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
		if($("#CC").attr("checked")==true){
			$("#CC").val("Y");
		}
		else{
			$("#CC").val("N");
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
		if($("#urgentCase").attr("checked")==true){//當urgentCase選中時
				$("#urgentCase").val("Y");
				if(parseInt($("#num").val())<200 && parseInt($("#num").val())>0){
				 error("在Urgent情况下，Quantity不能低于200!");
					$("#num").focus();
					return false;
				}
				else{
					if(!confirm("StaffNo:"+$("#StaffNo").val()+"    Quantity:"+$("#num").val()+"\n            您確定要提交嗎？")){
						 	return false;
						}
				}
		}
		//貌似trim()方法不支持
		if($("#payer").val() != null){
			if($("#StaffNo").val().toLowerCase().replace(" ","")!=$("#payer").val().toLowerCase().replace(" ","")){
			 	if(pay!=true){
					error("支付人不存在，請重新填寫！");
					$("#payer").focus();
					return false;
				}else{ 
						if(!confirm("StaffNo:"+$("#StaffNo").val()+"與Payer:"+$("#payer").val()+"不同 "+"\n          您確定要提交嗎？")){
						 	return false;
						}
				 }
			}
		}else{
			error("支付人不能為空！");
			return false;
		}
		if(parseInt($("#num").val())<0){
			if(!confirm("您正在進行刪除操作，確定要刪除該數據嗎？")){
				return false;
			}
		}
		if($("#EnglishPosition").val()=="" || $("#EnglishPosition").val()==null || $("#EnglishPosition").val()==""){
			error("請選擇Title_Department in English ！");
			$("#EnglishPosition").focus();
			return false;
			
		}
		if($("#EnglishProfessionalTitle").val()=="professional"){
			$("#EnglishProfessionalTitle").val("");
		}
		$("#AddNameCard").attr("disabled","disabled");
	if($("#ET").attr("checked")==true){
	$.ajax({
		url:"<%=basePath%>VailEliteServlet",
		type:"post",
		data:{"staffcode":$("#StaffNo").val(),"table":"request_macau"},
		success:function(date){
			 
			if(date=="true"){
					if(!confirm("Elite Team 的免费限额已满,继续办理需支付额外费用")){
						
					
					$("#ET").attr("checked","");
					$("#AddNameCard").attr("disabled","");
					return false;
					}
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
	function changeDate(){
		var date=$("#urgentDate").val();
		if(date!=""){
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
		if(day<10){
		ba="-0";
		}else{
		ba="-";
		}
		$("#urgentDate").val(year+ca+parseInt(month)+ba+parseInt(day));
		}
	}					
});
</script>
</head>
 
<body>
<div class="cont-info">
	<div class="info-title">
		<div class="title-info">
			<form action="<%=basePath%>/AddNewCardServlet" id="myform" method="post">
			<table>
				<tr>
					<td class="tagName">Staff Code:</td>
					<td class="tagCont">
						<input id="StaffNo" name="StaffNos" onkeyup="this.value=this.value.toUpperCase();"  type="text" size="28" class="txt" />
						<label class="inline checkbox" style="vertical-align: top;">
							<input type="checkbox" id="sf" name="sfs" value="N" />No Code
						</label>
					</td>
					<td class="tagName">quantity:</td>
					<td class="tagCont">
						<input name="nums" type="text" class="txt" id="num" size="5" value="100" onkeyup="checkNum(this);" onkeypress="checkNum(this);" onfocus="$('#numId').show();"/>
				        <select id="numId" name="nums" style="display: none;" onchange=" $('#num').val($(this).val()) ;">
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
						<select name="locatins" id="location"></select>
					</td>
					<td class="tagName">Layout:</td>
					<td class="tagCont">
						<select name="types" id="type">
				        	<option value="S">Standard Layout</option>
				        	<option value="P">Premium Layout</option>
				      	</select>
					</td>
				</tr>
				<tr>
					<td class="tagName">AE Counsultant:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input type="checkbox" name="AEs" value="N" id="AE" />YES
						</label>
					</td>
					<td class="tagName">Urgent Case:</td>
					<td class="tagCont">
						<label class="inline checkbox">
							<input id="urgentCase" type="checkbox" name="urgent" value="N" />
						</label>
						<input name="urgentDate" id="urgentDate" style='cursor:hand' type="text" readonly="readonly" title="輸入日期最好大於當前日期" onclick="return Calendar('urgentDate');"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">Elite Team:</td>
					<td class="tagCont" colspan="3">
						<label class="inline checkbox">
							<input type="checkbox" name="ETs" value="N" id="ET" />YES
						</label>
					</td>
				</tr>
				<tr>
					<td class="tagName">English Name:</td>
					<td class="tagCont">
						<input name="EnglishNames" type="text" size="35" class="txt" id="EnglishName" />
					</td>
					<td class="tagName">Chinese Name:</td>
					<td class="tagCont">
						<input id="ChineseName" name="ChineseNames" type="text" size="33" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Last print Title dept in English:</td>
					<td class="tagCont">
						<input type="text" id="EnglishPostion1" size="38" class="txt" />
					</td>
					<td class="tagName">Last print Title dept in Chinese</td>
					<td class="tagCont">
						<input type="text" id="ChinesePostion1" size="33" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Title Department in English:</td>
					<td class="tagCont">
						<select id="EnglishPosition" name="EnglishPositions">
			            	<option value="">Please Select PositionName</option>
			        	</select>
					</td>
					<td class="tagName">Title Department in Chinese:</td>
					<td class="tagCont">
						<input id="ChinesePosition" name="ChinesePositions" type="text" size="33"  class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">ExternalTitle_Department in English:</td>
					<td class="tagCont">
						<input id="EnglishExternalTitle" name="EnglishExternalTitles" type="text" size="33"  class="txt" />
					</td>
					<td class="tagName">ExternalTitle_Department in Chinese:</td>
					<td class="tagCont">
						<input id="ChineseExternalTitle" name="ChineseExternalTitles" type="text" size="33"  class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">English Academic & Professional Title :</td>
					<td class="tagCont" colspan="3">
						<input type="text" name="EnglishProfessionalTitles" class="txt" id="EnglishProfession" size="70"/>
	    				<br />
						<select id="EnglishProfessionalTitle" name="">
				        	<option value="">Please Select ProfessionalTitle</option>
				        </select>
					</td>
				</tr>
				<tr>
					<td class="tagName">Chinese Academic & Professional Title :</td>
					<td class="tagCont" colspan="3">
						<input id="ChineseProfessionalTitle" name="ChineseProfessionalTitles" type="text" size="70"  class="txt"/>
					</td>
				</tr>
				<tr>
					<td class="tagName">TR Reg NO:</td>
					<td class="tagCont">
						<input id="TR_RegNo" name="TR_RegNos" type="text" size="35" class="txt" />
					</td>
					<td class="tagName">CE NO:</td>
					<td class="tagCont">
						<input id="CENO" name="CENOs" type="text" size="33" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">MPFA NO:</td>
					<td class="tagCont">
						<input id="MPFA_NO" name="MPFA_NOs" type="text" size="35" class="txt" />
					</td>
					<td class="tagName">Mobile:</td>
					<td class="tagCont">
						<input id="Mobile" name="Mobiles" type="text" size="33" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">FAX:</td>
					<td class="tagCont">
						<input id="FAX" name="FAXs" type="text" size="35" class="txt" />
					</td>
					<td class="tagName">Direct Line:</td>
					<td class="tagCont">
						<input id="DirectLine" name="DirectLines" type="text" size="33" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">E-Mail:</td>
					<td class="tagCont">
						<input id="Email" name="Emails"  onkeyup="this.value=this.value.toLowerCase();"  type="text" size="35" class="txt" />
					</td>
					<td class="tagName">Payer:</td>
					<td class="tagCont">
						<input id="payer" type="text" class="txt" name="payers" size="33" />
					</td>
				</tr>
				<tr>
					<td class="tagName"></td>
					<td class="tagCont" colspan="3">
						<label class="inline checkbox">
							<input id="C24" type="checkbox" name="CIB" value="Y"  checked="checked" />CIB
						</label>
						<label class="inline checkbox">
							<input id="CC" type="checkbox" name="CFS" value="N"/>CFS
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
						<input id="pay" type="hidden" name="pays"/>
					</td>
				</tr>
				<tr style="height: 30px;">
				
				</tr>
				<tr>
					<td class="tagBtn" colspan="4">
						<c:if test="${roleObj.add==1}">
							<a class="btn" id="AddNameCard" name="Submit">
								<i class="icon-submit"></i>
								Submit
							</a>
						</c:if>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
