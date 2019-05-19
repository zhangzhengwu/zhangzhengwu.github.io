<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'pibabook_info.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<style>
	.col-4-table .tagName{
		width: 18%!important;
	}
	</style>
  </head>
  
<body>
<div class="cont-info">
	<div class="form-table">
		<table class="col-2-table">
			<tr>
				<td class="tagName">保险中文名称：</td>
				<td class="tagCont">
					<input type="text" name="" id="bookCName" />
				</td>
				
			</tr>
			<tr>
				<td class="tagName">保险英文名称：</td>
				<td class="tagCont">
					<input type="text" name="" id="bookEName" />
				</td>
			</tr>
			<tr>
				<td class="tagName">保险类型：</td>
				<td class="tagCont">
					<select id="type">
   						<option value="Paper 1">Paper 1</option>
   						<option value="Paper 2">Paper 2</option>
   						<option value="Paper 3">Paper 3</option>
   						<option value="Paper 4">Paper 4</option>
   						<option value="Paper 5">Paper 5</option>
   					</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">数量：</td>
				<td class="tagCont">
					<input type="text" name="" id="num" data-tag="数量" isnum/>
				</td>
			</tr>
			<tr>
				<td class="tagName">语言：</td>
				<td class="tagCont">
					<input type="text" name="" id="language" />
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;

$(function(){
	api.button({
		name: '确定',
		callback: function(){
			if(isCheck()){
				var data = {
					'bookEName': $('#bookEName').val(),
					'bookCName': $('#bookCName').val(),
					'type': $('#type').val(),
					'num': $('#num').val(),
					'language': $('#language').val(),
					'method': 'add'
				};
	   			mylhgAjaxs("QueryPibaBookServlet",data);
			}
   			return false;
		}
	},{
		name: '取消',
		callback: function(){
			
		}
	});
	function isCheck(){
		var flag = true;
		$('input[isnum]').each(function(){
			var _that = $(this);
			if(isNaN(_that.val())){
				W.error(_that.attr("data-tag")+"为数值类型");
				_that.select();
				flag = false;
				return false;
			}
		});
		return flag;
	}
});
</script> 
</body>
</html>