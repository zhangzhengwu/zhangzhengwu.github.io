<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>trainee</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="./css/style.css">
    <style type="text/css">
<!--
#Medical {
	width:100%;
	height:99%;
	z-index:-1;
	left: 0px;
	top: 0px;
}
body{
	font-family: Arial Narrow;overflow: auto;width: 100%;
	background-image: url("css/officeAdmin_bg.gif");
}
tr td{
font-size: 16px;
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
.main_head{height:27px;text-align: center;background: url("../images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}
.STYLE1 {
	font-size: 36px;
	font-weight: bold;
	color: #0000FF;
}
-->
    </style>
    <link href="./css/style.css" rel="stylesheet" type="text/css">
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
	 var yesterday_milliseconds=date.getTime()-1000*60*60*24;     
	 var yesterday = new Date();     
	 yesterday.setTime(yesterday_milliseconds);     
	   
	 var strYear = yesterday.getFullYear();  
	 var strDay = yesterday.getDate();  
	 var strMonth = yesterday.getMonth()+1;
	 if(strMonth<10)  
	 {  
	  strMonth="0"+strMonth;  
	 }  
	 datastr = strYear+"-"+strMonth+"-"+strDay;
	 return datastr;
  }
/*
*获取前一个月的最后一天
*date : Date类型.  
*/

function getBeforeMonthLastDay(date){
	 var yesterday_milliseconds=date.getTime()-1000*60*60*24;     
	 var yesterday = new Date();     
	 yesterday.setTime(yesterday_milliseconds);     
	   
	 var strYear = yesterday.getFullYear();  
	 var strDay = yesterday.getDate();  
	 var strMonth = yesterday.getMonth()+1;
	 if(strMonth<10)  
	 {  
	  strMonth="0"+strMonth;  
	 }  
	 datastr = strYear+"-"+strMonth+"-"+strDay;
	 return datastr;
}
/*****************************************************Down click**********************************/
	$("#down").click(function(){ 
		if(downs!=null){
			//window.location.href="../ReportMarkPremiumServlet?start_Date="+$("#start_date").val()+"&end_Date="+$("#end_date").val()+"&ordercode="+$("#ordercodes").val()+"&staffcode="+$("#staffcodes").val();
			$.ajax({
				type: "post",
				url:"ReportMarkPremiumServlet",
				async:false,
				data: {'start_Date':$("#start_date").val(),'end_Date':$("#end_date").val(),'staffcode':$("#staffcodes").val(),'ordercode':$("#ordercodes").val(),'curretPage':curPage},
				success:function(date){
					clipboardData.setData('Text',date.substring(date.indexOf(':')+1));
					alert(date);
				},error:function(){
					alert("导出失败!");
				}
			});
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
		}
	
	});
	
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
			
			$.ajax({
			type: "post",
			url:"QueryMarkPremiumRecordServlet",
			data: {'start_Date':$("#start_date").val(),'end_Date':$("#end_date").val(),'staffcode':$("#staffcodes").val(),'ordercode':$("#ordercodes").val(),'curretPage':curPage},
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
						html+="<tr id='select' title='"+i+"'><td>"+dataRole[0][i].ordercode+"</td><td>"+dataRole[0][i].clientname+"</td><td>"+
						dataRole[0][i].clientcode+"</td><td>"+dataRole[0][i].price+"</td><td>"+
						dataRole[0][i].quantity+"</td><td>"+dataRole[0][i].priceall+"</td><td>"+
						dataRole[0][i].location+"</td>  <td>"+dataRole[0][i].orderdate+"</td></tr>";
					}
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select'><td colspan='18' style='color:blue;size=5px' align='center'><b>"+"對不起，沒有您想要的數據!"+"</b></td></tr>";
				}	 page(dataRole[1],dataRole[2]);
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[1]);
					 $("#totalPage").empty().append(dataRole[2]);
				 	 $("tr[id='select']:odd").css("background","#COCOCO");
	                 $("tr[id='select']:even").css("background","#F0F0F0");
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
  <br>
  <div id="Medical">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-ss">
            <tr>
              <td height="21" colspan="4" align="left"><div align="center" class="STYLE1">Query Marketing Premium Order For HR<br>
              </div></td>
            </tr>
            <tr>
              <td width="50%" height="32" align="left">開 始 日 期 ：
              <input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" />              </td>
              <td width="50%" height="32" align="left"> 結 束 日 期：
              <input id="end_date" type="text" name="it" onClick="return Calendar('end_date')" /></td>
              <td width="63" align="left" style="color:red"></td>
              <td width="178">&nbsp;</td>
            </tr>
            <tr>
              <td height="23" align="left">Staff Code ：
              <input type="text" name="staffcode" id="staffcodes"></td>
              <td height="23" align="left">Order Code ：
              <input type="text" name="ordercode" id="ordercodes"></td>
              <td align="left" style="color:red"></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              
              <td colspan="4" align="left" style="color:red"> </td>
            </tr>
           
            <tr>
              <td colspan="2" align="right"></td><td><input id="searchs" name="search" type="submit" value="查 詢" align="right"/>              </td>
              <td width="178" ><input id="down" name="downs" type="button" value="導 出">              </td>
            </tr>
    </table>
    <table width="100%" border="1" cellpadding="0" cellspacing="0" class="table-ss" id="jqajax" >
      
      <tr id="title" >
        <td  align="center"><b><Strong>Order Code</strong></b></td> 
        <td  align="center"><strong>Staff Name</strong><br></td> 
        <td  align="center"><strong>Staff Code</strong><br></td>
        <td  align="center"><strong>Price</strong><br></td>
        <td  align="center"><strong>Quantity</strong><br></td>
        <td  align="center"><strong>Price Total</strong><br></td>
        <td  align="center"><strong>Location</strong><br></td>
        <td  align="center"><strong>Submit Date</strong><br></td>
         
      </tr>
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
  </body>
</html>
