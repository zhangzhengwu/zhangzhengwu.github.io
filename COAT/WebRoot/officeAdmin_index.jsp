<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Convoy Office Admin Tools Index</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">

*{
font-family: Arial;
text-shadow: 1px 1px 1px #FFFFFF;
font-size:12px; 
color: #333;
}
body{
	margin: 0px;
	padding: 0px;
}
.container{
	border: 1px solid #eee;
	padding: 10px;
	margin: 10px;
	border-right: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
	border-top: 2px solid #428bca;
	background: #fff;
}
.body_index td{
	height:30px;
	line-height: 30px;
	word-break: break-word;
	word-wrap: break-word;
}
.field{
background-color: #DFF2FF;height:30px;
}
.body_index{
	border-top:1px solid #CACACA;
	border-bottom:1px solid #CACACA;
	overflow-y:auto;
	overflow-x:hidden;
	max-height:500px;
}

 #topic{
	margin:0px;
	padding:0px;
	 position:absolute;
	 width:350px;
	 height:220px;
	 z-index:1000;
	 display:none;
 	/**background-color:#F5F5F5;**/
	background-color:#F5F5DC;
	border:1px solid #D3D3D3;
 	border-radius:4px;
 	
 }

</style>
<script type="text/javascript">
$(function(){
	 // $("#topic").slideDown("slow");
	//  $("#topic").css("right",1).css("bottom",0);
	//  setTimeout('$("#topic").delay(10000).slideUp("slow")',0);
	  

});

</script>


  </head>
  
  <body>
  	<div class="container">
   <pre >
Welcome to the Office Adm System of Convoy (COAT, Convoy Office Adm Platform)
With this system, you can :
-	Raise the administration requests
-	Checking your client’s return mail
-	Checking your opt-out clients
-	Checking your medical claim history

Below is a summary of function of COAT :
</pre>
<div class="body_index" >
<table  cellpadding="0" cellspacing="0" width="100%" style="border:0px soild #4BACC6;border-collapse: collapse;min-width:1024px;">
	<tr>
		<td width="200px">Medical Claim</td><td>	To enquire your medical claim history</td>
	</tr>
	<tr class="field">
		<td>Name Card</td><td>To apply new name card. The info of name card will be defaulted as your last input data during your last application of name card. And you can change it according to your latest info.</td>
	</tr>
	<tr>
		<td>Stationery Form	</td><td>To buy stationery, click the stationery form, a list of available stationery items will be for your choice to buy(Price renewed yearly on 1 Jan).</td>
	</tr>
	<tr class="field">
		<td>Marketing Premium Form	</td><td>	To buy marketing premium, click the stationery form, a list of available stationery items will be for your choice to buy.</td>
	</tr>
	<tr>
		<td>Return Mail	</td><td>	You can sort out all your clients with return mail, if any, by date range.</td>
	</tr>
	<tr class="field">
		<td>Client Opt Out List	</td><td>	You can sort out all your opt-out clients, if any, by clicking the “Search” key in this page</td>
	</tr>
	<tr>
		<td>Access Card	</td><td>	To apply an access card if your existing card is loss or damaged
	</tr>
	<tr class="field">
		<td>ADM Services	</td><td>	To notify ADM dept to change of fluorescent tube, repair desk phone, reset pw of desk phone, copier repair or others</td>
	</tr>
	<tr>
		<td>Keys		</td><td>To request for locker key, drawer key or pigeon box key. Your locker no., drawer no., and pigeon box number will be defaulted at the entry field for your easy reference.</td>
	</tr>
	<tr class="field">
		<td>Company Asset	</td><td>	To borrow company equipment for your outdoor promotion activities.
	</tr>
	<tr>
		<td>Room Setting	</td><td>	To request for combining the meeting room. Please be reminded to book the rooms for combining.</td>
	</tr>
	
	<tr class="field">
		<td>Recruitment Advertising	</td><td>	To queue up for the recruitment advertising placement (AD or above ONLY)</td>
	</tr>
	
	
	
</table>
</div>
 <pre >
The COAT is a very user friendly system. Once your click “Submit” button at each request, it is done.
You can download “<a href="manual/Convoy Office Admin Tools (COAT) User Guide v6.docx">User manual  of COAT</a>” for your reference”
<!-- User manual of the old functions of COAT<a href="manual/Convoy Office Admin Tools(COAT)使用说明.docx">download</a> -->
</pre>
      
	  
	  <div id="topic" >
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;border-radius:4px;">
<tr style="line-height: 25px; height:25px;background-image:url(css/officeAdmin-menu-bar.gif);">
<td  style="color:white;font-weight: bold;padding-left:5px;text-shadow: 0px 1px 1px #FFFFFF;">Notice</td>
<td style="padding-right:5px;"><span onclick="javascript:$('#topic').remove();" style="float:right;border:0px solid #808080;line-height:16px;width:18x;vertical-align:middle;cursor:pointer;font-weight: bold;" onMouseOut="javascript:$(this).css('border-width','0px');"onMouseOver="javascript:$(this).css('border-width','1px');">&nbsp;&nbsp;X&nbsp;&nbsp;</span></td></tr>
<tr>
	<td colspan="2">
		<div style="color:#000;font-size:11px;">
			<table width="100%" height="100%">
				<tr>
					<td colspan="3" style="vertical-align:top;">
						 Dear All,<br/>
							&nbsp;&nbsp;In order to provide you with more hign-quality and convenitent services,<br/>
							&nbsp;&nbsp;the COAT system will be scheduled for the <strong style='color:blue;'>2014-10-17 19:00 - 21:00 </strong>during the upgrade.<br/>
							&nbsp;&nbsp;During this period the COAT system will suspend business Request,Query and Other Services.<br/>
						The inconvenience Please understand.<br/>
						Notice is hereby given.<br/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td align="right" height="50">
						COAT Administrator<br/>
						 2014-10-31	
					</td>
				</tr>
			</table>
		</div>
	</td>
</tr>
</table>
</div>
</div>
  </body>
</html>
