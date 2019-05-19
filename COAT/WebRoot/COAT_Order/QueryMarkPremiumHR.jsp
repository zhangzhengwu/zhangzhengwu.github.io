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
	
    <style type="text/css">
<!--
		#firstTable2 .tagName{width: 15%!important;}
		#firstTable2 .tagCont{width: 35%!important;}
#overs {

	display:none;
}
.images {
	width: 16px;
	height: 16px;
	margin-top: 2px;
}
.inputNumber {
	width: 28px!important;
	border: 0px;
}
-->
    </style>
 
  </head>
  <script type="text/javascript" >
  var downs=null;
  var d=null;
  var pagenow=1;
  var totalPage=1;
  var before=null;
  var location_load=null;
  var downs_last=null;
  var email=null;
  var allMoney =0;
  function resize(){
	  $("#divId,#divIdSelected,.span_menu,#Medical").width($("body").width()).css("left",0); 
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
	
	
	
	$("#downall").click(function(){ 
		if(downs!=null){
			window.location.href="<%=basePath%>ReportAllMarkPremiumServlet?start_Date="+$("#start_date").val()+"&end_Date="+$("#end_date").val()+"&ordercode="+$("#ordercode").val()+"&staffcode="+$("#clientcode").val();
		}else{
			alert("请先查询数据，再做导出相关操作！");
		}
	});
	/*****************************************************Down click**********************************/
	$("#down").click(function(){ 
		if(downs!=null){
			//window.location.href="../ReportMarkPremiumServlet?start_Date="+$("#start_date").val()+"&end_Date="+$("#end_date").val()+"&ordercode="+$("#ordercode").val()+"&staffcode="+$("#staffcodes").val();
			$.ajax({
				type: "post",
				url:"ReportMarkPremiumServlet",
				async:false,
				data: {'start_Date':$("#start_date").val(),'end_Date':$("#end_date").val(),'staffcode':$("#clientcode").val(),'ordercode':$("#ordercode").val()},
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
	
	
	
	
	/***********************************************select function************************************/
	function selects(pageNow){
		before=null;
			/**	if($("#start_date").val()==""){
			alert("Start Date can’t be blank.");
			$("#start_date").focus();
			return false;
		}	
		if($("#end_date").val()==""){
			alert("End Date can’t be blank.");
			$("#end_date").focus();
			return false;
		}**/
		if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("Start Date can’t be later than End Date.");
				$("#start_date").focus();
				return false;
			}
		}
			 /// <a href='javascript:void(0);' onclick='del("+i+");' name ='2'>Delete</a>
			$.ajax({
			type: "post",
			url:"QueryMarketingPremiumHRServlet",
			data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'clientcode':$("#clientcode").val(),'ordercode':$("#ordercode").val(),ordertype:$("#ordertype").val(),"productname":$("#productname").val(),"location":$("#locationid").val(),"clientname":$("#clientname").val(),'pageNow':pageNow},
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
					pagenow=dataRole[2];
	 			   	totalPage=dataRole[1];
					for(var i=0;i<dataRole[0].length;i++){
						var caozuo="<span id='span_"+i+"' style='width:100%;' >"+dataRole[0][i].quantity+"</span>";
	 			   		var edit="";
	 			   		var caozuo_bar="<span id='span_"+i+"' class='view' style='width:100%;' onclick='modify_input("+i+");'>"+
						dataRole[0][i].quantity+"</span><span id='id_"+i+"' style='display:none;' class='input'><img src='images/jian.png' class='images' onclick='deleteQuantity(this,"+i+");'/>" +
							"<input  class='inputNumber' maxlength='3' onblur='NumberBlur(this,"+i+");' type='text' id='input_"+i+"' value='"+dataRole[0][i].quantity+"'/>" +
							"<img src='images/jia.png' class='images' onclick='addQuantity(this,"+i+");'/></span>";
							
							caozuo=caozuo_bar;
						    edit="<a href='javascript:void(0);' onclick='modify_input("+i+");' id ='button_"+i+"'>Edit</a>";
							
							/**	if( parseFloat(("${SystemAcceTime}").substring(11,13))<12){//当前时间为上午
									if(comptime(dataRole[0][i].orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1){//submitDate >大于昨天中午12点
										caozuo=caozuo_bar;
										edit="<a href='javascript:void(0);' onclick='modify_input("+i+");' id ='button_"+i+"'>Edit</a>";
									}else{
										edit="Approved";
									}
								}else{//时间为下午
									if(comptime(dataRole[0][i].orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1){
										caozuo=caozuo_bar;
										edit="<a href='javascript:void(0);' onclick='modify_input("+i+");' id ='button_"+i+"'>Edit</a>";
										}else{
											edit="Approved";
										}
									
								}**/
						
						
					
						html+="<tr id='select' ><td  align='center' height='18'>"+((pagenow-1)*15+i+1)+"</td><td >"+
						dataRole[0][i].ordercode+"</td><td  >"+
						dataRole[0][i].procode+"</td><td >"+
						dataRole[0][i].proname+"</td><td align='center'>"+
						dataRole[0][i].calculation+"</td><td align='center' id='price_"+i+"'>"+
						dataRole[0][i].price+"</td>  <td align='center' >"+caozuo+"</td> <td  align='center' id='allprice_"+i+"'>" +
						dataRole[0][i].priceall+"</td><td  align='center'><a href='javascript:void(0);' onclick='Detial("+i+");' name ='1'>Detail</a>&nbsp;&nbsp;"+edit+" <a href='javascript:void(0);' style='display:none;' onclick='save("+i+");' id='save_"+i+"'>Save</a></td></tr>";
						
					
						
					}
						html+="<tr id='select'><td colspan='9' ><span  style='color:blue'><strong>Remark: Please find the details about each status under “Handle” as below.</br></strong></span><strong>&nbsp;HOLD:<span style='color:blue'>This record can be edited after 12:00p.m.</span>";
						html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						html+="<strong>Approved:</strong><span style='color:blue'>This record has been freenzen and can't be edited.</span></br>";
						html+="<strong>&nbsp;&nbsp;&nbsp; Edit:</strong><span style='color:blue'>The quantity can be only decreased..</span>";
						html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						html+="<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Save:</strong><span style='color:blue'>To save the change.</span></strong></td></tr>";
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select'><td colspan='9' align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
				}	 page(dataRole[2],dataRole[1]);
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[2]);
					 $("#totalPage").empty().append(dataRole[1]);
				 	 $("tr[id='select']:odd").css("background","#F0F0F0");
	                 $("tr[id='select']:even").css("background","#COCOCO");
			 }
		 });
			
			
			
	}

	
	
		
	$(".paymentAmount").click(function(){
		if($(".paymentAmount:checked").length>0){
			$(".paymentAmount").attr("checked",false);
			this.checked=true;
		}else{
			this.checked=true;
		}
		$("#amount").val(this.value);
		
	});
	
	
	/**************************************************************************************************/
  });
  
  function selects_last(pageNow){
			if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("Start Date can’t be later than End Date.");
				$("#start_date").focus();
				return false;
			}
		}
			 
			$.ajax({
			type: "post",
			url:"MarketingPremiumServlet",
			data: {'method':'select','start_Date':$("#start_date").val(),
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
						dataRole[0][i].orderdate+"</td><td align='center'>"+dataRole[0][i].status+"</td> <td  align='center'>" +
						"<c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='Detial("+i+");' name ='1'>Detail</a></c:if>";
						//if(comptime(dataRole[0][i].orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1 && parseFloat(("${SystemAcceTime}").substring(11,13))<12){// 大于昨天12点且当前时间小于12点 ： 可以删除
						////	html+="&nbsp;<a href='javascript:void(0);' onclick='del("+i+");' name ='2'>Delete</a>";
						//}else if(comptime(dataRole[0][i].orderdate,"${SystemAcceTime}".substring(0,10)+" 12:00:00")==1 && parseFloat(("${SystemAcceTime}").substring(11,13))>=12){//大于当天12点 且当前时间大于于12点 可删除
						//	html+="&nbsp;<a href='javascript:void(0);' onclick='del("+i+");' name ='2'>Delete</a>";
						//}
							html+="</td></tr>";
					}
					//html+="<tr  id='select'><td colspan='8'><span  style='color:red'><strong>Remark:&nbsp;&nbsp;1.Daily operating time: after 12:00 noon" +
					//"&nbsp;&nbsp;&nbsp;&nbsp;2.‘Submit Date’ is greater than the record after 12 noon today, please try not to operate.</strong></span><strong></td></tr>";
					
					
						//html+="<tr id='select'><td colspan='9' ><span  style='color:blue'><strong>Remark: Please find the details about each status under “Handle” as below.</br></strong></span><strong>&nbsp;HOLD:<span style='color:blue'>This record can be edited after 12:00p.m.</span>";
						//html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						//html+="<strong>Approved:</strong><span style='color:blue'>This record has been freenzen and can't be edited.</span></br>";
						//html+="<strong>&nbsp;&nbsp;&nbsp; Edit:</strong><span style='color:blue'>The quantity can be only decreased..</span>";
						//html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						//html+="<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Save:</strong><span style='color:blue'>To save the change.</span></strong></td></tr>";
					//html+="<tr  id='select'><td colspan='8'><span  style='color:blue'><strong>Remark:</br>&nbsp;&nbsp;1.Daily Cut-Off time : 12:00noon.</br>" +
					//"&nbsp;&nbsp;2.“Submit Date” is less than today's record has been freenzer and can't be deleted.</strong></span><strong></td></tr>";
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select'><td colspan='8' align='center'><b>"+"Sorry, there is no matching record."+"</b></td></tr>";
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
  
  
  
  
   function Detial(i){
	   	$('#lists').hide();
    	$("#overs").show(10);
    	
    	$.ajax({
				url: "QueryMarkPremiumForUpdServlet",
				type:"post",
			 	data:{'ordercode':downs[0][i].ordercode},
		  	 	success:function(date){
					var d=eval(date);
					var html_left="";
					$(".product").remove();
					downs_last=d;
					
					if(d[0].length>0){
							for(var k=0; k< d[0].length; k++){
								var nums = k+1;
								var caozuo="<span id='span_"+k+"' style='width:100%;' >"+d[0][k].quantity+"</span>";
			 			   		var edit="";
			 			   		var caozuo_bar="";
									if(downs[0][i].status!="Completed"){//当状态不为Completed
										caozuo_bar="<span id='span_"+k+"' class='view' style='width:100%;' onclick='modify_input("+k+");'>"+
												d[0][k].quantity+"</span><span id='id_"+k+"' style='display:none;' class='input'><img src='images/jian.png' class='images' onclick='deleteQuantity(this,"+k+");'/>" +
												"<input  class='inputNumber' maxlength='3' onblur='NumberBlur(this,"+k+");' type='text' id='input_"+k+"' value='"+d[0][k].quantity+"'/>" +
												"<img src='images/jia.png' class='images' onclick='addQuantity(this,"+k+");'/></span>";
												
												caozuo=caozuo_bar;
												edit="<a href='javascript:void(0);' onclick='modify_input("+k+");' id ='button_"+k+"'>Edit</a>";
									/**if( parseFloat(("${SystemAcceTime}").substring(11,13))<12){//当前时间为上午
											if(comptime(downs[0][i].orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1){//submitDate >大于昨天中午12点
												caozuo=caozuo_bar;
												edit="<a href='javascript:void(0);' onclick='modify_input("+k+");' id ='button_"+k+"'>Edit</a>";
											}else{
												edit="";
											}
										}else{//时间为下午
											if(comptime(downs[0][i].orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1){
												caozuo=caozuo_bar;
												edit="<a href='javascript:void(0);' onclick='modify_input("+k+");' id ='button_"+k+"'>Edit</a>";
												}else{
													edit="";
												}
											
										}**/
								}
								
								html_left+="<tr id='select' id='left"+k+"' class='product'>" +
								"<td  align='center' height='18'>"+nums+"</td><td >"+
								d[0][k].ordercode+"</td><td  >"+
								d[0][k].procode+"</td><td >"+
								d[0][k].proname+"</td><td align='center'>"+
								d[0][k].calculation+"</td><td align='center' id='price_"+k+"'>"+
								d[0][k].price+"</td>  <td align='center' >"+caozuo+"</td> <td  align='center' id='allprice_"+k+"'>" +
								
								d[0][k].priceall+"</td><td  align='center'>" +
								 
								"&nbsp;&nbsp;"+edit+" <a href='javascript:void(0);' style='display:none;' onclick='save("+k+","+i+");' id='save_"+k+"'>Save</a></td></tr>";
								 
							}
					}else{
						html_left="<tr id='select' class='product' align='center'>Sorry, there is no matching record.</td></tr>";
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
				
				 $("#leftTable").append(html_left);
				 $(".product:odd").css("background","#F0F0F0");
                 $(".product:even").css("background","#COCOCO");
                	 		allMoney=downs[0][i].priceall;
                	 		$("#moneyId").val(downs[0][i].priceall);
                 			$("#spanMoeny").empty().append(downs[0][i].priceall);
		                 	if(downs[0][i].status=="Submitted"){
		                 		email=getEmail(downs[0][i].clientcode,downs[0][i].ordertype);	
		                 		$("#ready,#void").show();
								$("#completed").hide();
								$("#pay_div,#pay_table").hide();
								$("#firstTable2,#firstTable,.detailTitle").hide();	//--<<
		                 	}
							else if(downs[0][i].status=="Ready"){
								$("#moneyId1,#saleno1,#paySelectId1,#moneyId2,#saleno2,#paySelectId2").attr("disabled",false).val("");
								$("#ready").hide();
								$("#void,#completed").show(); 
								$("#pay_div,#pay_table,#moreMethod,#moneyId,#payDate,#FOC,#handledId,#saleno,#paySelectId").attr("disabled",false).show();
								$(".paymentAmount").attr("checked",false);
								$("#paySelectId").val("");
								$("#payDate,#handledId,#saleno").attr("disabled",false).css("border-bottom-color","#0089FF").val("");
								$("#payDate,#payDate1,#payDate2").attr("disabled",false).val(getMonthBeforeDay(new Date()));//默认当天
								$("#handledId,#handledId1,#handledId2").attr("disabled",false).val("${adminUsername}");
								
								if($("#moreMethod").attr('checked')){
									$("#firstTable2,.detailTitle").show();
									$("#firstTable").hide();
								}else{
									$("#firstTable,.detailTitle").show();
									$("#firstTable2").hide();
								}
							}else if(downs[0][i].status=="VOID"){
								$("#ready,#completed,#void").hide();
							}else if(downs[0][i].status=="Completed"){
								$("#moneyId,#payDate,#FOC,#handledId,#saleno,#paySelectId").attr("disabled",true);
								$("#moreMethod").attr("disabled",true);   //--<<
		                 		$("#ready,#void,#completed").hide();
								
								if(d[1].length>1){
									$("#firstTable2").show();
									$("#firstTable").hide();
								}else{
									$("#firstTable").show();
									$("#firstTable2").hide();
								}
								
								for(var j=0; j< d[1].length; j++){
									var ii=parseInt(j)+parseInt("1");
									if(d[1].length>1){
										$("#paySelectId"+ii+"").val(d[1][j].paymentMethod);
									  	$("#paySelectId"+ii+"").attr("disabled",true);
									  	$("#moneyId"+ii+"").val(d[1][j].paymentAount);
									  	
									  	$("#moneyId"+ii+",#payDate"+ii+",#handledId"+ii+",#saleno"+ii+"").attr("disabled",true);
									  	$("#payDate"+ii+"").val(d[1][j].paymentDate);
									  	$("#handledId"+ii+"").val(d[1][j].handleder);
									  	$("#saleno"+ii+"").val(d[1][j].saleno);
									}else{
										$("#paySelectId").val(d[1][j].paymentMethod);
									  	$("#paySelectId").attr("disabled",true);
									  	
									  	if(d[1][j].paymentAount != "0"){
									  		$("#moneyId").attr("checked",true);
									  		$("#FOC").attr("checked",false);
									  	 
									  	}else{
								  			$("#moneyId").attr("checked",false);
								  			$("#FOC").attr("checked",true);
									  	}
									  	
									  	$("#moneyId,#payDate,#FOC,#handledId,#saleno").attr("disabled",true);
									  	$("#payDate").val(d[1][j].paymentDate);
									  	$("#handledId").val(d[1][j].handleder);
									  	$("#saleno").val(d[1][j].saleno);
									}
								}
								
								/**$.ajax({
									url:"PaymentServlet",
									type:"post",
									data:{"method":"select","refno":downs[0][i].ordercode},
									success:function(date){
										var dataRoles=eval(date);
										if(dataRoles==""){//
											$("#pay_div,#pay_table").attr("disabled",true).hide();
											alert("Obtain payment information abnormalities");
											return false;
										}else{
											$("#pay_div,#pay_table").attr("disabled",true).show();
											$("#paySelectId").val(dataRoles[0].paymentMethod);
											$("[name='paymentAmount']:checkbox").each(function(){
												if(this.value==dataRoles[0].paymentAount){
													this.checked=true;
													return false;
												}
											});
											$("#payDate").val(dataRoles[0].paymentDate);
											$("#handledId").val(dataRoles[0].handleder);
											$("#saleno").val(dataRoles[0].saleno);
										}
									},error:function(){
										alert("Network connection is failed, please try later...");
			  			   				return false;
									}
								});**/
								
								
		                 	}
               /**  if(parseFloat(("${SystemAcceTime}").substring(11,13))>=12 || downs[0][i].status=="Ready"|| downs[0][i].status=="Completed"){//当前时间超过十二点 	
			 	
				 	}else{
                 		alert("Normal operation time is after 12 o 'clock in the afternoon!");
                 		$("#ready,#completed").attr("disabled",true).hide();
                 		return false;
                 	}
                  **/
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
			});
    	
    	
    	 
    }
  
  
  
  function modify_input(obj){
	  if($("#span_"+obj+"").css("display")=="none")
		  return false;
	
	  if(before==null){
		  $("#span_"+obj+",.input[id!='id_"+obj+"']").hide();
		  $("#id_"+obj+",.view[id!='span_"+obj+"']").show();
		  	  modify_view(obj);
	  }else{
		  
		 // alert($(".inputNumber[id='input_"+before+"']").val()+"=="+$(".view[id='span_"+before+"']").text());
		  if(parseFloat($(".inputNumber[id='input_"+before+"']").val())!=parseFloat($(".view[id='span_"+before+"']").text())){
			  if(confirm("Confirm to cancel changes?")){
				    $("#span_"+obj+",.input[id!='id_"+obj+"']").hide();
				     $(".inputNumber[id='input_"+before+"']").val($(".view[id='span_"+before+"']").text());
					$("#id_"+obj+",.view[id!='span_"+obj+"']").show();
					$("#allprice_"+before+"").empty().append(downs[0][obj].priceall);
						  modify_view(obj);
			  }else{
			 
				  $(".inputNumber[id='input_"+before+"']").focus().select();
			  }
		  }else{
			  $("#span_"+obj+",.input[id='id_"+before+"']").hide();
			 $("#id_"+obj+",.view[id='span_"+before+"']").show();
			 	  modify_view(obj);
		  }
	
	  }
	    $(".inputNumber[id='input_"+obj+"']").focus().select();
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
 
  function save(k,i){
	  //alert(downs[0][i].ordercode);
	 // alert(downs[0][i].procode);
	//  alert(downs[0][i].price);
	 // alert(downs[0][i].quantity);
	 // alert(downs[0][i].priceall);
	  if(parseFloat(downs_last[0][k].quantity)==parseFloat($("#input_"+k+"").val())){
		  $(".view,#button_"+k+"").show();
		  $(".input,#save_"+k+"").hide();
 
	  }else{
		  if(confirm("Are you sure to Save?")){
				  $.ajax({
					  url:"ModifyMarketingPremiumHRServlet",
					  type:"post",
					  async:false,
					  data:{"id":downs_last[0][k].id,"quantity":$("#input_"+k+"").val(),"allprice":$("#allprice_"+k+"").text()},
					  success:function(date){
						  if(date=="success"){
							  alert(date);
							 Detial(i);
							
							 
							 //$(".view,#button_"+k+"").show();
		 					 // $(".input,#save_"+k+"").hide();
						  }else{
							  alert(date);
							  return false;
						  }
					  },error:function(){
						  alert("Network connection is failed, please try later...");
						  return false;
					  }
				  });
		  }
		  
		   
	  }
	before=null;
  }
  
  	
		/*按减号所触发的函数*/
		function deleteQuantity(r,id){
			var val = parseFloat(document.getElementById("input_"+id+"").value);
			if(val > 0){
				$("[id='input_"+id+"']").val(val-1).blur();
				$("#allprice_"+id+"").empty().append(parseFloat(val-1)*parseFloat($("#price_"+id+"").text()));    
			}
		}
		/*按加号所触发的函数*/
		function addQuantity(r,id){
			var val = parseFloat(document.getElementById("input_"+id+"").value);
			if(val < parseFloat(downs_last[0][id].quantity)){
				$("[id='input_"+id+"']").val(val+1).blur();
				$("#allprice_"+id+"").empty().append(parseFloat(val+1)*parseFloat($("#price_"+id+"").text()));   
			}else{
				alert("Inventory is not enough!");
			}
			updprice(id);
		}
		 function updprice(i){
			$("#allprice_"+i+"").empty().append(parseFloat($("#input_"+i+"").val()) * parseFloat($("#price_"+i+"").text().replace("HK$",""))); 
		//order_price();
		 }
		 
		 function NumberBlur(r,id){
			 	var val = parseFloat(document.getElementById("input_"+id+"").value);
			if(val < parseFloat(downs_last[0][id].quantity)){
				//$("[id='input_"+id+"']").val(val+1).blur();
				$("#allprice_"+id+"").empty().append(parseFloat(val)*parseFloat($("#price_"+id+"").text()));   
			}else{
				$("[id='input_"+id+"']").val(downs_last[0][id].quantity);
			}
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
    	$('#lists').show();
    	selects_last(pagenow);
    }
   /** function del(i){
	  if(confirm("Sure to delete?")){
		  $.ajax({
			 url:"DelMarkPremiumRecord",
			 type:"post",
			 data:{'ordercode':downs[0][i].ordercode},
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
  }**/
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
  /**
   * 准备
   * 
   * */
  function ready(){
	  
	  if($("#location").val()==""){
    		 alert("Lost receipt floors!");
    		 return false;
    	 }
	  
	  if(confirm("Are you sure to Ready?")){// 确认操作？
		  $.ajax({
			 url:"MarketingPremiumServlet",
			 type:"post",
			 data:{"method":"ready","ordercode":downs_last[0][0].ordercode,"status":"Ready",
			    "to":email,
			    "location":$("#location").val()},
			 success:function(date){
				 //alert(sendMail(email,email_marktingPremium($("#location").val()))); 
				 var result=$.parseJSON(date);
    			 if(result.state=="success"){
    				 alert("success");
    			 }else{
    				 alert(result.msg);
    			 }
				 hideview();
			 },error:function(){
				 alert("Network connection failure, please return to the login page to log in!");
				 return false;
			 }
		  });
	  }
	 
  }
  
   function VOID(){
	  if(confirm("Are you sure to VOID?")){// 确认操作？
		  $.ajax({
			 url:"MarketingPremiumServlet",
			 type:"post",
			 data:{"method":"VOID","ordercode":downs_last[0][0].ordercode,"status":"VOID"},
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
  
  /**
   * 完成
   */
  function completed(){
	  var result=true;
	  	var menthod = $("#paySelectId").val(); //支付方式
	  	var money = document.getElementById("moneyId").checked;
	  	var FOC = document.getElementById("FOC").checked;
	    var payDate = $("#payDate").val();
	  	var handled = $("#handledId").val();
	  	var saleno=$("#saleno").val();
	  	
	  	var menthod1 = $("#paySelectId1").val(); //支付方式
	  	var money1 = $("#moneyId1").val();
	  	var payDate1 = $("#payDate1").val();
	  	var handled1 = $("#handledId1").val();
	  	var saleno1=$("#saleno1").val();
	  	
	    var menthod2 = $("#paySelectId2").val(); //支付方式
	  	var money2 =   $("#moneyId2").val();
	    var payDate2 = $("#payDate2").val();
	  	var handled2 = $("#handledId2").val();
	  	var saleno2=$("#saleno2").val();
	  	
	  	var paymentAmount = "";
	  	var menthods ="";
	  	var moneys = "";
	  	var payDates = "";
	  	var handleds = "";
	  	var salenos="";
	  	
	  	/*******************************************************************************************/
	    	 if($("#moreMethod").attr('checked')){  //如果勾选了其他支付方式
	    		var a=parseFloat(money1);
	    		var b=parseFloat(money2);
	    		//alert(a+"--"+b)
	    		if(menthod1==""){
			  		alert("please select Payment Method");
			  		$("#paySelectId1").focus();
			  		result =false;
			  		return false;
			  	}
	    		if(menthod2==""){
			  		alert("please select Payment Method");
			  		$("#paySelectId2").focus();
			  		result =false;
			  		return false;
			  	}
	    	 	if(money1==""){
			  		alert("Please chose Payment Amount!");
			  		$("#moneyId1").focus();
			  		result =false;
			  		return false;
			  	}
	    	 	if(money2==""){
			  		alert("Please chose Payment Amount!");
			  		$("#moneyId2").focus();
			  		result =false;
			  		return false;
			  	}
				if((a+b)!=allMoney){
					alert("Payment Amount is not consistent");
					result =false;
					return false;
				}
			  	if(payDate1==""){
			  		alert("please input Payment Date");
			  		$("#payDate1").focus();
			  		result =false;
			  		return false;
			  	}
			  	if(payDate2==""){
			  		alert("please input Payment Date");
			  		$("#payDate2").focus();
			  		result =false;
			  		return false;
			  	}
			  	if(handled1==""){
			  		alert("please input Handled By");
			  		$("#handledId1").focus();
			  		result =false;
			  		return false;
			  	}
			  	if(handled2==""){
			  		alert("please input Handled By");
			  		$("#handledId2").focus();
			  		result =false;
			  		return false;
			  	}
			  	
			  	menthods=menthod1+"~~"+menthod2;
			  	paymentAmount =money1+"~~"+money2;
			  	payDates = payDate1+"~~"+payDate2;
			  	handleds = handled1+"~~"+handled2;
			  	if(saleno2==""){
			  		saleno2=" ";
			  	}
			  	salenos=saleno1+"~~"+saleno2;
	    	}else{						  //没勾选其他支付方式
    			if(menthod==""){
			  		alert("please select Payment Method");
			  		$("#paySelectId").focus();
			  		result =false;
			  		return false;
			  	}
			  	if(money == false && FOC == false){
			  		alert("Please chose Payment Amount!");
			  		result =false;
			  		return false;
			  	}
			  	if(payDate==""){
			  		alert("please input Payment Date");
			  		$("#payDate").focus();
			  		result =false;
			  		return false;
			  	}
			  	if(handled==""){
			  		alert("please input Handled By");
			  		$("#handledId").focus();
			  		result =false;
			  		return false;
			  	}
			  	
			  	menthods=menthod;
			  	if(FOC == true){
			  		paymentAmount = 0;
			  	}else{
			  		paymentAmount = allMoney;
			  	}
			  	payDates = payDate;
			  	handleds = handled;
			    salenos=saleno;
	    	}
	  	
	  	if(result){
		  if(confirm("Are you sure to Completed?")){// 确认操作？
			    $.ajax({
				 url:"MarketingPremiumServlet",
				 type:"post",
				 data:{"method":"complete","ordercode":downs_last[0][0].ordercode,"status":"Completed","saleno":salenos,"staffname":$("#staffnames").val(),"location":$("#location").val(),
			    	"paymethod":menthods,"payamount":paymentAmount,"payDate":payDates,
		    		 		"handle":handleds,"savetype":"MarketingPremium"},
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
  }
  	
    $('input[name=moreMethod]').live('click',function(){
    	if($(this).attr('checked')){
    		$("#firstTable").hide();
    		$("#firstTable2").show();
    	}else{
    		$("#firstTable2").hide();
    		$("#firstTable").show();
    	}
    });
  </script>
<body >
<div class="cont-info" id="lists">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="Calendar('start_date')" />
              		<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it"  onClick="Calendar('end_date')" />
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
				<td class="tagName">Status：</td>
				<td class="tagCont">
					<input type="hidden" name="productname" id="productname">
					<select id="statu">
		              <option value="">please select Status</option>
		              <option >Submitted</option>
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
				<td class="tagCont" colspan="2"></td>
				<td class="tagCont" colspan="2">
				<c:if test='${roleObj.search==1}'>
					<a class="btn" id="searchs" name="search">
						<i class="icon-search"></i>
						Search
					</a>
				</c:if>
				<c:if test='${roleObj.export==1}'>
					<a class="btn" id="downall" name="downsall">
						<i class="icon-download"></i>
						Export
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
		<table class="table-ss" width="100%" border="0" cellpadding="0" cellspacing="0">
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
<div class="e-container" id="overs">
	<div class="info-form">
		<h4>Marketing Primium Request</h4>
		<table class="form-table" id="topTable">
			<tr>
				<td class="tagName">Ref.number</td>
				<td class="tagCont">
					<span id="refnos"></span>
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
	<div class="info-form">
		<h4>Marketing Primium Shopping Information</h4>
		<table class="from-table" id="leftTable">
			<tr>
				<td class="">ID</td>
				<td class="">Order Code</td>
				<td class="">Product Code</td>
				<td class="">Product English Name</td>
				<td class="">Calculation</td>
				<td class="">Price</td>
				<td class="">Quantity</td>
				<td class="">Total Price</td>
				<td class="">Handle</td>
			</tr>
		</table>
		<div class="detail-cotainer" style="margin-top: 10px;">
			<div class="detailTitle" style="width: 100%; border: 1px solid #bbb; border-bottom: none; padding-left: 8px; ">
				<label class="inline checkbox" style="*margin-bottom: 10px!important;">
	   				<input type="checkbox" name="moreMethod" id="moreMethod"/>
	   				More than one payment Methods 
	   			</label>
			</div>
			<div class="detail" id="firstTable">
				<table>
					<tr>
						<td class="tagName">Payment Method</td>
						<td class="tagCont">
							<select id="paySelectId">
				            	<option value="">please select PaymentMethod</option>
				             	<option>Octopus</option>
				             	<option>C-Club</option>
				             	<option>EPS</option>
				            	<option>FOC</option>
				            </select>
				            <input type="text"  id="saleno">
						</td>
					</tr>
					<tr>
						<td class="tagName">Payment Amount</td>
						<td class="tagCont">
							<input type="hidden" id="amount"/>
							<label class="inline checkbox">
								<input type="checkbox"  name="paymentAmount" value="20" id="moneyId"  class="paymentAmount"/> HKD
								<span id="spanMoeny"></span>
							</label>
							<label class="inline checkbox">
								<input type="checkbox" name="paymentAmount" value="0" id="FOC" class="paymentAmount"/>&nbsp;FOC
							</label>
							<label class="inline checkbox" style="margin-left: 0px; padding-left: 0px; ">
								
							</label> 
						</td>
					</tr>
					<tr>
						<td class="tagName">Payment Date</td>
						<td class="tagCont">
							<input type="text" id="payDate" onClick="Calendar('payDate')" />
						</td>
					</tr>
					<tr>
						<td class="tagName">Handled by</td>
						<td class="tagCont">
							<input type="text" id="handledId"/>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="detail2" id="firstTable2" style="display:none;">
				<table>
					<tr>
						<td class="tagName" width="20%">Payment Method</td>
						<td class="tagCont" width="30%">
							<select id="paySelectId1">
					            <option value="">Please Select Payment Method</option>
					            <option value="Octopus">Octopus</option>
					            <option value="C-Club">C-Club</option>
					            <option value="EPS">EPS</option>
					            <option value="FOC">FOC</option>
				            </select>
			            	<input type="text"  id="saleno1" name="saleno1"  class="inputstyle">
						</td>
						<td class="tagName" width="20%">Payment Method</td>
						<td class="tagCont" width="30%">
							<select id="paySelectId2">
					            <option value="">Please Select Payment Method</option>
					            <option value="Octopus">Octopus</option>
					            <option value="C-Club">C-Club</option>
					            <option value="EPS">EPS</option>
					            <option value="FOC">FOC</option>
				            </select>
			            	<input type="text"  id="saleno2" name="saleno2"  class="inputstyle">
						</td>
					</tr>
					<tr>
						<td class="tagName" width="20%">Payment Amount</td>
						<td class="tagCont" width="30%">
							<label class="inline checkbox">HKD:
								<input type="text" id="moneyId1" onchange="checkChange('moneyId1');"/>
							</label>
						</td>
						<td class="tagName" width="20%">Payment Amount</td>
						<td class="tagCont" width="30%">
							<label class="inline checkbox">HKD:
								<input type="text" id="moneyId2" onchange="checkChange('moneyId2');"/>
							</label>
						</td>
					</tr>
					<tr>
						<td class="tagName" width="20%">Payment Date</td>
						<td class="tagCont" width="30%">
							<input type="text" id="payDate1" class="inputstyle" onclick="Calendar('payDate1')"/>
						</td>
						<td class="tagName" width="20%">Payment Date</td>
						<td class="tagCont" width="30%">
							<input type="text" id="payDate2" class="inputstyle" onclick="Calendar('payDate2')"/>
						</td>
					</tr>
					<tr>
						<td class="tagName" width="20%">Handled by</td>
						<td class="tagCont" width="30%">
							<input type="text" id="handledId1" class="inputstyle"/>
						</td>
						<td class="tagName" width="20%">Handled by</td>
						<td class="tagCont" width="30%">
							<input type="text" id="handledId2" class="inputstyle"/>
						</td>
					</tr>
				</table>
			</div>
			
			
			<div class="" id="cloneTable"></div>
			<div style="clear: both;"></div>
		</div>
		<div style="width: 100%; padding-top:10px;">
			<p align="center">
			<c:if test='${roleObj.audit==1}'>
				<a class="btn" id="ready" name ="ccc" onClick="ready();">
					Ready
				</a>
			</c:if>
			<c:if test='${roleObj.audit==1}'>
				<a class="btn" id="void" name ="bbb" onClick="VOID();">
					Void
				</a>
			</c:if>
			<c:if test='${roleObj.audit==1}'>
				<a class="btn" id="completed" name ="bbb" onClick="completed();">
					Completed
				</a>
			</c:if>
				<a class="btn" onClick="hideview();">
					Cancel
				</a>
  		    </p>
    	</div> 
	</div>
</div>
</body>
</html>
