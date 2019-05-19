<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Key Request</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<script type="text/javascript" src="./css/Util.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">
    <style type="text/css">
    .tagCbox{width: 10%!important;}
    .tagText{width: 15%!important;}
    .tagInput{width: 60%!important;}
    </style>
    <script type="text/javascript">
    
    	/*窗体加载事件 */
		$(function(){
			var econvoy=new Array();
			econvoy="${Econvoy}".split("-");
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
			
			$("#staffcode").val(econvoy[0]).select().blur();
			$("#staffname").val(econvoy[1]);
			
			
			
			
			$("#staffcode").blur(function(){
			 
				if($("#staffcode").val().length==6 ){
				$.ajax({ 
				 type: "post", 
				 url: "keyServlet",
				 data: {'staffcode':$("#staffcode").val(),'method':"findcons"},
				 success: function(date){
					 if(date!=null && date!="[null]" && date!="null" && date!=""){
					 	var master=eval(date);
						$("#lockerno").val(master[0].lockerno);
						$("#deskDrawerno").val(master[0].deskDrawerno);
						$("#pigenBoxno").val(master[0].pigenBoxno);
						$("#staffname").val(master[0].staffname);
					 }else{
					 	$.dialog.alert("Without your informattion !");
					 	$("#staffcode").select();
				 		$("#staffname,#location,#lockerno,#deskDrawerno,#pigenBoxno").val("");
						$(":checkbox").attr("checked",false);
					 	return false;
					 }
				 },error:function(){
						 $.dialog.alert("Network connection is failed, please try later...");
						 return false;
					 } 
				  });
				}else{
					  
					error("Please enter the correct Staff Code!");
					$("#staffcode").select();
					$("#staffname,#location,#lockerno,#deskDrawerno,#pigenBoxno").val("");
					$(":checkbox").attr("checked",false);
					return false; 
				}
			});
					 
    		$("#save").click(function(){ 
    			var one=false;//是否选择Request Options
		  		if($("#staffcode").val()==""){//staffcode
		  			error("please input Staffcode!");
		  			$("#staffcode").focus();
		  			return false;
		  		}
		  		if($("#staffname").val()==""){//staffname
		  			error("please input Staffname!");
		  			$("#staffname").focus();
		  			return false;
		  		}
		  		if($("#location").val()==""){															 
					error("Please Select Location!");	
					$("#location").focus();
					return false;																				 
				}	
				if($("#loker").attr("checked")==true){//Desk Phone Reset Password\
					$("#loker").val("Y");
					one=true;
		  		}else{
		  			$("#loker").val("N");
		  		}
		  		
		  		if($("#deskDrawer").attr("checked")==true){//Copier Repair
		  			$("#deskDrawer").val("Y");
		  			one=true;
		  		}else{
		  			$("#deskDrawer").val("N");
		  		}
		  		if($("#pigenBox").attr("checked")==true){//office Maintenance
		  			$("#pigenBox").val("Y");
		  			one=true;
		  		}else{
		  			$("#pigenBox").val("N");
		  		}
		  		if(one ==true){
					 if(confirm("Are you sure to Submit?")){// 确认操作？
				    	 $.ajax({
				    		 url:"keyServlet",
				    		 type:"post",
				    		 data:{"method":"add","staffcode":$.trim($("#staffcode").val()),"staffname":$.trim($("#staffname").val()),"location":$.trim($("#location").val()),"userType":"${convoy_userType}"
				    		 ,"locker":$.trim($("#loker").val()),"lockerno":$.trim($("#lockerno").val()),"deskDrawer":$.trim($("#deskDrawer").val()),"deskDrawerno":$.trim($("#deskDrawerno").val()),
				    		 "pigenBox":$.trim($("#pigenBox").val()),"pigenBoxno":$.trim($("#pigenBoxno").val()),"remarks":$.trim($("#remark").val())},
				    		 success:function(date){
				    			 if(date == 'success'){
				    				  successAlert("We will notify you by email to collect the key in 3 working days.",function(){
					    				 location.reload();
					    			 });
				    			 }else{
				    				 errorAlert(date);
				    				 return false;
				    			 }
				    			 //Cancel();
				    			 //select_adminservice(pagenow);
				    		 },error:function(){
				    			 error("please select at least one Request option!");
				  			   	 return false;
				    		 }
				    	 });
			    	  }else{
						return false;
					}
				 
				}else{
		  			error("please select at least one Request option!");
		  			return false;
		  		}
			});	
			 	
    	});
    	
    </script>
  </head>
<body>
<div class="e-container">
	<div class="info-form">
		<h4>Key Request</h4>
		<table id="topTable">
			<tr class="head_bottom"  style="display:none;">
				<td class="tagName">Ref.number</td>
				<td class="tagCont">
					系統自動獲取的
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code</td>
				<td class="tagCont">
					<input name="staffcode" id="staffcode"  type="text" class="inputstyle" readonly="readonly" />
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant Name</td>
				<td class="tagCont">
					<input name="staffname" id="staffname" type="text" class="inputstyle" readonly="readonly" />
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont">
					<select id="location" >
		              	<option value="">Please Select Location</option>
		              	<option value="@CONVOY">@CONVOY</option>
		              	<option value="CP3">CP3</option>
	            	</select>
	            	<span class="redspan">*</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divIdSelected">
		<h4>Request Options</h4>
		<table id="buttomTable">
			<tr>
				<td class="tagName">Locker Key</td>
				<td class="tagCbox">	
	            	<input id="loker" name="loker" type="checkbox"/>
				</td>
				<td class="tagText">Locker#</td>
				<td class="tagInput">
					<input id="lockerno" name="lockerno" value="" type="text" disabled="disabled" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Desk Drawer Key</td>
				<td class="tagCbox">
					<input name="deskDrawer" id="deskDrawer"  type="checkbox"/>
				</td>
				<td class="tagText">Desk Drawer#</td>
				<td class="tagInput">
	            	<input name="deskDrawerno" id="deskDrawerno" disabled="disabled"  type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Pigeon Box Key</td>
				<td class="tagCbox">
					<input id="pigenBox"  name="pigenBox" type="checkbox"/>
				</td>
				<td class="tagText">Pigeon Box#</td>
				<td class="tagInput">
					<input name="pigenBoxno" id="pigenBoxno" disabled="disabled"  type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Remarks</td>
				<td class="tagCont" colspan="3">
					<input type="text" name="remark" id="remark" size="80" class="inputstyle" maxlength="80" style="width: 70%; min-width: 400px;">
				</td>
			</tr>
			<tr>
				<td class="tagName" colspan="4">Note: * is required</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
		<button class="btn" id="save">Submit</button>
		<button class="btn" id="cancel" onClick="javascript:if(confirm('Sure to Reset?')){location.reload();}">Reset</button>
	</div>
</div>
</body>
</html>
