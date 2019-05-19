<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>菜单操作权限</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="plug/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="plug/css/layout.css">
	<link rel="stylesheet" href="plug/css/font-awesome.min.css">
	

 
  
  <style type="text/css">
  table{width: 100%;}
  table tr{border-bottom: 1px solid #ccc;}
  table td{padding: 8px 10px;}
  </style>
  </head>
  
  <body>
  

  
  
<ul id="myTab" class="nav nav-tabs">
   <li class="active">
      <a href="#home" data-toggle="tab">
         用户组权限
      </a>
   </li>
   <li><a href="#ios" data-toggle="tab" >个人权限</a></li>

</ul>


<div id="myTabContent" class="tab-content">
   <div class="tab-pane fade in active" id="home">
     <table style="background:white;" cellspacing="1" cellpadding="1" id="group"> 
     <tr>
     	<td rowspan="2">用户组名称</td>
     	<td>全选</td>
     	<td>新增</td>
      	<td>修改</td>
      	<td>删除</td>
      	<td>查询</td>
      	<td>导出</td>
      	<td>审核</td>
      	<td>其它</td>
     </tr>
     <tr>
      		      	
	 </tr>
   	 		<c:forEach var="roleList" items="${MenuRoleList}" >
				<tr class="operation_ul"  roleid="${roleList[0]}" >
					<td class="menuName"> ${roleList[1]}</td>
					<td class="operation_box"><input type="checkbox" <c:if test="${roleList[2]=='Y' && roleList[3]=='Y' && roleList[4]=='Y' && roleList[5]=='Y' && roleList[6]=='Y' && roleList[7]=='Y' && roleList[8]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.add < 1 && singlemenu.upd < 1 && singlemenu.del < 1 && singlemenu.search < 1 && singlemenu.export < 1 && singlemenu.audit < 1 && singlemenu.other < 1}">disabled="disabled" </c:if>  roleid="${roleList[0]}"  opr="all"/></td>
					<td class="operation_box"><input type="checkbox" <c:if test="${roleList[2]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.add < 1}">disabled="disabled" </c:if>  roleid="${roleList[0]}"  opr="add"/></td>
					<td class="operation_box"><input type="checkbox" <c:if test="${roleList[3]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.upd < 1}">disabled="disabled" </c:if>  roleid="${roleList[0]}"  opr="upd"/></td>
					<td class="operation_box"><input type="checkbox" <c:if test="${roleList[4]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.del < 1}">disabled="disabled" </c:if>  roleid="${roleList[0]}"  opr="del"/></td>
					<td class="operation_box"><input type="checkbox" <c:if test="${roleList[5]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.search < 1}">disabled="disabled" </c:if>  roleid="${roleList[0]}"  opr="search"/></td>
					<td class="operation_box"><input type="checkbox" <c:if test="${roleList[6]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.export < 1}">disabled="disabled" </c:if>  roleid="${roleList[0]}"  opr="export"/></td>
					<td class="operation_box"><input type="checkbox" <c:if test="${roleList[7]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.audit < 1}">disabled="disabled" </c:if>  roleid="${roleList[0]}"  opr="audit"/></td>
					<td class="operation_box"><input type="checkbox" <c:if test="${roleList[8]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.other < 1}">disabled="disabled" </c:if>  roleid="${roleList[0]}"  opr="other"/></td>
				</tr>
            
			</c:forEach>	
		<tr>
			<td><a href='javascript:void(0);'  onclick="add('group')" >增加</a></td>
		</tr>	
			<c:if test="${fn:length(MenuRoleList)==0}"><tr><td colspan="8" align='center'>该菜单操作权限暂未分配至群组</td></tr></c:if>
     </table>
   </div> 
   <div class="tab-pane fade" id="ios">
      <table style="width: 99%;border:0px solid gray;background:white;" cellspacing="1" cellpadding="1" id="person">
     <tr>
	    <td>用户登录名称</td>
	    <td>全部</td>
	    <td>新增</td>
     	<td>修改</td>
     	<td>删除</td>
     	<td>查询</td>
     	<td>导出</td>
     	<td>审核</td>
     	<td>其它</td>
     </tr>
     
      <c:forEach items="${UserRoleList}" var="userRoleList" >
			<tr class="operation_ul"  roleid="${userRoleList[9]}">
				<td class="menuName"> ${userRoleList[1]}</td>
				<td class="operation_box"><input type="checkbox" <c:if test="${userRoleList[2]=='Y' && userRoleList[3]=='Y' && userRoleList[4]=='Y' && userRoleList[5]=='Y' && userRoleList[6]=='Y' && userRoleList[7]=='Y' && userRoleList[8]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.add < 1 && singlemenu.upd < 1 && singlemenu.del < 1 && singlemenu.search < 1 && singlemenu.export < 1 && singlemenu.audit < 1 && singlemenu.other < 1}">disabled="disabled" </c:if>  roleid="${userRoleList[9]}" opr="all"/></td>
				<td class="operation_box"><input type="checkbox" <c:if test="${userRoleList[2]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.add < 1}">disabled="disabled" </c:if>  roleid="${userRoleList[9]}" opr="add"/></td>
				<td class="operation_box"><input type="checkbox" <c:if test="${userRoleList[3]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.upd < 1}">disabled="disabled" </c:if>  roleid="${userRoleList[9]}" opr="upd"/></td>
				<td class="operation_box"><input type="checkbox" <c:if test="${userRoleList[4]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.del < 1}">disabled="disabled" </c:if>  roleid="${userRoleList[9]}" opr="del"/></td>
				<td class="operation_box"><input type="checkbox" <c:if test="${userRoleList[5]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.search < 1}">disabled="disabled" </c:if>  roleid="${userRoleList[9]}" opr="search"/></td>
				<td class="operation_box"><input type="checkbox" <c:if test="${userRoleList[6]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.export < 1}">disabled="disabled" </c:if>  roleid="${userRoleList[9]}" opr="export"/></td>
				<td class="operation_box"><input type="checkbox" <c:if test="${userRoleList[7]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.audit < 1}">disabled="disabled" </c:if>  roleid="${userRoleList[9]}" opr="audit"/></td>
				<td class="operation_box"><input type="checkbox" <c:if test="${userRoleList[8]=='Y'}">checked="checked"</c:if> <c:if test="${singlemenu.other < 1}">disabled="disabled" </c:if>  roleid="${userRoleList[9]}" opr="other"/></td>
			</tr>
      	
      </c:forEach>
      <tr>
		<td><a href='javascript:void(0);'  onclick="add('person')">增加</a></td>
	  </tr>	
      <c:if test="${fn:length(UserRoleList)==0}"><tr><td colspan="8" align='center'>该菜单操作权限暂未分配至个人</td></tr></c:if>
      </table>
   </div>
  
</div>

	
<script type="text/javascript" src="plug/js/jquery.min.js"></script>
 <script type="text/javascript" src="plug/js/bootstrap.js"></script>

<script type="text/javascript">
    var api = frameElement.api, W = api.opener;
    	api.button({
		    id:'saveRole',
		    name:'保存',
		    callback:saveMenu
		},{name:"返回"});
	
		
		$("tr.operation_ul input:checkbox").click(function(){
//			if(this.checked){//判断是否选中
			if($(this).is(":checked")){//判断是否选中
				if($(this).attr("opr")=="all"){//判断是否全选
					$(this).parent().parent().find("input:checkbox").not(":disabled").prop("checked",true);
				}else{
					$(this).parent().parent().find("input[opr='search']").prop("checked",true);
				}
			}else{//没有选中
				if($(this).attr("opr")=="all"){//判断是否全选
					$(this).parent().parent().find("input:checkbox").not(":disabled").prop("checked",false);
				}else{//没有全选
					if($(this).attr("opr")=="search"){//判断当前按钮是否为查询按钮
						if($(this).parent().parent().find("input[opr!='search']:checkbox:checked").length>0){
							return false;
						}
					}
				}
			}
		
		});
	
	
		//组装参数
	function assembleParam(id){
		var param=[];
		var roleid="";
		var add="";
		var del="";
		var search="";
		var upd="";
		var report="";
		var audit="";
		var other="";
		$("#"+id+" tr.operation_ul").each(function(){
			roleid=parseFloat($(this).attr("roleid"));
			add=$(this).find("input[opr='add']").prop("checked")?1:0;
			del=$(this).find("input[opr='del']").prop("checked")?1:0;
			search=$(this).find("input[opr='search']").prop("checked")?1:0;
			upd=$(this).find("input[opr='upd']").prop("checked")?1:0;
			report=$(this).find("input[opr='export']").prop("checked")?1:0;
			audit=$(this).find("input[opr='audit']").prop("checked")?1:0;
			other=$(this).find("input[opr='other']").prop("checked")?1:0;
			var data = {
					'roleid' : roleid,
					'add': add,
					'del' : del,
					'search' : search,
					'upd' : upd,
					'report' : report,
					'audit' : audit,
					'other' : other
				};
			 param.push(data);
		});
		return JSON.stringify(param);
	}	
	
	
		
	function saveMenu(){
	  	var flag = true;
		if (flag) {
			W.$.dialog.confirm(
					"&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
							+ "确认保存?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", function() {
						$.ajax( {
							url : "SystemMenuServlet",
							type : "post",
							data :{"menuid":"${menuid}","method":"saveRoleMenu","groupParamList":assembleParam("group"),"personParamList":assembleParam("person")},
							beforeSend: function(){
								parent.showLoad();
							},
							complete: function(){
								parent.closeLoad();
							},
							success : function(date) {
								var result=$.parseJSON(date);
								W.alertMessage(result.state);
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
    
function add(str){
var titleShow = null;
if(str=="group"){
	titleShow="增加操作组";
}else{
	titleShow="增加操作人";
}
    W.$.dialog( {
		title : titleShow,
		id : 'grouporperson_new',
		width : 400,
		height : 200,
		cover : true,
		drag : false,
		lock : true,
		focus : false,
		max : false,
		min : false,
		resize : false,
		parent:api,
		content : "url:<%=basePath%>SystemMenuServlet?method=addParam&menuid=${menuid}&str="+str
	});
}
	
</script>
  </body>
</html>
