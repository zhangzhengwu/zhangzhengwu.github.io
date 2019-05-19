<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" />
  	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
  	<script type="text/javascript">
		var basepath = "<%=basePath%>";
		
/* 		var checkFlag = 0;
		var extensionFlag = 0; */
		$(function(){
		
			$("#staffcodeA").val('${convoy_username}');
			ifMeLegit();
		
			$("#remarkflag").hide();
			$("#remarkall").hide();
						
/* 			$("#checkbox_pigeonbox").change(function(){
					if($("#checkbox_pigeonbox").attr("checked")=="checked"){//當checkbox_pigeonbox選中時
						checkFlag = 1 ;
						$("#pigeonboxC").val($("#pigeonboxB").val());
						$("#pigeonboxD").val($("#pigeonboxA").val());
						$("#checkFlag").val(1);
					}else{
						checkFlag = 0 ;
						$("#checkFlag").val(0);
						$("#pigeonboxC").val($("#pigeonboxA").val());
						$("#pigeonboxD").val($("#pigeonboxB").val());
					}
			});
			
			$("#checkbox_extension").change(function(){
					if($("#checkbox_extension").attr("checked")=="checked"){//當checkbox_extension選中時
						extensionFlag = 1 ;
						$("#extensionFlag").val(1);
						$("#extensionC").val($("#extensionB").val());
						$("#extensionD").val($("#extensionA").val());
					}else{
						extensionFlag = 0 ;
						$("#extensionFlag").val(0);
						$("#extensionC").val($("#extensionA").val());
						$("#extensionD").val($("#extensionB").val());
					}
			}); */
			
			
		});


		function changeRule(){
			if($("#floora").val() != "15F" &&  $("#floora").val()==$("#floorb").val() ){
				$("#pigeonboxD").val($("#pigeonboxA").val());
				$("#pigeonboxC").val($("#pigeonboxB").val());				
			}else{
				$("#pigeonboxC").val($("#pigeonboxA").val());
				$("#pigeonboxD").val($("#pigeonboxB").val());			
			}
		
		}

		function isHiddenSeatB(){
			$.ajax({
				type:"post",
				url: basepath+"SeatServlet",
				data:{"method":"isHidden",
					  "seatnoA":$("#seatnoB").val()},
				success: function(date){
					var dataRole = eval(date);
					if(dataRole == 0){
						saveApply();
						}else{
							alert("您想更换的座位號不允許主動換座，如有需要請聯繫管理員辦理。");
						}
				},error:function(){
    			  alert("Network connection is failed, please try later...");
	  			  return false;
    		  }
			});
			return false;
		}



		function querySeatB(){
			if($("#seatnoB").val().trim().toUpperCase()==$("#seatnoA").val().toUpperCase()){
				alert("您已处于当前座位，无须发起申请！");
				$("#staffcodeB").val("");
				$("#seatnoB").val("");
				$("#floorb").val("");
				$("#floorc").val("");
				$("#floord").val("");
				$("#seatnoC").val("");
				$("#seatnoD").val("");
				$("#staffcodeC").val("");
				$("#staffcodeD").val("");
				$("#pigeonboxB").val("");
				$("#pigeonboxC").val("");
				$("#pigeonboxD").val("");
				$("#extensionB").val("");
				$("#extensionC").val("");
				$("#extensionD").val("");
				$("#seatnoB").focus();
				return false;
			}
			
			if($("#seatnoB").val().trim().length>0){
			$.ajax({
					type:"post",
					url: basepath+"SeatServlet",
					data:{"method":"querySeatB","seatnoB":$("#seatnoB").val()},
					dataType:"json",
					success: function(result){
					if(result.state=="success"){
						if(result.data != null){ 
							$("#staffcodeB").val(result.data.userBList.staffcode);
							$("#extensionB").val(result.data.userBList.extensionno);
							if(result.data.PAList.indexOf(result.data.userBList.staffcode) >-1 ){
								$("#pigeonboxB").val("");
							}else{
								$("#pigeonboxB").val(result.data.userBList.pigenBoxno);
							}	
							$("#floorb").val(result.data.userBList.floor);
							$("#floorc").val($("#floora").val());
							$("#floord").val(result.data.userBList.floor);
							$("#staffname").val(result.data.userBList.staffname);
							$("#seatnoC").val($("#seatnoA").val());
							$("#seatnoD").val(result.data.userBList.seatno);
							$("#staffcodeC").val(result.data.userBList.staffcode);
							$("#staffcodeD").val($("#staffcodeA").val());
							$("#extensionD").val($("#extensionA").val());
							$("#extensionC").val($("#extensionB").val());
							changeRule();
							/* $("#pigeonboxC").val($("#pigeonboxA").val());
							$("#pigeonboxD").val(date.pigenBoxno);
							$("#extensionC").val($("#extensionA").val());
							$("#extensionD").val(date.extensionno); */
						} else {
							alert("当前座位号不存在！");
							$("#seatnoB").val("");
							$("#floorb").val("");
							$("#floorc").val("");
							$("#floord").val("");
							$("#seatnoC").val("");
							$("#seatnoD").val("");
							$("#staffcodeB").val("");
							$("#staffcodeC").val("");
							$("#staffcodeD").val("");
							$("#pigeonboxB").val("");
							$("#pigeonboxC").val("");
							$("#pigeonboxD").val("");
							$("#extensionB").val("");
							$("#extensionC").val("");
							$("#extensionD").val("");
							$("#seatnoB").focus();
						}
						
						
					}else{
						 alert(result.msg);
						 $("#seatnoB").val("").focus();
						 
					}
					
						
					},error:function(){
    			  alert("Network connection is failed, please try later...");
	  			  return false;
    		  }
			});
			}
		}		
		
		function queryStaffcodeB(){
			if($("#staffcodeB").val().trim().toUpperCase()==$("#staffcodeA").val().toUpperCase()){
				alert("您已处于当前座位，无须发起申请！");
				$("#staffcodeB").val("");
				$("#seatnoB").val("");
				$("#floorb").val("");
				$("#floorc").val("");
				$("#floord").val("");
				$("#seatnoC").val("");
				$("#seatnoD").val("");
				$("#staffcodeC").val("");
				$("#staffcodeD").val("");
				$("#pigeonboxB").val("");
				$("#pigeonboxC").val("");
				$("#pigeonboxD").val("");
				$("#extensionB").val("");
				$("#extensionC").val("");
				$("#extensionD").val("");
				$("#staffcodeB").focus();
				return false;
			}
			if($("#staffcodeB").val().trim().length>0){
			$.ajax({
					type:"post",
					url: basepath+"SeatServlet",
					data:{"method":"queryStaffcodeB","staffcodeB":$("#staffcodeB").val()},
					dataType:"json",
					success: function(result){
						if("success" == result.state){
							if(result.data != null){
								$("#seatnoB").val(result.data.userBList.seatno);
								$("#floorb").val(result.data.userBList.floor);
								$("#floorc").val($("#floora").val());
								$("#floord").val(result.data.userBList.floor);
								$("#extensionB").val(result.data.userBList.extensionno);
								if(result.data.PAList.indexOf(result.data.userBList.staffcode) >-1 ){
									$("#pigeonboxB").val("");
								}else{									
									$("#pigeonboxB").val(result.data.userBList.pigenBoxno);
								}								
								$("#staffname").val(result.data.userBList.staffname);
								$("#seatnoC").val($("#seatnoA").val());
								$("#seatnoD").val(result.data.userBList.seatno);
								$("#staffcodeC").val(result.data.userBList.staffcode);
								$("#staffcodeD").val($("#staffcodeA").val());
								$("#extensionD").val($("#extensionA").val());
								$("#extensionC").val($("#extensionB").val());	
								changeRule();					
							/* 	$("#pigeonboxC").val($("#pigeonboxA").val());
								$("#pigeonboxD").val(date.pigenBoxno);
								$("#extensionC").val($("#extensionA").val());
								$("#extensionD").val(date.extensionno); */
								
							} else {
								alert("当前staffcode不存在！");
								$("#seatnoB").val("");
								$("#floorb").val("");
								$("#floorc").val("");
								$("#floord").val("");
								$("#seatnoC").val("");
								$("#seatnoD").val("");
								$("#staffcodeB").val("");
								$("#staffcodeC").val("");
								$("#staffcodeD").val("");
								$("#pigeonboxB").val("");
								$("#pigeonboxC").val("");
								$("#pigeonboxD").val("");
								$("#extensionB").val("");
								$("#extensionC").val("");
								$("#extensionD").val("");
								$("#staffcodeB").focus();
								return false;
							}
						}
					},error:function(){
    			  alert("Network connection is failed, please try later...");
	  			  return false;
    		  }
			});
			}
		}		
		function queryStaffcodeA(){
			$.ajax({
					type:"post",
					url: basepath+"SeatServlet",
					data:{"method":"queryStaffcodeA","staffcodeA":$("#staffcodeA").val()},
					dataType:"json",
					success: function(result){
							if("success" == result.state){													
								if(null != result.data.userBList){
									$("#seatnoA").val(result.data.userBList.seatno);
									$("#extensionA").val(result.data.userBList.extensionno);
									$("#staffcodeA").val(result.data.userBList.staffcode);
									if(result.data.PAList.indexOf(result.data.userBList.staffcode) >-1 ){
										$("#pigeonboxA").val("");
									}else{										
										$("#pigeonboxA").val(result.data.userBList.pigenBoxno);
									}
									$("#floora").val(result.data.userBList.floor);
								}
							}					
					},error:function(){
    			  alert("Network connection is failed, please try later...");
	  			  return false;
    		  }
			});
		
		}			
		function ifLegit(){
			$.ajax({
				type:"post",
				url: basepath+"SeatServlet",
				data:{"method":"ifLegit",
					  "seatnoA":$("#seatnoA").val(),
					  "seatnoB":$("#seatnoB").val()},
				success: function(date){
				var dataRole = eval(date);
				if(dataRole == 0){
						isHiddenSeatA();
					}else if(dataRole > 0){
						alert("被换位者尚有正在執行的換位申請，您不能与其换位。");
						return;
					}else{
						alert("網絡異常，換位申請提交失敗！");
					}
				},error:function(){
    			  alert("Network connection is failed, please try later...");
	  			  return false;
    		  }
			});
			return false;
		}
		
		function ifMeLegit(){
			$.ajax({
				type:"post",
				url: basepath+"SeatServlet",
				data:{"method":"ifMeLegit",
					  "staffcodeA":$("#staffcodeA").val()},
				success: function(date){
					var dataRole = eval(date);
					if(dataRole == 0){
						queryStaffcodeA();
						}else{
							alert("您尚有正在執行的換位申請，不能再次提交。");
							$("#submit").hide();
						}
				},error:function(){
    			  alert("Network connection is failed, please try later...");
	  			  return false;
    		  }
			});
			return false;
		}
		
		
		function isHiddenSeatA(){
			$.ajax({
				type:"post",
				url: basepath+"SeatServlet",
				data:{"method":"isHidden",
					  "seatnoA":$("#seatnoA").val()},
				success: function(date){
					var dataRole = eval(date);
					if(dataRole == 0){
						isHiddenSeatB();
						}else{
							alert("您當前的座位號不允許主動換座，如有需要請聯繫管理員辦理。");
						}
				},error:function(){
    			  alert("Network connection is failed, please try later...");
	  			  return false;
    		  }
			});
			return false;
		}
				
		
		function saveApply(){
		
		if($("#remark").val()==""){																 
			error("remark不能為空！");	
			$("#remark").focus();
			return false;																			 
		}
		
		$("#remarkall").val("Requester："+($("#remark").val().replace(/\n/g, "~.~")));
		
		$("#remark").val("");
	
		parent._ajax("Are you sure submit ? ",basepath+"SeatServlet",$("#info").serialize(),function(result){
										   		if("success"==result.state){
													alert("提交成功");
													location.reload();
												}else{
													alert(result.msg);
												}
										   });
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
  		<form id="info" name="info">
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
  						<input type="text" name="seatnoA" id="seatnoA" readonly="readonly"  value="${userAList.seatno }"/>
  						
  					</td>
  					<td class="tagName">Seat NoB:</td>
  					<td class="tagCont">
  						<input type="text" name="seatnoB" id="seatnoB" onblur="querySeatB();"/>
  						
  					</td>
  					<td class="tagName"></td>
  					<td class="tagName">
  						<input type="text" name="seatnoC" readonly="readonly"  id="seatnoC"/>
  					</td>
  					<td class="tagCont" colspan="2">
  						<input type="text" name="seatnoD" readonly="readonly"  id="seatnoD"/>
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Staff CodeA:</td>
  					<td class="tagCont">
  						<input type="text" name="staffcodeA" id="staffcodeA" readonly="readonly"  value="${userAList.staffcode }"/>
  					</td>
  					<td class="tagName">Staff CodeB:</td>
  					<td class="tagCont">
						<input type="text" name="staffcodeB" id="staffcodeB" onblur="queryStaffcodeB();"/>
						<input type="hidden" name="staffname" id="staffname" />
  					</td>
  					<td class="tagName"></td>
  					<td class="tagName">
  						<input type="text" name="staffcodeC" readonly="readonly"  id="staffcodeC" />
  					</td>
  					<td class="tagCont" colspan="2">
						<input type="text" name="staffcodeD" readonly="readonly"  id="staffcodeD" />
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Extension NoA:</td>
  					<td class="tagCont">
  						<input type="text" name="extensionA" id="extensionA" readonly="readonly"  value="${userAList.extensionno }"/>
  					</td>
  					<td class="tagName">Extension NoB:</td>
  					<td class="tagCont">
  						<input type="text" name="extensionB" readonly="readonly"  id="extensionB"/>
  					</td>
<!--   					<td class="tagCont" style="vertical-align: middle; padding-top: 0px;">
  						<label calss="inline checkbox" style="display: none;" >
  							<input type="checkbox" name="checkbox_extension" id="checkbox_extension"  />
  						</label>
  					</td> -->
  					<td class="tagName"></td>
  					<td class="tagName">
  						<input type="text" name="extensionC" readonly="readonly"  id="extensionC"/>
  					</td>
  					<td class="tagCont" colspan="2">
  						<input type="text" name="extensionD" readonly="readonly"  id="extensionD"/>
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">Pigeon BoxA:</td>
  					<td class="tagCont">
  						<input type="text" name="pigeonboxA" id="pigeonboxA" readonly="readonly"  value="${userAList.pigenBoxno }"/>
  					</td>
  					<td class="tagName">Pigeon BoxB:</td>
  					<td class="tagCont">
  						<input type="text" name="pigeonboxB" readonly="readonly"  id="pigeonboxB"/>
  					</td>
<!--   					<td class="tagCont" style="vertical-align: middle; padding-top: 0px;">
  						<label calss="inline checkbox"  style="display: none;" >
  							<input type="checkbox" name="checkbox_pigeonbox" id="checkbox_pigeonbox" />
  						</label>
  					</td> -->
  					<td class="tagName"></td>
  					<td class="tagName">
  						<input type="text" name="pigeonboxC" readonly="readonly"  id="pigeonboxC"/>
  					</td>
  					<td class="tagCont" colspan="2">
  						<input type="text" name="pigeonboxD" readonly="readonly"  id="pigeonboxD"/>
  					</td>
  				</tr>
  				<tr>
  					<td class="tagName">FloorA:</td>
  					<td class="tagCont">
  						<input type="text" name="floora" readonly="readonly"  id="floora" />
  					</td>
  					<td class="tagName">FloorB:</td>
  					<td class="tagCont">
						<input type="text" name="floorb" readonly="readonly"  id="floorb" />
  					</td>
  					<td class="tagName"></td>
  					<td class="tagName">
  						<input type="text" name="floorc" readonly="readonly"  id="floorc" />
  					</td>
  					<td class="tagCont" colspan="2">
  						<input type="text" name="floord" readonly="readonly"  id="floord" />
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
  						<textarea id="remarkall" name="remarkall" rows="3" cols="" readonly="readonly"  style="width: 97%;"></textarea>
  					</td>
  				</tr>
  				<tr>
  					<td style="text-align: center;" colspan="8">
  						<button class="btn" type="button" id="submitbtn" onclick="ifLegit()">Submitted</button>
  						<!-- <a class="btn" id="submit" onClick="ifLegit()">Submitted</a> -->
						<a class="btn" id="cancel" onclick="javascript:if(confirm('Sure to Reset?')){location.reload();}">Reset</a>
  					</td>
  				</tr>
  			</table>
  			
  			<input name="method" value="saveApply" type="hidden"/>
  			<input id="checkFlag" name="checkFlag" value="0" type="hidden"/>
  			<input id="extensionFlag" name="extensionFlag" value="0" type="hidden"/>
  			</form>
  		</div>
  	</div>
  </div>
  </body>
</html>
