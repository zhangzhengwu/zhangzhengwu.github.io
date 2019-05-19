<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>清空座位</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="plug/css/site.css" />
		<link rel="stylesheet" href="plug/css/font-awesome.min.css" />
		<link rel="stylesheet" href="plug/css/layout.css" />
		<style type="text/css">
			.tagName{
				text-align: right;
				padding-right: 10px!important;
			}
</style>
	</head>

	<body>
		<div class="form-table">
				<form>
					<table cellpadding="0" cellspacing="0" class="table-col-2">
						<tr>
							<td class="tagName">Seat No:</td>
							<td class="tagCont">
								<input type="text" id="seatnum" name="seatnum" value="${seatno}" disabled="disabled"/>
							</td>
						</tr>

						<tr>
							<td class="tagName">Reason :</td>
							<td class="tagCont">
								<select id="reason" name="reason">
										<option value="">请选择...</option>
										<option value="leave">离职</option>
										<option value="change">座位调换</option>
										<option value="standup">Stand Up</option>
										<option value="relocate">搬迁</option>
										<option value="other">其他</option>
								</select>
							</td>
						</tr>
			         </table>
				</form>
		</div>
<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
api.button( {
	id : 'valueOk',
	name : '清空',

	callback : cleanseatMsg
}, {
	name : "返回"
});

function cleanseatMsg() {

		if($("#reason").val()==""){																 
			alert("reason不能為空！");	
			$("#remark").focus();
			return false;																			 
		}

		W.$.dialog.confirm(
				"&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ "确认清空?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", function() {
					$.ajax( {
						url : "<%=basePath%>"+"SeatServlet",
						type : "post",
						data : {"method":"cleanseatMessage",
				  				  "reason":$("#reason").val(),
				  				  "seatno":$("#seatnum").val()},
				  		dataType:'json',		  
						success : function(result) {
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

				});
	return false;
}

</script>
	</body>
</html>
