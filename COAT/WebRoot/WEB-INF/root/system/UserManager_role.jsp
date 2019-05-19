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
table tr.title td{text-align: center;}
table td.operation_box{text-align: center;}
#save_table_title{
	background: #428bca;
	position: fixed;
	top: 0;
	font-weight: bold;
	border-collapse: collapse;
	
}
#save_table_title td{
	border: 1px solid #428bca;
	color: #fff;
}
input[type="checkbox"][disabled="disabled"]{cursor: no-drop;}
</style>

	</head>
	<body>
	
	
	
	
	
	

	<form id="save_form">
		<table id="save_table_title">
			<tr class="title">
				<td class="width_30">菜单名</td>
				<td class="width_8">全选</td>
				<td class="width_8">添加</td>
				<td class="width_8">删除</td>
				<td class="width_8">查询</td>
				<td class="width_8">修改</td>
				<td class="width_8">导出</td>
				<td class="width_8">审核</td>
				<td class="width_8">其他</td>
			</tr>
			
		</table>
		<table id="save_table">
			<tr class="title">
				<td class="width_30">菜单名</td>
				<td class="width_8">全选</td>
				<td class="width_8">添加</td>
				<td class="width_8">删除</td>
				<td class="width_8">查询</td>
				<td class="width_8">修改</td>
				<td class="width_8">导出</td>
				<td class="width_8">审核</td>
				<td class="width_8">其他</td>
			</tr>


			<c:choose>
				<c:when test="${fn:length(BaseMenuList)!=0}">
					<c:forEach var="item" items="${BaseMenuList}" varStatus="itemStu_index">
					<c:set value="0" var="parent_merge" />
						<c:forEach var="m_parent" items="${modifyGMenuList}" varStatus="itemStu_index">
					
							<c:if test="${item.menuid==m_parent.menuid && parent_merge!=1}">
								<c:set value="1" var="parent_merge" />
								<tr class="operation_ul" type="modify" name="${m_parent.usermenuid }"  mid="${item.menuid}" pid="0">
									<td class="menuName"> ${item.menuname}</td>
									<td class="operation_box"><input type="checkbox"  <c:if test="${item.sadd+item.del+item.search+item.upd+item.export+item.audit+item.other==0}">disabled="disabled"</c:if>  mid="${item.menuid}" pid="0" opr="all"/></td>
									<td class="operation_box"><input type="checkbox" <c:if test="${m_parent.add=='Y' }">checked="checked"</c:if>  <c:if test="${item.sadd==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="add"/></td>
									<td class="operation_box"><input type="checkbox" <c:if test="${m_parent.delete=='Y' }">checked="checked"</c:if> <c:if test="${item.del==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="del"/></td>
									<td class="operation_box"><input type="checkbox" <c:if test="${m_parent.search=='Y' }">checked="checked"</c:if> <c:if test="${item.search==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="search"/></td>
									<td class="operation_box"><input type="checkbox" <c:if test="${m_parent.upd=='Y' }">checked="checked"</c:if> <c:if test="${item.upd==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="upd"/></td>
									<td class="operation_box"><input type="checkbox" <c:if test="${m_parent.export=='Y' }">checked="checked"</c:if> <c:if test="${item.export==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="export"/></td>
									<td class="operation_box"><input type="checkbox" <c:if test="${m_parent.audit=='Y' }">checked="checked"</c:if> <c:if test="${item.audit==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="audit"/></td>
									<td class="operation_box"><input type="checkbox" <c:if test="${m_parent.other=='Y' }">checked="checked"</c:if> <c:if test="${item.other==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="other"/></td>
								</tr>
							</c:if>
						</c:forEach>	
					 
						<c:if test="${parent_merge!=1}">
							<tr class="operation_ul" type="new"  mid="${item.menuid}" pid="0">
								<td class="menuName"> ${item.menuname}</td>
								<td class="operation_box"><input type="checkbox" <c:if test="${item.sadd+item.del+item.search+item.upd+item.export+item.audit+item.other==0}">disabled="disabled"</c:if>  mid="${item.menuid}" pid="0" opr="all"/></td>
								<td class="operation_box"><input type="checkbox" <c:if test="${item.sadd==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="add"/></td>
								<td class="operation_box"><input type="checkbox" <c:if test="${item.del==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="del"/></td>
								<td class="operation_box"><input type="checkbox" <c:if test="${item.search==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="search"/></td>
								<td class="operation_box"><input type="checkbox" <c:if test="${item.upd==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="upd"/></td>
								<td class="operation_box"><input type="checkbox" <c:if test="${item.export==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="export"/></td>
								<td class="operation_box"><input type="checkbox" <c:if test="${item.audit==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="audit"/></td>
								<td class="operation_box"><input type="checkbox" <c:if test="${item.other==0 }">disabled="disabled" </c:if> mid="${item.menuid}" pid="0" opr="other"/></td>
							</tr>
						</c:if>
						
						
						
									
						
						<c:if test="${fn:length(item.child)!=0}">
							<c:forEach var="son" items="${item.child}">
								<c:set value="0" var="son_merge" />
								<c:forEach var="m_son" items="${modifyGMenuList}" varStatus="itemStu_index">
									<c:if test="${son.menuid==m_son.menuid &&son_merge!=1}">
										<c:set value="1" var="son_merge" />
											<tr class="operation_ul" type="modify" name="${m_son.usermenuid }"  mid="${son.menuid}" pid="${item.menuid }">
												<td class="menuName textIndent"> ${son.menuname}</td>
												<td class="operation_box"><input type="checkbox" <c:if test="${son.sadd+son.del+son.search+son.upd+son.export+son.audit+son.other==0}">disabled="disabled"</c:if> mid="${son.menuid}" pid="${item.menuid}" opr="all"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${m_son.add=='Y' }">checked="checked"</c:if>  <c:if test="${son.sadd==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="add"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${m_son.delete=='Y' }">checked="checked"</c:if>  <c:if test="${son.del==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="del"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${m_son.search=='Y' }">checked="checked"</c:if>  <c:if test="${son.search==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="search"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${m_son.upd=='Y' }">checked="checked"</c:if>  <c:if test="${son.upd==0 }">disabled="disable" </c:if>  mid="${son.menuid}" pid="${item.menuid}" opr="upd"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${m_son.export=='Y' }">checked="checked"</c:if>  <c:if test="${son.export==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="export"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${m_son.audit=='Y' }">checked="checked"</c:if>  <c:if test="${son.audit==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="audit"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${m_son.other=='Y' }">checked="checked"</c:if>  <c:if test="${son.other==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="other"/></td>
											</tr>
									</c:if>
							
							</c:forEach>
							
							<c:if test="${son_merge!=1}">
								<tr class="operation_ul" type="new"  mid="${son.menuid}"  pid="${item.menuid }">
												<td class="menuName textIndent"> ${son.menuname}</td>
												<td class="operation_box"><input type="checkbox" <c:if test="${son.sadd+son.del+son.search+son.upd+son.export+son.audit+son.other==0}">disabled="disabled"</c:if> mid="${son.menuid}" pid="${item.menuid}" opr="all"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${son.sadd==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="add"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${son.del==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="del"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${son.search==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="search"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${son.upd==0 }">disabled="disable" </c:if>  mid="${son.menuid}" pid="${item.menuid}" opr="upd"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${son.export==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="export"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${son.audit==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="audit"/></td>
												<td class="operation_box"><input type="checkbox" <c:if test="${son.other==0 }">disabled="disable" </c:if> mid="${son.menuid}" pid="${item.menuid}" opr="other"/></td>
											</tr>
							
							</c:if>
						</c:forEach>
						
						
						
						</c:if>
						
						
						
	
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td  colspan="9">
							No menu for show!
						</td>
					</tr>
				</c:otherwise>

			</c:choose>







 

		</table>

	</form>
	
	
	
	
	
	</body>
	<script type="text/javascript">
	var api = frameElement.api, W = api.opener;

	$(function(){
		api.button( {
			name : "返回"
		}, {
			id : 'valueOk',
			name : '保存',

			callback : saveMenus
		}, {
			id : 'ALlSelect',
			name : '全选',
			callback : all_select
		});
		
		
		
		
		$("#save_table tr.operation_ul input:checkbox").click(function(){
			if($(this).attr("checked")=="checked"){//判断是否选中
				if($(this).attr("opr")=="all"){//判断是否全选
					$(this).parent().parent().find("input:checkbox").not(":disabled").attr("checked","checked");
				}else{
					$(this).parent().parent().find("input[opr='search']").attr("checked","checked");
				}
				var mid=$(this).parent().parent().attr("pid");
				$(".operation_ul[mid='"+mid+"']").find("input[opr='search']").attr("checked","checked");
			}else{//没有选中
				if($(this).attr("opr")=="all"){//判断是否全选
					$(this).parent().parent().find("input:checkbox").not(":disabled").removeAttr("checked");
				}else{//没有全选
					if($(this).attr("opr")=="search"){//判断当前按钮是否为查询按钮
						if($(this).parent().parent().find("input[opr!='search']:checkbox:checked").length>0){
							return false;
						}
					}
				}
				
				var mid=$(this).parent().parent().attr("pid");
				if(mid==0){
					mid=$(this).parent().parent().attr("mid");
				}
				if($(".operation_ul[pid='"+mid+"']").find("input:checkbox:checked").length==0){
					$(".operation_ul[mid='"+mid+"']").find("input[opr='search']").removeAttr("checked");
				}else{
					if($(this).parent().parent().attr("pid")==0){
						return false;
					}
				}
			
			}
		
		});
		
		
		
		$('.operation_ul').hover(function(){
			var mid = $(this).attr("pid");
			var parMenu = $(".operation_ul[mid='"+mid+"']");
			$(".operation_ul[pid=0]").find("td").css('font-weight','lighter');
			$(parMenu).find("td").css({
				'font-weight' : 'bold'
			});
			$(this).css({
				'background' : '#ddd'
			});
		},function(){
			$(this).css({
				'background' : 'none'
			});
		});
		
	});
	
	
	
	
	function all_select() {
		if($("input[type='checkbox']").not(":disabled").not(":checked").length>0){
			$("input[type='checkbox']").not(":disabled").attr("checked","checked");
		}else{
			$("input[type='checkbox']").not(":disabled").removeAttr("checked");
		}
		
		return false;
		
	}
	
	//组装参数
	function assembleParam(){
		var param=[];
		var usermenuid="";
		var menuid="";
		var add="";
		var del="";
		var search="";
		var upd="";
		var report="";
		var audit="";
		var other="";
		$("#save_table tr.operation_ul").each(function(){
			menuid=parseFloat($(this).attr("mid"));
			usermenuid=parseFloat($(this).attr("name")==undefined?0:$(this).attr("name"));
			var temp=0;
			
			if($(this).find("input[opr='add']").attr("disabled")=="disabled"){
				add=-1;
				temp+=1;
			}else{
				add=$(this).find("input[opr='add']").attr("checked")=="checked"?1:0;
			}
			if($(this).find("input[opr='del']").attr("disabled")=="disabled"){
				del=-1;
				temp+=1;
			}else{
				del=$(this).find("input[opr='del']").attr("checked")=="checked"?1:0;
			}
			if($(this).find("input[opr='search']").attr("disabled")=="disabled"){
				search=-1;
				temp+=1;
			}else{
				search=$(this).find("input[opr='search']").attr("checked")=="checked"?1:0;
			}
			if($(this).find("input[opr='upd']").attr("disabled")=="disabled"){
				upd=-1;
				temp+=1;
			}else{
				upd=$(this).find("input[opr='upd']").attr("checked")=="checked"?1:0;
			}
			if($(this).find("input[opr='export']").attr("disabled")=="disabled"){
				report=-1;
			}else{
				report=$(this).find("input[opr='export']").attr("checked")=="checked"?1:0;
			}
			if($(this).find("input[opr='audit']").attr("disabled")=="disabled"){
				audit=-1;
				temp+=1;
			}else{
				audit=$(this).find("input[opr='audit']").attr("checked")=="checked"?1:0;
			}
			if($(this).find("input[opr='other']").attr("disabled")=="disabled"){
				other=-1;
				temp+=1;
			}else{
				other=$(this).find("input[opr='other']").attr("checked")=="checked"?1:0;
			}
			
			var data = {
					'usermenuid' : usermenuid,
					'menuid':menuid,
					'add': add,
					'del' : del,
					'sel' : search,
					'upd' : upd,
					'exp' : report,
					'audit' : audit,
					'other' : other
				};
				if(add+del+search+upd+report+audit+other+temp>0){
					param.push(data);
				}else{
					if(usermenuid!=0){
						param.push(data);
					}
				}
		});
		return JSON.stringify(param);
	}		


	
	
	
	
	
	


function saveMenus() {
	var flag = true;
	if (flag) {
		W.$.dialog.confirm(
				"&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "确认保存?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", function() {
							
					$.ajax( {
						url : "SystemUserServlet",
						type : "post",
						data :{"userid":"${userid}","method":"SaveRoleUser","paramList":assembleParam()},
						beforeSend: function(){
							parent.showLoad();
						},
						complete: function(){
							parent.closeLoad();
						},
						success : function(date) {
							var result=$.parseJSON(date);
							W.alertMessage(result.msg);
				   			if(result.state=="success"){
								W.$("#search").click();
								api.close();
				   			}
						},
						error : function() {
							$("#paramList").val("");
							W.showErroMessage("网络连接失败,请稍后重试...");
							return false;
						}
						});

				}, function() {
					//$("#paramList").val("");
					//W.alertMessage("执行取消操作");
				//return false;
			}, this);

	}
	return false;
}


</script>
</html>
