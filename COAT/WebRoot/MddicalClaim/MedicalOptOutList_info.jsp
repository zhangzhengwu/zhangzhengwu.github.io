<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Add Medical Opt Out</title>

	<link rel="stylesheet" href="plug/css/bootstrap.css">
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<%-- <script type="text/javascript" src="<%=basePath%>plug/js/json2.js"></script> --%>
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
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input id="staffcode" name="staffcode"  onkeyup="this.value=this.value.toUpperCase();" type="text" class="inputstyle"/>					
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Name：</td>
				<td class="tagCont">
					<input id="staffname" name="staffname" type="text" class="inputstyle"/> 
				</td>
			</tr>
			<tr>
				<td class="tagName">Remark：</td>
				<td class="tagCont">
					<textarea id="remark" name="remark" class="inputstyle"></textarea>
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
	
	});
	

	function save(){
				
				/* 当staffcode或者staffname有值时，需进一步验证 */
				if($("#staffcode").val() != "" ||$("#staffname").val() != ""){
					if($("#staffcode").val()==""){															 
						W.error("Please Input StaffCode!");	
						$("#staffcode").focus();
						return false;																				 
					}
					if($("#staffcode").val() != "" && $("#staffcode").val().length < 6){															 
						W.error("Please Input Legal StaffCode!");	
						$("#staffcode").focus();
						return false;																				 
					}					
					
					if($("#staffname").val() == ""){															 
						W.error("Please Input StaffName!");	
						$("#staffname").focus();
						return false;																				 
					}					
				}
				
				
																				
					 if(confirm("Are you sure to save?")){// 确认操作？
				    	 $.ajax({
				    		 url:basepath+"MedicalServlet",
				    		 type:"post",			               							 		    		 
				    		 data:{"method":"add","staffcode":$.trim($("#staffcode").val()),"staffname":$.trim($("#staffname").val()),"remark":$.trim($("#remark").val())},
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
