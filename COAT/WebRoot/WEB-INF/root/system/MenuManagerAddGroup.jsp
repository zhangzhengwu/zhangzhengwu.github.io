<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="plug/css/site.css" />
	<link rel="stylesheet" href="plug/css/font-awesome.min.css" />
	<link rel="stylesheet" href="plug/css/layout.css" />
 <style type="text/css">
 div.cont-info,.info-search{width:100%;height:100%}
 .info-search table{width:100%;height:95%;}
 .info-search table .tagName{text-align:right;}
 div.cont-info{padding-left: 0px;}
 table{ width: 100%;}
 table td{height: 24px; font-size: 13px;}
 table select{width: 130px; height: 24px; padding: 2px 6px; margin-bottom: 0px; }
 </style>
  </head>
  
  <body>
  <div class="cont-info">
	   <div class="info-type">
		  	<form id="">
				<table cellpadding="0" cellspacing="0"  class="table-col-2">

		     <tr>
			    <td>用户组名称</td>
			   	    <td>新增</td>
			     	<td>修改</td>
			     	<td>删除</td>
			     	<td>查询</td>
			     	<td>导出</td>
			     	<td>审核</td>
			     	<td>其它</td>
		     </tr>
							
			<tr class="operation_ul"  >
			     <td>
					 	<select id="groupid">
						  <c:forEach var="groupList" items="${groupList}" >
						 		<option value="${groupList[0]}" >${groupList[1]}</option>
				          </c:forEach>
					 	</select>
					 	
				</td>
				<td class="operation_box"><input type="checkbox" id="add" value="N"   <c:if test="${singlemenu.add < 1}">disabled="disabled" </c:if> /></td>
				<td class="operation_box"><input type="checkbox" id="upd" value="N"  <c:if test="${singlemenu.upd < 1}">disabled="disabled"</c:if> /></td>
				<td class="operation_box"><input type="checkbox" id="del" value="N"  <c:if test="${singlemenu.del < 1}">disabled="disabled" </c:if> /></td>
				<td class="operation_box"><input type="checkbox" id="search" value="N"  <c:if test="${singlemenu.search < 1}">disabled="disabled" </c:if> /></td>
				<td class="operation_box"><input type="checkbox" id="export" value="N"  <c:if test="${singlemenu.export < 1}">disabled="disabled" </c:if> /></td>
				<td class="operation_box"><input type="checkbox" id="audit" value="N"  <c:if test="${singlemenu.audit < 1}">disabled="disabled" </c:if> /></td>
				<td class="operation_box"><input type="checkbox" id="other" value="N"  <c:if test="${singlemenu.other < 1}">disabled="disabled" </c:if> /></td>

			</tr>				
					
			  	</table>
			  	<input type="hidden" id="menuid" value="${menuid}"/>
		  	</form>
	  	</div>
  
  </div>
    <script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript">   
    var api = frameElement.api, W = api.opener;
    	api.button({
		    id:'valueOk',
		    name:'保存',
		    callback:saveGroupMenu
		},{name:"返回"});
    
    	function saveGroupMenu(){
    	    var flag=true;
			     		   		
    		if(flag){
    		W.$.dialog.confirm("&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    		"确认保存?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
    		                     function(){
    								$.ajax({
    									url:"SystemMenuServlet",
							    		type:"post",
							    		data:{"menuid":"${menuid}",'groupid':$("#groupid").val(),"method":"saveGroupMenu",'add':$("#add").val(),'upd':$("#upd").val(),'del':$("#del").val(),'search':$("#search").val(),'export':$("#export").val(),'audit':$("#audit").val(),'other':$("#other").val()},
							    		success:function(date){
							    			if(date=="notEffective"){
							    			  W.alertMessage("添加组为无效组！");
							    			}
							    			if(date=="isexist"){
							    			  W.alertMessage("添加组已存在！");
							    			}
							    			if(date=="success"){
							    			  W.alertMessage(date);
	 		 								  W.$.dialog.list['menu_role'].content.location.reload();
							    			  api.close();
							    			}
							    			
							    		},error:function(){
							    			W.showErroMessage("网络连接失败,请稍后重试...");
											return false;
							    		}
    								});
    							},function(){
    								W.tips("执行取消操作");
    							},this);
    		
    		}
    		return false;
    	}
    	   
    	   $(function(){
	    	   	$("tr.operation_ul input:checkbox").not(":disabled").click(function(){
    	   
	    	   		if($(this).prop("checked")){
	    	   			$("#search").prop("checked",true).val("Y");
	    	   			this.value="Y";
	    	   		}else{
	    	   			if(this.id=="search"){
		    	   			if($("tr.operation_ul").find("input:checkbox:checked").not(":disabled").not("id='search'").length>0){
		    	   				$(this).prop("checked",true).val("Y");
		    	   			} 
	    	   			}else{
	    	   				this.value="N";
	    	   			}
	    	   			
	    	   		}
	    	   	});
    	   
    	   });
        </script>
    
    
    
    
    

    
    
    
    
    
  </body>
</html>
