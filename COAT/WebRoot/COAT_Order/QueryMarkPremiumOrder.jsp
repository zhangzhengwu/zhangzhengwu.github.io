<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>QueryMarkPremiumOrder</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<script type="text/javascript" src="./css/Util.js"></script>
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
  var d=null;
  var curPage=1;
  var totalPage=1;
  var location_load=null;
  
  function resize(){
	  //$("#overs, .span_menu,#Medical").css("width",$("body").width()).css("top",0); 
  }
  function getlocation(){
		$.ajax({
			type:"post",
			//url:"QueryLocationServlet",
			//data:null,
			url: "common/CommonReaderServlet",
			data:{"method":"getlocation"},
			success:function(date){
			 location_load=eval(date);
			 	var html="";
					$("#locationid").empty();
					if(location_load.length>0){
						html+="<option value='' >Please Select Location</option>";
						for(var i=0;i<location_load.length;i++){
							html+="<option value='"+location_load[i].realName+"' >"+location_load[i].name+"</option>";
						}
					}else{
						html+="<option value=''>loading error</option>";
					}
					$("#locationid").append(html);
			},error:function(){
				alert(" Network connection is failed, please try later...");//网络连接失败
				return false;
			}
		});
	}
  $(function(){
	   window.onresize=resize;
	   $("#topTable,#pay_table *").attr("disabled",true);
	   // $("#overs,#divId,#divIdSelected,.span_menu,#Medical").css("width",$("body").width()).css("top",0);
 	   var econvoy="${Econvoy}".split("-");
	   //  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
	   $("#clientcode").val(econvoy[0]);
     //注册单击事件
     //注册单击事件
	$("#start_date").val(getMonthBeforeDay(new Date()));	
	$("#end_date").val(getBeforeMonthLastDay(new Date));
	getlocation();
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
		
		 var yesterday_milliseconds=date.getTime();  //-1000*60*60*24   
		 var yesterday = new Date();     
	     yesterday.setTime(yesterday_milliseconds);     
	   
		 var strYear = yesterday.getFullYear();  
		 var strDay = yesterday.getDate();  
		 var strMonth = yesterday.getMonth()+1;
		 if(strMonth<10)  
		 {  
		  strMonth="0"+strMonth;  
		 }  
		 if(strDay<10)  
		 {  
		  strDay="0"+strDay;  
		 }  
		 
		 datastr = strYear+"-"+strMonth+"-"+strDay;
		 return datastr;
	}
	/*
	*获取前一个月的最后一天
	*date : Date类型.  
	*/
	
	function getBeforeMonthLastDay(date){
		 var yesterday_milliseconds=date.getTime();     //-1000*60*60*24
		 var yesterday = new Date();     
	     yesterday.setTime(yesterday_milliseconds);     
		 var strYear = yesterday.getFullYear();  
		 var strDay = yesterday.getDate();  
		 var strMonth = yesterday.getMonth()+1;
		 if(strMonth<10)  
		 {  
		  strMonth="0"+strMonth;  
		 }  
		  if(strDay<10)  
		 {  
		  strDay="0"+strDay;  
		 }  
		 datastr = strYear+"-"+strMonth+"-"+strDay;
		 return datastr;
	}
 	/******************************************selects Click*****************************************************/
	$("#searchs").click(function(){
		selects();
	});
	/***********************************************select function************************************/
	function selects(){
		if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("Start Date can’t be later than End Date.");
				$("#start_date").focus();
				return false;
			}
		}
		//alert('start_Date'+$("#start_date").val()+'end_Date'+$("#end_date").val()+'staffcode'+$("#clientcode").val()+'ordercode'+$("#ordercodes").val());
			 
		$.ajax({
			type: "post",
			url:"MarketingPremiumServlet",
			data: {'method':'select',
					'start_Date':$("#start_date").val(),
					'end_Date':$("#end_date").val(),
					'clientcode':$("#clientcode").val(),
					'ordercode':$("#ordercodes").val(),
					"ordertype":$("#ordertype").val(),
					"location":$("#locationid").val(),
					"clientname":$("#clientname").val(),
					"status":$("#statu").val(),
					'curretPage':curPage},
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
				if(dataRole[3]>0){
					downs=dataRole;
					$(".page_and_btn").show();
					curPage=dataRole[2];
	 			   	totalPage=dataRole[1];
	 			 
	 			   	var location_marketing=null;
					for(var i=0;i<dataRole[0].length;i++){
							for(var j=0;j<location_load.length;j++){
								 if(location_load[j].realName==dataRole[0][i].collectionlocation){
									 location_marketing=location_load[j].name;
									 j=location_load.length;
								 }
							}
						html+="<tr id='select' title='"+i+"'><td align='center'>"+
						dataRole[0][i].ordercode+"</td><td  align='center'>"+
						dataRole[0][i].clientname+"</td><td  align='center'>"+
						dataRole[0][i].clientcode+"</td><td align='center'>"+
						location_marketing+"</td><td align='center'>HK$ "+
						dataRole[0][i].priceall+"</td>  <td align='center'>"+
						dataRole[0][i].orderdate+"</td><td align='center'>"+dataRole[0][i].status+"</td> <td  align='center'><a href='javascript:void(0);' onclick='Detial("+i+");' name ='1'>Detail</a>";
						if(dataRole[0][i].status=="Submitted"){
							html+="&nbsp;<a href='javascript:void(0);' onclick='del("+i+");' name ='2'>Delete</a>";
						}
							html+="</td></tr>";
					}
					//html+="<tr  id='select'><td colspan='8'><span  style='color:red'>Remark:&nbsp;&nbsp;1.Daily Cut-Off time : 12:00noon." +
					//"&nbsp;&nbsp;2.“Submit Date” is less than today's record has been freenzer and can't be deleted.</span></td></tr>";
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select'><td colspan='8' style='size=5px' align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
				}	 page(dataRole[2],dataRole[1]);
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[2]);
					 $("#totalPage").empty().append(dataRole[1]);
				 	 $("tr[id='select']:odd").css("background","#F0F0F0");
	                 $("tr[id='select']:even").css("background","#COCOCO");
			 },error:function(){
					alert("Network connection is failed, please try later...");
			   				return false;
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
  
    function Detial(i){
		$("#staffcodes,#staffnames,#location,#paymentDate,#handled,#method").val("");
		$("[name='paymentAmount']:checkbox").attr("checked",false);
  		$('#Medical').hide();
		$("#overs").show();
    	$.ajax({
				url: "QueryMarkPremiumForUpdServlet",
				type:"post",
			 	data:{'ordercode':downs[0][i].ordercode},
		  	 	success:function(date){
					var dd=eval(date);
					var html_left="";
					d=eval(dd);
			
					$(".product").remove();
					for(var k=0; k< d[0].length; k++){
						var num = k+1;
						html_left+="<tr id='left"+k+"' class='product'><td >"+
							num+"</td><td align='left'>"+
							d[0][k].ordercode+"</td><td  align='left'>"+
							d[0][k].procode+"</td><td  align='left'>&nbsp; "+
							d[0][k].proname+"</td><td>"+d[0][k].calculation+"</td><td>HK$ "+
							d[0][k].price+"</td><td>"+
							d[0][k].quantity+"</td><td>HK$ "+
							d[0][k].priceall+"</td></tr>";
						 
					}
				$("#refnos").empty().append(downs[0][i].ordercode);
				$("#staffcodes").val(downs[0][i].clientcode);
				$("#staffnames").val(downs[0][i].clientname);
				$("#status").empty().append(downs[0][i].status);
			
				for(var j=0;j<location_load.length;j++){
					 if(location_load[j].realName==downs[0][i].collectionlocation){
							$("#location").val(location_load[j].name);
							 j=location_load.length;
					 }
					}

        	 	$("#check_money").val(downs[0][i].priceall);
         		$("#span_money").empty().append(downs[0][i].priceall);
				if(downs[0][i].status=="Completed"){
					
					$.ajax({
							url:"PaymentServlet",
							type:"post",
							data:{"method":"select","refno":downs[0][i].ordercode},
							success:function(date){
								var dataRoles=eval(date);
								if(dataRoles==""){//
									$("#pay_div,#pay_table").hide();
									alert("Obtain payment information abnormalities");
									return false;
								}else{
									$("#pay_div,#pay_table").show();
									$("#method").val(dataRoles[0].paymentMethod);
									$("[name='paymentAmount']:checkbox").each(function(){
										if(this.value==dataRoles[0].paymentAount){
											this.checked=true;
											return false;
										}
									});
									$("#paymentDate").val(dataRoles[0].paymentDate);
									$("#handled").val(dataRoles[0].handleder);
								}
							},error:function(){
								alert("Network connection is failed, please try later...");
	  			   				return false;
							}
						});
				}else{
					$("#pay_div,#pay_table").hide();
				}
				
				
				 $("#leftTable").append(html_left);
				 $(".product:odd").css("background","#F0F0F0");
                 $(".product:even").css("background","#COCOCO");	
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
			});
    }
    function hideview(){
    	$("#overs").hide();
    	$('#Medical').show();
    }
    function del(i){
    	/**if(comptime(downs[0][i].orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1 && parseFloat(("${SystemAcceTime}").substring(11,13))<12){
    		
    	}else if(comptime(downs[0][i].orderdate,"${SystemAcceTime}".substring(0,10)+" 12:00:00")==1 && parseFloat(("${SystemAcceTime}").substring(11,13))>=12){
    		
    	}else{
			alert("Daily Cut-Off time : 12:00noon");
			selects();
			return false;
		}**/
	  if(confirm("Sure to delete?")){
		  $.ajax({
			 url:"MarketingPremiumServlet",
			 type:"post",
			 data:{"method":"delete",'ordercode':downs[0][i].ordercode},
		  	 success:function(date){
				 if(date=="success"){
				 	$("#searchs").click();
				 alert("Success!");
					
				 }if(date=="error"){
					 alert("Error!");
					 return false;
				 }
		     },error:function(){
				  alert("Network connection is failed, please try later...");
				  return false;
		     }
		  });
	  }
  }
  </script>
<body>
<div class="cont-info" id="Medical">
	<div class="info-search">
		<table id="topTable">
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it" onClick="return Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Order Code：</td>
				<td class="tagCont">
					<input type="text" name="ordercode" id="ordercodes">
				</td>
				<td class="tagName">Status：</td>
				<td class="tagCont">
					 <input type="hidden" name="productname" id="productname">
		            <select id="statu">
			            <option value="">please select Status</option>
			            <option>Submitted</option>
			            <option>Ready</option>
			            <option>Completed</option>
	              	</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Order Type：</td>
				<td class="tagCont">
					<select name="ordertype" id="ordertype">
			            <option value="">Please Select Order Type</option>
			            <option value="Consultant">Consultant</option>
			            <option value="Staff">Staff</option>
           	 		</select>
				</td>
				<td class="tagName">Location：</td>
				<td class="tagCont">
					<select name="locationid" id="locationid"></select>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
					<a class="btn" id="searchs" name="search">
						<i class="icon-search"></i>Search
					</a>
              		<input type="hidden" name="clientcode" id="clientcode">
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_">Order Code</th>
				<th class="width_">Staff Name</th>
				<th class="width_">Staff Code</th>
				<th class="width_">Location</th>
				<th class="width_">Order Total</th>
				<th class="width_">Submit Date</th>
				<th class="width_">Status</th>
				<th class="width_">Details</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn">
			<table class="table-ss" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="main_head">
					<td colspan="14" align="center">
						<a id="first" href="javascript:void(0);">first Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a><SPAN style="color: red;" id="curretPage">
						</SPAN> /
						<SPAN style="color: red;" id="totalPage">
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<div class="e-container" id="overs" style="display: none;">
	<div class="info-form">
		<h4>Marketing Primium Request</h4>
		<table>
			<tr>
				<td class="tagName">Ref.number</td>
				<td class="tagCont">
					<span id="refnos">系統自動獲取的</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Code</td>
				<td class="tagCont">
					<input type="text" id="staffcodes" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Name</td>
				<td class="tagCont">
					<input type="text" id="staffnames" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont">
					<input id="location" type="text" />
				</td>
			</tr>
			<tr>
				<td class="tagName">Status</td>
				<td class="tagCont">
					<span id="status" style="font-weight: bold;"></span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divIdSelected">
		<h4>Request Option</h4>
		<table id="leftTable">
			<tr>
				<td class="width_">ID</td>
				<td class="width_">Order Code</td>
				<td class="width_">Product Code</td>
				<td class="width_">Product English Name</td>
				<td class="width_">Calculation</td>
				<td class="width_">Price</td>
				<td class="width_">Quantity</td>
				<td class="width_">Total Price</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="pay_div">
		<h4>Payment(for internal use)</h4>
		<table id="pay_table">
			<tr>
				<td class="tagName">Payment Method</td>
				<td class="tagCont">
					<select id="method">
	            		<option value="">please select PaymentMethod</option>
		             	<option>Octopus</option>
	            	</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Payment Amount</td>
				<td class="tagCont">
					<input type="hidden" id="amount"/>
					<label class="inline checkbox">
						<input type="checkbox"  name="paymentAmount" value="20" id="check_money"  class="paymentAmount"/>
						HKD
						<span id="span_money"></span>
					</label>
					<label class="inline checkbox">
						<input type="checkbox" name="paymentAmount" value="0" class="paymentAmount"/>
						FOC
					</label>
				</td>
			</tr>
			<tr>
				<td class="tagName">Payment Date</td>
				<td class="tagCont">
					<input type="text" id="paymentDate" onClick="return Calendar('paymentDate')" />
				</td>
			</tr>
			<tr>
				<td class="tagName">Handled by</td>
				<td class="tagCont">
					<input type="text" id="handled"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
		<button class="btn" name ="aaa" onClick="hideview();">Cancel</button>
	</div>
</div>
</body>
</html>
