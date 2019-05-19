<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ScanningRecord</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="css/jquery-pager-1.0.js"></script>
	<link rel="stylesheet" type="text/css" href="css/pager.css">
	<script src="css/date.js" language="JavaScript"></script>
	<script type="text/javascript" src="../ConvoyUtil/UI/js/ReturnMailReason.js?version=<%=new Date() %>"></script>
	<link rel="stylesheet" type="text/css" href="css/style.css">
<style type="text/css">
*{font-family: Arial Narrow;font-size: 14px;}
body{vertical-align: middle;background-image: url("css/officeAdmin_bg.gif");width:100%;margin:0px;padding:0px;}
 .daw {
    width: 100%;
    height: auto;
  /**  margin: 0px auto;
    margin-top: -5px;
    margin-left:-8px;**/
    overflow: hidden;
   
}
.daw-z {
    width: 100%;
    height: auto;
      /** margin: 0px auto;
 background: url(UI/images/news-title-bg.gif) repeat-y;
    padding-top: 0px;**/
    overflow: hidden;
}
.editTable {
    margin-top: 0px;
    margin-bottom: 5px;

    font-size: 14px;
    border: #83accf 1px solid;
   
}
.editTable span.bigtitle{
    font-weight: bold;
    height: 25px;
}
.editTable td.title{
	height:25px;
   vertical-align: middle;
}

.editTable tr.title{
	height:25px;
   vertical-align: middle;
}
.editTable tr.title td{
text-align:center;
font-weight:bold;
font-size:14px;

}
.editTable tr{
line-height: 15px;
}

.editTable td.title{
text-align:right;
}
#stable tr{
line-height: 20px;
}

#stable tr.field{
text-align:left;
}
#stable .title td{
text-align:center;
border:1px solid #000;
}
img{
height: 12px;
width: 12px;
cursor: pointer;
}
  .page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}

</style>


<script type="text/javascript">
var pageindex=1;
     var pagenow =1;
  var totalpage=1;
  var total=0;
var dataRole=null;
var ReasonHtml="<option value='' >Please Choose</option>";

var editRight=false;
$(function(){
if("${convoy_username}"=="LK0102" || "${convoy_username}"=="AY6459" || "${convoy_username}"=="JL0131"||"${convoy_username}"=="CK0101"||"${convoy_username}"=="ML0476"||"${convoy_username}"=="MA0321"||"${convoy_username}"=="FK0076")
	editRight=true;
else{
	editRight=false;
	$("#sendMail,#td_emailStatus").remove();
	}
	$("#search").click(function(){
		select_pager(1);
	
	});
	
		$.ajax({
		url:"http://"+window.location.host+"/ConvoyUtil/ReturnMailReasonServlet",
		type:"post",
		async:false,
		data:{"method":"findAll"},
		success:function(date){
			dataRole=eval(date);
				for(var i=0;i<dataRole.length;i++){
					ReasonHtml+="<option>"+dataRole[i].reason+"</option>";
				}
					ReasonHtml+="</select>";
		},error:function(){
			alert("Return Mail Reason initialization exception. Please refresh retry...");
			return false;
			}
		});
	
	
	$("#report").click(function(){
		location.href="http://"+window.location.host+"/ConvoyUtil/ExpReturnMailScanningRecordServlet?staffcode="+$("#staffcode").val()+"&staffname=&startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&isupload=Y"+"&clientId="+$("#clientId").val();
	});
	
			var econvoy=new Array();
			econvoy="${Econvoy}".split("-");
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
			//$("#staffcode").val(econvoy[0]);
		
	          
              		//注册单击事件
	$("#pre").bind("click", function() {//上一页
				pagenow = pagenow - 1;
				select_pager(pagenow);
			});
	$("#next").bind("click", function() {//下一页
				pagenow = pagenow + 1;
				select_pager(pagenow);
			});
	$("#first").bind("click", function() {//首页
				pagenow = 1;
				select_pager(pagenow);
			});
	$("#end").bind("click", function() {//尾页
				pagenow = totalpage;
				select_pager(pagenow);
			});
	
	
	$("#sendMail").click(function(){
		
		if(confirm("Sure to Send Mail?")){
				$.ajax({
					url:"http://"+window.location.host+"/ConvoyUtil/BatchSendMailServlet",
					type:"post",
					async:false,
					data:{"scanningDate":null},
					success:function(date){
						if(date==""){
							alert("There is no need to send the record");
						}else{
							alert(date);
						}
						$("#sendMail").attr("disabled",true);
					},error:function(){
						alert("Connection Error!");
						return false;
					}
				});
			}
	});
	
	
	
	
	
	
	
	
});

function select_pager(pagenow){
	if($("#startDate").val()!="" && $("#endDate").val()!=""){
			if($("#startDate").val()>$("#endDate").val()){
				alert("Start Date can’t be later than End Date.");
				$("#startDate").focus();
				return false;
			}
		}
		
		$.ajax({
			url:"http://"+window.location.host+"/ConvoyUtil/ReturnMailScanningRecordServlet",
			type:"post",
			data:{"staffcode":$("#staffcode").val(),"staffname":"","startDate":$("#startDate").val(),"endDate":$("#endDate").val(),"clientId":$("#clientId").val(),"isupload":"Y","pagenow":pagenow},
			beforeSend: function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
			success:function(date){
				var html="";
				$("#stable tr.field").remove();
				if(date=="error"){
					
				}else if(date==null){
					
				}else{
					var dataRole=eval(date);
					$(".page_and_btn,#remark_table").hide();
					if(dataRole[2]>0){
							var edit=null;
							var emailstatus="";
						pagenow =dataRole[1];
				    	totalpage=dataRole[3];
						for(var i=0;i<dataRole[0].length;i++){
							if(editRight){
								edit="<select id='"+dataRole[0][i].id+"' onchange='changeValue(this,"+dataRole[0][i].id+")' class='searchData'>"+(ReasonHtml.replace(">"+dataRole[0][i].reason+"<"," selected='selected' >"+dataRole[0][i].reason+"<"));
								if(dataRole[0][i].url=="Y")
									emailstatus="<td align='center'>Has been sent</td>";
								else if(dataRole[0][i].url=="H")
									emailstatus="<td align='center'>No Email Address</td>";
								else
									emailstatus="<td align='center'></td>";
							}else{
								edit=dataRole[0][i].reason;
								emailstatus="";
								}
							
							
							
							html+="<tr class='field'>" +
							"<td>"+((pagenow-1)*15+i+1)+"</td>" +
							"<td>"+dataRole[0][i].clientID+"</td>" +
							"<td>"+dataRole[0][i].documentType+"</td>" +
							"<td>"+("20"+dataRole[0][i].documentDate.substring(0,2)+"-"+dataRole[0][i].documentDate.substring(2,4))+"</td>" +
							"<td>"+dataRole[0][i].documentType2+"</td>" +
							"<td>"+dataRole[0][i].unknown+"</td>" +
							"<td  align='center'>"+dataRole[0][i].staffcode+"</td>" +
							//"<td>"+dataRole[0][i].staffname+"</td>" +
							//"<td>"+dataRole[0][i].chickenboxno+"</td>" +
							//"<td>"+dataRole[0][i].location+"</td>" +
							//"<td>"+dataRole[0][i].scanningName+"</td>" +
							"<td align='center'>"+dataRole[0][i].scanningDate+"</td>"+
							"<td>"+((getMonthBeforeDay(new Date())==dataRole[0][i].scanningDate||dataRole[0][i].reason=="")?edit:(dataRole[0][i].reason))+"</td>" +
							emailstatus +
							"</tr>";
						}
						$(".page_and_btn,#remark_table").show();
					}else{
						html+="<tr class='field'><td colspan='7' style='color:blue;size=14px' align='center'><b>"+" Sorry, there is no matching record."+"</b></td></tr>";
					}
				}
				//InitPager(dataRole[2],dataRole[1]);
				page(dataRole[1],dataRole[3]);
				  $("#total").empty().append(dataRole[1]+"/"+dataRole[3]);
				$("#stable").append(html);
				$("#stable tr.field:odd").css("backgroundColor","#F0F0F0");
				$("#stable tr.field:even").css("backgroundColor","#COCOCO");
				$(".searchData").each(function(n){
					if($(this).val()==""){
						$(this).parent().parent().css("background-color","#1E90FF");
					}
				});
				parent.closeLoad();
			},error:function(){
				alert("Network Connection Error!");
			}
		});
}

function changeValue(obj,id){
	$.ajax({
		url:"http://"+window.location.host+"/ConvoyUtil/OperationServlet",
		type:"post",
		async:false,
		data:{"method":"update","id":id,"reason":$(obj).val()},
		success:function(date){
			if(date=="error")
				alert("Save Error!");
			else{
				if(window.frames.length != parent.frames.length)
					$("#bcode",parent.document).select();
			}
				
		},error:function(){
			alert("Connection Error!");
		}
	});
}



   function InitPager(RecordCount, PageIndex) {
            $("#test").setPager({ RecordCount: RecordCount, PageIndex: PageIndex, buttonClick: PageClick });
            //$("#result").html("您点击的是第" + PageIndex + "页");
            pageindex=PageIndex;
        };
        PageClick = function(RecordCount, PageIndex) {
            //InitPager(RecordCount, PageIndex);
       select_pager(PageIndex);
        };
        
        
        
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
    /*
	*获取前一个月的第一天
	* date : Date类型.
	*/
	function getMonthBeforeDay(date){
		
		 var yesterday_milliseconds=date.getTime();  //-1000*60*60*24   
		 var yesterday = new Date();     
	     yesterday.setTime(yesterday_milliseconds);     
	   
		 var strYear = yesterday.getFullYear();  
		 var strDay = yesterday.getDate();  
		 var strMonth = yesterday.getMonth()+1;
		 if(strMonth<10)  
		 {  
		  strMonth="0"+strMonth;  
		 }  
		 if(strDay<10)  
		 {  
		  strDay="0"+strDay;  
		 }  
		 datastr = strYear+"-"+strMonth+"-"+strDay;
		 return datastr;
	}
</script>
  </head>
  
  <body>
     <div class="daw">
          	 
           <div class="daw-z">
   
   
    <table width="100%" id="searchTable" align="center" class="editTable" border="0" cellspacing="0" cellpadding="0" style="border-bottom: 0px;">
	<tr style="vertical-align: top;" height="10"><td colspan="4" align="center"> <strong>Return Mail Query</strong></td></tr>
	<tr><td class="title">Start Date:</td>
	<td class="field"><input type="text" style="width:150px;" id="startDate" onclick="return Calendar('startDate')"/>
	<%--<img src="css/003_15.png" name="clear1" id="clear1"   align="middle"     onClick="javascript:$('#startDate').val('');">
	--%></td>
	<td class="title">End Date:</td>
	<td class="field"><input type="text" style="width:150px;" id="endDate" onclick="return Calendar('endDate')"/>
	<%--<img src="css/003_15.png" name="clear1" id="clear1"   align="middle"     onClick="javascript:$('#endDate').val('');">
	--%></td></tr>
	
	

	
	<tr>
	<td  class="title">Client ID:</td><td  class="field">
	<input type="text" id="clientId"/><input id="staffcode"  type="hidden"/></td>
	<td  class="field"></td>
	<td  class="field">
	<input type="button" id="search" value="Search"/>&nbsp;&nbsp;
	<input type="button" id="report" value="Report"/>
	<input type="button" id="sendMail" value="Send Mail"/>
	
	</td>
	</tr>
	
	</table>        
    <table width="70%" id="stable" class="table-ss" border="1"  cellspacing="0" cellpadding="0" style="border:1px solid #000;">
        
        <tr  class="title" style="background-color:silver;"><%--
            <td align="center" class="title" colspan="2">
                &nbsp;<span class="bigtitle">Return Mail ScanningRecord</span></td>
        --%>
        <td >Serial</td>
        <td >ClientId</td>
        <td >DocumentType</td>
        <td >Create Document Date</td>
        <td >DocumentType Detail</td>
        <%--
        <td >DocumentDate</td>
        --%><td >ClientName</td>
        <td>StaffCode</td>
        <%--<td>StaffName</td>
        <td>ChickenBox No</td>
        <td>Location</td>
        <td>ScanningName</td>
        --%><td>ScanningDate</td>
        <td>Return Mail Reason <font style="color:blue">*</font></td>
        <td id="td_emailStatus">Email Send Status</td>
        </tr></table>

<table id="remark_table" width="70%" class="table-ss" height="20" cellspacing="0" cellpadding="0" style="border:1px solid #000;border-top-width:0px;display:none;">
<tr class='field'><td colspan='7' style='color:blue;size=14px' align='left'><b>* Return Mail Reason will be captured since 20 October, 2014.</b></td></tr></table>
       </div>
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
<%--<div id="test" class="pageinfo" ></div>
 
   --%></div> 
  </body>
</html>
