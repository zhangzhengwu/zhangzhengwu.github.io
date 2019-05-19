<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" />
  	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
  	<style>
  	.info-form textarea{ width: 60%; }
  	.info-form input, .info-form select{ width: 200px!important;  }
  	</style>
  	<script type="text/javascript">
	var basepath = "<%=basePath%>";
	

	$(function(){
		showdetail();
		/* querySeatNoBefore(); */
		queryLegalSeatNo();


		$("#seatnoafter").change(function(){
               $.ajax({
                 url:basepath+"SeatAutoServlet", 
                 type:"post",
                 data:{"method":"getSeatListInforBySeatNo",
                 	   "seatnoafter":$("#seatnoafter").val()}, 
                 dataType:"json",	   
				 success:function(date){
					 	$("#location").val(date.location);
					    $("#floor").val(date.floor);
					    $("#lockerno").val(date.lockerno);
					    $("#deskdrawerno").val(date.deskDrawerno);
					    $("#extensionno").val(date.extensionno);
					    $("#pigenboxno").val(date.pigenBoxno);
	    				if("${seatListBefore}" != ""){
						    $("#extensionno").val("${seatListBefore.extensionno}");
						    if("${seatListBefore.floor}" != "15F" && "${seatListBefore.floor}" == "${seatListAfter.floor}"){
						    	$("#pigenboxno").val("${seatListBefore.pigenBoxno}");
						    }	    				
	    				} 	
				   } 
				});
          return false;			
		
		});
				
	
	});

/* 	function querySeatNoBefore(){
		$.ajax({
				type:"post",
				url: "SeatAutoServlet",
				data:{"method":"querySeatNoBefore","staffcode":$("#staffcode").val()},
				dataType:"json",
				success: function(date){
					$("#seatnobefore").val(date.seatno);
				}
			});
	
	} */

	function queryLegalSeatNo(){
		$.ajax({
				type:"post",
				url: basepath+"SeatAutoServlet",
				data:{"method":"queryAllLegalSeatNo","leadercode":$("#leadercode").val(),"flag":$("#flag").val()},
				//dataType:"json",
				success: function(date){
						var html="";
						var dataRole=eval(date);
						html+="<option value='${seatAutoChangeApply.seatno}'>"+"${seatAutoChangeApply.seatno}"+"</option>";
						
						if(dataRole != null && dataRole != "" ){
							for(var i=0; i< dataRole.length;i++){
							    html+="<option value='"+dataRole[i]+"'>"+dataRole[i]+"</option>";
							}
						}
						$("#seatnoafter").append(html);
					
				}
			});
	
	}


	function showdetail(){
	
		$("#refno").val("${seatAutoChangeApply.refno}");
		$("#staffcode").val("${seatAutoChangeApply.staffcode}");
		$("#staffname").val("${seatAutoChangeApply.staffname}");
		$("#seatnobefore").val("${seatAutoChangeApply.seatnobefore}");
		$("#leadercode").val("${seatAutoChangeApply.leadercode}");
		$("#leadername").val("${seatAutoChangeApply.leadername}");
		$("#createdate").val("${seatAutoChangeApply.createdate}");
		$("#flag").val("${seatAutoChangeApply.flag}");
		$("#status").val("${seatAutoChangeApply.status}");
/* 		$("#remarkA").val("${seatAutoChangeApply.remarkA}");
		$("#remarkB").val("${seatAutoChangeApply.remarkB}");
		$("#remarkC").val("${seatAutoChangeApply.remarkC}"); */
		
		
		$("#location").val("${seatListAfter.location}");
	    $("#floor").val("${seatListAfter.floor}");
	    $("#lockerno").val("${seatListAfter.lockerno}");
	    $("#deskdrawerno").val("${seatListAfter.deskDrawerno}");
	    $("#extensionno").val("${seatListAfter.extensionno}");
	    $("#pigenboxno").val("${seatListAfter.pigenBoxno}");
	    
	    if("${seatListBefore}" != ""){
			$("#locationbefore").val("${seatListBefore.location}");
		    $("#floorbefore").val("${seatListBefore.floor}");
		    $("#extensionnobefore").val("${seatListBefore.extensionno}");
		    $("#pigenboxnobefore").val("${seatListBefore.pigenBoxno}");
		    $("#lockernobefore").val("${seatListBefore.lockerno}");
		    $("#deskdrawernobefore").val("${seatListBefore.deskDrawerno}");
		    $("#extensionno").val("${seatListBefore.extensionno}");
		    if("${seatListBefore.floor}" != "15F" && "${seatListBefore.floor}" == "${seatListAfter.floor}"){
		    	$("#pigenboxno").val("${seatListBefore.pigenBoxno}");
		    }
	    }else{
	    	$(".before").hide();
	    }
	    
		
		$("#confirm").hide();
		$("#refused").hide();
		$("#canel").hide();
		$("#remarkB").parents("tr").hide();
		$("#remarkC").parents("tr").hide();
		
		
		if("${seatAutoChangeApply.status}" == "Submitted" && ("${seatAutoChangeApply.flag}" == 0||"${seatAutoChangeApply.flag}" == 2) &&"${seatAutoChangeApply.staffcode}".toLowerCase() == '${convoy_username}'.toLowerCase() ){
			$("#canel").show();
			$("#seatnoafter").attr("disabled","disabled");
			
		} else if ("${seatAutoChangeApply.status}" == "Submitted" && "${seatAutoChangeApply.flag}" == 1 && "${seatAutoChangeApply.staffcode}".toLowerCase() == '${convoy_username}'.toLowerCase() ){
			$("#remarkB").parents("tr").show();
/* 			$("#seatnoafter").attr("disabled","disabled"); */
			$("#confirm").show();
			$("#refused").show();
			$("#canel").show();
		} else if ("${seatAutoChangeApply.status}" == "Submitted" && ("${seatAutoChangeApply.flag}" == 0||"${seatAutoChangeApply.flag}" == 2) && "${seatAutoChangeApply.leadercode}".toLowerCase() == '${convoy_username}'.toLowerCase() ){
			$("#remarkB").parents("tr").show();
			$("#confirm").show();
			$("#refused").show();
			$("#canel").show();
		} else if ("${seatAutoChangeApply.status}" == "Submitted" && "${seatAutoChangeApply.flag}" == 1 && "${seatAutoChangeApply.leadercode}".toLowerCase() == '${convoy_username}'.toLowerCase() ){
			$("#canel").show();
			$("#seatnoafter").attr("disabled","disabled");
		} else if ("${seatAutoChangeApply.status}" == "Confirmed" ||"${seatAutoChangeApply.status}" == "Refused"){
			$("#remarkB").parents("tr").show();
			$("#seatnoafter").attr("disabled","disabled");
			$("#remarkB").attr("disabled","disabled");
			$("#canel").show();
		} else {
			$("#seatnoafter").attr("disabled","disabled");
			$("#remarkB").parents("tr").show();
			$("#remarkC").parents("tr").show();
			$("#canel").show();
			$("#remarkB").attr("disabled","disabled");
			$("#remarkC").attr("disabled","disabled");
		}		
		
	
	}








	function cancel(){
		location.href="<%=basePath%>"+"SeatAutoServlet?method=cancel";
	}


	function confirm(){
		
               $.ajax({
                 url:basepath+"SeatAutoServlet", 
                 type:"post",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
                 data:{"method":"confirm",
                 	   "refno":$("#refno").val(),
                 	   "flag":$("#flag").val(),
                 	   "seatnoafter":$("#seatnoafter").val(),
                 	   "seatnobefore":$("#seatnobefore").val(),
                 	   "staffcode":$("#staffcode").val(),
                 	   "staffname":$("#staffname").val(),
                 	   "leadercode":$("#leadercode").val(),
                 	   "remarkB":$("#remarkB").val()}, 
				 success:function(date){
						var dataRole = eval(date);
						if(dataRole > 0){
							alert("提交成功");
							cancel();
						}else{
							alert("提交失败");
							return;
						}
				   } 
				});
          return false;
	}
	function refuse(){
	
	     	if($("#remarkB").val()==""){																 
				error("Leader Remark不能為空！");	
				$("#remarkB").focus();
				return false;																			 
			}
	
               $.ajax({
                 url:basepath+"SeatAutoServlet", 
                 type:"post",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },                 
                 data:{"method":"refuse",
                 	   "refno":$("#refno").val(),
                 	   "flag":$("#flag").val(),
                 	   "seatnobefore":$("#seatnobefore").val(),
                 	   "seatnoafter":$("#seatnoafter").val(),
                 	   "staffcode":$("#staffcode").val(),
                 	   "leadercode":$("#leadercode").val(),
                 	   "remarkB":$("#remarkB").val()}, 
				 success:function(date){
						var dataRole = eval(date);
						if(dataRole > 0){
							alert("提交成功");
							cancel();
						}else{
							alert("提交失败");
							return;
						}
				   } 
				});
          return false;
	}


	  	
  	</script>
  	<style>
  		.info-title .title-info table .tagName{ width: 12%!important; vertical-align: middle; padding-top: 0px;}
  		.info-title .title-info table .tagCont{ width: 13%!important; padding-top: 5px;}
  		.info-form table.col-4 td.tagnAME{ width: 15%; }
  		.info-form table.col-4 td.tagCont{ width: 35%; }
  		.info-form table.col-4 textarea{ width: 75%; }  		
  	</style>  	
  </head>
	  
  <body>
  <div class="e-container">
  		<div class="info-form">
  			<h4>Recruiter's Corner</h4>
			<c:choose>
			   <c:when test="${not empty seatListBefore}">  
			        <table class="col-4">      
			   </c:when>
			   <c:otherwise> 
			       <table>
			   </c:otherwise>
			</c:choose>

  				<tr>
  					<td class="tagName">Staff Code:</td>
					<c:choose>
					   <c:when test="${not empty seatListBefore}">  
					        <td class="tagCont" colspan="3">      
					   </c:when>
					   <c:otherwise> 
					       <td class="tagCont">
					   </c:otherwise>
					</c:choose>
  						<input type="hidden" name="refno" id="refno" />
  						<input type="hidden" name="flag" id="flag" />
						<input type="text" name="staffcode" disabled="disabled" id="staffcode" />
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Staff Name:</td>
					<c:choose>
					   <c:when test="${not empty seatListBefore}">  
					        <td class="tagCont" colspan="3">      
					   </c:when>
					   <c:otherwise> 
					       <td class="tagCont">
					   </c:otherwise>
					</c:choose>
						<input type="text" name="staffname" disabled="disabled" id="staffname" />
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Leader Code:</td>
					<c:choose>
					   <c:when test="${not empty seatListBefore}">  
					        <td class="tagCont" colspan="3">      
					   </c:when>
					   <c:otherwise> 
					       <td class="tagCont">
					   </c:otherwise>
					</c:choose>
						<input type="text" name="leadercode" disabled="disabled" id="leadercode" />
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Leader Name :</td>
					<c:choose>
					   <c:when test="${not empty seatListBefore}">  
					        <td class="tagCont" colspan="3">      
					   </c:when>
					   <c:otherwise> 
					       <td class="tagCont">
					   </c:otherwise>
					</c:choose>
						<input type="text" name="leadername" disabled="disabled" id="leadername" />
  					</td>
  				</tr>
<!--   				<tr>
  					<td class="tagName">Seat No Before:</td>
  					<td class="tagCont">
						<input type="text" name="seatnobefore" disabled="disabled" id="seatnobefore" />
  					</td>
  				</tr> -->

  				<tr>
  					<td class="tagName">Seat No:</td>
					<td class="tagCont">
							<select id="seatnoafter" name="seatnoafter"></select>
					</td>
					<td class="tagName before">Seat No Before:</td>
  					<td class="tagCont before">
						<input type="text" name="seatnobefore" disabled="disabled" id="seatnobefore" />
  					</td>
  				</tr>

  				<tr>
  					<td class="tagName">Location:</td>
					<td class="tagCont">
							<input type="text" name="location" disabled="disabled" id="location" />
					</td>
					<td class="tagName before">Location Before:</td>
  					<td class="tagCont before">
						<input type="text" name="locationbefore" disabled="disabled" id="locationbefore" />
  					</td>
  				</tr>
 
   				<tr>
  					<td class="tagName">Floor:</td>
					<td class="tagCont">
							<input type="text" name="floor" disabled="disabled" id="floor" />
					</td>
					<td class="tagName before">Floor Before:</td>
  					<td class="tagCont before">
						<input type="text" name="floorbefore" disabled="disabled" id="floorbefore" />
  					</td>
  				</tr>
   				<tr>
  					<td class="tagName">Extension No:</td>
					<td class="tagCont">
							<input type="text" name="extensionno" disabled="disabled" id="extensionno" />
					</td>
					<td class="tagName before">Extension No Before:</td>
  					<td class="tagCont before">
						<input type="text" name="extensionnobefore" disabled="disabled" id="extensionnobefore" />
  					</td>
  				</tr>

   				<tr>
  					<td class="tagName">Pigen Box No:</td>
					<td class="tagCont">
							<input type="text" name="pigenboxno" disabled="disabled" id="pigenboxno" />
					</td>
					<td class="tagName before">Pigen Box No Before:</td>
  					<td class="tagCont before">
						<input type="text" name="pigenboxnobefore" disabled="disabled" id="pigenboxnobefore" />
  					</td>
  				</tr>

   				<tr>
  					<td class="tagName">Locker No:</td>
					<td class="tagCont">
							<input type="text" name="lockerno" disabled="disabled" id="lockerno" />
					</td>
					<td class="tagName before ">Locker No Before:</td>
  					<td class="tagCont before">
						<input type="text" name="lockernobefore" disabled="disabled" id="lockernobefore" />
  					</td>
  				</tr>

   				<tr>
  					<td class="tagName">Desk Drawer No:</td>
					<td class="tagCont">
							<input type="text" name="deskdrawerno" disabled="disabled" id="deskdrawerno" />
					</td>
					<td class="tagName before">Desk Drawer No Before:</td>
  					<td class="tagCont before">
						<input type="text" name="deskdrawernobefore" disabled="disabled" id="deskdrawernobefore" />
  					</td>
  				</tr>
  				  				 		
  				<tr>
  					<td class="tagName">Create Date:</td>
					<c:choose>
					   <c:when test="${not empty seatListBefore}">  
					        <td class="tagCont" colspan="3">      
					   </c:when>
					   <c:otherwise> 
					       <td class="tagCont">
					   </c:otherwise>
					</c:choose>
						<input type="text" name="createdate" disabled="disabled" id="createdate" />
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Status:</td>
					<c:choose>
					   <c:when test="${not empty seatListBefore}">  
					        <td class="tagCont" colspan="3">      
					   </c:when>
					   <c:otherwise> 
					       <td class="tagCont">
					   </c:otherwise>
					</c:choose>
						<input type="text" name="status" disabled="disabled" id="status" />
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">remark:</td>
					<c:choose>
					   <c:when test="${not empty seatListBefore}">  
					        <td class="tagCont" colspan="3">      
					   </c:when>
					   <c:otherwise> 
					       <td class="tagCont">
					   </c:otherwise>
					</c:choose>
  						<textarea id="remarkA"  disabled="disabled">${seatAutoChangeApply.remarkA}</textarea>
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName" id="remarkbflag"> Leader Remark:</td>
					<c:choose>
					   <c:when test="${not empty seatListBefore}">  
					        <td class="tagCont" colspan="3">      
					   </c:when>
					   <c:otherwise> 
					       <td class="tagCont">
					   </c:otherwise>
					</c:choose>
  						<textarea id="remarkB">${seatAutoChangeApply.remarkB}</textarea>
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName" id="remarkcflag">ADM Remark:</td>
					<c:choose>
					   <c:when test="${not empty seatListBefore}">  
					        <td class="tagCont" colspan="3">      
					   </c:when>
					   <c:otherwise> 
					       <td class="tagCont">
					   </c:otherwise>
					</c:choose>
  						<textarea id="remarkC">${seatAutoChangeApply.remarkC}</textarea>
  					</td>
  				</tr>
  				<tr>
  					<td style="text-align: center;" colspan="4">
						<a class="btn" id="confirm" onClick="confirm()">Confirm</a>
						<a class="btn" id="refused" onClick="refuse()">Refuse</a>
						<a class="btn" id="canel" onClick="cancel();">Exit</a>
  					</td>
  				</tr>
  			</table>
  		</div>
  </div>
  </body>
</html>
