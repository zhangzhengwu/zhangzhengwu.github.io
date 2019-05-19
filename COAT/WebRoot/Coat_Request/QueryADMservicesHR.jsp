<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Query ADMSERVICES Request</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	
   
   
  <script type="text/javascript" >
  var pagenow=1;
  var totalpage=0;
      $(function(){
    	    $("#start_date,#end_date").val(getMonthBeforeDay(new Date()));
    	  $(".field,:checkbox").attr("disabled",true);
    	  
    	  //注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				select_adminservice(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				select_adminservice(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				select_adminservice(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				select_adminservice(pagenow);
			});
    	  
    	  
      });
      /**
       * 导出数据
       * */
      function down_adminservice(){
    	  location.href="<%=basePath%>"+"admservice/AdminServicesServlet?method=down&" +
    	  "startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+
    	  "&staffcode="+$("#staffcode").val()+"&staffname="+$("#staffname").val()+"&refno="+$("#refno").val()+
	      "&status="+$("#status").val()+"&location="+$("#location").val()+"&requestType="+$("#requestType").val();
      }
      
      
      function select_adminservice(pageNow){
    	  
    	  if($("#start_date").val()!="" && $("#end_date").val()!=""){
			if($("#start_date").val()>$("#end_date").val()){
				alert("Start Date can’t be later than End Date.");
				$("#start_date").focus();
				return false;
			}
		}
    	  $.ajax({
    		  url:"admservice/AdminServicesServlet",
    		  type:"post",
    		  data:{"method":"select","startDate":$("#start_date").val(),
	    		  "endDate":$("#end_date").val(),"staffcode":$("#staffcode").val(),
	    		  "staffname":$("#staffname").val(),"refno":$("#refno").val(),
	    		  "status":$("#status").val(),"location":$("#location").val(),"requestType":$("#requestType").val(),"curretPage":pageNow},
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
    				  		+"</td><td>"+dataRole[0][i].remark
    				  		+"</td><td>"+dataRole[0][i].status
    				  		+"</td><td><c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='detail(\""+dataRole[0][i].refno+"\");'>Detail</a></c:if>&nbsp;&nbsp;</td></tr>";
    				  		//<a href='javascript:void(0);' onclick='deletes(\""+dataRole[0][i].refno+"\");'>Delete</a></td></tr>";
    				  }
    				  $(".page_and_btn").show();
    				  $("#export").attr("disabled",false);
    			  }else{
    				  html+="<tr class='tr_search'><td colspan='9' align='center' >"+"Sorry, there is no matching record."+"</td></tr>";
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
    	   $("#detail_shelter").show();
    	   $("#detail_shelter input[type='text']").val("");
    	   $("#detail_shelter input[type='checkbox']").removeAttr("checked");
    	   $("#detail_shelter select").val("");
    	   $("#Medical").hide();
    	$("#detail_shelter input[type='text']").attr("disabled",true);
    	   $.ajax({
    		  url:"admservice/AdminServicesServlet",
    		  type:"post",
    		  beforeSend: function(){
	    			  parent.showLoad();
	    	  },
	    	  complete: function(){
	    		  parent.closeLoad();
	    	  },
    		  data:{"refno":obj,"method":"detail"},
    		  success:function(date){
    			  var dataRole=eval(date);
    			 	$("#refnos").empty().append(dataRole[0].refno);
    			 	$("#staffcodes").val(dataRole[0].staffcode);
    			  	$("#staffnames").val(dataRole[0].staffname)
					$("#remarks").val(dataRole[0].remark);
    			  	$("#statuss").empty().append(dataRole[0].status);
    			  	$("#select_location").val(dataRole[0].location);
    			  	$("[class^='request']").css("font-weight","");
    			  	
    			  	$("#seatNumber").val(dataRole[0].seatNumber);
					$("#passwords").val(dataRole[0].phonePas);
					$("#forgetExtension").val(dataRole[0].forgetExtenmsion);
					if(dataRole[0].phoneType=="4019"){
						$("#formCheckbox4019").attr("checked",true);
						$("#formCheckbox4010").attr("checked",false);
						$(".formImg[value='4019']").css("border","1px solid gray");//border: 1px solid red;
						$(".formImg[value='4010']").css("border","0px solid red");//border: 1px solid red;
					}else if(dataRole[0].phoneType=="4010"){
						$("#formCheckbox4019").attr("checked",false);
						$("#formCheckbox4010").attr("checked",true);
						$(".formImg[value='4010']").css("border","1px solid gray");//border: 1px solid red;
						$(".formImg[value='4019']").css("border","0px solid red");//border: 1px solid red;
					} 
					
					if(dataRole[0].forgetExtenmsion!=""){
						$("#check_3").attr("checked",true);
					}
					
					if(dataRole[0].fluorTube=="Y"){
						$("#check_changeTube").attr("checked",true);
						$("#floor1").val(dataRole[0].floor);
						$("#seat").val(dataRole[0].seat);
						$(".request_fluorTube").css("font-weight","bold");
					}
    				if(dataRole[0].phoneRepair=="Y"){
    					$("#check_phone1").attr("checked",true);
    					$("#phone1").val(dataRole[0].phoneNumber);
    					$(".request_repair").css("font-weight","bold");
    				}
					if(dataRole[0].phoneRpass=="Y"){
    					$("#check_phone2").attr("checked",true);
    					$("#phone2").val(dataRole[0].phoneNumber2);
    					$(".request_reset").css("font-weight","bold");
    				}
					if(dataRole[0].copierRepair=="Y"){
    					$("#check_copier").attr("checked",true);
    					$("#floor2").val(dataRole[0].floor2);
    					$("#copier").val(dataRole[0].copier);
    					$(".request_copier").css("font-weight","bold");
    				}
					
	  				if(dataRole[0].officeMaintenance=="Y"){
	  					$("#check_other").attr("checked",true);
	  					$("#floor3").val(dataRole[0].floor3);
    					$("#descript").val(dataRole[0].description);
    					$(".request_other").css("font-weight","bold");
	  				}
	  				if(dataRole[0].status=="Submitted"){
	  					$("#completed").attr("disabled",false).show();
	  					$("#void").show();
	  				}
	  				else if(dataRole[0].status=="Completed"){
	  					$("#completed").attr("disabled",true);
	  					$("#void").hide();
	  				}else if(dataRole[0].status=="Deleted"){
	  					$("#completed").hide();
	  				}
    			  
  				
  				/**  if(1==1||parseFloat(("${SystemAcceTime}").substring(11,13))>=12 || dataRole[0].status=="Ready"|| dataRole[0].status=="Completed"){//当前时间超过十二点 	
	 	
				 	}else{
                 		alert("Normal operation time is after 12 o 'clock in the afternoon!");
                 		$("#completed").attr("disabled",true).hide();
                 		return false;
                 	}**/
	  				
    		  },error:function(){
    			   alert("Network connection is failed, please try later...");
	  			   return false;
    		  }
    	   });
       }
       /**
        * 完成
        * */
       function Completed(){
    	  if(confirm("Are you sure to Complete?")){// 确认操作？
	    	 $.ajax({
	    		 url:"admservice/AdminServicesServlet",
	    		 type:"post",
	    		  beforeSend: function(){
	    			  parent.showLoad();
		    	  },
		    	  complete: function(){
		    		  parent.closeLoad();
		    	  },
	    		 data:{"method":"complete","refno":$.trim($("#refnos").text())},
	    		 success:function(date){
	    				var result=$.parseJSON(date);
	    					alert(result.msg);
	    				if(result.state=="success"){
			    			Cancel();
			    			select_adminservice(pagenow);
	    				}
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
        	if(confirm("Are you sure to Delete?")){// 确认删除？
    	   		 $.ajax({
	    		 url:"admservice/AdminServicesServlet",
	    		 type:"post",
	    		  beforeSend: function(){
	    			  parent.showLoad();
		    	  },
		    	  complete: function(){
		    		  parent.closeLoad();
		    	  },
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
    	   $(".field input,select").not($("#Medical *")).val("");
    	   $("[id^='check_']").attr("checked","");
    	   
    	   $("#detail_shelter").hide();
    	   $('#Medical').show();
       }
       
      function VOID(){
    	if($("#select_location").val()==""){
    		 alert("Lost receipt floors!");
    		 return false;
    	 }
    	  if(confirm("Are you sure to VOID?")){// 确认操作？
	    	 $.ajax({
	    		 url:"admservice/AdminServicesServlet",
	    		 type:"post",
	    		  beforeSend: function(){
	    			  parent.showLoad();
		    	  },
		    	  complete: function(){
		    		  parent.closeLoad();
		    	  },
	    		 data:{"method":"VOID","refno":$.trim($("#refnos").text()),"type":"VOID"},
	    		 success:function(date){
	    			alert(date);
	    			 Cancel();
	    			 select_adminservice(pagenow);
	    		 },error:function(){
	    			 alert("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
	    	 });
    	  }
       }
  </script>
   <style type="text/css">
	.tagCbox{width:10%!important;}
    .tagText{width:15%!important;}
    .tagInput{width:60%!important;}
	</style>
  
  </head>

<body >
<div class="cont-info" id="Medical">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date:</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="Calendar('start_date')" />
               		<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">EndDate:</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it" onClick="Calendar('end_date')"/>
               		<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName">StaffCode:</td>
				<td class="tagCont">
					<input name="text" type="text" id="staffcode"  />
				</td>
				<td class="tagName">StaffName:</td>
				<td class="tagCont">
					<input type="text" id="staffname">
				</td>
			</tr>
			<tr>
				<td class="tagName">Ref.No:</td>
				<td class="tagCont">
					<input type="text" name="ordercode" id="refno">
				</td>
				<td class="tagName">Status: </td>
				<td class="tagCont">
					<select id="status">
		                <option value="">Please Select Status</option>
		                <option value="Submitted">Submitted</option>
		                <option value='Completed'>Completed</option>
                	</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Request Options: </td>
				<td class="tagCont">
					<select id="requestType">
	              		<option value="">please select Request Options</option>
	              		<option value="fluorTube">Change of Fluorescent Tube</option>
	              		<option value="phoneRepair">Desk Phone Repair</option>
	              		<option value="PhoneRpass">Desk Phone Reset Password</option>
	              		<option value="copierRepair">Copier Repair</option>
	              		<option value="officeMaintenance">Other</option>
           			</select>
				</td>
				<td class="tagName">Location:</td>
				<td class="tagCont">
					<select id="location">
	              		<option value="">please select Location</option>
	              		<option value="@CONVOY">@CONVOY</option>
	              		<option value="CP3">CP3</option>
              		</select>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
				<c:if test='${roleObj.search==1}'>
					<a class="btn" onclick="select_adminservice(1);">
						<i class="icon-search"></i>
						Search
					</a>
				</c:if>
					<%--<a class="btn" id="export" disabled="disabled" onclick="down_adminservice();">
						<i class="icon-search"></i>
						Export
					</a>--%>
				<c:if test='${roleObj.export==1}'>
					<input type="button" class="btn" id="export" value="Export" disabled="disabled" onclick="down_adminservice();"/>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_10">Num</th>
				<th class="width_10">Ref.No</th>
				<th class="width_10">Staff Code</th>
				<th class="width_10">Staff Name</th>
				<th class="width_10">Creator</th>
				<th class="width_15">CreateDate</th>
				<th class="width_15">remark</th>
				<th class="width_8">Status</th>
				<th class="width_8">Operation</th>
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
<div class="e-container" id="detail_shelter" style="display: none;">
	<div class="info-form">
		<h4>ADM SERVICES REQUEST</h4>
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
					<input id="staffcodes" type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Name</td>
				<td class="tagCont">
					<input id="staffnames" type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont">
					<input id="select_location" type="text"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divIdSelected">
		<h4>Request Options</h4>
		<table id="buttomTable">
			<tr>
				<td class="tagName" rowspan="2">Change of Fluorescent Tube</td>
				<td class="tagCbox" rowspan="2">
					<input id="check_changeTube" type="checkbox"/>
				</td>
				<td class="tagText">Floor</td>
				<td class="tagInput">
					<select id="floor1">
	            		<option value="">Please Select Location</option>	
		            	<option value="5F">5F</option>
		              	<option value="7F">7F</option>
		              	<option value="15F">15F</option>
	              		<option value="24F">24F</option>
              			<option value="39F">39F</option>
           				<option value="40F">40F</option>
           				<option value="CP3">CP3</option>
           				<option value="10F 148 Electric Road">10F 148 Electric Road</option>
            			<option value="16F Peninsula">16F Peninsula</option>
           				<option value="18F 148 Electric Road">18F 148 Electric Road</option>
	            	</select>
				</td>
			</tr>
			<tr>
				<td class="tagText">Seat Number</td>
				<td class="tagInput">
					<input type="text" id="seat"/>
				</td>
			</tr>
			
			<%--<tr style="background: red; ">
			--%><tr>
				<td class="tagName" rowspan="5">Desk Phone Repair</td>
				<td class="tagCbox" rowspan="5">
					<input type="checkbox" value="N"  id="check_phone1"/>
				</td>
				<td class="tagText">Seat Number</td>
				<td class="tagInput"><input type="text" id="seatNumber" disabled="disabled" /></td>
			</tr>
			<%--<tr style="background: red; ">
			--%><tr>
				<td class="tagText">Extension#</td>
				<td class="tagInput">
					<input type="text" id="phone1" disabled="disabled" />
				</td>
			</tr>
			<%--<tr style="background: red; ">
			--%><tr>
				<td class="tagText">Password</td>
				<td class="tagInput">
					<input type="text" id="passwords" disabled="disabled" />
				</td>
			</tr>
			<%--<tr style="background: red; ">
			--%><tr>
				<td class="tagText">Phone Type</td>
				<td class="tagInput" id="check_xh">
					<label class="inline checkbox">
              			<input type="checkbox" id="formCheckbox4019" name="cb" ccc='c' value="4019"/>4019
              		</label>
              		<label class="inline checkbox">
              			<input type="checkbox" id="formCheckbox4010" name="cb" ccc='e' value="4010"/>4010
              		</label>
				</td>
			</tr>
		<%--<tr style="background: red; ">
			--%><tr>
				<td class="tagText" colspan="2">
					<img class="formImg" value="4019" src="images/phone_4019.jpg" />
              		<img class="formImg" value="4010" src="images/phone_4010.jpg" />
				</td>
			</tr>
			
			<%--<tr>
				<td class="tagName">Desk Phone Repair</td>
				<td class="tagCbox">
					<input type="checkbox" value="N"  id="check_phone1"/>
				</td>
				<td class="tagText">Extension#</td>
				<td class="tagInput">
					<input type="text" id="phone1"/>
				</td>
			</tr>--%>
			<tr>
				<td class="tagName" >Phone Voicemail Reset</td><!-- style="background: red;" -->
				<%--<td class="tagName">Desk Phone Reset Password</td>--%>
				<td class="tagCbox">
					<input type="checkbox" value="N"  id="check_phone2"/>  
				</td>
				<td class="tagText">Extension#</td>
				<td class="tagInput">
					<input type="text" id="phone2"/>
				</td>
			</tr>
		<%--<tr style="background: red; ">
			--%><tr>
				<td class="tagName">Forget Extension's Password</td>
				<td class="tagCbox">
					<input type="checkbox" value="N"  id="check_3"/>
				</td>
				<td class="tagText">Extension#</td>
				<td class="tagInput">
					<input type="text" id="forgetExtension" disabled="disabled" />
				</td>
			</tr>
			<tr>
				<td class="tagName" rowspan="2">Copier Repair</td>
				<td class="tagCbox" rowspan="2">
					<input value="N"  type="checkbox" id="check_copier"/>
				</td>
				<td class="tagText">Floor</td>
				<td class="tagInput">
					<select id="floor2">
			            <option value="">Please Select Location</option>	
		            	<option value="5F">5F</option>
		              	<option value="7F">7F</option>
		              	<option value="15F">15F</option>
	              		<option value="24F">24F</option>
              			<option value="39F">39F</option>
           				<option value="40F">40F</option>
           				<option value="CP3">CP3</option>
           				<option value="10F 148 Electric Road">10F 148 Electric Road</option>
            			<option value="16F Peninsula">16F Peninsula</option>
           				<option value="18F 148 Electric Road">18F 148 Electric Road</option>
	            	</select>
				</td>
			</tr>
			<tr>
				<td class="tagText">Copier#</td>
				<td class="tagInput">
					<input type="text" id="copier"/>
				</td>
			</tr>
			<tr>
				<td class="tagName" rowspan="2">Others (Wet Pantry,Public Area,Toilet,etc...)</td>
				<td class="tagCbox" rowspan="2">
					<input type="checkbox" value="N" id="check_other"/>
				</td>
				<td class="tagText">Floor</td>
				<td class="tagInput">
					<select id="floor3">
		            	<option value="">Please Select Location</option>	
		            	<option value="5F">5F</option>
		              	<option value="7F">7F</option>
		              	<option value="15F">15F</option>
	              		<option value="24F">24F</option>
              			<option value="39F">39F</option>
           				<option value="40F">40F</option>
           				<option value="CP3">CP3</option>
           				<option value="10F 148 Electric Road">10F 148 Electric Road</option>
            			<option value="16F Peninsula">16F Peninsula</option>
           				<option value="18F 148 Electric Road">18F 148 Electric Road</option>
              		</select>
				</td>
			</tr>
			<tr>
				<td class="tagText">Descriptions of request</td>
				<td class="tagInput">
					<input type="text" id="descript"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Remarks</td>
				<td class="tagCont" colspan="3">
					<input id="remarks" type="text" class="inputstyle" style="width: 70%; min-width:400px;"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Status: </td>
				<td class="tagCont" colspan="3">
					<span id="statuss" style="float:left;padding-left: 5px;font-weight: bold;"></span>
				</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="completed" onclick="Completed();">Completed</button>
	</c:if>
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="void" onclick="VOID()" style="background: red;">Void</button>
	</c:if>
		<button class="btn" onclick="Cancel();">Cancel</button>
	</div>
</div>
</body>
</html>
