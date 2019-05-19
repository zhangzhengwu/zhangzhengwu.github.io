<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Query Request For Room Setting</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./css/Util.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
    <style type="text/css">
<!--

.page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}
.STYLE1 {
		font-size: 14px;
	font-weight: bold;
	color: #00305A;
}

tr#title{
	line-height:25px;
}
-->
    </style>
  </head>
  <script type="text/javascript" >
  var curPage=1;
  var totalPage=1;
  $(function(){
	    $("#start_date,#end_date").val(getMonthBeforeDay(new Date()));
 			//selects();
		  	//注册单击事件
		 	var da = new Date();
		     //注册单击事件
			//$("#start_date").val(getMonthBeforeDay(da));	
			//$("#end_date").val(getMonthBeforeDay(da));	
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
			
	  	$("#searchs").click(function(){
			selects();
		});
		
	});
	function selects(){
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
			url:"QueryRoomSettingServlet",
			data: {
				'query':"query",
				'start_Date':$("#start_date").val(),
				'end_Date':$("#end_date").val(),
				'eventDate':$("#eventDate").val(),
				'status':$("#status").val(),
				'refno':$("#refnos").val(),
				'staffcode':'${convoy_username}',
				
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
						html+="<tr id='select' title='"+i+"' style='line-height:20px;'><td align='center'>"+dataRole[0][i].refno+"</td><td align='center'>"+
						dataRole[0][i].staffcode+"</td><td align='center'>"+
						dataRole[0][i].staffname+"</td><td align='center'>"+
						dataRole[0][i].eventname+"</td><td align='center'>"+
						dataRole[0][i].eventDate+"</td><td align='center'>"+
						dataRole[0][i].remark+"</td><td align='center'>"+
						dataRole[0][i].status+"</td><td align='center'><a href='javascript:void(0);' onclick='Detail("+i+");' name ='1'>Detail</a>";
						
					    if(dataRole[0][i].status=="Submitted"){
							html+="&nbsp;&nbsp;<a href='javascript:void(0);' onclick='del("+i+");' name ='2'>Delete</a>";
						}
						
						html+="</td></tr>";
					}
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select' style='line-height:20px;'><td colspan='18' align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
				}	 page(dataRole[1],dataRole[2]);
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[1]);
					 $("#totalPage").empty().append(dataRole[2]);
			 }
		 });
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
	
    function Detail(i){
   		$("#overs").show();
   		$('#lists').hide();
   		var el=document.getElementById("overs");
		//el.style.opacity=5; //这里写出50/100主要是为了和ie的写法统一，便于一参数形式传入            
    	$.ajax({
				url: "QueryRoomSettingServlet",
				type:"post",
			 	data:{'detail':"detail",'refno':downs[0][i].refno},
		  	 	success:function(date){
					var d=eval(date);
					for(var i=0; i< d[0].length; i++){
						$("#refno").val(d[0][i].refno);
						$("#eventName1").val(d[0][i].eventname);
						$("#eventDate1").val(d[0][i].eventDate);
						$("#start_date1").val(d[0][i].startTime);
						$("#end_date1").val(d[0][i].endTime);
						var convoy = d[0][i].convoy.split("#");
						document.getElementById("R+S").checked = "";
						document.getElementById("T+V").checked = "";
						document.getElementById("R+S+T+V").checked = "";
						document.getElementById("Convoy13+Convoy14").checked="";
    					document.getElementById("Convoy17+Convoy18").checked="";
						for(var j=0; j< convoy.length-1; j++){
							document.getElementById(convoy[j]).checked = "checked";
							//$(document.getElementById(convoy[j])).parent().parent().css("font-weight","bold");
						}
					 
						var cp3 = d[0][i].cp3.split("#");
						document.getElementById("CP-01+").checked = "";
						document.getElementById("CP-02+").checked = "";
						document.getElementById("CP-03+").checked = "";
						document.getElementById("CP-04+").checked = "";
						document.getElementById("CP-05+").checked = "";
						document.getElementById("CP-06+").checked = "";
						for(var j=0; j< cp3.length-1; j++){
							document.getElementById(cp3[j]).checked = "checked";
						}
						$("#remarks").val(d[0][i].remark);
						/**if(d[0][i].status=="Submitted"){
							document.getElementById("Completed").disabled="";
							document.getElementById("Void").disabled="";
						}else {
							document.getElementById("Completed").disabled="disabled";
							document.getElementById("Void").disabled="disabled";
						}else if(d[0][i].status=="Void"){
							document.getElementById("Void").disabled="disabled";
							document.getElementById("Completed").disabled="";
						}**/
						$("#statusId").empty().append(d[0][i].status=="Submitted"?"Submitted":(d[0][i].status=="Completed"?"Completed":"Void"));
						//document.getElementById(d[0][i].status).type="hidden";
					}
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
			});
    }
    function del(i){
		  if(confirm("Are you sure to Delete this record?")){
			  $.ajax({
				 url:"QueryRoomSettingServlet",
				 type:"post",
				 data:{'del':"del",'refno':downs[0][i].refno},
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
    function hideview(){
    	$("#overs").hide();
    	$('#lists').show();
		selects();
    }
    function changeStatus(status){
    	if(confirm("Sure to "+status+" ?")){
    		  $.ajax({
				 url:"QueryRoomSettingServlet",
				 type:"post",
				 data:{'changeStatus':"changeStatus",'refno':$("#refno").val(),'status':status},
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
		 
    function Report(){ 
			window.location.href="ReportRoomSettingServlet?start_Date="+$("#start_date").val()+"&end_Date="+$("#end_date").val()+"&eventname="+$("#eventName").val();
	}
  </script>
<body style='overflow:auto;'>
<div class="cont-info" id="lists">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onclick="return Calendar('start_date')"/>
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it" onclick="return Calendar('end_date')"/>
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Event Date：</td>
				<td class="tagCont">
					<input type="text"  id="eventDate" onclick="return Calendar('eventDate')">
				</td>
				<td class="tagName">RefNo：</td>
				<td class="tagCont">
					<input type="text" name="" id="refnos">
				</td>
			</tr>
			<tr>
				<td class="tagName">Status：</td>
				<td class="tagCont" colspan="3">
					<select id="status">
		              	<option value="">Please select Status</option>
		              	<option>Submitted</option>
		              	<option>Void</option>
		              	<option>Completed</option>
		            </select>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
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
			<thead>
			<tr id="title">
				<th class="width_">Ref No</th>
				<th class="width_">Staff Code</th>
				<th class="width_">Staff Name</th>
				<th class="width_">Event Name</th>
				<th class="width_">Event Date</th>
				<th class="width_">Remarks</th>
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
<div class="e-container" id="overs" style="display: none;">
	<div class="info-form">
		<h4> Request for Room Setting</h4>
		<table>
			<tr>
				<td class="tagName">Event Name</td>
				<td class="tagCont" colspan="2">
					<input id="eventName1" disabled="disabled" type="text" class="txts"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Event Date</td>
				<td class="tagCont" colspan="2">
					<input type="text" disabled="disabled" id="eventDate1" class="txts"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Time</td>
				<td class="tagCont" colspan="2">
					From:<input type="text" disabled="disabled" id="start_date1" class="txts"/> 
					To:<input type="text" disabled="disabled" id="end_date1" class="txts"/>
				</td>
			</tr>
			<tr>
				<td class="tagName" rowspan="5">@CONVOY</td>
				<td class="mid">
					<input type="checkbox" disabled="disabled" id="R+S" value="R+S"/>
				</td>
				<td class="right">5/F R+S</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" disabled="disabled" id="T+V" value="T+V"/>
				</td>
				<td class="right">5/F T+V</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" disabled="disabled" id="R+S+T+V" value="R+S+T+V"/>
				</td>
				<td class="right">5/F R+S+T+V</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" id="Convoy13+Convoy14" disabled="disabled" value="Convoy 13 + Convoy 14" />
				</td>
				<td class="right">39/F Convoy 13 + Convoy 14</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" id="Convoy17+Convoy18" disabled="disabled" value="Convoy 17 + Convoy 18"/>
				</td>
				<td class="right">39/F Convoy 17 + Convoy 18</td>
			</tr>
	        
			<tr>
				<td class="tagName" rowspan="6">17/F CP3</td>
				<td class="mid">
					<input type="checkbox" disabled="disabled" id="CP-01+" value="CP-01+"/>
				</td>
				<td class="right">CP-01+</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" disabled="disabled" id="CP-02+" value="CP-02+"/>
				</td>
				<td class="right">CP-02+</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" disabled="disabled" id="CP-03+" value="CP-03+"/>
				</td>
				<td class="right">CP-03+</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" disabled="disabled" id="CP-04+" value="CP-04+"/>
				</td>
				<td class="right">CP-04+</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" disabled="disabled" id="CP-05+" value="CP-05+"/>
				</td>
				<td class="right">CP-05+</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" disabled="disabled" id="CP-06+" value="CP-06+"/>
				</td>
				<td class="right">CP-06+</td>
			</tr>
			
			<tr>
				<td class="tagName">Remarks</td>
				<td class="tagCont" colspan="2">
					<input type="text" disabled="disabled"  id="remarks" class="txts" maxlength="255" style="width:70%; min-width: 400px;"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Status</td>
				<td class="tagCont" colspan="2">
					<span id="statusId" style="float:left;padding-left: 5px;font-weight: bold;"></span>
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
