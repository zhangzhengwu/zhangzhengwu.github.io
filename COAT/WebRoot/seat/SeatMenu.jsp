<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
  	<script type="text/javascript">
	var basepath = "<%=basePath%>";
 	 var pagenow =1;
	 var totalpage=1;
	 var total=0;
  	 var num1;
  	 var num2;
  	 var num3;
  	 var num4;
  	 var bv1;
  	 var bv2;
  	 
  	$(function(){
  	
  			getTotalSeatNum();
  	
  			select(1);
  	
	  		$("#search").click(function(){
			select(1);	
			});
  	
  	
  			$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				select(pagenow);
			});
			$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				select(pagenow);
			});
			$("#first").bind("click", function() {//首页
				pagenow = 1;
				select(pagenow);
			});
			$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				select(pagenow);
			});
  	

/* Add start */
$("#add").click(function (){ 
	var url = basepath + "seat/seatmenu_info.jsp";
	addlhg(900,500,url);
});
/* Add end */
  	
  	});


  	
  	/*获取总的座位数 */
    function getTotalSeatNum(){
  		$.ajax({
				type:"post",
				url: basepath+"SeatServlet",
				data:{"method":"getTotalSeatNum"},
				//dataType:"json",
				success: function(date){
						num1 = date;
						$("#totalseatnum").html(date);
						getTotalPTSeatNum();
					}
				});
    	
    }	
  	/*获取总的普通座位数 */
  	function getTotalPTSeatNum(){
  		  		$.ajax({
				type:"post",
				url: basepath+"SeatServlet",
				data:{"method":"getTotalPTSeatNum"},
				//dataType:"json",
				success: function(date){
						num2 = date;
						$("#totalptseatnum").html(date);
						getTotalUsePTSeatNum();
					}
				});
  	}
  	
  	/*获取总的在用普通座位数 */
	function getTotalUsePTSeatNum(){
		$.ajax({
				type:"post",
				url: basepath+"SeatServlet",
				data:{"method":"getTotalUsePTSeatNum"},
				//dataType:"json",
				success: function(date){
						num3 = date;
						$("#totaluseptseatnum").html(date);
						getTotalUseADSeatNum();

					}
				});
				
	}

  	/*获取总的在用AD座位数 */
	function getTotalUseADSeatNum(){
	  	$.ajax({
				type:"post",
				url: basepath+"SeatServlet",
				data:{"method":"getTotalUseADSeatNum"},
				//dataType:"json",
				success: function(date){
						num4 = date;
						$("#totaluseadseatnum").html(date);
												
						bv1 = (Number(num3)/Number(num2)*100).toFixed(2)+'%';
						$("#ptseatusebv").html(bv1);
						
						num5 = Number(num1)-Number(num2);
						$("#totaladseatnum").html(num5);
						
						bv2 = (Number(num4)/Number(num5)*100).toFixed(2)+'%';
						$("#adseatusebv").html(bv2);
						
					}
				});
	
	}

  	
  	function select(pagenow){
  		$.ajax({
  			url:basepath+"SeatServlet",
  			type:"post",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
  			data:{"method":"select",
  				  "staffcode":$("#staffcode").val(),
  				  "staffname":$("#staffname").val(),
  				  "location":$("#location").val(),
  				  "floor":$("#floor").val(),
  				  "seatno":$("#seatno").val(),
  				  "ifhidden":$("#ifhidden").val(),
  				  "ifAD":$("#ifAD").val(),
  				  'pagenow':pagenow},
  			success:function(date){
	  			var dataRole = eval(date);
	  			var html="";
	  			$(".tr_search").remove();
	  			if(dataRole[3]>0){
		  			total=dataRole[3];
		  			pagenow = dataRole[2];
		  			totalpage = dataRole[1];
	  				for ( var i = 0; i < dataRole[0].length; i++) {
		  				html+="<tr class='tr_search'><td>"+(((pagenow-1)*15)+(i+1))
		  					 +"</td><td>"+dataRole[0][i].seatno
		  					 +"</td><td>"+dataRole[0][i].staffcode
		  					 +"</td><td>"+dataRole[0][i].staffname
		  					 +"</td><td>"+dataRole[0][i].location
		  					 +"</td><td>"+dataRole[0][i].extensionno
		  					 +"</td><td>"+dataRole[0][i].floor
		  					 +"</td><td>"+dataRole[0][i].lockerno
		  					 +"</td><td>"+dataRole[0][i].deskDrawerno
		  					 +"</td><td>"+dataRole[0][i].pigenBoxno
		  					 +"</td><td>"+(dataRole[0][i].ifhidden=="Y"?"YES":"NO")
		  					 +"</td><td>"+dataRole[0][i].remark
		  					 +"</td><td><c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='update(\""+dataRole[0][i].seatno+"\");'>Update</a></c:if>&nbsp;<c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='detail(\""+dataRole[0][i].seatno+"\");'>Detail</a></c:if>&nbsp;";
		  					 if(dataRole[0][i].staffcode != "" && dataRole[0][i].staffcode != null){
		  					 html+= "<c:if test='${roleObj.search==1}' ><a href='javascript:void(0);' onclick='clean(\""+dataRole[0][i].seatno+"\");'>Clean</a></c:if>&nbsp;";
		  					 }
		  					 html+="<c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='del(\""+dataRole[0][i].seatno+"\");'>Delete</a></c:if>&nbsp;</td></tr>";
					}
	  				$(".page_and_btn").show();
	  				$("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
	  			}else{
	  				html+="<tr class='tr_search'><td colspan='9' align='center'>Sorry, there is no matching record.</td></tr>";
    				$(".page_and_btn").hide();
	  			}
	  			$("#seatLists").append(html);
	  			page(pagenow,totalpage);
  			},error:function(){
  			 	alert("网络连接异常!");
  				return false;
  			}
  			
  		});
  	}
  	
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

	}  	
  
  
      function  exportdata(){
      var staffcode=$("#staffcode").val();
	  var staffname=$("#staffname").val();
	  var address=$("#location").val();
	  var floor=$("#floor").val();
	  var ifhidden=$("#ifhidden").val();
	  var ifAD=$("#ifAD").val();
      var seatno=$("#seatno").val();
      var exportExecl="exportExecl";
      location.href="<%=basePath%>"+"SeatServlet?method="+exportExecl+"&staffcode="+staffcode+"&staffname="+staffname+"&address="+address+"&floor="+floor+"&ifhidden="+ifhidden+"&ifAD="+ifAD+"&seatno="+seatno;
    }	
    
    
    function  synSeatPlan(){
    	$.ajax({
  			url:basepath+"SeatServlet",
  			type:"post",
  			data:{"method":"synSeatPlan"},
  			beforeSend: function(){
					parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},success:function(date){
	  			var dataRole = $.parseJSON(date);
	  			alert(dataRole.msg);
  			},error:function(){
  			 	alert("网络连接异常!");
  				return false;
  			}
  			
  		});
    }	
    function  batchCleanSeat(){
    	$.ajax({
  			url:basepath+"SeatServlet",
  			type:"post",
  			data:{"method":"batchCleanSeat"},
  			beforeSend: function(){
					parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},success:function(date){
	  			var dataRole = $.parseJSON(date);
	  			alert(dataRole.msg);
  			},error:function(){
  			 	alert("网络连接异常!");
  				return false;
  			}
  			
  		});
    }	
  	
  	
  	function clean(i) {
	$.dialog( {
		title : '清空座位信息',
		id : 'seat_clean',
		width : 500,
		height : 300,
		cover : true,
		drag : false,
		lock : true,

		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath
				+ "SeatServlet?method=SeatDetail&seatno=" + i + ""
	});
	load();
}


  	function detail(i) {
	$.dialog( {
		title : '详细',
		id : 'seat_detail',
		width : 900,
		height : 560,
		cover : true,
		drag : false,
		lock : true,

		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath
				+ "SeatServlet?method=getSeatListInforBySeatNo&seatno=" + i + ""
	});
	load();
}
  	function update(i) {
	$.dialog( {
		title : '修改',
		id : 'seat_update',
		width : 1000,
		height : 500,
		cover : true,
		drag : false,
		lock : true,

		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url:" + basepath
				+ "SeatServlet?method=updateSeatListBySeatNo&seatno=" + i + ""
	});
	load();
}

	  function del(i){
	  if(confirm('确认删除当前座位号? ')){  
			$.ajax({
				type:"post",
				url: basepath+"SeatServlet",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
				data:{"method":"delSeatNo","seatno":i},
				dataType:'json',
				success: function(result){
    				if("success" == result.state){
    					success(result.msg);
    					select(1);
    				}else{
    					error(result.msg);
    				}
	    		 },error:function(result){
	    			 error("Network connection is failed, please try later...");
	  			   	 return false;
	    		 }
			});
			return false;
			}
	  	      
	  	      
	  }

  	
		$('#upload_btn').live('click',function(){
	 		var data = 'uploadSeatList';
	 		var url = basepath + "publicUpload.jsp";
			uploadlhg(440,300,url,data);
	 	});
	 	
		$('#model_btn').live('click',function(){
			location.href=basepath + "TemplateResours/SeatList_xxx.xls";;
	 	});	 	
	 	
	 	function uploadSeatList(fileName){
           	if(fileName==""){
				alert("請選擇需要上傳的文件！");
				return;
			}
			 $.ajax({
				type: "post",
				url:"UpExcelServlet",
				dateType:"html",
				complete: function(){
				  $.dialog.list['menu_upload'].close();
				},
				data: {'uploadfile':fileName},
				success:function(date){
					if(date != "null"){
						//alert(date);
						var dataRole=eval(date);
						alert("導入數據成功!本次操作新增"+(dataRole[1]-dataRole[3]) +"条數據，修改"+dataRole[3]+"條數據    "+"共耗時"+dataRole[2]+"分鐘");		
						$("#shangchuan").attr("disabled",true);
					}else{
						alert("非系统所需文件！");
					}
				 },error:function(){
					 alert("网络连接失败，请稍后重试...");
					 return false;
				 } 
				});
	 	}
	 	
	 	
	 	
	 	
	 	
	 	  	
  	</script>
  </head>
	  
  <body>
<div class="cont-info">
  <div class="info-search">
	<table>
		<tr>
			<td class="tagName">Staff Code:</td>
			<td class="tagCont">
				<input type="text" name="staffcode" id="staffcode"/>
			</td>
			<td class="tagName">Staff Name:</td>
			<td class="tagCont">
				<input type="text" name="staffname" id="staffname"/>
			</td>
		</tr>
		<tr>
			<td class="tagName">Location:</td>
			<td class="tagCont">
				<select id ="location" name ="location" >
	              	<option value="">Please Select Location</option>
	              	<option value="@CONVOY">@CONVOY</option>
	              	<option value="CP3">CP3</option>
             	</select> 
			</td>
			<td class="tagName">Floor:</td>
			<td class="tagCont">
					<select name="floor" id="floor">
		              	<option value="">Please Select Floor</option>
		              	<option value="5F">5F</option>
		              	<option value="7F">7F</option>
		              	<option value="15F">15F</option>
		              	<option value="17F">17F</option>
		              	<option value="40F">40F</option>
	               </select> 
			</td>
		</tr>
		<tr>			
			<td class="tagName">If Hidden Seat:</td>
			<td class="tagCont">
				<select id ="ifhidden" name ="ifhidden" >
	              	<option value="">Please Select Location</option>
	              	<option value="Y">YES</option>
	              	<option value="N">NO</option>
             	</select> 
			</td>
			<td class="tagName">If AD or DD Seat:</td>
			<td class="tagCont">
				<select id ="ifAD" name ="ifAD" >
	              	<option value="">Please Select Location</option>
	              	<option value="AD">AD</option>
	              	<option value="DD">DD</option>
             	</select> 
			</td>			
		</tr>		
		<tr>
			<td class="tagName">Seat No:</td>
			<td class="tagCont">
				<input type="text" name="seatno" id="seatno"/>
			</td>
			<td class="tagCont" colspan="2">
					<c:if test="${roleObj.search==1}">
					<a class="btn" id="search" >
						<i class="icon-search"></i>
						Search
					</a>
					</c:if>
					<c:if test="${roleObj.export==1}">
						<a class="btn" id="exp" onclick="exportdata()">
							<i class="icon-download"></i>
							Export
						</a>
					</c:if>					
					<c:if test="${roleObj.add==1}">
  					<a class="btn" id="add">
  						<i class="icon-plus"></i>
  						Add New Seat
  					</a>
  					</c:if>
					<c:if test="${roleObj.upd==1}">
						<a class="btn" id="model_btn">
							<i class="icon-download"></i>
							Template 
						</a>
					</c:if>
					<c:if test="${roleObj.upd==1}">
						<a class="btn" id="upload_btn">
							<i class="icon-upload-alt"></i>
							Batch Upload
						</a>
					</c:if>		
					<c:if test="${roleObj.upd==1}">
						<a class="btn" id="clean_btn" onclick="batchCleanSeat()" >
							<i class="icon-upload-alt"></i>
							Batch Clean
						</a>
					</c:if>				
<%-- 					<c:if test="${roleObj.audit==1}">
						<a class="btn" id="synSeatPlan" onclick="synSeatPlan()">
							<i class="icon-refresh"></i>
							SynSeatPlan
						</a>
					</c:if> --%>
			</td>
		</tr>
		<tr>
			<td class="tagName" style="padding-bottom: 0px;">Statistical Data:</td>
			<td class="tagCont" colspan="3" >
				座位总数<a><span id="totalseatnum"></span></a>个、普通座位总数<a><span id="totalptseatnum"></span></a>个、已使用普通座位数<a><span id="totaluseptseatnum"></span></a>个、普通座位使用率<a><span id="ptseatusebv"></span></a>、AD座位总数<a><span id="totaladseatnum"></span></a>个、已使用AD座位数<a><span id="totaluseadseatnum"></span></a>个、AD座位使用率<a><span id="adseatusebv"></span></a> .
			</td>
		</tr>
     </table>
  </div>
  <div class="info-table">
  	<table id="seatLists">
  		<thead>
		<tr>
			<th class="width_5">Num</th>
			<th class="width_5">Seat No</th>
			<th class="width_5">Staff Code</th>
			<th class="width_10">Staff Name</th>
			<th class="width_8">Location</th>
			<th class="width_5">Extension No</th>
			<th class="width_5">Floor</th>
			<th class="width_5">Locker No</th>
			<th class="width_5">Desk Drawer No</th>
			<th class="width_5">Pigen Box No</th>
			<th class="width_5">IF Hidden</th>
			<th class="width_10">AD or DD</th>
			<th class="width_10">Handle</th>
		</tr>
		</thead>  	
  	</table>
 		<div align="center" class="page_and_btn">
			<table class="table-ss" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="main_head">
					<td colspan="14" align="center">
						<a id="first" href="javascript:void(0);">first Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a>Total
						<SPAN style="color: red;" id="total">
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
  </div>
</div>	
</body>
</html>
