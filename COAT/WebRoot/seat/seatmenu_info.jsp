<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Add Seat Menu</title>

	<link rel="stylesheet" href="plug/css/bootstrap.css">
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
	<%-- <script type="text/javascript" src="<%=basePath%>plug/js/json2.js"></script> --%>
	<style type="text/css">
	.red{
		color: red;
	}
	.info-form table td{
		padding: 5px 8px;
	}
	.info-form table td input[type="text"],
	.info-form table td select{
		height: 26px;
		line-height: 26px;
	}
	.info-form table td.tagCont textarea{
		width: 90%;
	}
    </style>
	
	
  </head>
  
<body style="overflow-y:hidden;overflow-y:auto;padding-left:2px; ">
<div class="e-container">
	<div class="info-form">
		<table>
			<tr>
				<td class="tagName">Seat No：</td>
				<td class="tagCont">
					<input id="seatno" name="seatno" onkeyup="this.value=this.value.toUpperCase();" type="text" class="inputstyle"/> <span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location：</td>
				<td class="tagCont">
					<select id ="location" name ="location" >
		              	<option value="">Please Select Location</option>
		              	<option value="@CONVOY">@CONVOY</option>
		              	<option value="CP3">CP3</option>
              		</select> 
              		<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Floor：</td>
				<td class="tagCont">
					<select name="floor" id="floor">
		              	<option value="">Please Select Floor</option>
		              	<option value="5F">5F</option>
		              	<option value="7F">7F</option>
		              	<option value="15F">15F</option>
		              	<option value="17F">17F</option>
		              	<option value="40F">40F</option>
	               </select> 
               		<span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Locker No：</td>
				<td class="tagCont">
					<input id="lockerno" name="lockerno" type="text" class="inputstyle"/>
				</td>
			</tr>
			<tr>
				<td class="tagName">Desk Drawer No:</td>
				<td class="tagCont">
					<input id="deskDrawerno" name="deskDrawerno"  type="text" class="inputstyle"/>
				</td>
			</tr>	
			<tr>
				<td class="tagName">Pigeon Box No：</td>
				<td class="tagCont">
					<input id="pigenBoxno" name="pigenBoxno"  type="text" class="inputstyle"/> <span class="red">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">If AD or DD Seat：</td>
				<td class="tagCont">
					<select id ="seatType" name ="seatType" >
		              	<option value="">Please Select ...</option>
		              	<option value="AD">AD</option>
		              	<option value="DD">DD</option>
              		</select> <span class="blue">備註：非AD Seat，也非DD Seat，則無須選擇。</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">If Hidden Seat：</td>
				<td class="tagCont">
					<select id ="ifhidden" name ="ifhidden" >
		              	<option value="">Please Select ...</option>
		              	<option value="Y">YES</option>
		              	<option value="N">NO</option>
		            </select>  <span class="red">*</span>	
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input id="staffcode" name="staffcode"  onkeyup="this.value=this.value.toUpperCase();" type="text" class="inputstyle"/>					
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Name：</td>
				<td class="tagCont">
					<input id="staffname" name="staffname" type="text" class="inputstyle"/> 
				</td>
			</tr>
			<tr>
				<td class="tagName">Extension No：</td>
				<td class="tagCont">
					<input name ="extensionno" id ="extensionno" type="text" class="inputstyle"> 
				</td>
			</tr>
			<tr>
				<td class="tagName">Remark：</td>
				<td class="tagCont">
					<textarea id="remark" name="remark" class="inputstyle"></textarea>
				</td>
			</tr>
																							
		</table>
	</div>
</div>
</body>
<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	
	api.button({
		name : '返回'
	},{
		name : '保存',
		callback : save
	});
	var basepath = "<%=basePath%>";

	$(function(){
		/* 输入座位号验证当前座位号是否已存在*/
		$("#seatno").blur(function(){
			if($("#seatno").val().length==4){
				$.ajax({
					url:basepath+"SeatServlet",
					type:"post",
					data:{"method":"selectSeatNo","seatno":$.trim($("#seatno").val()) },
					dataType:'json',
					success:function(result){
						if("success"==result.state){
						
						}else{
							W.error(result.msg);
							$("#seatno").val("");
							$("#seatno").focus();
							
						}
					}
				});
			
			}else{
				if($("#seatno").val().length != 0){
					W.error("Please Input Legal Seat No！");
					$("#seatno").val("");
					$("#seatno").focus();
				}
			
			}
		
		});
		
		
		
		/* 输入Staff Code查询Staff Code的相关信息*/
		$("#staffcode").blur(function(){
			/* 输入前清空已查询出的相关信息 */
			$("#staffname").val("");
			$("#extensionno").val("");
			$("#warn").html("");
			if($("#staffcode").val().length != 0){
				if($("#staffcode").val().length < 6 ){
					W.error("Please Input Legal Staff Code！");
					$("#staffcode").val("");
					$("#staffcode").focus();
				}else{
					$.ajax({
						url:basepath+"SeatServlet",
						type:"post",
						data:{"method":"getSeatListInfor","staffcode":$.trim($("#staffcode").val()) },
						dataType:'json',
						success:function(result){
							if("success" == result.state){													
								if(null != result.data.seatlist){
									$("#staffname").val(result.data.seatlist.staffname);
									$("#extensionno").val(result.data.seatlist.extensionno);
									$("#staffcode").after(" <span id='warn' class='blue'>提醒：当前staffcode已有座位安排</span> ");
								}
							}
						}
					});	
				}
			}
		});
	});
	

	function save(){
				if($("#seatno").val() == ""){															 
					W.error("Please Input Seat No!");	
					$("#seatno").focus();
					return false;																				 
				}	
				if($("#location").val() == ""){															 
					W.error("Please Select Location!");	
					$("#location").focus();
					return false;																				 
				}
				if($("#floor").val() == ""){															 
					W.error("Please Select Floor!");	
					$("#floor").focus();
					return false;																				 
				}
				if($("#pigenBoxno").val() == ""){															 
					W.error("Please Input PigenBox No!");	
					$("#pigenBoxno").focus();
					return false;																				 
				}
				if($("#ifhidden").val() == ""){															 
					W.error("Please Select If Hidden Seat!");	
					$("#ifhidden").focus();
					return false;																				 
				}
				
				/* 当staffcode或者staffname有值时，需进一步验证 */
				if($("#staffcode").val() != "" ||$("#staffname").val() != ""){
					if($("#staffcode").val()==""){															 
						W.error("Please Input StaffCode!");	
						$("#staffcode").focus();
						return false;																				 
					}
					if($("#staffcode").val() != "" && $("#staffcode").val().length < 6){															 
						W.error("Please Input Legal StaffCode!");	
						$("#staffcode").focus();
						return false;																				 
					}					
					
					if($("#staffname").val() == ""){															 
						W.error("Please Input StaffName!");	
						$("#staffname").focus();
						return false;																				 
					}					
					if($("#extensionno").val() == ""){															 
						W.error("Please Input Extension No!");	
						$("#extensionno").focus();
						return false;																				 
					}																				 
				}
				
				
																				
					 if(confirm("Are you sure to save?")){// 确认操作？
				    	 $.ajax({
				    		 url:basepath+"SeatServlet",
				    		 type:"post",	
                 beforeSend: function(){
					parent.showLoad();
				 },
				 complete: function(){
					parent.closeLoad();
				 },				    		 		               							 		    		 
				    		 data:{"method":"add","staffcode":$.trim($("#staffcode").val()),"seatType":$.trim($("#seatType").val()),"staffname":$.trim($("#staffname").val()),"location":$.trim($("#location").val()),"pigenBoxno":$.trim($("#pigenBoxno").val())
				    		 ,"floor":$.trim($("#floor").val()),"ifhidden":$.trim($("#ifhidden").val()),"extensionno":$.trim($("#extensionno").val()),"deskDrawerno":$.trim($("#deskDrawerno").val()),"seatno":$.trim($("#seatno").val()),"lockerno":$.trim($("#lockerno").val()),"remark":$.trim($("#remark").val())},
				    		 dataType:'json',
				    		 success:function(result){
				    				if("success" == result.state){
				    					W.$("#search").click();
				    					api.close();
				    					W.success(result.msg);
				    				}else{
				    					W.error(result.msg);
				    				}
				    		 },error:function(result){
				    			 W.error("Network connection is failed, please try later...");
				  			   	 return false;
				    		 }
				    	 });
			    	  }
			    	  return false;
	}

</script>
</html>
