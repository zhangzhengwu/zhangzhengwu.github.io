<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Query Access Card Request</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
	
  </head>
  <script type="text/javascript" >
  
  
  
  
  
  
  
  
  var pagenow=1;
  var totalpage=0;
  var email=null;
  $(function(){
	  $("#start_date,#end_date").val(getMonthBeforeDay(new Date()));
	  $(".inputstyle,.field input").attr("disabled",true);
	  
	  
	      	  //注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				search_access(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				search_access(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				search_access(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				search_access(pagenow);
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
  function search_access(pageNow){
	  
	  if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("Start Date can’t be later than End Date.");
				$("#start_date").focus();
				return false;
			}
		}
	  
	   $.ajax({
    		  url:"AccessCardServlet",
    		  type:"post",
    		  data:{"method":"select","startDate":$("#start_date").val(),
	    		  "endDate":$("#end_date").val(),"staffcode":$("#staffcode").val(),
	    		  "staffname":$("#staffname").val(),"refno":$("#refno").val(),
	    		  "status":$("#status").val(),"curretPage":pageNow},
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
    				  		+"</td><td>"+dataRole[0][i].staffcode
    				  		+"</td><td>"+dataRole[0][i].staffname
    				  		+"</td><td>"+dataRole[0][i].creator
    				  		+"</td><td>"+dataRole[0][i].createDate
    				  		+"</td><td>"+dataRole[0][i].reason
    				  		+"</td><td>"+dataRole[0][i].status
    				  		+"</td><td>"+dataRole[0][i].remark
    				  		+"</td><td><c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='detail(\""+dataRole[0][i].refno+"\");'>Detail</a></c:if>&nbsp;&nbsp;</td></tr>";
    				  		//<a href='javascript:void(0);' onclick='deletes(\""+dataRole[0][i].refno+"\");'>Delete</a></td></tr>";
    				  }
    				  $(".page_and_btn").show();
    				  $("#export").removeAttr("disabled");
    			  }else{
    				  html+="<tr class='tr_search'><td colspan='11' align='center' >"+"Sorry, there is no matching record."+"</td></tr>";
    				  $(".page_and_btn").hide();
    				  $("#export").attr("disabled",true);
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
   /**
        * 查看详细
        * */
       function detail(obj){
	   		email=null;
    	   $("#detail_shelter").show();
    	   $('#Medical').hide();
    	   $(".paymentAmount").attr("checked",false);
    	   $.ajax({
    		  url:"AccessCardServlet",
    		  type:"post",
    		  data:{"refno":obj,"method":"detail"},
    		  success:function(date){
    			  var dataRole=eval(date);
    			 	$("#refnos").empty().append(dataRole[0].refno);
    			 	$("#staffcodes").val(dataRole[0].staffcode);
    			  	$("#staffnames").val(dataRole[0].staffname);
    			  	$("#location").val(dataRole[0].location);
					$("#exitAccess").val(dataRole[0].historyno);
					$("#newAccess").val(dataRole[0].newno);
    			  	$("#reason").val(dataRole[0].reason);
    			  	$("#statuss").empty().append(dataRole[0].status);
    			  	$("#remark").val(dataRole[0].remark);
    			 
					if(dataRole[0].staffCard=="Y"){
						$("#check_staffcard").attr("checked",true).val("Y");
						if(dataRole[0].status=="Submitted"){
							$("#exitAccess,#newAccess").removeAttr("disabled");
						}
					}else{
						$("#check_staffcard").removeAttr("checked");
					}
    				if(dataRole[0].photoSticker=="Y"){
    					$("#check_photostick").attr("checked",true).val("Y");
    					//alert(dataRole[0].remark1);
    					if(""!=dataRole[0].remark1){
    					$("#photoPath").empty().append(""+dataRole[0].remark1.replace("\hkgnas11operdept$dept_sharestaffphoto","\\\\hkgnas11\\operdept$\\dept_share\\staffphoto\\"));
    					}
    					//$("#check_photostick").after("&nbsp;&nbsp;&nbsp;文件路径:"+dataRole[0].remark);
    				}else{
    					$("#check_photostick").removeAttr("checked");
    					$("#photoPath").empty();
    				}
    				
    				$("#pay_div,#pay_table").hide();
					if(dataRole[0].status=="Submitted"){
						$("#HKADM").show();//2014-06-24 King 临时放开HKADM权限给到 
						$("#ready,#completed").hide();
						$("#void").show();
					}else if(dataRole[0].status=="HKADM"){//HKADM
						email=getEmail(dataRole[0].staffcode,dataRole[0].userType);
						$("#HKADM,#completed").hide();
						$("#ready,#void").show();
					}else if(dataRole[0].status=="Ready"){//Ready
						$("#HKADM,#ready").hide();
						$("#completed,#method,#void,.paymentAmount,#pay_div,#pay_table,#saleno").attr("disabled",false).show();
						$("#paymentDate,#handled,#saleno").removeAttr("disabled").val("");
						$("#paymentDate").val(getMonthBeforeDay(new Date()));//默认当天
						$("#handled").val("${adminUsername}");
					}else if(dataRole[0].status=="VOID"){
						$("#HKADM,#ready,#completed,#void").hide();
					}else if(dataRole[0].status=="Completed"){//Completed
						$("#HKADM,#void,#ready").hide();
						$("#completed,.paymentAmount,#method,#pay_div,#pay_table,#saleno").attr("disabled",true).show();
						$("#paymentDate,#handled,#saleno").attr("disabled",true);
						$.ajax({
							url:"PaymentServlet",
							type:"post",
							data:{"method":"select","refno":obj},
							success:function(date){
								var dataRoles=eval(date);
								if(dataRoles==""){//
									alert("Obtain payment information abnormalities");
									return false;
								}else{
									
									$("#method").val(dataRoles[0].paymentMethod);
									$("[name='paymentAmount']:checkbox").each(function(){
										if(this.value==dataRoles[0].paymentAount){
											this.checked=true;
											return false;
										}
									});
									$("#paymentDate").val(dataRoles[0].paymentDate);
									$("#handled").val(dataRoles[0].handleder);
									$("#saleno").val(dataRoles[0].saleno);
								}
						
							},error:function(){
								alert("please select at least one Request option!");
	  			   				return false;
							}
						});
					}else if(dataRole[0].status=="Deleted"){
						$("#HKADM,#ready,#completed").hide();
					}
					
			/**  if(parseFloat(("${SystemAcceTime}").substring(11,13))>=12 || dataRole[0].status=="Ready" || dataRole[0].status=="Completed"){//当前时间超过十二点 	
	 	
				 	}else{
                 		alert("Normal operation time is after 12 o 'clock in the afternoon!");
                 		$("#HKADM,#ready,#completed").attr("disabled",true).hide();
                 		return false;
                 	}**/
    		  },error:function(){
    			   alert("Network connection is failed, please try later...");
	  			   return false;
    		  }
    	   });
    	
       }
  
   /**
        *HKADM
        * */
       function HKADM(){
	   
	   if($("#check_staffcard").attr("checked")){
			   if($("#exitAccess").val()==""){
				   alert("please input Exiting Access Card!");
				   $("#exitAccess").focus();
				   return false;
			   }
			  if($("#newAccess").val()==""){
				alert("please input new Access Card!");
				$("#newAccess").focus();
				  return false;
			   }
			  if($.trim($("#exitAccess").val())==$.trim($("#newAccess").val())){
				  alert("The newAccess cannot be the same as the ExitAccess!");
				  $("#newAccess").select();
				  return false;
			  }
			 
			   if($("#exitAccess").val().length!=8){
				  alert("The card number should be 8 digits");
				  $("#exitAccess").select();
				  return false;
			  }
			   if($("#newAccess").val().length!=8){
				    alert("The card number should be 8 digits");
				   $("#newAccess").select();
				  return false;
			  }
	   }
    	  if(confirm("Are you sure to HKADM?")){// 确认操作？
	    	 $.ajax({
	    		 url:"AccessCardServlet",
	    		 type:"post",
	    		 data:{"method":"HKADM","refno":$.trim($("#refnos").text()),"exitAccess":$("#exitAccess").val(),"newAccess":$("#newAccess").val(),"remark":$("#remark").val(),"type":"HKADM"},
	    		 success:function(date){
	    			 alert(date);
	    			 Cancel();
	    			 search_access(pagenow);
	    		 },error:function(){
	    			 alert("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
	    	 });
    	  }
       }
   

   
   /**
    * Ready
    * @return {TypeName} 
    */
    function Ready(){
    	
    	if($("#location").val()==""){
    		 alert("Lost receipt floors!");
    		 return false;
    	 }
    	
    	  if(confirm("Are you sure to Ready?")){// 确认操作？
	    	 $.ajax({
	    		 url:"AccessCardServlet",
	    		 type:"post",
	    		 data:{"method":"Ready","refno":$.trim($("#refnos").text()),"type":"Ready","remark":$("#remark").val(),
	    		 "to":email,
				 "localtion":$("#location").val(),
				 "types":($("#check_staffcard").attr("checked")?"staffcard":"")+($("#check_photostick").attr("checked")?"photosticker":"")},
	    		 success:function(date){
	    			//alert(sendMail(email,email_accessCard($("#location").val(),($("#check_staffcard").attr("checked")?"staffcard":"")+($("#check_photostick").attr("checked")?"photosticker":""))));
	    			 var result=$.parseJSON(date);
	    			 if(result.state=="success"){
	    				 alert("success");
	    			 }else{
	    				 alert(result.msg);
	    			 }
	    			 Cancel();
	    			 search_access(pagenow);
	    		 },error:function(){
	    			 alert("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
	    	 });
    	  }
       }
    
    function VOID(){
    	if($("#location").val()==""){
    		 alert("Lost receipt floors!");
    		 return false;
    	 }
    	if($("#remark").val()==""){
    		alert("Please state the reason why you cancel.");
    		$("#remark").focus();
    		return false;
    	}
	   	  if(confirm("Are you sure to VOID?")){// 确认操作？
	    	 $.ajax({
	    		 url:"AccessCardServlet",
	    		 type:"post",
	    		 data:{"method":"VOID","refno":$.trim($("#refnos").text()),"type":"VOID","remark":$("#remark").val()},
	    		 success:function(date){
	    			alert(date);
	    			 Cancel();
	    			 search_access(pagenow);
	    		 },error:function(){
	    			 alert("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
	    	 });
	   	  }
       }
    
    
    
    
    /**
    * Completed
    * @return {TypeName} 
    */
    function Completed(){
    	if($("#method").val()==""){
    		alert("please input Payment Method!");
    		$("#method").focus();
    		return false;
    	}
    	if($("#method").val()==""){
    		alert("please input Payment Method!");
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
    	
    	//alert($("#method").val()+"---"+$("#amount").val()+"---"+$("#paymentDate").val()+"---"+$("#handled").val());
    	
    	//return false;
    	
    	  if(confirm("Are you sure to Completed?")){// 确认操作？
	    	 $.ajax({
	    		 url:"AccessCardServlet",
	    		 type:"post",
	    		 data:{"method":"complete","refno":$.trim($("#refnos").text()),"type":"Completed",
	    		 		"paymethod":$("#method").val(),"payamount":$("#amount").val(),"payDate":$("#paymentDate").val(),
	    		 		"handle":$("#handled").val(),"savetype":"AccessCard","location":$("#location").val(),"staffname":$("#staffnames").val(),"saleno":$("#saleno").val()},
	    		 success:function(date){
	    			 alert(date);
	    			 Cancel();
	    			 search_access(pagenow);
	    		 },error:function(){
	    			 alert("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
	    	 });
    	  }
       }
    /**
        * 删除
        * @param {Object} obj
        */
       function deletes(obj){
        	if(confirm("Are you sure to Delete this record?")){// 确认删除？
    	   		 $.ajax({
	    		 url:"AccessCardServlet",
	    		 type:"post",
	    		 data:{"method":"delete","refno":obj,"type":"Deleted"},
	    		 success:function(date){
	    			 alert(date);
	    			  select_adminservice(pagenow);
	    		 },error:function(){
	    			alert("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
	    	 });
        	}
       }
         function Cancel(){
    	   $(".field input,select").not(".paymentAmount:checkbox").val("");
    	   $("[id^='check_']").attr("checked","");
    	   email=null;
    	   $("#detail_shelter").hide();
    	  $('#Medical').show();
       }
  
       function down_access(){
    	   if($(".page_and_btn").is(":hidden")){
    		   alert("Please query data first, and then do export related operation!");
    		   return false;
    	   }else{
    		   
    		   location.href="<%=basePath%>"+"AccessCardServlet?method=down&startDate="+$("#start_date").val()
    		                +"&endDate="+$("#end_date").val()+"&staffcode="+$("#staffcode").val()
	    		  			+"&staffname="+$("#staffname").val()+"&refno="+$("#refno").val()
	    		  			+"&status="+$("#status").val();
    	   }
       }
       
       function downPhoto(){
    	   //alert($("#photoPath").text());
    	   if($("#photoPath").text()==""){
    		   alert("文件路径为空!");
    		   return false;
    	   }
    	   location.href="<%=basePath%>"+"AccessCardServlet?method=downPhoto&photoPath="+$("#photoPath").text(); 
       }

  </script>
<body >
<div class="cont-info" id="Medical">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="Calendar('start_date')"  value=""/>
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text"  onClick="Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input name="text" type="text" id="staffcode" />
				</td>
				<td class="tagName">Staff Name：</td>
				<td class="tagCont">
					<input id="staffname" type="text" />
				</td>
			</tr>
			<tr>
				<td class="tagName">Ref.No：</td>
				<td class="tagCont">
					<input type="text" name="ordercode" id="refno">
				</td>
				<td class="tagName">Status：</td>
				<td class="tagCont">
					<select style="height:24px;" id="status">
			            <option value="">Please Select Status</option>
			            <option value="Submitted">Submitted</option>
			            <option value="HKADM">HKADM</option>
			            <option value="Ready">Ready</option>
			            <option value='Completed'>Completed</option>
			            <option value='VOID'>VOID</option>
		            </select>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
				<c:if test='${roleObj.search==1}'>
					<a class="btn" name="search" onclick="search_access(1)">
						<i class="icon-search"></i>
						Search
					</a>
				</c:if>
				<c:if test='${roleObj.export==1}'>
					<a class="btn" id="export" onclick="down_access();" >
						<i class="icon-download" disabled="disabled"></i>
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
				<th class="width_">Num</th>
				<th class="width_">Ref.No</th>
				<th class="width_">Staff Code</th>
				<th class="width_">Staff Name</th>
				<th class="width_">Creator</th>
				<th class="width_">CreateDate</th>
				<th class="width_">Reason</th>
				<th class="width_">Status</th>
				<th class="width_">Remark</th>
				<th class="width_">Operation</th>
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
<div class="e-container" id="detail_shelter" style="display: none;">
	<div class="info-form">
		<h4>Access Card Request</h4>
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
		</table>
	</div>
	<div class="info-form" id="divIdSelected">
		<h4>Request Options</h4>
		<table id="buttomTable">
			<tr>
				<td class="tagName">Staff Card</td>
				<td class="tagCont">
					<input id="check_staffcard" value="N"  type="checkbox"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Existing Access Card#(for internal use)</td>
				<td class="tagCont">
					<input type="text" id="exitAccess" maxlength="8" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">New Access Card#(for internal use)</td>
				<td class="tagCont">
					<input type="text" id="newAccess" maxlength="8" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Photo stick on card</td>
				<td class="tagCont">
					<input id="check_photostick" value="N"  type="checkbox"/>&nbsp;&nbsp;
					<a id="photoPath" onclick="downPhoto()"></a>
				</td>
			</tr>
			<tr>
				<td class="tagName">Reason</td>
				<td class="tagCont">
					<input id="reason" type="text" style="width:450;" maxlength="95" />
				</td>
			</tr>
			<tr>
				<td class="tagName">Status: </td>
				<td class="tagCont">
					<span id="statuss" style="float:left;padding-left: 5px;font-weight: bold;"></span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Remark:</td>
				<td class="tagCont">
					<textarea name="remark" id="remark"   maxlength="225"  rows="3" cols="120"></textarea>
				</td>
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
						<input type="checkbox"  name="paymentAmount" value="100"  class="paymentAmount"/>
						HKD 100.00
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
					<input type="text" id="paymentDate" onClick="Calendar('paymentDate')" value=""/>
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
		<button class="btn" id="HKADM" onclick="HKADM();">HKADM</button>
	</c:if>
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="ready" onclick="Ready();">Ready</button>
	</c:if>
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="void" onclick="VOID();">VOID</button>
	</c:if>
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="completed" onclick="Completed();">Completed</button>
	</c:if>
		<button class="btn" onclick="Cancel();">Cancel</button>
	</div>
</div>
</body>
</html>
