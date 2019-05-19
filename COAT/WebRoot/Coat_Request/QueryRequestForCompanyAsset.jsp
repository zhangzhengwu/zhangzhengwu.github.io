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
   <style type="text/css">
   .tagName{width: 15%!important;}
   .tagCont{width: 35%!important;}
   </style>
   
  <script type="text/javascript" >
   var downs=null;
  var pagenow=1;
  var totalpage=0;
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
    	  location.href="../companyAssetServlet?method=down&" +
    	  "startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+
    	  "&staffcode="+$("#staffcode").val()+"&staffname="+$("#staffname").val()+"&refno="+$("#refno").val()+
	      "&status="+$("#status").val();
      }
      
      
      function select_requestservice(pageNow){
    	  $.ajax({
    		  url:"companyAssetServlet",
    		  type:"post",
    		  data:{"method":"select","startDate":$("#start_date").val(),
	    		  "endDate":$("#end_date").val(),"staffcode":'${convoy_username}',
	    		  "staffname":'',"refno":$("#refno").val(),
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
    				  		//+"</td><td><a href='javascript:void(0);' onclick='detail(\""+i+"\");'>Detail</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deletes(\""+dataRole[0][i].refno+"\");'>Delete</a></td></tr>";
    				  		+"</td><td><a href='javascript:void(0);' onclick='detail("+i+");'>Detail</a> ";
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
							html_left+="<tr id='left"+k+"' style='line-height:20px;' class='product'><td  align='center' >"+
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
						
    			 
	  				/**if(downs[0][i].status=="Submitted"){
	  					$("#ready").attr("disabled",false).show();
	  					$("#Delivered").attr("disabled",true);
	  					$("#Returned").attr("disabled",true);
	  					
	  				} else if(downs[0][i].status=="Ready"){
	  					$("#Delivered").attr("disabled",false).show();
	  					
	  					$("#ready").attr("disabled",true);
	  					$("#Returned").attr("disabled",true);
	  				} else if(downs[0][i].status=="Delivered"){
	  					$("#Delivered").attr("disabled",true);
	  					$("#ready").attr("disabled",true);
	  					$("#Returned").attr("disabled",false).show();
	  					
	  				} else if(downs[0][i].status=="Returned"){
	  					$("#Delivered").attr("disabled",true);
	  					$("#ready").attr("disabled",true);
	  					$("#Returned").attr("disabled",true);
	  					
	  				} else if(downs[0][i].status=="Deleted"){
	  					$("#ready").hide();
	  					$("#Returned").hide();
	  					$("#Delivered").hide();
	  				}
	  				*/
    			   $("#leftTable").append(html_left);
	  				 $(".product:odd").css("background","#F0F0F0");
	                 $(".product:even").css("background","#COCOCO");
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
					<a class="btn" onclick="select_requestservice(1);">
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
				<td class="tagCont" colspan="3">
					<input name="refnos" id="refnos"  type="text" disabled="disabled" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Event Name</td>
				<td class="tagCont" colspan="3">
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
				<td class="tagCont" colspan="3">
					<select id="locations" disabled="disabled">
		              	<option value="">请选择</option>
		              	<option value="@CONVOY">@CONVOY</option>
		              	<option value="CP3">CP3</option>
	              	</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">Status</td>
				<td class="tagCont" colspan="3">
					<span id="statusd" style="font-weight: bold;"></span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divIdSelected">
		<h4>Request Option</h4>
		<table id="leftTable">
			<tr>
				<td class="width_15">ID</td>
				<td class="width_20">RefNo#</td>
				<td class="width_20">Item Code</td>
				<td class="width_25">Item Name</td>
				<td class="width_20">Quantity</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
		<button class="btn" onclick="Cancel();">Cancel</button>
	</div>
</div>
</body>
</html>
