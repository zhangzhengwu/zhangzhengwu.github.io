
$('td').each(function(){
	if($(this).text().length>3){
		$(this).attr("id",$(this).text());
	}
});
$("body").append("  <div class='quick-box'><div class='quick-content'><table><thead><tr><th>DD Tree Head</th><th>Color</th></tr></thead><tbody></tbody></table></div></div>");
$("body").append(" <div id='seatlists' style='position:absolute;left:"+($('table').width()+80)+"px;top:0px;width:200px;min-height:300; background: #fff; z-index: 998;'><table><tr><td>Seat No.</td><td>Staff Code</td><td>Staff Name</td><td>DDTH</td></tr></table></div>");
$(function(){
    $('.quick-box').hover(function(){
       $(this).toggleClass('active');
    },function(){
        $(this).toggleClass('active');
    });
    
   //$(".ltrb").append("<div class='rotate135' style='height:"+$(".ltrb").height()+"px'></div>");
    $(".ltrb").append('<svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%"><line x1="0" y1="0" x2="100%" y2="100%" stroke="black" stroke-width="1"/></svg>');
    $(".lbrt").append('<svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%"><line x1="100%" y1="0%" x2="0%" y2="100%" stroke="black" stroke-width="1"/></svg>');
});
function myajaxforseat(url,data,callback){
	$.ajax({
		url:url,
		data:data,
		type:"post",
		dataType:"json",
		success:function(result){
			 if(result.state=="success"){
				 seatlist=result.data;
				 var dd=new Array();
				 var seats="";
				 for(var i=0;i<seatlist.length;i++){
					 if("N"==seatlist[i].ifhidden){
						if(seatlist[i].teamcode !=null){
							//$("td[id='"+seatlist[i].seatno+"']").addClass("td"+seatlist[i].teamcode).attr("title",seatlist[i].EmployeeId+" - "+seatlist[i].EmployeeName);
							$("td[id='"+seatlist[i].seatno+"']").addClass("td"+seatlist[i].teamcode).attr("title","Staff Code: "+seatlist[i].EmployeeId+"\r\nStaff Name: "+seatlist[i].EmployeeName+"\r\nDDTH: "+seatlist[i].DDTreeHead);
						 }else{
							 $("td[id='"+seatlist[i].seatno+"']").addClass("td0");
						 }
					 }else{
						 $("td[id='"+seatlist[i].seatno+"']").text("");
					 }
					if(seatlist[i].DDTreeHead!=null){
						if($.inArray(seatlist[i].DDTreeHead, dd)<0){
							dd.push(seatlist[i].DDTreeHead);
							$("div.quick-content table tbody").append("<tr><td>"+seatlist[i].DDTreeHead+"</td><td><div class='colorBox td"+seatlist[i].teamcode+"' ></div></td></tr>");
						}
					}
					if("N"==seatlist[i].ifhidden){
						seats+="<tr><td>"+seatlist[i].seatno+"</td><td>"+(seatlist[i].EmployeeId||"")+"</td><td style='text-align:left;'>"+(seatlist[i].EmployeeName||"")+"</td><td>"+(seatlist[i].DDTreeHead||"")+"</td></tr>"
					}
				 }
				 if(seats!=""){
					 $("#seatlists table").append(seats);
				 }
			 }
		},error:function(){
			alert("Connection Error!");
		}
	});
}