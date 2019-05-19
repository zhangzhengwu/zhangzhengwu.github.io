<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
if(session.getAttribute("convoy_username")==null || session.getAttribute("convoy_username")==""){
	out.println("<script>parent.location.href='../error.jsp';</script>");
}
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>QueryMedicalConsultant</title>
 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">

	<meta http-equiv="description" content="This is my page">  
	
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">

  </head>
  <script type="text/javascript" >
  var downs=null;
     var pagenow =1;
  var totalpage=1;
  var total=0;
  window.onresize=popupDiv;
  function popupDiv(){
   $("#content").css("top",$("#Medical").height());
	 $("#content,#Medical").width($("body").width());
	 }
  $(function(){
	 $("#staffcodes").val("${convoy_username}");//香港Loging code
     $("#content").css("top",$("#Medical").height());
	 $("#content,#Medical").width($("body").width());
 	 $("#start_date").val((new Date()).getFullYear()+"-01-01");
     $("#end_date").val((new Date()).getFullYear()+"-12-31");
/*********************************改变时间格式*********************************************/
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
	 

/*****************************************************Down click**********************************/
	
	$("#down").click(function(){ 
		//if(downs!=null){
			window.location.href="../DownForConsServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val()+"&econvoy=hk_econvoy";
	/**	}
		else{
			alert("请先查询数据，再做导出相关操作！");
		}**/
	});
	/******************************************************Down END***********************************/
	
	/**************************************************selects Click*****************************************************/
	$("#searchs").click(function(){
		//style="color: #004A81;border: 2px solid #004A81" 
		selects(pagenow);
	});
/******************************************************************************************************************/
 

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
/***********************************************select function************************************/
	function selects(pagenow){
			/**	if($("#start_date").val()==""){
					alert("Start Date can’t be blank.");
					$("#start_date").focus();
					return false;
				}	
				if($("#end_date").val()==""){
					alert("End Date can’t be blank.");
					$("#end_date").focus();
					return false;
				}
				 **/
				
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("  Start Date can’t be later than End Date.");
						$("#start_date").focus();
						return false;
					}
				}
		 //$("#staffcodes").val()
				$.ajax({
				type: "post",
				url:"QueryMedicalConsultantServlet",
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'code':parent.staffcode,'name':$("#name").val(),"pageNow":pagenow,"econvoy":"hk_econvoy"},
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
					downs=dataRole[0];
					 
					 var numbers=0;
						for(var i=0;i<dataRole[0].length;i++){
							if(dataRole[0][i].SP_type=="S"){
								numbers++;
								}
							html+="<tr id='select' title='"+i+"'><td height='23' align='center'>"+dataRole[0][i].staffcode+"</td><td align='center'>"
							+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].medical_date
							+"</td><td align='center'>"+dataRole[0][i].medical_Fee+"</td><td align='center'>"+dataRole[0][i].entitled_Fee+"</td><td align='center'>"+dataRole[0][i].terms_year+"</td><td align='center'>"
							+dataRole[0][i].upd_Date.substring(0,4)+"-"+dataRole[0][i].medical_month +"</td><td align='center'>"+(dataRole[0][i].SP_type=='S'?'SP':'GP') +"</td><td align='center'>"+dataRole[0][i].upd_Date.substring(0,10)+"</td></tr>";		 
						}
						html+="<tr id='select' title='"+i+"'><td colspan='7' style='text-align: right; padding-right: 20px;'>Normal:</td><td align='center'>"+(i-numbers)+"</td><td></td>";
						html+="<tr id='select' title='"+i+"'><td colspan='7' style='text-align: right; padding-right: 20px;'>Special:</td><td align='center'>"+numbers+"</td><td></td>";
						html+="<tr id='select' ><td  align='right' colspan='9'><span style='color:red'>Remark: &nbsp;&nbsp;1.“No. of Term” is only including the data from 2013. &nbsp;&nbsp;2.“GP” is General Practitioner, “SP” is Specialist Practitioner.<span></td></tr>";
						
							$(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}
					else{
						html+="<tr id='select'><td colspan='18' align='center'>"+" Sorry, there is no matching record."+"</td></tr>";
					}
						 $("#jqajax:last").append(html);
					 	 $("tr[id='select']:even").css("background","#COCOCO");
		                 $("tr[id='select']:odd").css("background","#F0F0F0");
		                  page(pagenow,totalpage);
		                  
				 }
			 });
	}
	/**************************************************************************************************/
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
<body>
<div class="cont-info" id="Medical">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" readonly="readonly" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it" readonly="readonly"  onClick="return Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
					<a class="btn" id="searchs" name="search">
						<i class="icon-search"></i>
						Search
					</a>
					<a class="btn" id="down" name="downs">
						<i class="icon-download"></i>
						Export
					</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_">Staff Code</th>
				<th class="width_">Name</th>
				<th class="width_">Medical Date</th>
				<th class="width_">Consultaing Fee</th>
				<th class="width_">Claimed Amount</th>
				<th class="width_">No of Terms</th>
				<th class="width_">Payer Month</th>
				<th class="width_">Claim Type</th>
				<th class="width_">Submit Date</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;" >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">First Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a> Total
						<SPAN style="color: red;" id="total">
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>
