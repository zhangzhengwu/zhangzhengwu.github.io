<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Query Key Request</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
   
  <script type="text/javascript" >
   var downs=null;
  var pagenow=1;
  var totalpage=0;
  var email=null;
      $(function(){
    	  $("#start_date,#end_date").val(getMonthBeforeDay(new Date()));
    	  $(".field,:checkbox").attr("disabled",true);
    	  
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
    	  
    	  
      });
      /**
       * 导出数据
       * */
      function down_adminservice(){
    	  location.href="<%=basePath%>"+"companyAssetServlet?method=down&" +
    	  "startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+
    	  "&staffcode="+$("#staffcode").val()+"&staffname="+$("#staffname").val()+"&refno="+$("#refno").val()+
	      "&status="+$("#status").val()+"&collectionDate="+$("#collectionDate").val()+"&returnDate="+$("#returnDate").val();
      }
      
      
      function select_requestservice(pageNow){
    	  $.ajax({
    		  url:"companyAssetServlet",
    		  type:"post",
    		  data:{"method":"select","startDate":$("#start_date").val(),
	    		  "endDate":$("#end_date").val(),"staffcode":$("#staffcode").val(),
	    		  "staffname":$("#staffname").val(),"refno":$("#refno").val(),
	    		  "status":$("#status").val(),"collectionDate":$("#collectionDate").val(),"returnDate":$("#returnDate").val(),"curretPage":pageNow},
	     	  beforeSend: function(){
	    			  parent.showLoad();
	     	  },
	     	  complete: function(){
	     		  parent.closeLoad();
	     	  },
    		  success:function(date){
    			  var dataRole=eval(date);
    			  downs=null;
    			  var html="";
    			  pagenow=dataRole[2];
    			  totalpage=dataRole[1];
    			  $(".tr_search").remove();
    			  if(dataRole[3]>0){
    			  downs=dataRole;
    				  for(var i=0;i<dataRole[0].length;i++){
    				  	html+="<tr class='tr_search'><td>"+((pagenow-1)*15+(i+1))
    				  		+"</td><td>"+dataRole[0][i].refno
    				  		+"</td><td>"+dataRole[0][i].staffcode
    				  		+"</td><td>"+dataRole[0][i].staffname
    				  		+"</td><td>"+dataRole[0][i].creator
    				  		+"</td><td>"+dataRole[0][i].createDate
    				  		+"</td><td>"+dataRole[0][i].status
    				  		+"</td><td>" +
    				  		"<c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='detail(\""+i+"\");'>Detail</a></c:if></td></tr>";
    				  	//&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deletes(\""+dataRole[0][i].refno+"\");'>Delete</a>
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
       function detail(i){
    	   $("#detail_shelter").show();
    	   $('#Medical').hide();
    	   $.ajax({
    		  url:"companyAssetServlet",
    		  type:"post",
    		  data:{"refno":downs[0][i].refno,"method":"detail"},
    		  success:function(date){
    		  	var dd=eval(date);
					var html_left="";
					d=eval(dd);
					$(".product").remove();
					for(var k=0; k< d[0].length; k++){
						var num = k+1;
						html_left+="<tr id='left"+k+"'  class='product' style='line-height:20px;'><td  align='center' >"+
							num+"</td><td align='center'>"+
							d[0][k].refno+"</td><td  align='center'>"+
							d[0][k].itemcode+"</td><td  align='center'>&nbsp; "+
							d[0][k].itemname+"</td><td  align='center'>&nbsp; "+
							d[0][k].num+"</td></tr>";
						 
					}
    		  
    			 	$("#refnos").val(downs[0][i].refno);
    			 	$("#eventNames").val(downs[0][i].eventName);
    			 	$("#staffcodes").val(downs[0][i].staffcode);
    			  	$("#staffnames").val(downs[0][i].staffname)
    			  	$("#locations").val(downs[0][i].location)
    			  	$("#collectionDates").val(downs[0][i].collectionDate)
    			  	$("#returnDates").val(downs[0][i].returnDate)
    			  	$("#statusd").empty().append(downs[0][i].status);
					
	  				if(downs[0][i].status=="Submitted"){
	  					email=getEmail(downs[0][i].staffcode,downs[0][i].userType);	
	  					$("#ready").attr("disabled",false).show();
	  					$("#Delivered,#Returned").attr("disabled",true).hide();
	  					
	  				} else if(downs[0][i].status=="Ready"){
	  					$("#Delivered").attr("disabled",false).show();
	  					
	  					$("#ready,#Returned").attr("disabled",true).hide();
	  				} else if(downs[0][i].status=="Delivered"){
	  					$("#Delivered,#ready").attr("disabled",true).hide();
	  					if(getMonthBeforeDay(new Date())>=downs[0][i].returnDate)
	  						$("#Returned").attr("disabled",false).show();
	  					else
	  						$("#Returned").attr("disabled",true).show();
	  					
	  				} else if(downs[0][i].status=="Returned"){
	  					$("#Delivered,#ready").attr("disabled",true).hide();
	  					$("#Returned").attr("disabled",true).show();
	  					
	  				} else if(downs[0][i].status=="Deleted"){
	  					$("#ready,#Returned,#Delivered").hide();
	  				}
    			   $("#leftTable").append(html_left);
    			   	 $(".product:odd").css("background","#F0F0F0");
	                 $(".product:even").css("background","#COCOCO");
    			   /** if(parseFloat(("${SystemAcceTime}").substring(11,13))>=12 || downs[0][i].status=="Ready" ){//当前时间超过十二点 	
	 	
				 	}else{
                 		alert("Normal operation time is after 12 o 'clock in the afternoon!");
                 		$("#ready,#Delivered").attr("disabled",true).hide();
                 		return false;
                 	}**/
    		  },error:function(){
    			   alert("please select at least one Request option!");
	  			   return false;
    		  }
    	   });
       }
       /**
        * 完成 Delivered
        **/
       function Delivered(){
    	  if(confirm("Are you sure to Complete this record?")){// 确认操作？
	    	 $.ajax({
	    		 url:"companyAssetServlet",
	    		 type:"post",
	    		 data:{"method":"complete","refno":$.trim($("#refnos").val()),"type":"Delivered"},
	    		 success:function(date){
	    			 //alert(date);
	    			 var result=$.parseJSON(date);
	    			 if(result.state=="success"){
	    				 alert("success");
	    			 }else{
	    				 alert(result.msg);
	    			 }
	    			 Cancel();
	    			 select_requestservice(pagenow);
	    		 },error:function(){
	    			 alert("please select at least one Request option!");
	  			   	 return false;
	    		 }
	    	 });
    	  }
       } 
       function returned(){
    	  if(confirm("Are you sure to Complete this record?")){// 确认操作？
	    	 $.ajax({
	    		 url:"companyAssetServlet",
	    		 type:"post",
	    		 data:{"method":"returned","refno":$.trim($("#refnos").val()),"type":"Returned"},
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
        * 完成READY
        * */
       function Ready(){
    	  if(confirm("Are you sure to Ready?")){// 确认操作？
	    	 $.ajax({
	    		 url:"companyAssetServlet",
	    		 type:"post",
	    		 data:{"method":"ready","refno":$.trim($("#refnos").val()),"type":"Ready","to":email},
	    		 success:function(date){
	    			 //alert(sendMail(email, email_companyAsset()));
	    			 var result=$.parseJSON(date);
	    			 if(result.state=="success"){
	    				 alert("success");
	    			 }else{
	    				 alert(result.msg);
	    			 }
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
        	if(confirm("Are you sure to Delete this record?")){// 确认删除？
    	   		 $.ajax({
	    		 url:"companyAssetServlet",
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
    	   
    	   $("#detail_shelter").hide();
    	   $('#Medical').show();
       }
  </script>
  <style type="text/css">
  .e-container .tagName{width: 15%!important;}
  .e-container .tagCont{width: 35%!important;}
  </style>
  </head>

<body>
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
				<td class="tagName">Staff Code:</td>
				<td class="tagCont">
					<input name="text" type="text" id="staffcode"  />
				</td>
				<td class="tagName">Staff Name:</td>
				<td class="tagCont">
					<input type="text" id="staffname">
				</td>
			</tr>
			<tr>
				<td class="tagName">Date of Collection:</td>
				<td class="tagCont">
					<input name="text" type="text" id="collectionDate" onClick="return Calendar('collectionDate')" />
				</td>
				<td class="tagName">Date of Return:</td>
				<td class="tagCont">
					<input type="text" id="returnDate"  onClick="return Calendar('returnDate')">
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
			            <option value='Ready'>Ready </option>
			            <option value='Delivered'>Delivered</option>
			            <option value='Returned'>Returned</option>
		            </select>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
				<c:if test='${roleObj.search==1}'>
					<a class="btn" onclick="select_requestservice(1);">
						<i class="icon-search"></i>
						Search
					</a>
				</c:if>
				<c:if test='${roleObj.export==1}'>
					<a class="btn" id="export" disabled="disabled" onclick="down_adminservice();">
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
				<th class="width_">Num</th>
				<th class="width_">Ref.No</th>
				<th class="width_">Staff Code</th>
				<th class="width_">Staff Name</th>
				<th class="width_">Creator</th>
				<th class="width_">CreateDate</th>
				<th class="width_">Status</th>
				<th class="width_">Operation</th>
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
		<h4>Company Asset Request</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Ref.number</td>
				<td class="tagCont" colspan="4">
					<input name="refnos" id="refnos"  type="text" disabled="disabled" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Event Name</td>
				<td class="tagCont" colspan="4">
					<input id="eventNames" type="text" size="50" disabled="disabled" class="inputstyle" maxlength="50"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code</td>
				<td class="tagCont">
					<input name="staffcode" id="staffcodes"  type="text" disabled="disabled" class="inputstyle"/>
				</td>
				<td class="tagName">Staff Name</td>
				<td class="tagCont">
					<input name="staffname" id="staffnames" type="text" disabled="disabled" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Date of collection</td>
				<td class="tagCont">
					<input id ="collectionDates" name="collectionDate" type="text" disabled="disabled" class="inputstyle" onClick="return Calendar('collectionDate')"/>
				</td>
				<td class="tagName">Date of return</td>
				<td class="tagCont">
					<input id="returnDates" name="returnDate" type="text" disabled="disabled" class="inputstyle" onClick="return Calendar('returnDate')"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont" colspan="4">
					<select id="locations" disabled="disabled">
		              	<option value="">请选择</option>
		              	<option value="@CONVOY">@CONVOY</option>
		              	<option value="CP3">CP3</option>
		            </select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Status</td>
				<td class="tagCont" colspan="4">
					<span id="statusd" style="font-weight: bold;"></span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divIdSelected">
		<h4>Request Option</h4>
		<table id="leftTable">
			<tr>
				<td class="width_">ID</td>
				<td class="width_">RefNo#</td>
				<td class="width_">Item Code</td>
				<td class="width_">Item Name</td>
				<td class="width_">Quantity</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="ready" onclick="Ready();">Ready</button>
	</c:if>
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="Delivered" onclick="Delivered();">Delivered</button>
	</c:if>
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="Returned" onclick="returned();">returned</button>
	</c:if>
		<button class="btn" onclick="Cancel();">Cancel</button>
	</div>
</div>
</body>
</html>
