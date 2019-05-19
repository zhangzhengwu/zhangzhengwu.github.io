<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Seat Menu Detail</title>

	<link rel="stylesheet" href="plug/css/bootstrap.css">
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
<%-- 	<script type="text/javascript" src="<%=basePath%>plug/js/json2.js"></script> --%>
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
				<td class="tagName">Seat No：</td>
				<td class="tagCont">
					<input id="seatno" name="seatno" type="text" class="inputstyle" value="${seatlist.seatno}"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location：</td>
				<td class="tagCont">
					<input id="location" name="location" type="text" class="inputstyle" value="${seatlist.location}"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Floor：</td>
				<td class="tagCont">
					<input id="floor" name="floor" type="text" class="inputstyle" value="${seatlist.floor}"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Locker No：</td>
				<td class="tagCont">
					<input id="lockerno" name="lockerno" type="text" class="inputstyle" value="${seatlist.lockerno}"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Desk Drawer No:</td>
				<td class="tagCont">
					<input id="deskDrawerno" name="deskDrawerno"  type="text" class="inputstyle" value="${seatlist.deskDrawerno}"/>
				</td>
			</tr>	
			<tr>
				<td class="tagName">Pigeon Box No：</td>
				<td class="tagCont">
					<input id="pigenBoxno" name="pigenBoxno"  type="text" class="inputstyle" value="${seatlist.pigenBoxno}"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">If Hidden Seat：</td>
				<td class="tagCont">
					<input id="ifhidden" name="ifhidden"  type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">If AD or DD Seat：</td>
				<td class="tagCont">
					<input id="pigenBoxno" name="pigenBoxno"  type="text" class="inputstyle" value="${seatlist.remark}"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input id="staffcode" name="staffcode"  type="text" class="inputstyle" value="${seatlist.staffcode}"/>					
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Name：</td>
				<td class="tagCont">
					<input id="staffname" name="staffname" type="text" class="inputstyle" value="${seatlist.staffname}"/> 
				</td>
			</tr>
			<tr>
				<td class="tagName">Extension No：</td>
				<td class="tagCont">
					<input name ="extensionno" id ="extensionno" type="text" class="inputstyle" value="${seatlist.extensionno}"/> 
				</td>
			</tr>
			<tr>
				<td class="tagName">Updater：</td>
				<td class="tagCont">
					<input name ="updater" id ="updater" type="text" class="inputstyle" value="${seatlist.updater}"/> 
				</td>
			</tr>
			<tr>
				<td class="tagName">UpdateDate：</td>
				<td class="tagCont">
					<input name ="updatedate" id ="updatedate" type="text" class="inputstyle" value="${seatlist.updateDate}"/> 
				</td>
			</tr>
			<tr>
				<td class="tagName">Remark：</td>
				<td class="tagCont">
					<textarea id="remark" name="remark" class="inputstyle">${seatlist.remark1}</textarea>
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
	});
	$(function(){
		$(".inputstyle").attr("readonly",true);
		
		if("${seatlist.ifhidden}" == 'Y'){
			$("#ifhidden").val("YES");
		}else{
			$("#ifhidden").val("NO");
		}
		
		
	});
	
	
</script>
</html>
