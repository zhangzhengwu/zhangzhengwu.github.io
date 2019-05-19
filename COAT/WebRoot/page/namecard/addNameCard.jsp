<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML>
<html>
<head>
<title>信息錄入</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

<style type="text/css">
<!--
#information {
	position: absolute;
	width: 100%;
	height: 100%;
	z-index: 1;
	border: 1px solid #999999;
	left: 1px;
	top: 1px;
}

.txt {
	color: #005aa7;
	border: 0px;
	border-bottom: 1px solid #005aa7; /* 下划线 */
	height: 20px;
	background-color: transparent; /* 顏色透明*/
	size: 21px;
	font-family: 'Arial Narrow';
	font-size: 16px;
}
-->
</style>

<script type="text/javascript">

var base="<%=basePath%>";
var DD = true;
var AECode = true;
var Elite = true;
var pay = false;
var professionalCText = "";
var professionalEText = "";
	/**
	 * 只读文本框禁用退格键
	 * 
	 * */
	function BackSpace(e) {
		if (window.event.keyCode == 8)//屏蔽退格键
		{
			var type = window.event.srcElement.type;//获取触发事件的对象类型
			//var tagName=window.event.srcElement.tagName;
			var reflag = window.event.srcElement.readOnly;//获取触发事件的对象是否只读
			var disflag = window.event.srcElement.disabled;//获取触发事件的对象是否可用
			if (type == "text" || type == "textarea")//触发该事件的对象是文本框或者文本域
			{
				if (reflag || disflag)//只读或者不可用
				{
					//window.event.stopPropagation();
					window.event.returnValue = false;//阻止浏览器默认动作的执行
				}
			} else {
				window.event.returnValue = false;//阻止浏览器默认动作的执行
			}
		}
	}

	
	/*窗体加载事件 */
	$(function() {
		document.onkeydown = BackSpace;
		/**********************************窗體加載************************************************/
		getlocation();
		position();
		professional();
		//getLayout()
		$("#EnglishProfessionalTitle").hide();
		
/* 		$("input[name='companys'][value='CAM']").click(function(){
			if($("#CENO").val()==""){
				$(this).removeAttr("checked");
			}
		});
		$("#CENO").blur(function(){
			if(this.value==""){
				$("input[name='companys'][value='CAM']").removeAttr("checked");
			}else{
				$("input[name='companys'][value='CAM']").attr("checked","checked");
			}
		}); */
		/**
		 * Elite Team change事件
		 * 
		 * */
		$("#ET,#num,#numId").change(function() {
			if ($("#ET").attr("checked")) {

				//$("#type").val("P");
				if ($("#sf").attr("checked")) {
					$("#ET").val("N").removeAttr("checked");
					error("No Code 不能选择Elite Team!");
				} else {
					$("#ET").val("Y");
					//$("#num").val(100);

				}

			} else {
				$("#ET").val("N");
				$("#type").val("S");
			}
		});
		$("#type").change(function() {
			if ($("#ET").attr("checked")) {
				//$(this).val("P");
			} else {
				/**if(DD==false){
					$(this).val("S");
				}else{
					$(this).val("P");
				}**/
			}
		});

	

		/***************************************************************************************************************/
		$("#sf").click(function() {
			if ($("#sf").attr("checked")) {
				$("#sf").val("Y");
				$("#StaffNo").val(formatTime());
				$("#StaffNo").attr("readonly", "readonly");
				$(".txt[id!='StaffNo']").val("");
				$("#location").val("");
				$("#locationflag").val("");
				$("#locationflag").attr("readonly", "readonly");
			} else {
				$("#sf").val("N");
				$("#StaffNo").val("");
				$("#StaffNo").removeAttr("readonly");
				$("#locationflag").removeAttr("readonly");
				$("#location").val("");
				$("#locationflag").val("");
			}
		});
		/**************************************EnglishProfession单击事件*****************************************/
		$("#EnglishProfession").focus(function() {
			$("#EnglishProfessionalTitle").show();
		});
		/*********************************************************************************************************/
	
		/******************************************************************************************/
	
		/********************************EnglishProfessionalTitle值發生改變時*******************/
		$("#EnglishProfessionalTitle").change(function() {
							if ($("#EnglishProfession").val().indexOf(
									$(this).val()) >= 0) {
								return false;
							} else {
								$.ajax({
											type : "post",
											url : "SelectProfessionalServlet",
											data : {
												'EnglishProfessionalTitle' : $(
														"#EnglishProfessionalTitle")
														.val()
											},
											success : function(date) {
												if (date != "[]"
														&& date != "null") {//没有中文
													if ($(
															"#ChineseProfessionalTitle")
															.val() == "null"
															|| $(
																	"#ChineseProfessionalTitle")
																	.val() == null) {//中文框为null
														$(
																"#ChineseProfessionalTitle")
																.val("");
													}

													professionalEText = $(
															"#EnglishProfession")
															.val()
															+ ($(
																	"#EnglishProfession")
																	.val() == "" ? ""
																	: ";")
															+ $(
																	"#EnglishProfessionalTitle")
																	.val();
													professionalCText = $(
															"#ChineseProfessionalTitle")
															.val()
															+ ($(
																	"#ChineseProfessionalTitle")
																	.val() == "" ? date
																	: ((date == "" ? ""
																			: ";") + date));

													$(
															"#ChineseProfessionalTitle")
															.val(
																	professionalCText);
													$("#EnglishProfession")
															.val(
																	professionalEText)
															.change();
												} else {
													professionalEText = $(
															"#EnglishProfession")
															.val()
															+ ($(
																	"#EnglishProfession")
																	.val() == "" ? ""
																	: ";")
															+ $(
																	"#EnglishProfessionalTitle")
																	.val();
													$("#EnglishProfession")
															.val(
																	professionalEText)
															.change();
												}
											}
										});
							}
						});


		
		/**************************************************************************************/
		function payers() {
			$.ajax({
				type : "post",
				url : "PayerServlet",
				data : {
					'payer' : $("#payer").val(),
					"type" : "position"
				},
				success : function(date) {
					if (date == "" || date == null) {
						error("支付人不存在,请重新录入!");
						pay = false;
						$("#payer").val("");
						$("#payer").focus();
						return false;
					} else {
						if (date == "TRUE" && $("#sf").attr("checked")) {
							error("该顾问暂时没有权限为他人办理名片!");//您暂时无权限帮他人办理名片
							$("#sf").removeAttr("checked");
							$("#payer").val("");
							$("#StaffNo").val(parent.staffcode).blur();
							pay = false;
						} else {
							pay = true;
							$.ajax({
								type : "post",
								url : "PayerServlet",
								data : {
									'payer' : $("#payer").val(),
									"type" : "name"
								},
								success : function(date) {
									$("#pay").val(date);
								},
								error : function() {
									error("支付人信息异常!");
									$("#payer").val("");
									$("#payer").focus();
									return false;
								}
							});
						}
					}
				}
			});
		}
		/************************************验证payer是否存在*************************/
		$("#payer").blur(function() {
			payers();
		});
		/****************************************pater验证结束***********************/
		$("#EnglishPosition")
				.change(
						function() {
							$.ajax({
										type : "post",
										url : "SelectPositionServlet",
										data : {
											'EnglishName' : $(
													"#EnglishPosition").val()
													.replace('\r\n', '')
													.replace('\r\n', '')
													.replace('\r\n', '')
										},
										success : function(date) {
											if (date != "[]" || date != "null"
													|| date == null) {
												$("#ChinesePosition").val(date);
												if ($("#ChinesePosition").val() == "null"
														|| $("#ChinesePosition")
																.val() == null) {
													$("#ChinesePosition").val(
															"");
												}
											}
										}
									});
						});

	 

		$("#AE").change(function() {
			if (AECode) {
				if ($("#AE").attr("checked")) {
					if ($("#sf").attr("checked")) {
						error("No Code 不能选择AE Consultant!");
						return false;
					} else {
						$("#AE").val("Y");
						$("#CCC").attr("checked", "checked").val("Y");
					}
				} else {
					$("#AE").val("N");
					$("#CCC").val("N").removeAttr("checked");
				}
			} else {
				$("#AE").val("N").removeAttr("checked");
				$("#CCC").val("N").removeAttr("checked");
			}
		});
		/****************************************************StaffNo 失去焦点时触发的时间****************************************************/
		$("#StaffNo")
				.blur(
						function() {

							$("#EnglishPosition").get(0).selectedIndex = 0;
							$("#ET,#AE,#urgentCase").val("N").removeAttr("checked");
							$("#EnglishProfessionalTitle").get(0).selectedIndex = 0;
							$(".txt[id!='StaffNo']").val("");
							if ($("#sf").attr("checked")) {
								return;
							}
							if ($("#StaffNo").val().length > 5) {
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
												if(result.staffNo==""){
													error("Cons Code為非法數據！");
													$(".txt").val("").html("");
													$("#StaffNo").focus();
													return false;
												}
												$("#num").val(100);
												$("#EnglishName").val(modifyString(result.englishName));
												$("#ChineseName").val(modifyString(result.name));
//												$("#TR_RegNo").val(modifyString(result.TR_RegNo));
//												$("#CENO").val(modifyString(result.CENo));
//												$("#MPFA_NO").val(modifyString(result.MPFNo));
												$("#Mobile").val(modifyString(result.mobilePhone));
												$("#FAX").val(modifyString(result.fax));
												$("#DirectLine").val(modifyString(result.directLine));
												$("#Email").val(modifyString(result.email.toLowerCase()));
												$("#EnglishPosition").val(modifyString(result.e_DepartmentTitle).replace('\r\n','')).change();
												$("#EnglishPostion1").val(modifyString(result.lastPosition_E).replace('\r\n',''));
												$("#ChinesePostion1").val(modifyString(result.lastPosition_C).replace('\r\n',''));
												$("#ChineseExternalTitle").val(modifyString(result.c_ExternalTitle));
												$("#EnglishExternalTitle").val(modifyString(result.e_ExternalTitle));
												$("#ChineseProfessionalTitle").val(modifyString(result.c_EducationTitle));
												$("#EnglishProfession").val(modifyString(result.e_EducationTitle));
												
							/* 					if (result.CENo != ""&& result.CENo != "null") {
													$("input[name='companys'][value='CAM']").attr("checked","checked");
												}else{
													$("input[name='companys'][value='CAM']").removeAttr("checked");
												} */
												if (result.grade == "PA"|| result.grade == "AC"|| result.grade == "CA"){
													$("#payer").val(result.recruiterId);
												}else{
													$("#payer").val(result.staffNo);
												}
												
												$("#AddNameCard").attr("disabled", false);
												licenseplate();
												payers();
												use();//名片使用量
												vailDD();
												vailAE();
												getLocationFullName();
											}else{
												error("Cons Code 不存在");
												$(".txt").val("").html("");
												$("#StaffNo").focus();
												return false;
											}
										}
									});
							} else {
								if ($("#StaffNo").val().length != 0) {
									error("Cons Code為非法數據！");
									$(".txt").val("").html("");
									$("#StaffNo").focus();
									return false;
								}

							}

						});
		/*****************************************表单提交***************************/
		$("#AddNameCard").click(function() {
			vailed();
			return false;
		});
		$("body").keydown(function(e) {
			if (e.keyCode == 13) {
				vailed();
				return false;
			}
		});
		/*****************************表单结束********************************/
		/********************************UrgentCase值发生改变时*********************/
		$("#urgentCase").change(function() {
			if ($("#urgentCase").attr("checked")) {//當urgentCase選中時
				$("#num").val("200");
			} else {
				$("#num").val("100");
			}
		});
		/**************************************************************************/

		function vailed() {
			var f=false;//是否已经有过确认提示
			var nums = $("#num").val();
			if ($("#StaffNo").val() == "") {
				error("Staff Code不能為空!");
				$("#StaffNo").focus();
				return false;
			}
			if ($("#num").val() == "") {
				error("Quantity不能為空！");
				$("#num").focus();
				return false;
			}
			if(getCompany()==""){
				error("Company 不能为空!");
				return false;
			}else{
				$("#company").val(getCompany());
			}
			if (isNaN(nums)) {
				error("Quantity 必須在 1~400 之間的數字!");
				$("#num").focus();
				return false;
			}
			if (!isNaN(nums)) {
				if (parseInt($("#num").val()) > 400) {
					error(" Quantity 數量單次不能大於 400！");
					$("#num").focus();
					return false;
				}
				if ($("#num").val() % 100 != 0) {
					error("quantity必须是100的整数倍！");
					$("#num").focus();
					return false;
				}
			}

			/**************************判断事件是否合法*****************/
			/**********************************************************/

			if ($("#EnglishProfession").val().split(";").length - 1 > 4) {
				error("English Academic & Professional Title只能包含4个专业title和一个学历title!");
				return false;
			}
			if ($("#urgentCase").attr("checked")) {//當urgentCase選中時
				$("#urgentCase").val("Y");
				if (parseInt($("#num").val()) < 200
						&& parseInt($("#num").val()) > 0) {
					error("在选择Urgent的情况下，Quantity不能低于200!");
					$("#num").focus();
					return false;
				}else{
					f=true;
					if (!confirm("Urgent Case 需要额外支付费用,确定要提交吗?")){
						return false;
					}
				}
			}
			
			//貌似trim()方法不支持
			if ($("#payer").val() != null) {
				if ($("#StaffNo").val().toLowerCase().replace(" ", "") != $(
						"#payer").val().toLowerCase().replace(" ", "")) {
					if (pay != true) {
						error("支付人不存在，請重新填寫！");
						$("#payer").focus();
						return false;
					} else {
						f=true;
						if (!confirm("StaffNo:" + $("#StaffNo").val()
								+ "與Payer:" + $("#payer").val() + "不同 "
								+ "\n          您確定要提交嗎？")) {
							return false;
						}
					}
				}
			} else {
				error("支付人不存在，請重新填寫！");
				return false;
			}
			if ($("#EnglishPosition").val() == ""
					|| $("#EnglishPosition").val() == null
					|| $("#EnglishPosition").val() == "") {
				error("Please Select Title_Department in English !");
				$("#EnglishPosition").focus();
				return false;

			}
			if ($("#EnglishProfessionalTitle").val() == "professional") {
				$("#EnglishProfessionalTitle").val("");
			}
			var remainNumber = parseFloat($("#agun").text().substring($("#agun").text().lastIndexOf("is ") + 3));
			if ($("#type").val() == "S" && !$("#urgentCase").attr("checked")) {
				if (nums > parseFloat(remainNumber)) {
					if (parseFloat(remainNumber) > 0) {
						f=true;
						if (!confirm("您的免费限额还剩" + remainNumber
								+ ",如果继续，超出部分将按正常收费!")) {
							$("#num").focus();
							return false;
						}
					} else {
						f=true;
						if (!confirm("您的免费限额已用完,如果继续，超出部分将按正常收费!")) {
							$("#num").focus();
							return false;
						}
					}
				}
			}
			$("#AddNameCard").attr("disabled", "disabled");
			if ($("#ET").attr("checked")) {
				$
						.ajax({
							url : "VailEliteServlet",
							type : "post",
							data : {
								"staffcode" : $("#StaffNo").val(),
								"table" : "request_new"
							},
							success : function(date) {
								if (date == "true") {
									f=true;
									if (!confirm("Elite Team 的免费限额已满,继续办理需支付额外费用")) {
										$("#ET").removeAttr("checked");
										$("#type").val("S");
										$("#AddNameCard").removeAttr("disabled");
										return false;
									}
								} else {
									if (parseFloat($("#num").val()) > 100) {
										f=true;
										if (!confirm("该顾问的Elite Team 的免费限额只有100,超出部分将正常收费，是否继续?")) {
											$("#save").attr("#disabled",
													"disabled");
											return false;
										}
									}
								}
								submitForm(f);
							},
							error : function() {
								$("#AddNameCard").removeAttr("checked");

								errorAlert("网络连接失败，请稍后重试...");
								return false;
							}
						});
			} else {
				submitForm(f);
			}

		}

		//NameCardWriteServlet

		



	});


    //获取牌照集合 ，包括PIBA+HKCIB+SFC+MPF	
	function licenseplate(){
		$.ajax({
			type:"post",
			url:"<%=basePath%>common/CommonReaderServlet",
			data:{"method":"getlicenseplate","staffcode":$("#StaffNo").val()},		
			dataType:"json",	
			success: function(date){
				if(date!=null && date!="[null]" && date!="null" && date!=""){
					$("#TR_RegNo").val(date.PIBA);
					$("#CENO").val(date.SFC);
					$("#MPFA_NO").val(date.MPF);
					$("#HKCIB_NO").val(date.HKCIB);
					checklist();
				}
			}
		});
	}	

 	function checklist(){
//	alert("PIBA："+$("#TR_RegNo").val()+"SFC："+$("#CENO").val()+"MPF："+$("#MPFA_NO").val()+"HKCIB："+$("#HKCIB_NO").val());
				
		//	PIBA SFC MPF HKCIB
		if($("#TR_RegNo").val()!="" && $("#CENO").val()!="" && $("#MPFA_NO").val()!="" && $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CAM']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA SFC HKCIB	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CAM']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA MPF HKCIB	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()==""&& $("#MPFA_NO").val()!=""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA SFC MPF 	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()!=""&& $("#HKCIB_NO").val()==""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CAM']").attr("checked","checked");
		//	SFC MPF HKCIB	
		}else if($("#TR_RegNo").val()==""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()!=""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CAM']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA SFC  	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()==""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CAM']").attr("checked","checked");
		//	PIBA HKCIB	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()==""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	SFC HKCIB	
		}else if($("#TR_RegNo").val()==""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CAM']").attr("checked","checked");
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA MPF 	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()==""&& $("#MPFA_NO").val()!=""&& $("#HKCIB_NO").val()==""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
		//  HKCIB	
		}else if($("#TR_RegNo").val()==""&& $("#CENO").val()==""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()!=""){
			$("input[name='companys'][value='CFSO']").attr("checked","checked");
		//	PIBA   	
		}else if($("#TR_RegNo").val()!=""&& $("#CENO").val()==""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()==""){
			$("input[name='companys'][value='CFS']").attr("checked","checked");
		//	SFC   	
		}else if($("#TR_RegNo").val()==""&& $("#CENO").val()!=""&& $("#MPFA_NO").val()==""&& $("#HKCIB_NO").val()==""){
			$("input[name='companys'][value='CAM']").attr("checked","checked");
		}else{
//			alert(" Failure to meet the conditions of application...");//不符合申请条件
			$("input[name='companys'][value='CFS']").attr("checked","checked");
		}
	
	}

	function getLocationFullName(){
		$.ajax({
			type: "post",
			url: "<%=basePath%>common/CommonReaderServlet",
			data:{"method":"getLocation","staffcode":$("#StaffNo").val()},
			success: function(dat){
				$("#locationflag").val(dat);
		        if($("#locationflag").val().indexOf("Nan Fung") > -1){
		        	$("#location").val("Nan Fung");
		        }else if($("#locationflag").val().indexOf("Cityplaza 3") > -1){
		        	$("#location").val("CP3");		        	
		        }else{
		        	$("#location").val("@CONVOY");
		        }
			},error:function(){
				alert("Network connection is failed, please try later…");
				return false;
			}
		});
	}
	//<label class="inline checkbox"> <input id="CC"1
		//type="checkbox" name="CFS" value="Y" checked="checked" />CFS </label>
</script>
</head>
<body style="overflow: auto;">
	<div class="cont-info">
		<div class="info-title">
			<div class="title-info">

				<form action="AddNewRequestServlet" id="myform" method="post">
					<table>
						<tr>
							<td class="tagName">Cons Code:</td>
							<td class="tagCont"><input id="StaffNo" name="StaffNos"
								onkeyup="this.value=this.value.toUpperCase();" type="text"
								size="20" class="txt" /> <label class="inline checkbox">
									<input type="checkbox" id="sf" name="sfs" value="N" /> No Code
							</label></td>
							<td class="tagName">quantity:</td>
							<td class="tagCont"><input name="nums" type="text"
								class="txt" id="num" size="5" maxlength="3" value="100"
								onkeyup="checkNum(this);" onkeypress="checkNum(this);"
								onfocus="javascript:$('#numId').show();" /> <select id="numId"
								name="nums" style="display: none;width:80px;"
								onchange=" $('#num').val($(this).val()) ;">
									<option value="100">100</option>
									<option value="200">200</option>
									<option value="300">300</option>
									<option value="400">400</option>
							</select></td>
						</tr>
						<tr>
							<td class="tagName">Location:</td>
							<td class="tagCont"><select name="locatins" id="location"></select><input type="text" id="locationflag" style="width:220px" />
							</td>
							<td class="tagName">Layout:</td>
							<td class="tagCont"><select name="types" id="type">
									<option value="S">Standard Layout</option>
							</select></td>
						</tr>
						<tr>
							<td class="tagName">AE Consultant:</td>
							<td class="tagCont"><label class="inline checkbox">
									<input type="checkbox" name="AEs" value="N" id="AE"
									onchange="javascript:if(AECode==false){return false;}"
									onclick="javascript:if(AECode==false){return false;}" />YES </label> <span>（如無AE
									Code請勿勾選）</span></td>
							<td class="tagName">Urgent Case:</td>
							<td class="tagCont"><label class="inlune checkbox">
									<input id="urgentCase" type="checkbox" name="urgent" value="N" />YES
							</label></td>
						</tr>
						<tr>
							<td class="tagName">Elite Team:</td>
							<td class="tagCont"><label class="inline checkbox">
									<input type="checkbox" name="ETs" value="N" id="ET"
									onchange="javascript:if(Elite==false){return false;}"
									onclick="javascript:if(Elite==false){return false;}" />YES </label> <span>（非Elite
									Team 成員請勿勾選）</span></td>
							<td class="tagName"></td>
							<td class="tagCont"></td>
						</tr>
						<tr>
							<td class="tagName">English Name:</td>
							<td class="tagCont"><input name="EnglishNames" type="text"
								size="25" class="txt" id="EnglishName" readonly="readonly"/></td>
							<td class="tagName">Chinese Name:</td>
							<td class="tagCont"><input id="ChineseName"
								name="ChineseNames" type="text" size="25" class="txt" /></td>
						</tr>
						<tr>
							<td class="tagName">Last print Title dept in English:</td>
							<td class="tagCont"><input type="text" id="EnglishPostion1"
								size="25" class="txt" /></td>
							<td class="tagName">Last print Title dept in Chinese</td>
							<td class="tagCont"><input type="text" id="ChinesePostion1"
								size="25" class="txt" /></td>
						</tr>
						<tr>
							<td class="tagName">Title Department in English:</td>
							<td class="tagCont"><select id="EnglishPosition"
								name="EnglishPositions">
									<option value="">Please Select PositionName</option>
							</select></td>
							<td class="tagName">Title Department in Chinese:</td>
							<td class="tagCont"><input id="ChinesePosition"
								name="ChinesePositions" type="text" size="25" class="txt" /></td>
						</tr>
						<tr>
							<td class="tagName">ExternalTitle Department in English:</td>
							<td class="tagCont"><input id="EnglishExternalTitle"
								name="EnglishExternalTitles" type="text" size="25" class="txt" />
							</td>
							<td class="tagName">ExternalTitle Department in Chinese:</td>
							<td class="tagCont"><input id="ChineseExternalTitle"
								name="ChineseExternalTitles" type="text" size="25" class="txt" />
							</td>
						</tr>
						<tr>
							<td class="tagName">English Academic & Professional Title:</td>
							<td class="tagCont" ><input type="text"
								name="EnglishProfessionalTitles" class="txt"
								id="EnglishProfession" size="70" /> <select
								id="EnglishProfessionalTitle" name="">
									<option value="">Please Select ProfessionalTitle</option>
							</select></td>
							
							
					 
							<td class="tagName">Chinese Academic & Professional Title:</td>
							<td class="tagCont" ><input
								id="ChineseProfessionalTitle" name="ChineseProfessionalTitles"
								type="text" size="70" class="txt" /></td>
						</tr>
						<tr>
							<td class="tagName">TR Reg NO:</td>
							<td class="tagCont"><input id="TR_RegNo" name="TR_RegNos"
								type="text" size="25" class="txt" readonly="readonly"/></td>
							<td class="tagName">CE NO:</td>
							<td class="tagCont"><input id="CENO" name="CENOs"
								type="text" size="25" class="txt" readonly="readonly"/></td>
						</tr>
						<tr>
							<td class="tagName">MPFA NO:</td>
							<td class="tagCont"><input id="MPFA_NO" name="MPFA_NOs"
								type="text" size="25" class="txt" readonly="readonly"/></td>
							<td class="tagName">CIB Reg. NO:</td>
							<td class="tagCont">
								<input id="HKCIB_NO" name="HKCIB_NOs" type="text" size="25" class="txt" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td class="tagName">Mobile:</td>
							<td class="tagCont"><input id="Mobile" name="Mobiles"
								type="text" size="25" class="txt" /></td>
							<td class="tagName">FAX:</td>
							<td class="tagCont"><input id="FAX" name="FAXs" type="text"
								size="25" class="txt" /></td>
							
						</tr>
						<tr>
							<td class="tagName">Direct Line:</td>
							<td class="tagCont"><input id="DirectLine"
								name="DirectLines" type="text" size="25" class="txt" /></td>
							<td class="tagName">E-Mail:</td>
							<td class="tagCont"><input id="Email" name="Emails"
								onkeyup="this.value=this.value.toLowerCase();" type="text"
								size="25" class="txt" /></td>
							
						</tr>
						<tr>
							<td class="tagName">Payer:</td>
							<td class="tagCont"><input id="payer" type="text"
								class="txt" name="payers" size="25" /></td>
							<td class="tagName"></td>
							<td class="tagCont"></td>
						</tr>
						<tr>
							<td class="tagName">Company:</td>
							<td class="tagCont" colspan="3" id="companys"></td>
						</tr>
						<%--
						
						
						<tr>
							<td class="tagCont" colspan="4" style="padding-left: 30px;">
								<label class="inline checkbox"> <input id="CC"
									type="checkbox" name="CFS" value="Y" checked="checked" />CFS </label>
								<label class="inline checkbox"> <input id="C"
									type="checkbox" name="CAM" value="N" />CAM </label> <label
								class="inline checkbox"> <input id="CCC" type="checkbox"
									name="CIS" value="N" />CIS </label> <label class="inline checkbox">
									<input id="C2" type="checkbox" name="CCL" value="N" />CCL </label> <label
								class="inline checkbox"> <input id="C3" type="checkbox"
									name="CFSH" value="N" />CFSH </label> <label class="inline checkbox">
									<input id="C4" type="checkbox" name="CMS" value="N" />CMS </label> <label
								class="inline checkbox"> <input id="C22" type="checkbox"
									name="CFG" value="N" />CFG </label> <label class="inline checkbox">
									<input id="C23" type="checkbox" name="Blank" value="N" />Blank
							</label> <input id="pay" type="hidden" name="pays" /><input
								id="DD_result" name="DD" type="hidden" />
							</td>

						</tr>
						--%>
						<tr>
							<td class="tagName" >
								<span id="agun_staffcode" style="font-size: 14px; font-weight: bold;"></span>
							</td>
							<td class="tagCont" colspan="3">
								<span id ="agun" style="font-size:14px; display: block; margin-top:5px; "> </span>
							</td>
						
						</tr>
						
						<tr>
							<td class="tagBtn" colspan="4">
							<input name="method" value="savenamecardrequest" type="hidden" />
								<input id="company" name="company" type="hidden" />
								<%--<div id="agun"style="color:#00305A;font-size:18px;font-family: 'Arial Narrow';"></div>--%> 
								<c:if test="${roleObj.audit==1}">
									<a class="btn" id="AddNameCard"> Submit </a>
								</c:if>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>




	<script type="text/javascript">
	var company=namecard_consultant_company.split(",");
	var comstring="";
	for ( var i = 0; i < company.length; i++) {
	//	comstring+="<label class='inline checkbox'><input type='checkbox' "+(company[i]=="CFS"?"checked='checked'":"")+" name='companys' value='"+company[i]+"' />"+company[i]+"</label>";
		comstring+="<label class='inline checkbox'><input type='checkbox' name='companys' value='"+company[i]+"' />"+company[i]+"</label>";
	}
	$("#companys").html(comstring);
	
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
	
	
	
	function submitForm(f) {
		if(!f){
			if(!confirm("确定提交申请?")){
				return false;
			}
		}
		$.ajax({
					//url:"AddStaffRequestConvoyServlet",
					url : base + "namecard/NameCardWriteServlet",
					type : "post",
					async : false,
					data : $('#myform').serialize(),
					beforeSend : function() {
						parent.showLoad();
						$("#AddNameCard").attr("disabled", true);
					},
					complete : function() {
						parent.closeLoad();
						$("#AddNameCard").attr("disabled", false);
					},
					success : function(date) {
						var result = $.parseJSON(date);
						if (result.state == "success") {
							successAlert(
									'Successfully submitted! The Cut-off date is Every Friday at 17:00 !',
									function() {
										location.reload();
									});
						} else if (result.state == "error") {
							errorAlert("Save error");
						} else {
							errorAlert(result.msg);
						}
					},
					error : function() {
						errorAlert("Network connection is failed, please try later...");
						return false;
					}
				});
		return false;
	}
	
	
	
	function CurentTime() {
		var now = new Date();
		var year = now.getFullYear(); //年
		var month = now.getMonth() + 1; //月
		var day = now.getDate(); //日
		var clock = year + "-";
		if (month < 10)
			clock += "0";
		clock += month + "-";
		if (day < 10)
			clock += "0";
		clock += day;
		return (clock);
	}
	
	function getlocation() {
		$
				.ajax({
					type : "post",
					//url : "QueryLocationServlet",
					//data : null,
					url: "common/CommonReaderServlet",
					data:{"method":"getlocation"},
					success : function(date) {
						var location = eval(date);
						var html = "";
						$("#location").empty();
						if (location.length > 0) {
							html += "<option value=''>请选择</option>";
							for ( var i = 0; i < location.length; i++) {
								html += "<option value='"+location[i].realName+"' >"
										+ location[i].name + "</option>";
							}
						} else {
							html += "<option value=''>加载异常</option>";
						}
						$("#location").append(html);
					},
					error : function() {
						errorAlert("网络连接失败,请返回登录页面重新登录!", function() {
						});

						return false;
					}
				});
	}
	
	function position() {
		$
				.ajax({
					type : "post",
					//url : "QueryPositionServlet",
					//data : null,
					url: "<%=basePath%>common/CommonReaderServlet",
					data:{"method":"getposition"},
					success : function(date) {
						var d = eval(date);
						var html;
						if (d.length > 0) {
							for ( var i = 0; i < d.length; i++) {
								html += "<option value='"
										+ d[i].position_ename.replace(
												'\r\n', '') + "' >"
										+ d[i].position_ename + "</option>";
							}
							$("#EnglishPosition:last").append(html);
						}
					}
				});
	}
	
	
 
	
	function professional() {
		$
				.ajax({
					type : "post",
					//url : "QueryProfessionalServlet",
					//data : null,
					url: "<%=basePath%>common/CommonReaderServlet",
					data:{"method":"getprofessional"},
					success : function(date) {
						var d = eval(date);
						var html;
						if (d.length > 0) {
							for ( var i = 0; i < d.length; i++) {
								html += "<option value='"+d[i].prof_title_ename+"'>"
										+ d[i].prof_title_ename
										+ "</option>";
							}
							$("#EnglishProfessionalTitle:last")
									.append(html);
						}
					}
				});
	}
	
	

	function getLayout() {
		//2014-04-14 King 注释   $("#type").empty().append("<option value='S'>Standard Layout</option><option value='P'>Premium Layout</option>");
		$("#type").empty().append(
				"<option value='S'>Standard Layout</option>");

	}
	
	/*******************************************获取随机Code**********************************************************/
	function formatTime() {

		var d = new Date();
		var date = "NC" + "" + d.getFullYear() + "" + (d.getMonth() + 1)
				+ "" + d.getDate() + "" + d.getHours() + ""
				+ d.getMinutes() + "" + d.getSeconds();
		return date;
	}
	/**
	* 名片使用量
	**/
	function use(){
		$.ajax({
			type : "post",
			//url : "QueryConsMarqueeServlet",
			url : base+"namecard/NameCardReaderServlet",
			data : {
				'code' : $("#StaffNo").val(),"method":"finduserUsage"
			},
			success : function(date) {
				if(date.indexOf("{")==0){
					var result=$.parseJSON(date);
					alert(result.msg);
				}else{
					var temp="";
					if(date.indexOf(",")>-1){
						temp=date.replace(".0","").replace(".0","").replace(".0","").split(",");	
						$("#agun_staffcode").html(temp[0]+":");
						$("#agun").html("<strong>"+temp[1]+"</strong>");
					}else{
						$("#agun").html("<strong>"+date+"</strong>");
					}
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
			url : "VialDDServlet",
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
	/**
	*
	* 验证是否为AE
	**/
	function vailAE(){
		$.ajax({
			url : "VailAEorConsultant",
			type : "post",
			data : {
				"staffcode" : $("#StaffNo").val()
			},
			success : function(date) {
				var dataRole = eval(date);
				if (dataRole != null
						&& dataRole != "null") {

					if (dataRole == "") {
						AECode = false;
						Elite = false;
					} else {
						if (dataRole[0] != ""
								&& dataRole[0] != null) {
							AECode = true;
						} else {
							AECode = false;
						}
						if (dataRole[1] != ""
								&& dataRole[1] != null) {
							Elite = true;
						} else {
							Elite = false;
						}
					}
				} else {
					errorAlert("System ERROR, please try later...");
					return false;
				}
			},
			error : function() {
				errorAlert("Network connection is failed, please try later...");
				return false;
			}
		});
	}


	</script>
</body>
</html>
