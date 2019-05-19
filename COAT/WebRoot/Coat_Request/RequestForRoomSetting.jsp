<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Request for Room Setting</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<script type="text/javascript" src="./css/Util.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" href="plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
	<link rel="stylesheet" href="css/layout.css">
	<link rel="stylesheet" href="plug/css/site.css">
    <style type="text/css">
	.redspan{
		color:red;	
	}
    </style>
    <script type="text/javascript">
    var rule=false;
    	function saveRoomSetting(){
    		
    		var eventName = $("#eventName").val();
    		var eventDate = $("#eventDate").val();
    		var startTime = $("#start_date").val();
    		var endTime = $("#end_date").val();
    		var convoy1 = document.getElementById("R+S").checked;
    		var convoy2 = document.getElementById("T+V").checked;
    		var convoy3 = document.getElementById("R+S+T+V").checked;
    		var convoy4 = document.getElementById("Convoy13+Convoy14").checked;
    		var convoy5 = document.getElementById("Convoy17+Convoy18").checked;
    		
    		var convoy = "";
    		if(convoy1 == true){
    			convoy += "R+S#";
    		}
    		if(convoy2 == true){
    			convoy += "T+V#";
    		}
    		if(convoy3 == true){
    			convoy += "R+S+T+V#";
    		}
    		if(convoy4==true){
    			convoy += "Convoy13+Convoy14#";
    		}
    		if(convoy5==true){
    			convoy += "Convoy17+Convoy18#";
    		}
    		var cp31 = document.getElementById("CP-01+").checked;
    		var cp32 = document.getElementById("CP-02+").checked;
    		var cp33 = document.getElementById("CP-03+").checked;
    		var cp34 = document.getElementById("CP-04+").checked;
    		var cp35 = document.getElementById("CP-05+").checked;
    		var cp36 = document.getElementById("CP-06+").checked;
    		var cp3 = "";
    		if(cp31 == true){
    			cp3 += "CP-01+#";
    		}
    		if(cp32 == true){
    			cp3 += "CP-02+#";
    		}
    		if(cp33 == true){
    			cp3 += "CP-03+#";
    		}
    		if(cp34 == true){
    			cp3 += "CP-04+#";
    		}
    		if(cp35 == true){
    			cp3 += "CP-05+#";
    		}
    		if(cp36 == true){
    			cp3 += "CP-06+#";
    		}
    		
    		
    		
    		
    		if($("#staffcode").val()==""){
    			error("please input Staff Code!");
    			$("#staffcode").focus();
    			return false;
    		}
    		if($("#staffname").val()==""){
    			error("please input Staff Name!");
    			$("#staffname").focus();
    			return false;
    		}
    		
    		if(eventName==""){
    			error("please input eventName!");
    			$("#eventName").focus();
    			return false;
    		}
    		
    		
    		if(eventDate==""){
    			error("please input eventDate!");
    			$("#eventDate").focus();
    			return false;
    		}
    		
    		
    		
    		
    		/**if(startTime==""){
    			alert("please input eventTime!");
    			$("#start_date").focus();
    			return false;
    		}
    		if(endTime==""){
    			alert("please input eventTime!");
    			$("#end_date").focus();
    			return false;
    		}**/
    		if($("#start_hour").val()==""){
    			error("Please select From Hour");
    			$("#start_hour").focus();
    			return false;
    		}
    		if($("#start_min").val()==""){
    			error("Please select From Min");
    			$("#start_min").focus();
    			return false;
    		}
    		if($("#end_hour").val()==""){
    			error("Please select To Hour");
    			$("#end_hour").focus();
    			return false;
    		}
    		if($("#end_min").val()==""){
    			error("Please select To Min");
    			$("#end_min").focus();
    			return false;
    		}
    		//alert(parseFloat($("#start_hour").val()+$("#start_min").val())+"---"+parseFloat($("#end_hour").val()+$("#end_min").val()));
    		if(parseFloat($("#start_hour").val()+$("#start_min").val())>=parseFloat($("#end_hour").val()+$("#end_min").val())){
  					error("From Time can’t be later than To Time.");
  					return false;
    		}
    		
    		startTime=$("#start_hour").val()+":"+$("#start_min").val();
    		endTime=$("#end_hour").val()+":"+$("#end_min").val();
    		
    	  if($("input:checkbox:checked").length<1){
    		  error("please check at least one Request option!");
   				return false;
    	  }
   
    		
    		var remarks = $("#remarks").val();
    		 
    		 		
    		 var flag=true;
 		var bef=null;
 	$(".CP3:checked").each(function(){
   			if(bef!=null){
   				if(parseFloat(this.title)-bef!=1){
   					alert("Please follow the house rules");
   					flag=false;
   					
   					return false;
   				}else{
   					bef=this.title;
   					flag=true;
   				}
   			}else{
   				flag=true;
   				bef=this.title;
   			}
   	});
    	 	if(flag){
		    		if(confirm("Sure to Submit?")){
						$.ajax({
							type: "post",
							url: "SaveRoomSettingServlet",
							data:{"eventName":eventName,"eventDate":eventDate,
								"start_date":startTime,"end_date":endTime,"convoy":convoy,"cp3":cp3,
								"remarks":remarks,"staffcode":$("#staffcode").val(),"staffname":$("#staffname").val(),"userType":"${convoy_userType}"},
							success: function(msg){
								if(msg == "已存在"){
									errorAlert("EventName already exists!");
									return false;
								}
								successAlert("Your request will be handled on the event date.",function(){
									window.location.reload()
								});
							},error:function(){
							  error("Network connection is failed, please try later...");
							  return false;
					     	}
						});
					}
    		 }
    		 
    		 
    		
    	}
    	function judgeCp3(id){
    		/**
    		var cp3 = document.getElementById(id).checked;
    		var numb = id.substring(3,id.length-1);
    		var number = parseInt(numb)+1;
    		if(cp3 == true && number < 7){
    			document.getElementById("CP-0"+number+"+").disabled="";
    		}else{
    			for(i = number; i<7;i++){
	    			document.getElementById("CP-0"+i+"+").checked="";
	    			document.getElementById("CP-0"+i+"+").disabled="disabled";
    			}
    		}**/
    	}
    	
    	
    	$(function(){
    		
    		var econvoy=new Array();
			econvoy="${Econvoy}".split("-");
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
			
			$("#staffcode").val(econvoy[0]);
			$("#staffname").val(econvoy[1]);
    		
    		
    		
    		
    		$(".convoy").click(function(){
    			if(this.checked){
	    			$(".convoy").attr("checked",false);
	    			$(this).attr("checked",true);
    			}
    		});
    		
    		/**
    		 * 开始时间小时选择改变事件
    		 * @memberOf {TypeName} 
    		 */
    		$("#start_hour").change(function(){
    			if(this.value!=""){
    				$("#start_min").focus();
    			}
    		});
    		/**
    		 * 结束时间小时选择改变事件
    		 * @memberOf {TypeName} 
    		 */
    		$("#end_hour").change(function(){
    			if(this.value!=""){
    					$("#end_min").focus();
    			}
    		});
   			/**
    		 * 开始时间分钟选择改变事件
    		 * @memberOf {TypeName} 
    		 */
    		$("#start_min").change(function(){
    			if(this.value!=""){
    				if($("#start_hour").val()==""){
    					$("#start_hour").focus();
    				}else{
    					$("#end_hour").focus();
    				}
    			}
    		});
   		 	/**
    		 * 结束时间分钟选择改变事件
    		 * @memberOf {TypeName} 
    		 */
   			$("#end_min").change(function(){
    			if(this.value!=""){
    				if($("#end_hour").val()==""){
    					$("#end_hour").focus();
    				}
    			}
    		});
    		
    		
    		
    		
    		/**$(".CP3").click(function(){
    			//alert($(".CP3:checked").length);
    			
    			if(this.checked){
    					var be=this;
    				$(".CP3").not($(be)).each(function(){
    					//alert(parseFloat(this.title)+"---"+parseFloat(be.title));
    					if(parseFloat(this.title)-parseFloat(be.title)==1 || parseFloat(be.title)-parseFloat(this.title)==1){
    						$(this).attr("disabled",false);
    					}else{
    						if(!this.checked)
    							$(this).attr("disabled",true);
    					}
    				});
    			}else{
    				if($(".CP3:checked").length<1){
    					$(".CP3").attr("disabled",false);
    				}else{
    					
	    					if($(".CP3[title='"+(parseFloat(this.title-1))+"']").attr("checked") && $(".CP3[title='"+(parseFloat(this.title+1))+"']").attr("checked")){
	    						$(this).attr("checked",true);
	    					}
    					
    					
    					
    			 
    					
    						
    				}
    			}
    				
    			
    			
    		 
    		});**/
    		
    	});
    	
    	
    	function getValue(){
    		var bef=null;
    			$(".CP3:checked").each(function(){
 		
   			if(bef!=null){
   				alert(parseFloat(this.title)+"---"+bef);
   				if(parseFloat(this.title)-bef!=1){
   					alert("Please follow the house rules");
   					flag=false;
   					return false;
   				}else{
   					flag=true;
   				}
   			}else{
   				flag=true;
   				bef=this.title;
   			}
   		});
    		/**var bef=null;
    	 
    		$(".CP3:checked").each(function(){
    			alert(bef);
    			if(bef!=null){
    				if(parseFloat(this.title)-bef>1){
    					rule=false;
    					return ;
    				}
    			}else{
    				bef=this.title;
    				rule=true;
    			}
    		});**/
    		
    		
    	}
    </script>
  </head>
<body style='overflow:auto;padding-left:2px;' background="css/officeAdmin_bg.gif">
<div class="e-container">
	<div class="info-form">
		<h4>Request for Room Setting</h4>
		<table>
			<tr>
				<td class="tagName">Staff Code</td>
				<td class="tagCont" colspan="2">
					<input class="inputstyle" id="staffcode" type="text" readonly="readonly" />
	    			<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Staff Name</td>
				<td class="tagCont" colspan="2">
					<input type="text" id="staffname" class="inputstyle" readonly="readonly"  />
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Event Name</td>
				<td class="tagCont" colspan="2">
					<input class="inputstyle" id="eventName" type="text" maxlength="20"/>
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Event Date</td>
				<td class="tagCont" colspan="2">
					<input type="text" id="eventDate" class="inputstyle" onfocus="return Calendar('eventDate')" onclick="return Calendar('eventDate')"/>
		    		<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Time</td>
				<td class="tagCont" colspan="2">
					From:
			    	<select id="start_hour">
			    		<option value="">Hour</option>
			    		<option>00</option>
			    		<option>01</option>
			    		<option>02</option>
			    		<option>03</option>
			    		<option>04</option>
			    		<option>05</option>
			    		<option>06</option>
			    		<option>07</option>
			    		<option>08</option>
			    		<option>09</option>
			    		<option>10</option>
			    		<option>11</option>
			    		<option>12</option>
			    		<option>13</option>
			    		<option>14</option>
			    		<option>15</option>
			    		<option>16</option>
			    		<option>17</option>
			    		<option>18</option>
			    		<option>19</option>
			    		<option>20</option>
			    		<option>21</option>
			    		<option>22</option>
			    		<option>23</option>
			    	</select>
			    	<select id="start_min">
			    		<option value="">Min</option>
			    		<option>00</option>
			    		<option>15</option>
			    		<option>30</option>
			    		<option>45</option>
			    	</select>
			    	<input type="hidden" id="start_date" class="inputstyle" /> 
			    	To:
			    	<select id="end_hour">
			    		<option value="">Hour</option>
			    		<option>00</option>
			    		<option>01</option>
			    		<option>02</option>
			    		<option>03</option>
			    		<option>04</option>
			    		<option>05</option>
			    		<option>06</option>
			    		<option>07</option>
			    		<option>08</option>
			    		<option>09</option>
			    		<option>10</option>
			    		<option>11</option>
			    		<option>12</option>
			    		<option>13</option>
			    		<option>14</option>
			    		<option>15</option>
			    		<option>16</option>
			    		<option>17</option>
			    		<option>18</option>
			    		<option>19</option>
			    		<option>20</option>
			    		<option>21</option>
			    		<option>22</option>
			    		<option>23</option>
			    	</select>
			    	<select id="end_min">
			    		<option value="">Min</option>
			    		<option>00</option>
			    		<option>15</option>
			    		<option>30</option>
			    		<option>45</option>
			    	</select>
			    	<input type="hidden" id="end_date" class="inputstyle" />
			    	<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName" rowspan="5">@CONVOY</td>
				<td class="mid">
					<input type="checkbox"  id="R+S" value="R+S" class="convoy"/>
				</td>
				<td class="right">5/F R+S</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" id="T+V" value="T+V" class="convoy"/>
				</td>
				<td class="right">5/F T+V</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" id="R+S+T+V" value="R+S+T+V" class="convoy"/>
				</td>
				<td class="right">5/F R+S+T+V</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" id="Convoy13+Convoy14" value="Convoy 13 + Convoy 14" class="convoy"/>
				</td>
				<td class="right">39/F Convoy 13 + Convoy 14</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox" id="Convoy17+Convoy18" value="Convoy 17 + Convoy 18" class="convoy"/>
				</td>
				<td class="right">39/F Convoy 17 + Convoy 18</td>
			</tr>
			<tr>
				<td class="tagName" rowspan="6">17/F CP3</td>
				<td class="mid">
					<input type="checkbox" id="CP-01+" title="1" value="CP-01+" class="CP3" onclick="judgeCp3('CP-01+');"/>
				</td>
				<td class="right">CP-01+</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox"  id="CP-02+" title="2"  value="CP-02+"  class="CP3"  onclick="judgeCp3('CP-02+');"/>
				</td>
				<td class="right">CP-02+</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox"  id="CP-03+" title="3"  value="CP-03+" class="CP3"  onclick="judgeCp3('CP-03+');"/>
				</td>
				<td class="right">CP-03+</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox"  id="CP-04+" title="4"  value="CP-04+" class="CP3"  onclick="judgeCp3('CP-04+');"/>
				</td>
				<td class="right">CP-04+</td>
			</tr>
			<tr>
				<td class="mid">
					<input type="checkbox"  id="CP-05+" title="5"  value="CP-05+" class="CP3"  onclick="judgeCp3('CP-05+');"/>
				</td>
				<td class="right">CP-05+</td>
			</tr>
          	<tr>
	            <td class="mid">
	            	<input type="checkbox"  id="CP-06+" title="6"  value="CP-06+" class="CP3"  onclick="judgeCp3('CP-06+');"/>
	            </td>
	            <td class="right">CP-06+</td>
            </tr>
            <tr>
            	<td class="tagName">Remarks</td>
            	<td class="tagCont" colspan="2">
            		<input type="text" id="remarks" class="inputstyle" maxlength="255" style="width:70%; min-width: 400px;"/>
            	</td>
            </tr>
            <tr>
            	<td class="tagName" colspan="3">
            		Note: * is required
            		CP3 choose room rules: between two rooms must be even, does not allow any vacancies in the middle
            	</td>
            </tr>
		</table>
	</div>
	<div class="btn-board">
		<a class="btn" id="save" name="save" onclick="saveRoomSetting();">Submit</a>
		<a class="btn" id="cancel" onclick="javascript:if(confirm('Sure to Reset?')){location.reload();}">Reset</a>
	</div>
</div>
</body>
</html>
