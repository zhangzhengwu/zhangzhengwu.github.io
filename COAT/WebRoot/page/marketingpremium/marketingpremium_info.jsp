<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'marketingpremium_info.jsp' starting page</title>
    
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
	.form-table table.col-4-table textarea{ width: 92%!important; height: 60px;}
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
				<td class="tagName">可订购对象：</td>
				<td class="tagCont">
					<select id="blbz">
						<option value="A">Consultant && Staff</option>
						<option value="C">Consultant</option>
						<option value="S">Staff</option>
						<option value="N">Hidden</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">单价：</td>
				<td class="tagCont">
					<input type="text" name="" id="unitprice" data-tag="单价" isnum/>
				</td>
				<td class="tagName">俱乐部价格：</td>
				<td class="tagCont">
					<input type="text" name="" id="clubprice" data-tag="俱乐部价格" isnum/>
				</td>
			</tr>
			<tr>
				<td class="tagName">特殊价格：</td>
				<td class="tagCont" colspan="3">
					<input type="text" name="" id="specialprice" data-tag="特殊价格" isnum />
				</td>
			</tr>
			<tr>
				<td class="tagName">数量：</td>
				<td class="tagCont">
					<input type="text" name="" id="quantity" data-tag="数量" isnum/>
				</td>
				<td class="tagName">单位：</td>
				<td class="tagCont">
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
					'unitprice': $('#unitprice').val(),
					'unit': $('#unit').val(),
					'quantity': $('#quantity').val(),
					'clubprice': $('#clubprice').val(),
					'specialprice': $('#specialprice').val(),
					'remark': $('#remark').val(),
					'method': 'add'
				};
	   			mylhgAjaxs("QueryMarketPremiumServlet",data);
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
