<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>Query Pick Up System Record</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<script type="text/javascript">
 	 /***********************************************************delete staff message************************/
   		var downs=null;
 	 	var pagenow =1;
		var totalpage=1; 
		var total=0;
    	var basepath = "<%=basePath%>";
    /**
	 * 只读文本框禁用退格键
	 * 
	 * */
	function BackSpace(e){
		if(window.event.keyCode==8)//屏蔽退格键
	 {
	    var type=window.event.srcElement.type;//获取触发事件的对象类型
	  //var tagName=window.event.srcElement.tagName;
	  var reflag=window.event.srcElement.readOnly;//获取触发事件的对象是否只读
	  var disflag=window.event.srcElement.disabled;//获取触发事件的对象是否可用
		  if(type=="text"||type=="textarea")//触发该事件的对象是文本框或者文本域
		  {
			   if(reflag||disflag)//只读或者不可用
			   {
			    //window.event.stopPropagation();
			    window.event.returnValue=false;//阻止浏览器默认动作的执行
			   }
		  }
		  else{ 
		   window.event.returnValue=false;//阻止浏览器默认动作的执行
		  }
	 }
	}


/*窗体加载事件 */
		
 /*****************************************************WindowForm Load *************************************/
 $(function(){
	 document.onkeydown=BackSpace;
	$("#information [id!='back']").attr("disabled","disabled"); 
	 window.onresize=function(){
	 }
/**********************************窗體加載************************************************/
	$("#start_date").val(CurentTime());	
	$("#end_date").val(CurentTime());	
 
 	//--->新增
 	$('#add').bind('click',function(){
	 	addList();
	});
 	
 	//--->批量Ready
 	$('#batchReady').bind('click',function(){
	 	batchReady();
	});

 	//-->导出
 	$("#export").click(function(){
		if(downs!=null){
			window.location.href=basepath+"pickuprecord/PickUpRecordServlet?staffcode="+$("#staffcode").val()+"&clientname="+$("#clientname").val()+"&startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&method=down"+"&status="+$("#status").val();
		}else{
			alert("请先查询数据，再做导出相关操作！");
		}
	});
/****************************************************Search click***********************************/
	$("#searchs").click(function(){ 	
		selects(1);	
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
		$("[type='checkbox']").click(function(){
		if($(this).attr("checked"))
			$(this).val("Y");
		else
			$(this).val("N");
	});

	/***********************************************************Search Click end************************/
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
	
	//所有checkbox单击事件
 	$("#checkAll").click(function(){
 		if(this.checked){
 			$("#jqajax td input[type='checkbox']").attr("checked",true);
 		}else{
 			$("#jqajax td input[type='checkbox']").attr("checked",false);
 		}
	});
	
});
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
  	/**
	 * 删除Namecard request
	 * 2015-08-07 11:45:31
	 * */
	function del(code){
		if(confirm("Sure to Delete?")){
			$.ajax({
				//url:"DeleteNameCardServlet",
				url:"pickuprecord/PickUpRecordServlet",
				type:"post",
				data:{"method":"delList","refno":code},
				success:function(date){
					var result=$.parseJSON(date);
					if(result.state=="success"){
						$("#searchs").click();
				  	}
					alert(result.msg);
				},error:function(){
					alert("Network connection is failed, please try later...");
					return false;
				}
			});
		}
	}
  	
  	function modify(num){
		var data = downs[num];
		$('#body_view').hide();
		$('#details').show();
		if(data.status=="Submitted"){
			$("#saveReady").attr("disabled",false);
		}else{
			$("#saveReady").attr("disabled","disabled");
		}
		if(data.status=="Return to Sender"){
			$("#m_reject").attr("disabled","disabled");
		}else{
			$("#m_reject").attr("disabled",false);
		}
	  	/*************************************************/
		$("#refno").val(data.refno);
		$("#m_staffcode").val(data.staffcode);
		$("#m_clientname").val(data.clientName);
		//$("#m_location").val(data.location);
		var a=data.location.toUpperCase();
		$("#m_location").find("option[value='"+a+"']").attr("selected",true);
		$("#m_sender").val(data.sender);
		$("#m_documentId").val(data.documentId);
		$("#m_documentType").val(data.documentType);
		$("#m_scandate").val(data.scanDate);
		$("#m_remark").val(data.result);
  	}
  	
  	
  	function save_Ready(type,num){
  		if(type == 1){
  			var data = downs[num];
			//alert(data.location);
			if(data.status!="Submitted"){
				alert('当前状态还不是Submitted, 不予操作');
				return false;
			}
			$("#refno").val(data.refno);
			$("#m_staffcode").val(data.staffcode);
			$("#m_clientname").val(data.clientName);
			var a=data.location.toUpperCase();
			$("#m_location").find("option[value='"+a+"']").attr("selected",true);
			/**$("#m_location option").each(function(){
				if(this.value.toLowerCase()==data.location.toLowerCase()){
					$(this).attr("selected",true);
				}
			});*/
			$("#m_sender").val(data.sender);
			$("#m_documentId").val(data.documentId);
			$("#m_documentType").val(data.documentType);
			$("#m_scandate").val(data.scanDate);
			$("#m_remark").val(data.result);
  		}
  		
  		if(confirm("是否确认提交?")){
	  		$.ajax({
				url:"pickuprecord/PickUpRecordServlet",
				type:"post",
				data:$("#myform").serialize(),
				beforeSend: function(){
					parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				success:function(date){
				  var result=$.parseJSON(date);
				  if(result.state=="success"){
						reback();
						$("#searchs").click();
				   }
				   alert(result.msg);
				},error:function(){
					alert("网络连接失败，请稍后重试...");
					return false;
				}
			});
  		}
  	}
  	
    function fileupload(){
             if($("#filePaths").val()==""){ 
                 alert("请选择有效文件"); 
                 return false;  
             } 
             $.ajaxFileUpload({  
                  url:'UpLoadFileServlet', 
                  secureuri:false,  
                  fileElementId:'filePaths',  
                  dataType: 'text/html',
                  success: function (data) {
                     if(data!="error"){
	                     fileName=data;
	                     uploadConsultant(fileName);
	                 }else{
	       	 		    alert("上传失败");
	       	 		 }
                  },error: function (data, status, e){  
                      alert("fail");  
                  }  
              });  
           }
    
 	function uploadConsultant(fileName){
           	if(fileName==""){
				alert("請選擇需要上傳的文件！");
				return;
			}
			 $.ajax({
				type: "post",
				url:"UpExcelServlet",
				dateType:"html",
				complete: function(){
				 	$.dialog.list['menu_upload'].close();
				},
				data: {'uploadfile':fileName},
				success:function(date){
					if(date != "null"){
						var dataRole=eval(date);
						alert(dataRole[4]+" 共耗时:"+dataRole[3] +"分鐘");	
						$("#shangchuan").attr("disabled",true);
					}else{
						alert("非系统所需文件！");
					}
				 },error:function(){
					 alert("网络连接失败，请稍后重试...");
					 return false;
				 } 
				});
           } 
 	
 	//-->返回键
 	function reback(){
		$('#details').hide();
		$('#body_view').show();
	}
 	
 	function addList(){
		var url = basepath + "page/pickuprecord/queryPickUpRecord_info.jsp";
		addlhg(900,400,url);
 	}
 	function selects(pagenow){
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("Start Date can’t be later than End Date.");
						$("#start_date").focus();
						return false;
					}
				}
				$.ajax({
				type: "post",
				url:"pickuprecord/PickUpRecordServlet",
				data: {"method":"select",'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),
					"staffcode":$("#staffcode").val(),"clientname":$("#clientname").val(),"status":$("#status").val(),"pagenow":pagenow},
				beforeSend: function(){
						parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				success:function(data){
					//alert(data);
					var totals=0;
					var dataRole=eval(data);
					var html="";
					$("tr[id='select']").remove();
					downs=null;
					if(dataRole[3]>0){
						total=dataRole[3];
						pagenow =dataRole[2];
					    totalpage=dataRole[1];
						downs=dataRole[0];
						for(var i=0;i<dataRole[0].length;i++){
							html+="<tr id='select' title='"+i+"'>" ;
							if(dataRole[0][i].status=="Submitted"){
								html+="<td><input id='"+dataRole[0][i].refno+"' c_status='"+dataRole[0][i].status+"' c_code='"+dataRole[0][i].staffcode+"' c_name='"+dataRole[0][i].clientName+"' align='center' class='checkAll"+i+"' type='checkbox' style='width:15px;' /></td>" ;
							}else{
								html+="<td>--</td>" ;
							}
							html+="<td align='center'>"+dataRole[0][i].staffcode+"</td><td align='center'>"
							+dataRole[0][i].clientName +"</td><td align='center'>"+dataRole[0][i].location +"</td>" +
							"<td align='center'>"+dataRole[0][i].sender +"</td><td align='center'>"+dataRole[0][i].documentId+"</td><td align='center'>"
							+dataRole[0][i].documentType +"</td><td align='center'>"+dataRole[0][i].scanDate+"</td>" +
							"<td align='center'>"+dataRole[0][i].status +"</td><td align='center'>"+dataRole[0][i].code +"</td>" +
							"<td align='center'>"+dataRole[0][i].upd_date +"</td><td align='center'>"+dataRole[0][i].result +"</td>" +
							"<td align='center'>";
							if(dataRole[0][i].status == 'Submitted'){
								html+="<c:if test='${roleObj.audit==1}'><a href='javascript:void(0);' onclick='save_Ready(1,"+i+")'>Ready</a></c:if>";
							}
							html+="&nbsp;<c:if test='${roleObj.search==1}'><a href='javascript:void(0);' onclick='modify("+i+")'>"+"详细"+"</a></c:if>&nbsp;";
							
							if(dataRole[0][i].status != 'Received' && dataRole[0][i].status != 'Return to Sender' ){
								html += "<c:if test='${roleObj.delete==1}'><a onclick=\"del('"+dataRole[0][i].refno+"')\">删除</a></c:if>";
							}
							html+="</td></tr>";
						}
						
			 				$(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}else{
						html+="<tr id='select'><td colspan='13' align='center'>"+" Sorry, there is no matching record."+"</td></tr>";
					 	$(".page_and_btn").hide();
					}
						 $("#jqajax:last").append(html);
					 	 $("tr[id='select']:even").css("background","#COCOCO");
		                 $("tr[id='select']:odd").css("background","#F0F0F0");
		                 	 page(pagenow,totalpage);
				 }
			 });
					
		}
 	$('#upload_btn').live('click',function(){
 		var data = 'uploadConsultant';
 		var url = basepath + "publicUpload.jsp";
		uploadlhg(440,300,url,data);
 	});
 	
 	
 		/**
	 * 删除Namecard request
	 * 2015-08-07 11:45:31
	 * */
	function batchReady(){
 		var n=0;
		var cid="";
		var staffCodes="";
		var clientNames="";
		$("#jqajax td input:checkbox").each(function(i){
			if(this.checked ){
				if($(this).attr("c_status")=="Submitted"){
					if(cid==""){
						cid+=this.id;
						staffCodes+=$(this).attr("c_code");
						if($(this).attr("c_name")==""){
							clientNames+=" ";
						}else{
							clientNames+=$(this).attr("c_name");
						}
					}else{
						cid+="~,~"+this.id;
						staffCodes+="~,~"+$(this).attr("c_code");
						if($(this).attr("c_name")==""){
							clientNames+="~,~"+" ";
						}else{
							clientNames+="~,~"+$(this).attr("c_name");
						}
					}
					n++;
				}else{
				}
			}
		});
 		if(n<=0){
			alert("not choose item!");
			return false;
		}else{
			if(confirm("Sure to Ready All?")){
				$.ajax({
					url:"pickuprecord/PickUpRecordServlet",
					type:"post",
					beforeSend: function(){
						parent.showLoad();
					},
					complete: function(){
						parent.closeLoad();
					},
					data:{"method":"batchReady","refnos":cid,"staffCodes":staffCodes,"clientNames":clientNames},
					success:function(date){
						var result=$.parseJSON(date);
						if(result.state=="success"){
							$("#searchs").click();
					  	}
						alert(result.msg);
					},error:function(){
						alert("Network connection is failed, please try later...");
						return false;
					}
				});
			}
		}
		
	}
 		
 	function reject_back(){
 		if(confirm("是否确退回给申请人?")){
	  		$.ajax({
				url:"pickuprecord/PickUpRecordServlet",
				type:"post",
				data:{"method":"reject_back","refno":$("#refno").val()},
				dataType:"json",
				beforeSend: function(){
					parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				success:function(result){
				  //var result=$.parseJSON(date);
				  if(result.state=="success"){
						reback();
						$("#searchs").click();
				   }
				   alert(result.msg);
				},error:function(){
					alert("网络连接失败，请稍后重试...");
					return false;
				}
			});
  		}
  	}
 		
 		
</script>

</head>
<body>
	<div class="cont-info" id="body_view">
		<div class="info-search">
			<table>
				<tr>
					<td class="tagName">Start Date：</td>
					<td class="tagCont"><input id="start_date" type="text"
						readonly="readonly" onClick="Calendar('start_date')" /> <i
						class="icon-trash icon-large i-trash" id="clear1" align="middle"
						onclick="javascript:$('#start_date').val('');"></i></td>
					<td class="tagName">End Date：</td>
					<td class="tagCont"><input id="end_date" type="text" name="it"
						readonly="readonly" onClick="Calendar('end_date')" /> <i
						class="icon-trash icon-large i-trash" id="clear2" align="middle"
						onclick="javascript:$('#end_date').val('');"></i></td>
				</tr>
				<tr>
					<td class="tagName">Staff Code：</td>
					<td class="tagCont"><input type="text" name="staffcode" id="staffcode" /></td>
					<td class="tagName">Client Name：</td>
					<td class="tagCont"><input type="text" name="clientname" id="clientname" /></td>
				</tr>
				<tr>
					<td class="tagName">Status：</td>
					<td class="tagCont">
						<select id="status" name="status">
							<option value="">Please Select Status</option>
							<option value="Submitted">Submitted</option>
							<option value="Ready">Ready</option>
							<option value="Received">Received</option>
							<option value="Deleted">Deleted</option>
							<option value="Return to Sender">Return to Sender</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tagName"></td>
					<td class="tagCont" style="text-align: center;" colspan="3">
					<c:if test='${roleObj.upd==1}'>
						<a class="btn" id="upload_btn">
							<i class="icon-upload-alt"></i>
							Upload File
						</a>
					</c:if>
					<c:if test='${roleObj.search==1}'>
						<a class="btn" id="searchs" name="search"> <i class="icon-search"></i>Search </a>
					</c:if>
					<c:if test='${roleObj.add==1}'>
						<a class="btn" id="add" name="add"><i class="icon-add"></i>Add</a>
					</c:if>
					<c:if test='${roleObj.audit==1}'>
						<a class="btn" id="batchReady" name="batchReady"><i class="icon-add"></i>Batch Ready</a>
					</c:if>
					<c:if test='${roleObj.export==1}'>
						<a class="btn" id="export" name="downs"><i class="icon-add"></i>Export</a>
					</c:if>
					</td>
				</tr>
			</table>
		</div>
		<div class="info-table">
			<table id="jqajax">
				<thead id="title">
					<tr>
						<th width="2%"> <input id="checkAll" type="checkbox" style="width:15px;"/></th>
						<th class="width_5">StaffCode</th>
						<th class="width_13">Client Name</th>
						<th class="width_7">Location</th>
						<th class="width_5">Sender</th>
						<th class="width_5">DocumentId</th>
						<th class="width_10">Document Type</th>
						<th class="width_10">Scan Date</th>
						<th class="width_10">Status</th>
						<th class="width_10">Received By</th>
						<th class="width_10">Receive Date</th>
						<th class="width_5">Result</th>
						<th class="width_10">Operation</th>
					</tr>
				</thead>
			</table>
			<div align="center" class="page_and_btn" style="display: none;">
				<table class="main_table" width="100%" border="0" cellpadding="0"
					cellspacing="0" id="select_table">
					<tr class="main_head">
						<td colspan="6" align="center"><a id="first"
							href="javascript:void(0);">first Page</a> <a id="pre"
							href="javascript:void(0);">Previous Page</a> Total <SPAN
							style="color: red;" id="total"> </SPAN>Page <a id="next"
							href="javascript:void(0);">Next Page</a> <a id="end"
							href="javascript:void(0);">Last Page</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="cont-info" id="details" style="display: none;">
  	<div class="info-title">
  		<div class="title-info">
  			<form action="" id="myform" method="post">
  			<table>
  				<tr>
					<td class="tagName">Staff Code:</td>
					<td class="tagCont">
						<input id="m_staffcode"  readonly="readonly"  name="m_staffcode" type="text" size="35" class="txt" />
					</td>
					<td class="tagName">Client Name:</td>
					<td class="tagCont">
						<input id="m_clientname" name="m_clientname" type="text" size="35" class="txt" />
					</td>
				</tr>
				<tr>
					<td class="tagName">Location:</td>
					<td class="tagCont">
						<select name="m_location" id="m_location">
							<option value="">Please Select Location</option>
			      			<option value="CP3">CP3</option>
			      			<option value="@CONVOY">@CONVOY</option>
			      		</select>
					</td>
					<td class="tagName">Sender: </td>
					<td class="tagCont">
						<input type="text" name="m_sender" id="m_sender" />
						<%--<select name="m_sender" id="m_sender">
							<option value="">Please Select Sender</option>
				        	<option value="BPD">BPD</option>
				        </select>--%>
					</td>
				</tr>
				<tr>
					<td class="tagName">Document ID:</td>
					<td class="tagCont">
						<input type="text" name="m_documentId" id="m_documentId" />
					</td>
					<td class="tagName">Document Type:</td>
					<td class="tagCont">
						<input type="text" name="m_documentType" id="m_documentType" />
						<%--<select name="m_documentType" id="m_documentType">
				        	<option value="">Please Select Type</option>
				        	<option value="NB">NB</option>
				        	<option value="CS">CS</option>
				        </select>--%>
					</td>
					
				</tr>
				<tr>
					<td class="tagName">Scan Date:</td>
					<td class="tagCont"><input id="m_scandate" name="m_scandate" type="text"
						readonly="readonly" onClick="Calendar('m_scandate')" /> <i
						class="icon-trash icon-large i-trash" id="clear1" align="middle"
						onclick="javascript:$('#m_scandate').val('');"></i>
					</td>
				</tr>
				<tr>
					<td class="tagName">RemarkCons:</td>
					<td class="tagCont" colspan="3">
						<textarea name="m_remark" id="m_remark" cols="78" rows="4" style="width: 80%;"></textarea>
					</td>
				</tr>
				<tr style="display: ;">
					<td class="tagBtn" colspan="4">
					<c:if test='${roleObj.audit==1}'>
						<input id="saveReady" type="button" class="btn" name="Submit" onclick="save_Ready(0)" value="Ready"/>
					</c:if>
						<input id="m_reject" type="button" class="btn" name="m_reject" onclick="reject_back()" value="Return to Sender"/>
						<input id="back" type="button" class="btn" value="Back" onclick="reback();"/>
						<input id="refno" class="btn" name="refno" type="hidden"/>
						<input  class="btn" name="method" value="save_Ready" type="hidden"/>
					</td>
				</tr>
  			</table>
  			</form>
  		</div>
  	</div>
  </div>
</body>
</html>