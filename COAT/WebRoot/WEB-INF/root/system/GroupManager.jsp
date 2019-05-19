<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>组管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	
		<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=basePath%>plug/css/font-awesome.min.css">
		<!--[if lte IE 7]> 
		<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
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
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td class="tagName">
										<fmt:message key="public.startdate" />:
									</td>
									<td class="tagCont">
										<input id="startDate" name="startDate" type="text"
											readonly="readonly" onClick="Calendar('startDate')" />
										<i class="icon-trash icon-large i-trash" id="clear1"
											align="middle" onClick="javascript:$('#startDate').val('');"></i>

									</td>
									<td class="tagName">
										<fmt:message key="public.enddate" />:
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
										<fmt:message key="role.groupcode" />:
									</td>
									<td class="tagCont">
										<input id="groupCode" name="groupcode" type="text" />
									</td>
									<td class="tagName">
										<fmt:message key="role.groupname" />:
									</td>
									<td class="tagCont">
										<input id="groupName" name="groupname" type="text" />
									</td>
								</tr>

								<tr>
									<td class="tagName"></td>
									<td class="tagCont"></td>
									<td class="tagCont" colspan="2">
										
										<button class="btn" id="search" type="button">
											<span class="icon icon-search"> </span>
											<fmt:message key="public.search" />
										</button>
										<c:if test="${roleObj['add']>0}">
											<button class="btn" id="add" type="button">
												<span class="icon icon-plus-sign"> </span>
												<fmt:message key="public.add" />
											</button>
										</c:if>
									 	 
										<input type="hidden" name="pageNow" id="pageNow" value="1" />
										<input type="hidden" name="menuid" id="menuid"
											value="${menuid}" />
										<input type="hidden" id="method" name="method" value="select" />
									</td>
								</tr>

							</table>
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
									<th class="wid_10">
										<fmt:message key="role.groupcode" />
									</th>
									<th class="wid_10">
										<fmt:message key="role.groupname" />
									</th>
									<th class="wid_10">
										<fmt:message key="role.groupfullname" />
									</th>
									<th class="wid_10">
										<fmt:message key="public.remark" />
									</th>
									<th class="wid_10">
										<fmt:message key="role.createname" />
									</th>
									<th class="wid_15">
										<fmt:message key="role.createdate" />
									</th>
									<th class="wid_10">
										<fmt:message key="public.status" />
									</th>
									<th class="wid_10">
										<fmt:message key="public.operation" />
									</th>
								</tr>
							</thead>

						</table>
					</div>

					<div id="pageResult" class="pageinfo"></div>
				</div>
			</c:otherwise>
		</c:choose>
		<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js">
</script>

		<script type="text/javascript">
var basepath = "<%=basePath%>";
$(function() {

	$("#add").click(function() {
		addGroup();
	})

	$("#search").click(function() {
		searchPage(1);
	});

});
function searchPage(pageNow) {
	$("#pageNow").val(pageNow);
	$("#method").val("select");
	$.ajax( {
				url : "SystemGroupServlet",
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
						html += "<tr class='table_result' ><td colspan='11'>没有获取到模块权限,不能显示数据列表...</td></tr>";
					}else{
					if (dataRole[2] == null) {
						html += "<tr class='table_result' ><td colspan='11'>没有获取到模块权限,不能显示数据列表...</td></tr>";
					} else {
						if (dataRole[1].allRows > 0) {
							var ismod=0;
							var isdel=0;
							var isaudit=0;
								if(dataRole[2].audit>0){
									isaudit=1;
								}
							if(dataRole[2].upd>0){
								ismod=1;
							}
							if(dataRole[2].delete>0){
								isdel=1;
							}
							for ( var i = 0; i < dataRole[0].length; i++) {
								html += "<tr class='table_result'><td>"
										+ parseFloat(((dataRole[1].curPage - 1) * dataRole[1].pageSize)
												+ i + 1)
										+ "</td><td>"
										+ dataRole[0][i].groupcode
										+ "</td><td>"
										+ dataRole[0][i].groupname
										+ "</td><td>"
										+ dataRole[0][i].groupfullname
										+ "</td><td>"
										+ dataRole[0][i].remark
										+ "</td><td>"
										+ dataRole[0][i].createname
										+ "</td><td>"
										+ dataRole[0][i].createDate
										+ "</td><td>"
										+ (dataRole[0][i].sfyx == "Y" ? "有效"
												: "失效")
										+ "</td><td>";
										if(ismod==1){
										html += "<a href='javascript:void(0);' onclick='updateGroup("
										+ dataRole[0][i].groupid
										+ ")'> <fmt:message key='public.modify' /></a>" 
										+"&nbsp;";}
										if(isaudit==1){
										html +="<a href='javascript:void(0);' onclick='roleGroup("
										+ dataRole[0][i].groupid
										+ ",\""
										+ dataRole[0][i].groupname
										+ "\");'><fmt:message key='public.ability' /></a>" ;
										}
										if(isdel==1){
										html += "&nbsp;<a href='javascript:void(0);' onclick='delGroup("
										+ dataRole[0][i].groupid
										+ ");'> <fmt:message key='public.delete' /></a>";}
										html += "</td></tr>";

							}
							InitPager(dataRole[1].allRows, dataRole[1].curPage,
								dataRole[1].pageSize);
						} else {//没有数据
							html += "<tr class='table_result' ><td colspan='11'><fmt:message key='public.nodata' /></td></tr>";
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

function roleGroup(id, grouname) {
	$.dialog( {
		title : '[' + grouname + ']角色权限编辑',
		id : 'menu_edit',
		width : 780,
		height : 550,
		cover : true,
		drag : false,
		lock : true,

		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath
				+ "SystemGroupServlet?method=loadRoleGroup&groupid=" + id
	});
	load();
}
function addGroup() {
	$.dialog( {
		title : '新建组',
		id : 'menu_new',
		width : 500,
		height : 300,
		cover : true,
		drag : false,
		lock : true,

		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url: " + basepath + "PageControlAction?m=WEB-INF/root/system/GroupManager_add.jsp"
	});
	load();
}
function updateGroup(id) {
	$.dialog( {
		title : '更新组信息',
		id : 'menu_upd',
		width : 500,
		height : 300,
		cover : true,
		drag : false,
		lock : true,

		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath
				+ "SystemGroupServlet?method=detailGroup&groupid=" + id + ""
	});
	load();
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
/**
 * 删除用户
 * @param {Object} userid
 */
function delGroup(groupid) {
	if (confirm("确定删除该角色?")) {
		$.ajax( {
			url : "SystemGroupServlet",
			type : "post",
			data : {
				"method" : "delGroup",
				"groupid" : groupid
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
</script>
		<script src="css/date.js" type="text/javascript">
</script>
		<script type="text/javascript" src="./plug/js/jquery-pager-1.0.js">
</script>
		<script type="text/javascript" src="./plug/lhgdialog/lhgdialog.min.js">
</script>
	</body>
</html>
