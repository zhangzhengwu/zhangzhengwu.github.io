<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>QueryRecruitment</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./css/Util.js?ver=<%=new Date() %>"></script>
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
  var temp;
  	    var econvoy="${Econvoy}".split("-");
  function resize(){
	  //$("#overs,#divId,#divIdSelected,.span_menu,#Medical").width($("body").width()).css("left",0); 
  }
  
  $(function(){
	  window.onresize=resize;
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
  	  
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
	
	/**************************************************selects Click*****************************************************/
	$("#searchs").click(function(){
		selects();
	});

  });
  
  
  	function selects(){
                //时间判断
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
			url:"QueryRecruitmentHRServlet",
			data: {"medianame":"","date1":$("#start_date").val(),"date2":$("#end_date").val(),"staffcode":econvoy[0],"refno":$("#ordercode").val(),"staffname":"","status":$("#status").val(),'pageIndex':curPage,"method":"findAll"},
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
					curPage=dataRole[2];
	 			   	totalPage=dataRole[1];
	 			   
					for(var i=0;i<dataRole[0].length;i++){
						html+="<tr id='select' title='"+i+"' style='line-height:20px;'><td align='center'>"+
						dataRole[0][i].refno+"</td><td align='center'>"+
						dataRole[0][i].staffcode+"</td><td align='center'>"+
						dataRole[0][i].staffname+"</td><td align='center'>"+
						dataRole[0][i].usertype+"</td><td align='center'>"+
					    dataRole[0][i].position+"</td><td align='center'>"+
						dataRole[0][i].contactperson+"</td> <td align='center'>" +
						dataRole[0][i].contactemail+"</td><td align='center'>"+
						dataRole[0][i].chargecode+"</td><td align='center'>"+
						dataRole[0][i].chargename+"</td><td align='center'>"+
						dataRole[0][i].date+"</td><td align='center'>"+
						dataRole[0][i].createdate+"</td><td align='center'>"+
						dataRole[0][i].creater+"</td><td align='center'>"+
						dataRole[0][i].status+"</td><td align='center'><a href='javascript:void(0);' onclick='Detail("+i+");' name ='1'>Detail</a>&nbsp;";
						if(dataRole[0][i].status=='Submitted'){
	  					 	html+="/&nbsp;<a href='javascript:void(0);' onclick='del("+i+");'>Delete</a>&nbsp;";
	  					 }
							html+="</td></tr>";
					}
						$(".page_and_btn").show();
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select' style='line-height:20px;'><td colspan='18' align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
				}	   
				     page(dataRole[2],dataRole[1]);	
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[2]);
					 $("#totalPage").empty().append(dataRole[1]);
				 	 $("tr[id='select']:odd").css("background","#F0F0F0");
	                 $("tr[id='select']:even").css("background","#COCOCO");
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
      $.ajax({
         url:"QueryRecruitmentHRServlet", 
         type:"post",
         data:{"refno":downs[0][i].refno,"method":"detial"},
         success:function(date){
            var ht="";
            temp=eval(date);
            if(temp!="error"){
                $("#overs").show();
                $("#Medical").hide();
                $(".product").remove();
                //除了cancle按钮以外,所有按钮在页面初始化时隐藏
                $("#Confirm").hide();
                $("#void").hide();
                $("#Completed").hide();
                $("#Scheduled").hide();
                $("#RefusedScheduled").hide();
                $("#payId").attr("style","display:none");
                  //状态为completed或者Scheduled
                if(temp[0].status=="Completed"||temp[0].status=="Scheduled"){
                  $("#date").attr("disabled",true);
                  //显示付费信息 div
                  $("#payId").attr("style","display:block");
                  //赋值     
                  $("#saleno").val(temp[2].saleno);                
                  $("#spanMoeny").text("HKD"+temp[1].price);
                  $("#payDate").val(temp[2].paymentDate);
                  $("#handledId").val(temp[2].handleder);
                 $("#saleno,#payDate,#handledId").attr("disabled",true);
                 } 
                  //状态为confirm
                else if(temp[0].status=="Confirmation Request"){
                   $("#date").attr("disabled",true);
                   $("#Scheduled").show();
                   $("#RefusedScheduled").show();
//                   $("#void").show();   
//				   $("#payId").attr("style","display:block");   
				               
                }    
                else if(temp[0].status=="Cancelled"){
  					$("#date").attr("disabled",true);
                }  
                                         
                //显示订单信息
                 $("#refno").val(temp[0].refno);
                 $("#name").val(temp[0].staffname);
                 $("#code").val(temp[0].staffcode);
                 $("#position").val(temp[0].position);
                 $("#dstatus").val(temp[0].status);
                 $("#date").val(temp[0].date);
                 $("#person").val(temp[0].contactperson);
                 $("#email").val(temp[0].contactemail);
                 $("#Chargecode").val(temp[0].chargecode);
                 $("#ChargeName").val(temp[0].chargename);
                 //显示订单详细信息     
                 ht="<tr class='product'><td>"+temp[1].refno+"</td><td>"+temp[1].mediacode+"</td><td>"+temp[1].medianame+"</td><td>"+temp[1].price+"</td></tr>"; 
                 $("#leftTable").append(ht);
            }else{
               alert("system  Date error");
            }      
           
        }
       }); 
     }
     
     
    function del(i){
	  if(confirm('Are you sure delete ? ')){  
			$.ajax({
				type:"post",
				url: "QueryRecruitmentHRServlet",
				data:{"method":"del","refno":downs[0][i].refno},
				success: function(date){
				var dataRole = eval(date);
					if(dataRole > 0){
						alert("删除成功");
						location.reload();
					}else{
						alert("删除失败");
						return;
					}
				}
			});
			return false;
			}
	  	      
	  	      
	  }
 
       //cancle 按钮事件     
       function hideview(){
            selects();
            $("#overs").hide();
            $("#Medical").show();
       }
      
  //Confirm按钮事件
   function Confirm(buttonval){
   if( confirm('Are you sure '+buttonval+' ?')){
       $.ajax({
      url:"QueryRecruitmentHRServlet", 
      type:"post",
      data:{"refno":$("#refno").val(),"status":buttonval,"method":"changeStatus"},
      success:function(date){
      if(date=="success"){
           alert("success");
                  hideview();
             }
             else{
                  alert("failed");
                }
      }
     
     });
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
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it"  onClick="return Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr style="display: none;">
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input type="text" name="clientcode" id="staffcode">
				</td>
				<td class="tagName">Staff Name：</td>
				<td class="tagCont">
					<input type="text" name="clientname" id="staffname">
				</td>
			</tr>
			<tr>
				<td class="tagName">Order Code：</td>
				<td class="tagCont">
					<input type="text" name="ordercode" id="ordercode">
				</td>
				<td class="tagName">Status：</td>
				<td class="tagCont">
					<select id="status">
		             	<option value="">Please Select Status</option>
		             	<option value="Submitted" >Submitted</option>
		             	<option value=" Confirmation Request"> Confirmation Request</option>
		             	<option value="Scheduled">Scheduled</option>
		             	<option value="Completed">Completed</option>
		             	<option value="Cancelled">Cancelled</option>
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
			<thead id="title">
			<tr>
				<th class="width_">Refno</th>
				<th class="width_">StaffCode</th>
				<th class="width_">StaffName</th>
				<th class="width_">UserType</th>
				<th class="width_">Position</th>
				<th class="width_">ContactPerson</th>
				<th class="width_">ContactEmail</th>
				<th class="width_">ChargeCode</th>
				<th class="width_">ChargeName</th>
				<th class="width_">Date</th>
				<th class="width_">CreateDate</th>
				<th class="width_">Creater</th>
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
<div class="e-container" id="overs" style="display: none;">
	<div class="info-form">
		<h4>Recruitment Request</h4>
		<table>
			<tr>
				<td class="tagName">Refno：</td>
				<td class="tagCont">
					<input type="text" name="dordercode" id="refno" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Name：</td>
				<td class="tagCont">
					<input type="text" name="dclientname" id="name" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input type="text" name="dclientcode" id="code" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Position：</td>
				<td class="tagCont">
					<input type="text" name="dlocation" id="position" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			
			<tr>
				<td class="tagName">Date：</td>
				<td class="tagCont">
					<input type="text" name="date" id="date"  class="inputstyle" onClick="return Calendar('date')">
				</td>
			</tr>
			<tr>
				<td class="tagName">ConstactPerson：</td>
				<td class="tagCont">
					<input type="text" name="person" id="person" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">ConstactEmail：</td>
				<td class="tagCont">
					<input type="text" name="email" id="email" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">ChargeCode：</td>
				<td class="tagCont">
					<input type="text" name="Chargecode" id="Chargecode" disabled="disabled" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">ChargeName：</td>
				<td class="tagCont">
					<input type="text" name="ChargeName" id="ChargeName" disabled="disabled" class="inputstyle">
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
		<h4>Request Options</h4>
		<table id="leftTable">
			<tr>
				<td class="wdith_5">Refno</td>
				<td class="wdith_15">Mediacode</td>
				<td class="wdith_15">Medianame</td>
				<td class="wdith_10">Price</td>
			</tr>
		</table>	
	</div>
	<div class="info-form" id="payId">
		<h4>Payment(for internal use)</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Payment Method</td>
				<td class="tagCont">
					<select id="paySelectId">
	             		<option value="Deduct From Commission">Deduct From Commission</option>
	             	</select>
	             	<input type="text"  id="saleno" name="saleno"  class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Payment Amount</td>
				<td class="tagCont">
					<label class="inline checkbox">
						<input type="checkbox" id="moneyId" checked="checked" disabled="disabled" class="paymentAmount"/>
						<span id = "spanMoeny">HKD</span>
					</label>
				</td>
			</tr>
			<tr>
				<td class="tagName">Payment Date</td>
				<td class="tagCont">
					<input type="text" id="payDate" class="inputstyle" onClick="return Calendar('payDate')" />
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
	<div class="btn-board">
		<button class="btn" name ="bbb" id="Confirm">Confirmation Request</button>
		<button class="btn" name ="zzz" id="Scheduled" onClick="Confirm('Scheduled')">Confirm</button>
		<button class="btn" id="RefusedScheduled" onClick="Confirm('Cancelled')">Refused Scheduled</button>
		<button class="btn" name ="aaa" id="Completed">Completed</button>
		<button class="btn" name ="ddd" id="void">VOID</button>
		<button class="btn" name ="ccc" id="Canel" onClick="hideview();">Cancel</button>
	</div>
</div>  
</body>
</html>
