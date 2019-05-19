<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>定时任务新增页面</title>
 <script type="text/javascript" src="<%=basePath %>plug/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript">
  	var api = frameElement.api, W = api.opener;
	api.button({
		name : '返回'
	},{
		name : '保存',
		callback : save
	});
  
  
  	function save(){
  		var dateObj = $('#choosed a.choosed'), dateStr = [], executeTime = '';
		if($("#taskName").val()==""){
			W.error("任务名称不能為空！");
			$('#taskName').focus();
			return false;
		}
		if(dateObj.length <= 0){
			W.error("执行时间不能為空！");
			$("#executeTime").focus();
			return false;
		}
		if($("#executeScript").val()==""){
			W.error("执行Script不能為空！");
			$("#executeScript").focus();
			return false;
		}
		if($("#explain").val()==""){
			W.error("任务说明不能為空！");
			$("#explain").focus();
			return false;
		}
		var dateObj = $('#choosed a.choosed'), dateStr = [], executeTime = '';
  		for(var i=0; i< dateObj.length; i++){
  			dateStr.push(dateObj.eq(i).attr('aid'));
  		}
  		executeTime = dateStr.join(',');
		var data = {"method":"addTimeTask",'taskName':$("#taskName").val(),'executeTime':executeTime,'executeScript':$("#executeScript").val(),'explain':$("#explain").val(),'remark':$("#remark").val()};
		mylhgAjaxs("QueryTimeTaskScheduleServlet",data);
		return false;
	}
  
  
  </script>  
    
    
  </head>
  <body>
	<div class="form-table">
		<table class="col-2-table">
			<tr>
				<td class="tagName">任务名称</td>
				<td class="tagCont">
					<input type="text"  id="taskName"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">执行时间</td>
				<td class="tagCont">
					<div style="float: left; width: 100%;">
						<input type="text" id="executeTime" onclick="WdatePicker({dateFmt:'HH:mm',isShowClear: false, onpicked: choosed})"/>
					</div>
					<div id="choosed" style="float: left; width: 80%; margin-top: -5px;">
						
					</div>
					<div style="clear: both"></div>
				</td>
			</tr>
			<tr>
				<td class="tagName">执行Script</td>
				<td class="tagCont">
					<input type="text" id="executeScript"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">任务说明</td>
				<td class="tagCont">
					<input type="text" id="explain"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">备注</td>
				<td class="tagCont">
					<textarea rows="5" cols="3" id="remark"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<style type="text/css">
		*{font-size: 12px;}
		a{text-decoration: none; cursor: pointer;}
		a.choosed{display: block; padding: 1px 15px 1px 5px; float: left; background: #fff; border: 1px solid #ccc; border-radius: 5px; position: relative; text-align: left; height: 22px; line-height: 22px; color: #333; margin: 3px 10px 3px 0px; }
		a.choosed:hover{text-decoration: none; border: 1px solid #aaa;}
		/*a.choosed:hover span{color: red;}*/
		span.closed{position: absolute; right: 2px; top: 0px; font-size: 16px; }
	</style>
	<script type="text/javascript">
		function choosed(){
			var d = $dp.cal.getDateStr(), choosedStr = [];
			
			var choosedList = $('a.choosed');
			for(var i=0; i<choosedList.length; i++){
				if(d == choosedList.eq(i).attr('aid')){//防止重复选择
					return false;
				}
			}
			$('#choosed').append(makeChoosed(d));
		}
		function makeChoosed(date){	
			return '<a class="choosed" aid="'+date+'">'+
						date+
						'<span class="closed">×</span>'+
					'</a>';
		}
		$('span.closed').live('click',function(){
			var _that = $(this).parent();
			_that.fadeOut(function(){
				_that.remove();
			});
		});
		
	</script>
  </body>
</html>
