<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>trainee</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	
	
    <style type="text/css">
<!--
#Medical {
	width:100%;
	height:99%;
	z-index:-1;
	left: 0px;
	top: 0px;
}
.txt{
    color:#0000FF;
    border:0px;
   border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
 background-color:transparent; /* 顏色透明*/
	size:35px;
	font-style:oblique;
	font-family: '仿宋';
	font-size: 18px;

    }
    .select{text-align: center;
   height: 12px;
    }
    td{
    text-align: center;
    }
	.txts{
    color:#005aa7;
    border:0px;
     border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:35px;
	font-family: '仿宋';
	font-size: 16px;

    }
 .page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}
.STYLE1 {
	font-size: 36px;
	font-weight: bold;
	color: #0000FF;
}
-->
    </style>
    
  </head>
  <script type="text/javascript" >
  var downs=null;
  var curPage=1;
  var totalPage=1;
  $(function(){
     //注册单击事件
 	var da = new Date();
     //注册单击事件
	$("#start_date").val(getMonthBeforeDay(da));	
	$("#end_date").val(getBeforeMonthLastDay(da));	
	$("#pre").bind("click", function() {//上一页
				curPage=curPage - 1;
				selects();
			});
	$("#next").bind("click", function() {//下一页
			curPage=curPage + 1;
				selects();
			});
	$("#first").bind("click", function() {//首页
				curPage=1;
				selects();
			});
	$("#end").bind("click", function() {//尾页
				curPage=totalPage;
				selects();
			});
	
/***************************************************************************************************/
/*
*获取前一个月的第一天
* date : Date类型.
*/
function getMonthBeforeDay(date){
	var year = date.getFullYear();
	var month = date.getMonth();
	if(month == 0){
		month = 12;
		year = year - 1;
	}
	date = year+"-"+((month)<=9?"0"+month:month)+"-01";
	return date;
}
/*
*获取前一个月的最后一天
*date : Date类型.  
*/

function getBeforeMonthLastDay(date){
	var year = date.getFullYear();
	var month = date.getMonth();
	if(month == 0){
		month = 12;
		year = year - 1;
	}
	month2 = parseInt(month,10)+1;
	//var temp = new Date(year+"/"+month2+"/0");
	var temp = new Date(year+"/"+month+"/31");
	while(temp.getDate()<10){
		temp=new Date(year+"/"+month2+"/"+(31-temp.getDate()));
	}
	 	date = year+"-"+((month)<=9?"0"+month:month)+"-"+temp.getDate();
//	date = year+"-"+month+"-"+temp.getDate();
	return date;
}
/*****************************************************Down click**********************************/
	$("#down").click(function(){ 
		if(downs!=null){
			window.location.href="<%=basePath%>/DownTraineeReportServlet?start_Date="+$("#start_date").val()+"&end_Date="+$("#end_date").val()+"&curretPage="+curPage+"&staffcode="+$("#staffcodes").val();
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
			}
	
	});
	/******************************************************Down END***********************************/
	function checkMonth(bdate,edate){
		var dt1 = new Date(Date.parse(bdate.replace(/-/g, "/")));
		var dt2 = new Date(Date.parse(edate.replace(/-/g, "/")));
		if(dt1.getYear()== dt2.getYear()){
			if(dt1.getMonth() == dt2.getMonth()){
				return true;
			}else{
				alert("月份不同！");
				return false;
			}
		}else{
			alert("年份不同！");
			return false;
		}
	}
	/**************************************************selects Click*****************************************************/
	$("#searchs").click(function(){
		selects();
	});
	/***********************************************select function************************************/
	function selects(){
			if($("#start_date").val()=="" ){
				alert("開始日期不能为空!");
				$("#start_date").focus();
				return false;
			}
			if($("#end_date").val()=="" ){
				alert("結束日期不能为空!");
				$("#end_date").focus();
				return false;
			}
			if($("#start_date").val()!="" && $("#end_date").val()!=""){
				if($("#start_date").val()>$("#end_date").val()){
					alert("開始日期不能大於結束日期!");
					$("#start_date").focus();
					return false;
				}
			}
			if(!checkMonth($("#start_date").val(),$("#end_date").val())){
				return false;
			}
			$.ajax({
			type: "post",
			url:"QueryTraineeServlet",
			data: {'start_Date':$("#start_date").val(),'end_Date':$("#end_date").val(),'staffcode':$("#staffcodes").val(),'curretPage':curPage},
			beforeSend: function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
			success:function(data){
				var totals=0;
				var dataRole=eval(data);
				var html="";
				$("tr[id='select']").remove();
				downs=null;
				if(dataRole[0].length>0){
					downs=dataRole;
					$(".page_and_btn").show();
					curPage=dataRole[1];
	 			   	totalPage=dataRole[2];
					for(var i=0;i<dataRole[0].length;i++){
						html+="<tr id='select' title='"+i+"'><td>"+dataRole[0][i].code+"</td><td>"+dataRole[0][i].staffcode+"</td><td>"+
						dataRole[0][i].ldatestart+"</td><td>"+dataRole[0][i].ldateend+"</td><td>"+dataRole[0][i].daynum+"</td><td>"+
						dataRole[0][i].d+"</td><td>"+dataRole[0][i].lnum+"</td><td>"+dataRole[0][i].lyear+"</td><td>"+dataRole[0][i].leave_type+"</td></tr>";
					}
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select'><td colspan='18' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
				}	 page(dataRole[1],dataRole[2]);
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[1]);
					 $("#totalPage").empty().append(dataRole[2]);
				 	 $("tr[id='select']:even").css("background","#COCOCO");
	                 $("tr[id='select']:odd").css("background","#F0F0F0");
			 }
		 });
	}
	/**************************************************************************************************/
  });
  function page(currt,total){
	 
		if(curPage<=1){
			$("#first").hide();
			$("#pre").hide();
		}else{
			$("#first").show();
			$("#pre").show();
		}
		if(curPage>=totalPage){
			$("#end").hide();
			$("#next").hide();
		}else{
			$("#end").show();
			$("#next").show();
		}

	}
  
    function del(i){
	  if(confirm("确定要删除？无法恢复！")){
		  $.ajax({
			 url:"DelConsultantReport",
			 type:"post",
			 data:{'staffcode':downs[0][i].staffcode,'mapdate':downs[0][i].mapdate},
		  success:function(date){
				 if(date=="success"){
					 alert("删除成功！");
					//$("tr [title='"+i+"']").remove();
					$("#searchs").click();
				 }if(date=="error"){
					 alert("删除异常");
					 return false;
				 }
		  },error:function(){
			  alert("网络连接失败，请联系管理员或稍后重试...");
			  return false;
		  }
		  });
	  }
  }
  </script>
<body style='overflow:auto;'>
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">開 始 日 期 ：</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">結 束 日 期：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it" onClick="return Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input type="text" name="staffcode" id="staffcodes">
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
			<tr>
				<td class="tagCont" colspan="2"></td>
				<td class="tagCont" colspan="2">
					<c:if test="${roleObj.search==1}">
						<a class="btn" id="searchs" name="search">
							<i class="icon-search"></i>
							查詢
						</a>
					</c:if>
					<c:if test="${roleObj.export==1}">
						<a class="btn" id="down" name="downs">
							<i class="icon-download"></i>
							導出
						</a>
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead>
			<tr id="title">
				<th class="width_">Num</th>
				<th class="width_">Code</th>
				<th class="width_">Date</th>
				<th class="width_">Date2</th>
				<th class="width_">Day</th>
				<th class="width_">D</th>
				<th class="width_">Day2</th>
				<th class="width_">Year</th>
				<th class="width_">Leave Type</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn">
			<table class="table-ss" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="main_head">
					<td colspan="14" align="center">
						<a id="first" href="javascript:void(0);">首页</a>
						<a id="pre" href="javascript:void(0);">上一页</a><SPAN style="color: red;" id="curretPage">
						0</SPAN> /
						<SPAN style="color: red;" id="totalPage">0
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
