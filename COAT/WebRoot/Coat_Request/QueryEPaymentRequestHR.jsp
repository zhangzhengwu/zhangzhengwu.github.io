<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>QueryMarkPremiumOrder</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="./css/Util.js?ver=<%=new Date() %>"></script>
	

  </head>
  <script type="text/javascript" >
  var downs=null;
  var d=null;
  var pagenow=1;
  var totalPage=1;
  var before=null;
  var location_load=null;
  var email=null;
  function resize(){
	  //$("#overs,#divId,#divIdSelected,.span_menu,#Medical").width($("body").width()).css("left",0); 
  }
  
  $(function(){
	  window.onresize=resize;
	  $("#topTable").attr("disabled",true);
  	  var econvoy="${Econvoy}".split("-");
  	
	 getlocation();
     //注册单击事件
 	 var da = new Date();
     //注册单击事件
	 $("#start_date").val(getMonthBeforeDay(da));	
	 $("#end_date").val(getBeforeMonthLastDay(da));	
	
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
		selects_last(1);
	});
 				//注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				selects_last(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				selects_last(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				selects_last(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalPage;
				selects_last(pagenow);
			});

	
	$(".paymentAmount").click(function(){
		if($(".paymentAmount:checked").length>0){
			$(".paymentAmount").attr("checked",false);
			this.checked=true;
		}else{
			this.checked=true;
		}
		$("#amount").val(this.value);
		
	});
	
  });
  /****************************************QueryEpaymentOrder**********************************************************/
  function selects_last(pageNow){
			if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("Start Date can’t be later than End Date.");
				$("#start_date").focus();
				return false;
			}
		}
			$.ajax({
			type:"post",
			url:"EPaymentServlet",
			data: {'method':'selects','start_Date':$("#start_date").val(),
					'end_Date':$("#end_date").val(),
					'clientcode':$("#clientcode").val(),
					'ordercode':$("#ordercode").val(),
					"ordertype":$("#ordertype").val(),
					"location":$("#locationid").val(),
					"clientname":$("#clientname").val(),
					"status":$("#statu").val(),
					'curretPage':pageNow},
			beforeSend: function(){
						parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
			success:function(data){
				//alert(data);
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
						html+="<tr id='select' title='"+i+"'><td align='center'>"+dataRole[0][i].refno+"</td><td  align='left'>"+
						dataRole[0][i].staffname+"</td><td  align='center'>"+dataRole[0][i].staffcode+"</td><td align='left'>"+dataRole[0][i].location+"</td>"
						+"<td align='center'>"+dataRole[0][i].createdate+"</td><td align='center'>"+dataRole[0][i].status+"</td>" +
						" <td  align='center'>" +
						"<c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='Detial("+i+");' name ='1'>Detail</a></c:if>";
						html+="</td></tr>";
					}
					//html+="<tr  id='select'><td colspan='8'><span  style='color:red'><strong>Remark:&nbsp;&nbsp;1.Daily operating time: after 12:00 noon" +
					//"&nbsp;&nbsp;2.‘Submit Date’ is greater than the record after 12 noon today, please try not to operate.</strong></span><strong></td></tr>";
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select'><td colspan='8' align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
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
 var datePrice=null;
  	/******************************************************Click Detial****************************************************************************/	
   function Detial(i){
    	$("#void,#completed").hide();
  		$("#paymentDate,#handled,#saleno,#method").val("");
  		//$("#check_money,#FOC").attr("checked",false);
  		$("tr.aaa").remove();
  		$('#Medical').hide();
    	$("#overs").show(10);
    	$.ajax({
				url: "EPaymentServlet",
				type:"post",
			 	data:{'ordercode':downs[0][i].refno,"method":"detial"},
		  	 	success:function(date){
					var d=eval(date);
					var html_left="";
					$(".product").remove();
					$("#refnos").empty().append(d[0].refno);
					$("#staffcodes").val(d[0].staffcode);
					$("#staffnames").val(d[0].staffname);
					$("#location").val(d[0].location);
					$("#status").empty().append(d[0].status);
					queryDetail(i);  /**---->>查询Detaile**************/
					$("#paymentDate,#handled").val("");
                 	if(downs[0][i].status=="Submitted"){
                 		$("#paymentDate,#handled,#saleno,#method").attr("disabled",false).css("border-bottom-width",1);
                 		//$("#check_money,#FOC").attr("disabled",false);
						$("#completed,#void").attr("disabled",false).show();
						$("#paymentDate").val(getMonthBeforeDay(new Date()));//默认当天
						$("#handled").val("${adminUsername}");
						
                 	}else if(downs[0][i].status=="Completed"){
                 		$("#paymentDate,#handled,#saleno,#method").attr("disabled",true).css("border-bottom-width",0);
                 		//$("#check_money,#FOC").attr("disabled",true);
						$("#completed").attr("disabled",true).show();
						epaymentQuery(i); /**---->>加载ePayment表****/

					}
                	 		
                /** if(parseFloat(("${SystemAcceTime}").substring(11,13))>=12 || downs[0][i].status=="Ready"|| downs[0][i].status=="Completed"){//当前时间超过十二点 	
			 	
				 	}else{
                 		alert("Normal operation time is after 12 o 'clock in the afternoon!");
                 		$("#ready,#completed").attr("disabled",true).hide();
                 		return false;
                 	}*/
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
			});
    }
  	
  	$(function(){
		 $("#method").change(function(){
			if($(this).val()=="FOC"){ 
				 $("#FOC").attr("checked",true);
				 $("#check_money").attr("checked",false);
			}else{
				$("#FOC").attr("checked",false);
				$("#check_money").attr("checked",true);
			}
		 });
  	})
  		 /*****************************************************QueryDetail********************************************************************/
  		 var priceAmount=0;
  	  	 function queryDetail(i){
	 					var priceAll=0;
						$.ajax({
							url:"EPaymentServlet",
							type:"post",
							data:{"method":"queryDetail","refno":downs[0][i].refno},
							success:function(date){
								var dataRoles=eval(date);	
									var htmls="";
								if(dataRoles!=null){
									datePrice=dataRoles;
									$("tr.aaa").remove();
									for(var i=0;i<dataRoles.length;i++){
										htmls+="<tr class='aaa'>"+"<td  align='center' height='18'>"+dataRoles[i].detailid+"</td><td>"+dataRoles[i].refno+"</td><td>"+
										dataRoles[i].productcode+"</td><td >"+dataRoles[i].productname+"</td><td align='center'>"+
										dataRoles[i].price+"</td><td>"+dataRoles[i].quantity+"</td>"+"</tr>";
										priceAll=parseFloat(priceAll)+parseFloat(dataRoles[i].price);
									}
								}else{
									htmls+="<tr class='aaa' class='product' style='color:blue;size=5px' align='center'><b>Sorry, there is no matching record.</b></td></tr>";
								}
									$("#leftTable:last").append(htmls);
									$(".product:odd").css("background","#F0F0F0");
					                $(".product:even").css("background","#COCOCO");
					                $("#span_money").text(" "+priceAll);
					                priceAmount=priceAll;
							},error:function(){
								alert("Network connection is failed, please try later...");
						   		return false;
							}
						});
  	 }
  	
  	  		/****************************************************EpaymentQuery*************************************************************************/								
	/*****************************************************************************************************************************/		
  	function epaymentQuery(i){ 
			$.ajax({
				url:"EPaymentServlet",
				type:"post",
				data:{"method":"queryPayment","refno":downs[0][i].refno},
				success:function(date){
					//alert(date);
					var dataRoles=eval(date);
					if(dataRoles==""){//
						$("#pay_div,#pay_table").attr("disabled",true).hide();
						alert("Obtain payment information abnormalities");
						return false;
					}else{
						$("#pay_div,#pay_table").attr("disabled",true).show();
						$("#method").val(dataRoles[0].paymentMethod);
						if(dataRoles[0].paymentMethod!="FOC"){
							//$("#check_money").select();
							$("#check_money").attr("checked",true);
						}else{
							$("#FOC").attr("checked",true);
						}
						$("#paymentDate").val(dataRoles[0].paymentDate);
						$("#handled").val(dataRoles[0].handleder);
						$("#saleno").val(dataRoles[0].saleno);
					}
				},error:function(){
					alert("Network connection is failed, please try later...");
			   				return false;
				}
			});
  	}
  	  		

  
	  function modify_view(obj){
		   $("#save_"+obj+"").show();
		   $("#button_"+obj+"").hide();
		   $("#input_"+obj+"").addClass("inputNumber_color");
		   $("#save_"+before+"").hide();
		   $("#button_"+before+"").show();
		   $("#input_"+obj+"").addClass("inputNumber");
		   before=obj;
	  }

 
  function page(currt,total){
	 
		if(pagenow<=1){
			$("#first").hide();
			$("#pre").hide();
		}else{
			$("#first").show();
			$("#pre").show();
		}
		if(pagenow>=totalPage){
			$("#end").hide();
			$("#next").hide();
		}else{
			$("#end").show();
			$("#next").show();
		}
	}
  
  
    function hideview(){
    	before=null;
    	$("#overs").hide();
    	$('#Medical').show();
    	selects_last(pagenow);
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
					//$("#locationid").val("Y");//默认选择@Convoy
				},error:function(){
					alert("Network connection failure, please return to the login page to log in!");
					return false;
				}
			});
		}
    	
 /**************************************************Void********************************************************************/
   function VOID(i){
	  if(confirm("Are you sure to VOID?")){// 确认操作？
		  $.ajax({
			 url:"EPaymentServlet",
			 type:"post",
			 data:{"method":"VOID","refno":$("#refnos").html(),"status":"VOID"},
			 success:function(date){
				 alert(date);
				 hideview();
			 },error:function(){
				 alert("Network connection failure, please return to the login page to log in!");
				 return false;
			 }
		  });
	  }
	 
  }
  
  /*******************************************Completed******************************************************************/
  function completed(){
	  if($("#method").val()==""){
    		alert("please select Payment Method!");
    		$("#method").focus();
    		return false;
    	}
    	if($(".paymentAmount:checked").length<1){
    		alert("please check Payment Amount!");
    		return false
    	}
    	if($("#paymentDate").val()==""){
    		alert("please input Payment Date!");
    		$("#paymentDate").focus();
    		return false;
    	}
    	if($("#handled").val()==""){
    		alert("please input Handled!");
    		$("#handled").focus();
    		return false;
    	}
    	var paymentAmount = "";
    	/***var FOC = document.getElementById("FOC").checked;
	  	if(FOC == true){
	  		paymentAmount = 0;
	  	}else{
	  		paymentAmount = priceAmount;
	  	}**/
	  	paymentAmount = priceAmount;
	  if(confirm("Are you sure to Completed?")){	// 确认操作？
		    $.ajax({
			 url:"EPaymentServlet",
			 type:"post",
			 data:{"method":"completed","type":"epayment","refno":$("#refnos").html(),"status":"Completed","saleno":$("#saleno").val(),"staffname":$("#staffnames").val(),"location":$("#location").val(),
		    	"staffcode":$("#staffcodes").val(),"paymethod":$("#method").val(),"payamount":paymentAmount,"payDate":$("#paymentDate").val(),"handle":$("#handled").val()},
			 success:function(date){
				 alert(date);
				 hideview();
			 },error:function(){
				 alert("Network connection failure, please return to the login page to log in!");
				 return false;
			 }
		  });
	  }
  }
  
    //数据导出
    function  export1(){
      var date1=$("#start_date").val();
      var date2=$("#end_date").val();
      var staffcode=$("#clientcode").val();
      var refno=$("#ordercode").val();
      var staffname=$("#clientname").val();
      var status=$("#status").val();
      var ordertype=$("#ordertype").val();
      var location=$("#locationid").val();
      var method="down";
      window.location.href="<%=basePath%>"+"EPaymentServlet?location="+location+"&ordertype="+ordertype+"&start_Date="+date1+"&end_Date="+date2+"&clientcode="+staffcode+"&ordercode="+"&clientname="+staffname+"&status="+status+"&method="+method;
    }
  
</script>
<body>
<div class="cont-info" id="Medical">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it"  onClick="return Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">User Code：</td>
				<td class="tagCont">
					<input type="text" name="clientcode" id="clientcode">
				</td>
				<td class="tagName">User Name：</td>
				<td class="tagCont">
					<input type="text" name="clientname" id="clientname">
				</td>
			</tr>
			<tr>
				<td class="tagName">Order Code：</td>
				<td class="tagCont">
					<input type="text" name="ordercode" id="ordercode">
				</td>
				<td class="tagName">Status：Status：</td>
				<td class="tagCont">
					<input type="hidden" name="productname" id="productname">
	              	<select id="statu">
			            <option value="">please select Status</option>
			            <option >Submitted</option>
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
				<c:if test='${roleObj.search==1}'>
					<a class="btn" id="searchs" name="search">
						<i class="icon-search"></i>
						Search
					</a>
				</c:if>
				<c:if test='${roleObj.export==1}'>
					<a class="btn" id="exp" onclick="export1()">
						<i class="icon-search"></i>
						Export
					</a>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_15">Order Code</th>
				<th class="width_15">Staff Name</th>
				<th class="width_15">Staff Code</th>
				<th class="width_15">Location</th>
				<th class="width_15">Submit Date</th>
				<th class="width_5">Status</th>
				<th class="width_10">Details</th>
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
		<h4>E-Payment Request</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Ref.number</td>
				<td class="tagCont">
					<span id="refnos">系統自動獲取的</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Code</td>
				<td class="tagCont">
					<input type="text" id="staffcodes" disabled="disabled" style="border-width:0px"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Name</td>
				<td class="tagCont">
					<input type="text" id="staffnames" disabled="disabled"  style="border-width:0px" />
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont">
					<input id="location" disabled="disabled"  type="text"  style="border-width:0px" />
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
				<td class="width_5">ID</td>
				<td class="width_15">Order Code</td>
				<td class="width_15">Product Code</td>
				<td class="width_30">Product English Name</td>
				<td class="width_20" id="prices">Price</td>
				<td class="width_10">Quantity</td>
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
		             	<option>EPS</option>
					</select>
				    <input type="text"  id="saleno">
				</td>
			</tr>
			<tr>
				<td class="tagName">Payment Amount</td>
				<td class="tagCont">
					<input type="hidden" id="amount"/>
					<label class="inline checkbox">
						<input type="checkbox" checked="checked"  name="paymentAmount" value="20" id="check_money"  class="paymentAmount"/> HKD
						<span id="span_money"></span>
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
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="completed" name ="bbb" onClick="completed();">Completed</button>
	</c:if>
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="void" name ="bbb" onClick="VOID();">Void</button>
	</c:if>
		<button class="btn" onClick="hideview();">Cancel</button>
	</div>
</div>
</body>
</html>
