<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML >
<html>
  <head>
    <base href="<%=basePath%>">
    <title>ADM SERVICES REQUEST</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">

	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">

    <style type="text/css">
    .tagCbox{width:10%;}
    .tagText{width:15%;}
    .tagInput{width:60%;}
    </style>
    <script type="text/javascript">
    var passwordss="";
    var extension="";
  	$(function(){
  		$(".field input,select").not($("#staffcode,#staffname,#location")).attr("disabled",true);
  		var econvoy=new Array();
			econvoy="${Econvoy}".split("-");
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
			
			$("#staffcode").val(econvoy[0]).blur();
		//	$("#staffcode").val("AA0201").blur();
			$("#staffname").val(econvoy[1]);
			
			$("#staffcode").blur(function(){
				$.ajax({
					url:"admservice/AdminServicesServlet",
					type:"post",
					data:{"method":"queryAllExtension","staffcode":$("#staffcode").val()},
					success:function(date){
						var dataRole=eval(date);
  						if($("#check_3").val()=="Y"){
							$("#forgetExtension").val(dataRole[0].extension);
  						}
  						if($("#check_phone2").val()=="Y"){
							$("#phone2").val(dataRole[0].extension);
  						}
  						extension=dataRole[0].extension;
  						passwordss=dataRole[0].password;
					},error:function(){
						alert("Can't get the Extension!");
						return false;
					}
	  			 });
			});
			
			
  		/*
  		 * 点击图片触发checkbox选择(jimmy) 
  		 */
	$("#formCheckbox4019").click(function(){
		$(".formImg[value='4019']").css("border","1px solid gray");//border: 1px solid red;
		$(".formImg[value='4010']").css("border","0px solid red");//border: 1px solid red;
	});
	$("#formCheckbox4010").click(function(){
		$(".formImg[value='4010']").css("border","1px solid gray");//border: 1px solid red;
		$(".formImg[value='4019']").css("border","0px solid red");//border: 1px solid red;
	});
  		 	
  		 $('img.formImg').each(function(){
  			 $(this).click(function(){
  				var valId = $(this).attr('value')
  				var flag = false;
  				flag =  $('#formCheckbox'+valId).attr('checked');
	            if(!flag){
	            	//--->只能选择一种型号的电话
	            	if(valId=="4019"){
	            		$("input[ccc='e']").attr("checked",false);
	            		$("input[ccc='c']").attr("checked",true);
	            		$(".formImg[value='4019']").css("border","1px solid gray");//border: 1px solid red;
	            		$(".formImg[value='4010']").css("border","0px solid red");//border: 1px solid red;
	            	}else{
	            		$("input[ccc='c']").attr("checked",false);
	            		$("input[ccc='e']").attr("checked",true);
	            		$(".formImg[value='4010']").css("border","1px solid gray");
	            		$(".formImg[value='4019']").css("border","0px solid red");
	            	}
	            	//$('#formCheckbox'+valId).attr('checked',true);
	            }else{
	                $('#formCheckbox'+valId).attr('checked',false);
	            }
  			 });
  		 });
  		/**
  		 * 复选框只能选择一个
  		 * */
  		$("input[name='cb']").live("click",function(){
		    if(this.checked){
			    if($(this).attr("ccc")=="c"){
			    	$("input[ccc='e']").attr("checked",false);
			    }else{
			    	$("input[ccc='c']").attr("checked",false);
			    }
		    }
		});
  		
  		
  		/**
  		 * 
  		 *Change of Fluorescent Tube 
  		 * */
  		$("#check_changeTube").click(function(){
  			if(!$(this).attr("checked")){
  				$(this).val("N");
  				$("#floor1,#seat").attr("disabled",true).val("");
  			}else{
  				$(this).val("Y");
  				$("#floor1,#seat").attr("disabled",false);
  			}
  		});
  		/**
  		 * Desk Phone Repair
  		 * 
  		 * */
  		$("#check_phone1").click(function(){
  			if(!$(this).attr("checked")){
  				$(this).val("N");
  				$("#phone1").attr("disabled",true).val("");
  				$("#seatNumber").attr("disabled",true).val("");
  				$("#passwords").attr("disabled",true).val("");
  			}else{
  				$(this).val("Y");
  				$("#phone1").attr("disabled",false)
  				$("#seatNumber").attr("disabled",false).val("");
  				$("#passwords").attr("disabled",false).val("");
  			}
  			
  		});
  		
  		
  		/**
  		 * Desk Phone Reset Password
  		 * 
  		 * */
  		$("#check_phone2").click(function(){
  			if(!$(this).attr("checked")){
  				$(this).val("N");
  				$("#phone2").val("");
  				$("#about_reamrk").hide(); 
  			}else{
  				$("#about_reamrk").show(); 
  				$(this).val("Y");
  				if(extension==""){
	  				$.ajax({
						url:"admservice/AdminServicesServlet",
						type:"post",
						data:{"method":"queryAllExtension","staffcode":$("#staffcode").val()},
						success:function(date){
							var dataRole=eval(date);
	  						$("#phone2").val(dataRole[0].extension);
	  						passwordss=dataRole[0].password;
						},error:function(){
							alert("Can't get the Extension!");
							return false;
						}
		  			 });
				}else{
					$("#phone2").val(extension);
				}
  			}
  		});
  		
  		/**
  		 * Desk Phone Reset Password
  		 * 
  		 * */
  		$("#check_3").click(function(){
  			if(!$(this).attr("checked")){
  				$(this).val("N");
  				$("#forgetExtension").val("");
  			
  			}else{
  				$(this).val("Y");
				if(extension==""){
	  				$.ajax({
						url:"admservice/AdminServicesServlet",
						type:"post",
						data:{"method":"queryAllExtension","staffcode":$("#staffcode").val()},
						success:function(date){
							var dataRole=eval(date);
	  						$("#forgetExtension").val(dataRole[0].extension);
	  						passwordss=dataRole[0].password;
						},error:function(){
							alert("Can't get the Extension!");
							return false;
						}
		  			 });
				}else{
					$("#forgetExtension").val(extension);
				}
  			}
  		});
  		
  		/**
  		 * Copier Repair
  		 * 
  		 * */
  		$("#check_copier").click(function(){
  			if(!$(this).attr("checked")){
  				$(this).val("N");
  				$("#floor2,#copier").attr("disabled",true).val("");
  			}else{
  				$(this).val("Y");
  				$("#floor2,#copier").attr("disabled",false);
  			}
  		});
  		/**
  		 * office Maintenance
  		 * 
  		 * */
  		$("#check_other").click(function(){
  			if(!$(this).attr("checked")){
  				$(this).val("N");
  				$("#floor3,#descript").attr("disabled",true).val("");
  			}else{
  				$(this).val("Y");
  				$("#floor3,#descript").attr("disabled",false);
  			}
  		});
  		
  		
  		
  		
  		
  		
  		
  	  	$("td.tagCbox").find("input[type='checkbox']").click(function(){
  	  		var t=$(this).parent().parent().attr("name");
  	  		if(this.checked){
  	  			//alert(this.id+"=="+$(this).parent().parent().attr("name"));
  	  			$("#buttomTable").find("input[type='checkbox']").not(this).not("value='4019'").not("value='4010'").removeAttr("checked",false).val("N");
  	  			
  	  			$("#buttomTable tr[name!="+t+"]").each(function(){
  	  				$(this).find("input[type='text'],select").val("").attr("disabled","disabled");
  	  			//forgetExtension,phone2
  	  			
  	  			
  	  			});//.find("input[type='text'],select").val("asdg");
  	  		}
  	  		
  	  	});
  		
  		
  		
  	});
  	

  	/**
  	 * 保存adminservice
  	 */
  	function save_adminservice(){
  		var xinghao="";//型号
  		var one=false;//是否选择Request Options
	  		if($("#staffcode").val()==""){//staffcode
	  			alert("please input Staffcode!");
	  			$("#staffcode").focus();
	  			return false;
	  		}
	  		if($("#staffname").val()==""){//staffname
	  			alert("please input Staffname!");
	  			$("#staffname").focus();
	  			return false;
	  		}
	  		if($("#location").val()==""){//location
	  			alert("please select Location!");
	  			$("#location").focus();
	  			return false;
	  		}
			 if($("#check_changeTube").attr("checked")==true){//Change of Fluorescent Tube 
				 one=true;
	  			if($("#floor1").val()==""){
  					alert("please select Floor!");
		  			$("#floor1").focus();
		  			return false;
	  			}
	  			if($("#seat").val()==""){
	  				alert("please input Seat Number!");
		  			$("#seat").focus();
		  			return false;
	  			}
	  		}
			 if($("#check_phone1").attr("checked")==true){//Desk Phone Repair
				 one=true;
	  			if($("#phone1").val()==""){
  					alert("please input Extension!");
		  			$("#phone1").focus();
		  			return false;
	  			}
	  			if($("#seatNumber").val()==""){
  					alert("please input Seat Number!");
		  			$("#seatNumber").focus();
		  			return false;
	  			}
	  			if($("#passwords").val()==""){
  					alert("please input Password!");
		  			$("#passwords").focus();
		  			return false;
	  			}
	  			var a=0;
	  			$("#check_xh").find("input[name='cb']").each(function(){ 
		   		   if(this.checked){
		   			   	xinghao=this.value;
				   }else{
					   a+=1;
				   }
		  	   	});
	  			if(a==2){
	  				alert("please choose Phone Type!");
		  			return false;
	  			}
	  		}
			 if($("#check_phone2").attr("checked")==true){//Desk Phone Reset Password
				 one=true;
	  			if($("#phone2").val()==""){
  					alert("please input Extension!");
		  			$("#phone2").focus();
		  			return false;
	  			}
	  		}
			if($("#check_3").attr("checked")==true){//Forget Extension's Password
				 one=true;
	  			if($("#forgetExtension").val()==""){
  					alert("Extension# cant be empty!");
		  			$("#forgetExtension").focus();
		  			return false;
	  			}
	  		}
	  		if($("#check_copier").attr("checked")==true){//Copier Repair
	  			one=true;
	  			if($("#floor2").val()==""){
  					alert("please select Floor!");
		  			$("#floor2").focus();
		  			return false;
	  			}
	  			if($("#copier").val()==""){
	  				alert("please input copier!");
		  			$("#copier").focus();
		  			return false;
	  			}
	  		}
	  		if($("#check_other").attr("checked")==true){//office Maintenance
	  			one=true;
	  			if($("#floor3").val()==""){
  					alert("please select Floor!");
		  			$("#floor3").focus();
		  			return false;
	  			}
	  			if($("#descript").val()==""){
	  				alert("please input descriptions!");
		  			$("#descript").focus();
		  			return false;
	  			}
	  		}
	  		//通过所有验证
	  		if(one){//用户至少选择了一个Request Option
	  			if(confirm("Are you sure to Submitted?")){// 确认保存？
	  					$.ajax({
	  						url:"admservice/AdminServicesServlet",
	  						type:"post",
	  						data:{
		  						"method":"add","staffcode":$("#staffcode").val(),"staffname":$("#staffname").val(),
		  						"fluortube":$("#check_changeTube").val(),"userType":"${convoy_userType}",
		  						"floor":$("#floor1").val(),"seat":$("#seat").val(),"phoneRepair":$("#check_phone1").val(),
		  						"phone1":$("#phone1").val(),"phoneRpass":$("#check_phone2").val(),"phone2":$("#phone2").val(),
		  						"copierRepair":$("#check_copier").val(),"floor2":$("#floor2").val(),"copier":$("#copier").val(),
		  						"officeMain":$("#check_other").val(),"floor3":$("#floor3").val(),"descript":$("#descript").val(),
		  						"remark":$("#remarks").val(),"location":$("#location").val(),
		  						"seatNumber":$("#seatNumber").val(),
		  						"passwords":$("#passwords").val(),
		  						"forgetExtension":$("#forgetExtension").val(),
		  						"xinghao":xinghao,"password":passwordss
		  					},success:function(date){
	  							alert(date);
	  							location.reload();
	  						},error:function(){
	  							alert("Network connection is failed, please try later...");
								return false;
	  						}
	  					});
	  		
	  			}
	  		}else{
	  			alert("please select at least one Request option!");
	  			return false;
	  		}
	  				
	  			
	  		
  		
  		
  	}
  	
    </script>
  </head>
<body>
<div class="e-container">
	<div class="info-form">
		<h4>ADM SERVICES REQUEST</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Consultant/Staff Code</td>
				<td class="tagCont">
					<input id="staffcode" type="text" class="inputstyle" readonly="readonly" />
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Name</td>
				<td class="tagCont">
					<input id="staffname" type="text" class="inputstyle" readonly="readonly" />
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont">
					<select id="location">
						<option value="">Please Select Location</option>	    	
		              	<option>@CONVOY</option>
		              	<option>CP3</option>
		              	<option >148 Electric Road</option>
		              	<option >Peninsula</option>
		            </select> <span class="redspan">*</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divIdSelected">
		<h4>Request Options</h4>
		<table id="buttomTable">
			<tr name="1">
				<td class="tagName" rowspan="2">Change of Fluorescent Tube</td>
				<td class="tagCbox" rowspan="2">
					<input id="check_changeTube" type="checkbox"/>
				</td>
				<td class="tagText">Floor</td>
				<td class="tagInput">
					 <select id="floor1">
		             	<option value="">Please Select Location</option>	
		              	<option>5F</option>
		              	<option>7F</option>
		              	<option>15F</option>
	              		<option>24F</option>
              			<option>39F</option>
             			<option>40F</option>
           				<option>CP3</option>
           				<option>10F 148 Electric Road</option>
           				<option>16F Peninsula</option>
           				<option>18F 148 Electric Road</option>
		             </select>
				</td>
			</tr>
			<tr name="1">
				<td class="tagText">Seat Number</td>
				<td class="tagInput">
					<input type="text" id="seat" disabled="disabled"/>
				</td>
			</tr>
			<!-- ------------------------------------------------------------------------------------ -->
			<%--<tr style="background: red; ">
			--%><tr name="3">
				<td class="tagName" rowspan="5">Desk Phone Repair</td>
				<td class="tagCbox" rowspan="5">
					<input type="checkbox" value="N"  id="check_phone1"/>
				</td>
				<td class="tagText">Seat Number</td>
				<td class="tagInput"><input type="text" id="seatNumber" disabled="disabled" /></td>
			</tr>
			<%--<tr style="background: red; ">
			--%><tr name="3">
				<td class="tagText">Extension#</td>
				<td class="tagInput">
					<input type="text" id="phone1" disabled="disabled" />
				</td>
			</tr>
			<%--<tr style="background: red; ">
			--%><tr name="3">
				<td class="tagText">Password</td>
				<td class="tagInput">
					<input type="text" id="passwords" disabled="disabled" />
				</td>
			</tr>
			<%--<tr style="background: red; ">
				--%>
			<tr name="3">
				<td class="tagText">Phone Type</td>
				<td class="tagInput" id="check_xh">
					<label class="inline checkbox">
              			<input type="checkbox" id="formCheckbox4019" name="cb" ccc='c' value="4019"/>4019
              		</label>
              		<label class="inline checkbox">
              			<input type="checkbox" id="formCheckbox4010" name="cb" ccc='e' value="4010"/>4010
              		</label>
				</td>
			</tr>
			<%--<tr style="background: red; ">
			--%><tr name="3">
				<td class="tagText" colspan="2">
					<img class="formImg" value="4019" src="images/phone_4019.jpg" />
              		<img class="formImg" value="4010" src="images/phone_4010.jpg" />
				</td>
			</tr>
			
			<%--<tr >
				<td class="tagName" >Desk Phone Repair</td>
				<td class="tagCbox" >
					<input type="checkbox" value="N"  id="check_phone1"/>
				</td>
				<td class="tagText">Extension#</td>
				<td class="tagInput"><input type="text" id="phone1" disabled="disabled"></td>
			</tr>--%>
			<tr name="4">
				<td class="tagName" >Phone Voicemail Reset</td><!-- style="background: red;" -->
				<%--<td class="tagName" >Desk Phone Reset Password</td>--%>
				<td class="tagCbox">
					<input type="checkbox" value="N"  id="check_phone2"/>
				</td>
				<td class="tagText">Extension#</td>
				<td class="tagInput">
					<input type="text" id="phone2"  readonly="readonly"/>
				</td>
			</tr>
			<!-- --------------------------------------------------------------------- -->
			<%--<tr style="background: red; ">
			--%><tr name="5">
				<td class="tagName">Forget Extension's Password</td>
				<td class="tagCbox">
					<input type="checkbox" value="N"  id="check_3" />
				</td>
				<td class="tagText">Extension#</td>
				<td class="tagInput">
					<input type="text" id="forgetExtension" readonly="readonly"/>
				</td>
			</tr>
			<tr name="6">
				<td class="tagName" rowspan="2">Copier Repair</td>
				<td class="tagCbox" rowspan="2">
					<input value="N"  type="checkbox" id="check_copier"/>
				</td>
				<td class="tagText">Floor</td>
				<td class="tagInput">
					<select id="floor2">
		                <option value="">Please Select Location</option>	
		              	<option>5F</option>
		              	<option>7F</option>
		              	<option>15F</option>
	              		<option>24F</option>
              			<option>39F</option>
            			<option>40F</option>
						<option>10F 148 Electric Road</option>
            			<option>16F Peninsula</option>
            			<option>18F 148 Electric Road</option>
		            </select>
				</td>
			<tr name="6">
				<td class="tagText">Copier Serial#</td>
				<td class="tagInput">
					<input type="text" id="copier"  disabled="disabled"/>
				</td>
			</tr>
			<tr name="8">
				<td class="tagName" rowspan="2">Others (Wet Pantry,Public Area,Toilet,etc...)</td>
				<td class="tagCbox" rowspan="2">
					<input type="checkbox" value="N" id="check_other"/>
				</td>
				<td class="tagText">Floor</td>
				<td class="tagInput">
					<select id="floor3">
		               <option value="">Please Select Location</option>	
		              	<option>5F</option>
		              	<option>7F</option>
		              	<option>15F</option>
	              		<option>24F</option>
              			<option>39F</option>
           				<option>40F</option>
           				<option>CP3</option>
           				<option>10F 148 Electric Road</option>
           				<option>16F Peninsula</option>
           				<option>18F 148 Electric Road</option>
		            </select>
				</td>
			</tr>
			<tr name="8">
				<td class="tagText">Descriptions of request</td>
				<td class="tagInput">
					<input type="text" id="descript"  disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Remarks</td>
				<td class="tagCont" colspan="3">
					<input id="remarks" type="text" class="inputstyle" style="width:70%;min-width: 400px;" maxlength="90"/>
				</td>
			</tr>
			<tr>
				<td class="tagName" style="color: blue;">Note: * is required
				</td>
				<td class="tagCont" colspan="3" style="color: red " >
					<span style="display:none;" id="about_reamrk">After reset the voice mail box, non-listen voice mails will be erased.<%-- 
					2.Please set up the voice mail immediately.
					3.The temporary password of the voice mail is your extension number after resetting.</span>
				--%></td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
		<button class="btn" id="save" name="save" onclick="save_adminservice();">Submit</button>
		<button class="btn" id="cancel" onClick="javascript:if(confirm('Sure to Reset?')){location.reload();}">Reset</button>
	</div>
</div>
</body>
</html>
