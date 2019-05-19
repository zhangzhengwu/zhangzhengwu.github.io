<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>QueryRecruitment</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">  
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./css/Util.js?ver=<%=new Date() %>"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="./css/style.css">
    <style type="text/css">
<!--

 .images{
width:16px;
 height:16px; 
 margin-top:2px;
}
.inputNumber{
width: 24px;
border-left-width: 0px;
border-right-width: 0px;
border-top-width: 0px;
border-bottom-width: 0px;

}
.inputNumber_color{
width: 24px;

 border:#009AE2 solid 1px;


}

#Medical {
position:absolute;
	width:100%;
	height:99%;
	z-index:0;
	left: 0px;
	top: 0px;
}
#overs {

	position:absolute;
		top: 0px;
	width:100%;
	height:100%;
	z-index:50;
	display:none;
	font-size: 14px;
	font-family: 'Arial Narrow';
	background-color: #F5FFFA;
}
.span_menu{
	background-image: url("images/bg.gif");
	z-index:5;
	width: 100%;
}
.head_bottom{
	top:expression(document.getElementById('divIdSelected').scrollTop-2);
 	position:relative ;
 	background-color: silver;
	height: 25px;
}

#divIdSelected{
	 
	/****滚动块颜色*/
	SCROLLBAR-FACE-COLOR: #F0FFFF;
 	/****滚动块边框颜色*/
 	SCROLLBAR-SHADOW-COLOR: #fff; 
 	/****滚动条箭头颜色*/
 	SCROLLBAR-ARROW-COLOR: #EAECEC;
    /****滚动条背景颜色*/
    SCROLLBAR-TRACK-COLOR: #fff;
}

body{
	font-family: 'Arial Narrow';
	width: 100%;
	background-image: url("css/officeAdmin_bg.gif");
	overflow-x:hidden;
}
tr td{
	font-size: 12px;
	font-family: 'Arial Narrow';
}
.width_left{
	left:0px;
	width:100%;
}
.txt{
    color:#0000FF;
    border:0px;
    border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
 	background-color:transparent; /* 顏色透明*/
	size:35px;
	font-style:oblique;
	font-size: 12px;
	font-family: 'Arial Narrow';

}
.select{text-align: center;
   height: 12px;
}

.txts{
    color:#005aa7;
    border:0px;
    border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:35px;
	font-size: 14px;
	font-family: 'Arial Narrow';

}
.inputstyle{
border-width:0px;
background-color: #F5FFFA;
}
img{
	width: 12px;
	height: 12px;
}
.page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}
.STYLE1 {
		font-size: 14px;
	font-family: 'Arial Narrow';
	font-weight: bold;
	color: #00305A;
}
-->
    </style>
  <link href="./css/style.css" rel="stylesheet" type="text/css">
  </head>
  <script type="text/javascript" >
  var downs=null;
  var d=null;
  var curPage=1;
  var totalPage=1;
  var temp;
  function resize(){
	  $("#overs,#divId,#divIdSelected,.span_menu,#Medical").width($("body").width()).css("left",0); 
  }
  
  $(function(){
	  window.onresize=resize;
  	    var econvoy="${Econvoy}".split("-");
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
			$("#staffcode").val(econvoy[0]);
  	  
     //注册单击事件
 	var da = new Date();
     //注册单击事件
	$("#start_date").val(getMonthBeforeDay(da));	
	$("#end_date").val(getMonthBeforeDay(da));	

	$("#pre").bind("click", function() {//上一页
		curPage=curPage - 1;
		selects();
	});
	
	$("#next").bind("click", function() {//下一页
		curPage=curPage + 1;
		selects();
	});
	$("#first").bind("click", function() {//首页
		curPage=1;
		selects();
	});
	$("#end").bind("click", function() {//尾页
		curPage=totalPage;
		selects();
	});
	/***************************************************************************************************/
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
	/*
	*获取前一个月的最后一天
	*date : Date类型.  
	*/
	
	function getBeforeMonthLastDay(date){
		
		 var yesterday_milliseconds=date.getTime();     //-1000*60*60*24
		 var yesterday = new Date();     
	     yesterday.setTime(yesterday_milliseconds);     
	   
		 var strYear = yesterday.getFullYear();  
		 var strDay = yesterday.getDate();  
		 var strMonth = yesterday.getMonth()+1;
		 if(strMonth<10)  
		 {  
		  strMonth="0"+strMonth;  
		 }  
		 datastr = strYear+"-"+strMonth+"-"+strDay;
		 return datastr;
	}
	
	/**************************************************selects Click*****************************************************/
	$("#searchs").click(function(){
		selects();
	});

  });
  
  
  	function selects(){
                //时间判断
			if($("#start_date").val()!="" && $("#end_date").val()!=""){
				var beginTime = $("#start_date").val();
				var endTime = $("#end_date").val();
				if(beginTime>endTime){
					alert("Start Date can’t be later than End Date.");
					$("#start_date").focus();
					return false;
				}
			}
			$.ajax({
			type: "post",	
			url:"QueryRecruitmentHRServlet",
			data: {"date1":$("#start_date").val(),"date2":$("#end_date").val(),"staffcode":$("#staffcode").val(),"refno":$("#ordercode").val(),"staffname":$("#staffname").val(),"status":$("#status").val(),'pageIndex':curPage,"method":"findAll"},
			success:function(data){
				var totals=0;
				var dataRole=eval(data);
				var html="";
				$("tr[id='select']").remove();
				downs=null;
				if(dataRole[0].length>0){
					downs=dataRole;
					curPage=dataRole[2];
	 			   	totalPage=dataRole[1];
	 			   
					for(var i=0;i<dataRole[0].length;i++){
						html+="<tr id='select' title='"+i+"' style='line-height:20px;'><td align='center'>"+
						dataRole[0][i].refno+"</td><td align='center'>"+
						dataRole[0][i].staffcode+"</td><td align='center'>"+
						dataRole[0][i].staffname+"</td><td align='center'>"+
						dataRole[0][i].usertype+"</td><td align='center'>"+
					    dataRole[0][i].position+"</td><td align='center'>"+
						dataRole[0][i].contactperson+"</td> <td align='center'>" +
						dataRole[0][i].contactemail+"</td><td align='center'>"+
						dataRole[0][i].chargecode+"</td><td align='center'>"+
						dataRole[0][i].chargename+"</td><td align='center'>"+
						dataRole[0][i].date+"</td><td align='center'>"+
						dataRole[0][i].createdate+"</td><td align='center'>"+
						dataRole[0][i].creater+"</td><td align='center'>"+
						dataRole[0][i].status+"</td><td align='center'><a href='javascript:void(0);' onclick='Detail("+i+");' name ='1'>Detail</a>";
							html+="</td></tr>";
					}
						$(".page_and_btn").show();
				}
				else{
					$(".page_and_btn").hide();
					html+="<tr id='select' style='line-height:20px;'><td colspan='18' style='color:blue;size=5px' align='center'><b>"+"Sorry, there is no matching record."+"</b></td></tr>";
				}	   
				     page(dataRole[2],dataRole[1]);	
					 $("#jqajax:last").append(html);
					 $("#curretPage").empty().append(dataRole[2]);
					 $("#totalPage").empty().append(dataRole[1]);
				 	 $("tr[id='select']:odd").css("background","#F0F0F0");
	                 $("tr[id='select']:even").css("background","#COCOCO");
			 }
		 });
	}
  function page(currt,total){
	 
		if(curPage<=1){
			$("#first").hide();
			$("#pre").hide();
		}else{
			$("#first").show();
			$("#pre").show();
		}
		if(curPage>=totalPage){
			$("#end").hide();
			$("#next").hide();
		}else{
			$("#end").show();
			$("#next").show();
		}
	}
  function Detail(i){
      $.ajax({
         url:"QueryRecruitmentHRServlet", 
         type:"post",
         data:{"refno":downs[0][i].refno,"method":"detial"},
         success:function(date){
            var ht="";
            temp=eval(date);
            if(temp!="error"){
                $("#overs").show();
                $("#Medical").hide();
                $(".product").remove();
                //除了cancle按钮以外,所有按钮在页面初始化时隐藏
                $("#Confirm").hide();
                $("#void").hide();
                $("#Completed").hide();
                $("#Scheduled").hide();
                $("#payId").attr("style","display:none");
                  //状态为completed
                if(temp[0].status=="Completed"){
                  $("#date").attr("disabled",true).css("border-bottom-color","");
                  //显示付费信息 div
                  $("#payId").attr("style","display:block");
                  //赋值     
                  $("#saleno").val(temp[2].saleno);                
                  $("#spanMoeny").text("HKD"+temp[1].price);
                  $("#payDate").val(temp[2].paymentDate);
                  $("#handledId").val(temp[2].handleder);
                 $("#saleno,#payDate,#handledId").attr("disabled",true).css("border-bottom-color","");
                 }             
                //显示订单信息
                 $("#refno").val(temp[0].refno);
                 $("#name").val(temp[0].staffname);
                 $("#code").val(temp[0].staffcode);
                 $("#position").val(temp[0].position);
                 $("#dstatus").val(temp[0].status);
                 $("#date").val(temp[0].date);
                 $("#person").val(temp[0].contactperson);
                 $("#email").val(temp[0].contactemail);
                 $("#Chargecode").val(temp[0].chargecode);
                 $("#ChargeName").val(temp[0].chargename);
                 //显示订单详细信息     
                 ht="<tr class='product'><td>"+temp[1].refno+"</td><td>"+temp[1].mediacode+"</td><td>"+temp[1].medianame+"</td><td>"+temp[1].price+"</td></tr>"; 
                 $("#leftTable").append(ht);
            }else{
               alert("system  Date error");
            }      
           
        }
       }); 
     }
       //cancle 按钮事件     
       function hideview(){
            selects();
            $("#overs").hide();
            $("#Medical").show();
       }
      
  
 
  </script>
  <body style='overflow:auto;'><br>
  
  <div id="overs" >
  <div align="center" style="color:#00305A;font-family: 'Arial Narrow';font-size:16px; top:0px;bottom: 20px;absolute;width: 100%;"><strong>
			      Stationery Order Information
			    </strong></div>
	     <div class="span_menu" align="left" style="line-height:25px;"> <strong>Recruitment Request</strong></div>
	     <table width="100%"  border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
	        
	            <tr>
	              <td height="28" align="left" width="20%">Refno：</td> <td align="left" width="80%">            <input type="text" name="dordercode" id="refno" disabled="disabled" class="inputstyle"> </td>
	              
	            </tr>
	             <tr>
	              <td height="28" align="left">Staff Name： </td> <td align="left">             <input type="text" name="dclientname" id="name" disabled="disabled" class="inputstyle"> </td>
	            </tr>
	             <tr>
	              <td height="28" align="left">Staff Code：</td> <td align="left">              <input type="text" name="dclientcode" id="code" disabled="disabled" class="inputstyle"> </td>
	            </tr>
	               <tr>
	              <td height="28" align="left">Position：</td> <td align="left">              <input type="text" name="dlocation" id="position" disabled="disabled" class="inputstyle"> </td>
	            </tr>
	            <tr>
	              <td height="28" align="left">Date：   </td> <td align="left">            <input type="text" name="date" id="date"  class="inputstyle" style="border-bottom:1px solid blue;" onClick="return Calendar('date')"> </td>
	            </tr>
	             <tr>
	           <td height="28" align="left">ConstactPerson：   </td> <td align="left">            <input type="text" name="person" id="person" disabled="disabled" class="inputstyle"> </td>
	            </tr>
	            <tr>
	           <td height="28" align="left">ConstactEmail：   </td> <td align="left">            <input type="text" name="email" id="email" disabled="disabled" class="inputstyle"> </td>
	            </tr>
	            <tr>
	           <td height="28" align="left">ChargeCode：   </td> <td align="left">            <input type="text" name="Chargecode" id="Chargecode" disabled="disabled" class="inputstyle"> </td>
	            </tr>
	            <tr>
	           <td height="28" align="left">ChargeName：   </td> <td align="left">            <input type="text" name="ChargeName" id="ChargeName" disabled="disabled" class="inputstyle"> </td>
	            </tr>
	             <tr>
	              <td height="28" align="left">Status：   </td> <td align="left">            <input type="text" name="dstatus" id="dstatus" disabled="disabled" class="inputstyle"> </td>
	            </tr>
	    </table>
	    <div class="span_menu" align="left" style="line-height:25px;"> <strong>Request Options</strong></div>
	    <div id="divIdSelected" style="width:100%;height: auto; max-height:40%;overflow: auto"  class="width_left">
	    	<table border="1"  id="leftTable" width="100%" cellpadding="0" cellspacing="0"  style='font-family: Arial Narrow;text-align: center;margin-left:-1px;' class="table-ss"  >
	    	<tr bgcolor='#F0FFFF' style="line-height: 25px;">
	    	<td align='center' width="5%"><strong>Refno</strong></td>
	    	<td align='center' width="15%"><strong>Mediacode</strong></td>
	    	<td align='center' width="15%"><strong>Medianame</strong></td>
	    	<td align='center' width="10%"><strong>Price</strong></td>
	    	</tr>
	    	</table>
	    </div>
	    <div id="payId" style="display: none;">
	    <div align="left" style="line-height:25px;font-family: Arial Narrow;background-image: url('images/bg.gif');font-size: 14px;"> <strong>Payment(for internal use)</strong></div>
	    <table id="topTable" rules="all" border="1" width="100%" cellpadding="0" cellspacing="0"  style='border:1px solid gary;font-family: Arial Narrow;text-align: center;margin-left:-1px;' >
	    	<tr >
	    	<td align='left' width="20%">Payment Method</td>
	    	<td align='left' width="80%" height="28">
             <select id="paySelectId">
             	<option value="Deduct From Commission">Deduct From Commission</option>
             </select>
             &nbsp;&nbsp;<input type="text"  id="saleno" name="saleno"  class="inputstyle" style="border-bottom:1px solid blue;">
	    	</td>
	    	</tr>
	    	<tr >
	    	<td align='left' width="20%">Payment Amount</td>
	    	<td align='left' width="80%" height="28">
	    	<input type="checkbox" id="moneyId" checked="checked" disabled="disabled" class="paymentAmount"/><span id = "spanMoeny">HKD</span>
	    	</tr>
	    	<tr >
	    	<td align='left' width="20%">Payment Date</td>
	    	<td align='left' width="80%" height="28"><input type="text" id="payDate" class="inputstyle" style="border-bottom:1px solid blue;" onClick="return Calendar('payDate')" /></td>
	    	</tr>
	    	<tr >
	    	<td align='left' width="20%">Handled by</td>
	    	<td align='left' width="80%" height="28"><input type="text" id="handledId" class="inputstyle" style="border-bottom:1px solid blue;"/></td>
	    	</tr>
    	</table>
    	</div>
	    <div style="width: 100%;"><p align="center">  
	    	  <input type="button" name ="bbb" id="Confirm" value="Confirmation Request" /> &nbsp;&nbsp;&nbsp;&nbsp;
	    	   <input type="button" name ="zzz" id="Scheduled" value="Scheduled" /> &nbsp;&nbsp;&nbsp;&nbsp;
	    	    <input type="button" name ="aaa" id="Completed" value="Completed" /> &nbsp;&nbsp;&nbsp;&nbsp;
	    	  <input type="button" name ="ddd" id="void" value="VOID" /> &nbsp;&nbsp;&nbsp;&nbsp;
	   		 
	   		  <input type="button" name ="ccc" id="Canel" value="Canel" onClick="hideview();"/> &nbsp;&nbsp;&nbsp;&nbsp;</p>
	    </div>
    </div>
  <br>
  
  <div id="Medical">
  <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="table-ss" background="css/officeAdmin_bg.gif">
            <tr>
              <td height="21" colspan="4" align="left"><div align="center" class="STYLE1">Query Recruitment  Order For HR<br>
              </div></td>
            </tr>
            <tr>
              <td width="10%" height="32" align="right">Start&nbsp;&nbsp;Date ：              </td>
              <td width="25%" align="left"><input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" />
              &nbsp;&nbsp; <img src="css/003_15.png" name="clear1" id="clear1"  align="middle"   onClick="javascript:$('#start_date').val('');"></td>
              <td width="10%" height="32" align="right">&nbsp;&nbsp;End&nbsp;&nbsp;&nbsp;&nbsp;Date ：              </td>
              <td width="25%" align="left"><input id="end_date" type="text" name="it"  onClick="return Calendar('end_date')" />
              &nbsp;&nbsp; <img src="css/003_15.png" name="clear1" id="clear2"   align="middle"   onClick="javascript:$('#end_date').val('');"></td>
            </tr>
            
      
            <tr style="display:none">
              <td height="23" align="right">Staff Code&nbsp;：              </td>
              <td height="23" align="left"><input type="text" name="clientcode" id="staffcode"></td>
              <td height="23" align="right">Staff&nbsp;&nbsp;Name ：              </td>
              <td height="23" align="left"><input type="text" name="clientname" id="staffname"></td>
            </tr>
        
                    <tr>
              <td height="23" align="right">OrderCode ：              </td>
              <td height="23" align="left"><input type="text" name="ordercode" id="ordercode"></td>
               <td height="23" align="right">Status ：              </td>
               <td height="23" align="left">
					 <select id="status">
		             	<option value="">Please Select Status</option>
		             	<option value="Submitted" >Submitted</option>
		             	<option value=" Confirmation Request"> Confirmation Request</option>
		             	<option value="Scheduled">Scheduled</option>
		             	<option value="Completed">Completed</option>
	              </select>
				</td>
            </tr>
            <tr>   
      		  <td width="63" colspan="2" align="left" style="color:red"></td>
              <td width="178" colspan="2"> </td>
            </tr>
            <tr> 
              <td colspan="4" align="left" style="color:red"> </td>
            </tr>
            <tr>
              <td colspan="2" align="right"></td>
              <td  align="right"><input id="searchs" name="search" type="submit" value="Search" align="right"/>                     
               </td>
               <td></td>
            </tr>
    </table>
    
      <table width="100%" border="1" cellpadding="0" cellspacing="0" class="table-ss" id="jqajax" >
      
      <tr id="title"  bgcolor='silver' style="line-height: 25px;">
        <td  align="center"><b><Strong>Refno</strong></b></td> 
        <td  align="center"><strong>StaffCode</strong><br></td> 
        <td  align="center"><strong>StaffName</strong><br></td>
        <td  align="center"><strong>UserType</strong><br></td>
        <td  align="center"><strong>Position</strong><br></td>
        <td  align="center"><strong>ContactPerson</strong><br></td>
        <td  align="center"><strong>ContactEmail</strong><br></td>
        <td  align="center"><strong>ChargeCode</strong><br></td>
        <td  align="center"><strong>ChargeName</strong><br></td>
        <td  align="center"><strong>Date</strong><br></td>
        <td  align="center"><strong>CreateDate</strong><br></td>
        <td  align="center"><strong>Creater</strong><br></td>
        <td  align="center"><strong>Status</strong><br></td>
        <td  align="center"><strong>Handle</strong><br></td>
      </tr>
    </table>
    
    <div align="center" class="page_and_btn">

			<table class="table-ss" width="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr class="main_head">
					<td colspan="14" align="center">
						<a id="first" href="javascript:void(0);">first Page</a>
						<a id="pre" href="javascript:void(0);">Previous Page</a><SPAN style="color: red;" id="curretPage">
						</SPAN> /
						<SPAN style="color: red;" id="totalPage">
						</SPAN>Page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
  </div>
  </body>
</html>
