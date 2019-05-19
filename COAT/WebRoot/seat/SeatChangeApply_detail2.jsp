<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" />
  	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
  	<script type="text/javascript">
	var basepath = "<%=basePath%>";
	
	var checkFlag = 0;

	$(function(){
		showdetail();
		
	
	
	});


	function showdetail(){
		$("#responseConfirm").hide();
		$("#requestLeaderConfirm").hide();
		$("#responseLeaderConfirm").hide();
		$("#responseRefused").hide();
		$("#requestLeaderRefused").hide();
		$("#responseLeaderRefused").hide();
		$("#remarkall").attr("disabled","disabled");
		
		$("#refno").val("${seatChangeApply.refno}");
		$("#seatnoA").val("${seatChangeApply.seatnoa}");
		$("#seatnoB").val("${seatChangeApply.seatnob}");
		$("#floora").val("${seatChangeApply.remark1}");
		$("#floorb").val("${seatChangeApply.remark2}");
		$("#floorc").val("${seatChangeApply.remark1}");
		$("#floord").val("${seatChangeApply.remark2}");
		$("#seatnoC").val("${seatChangeApply.seatnoc}");
		$("#seatnoD").val("${seatChangeApply.seatnod}");
		$("#staffcodeA").val("${seatChangeApply.staffcodea}");
		$("#staffcodeB").val("${seatChangeApply.staffcodeb}");
		$("#staffcodeC").val("${seatChangeApply.staffcodec}");
		$("#staffcodeD").val("${seatChangeApply.staffcoded}");
		$("#pigeonboxA").val("${seatChangeApply.pigeonboxa}");
		$("#pigeonboxB").val("${seatChangeApply.pigeonboxb}");
		$("#pigeonboxC").val("${seatChangeApply.pigeonboxc}");
		$("#pigeonboxD").val("${seatChangeApply.pigeonboxd}");
		$("#extensionA").val("${seatChangeApply.extensiona}");
		$("#extensionB").val("${seatChangeApply.extensionb}");
		$("#extensionC").val("${seatChangeApply.extensionc}");
		$("#extensionD").val("${seatChangeApply.extensiond}");
		$("#remark").val("${seatChangeApply.remark}");
		$("#remarkall").val("${seatChangeApply.remarkall}");
		$("#leadercodea").val("${leadercodea}");
		$("#leadercodeb").val("${leadercodeb}");
	/* 	
		if("${seatChangeApply.checkflag}" == 1){
			$("#checkbox_pigeonbox").attr("checked",true);
		}else{
			$("#checkbox_pigeonbox").attr("checked",false);
		}
		
		if("${seatChangeApply.extensionflag}" == 1){
			$("#checkbox_extension").attr("checked",true);
		}else{
			$("#checkbox_extension").attr("checked",false);
		} */



		if ("${seatChangeApply.status}" == "Submitted" && "${seatChangeApply.staffcodeb}".toLowerCase() == '${convoy_username}'.toLowerCase() ){
			$("#responseConfirm").show();
			$("#responseRefused").show();
		} else if("${seatChangeApply.status}" == "ResponseConfirm" && ($("#leadercodea").val()).toLowerCase() == '${convoy_username}'.toLowerCase()){
			$("#requestLeaderConfirm").show();
			$("#requestLeaderRefused").show();
		} else if("${seatChangeApply.status}" == "RequestLeaderConfirm" && ($("#leadercodeb").val()).toLowerCase() == '${convoy_username}'.toLowerCase()){
			$("#responseLeaderConfirm").show();
			$("#responseLeaderRefused").show();
		} else {
		
		}
		
		
	
	}

	function responseConfirm(){
	
		$("#remarkall").val($("#remarkall").val().replace(/\n/g, "~.~")+"~.~"+"Response："+($("#remark").val().replace(/\n/g, "~.~")));
		
		$("#remark").val("");
	
	
               $.ajax({
                 url:basepath+"SeatServlet", 
                 type:"post",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
                 data:{"method":"responseConfirm",
                 	   "refno":$("#refno").val(),
                 	   "staffcodeA":$("#staffcodeA").val(),
                 	   "remarkall":$("#remarkall").val()}, 
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
	
	
	function requestLeaderConfirm(){
	
		$("#remarkall").val($("#remarkall").val().replace(/\n/g, "~.~")+"~.~"+"Requester upline："+($("#remark").val().replace(/\n/g, "~.~")));
		
		$("#remark").val("");
	
	
               $.ajax({
                 url:basepath+"SeatServlet", 
                 type:"post",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
                 data:{"method":"requestLeaderConfirm",
                 	   "refno":$("#refno").val(),
                 	   "staffcodeA":$("#staffcodeA").val(),
                 	   "staffcodeB":$("#staffcodeB").val(),
                 	   "seatnoC":$("#seatnoC").val(),
                 	   "seatnoD":$("#seatnoD").val(),
                 	   "staffcodeC":$("#staffcodeC").val(),
                 	   "staffcodeD":$("#staffcodeD").val(),
                 	   /* "extensionC":$("#extensionC").val(),
                 	   "extensionD":$("#extensionD").val(),
                 	   "pigeonboxC":$("#pigeonboxC").val(),
                 	   "pigeonboxD":$("#pigeonboxD").val(), */
                 	   "remarkall":$("#remarkall").val()}, 
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
	
	
	function responseLeaderConfirm(){
	
		$("#remarkall").val($("#remarkall").val().replace(/\n/g, "~.~")+"~.~"+"Response upline："+($("#remark").val().replace(/\n/g, "~.~")));
		
		$("#remark").val("");
	
	
               $.ajax({
                 url:basepath+"SeatServlet", 
                 type:"post",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
                 data:{"method":"responseLeaderConfirm",
                 	   "refno":$("#refno").val(),
                 	   "staffcodeA":$("#staffcodeA").val(),
                 	   "staffcodeB":$("#staffcodeB").val(),
                 	   "seatnoC":$("#seatnoC").val(),
                 	   "seatnoD":$("#seatnoD").val(),
                 	   "staffcodeC":$("#staffcodeC").val(),
                 	   "staffcodeD":$("#staffcodeD").val(),
              /*    	   "extensionC":$("#extensionC").val(),
                 	   "extensionD":$("#extensionD").val(),
                 	   "pigeonboxC":$("#pigeonboxC").val(),
                 	   "pigeonboxD":$("#pigeonboxD").val(), */
                 	   "remarkall":$("#remarkall").val()}, 
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
	
	
	
	function responseRefused(){
	     	if($("#remark").val()==""){																 
				error("remark不能為空！");	
				$("#remark").focus();
				return false;																			 
			}	

		$("#remarkall").val($("#remarkall").val().replace(/\n/g, "~.~")+"~.~"+"Response："+($("#remark").val().replace(/\n/g, "~.~")));
		
		$("#remark").val("");
	
               $.ajax({
                 url:basepath+"SeatServlet", 
                 type:"post",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
                 data:{"method":"refuseByResponse",
                 	   "refno":$("#refno").val(),
                 	   "staffcodeA":$("#staffcodeA").val(),
                 	   "staffcodeB":$("#staffcodeB").val(),
                 	   "remarkall":$("#remarkall").val()}, 
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
	
	
	
	function requestLeaderRefused(){
	     	if($("#remark").val()==""){																 
				error("remark不能為空！");	
				$("#remark").focus();
				return false;																			 
			}	

		$("#remarkall").val($("#remarkall").val().replace(/\n/g, "~.~")+"~.~"+"Requester upline："+($("#remark").val().replace(/\n/g, "~.~")));
		
		$("#remark").val("");
	
               $.ajax({
                 url:basepath+"SeatServlet", 
                 type:"post",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
                 data:{"method":"refuseByRequestLeader",
                 	   "refno":$("#refno").val(),
                 	   "staffcodeA":$("#staffcodeA").val(),
                 	   "staffcodeB":$("#staffcodeB").val(),
                 	   "remarkall":$("#remarkall").val()}, 
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
	
	
	function responseLeaderRefused(){
	     	if($("#remark").val()==""){																 
				error("remark不能為空！");	
				$("#remark").focus();
				return false;																			 
			}	

		$("#remarkall").val($("#remarkall").val().replace(/\n/g, "~.~")+"~.~"+"Response upline："+($("#remark").val().replace(/\n/g, "~.~")));
		
		$("#remark").val("");
	
               $.ajax({
                 url:basepath+"SeatServlet", 
                 type:"post",
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },
                 data:{"method":"refuseByResponseLeader",
                 	   "refno":$("#refno").val(),
                 	   "staffcodeA":$("#staffcodeA").val(),
                 	   "staffcodeB":$("#staffcodeB").val(),
                 	   "remarkall":$("#remarkall").val()}, 
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


	function cancel(){
		if("leader" == $("#emptype").val()){
			location.href="<%=basePath%>"+"SeatServlet?method=cancel2";
		}else{
			location.href="<%=basePath%>"+"SeatServlet?method=cancel1";
		}
	}


	  	
  	</script>
  	<style>
  		.info-title .title-info table .tagName{ width: 12%!important; vertical-align: middle; padding-top: 0px;}
  		.info-title .title-info table .tagCont{ width: 13%!important; padding-top: 5px;}
  	</style>  	
  </head>
	  
  <body>
  <div class="cont-info">
  	<div class="info-title">
  		<div class="title-info">
  			<table>
  				<tr>
  					<td class="tagName"></td>
  					<td class="tagCont" style="padding-left: 35px; font-weight: bold;">
  						Applicant
  					</td>
  					<td class="tagName"></td>
  					<td class="tagCont" style="padding-left: 35px; font-weight: bold;">
  						Replace
  					</td>
  					<td class="tagCont" style="font-weight: bold;"></td>
  					<td class="tagName" colspan="3" style="text-align: center; font-weight: bold;">Results Preview</td>
  				</tr>
  				<tr>
  					<td class="tagName">Seat NoA:</td>
  					<td class="tagCont">
  						<input type="hidden" name="refno" id="refno" />
						<input type="text" name="seatnoA" disabled="disabled" id="seatnoA" />
  					</td>
  					<td class="tagName">Seat NoB:</td>
  					<td class="tagCont">
  						<input type="text" name="seatnoB" disabled="disabled" id="seatnoB" />
  					</td>
  					<td class="tagName"></td>
  					<td class="tagName">
  						<input type="text" name="seatnoC" disabled="disabled" id="seatnoC" />
  					</td>
  					<td class="tagCont" colspan="2">
  						<input type="text" name="seatnoD" disabled="disabled" id="seatnoD"/>
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Staff CodeA:</td>
  					<td class="tagCont">
  						<input type="text" name="staffcodeA" disabled="disabled" id="staffcodeA" />
  					</td>
  					<td class="tagName">Staff CodeB:</td>
  					<td class="tagCont">
						<input type="text" name="staffcodeB" disabled="disabled" id="staffcodeB" />
  					</td>
  					<td class="tagName"></td>
  					<td class="tagName">
  						<input type="text" name="staffcodeC" disabled="disabled" id="staffcodeC" />
  					</td>
  					<td class="tagCont" colspan="2">
						<input type="text" name="staffcodeD" disabled="disabled" id="staffcodeD" />
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Extension NoA:</td>
  					<td class="tagCont">
  						<input type="text" name="extensionA" disabled="disabled" id="extensionA" />
  					</td>
  					<td class="tagName">Extension NoB:</td>
  					<td class="tagCont">
  						<input type="text" name="extensionB" disabled="disabled" id="extensionB" />
  					</td>
<!--   					<td class="tagCont" style="vertical-align: middle; padding-top: 0px;">
  						<label calss="inline checkbox" style="display: none;">
  							<input type="checkbox" name="checkbox_extension" disabled="disabled" id="checkbox_extension" />
  						</label>
  					</td> -->
  					<td class="tagName"></td>
  					<td class="tagName">
  						<input type="text" name="extensionC" disabled="disabled" id="extensionC"/>
  					</td>
  					<td class="tagCont" colspan="2">
  						<input type="text" name="extensionD" disabled="disabled" id="extensionD"/>
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Pigeon BoxA:</td>
  					<td class="tagCont">
  						<input type="text" name="pigeonboxA" disabled="disabled" id="pigeonboxA" />
  					</td>
  					<td class="tagName">Pigeon BoxB:</td>
  					<td class="tagCont">
  						<input type="text" name="pigeonboxB" disabled="disabled" id="pigeonboxB" />
  					</td>
<!--   					<td class="tagCont" style="vertical-align: middle; padding-top: 0px;">
  						<label calss="inline checkbox" style="display: none;">
  							<input type="checkbox" name="checkbox_pigeonbox" disabled="disabled" id="checkbox_pigeonbox" />
  						</label>
  					</td> -->
  					<td class="tagName"></td>
  					<td class="tagName">
  						<input type="text" name="pigeonboxC" disabled="disabled" id="pigeonboxC"/>
  					</td>
  					<td class="tagCont" colspan="2">
  						<input type="text" name="pigeonboxD" disabled="disabled" id="pigeonboxD"/>
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">FloorA:</td>
  					<td class="tagCont">
  						<input type="text" name="floora" disabled="disabled" id="floora" />
  					</td>
  					<td class="tagName">FloorB:</td>
  					<td class="tagCont">
						<input type="text" name="floorb" disabled="disabled" id="floorb" />
  					</td>
  					<td class="tagName"></td>
  					<td class="tagName">
  						<input type="text" name="floorc" disabled="disabled" id="floorc" />
  					</td>
  					<td class="tagCont" colspan="2">
  						<input type="text" name="floord" disabled="disabled" id="floord" />
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Remark:</td>
  					<td class="tagCont" colspan="7">
  						<textarea id="remark" rows="3" cols="" style="width: 97%;"></textarea>
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName" id="remarkflag">Remark Overview:</td>
  					<td class="tagCont" colspan="7">
  						<textarea id="remarkall" rows="6" cols="" disabled="disabled" style="width: 97%;"></textarea>
  					</td>
  					<input type="hidden" name="leadercodea" id="leadercodea" />
  					<input type="hidden" name="leadercodeb" id="leadercodeb" />
  				</tr>
  				<tr>
  					<td style="text-align: center;" colspan="8">
						<a class="btn" id="responseConfirm" onClick="responseConfirm()">Confirm</a>
						<a class="btn" id="requestLeaderConfirm" onClick="requestLeaderConfirm()">Confirm</a>
						<a class="btn" id="responseLeaderConfirm" onClick="responseLeaderConfirm()">Confirm</a>
						<a class="btn" id="responseRefused" onClick="responseRefused()">Refuse</a>
						<a class="btn" id="requestLeaderRefused" onClick="requestLeaderRefused()">Refuse</a>
						<a class="btn" id="responseLeaderRefused" onClick="responseLeaderRefused()">Refuse</a>
						<a class="btn" id="canel" onClick="cancel();">Cancel</a>
  					</td>
  				</tr>
  			</table>
  			<input type="hidden" id="emptype" value="${type}">
  		</div>
  	</div>
  </div>
  </body>
</html>
