<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Return Mail Report</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="css/pager.css">

  </head>
  
<body >
<div class="cont-info">
	<div class="info-search">
		<table>
			<tr>
				<td class="tagName">ClientID:</td>
				<td class="tagCont">
					<input name="text" type="text" id="clientId" />
				</td>
				<td class="tagName">Repeat Number:</td>
				<td class="tagCont">
					<select id="repeatNumber">
		              	<option>1</option>
		              	<option>2</option>
		              	<option selected="selected">3</option>
		              	<option>4</option>
		              	<option>5</option>
		              	<option>6</option>
		              	<option>7</option>
		              	<option>8</option>
		              	<option>9</option>
		              	<option>10</option>
		        	</select>
				</td>
			</tr>
			<tr>
				<td class="tagName" style="vertical-align: middle; padding-bottom: 0px;">friendly reminder:</td>
				<td class="tagCont" > 
					Please Search before you export.
				</td>
				<td class="tagCont" colspan="2">
					<c:if test='${roleObj.search==1}'>
						<a class="btn" name="search" onclick="select_pager(1)">
							<i class="icon-search"></i>Search
						</a>
					</c:if>
					<c:if test='${roleObj.export==1}'>
						<a class="btn" id="export" disabled="disabled" onclick="reportReturnMail()">
							<i class="icon-download"></i>Export
						</a>
					</c:if>
					<c:if test='${roleObj.export==1}'>
						<a class="btn" onclick="javascript:window.open('<%=basePath%>../ConvoyUtil/downloadFileServlet?filename=client_address_log.xlsx&filepath=template/client_address_log.xlsx')">Excel Template</a>
					</c:if>	
					<c:if test='${roleObj.upd==1}'>
	        			<a class="btn" id="upload_btn"><i class="icon-upload-alt"></i>Upload File</a>
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div class="info-table" >
		<table id="jqajax">
			<thead id="title">
			<tr>
				<th class="width_10">Num</th>
				<th class="width_20">ClientID</th>
				<th class="width_30">Client Name</th>
				<th class="width_20">Address Change Date</th>
				<th class="width_20">Repeat Number</th>
			</tr>
			</thead>
		
		</table>
		<div id="test" class="pageinfo" style="float: right;"></div>
	</div>
</div>  
  <script type="text/javascript" src="css/jquery-pager-1.0.js"></script>
<script type="text/javascript" >

  var pagenow=1;
  var totalpage=0;
  var email=null;
  var basepath = '<%=basePath%>';
  $(function(){
	  //$("#start_date,#end_date").val(getMonthBeforeDay(new Date()));
	  $(".inputstyle,.field input").attr("disabled",true);
	  
	  
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
	
	
	
	$(".paymentAmount").click(function(){
		if($(".paymentAmount:checked").length>0){
			$(".paymentAmount").attr("checked",false);
			this.checked=true;
		}else{
			this.checked=true;
		}
		$("#amount").val(this.value);
		
	});
	
	
	
	
	
	
	
	
	
  });
 function select_pager(pagenow){
		$.ajax({
			url:"/ConvoyUtil/ReturnMailServlet",
			type:"post",
			data:{"method":"check","clientId":$("#clientId").val(),"repeatNumber":$("#repeatNumber").val(),"pagenow":pagenow},
			beforeSend:function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
			success:function(date){
				var html="";
				$("#jqajax tr.field").remove();
				if(date=="error"){
					$("#export").attr("disabled","disabled");
				}else if(date==null){
					$("#export").attr("disabled","disabled");
				}else{
					var dataRole=eval(date);
					if(dataRole[2]>0){
						$("#export").removeAttr("disabled");
						var edit=null;
						var emailstatus=null;
						for(var i=0;i<dataRole[0].length;i++){
							
							html+="<tr class='field' >" +
							"<td align='center'>"+((pagenow-1)*15+i+1)+"</td>" +
							"<td align='center'>"+dataRole[0][i].clientID+"</td>" +
							"<td align='center'><div style='text-align:left;width:250px; '>"+dataRole[0][i].unknown+"</div></td>" +
							"<td align='center'>"+dataRole[0][i].scanningDate+"</td>" +
							"<td align='center'>"+dataRole[0][i].reason+"</td>" +
							"</tr>";
						}
						html+="<tr class='field' ><td align='right' colspan='5' style='vertical-align: middle;color:blue;padding-right:20px;'><strong> Total Repeat Number："+dataRole[2]+" </strong></td></tr>";
					}else{
						$("#export").attr("disabled",true);
						html+="<tr class='field'><td colspan='5' align='center'> NO DATA !</td></tr>";
					}
				}
				InitPager(dataRole[2],dataRole[1]);
				$("#jqajax").append(html);
				$("#jqajax tr.field:odd").css("backgroundColor","#f6f9fc");
				$("#jqajax tr.field:even").css("backgroundColor","#fff");
		 
			},error:function(){
				alert("NetWord Connection Error!");
				return false;
			}
		});
}
  
    /**
       * 分页活动方法
       * @param {Object} currt
       * @param {Object} total
       */
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
				$("#curretPage").empty().append(currt);
				$("#totalPage").empty().append(total);
	}
        
  
       function reportReturnMail(){
    	   if($("#start_date").val()==""){
    		   alert("开始日期不能为空!");
    		   return false;
    	   }else if($("#end_date").val()==""){
    		   alert("结束日期不能为空!");
    		   return false;
    	   }else{
    		   
    		   location="<%=basePath%>../ConvoyUtil/ReturnMailServlet?method=report&clientId="+$("#clientId").val()
    		                +"&repeatNumber="+$("#repeatNumber").val();
    			$("#export").attr("disabled","disabled");
    	   }
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
        
        
        
        
         function fileupload(){
               if($("#up").val()==""){ 
                  alert("请选择有效文件"); 
                   return false;  
               }  
            $.ajaxFileUpload({  
                       url:'/ConvoyUtil/UploadFileServelet', 
                       secureuri:false,  
                       fileElementId:'up',  
                       dataType: 'text/html',
                       success: function (data) {
            			var dataRole=eval(data);
            			if(dataRole[0]=="success"){
            				//alert("上传成功,路径："+dataRole[1]);
            				handleExcel(dataRole[1]);
            			}else{
            				alert("上传失败,原因："+dataRole[1]);
            			}
                       },error: function (data, status, e){  
                    	   fileName=null;
                           alert("网络连接失败,请稍后重试...");  
                 			return false;
                       }  
                   });  
           }  
        
         
         
         function handleExcel(uploadFile){
        	 
         $.ajax({
			type: "post",
			url:"/ConvoyUtil/UploadExcelFileServlet",
			//data: {'up':getPath($("#up"))},
			data: {'uploadFile':uploadFile,"user":"${adminUsername}"},
			success:function(date){
				if(date==null){
					
				}else{
					var dataRole=eval(date);
					alert("本次耗时"+dataRole[0]+"\r\n结果："+dataRole[1]);
				}
			},error: function (data, status, e){  
                  alert("网络连接失败,请稍后重试...");  
                  return false;
            }  
		 });
         }
        $('#upload_btn').live('click',function(){
	 		var data = 'handleExcel';
	 		var url = basepath + "publicUpload.jsp";
			uploadlhg(440,300,url,data);
	 	});
        
  </script>
  </body>
</html>
