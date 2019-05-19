<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>定时任务总览页面</title>
  	
  	<style>
  	.info-table tabke{}
  	/*.info-table tabke td{word-break: break-word;}*/
  	</style>
  </head>
  
  <body>
  <div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">任务名称：</td>
				<td class="tagCont">
					<input type="text" id="taskName" />
				</td>
				<td class="tagName">是否有效：</td>
				<td class="tagCont">
					<select id="status">
						<option value="">请选择</option>
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">定时时间：</td>
				<td class="tagCont" colspan="3">
					<input type="text" id="executeTime" onClick="WdatePicker({dateFmt:'HH:mm'})"/><!-- WdatePicker({dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'end\')}'}) -->
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont"></td>
				<td class="tagCont" colspan="2">
					<a class="btn" id="select">
						<i class="icon-search"></i>
						查询
					</a>
					<a class="btn" id="add">
						<i class="icon-plus"></i>
						新增
					</a>
				</td>
			</tr>
		</table>	
	</div>

	<div class="info-table">
		<table id="queue">
			<thead>
			<tr>
				<th class="width_15">任务名称</th>
				<th class="width_5">执行时间</th>
				<th class="width_30">执行script</th>
				<th class="width_15">任务说明</th>
				<th class="width_5">是否有效</th>
				<th class="width_5">创建人</th>
				<th class="width_10">创建时间</th>
				<th class="width_5">备注</th>
				<th class="width_10">操作</th>
			</tr>
			</thead>
		</table>
		<div class="page_and_btn" align="center" style="display: none;">
			<table class="table-ss" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td id="pageLine">
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
  <script language="javascript" type="text/javascript" src="<%=basePath %>plug/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript">
  
  var total = 0;
  var pagenow = 1;
  var totalpage = 1;
  var bb = null;
  
  $(function(){
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
 

  	 

  
    $("#select").bind("click",function(){
    	select(1);
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
	  mylhgAjaxs("QueryTimeTaskScheduleServlet",{"method":"select",'taskName':$("#taskName").val(),'status':$("#status").val(),'executeTime':$("#executeTime").val(),'pagenow':pagenow },function(dat){
			if(dat.state=="success"){
	 		var html="";
	 		//var dataRole=eval(dat);
	 		var dataRole=dat.data;
				$("tr[id='select']").remove();
				if(dataRole[3]>0){
					total = dataRole[3];
					pagenow = dataRole[2];
					totalpage = dataRole[1];
					bb = dataRole[0];
					for(var i = 0;i < dataRole[0].length;i++){
						var sfyx="";
						if(dataRole[0][i].status=='1'){
							sfyx="有效";
						}else{
							sfyx="无效";
						}
						html+="<tr id='select'><td style='display:none;'>"+dataRole[0][i].id+"</td><td>"+dataRole[0][i].taskName+"</td><td>"+
						dataRole[0][i].executeTime+"</td><td>"+
						dataRole[0][i].executeScript+"</td><td>"+
						dataRole[0][i].explain+"</td><td>"+
						sfyx+"</td><td>"+
						dataRole[0][i].creator+"</td><td>"+
						dataRole[0][i].createDate+"</td><td>"+
						dataRole[0][i].remark+"</td><td><a onclick='modify("+i+")'>修改</a>/<a onclick='hisrecord("+i+")'>历史记录</a></td></tr>"; 
					}
					$(".page_and_btn").show();
					$("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
				}else{
					html+="<tr id='select'><td colspan='9'><font color='red';size=+1>"+"對不起，暫時沒有找到您想要的數據！"+"</font></td></tr>";
				    $(".page_and_btn").hide();
				}
				$("#queue:last").append(html); 
				page(pagenow,totalpage);
			}else{
				_alert(dat.msg);
			}
	  });
  
  }
 
 
  

$("#add").click(function(){ 
	var url = "<%=basePath%>page/timetask/timeTaskSchedule_info.jsp";
	addlhg(440,300,url);
});


function modify(index)
{
	var data = bb[index];
	var url = "<%=basePath%>page/timetask/timeTaskSchedule_modify.jsp";
	editlhg(440,340,url,data);
}

function hisrecord(index)
{
	var data = bb[index];
	var url = "<%=basePath%>page/timetask/timeTaskHistoryRecord.jsp";
	infolhg(800,600,url,data);
}
</script>  
</html>
