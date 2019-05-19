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
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="<%=basePath%>/css/layout.css">
	<link rel="stylesheet" href="<%=basePath%>/plug/css/site.css">
	<script src="<%=basePath%>/css/date.js" language="JavaScript"></script>
	<script src="<%=basePath%>/css/Util.js" language="JavaScript"></script>
	<script type="text/javascript" src="<%=basePath%>/plug/lhgdialog/lhgdialog.min.js"></script>
 	<script type="text/javascript">

		
	 var basepath = '<%=basePath%>';
 	 var pagenow =1;
	 var totalpage=1;
	 var total=0;

/*****************************************************WindowForm Load *************************************/
 $(function(){
	 	
	$("#start_date").val(CurentTime());	
	$("#end_date").val(CurentTime());	

/****************************************************Search click***********************************/
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

function select(pagenow){

				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("開始日期不能大於結束日期!");
						$("#start_date").focus();
						return false;
					}
				}

				$.ajax({
				type: "post",
				url:basepath+"/loginrecord/LoginRecordServlet",
				data: {"method":"select",'username':$("#username").val(),'platform':$("#platform").val(),
					'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),
					'ipaddress':$("#ipaddress").val(),'status':$("#status").val(),
					'pagenow':pagenow},
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

					if(dataRole[3]>0){
						total=dataRole[3];
						pagenow =dataRole[2];
					    totalpage=dataRole[1];
						for(var i=0;i<dataRole[0].length;i++){					
							html+="<tr id='select' title='"+i+"'><td align='center'>"+((i+1)+(dataRole[2]-1)*dataRole[4])+"</td><td align='center'>"+dataRole[0][i].username+"</td><td align='center'>"
							+dataRole[0][i].platform +"</td><td align='center'>"+dataRole[0][i].operation +"</td><td align='center'>"
							+dataRole[0][i].ipaddress +"</td><td align='center'>"+dataRole[0][i].date +"</td><td align='center'>"
							+dataRole[0][i].remark1
							+"</td></tr>";		 
						}
			                $(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}
					else{
						html+="<tr id='select'><td colspan='19' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
					 $(".page_and_btn").hide();
					}
						 $("#jqajax:last").append(html);
					 	 $("tr[id='select']:even").css("background","#COCOCO");
		                 $("tr[id='select']:odd").css("background","#F0F0F0");
		                 	 page(pagenow,totalpage);
				 }
			 });
				
		}

/***********************************************************Search Click end************************/
	function CurentTime()
    { 
        var now = new Date();
       
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        var clock = year + "-";
         if(month < 10)
            clock += "0";
        clock += month + "-";
        if(day < 10)
           clock += "0";
        clock += day + "";
        return clock; 
    } 
   

/*****************************************表单提交***************************/
	
$("body").keydown(function(e){
	if(e.keyCode==13){
	select(1);
	}
	});
/*****************************表单结束********************************/	

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
					$("#end").hide();
					$("#next").hide();
				}else{
					$("#end").show();
					$("#next").show();
				}
	}

 </script>

  
</head>
<body  >
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input id="start_date" type="text" readonly="readonly" onClick="return Calendar('start_date')" />&nbsp;&nbsp;
                  	<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it" readonly="readonly" onClick="return Calendar('end_date')" />&nbsp;&nbsp;
                	<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">UserName：</td>
				<td class="tagCont">
					<input type="text" name="username" id="username">
				</td>
				<td class="tagName">PlatForm：</td>
				<td class="tagCont">
					<input type="text" name="platform" id="platform">
				</td>
			</tr>
			<tr>
				<td class="tagName">Ip Address：</td>
				<td class="tagCont">
					<input type="text" name="ipaddress" id="ipaddress">
					<input type="hidden" name="status" id="status" value="Y" >
				</td>
				<td class="tagCont" colspan="2">
					<a class="btn" id="search">
						<i class="icon-search"></i>
						Search
					</a>
				</td>
			</tr>

		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead>
			<tr id="title">
				<th class="width_5">Id</th>
				<th class="width_5">UserName</th>
				<th class="width_10">PlatForm</th>
				<th class="width_5">Operation</th>
				<th class="width_15">Ip Address</th>
				<th class="width_10">Date</th>
				<th class="width_5">Remark1</th>

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