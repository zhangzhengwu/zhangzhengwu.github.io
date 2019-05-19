<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>QueryRecruitmentHR</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="./css/Util.js?ver=<%=(new Date()).getTime() %>"></script>

  </head>
  <script type="text/javascript" >
  var downs=null;
  var d=null;
  var curPage=1;
  var totalPage=1;
  var temp;
  $(function(){
  	  //var econvoy="${Econvoy}".split("-");
  	  
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
			data: {"date1":$("#start_date").val(),"date2":$("#end_date").val(),"staffcode":$("#staffcode").val(),"refno":$("#ordercode").val(),"staffname":$("#staffname").val(),"status":$("#status").val(),'pageIndex':curPage,"method":"findAll","medianame":$("#medianame").val()},
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
					    dataRole[0][i].position+"</td><td align='center'>"+
						dataRole[0][i].contactperson+"</td> <td align='center'>" +
						dataRole[0][i].contactemail+"</td><td align='center'>"+
						dataRole[0][i].chargecode+"</td><td align='center'>"+
						dataRole[0][i].chargename+"</td><td align='center'>"+
						dataRole[0][i].remark+"</td><td align='center'>"+
						dataRole[0][i].date+"</td><td align='center'>"+
						dataRole[0][i].createdate+"</td><td align='center'>"+
						dataRole[0][i].creater+"</td><td align='center'>"+
						dataRole[0][i].status+"</td><td align='center'>" +
						"<c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='Detail("+i+");' name ='1'>Detail</a></c:if>";
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
                $("#lists").hide();
                $(".product").remove();
                //除了cancle按钮以外,所有按钮在页面初始化时隐藏
                $("#Confirm").hide();
                $("#void").hide();
                $("#Completed").hide();
                $("#Scheduled").hide();
                $("#payId").attr("style","display:none");
                //状态为submitted
                if(temp[0].status=="Submitted"){
                       $("#void").show(); 
                       $("#Confirm").show();
                       $("#date").attr("disabled",false);
                  }
                  //状态为completed
                else if(temp[0].status=="Completed"){
                  $("#date").attr("disabled",true);
                  //显示付费信息 div
                  $("#payId").attr("style","display:block");
                  //赋值     
                  $("#saleno").val(temp[2].saleno);                
                  $("#spanMoeny").text("HDK"+temp[1].price);
                  $("#payDate").val(temp[2].paymentDate);
                  $("#handledId").val(temp[2].handleder);
                 $("#saleno,#payDate,#handledId").attr("disabled",true);
                 }
                 //状态为confirm
                else if(temp[0].status=="Confirmation Request"){
                   $("#date").attr("disabled",true);
                   $("#Scheduled").show();
                   $("#void").show();                  
                }  
                else if(temp[0].status=="Cancelled"){
                   $("#date").attr("disabled",true);
                }  
                //状态为Scheduled 
                else if(temp[0].status=="Scheduled"){
                    $("#date").attr("disabled",true);
                    //显示付费信息 div
                    $("#payId").attr("style","display:block");
                    $("#void").show();
                    $("#Completed").show();
                    //获取当前系统日期
                    var date=new Date();
                    var year=date.getFullYear();
                    var month=date.getMonth()+1;
                    var day=date.getDate();
                    var str=year+"-"+month+"-"+day;
                    $("#payDate").val(str);
                    //获取当前登录用户
                    $("#handledId").val(temp[0].creater);                 
                     //HKD赋值
                    $("#saleno").val(temp[2].saleno); 
                    $("#spanMoeny").text("HKD"+temp[1].price);
                    $("#moneyId").val(temp[1].price);
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
       //cancle 按钮事件     
       function hideview(){
            selects();
            $("#overs").hide();
            $("#lists").show();
       }
        //Confirm Request 按钮事件
       function Ready(){
           //判断刊登日期是否为空
           if($("#date").val()==""){
             alert("Please fill in the published date");
           }
           else{
              //判断填写的日期是否小于当前日期
             //获取当前系统日期
                    var date=new Date();
                    var year=date.getFullYear();
                    var month=date.getMonth()+1;
                    var day=date.getDate();
                    var str=year+"-"+month+"-"+day; 
             // if(str>$("#date").val()){
              if(1==2){
                 alert("The selected date is earlier than the current date");   
               }
               else{
                //把刊登日期存入订单表
                if(confirm('Are you sure Comfirmation Request ?')){
                     $.ajax({
                       url:"QueryRecruitmentHRServlet", 
                       type:"post",
                       data:{"refno":$("#refno").val(),"date":$("#date").val(),"method":"save","code":$("#code").val(),"Chargecode":$("#Chargecode").val()
						 ,"staffname":temp[0].staffname,"date1":getDate(),"date2":$("#date").val(),
						 "Person":temp[0].contactperson,
						 "email":temp[0].contactemail,"mediaName":temp[1].medianame}, 
                       success:function(date){
							 var result=$.parseJSON(date);
			    			 if(result.state=="success"){
			    				 alert("success");
			    			 }else{
			    				 alert(result.msg);
			    			 }
			    			 hideview();
                           /**if(date!="error"){
        	                          //发送邮件
                                      var data=eval(date);
                                        //data[0] 代表申请人的邮箱   data[1] 代表付款人的邮箱 
                                       var staffname=temp[0].staffname;
                                       var date2=$("#date").val();
                                       var date1=getDate();
                                       var Person=temp[0].contactperson;
                                       var email=temp[0].contactemail;
                                       var mediaName=temp[1].medianame;
                                       alert(sendMailForRecruitment(data[0],data[1]+";"+email,email_confirmation(staffname,date1,date2,Person,email,mediaName)));
                                       //刷新页面
                                       hideview();
	                        }else{
	                         alert("failed");
	                        }**/
                   } 
               });
                
                }
              
               
               }  
           }
       }
   //VOID Scheduled 按钮事件
   function Demo(status){
   if( confirm('Are you sure '+status+' ?')){
       $.ajax({
      url:"QueryRecruitmentHRServlet", 
      type:"post",
      data:{"refno":$("#refno").val(),"status":status,"method":"changeStatus"},
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
 //completed 按钮事件
 function getPayment(){
 
    //非空验证
     if($("#payDate").val()==""){     
         alert("Please Choose payDate");
         return;
     }else{
            //获取当前系统日期
         var date=new Date();
                    var year=date.getFullYear();
                    var month=date.getMonth()+1;
                    var day=date.getDate();
                    var str=year+"-"+month+"-"+day;
         if(str>$("#payDate").val()){
             alert("The selected date is earlier than the current date");
             return;
          }
      }if($("#handledId").val()==""){
          alert("Please input Handled");
           return;
       }
       if(confirm('Are you sure completed this order')){    
        $.ajax({
          url:"QueryRecruitmentHRServlet",
          type:"post",
          data:{"refno":$("#refno").val(),"staffname":$("#name").val(),"saleno":$("#saleno").val(),"paymentMethod":$("#paySelectId").val(),"paymentAount":$("#moneyId").val(),"payDate":$("#payDate").val(),"handled":$("#handledId").val(),"method":"savePayment","code":$("#code").val(),"Chargecode":$("#Chargecode").val()},
          success:function(date){
             if(date!="error"){
                 
                 //data[0] 代表申请人的邮箱   data[1] 代表付款人的邮箱 
                 var data=eval(date);
                  //alert(data);       
                  var staffname=temp[0].chargename;        
                  var date2=temp[0].date;
                  var Person=temp[0].contactperson;
                  var email=temp[0].contactemail;
                  var mediaName=temp[1].medianame;
                  var money=temp[1].price;
                  var staffcode=temp[0].chargecode;
          alert(sendMailForRecruitment(data[0],data[1]+";"+email,eamil_conpleted(money,staffname,staffcode,mediaName,date2,Person,email)));   
                hideview();
           }
            else{
                alert("failed");
              } 
      }
   });
       
       }
   
 }
   //获取第n个日期
   function reduceByTransDate(dateParameter,n) {
    var translateDate = "", dateString = "", monthString = "", dayString = "";
    translateDate = dateParameter.replace("-", "/").replace("-", "/"); 
    var newDate = new Date(translateDate);
    newDate = newDate.valueOf();
    newDate = newDate + n * 24 * 60 * 60 * 1000;
    newDate = new Date(newDate);
    //如果月份长度少于2，则前加 0 补位   
    if ((newDate.getMonth() + 1).toString().length == 1) {
       monthString = 0 + "" + (newDate.getMonth() + 1).toString();
    } else {
       monthString = (newDate.getMonth() + 1).toString();
    }
    //如果天数长度少于2，则前加 0 补位   
    if (newDate.getDate().toString().length == 1) {
      dayString = 0 + "" + newDate.getDate().toString();
    } else {
      dayString = newDate.getDate().toString();
    }
       dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;
    return dateString;
} 
   
   
   //获取当前日期
   function getDate(){
	   var this_date;//系统当前日期
	   var next_date;//发送邮件日期
	   var date=new Date();
	   var year=date.getFullYear();//获取年份
	   var month=date.getMonth()+1;//获取月份
	   var today=date.getDate();
	   var day=date.getDay();//获取当前的日
	  this_date=year+"-"+month+"-"+today;
	   if(day==5){
	    	//代表星期五  ,获取星期一的日期
	     next_date=reduceByTransDate(this_date,3);	
	    }
	    else if(day==6){
	    	//星期六
	    	next_date=reduceByTransDate(this_date,2);
	    }
	  else{
		  next_date=reduceByTransDate(this_date,1);
	  }
	  return next_date;
   }
   
     //数据导出
    function   export1(){
      var date1=$("#start_date").val();
      var date2=$("#end_date").val();
      var staffcode=$("#staffcode").val();
      var refno=$("#ordercode").val();
      var staffname=$("#staffname").val();
      var status=$("#status").val();
      var method="export1";
      location.href="<%=basePath%>"+"QueryRecruitmentHRServlet?date1="+date1+"&date2="+date2+"&staffcode="+staffcode+"&refno="+refno+"&staffname="+staffname+"&status="+status+"&method="+method;
    }
  </script>
<body>
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
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input type="text" name="clientcode" id="staffcode">
				</td>
				<td class="tagName">StaffName：</td>
				<td class="tagCont">
					<input type="text" name="clientname" id="staffname">
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
		             	<option value=" Confirmation Request"> Confirmation Request</option>
		             	<option value="Scheduled">Scheduled</option>
		             	<option value="Completed">Completed</option>
		             	<option value="Cancelled">Cancelled</option>
	            	</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Medianame：</td>
				<td class="tagCont">
					<input type="text" name="medianame" id="medianame">
				</td>
				<td class="tagCont" colspan="2">
					<c:if test="${roleObj.search==1}">
						<a class="btn" id="searchs" name="search">
							<i class="icon-search"></i>
							Search
						</a>
					</c:if>
					<c:if test="${roleObj.export==1}">
						<a class="btn" id="exp" onclick="export1()">
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
			<thead id="title">
			<tr>
				<th class="width_">Refno</th>  
		        <th class="width_">StaffCode</th>  
		        <th class="width_">StaffName</th> 
		        <th class="width_">Position</th> 
		        <th class="width_">ContactPerson</th> 
		        <th class="width_">ContactEmail</th> 
		        <th class="width_">ChargeCode</th> 
		        <th class="width_">ChargeName</th> 
		        <th class="width_">MediaName</th> 
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
					<input type="text" name="date" id="date"  class="inputstyle"  >
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
	<div class="info-form" id="divIdSelected" style="height: auto; max-height:40%;overflow: auto">
		<h4>Request Options</h4>
		<table id="leftTable">
			<tr>
				<td class="width_">Refno</td>
				<td class="width_">Mediacode</td>
				<td class="width_">Medianame</td>
				<td class="width_">Price</td>
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
					<label class="checkbox inline">
						<input type="checkbox" id="moneyId" checked="checked" disabled="disabled" class="paymentAmount"/>
						<span id = "spanMoeny">HKD</span>
					</label>
				</td>
			</tr>
			<tr>
				<td class="tagName">Payment Date</td>
				<td class="tagCont">
					<input type="text" id="payDate" class="inputstyle" onClick="Calendar('payDate')" />
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
	<c:if test="${roleObj.audit==1}">
		<button class="btn" id="Confirm" onClick="Ready()">Confirmation Request</button>
	</c:if>
	<c:if test="${roleObj.audit==1}">
		<button class="btn" id="Scheduled" onClick="Demo('Scheduled')">Scheduled</button>
	</c:if>
	<c:if test="${roleObj.audit==1}">
		<button class="btn" id="Completed"  onClick="getPayment()">Completed</button>
	</c:if>
	<c:if test="${roleObj.audit==1}">
		<button class="btn" id="void" onClick="Demo('VOID')">VOID</button>
	</c:if>
		<button class="btn" id="Canel" onClick="hideview();">Cancel</button>
	</div>
</div>
</body>
</html>
