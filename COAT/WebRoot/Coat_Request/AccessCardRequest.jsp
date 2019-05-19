 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%
 String path = request.getContextPath();
 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
 
 <!DOCTYPE HTML>
 <html>
   <head>
     <base href="<%=basePath%>">
     <title>Access Card Request</title>
     <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
 	<meta http-equiv="pragma" content="no-cache">
 	<meta http-equiv="cache-control" content="no-cache">
 	<meta http-equiv="description" content="This is my page">
 
 	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
 	<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
 	<script type="text/javascript" src="plug/js/Common.js"></script>
 	<script type="text/javascript" src="./css/ajaxfileupload.js"></script>
 	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
 	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
 	<!--[if lte IE 7]> 
 	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
 	<![endif]--> 
 		
 	<link rel="stylesheet" href="./css/layout.css">
 	<link rel="stylesheet" href="./plug/css/site.css">
     <script type="text/javascript">
     var result=false;
     var fileName="";
     $(function(){
     	
     	var econvoy=new Array();
 			econvoy="${Econvoy}".split("-");
 			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
 			
 			$("#staffcode").val(econvoy[0]);
 			$("#staffname").val(econvoy[1]);
 			
 			
 			
     	$("#select_reason").change(function(){
     		if(this.value!="Other" && this.value!="Special Request"){
     			$("#reason").val(this.value).hide();
     		}else{
     			$("#reason").val("").show();
     		}
     	});
     	$("#shangchuan").hide();
     	/**
     	 * 复选框单击事件
     	 * @memberOf {TypeName} 
     	 */
     	$("[id^=checks]").click(function(){
     		if(this.checked){
     			this.value="Y";
     			$("#shangchuan").show();
     		}else{
     			$("#shangchuan").hide();
     			this.value="N";
     		}
     	});
     	 
     	$("[id^=check_staffcard]").click(function(){
     		if(this.checked){
     			this.value="Y";
     		}else{
     			this.value="N";
     		}
     	});
     	
     });
     
      function fileupload(){
     	    if($("#filePaths").val()==""){ 
                 errorAlert("请选择有效文件"); 
                 return false;  
             }
            $.ajaxFileUpload({  
                 url:'AccessCardServlet?method=getPath&staffcode='+$("#staffcode").val(), 
                 secureuri:false,  
                 fileElementId:'filePaths',  
                 dataType: 'text/html',
                 success: function (data) {
                    if(data=="error"){
 	      	 		    errorAlert("上传失败");
 	      	 		    result=false;
 	                }else if(data=="-1"){
 	                	errorAlert("文件格式错误!");
 	      	 		}else{
 	                    fileName=data;
 	                    successAlert("上传成功，上传路径为："+fileName);
 	                    result=true;
 	      	 		}
                 },error: function (data, status, e){  
                     errorAlert("fail");
                     result=false;
                 }  
             });  
      }
     
     
     function save_Access(){
     	var photosticker="";
     	if($("#checks").attr("checked")){
     		photosticker="Y";
     		if(result){
     		
     		}else{
     			error("Please upload the Photo!");
     			return false;
     		}
     	}else{
     		photosticker="N";
     	}
     	
     	
     	if($("#staffcode").val()==""){//员工编号
     		error("please input Staff Code!");
     		$("#staffcode").focus();
     		return false;
     	}
     	if($("#staffname").val()==""){//员工姓名
     		error("please input Staff Name!");
     		$("#staffname").focus();
     		return false;
     	}
     	if($("#location").val()==""){//Location
     		error("please select Location!");
     		$("#location").focus();
     		return false;
     	}
     	if($("[id^='check']:checked").length<1){//判断是否选择一个请求
    			error("please check at least one Request option!");
    			return false;
     	}
     	if($("#select_reason").val()==""){
     		error("please select Reason!");
     		$("#select_reason").focus();
     		return false;
     	}
    	if($("#select_reason").val()=="Print Photo only" && $("#checks").attr("checked") == false ){
    		error("Please checked photo sticker!");
    		$("#select_reason").focus();
    		return false;
    	}
     	if($("#select_reason").val()=="Special Request" && $("#reason").val()==""){
     		error("please input Reason!");
     		$("#reason").focus();
     		return false;
     	}
     	
     	if($("#select_reason").val()=="Other" && $("#reason").val()==""){
     		error("please input Reason!");
     		$("#reason").focus();
     		return false;
     	}
     	if($.trim($("#reason").val())!=""){
 	    	if($("#select_reason").val()=="Special Request" ||$("#select_reason").val()=="Other" ){
 	    		$("#reason").val($("#select_reason").val()+":"+$("#reason").val());
 	    	}
     	}
     	
    		if(confirm("Are you sure to Submitted?")){// 确认保存？
     		$.ajax({
     			url:"AccessCardServlet",
     			type:"post",
     			data:{"method":"add","staffcode":$("#staffcode").val(),"staffname":$("#staffname").val(),"userType":"${convoy_userType}",
     			"location":$("#location").val(),"photosticker":photosticker,"staffcard":$("#check_staffcard").val(),
     			"reason":$("#reason").val(),"fileName":fileName},
     			success:function(date){
 					successAlert(date);
 					location.reload();
     			},error:function(){
     				errorAlert("Network connection is failed, please try later...");
 					return false;
     			}
     		});
    		}
     	
     }
     
     
    
     
     </script>
   </head>
 <body>
 <div class="e-container">
 	<div class="info-form">
 		<h4>Access Card/Photos Sticker Request</h4>
 		<table id="topTable">
 			<tr>
 				<td class="tagName">Consultant/Staff Code</td>
 				<td class="tagCont">
 					<input type="text" id="staffcode" class="inputstyle" readonly="readonly" />
 	    			<span class="redspan">*</span>
 				</td>
 			</tr>
 			<tr>
 				<td class="tagName">Consultant/Staff Name</td>
 				<td class="tagCont">
 					<input type="text" id="staffname" class="inputstyle" readonly="readonly" />
 		    		<span class="redspan">*</span>
 				</td>
 			</tr>
 			<tr>
 				<td class="tagName">Location</td>
 				<td class="tagCont">
 					<select id="location">
 		              	<option value="">please select location</option>
 		              	<option value="@CONVOY">@CONVOY</option>
 		              	<option value="CP3">CP3</option>
 		              	<option >148 Electric Road</option>
 		              	<option >Peninsula</option>
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
 				<td class="tagName">Staff Card</td>
 				<td class="tagCont">
 					<input id="check_staffcard" value="N"  type="checkbox"/>
 				</td>
 			</tr>
 			<tr>
 				<td class="tagName">Photo sticker</td>
 				<td class="tagCont">
 					<input type="checkbox" name="checks" id="checks" style="float:left;">
 					<div id="shangchuan" style="float: left;">
 						<input type="file" name="filePaths" id="filePaths" style="float: left; height: 20px; margin-left: 20px; margin-top: 0px!important;" />
    						<button class="btn" name="fileLoad" id="fileLoad" onclick="fileupload()" style="float: left; height: 20px; padding: 0px 5px;">
    							<i class="icon-upload"></i>
    							Upload
    						</button> 
    					</div>
    					<div style="clear: both;"></div>
 				</td>
 			</tr>
 			<tr>
 				<td class="tagName">Reason</td>
 				<td class="tagCont">
 					<select id="select_reason" >
 			            <option value="">please select Reason</option>
 			            <option value="Loss">Loss</option>
 			            <option value="Damage">Damage</option>
			            <option value="Print Photo only"> Print Photo only</option>
 			            <option value="Special Request"> Special Request</option>
 			            <option value="Other">other</option>
 		            </select>
 			        <input id="reason" type="text" style="width:450;display:none;" maxlength="95" />
 		            <span class="redspan">*</span>
 				</td>
 			</tr>
 			<tr>
 				<td class="tagName">Remark:</td>
 				<td class="tagCont">
 					<p>1.	if no need to change the accesscard, please donnot choose “staff card” item.</p>
 					<p>2.	Only passport photo standard with WHITE background will be accepted.</p>
 	          		<p>3.	All upload file must be IMAGE(圖像)format(eg.jpeg or tiff).</p>
 				</td>
 			</tr>
 		</table>
 	</div>
 	<div class="btn-board">
 	    <button class="btn" onclick="save_Access()">Submit</button>
 	    <button class="btn" id="cancel" onClick="javascript:if(confirm('Sure to Reset?')){location.reload();}">Reset</button>
 	</div>
 </div>
 </body>
 </html>