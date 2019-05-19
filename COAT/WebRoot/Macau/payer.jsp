<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>财务查詢頁面</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
		<script type="text/javascript">
/*****************************************************WindowForm Load *************************************/
var downs = null;
function shanchu(i) {
	if (confirm("您确定要删除该记录？不可恢复！")) {
		// window.location.href="DeletePayerServlet?StaffNo="+downs[i].staffCode+"&Date="+downs[i].addDate;
		$.ajax( {
			type : "post",
			url : "DeletePayerServlet",
			data : {
				'StaffNo' : downs[i].staffCode,
				'Date' : downs[i].addDate
			},
			success : function(date) {
				alert(date);
				$("tr[title='" + i + "']").remove();
			}
		});
	}
}
$(function() {
	$("#start_date").val(CurentTime());
	$("#end_date").val(CurentTime());
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
		clock += day + "";
		return clock;
	}
	/*********************************改变时间格式*********************************************/
	function changeDate2() {
		var date = $("#end_date").val();
		var pattern = /^(19|20)\d{2}-(0?\d|1[012])-(0?\d|[12]\d|3[01])$/;

		var year = date.substr(0, 4);
		var index1 = date.indexOf("-");
		var index2 = date.lastIndexOf("-");
		var cha = parseInt(index2) - (parseInt(index1) + 1);
		var month = date.substr((parseInt(index1) + 1), cha);
		var day = date.substr(index2 + 1, date.length);
		var ca, ba;
		if (month < 10) {
			ca = "-0";
		} else {
			ca = "-";
		}
		if (parseFloat(day) < 10) {
			ba = "-0";
		} else {
			ba = "-";
		}
		if (pattern.test($("#end_date").val())) {
			$("#end_date").val(
					year + ca + parseInt(month) + ba + parseFloat(day));
		}
	}
	function changeDate1() {
		var date = $("#start_date").val();
		var pattern = /^(19|20)\d{2}-(0?\d|1[012])-(0?\d|[12]\d|3[01])$/;
		var year = date.substr(0, 4);
		var index1 = date.indexOf("-");
		var index2 = date.lastIndexOf("-");
		var cha = parseInt(index2) - (parseInt(index1) + 1);
		var month = date.substr((parseInt(index1) + 1), cha);
		var day = date.substr(index2 + 1);
		var ca, ba;
		if (month < 10) {
			ca = "-0";
		} else {
			ca = "-";
		}
		if (parseFloat(day) < 10) {
			ba = "-0";
		} else {
			ba = "-";
		}
		if (pattern.test($("#start_date").val())) {
			$("#start_date").val(
					year + ca + parseInt(month) + ba + parseFloat(day));
		}
	}
	/**************************************************************************************/
	/****************************************************Search click***********************************/
	$("#searchs")
			.click(
					function() {

						/**if ($("#start_date").val() == "") {
							alert("開始日期不能為空！");
							return false;
						}
						if ($("#end_date").val() == "") {
							alert("結束日期不能為空！");
							return false;
						}**/
						if ($("#start_date").val() != ""
								&& $("#end_date").val() != "") {
							changeDate1();
							changeDate2();
							if ($("#start_date").val() > $("#end_date").val()) {
								alert("開始日期不能大於結束日期!");
								return false;
							}
						}
						$
								.ajax( {
									type : "post",
									url : "PayerMacauServlet",
									data : {
										'startDate' : $("#start_date").val(),
										'endDate' : $("#end_date").val(),
										'staffcode' : $("#codebtn").val()
									},
									success : function(data) {
										var totals = 0;
										var dataRole = eval(data);
										var html = "";
										$("tr[id='tid']").remove();
										downs = null;
										if (dataRole.length > 0) {
											downs = dataRole;
											for ( var i = 0; i < dataRole.length; i++) {

												html += "<tr id ='tid' title='"
														+ i
														+ "'><td align='center'>"
														+ dataRole[i].addDate
														+ "</td><td align='center'>"
														+ dataRole[i].staffCode
														+ "</td><td align='center'>"
														+ dataRole[i].name
														+ "</td><td align='center'>"
														+ dataRole[i].number
														+ "</td><td align='center'>"
														+ dataRole[i].amount
														+ "</td><td align='center'>"
														+ dataRole[i].payer
														+ "</td><td align='center'>"
														+ dataRole[i].remarks
														+ "</td><td align='center'><c:if test='${roleObj.delete==1}'><a href='javascript:void(0);' onclick='shanchu("
														+ i + ")'>" + "刪除"
														+ "</a></c:if></td></tr>";
											}
										} else {
											html += "<tr id ='tid'><td colspan='8' align='center'>"
													+ "對不起，沒有您想要的數據!"
													+ "</td></tr>";

										}
										$("#jqajax:last").append(html);
									}
								});
					});
	/***********************************************************Search Click end************************/
	/*****************************************************Down click**********************************/

	$("#down").click(
			function() {
				if (downs != null) {
					window.location.href = "DownMacauRecordServlet?staffcode="
							+ $("#codebtn").val() + "&startDate="
							+ $("#start_date").val() + "&endDate="
							+ $("#end_date").val();
				} else {
					alert("请先查询数据，再做导出相关操作！");
				}

			});
	/******************************************************Down END***********************************/

});
</script>
		<style type="text/css">
<!--
#Layer1 {
	position: absolute;
	width: 100%;
	height: 592px;
	z-index: 1;
}
-->
</style>
</head>
<body>
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">開 始 日 期：</td>
				<td class="tagCont">
					<input id="start_date" type="text" readonly="readonly" class="Wdate" onClick="return Calendar('start_date');" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">結 束 日 期：</td>
				<td class="tagCont">
					<input id="end_date" type="text" class="Wdate" readonly="readonly" onClick="return Calendar('end_date');" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input type="text" name="codebtn" id="codebtn" />
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
			<tr>
				<td class="tagCont" colspan="2"></td>
				<td class="tagCont" colspan="2">
					<c:if test="${roleObj.search==1}">
						<a class="btn" id="searchs" name="search">
							<i class="icon-search"></i>
							查詢
						</a>
					</c:if>
					<c:if test="${roleObj.export==1}">
						<a class="btn" id="down" name="downs">
							<i class="icon-download"></i>
							導出
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
				<th class="width_">Date</th>
				<th class="width_">StaffCode</th>
				<th class="width_">Name</th>
				<th class="width_">Qty</th>
				<th class="width_">Amount</th>
				<th class="width_">Payer</th>
				<th class="width_">Remarks</th>
				<th class="width_">Operation</th>
			</tr>
			</thead>
		</table>
	</div>
</div>
</body>
</html>
