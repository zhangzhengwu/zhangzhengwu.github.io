<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Company Asset Update</title>

	<link rel="stylesheet" href="plug/css/bootstrap.css">
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<style type="text/css">
	.red{
		color: red;
	}
	.info-form table td{
		padding: 5px 8px;
	}
	.info-form table td:nth-child(1){
		width: 10%;
	}
	.info-form table td:nth-child(2){
		width: 60%;
	}
	.info-form table td:nth-child(3){
		width: 15%;
	}
	.info-form table td:nth-child(4){
		width: 15%;
	}
	.info-form table td.tagName{
		min-width: 175px!important;
	}
	.info-form table td input[type="text"],
	.info-form table td select{
		height: 26px;
		line-height: 26px;
	}
	.info-form table td.tagCont textarea{
		width: 90%;
	}
    </style>
	
	
  </head>
  
<body style="overflow-y:hidden;overflow-y:auto;padding-left:2px; ">
<div class="e-container">
	<div class="info-form">
		<table>
			<tr>
				<td class="tagName">编号：</td>
				<td class="tagCont">
					<input id="itemcode" name="itemcode" readonly="readonly" type="text" class="inputstyle" value="${companyAssetItem.itemcode }"/>
					<input id="itemId" name="itemId" type="hidden" class="inputstyle" value="${companyAssetItem.itemId }"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">名称：</td>
				<td class="tagCont">
					<input id="itemname" name="itemname" type="text" class="inputstyle" value="${companyAssetItem.itemname }" />
				</td>
			</tr>
			<tr>
				<td class="tagName">库存数量：</td>
				<td class="tagCont">
					<input id="itemnum" name="itemnum" type="text" class="inputstyle" value="${companyAssetItem.itemnum }" />
				</td>
			</tr>
			<tr>
				<td class="tagName">剩余数量：</td>
				<td class="tagCont">
					<input id="remainnum" name="remainnum" type="text" class="inputstyle" value="${companyAssetItem.remainnum }" />
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	
	api.button({
		name : '返回'
	},{
		name : '修改',
		callback : update
	});
	var basepath = "<%=basePath%>";

	function update(){

		if($("#itemname").val() == ""){															 
			W.error("Please Input itemname!");	
			$("#itemname").focus();
			return false;																				 
		}
		if($("#itemnum").val() == ""){															 
			W.error("Please Input itemnum!");	
			$("#itemnum").focus();
			return false;																				 
		}else{
			var reg = /\d+/;
			if(!reg.test($("#itemnum").val())){
				W.error("Please Input num!");	
				$("#itemnum").focus();
				return false;	
			}
		}	
		
		if($("#remainnum").val() == ""){															 
			W.error("Please Input remainnum!");	
			$("#remainnum").focus();
			return false;																				 
		}else{
			var reg = /\d+/;
			if(!reg.test($("#remainnum").val())){
				W.error("Please Input num!");	
				$("#remainnum").focus();
				return false;	
			}
		}			
		
		 if(confirm("Are you sure to update?")){// 确认操作？
		   	 $.ajax({
		   		 url:basepath+"CompanyAssetItemServlet",
		   		 type:"post",	
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },				    		 		               							 		    		 
		   		 data:{"method":"updateCompanyAsset","itemId":$.trim($("#itemId").val()),"itemname":$.trim($("#itemname").val()),"itemnum":$.trim($("#itemnum").val()),"remainnum":$.trim($("#remainnum").val())},
		   		 dataType:'json',
		   		 success:function(result){
	   				if("success" == result.state){
	   					W.$("#search").click();
	   					api.close();
	   					W.success(result.msg);
	   				}else{
	   					W.error(result.msg);
	   				}
		   		 },error:function(result){
		   			 W.error("Network connection is failed, please try later...");
	 			   	 return false;
		   		 }
		   	 });
	  	  }
	  	  return false;
	 }

</script>
</html>
