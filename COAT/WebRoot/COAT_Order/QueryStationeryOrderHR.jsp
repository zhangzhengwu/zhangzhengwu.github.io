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
		#firstTable2 .tagName{width: 15%!important;}
		#firstTable2 .tagCont{width: 35%!important;}
	    
	    #overs {
				display:none;
			}
		.page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
		.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
		.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
		.page_and_btn ul li a{color:#333; text-decoration:none;}
		.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
		.page_and_btn  { color:#555;}
		.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
		.page_and_btn div{float:left;}
		.images {
			width: 16px;
			height: 16px;
			margin-top: 2px;
		}
		.inputNumber {
			width: 28px!important;
			border: 0px;
		}
    </style>
    
  </head>
  <script type="text/javascript" >
  var downs=null;
  var d=null;
  var curPage=1;
  var totalPage=1;
  var before=null;
  var allMoney = 0;
  var dataDetail=null;
  var locations=null;
  var email=null;
  function resize(){
	  $("#divId,#divIdSelected,.span_menu,#Medical").width($("body").width()).css("left",0); 
  }
  
  $(function(){
	  window.onresize=resize;
  	  var econvoy="${Econvoy}".split("-");
  	  
  	  getlocation();
     //注册单击事件
 	var da = new Date();
     //注册单击事件
	$("#start_date").val(getMonthBeforeDay(da));	
	$("#end_date").val(getMonthBeforeDay(da));	
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
	 /*加载location*/
	function getlocation(){
		$.ajax({
			type:"post",
			//url:"QueryLocationServlet",
			//data:null,
			url: "common/CommonReaderServlet",
			data:{"method":"getlocation"},
			success:function(date){
				var locationc=eval(date);
				var html="";
				$("#locationid").empty();
				if(locationc.length>0){
					html+="<option value='' >Please Select Location</option>";
					for(var i=0;i<locationc.length;i++){
						html+="<option value='"+locationc[i].realName+"' >"+locationc[i].name+"</option>";
					}
				}else{
					html+="<option value=''>loading error</option>";
				}
				$("#locationid").append(html);
			},error:function(){
				alert("Network connection failure, please return to the login page to log in!");
				return false;
			}
		});
	}
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
		 datastr = strYear+"-"+strMonth+"-"+strDay;
		 return datastr;
	}
 	/******************************************selects Click*****************************************************/
 	$("#down").click(function(){ 
		if(downs!=null){
			//window.location.href="../ReportStationeryServlet?start_Date="+$("#start_date").val()+"&end_Date="+$("#end_date").val()+"&curretPage="+curPage+"&staffcode="+$("#clientcode").val();
			$.ajax({
				type: "post",
				url:"ReportStationeryServlet",
				async:false,
				data: {'start_Date':$("#start_date").val(),'end_Date':$("#end_date").val(),'staffcode':$("#clientcode").val(),'ordercode':$("#ordercode").val(),'curretPage':curPage},
				success:function(date){
					clipboardData.setData('Text',date.substring(date.indexOf(':')+1));
					alert(date);
				},error:function(){
					alert("The export failed!");
				}
			});
		}
		else{
			alert("Please check the data！");
			}
	
	});
	$("#downall").click(function(){ 
		if(downs!=null){
			window.location.href="../ReportAllStationeryServlet?start_Date="+$("#start_date").val()+"&end_Date="+$("#end_date").val()+"&curretPage="+curPage+"&staffcode="+$("#clientcode").val()+"&ordercode="+$("#ordercode").val();
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
	function selects_bak(){
		before=null;
		if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("Start Date can’t be later than End Date.");
				$("#start_date").focus();
				return false;
			}
		}
		
			$.ajax({
			type: "post",
			url:"QueryStationeryOrderForHRServlet",
			data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'clientcode':$("#clientcode").val(),'ordercode':$("#ordercode").val(),"productname":$("#productname").val(),"location":$("#locationid").val(),"clientname":$("#clientname").val(),'pageNow':curPage},
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
						var caozuo="<span id='span_"+i+"' style='width:100%;' >"+
						dataRole[0][i].quantity+"</span>";
	 			   		var edit="";
	 			   		var caozuo_bar="<span id='span_"+i+"' class='view' style='width:100%;' onclick='modify_input("+i+");'>"+
						dataRole[0][i].quantity+"</span><span id='id_"+i+"' style='display:none;' class='input'><img src='images/jian.png' class='images' onclick='deleteQuantity(this,"+i+");'/>" +
							"<input  class='inputNumber' maxlength='3' onblur='NumberBlur(this,"+i+");' type='text' id='input_"+i+"' value='"+dataRole[0][i].quantity+"'/>" +
							"<img src='images/jia.png' class='images' onclick='addQuantity(this,"+i+");'/>"
 			   		 			caozuo=caozuo_bar;
								edit="<a href='javascript:void(0);' onclick='modify_input("+i+");' id ='button_"+i+"'>Edit</a>";
								
								/**if( parseFloat(("${SystemAcceTime}").substring(11,13))<12){//当前时间为上午
									if(comptime(dataRole[0][i].orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1){//submitDate >大于昨天中午12点
										caozuo=caozuo_bar;
										edit="<a href='javascript:void(0);' onclick='modify_input("+i+");' id ='button_"+i+"'>Edit</a>";
									}else{
										edit="<a href='javascript:void(0);' onclick='Detail("+i+");' name ='1'>Detail</a>";
									}
								}else{//时间为下午
									if(comptime(dataRole[0][i].orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1){
										caozuo=caozuo_bar;
											edit="<a href='javascript:void(0);' onclick='modify_input("+i+");' id ='button_"+i+"'>Edit</a>";
										}else{
											edit="<a href='javascript:void(0);' onclick='Detail("+i+");' name ='1'>Detail</a>";
										}
									
								}**/
							 
	 			   			 
						html+="<tr id='select' align='center'><td >"+
						dataRole[0][i].ordercode+"</td><td  >"+
						dataRole[0][i].clientcode+"</td><td >"+
						dataRole[0][i].clientname+"</td><td  align='center' id='allprice_"+i+"'>"+
						dataRole[0][i].priceall+"</td><td align='center' >"+dataRole[0][i].orderdate+"</td><td >"+dataRole[0][i].status+"</td><td  align='center'>"+edit+"  <a href='javascript:void(0);' style='display:none;' onclick='save("+i+");' id='save_"+i+"'>Save</a></td></tr>";
					}

						html+="<tr id='select' align='center'><td colspan='9' ><span  style='color:blue'><strong>Remark: Please find the details about each status under “Handle” as below.</br></strong></span><strong>&nbsp;HOLD:<span style='color:blue'>This record can be edited after 12:00p.m.</span>";
						html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						html+="<strong>Approved:</strong><span style='color:blue'>This record has been freenzen and can't be edited.</span></br>";
						html+="<strong>&nbsp;&nbsp;&nbsp; Edit:</strong><span style='color:blue'>The quantity can be only decreased..</span>";
						html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						html+="<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Save:</strong><span style='color:blue'>To save the change.</span></strong></td></tr>";
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select' align='center'><td colspan='8' align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
				}	 page(dataRole[1],dataRole[2]);
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[1]);
					 $("#totalPage").empty().append(dataRole[2]);
				 	 $("tr[id='select']:odd").css("background","#F0F0F0");
	                 $("tr[id='select']:even").css("background","#COCOCO");
			 }
		 });
	}
	/**************************************************************************************************/
  });
  
  
  	function selects(){
			var econvoy="${Econvoy}".split("-");
			
			if($("#start_date").val()!="" && $("#end_date").val()!=""){
				var beginTime = $("#start_date").val();
				var endTime = $("#end_date").val();
				if(beginTime>endTime){
					alert("Start Date can’t be later than End Date.");
					$("#start_date").focus();
					return false;
				}
			}
			
			$.ajax({
			type: "post",
			//url:"QueryStationeryOrderServlet",
			url:"StationeryServlet",
			data: {'method':"select_HR",
				'start_Date':$("#start_date").val(),
				'end_Date':$("#end_date").val(),
				'clientcode':$("#clientcode").val(),
				'clientname':$("#clientname").val(),
				'ordercode':$("#ordercode").val(),
				'location':$("#locationid").val(),
				'status':$("#status").val(),
				'orderType':$("#orderType").val(),
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
				if(dataRole[0].length>0){
					downs=dataRole;
					$(".page_and_btn").show();
					curPage=dataRole[1];
	 			   	totalPage=dataRole[2];
					for(var i=0;i<dataRole[0].length;i++){
						html+="<tr id='select' title='"+i+"' style='line-height:20px;'><td align='center'>"+
						dataRole[0][i].ordercode+"</td><td align='center'>"+
						dataRole[0][i].clientname+"</td><td align='center'>"+
						dataRole[0][i].clientcode+"</td><td align='center'>"+
						dataRole[0][i].location+"</td><td align='center'>"+
						(dataRole[0][i].staffOrCons=='Staff'?'FOC':('HKD '+dataRole[0][i].priceall))+"</td>  <td align='center'>"+
						dataRole[0][i].orderdate+"</td> <td align='center'>" +
						dataRole[0][i].status+"</td><td align='center'><c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='Detail("+i+");' name ='1'>Detail</a></c:if>";
							html+="</td></tr>";
					}
					//html+="<tr  id='select'><td colspan='8'><span  style='color:red'><strong>Remark:&nbsp;&nbsp;1.Daily Cut-Off time : 12:00noon." +
					//"&nbsp;&nbsp;&nbsp;&nbsp;2.“Submit Date” is less than today's record has been freenzer and can't be deleted.</strong></span><strong></td></tr>";

				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select' style='line-height:20px;'><td colspan='18' align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
				}	 page(dataRole[1],dataRole[2]);
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[1]);
					 $("#totalPage").empty().append(dataRole[2]);
				 	 $("tr[id='select']:odd").css("background","#F0F0F0");
	                 $("tr[id='select']:even").css("background","#COCOCO");
			 }
		 });
	}
  
  
  
  function modify_input(obj){
	  if($("#span_"+obj+"").css("display")=="none")
		  return false;
	 // alert(before);
	  if(before==null){
		  $("#span_"+obj+",.input[id!='id_"+obj+"']").hide();
		  $("#id_"+obj+",.view[id!='span_"+obj+"']").show();
		  	  modify_view(obj);
	  }else{
		  
		 // alert($(".inputNumber[id='input_"+before+"']").val()+"=="+$(".view[id='span_"+before+"']").text());
		  if(parseFloat($(".inputNumber[id='input_"+before+"']").val())!=parseFloat($(".view[id='span_"+before+"']").text())){
			  if(confirm("Please confirm if Save?")){
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
  
  
  
	function Detail(id){
		locations=null;
		$("#dordercode").val(downs[0][id].ordercode);
		$("#dclientname").val(downs[0][id].clientname);
		$("#dclientcode").val(downs[0][id].clientcode);
		$("#dlocation").val(downs[0][id].location);
		$("#dstatus").val(downs[0][id].status);
		locations=downs[0][id].location;
		
		var orderdate=downs[0][id].orderdate;
		$("tr#select").remove();
   		$("#overs").show();
   		$('#lists').hide();
   		
    	$.ajax({
				url: "QueryStationeryForUpdServlet",
				type:"post",
			 	data:{'ordercode':downs[0][id].ordercode,'type':"Stationery"},
		  	 	success:function(date){
					var dd=eval(date);
					dataDetail=d=eval(dd);
					
					var html_left="";
					$(".product").remove();
					allMoney = 0;
					if(d[0].length>0){
					for(var j=0; j< d[0].length; j++){
						
						var caozuo="<span id='span_"+j+"' style='width:100%;' >"+
						d[0][j].quantity+"</span>";
	 			   		var edit="";
	 			   		var caozuo_bar="<span id='span_"+j+"' class='view' style='width:100%;' onclick='modify_input("+j+");'>"+
						d[0][j].quantity+"</span><span id='id_"+j+"' style='display:none;' class='input'><img src='images/jian.png' class='images' onclick='deleteQuantity(this,"+j+");'/>" +
							"<input  class='inputNumber' maxlength='3' onblur='NumberBlur(this,"+j+");' type='text' id='input_"+j+"' value='"+d[0][j].quantity+"'/>" +
							"<img src='images/jia.png' class='images' onclick='addQuantity(this,"+j+");'/>"
	 			   		 
	 			   			if($("#dstatus").val()!="Completed"){
	 			   				edit="<a href='javascript:void(0);' onclick='modify_input("+j+");' id ='button_"+j+"'>Edit</a>";
								caozuo=caozuo_bar;
	 			   			}	
						
								/**if( parseFloat(("${SystemAcceTime}").substring(11,13))<12){//当前时间为上午
									if(comptime(orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1){//submitDate >大于昨天中午12点
										edit="<a href='javascript:void(0);' onclick='modify_input("+j+");' id ='button_"+j+"'>Edit</a>";
										caozuo=caozuo_bar;
									}else{
										edit="";
									}
								}else{//时间为下午
									if(comptime(orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1){
										caozuo=caozuo_bar;
											edit="<a href='javascript:void(0);' onclick='modify_input("+j+");' id ='button_"+j+"'>Edit</a>";
										}else{
											edit="";
										}
									
								}**/
						
						var num = j+1;
					
						
						html_left+="<tr id='select' title='"+j+"' style='line-height:20px;'><td><input type='hidden' id='ordId' value='"+d[0][j].ordercode+"'/>"+
						(j+1)+"</td><td>"+
						d[0][j].ordercode+"</td><td>"+
						d[0][j].procode+"</td><td>"+
						d[0][j].proname+"</td><td id='price_"+j+"'>"+
						d[0][j].price+"</td><td>"+caozuo+"</td><td id='allprice_"+j+"'>"+
						(downs[0][id].staffOrCons=='Staff'?'FOC':d[0][j].priceall)+"</td> <td>";
							html_left+="&nbsp;&nbsp;"+edit+" <a href='javascript:void(0);' style='display:none;' onclick='save("+j+","+id+");' id='save_"+j+"'>Save</a></td></tr>";
						
						if(downs[0][id].staffOrCons=='Consultant'){
							allMoney += parseFloat(d[0][j].priceall);
							$("#moneyId,#spanMoeny").show();
							$("#FOC").attr("checked",false);
						}else{
							$("#moneyId,#spanMoeny").hide();
							$("#FOC").attr("checked",true);
						}
					}
					allMoney=parseFloat(allMoney.toFixed(2));
					$("#spanMoeny").text("HKD"+allMoney);	
					if($("#dstatus").val()=="Submitted" || $("#dstatus").val()==""){
						email=getEmail(downs[0][id].clientcode,downs[0][id].staffOrCons);	
						$("#payId,#Completed").hide();
						$("#Ready,#void").show();
						$("#firstTable2,#firstTable,.detailTitle").hide();	//--<<
					}else if($("#dstatus").val()=="Completed"){
						$("#payId").show();
						$("#Completed,#Ready,#void").hide();
						$("#saleno").attr("disabled",false);
						if(d[1].length>1){
							$("#firstTable2").show();
							$("#firstTable").hide();
						}else{
							$("#firstTable").show();
							$("#firstTable2").hide();
						}
						$("#moreMethod").attr("disabled",true);   //--<<
					}else if($("#dstatus").val()=="Ready"){
						$("#payId,#Completed").attr("checked",false).show();
						$("#Ready").hide();
						$("#void").show();
						
						if($("#moreMethod").attr('checked')){
							$("#firstTable2,.detailTitle").show();
							$("#firstTable").hide();
						}else{
							$("#firstTable,.detailTitle").show();
							$("#firstTable2").hide();
						}
						
						//$("#paySelectId option:selected").text("Cash");
						$("#moneyId,#FOC","#moreMethod").attr("checked",false);
					  	$("#paySelectId,#moneyId,#FOC,#payDate,#handledId,#saleno,#moreMethod").attr("disabled",false);
					  	$("#moneyId1,#saleno1,#paySelectId1,#moneyId2,#saleno2,#paySelectId2").attr("disabled",false).val("");
					  	$("#paySelectId,#payDate,#handledId,#saleno").val("");
					  	$("#payDate,#payDate1,#payDate2").val(getMonthBeforeDay(new Date()));//默认当天
						$("#handledId,#handledId1,#handledId2").val("${adminUsername}");
					}
					
					
					for(var i=0; i< d[1].length; i++){
						var ii=parseInt(i)+parseInt("1");
						if(d[1].length>1){
							$("#paySelectId"+ii+"").val(d[1][i].paymentMethod);
						  	$("#paySelectId"+ii+"").attr("disabled",true);
						  	$("#moneyId"+ii+"").val(d[1][i].paymentAount);
						  	
						  	$("#moneyId"+ii+",#payDate"+ii+",#handledId"+ii+",#saleno"+ii+"").attr("disabled",true);
						  	$("#payDate"+ii+"").val(d[1][i].paymentDate);
						  	$("#handledId"+ii+"").val(d[1][i].handleder);
						  	$("#saleno"+ii+"").val(d[1][i].saleno);
						}else{
							$("#paySelectId").val(d[1][i].paymentMethod);
						  	$("#paySelectId").attr("disabled",true);
						  	
						  	if(d[1][i].paymentAount != "0"){
						  		$("#moneyId").attr("checked",true);
						  		$("#FOC").attr("checked",false);
						  	 
						  	}else{
					  			$("#moneyId").attr("checked",false);
					  			$("#FOC").attr("checked",true);
						  	}
						  	
						  	$("#moneyId,#payDate,#FOC,#handledId,#saleno").attr("disabled",true);
						  	$("#payDate").val(d[1][i].paymentDate);
						  	$("#handledId").val(d[1][i].handleder);
						  	$("#saleno").val(d[1][i].saleno);
						}
					  
						
					}
					
					}else{
					html_left+="<tr id='select' style='line-height:20px;'><td colspan='8' style='line-height:20px;' align='center'><b>Sorry, there is no matching record.</b></td></tr>";	
					}
					$("#leftTable").append(html_left);
					 $("tr[id='select']:odd").css("background","#F0F0F0");
	                 $("tr[id='select']:even").css("background","#COCOCO");
					
					
					/** if(parseFloat(("${SystemAcceTime}").substring(11,13))>=12 || downs[0][id].status=="Ready"|| downs[0][id].status=="Completed"){//当前时间超过十二点 	
			 	
				 	}else{
	                 		alert("Normal operation time is after 12 o 'clock in the afternoon!");
	                 		$("#Ready,#Completed").attr("disabled",true).hide();
	                 		return false;
	                 	}**/
					
					
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
			});
    }
  function save(k,keyID){
	  if(parseFloat(dataDetail[0][k].quantity)==parseFloat($("#input_"+k+"").val())){
		  $(".view,#button_"+k+"").show();
		  $(".input,#save_"+k+"").hide();
 
	  }else{
		  $.ajax({
			  url:"MotifyStaioneryOrderHRServlet",
			  type:"post",
			  async:false,
			  data:{"id":dataDetail[0][k].id,"quantity":$("#input_"+k+"").val(),"allprice":$("#allprice_"+k+"").text()},
			  success:function(date){
				  if(date=="success"){
					  alert(date);
					  Detail(keyID);
				  }else{
					  alert(date);
					  return false;
				  }
			  },error:function(){
				  alert("Network connection is failed, please try later...");
				  return false;
			  }
		  })
		  
		  
		   
	  }
	before=null;
  }
  
  	
		/*按减号所触发的函数*/
		function deleteQuantity(r,id){
			var val = parseFloat($("[id='input_"+id+"']").val());
			if(val > 0){
				$("input[id='input_"+id+"']").val(val-1).blur();
			}
		}
		/*按加号所触发的函数*/
		function addQuantity(r,id){
			var val = parseFloat($("#input_"+id+"").val());
			if(val < parseFloat(dataDetail[0][id].quantity)){
				$("input[id='input_"+id+"']").val(val+1).blur();
			}else{
				alert("Inventory is not enough!");
				
			}
			updprice(id);
		}
		 function updprice(i){
			$("#allprice_"+i+"").empty().append(parseFloat($("#input_"+i+"").val()) * parseFloat($("#price_"+i+"").text().replace("HK$",""))); 
		 }
		 
		 function NumberBlur(r,id){
			 	var val = parseFloat($("#input_"+id+"").val());
			if(val < parseFloat(dataDetail[0][id].quantity)){
				//$("[id='input_"+id+"']").val(val+1).blur();
				$("#allprice_"+id+"").empty().append(parseFloat(val)*parseFloat($("#price_"+id+"").text()));   
			}else{
				$("[id='input_"+id+"']").val(dataDetail[0][id].quantity);
			}
		 }
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
    function hideview(){
    	$("#overs").hide();
    	$('#lists').show();
    	selects();
    }
    
    function changeStatus(status,id){
    	if(confirm("Are you sure to "+status+" ?")){
    		  $.ajax({
				 url:"QueryRoomSettingServlet",
				 type:"post",
				 data:{'changeStatus':"changeStationeryStatus",'ordercode':$("#"+id+"").val(),'status':status},
			  	 success:function(date){
					 if(date=="success"){
					 	$("#searchs").click();
					 	alert("Success!");
					 	hideview();
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
     function Ready(id){
    	 if(locations==null || locations==""){
    		 alert("Lost receipt floors!");
    		 return false;
    	 }
    	
    	if(confirm("Are you sure to Ready ?")){
    		  $.ajax({
				 url:"StationeryServlet",
				 type:"post",
				 data:{'method':"ready",'ordercode':$("#"+id+"").val(),'status':"Ready",
    			  "to":email,
				  "location":locations},
			  	 success:function(date){
					 //alert(sendMail(email,email_Stationery(locations)));
					 var result=$.parseJSON(date);
	    			 if(result.state=="success"){
	    				 alert("success");
	    			 }else{
	    				 alert(result.msg);
	    			 }
					 hideview();
			     },error:function(){
					  alert("Network connection is failed, please try later...");
					  return false;
			     }
			  }); 
    	}
    }
   function VOID(id){
    	if(confirm("Are you sure to  VOID ?")){
    		  $.ajax({
				 url:"StationeryServlet",
				 type:"post",
				 data:{'method':"VOID",'ordercode':$("#"+id+"").val(),'status':"VOID"},
			  	 success:function(date){
					 alert(date);
					 hideview();
			     },error:function(){
					  alert("Network connection is failed, please try later...");
					  return false;
			     }
			  });
		  }
    }
     
    function getPayment(id){
    	var result=true;
	  	//alert($("#"+id+"").val());
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
			  	salenos=saleno1+"~~"+saleno2;;
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
	  	/*******************************************************************************************/
		  	//alert(handleds+""+payDates+""+""+menthods+""+paymentAmount+salenos);
	    	//changeStatus(status,id);
	    	if(result){
	    		if(confirm("Sure to Completed ?")){
	    		  $.ajax({
					 url:"StationeryServlet",
					 type:"post",
					 data:{'method':"complete",'ordercode':$("#"+id+"").val(),'status':"Completed",'menthod':menthods,'saleno':salenos,'payDate':payDates,'handled':handleds,'paymentAmount':paymentAmount,'type':"Stationery","staffname":$("#dclientname").val(),"location":$("#dlocation").val()},
				  	 success:function(date){
						 alert(date);
						 hideview();
				     },error:function(){
						  alert("Network connection is failed, please try later...");
						  return false;
				     }
				  });
			   }
	    	}
    }
    
    
    function checkChange(id){
    	if('FOC' == id){
	  		document.getElementById("moneyId").checked = "";
	  	}else{
	  		document.getElementById("FOC").checked = "";
	  	}
    }
    $('input[name=moreMethod]').live('click',function(){
    	if($(this).attr('checked')){
    		$("#firstTable").hide();
    		$("#firstTable2").show();
    		//$('#cloneTable').html($('#firstTable').html());
    		//$('#cloneTable').addClass('detail');
    		//$('.detail').css({width: '50%'});
    	}else{
    		$("#firstTable2").hide();
    		$("#firstTable").show();
    		//$("#moneyId,#FOC").show();
    		//$('#cloneTable').empty();
    		//$('#cloneTable').removeClass('detail');
    		//$('.detail').css({width: '100%'});
    	}
    });
    
</script>
<body style='overflow:auto;'>
<div class="cont-info" id="lists">
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
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input type="text" name="clientcode" id="clientcode">
				</td>
				<td class="tagName">Staff Name：</td>
				<td class="tagCont">
					<input type="text" name="clientname" id="clientname">
				</td>
			</tr>
			<tr>
				<td class="tagName">OrderCode：</td>
				<td class="tagCont">
					<input type="text" name="ordercode" id="ordercode">
				</td>
				<td class="tagName">Status：</td>
				<td class="tagCont">
					<select id="status">
		             	<option value="">Please Select Status</option>
		             	<option value="Submitted" >Submitted</option>
		             	<option value="Ready">Ready</option>
		             	<option value="Completed">Completed</option>
	            	</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Order Type:</td>
				<td class="tagCont">
					<select id="orderType">
		             	<option value="">Please Select Order Type</option>
		             	<option value="Consultant">Consultant</option>
		             	<option value="Staff">Staff</option>
	            	</select>
				</td>
				<td class="tagName">Location:</td>
				<td class="tagCont">
					<select id="locationid">
		             	<option value="">Please Select Location</option>
		            </select>
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
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead>
			<tr id="title">
				<th class="width_">Order Code</th>
				<th class="width_">Client Name</th>
				<th class="width_">Client Code</th>
				<th class="width_">Location</th>
				<th class="width_">Order Total</th>
				<th class="width_">Submit Date</th>
				<th class="width_">Status</th>
				<th class="width_">Handle</th>
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
<div class="e-container" id="overs">
	<div class="info-form">
		<h4>Stationery Request</h4>
		<table class="form-table">
			<tr>
				<td class="tagName">Order Code：</td>
				<td class="tagCont">
					<input type="text" name="dordercode" id="dordercode" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Client Name：</td>
				<td class="tagCont">
					<input type="text" name="dclientname" id="dclientname" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Client Code：</td>
				<td class="tagCont">
					<input type="text" name="dclientcode" id="dclientcode" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Location：</td>
				<td class="tagCont">
					<input type="text" name="dlocation" id="dlocation" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Status：</td>
				<td class="tagCont">
					<input type="text" name="dstatus" id="dstatus" disabled="disabled" class="inputstyle">
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form">
		<h4></h4>
		<table class="form-table" id="leftTable">
			<tr>
				<td class="width_">ID</td>
				<td class="width_">Order Code</td>
				<td class="width_">Product Code</td>
				<td class="width_">Product English Name</td>
				<td class="width_">Price</td>
				<td class="width_">Quantity</td>
				<td class="width_">Total Price</td>
				<td class="width_">Handle</td>
			</tr>
		</table>
		<div class="detail-cotainer" style="margin-top: 10px;">
			<div class="detailTitle" style="width: 100%;  padding-left: 8px; ">
				<label class="inline checkbox" style="*margin-bottom: 10px!important;">
					<input type="checkbox" name="moreMethod" id="moreMethod" />
	    			More than one payment Methods 
				</label>
			</div>
			<div class="detail" id="firstTable">
				<table>
					<tr>
						<td class="tagName">Payment Method</td>
						<td class="tagCont">
							<select id="paySelectId">
					            <option value="">Please Select Payment Method</option>
					            <option value="Octopus">Octopus</option>
					            <option value="C-Club">C-Club</option>
					            <option value="EPS">EPS</option>
					            <option value="FOC">FOC</option>
				            </select>
			            	<input type="text"  id="saleno" name="saleno"  class="inputstyle">
						</td>
					</tr>
					<tr>
						<td class="tagName">Payment Amount</td>
						<td class="tagCont">
							<label class="inline checkbox">
								<input type="checkbox" id="moneyId" onchange="checkChange('moneyId');"/>
								<span id = "spanMoeny"></span>
							</label>
							<label class="inline checkbox">
								<input type="checkbox" id="FOC" onchange="checkChange('FOC');"/>FOC
							</label>
						</td>
					</tr>
					<tr>
						<td class="tagName">Payment Date</td>
						<td class="tagCont">
							<input type="text" id="payDate" class="inputstyle" onclick="return Calendar('payDate')"/>
						</td>
					</tr>
					<tr>
						<td class="tagName">Handled by</td>
						<td class="tagCont">
							<input type="text" id="handledId" class="inputstyle"/>
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
							<input type="text" id="payDate1" class="inputstyle" onclick="return Calendar('payDate1')"/>
						</td>
						<td class="tagName" width="20%">Payment Date</td>
						<td class="tagCont" width="30%">
							<input type="text" id="payDate2" class="inputstyle" onclick="return Calendar('payDate2')"/>
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
			<%--<div class="detail" id="cloneTable"></div>--%>
			<div class="btn-board" style="float: left; width: 100%;">
				<p align="center">  
				<c:if test='${roleObj.audit==1}'>
					<a class="btn" id="Ready" onClick="Ready('ordId');">Ready</a> 
				</c:if>
				<c:if test='${roleObj.audit==1}'>
					<a class="btn" id="void" onClick="VOID('ordId');">VOID</a>
				</c:if>
				<c:if test='${roleObj.audit==1}'>
					<a class="btn" id="Completed" onClick="getPayment('ordId');">Completed</a>
				</c:if>
					<a class="btn" id="Canel" onClick="hideview();">Cancel</a>
		   		</p>
		    </div>
		    <div style="clear: both;"></div>
		</div>
	</div>
</div>
</body>
</html>
