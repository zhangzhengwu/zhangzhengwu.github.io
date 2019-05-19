<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>用户管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=basePath%>plug/css/font-awesome.min.css">
		<!--[if lte IE 7]> 
		<link rel="stylesheet"href="plug/css/font-awesome-ie7.css">
		<style>
			.cont-info{float:right;width:99%;padding-left:12px;}
			.info-search table input[type="text"]{height:14px;padding-top:0px;width:165px;}
			</style>
		<![endif]-->
		<link rel="stylesheet" href="<%=basePath%>css/layout.css">
		<link rel="stylesheet" href="<%=basePath%>plug/css/site.css">
		<link rel="stylesheet" href="<%=basePath%>plug/css/pager.css">
				

	</head>

	<body>
		<c:choose>
			<c:when test="${eMsg!=''}">
				${eMsg}
			</c:when>
			<c:otherwise>
				<div class="cont-info">
					<!-- 查询div -->
					<div class="info-search">
						<form id="search_form">
							<table>
								<tr>
									<td class="tagName">
										<fmt:message key="public.startdate" />
									</td>
									<td class="tagCont">
										<input id="startDate" name="startDate" type="text"
											readonly="readonly" onClick="Calendar('startDate')" />
										<i class="icon-trash icon-large i-trash" id="clear1"
											align="middle" onClick="javascript:$('#startDate').val('');"></i>

									</td>
									<td class="tagName">
										<fmt:message key="public.enddate" />
									</td>
									<td class="tagCont">
										<input id="endDate" name="endDate" type="text"
											readonly="readonly" onClick="Calendar('endDate')" />
										<i class="icon-trash icon-large i-trash" id="clear1"
											align="middle" onClick="javascript:$('#endDate').val('');"></i>
									</td>
								</tr>
								<tr>
									<td class="tagName">
										<fmt:message key="user.loginname" />
									</td>
									<td class="tagCont">
										<input id="loginName" name="loginname" type="text" />
									</td>
									<td class="tagName">
										<fmt:message key="user.usercode" />
									</td>
									<td class="tagCont">
										<input id="userCode" name="usercode" type="text" />
									</td>
								</tr>
								<tr>
									<td class="tagName">
										<fmt:message key="public.englishname" />:
									</td>
									<td class="tagCont">
										<input id="loginName" name="englishname" type="text" />
									</td>
									<td class="tagName">
										<fmt:message key="public.chinesename" />:
									</td>
									<td class="tagCont">
										<input id="userCode" name="chinesename" type="text" />
									</td>
								</tr>
								<tr>
									<td class="tagName">
										<fmt:message key="user.validstate" />:
									</td>
									<td class="tagCont">
										<select id="status" name="status">
											<option value="">
											<fmt:message key="public.choose" />
											</option>
											<option value="Y">
												<fmt:message key="public.effective" />
											</option>
											<option value="N">
												<fmt:message key="public.invalid" />
											</option>
										</select>
									</td>
								
									<td class="tagName">
										<fmt:message key="user.dept" />
									</td>
									<td class="tagCont" colspan="2">
										
									 <input name="dept" type="text"/>
										
									</td>
								</tr>
								<tr>
										<td class="tagName">
										</td>
										<td class="tagCont">
										
										</td>
										<td class="tagCont" colspan="2">
											<button class="btn" id="search" type="button">
												<span class="icon icon-search"> </span>
												<fmt:message key="public.search" />
											</button>
											<c:if test="${roleObj['add']>0}">
												<button class="btn" id="add" type="button" onclick="addUser();">
													<span class="icon icon-plus-sign"> </span>
													<fmt:message key="public.add" />
												</button>
											</c:if>
										</td>
									</tr>
							</table>
							<input type="hidden" name="menuid" id="menuid"
											value="${menuid}" />
										<input type="hidden" name="pageNow" id="pageNow" value="1" />
										<input type="hidden" id="method" name="method" value="select" />
						</form>
					</div>

					<!-- 内容DIV -->
					<div class="info-table">
						<table id="table_result">
							<thead>
								<tr>
									<th class="wid_5">
										<fmt:message key="public.num" />
									</th>
									<th class="wid_5">
										<fmt:message key="user.loginname" />
									</th>
									<th class="wid_5">
										<fmt:message key="user.usercode" />
									</th>
									<th class="wid_5">
										<fmt:message key="public.englishname" />
									</th>
									<th class="wid_10">
										<fmt:message key="public.chinesename" />
									</th>
									<th class="wid_5">
										<fmt:message key="user.sex" />
									</th>
									<th class="wid_15">
										<fmt:message key="user.dept" />
									</th>
									<th class="wid_10">
										<fmt:message key="user.registration" />
									</th>
									<th class="wid_10">
										<fmt:message key="user.position" />
									</th>
								
									<th class="wid_5">
										<fmt:message key="public.status" />
									</th>
									<th class="wid_20">
										<fmt:message key="public.operation" />
									</th>
								</tr>
							</thead>

						</table>
					</div>


					<div id="pageResult" class="pageinfo"></div>

					<!-- 修改层 -->
				</div>
			</c:otherwise>
		</c:choose>
		<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js">
</script>

		<script type="text/javascript">
var basepath = "<%=basePath%>";
function searchPage(pageNow) {
	$("#pageNow").val(pageNow);
	$("#method").val("select");
	$.ajax( {
				url : basepath + "SystemUserServlet",
				type : "post",
				data : $('#search_form').serialize(),
				beforeSend: function(){
					parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				success : function(date) {
					var dataRole = eval(date);
					var html = "";
					$("tr.table_result").remove();
					if(dataRole.length==0){
						html += "<tr class='table_result' style='height:60px;'><td colspan='11'>没有获取到模块权限,不能显示数据列表...</td></tr>";
					}else{
					if (dataRole[2] == null) {
						html += "<tr class='table_result' style='height:60px;'><td colspan='11'>没有获取到模块权限,不能显示数据列表...</td></tr>";
					} else {
					if (dataRole[1].allRows > 0) {
						var ismod=0;
						var isaudit=0;
						var isdel=0;
						if(dataRole[2].upd>0){
							ismod=1;
						}
						if(dataRole[2].audit>0){
							isaudit=1;
						}
						if(dataRole[2].delete>0){
							isdel=1;
						}
						for ( var i = 0; i < dataRole[0].length; i++) {
							html += "<tr class='table_result'><td>"
									+ parseFloat(((dataRole[1].curPage - 1) * dataRole[1].pageSize)
											+ i + 1)
									+ "</td><td>"
									+ dataRole[0][i].loginname
									+ "</td><td>"
									+ dataRole[0][i].usercode
									+ "</td><td>"
									+ dataRole[0][i].englishname
									+ "</td><td>"
									+ dataRole[0][i].chinesename
									+ "</td><td>"
									+ dataRole[0][i].sex
									+ "</td><td>"
									+ dataRole[0][i].dept
									+ "</td><td>"
									+ dataRole[0][i].registration
									+ "</td><td>"
									+ dataRole[0][i].postion
								
									+ "</td><td>"
									+ (dataRole[0][i].sfyx == "Y" ? "有效" : "失效")
									+ "</td><td>";
									if(ismod==1){
									html += "<a href='javascript:void(0);' onclick='updateUser("
									+ dataRole[0][i].userid
									+ ")'> <fmt:message key='public.modify' /></a>&nbsp;";
									}
									
									if(isaudit==1){
									html += "<a href='javascript:void(0);' onclick='roleUser("+ dataRole[0][i].userid+ ")'><fmt:message key='public.ability' /></a>";
									}
									if(isdel==1){
									html +="&nbsp;<a href='javascript:void(0);' onclick='delUser("
									+ dataRole[0][i].userid + ");'> <fmt:message key='public.delete' /></a>";
									}
									html +=  "</td></tr>";

						}
						InitPager(dataRole[1].allRows, dataRole[1].curPage,
								dataRole[1].pageSize);
					} else {//没有数据
						html += "<tr class='table_result' ><td colspan='11'>No Data Show...</td></tr>";
					}
					}
					}
					$("#table_result:last").append(html);
					
				},
				error : function() {
					alert("网络连接失败,请稍后重试...");
					return false;
				}

			});

}

$(function() {

	$("#search").click(function() {
		searchPage(1);
	});

});
/**
 * 生成分页信息
 * @param {Object} RecordCount 总记录数
 * @param {Object} PageIndex 当前页码
 */
function InitPager(RecordCount, PageIndex, PageCount) {
	$("#pageResult").setPager( {
		RecordCount : RecordCount,
		PageIndex : PageIndex,
		PageSize : PageCount,
		buttonClick : PageClick
	});
	//$("#result").html("您点击的是第" + PageIndex + "页");
	//pageindex = PageIndex;
};

/**
 * 执行分页单击事件
 * @param {Object} RecordCount 总记录数
 * @param {Object} PageIndex 当前页码
 */
PageClick = function(RecordCount, PageIndex) {
	//InitPager(RecordCount, PageIndex);
	searchPage(PageIndex);
};

function roleUser(id) {
	$.dialog( {
		title : '用户权限编辑',
		id : 'menu_new',
		width : 780,
		height : 500,
		cover : true,
		drag : false,
		lock : true,
		
		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath
				+ "SystemUserServlet?method=loadRoleUser&userid=" + id + ""
	});
	load();
}

function updateUser(id) {
	$.dialog( {
		title : '更新用户信息',
		id : 'menu_new',
		width : 750,
		height : 440,
		cover : true,
		drag : false,
		lock : true,

		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath
				+ "SystemUserServlet?method=detailUser&userid=" + id + ""
	});
	load();
}

/**
 * 删除用户
 * @param {Object} userid
 */
function delUser(userid) {
	if (confirm("确定删除该用户?")) {
		$.ajax( {
			url : basepath + "SystemUserServlet",
			type : "post",
			data : {
				"method" : "delUser",
				"userid" : userid
			},
			success : function(date) {
				alert(date);
				$("#search").click();
			},
			error : function() {
				alert("网络连接失败,请稍后重试...");
				return false;
			}
		});
	}
}
/**
 * 数据加载中
 */
this.load = function() {
	showMessage("数据加载中...", 0.5);
}
/**
 * 整个页面提示信息
 * @param {Object} message
 * @param {Object} timer
 */
this.showMessage = function(message, timer) {
	$.dialog.tips(message, timer);
}

this.showErroMessage = function(message, timer) {
	$.dialog.tips("<span style='color:red'>" + message + "</span>", timer);
}
this.alertMessage = function(message) {
	var alert = $.dialog.alert(message);
	alert.lock();
}

function createAddUserUI() {
	$.dialog( {
		title : '新增用户',
		id : 'menu_new',
		width : 750,
		height : 440,
		cover : true,
		drag : false,
		lock : true,

		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath + "SystemUserServlet?method=loadGroup"
	});
	load();
}

/**
 *添加用户
 */
function addUser() {
	createAddUserUI();
}
</script>
		<script type="text/javascript" src="plug/js/jquery-pager-1.0.js"></script>
		<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
		<%--<script type="text/javascript" src="./js/main.js"></script>
		--%><script src="css/date.js" type="text/javascript"></script>
	</body>
</html>
