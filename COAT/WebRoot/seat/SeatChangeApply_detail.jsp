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
		/* if("${seatChangeApply.checkflag}" == 1){
			$("#checkbox_pigeonbox").attr("checked",true);
		}else{
			$("#checkbox_pigeonbox").attr("checked",false);
		}

		if("${seatChangeApply.extensionflag}" == 1){
			$("#checkbox_extension").attr("checked",true);
		}else{
			$("#checkbox_extension").attr("checked",false);
		} */
				
		$("#completed").hide();
		$("#void").hide();
		$("#canel").hide();
		$("#remarkall").attr("disabled","disabled");

        if ("${seatChangeApply.status}" == "Confirmed"){
			$("#completed").show();
			$("#void").show();
			$("#canel").show();
		} else {
			$("#canel").show();
		}	
		
		
		
	
	}

/* 	function confirm(buttonval){
               $.ajax({
                 url:"SeatServlet", 
                 type:"post",
                 data:{"method":"confirm",
                 	   "refno":$("#refno").val(),
                 	   "status":buttonval,
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
						                     if(date!="error"){
						  	                          //发送邮件
						                                var data=eval(date);
						                                  //data[0] 代表申请人的邮箱   data[1] 代表付款人的邮箱 
						                                 var staffname=temp[0].staffname;
						                                 var date2=$("#date").val();
						                                 var date1=getDate();
						                                 var Person=temp[0].contactperson;
						                                 var email=temp[0].contactemail;
						                                 var mediaName=temp[1].medianame;
						                                 alert(sendMailForRecruitment(data[0],data[1]+";"+email,email_confirmation(staffname,date1,date2,Person,email,mediaName)));
						                                 //刷新页面
						                                 hideview();
						                   }else{
						                    alert("failed");
						                   }
				   } 
				});
          return false;
	} */
	function complete(buttonval){
	
		    if($("#remark").val()==""){																 
				error("remark不能為空！");	
				$("#remark").focus();
				return false;																			 
			}	
		$("#remarkall").val($("#remarkall").val().replace(/\n/g, "~.~")+"~.~"+"HK ADM："+($("#remark").val().replace(/\n/g, "~.~")));
		
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
                 data:{"method":"complete",
                 	   "refno":$("#refno").val(),
                 	   "seatnoC":$("#seatnoC").val(),
                 	   "seatnoD":$("#seatnoD").val(),
                 	   "staffcodeC":$("#staffcodeC").val(),
                 	   "staffcodeA":$("#staffcodeA").val(),
                 	   "staffcodeB":$("#staffcodeB").val(),
                 	   "staffcodeD":$("#staffcodeD").val(),
                 	/*    "extensionC":$("#extensionC").val(),
                 	   "extensionD":$("#extensionD").val(),
                 	   "pigeonboxC":$("#pigeonboxC").val(),
                 	   "pigeonboxD":$("#pigeonboxD").val(), */
                 	   "status":buttonval,
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
						                     /**if(date!="error"){
						  	                          //发送邮件
						                                var data=eval(date);
						                                  //data[0] 代表申请人的邮箱   data[1] 代表付款人的邮箱 
						                                 var staffname=temp[0].staffname;
						                                 var date2=$("#date").val();
						                                 var date1=getDate();
						                                 var Person=temp[0].contactperson;
						                                 var email=temp[0].contactemail;
						                                 var mediaName=temp[1].medianame;
						                                 alert(sendMailForRecruitment(data[0],data[1]+";"+email,email_confirmation(staffname,date1,date2,Person,email,mediaName)));
						                                 //刷新页面
						                                 hideview();
						                   }else{
						                    alert("failed");
						                   }**/
				   } 
				});
          return false;
	}


	function cancel(){
		location.href="<%=basePath%>"+"SeatServlet?method=cancel";
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
  						<input type="text" name="seatnoD" disabled="disabled" id="seatnoD" />
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
  				</tr>
  				<tr>
  					<td style="text-align: center;" colspan="8">
						<a class="btn" id="completed"  onClick="complete('Completed');">Completed</a>
						<a class="btn" id="void" onClick="complete('VOID')">VOID</a>
						<a class="btn" id="canel" onClick="cancel();">Cancel</a>
  					</td>
  				</tr>
  			</table>
  		</div>
  	</div>
  </div>

  </body>
</html>
