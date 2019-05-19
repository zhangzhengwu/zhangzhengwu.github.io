<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	 <head>
	   <base href="<%=basePath%>">
	   	<link rel="stylesheet" href="css/layout.css">
	 	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
	 	<script type="text/javascript">
		var basepath = "<%=basePath%>";
	 	
	    function  createphone(){
		    if(confirm('Are You Sure ? ')){  
		    	$.ajax({
		  			url:basepath+"IvrServlet",
		  			type:"post",
		  			data:{"method":"synphone"},
		  			beforeSend: function(){
					parent.showLoad();
					},
					complete: function(){
					parent.closeLoad();
					},
					success:function(date){
			  			var dataRole = $.parseJSON(date);
			  			alert(dataRole.msg);
		  			},
		  			error:function(){
		  			 	alert("Network connection is failed, please try later...");
		  				return false;
		  			}
		  			
		  		});
		  		return false;
		    }  
	    }	
		  	
	 	</script>
	</head>
	<body>
		<div class="cont-info">
		  <div class="info-search">
			<table>
				<tr>
					<td class="tagCont">				
						<c:if test="${roleObj.audit==1}">
							<a class="btn" id="createphone" onclick="createphone()">
								<i class="icon-refresh"></i>
								Create Daily Opt-out List
							</a>&nbsp;&nbsp;&nbsp;&nbsp;<span class='blue'>备注：点击按钮后，生成数据表大约需要10分钟左右，请等待...</span>
						</c:if>
					</td>
				</tr>
		     </table>
		  </div>
		</div>	
	</body>
</html>
