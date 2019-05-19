<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>修改组别</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="plug/css/site.css" />
		<link rel="stylesheet" href="plug/css/font-awesome.min.css" />
		<link rel="stylesheet" href="plug/css/layout.css" />
		<style type="text/css">
			.tagName{
				text-align: right;
				padding-right: 10px!important;
			}
</style>
	</head>

	<body>
		<div class="form-table">
			<c:if test="${!empty obj}">
				<form id="save_form">
					<table cellpadding="0" cellspacing="0" class="table-col-2">
						<tr>
							<td class="tagName">
								<fmt:message key="role.groupcode" />:
							</td>
							<td class="tagCont">
								<input type="text" id="groupcode" name="groupcode"
									value="${obj.groupcode}" tip="GroupCode" must="true" />
							</td>
						</tr>

						<tr>
							<td class="tagName">
								<fmt:message key="role.groupname" />:
							</td>
							<td class="tagCont">
								<input type="text" id="groupname" name="groupname"
									value="${obj.groupname}" tip="GroupName" />
							</td>
						</tr>

						<tr>
							<td class="tagName">
								<fmt:message key="role.groupfullname" />
							</td>
							<td class="tagCont">
								<input type="text" id="groupfullname" name="groupfullname"
									value="${obj.groupfullname}" tip="GroupFullName" />
							</td>
						</tr>
						<tr>
							<td class="tagName">
								<fmt:message key="public.status" />:
							</td>
							<td class="tagCont">
								<select name="sfyx">
									<c:if test="${obj.sfyx=='Y'}">
										<option value="Y">
											有效
										</option>
										<option value="N">
											失效
										</option>
									</c:if>
									<c:if test="${obj.sfyx=='N'}">
										<option value="N">
											失效
										</option>
										<option value="Y">
											有效
										</option>
									</c:if>
								</select>
							</td>
						</tr>
						<tr>
							<td class="tagName">
								<fmt:message key="public.remark" />:
							</td>
							<td class="tagCont">
								<textarea type="text" id="remark" name="remark"
									style="max-height: 80px; max-width: 170px;">${obj.remark}</textarea>
							</td>
						</tr>
					</table>
					<input type="hidden" name="method" value="modifyGroup" />
					<input type="hidden" name="groupid" value="${obj.groupid}" />
					<input type="hidden" name="createname" value="${obj.createname}" />
					<input type="hidden" name="createDate" value="${obj.createDate}" />
				</form>
			</c:if>
		</div>
		<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js">
</script>

		<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js">
</script>
		<script type="text/javascript">
var api = frameElement.api, W = api.opener;
api.button( {
	id : 'valueOk',
	name : '保存',

	callback : saveGroup
}, {
	name : "返回"
});

function saveGroup() {
	var flag = true;
	$("#save_form [must='true']").each(
			function() {
				if ($(this).val() == "") {
					W.showErroMessage("\"" + $(this).attr("tip") + "\""
							+ " Can't be empty !", 3);
					$(this).focus();
					flag = false;
					return false;
				} else {
					flag = true;
				}
			});

	if (flag) {
		W.$.dialog.confirm(
				"&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "确认保存?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", function() {
					$.ajax( {
						url : "SystemGroupServlet",
						type : "post",
						dataType : 'json',
						data : $("#save_form").serialize(),
						success : function(date) {
							if (date.Stu=='1') {
								W.$("#search").click();
								api.close();
							}
							W.alertMessage(date.Msg);
						},
						error : function() {
							W.showErroMessage("网络连接失败,请稍后重试...");
							return false;
						}
					});

				}, function() {
					W.tips("执行取消操作");
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
	</body>
</html>
