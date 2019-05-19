<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
 <html>
<head>
<title>关于系统和作者- 桌面</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="plug/js/Common.js"></script>
<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.css">
<link rel="stylesheet" href="<%=basePath%>plug/css/site.css">
<link rel="stylesheet" href="<%=basePath%>plug/css/font-awesome.min.css">
<!--[if lte IE 7]> 
<link rel="stylesheet"href="<%=basePath%>plug/css/font-awesome-ie7.css">
<![endif]--> 
<link rel="stylesheet" href="<%=basePath%>css/layout.css">
<!-- 
<link rel="stylesheet" href="<%=basePath%>css/systemmsg/main.css">
 -->
 <style type="text/css">
 
 .subcontainer{
 	position: relative;
 	padding: 3px 5px;
 	border: 1px solid #ddd;
 	border-top-left-radius: 5px;
 	border-top-right-radius: 5px;
 }
 .col-4{
 	margin: 10px 0px;
 	padding: 0px 5px;
 }
@media screen and (max-width: 1000px){
	.col-4{
		width: 100%;
	}
	.subcontainer table td .info{
		width: 450px;
	}
 }
 @media screen and (min-width: 1001px){
 	.col-4{
 		float:left;
 		width: 33.3333%;
 	}
 	.subcontainer table td .info{
 		width: 100px;
 	}
 }
 .subcontainer .tip{
 	position: absolute;
    left: 0px;
    top: 0px;
    display: block;
    width: 70px;
    text-align: center;
    padding: 1px 6px;
    border-bottom-right-radius: 5px;
    border-right: 1px solid #ddd;
    border-bottom: 1px solid #ddd;
    font-size: 12px;
    color: #333;
    background: #f1f1f1;
 }
 .subcontainer table{
 	margin: 30px 0px 10px 0px!important;
 }
 .subcontainer table thead{
 	border-bottom: 1px solid #ddd;
 }
  .subcontainer table tfoot{
 	border-top: 1px solid #ddd;
 }
 .subcontainer table th{
 	text-align: left;
 	padding: 0px 5px;
 }
 .subcontainer table td{
 	text-align: left;
 	padding: 0px 5px;
 }
 .subcontainer table td .info{
 line-height: 30px;
 	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
 }
 </style>
</head>
<body>
<div class="cont-info">
	<div class="info-title" style="display:none;">
		<div class="title-head">
			<h1>
				<i class="icon-folder-open"></i>系统功能
			</h1>
		</div>
		<div class="title-info">
			<p class="info-group">
				（1）信息管理：文件上传、数量添加、员工信息查询、提交明信片信息、查询导出、统计与导出、财务分析
			</p>
			<p class="info-group">
				（2）系统管理：用户管理、角色设置、修改密码、关于系统
			</p>
			<p class="info-group">
				（3）系统模块：①理財顧問名片管理、②Medical Claim、③Staff 名片管理、④Attendance
			</p>
		</div>
	</div>
		<div class="info-title">
		<div class="title-head">
			<h1>
				<i class="icon-folder-open"></i>系统使用概况
			</h1>
		</div>
		<div class="title-info" >
			<div id="container" style="width:50%;min-width:400px;height:300px;float:left;"></div>
			<div id="container2" style="width:50%;min-width:400px;height:300px;float:left;"></div>
			<div style="clear: both;"></div>
		</div>
	</div>
</div>

<!-- -------------------------------------------start----------------------------------------------------------- -->
<div class="cont-info">
	<div class="info-title" style="display:none;">
		<div class="title-head">
			<h1>
				<i class="icon-folder-open"></i>系统功能
			</h1>
		</div>
		<div class="title-info">
			<p class="info-group">
				（1）信息管理：文件上传、数量添加、员工信息查询、提交明信片信息、查询导出、统计与导出、财务分析
			</p>
			<p class="info-group">
				（2）系统管理：用户管理、角色设置、修改密码、关于系统
			</p>
			<p class="info-group">
				（3）系统模块：①理財顧問名片管理、②Medical Claim、③Staff 名片管理、④Attendance
			</p>
		</div>
	</div>
		<div class="info-title">
		<div class="title-head">
			<h1>
				<i class="icon-folder-open"></i>消息管理信息
			</h1>
		</div>
		<div class="title-info" >
			<div class="col-4">
				<div class="subcontainer" id="container3">
					<span class="tip">Group</span>
					<table>
						<thead>
							<tr id="title3">
								<th>序号</th>
								<th>主题</th>
								<th>公司</th>
								<th>时间</th>
								<th>发件人</th>
							</tr>
						</thead>
						<tbody id="jqajax"></tbody>
						<tfoot>
							<tr align="center" id="more1">
								<td style="text-align: right;" colspan="5">
									<a href="javascript:void(0)" onclick="notice_detail()">更多&gt;&gt;</a>
								</td>
							</tr>
						</tfoot>
						
					</table>
				</div>
			</div>
			<div class="col-4">
				<div class="subcontainer" id="container4">
					<span class="tip">Personal</span>
					<table >
						<thead>
							<tr id="title4">
								<th>序号</th>
								<th>主题</th>
								<th>公司</th>
								<th>时间</th>
								<th>发件人</th>
							</tr>
						</thead>
						<tbody id="jqajax2"></tbody>
						<tfoot>
							<tr align="center" id="more2">
								<td style="text-align: right;" colspan="5">
									<a href="javascript:void(0)" onclick="notice_detail()">更多&gt;&gt;</a>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div class="col-4">
				<div class="subcontainer" id="container5">
					<span class="tip">All</span>
					<table>
						<thead>
							<tr id="title5">
								<th>序号</th>
								<th>主题</th>
								<th>公司</th>
								<th>时间</th>
								<th>发件人</th>
							</tr>
						</thead>
						<tbody id="jqajax3"></tbody>
						<tfoot>
							<tr align="center" id="more3">
								<td style="text-align: right;" colspan="5">
									<a href="javascript:void(0)" onclick="notice_detail()">更多&gt;&gt;</a>
								</td>
							</tr>
						</tfoot>
						
					</table>
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
</div>
<!-- ---------------------------------------------end---------------------------------------------------------- -->

<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="plug/Highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="plug/Highcharts/js/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=basePath%>plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="plug/js/Util.js"></script>
<script type="text/javascript">

function init(){
	$.ajax({
		url:"operationrecord/OperationRecordServlet",
		type:"post",
		data:{"method":"usage"},
		success:function(date){
			var result=$.parseJSON(date);
   			if(result.state=="success"){
   				initSystemUsage30day(result.msg);
   			}else{
   				alert(result.msg);
   			}
		},error:function(){
			alert("网络连接失败!");
		}
	});
	$.ajax({
		url:"loginrecord/LoginRecordServlet",
		type:"post",
		data:{"method":"usage"},
		success:function(date){
			var result=$.parseJSON(date);
   			if(result.state=="success"){
   				window.setTimeout(initLoginCount(result.msg),500);
   			}else{
   				alert(result.msg);
   			}
		},error:function(){
			alert("网络连接失败!");
		}
	});
	
	queryType();//查询所有未读消息
	
}

function queryType(){
	$.ajax({
		url:"notice/NoticeServlet",
		type:"post",
		data:{"method":"findNotice_Type"},
		success:function(date){
			var html="";
			var html2="";
			var html3="";
			var num=0;
			var num2=0;
			var num3=0;
			if(date==null || date == "[]"){
				html+="<tr id='select'><td colspan='6' align='center'>"+"暂无未读消息!"+"</td></tr>";
				html2+="<tr id='select2'><td colspan='6' align='center'>"+"暂无未读消息!"+"</td></tr>";
				html3+="<tr id='select3'><td colspan='6' align='center'>"+"暂无未读消息!"+"</td></tr>";
			}else{
				var dataRole=eval(date);
				$("tr[id='select']").remove();
				$("tr[id='select2']").remove();
				$("tr[id='select3']").remove();
				
				for(var i=0;i<dataRole.length;i++){
					if(dataRole[i].type=="Group"){
						if(num<5){
							html+="<tr id='select' title='"+i+"'><td align='center'>"+(num+=1)+"</td></td><td align='center'>" +
							"<div class='info'><a href='javascript:void(0);' onclick='detail("+dataRole[i].id+")'>"
							+dataRole[i].subject +"</a></div></td><td align='center'>"+dataRole[i].company +"</td><td align='center'>"
							+dataRole[i].startdate +"</td><td align='center'>"+dataRole[i].creator +"</td>";		 
						}
					}else if(dataRole[i].type=="Personal"){
						if(num2<5){
							html2+="<tr id='select2' title='"+i+"'><td align='center'>"+(num2+=1)+"</td></td><td align='center'>" +
							"<div class='info'><a href='javascript:void(0);' onclick='detail("+dataRole[i].id+")'>"
							+dataRole[i].subject +"</a></div></td><td align='center'>"+dataRole[i].company +"</td><td align='center'>"
							+dataRole[i].startdate +"</td><td align='center'>"+dataRole[i].creator +"</td>";		 
						}
					}else{
						if(num3<5){
							html3+="<tr id='select3' title='"+i+"'><td align='center'>"+(num3+=1)+"</td></td><td align='center'>" +
							"<div class='info'><a href='javascript:void(0);' onclick='detail("+dataRole[i].id+")'>"
							+dataRole[i].subject +"</a></div></td><td align='center'>"+dataRole[i].company +"</td><td align='center'>"
							+dataRole[i].startdate +"</td><td align='center'>"+dataRole[i].creator +"</td>";		 
						}
					}
				}
				if(num==0){
					html+="<tr id='select'><td colspan='6' align='center'>"+"暂无未读消息!"+"</td></tr>";
				}
				if(num2==0){
					html2+="<tr id='select2'><td colspan='6' align='center'>"+"暂无未读消息!"+"</td></tr>";
				}
				if(num3==0){
					html3+="<tr id='select3'><td colspan='6' align='center'>"+"暂无未读消息!"+"</td></tr>";
				}
	 		}
			 $("#jqajax").append(html);
			 $("#jqajax2").append(html2);
			 $("#jqajax3").append(html3);
		 	 $("tr[id='select']:even").css("background","#COCOCO");
             $("tr[id='select']:odd").css("background","#F0F0F0");
		 	 $("tr[id='select2']:even").css("background","#COCOCO");
             $("tr[id='select2']:odd").css("background","#F0F0F0");
		 	 $("tr[id='select3']:even").css("background","#COCOCO");
             $("tr[id='select3']:odd").css("background","#F0F0F0");
		},error:function(){
			alert("网络连接失败!");
		}
	});
}

$(function(){
	init();
});

			
function detail(noticeId){
	$.ajax({
		 url: "notice/NoticeServlet",
		 type: "post",
		 data: {"method":"selectSingle_Own","noticeId":noticeId },	
		 success:function(data){
		 	var url1 = "page/notice/notice_detail1.jsp";
	           infolhg(1024,600,url1,data);
			   //window.location.reload();
			   queryType();
		 }
	});		
}

function notice_detail(){
	window.location.href="page/notice/notice.jsp";
}

function initSystemUsage30day(datas){
	 $('#container').highcharts({
         chart: {
        	 type:'pie',
           
             options3d: {
                 enabled: true,
                 alpha: 45,
                 beta: 0
             }
         },
         title: {
             text: '${adminUsername}最近30天访问记录'
         },
         credits:{
        	 enabled:0,
        	 text:"COAT System Function Usage",
        	 href:""
        	 },
         tooltip: {
             pointFormat: '{series.name}: ({point.y})<b>{point.percentage:.1f}%</b>'
         },
         plotOptions: {
             pie: {
                 allowPointSelect: true,
                 cursor: 'pointer',
                 depth: 35,
                 dataLabels: {
                     enabled: true
                 },
                 showInLegend: false
             }
         },
         series: [{
             type: 'pie',
             name: 'Visit Record',
             data: datas
         }]
     });
}

function initLoginCount(datas){
	$('#container2').highcharts({
        chart: {
            type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: 5,
                beta: 0
            }
        },
        credits:{
       	 enabled:1,
       	 text:"COAT System Usage Statistics",
       	 href:"javascript:init();"
       	 },
        title: {
            text: '${adminUsername}本年度登录情况统计'
        },
        subtitle: {
           // text: 'Notice the difference between a 0 value and a null point'
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: {
            categories: Highcharts.getOptions().lang.shortMonths
        },
        yAxis: {
            title: {
                text: null
            }
        },
        series: [{
            name: 'Login Record',
            data: datas
        }]
    });
}
</script>
</body>
</html>