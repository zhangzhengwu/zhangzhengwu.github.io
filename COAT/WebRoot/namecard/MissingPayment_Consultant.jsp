<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
//if(session.getAttribute("convoy_username")==null || session.getAttribute("convoy_username")==""){
//	out.println("<script>parent.location.href='../error.jsp';</script>");
//}
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>missing</title>
 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="Missing Payment Report">  
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="./css/style.css">

    <style type="text/css">
<!--
#Medical {
	position:absolute;
	
	width:100%;
	higet:100%;
	z-index:1;
	left:0px;
	top: 0px;
	font-family: 'Arial Narrow';
	font-size: 14px;
}
.txt{
    color:#0000FF;
    border:0px;
   border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
 background-color:transparent; /* 顏色透明*/
	size:35px;
	font-style:oblique;
	font-family: 'Arial Narrow';
	font-size: 14px;

    }
	.txts{
    color:#005aa7;
    border:0px;
     border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:35px;
	font-family: 'Arial Narrow';
	font-size: 14px;

    }
    .page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
	.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
	.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
	.page_and_btn ul li a{color:#333; text-decoration:none;}
	.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
	.page_and_btn  { color:#555;}
	.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
	.page_and_btn div{float:left;}
.STYLE1 {
	font-size: 36px;
	font-weight: bold;
	color: #0000FF;
}
#content{
	position:absolute;
	width:100%;
	z-index:1;
	left:0px;
	top: 6px;	
	height: 405px;
	min-height:250px;overflow-y:auto; max-height:300px;
}
body{
	font-family: 'Arial Narrow';
	font-size: 14px;
}
img{
height: 12px;
width: 12px;

}

.table-ss  tr.title  td{
	white-space: nowrap;
}
-->
.center_div{position:absolute;width:500px;height:400px;background-color:white;z-index:1000;top:160;left:260px;text-align:right;}
.center_div table{width:100%;height:96%;padding:5px;}
.center_div table tr td{text-align:center;border:1px solid #87cefa;}
.center_div span{float:right;width:100%;background-color:#e0ffff;}
  </style>
  </head>
  <script type="text/javascript" >
  	  var downs=null;
      var pagenow =1;
	  var totalpage=1;
	  var total=0;
 	  //window.onresize=popupDiv;
	  function popupDiv(){
	  	 $("#content").css("top",$("#Medical").height());
		 $("#content,#Medical").width($("body").width());
	  }
	  $(function(){
	 	 $("#helpdiv").hide();
	  	 getPrincipal();
		 $("#staffcode").attr("disabled","disabled").val("${convoy_username}");
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
	 function getPrincipal(){
		$("#principal").empty();
		var html;
		html+="<option value=''>Please Select Principal</option>";
		
		$.ajax({
			type: "post",
			url: "SelectMissPrincipalServlet",
			data: null,
			success: function(date){
			var d=eval(date);
			if(d.length>0){
				for(var i=0;i<d.length;i++){
				html+="<option value='"+d[i].principal+"'>"+d[i].remark+"</option>";
				}
				$("#principal:last").append(html);
				}
			}
		});
	} 
	
	$("#down").click(function(){
			var code ="";
			var lastday ="";
			if($("#stage1").attr("checked") == true){
				code = 1;
				lastday = $("#lastday").val();
			}else{
				code = 2;
				if($("#start_date").val() !="" && $("#end_date").val() ==""){
					alert("Please Check The EndDate!");
					$("#endDate").focus();
					return false;
				}
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("Start Date can’t be later than End Date.");
						$("#start_date").focus();
						return false;
					}
				}
			}
			var staffcode="${convoy_username}";
			window.location.href="<%=basePath%>/DownMissingPaymentReportServlet?startdate="+$("#start_date").val()+"&ctype="+code+"&lastday="+lastday+"&enddate="+$("#end_date").val()+"&staffcode="+staffcode+"&clientname="+$("#clientname").val()+"&principal="+$("#principal").val()+"&policyno="+$("#policyno").val();
	});
	
	$("#searchs").click(function(){
		selects(pagenow);
	});

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
	function selects(pagenow){
			$("#total").empty();
			var code ="";
			var lastday ="";
			if($("#stage1").attr("checked") == true){
				code = 1;
				lastday = $("#lastday").val();
			}else{
				code = 2;
				if($("#start_date").val() !="" && $("#end_date").val() ==""){
					alert("Please Check The EndDate!");
					$("#endDate").focus();
					return false;
				}
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("Start Date can’t be later than End Date.");
						$("#start_date").focus();
						return false;
					}
				}
			}
			var staffcode="${convoy_username}";//${convoy_username}
			$.ajax({
			type: "post",
			url:"QueryMissingPaymentForConsServlet",
			data: {'ctype':code,'lastday':lastday,'startdate':$("#start_date").val(),'enddate':$("#end_date").val(),
			'staffcode':staffcode,'clientname':$("#clientname").val(),'principal':$("#principal").val(),'policyno':$("#policyno").val(),
			"pageNow":pagenow,"econvoy":"hk_econvoy"},
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
						var staffcodes = dataRole[0][i].staffcode;
						
						if(dataRole[0][i].staffcode2 !=""){
							staffcodes += " / "+dataRole[0][i].staffcode2;
						}
						var reson = dataRole[0][i].reason;
						 if(reson.length>=22){
							//reson=reson.substring(0,22)+"..";
						}
						var datafrom = dataRole[0][i].datafrom;
						 if(datafrom.length>=55){
							//datafrom=datafrom.substring(0,55)+"...";
						}
						var principal ="";
						if(dataRole[0][i].principal =="Zurich"){
							principal = "Zurich International Life Ltd";
						}else{
							principal = dataRole[0][i].principal;
						}
						html+="<tr id='select' title='"+i+"'><td height='23' align='center'>"+staffcodes+"</td><td height='23' align='center'>"+principal+"</td><td align='center'>"
						+dataRole[0][i].clientname +"</td><td align='center'>"+dataRole[0][i].policyno 
						+"</td><td align='center'>"+dataRole[0][i].missingsum+"</td><td align='center'>"+dataRole[0][i].premiumDate+"</td><td align='center' title='"+dataRole[0][i].reason+"'>"+reson+"</td><td align='center'>"
						+dataRole[0][i].receivedDate +"</td><td align='center'>"+dataRole[0][i].datafrom+"</td></tr>";		 
						//<td align='center' title='"+dataRole[0][i].datafrom+"'>"+datafrom+"</td>
					}
						//$(".page_and_btn").show();
				   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
				}
				else{
					html+="<tr id='select'><td colspan='18' style='color:blue;size=5px' align='center'><b>"+" Sorry, there is no matching record."+"</b></td></tr>";
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
      function del(id){
      	if(confirm("Sure to Delete?")){
			$.ajax({
				url:"DelMissingPaymentServlet",
				type:"post",
				data:{"missId":id},
				success:function(date){
					alert(date);
					$("#searchs").click();
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
			});
		}
      }
      function check(obj){
		$("#"+obj+"").attr("checked","checked");
		if($("#stage1").attr("checked") == true){
			$("#startdate").val("").attr("disabled","disabled");
			$("#enddate").val("").attr("disabled","disabled");
		}
		if($("#stage2").attr("checked") == true){
			$("#startdate").attr("disabled",false);
			$("#enddate").attr("disabled",false);
		}
	}
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
	function showhelp(){
		$("#helpdiv").css("left",($(window).width()-600)/2).show();
	}
  </script>
  <body>
  <br>
  <div id="Medical">
  <table width="100%" border="0" cellpadding="0" cellspacing="0"  style="" class="table-ss">
         <tr>
               <td colspan="5" align="left" style="height:10px;background:#013B63;">   
               <span style="font-family: 'Arial Narrow';font-size:18px;color:white;"><strong>Missing Premium Report</strong> </span>
			   <div align="left" style="hight:8px;background:#D2EA32;border:1px;"> &nbsp;
			   </div>
              </td>
            </tr>
         
    </table>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border: solid thin bleak" class="table-ss">
   <tr style="background:#DEE3EF;">
         	  <td height="21" align="right" style="font-family: 'Arial Narrow';font-size:14px; "><br/><strong>Staff Code ：</strong></td>
         	  <td height="21" align="left"><br/><input type="text" name="staffcode" id="staffcode"></td>
         	  <td height="21" align="right" style="font-family: 'Arial Narrow';font-size:14px; "><br/><strong>Client Name ：</strong></td>
         	  <td height="21" align="left"><br/><input type="text" name="clientname" id="clientname"></td>
              <td height="10" colspan="1" align="left"> 
              	
              </td>
            </tr>
       		 <tr style="background:#DEE3EF;">
              <td height="25" align="right" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Policy Number ：    </strong></td>
              <td height="25" align="left"><input type="text" name="policynos" id="policyno" ></td>
              <td height="25" align="right" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Principal ：   </strong></td>
              <td align="left" style="color:red">
              		<select name="selectprincipal" id="principal"  >
               			<option value="">Please Select Principal</option>
              		</select>
              </td>
              <td>&nbsp;</td>
            </tr>
       		 <tr style="background:#DEE3EF;">
              <td  height="25" align="right" style="font-family: 'Arial Narrow';font-size:14px; ">
              <strong>Period ：</strong>
              		
              </td>
              <td>
             	 <span onClick="check('stage1');"><input type="radio" name="code" checked="checked" value="1" id="stage1" />
              		<select id="lastday" >
              			<option value="30" >Last 30 days &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
              			<option value="60" >Last 60 days  </option>
              			<option value="90" >Last 90 days  </option>
              		</select></span>
              </td>
              <td colspan="3" >&nbsp;</td>
            </tr>
             <tr style="background:#DEE3EF;">
	             <td align="left">
	             </td>
	               <td align="left" >
           		   <span onClick="checkredio('stage2');"><input type="radio" name="code"  align="middle"  value="2" id="stage2" />
	             		<strong>From：</strong>
	             		<input name="text" type="text" id="start_date" size="13"  />&nbsp;&nbsp; 
           		    <img src="images/iconDate.gif" name="clear1"  vspace="0" border="0"   align="middle" id="clear1" style="height:18;width:18;"  onClick="return Calendar('start_date')">           		   </span>
	              </td>
	              <td   height="20" align="right" style="font-family: 'Arial Narrow';font-size:14px; "><strong>To ：</strong></td>
	              <td   align="left" >
	               	    <input id="end_date" type="text" name="it"  />&nbsp;&nbsp; 
	             		 <img src="images/iconDate.gif" name="clear1" id="clear2"  vspace="0" border="0"  align="middle"  style="height:18;width:18;"  onClick="return Calendar('end_date')">
	              </td>
             	 
             	  <td colspan="2" align="left" style="color:red"> </td>
            </tr>
             <tr style="background:#DEE3EF;">
              <td colspan="5" align="left" style="color:red"> </td>
            </tr>
            <tr style="background:#DEE3EF;">
              <td colspan="2" >
              <table border='0'  style="display:none">
				<tr>   
	 			<td>&nbsp;&nbsp;<Strong>Available Principals:</Strong></td>
				<td> </td>
				</tr>
				<tr>   
	 			<td>&nbsp;&nbsp;<Strong>1.	Link:</Strong></td>
				<td><Strong>Zurich International Life; Royal Skandia; Standard Life;Generali</Strong></td>
				</tr>
				<tr>   
	 			<td>&nbsp;&nbsp;<Strong>2.	Non-Link:</Strong></td>
				<td><Strong>Prudential;ACE</Strong></td>
				</tr>
				<tr>   
	 			<td>&nbsp;&nbsp;<Strong>3.	Link/Non-Link:</Strong></td>
				<td><Strong>Ageas; Aviva; AXA; AIA; Friends; Manulife; MassMutual</Strong></td>
				</tr>
			</table> 
              </td>
              <td >       
              <br/>      
              </td>
              <td align="center">
              <input id="searchs" name="search" type="button" value="Search"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
			   <input id="down" name="downs" type="button" value="Export">	
			   &nbsp;&nbsp;&nbsp;&nbsp;
			   <input id="helpid" name="help" type="button" value="Help" onclick="showhelp();"></td>
              <td >&nbsp;</td>
            </tr>
         
    </table>
    
  </div>
  <div id="content">
 
  <table width="100%"  border="1" cellpadding="0" cellspacing="0" class="table-ss" id="jqajax" >
      <tr id="title" class="title" style="background-color:#DEE3EF;" >
	        <td width="8%" align="center" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Staff Code</strong></td>
	        <td width="13%" align="center" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Principal</strong></td>
			<td width="10%" align="center" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Policy Owner Name</strong></td>
	        <td width="10%" align="center" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Policy Number</strong></td>
	        <td width="10%" align="center" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Modal Premium</strong></td>
	        <td width="11%" align="center" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Premium Due Date</strong></td>
	        <td width="15%" align="center" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Principal Remark</strong></td>
	        <td width="11%" align="center" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Last Update Date</strong></td> 
	        <td width="12%" align="center" style="font-family: 'Arial Narrow';font-size:14px; "><strong>Next Collection Date</strong></td>  
      </tr>
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
  
	<div id="helpdiv" class="center_div">
		<span><a href="javascript:void(0);" onclick="javascript:$(this).parent().parent().hide();">X</a>&nbsp;&nbsp;</span>
		<table cellspacing="0" cellpadding="0">
		<tr><td>Pick Up/Pick Up Card </td>	<td>信用卡不獲授權</td></tr>
		<tr><td>Invalid Card Number  </td>	<td>卡號無效</td></tr>
		<tr><td>Invalid Expiry Date </td>	<td>到期日失效</td></tr>
		<tr><td>Invalid Account </td>		<td>戶口不符</td></tr>
		<tr><td>Account Dose Not Existed</td><td>戶口並不存在</td></tr>
		<tr><td>DDA Terminated     </td>	 <td>直接付款授權終止</td></tr>
		<tr><td>Authority Cancelled</td>	 <td>授權取消</td></tr>
		<tr><td>Account Closed  </td>		 <td>戶口關閉</td></tr>
		<!-- <tr><td>Refer to Drawer </td>		 <td></td></tr>
		<tr><td>No Authority    </td>		 <td></td></tr>
		<tr><td>Refer to A/C Holder</td>	 <td></td></tr>
		<tr><td>Declined</td>				 <td></td></tr>
		<tr><td>Miscellaneous Bucket</td>	 <td></td></tr>
		<tr><td>D.A.U.E.</td>				 <td></td></tr>
		<tr><td>Insufficient Funds    </td>	 <td></td></tr>
		<tr><td>DA Stopped By Bank    </td>  <td>    </td></tr> 
		<tr><td>Do Not Honor</td>			 <td></td></tr> -->
		
	</table>
	</div>
  </body>
</html>
