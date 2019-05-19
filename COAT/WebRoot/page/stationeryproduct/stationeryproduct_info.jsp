<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'stationeryproduct_info.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
	.form-table table.col-4-table .tagName{ width: 16%!important; }
	.form-table table.col-4-table .tagCont{ width: 32%!important; }
	.form-table table.col-4-table textarea{ width: 96%!important; height: 60px;}
	</style>
  </head>
  
<div class="cont-info">
	<div class="form-table">
		<table class="col-4-table">
			<tr>
				<td class="tagName">产品编号：</td>
				<td class="tagCont">
					<input type="text" name="" id="procode" />
				</td>
				<td class="tagName">产品英文名称：</td>
				<td class="tagCont">
					<input type="text" name="" id="englishname" />
				</td>
			</tr>
			<tr>
				<td class="tagName">产品中文名称：</td>
				<td class="tagCont">
					<input type="text" name="" id="chinesename" />
				</td>
				<td class="tagName">适用类型：</td>
				<td class="tagCont">
					<select id="blbz">
						<option value="A">所有人</option>
						<option value="S">内勤</option>
						<option value="C">顾问</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">单价：</td>
				<td class="tagCont">
					<input type="text" name="" id="price" data-tag="单价" isnum/>
				</td>
				<td class="tagName">数量：</td>
				<td class="tagCont">
					<input type="text" name="" id="quantity" data-tag="数量" isnum />
				</td>
			</tr>
			<tr>
				<td class="tagName">单位：</td>
				<td class="tagCont" colspan="3">
					<input type="text" name="" id="unit" />
				</td>
			</tr>
			
			<tr>
				<td class="tagName">备注：</td>
				<td class="tagCont" colspan="3">
					<textarea id="remark"></textarea>
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
					'englishname': $('#englishname').val(),
					'chinesename': $('#chinesename').val(),
					'procode': $('#procode').val(),
					'blbz': $('#blbz').val(),
					'price': $('#price').val(),
					'unit': $('#unit').val(),
					'quantity': $('#quantity').val(),
					'remark': $('#remark').val(),
					'method': 'add'
				};
	   			mylhgAjaxs("QueryStationeryProductServlet",data);
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
		if($('#procode').val()==""){
			flag = false;
			W.error("产品编号不能为空");
			$('#procode').focus();
			return false;
		}
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
