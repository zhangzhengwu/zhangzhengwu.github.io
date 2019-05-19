<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>QueryMedicalStaff</title>
 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">

	<meta http-equiv="description" content="This is my page">  
	
	<script type="text/javascript" src="./css/jquery-1.4.4.js"></script>
	<script src="./css/date.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="./css/style.css">
	<script src="css/Util.js" language="JavaScript"></script>
	

    <style type="text/css">
<!--
#Medical {
	position:absolute;
	width:100%;
	z-index:0;
	left: 3px;
	top: 3px;
}
#modify {
	position:absolute;
	z-index:0;
	width: 100%;
	height: 100%;
	left: 4px;
	top: 10px;
	display: none;
}
.txt{
    color:#0000FF;
    border:0px;
   border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
 background-color:transparent; /* 顏色透明*/
	size:35px;
	font-style:oblique;
	font-family: '仿宋';
	font-size: 18px;

    }
	.txts{
    color:#005aa7;
    border:0px;
     border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:35px;
	font-family: '仿宋';
	font-size: 16px;

    }
    .txt_staff{
   color:#005aa7;
    border:0px;
     border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
    background-color:transparent; /* 顏色透明*/
	size:35px;
	font-family: '仿宋';
	font-size: 16px;


    }
    
 .txt_readonly{
    color:#0000FF;
    border:0px;
   border-bottom:1px solid #005aa7; /* 下划线 */ 
	height:20px;
 	background-color:transparent; /* 顏色透明*/
	size:35px;
	font-style:oblique;
	font-family: '仿宋';
	font-size: 18px;


    }
.STYLE1 {
	font-size: 36px;
	font-weight: bold;
	color: #0000FF;
}
#content{
	position:absolute;
	width:100%;
	z-index:-1;
	left:3px;
	
}
.page_and_btn {width:100%;height:auto;padding:10px 0px 0px 0px;display: none;}
.page_and_btn ul { margin:0px;list-style:none;float:right;width:auto;height:100%;padding:0px;}
.page_and_btn ul li { float:left; border:1px solid #CCCCCC; height:20px; line-height:20px; margin:0px 2px;}
.page_and_btn ul li a{color:#333; text-decoration:none;}
.page_and_btn ul li a, .pageinfo { display:block; padding:0px 6px; background:#FAFBFD;}
.page_and_btn  { color:#555;}
.main_head{height:27px;text-align: center;background: url("images/news-title-bg.gif") repeat-x;}
.page_and_btn div{float:left;}

img{
height:12px;
width:12px;
}
-->
    </style>
  </head>
  <script type="text/javascript" >
  var downs=null;
  var pagenow =1;
  var totalpage=1;
  var total=0;
  var id=null;
  var reType=null;
  var reMedicalDate=null;
  var reMedicalFee=null;
  var reEntitle=null;
  var reReturn="N";
  var email=null;
  var addDate=null;
  var addName=null;
  
  $(function(){
	 $("#modify [class='txt_readonly']").attr("readonly","readonly");
   	 $("#content").css("top",$("#Medical").height());
	 $("#start_date").val(CurentTime);
     $("#end_date").val(CurentTime);
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
/*****************************************************Down click**********************************/
/**
 * 導出到個人
 * */

$("#down").click(function(){ 
		if(downs!=null){
			//window.location.href="../DownForConsServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
			//	window.location.href="../TestDownServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
			$.ajax({
				type: "post",
				url:"DownMedicalStaffServlet",
				async:false,
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'staffcode':$("#staffcodes").val(),'oraginal':$("#oraginal").val()},
				success:function(date){
					clipboardData.setData('Text',date.substring(date.indexOf(':')+1));
				alert(date);
				},error:function(){
					alert("导出失败!");
				}
				});
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
			}
	
	});
	/**
	 * 導出全部數據
	 * */
	$("#down_all").click(function(){ 
		if(downs!=null){
	 
			 window.location.href="../DownAllMedicalStaffServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&staffcode="+$("#staffcodes").val()+"&oraginal="+$("#oraginal").val();
		//	window.location.href="../TestDownServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
		
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
			}
	
	});
	/**
	 * 導出給FAD
	 * */
	$("#down_fad").click(function(){ 
		if(downs!=null){
			//window.location.href="../DownForConsServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
			//	window.location.href="../TestDownServlet?startDate="+$("#start_date").val()+"&endDate="+$("#end_date").val()+"&code="+$("#staffcodes").val()+"&name="+$("#name").val();
			$.ajax({
				type: "post",
				url:"DownForFADMedicalStaffServlet",
				async:false,
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'staffcode':$("#staffcodes").val(),'oraginal':$("#oraginal").val()},
				success:function(date){
					clipboardData.setData('Text',date.substring(date.indexOf(':')+1));
				alert(date);
				},error:function(){
					alert("导出失败!");
				}
				});
		}
		else{
			alert("请先查询数据，再做导出相关操作！");
			}
	
	});
	/******************************************************Down END***********************************/
	
	/**************************************************selects Click*****************************************************/
	$("#searchs").click(function(){
	
	selects(1);
	});
/******************************************************************************************************************/
/**
 * 报销金额框离开事件 
 * 
 * */
  $("#MedicalFee").blur(function(){
	  if($("#MedicalDate").val()!=""){
	  if($("#Medical_type").find("option:selected").val()==""){
		  $("#MedicalFee").val("");
		  alert("请先选择类型!");
		 
		  return false;
		  }
	  if(parseFloat($("#MedicalFee").val())<=0 || $("#MedicalFee").val().length<=0){
		  alert("Medical Fee 不能等于0或为空!");
		   $("#MedicalFee").focus();
		  return false;
	  }
	  if(isNaN($("#MedicalFee").val())){
		   alert("金額必須是數字！");
		   $("#MedicalFee").val("").focus();
		  return false;
	  } if($("#MedicalFee").val().length>0 && $("#MedicalFee").val().indexOf('.')==($("#MedicalFee").val().length-1)){
		   $("#MedicalFee").val($("#MedicalFee").val()+"00");  
	  }
	
		moeny();
		}
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

	
  });
   /***********************************************select function************************************/
	function selects(pagenow){
			/**	if($("#start_date").val()==""){
					alert("開始日期不能為空！");
					$("#start_date").focus();
					return false;
				}	
				if($("#end_date").val()==""){
					alert("結束日期不能為空！");
					$("#end_date").focus();
					return false;
				}**/
				 
				if($("#start_date").val()!="" && $("#end_date").val()!=""){
					if($("#start_date").val()>$("#end_date").val()){
						alert("開始日期不能大於結束日期!");
						$("#start_date").focus();
						return false;
					}
				}
		 
				$.ajax({
				type: "post",
				url:"SelectMedicalStaffServlet",
				data: {'startDate':$("#start_date").val(),'endDate':$("#end_date").val(),'staffcode':$("#staffcodes").val(),'oraginal':$("#oraginal").val(),'pageNow':pagenow},
				success:function(date){
				var dataRole=eval(date);
	
					var totals=0;
					var html="";
					$("tr[id='select']").remove();
					downs=null;
				 
					if(dataRole[3]>0){
					total=dataRole[3];
					pagenow =dataRole[2];
				    totalpage=dataRole[1];
					downs=dataRole[0];
						for(var i=0;i<dataRole[0].length;i++){
							html+="<tr id='select' title='"+i+"'><td align='center'>"+dataRole[0][i].staffcode+"</td><td align='center'>"
							+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].dept +"</td><td align='center'>"+dataRole[0][i].grade+"</td><td align='center'>"
							+dataRole[0][i].plan+"</td><td align='center'>"+dataRole[0][i].type+"</td><td align='center'>"+dataRole[0][i].medical_date+"</td><td align='center'>"+dataRole[0][i].medical_fee+"</td><td align='center'>"
							+dataRole[0][i].amount +"</td><td align='center'>"+dataRole[0][i].medical_month+"</td><td align='center'>"+dataRole[0][i].term +"</td><td align='center'>" 
							+dataRole[0][i].medical_Normal +"</td><td align='center'>"+dataRole[0][i].medical_Special +"</td><td align='center'>"+dataRole[0][i].medical_Dental+"</td><td align='center'>"
							+dataRole[0][i].medical_Regular +"</td><td align='center'>"+dataRole[0][i].return_oraginal +"</td><td align='center'>"+dataRole[0][i].add_Date+"</td><td><a href='javascript:void(0);' onclick='modify_staff("+i+");'>Modify</a> <a href='javascript:void(0);' onclick='delete_staff("+i+");'>Delete</a></td></tr>";		 
						}
			$(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}
					else{
						html+="<tr id='select'><td colspan='18' style='color:blue;size=5px' align='center'><b>"+"對不起，沒有您想要的數據!"+"</b></td></tr>";
					 $(".page_and_btn").hide();
					}
						 $("#jqajax:last").append(html);
					 	 $("tr[id='select']:even").css("background","#COCOCO");
		                 $("tr[id='select']:odd").css("background","#F0F0F0");
		                  page(pagenow,totalpage);
				 }
			 });
	}
	/**************************************************************************************************/
	
	function modify_staff(sid){
				$("#staffcode").val(downs[sid].staffcode);
				$("#MedicalDate").val(downs[sid].medical_date);
				$("#MedicalFee").val(downs[sid].medical_fee);
				$("#staffname").val(downs[sid].name);
				$("#company").val(downs[sid].company),
				$("#deptid").val(downs[sid].dept);
				$("#grade").val(downs[sid].grade);
				$("#entitle").val(downs[sid].amount);
				if(downs[sid].return_oraginal=="Y"){
					$("#return_oraginal").attr("checked","checked");
				}else{
					$("#return_oraginal").attr("checked","");
				}
				$("#pplan").val(downs[sid].plan);	selectType();
				$("#package").val(downs[sid].packages),
				$("#max_amount").val();
				$("#max_amount_money").val(downs[sid].maxamount);
				$("#Normal_Number").val(downs[sid].medical_Normal);
				$("#Special_Number").val(downs[sid].medical_Special);
				$("#Oral_Number").val(downs[sid].medical_Regular==""?0:downs[sid].medical_Regular);
				$("#Dental_Number").val(downs[sid].medical_Dental==""?0:downs[sid].medical_Dental);
				id=downs[sid].id;
				reType=downs[sid].type;
				reMedicalDate=downs[sid].medical_date;
				reMedicalFee=downs[sid].medical_fee;
				reEntitle=downs[sid].amount;
				reReturn=downs[sid].return_oraginal;
				email=downs[sid].email;
				addDate=downs[sid].add_Date;
				addName=downs[sid].add_Name;
		$("#Medical_type option[text='"+downs[sid].packages+"']").attr("selected","selected");
		 $("#modify").show();
		 $("#select").hide();
		 
	}
	
	function delete_staff(d){
	if(confirm("确定要删除该数据?")){
		$.ajax({
			url:"DeleteMedicalStaffServlet",
			type:"post",
			async:false,
			data:{"staffcode":downs[d].staffcode,"upd_date":addDate,"type":downs[d].type,"medical_Dental":downs[d].medical_Dental,"term":downs[d].term},
			success:function(date){
				alert(date);selects(1);
			},error:function(){
				alert("网络连接失败，请稍后重试...");
				return false;
			}
		});
		}
	}
	
	
	
	function selectType(){
		//alert($("#pplan").val());
	$.ajax({
	url:"SelectStaffMedicalServlet",
	type:"post",
	async:false,
	data:{"type":$("#pplan").val()},
	success:function(date){
		if(date!==null || d!==""){
			var dataRole=eval(date);
		var html="";
		staffType=dataRole;
		$("#Medical_type option:gt(0)").remove();
		for(var i=0;i<dataRole.length;i++){
		html+="<option value='"+i+"'>"+dataRole[i].type+"</option>";
		}
		$("#Medical_type").append(html);
		}
	},error:function(){
		alert("网络连接失败，请稍后重试...");
		return false;
	}
	});

}
	/**
 * 根据类型获取对应的次数，金额的上限
 *   
 *   调用处type的change事件
 */

function changeType(){
	$("#package").val($("#Medical_type").find("option:selected").text());
	var num=$("#Medical_type").find("option:selected").val();
	$("#max_amount").val(staffType[num].number);
	$("#max_amount_money").val(staffType[num].money);
	$("#Normal,#Special").val("");
	if($("#MedicalFee").val()!=""){
		moeny();
	}
}
	/**
 * 根据MedicalFee计算Amount Entitle
 *  
 * 调用处  MedicalFee blur事件
 * */
 
  function moeny(){
	  if($("#MedicalFee").val()=="" || isNaN($("#MedicalFee").val())){  //这里不给于无数据提示是因为避免计算MedicalFee出现空指针
		  return ;
	  }
	  	var num=$("#Medical_type").find("option:selected").val();
	  	var moneys=parseFloat($("#MedicalFee").val())*(staffType[num].per);
	  	var entitle=parseFloat(staffType[num].money);
	  	$("#entitle").val(moneys>entitle?entitle:moneys).blur();

  } 
	/**
	 * 保存修改结果
	 * 
	 * 调用处  save单击事件
	 */
	function save(){
		if(!confirm("确认保存本次修改数据？")){
			return false;
		}
			if(checkSave()){
	$.ajax({
		url:"ModifyStaffMedicalServlet",
		type:"post",
		async:false,
		data:{
				"id":id,
				"staffcode":$("#staffcode").val(),
				"type":$("#Medical_type").find("option:selected").text(),
				"MedicalDate":$("#MedicalDate").val(),
				"MedicalFee":$("#MedicalFee").val(),
				"staffname":$("#staffname").val(),
				"Company":$("#company").val(),
				"Department":$("#deptid").val(),
				"grade":$("#grade").val(),
				"entitle":$("#entitle").val(),
				"return_oraginal":$("#return_oraginal").val(),
				"pplan":$("#pplan").val(),
				"package":$("#package").val(),
				"max_amount":$("#max_amount").val(),
				"max_amount_money":$("#max_amount_money").val(),
				"Normal_Number":$("#Normal_Number").val(),
				"Special_Number":$("#Special_Number").val(),
				"Oral_Number":$("#Oral_Number").val(),
				"Dental_Number":$("#Dental_Number").val(),
				"reType":reType,
				"reMedicalDate":reMedicalDate,
				"reMedicalFee":reMedicalFee,
				"reEntitle":reEntitle,
				"email":email,
				"add_Date":addDate,
				"add_Name":addName},
		success:function(date){
				alert(date);
				 $("#modify").hide();
			 $("#select").show();
				$("#searchs").click();
			//	clear_txt();
			//	$("#staffcode").val("").focus();
				},error:function(){
					alert("网络连接失败，请稍后重试...");
					return false;
				}
		});
	}
		
	}
	/**
 *保存之前的验证方法
 * @return {TypeName}
 * 
 *  调用处save方法
 */
	function checkSave(){
	if($("#staffcode").val()==""){
		alert("staffcode 不能为空!");
		$("#staffcode").focus();
		return false;
	}
	else if($("#MedicalFee").val()==""){
		alert("MedicalFee不能为空!");
		$("#MedicalFee").focus();
		return false;
	}else if($("#Medical_type").val()==""){
		alert("请选择MedicalType!");
		$("#Medical_type").focus();
		return false;
	}
	else{
		//没有修改任何数据，避免提交
		if($.trim(reType)==$.trim($("#package").val().substring($("#package").val().lastIndexOf('-')+1))&&reMedicalDate==$.trim($("#MedicalDate").val())&&reMedicalFee==$.trim($("#MedicalFee").val())&&reReturn==$.trim($("#return_oraginal").val())){
			 alert("没有修改任何数据!");
			 clear_txt();
			 $("#modify").hide();
			 $("#select").show();
			return false;
		}else{
		
			return true;
		}
		
	}
}
   /**
   * 及时清除缓存信息  例如 当改变staffcode时首先清除其他信息然后再次添加相应的信息
   * 
   * 调用处staffcode 的blur事件
   * */
function clear_txt(){
	$(".txt,.txts,.txt_readonly,.txt_readonly").val("");
 $("#Oral_Number,#Normal_Number,#Special_Number,#Dental_Number").val(""); 
	
}
   
   
   
/**
 * 金额小数点控制
 * @param {Object} obj
 * 调用处 MedicalFee的按键操作
 */
 function check2(obj)
{
var v = obj.value; 
if(v.indexOf('.')==0){
	obj.value="";
}else if(v.indexOf('.')>0 && v.lastIndexOf('.')<v.length-2 && v.length-2>=0){
	obj.value=v.substring(0,v.lastIndexOf('.')+3).replace(/[^\d\.]/g,'');
}else if(v.indexOf("..")==v.length-2 && v.length-2>=0){//判断小数点是不是最后一位
	
	 obj.value=v.substring(0,v.length-1).replace(/[^\d\.]/g,'');
}
else{
	if(v.indexOf('.')>0 && v.indexOf('.')<v.length-2 && v.length-2>=0){
		if(v.lastIndexOf('.')==v.length-1){
			
			obj.value=v.substring(0,v.length-1).replace(/[^\d\.]/g,'');
		}
		else{
			 obj.value = v.replace(/[^\d\.]/g,'');
		}
	}else{
		 obj.value = v.replace(/[^\d\.]/g,'');
	}
}

}
 
 function rejected(){
	if($("#subject").val()==""){
		alert("请选择Subject!");
		return false;
	}else if($("#staffcode").val()==""){
		alert("staffcode 不能为空!");
		$("#staffcode").focus();
		return false;
	}
	else if($("#MedicalFee").val()==""){
		alert("MedicalFee不能为空!");
		$("#MedicalFee").focus();
		return false;
	}else if($("#Medical_type").val()==""){
		alert("请选择MedicalType!");
		$("#Medical_type").focus();
		return false;
	}
 
	
	var recontext="";
	recontext+="Staff Code: "+$("#staffcode").val()+"%0d";
	recontext+="Consulting date: "+$("#MedicalDate").val()+"%0d";
	recontext+="Consulting Fee:"+$("#MedicalFee").val()+"%0d";
	recontext+="Reject Reason:%0d";
	recontext+="1.	Receipts must be dropped into the drop-in box within 3 months from the consulting date.%0d";
	recontext+="2.	More than 1 medical claim at the same day unless the doctor refer to see the specialist.%0d";
	recontext+="3.	Exceed year quota for medical claim. (12 times for normal Visit and 8 times for special Visit)%0d";
	recontext+="4.	Exceed year quota for medical claim. (15 times for normal Visit and 10 times for special Visit)%0d";
	recontext+="5.	Exceed year quota for medical claim. (1 times for Routine Oral Examination per year)%0d";
	recontext+="6.	Exceed year quota for medical claim. (Overall Maximum HK$5000/HK$7000 per Year)%0d";
	recontext+="7.	Your plan of medical claim is not included Dental.%0d";
	recontext+="8.	To claim as specialist items, referral letter and receipt must be logged in with specialist stated on the receipt.%0d";	recontext+="%0d";
	recontext+="If you have inquiries please email to SZOAdm@convoy.com.hk%0a";
	recontext+="%0d";
 	window.open("mailto:"+$.trim(email)+"?subject="+$("#subject").val()+"&body="+recontext); 
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
   
   function choose(re){
	   if($(re).attr("checked"))
		   $(re).val("Y");
	   else
		   $(re).val("N");
   }
  </script>
  <body> <div id="select">
  <br>
 
  <div id="Medical">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-ss">
    
            <tr>
              <td height="21" colspan="4" align="left"><div align="center" class="STYLE1">QueryMedicalStaff<br>
              </div></td>
            </tr>
            <tr>
              <td   height="48" align="left">開 始 日 期：
              <input name="text" type="text" id="start_date" onClick="Calendar('start_date')" readonly="readonly" />   <img src="css/003_15.png" name="clear1" id="clear1"     onClick="javascript:$('#start_date').val('');">	             </td>
              <td   height="48" align="left"> 結 束 日 期：
              <input id="end_date" type="text" name="it" readonly="readonly" onClick="Calendar('end_date')" />   <img src="css/003_15.png" name="clear1" id="clear2"     onClick="javascript:$('#end_date').val('');">	</td>
              <td align="left" style="color:red"><!-- <div align="right">點擊日期無反應請按<a href="javascript:parent.location.reload();">F5</a></div> --></td>
              <td >&nbsp;</td>
            </tr>
            <tr>
              <td height="23" align="left">顾 问 编 号：
              <input type="text" name="staffcode" id="staffcodes"></td>
              <td height="23" align="left">返回报销凭证：
              <select id="oraginal">
              <option value="">请选择</option>
              <option value="Y">是</option>
              <option value="N">否</option>
              </select></td><%--
              <td height="23" align="left">顾 问 名 称：
              <input type="text" name="names" id="name"></td>
              --%><td align="left" style="color:red"></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              
              <td colspan="4" align="left" style="color:red"> </td>
            </tr>
           
            <tr>
              <td colspan="3" align="right"><input id="searchs" name="search" type="submit" value="查 詢"/>              </td>
              <td  ><input id="down"   name="downs" type="button" value="個人導出"><input id="down_all" name="downs_all" type="button" value="全部導出"><input id="down_fad" name="downs_fad" type="button" value="For HRD"></td>
            </tr>
    </table>
    <table width="100%"  border="1" cellpadding="0" cellspacing="0" class="table-ss" id="jqajax" >
      <tr id="title" style="background-color:silver;">
        <td   height="23" align="center"><b>Staff Code</b></td>
        <td    align="center"><b>Name</b></td>
        <td   align="center"><b>Dept</b></td>
        <td   align="center"><strong>Grade</strong></td>
        <td   align="center"><strong>Plan</strong></td>
        <td   align="center"><strong>Type</strong></td>
        <td   align="center"><strong>MedicalDate</strong></td>
        <td   align="center"><strong>Medical_fee </strong></td>
        <td    align="center"><strong>Medical entitle</strong></td>
        <td   align="center"><strong>Month Entitled</strong></td>
        <td    align="center"><strong>No.of Terms</strong></td>
        
        <td   align="center"><strong>Normal</strong></td>
        <td   align="center"><strong>Special</strong></td>
        <td   align="center"><strong>Dental</strong></td>
        <td   align="center"><strong>Dental-Regular</strong></td>
        
        <td  align="center"><strong>return Oraginal </strong></td>
        <td   align="center"><strong>upd_Date</strong></td>
        <td   align="center"><strong>operat</strong></td>
      </tr>
    </table>
 <div align="center" class="page_and_btn" style="display: none;" >
			<table class="main_table" width="100%" border="0" cellpadding="0"
				cellspacing="0" id="select_table">
				<tr class="main_head">
					<td colspan="6" align="center">
						<a id="first" href="javascript:void(0);">首页</a>
						<a id="pre" href="javascript:void(0);">上一页</a> 总共
						<SPAN style="color: red;" id="total">
						</SPAN>页
						<a id="next" href="javascript:void(0);">下一页</a>
						<a id="end" href="javascript:void(0);">尾页</a>
					</td>
				</tr>
			</table>
		</div>
  </div>
  </div>
 
  
    
      
   
 
     
<div id="modify" >
  <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="table-ss">
  <tr><td height="5%" colspan="6" align="center"><strong><font color="blue" size="+4">Medical Claim for Staff </font></strong></td>
  </tr>
  
  <tr height="7%" align="center"><!-- 第一行 -->
  <td width="40%"  align="center">Staff Code:<input  id="staffcode" class="txt_readonly" onblur="clear_txt();selectStaff();" type="text"/></td >
  <td width="20%"></td>
  <td width="40%" align="left"> Type:<select id="Medical_type" onchange="changeType();"><option value="">请选择</option></select> </td>
  </tr>
  
  <tr height="7%" align="center"><!-- 第二行 -->
  <td width="40%" align="center">Medical Date:<input  id="MedicalDate" class="txts" type="text"   onClick="javascript:$('#MedicalFee,#MedicalDate').val(''); Calendar('MedicalDate');" /></td >
  <td width="20%"></td>
  <td width="40%" align="left">Medical Fee:
  <input type="text" class="txts" id="MedicalFee" onKeyPress="check2(this);" onKeyUp="check2(this);"/></td>
  </tr>
  
  <tr height="7%" align="center"><!-- 第三行 -->
  <td width="40%" align="center">Staff Name:<input id="staffname" class="txt_readonly" type="text"/></td >
  <td width="20%"></td>
  <td width="40%" align="left">Company:
  <input id="company" class="txt_readonly" type="text"/></td>
  </tr>
  
 
  <tr height="6%" align="center"><!-- 第四行 -->
  <td width="40%" align="center">Department:<input id="deptid" class="txt_readonly" type="text"/></td >
  <td width="20%"></td>
  <td width="40%" align="left">Grade:
  <input id="grade" class="txt_readonly" type="text"/></td>
  </tr>
  
  <tr height="6%" align="center"><!-- 第五行 -->
  <td width="40%" align="center">Amount Entitle:<input id="entitle" class="txt_readonly"  onblur="check2(this);"  type="text"/></td >
  <td width="20%"></td>
  <td width="40%"  align="left" >Return Oraginal:
  <input  type="checkBox" id="return_oraginal" onclick="choose(this);" value="N"/></td>
  </tr>
  
   <tr height="7%"><!-- 第六行 -->
    <td height="20" colspan="5"><div align="center" class="txt"><strong id="mymessage">--------------------------------</strong></div></td>
  </tr>
  
  <tr height="6%" align="center"><!-- 第七行 -->
  <td width="40%" align="center">Plan:<input id="pplan" class="txt_readonly" type="text"/></td >
  <td width="20%"></td>
  <td width="40%" align="left">Package:
  <input id="package" class="txt_readonly" type="text"/></td>
  </tr>
  
   <tr height="6%" align="center"><!-- 第八行 -->
  <td width="40%" align="center">Max amount:<input id="max_amount" class="txt_readonly" type="text"/></td >
  <td width="20%"></td>
  <td width="40%" align="left">Consultlting Fee:<input id="max_amount_money" class="txt_readonly" type="text"/></td>
  </tr>
   <tr height="6%" align="center"><!-- 第九行 -->
  <td width="40%" align="center">Amount of Normal:<input id="Normal_Number" class="txt_readonly" type="text" value="0"/></td >
  <td width="20%"></td>
  <td width="40%" align="left">Amount of Special:<input id="Special_Number" class="txt_readonly" type="text" value="0"/></td>
  </tr>
   <tr height="6%" align="center"><!-- 第九行 -->
  <td width="40%" align="center">Amount of Regular:<input id="Oral_Number" class="txt_readonly" type="text" value="0"/></td >
  <td width="20%"></td>
  <td width="40%" align="left">Amount of Dental:<input id="Dental_Number" class="txt_readonly" type="text" value="0"/></td>
  </tr>
  
  <tr height="1%"><td></td><td><span style="display: none;">Subject:</span><select id="subject" style="display: none;">
  <option value="">请选择</option>
  <option value="You medical claim was rejected">You medical claim was rejected</option>
  <option value=" Medical claims notice"> Medical claims notice</option>
  </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input  type="hidden" value="reject" onclick="rejected();"/><input type="button" value="Save" id="save" onclick="save();"/></td><td><input onclick="$('#modify').hide();$('#select').show();" type="button" value="back"/></td></tr>
 
 <tr></tr>
  </table>
  </div>

  </body>
</html>
