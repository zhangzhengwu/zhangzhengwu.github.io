<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'UserManager_info.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
			<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=basePath%>plug/css/font-awesome.min.css">
		<!--[if lte IE 7]> 
		<link rel="stylesheet"href="plug/css/font-awesome-ie7.css">

		<![endif]-->
		<link rel="stylesheet" href="<%=basePath%>css/layout.css">
		<link rel="stylesheet" href="<%=basePath%>plug/css/site.css">
	
		<style type="text/css">
div.cont-info,.info-search {
	width: 100%;
	height: 100%
}

.info-search table {
	width: 100%;
	height: 95%;
}

.info-search table .tagName {
	text-align: right;
}

.sel_group {
	margin-bottom: 10px;
}

.sel_group span {
	margin-left: 10px;
	background-color: rgb(206, 183, 183);
	border: 1px solid brown;
	padding: 5px;
	color: white;
	font-size: 0.8em;
	cursor: pointer;
	float: left;
	margin-bottom: 7px;
}
</style>
	</head>

	<body>
		<div class="cont-info">
			<div class="info-title">
			<div class="title-info">
				<form id="save_form">
					<table cellpadding="0" cellspacing="0" class="table-col-2">
						<tr>
						
						<td class="tagName">
								<fmt:message key="user.loginname" />:
							</td>
							<td class="tagCont">
								<input type="text" id="loginname" name="loginname"
									tip='<fmt:message key="user.loginname" />' must="true" />
						</td>
						
						<td class="tagName">
								<fmt:message key="system.password" />:
							</td>
							<td class="tagCont">
								<input type="text" id=loginpass name="loginpass"
									tip='<fmt:message key="system.password" />' must="true" />
							</td>
						
						
							
						</tr>
						<tr>
								<td class="tagName">
								<fmt:message key="user.usercode" />:
							</td>
							<td class="tagCont">
								<input type="text" id="usercode" name="usercode"
									tip='<fmt:message key="user.usercode" />' must="true" />
							</td>
					
							<td class="tagName">
								<fmt:message key="user.truename" />:
							</td>
							<td class="tagCont">
								<input type="text" id="truename" name="truename" tip='<fmt:message key="user.truename" />'
									must="true" />
							</td>
						</tr>
						<tr>
							<td class="tagName">
								<fmt:message key="public.englishname" />:
							</td>
							<td class="tagCont">
								<input type="text" id="englishname" name="englishname"
									tip='<fmt:message key="public.englishname" />' must="true" />
							</td>
							<td class="tagName">
								<fmt:message key="public.chinesename" />:
							</td>
							<td class="tagCont">
								<input type="text" id="chinesename" name="chinesename"
									tip='<fmt:message key="public.chinesename" />' must="true" />
							</td>
						</tr>
						<tr>
							<td class="tagName">
								<fmt:message key="public.idcard" />:
							</td>
							<td class="tagCont">
								<input type="text" id="idcard" name="idcard"
									 />
							</td>
							
							
							
							<td class="tagName">
								<fmt:message key="user.sex" />:
							</td>
							<td class="tagCont">
								<select id="Sex" name="sex" tip="sex">
									<option value="男">
										男
									</option>
									<option value="女">
										女
									</option>
								</select>
							</td>
						</tr>
						<tr>
							
							<td class="tagName">
								<fmt:message key="role.groupname" />:
							</td>
							<td class="tagCont">
								<select id="Group" name="Group" tip="Group" must="true">
									<c:forEach var="item2" items="${gList}" varStatus="itemStu">

										<option value="${item2['groupid']}">
											${item2['groupname']}
										</option>
									</c:forEach>
								</select>

							</td>
							<td class="tagName">
								<fmt:message key="user.allowlogin" />:
							</td>
							<td class="tagCont">
								<select id="allowLogin" name="allowLogin" >
									<option value="Y">
										Yes
									</option>
									<option value="N">
										No
									</option>
								</select>
							</td>
							
							
						</tr>
						<tr>
							<td class="tagName">
								<fmt:message key="role.groupselected" />:
							</td>
							<td class="tagCont">
								<div id="sel_group" class="sel_group"></div>
		
							</td>
							
						</tr>
						<tr>
							<td class="tagName">
								<fmt:message key="client.dateofbirth" />:
							</td>
							<td class="tagCont">
								<input id="birthday" name="birthday" type="text"
									readonly="readonly" onClick="Calendar('birthday')"
									 />
								<i class="icon-trash icon-large i-trash"
									style="cursor: pointer;" id="clear1" align="middle"
									onClick="javascript:$('#startDate').val('');"></i>
							</td>
							
							<td class="tagName">
								<fmt:message key="client.email" />:
							</td>
							<td class="tagCont">
								<input id="email" name="email" type="text"
								must="true" tip="<fmt:message key='client.email' />" />
							</td>
						</tr>
						<tr>
							<td class="tagName">
								<fmt:message key="user.registration" />:
							</td>
							<td class="tagCont">
								<input type="text" id="registration" name="registration"
									/>
							</td>
							<td class="tagName">
								<fmt:message key="user.dept" />:
							</td>
							<td class="tagCont">
								<input type="text" id="dept" name="dept"  />
							</td>
						</tr>
						<tr>
							<td class="tagName">
								<fmt:message key="user.position" />:
							</td>
							<td class="tagCont">
								<input type="text" id="postion" name="postion" 
									/>
							</td>
						</tr>
						<tr>
							<td class="tagName">
								<fmt:message key="client.address" />:
							</td>
							<td class="tagCont">
								<textarea type="text" id="address" name="address"
									style="max-height: 80px; max-width: 170px;" 
									></textarea>
							</td>
						</tr>

					</table>
					<input type="hidden" name ="groupid" id ="groupid" />
					<input type="hidden" name="method" value="saveUser" />
				</form>
			</div>

		</div>
</div>










		<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="css/date.js"></script>
		<script type="text/javascript">
var api = frameElement.api, W = api.opener;
api.button( {
	id : 'valueOk',
	name : '保存',

	callback : saveMenu
}, {
	name : "返回"
});




var temp = [];
$(function() {
	$("#save_form [must='true']").after("<span style='color:red;'>*</span>");
			$("#Group").change(
					function() {
						v = $(this).children('option:selected').val();
						n = $(this).children('option:selected').html();
						if (temp[v] != 1) {
							$("#sel_group").append(
									'<span title="点我取消选择" onclick="this.remove();temp['
											+ v + ']=0;" id="' + v + '">' + n
											+ '</span>');
							temp[v] = 1;
						}
					});
		});












function getGrouList(){
	var str='';
	$('#sel_group > span').each(function(n){
		if(n!=0){
			str+=",";
		}
		str +=$(this).attr("id");
	});
	$("#groupid").val(str);
}
function saveMenu() {
	var flag = true;
	$("#save_form [must='true']").each(
			function() {
				if ($(this).val() == "") {
					
				//	<fmt:message key="public.notempty">
//<fmt:param value="xsdg"></fmt:param>
//</fmt:message>
					W.showErroMessage("<fmt:message key='public.notempty'><fmt:param value='"+ $(this).attr("tip")+"'></fmt:param></fmt:message> "
							, 3);
				//	W.showErroMessage("\"" + $(this).attr("tip") + "\""
					//		+ " Can't be empty !", 3);
					$(this).focus();
					flag = false;
					return false;
				} else {
					flag = true;
				}
			});
	getGrouList();
	if (flag) {
		W.$.dialog.confirm(
				"&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "确认保存?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", function() {
					$.ajax( {
						url : "SystemUserServlet",
						type : "post",
						data : $("#save_form").serialize(),
						success : function(date) {
							var result=$.parseJSON(date);
							if (result.state == "success") {
								W.$("#search").click();
								api.close();
							}
							W.alertMessage(result.msg);

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
