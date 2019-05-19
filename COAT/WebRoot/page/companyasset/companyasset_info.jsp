<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Add Company Asset</title>

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
					<input id="itemcode" name="itemcode" onkeyup="this.value=this.value.toUpperCase();" type="text" class="inputstyle"/> <span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">名称：</td>
				<td class="tagCont">
					<input id="itemname" name="itemname" type="text" class="inputstyle"/><span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">库存：</td>
				<td class="tagCont">
					<input id="itemnum" name="itemnum" type="text" class="inputstyle"/><span class="red">*</span>
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
		name : '保存',
		callback : save
	});
	var basepath = "<%=basePath%>";

	$(function(){
		/* 输入编号验证当前编号是否已存在*/
		$("#itemcode").blur(function(){
			if($("#itemcode").val() == ""){															 
				W.error("Please Input itemcode!");	
				$("#itemcode").focus();
				return false;																				 
			}			
			$.ajax({
				url:basepath+"CompanyAssetItemServlet",
				type:"post",
				data:{"method":"selectItemNo","itemcode":$.trim($("#itemcode").val()) },
				dataType:'json',
				success:function(result){
					console.log(result);
					if("success"==result.state){
					
					}else{
						W.error(result.msg);
						$("#itemcode").val("");
						$("#itemcode").focus();
					}
				},error:function(result){
	    			 W.error("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
			});
		});
		
	});
	

	function save(){
		if($("#itemcode").val() == ""){															 
			W.error("Please Input itemcode!");	
			$("#itemcode").focus();
			return false;																				 
		}	
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
		 if(confirm("Are you sure to save?")){// 确认操作？
	    	 $.ajax({
	    		 url:basepath+"CompanyAssetItemServlet",
	    		 type:"post",	
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },				    		 		               							 		    		 
	    		 data:{"method":"add","itemcode":$.trim($("#itemcode").val()),"itemname":$.trim($("#itemname").val()),"itemnum":$.trim($("#itemnum").val())},
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
