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
/**********************************窗體加載************************************************/
	//$("#start_date").val(CurentTime());	
	//$("#end_date").val(CurentTime());	
 
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
			if($("#start_date").val()!="" && $("#end_date").val()!=""){
				if($("#start_date").val()>$("#end_date").val()){
					alert("Start Date can’t be later than End Date.");
					$("#start_date").focus();
					return false;
				}
			}
			$.ajax({
			type: "post",
			url:"SeattingPlanServlet",
			data: {"method":"select",'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),
				"dataFile":$("#dataFile").val(),"imgFile":$("#imgFile").val(),"pagenow":pagenow},
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
						html+="<tr id='select'><td align='center'>"+((i+1)+(dataRole[2]-1)*dataRole[4])+"</td><td align='center'>"+dataRole[0][i].imgFile+"</td><td align='center'>"
						+dataRole[0][i].imgSheet +"</td><td align='center'>"+dataRole[0][i].dataFile +"</td>" +
						"<td align='center'>"+dataRole[0][i].dataSheet +"</td><td align='center'>"
						+dataRole[0][i].outFile +"</td></tr>";
					}
	 				$(".page_and_btn").show();
			   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
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
 	
 	
 	
</script>

</head>
<body>
	<div class="cont-info" id="body_view">
		<div class="info-search">
			<table>
				<tr>
					<td class="tagName">Start Date:</td>
					<td class="tagCont"><input id="start_date" type="text"
						readonly="readonly" onClick="Calendar('start_date')" /> <i
						class="icon-trash icon-large i-trash" id="clear1" align="middle"
						onclick="javascript:$('#start_date').val('');"></i></td>
					<td class="tagName">End Date:</td>
					<td class="tagCont"><input id="end_date" type="text" name="it"
						readonly="readonly" onClick="Calendar('end_date')" /> <i
						class="icon-trash icon-large i-trash" id="clear2" align="middle"
						onclick="javascript:$('#end_date').val('');"></i></td>
				</tr>
				<tr>
					<td class="tagName">Image Path:</td>
					<td class="tagCont"><input type="text" name="imgFile" id="imgFile" /></td>
					<td class="tagName">Data Path:</td>
					<td class="tagCont"><input type="text" name="dataFile" id="dataFile" /></td>
				</tr>
				<tr>
					<td class="tagCont" style="padding-left: 10%;" colspan="2">
						
					</td>
					<td class="tagName" style="text-align: left;" colspan="2">
					<c:if test='${roleObj.search==1}'>
						<a class="btn" id="searchs" name="search"> <i class="icon-search"></i>Search </a>
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
						<th class="width_30">Image Path</th>
						<th class="width_10">Image Sheet Name</th>
						<th class="width_30">Data Path</th>
						<th class="width_8">Data Sheet Name</th>
						<th class="width_20">Out Path</th>
						<%--<th class="width_5">Craetor</th>
						<th class="width_10">Create Date</th>
						<th class="width_10">Reamrk</th>--%>
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
</body>
</html>