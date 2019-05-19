<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'epaymentlist_modify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body>  
<div class="cont-info">
	<div class="form-table">
		<table class="col-2-table">
			<tr>
				<td class="tagName">产品编号：</td>
				<td class="tagCont">
					<input type="text" name="" id="procode" />
				</td>
				
			</tr>
			<tr>
				<td class="tagName">产品名称：</td>
				<td class="tagCont">
					<input type="text" name="" id="proname" />
				</td>
			</tr>
			<tr>
				<td class="tagName">产品状态：</td>
				<td class="tagCont">
					<select id="sfyx">
   						<option value="Y">有效</option>
   						<option value="N">无效</option>
   					</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">单价：</td>
				<td class="tagCont">
					<input type="text" name="" id="unit" data-tag="单价" isnum/>
				</td>
			</tr>
			<tr>
				<td class="tagName">数量：</td>
				<td class="tagCont">
					<input type="text" name="" id="quantity" data-tag="数量" isnum/>
				</td>
			</tr>
			<tr>
				<td class="tagName">费用：</td>
				<td class="tagCont">
					<input type="text" name="" id="price" data-tag="费用" isnum/>
				</td>
			</tr>
			<tr>
				<td class="tagName">备注：</td>
				<td class="tagCont">
					<input type="text" name="" id="remark" />
					<input type="hidden" id="proid" />
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
var api = frameElement.api, W = api.opener, data = api.data;

$(function(){
	$('#procode').val(data.productcode);
	$('#proname').val(data.productname);
	$('#sfyx').val(data.sfyx);
	$('#unit').val(data.unit);
	$('#quantity').val(data.quantity);
	$('#price').val(data.price);
	$('#remark').val(data.remark);
	$('#proid').val(data.id);
	api.button({
		name: '确定',
		callback: function(){
			if(isCheck()){
				var data = {
					'procode': $('#procode').val(),
					'proname': $('#proname').val(),
					'sfyx': $('#sfyx').val(),
					'unit': $('#unit').val(),
					'price': $('#price').val(),
					'quantity': $('#quantity').val(),
					'remark': $('#remark').val(),
					'proid': $('#proid').val(),
					'method': 'modify'
				};
	   			mylhgAjaxs("QueryEPaymentListServlet",data);
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
