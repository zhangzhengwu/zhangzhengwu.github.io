<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Query Key Request</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="css/Util.js"></script>
	<script src="css/date.js"  type="text/javascript" ></script>
  	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">
   
   
  <script type="text/javascript" >
  var pagenow=1;
  var totalpage=0;
      $(function(){
    	    $("#start_date,#end_date").val(getMonthBeforeDay(new Date()));
    	 // $(".field,:checkbox").attr("disabled",true);
    	  
    	  //注册单击事件
			$("#pre").bind("click", function() {//上一页
						pagenow = pagenow - 1;
						select_requestservice(pagenow);
					});
			$("#next").bind("click", function() {//下一页
						pagenow = pagenow + 1;
						select_requestservice(pagenow);
					});
			$("#first").bind("click", function() {//首页
						pagenow = 1;
						select_requestservice(pagenow);
					});
			$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				select_requestservice(pagenow);
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
      /**
       * 导出数据
       * */
      function down_adminservice(){
    	  location.href="../keyServlet?method=down&" +
    	  "startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+
    	  "&staffcode="+'${convoy_username}'+"&staffname="+$("#staffname").val()+"&refno="+$("#refno").val()+
	      "&status="+$("#status").val();
      }
      
      
      function select_requestservice(pageNow){
    	  $.ajax({
    		  url:"keyServlet",
    		  type:"post",
    		  data:{"method":"select","startDate":$("#start_date").val(),
	    		  "endDate":$("#end_date").val(),"staffcode":'${convoy_username}',
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
    				  		+"</td><td>"+dataRole[0][i].remarks
    				  		+"</td><td>"+dataRole[0][i].status
    				  		+"</td><td><a href='javascript:void(0);' onclick='detail(\""+dataRole[0][i].refno+"\");'>Detail</a> ";
    				  		if(dataRole[0][i].status=="Submitted"){
								html+="<a href='javascript:void(0);' onclick='deletes(\""+dataRole[0][i].refno+"\");'>Delete</a>";
							}
							
							html+="</td></tr>";
 
    				  	//<a href='javascript:void(0);' onclick='Completed(\""+dataRole[0][i].refno+"\");'>Completed</a>&nbsp;&nbsp;
    				 
    				  	
    				  	
    				  
    				  }
    				  $(".page_and_btn").show();
    				  $("#export").attr("disabled",false);
    			  }else{
    				  html+="<tr class='tr_search'><td colspan='9' align='center'>Sorry, there is no matching record.</td></tr>";
    				  $(".page_and_btn").hide();
    				  $("#export").attr("disabled",true);
    			  }
    			  $("#jqajax").append(html);
    			  page(pagenow,totalpage);
    		  },error:function(){
    			  alert("please select at least one Request option!");
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
    	   $("#overs").show();
    	   $('#Medical').hide();
    	   $(".paymentAmount").attr("checked",false);
    	   $("#pigenBoxs").attr("checked",false);
    	   $("#lokers").attr("checked",false);
    	   $("#deskDrawers").attr("checked",false);
    	   $.ajax({
    		  url:"keyServlet",
    		  type:"post",
    		  data:{"refno":obj,"method":"detail"},
    		  success:function(date){
    			  var dataRole=eval(date);
    			 	$("#refnos").val(dataRole[0].refno);
    			 	$("#staffcodes").val(dataRole[0].staffcode);
    			  	$("#staffnames").val(dataRole[0].staffname)
    			  	
    			  	$("#locations").val(dataRole[0].location)
    			  	if(dataRole[0].locker=="Y"){
						$("#lokers").attr("checked",true);  
    			  		$("#lokernos").val(dataRole[0].lockerno)
    			  	}
    			  	if(dataRole[0].deskDrawer=="Y"){
						$("#deskDrawers").attr("checked",true);  
    			  		$("#deskDrawernos").val(dataRole[0].deskDrawerno)
    			  	}
    			  	if(dataRole[0].pigenBox=="Y"){
    			  		$("#pigenBoxs").attr("checked",true);  
    			  		$("#pigenBoxnos").val(dataRole[0].pigenBoxno)
    			  	}
    			  	
					$("#remarks").val(dataRole[0].remarks);
    			  	$("#statusdetail").empty().append(dataRole[0].status);
						
    			 
	  				if(dataRole[0].status=="Submitted"){
	  					$("#ready").attr("disabled",false).show();
	  					$("#completed").attr("disabled",true);
	  				 	$("#pay_div,#pay_table").hide();
								
	  				} else if(dataRole[0].status=="Ready"){
	  					$("#completed").attr("disabled",false).show();
	  					$("#ready").attr("disabled",true);
	  					
	  					$("#pay_div,#pay_table").attr("disabled",false).show();
	  					$("#paymentDate,#handled").attr("disabled",false).css("border-bottom-color","blue").val("");
	  					$(".paymentAmount").attr("checked",false);
						$("#method").val("");
						
	  				} else if(dataRole[0].status=="Deleted"){
	  					$("#ready").hide();
	  					$("#completed").hide();
	  					$("#pay_div,#pay_table").attr("disabled",true).show();
	  					$("#paymentDate,#handled").attr("disabled",true);
	  				}
    			  	else if(dataRole[0].status=="Completed"){
		                 		$("#paymentDate,#handled").attr("disabled",false).css("border-bottom-color","");
		                 		$("#ready").hide();
								$("#completed").attr("disabled",true).show();
								$("#pay_div,#pay_table").attr("disabled",true).show();
								$.ajax({
									url:"PaymentServlet",
									type:"post",
									data:{"method":"select","refno":dataRole[0].refno},
									success:function(date){
										var dataRoles=eval(date);
										if(dataRoles==""){//
											alert("Obtain payment information abnormalities");
											return false;
										}else{
											$("#check_money").val(dataRoles[0].paymentAount);
											$("#span_money").empty().append(dataRoles[0].paymentAount);
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
								
								
		                 	}
    		  },error:function(){
    			   alert("please select at least one Request option!");
	  			   return false;
    		  }
    	   });
       }
       /**
        * 完成
        **/
       function Completed(){
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
    	  if(confirm("Are you sure to Complete this record?")){// 确认操作？
	    	 $.ajax({
	    		 url:"keyServlet",
	    		 type:"post",
	    		 data:{"method":"paymend","refno":$.trim($("#refnos").val()),"type":"Completed"},
	    		 success:function(date){
	    			 alert(date);
	    			 Cancel();
	    			 select_requestservice(pagenow);
	    		 },error:function(){
	    			 alert("please select at least one Request option!");
	  			   	 return false;
	    		 }
	    	 });
    	  }
       } 
       /**
        * 完成HKADM
        *
       function hkadm(){
    	  if(confirm("Are you sure to HKADM?")){// 确认操作？
	    	 $.ajax({
	    		 url:"keyServlet",
	    		 type:"post",
	    		 data:{"method":"complete","refno":$.trim($("#refnos").val()),"type":"HKADM"},
	    		 success:function(date){
	    			 alert(date);
	    			 Cancel();
	    			 select_requestservice(pagenow);
	    		 },error:function(){
	    			 alert("please select at least one Request option!");
	  			   	 return false;
	    		 }
	    	 });
    	  }
       } */
       /**
        * 完成READY
        * */
       function Ready(){
    	  if(confirm("Are you sure to Ready?")){// 确认操作？
	    	 $.ajax({
	    		 url:"keyServlet",
	    		 type:"post",
	    		 data:{"method":"complete","refno":$.trim($("#refnos").val()),"type":"Ready"},
	    		 success:function(date){
	    			 alert(date);
	    			 Cancel();
	    			 select_requestservice(pagenow);
	    		 },error:function(){
	    			 alert("please select at least one Request option!");
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
        	if(confirm("Are you sure to Delete?")){// 确认删除？
    	   		 $.ajax({
	    		 url:"keyServlet",
	    		 type:"post",
	    		 data:{"method":"delete","refno":obj},
	    		 success:function(date){
	    			 alert(date);
	    			 select_requestservice(pagenow);
	    		 },error:function(){
	    			 alert("please select at least one Request option!");
	  			   	 return false;
	    		 }
	    	 });
        	}
       }
       function Cancel(){
    	   $(".field input,select").val("");
    	   $("[id^='check_']").attr("checked","");
    	   
    	   $("#overs").hide();
    	   $('#Medical').show();
       }
  </script>
   <style type="text/css">
	.tagCbox{width: 10%!important;}
	.tagText{width: 15%!important;}
	.tagInput{width: 60%!important;}
</style>
  
  </head>

<body >
<div class="cont-info" id="Medical">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date:</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date:</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it" onClick="return Calendar('end_date')"/>
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">Ref.No:</td>
				<td class="tagCont">
					<input type="text" name="ordercode" id="refno">
				</td>
				<td class="tagName">Status:</td>
				<td class="tagCont">
					<select style="height:24px;" id="status">
			            <option value="">Please Select Status</option>
			            <option value="Submitted">Submitted</option>
			            <option value='Ready'>Ready</option>
			            <option value='Completed'>Completed</option>
		            </select>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
					<a class="btn" onclick="select_requestservice(1);">
						<i class="icon-search"></i>
						Search
					</a>
		            <input type="hidden" id="staffcode"  />
		            <input type="hidden" id="staffname"> 
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_5">Num</th>
				<th class="width_10">Ref.No</th>
				<th class="width_10">Staff Code</th>
				<th class="width_10">Staff Name</th>
				<th class="width_10">Creator</th>
				<th class="width_10">CreateDate</th>
				<th class="width_25">Remark</th>
				<th class="width_10">Status</th>
				<th class="width_10">Operation</th>
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
		<h4>Key Request</h4>
		<table id="topTable">
			<tr class="head_bottom"  style="display:none;">
				<td class="tagName">Ref.number</td>
				<td class="tagCont">
					<input name="refnos" id="refnos"  type="text" disabled="disabled" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Code</td>
				<td class="tagCont">
					<input name="staffcode" id="staffcodes"  type="text" disabled="disabled" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Name</td>
				<td class="tagCont">
					<input name="staffname" id="staffnames" type="text" disabled="disabled" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont">
					<select id="locations" disabled="disabled" >
		              	<option value="">请选择</option>
		              	<option value="@CONVOY">@CONVOY</option>
		              	<option value="CP3">CP3</option>
	              	</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Status</td>
				<td class="tagCont">
					<span id="statusdetail" style="font-weight: bold;"  disabled="disabled"></span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divIdSelected">
		<h4>Request Options</h4>
		<table id="buttomTable">
			<tr>
				<td class="tagName">Locker Key</td>
				<td class="tagCbox">
					<input id="lokers" name="loker" type="checkbox" disabled="disabled" />
				</td>
				<td class="tagText">Locker#</td>
				<td class="tagInput">
					<input id="lokernos" name="lokerno"   type="text" disabled="disabled" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Desk Drawer Key</td>
				<td class="tagCbox">
					<input name="deskDrawer" id="deskDrawers" disabled="disabled"  type="checkbox"/>
				</td>
				<td class="tagText">Desk Drawer#</td>
				<td class="tagInput">
					<input name="deskDrawerno" id="deskDrawernos" disabled="disabled"  type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Pigeon Box Key</td>
				<td class="tagCbox">
					<input id="pigenBoxs"  name="pigenBox" disabled="disabled"  type="checkbox"/>
				</td>
				<td class="tagText">Pigeon Box#</td>
				<td class="tagInput">
					<input name="pigenBoxno" id="pigenBoxnos" disabled="disabled"  type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Remarks</td>
				<td class="tagCont" colspan="3">
					<input type="text" name="remark"  disabled="disabled"  id="remarks" size="80" class="inputstyle" maxlength="80" style="width: 70%f; min-width: 400px;">
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
		           	</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Payment Amount</td>
				<td class="tagCont">
					<input type="hidden" id="amount"/>
					<label class="inline checkbox">
						<input type="checkbox"  name="paymentAmount" value="" id="check_money"  class="paymentAmount"/> HKD
						<span id="span_money"></span>
					</label>
					<label class="inline checkbox">
						<input type="checkbox" name="paymentAmount" value="0" class="paymentAmount"/> FOC
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
		<button class="btn" onclick="Cancel();">Cancel</button>
	</div>
</div>
</body>
</html>
