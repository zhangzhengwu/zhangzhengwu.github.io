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
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">
	<script src="css/Util.js" language="JavaScript"></script>
	

   
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
  var email=null;
  var updateDate=null;
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
  
  
  
  
  $(function(){
	 	document.onkeydown=BackSpace;
	 $("#modify [class='txt_readonly']").attr("readonly","readonly");
   	 $("#content").css("top",$("#Medical").height());
	 $("#start_date").val(CurentTime);
     $("#end_date").val(CurentTime);
      $("#staffcodes").val("${convoy_username}");
    
   //  alert($("#select").width()+"--"+$("#right_div",parent.document).width());
    // alert($("#left_div",parent.document).width()+"--"+$("#right_div",parent.document).width()+"--"+$(parent.document).width());
     $(window).width($(parent.document).width());
      $("#select").width($("body").width());
     
     
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
	 
	/******************************************************Down END***********************************/
	
	/**************************************************selects Click*****************************************************/
	
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

	
  });
   /***********************************************select function************************************/
  
	function selects(pagenow){
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
				beforeSend: function(){
					parent.showLoad();
				},
				complete: function(){
					parent.closeLoad();
				},
				success:function(date){
				var dataRole=eval(date);
	
					var totals=0;
					var html="";
					var SPNumber=0;
					var GPNumber=0;
					var Regular=0;
					var DentalNumber=0;
					$("tr[id='select']").remove();
					downs=null;
					if(dataRole[3]>0){
					total=dataRole[3];
					pagenow =dataRole[2];
				    totalpage=dataRole[1];
					downs=dataRole[0];
						for(var i=0;i<dataRole[0].length;i++){
							if(dataRole[0][i].type=="GP"){
								GPNumber++;
							}else if(dataRole[0][i].type=="SP"){
								SPNumber++;
							}else if(dataRole[0][i].type=="Dental-Regular"){
								Regular++;
							}else{
								DentalNumber++;
							}
							html+="<tr id='select' ><td align='center'>"+dataRole[0][i].staffcode+"</td><td align='center'>"
							+dataRole[0][i].name +"</td><td align='center'>"+dataRole[0][i].medical_date+"</td><td align='center'>"+dataRole[0][i].medical_fee+"</td><td align='center'>"
							+dataRole[0][i].amount +"</td><td align='center'>"+dataRole[0][i].medical_month+"</td><td align='center'>"+dataRole[0][i].term +"</td><td align='center'>" 
							+dataRole[0][i].type+"</td><td align='center'>"+dataRole[0][i].return_oraginal +"</td><td align='center'>"+dataRole[0][i].add_Date+"</td></tr>";		 
						}
						html+="<tr id='select' ><td colspan='7' align='right'>GP:</td><td>"+GPNumber+"</td><td></td><td></td></tr>";
						html+="<tr id='select' ><td colspan='7' align='right'>SP:</td><td>"+SPNumber+"</td><td></td><td></td></tr>";
						html+="<tr id='select' ><td colspan='7' align='right'>Dental-Regular:</td><td>"+Regular+"</td><td></td><td></td></tr>";
						html+="<tr id='select' ><td colspan='7' align='right'>Dental:</td><td>"+DentalNumber+"</td><td></td><td></td></tr>";
						html+="<tr id='select' ><td  align='right' ><span style='color:blue'>Remark:</span></td><td colspan='7' ><span style='color:blue'>1.“No. of Term” is only including the data from 2013.</span></td><td></td><td></td></tr>";
						html+="<tr id='select' ><td align='right' ></td><td colspan='7' ><span style='color:blue'>2.“GP” is General Practitioner, “SP” is Specialist Practitioner.</span></td><td></td><td></td></tr>";
			$(".page_and_btn").show();
					   	    $("#total").empty().append(dataRole[2]+"/"+dataRole[1]);
					}
					else{
						html+="<tr id='select'><td colspan='18' align='center'>"+"Sorry, there is no matching record."+"</td></tr>";
					 $(".page_and_btn").hide();
					}
						 $("#jqajax:last").append(html);
					 	 $("tr[id='select']:odd").css("background","#F0F0F0");
		                 $("tr[id='select']:even").css("background","#COCOCO");
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
				email=downs[sid].email;
				updateDate=downs[sid].add_Date;
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
			data:{"staffcode":downs[d].staffcode,"upd_date":downs[d].upd_Date,"type":downs[d].type,"medical_Dental":downs[d].medical_Dental,"term":downs[d].term},
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
				"add_Date":updateDate},
		success:function(date){
				alert(date);
				 $("#modify").hide();
			 $("#select").show();
				$("#searchs").click();
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
		if($.trim(reType)==$.trim($("#package").val().substring($("#package").val().lastIndexOf('-')+1))&&reMedicalDate==$.trim($("#MedicalDate").val())&&reMedicalFee==$.trim($("#MedicalFee").val())){
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
   
  </script>
<body> 
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">Start Date：</td>
				<td class="tagCont">
					<input name="text" type="text" id="start_date" onClick="return Calendar('start_date')" readonly="readonly" />
					<i class="icon-trash icon-large i-trash" id="clear1" align="middle" onclick="javascript:$('#start_date').val('');"></i>
				</td>
				<td class="tagName">End Date：</td>
				<td class="tagCont">
					<input id="end_date" type="text" name="it" readonly="readonly" onClick="return Calendar('end_date')" />
					<i class="icon-trash icon-large i-trash" id="clear2" align="middle" onclick="javascript:$('#end_date').val('');"></i>
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont" colspan="3" style="text-align: center;">
					<a class="btn" id="searchs" name="search">
						<i class="icon-search"></i>
						Search
					</a>
					<a class="btn" id="down_all" name="downs_all">
						<i class="icon-search"></i>
						Export
					</a>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table">
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_">Staff Code</th>
				<th class="width_">Name</th>
				<th class="width_">Medical Date</th>
				<th class="width_">Medical Fee</th>
				<th class="width_">Medical Entitle</th>
				<th class="width_">Month Entitled</th>
				<th class="width_">No.of Terms</th>
				<th class="width_">Claim Type</th>
				<th class="width_">Return Original?</th>
				<th class="width_">Submit Date</th>
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
						</SPAN>page
						<a id="next" href="javascript:void(0);">Next Page</a>
						<a id="end" href="javascript:void(0);">Last Page</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>
