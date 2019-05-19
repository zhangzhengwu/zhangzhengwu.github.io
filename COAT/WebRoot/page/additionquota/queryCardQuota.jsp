<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>查詢頁面</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		
		<style type="text/css">
<!--
#Layer1 {
	position: absolute;
	width: 100%;
	height: 100%;
	z-index: 1;
}

#login {
	position:absolute;
	width:165px;
	height:71px;
	z-index:100;
	left: 525px;
	top: 129px;
	border-color:#99CCCC;
display:none;
	

}.page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}
.STYLE1 {font-size: 10px}
.STYLE3 {font-size: 16px;
font-family:"仿宋"}
-->
</style>
		<script type="text/javascript">
 						/**************************窗体加载事件*************************/
 $(function(){
 var down=null;
 		var pagenow =1;
		var totalpage=1;
		var total=0;
 $("#down").attr("disabled","disabled");
selects(1);
/*************************************Query Start*************************************************/

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
	$("#select").bind("click", function() {//尾页
	
				selects(1);
			});
function selects(pagenow){
	 $.ajax({
		 type:"post",
		 url:"<%=basePath%>additionquota/AdditionalQuotaServlet",
		 data:{"staffcode":$("#staffcode").val(),"staffname":$("#staffname").val(),"pagenow":pagenow,"method":"queryQuotaChecking"},
		 beforeSend: function(){
			 parent.showLoad();
		 },
		 complete: function(){
			 parent.closeLoad();
		 },
		 success:function(data){
			 $("#down").removeAttr("disabled");
		  	 $("tr[id='select']").remove();
		var dataRole=eval(data);
		var html="";
		var adddate="";
				if(dataRole[3]>0){
						total=dataRole[3];
						pagenow =dataRole[2];
					    totalpage=dataRole[1];
					    down=dataRole[0];
						for(var i=0;i<dataRole[0].length;i++){
							if(dataRole[0][i].add_date==null){
					    		adddate="";
					    	}else{
					    		adddate=dataRole[0][i].add_date;
					    	} 
							html+="<tr id='select'><td align='center'>"+dataRole[0][i].employeeId+"</td><td  >"+dataRole[0][i].EmployeeName
							 +"</td><td align='center'>"+dataRole[0][i].C_Name +"</td><td align='center'>"+dataRole[0][i].eqnum
							  +"</td><td align='center'>"+dataRole[0][i].addnum
							 +"</td><td align='center'>"+(parseInt(dataRole[0][i].addnum)+parseInt(dataRole[0][i].eqnum))+"</td><td align='center'>"+dataRole[0][i].used
							  +"</td><td align='center'>"+dataRole[0][i].selfpay +"</td><td align='center'>"+dataRole[0][i].balance
							  +"</td><td align='center'>"+adddate
							  +"</td></tr>";		 
							}
						  $(".page_and_btn").show();
						  $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
						}else{
							 html+="<tr id='select'><td colspan='10' align='center'>"+"對不起，沒有您想要的數據!"+"</td></tr>";
							 $(".page_and_btn").hide();	
						}
							 $("#jqajax:last").append(html);
							 $("tr[id='select']:even").css("background","#COCOCO");
			                 $("tr[id='select']:odd").css("background","#F0F0F0");
			                 page(pagenow,totalpage);
		 }
	 });
 }
 /*************************************Query END*************************************************/
 /*************************************Down Start*************************************************/
 $("#down").click(function(){
	 if(down!=null){
		window.location.href="<%=basePath%>additionquota/AdditionalQuotaServlet?staffcode="+$("#staffcode").val()
		                      +"&staffname="+$("#staffname").val()+"&method=down";
	 }else{
	 	alert("對不起，暫時沒有任何數據需要導出！");
	 }
 });
  /*************************************Down END*************************************************/
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
<body>
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Staff Code:</td>
				<td class="tagCont">
					<input id="staffcode" type="text"/>
				</td>
				<td class="tagName">English Name:</td>
				<td class="tagCont">
					<input id="staffname" type="text"/>
				</td>
			</tr>
			<tr>
				<td class="tagCont" colspan="2"></td>
				<td class="tagCont" colspan="2">
					<c:if test="${roleObj.search==1}">
						<a class="btn" id="select" name="select"><i class="icon-search"></i>Search</a>
					</c:if>
					<c:if test="${roleObj.export==1}">
						<a class="btn" id="down" name="dowm"><i class="icon-export"></i>Export</a>
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead>
			<tr id="title">
			
				<th class="width_8">Staff Code</th>
				<th class="width_20">English Name</th>
				<th class="width_10">Name</th>
				<th class="width_10">Entitled Quota</th>
				<th class="width_10">Additional</th>
				<th class="width_10">Total_Quota</th>
				<th class="width_8">Quota_Used</th>
				<th class="width_8">Self_Paid</th>
				<th class="width_8">Balance</th>
				<th class="width_10">ADD_DATE</th>
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