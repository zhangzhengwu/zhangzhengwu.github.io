<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'UserManager_role.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js">
</script>
		<script type="text/javascript" src="plug/lhgdialog/lhgdialog.js">
</script>
		<script type="text/javascript" src="js/main.js">
</script>
<link rel="stylesheet" href="plug/css/layout.css">
		<style>

.operation_box input[type="checkbox"] {
	width: 20px;
	/**height: 20px;**/
}
</style>
<style type="text/css">
table{border-collapse: collapse; width: 100%;}
table tr{}
table td{ text-align: left; border: 1px solid #ddd; padding: 6px 0px 6px 10px;}
.textIndent{padding-left: 25px; font-size: 13px; }
</style>

	</head>
	<body>
		<form id="save_form">
				<table>
					<tr class="title">
						<td class="width_25">菜单名</td>
						<td class="width_8">全选</td>
						<td class="width_8">添加</td>
						<td class="width_8">删除</td>
						<td class="width_8">查询</td>
						<td class="width_8">修改</td>
						<td class="width_8">导出</td>
						<td class="width_8">审核</td>
						<td class="width_8">其他</td>
					</tr>
					<c:forEach var="item" items="${loginGMenuList}" varStatus="itemStu">
						<c:set value="${item['menuid']}" var="menuid" />
						<c:set value="${item['parentId']}" var="parentid" />
						<c:set value="-1" var="add" />
						<c:set value="-1" var="upd" />
						<c:set value="-1" var="del" />
						<c:set value="-1" var="search" />
						<c:set value="-1" var="other" />
						<c:set value="-1" var="export" />
						<c:set value="-1" var="audit" />
						<c:set value="0" var="yCountTemp" />
						<c:set value="0" var="yCount" />
						<c:set value="${item['menuroleid']}" var="menuroleid" />
						<c:set value="${item['menuid']}" var="menuid" />
						<c:if test="${item['add']=='Y'}">
							<c:set value="${yCount+1}" var="yCount" />
							<c:set value="0" var="add" />
						</c:if>
						<c:if test="${item['audit']=='Y'}">
							<c:set value="${yCount+1}" var="yCount" />
							<c:set value="0" var="audit" />
						</c:if>
						<c:if test="${item['upd']=='Y'}">
							<c:set value="${yCount+1}" var="yCount" />
							<c:set value="0" var="upd" />
						</c:if>
						<c:if test="${item['delete']=='Y'}">
							<c:set value="${yCount+1}" var="yCount" />
							<c:set value="0" var="del" />
						</c:if>
						<c:if test="${item['search']=='Y'}">
							<c:set value="${yCount+1}" var="yCount" />
							<c:set value="0" var="search" />
						</c:if>
						<c:if test="${item['other']=='Y'}">
							<c:set value="${yCount+1}" var="yCount" />
							<c:set value="0" var="other" />
						</c:if>
						<c:if test="${item['export']=='Y'}">
							<c:set value="${yCount+1}" var="yCount" />
							<c:set value="0" var="export" />
						</c:if>
						<c:if test="${yCount !=0}">
							<tr class="operation_ul">
									<c:choose>
										<c:when test="${item['parentId']==0}">
											<td class="menuName">
										</c:when>
										<c:otherwise>
											<td class="menuName textIndent">
										</c:otherwise>
									</c:choose>
									${item['menuname']}
									</td>
									<c:forEach var="item2" items="${modifyGMenuList}"
										varStatus="itemStu_index">
										<c:set value="${item2['menuid']}" var="tempMenuid" />
										<c:if test="${menuid==tempMenuid}">
											<c:set value="${item2['menuroleid']}" var="menuroleid" />
											<c:if test="${item['add']=='Y'}">
												<c:choose>
													<c:when test="${item2['add']=='Y'}">
														<c:set value="1" var="add" />
														<c:set value="${yCountTemp+1}" var="yCountTemp" />
													</c:when>
													<c:otherwise>
														<c:set value="0" var="add" />
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${item['upd']=='Y'}">
												<c:choose>
													<c:when test="${item2['upd']=='Y'}">
														<c:set value="1" var="upd" />
														<c:set value="${yCountTemp+1}" var="yCountTemp" />
													</c:when>
													<c:otherwise>
														<c:set value="0" var="upd" />
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${item['delete']=='Y'}">
												<c:choose>
													<c:when test="${item2['delete']=='Y'}">
														<c:set value="1" var="del" />
														<c:set value="${yCountTemp+1}" var="yCountTemp" />
													</c:when>
													<c:otherwise>
														<c:set value="0" var="del" />
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${item['search']=='Y'}">
												<c:choose>
													<c:when test="${item2['search']=='Y'}">
														<c:set value="1" var="search" />
														<c:set value="${yCountTemp+1}" var="yCountTemp" />
													</c:when>
													<c:otherwise>
														<c:set value="0" var="search" />
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${item['other']=='Y'}">
												<c:choose>
													<c:when test="${item2['other']=='Y'}">
														<c:set value="1" var="other" />
														<c:set value="${yCountTemp+1}" var="yCountTemp" />
													</c:when>
													<c:otherwise>
														<c:set value="0" var="other" />
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${item['export']=='Y'}">
												<c:choose>
													<c:when test="${item2['export']=='Y'}">
														<c:set value="1" var="export" />
														<c:set value="${yCountTemp+1}" var="yCountTemp" />
													</c:when>
													<c:otherwise>
														<c:set value="0" var="export" />
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${item['audit']=='Y'}">
												<c:choose>
													<c:when test="${item2['audit']=='Y'}">
														<c:set value="1" var="audit" />
														<c:set value="${yCountTemp+1}" var="yCountTemp" />
													</c:when>
													<c:otherwise>
														<c:set value="0" var="audit" />
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if
												test="${add == 1 || upd == 1 || export == 1 || audit == 1
												|| other == 1 || del == 1}">
												<c:if test="${search != -1}">
													<c:set value="1" var="search" />
												</c:if>
											</c:if>
										</c:if>
									</c:forEach>
										<c:choose>
											<c:when test="${yCountTemp==yCount}">
												<td class="menu_operation">
													<input id="${menuroleid}" type="checkbox" checked="true"
														pid="${parentid}" mid="${menuid}" />
												</td>
											</c:when>
											<c:otherwise>
												<td class="menu_operation">
													<input id="${menuroleid}" type="checkbox" pid="${parentid}"
														mid="${menuid}" />
												</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${add==1}">
												<td class="operation_box">
													<input id="${menuroleid}_add" pid="${parentid}__add"
														mid="${menuid}__add" type="checkbox" checked="true"
														value="Y" />
												</td>
											</c:when>
											<c:when test="${add==0}">
												<td class="operation_box">
													<input id="${menuroleid}_add" pid="${parentid}__add"
														mid="${menuid}__add" type="checkbox" value="N" />
												</td>
											</c:when>
											<c:otherwise>
												<td class="operation_box">
													<input id="${menuroleid}_add" pid="${parentid}__add"
														mid="${menuid}__add" disabled="true" title="没有权限操作"
														type="checkbox" value="-1" />
												</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${del==1}">
												<td class="operation_box">
													<input id="${menuroleid}_delete" pid="${parentid}__delete"
														mid="${menuid}__delete" type="checkbox" checked="true"
														value="Y" />
												</td>
											</c:when>
											<c:when test="${del==0}">
												<td class="operation_box">
													<input id="${menuroleid}_delete" pid="${parentid}__delete"
														mid="${menuid}__delete" type="checkbox" value="N" />
												</td>
											</c:when>
											<c:otherwise>
												<td class="operation_box">
													<input id="${menuroleid}_delete" pid="${parentid}__delete"
														mid="${menuid}__delete" disabled="true" title="没有权限操作"
														type="checkbox" value="-1" />
												</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${search==1}">
												<td class="operation_box">
													<input id="${menuroleid}_select" pid="${parentid}__select"
														mid="${menuid}__select" type="checkbox" checked="true"
														value="Y" />
												</td>
											</c:when>
											<c:when test="${search==0}">
												<td class="operation_box">
													<input id="${menuroleid}_select" pid="${parentid}__select"
														mid="${menuid}__select" type="checkbox" value="N" />
												</td>
											</c:when>
											<c:otherwise>
												<td class="operation_box">
													<input id="${menuroleid}_select" pid="${parentid}__select"
														mid="${menuid}__select" disabled="true" title="没有权限操作"
														type="checkbox" value="-1" />
												</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${upd==1}">
												<td class="operation_box">
													<input id="${menuroleid}_update" pid="${parentid}__update"
														mid="${menuid}__update" type="checkbox" checked="true"
														value="Y" />
												</td>
											</c:when>
											<c:when test="${upd==0}">
												<td class="operation_box">
													<input id="${menuroleid}_update" pid="${parentid}__update"
														mid="${menuid}__update" type="checkbox" value="N" />
												</td>
											</c:when>
											<c:otherwise>
												<td class="operation_box">
													<input id="${menuroleid}_update" pid="${parentid}__update"
														mid="${menuid}__update" disabled="true" title="没有权限操作"
														type="checkbox" value="-1" />
												</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${export==1}">
												<td class="operation_box">
													<input id="${menuroleid}_export" pid="${parentid}__export"
														mid="${menuid}__export" type="checkbox" checked="true"
														value="Y" />
												</td>
											</c:when>
											<c:when test="${export==0}">
												<td class="operation_box">
													<input id="${menuroleid}_export" pid="${parentid}__export"
														mid="${menuid}__export" type="checkbox" value="N" />
												</td>
											</c:when>
											<c:otherwise>
												<td class="operation_box">
													<input id="${menuroleid}_export" pid="${parentid}__export"
														mid="${menuid}__export" disabled="true" title="没有权限操作"
														type="checkbox" value="-1" />
												</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${audit==1}">
												<td class="operation_box">
													<input id="${menuroleid}_audit" pid="${parentid}__audit"
														mid="${menuid}__audit" type="checkbox" checked="true"
														value="Y" />
												</td>
											</c:when>
											<c:when test="${audit==0}">
												<td class="operation_box">
													<input id="${menuroleid}_audit" pid="${parentid}__audit"
														mid="${menuid}__audit" type="checkbox" value="N" />
												</td>
											</c:when>
											<c:otherwise>
												<td class="operation_box">
													<input id="${menuroleid}_audit" pid="${parentid}__audit"
														mid="${menuid}__audit" disabled="true" title="没有权限操作"
														type="checkbox" value="-1" />
												</td>
											</c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${other==1}">
												<td class="operation_box">
													<input id="${menuroleid}_other" pid="${parentid}__other"
														mid="${menuid}__other" type="checkbox" checked="true"
														value="Y" />
												</td>
											</c:when>
											<c:when test="${other==0}">
												<td class="operation_box">
													<input id="${menuroleid}_other" pid="${parentid}__other"
														mid="${menuid}__other" type="checkbox" value="N" />
												</td>
											</c:when>
											<c:otherwise>
												<td class="operation_box">
													<input id="${menuroleid}_other" pid="${parentid}__other"
														mid="${menuid}__other" disabled="true" title="没有权限操作"
														type="checkbox" value="-1" />
												</td>
											</c:otherwise>
										</c:choose>
							</tr>
						</c:if>
					</c:forEach>
					<c:if test="${loginGMenuListSize==0}">
					No data show!
				</c:if>
			</table>
			<input type="hidden" name="groupid" value="${groupid}" />
			<input type="hidden" id="paramList" name="paramList" value="-1" />
			<input type="hidden" name="method" value="saveRoleGroup" />
		</form>
	</body>
	<script type="text/javascript">
var paramArray = [];
$('body').ready(function() {
	$("input[type='checkbox']").each(function() {
		$(this).click(function() {

			if ($(this).attr("id").indexOf("_") != -1) {
				change_CheckBox(this, "");
			} else {
				change_CheckBox(this, "all");

			}
			//当前行
				getRowInfo(this);
				//当前行的父级的变化
				getRowInfo($("input[mid=" + $(this).attr("pid") + "]"));
			})
	});
})
function checkedAll() {
	var list = Array();
	var temp = [];
	var data = {}
	$("input[type='checkbox']").each(function(k, item) {
		if (item.id.indexOf("_") != -1) {
			id = item.id.substr(0, item.id.indexOf("_"));
			if (temp[id] != 1) {
				var add = $("#" + id + "_add").val();
				var del = $("#" + id + "_delete").val()
				var sel = $("#" + id + "_select").val()
				var upd = $("#" + id + "_update").val();
				var exp = $("#" + id + "_export").val();
				var audit = $("#" + id + "_audit").val();
				var other = $("#" + id + "_other").val();
				var data = {
					"menuroleid" : id,
					"add" : add == undefined ? "-1" : add,
					"del" : del == undefined ? "-1" : del,
					"sel" : sel == undefined ? "-1" : sel,
					"upd" : upd == undefined ? "-1" : upd,
					"exp" : exp == undefined ? "-1" : exp,
					"audit" : audit == undefined ? "-1" : audit,
					"other" : other == undefined ? "-1" : other

				}
				paramArray[id] = data
				temp[id] = 1;
			}
		}
	})
}
function getRowInfo(item) {
	if (item == undefined || item == null) {
		return;
	}
	if ($(item).attr("id").indexOf("_") != -1) {
		id = $(item).attr("id").substr(0, $(item).attr("id").indexOf("_"));
		var add = $("#" + id + "_add").val();
		var del = $("#" + id + "_delete").val()
		var sel = $("#" + id + "_select").val()
		var upd = $("#" + id + "_update").val();
		var exp = $("#" + id + "_export").val();
		var audit = $("#" + id + "_audit").val();
		var other = $("#" + id + "_other").val();
		var data = {
			"menuroleid" : id,
			"add" : add == undefined ? "-1" : add,
			"del" : del == undefined ? "-1" : del,
			"sel" : sel == undefined ? "-1" : sel,
			"upd" : upd == undefined ? "-1" : upd,
			"exp" : exp == undefined ? "-1" : exp,
			"audit" : audit == undefined ? "-1" : audit,
			"other" : other == undefined ? "-1" : other

		}
		paramArray[id] = data;
	} else {
		//select all 
		id = $(item).attr("id");
		var add = $("#" + id + "_add").val();
		var del = $("#" + id + "_delete").val()
		var sel = $("#" + id + "_select").val()
		var upd = $("#" + id + "_update").val();
		var exp = $("#" + id + "_export").val();
		var audit = $("#" + id + "_audit").val();
		var other = $("#" + id + "_other").val();
		var data = {
			"menuroleid" : id,
			"add" : add == undefined ? "-1" : add,
			"del" : del == undefined ? "-1" : del,
			"sel" : sel == undefined ? "-1" : sel,
			"upd" : upd == undefined ? "-1" : upd,
			"exp" : exp == undefined ? "-1" : exp,
			"audit" : audit == undefined ? "-1" : audit,
			"other" : other == undefined ? "-1" : other

		}
		paramArray[id] = data;
	}
}

var all_select_flag = false;
function all_select() {
	$("input[type='checkbox']:not(:disabled)").each(
			function(i, item) {
				if ($(item).attr("id").indexOf("_") == -1) {
					if (all_select_flag == false) {
						$(item).attr("checked", true);
						$("[id ^=" + $(item).attr("id") + "_]:not(:disabled)")
								.attr("checked", true);
						$("[id ^=" + $(item).attr("id") + "_]:not(:disabled)")
								.val("Y");
					} else {
						$(item).attr("checked", false);
						$("[id ^=" + $(item).attr("id") + "_]:not(:disabled)")
								.attr("checked", false);
						$("[id ^=" + $(item).attr("id") + "_]:not(:disabled)")
								.val("N");
					}

				}
			})
	if (all_select_flag) {
		all_select_flag = false;
	} else {
		all_select_flag = true;
	}
	checkedAll();
	return false;

}
function change_CheckBox(obj, type) {
	var indexid = $(obj).attr("id");
	var pid = $(obj).attr("pid");
	var mid = $(obj).attr("mid");
	if (type == "all") {
		if ($(obj).attr("checked") == "checked") {
			$("[id ^=" + indexid + "_]:not(:disabled)").attr("checked", true);
			$("[id ^=" + indexid + "_]:not(:disabled)").val("Y");

			//如果存在父节点，也要相应的处理

			$("input[mid ^=" + pid + "__]:not(:disabled)")
					.attr("checked", true);
			$("input[mid ^=" + pid + "__]:not(:disabled)").val("Y");
			$("input[mid =" + pid + "]:not(:disabled)").attr("checked", true);
			$("input[mid =" + pid + "]:not(:disabled)").val("Y");
			//$("input[mid ^=" + pid + "]:not(:disabled)").attr("checked", true);

		} else {
			//parent node
			//取消的时候，先判断是否存在子级有选中，如果有选中则不能取消这个列！
			if ($("input[pid =" + mid + "]:not(:disabled):checked").length > 0) {

				$("[id ^=" + indexid + "_]:not(:disabled)").attr("checked",
						true);
				$("[id ^=" + indexid + "_]:not(:disabled)").val("Y");
				$(obj).attr("checked", true);

			} else {
				$("[id ^=" + indexid + "_]:not(:disabled)").attr("checked",
						false);
				$("[id ^=" + indexid + "_]:not(:disabled)").val("N");

				$("input[pid ^=" + mid + "]:not(:disabled):checked").each(
						function(k, item) {
							$(
									"input[mid ^=" + $(item).attr("pid")
											+ "]:not(:disabled)").attr(
									"checked", true);
							$(
									"input[mid ^=" + $(item).attr("pid")
											+ "]:not(:disabled)").val("Y");

						})
			}
			/*
			$("input[mid ^=" + pid + "]:not(:disabled)").attr("checked", false);
			$("input[mid ^=" + pid + "]:not(:disabled)").val("N");*/

		}
	} else {
		//把类似1_delete的ID截取数字的部分
		id = indexid.substr(0, indexid.indexOf("_"));
		pidNum = pid.substr(0, pid.indexOf("_"));
		if ($(obj).attr("checked") == "checked") {
			$(obj).attr("checked", true);
			$(obj).val("Y");
			$("#" + id + "_select:not(:disabled)").attr("checked", true);
			$("#" + id + "_select:not(:disabled)").val("Y");

			//parentid
			$("input[mid=" + pid + "]:not(:disabled)").attr("checked", true);
			$("input[mid=" + pid + "]:not(:disabled)").val("Y");

			$("input[mid=" + pidNum + "__select]:not(:disabled)").attr(
					"checked", true);
			$("input[mid=" + pidNum + "__select]:not(:disabled)").val("Y");

		} else {

			if ($("input[pid=" + $(obj).attr("mid") + "]:checked").length > 0) {
				//如果当前操作的这个记录存在子级勾选，则父级不能取消
				$("#" + id + "_select:not(:disabled)").attr("checked", true);
				$("#" + id + "_select:not(:disabled)").val("Y");
				$(obj).attr("checked", true);
				$(obj).val("Y");
			} else {

				if ($("input[pid=" + $(obj).attr("pid") + "]:checked").length > 0) {

				} else {
					//如果同父级别下还有别的自己勾选了，父级不能取消
					//parentid
					$("input[mid=" + pid + "]:not(:disabled)").attr("checked",
							false);
					$("input[mid=" + pid + "]:not(:disabled)").val("N");

				}
				$(obj).attr("checked", false);
				$(obj).val("N");
				var flag = true;
				$("[id ^=" + id + "_]:not(:disabled)[checked=checked]").each(
						function() {
							if (this.id.substr(indexid.indexOf("_") + 1,
									this.id.length) != "select") {
								flag = false;
							}
						})
				if (flag) {
					$("#" + id + "_select:not(:disabled)").attr("checked",
							false);
					$("#" + id + "_select:not(:disabled)").val("N");

					//取消的时候，先判断select框是否存在子级有选中，如果有选中则不能取消这个列！
					if ($("input[pid ^=" + pidNum
							+ "__select]:not(:disabled):checked").length > 0) {

					} else {
						$("input[mid=" + pidNum + "__select]:not(:disabled)")
								.attr("checked", false);
						$("input[mid=" + pidNum + "__select]:not(:disabled)")
								.val("N");
					}

				} else {
					$("#" + id + "_select:not(:disabled)")
							.attr("checked", true);
					$("#" + id + "_select:not(:disabled)").val("Y");
					$("input[mid=" + pidNum + "__select]:not(:disabled)").attr(
							"checked", true);
					$("input[mid=" + pidNum + "__select]:not(:disabled)").val(
							"Y");
				}
			}
		}
	}
}
var api = frameElement.api, W = api.opener;
api.button( {
	name : "返回"
}, {
	id : 'valueOk',
	name : '保存',

	callback : saveMenu
}, {
	id : 'ALlSelect',
	name : '全选',
	callback : all_select
});
function makeupParam() {
	var list = Array();
	paramArray.sort();
	$.each(paramArray, function() {
		if (this.menuroleid != undefined) {
			list.push(this);
		}
	})
	var datas = JSON.stringify(list);
	$("#paramList").val(datas);
}

function saveMenu() {
	var flag = true;
	if (flag) {
		W.$.dialog.confirm(
				"&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "确认保存?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", function() {
					makeupParam();
					$.ajax( {
						url : "SystemGroupServlet",
						type : "post",
						dataType : 'json',
						data : $("#save_form").serialize(),
						beforeSend : function() {
							showLoad();//打开加载层  
					},
					complete : function(data) {
						closeLoad();
						//closeLoad();//关闭加载层  
					},
					success : function(date) {
						loadingflag = false;
						$("#paramList").val("");
						if (date.Stu == "1") {
							W.$("#search").click();
							api.close();
						}
						W.alertMessage(date.Msg);

					},
					error : function() {
						$("#paramList").val("");
						W.showErroMessage("网络连接失败,请稍后重试...");
						return false;
					}
					});

				}, function() {
					$("#paramList").val("");
					//W.alertMessage("执行取消操作");
				//return false;
			}, this);

	}
	return false;
}

function vailNumber(obj) {
	if (obj.value != "" && !obj.value.match(/^[0-9]{0,3}$/)) {
		W.showErroMessage("只能输入1-9的数字");
		obj.value = "";
		obj.select();
		return false;
	}
}
</script>
</html>
