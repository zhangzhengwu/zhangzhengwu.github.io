<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查詢頁面</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="plug/js/Common.js"></script>
 	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<link rel="stylesheet" href="<%=basePath%>/css/layout.css">
	<link rel="stylesheet" href="<%=basePath%>/plug/css/site.css">
	<script src="<%=basePath%>/css/date.js" language="JavaScript"></script>
	<script src="<%=basePath%>/css/Util.js" language="JavaScript"></script>
	<script type="text/javascript" src="<%=basePath%>plug/lhgdialog/lhgdialog.min.js"></script>
	<script src="<%=basePath%>plug/js/Common.js" language="JavaScript"></script>
 	<script type="text/javascript">
var basepath = '<%=basePath%>';
var noticetype = '<%=request.getParameter("noticetype")%>';
var pagenow =1;
var totalpage=1;
var total=0;
var downs=null;
 		
 $(function(){
 	$("#type").val(noticetype);
	 	select(1);
		$("#search").click(function(){
       		select(1);	
		});
		    //注册单击事件
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
			$("#end").bind("click", function() {//尾页
						pagenow = totalpage;
						select(pagenow);
			});
			
});
			
function select(pagenows){
$.ajax({
	 url: basepath+"/notice/NoticeServlet",
	 type: "post",
	 data: {"method":"selectListByType","noticetype":$("#type").val(),"pageNow":pagenows,"subject":$("#subject").val(),"company":$("#company").val(),"date1":$("#date1").val(),"date2":$("#date2").val()},
	 beforeSend: function(){
		parent.showLoad();
	 },
	 complete: function(){
		parent.closeLoad();
	 },	
	 success:function(data){
	 	if(data==null || data == "[]"){
			alert("没有更多消息！");
		}else{
			var dataRole=eval(data);
			var html="";
			$("tr[id='select']").remove();
			downs=null;
			if(dataRole[3]>0){
				total=dataRole[3];
				pagenow =dataRole[2];
			    totalpage=dataRole[1];
			    downs=dataRole[0];
				for(var i=0;i<dataRole[0].length;i++){                        
					html+="<tr id='select' title='"+i+"'><td align='center'>"+((i+1)+(dataRole[2]-1)*dataRole[4])+"</td></td><td align='center'>"
					+dataRole[0][i].type +"</td><td align='center'><a href='javascript:void(0);' onclick='detail("+dataRole[0][i].id+")'>"
					+dataRole[0][i].subject +"</a></td><td align='center'>"+dataRole[0][i].company +"</td><td align='center'>"
					+dataRole[0][i].startdate +"</td><td align='center'>"+dataRole[0][i].creator +"</td><td align='center'>"
					+dataRole[0][i].ifread +"</td><td align='center'><a href='javascript:void(0);' onclick='detail("+dataRole[0][i].id+")'>详细</a>/<a href='javascript:void(0);' onclick='del("+dataRole[0][i].id+")'>删除</a></td>";		 
				}
	 				$(".page_and_btn").show();
			   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
			}else{
				html+="<tr id='select'><td colspan='19' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
			 	$(".page_and_btn").hide();
			}
				 $("#jqajax:last").append(html);
			 	 $("tr[id='select']:even").css("background","#COCOCO");
	             $("tr[id='select']:odd").css("background","#F0F0F0");
	           	 page(pagenow,totalpage);
	 	}

	 }
  });

}
			
 function page(currt,total){
	if(currt<=1){
			$("#first").hide();
			$("#pre").hide();
		}else{
			$("#first").show();
			$("#pre").show();
		}
		if(currt>=total){
			$("#end").hide();
			$("#next").hide();
		}else{
			$("#end").show();
			$("#next").show();
		}
}
			
function detail(noticeId){
	$.ajax({
			 url: basepath+"/notice/NoticeServlet",
			 type: "post",
			 data: {"method":"selectSingle","noticeId":noticeId },	
			 success:function(data){
			 	var url1 = basepath + "page/notice/notice_detail1.jsp";
	            infolhg(1200,600,url1,data);
						    //window.location.reload();
						    select(pagenow);
			 }
	});		
}
function del(noticeid){
	$.ajax({
			 url: basepath+"/notice/NoticeServlet",
			 type: "post",
			 data: {"method":"saveNoticeDelete","noticeId":noticeid },	
			 success:function(data){
			 var dataRole=eval(data);
				if(dataRole==1){
					alert("消息删除成功！");
					window.location.reload();
				}else{
					alert("消息删除失败！");
					window.location.reload();
				}
			 }
	});		
}
			
function add()
	{
		$.ajax({
			 url: basepath+"/notice/NoticeServlet",
			 type: "post",
			 data: {"method":"queryTypeList"},	
			 success:function(data){
				addlhg(1200,800,'page/notice/notice_add.jsp',data);
			 }
		});		
	
	
	
	}
				
function exportDate(){
		if(downs!=null){			
			var subject=$("#subject").val();
			var company=$("#company").val();
			var type=$("#type").val();
			var date1=$("#date1").val();
			var date2=$("#date2").val();
		    var method="export";
	        location.href=basepath+"/notice/NoticeServlet?subject="+subject+"&company="+company+"&type="+type+"&date1="+date1+"&date2="+date2+"&method="+method;
        }else{
	        alert("请先查询数据，再做导出相关操作！");
	        }	
}
		 </script>
	</head>
<body  >
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">标题：</td>
				<td class="tagCont">
					<input type="text" name="subject" id="subject">
				</td>
				<td class="tagName">公司：</td>
				<td class="tagCont">
					<input type="text" name="company" id="company">
				</td>
			</tr>
			<tr>
				<td class="tagName">类型：</td>
				<td class="tagCont">
					<select name="type" id="type"> 
			            <option value="Group" >Group</option>
			            <option value="Personal" >Personal</option>
			            <option value="All">All</option>
			        </select>
				</td>
			</tr>
			<tr>
				<td class="tagName">时间起：</td>
				<td class="tagCont">
					<input type="text" name="date1" id="date1" onClick="Calendar('date1');" >
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#date1').val('');"></i>
				</td>
				<td class="tagName">时间至：</td>
				<td class="tagCont">
					<input type="text" name="date2" id="date2" onClick="Calendar('date2');">
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#date2').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagCont" colspan="2"></td>
				<td class="tagCont" style="padding-left: 30px;" colspan="2">
					<a class="btn" id="search" name="search">
						<i class="icon-search"></i>
						查询
					</a>
					<a class="btn" onclick="add()" >
						<i class="icon-plus"></i>
						添加
					</a>
					<a class="btn" id="down" name="downs" onclick="exportDate()">
						<i class="icon-download"></i>
						导出
					</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead>
			<tr id="title">
				<th>序号</th>
				<th>类型</th>
				<th>主题</th>
				<th>公司</th>
				<th>时间</th>
				<th>发件人</th>
				<th>是否已读</th>
				<th>操作</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;" >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">首页</a>
						<a id="pre" href="javascript:void(0);">上一页</a> 总共
						<SPAN style="color: red;" id="total">
						</SPAN>页
						<a id="next" href="javascript:void(0);">下一页</a>
						<a id="end" href="javascript:void(0);">尾页</a>
					</td>
				</tr>
			</table>
		</div>	
	</div>
</div>
</body>
</html>