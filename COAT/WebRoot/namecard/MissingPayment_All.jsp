<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>
<%
//if(session.getAttribute("convoy_username")==null || session.getAttribute("convoy_username")==""){
//	out.println("<script>parent.location.href='../error.jsp';</script>");
//}
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>missing</title>
 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="Missing Payment Report">  

    <style type="text/css">
.radio{padding-top: 0px!important; margin-top: 0px!important;}
</style>
  </head>
  <script type="text/javascript" >
  	  var downs=null;
  	  var dataRole ="";
      var pagenow =1;
	  var totalpage=1;
	  var total=0;
	  var quan="";
 	  //window.onresize=popupDiv;
	  function popupDiv(){
	  	 $("#content").css("top",$("#Medical").height());
		 $("#content,#Medical").width($("body").width());
	  }
	    
	  $(function(){
	  	 getPrincipal();
		 //$("#staffcodes").val("${convoy_username}");//香港Loging code
	     $("#content").css("top",$("#Medical").height());
		 $("#content,#Medical").width($("body").width());
	 	 $("#start_date").val((new Date()).getFullYear()+"-01-01");
	     $("#end_date").val((new Date()).getFullYear()+"-12-31");
	     $("#shangchuan").hide();
	     
	     
        $("#btn4").click(function(){
           
          $("[name='pipei']").each(function(){
            if($(this).attr("checked"))
            {
                $(this).removeAttr("checked");
            }
            else
            {
                $(this).attr("checked",'true');
            }
            
          });
        });
        
       $("#upload").click(function(){
       		  $("#shangchuan").show();
       		  $("#num").empty();	
       });
       $("#delall").click(function(){
       		  quan="ss";
	          $("[name='delesall']").each(function(i){
	              //str+=$(this).val()+"\r\n";
	            var s=$(this);
		 		$("[title='"+$(this).attr("title")+"']").each(function(n){
		 			if($(this).attr("checked")=="checked")
		 			{
		 				i++;
		 				s.click();
		 			}
		 		});
	          });
	          quan="";
	          alert("删除所有成功!");
	          $("#searchs").click();
	       });
	 
	 /*********************************改变时间格式*********************************************/
	 function CurentTime()
     { 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        var clock = year + "-";
        if(month < 10)
            clock += "0";
       		clock += month + "-";
        if(day < 10)
            clock += "0";
            clock += day + "";
        return clock; 
    } 
	 function getPrincipal(){
		$("#principal").empty();
		var html;
		html+="<option value=''>Please Select Principal.</option>";
		
		$.ajax({
			type: "post",
			url: "SelectMissPrincipalServlet",
			data: null,
			success: function(date){
			var d=eval(date);
			if(d.length>0){
				for(var i=0;i<d.length;i++){
				html+="<option value='"+d[i].principal+"'>"+d[i].remark+"</option>";
				}
				$("#principal:last").append(html);
				}
			}
		});
	} 
	
	$("#down").click(function(){ 
			var code ="";
			var lastday ="";
			if($("#stage1").attr("checked") == true){
				code = 1;
				lastday = $("#lastday").val();
			}else{
				code = 2;
				if($("#start_date").val() !="" && $("#end_date").val() ==""){
					alert("Please Check The EndDate!");
					$("#endDate").focus();
					return false;
				}
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("Start Date can’t be later than End Date.");
						$("#start_date").focus();
						return false;
					}
				}
			}
			window.location.href= "<%=basePath%>"+"DownMissingPaymentReportServlet?startdate="+$("#start_date").val()+"&ctype="+code+"&lastday="+lastday+"&enddate="+$("#end_date").val()+"&staffcode="+$("#staffcode").val()+"&clientname="+$("#clientname").val()+"&principal="+$("#principal").val()+"&policyno="+$("#policyno").val();
	});
	
	$("#searchs").click(function(){
		selects(pagenow);
	});

	//注册单击事件
	$("#pre").bind("click", function() {//上一页
		pagenow = pagenow - 1;
		selects(pagenow);
	});
	$("#next").bind("click", function() {//下一页
		pagenow = pagenow + 1;
		selects(pagenow);
	});
	$("#first").bind("click", function() {//首页
		pagenow = 1;
		selects(pagenow);
	});
	$("#end").bind("click", function() {//尾页
		pagenow = totalpage;
		selects(pagenow);
	});
	
	/**************************************************************************************************/
  });
	  function selects(pagenow){
			var code ="";
			var lastday ="";
			if($("#stage1").attr("checked") == true){
				code = 1;
				lastday = $("#lastday").val();
			}else{
				code = 2;
				if($("#start_date").val() !="" && $("#end_date").val() ==""){
					alert("Please Check The EndDate!");
					$("#endDate").focus();
					return false;
				}
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("Start Date can’t be later than End Date.");
						$("#start_date").focus();
						return false;
					}
				}
			}
			$.ajax({
			type: "post",
			url:"QueryMissingPaymentServlet",
			data: {'ctype':code,'lastday':lastday,'startdate':$("#start_date").val(),'enddate':$("#end_date").val(),'staffcode':$("#staffcode").val(),'clientname':$("#clientname").val(),'principal':$("#principal").val(),'policyno':$("#policyno").val(),"pageNow":pagenow,"econvoy":"hk_econvoy"},
			beforeSend: function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
			success:function(data){
				var totals=0;
				dataRole=eval(data);
				var html="";
				$("tr[id='select']").remove();
				if(dataRole[3]>0){
				total=dataRole[3];
				pagenow =dataRole[2];
			    totalpage=dataRole[1];
				downs=dataRole[0];
				 
				 var numbers=0;
					for(var i=0;i<dataRole[0].length;i++){
						var staffcodes = dataRole[0][i].staffcode;
						if(dataRole[0][i].staffcode2 !=""){
							staffcodes += " / "+dataRole[0][i].staffcode2;
						}
						var reson = dataRole[0][i].reason;
						 if(reson.length>=16){
							//reson=reson.substring(0,16)+"...";
						}
						var datafrom = dataRole[0][i].datafrom;
						 if(datafrom.length>=16){
							//datafrom=datafrom.substring(0,30)+"...";
						}
						var principal ="";
						if(dataRole[0][i].principal =="Zurich"){
							principal = "Zurich International Life Ltd";
						}else{
							principal = dataRole[0][i].principal;
						}
						
						//"</td><td align='center' title='"+dataRole[0][i].datafrom+"'>"+datafrom+
						var checkboxbtn = "<input type='checkbox' id='"+dataRole[0][i].id+"' name='pipei' title='"+dataRole[0][i].id+"' checked='checked' />";
						html+="<tr id='select' title='"+i+"'><td height='23' align='center'>"+checkboxbtn+"</td><td height='23' align='center'>"+staffcodes+"</td><td height='23' align='center'>"+principal+"</td><td align='center'>"
						+dataRole[0][i].clientname +"</td><td align='center'>"+dataRole[0][i].policyno 
						+"</td><td align='center'>"+dataRole[0][i].missingsum+"</td><td align='center'>"+dataRole[0][i].premiumDate+"</td><td align='center' title='"+dataRole[0][i].reason+"'>"+reson+"</td><td align='center'>"
						+dataRole[0][i].receivedDate+"</td><td align='center'>"+dataRole[0][i].datafrom+
						"</td><td align='center'>" +
						"<c:if test='${roleObj.upd==1}'><a href='javascript:void(0);' onclick='add("+i+")'>Update </a></c:if> / <c:if test='${roleObj.delete==1}'><a href='javascript:void(0);' title='"+dataRole[0][i].id+"' name ='delesall' onclick='del("+dataRole[0][i].id+")'>Delete</a></c:if></td></tr>";		 
					}
						$(".page_and_btn").show();
				   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
				   	    $("#missingnums").empty().append(dataRole[4]);
				}
				else{
					html+="<tr id='select'><td colspan='18' align='center'><b>"+" Sorry, there is no matching record."+"</b></td></tr>";
				}
					 $("#jqajax:last").append(html);
				 	 $("tr[id='select']:even").css("background","#COCOCO");
	                 $("tr[id='select']:odd").css("background","#F0F0F0");
	                  page(pagenow,totalpage);
			 }
		 });
	}
      function del(id){
       if(quan ==""){
	      	if(confirm("Sure to Delete?")){
				$.ajax({
					url:"DelMissingPaymentServlet",
					type:"post",
					data:{"missId":id},
					success:function(date){
						alert(date);
						$("#searchs").click();
					},error:function(){
						alert("Network connection is failed, please try later...");
						return false;
					}
				});
			}
		}else{
			$.ajax({
					url:"DelMissingPaymentServlet",
					type:"post",
					data:{"missId":id},
					success:function(date){
					},error:function(){
						alert("Network connection is failed, please try later...");
						return false;
					}
				});
		}
      }
      function checkredio(obj){
		$("#"+obj+"").attr("checked","checked");
		if($("#stage1").attr("checked") == true){
			$("#startdate").val("").attr("disabled","disabled");
			$("#enddate").val("").attr("disabled","disabled");
		}
		if($("#stage2").attr("checked") == true){
			$("#startdate").attr("disabled",false);
			$("#enddate").attr("disabled",false);
		}
	}
	/*********************** ******************/
 	function exit(){
		$("#overs").hide();
	} 
	/*********************** *****************/
	function add(num){
		$("#overs").show();
		$("#updcode").val(dataRole[0][num].staffcode);
		$("#updcode2").val(dataRole[0][num].staffcode2);
		$("#updprincipal").val(dataRole[0][num].principal);
		$("#updclientname").val(dataRole[0][num].clientname);
		$("#updpolicyno").val(dataRole[0][num].policyno);
		$("#updpremiumsum").val(dataRole[0][num].missingsum);
		$("#updreceived").val(dataRole[0][num].receivedDate);
		$("#updpremiumdate").val(dataRole[0][num].premiumDate);
		$("#updremark").val(dataRole[0][num].reason);
		$("#missid").val(dataRole[0][num].id);
		//$("#upddatefrom").val(dataRole[0][num].datafrom);
	} 
	/*************************'datefrom':$("#upddatefrom").val(),******************************/
	 function save(){
		if($("#missid").val() !=""){
			$.ajax({
			type: "post",
			url: "ModifyMissingPaymentServlet",
			data:{'Id':$("#missid").val(),'staffcode':$("#updcode").val(),'staffcode2':$("#updcode2").val(),
			'principal':$("#updprincipal").val(),'clientname':$("#updclientname").val(),
			'policyno':$("#updpolicyno").val(),'missingsum':$("#updpremiumsum").val(),
			'premiumdate':$("#updpremiumdate").val(),'reason':$("#updremark").val(),
			'updreceived':$("#updreceived").val()},
				success:function(date){
					if(date!=null){
						//alert(date);
						
						$("#overs").hide();
						$("#policyno").val($("#updpolicyno").val());
						$("#searchs").click();
					}
				}
			
			});
		}
		else{
		return false;
		}
	} 
     function page(currt,total){
		if(currt<=1){
			$("#first").hide();
			$("#pre").hide();
		}else{
			$("#first").show();
			$("#pre").show();
		}
		if(currt>=total){
			$("#end").hide();
			$("#next").hide();
		}else{
			$("#end").show();
			$("#next").show();
		}
	}
	 function hideupload(){
	 	$("#num").empty();
	 	$("#shangchuan").hide();
	 }
	 function fileupload(){
	 		$("#num").empty();	
	 		$("#num").append("上传中...");
            if($("#filePaths").val()==""){ 
                alert("请选择有效文件"); 
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
                 success: function (data) {
                    if(data!="error"){
                     fileName=data;
                     //alert("上传成功，上传路径为："+fileName);
                     uploadmpr(fileName);
                 }
      	 		    else{
       	 		    alert("上传失败");
       	 		}
                 },error: function (data, status, e){  
                     alert("fail");  
                 }  
            });  
         }
         function uploadmpr(fileName){
         	 $.ajax({
				type: "post",
				url:"UpAllServlet",
				dateType:"html",
				data: {'up':fileName},
				success:function(date){
					if(date=="error"){
						alert("数据上传异常!");
						return false;
					}else if(date=="notfound"){
						alert("上传文件未找到!");
						return false;
					}else if(date!="null" && date!="" && date!=null){
						 
						var dataRole=eval(date);
						if(dataRole[1]>0){
							$("#num").empty();	
							var html="<font>上传"+dataRole[1]+"條記錄</font>";
							$("#num").append(html);
						 }else{
						 	$("#num").empty();	
							var html="<font>上传失败，请仔细检查数据。</font>";
							$("#num").append(html);
						 }
					 
					}else{
						alert("非系统所需文件！");
					}
				}
			 });
         
         }
  </script>
<body>
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Staff Code：</td>
				<td class="tagCont">
					<input type="text" name="staffcode" id="staffcode">
				</td>
				<td class="tagName">Client Name：</td>
				<td class="tagCont">
					<input type="text" name="clientname" id="clientname">
				</td>
			</tr>
			<tr>
				<td class="tagName">Policy Number：</td>
				<td class="tagCont">
					<input type="text" name="policynos" id="policyno" >
				</td>
				<td class="tagName">Principal：</td>
				<td class="tagCont">
					<select name="selectprincipal" id="principal">
               			<option value="">Please Select Principal</option>
              		</select>
				</td>
			</tr>
			<tr>
				<td class="tagName">
					<label class="radio inline">
						<input type="radio" name="code" checked="checked" value="1" id="stage1" />Period：
					</label>	
				</td>
				<td class="tagCont">
					<span class="inline" onClick="checkredio('stage1');">	
	              		<select id="lastday" >
	              			<option value="30" >Last 30 days &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
	              			<option value="60" >Last 60 days  </option>
	              			<option value="90" >Last 90 days  </option>
	              		</select>
              		</span>
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
			<tr>
				<td class="tagName">
					<label class="radio inline">
						<input type="radio" name="code"  align="middle"  value="2" id="stage2" />
						From：
					</label>
				</td>
				<td class="tagCont">
					<span onClick="checkredio('stage2');">
	             		<input name="text" type="text" id="start_date" size="13"  />
           		    	<img src="images/iconDate.gif" name="clear1"  vspace="0" border="0"   align="middle" id="clear1" style="height:22;width:20;"  onClick="return Calendar('start_date')">           		   
           		    </span>
				</td>
				<td class="tagName">To：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it"  />
	             	<img src="images/iconDate.gif" name="clear1" id="clear2"  vspace="0" border="0"  align="middle"  style="height:22;width:20;"  onClick="return Calendar('end_date')">
				</td>
			</tr>
			<tr>
				<td class="tagName" colspan="3" >
					<div id="shangchuan"  >
					    <input type="file" name="filePaths" id="filePaths"/>
						<input type="button" name="fileLoad" id="fileLoad" value=" Upload " onclick="fileupload()"> 
						<input type="button"  id="hideupload"  value=" Hide " onclick="hideupload()"> 
				    	<span id="num"></span>
				    	<span id ="missingnums" style="color:blue;size=14px;align:center;"></span>
			    	</div>
				</td>
			</tr>
			<tr>
				<td class="tagCont" colspan="4" style="text-align: center;">
				<c:if test='${roleObj.search==1}'>
					<a class="btn" id="searchs" name="search">
						<i class="icon-search"></i>
						Search
					</a>
				</c:if>
				<c:if test='${roleObj.export==1}'>
					<a class="btn" id="down" name="downs">
						<i class="icon-download"></i>
						Export
					</a>
				</c:if>
				<c:if test='${roleObj.delete==1}'>
					<a class="btn" id="delall" name="dels">
						<i class="icon-remove"></i>
						Delete All
					</a>
				</c:if>
				<c:if test='${roleObj.upd==1}'>
					<a class="btn" id="upload" name="upload">
						<i class="icon-upload"></i>
						Upload
					</a>
				</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title"> 
			<tr>
				<th class="width_5">
					<input type="checkbox" id="btn4" name="fanxuan"   checked="checked" />
				</th>
				<th class="width_10">Staff Code</th>
				<th class="width_10">Principal</th>
				<th class="width_10">Policy Owner Name</th>
				<th class="width_10">Policy Number</th>
				<th class="width_10">Modal Premium</th>
				<th class="width_10">Premium Due Date</th>
				<th class="width_10">Principal Remark</th>
				<th class="width_7">Last Update Date</th>
				<th class="width_7">Next Collection Date</th>
				<th class="width_10">Details</th>
			</tr>
			</thead>
		</table>
		<div align="center" class="page_and_btn" style="display: none;" >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">First Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a> Total
						<SPAN style="color: red;" id="total">
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript" src="plug/lhgdialog/lhgdialog.min.js"></script>
<script type="text/javascript">
function add(id)
{
	var data = dataRole[0][id];
	var url = "<%=basePath%>"+"namecard/MissingPayment_All_modify.jsp";
	editlhg(800,320,url,data);
}
</script>
</body>
</html>
