<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增菜单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="plug/css/site.css" />
	<link rel="stylesheet" href="plug/css/font-awesome.min.css" />
	<link rel="stylesheet" href="plug/css/layout.css" />
  	<style>
  		table.col-2-table{}
  		table.col-2-table .tagName{width: 40%!important;}
  		table.col-2-table .tagCont{width: 59%!important;}
  		table.col-2-table input[type="text"], table.col-2-table select{
  			width: 170!important;
  		}
  	</style>
  </head>
  
  <body>
   <div class="form-table">
	  	<form id="save_form">
			<table cellpadding="0" cellspacing="0"  class="col-2-table">
				<tr>
					<td class="tagName"><fmt:message key="menu.menuname" />:</td>
					<td class="tagCont">
						<input type="text" id="menuName" name="menuname" tip="Menu Name" must="true"/>
					</td>
				</tr>
				
				<tr>
					<td class="tagName"><fmt:message key="menu.menuurl" />:</td>
					<td class="tagCont">
						<input type="text" id="menuURL" name="menuAction" tip="Menu Url" />
					</td>
				</tr>
				
				<tr>
					<td class="tagName"><fmt:message key="menu.parentmenuname" />:</td>
					<td class="tagCont">
						<select id="parentMenu" name="parentMenu" tip="Parent Menu" must="true">
							 
						</select>
					</td>
				</tr>
				<tr>
					<td class="tagName"><fmt:message key="menu.menusort" />:</td>
					<td class="tagCont">
						<input type="text" id="short" name="sort" maxlength="3" onkeydown="vailNumber(this);" onblur="vailNumber(this);" tip="Menu Sort" must="true"/>
					</td>
				</tr>
				<tr>
					<td class="tagName"><fmt:message key="public.remark" />:</td>
					<td class="tagCont">
						<textarea id="remark" name="remark"  style="height: 60px;width: 170px;"></textarea>
					</td>
				</tr>
		  	</table>
	  	<input type="hidden" name="method" value="saveMenu"/>
	  	</form>
  	</div>
    
    
    
    
    
    
    <script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript">
    var api = frameElement.api, W = api.opener;
    	api.button({
		    id:'valueOk',
		    name:'保存',
		    
		    callback:saveMenu
		},{name:"返回"});
    
    	function saveMenu(){
    		var flag=true;
    		$("#save_form [must='true']").each(function(){
    			if($(this).val()==""){
    				W.showErroMessage("\""+$(this).attr("tip")+"\""+" Can't be empty !",3);
    				$(this).focus();
    				flag=false;
    				return false;
    			}else{
    				flag=true;
    			}
    		});
    		
    		if(flag){
    		W.$.dialog.confirm("&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    		"确认保存?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
    		                     function(){
    								$.ajax({
    									url:"SystemMenuServlet",
							    		type:"post",
							    		data:$("#save_form").serialize(),
							    		success:function(date){
							    			if(date=="success"){
								    			W.$("#search").click();
								    			api.close();
							    			}
    										W.alertMessage(date);
							    			
							    			
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
    	
    	$.ajax({
    		url:"SystemMenuServlet",
    		type:"post",
    		data:{"method":"dropdownMenu"},
    		success:function(date){
    			var dataRole=eval(date);
    			var html="";
    			if(date!=null && date!="[]"){
    				html+="<option value='0'>顶级菜单</option>";
	    			for(var i=0;i<dataRole.length;i++){
	    				html+="<option value='"+dataRole[i].split("~,~")[0]+"'>&nbsp;&nbsp;&nbsp;&nbsp;--"+dataRole[i].split("~,~")[1]+"</option>";
	    			}
	    			W.showMessage("数据加载完毕...");
    			}else{
    				W.showErroMessage("数据加载异常...");
    			}
    			$("#parentMenu").append(html);
    			
    		},error:function(){
    			//alert("网络连接失败,请稍后重试...");
    			W.showErroMessage("数据加载异常...");
				return false;
    		} 
    	});
    	
    	
    	function vailNumber(obj){
    		if(obj.value!="" && !obj.value.match(/^[0-9]{0,3}$/)){
    			W.showErroMessage("只能输入1-9的数字");
    			obj.value="";
    			obj.select();
    			return false;
    		}
    	}
    </script>
    
    
    
    
    
    
    
    
    
  </body>
</html>
