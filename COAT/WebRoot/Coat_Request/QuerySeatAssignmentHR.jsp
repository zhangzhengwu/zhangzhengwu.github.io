<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Query Seat Assignment Request</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
   
  <script type="text/javascript" >
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
    	  location.href="<%=basePath%>"+"seatServiceServlet?method=down&" +
    	  "startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+
    	  "&staffcode="+$("#staffcode").val()+"&staffname="+$("#staffname").val()+"&refno="+$("#refno").val()+
	      "&status="+$("#status").val();
      }
      
      
      function select_requestservice(pageNow){
    	  $.ajax({
    		  url:"seatServiceServlet",
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
    				  		+"</td><td>"+dataRole[0][i].creatDate
    				  		+"</td><td>"+dataRole[0][i].remark
    				  		+"</td><td>"+dataRole[0][i].status
    				  		+"</td><td>" +
    				  		"<c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='detail(\""+dataRole[0][i].refno+"\");'>Detail</a></c:if>&nbsp;&nbsp;</td></tr>";
    				  	//<a href='javascript:void(0);' onclick='deletes(\""+dataRole[0][i].refno+"\");'>Delete</a>
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
    	   $("#detail_shelter").show();
    	   $('#Medical').hide();
    	   $.ajax({
    		  url:"seatServiceServlet",
    		  type:"post",
    		  data:{"refno":obj,"method":"detail"},
    		  success:function(date){
    			  var dataRole=eval(date);
    			 	$("#refnos").val(dataRole[0].refno);
    			 	$("#staffcodes").val(dataRole[0].staffcode);
    			  	$("#staffnames").val(dataRole[0].staffname)
    			  	$("#locations").val(dataRole[0].location)
    			  	$("#floors").val(dataRole[0].floor);
    			  	$("#extensionnos").val(dataRole[0].extensionno)
    			  	$("#seatnos").val(dataRole[0].seatno)
    			  	$("#lokernos").val(dataRole[0].lockerno)
    			  	$("#deskDrawernos").val(dataRole[0].deskDrawerno)
    			  	$("#pigenBoxnos").val(dataRole[0].pigenBoxno)
    			  	$("#staffnames").val(dataRole[0].staffname)
					$("#remarks").val(dataRole[0].remark);
    			  	$("#statusdetail").empty().append(dataRole[0].status);
						email=getEmail(dataRole[0].staffcode,"Consultant");	
	  				if(dataRole[0].status=="Submitted"){
	  					
	  					$("#ready").attr("disabled",false).show();
	  					$("#completed").attr("disabled",true);
	  					//$("#ready").attr("disabled",true);
	  				} else if(dataRole[0].status=="HKADM"){
	  					$("#ready").attr("disabled",false).show();
	  					$("#completed").attr("disabled",true);
	  					//$("#hkadm").attr("disabled",true);
	  				} else if(dataRole[0].status=="Ready"){
	  					$("#completed").attr("disabled",false).show();
	  					$("#ready").attr("disabled",true);
	  					//$("#ready").attr("disabled",true);
	  				} else if(dataRole[0].status=="Deleted"){
	  					$("#ready,#completed").hide();
	  				//	$("#hkadm").hide();
	  					//$("#ready").hide();
	  				}else{
	  					$("#ready").hide();
	  					$("#completed").attr("disabled",true);
	  				}
    			    /**if(parseFloat(("${SystemAcceTime}").substring(11,13))>=12 ||dataRole[0].status=="Ready"||dataRole[0].status=="Completed"){//当前时间超过十二点 	
	 	
				 	}else{
                 		alert("Normal operation time is after 12 o 'clock in the afternoon!");
                 		$("#ready,#hkadm,#completed").attr("disabled",true).hide();
                 		return false;
                 	}**/
    		  },error:function(){
    			   alert("please select at least one Request option!");
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
	    		 url:"seatServiceServlet",
	    		 type:"post",
	    		 data:{"method":"complete","refno":$.trim($("#refnos").val()),"type":"Completed"},
	    		 success:function(date){
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
        * 完成HKADM
        * */
       function hkadm(){
    	  if(confirm("Are you sure to HKADM?")){// 确认操作？
	    	 $.ajax({
	    		 url:"seatServiceServlet",
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
       }
       /**
        * 完成rEADY
        * */
       function Ready(){
   	    if($("#locations").val()==""){
    		 alert("Lost receipt floors!");
    		 return false;
    	 }
    	   
    	  if(confirm("Are you sure to Ready?")){// 确认操作？
	    	 $.ajax({
	    		 url:"seatServiceServlet",
	    		 type:"post",
	    		 data:{"method":"ready","refno":$.trim($("#refnos").val()),"type":"Ready",
	    		   "to":email,
			       "location":$("#locations").val()=="CP3"?"17/F":"40/F"},
	    		 success:function(date){
	    			 //alert(sendMail(email, email_seat($("#locations").val()=="CP3"?"17/F":"40/F")));
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
	    		 url:"seatServiceServlet",
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
				<td class="tagName">Ref.No:</td>
				<td class="tagCont">
					<input type="text" name="ordercode" id="refno">
				</td>
				<td class="tagName">Status:</td>
				<td class="tagCont">
					<select style="height:24px;" id="status">
		              	<option value="">Please Select Status</option>
		              	<option value="Submitted" >Submitted</option>
						<option value='Ready'>Ready</option>
		              	<option value='Completed'>Completed</option>
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
				<th class="width_5">Num</th>
				<th class="width_10">Ref.No</th>
				<th class="width_10">Staff Code</th>
				<th class="width_10">Staff Name</th>
				<th class="width_10">Creator</th>
				<th class="width_10">CreateDate</th>
				<th class="width_25">remark</th>
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
<div class="e-container" id="detail_shelter" style="display: none;">
	<div class="info-form" id="topTable">
		<h4>Seat Assignment Details</h4>
		<table>
			<tr>
				<td class="tagName">Ref.number</td>
				<td class="tagCont">
					<input id="refnos" name="refno" type="text" disabled="disabled" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code</td>
				<td class="tagCont">
					<input id="staffcodes" name="staffcode" disabled="disabled" type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Name</td>
				<td class="tagCont">
					<input id="staffnames" name="staffname" disabled="disabled" type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont">
					<select id ="locations" name ="location" disabled="disabled">
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
		<h4>Seat Information</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Extension No</td>
				<td class="tagCont">
					<input name ="extensionno" id ="extensionnos" disabled="disabled" type="text" class="inputstyle">
				</td>
			</tr>
			<tr>
				<td class="tagName">Floor</td>
				<td class="tagCont">
					<select name="floor" id="floors" disabled="disabled">
		              	<option value="">请选择</option>
		              	<option value="5F">5F</option>
		              	<option value="7F">7F</option>
		              	<option value="15F">15F</option>
		              	<option value="24F">24F</option>
		              	<option value="39F">39F</option>
		              	<option value="40F">40F</option>
		              	<option value="CP3">CP3</option>
              		</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Seat No</td>
				<td class="tagCont">
					<input id="seatnos" name="seatno" disabled="disabled" type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Locker No</td>
				<td class="tagCont">
					<input id="lokernos" name="lokerno" disabled="disabled" type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Desk Drawer No</td>
				<td class="tagCont">
					<input id="deskDrawernos" name="deskDrawerno" disabled="disabled" type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Pigeon Box No</td>
				<td class="tagCont">
					<input id="pigenBoxnos" name="pigenBoxno" disabled="disabled" type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Remarks</td>
				<td class="tagCont">
					<input id="remarks" name="remark" disabled="disabled" type="text" size="80" class="inputstyle" maxlength="80"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="ready" onclick="Ready();">Ready</button>
	</c:if>
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="completed" onclick="Completed();">Completed</button>
	</c:if>
		<button class="btn" onclick="Cancel();">Cancel</button>
	</div>
</div>
</body>
</html>
