<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Company Request</title>
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
    <script type="text/javascript">
    
    	/*窗体加载事件 */
    	$(function(){
    		//loadValue();
    		 var econvoy=new Array();
			econvoy="${Econvoy}".split("-");
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
			
			$("#staffcode").val(econvoy[0]);
			$("#staffname").val(econvoy[1]);
    		
			 
    	});
    	
    	function vailReturnDate(){
    	//时间判断
			if($("#collectionDate").val()!="" && $("#returnDate").val()!=""){
				var beginTime = $("#collectionDate").val();
				var endTime = $("#returnDate").val();
				
				if(tab(endTime,beginTime)==1){
					error("returnDate 超出 collectionDate 7 天，請重新輸入 .");
					$("#returnDate").focus();
					return false;
				}

			}
    	}
    	
    	function search (){
    			if($("#collectionDate").val()==""){
    				alert("Please Select collectionDate!");
    			}else{
					vailReturnDate();
					loadValue($("#collectionDate").val());
					
				}
			 
    	
    	}
    	
    	//判断时间超过7天
    	function tab(date1,date2){
    		var oDate1 = new Date(date1);
    		var oDate2 = new Date(date2);
    		if(oDate1.getTime() - oDate2.getTime() > 604800000){
    			return 1;
    		}
    	}
    	
    	
    	
          function save(){
         	var item ="";
         		if($("#eventName").val() == ""){
					error("Please Input Event Name!");
					$("#eventName").focus();
					return false;
				}
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
		  		
    			if($("#collectionDate").val() == ""){
					error("Please Input Collection Date!");
					$("#collectionDate").focus();
					return false;
				}
    			if($("#returnDate").val() == ""){
					error("Please Input Return Date!");
					$("#returnDate").focus();
					return false;
				}
			//时间判断
				vailReturnDate();
				
				
				if($("#location").val()==""){															 
					error("Please Select Location!");	
					$("#location").focus();
					return false;																				 
				}	
   				 $("[type='checkbox']:checked").each(function(){
						//$(this).children().eq(1).each(function(){
						//	sale_record+=$(this).val()+",";
						//});
						//alert(this.value);
						item +=this.value+"&#&";
				 }); 
				 if(item==""){
				 	error("please select at least one Request option!");
				 	return false;
				 }
				  if(confirm("Are you sure to Submit this record?")){// 确认操作？
				    	 $.ajax({
				    		 url:"companyAssetServlet",
				    		 type:"post",
				    		 data:{"method":"add","staffcode":$.trim($("#staffcode").val()),"staffname":$.trim($("#staffname").val()),"location":$.trim($("#location").val()),"userType":"${convoy_userType}","eventName":$.trim($("#eventName").val())
				    		 ,"collectionDate":$.trim($("#collectionDate").val()),"returnDate":$.trim($("#returnDate").val()),"item":item},
				    		 success:function(date){
				    			 successAlert(date,function(){
				    				 location.reload();
				    			 });
				    		 },error:function(){
				    			 $.dialog.alert("please select at least one Request option!");
				  			   	 return false;
				    		 }
				    	 });
			    	  }else{
						return false;
					}
			}
          function showItem(){
          	var openVal = $("#openId").val();
          	if(openVal == "打开"){
          		$("#Medical").show();
          		$("#openId").val("隐藏");
          	}else{
          		document.getElementById("Medical").style.display = "none";
          		$("#openId").val("打开");
          	}
          }
          function cancelOrder(){
          		if(confirm("Sure to Reset?")){
          			location.reload();
          			location.flush();
          		}
          }
           /**
	     * 产品加载
	     * 
	     * */
	  	function loadValue(collectionDate){
	  	var d="";
			$.ajax({
				type: "post",
				url: "companyAssetServlet",
				data:{"method":"finditem","edate":collectionDate},
				success: function(date){
					var d=eval(date);
 
					var html_left="";
					var html_right="";
					var prices="";
					$(".product,.product").remove();
					for(var i=0;i<d.length;i++){
							html_left+="<tr id='left"+i+"' class='product'><td align='left'>"+
							d[i].itemcode+"</td><td align='left'>"
							+d[i].itemname+"</td>";
							if(d[i].remainnum<1){
								html_left+="<td title='No'><input type='checkbox' disabled='disabled' id='check"+i+"' value='"+d[i].itemcode+"' /></td></tr>"
							}else{
								html_left+="<td><input type='checkbox' id='check"+i+"' value='"+d[i].itemcode+"'  /></td></tr>"
							}
							
							}
							// html_left+="<tr>";
	             			// html_left+="<td colspan='3'  align='center' ><input id='save' name='save' type='submit' value='Submit'  onclick='save();' align='left'/> </td>";
	              			// html_left+="</tr>";
						$("#buttomTable").append(html_left);
				}
			}); 
		}
    </script>
  </head>
<body>
<div class="e-container">
	<div class="info-form" id="divIdSelected">
		<h4>Request For Company Asset</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Event Name</td>
				<td class="tagCont" colspan="3">
					<input id="eventName" type="text" size="50" class="inputstyle" maxlength="50"/>
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Code</td>
				<td class="tagCbox">
					<input name="staffcode" id="staffcode"  type="text" class="inputstyle" readonly="readonly" />
		    		<span class="redspan">*</span>
				</td>
				<td class="tagText">Staff Name</td>
				<td class="tagInput">
					<input name="staffname" id="staffname" type="text" class="inputstyle" readonly="readonly" />
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Date of collection</td>
				<td class="tagCbox">
					<input id ="collectionDate" name="collectionDate" type="text"  class="inputstyle" onfocus="return Calendar('collectionDate')" onClick="return Calendar('collectionDate')"/>
		    		<span class="redspan">*</span>
				</td>
				<td class="tagText">Date of return</td>
				<td class="tagInput">
					<input id="returnDate" name="returnDate" type="text"  class="inputstyle" onfocus="return Calendar('returnDate')" onClick="return Calendar('returnDate')"/>
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont" colspan="3">
					<select id="location" >
		              	<option value="@CONVOY">@CONVOY</option>
		            </select>
	            	<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName" colspan="4">
					<button class="btn" id="search" onclick="search();">
						<i class="icon-search"></i>
						Search
					</button>
	    			<span>For the items being borrowed by others on your request days, it will be disabled to “tick”</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divId">
		<h4>Company Asset</h4>
		<table id="buttomTable">
			<tr class="head_top">
				<td class="width_15">Items Code</td>
				<td class="width_70">Items Name</td>
				<td class="width_15">Borrow?</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
		<button class="btn" id="save" onclick="save();">Submit</button>
		<button class="btn" id="cancel" onClick="javascript:if(confirm('Sure to Reset?')){location.reload();}">Reset</button>
	</div>
</div>
</body>
</html>
