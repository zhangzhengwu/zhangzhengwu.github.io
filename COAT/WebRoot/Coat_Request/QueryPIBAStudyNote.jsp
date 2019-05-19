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
	
  </head>
  <script type="text/javascript" >
  var pagenow=1;
  var totalpage=0;
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
			var econvoy=new Array();
			econvoy="${Econvoy}".split("-");
			$("#staffcode").val(econvoy[0]);
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
	   $.ajax({
    		  url:"PIBAStudyNoteServlet",
    		  type:"post",
    		  data:{"method":"select","staffcode":$("#Staffcode").val(),"staffname":$("#Staffname").val(),"refno":$("#refno").val(),
	    		  "status":$("#status").val(),"curretPage":pageNow,"start_date":$("#start_date").val(),"end_date":$("#end_date").val()},
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
    				  		+"</td><td>" +
    				  		"<c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='detail(\""+dataRole[0][i].refno+"\",\""+dataRole[0][i].staffcode+"\",\""+dataRole[0][i].staffname+"\",\""+dataRole[0][i].location+"\",\""+dataRole[0][i].status+"\");'>Detail</a></c:if>&nbsp;&nbsp;" ;
    				  		//html+="<a href='javascript:void(0);' onclick='deletes(\""+dataRole[0][i].refno+"\");'>Delete</a>";
    				  		if(dataRole[0][i].status=="Submitted"){
								html+="<c:if test='${roleObj.delete==1}'><a href='javascript:void(0);' onclick='deletes(\""+dataRole[0][i].refno+"\");'>Delete</a></c:if>";
							}else if(dataRole[0][i].status=="Ready"){//大于当天12点 且当前时间大于于12点 可删除
								html+="<c:if test='${roleObj.delete==1}'><a href='javascript:void(0);' onclick='deletes(\""+dataRole[0][i].refno+"\");'>Delete</a></c:if>";
							}
    				  		html+="</td></tr>";
    				  }
    				  $(".page_and_btn").show();
    				  $("#export").attr("disabled",false);
    			  }else{
    				  html+="<tr class='tr_search'><td colspan='9' align='center' ><b style='color:blue;size=5px'>"+"Sorry, there is no matching record."+"</b></td></tr>";
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
      var obs="";
      function detail(obj,staffcode,staffname,location,status){
   	   $("#detail_shelter").show();
   	   $('#Medical').hide();
   	   obs=obj;
   	   //$(".paymentAmount").attr("checked",false);
   	   $.ajax({
   		  url:"PIBAStudyNoteServlet",
   		  type:"post",
   		  data:{"refno":obj,"method":"detail"},
  		  beforeSend: function(){
    		  parent.showLoad();
     	  },
     	  complete: function(){
     		  parent.closeLoad();
     	  },
   		  success:function(date){
   			  var dataRole=eval(date);
   			    $(".tr_detail").remove();
   			    $(".tr_detail2").remove();
   			 	$("#refnos").empty().append(obj);
   			 	$("#staffcodes").val(staffcode);
   			  	$("#staffnames").val(staffname);
   			  	$("#location").val(location);
   			  	if(dataRole[0].length>0){
    				  for(var i=0;i<dataRole[0].length;i++){
    					  html="<tr class='tr_detail'><td class='width_15'>"+dataRole[0][i].type+"</td>" +
    					  "<td class='width_25'>"+dataRole[0][i].staffcode+"</td>" +
    					  "<td class='width_25'>"+dataRole[0][i].bookCname+"</td>" +
    					  "<td class='width_25'>"+dataRole[0][i].bookNum+"</td></tr>"
   			  			 $("#buttomTable").append(html);
    				  }
    		     }
   			  	 if(dataRole[1].length>0){
    				  for(var i=0;i<dataRole[1].length;i++){
    					  html="<tr class='tr_detail2'><td class='width_15'>"+(i+1)+"</td>" +
    					  "<td class='width_25'>"+dataRole[1][i].signname+"</td>" +
    					  "<td class='width_25'>"+dataRole[1][i].signcode+"</td></tr>"
   			  			 $("#detailTable").append(html);
    				  }
    		      }
   			  	 
   			  	 if(status=="Submitted"){
   			  		 $("#Complete").hide();
   			  		 $("#Ready").show();
   			  		 $("#Void").show();
   			  	 }
   			  	 if(status=="Ready"){
   			  		 $("#Ready").hide();
   			  		 $("#Complete").show();
   			  		 $("#Void").show();
   			  	 }
   			  	 if(status=="Completed"){
   			  		 $("#Ready").hide();
   			  		 $("#Complete").hide();
   			  		 $("#Void").hide();
   			  	 }
   			  	 if(status=="Void"){
   			  		 $("#Ready").hide();
   			  		 $("#Complete").hide();
   			  		 $("#Void").hide();
   			  	 }
   			  	
   		  },error:function(){
   			   alert("Network connection is failed, please try later...");
  			   return false;
   		  }
   	   });
      }
 
       /**
        * 删除
        * @param {Object} obj
        */
       function deletes(obj){
        	if(confirm("Are you sure to Delete this record?")){// 确认删除？
    	   		 $.ajax({
	    		 url:"PIBAStudyNoteServlet",
	    		 type:"post",
	    		 data:{"method":"delete","refno":obj,"type":"Deleted"},
	    		 success:function(date){
	    			 alert(date);
	    			 search_access(pagenow);
	    		 },error:function(){
	    			alert("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
	    	 });
        	}
       }
        
       function Cancel(){
    	   //$(".field input,select").not(".paymentAmount:checkbox").val("");
    	   $("[id^='check_']").attr("checked","");
    	   $("#detail_shelter").hide();
    	   $('#Medical').show();
       }
       
       function Ready(){
    	   if(confirm("Are you sure?")){
    	   	  $.ajax({
	    		 url:"PIBAStudyNoteServlet",
	    		 type:"post",
	    		 data:{"method":"Ready","refno":obs,"type":"Ready","staffnames":$("#staffnames").val(),"location":$("#location").val()},
	    		 success:function(date){
	    			 var result=$.parseJSON(date);
	    			 if(result.state=="success"){
	    				 alert("success");
	    			 }else{
	    				 alert(result.msg);
	    			 }
	    			 search_access(pagenow);
	    			 $("#detail_shelter").hide();
		    	     $('#Medical').show();
	    		 },error:function(){
	    			alert("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
	    	 });
        	}
    	   
       }
       
       function Void(){
    	   if(confirm("Are you sure?")){
    	   	  $.ajax({
	    		 url:"PIBAStudyNoteServlet",
	    		 type:"post",
	    		 data:{"method":"VOID","refno":obs,"type":"Void"},
	    		 success:function(date){
	    			 alert(date);
	    			 search_access(pagenow);
	    			 $("#detail_shelter").hide();
		    	     $('#Medical').show();
	    		 },error:function(){
	    			alert("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
	    	 });
        	}
    	   
       }
       
         function Complete(){
    	   if(confirm("Are you sure?")){
    	   	  $.ajax({
	    		 url:"PIBAStudyNoteServlet",
	    		 type:"post",
	    		 data:{"method":"complete","refno":obs,"type":"Completed"},
	    		 success:function(date){
	    			 alert(date);
	    			 search_access(pagenow);
	    			 $("#detail_shelter").hide();
		    	     $('#Medical').show();
	    		 },error:function(){
	    			alert("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
	    	 });
        	}
    	   
       }
  
       function down_access(){
    	   if($(".page_and_btn").is(":hidden")){
    		   alert("Please query data first, and then do export related operation!");
    		   return false;
    	   }else{
    		   location="<%=basePath%>"+"PIBAStudyNoteServlet?method=down&startDate="+$("#start_date").val()
    		                +"&endDate="+$("#end_date").val()+"&staffcode="+$("#Staffcode").val()
	    		  			+"&staffname="+$("#Staffname").val()+"&refno="+$("#refno").val()
	    		  			+"&status="+$("#status").val();
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
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')"  />
              		<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text"  onClick="return Calendar('end_date')" />
              		<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
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
			            <option value="Void">Void</option>
			            <option value="Ready">Ready</option>
			            <option value='Completed'>Completed</option>
		            </select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staffcode：</td>
				<td class="tagCont">
					<input type="text" name="Staffcode" id="Staffcode">
				</td>
				<td class="tagName">Staffname：</td>
				<td class="tagCont">
					<input type="text" name="Staffname" id="Staffname">
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
					<%--<a class="btn" onclick="down_access();">
						<i class="icon-download"></i>
						Export
					</a>--%>
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
				<th class="width_10">CreateDate</th>
				<th class="width_15">remark</th>
				<th class="width_10">Status</th>
				<th class="width_10">Operation</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;">
			<table class="table-ss" width="100%" border="0" cellpadding="0" cellspacing="0">
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
		<h4>Consultant Information</h4>
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
		<h4>Order Detail</h4>
		<table id="buttomTable">
			<tr>
    			<td class="width_15">
    				<strong>Type</strong>
    			</td>
    			<td class="width_20">
    				<strong>Staff Code</strong>
    			</td>
    			<td class="width_20">
    				<strong>Name of Book</strong>
    			</td>
    			<td class="width_20">
    				<strong>Quantity</strong>
    			</td>
    		</tr>
		</table>
	</div>
	<div id="form_context" class="info-form">
  			<h4>Details of User</h4>
		    	<table class="form-table" id="detailTable">
		    		<tr>
		    			<td class="width_15">
		    				<strong>Num</strong>
		    			</td>
		    			<td class="width_40">
		    				<strong>Name of User</strong>
		    			</td>
		    			<td class="width_45">
		    				<strong>Staff Code(if any)</strong>
		    			</td>
		    		</tr>
		    	</table>
  	</div>
  <div class="btn-board">
 	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="Ready" onclick="Ready()">Ready</button>&nbsp;
	</c:if>
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="Complete" onclick="Complete()">Complete</button>&nbsp;
	</c:if>
	<c:if test='${roleObj.audit==1}'>
		<button class="btn" id="Void" onclick="Void()">Void</button>&nbsp;
	</c:if>
		<button class="btn" id="Cancel" onclick="Cancel();">Cancel</button>
	</div>
</div>
</body>
</html>
