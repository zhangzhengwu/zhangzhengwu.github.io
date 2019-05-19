<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>菜单管理</title>

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
											onClick="javascript:$('#startDate').val('');"></i>

									</td>
									<td class="tagName">
										<fmt:message key="public.enddate" />:
									</td>
									<td class="tagCont">
										<input id="endDate" name="endDate" type="text"
											readonly="readonly" onClick="Calendar('endDate')" />
										<i class="icon-trash icon-large i-trash" id="clear1"
											onClick="javascript:$('#endDate').val('');"></i>
									</td>
								</tr>
								<tr>
									<td class="tagName">
										<fmt:message key="menu.menuname" />:
									</td>
									<td class="tagCont">
										<input id="menuname" name="menuname" type="text" />
									</td>
									<td class="tagName">
										<fmt:message key="menu.parentmenuname" />:
									</td>
									<td class="tagCont">
										<input id="parentmenuname" name="parentMenuname" type="text" />
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
											<button class="btn" id="add" type="button"
												onclick="addMenu()">
												<span class="icon icon-plus-sign"> </span>
												<fmt:message key="public.add" />
											</button>
										</c:if>
 
										<input type="hidden" name="menuid" id="menuid"
											value="${menuid}" />
										<input id="pageNow" type="hidden" name="pageNow" value="1" />
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
									<th class="wid_15">
										<fmt:message key="menu.menuname" />
									</th>
									<th class="wid_30">
										<fmt:message key="menu.menuurl" /> 
									</th>
									<th class="wid_10">
										<fmt:message key="menu.parentmenuname" />
									</th>
									<th class="wid_5">
										<fmt:message key="menu.childsort" />
									</th>
									<th class="wid_3">
										<fmt:message key="menu.parentsort" />
									</th>
									<th class="wid_10">
										<fmt:message key="public.remark" />
									</th>
									<th class="wid_3">
										<fmt:message key="public.status" />
									</th>
									<th class="wid_15">
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


		<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>

		<script type="text/javascript">
var basepath = "<%=basePath%>";
$(function() {

	$("#search").click(function() {
		searchPage(1);

	});

});



/**
 * 删除菜单
 * @param {Object} userid
 */
function delMenu(menuid) {
	$.dialog.confirm(
			"&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "确认删除该菜单?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", function() {
				$.ajax( {
					url : "SystemMenuServlet",
					type : "post",
					data : {
						"method" : "delMenu",
						"menuid" : menuid
					},
					success : function(date) {
						alertMessage(date);
						$("#search").click();
					},
					error : function() {
						alert("网络连接失败,请稍后重试...");
						return false;
					}
				});
			}, function() {
				tips("执行取消操作");
			}, this);

}
</script>

		<script type="text/javascript" src="plug/lhgdialog/lhgdialog.js"></script>
	<script type="text/javascript" src="plug/js/jquery-pager-1.0.js"></script>
		<script src="css/date.js" type="text/javascript"></script>
		<script type="text/javascript">
function searchPage(pageNow) {
	$("#pageNow").val(pageNow);
	$("#method").val("select");
	$.ajax( {
				url : "SystemMenuServlet",
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
						if(dataRole[2].upd>0){
							ismod=1;
						}
						if(dataRole[2].delete>0){
							isdel=1;
						}
						var num = 0;
		
						/***
						//遍历父菜单
						for(var i=0;i<dataRole[0].length;i++){
							if(dataRole[0][i].parentName==""){
								html+="<tr class='table_result'><td>" +parseFloat(((dataRole[1].curPage-1)*dataRole[1].pageSize)+num+1)
								+"</td><td >"+dataRole[0][i].menuname
								+"</td><td>"+dataRole[0][i].menuAction
								+"</td><td align='left'>"+dataRole[0][i].parentName
								//+"</td><td>"+dataRole[0][i].childshort
								+"</td><td>"+""
								+"</td><td>"+dataRole[0][i].parentshort
								+"</td><td>"+dataRole[0][i].remark
								+"</td><td>"+(dataRole[0][i].sfyx=="Y"?"有效":"失效")
								+"</td><td>"+"<a href='javascript:void(0);' onclick='modifyMenu("+dataRole[0][i].menuid+")'> 修改</a>&nbsp;<a href='javascript:void(0);'>菜单权限</a>&nbsp;<a href='javascript:void(0);' onclick='delMenu("+dataRole[0][i].menuid+");'> 删除</a>"
								+"</td></tr>";
								num++;
								//遍历子菜单
								for(var j=0;j<dataRole[0].length;j++){
									if(dataRole[0][j].parentName!="" && dataRole[0][i].menuid==dataRole[0][j].parentId){
										html+="<tr class='table_result'><td>"  +parseFloat(((dataRole[1].curPage-1)*dataRole[1].pageSize)+num+1)
											+"</td><td align='left'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='icon icon-arrow-right'></span>"+dataRole[0][j].menuname
											+"</td><td>"+dataRole[0][j].menuAction
											+"</td><td>"+dataRole[0][j].parentName
						<%--										+"</td><td>"+(dataRole[0][j].childshort=="0"?"":dataRole[0][j].childshort)--%>
						<%--										+"</td><td>"+(dataRole[0][j].parentshort=="0"?"":dataRole[0][j].parentshort)--%>
											+"</td><td>"+dataRole[0][j].remark
											+"</td><td>"+(dataRole[0][j].sfyx=="Y"?"有效":"失效")
											+"</td><td>"+"<a href='javascript:void(0);' onclick='modifyMenu("+dataRole[0][j].menuid+")'> 修改</a>&nbsp;<a href='javascript:void(0);'>菜单权限</a>&nbsp;<a href='javascript:void(0);' onclick='delMenu("+dataRole[0][j].menuid+");'> 删除</a>"
											+"</td></tr>";
										num++;
									}
								}
							}
							
							
						}
						
						
						 **/
						for ( var i = 0; i < dataRole[0].length; i++) {
							html += "<tr class='table_result'><td>"
									+ parseFloat(((dataRole[1].curPage - 1) * dataRole[1].pageSize)
											+ num + 1)
									+ "</td><td style='text-align:left;' >"
									+ dataRole[0][i].menuname
									+ "</td><td style='text-align:left;'>"
									+ dataRole[0][i].menuAction
									+ "</td><td style='text-align:left;'>"
									+ dataRole[0][i].parentName
									+ "</td><td>"
									+ (dataRole[0][i].childshort == "0" ? ""
											: dataRole[0][i].childshort)
									+ "</td><td>"
									+ (dataRole[0][i].parentshort == "0" ? ""
											: dataRole[0][i].parentshort)
									+ "</td><td>"
									+ dataRole[0][i].remark
									+ "</td><td>"
									+ (dataRole[0][i].sfyx == "Y" ? "有效" : "失效")
									+ "</td><td>";
									if(ismod==1){
									html += "<a href='javascript:void(0);' onclick='modifyMenu("
									+ dataRole[0][i].menuid
									+ ")'> 修改</a>&nbsp;<a href='javascript:void(0);'  onclick='RoleMenu("
									+ dataRole[0][i].menuid
									+ ",\""
									+ dataRole[0][i].menuname
									+ "\")'>菜单权限</a>";
									}
									if(ismod==1){
									html += "&nbsp;<a href='javascript:void(0);' onclick='delMenu("
									+ dataRole[0][i].menuid + ");'> 删除</a>";
									}
									html += "</td></tr>";
							num++;

						}
						InitPager(dataRole[1].allRows, dataRole[1].curPage);
					} else {//没有数据
						html += "<tr class='table_result' ><td colspan='11'>No Data Show...</td></tr>";
					}
					}}
					$("#table_result:last").append(html);

				},
				error : function() {
					alert("网络连接失败,请稍后重试...");
					return false;
				}

			});
}
function addMenu() {
	$.dialog( {
		title : '新增菜单',
		id : 'menu_new',
		width : 380,
		height : 240,
		cover : true,
		drag : false,
		lock : true,
		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath + "PageControlAction?m=WEB-INF/root/system/MenuManager_info.jsp"
	});
	//load();
}

function modifyMenu(menuid) {
	$.dialog( {
		title : '修改菜单',
		id : 'menu_modify',
		width : 380,
		height : 240,
		cover : true,
		drag : false,
		lock : true,
		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath
				+ "SystemMenuServlet?method=detailMenu&menuid=" + menuid
	});
	//load();
}


function RoleMenu(menuid, menutitle) {
	$.dialog( {
		title : menutitle + '--操作权限',
		id : 'menu_role',
		width : 500,
		height : 400,
		cover : true,
		drag : false,
		lock : true,
		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath
				+ "SystemMenuServlet?method=RoleMenu&menuid=" + menuid
	});
	//load();
}
/**
 * 生成分页信息
 * @param {Object} RecordCount 总记录数
 * @param {Object} PageIndex 当前页码
 */
function InitPager(RecordCount, PageIndex) {
	$("#pageResult").setPager( {
		RecordCount : RecordCount,
		PageIndex : PageIndex,
		buttonClick : PageClick
	});
	//$("#result").html("您点击的是第" + PageIndex + "页");
	//  pageindex=PageIndex;
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

function tips(objValue) {
	$.dialog.tips(objValue);
}

/**
 * 数据加载中
 */
this.load = function() {
	//showMessage("数据加载中...", 0.5);
	top.layer.msg("数据加载中...", {time:500});
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
	//var alert = $.dialog.alert(message);
	//alert.lock();
	top.layer.alert(message, {icon: 6});
}
</script>
	</body>
</html>
