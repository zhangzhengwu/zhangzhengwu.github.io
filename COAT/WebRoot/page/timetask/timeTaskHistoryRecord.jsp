<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>定时历史记录详细</title>
 	<script type="text/javascript" src="<%=basePath %>plug/My97DatePicker/WdatePicker.js"></script>
 	<script type="text/javascript">
 	var api = frameElement.api, W = api.opener;
	var data = api.data;
	
	
    var total = 0;
    var pagenow = 1;
    var totalpage = 1;
  
	api.button({
		name : '返回'
	}/* ,{
		name : '保存',
		callback : modify
	}*/); 
	
	$(function(){
/* 		$("#taskName").val(data.taskName);
		$("#nid").val(data.id); */
		
	select(1);
 
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				select(pagenow);
	});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				select(pagenow);
	});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				select(pagenow);
	});
	$("#last").bind("click", function() {//尾页
				pagenow = totalpage;
				select(pagenow);
	}); 
		
	});
	
	
	
	function page(currt,total){
		if(currt<=1){
			$("#first").hide();
			$("#pre").hide();
		}else{
			$("#first").show();
			$("#pre").show();
		}
		if(currt>=total){
			$("#last").hide();
			$("#next").hide();
		}else{
			$("#last").show();
			$("#next").show();
		}
	 }


  function select(pagenow){
	  mylhgAjaxs("QueryTimeTaskScheduleServlet",{"method":"selectHisRecord",'taskName':data.taskName,'pagenow':pagenow },function(dat){
			if(dat.state=="success"){
	 		var html="";
	 		//var dataRole=eval(dat);
	 		var dataRole=dat.data;
				$("tr[id='select']").remove();
				if(dataRole[3]>0){
					total = dataRole[3];
					pagenow = dataRole[2];
					totalpage = dataRole[1];
					for(var i = 0;i < dataRole[0].length;i++){

						html+="<tr id='select'><td>"+(i+1)+"</td><td>"+
						dataRole[0][i].taskName+"</td><td>"+
						dataRole[0][i].executeTime+"</td><td>"+
						dataRole[0][i].result+"</td></tr>"; 
					}
					$('#queue thead').show();
					$(".page_and_btn").show();
					$("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
				}else{
					html+="<tr id='empty'><td colspan='9' style='height: 40px; text-align: center; '><font color='red' size=+1>"+"對不起，暫時沒有找到您想要的數據！"+"</font></td></tr>";
				    $('#queue thead').hide();
					$(".page_and_btn").hide();
				}
				$("#queue:last").append(html); 
				page(pagenow,totalpage);
			}else{
				_alert(dat.msg);
			}
	  });
  
  }
	//注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				selects(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				selects(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				selects(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				selects(pagenow);
			});
 	
 	</script>
 
 	<style>
 	body{background: #fff!important; overflow-x: hidden;}
 	.e-container{margin: 0px; border: 0px;}
 	#queue tr:hover td{background: #eee;}
 	#empty:hover td{background: #fff;}
 	.page_and_btn a:visited{text-decoration: none;}
 	</style>
  </head>
  <body>
  <div class="e-container">
	<div class="info-form">
		<table id="queue">
			<thead style="display:  none;">
			<tr>
				<th class="width_10">序号</th>
				<th class="width_20">任务名称</th>
				<th class="width_20">执行时间</th>
				<th class="width_50">执行结果</th>
			</tr>
			</thead>
		</table>
		<div class="page_and_btn" align="center" style="display: none;">
			<table class="table-ss" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td id="pageLine" style="text-align: center;">
						<a id="first" href="javascript:void(0);">首页</a>
						<a id="pre" href="javascript:void(0);">上一页</a> 总共
						<SPAN id="total"></SPAN>页
						<a id="next" href="javascript:void(0);">下一页</a>
						<a id="last" href="javascript:void(0);">尾页</a>
					</td>
				</tr>
			</table>
		</div>
		
	</div>
	</div>
  </body>
</html>
