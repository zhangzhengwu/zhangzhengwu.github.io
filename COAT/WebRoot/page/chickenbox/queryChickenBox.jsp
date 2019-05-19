<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>Chicken Box Query</title>
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
    
 /*****************************************************WindowForm Load *************************************/
 $(function(){
	 document.onkeydown=BackSpace;
	$("#information [id!='back']").attr("disabled","disabled"); 
	 window.onresize=function(){
	 }
/**********************************窗體加載************************************************/
	$("#start_date").val(CurentTime());	
	$("#end_date").val(CurentTime());	
 
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
	/***********************************************************Search Click end************************/
	function CurentTime(){ 
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
	
	//-->导出
 	$("#export").click(function(){
		if(downs!=null){
			window.location.href=basepath+"ChickenBoxServlet?staffcode="+$("#staffcode").val()+"&fullname="+
                $("#fullname").val()+"&othersname="+$("#othersname").val()+"&d_Recruite="+$("#d_Recruite").val()+"&method=down";
		}else{
			alert("请先查询数据，再做导出相关操作！");
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
  	
function selects(pagenow){
	/**
	 * 四个查询条件不能同时为空
	 * */
	/**if($("#staffcode").val()==""&&$("#fullname").val()==""&&$("#othersname").val()==""&&$("#d_Recruite").val()==""){
		alert("请输入一个查询条件");
		return false;
	}**/
	$.ajax({
		type: "post",
		url:"ChickenBoxServlet",
		data: {"method":"select","staffcode":$("#staffcode").val(),"fullname":$("#fullname").val(),
			"othersname":$("#othersname").val(),"d_Recruite":$("#d_Recruite").val(),"pagenow":pagenow},
		beforeSend: function(){
				parent.showLoad();
		},
		complete: function(){
			parent.closeLoad();
		},
		success:function(data){
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
					html+="<tr id='select' title='"+i+"'><td align='center'>"+((i+1)+(dataRole[2]-1)*dataRole[4])+"</td>" ;
					html+="<td align='center'>"+dataRole[0][i].staffcode+"</td><td align='center'>"
					+dataRole[0][i].othername +"</td><td align='center'>"+dataRole[0][i].fullname1 +"</td>" +
					"<td align='center'>"+dataRole[0][i].fullname2 +"</td><td align='center'>"
					+dataRole[0][i].location +"</td><td align='center'>"+dataRole[0][i].boxNo+"</td>" +
					"<td align='center'>"+dataRole[0][i].extensionno +"</td><td align='center'>"+dataRole[0][i].RecruiterId +"</td>" +
					"<td align='center'></tr>";
				}
	 				$(".page_and_btn").show();
			   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
			   	    
			   	   // saveQueryRecord(data);
			}else{
				html+="<tr id='select'><td colspan='12' align='center'>"+" Sorry, there is no matching record."+"</td></tr>";
			 	$(".page_and_btn").hide();
			}
				 $("#jqajax:last").append(html);
			 	 $("tr[id='select']:even").css("background","#COCOCO");
                 $("tr[id='select']:odd").css("background","#F0F0F0");
                 page(pagenow,totalpage);
		 }
 	});
}

/**
 * 保存查询到的数据
 * @param {Object} data
 */
function saveQueryRecord(dataRole){
	$.ajax({
		type: "post",
		url:"ChickenBoxServlet",
		data: {"method":"saveQueryRecord","dataRole":dataRole,"staffcode":$("#staffcode").val(),"fullname":$("#fullname").val(),
			"othersname":$("#othersname").val(),"d_Recruite":$("#d_Recruite").val()},
		success:function(data){
				
		}
 	});
	
}


</script>

</head>
<body>
	<div class="cont-info" id="body_view">
		<div class="info-search">
			<table>
				<tr>
					<td class="tagName">Staff Code：</td>
					<td class="tagCont"><input type="text" name="staffcode" id="staffcode" /></td>
					<td class="tagName">Full Name：</td>
					<td class="tagCont"><input type="text" name="fullname" id="fullname" /></td>
				</tr>
				<tr>
					<td class="tagName">Others Name：</td>
					<td class="tagCont"><input type="text" name="othersname" id="othersname" /></td>
					<td class="tagName">Department/Recruite：</td>
					<td class="tagCont"><input type="text" name="d_Recruite" id="d_Recruite" /></td>
				</tr>
				<tr>
					<td class="tagName" style="padding-left: 10%;" >
						
					</td>
					<td class="tagCont" style="text-align: center;" colspan="3">
					<c:if test='${roleObj.search==1}'>
						<a class="btn" id="searchs" name="search"> <i class="icon-search"></i>Search </a>
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
						<th class="width_5">Num</th>
						<th class="width_5">Code</th>
						<th class="width_15">Other Name</th>
						<th class="width_15">Full Name1</th>
						<th class="width_15">Full Name2</th>
						<th class="width_15">Location</th>
						<th class="width_10">Bor No.</th>
						<th class="width_10">Extension</th>
						<th class="width_10">Recruiter</th>
						<%--<th class="width_10">Operation</th>--%>
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
					</td>
				</tr>
				<tr>
					<td class="tagName">Document Type:</td>
					<td class="tagCont">
						<input type="text" name="m_documentType" id="m_documentType" />
					</td>
					<td class="tagName">Scan Date:</td>
					<td class="tagCont"><input id="m_scandate" name="m_scandate" type="text"
						readonly="readonly" onClick="Calendar('m_scandate')" /> <i
						class="icon-trash icon-large i-trash" id="clear1" align="middle"
						onclick="javascript:$('#m_scandate').val('');"></i></td>
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