<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<script type="text/javascript" src="plug/My97DatePicker/WdatePicker.js"></script>
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
 .items{
 	position: relative;
 	border: 1px solid #ddd;
 	padding: 10px;
 	margin: 15px 10px;
 	border-top-left-radius: 5px;
 	border-top-right-radius: 5px;
 }
 .items .tag{
 	position: absolute;
 	left: 0px;
 	top: 0px;
 	border-bottom: 1px solid #ddd;
 	border-right: 1px solid #ddd;
 	border-bottom-right-radius: 5px;
 	color: #333;
 	text-align: center;
 	padding: 2px 20px;
 	font-size: 13px;
 	background: #f1f1f1;
 }
 .clear{
 	clear: both;
 }
 table{
 	width: auto!important;
 }
 table td{
 	
 }
 table .tagName{
 	width: auto!important;
 	padding-top: 7px!important;
 }
 table .tagCont{
 	width: auto!important;
 }
 table .tagBtn{
 	width: auto!important;
 }
 table .btn{
 	padding: 2px 12px!important;
 	margin-bottom: 8px!important;
 }
 </style>
</head>
<body>
<div class="cont-info">
	<div class="info-title">
		<div class="title-head">
			<h1>
				<i class="icon-folder-open"></i>日志报表条形图
			</h1>
		</div>
		<div class="title-info">
			<div class="items">
				<span class="tag">年报表</span>
				<table class="">
					<tr>
						<td class="tagName">起始年份:</td>
						<td class="tagCont">
							<input type="text" class="Wdate" id="start_Y" onfocus="WdatePicker({dateFmt:'yyyy',minDate:'1990',maxDate:'2050'})"/>
						</td>
						<td class="tagName">结束年份:</td>
						<td class="tagCont">
							<input type="text" class="Wdate" id="end_Y" onfocus="WdatePicker({dateFmt:'yyyy',minDate:'1990',maxDate:'2050'})"/>
						</td>
						<td class="tagBtn">
							<a class="btn" id="searchs_Y" name="search"> <i class="icon-search"></i>Search </a>
						</td>
					</tr>
				</table>
				<div id="container" style="width:100%;min-width:400px;height:300px;float:left;"></div>
				<div class="clear"></div>
			</div>
			<div class="items">
				<span class="tag">月报表</span>
				<table class="">
					<tr>
						<td class="tagName">起始年月:</td>
						<td class="tagCont">
							<input type="text" class="Wdate" id="start_M" onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'1990-01',maxDate:'2050-01'})"/>
						</td>
						<td class="tagName">结束年月:</td>
						<td class="tagCont">
							<input type="text" class="Wdate" id="end_M" onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'1990-01',maxDate:'2050-01'})"/>
						</td>
						<td class="tagBtn">
							<a class="btn" id="searchs_M" name="search"> <i class="icon-search"></i>Search </a>
						</td>
					</tr>
				</table>
				<div id="container2" style="width:100%;min-width:400px;height:300px;float:left;"></div>
				<div class="clear"></div>
			</div>
			<div class="items">
				<span class="tag">日报表</span>
				<table class="">
					<tr>
						<td class="tagName">日期起:</td>
						<td class="tagCont">
							<input type="text" class="Wdate" id="startdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'1990-01-01',maxDate:'2050-01-01'})"/>
						</td>
						<td class="tagName">日期止</td>
						<td class="tagCont">
							<input type="text" class="Wdate" id="enddate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'1990-01-01',maxDate:'2050-01-01'})"/>
						</td>
						<td class="tagBtn">
							<a class="btn" id="searchs_D" name="search"> <i class="icon-search"></i>Search </a>
						</td>
					</tr>
				</table>
				<div id="container3" style="width:100%;min-width:400px;height:300px;float:left;"></div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="plug/js/jquery-1.7.1.min.js"></script>
 <script type="text/javascript" src="plug/Highcharts/js/highcharts.js"></script>
 <script type="text/javascript" src="plug/Highcharts/js/highcharts-3d.js"></script>
<script type="text/javascript" src="plug/Highcharts/modules/exporting.js"></script>
<script type="text/javascript" src="<%=basePath%>plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="plug/js/Util.js"></script>
<script type="text/javascript">
$(function(){
	var myDate = new Date();
	var vDay = myDate.getDate();
	$("#start_Y").val(myDate.getFullYear());
	$("#end_Y").val(myDate.getFullYear());
	$("#start_M").val(CurentTimeYM());
	$("#end_M").val(CurentTimeYM());
	$("#startdate").val(CurentTime());	
	$("#enddate").val(CurentTime());	
    init();
    init2();
    init3();
    
    $("#searchs_Y").click(function(){ 	
		init();	
	});
    $("#searchs_M").click(function(){ 	
		init2();	
	});
    $("#searchs_D").click(function(){ 	
		init3();	
	});
    
});

/**
 * 获取当前年月(如:2012-01)
 * */
function CurentTimeYM(){ 
       var now = new Date();
       var year = now.getFullYear();       //年
       var month = now.getMonth() + 1;     //月
       var clock = year + "-";
        if(month < 10)
           clock += "0";
       clock += month;
       return clock; 
   }

function init(){
	if($("#start_Y").val()!="" && $("#end_Y").val()!=""){
		if($("#start_Y").val()>$("#end_Y").val()){
			alert("开始日期不能大于结束日期.");
			$("#start_Y").focus();
			return false;
		}
	}
	$.ajax({
		url:"operationrecord/OperationRecordServlet",
		type:"post",
		data:{"method":"queryLogReport_Year","start_Y":$("#start_Y").val(),"end_Y":$("#end_Y").val()},
		beforeSend: function(){
			parent.showLoad();
		},complete: function(){
				parent.closeLoad();
		},
		success:function(date){
			var result=$.parseJSON(date);
   			if(result.state=="success"){
   				window.setTimeout(LogReport_Year(result.msg),500);
   			}else{
   				alert(result.msg);
   			}
		},error:function(){
			alert("网络连接失败!");
		}
	});
}

function init2(){
	if($("#start_M").val()!="" && $("#end_M").val()!=""){
		if($("#start_M").val()>$("#end_M").val()){
			alert("开始日期不能大于结束日期.");
			$("#start_M").focus();
			return false;
		}
	}
	$.ajax({
		url:"operationrecord/OperationRecordServlet",
		type:"post",
		data:{"method":"queryLogReport_Month","start_M":$("#start_M").val(),"end_M":$("#end_M").val()},
		beforeSend: function(){
				parent.showLoad();
		},complete: function(){
				parent.closeLoad();
		},
		success:function(date){
			var result=$.parseJSON(date);
   			if(result.state=="success"){
   				window.setTimeout(LogReport_Month(result.msg),500);
   			}else{
   				alert(result.msg);
   			}
		},error:function(){
			alert("网络连接失败!");
		}
	});
}
function init3(){
	if($("#startdate").val()!="" && $("#enddate").val()!=""){
		if($("#startdate").val()>$("#enddate").val()){
			alert("开始日期不能大于结束日期.");
			$("#startdate").focus();
			return false;
		}
	}
	$.ajax({
		url:"operationrecord/OperationRecordServlet",
		type:"post",
		data:{"method":"queryLogReport_Day","startDate":$("#startdate").val(),"endDate":$("#enddate").val()},
		beforeSend: function(){
			parent.showLoad();
		},complete: function(){
				parent.closeLoad();
		},
		success:function(date){
			var result=$.parseJSON(date);
			if(result.msg.length>0){
   				LogReport_Day(result.msg);
			}else{
				var a=[{
	            	name: '该天没有访问数据',
	            	data: "[0,0,0,0,0,0,0,0,0,0,00]"
	        	}]
					
	   				LogReport_Day(a);
			}
		},error:function(){
			alert("网络连接失败!");
		}
	});
}

function LogReport_Year(datas){
	$('#container').highcharts({
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
            text: '访问日志情况统计(年报表)'
        },
        subtitle: {
           // text: 'Notice the difference between a 0 value and a null point'
        },tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f}次</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
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
            name: 'Log Record',
            data: datas
        }]
    });
}

function LogReport_Month(datas){
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
	       	enabled:false,
	       	text:"COAT System Usage Statistics",
	       	href:"javascript:init();"
       	},
        title: {
            text: '访问日志情况统计(月报表)'
        },
        subtitle: {
           // text: 'Notice the difference between a 0 value and a null point'
        },tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f}次</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: {
           //categories: Highcharts.getOptions().lang.shortMonths
           categories:["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",
        	   "16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"]
        },
        yAxis: {
            title: {
                text: null
            }
        },
        series: [{
            name: 'Log Record',
            data: datas
        }]
    });
}

function LogReport_Day(datas){
	//console.log(datas);
	$('#container3').highcharts({
        chart: {
            type: 'column',
            margin: 75,
            options3d: {
                enabled: false,
                alpha: 5,
                beta: 0
            }
        },  exporting:
        {


            enabled:true
            
          }, credits:{
	       	enabled:false,
	       	text:"COAT System Usage Statistics",
	       	href:"javascript:init();"
       	},
        title: {
            text: '访问日志情况统计(日报表)'
        },
        subtitle: {
           // text: 'Notice the difference between a 0 value and a null point'
        },tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0;height:20px;">{series.name}: </td>' +
                '<td style="padding:0;height:20px;"><b>{point.y:.1f}次</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: {
            categories: [
                '00','01','02','03','04','05','06','07','08','09','10','11',
                '12','13','14','15','16','17','18','19','20','21','22','23']
        },
        yAxis: {
            title: {
                text: null
            }
        },
        series:datas /**[{
            name: '超级管理员组',
            data: datas1

        }, {
            name: '管理员',
            data: datas2

        }, {
            name: '香港行政',
            data: datas3

        }, {
            name: 'IT系统管理员',
            data: datas4

        }, {
            name: 'SSC Admin Team',
            data: datas5

        }, {
            name: 'CS Processing Team',
            data: datas6

        }, {
            name: '香港IT',
            data: datas7

        }]**/
    });
}


</script>
</body>
</html>