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
		<script type="text/javascript" src="./css/Util.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
    <style type="text/css">
<!--
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
-->
    </style>
  </head>
  <script type="text/javascript" >
  var downs=null;
  var d=null;
  var curPage=1;
  var totalPage=1;
  var allMoney = 0;
  
  function resize(){
	  $(".span_menu,#Medical").width($("body").width()); 
  }
  
  $(function(){
	   window.onresize=resize;
	 // $("#overs,#divId,#divIdSelected,.span_menu,#Medical").width($("body").width());
  	var econvoy="${Econvoy}".split("-");
     //注册单击事件
 	var da = new Date();
 	
 	getlocation();
 	
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
	$("#searchs").click(function(){
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
  });
  
  
  
  /***********************************************select function************************************/
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
			//data: {'start_Date':$("#start_date").val(),'end_Date':$("#end_date").val(),'clientcode':econvoy[0],'ordercode':$("#ordercodes").val(),'curretPage':curPage},
			url:"StationeryServlet",
			data: {'method':"select_HR",
				'start_Date':$("#start_date").val(),
				'end_Date':$("#end_date").val(),
				'clientcode':econvoy[0],
				'clientname':"",
				'ordercode':$("#ordercodes").val(),
				'location':$("#locationid").val(),
				'status':$("#status").val(),
				'orderType':"Consultant",
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
						dataRole[0][i].location+"</td><td align='center'>HK$ "+
						dataRole[0][i].priceall+"</td>  <td align='center'>"+
						dataRole[0][i].orderdate+"</td> <td align='center'>" +
						dataRole[0][i].status+"</td> <td align='center'><a href='javascript:void(0);' onclick='Detail("+i+");' name ='1'>Detail</a>";
						/* / <a href='javascript:void(0);' onclick='del("+i+");' name ='2'>Delete</a>*/
					
						if(dataRole[0][i].status=="Submitted"){
							html+="&nbsp;<a href='javascript:void(0);' onclick='del("+i+");' name ='2'>Delete</a>";
						}
							html+="</td></tr>";
					}
				//	html+="<tr  id='select'><td colspan='8' style='color: red;'><span>Remark: &nbsp;&nbsp;1.Daily Cut-Off time : 12:00noon." +
					//"&nbsp;&nbsp;&nbsp;&nbsp;2.“Submit Date” is less than today's record has been freenzer and can't be deleted.</span></td></tr>";
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select' style='line-height:20px;'><td colspan='18' align='center'>Sorry, there is no matching record.</td></tr>";
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
  
    function Detail(i){
		$("#dordercode").val(downs[0][i].ordercode);
		$("#clientname").val(downs[0][i].clientname);
		$("#clientcode").val(downs[0][i].clientcode);
		$("#dstatus").val(downs[0][i].status);
    	//window.showModalDialog("QueryStationeryOrderRecord.jsp",downs[0][i].ordercode);
   		$("#overs").show();
   		$('#lists').hide();
    	$.ajax({
				url: "QueryStationeryForUpdServlet",
				type:"post",
			 	data:{'ordercode':downs[0][i].ordercode,'type':"Stationery"},
		  	 	success:function(date){
					var dd=eval(date);
					d=eval(dd);
					var html_left="";
					$(".product").remove();
					allMoney = 0;
					for(var i=0; i< d[0].length; i++){
						var num = i+1;
						html_left+="<tr id='left"+i+"' style='line-height:20px;' class='product'><td ><input type='hidden' id='ordId' value='"+d[0][i].ordercode+"'/>"+
							num+"</td><td align='left'>"+
							d[0][i].ordercode+"</td><td  align='left'>"+
							d[0][i].procode+"</td><td  align='left'>"+
							d[0][i].proname+"</td><td>HK$ "+
							d[0][i].price+"</td><td>"+
							d[0][i].quantity+"</td><td>HK$ "+
							d[0][i].priceall+"</td>"+
						"</tr>";
						allMoney += d[0][i].priceall;
					}
					$("#spanMoeny").text(allMoney);	
					 if($("#dstatus").val()=="Completed"){
						$("#payId").show();
					}else{
						$("#payId").hide();
					}
					for(var i=0; i< d[1].length; i++){
					  	$("#paySelectId option:selected").text(d[1][i].paymentMethod);
					  	$("#paySelectId").attr("disabled",true);
					  	
					  	if(d[1][i].paymentAount != "0"){
					  		document.getElementById("moneyId").checked = "checked";
					  		document.getElementById("FOC").checked = "";
					  	}else{
					  		document.getElementById("FOC").checked = "checked";
					  		document.getElementById("moneyId").checked = "";
					  	}
					  	
					  	//alert(d[1][i].refno+"--"+d[1][i].handleder+"--"+d[1][i].paymentDate+"--"+d[1][i].paymentAount+"--"+d[1][i].paymentMethod);
					  	$("#moneyId").attr("disabled",true);
					  	$("#FOC").attr("disabled",true);
					  	$("#payDate").val(d[1][i].paymentDate);
					  	$("#payDate").attr("disabled",true);
					  	$("#handledId").val(d[1][i].handleder);
					  	$("#handledId").attr("disabled",true);
						
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
    	$('#lists').show();
    	selects();
    }
    	 
	function del(i){
	  /**	if(comptime(downs[0][i].orderdate,GetDateStr("${SystemAcceTime}".substring(0,10).split("-"),-1)+" 12:00:00")==1 && parseFloat(("${SystemAcceTime}").substring(11,13))<12){
	  		
	  	}else if(comptime(downs[0][i].orderdate,"${SystemAcceTime}".substring(0,10)+" 12:00:00")==1 && parseFloat(("${SystemAcceTime}").substring(11,13))>=12){
	  		
	  	}else{
			alert("Daily Cut-Off time : 12:00noon");
			selects();
			return false;
		}**/
	  	
		  if(confirm("Sure to delete ?")){
			  $.ajax({
				 url:"DelStationeryOrder",
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
	  }
	  function changeStatus(status,id){
    	if(confirm("Sure to "+status+" ?")){
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
    function getPayment(status,id){
	  	//alert($("#"+id+"").val());
	  	var menthod = $("#paySelectId option:selected").text();
	  	var money = document.getElementById("moneyId").checked;
	  	var FOC = document.getElementById("FOC").checked;
	  	if(money == false && FOC == false){
	  		alert("Please chose Payment Amount!");
	  		return false;
	  	}
	  	var payDate = $("#payDate").val();
	  	var handled = $("#handledId").val();
	  	var paymentAmount = "";
	  	if(FOC == true){
	  		paymentAmount = 0;
	  	}else{
	  		paymentAmount = allMoney;
	  	}
	  	//alert(handled+""+payDate+""+FOC+""+money+""+menthod+""+paymentAmount);
    	//changeStatus(status,id);
    	if(confirm("Sure to "+status+" ?")){
    		  $.ajax({
				 url:"QueryRoomSettingServlet",
				 type:"post",
				 data:{'changeStatus':"changeStationeryStatus",'ordercode':$("#"+id+"").val(),'status':status,'menthod':menthod,'payDate':payDate,'handled':handled,'paymentAmount':paymentAmount,'type':"Stationery"},
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
    function checkChange(id){
    	if('FOC' == id){
	  		document.getElementById("moneyId").checked = "";
	  	}else{
	  		document.getElementById("FOC").checked = "";
	  	}
    }
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
				<td class="tagName">End Date： </td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it"  onClick="return Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">OrderCode：</td>
				<td class="tagCont">
					<input type="text" name="ordercode" id="ordercodes">
				</td>
				<td class="tagName">Status：</td>
				<td class="tagCont">
					<select id="status">
		             	<option value="">Please Select Status</option>
		             	<option value="Submitted">Submitted</option>
		             	<option value="Ready">Ready</option>
		             	<option value="Completed">Completed</option>
	            	</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location：</td>
				<td class="tagCont">
					<select id="locationid"></select>
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" style="text-align: center;" colspan="3">
					<a class="btn" id="searchs" name="search">
						<i class="icon-search"></i>
						Search
					</a>
				</td>
			</tr>
		</table>	
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_">Order Code</th>
				<th class="width_">Client Name</th>
				<th class="width_">Client Code</th>
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
<div class="e-container" id="overs">
	<div class="info-form">
		<h4>Stationery Request</h4>
		<table>
			<tr>
				<td class="tagName">Order Code：</td>
				<td class="tagCont">
					<input type="text" name="dordercode" id="dordercode" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Client Name：</td>
				<td class="tagCont">
					<input type="text" name="clientname" id="clientname" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Client Code：</td>
				<td class="tagCont">
					<input type="text" name="clientcode" id="clientcode" disabled="disabled" class="inputstyle">
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
	<div class="info-form" id="divIdSelected" style="height:auto;max-height: 50%; overflow-y:auto;">
		<h4>Request Options</h4>
		<table id="leftTable">
			<tr>
				<td class="width_5">ID</td>
				<td class="width_15">Order Code</td>
				<td class="width_15">Product Code</td>
				<td class="width_20">Product English Name</td>
				<td class="width_10">Price</td>
				<td class="width_10">Quantity</td>
				<td class="width_10">Total Price</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="payId" style="display: none;">
		<h4>Payment(for internal use)</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Payment Method</td>
				<td class="tagCont">
					<select id="paySelectId">
		            	<option id="">Please select Payment Mehtod</option>
		             	<option id="Octopus">Octopus</option>
		             	<option id="Club">Club</option>
		            </select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Payment Amount</td>
				<td class="tagCont">
					<label class="inline checkbox">
						<input type="checkbox" id="moneyId" onchange="checkChange('moneyId');"/>HK$
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
					<input type="text" id="payDate" onclick="return Calendar('payDate')"/>
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
	<div class="btn-board">
		<a class="btn" onclick="hideview();">Cancel</a>
	</div>
</div>
</body>
</html>
