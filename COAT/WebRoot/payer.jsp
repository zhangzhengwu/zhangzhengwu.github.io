<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>查詢頁面</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">


		<script type="text/javascript">
/*****************************************************WindowForm Load *************************************/
var downs = null;
var pagenow = 1;
var totalpage = 1;
var total = 0;
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
			selects(pagenow);
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

	/**************************************************************************************/

	/****************************************************Search click***********************************/
	$("#searchs").click(function() {
		selects(1);
	});
	/**if($("#start_date").val()==""){
	alert("開始日期不能為空！");
	return false;
	}	if($("#end_date").val()==""){
	alert("結束日期不能為空！");
	return false;
	}
	 **/

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


	/***********************************************************Search Click end************************/

	/*****************************************************Down click**********************************/

	$("#down").click(
			function() {
				if (downs != null) {
					window.location.href = "DownChargeServlet?staffcode="
							+ $("#codebtn").val() + "&startDate="
							+ $("#start_date").val() + "&endDate="
							+ $("#end_date").val();
				} else {
					alert("请先查询数据，再做导出相关操作！");
				}

			});
	/******************************************************Down END***********************************/

});

	function selects(pagenow) {
		if ($("#start_date").val() != "" && $("#end_date").val() != "") {
			if ($("#start_date").val() > $("#end_date").val()) {
				alert("開始日期不能大於結束日期!");
				return false;
			}
		}

		$
				.ajax( {
					type : "post",
					url : "QueryChargeServlet",
					data : {
						'startDate' : $("#start_date").val(),
						'endDate' : $("#end_date").val(),
						'staffcode' : $("#codebtn").val(),
						'pageNow' : pagenow
					},
					beforeSend: function(){
						parent.showLoad();
					},
					complete: function(){
						parent.closeLoad();
					},
					success : function(data) {
						var dataRole = eval(data);
						var html = "";
						$("tr[id='tid']").remove();
						downs = null;
						if (dataRole[3] > 0) {
							total = dataRole[3];
							pagenow = dataRole[2];
							totalpage = dataRole[1];
							downs = dataRole[0];
							for ( var i = 0; i < dataRole[0].length; i++) {

								html += "<tr id ='tid' title='"
										+ i
										+ "'><td >"
										+ dataRole[0][i].addDate
										+ "</td><td >"
										+ dataRole[0][i].staffCode
										+ "</td><td >"
										+ dataRole[0][i].name
										+ "</td><td align='center'>"
										+ dataRole[0][i].number
										+ "</td><td align='center'>"
										+ dataRole[0][i].amount
										+ "</td><td align='center'>"
										+ dataRole[0][i].payer
										+ "</td><td >"
										+ dataRole[0][i].remarks
										+ "</td><td align='center'>" +
										"<c:if test='${roleObj.delete==1}'><a href='javascript:void(0);' onclick='shanchu("
										+ i + ")'>" + "刪除" + "</a></c:if></td></tr>";
							}
							$(".page_and_btn").show();
							$("#total").empty().append(
									dataRole[2] + "/" + dataRole[1]);
						} else {
							html += "<tr id ='tid'><td colspan='8' align='center'>"
									+ "對不起，沒有您想要的數據!" + "</td></tr>";
							$(".page_and_btn").hide();
						}
						$("#jqajax:last").append(html);
						$("tr[id='tid']:even").css("background","#COCOCO");
            			$("tr[id='tid']:odd").css("background","#F0F0F0");
						page(pagenow, totalpage);
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
</script>
		<style type="text/css">
<!--
#Layer1 {
	position: absolute;
	width: 100%;
	height: 592px;
	z-index: 1;
}.page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}
img{
height:12px;
width:12px;
}
-->
</style>
</head>
<body>
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">開始日期：</td>
				<td class="tagCont">
					<input id="start_date" type="text" readonly="readonly" class="Wdate" onClick="return Calendar('start_date');" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">結束日期：</td>
				<td class="tagCont">
					<input id="end_date" type="text" class="Wdate" readonly="readonly" onclick="return Calendar('end_date');" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">顾问编号：</td>
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
						<a class="btn" id="searchs"><i class="icon-search"></i>查詢</a>
					</c:if>
					<c:if test="${roleObj.export==1}">
						<a class="btn" id="down"><i class="icon-download"></i>導出</a>
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
				<th class="width_">Staff Code</th>
				<th class="width_">Name</th>
				<th class="width_">Quantity</th>
				<th class="width_">Amount</th>
				<th class="width_">Payer</th>
				<th class="width_">Remarks</th>
				<th class="width_">操作</th>
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
</body>
</html>
