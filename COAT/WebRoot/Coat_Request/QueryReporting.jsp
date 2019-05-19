<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Query Reporting</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="css/Util.js"></script>
	<script  type="text/javascript" src="css/date.js" ></script>
	<script  type="text/javascript" src="css/timerbar.js" ></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">
   
  </head>
  <script type="text/javascript" >
  var pagenow=1;
  var totalpage=0;
  $(function(){
	    $("#start_date,#end_date").val(getMonthBeforeDay(new Date()));
	  $(".inputstyle,.field input").attr("disabled",true);
	  
	  
	      	  //注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				select_payment(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				select_payment(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				select_payment(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				select_payment(pagenow);
			});
	
	
	
	
  });
  function select_payment(pageNow){
	  
	  if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("Start Date can’t be later than End Date.");
				$("#start_date").focus();
				return false;
			}
		}
	   $.ajax({
    		  url:"PaymentServlet",
    		  type:"post",
    		  data:{"method":"selectReporting","startDate":$("#start_date").val(),
	    		  	"endDate":$("#end_date").val(),"requestType":$("#requestType").val(),
	    		 	"curretPage":pageNow},
	          beforeSend: function(){
	    		 parent.showLoad();
	          },
	          complete: function(){
	          	parent.closeLoad();
	          },
    		  success:function(date){
    			  var dataRole=eval(date);
    			  var html="";
    			  pagenow=dataRole[2];
    			  totalpage=dataRole[1];
    			  $(".tr_search").remove();
    			  if(dataRole[3]>0){
    				  for(var i=0;i<dataRole[0].length;i++){
    				  	html+="<tr class='tr_search'><td>"+((pagenow-1)*15+(i+1))
    				  		+"</td><td>"+dataRole[0][i].refno
    				  		+"</td><td>"+dataRole[0][i].type
    				  		+"</td><td>"+dataRole[0][i].paymentMethod
    				  		+"</td><td>"+dataRole[0][i].paymentAount
    				  		+"</td><td>"+dataRole[0][i].handleder
    				  		+"</td><td>"+dataRole[0][i].paymentDate
    				  		+"</td><td>"+dataRole[0][i].creator
    				  		+"</td><td>"+dataRole[0][i].createDate
    				  		+"</td></tr>";
    
    				  }
    				  $(".page_and_btn").show();
    				  $("#export,#deductExport").removeAttr("disabled");
    			  }else{
    				  html+="<tr class='tr_search'><td colspan='9' align='center' >Sorry, there is no matching record.</b></td></tr>";
    				  $(".page_and_btn").hide();
    				  $("#export,#deductExport").attr("disabled",true);
    			  }
    			  $("#jqajax").append(html);
    			  page(pagenow,totalpage);

    		  },error:function(){
    			  alert("Network connection is failed, please try later...");
	  			  return false;
    		  }
    	  });
  }
  
    /**
       * 分页活动方法
       * @param {Object} currt
       * @param {Object} total
       */
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
				$("#curretPage").empty().append(currt);
				$("#totalPage").empty().append(total);
	}
      
      /** function down_Reportings(){
    	   if($("#start_date").val()!="" && $("#end_date").val()!=""){
				if($("#start_date").val()>$("#end_date").val()){
					alert("Start Date can’t be later than End Date.");
					$("#start_date").focus();
					return false;
				}
			}
    		   location="<%=basePath%>"+"ReportingServlet?startDate="+$("#start_date").val()
    		                +"&endDate="+$("#end_date").val();
       }**/
       /**
        * 
        *导出所有申请 
        * */
      function down_Reporting(){
    	   if($("#start_date").val()!="" && $("#end_date").val()!=""){
				if($("#start_date").val()>$("#end_date").val()){
					alert("Start Date can’t be later than End Date.");
					$("#start_date").focus();
					return false;
				}
			}
    	  
    		   progressBarInit(1);
    		   location="<%=basePath%>"+"ReportingServlet?startDate="+$("#start_date").val()
    		                +"&endDate="+$("#end_date").val();
       }
       
       
       
       
       
       /**
        * 
        *导出所有payment
        * */
        function down_payment(){
       	   if($(".page_and_btn").is(":hidden")){
    		   alert("Please query data first, and then do export related operation!");
    		   return false;
    	   }else{
    		   progressBarInit(0.2);
    		   location="<%=basePath%>"+"PaymentServlet?method=down&startDate="+$("#start_date").val()
    		                +"&endDate="+$("#end_date").val()+"&requestType="+$("#requestType").val();
   		   }
       }
        /**
         * 导出Deduct
         * @return {TypeName} 
         **/
        function down_deduct(){
       	   if($(".page_and_btn").is(":hidden")){
    		   alert("Please query data first, and then do export related operation!");
    		   return false;
    	   }else{
    		   progressBarInit(0.2);
    		   location="<%=basePath%>"+"ReportDeductServlet?method=down&startDate="+$("#start_date").val()
    		                +"&endDate="+$("#end_date").val()+"&requestType="+$("#requestType").val();
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
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')"  />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text"  onClick="return Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Request Type：</td>
				<td class="tagCont">
					<select id="requestType">
	              		<option value="">Please Select Request Type</option>
	              		<option>MarketingPremium</option>
	              		<option>Stationery</option>
	              		<option>AccessCard</option>
	              		<option>Keys</option>
	              		<option>epayment</option>
	              		<option>Advertisement</option>
	              	</select>
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
					<a class="btn" onclick="select_payment(1);">
						<i class="icon-download"></i>
						Search
					</a>
					<a class="btn" onclick="down_Reporting();">
						<i class="icon-download"></i>
						Export Report
					</a>
					<a class="btn" id="export" disabled="disabled" onclick="down_payment();">
						<i class="icon-download"></i>
						Export Payment
					</a>
					<a class="btn" id="deductExport" disabled="disabled" onclick="down_deduct();">
						<i class="icon-download"></i>
						Export Add/Deduction
					</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title" >
			<tr>
				<th class="width_">Num</th>
				<th class="width_">Ref.No</th>
				<th class="width_">Type</th>
				<th class="width_">paymentMethod</th>
				<th class="width_">paymentAount</th>
				<th class="width_">Handler By</th>
				<th class="width_">PaymentDate</th>
				<th class="width_">Creator</th>
				<th class="width_">Create Date</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;">
			<table class="table-ss" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="main_head">
					<td colspan="14" align="center">
						<a id="first" href="javascript:void(0);">first Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a><SPAN style="color: red;" id="curretPage">
						0</SPAN> /
						<SPAN style="color: red;" id="totalPage">0
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
