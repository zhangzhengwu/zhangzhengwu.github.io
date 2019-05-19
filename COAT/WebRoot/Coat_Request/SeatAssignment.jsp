<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Seat Assignment </title>
  	 <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
	
	<script type="text/javascript" src="css/Util.js?ver=<%=new Date() %>"></script>
	<script type="text/javascript" src="plug/js/fileUp.js"></script>
    <style type="text/css">
	.red{
		color: red;
	}
    </style>
    <script type="text/javascript">
    	/*窗体加载事件 */
		$(function(){
    		
    		
    		$("#save").click(function(){ 
    			
				if($("#staffcode").val()==""){															 
					error("Please Input StaffCode!");	
					$("#staffcode").focus();
					return false;																				 
				}
				if($("#staffname").val()==""){															 
					error("Please Input StaffName!");	
					$("#staffname").focus();
					return false;																				 
				}	
				if($("#location").val()==""){															 
					error("Please Select Location!");	
					$("#location").focus();
					return false;																				 
				}		 	
				if($("#extensionno").val()==""){															 
					error("Please Select Extension No!");	
					$("#extensionno").focus();
					return false;																				 
				}				
				if($("#floor").val()==""){															 
					error("Please Select Floor!");	
					$("#floor").focus();
					return false;																				 
				}				
				if($("#seatno").val()==""){															 
					error("Please Select Seat No!");	
					$("#seatno").focus();
					return false;																				 
				}				
				if($("#lokerno").val()==""){															 
					error("Please Select Loker No!");	
					$("#lokerno").focus();
					return false;																				 
				}				
				if($("#deskDrawerno").val()==""){															 
					error("Please Select DeskDrawer No!");	
					$("#deskDrawerno").focus();
					return false;																				 
				}				
				if($("#pigenBoxno").val()==""){															 
					error("Please Select PigenBox No!");	
					$("#pigenBoxno").focus();
					return false;																				 
				}	
					 if(confirm("Are you sure to Submit?")){// 确认操作？
						 
				    	 $.ajax({
				    		 url:"seatServiceServlet",
				    		 type:"post",
				    		 async:false,
				    		 data:{"method":"add","staffcode":$.trim($("#staffcode").val()),"staffname":$.trim($("#staffname").val()),"location":$.trim($("#location").val()),"pigenBoxno":$.trim($("#pigenBoxno").val())
				    		 ,"floor":$.trim($("#floor").val()),"extensionno":$.trim($("#extensionno").val()),"deskDrawerno":$.trim($("#deskDrawerno").val()),"seatno":$.trim($("#seatno").val()),"lockerno":$.trim($("#lokerno").val()),"remark":$.trim($("#remark").val())},
				    		 success:function(date){
				    				var result=$.parseJSON(date);
				    				if(result.state=="success"){
				    					successAlert("Success!");
				    				}else{
				    					errorAlert(result.msg);
						    			return false;
				    				}
				    			 //alert(date);
				    			//var cont="<tr><td>"+$.trim($("#staffcode").val())+"</td><td>"+$.trim($("#staffname").val())+"</td><td>"+$.trim($("#seatno").val())+"</td><td>"+$.trim($("#pigenBoxno").val())+"</td><td>"+$.trim($("#extensionno").val())+"</td><td>"+$.trim($("#floor").val())+" "+$.trim($("#location").val())+"</td></tr>";
				    			
				    			//if(date == 'Success'){
				    				//successAlert(sendMails(getEmail($("#staffcode").val())+";"+getRecruiterEmail($("#staffcode").val()), email_seat_Consultant($("#location").val()=="CP3"?"17/F":"40/F",cont)),function(){
				    				//	location.reload();
				    			//	});
				    			//}else{
				    			//	errorAlert("error");
				    			//	return false;
				    			//}
				    		 },error:function(){
				    			 errorAlert("please select at least one Request option!");
				  			   	 return false;
				    		 }
				    	 });
			    	  }else{
						return false;
					}
				
			
			});	
			function getPath(obj)  { 
			if(obj){ 
					if (window.navigator.userAgent.indexOf("MSIE")>=1){
						obj.select(); $("#products").focus();
						return document.selection.createRange().text;    
					} else if(window.navigator.userAgent.indexOf("Firefox")>=1){    
						if(obj.files){
							return obj.files.item(0).getAsDataURL();
				 		}       
						return obj.value; 
					}     
				return obj.value;   
				} 
			} 
			function check(){
	    		if($("#staffcode").val()==""){															 
					error("Please Input StaffCode!");	
					$("#staffcode").focus();
					return false;																				 
				}
				if($("#staffname").val()==""){															 
					error("Please Input StaffName!");	
					$("#staffname").focus();
					return false;																				 
				}	
				if($("#location").val()==""){															 
					error("Please Select Location!");	
					$("#location").focus();
					return false;																				 
				}		 	
				if($("#extensionno").val()==""){															 
					error("Please Select Extension No!");	
					$("#extensionno").focus();
					return false;																				 
				}				
				if($("#floor").val()==""){															 
					error("Please Select Floor!");	
					$("#floor").focus();
					return false;																				 
				}				
				if($("#seatno").val()==""){															 
					error("Please Select Seat No!");	
					$("#seatno").focus();
					return false;																				 
				}				
				if($("#lokerno").val()==""){															 
					error("Please Select Loker No!");	
					$("#lokerno").focus();
					return false;																				 
				}				
				if($("#deskDrawerno").val()==""){															 
					error("Please Select DeskDrawer No!");	
					$("#deskDrawerno").focus();
					return false;																				 
				}				
				if($("#pigenBoxno").val()==""){															 
					error("Please Select PigenBox No!");	
					$("#pigenBoxno").focus();
					return false;																				 
				}				
	    		return true;			
	    	}	
    	});
    	function fileupload(){
    	
             if($("#filePaths").val()==""){ 
                 error("请选择有效文件"); 
                 return false;  
             }  
             /*if(window.attachEvent){
                //说明用户使用ie 浏览器
                return;
             } */   
             $.ajaxFileUpload({  
                  url:'UpLoadFileServlet', 
                  secureuri:false,  
                  fileElementId:'filePaths',  
                  dataType: 'text/html',
                  complete: function(){
            	 	$('#filecont').empty();
                  },
                  success: function (data) {
                     if(data!="error"){
	                     fileName=data;
	                    // alert("上传成功，上传路径为："+fileName);
	                     uploadseat(fileName);
	                 }
       	 		    else{
	       	 		    errorAlert("上传失败");
	       	 		}
                  },error: function (data, status, e){  
                      errorAlert("fail");  
                  }  
              });  
           }
           
           function uploadseat(fileName){
           	if(fileName==""){
				error("請選擇需要上傳的文件！");
				return;
			}
			//$("#inputs").attr("disabled","disabled");
			 $.ajax({
				type: "post",
				url:"UpSeatServlet",
				dateType:"html",
				data: {'up':fileName},
				success:function(date){
					if(date != "null"){
						$("#inputs").attr("disabled",false);
						//var dataRole=eval(date);
						$("#num").empty();	
						var html="<font>"+date+"</font>";
						$("#num").append(html);
						successAlert(date);
					
					}else{
						errorAlert("非系统所需文件！");
					}
				 },error:function(){
					 errorAlert("网络连接失败，请稍后重试...");
					 return false;
				 } 
				});
           
           }
           $('#inputs').live('click',function(){
        	   
        	   
           });
           /*$('#filePaths').live('change',function(){
				var _this = $(this), pathStr = _this.val().split('\\');
				var fileName = pathStr[pathStr.length-1];
				
				if(fileName.length > 0){
					$('#filecont').text(fileName);
					$('.fileBtn').show();
				}else{
					$('#filecont').empty();
				}
			});*/
    </script>
  </head>
<body style="overflow-y:hidden;overflow-y:auto;background-image:url('css/officeAdmin_bg.gif');padding-left:2px;">
<div class="e-container">
	<div class="info-form" id="topTable">
		<h4>Seat Assignment</h4>
		<table>
			<tr style="display: none;">
				<td class="tagName">Ref.number</td>
				<td class="tagCont">
					系統自動獲取的
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code</td>
				<td class="tagCont">
					<input id="staffcode" name="staffcode" type="text" class="inputstyle"/> <span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Name</td>
				<td class="tagCont">
					<input id="staffname" name="staffname" type="text" class="inputstyle"/> <span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont">
					<select id ="location" name ="location" >
		              	<option value="">Please Select Location</option>
		              	<option value="@CONVOY">@CONVOY</option>
		              	<option value="CP3">CP3</option>
              		</select> 
              		<span class="red">*</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divIdSelected">
		<h4>Seat Information</h4>
		<table>
			<tr>
				<td class="tagName">Extension No</td>
				<td class="tagCont">
					<input name ="extensionno"  maxlength="4"  id ="extensionno" type="text" class="inputstyle"> 
					<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Floor</td>
				<td class="tagCont">
					<select name="floor" id="floor">
		              	<option value="">Please Select Floor</option>
		              	<option value="5F">5F</option>
		              	<option value="7F">7F</option>
		              	<option value="15F">15F</option>
		              	<option value="24F">24F</option>
		              	<option value="39F">39F</option>
		              	<option value="40F">40F</option>
		              	<option value="CP3">CP3</option>
	               </select> 
               		<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Seat No</td>
				<td class="tagCont">
					<input id="seatno" maxlength="4" name="seatno" type="text" class="inputstyle"/> <span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Locker No</td>
				<td class="tagCont">
					<input id="lokerno" name="lokerno" maxlength="5"  type="text" class="inputstyle"/> <span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Desk Drawer No</td>
				<td class="tagCont">
					<input id="deskDrawerno" name="deskDrawerno"  maxlength="5"  type="text" class="inputstyle"/> <span class="red">*</span>
				</td>
			</tr>
			
			<tr>
				<td class="tagName">Pigeon Box No</td>
				<td class="tagCont">
					<input id="pigenBoxno" name="pigenBoxno" maxlength="5"  type="text" class="inputstyle"/> <span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Remarks</td>
				<td class="tagCont">
					<input id="remark" name="remark" type="text" size="80" class="inputstyle" maxlength="80"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Note: * is required</td>
				<td class="tagCont" id="setFileUp">
					<c:if test="${roleObj.upd==1}">
					<script type="text/javascript">
					$('#setFileUp').fileUp({
						fileName : 'filePaths',
						fileId : 'filePaths',
						upbtnId : 'inputs',
						upload : function(){
							fileupload();
						}
					});
					</script>
						 
	    				 <!-- <input type="button" name="fileLoad" id="fileLoad" value="Upload" onclick="fileupload()"> --> 
	    			</c:if>		
				</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
		<c:if test="${roleObj.add==1}">
			<button class="btn" id="save">Submit</button>
		</c:if>
		<button class="btn" id="cancel" onClick="javascript:if(confirm('Sure to Reset?')){location.reload();}">Reset</button>
	</div>
</div>
</body>
</html>
