<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
  <base href="<%=basePath %>"></base>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
		<link rel="stylesheet" href="<%=basePath%>css/jquery.mCustomScrollbar.css" />
	
    <title>Staff New Marketing Premium Form</title>

<script src="<%=basePath%>css/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript">
	    var d ;
	    var overflow_quantity=null;//超出库存对象
	    var ishuzu=new Array();
	    var stock=null;
	    var param;
	    /*加载location - 06-10 Wilson 将location换成下拉*/
		function getlocation(){
			$.ajax({
				type:"post",
				//url:"QueryLocationServlet",
				//data:null,
				url: "common/CommonReaderServlet",
				data:{"method":"getlocation"},
				success:function(date){
					var location=eval(date);
					var html="";
					$("#locationid").empty();
					if(location.length>0){
						html+="<option value='' >Please Select Location</option>";
						for(var i=0;i<location.length;i++){
							html+="<option value='"+location[i].realName+"' >"+location[i].name+"</option>";
						}
					}else{
						html+="<option value=''>loading error</option>";
					}
					$("#locationid").append(html);
					//$("#locationid").val("Y");//默认选择@Convoy
				},error:function(){
					alert("Network connection failure, please return to the login page to log in!");
					return false;
				}
			});
		}
   
    /**
     * 产品加载
     * 
     * */
  var prices="";
  function selected(index,obj){
	  var result = checkDouble(obj);
	  var cbox = $(".aaa"+index+" >td").eq(3).children();
 	  
	  $('input[id=txtPrice]').not($(obj)).attr("disabled",true);
	  onas(index,cbox,1);
  }
  $(function(){
	  function setScrollTable(){
		var trLen = $('#leftTable tr').length;
		var lHeight = 29, height = 0;
		if(trLen >= 10){
			$('#scrollTable').height(lHeight * 9);
			$("#scrollTable").mCustomScrollbar({
		        axis:"y", // vertical and horizontal scrollbar
		        theme:'minimal-dark'
		    });
		}else{
			height = lHeight * trLen;
			$('#scrollTable').height(height);
		}
		
		
	}
	  
	  getlocation();
    	var econvoy=new Array();
			econvoy="${Econvoy}".split("-");
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
			
			$("#staffcode").val(econvoy[0]);
			$("#staffname").val(econvoy[1]);
	  	//	$("#locationid").val(econvoy[3].toUpperCase());
	  		
	  
	  $.ajax({
			type: "post",
			url: "EPaymentServlet",
			data:{"method":"query"},
			beforeSend: function(){
	    		parent.showLoad();
	    	},
	    	complete: function(){
	    		parent.closeLoad();
	    	},
			success: function(date){
			 var dataRole=eval(date);
			 var htmls="";
		         for(var i=0;i<dataRole.length;i++){
		        	 var edit="";
		        	 if(dataRole[i].productname.indexOf('(Free Text)')>-1){
		        		 edit="<input style='border:0px;width:200px;' type='text' value='"+dataRole[i].productname.replace("(Free Text)","")+"'/>";
		        	 }else{
		        		 edit="<input style='border:0px;width:200px;' readonly='readonly' type='text' value='"+dataRole[i].productname+"'/>";
		        	 }
		          	htmls+="<tr class='aaa"+i+"' height='25px'><td class='width_15'>"+dataRole[i].productcode+"</td><td align='left' class='width_45'>"+edit+"</td><td class='width_30'>" +
		          	"<input type='text' class='txt' id='txtPrice' onkeyup='selected("+i+",this)' />"
		          	+"</td><td class='width_10'><input type='checkbox' value='"+dataRole[i].productcode+"' id='check"+i+"'  onclick='onas("+i+",this,2)'/>"+"</td></td></tr>";
		          }
		        
			   	  $("#leftTable").append(htmls);
			   	  setScrollTable();
			}
		});
	  
	 		
  });
	 
 	$(function(){
		$("input.txt").live("blur",function(){
			if(this.value==""){
				if($(this).parent().next().children().attr("checked"))
				$(this).parent().next().children().attr("checked",false);
				 $("input[id^='check']").not($(this).parent().next().children()).attr("disabled",false);
				$("#totalprice").empty().append("HK$ "+0);
			}
		 });
  	});
  
	 /**
	  * buy统计
	  * */
	 var a=0;
	 function onas(i,obj,way){
		switch(way){
			case 1:
				if($(".aaa"+i+" >td").eq(2).children().val()==""){
					 $(obj).attr('checked',false);
					 $(".aaa"+i+" >td").eq(2).children().focus();
					 $("input[id^='check']").not($(obj)).attr("disabled",false);
					 $('input[id=txtPrice]').attr('disabled',false);
				 }else{
					 a=Number($(".aaa"+i+" >td").eq(2).children().val());
					 $(obj).attr('checked',true);
			 		 $("input[id^='check']").not($(obj)).attr("disabled",true);
					 $("#totalprice").empty().append("HK$ "+a);
				 }
				break;
			case 2:
				if($(".aaa"+i+" >td").eq(2).children().val()==""){
					 $(obj).attr('checked',false);
					 $(".aaa"+i+" >td").eq(2).children().focus();
				 }else{
					 $("input[id^='check']").attr("disabled",false);
					 $(".aaa"+i+" >td").eq(2).children().val("");
					 $('input[id=txtPrice]').attr("disabled",false);
					 $("#totalprice").empty().append("HK$ "+0);
				 }
				break;
			default: break;
		}
		 
	 }
	
	/**
	  * 保存
	  **/
  	 function save(){
	  var string="";
  			if($("#staffcode").val()==""){
				error("Please input Staff Code!");
				$("#staffcode").focus();
				return false;
			}
			if($("#staffname").val()==""){
				error("Please input Staff Name!");
				$("#staffname").focus();
				return false;
			}
	
			if($("#locationid").val()==""){
				error("Please select location!");
				$("#locationid").focus();
				return false;
			}
		
  		 
		 $("#leftTable [type='checkbox']:checked").each(function(){
			 //string+=this.value+":"+$(this).parent().parent().children().eq(1).text()+":"+$(this).parent().parent().children().eq(2).children().val()+"::";
			string+=this.value+":"+$(this).parent().parent().children().eq(1).children().val()+":"+$(this).parent().parent().children().eq(2).children().val()+"::";
			
		 });
		// alert(string);    //string是select产品时相加得到的字符串 
		if(string==""){
			error("No choice of products！");	
			return false;
		}else{
			if(confirm("Sure to Submit?")){
			  $.ajax({
				type: "post",
				url: "EPaymentServlet",
				data:{"staffcode":$("#staffcode").val(),"staffname":$("#staffname").val(),"location":$("#locationid").val(),"type":"Staff","status":"Submitted",
				  "string":string,"method":"save"},
				success: function(date){
					  if(date == 'Success!'){
						  successAlert(date,function(){
							  window.location.href = window.location.href;
						  });
					  }else{
						  errorAlert('error');
						  return false;
					  }
				}
			  });
			}
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
                  complete: function(){
            	 	$('#filecont').empty();
                  },
                  success: function (data) {
                     if(data!="error"){
	                     fileName=data;
	                     //alert("上传成功，上传路径为："+fileName);
	                     uploadConsultant(fileName);
	                 }
       	 		    else{
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
				data: {'uploadfile':fileName,'filename':"epayment"},
				success:function(date){
					if(date != "null"){
						var dataRole=eval(date);
						//alert("導入數據成功,此次操作共耗时"+dataRole[0] +"分鐘"+dataRole[1]+"條數據");
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
  </script> 
  <style type="text/css">
  .upload-item{
  	border: 1px solid #bbb;
  	padding: 6px 12px;
  }
  .upload-item .filecont{
  	padding: 0px 12px;
  }
  .info-form{
  	position: relative;
  }
  .form-top{
  	top: 0px;
  	left: 0px;
  	width: 100%;
  }
  .scrollTable{
  	margin-top: -1px;
  }
  .form-bottom{
  	margin-top: -1px;
  	width: 100%;
  }
  .form-bottom{
  	border-top: 1px solid #bbb!important;
  }
  </style>
</head>
<body>
<div class="e-container">
	<div class="info-form" id="topTable">
		<h4>E-Payment Request</h4>
		<table>
			<tr>
				<td class="tagName">Consultant/Staff Code</td>
				<td class="tagCont">
					<input type="text" class="inputstyle" id="staffcode"  value="${convoy_username}" />
			    	<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Name</td>
				<td class="tagCont">
					<input type="text" class="inputstyle"  id="staffname" />
			    	<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Location</td>
				<td class="tagCont">
					<select name="locationid" id="locationid"></select>
		            <span class="redspan">*</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-form" id="divId">
		<h4>E-Payment Products</h4>
		<div class="form-top">
			<table>
				<tr class="">
					<td class="width_15">Product Code</td>
					<td class="width_45">Englishname</td>
					<td class="width_30">Price</td>
					<td class="width_10">Select?</td>
				</tr>
			</table>
		</div>
		<div id="scrollTable" class="scrollTable">
			<table id='leftTable'>
			</table>
		</div>
		<div class="form-bottom"> 
			<strong>Total:<span id="totalprice">HK$ 0</span></strong>
		</div>
		<br/>
		<div class="upload-item" id="setFileUp">
			<script type="text/javascript" src="plug/js/fileUp.js"></script>
			<script type="text/javascript">
				$('#setFileUp').fileUp({
					fileId : 'filePaths',
					fileName : 'filePaths',
					upbtnId : 'inputs',
					width : '100%',
					upload :  function(){
						fileupload();
					}
				});
			</script>
		</div>
	</div>
	<div class="info-form" id="divIdSelected" style="display: none;">
		<h4>Marketing Primium Shopping Cart</h4>
		<table id="sale_table">
			<tr>
				<td class="width_">Product Code</td>
				<td class="width_">English Name</td>
				<td class="width_">Calculation</td>
				<td class="width_">Price</td>
				<td class="width_">Unit</td>
				<td class="width_">Quantity</td>
				<td class="width_">Total Price</td>
				<td class="width_">Buy?</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
		<button class="btn" id="save" onClick="save()">Submit</button>
		<button class="btn" id="cancel" onClick="javascript:if(confirm('Sure to Reset?')){location.reload();}">Reset</button>
	</div>
</div>
</body>
</html>
