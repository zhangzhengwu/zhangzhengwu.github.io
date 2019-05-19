<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <base href="<%=basePath%>" />
  	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
  	<style>
  	.info-form textarea{ width: 60%; }
  	.info-form input{ width: 200px!important;  }
  	</style>
  	<script type="text/javascript">
	var basepath = "<%=basePath%>";
	

	$(function(){
		showdetail();
		/* querySeatNoBefore(); */
	
	
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

	function showdetail(){
	
		$("#refno").val("${seatAutoChangeApply.refno}");
		$("#staffcode").val("${seatAutoChangeApply.staffcode}");
		$("#staffname").val("${seatAutoChangeApply.staffname}");
		$("#seatnobefore").val("${seatAutoChangeApply.seatnobefore}");
		$("#leadercode").val("${seatAutoChangeApply.leadercode}");
		$("#leadername").val("${seatAutoChangeApply.leadername}");
		$("#createdate").val("${seatAutoChangeApply.createdate}");
		$("#seatnoafter").val("${seatAutoChangeApply.seatno}");
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

		$("#completed").hide();
		$("#void").hide();
		$("#canel").hide();
		$("#remarkB").parents("tr").hide();
		$("#remarkC").parents("tr").hide();


		if("${seatAutoChangeApply.status}" == "Submitted"){
			$("#canel").show();
			
		} else if ("${seatAutoChangeApply.status}" == "Refused"){
			$("#remarkB").parents("tr").show();
			$("#canel").show();
		} else if ("${seatAutoChangeApply.status}" == "Confirmed"){
			$("#remarkB").parents("tr").show();
			$("#remarkC").parents("tr").show();
			$("#completed").show();
			$("#void").show();
			$("#canel").show();
		} else {
			$("#remarkB").parents("tr").show();
			$("#remarkC").parents("tr").show();
			$("#canel").show();
			$("#remarkC").attr("disabled","disabled");
		}	
	}


	function cancel(){
		location.href="<%=basePath%>"+"SeatAutoServlet?method=cancel1";
	}


	function complete(buttonval){
	
		    if($("#remarkC").val()==""){																 
				error("ADM Remark不能為空！");	
				$("#remarkC").focus();
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
                 data:{"method":"complete",
                 	   "refno":$("#refno").val(),
                 	   "flag":$("#flag").val(),
                 	   "staffcode":$("#staffcode").val(),
                 	   "staffname":$("#staffname").val(),
                 	   "seatnoafter":$("#seatnoafter").val(),
                 	   "seatnobefore":$("#seatnobefore").val(),
                 	   "leadercode":$("#leadercode").val(),
                 	   "status":buttonval,
                 	   "remarkC":$("#remarkC").val()}, 
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
			<h4>Seat Assignment</h4>
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
<!-- 				<tr>
					<td class="tagName">Seat No Before:</td>
					<td class="tagCont">
				<input type="text" name="seatnobefore" disabled="disabled" id="seatnobefore" />
					</td>
				</tr> -->

				<tr>
					<td class="tagName">Seat No:</td>
					<td class="tagCont">
						<input type="text" name="seatnoafter" disabled="disabled" id="seatnoafter" />
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
						<textarea id="remarkB" disabled="disabled">${seatAutoChangeApply.remarkB}</textarea>
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
				<a class="btn" id="completed"  onClick="complete('Completed');">Completed</a>
				<a class="btn" id="void" onClick="complete('VOID')">VOID</a>
				<a class="btn" id="canel" onClick="cancel();">Exit</a>
					</td>
				</tr>
			</table>
		</div>
  </div>
  </body>
</html>
