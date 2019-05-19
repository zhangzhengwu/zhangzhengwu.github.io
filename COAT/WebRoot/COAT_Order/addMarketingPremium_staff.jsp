<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
  <base href="<%=basePath %>"></base>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<script type="text/javascript" src="css/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="./plug/lhgdialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="./plug/js/Common.js"></script>
	<script language="javascript" type="text/javascript" src="css/date.js"></script>
	<script language="javascript" type="text/javascript" src="css/Util.js"></script>
	<link rel="stylesheet" href="./plug/css/bootstrap.min.css">
	<link rel="stylesheet"href="./plug/css/font-awesome.min.css">
	<!--[if lte IE 7]> 
	<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
	<![endif]--> 
		
	<link rel="stylesheet" href="./css/layout.css">
	<link rel="stylesheet" href="./plug/css/site.css">
	
    <title>Staff New Marketing Premium Form</title>
    
 
    
	  <script type="text/javascript">
	    var d ;
	    var overflow_quantity=null;//超出库存对象
	    var ishuzu=new Array();
	    var stock=null;
	    /*加载location - 06-10 Wilson 将location换成下拉*/
		function getlocation(){
			$.ajax({
				type:"post",
				//url:"QueryLocationServlet",
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
					errorAlert("Network connection failure, please return to the login page to log in!",function(){
					});
					return false;
				}
			});
		}
    	/*取消订单*/
		function cancelOrder(){
			if(confirm("Sure to Reset?")){
				location.reload();
			}
		}
	    /**
	     * 产品加载
	     * 
	     * */
	  	function loadValue(price){
			$.ajax({
				type: "post",
				url: "QueryMarketingPremiumServlet",
				data:{"BLBZ":"S"},
				success: function(date){
				var dd=eval(date);
				d=eval(dd);
				var html_left="";
				var html_right="";
				var prices="";
				$(".product,.product").remove();
					for(var i=0;i<d.length;i++){
						/**if(price=="club")
							prices=d[i].c_clubprice;
						else
							prices=d[i].unitprice;**/
							
							if(d[i].specialprice==""){
								prices="-";
							}else{
								prices="HK$ "+d[i].specialprice;
							}
							
						html_left+="<tr id='left"+i+"' class='product'><td align='left'>"+d[i].procode+"</td><td align='left'>"
						+d[i].englishname+"</td><td>"
						+"HK$"+d[i].unitprice+"</td><td>"+"HK$"+d[i].c_clubprice+"</td><td>"+prices+"</td><td>"+d[i].unit
						+"</td><td ><img src='images/jian.png' class='images' onclick='deleteQuantity(this,"+i+");'/>&nbsp;" +
						"<input onkeyup='checknumber("+i+",this);' onpaste='javascript: return false;'  onblur='NumberChange("+i+",this);'   type='text' maxlength='4' id='id"+i+"' class='inputNumber' value='0'/>&nbsp;" +
						"<img src='images/jia.png' class='images' onclick='addQuantity(this,"+i+");'/></td>"+
						"<td><input type='checkbox'  id='check"+i+"' onclick=\"onas(this,"+i+",'left');\"/></td></tr>";
								if(parseFloat(d[i].quantity)<1){
									ishuzu.push(i);
								} 
							}
						$("#leftTable").append(html_left);
						/**
						for(var i=1;i<d.length;i=i+2){
							if(price=="club")
							prices=d[i].c_clubprice;
						else
							prices=d[i].unitprice;
							html_right+="<tr id='right"+i+"' class='product'><td align='left'>"+d[i].procode+"</td><td align='left'>"
						+d[i].englishname+"</td><td>"
						+"HK$"+prices+"</td><td>"+d[i].unit
						+"</td><td ><img src='images/jian.png' class='images' onclick='deleteQuantity(this,"+i+");'/>&nbsp;" +
						"<input onkeyup='checknumber("+i+",this);'  onpaste='javascript: return false;'  onblur='NumberChange("+i+",this);' type='text' maxlength='3' id='id"+i+"' class='inputNumber' value='1'/>&nbsp;" +
						"<img src='images/jia.png' class='images' onclick='addQuantity(this,"+i+");'/></td>"+
						"<td><input type='checkbox'  id='check"+i+"' onclick=\"onas(this,"+i+",'right');\"/></td></tr>";
							if(parseFloat(d[i].quantity)<1){
										ishuzu.push(i);
									} 
						}
						
						 $("#rightTable").append(html_right);
						 
						 **/
						 $.each(ishuzu,function(key,val){//处理库存不足的显示问题
							 $("#left"+val+" *,#right"+val+" *").attr("disabled",true);
							 $("#id"+val+"").val(0);
						 });
				}
			});
			
		}
	    
	    function checknumber(n,obj){
	    	var vals=obj.value.replace(/[^\d]/g,"");
	    	if(vals=="")
	    		$("[id='id"+n+"']").val(0);
	    	else
	    		$("[id='id"+n+"']").val(parseFloat(vals));
	    }
	    
	    
	    /**
		function againTable(r,i,name,quantity){
			
			if(parseFloat($("#id"+i+"").val())==0){
				$(r).attr("checked","");
				return false;
			}else{
				 var html ="<tr id='bottom"+i+"'  >";
				 var allprice=0;
			 $("#right"+i+" td").each(function(n){
				 if(n<4){
						 if(n==2){
							  html+="<td id='bottom_price"+i+"' align='center'>"+$(this).text()+"</td>";
							 allprice=parseFloat($(this).text());
						 }else{
							  html+="<td align='center'>"+$(this).text()+"</td>";
						 }
					}else if(n==4){
					 html+="<td align='center'><img src='images/jian.png' class='images' onclick='deleteQuantity(this,"+i+");'/>" +
					 "<input onkeyup='checknumber("+i+",this);'  onpaste='javascript: return false;'  onblur='NumberChange("+i+",this);' type='text' maxlength='3' id='id"+i+"' style='width: 24px;border-left-width: 0px;border-right-width: 0px;border-top-width: 0px;' value="+$("#id"+i+"").val()+" />" +
					 "<img src='images/jia.png' class='images' onclick='addQuantity(this,"+i+",\""+d[i].quantity+"\");'/></td>";
					 allprice=allprice*parseFloat($("#id"+i+"").val());
							}else if(n==5){
								html+="<td id='bottom_allprice"+i+"' align='center'>"+allprice+"</td><td align='center'><input onclick=\"overas('right',"+i+",this);\" type='checkBox' checked/></td>";
							} 		
			 });
			 
			// html+="<td>"+$("#id"+i+"").val()+"</td></tr>";
			 $("#sale_table").append(html);
			  $("#right"+i+"").attr("disabled","disabled");
			  }
		}
		**/
		
		
		
			function onas(r,i,name){
			if(!r.checked){
					if(confirm("Sure to remove?")){
						$("#bottom"+i+"").remove();
						 order_price();
						return false;
					}else{
						r.checked=true;
					}
				}
			if(parseFloat($("#id"+i+"").val())==0){
				r.checked=false;
				$("#id"+i+"").focus().select();
				return false;
			}else{
				$("#bottom"+i+"").remove();
					 var html ="<tr id='bottom"+i+"' >";
					 var allprice=0;
					 var calculation="unit";
					 if(d[i].specialprice==""){
						 if($("#c-club").val()=="Y"){
							 allprice=parseFloat(d[i].c_clubprice);
							 calculation="c-club";
						 }else{
							 allprice=parseFloat(d[i].unitprice);
						 }
					 }else{
						 calculation="special";
						 allprice=parseFloat(d[i].specialprice);
					 }
						 $("#"+name+""+i+" td").each(function(n){
							 if(n<4){
								 if(n==2){
									  html+="<td>"+calculation+"</td><td id='bottom_price"+i+"' align='center'>HK$"+allprice+"</td>";
									// allprice=parseFloat($(this).text().replace("HK$",""));
								 }else if(n==3){
									  html+="<td align='center'>"+d[i].unit+"</td>";
								 }else{
									  html+="<td align='center'>"+$(this).text()+"</td>";
								 }
							}else if(n==4){
								 html+="<td align='center'><img src='images/jian.png' class='images' onclick='deleteQuantity(this,"+i+");'/>" +
								 "<input class='inputNumber' onkeyup='checknumber("+i+",this);'  onpaste='javascript: return false;'  onblur='NumberChange("+i+",this);' type='text' maxlength='4' id='id"+i+"' class='inputNumber' value="+$("#id"+i+"").val()+" />" +
								 "<img src='images/jia.png' class='images' onclick='addQuantity(this,"+i+");'/></td>";
								 allprice=allprice*parseFloat($("#id"+i+"").val());
							}else if(n==5){
								html+="<td id='bottom_allprice"+i+"' align='center' name='allPrice'>"+"HK$"+allprice+"</td><td align='center' id='td_check"+i+"'><input onclick=\"overas('left',"+i+",this);\" type='checkBox' checked/></td>";
							} 
						 });
				  $("#sale_table").append(html);
				  order_price();
		}
		}
		
		
		
		/*按减号所触发的函数*/
		function deleteQuantity(r,id){
			var val = parseFloat(document.getElementById("id"+id+"").value);
			if(val > 0){
				$("[id='id"+id+"']").val(val-1).blur();
				$("#bottom_allprice"+id+"").empty().append(parseFloat(val-1)*parseFloat($("#bottom_price"+id+"").text()));    
			}
			updprice(id);
		}
		/*按加号所触发的函数*/
		function addQuantity(r,id){
			var val = parseFloat(document.getElementById("id"+id+"").value);
			if(val < parseFloat(d[id].quantity)){
				$("[id='id"+id+"']").val(val+1).blur();
				$("#bottom_allprice"+id+"").empty().append(parseFloat(val+1)*parseFloat($("#bottom_price"+id+"").text()));   
			}else{
				//alert("Inventory is not enough!");
			}
			updprice(id);
		}
		/**
		 * 数量框blur事件
		 * @param {Object} i
		 * @param {Object} number
		 */
		function NumberChange(i,obj){
			//if(parseFloat(d[i].quantity)<parseFloat($(obj).val())){//判断库存量与购买数量的大小
			//	$("input[id='id"+i+"']").val(parseFloat(d[i].quantity));//购买数量大于库存，赋值最大库存量
			//}else{
				if(parseFloat($(obj).val())==0){//当购买库存为0时
					$("#bottom"+i+"").remove();
				$("#check"+i+"").attr("checked",false);
				}else{
				$("#check"+i+"").each(function(){
						$("#check"+i+"").attr("checked",true);
						 onas(this,i,'left');
					});
					$("[id='id"+i+"']").val($(obj).val());//否则去购买库存
				}
			//}
			
			updprice(i);
		}
		 /**
			 * 及时更新价格
			 * @param {Object} types
			 * @param {Object} i
			 * @param {Object} n
			 */
		 function updprice(i){
				$("#bottom_allprice"+i+"").empty().append("HK$"+parseFloat($("#id"+i+"").val()) * parseFloat($("#bottom_price"+i+"").text().replace("HK$",""))); 
			order_price();
			 }
			 
			/**
			 * 统计订单金额
			 * @param {Object} types
			 * @param {Object} i
			 * @param {Object} n
			 */
			 function order_price(){
				 	var total=0;
				$("[name='allPrice']").each(function(){
					total+=parseFloat($(this).text().replace("HK$",""));
				});
				$("#totalprice").empty().append("HK$ "+total);
			 }
	 
	
		/**
		 * 购物车Buy check框单击事件
		 * 
		 * 
		 * */
	function overas(types,i,n){
		if(n.checked){
		
		}else{
			if(confirm("Sure to remove?")){
			 $("#check"+i+"").attr("checked",false);
			 $("#bottom"+i+"").remove();
			 	 order_price();
			 	 }else{
			 		 n.checked=true;
			 	 }
		}
	}
	function resize(){
			//$("#order,#divId,#divIdSelected,.span_menu,#topTable").width($("body").width()-25);
		}
		
		$(function(){
			  //window.onresize=resize;
			document.onkeydown=BackSpace;
			//$("#order,#divId,#divIdSelected,.span_menu,#topTable").width($("body").width()-25);
			loadValue("unit");
			var econvoy=new Array();
			econvoy="${Econvoy}".split("-");
			//  0:staffcode,1:staffname,2:personType,3:location,4:isC-Club
			$("#staffcode").val(econvoy[0]);
			$("#staffname").val(econvoy[1]);
			//Wilson 06-10 先手动选
			//$("#location").val(econvoy[3]);
			getlocation();
			/**if(parseFloat(econvoy[4])==0){
				$("#c-club").attr("disabled",true).val("N").attr("checked",false);
			}else{
				$("#c-club").attr("disabled",true).val("Y").attr("checked",true);
					$("#payMethod").val("C-Club");
			}**/
		
			/**$.each(econvoy,function(i){
				alert(i+"-"+econvoy[i]);
			});
			**/
			
			/**
			$("#c-club").click(function(){//切换价格
				if(this.checked){
						this.value="Y";
						loadValue("club");
					}else{
						$(this).val("N");
						loadValue("unit");
					}$("#sale_table tr:gt(0)").remove();//清除购物车内容
			});
			**/
			function reValue(price){
	    		$.ajax({
				type: "post",
				url: "QueryMarketingPremiumServlet",
				data:{"BLBZ":"S"},
				success: function(date){
			 d=eval(date);
			 	if(confirm("The following product inventory shortage ["+stock+"] ")){
			 for(var i=0;i<d.length;i++){
				if(parseFloat(d[i].quantity)<1){
				 $("#left"+i+" *").attr("disabled",true);
				 $("[id='id"+i+"']").val(0);
				// $("#check"+i+"").removeAttr("checked");
				 $("#td_check"+i+"").empty().append("Insufficient inventory");
				 $("#bottom"+i+" *").css("backgroundColor","#ff4242");
				 
				
			}else{
				 if(parseFloat($("[id='id"+i+"']").val())>parseFloat(d[i].quantity)){
					  $("[id='id"+i+"']").val(parseFloat(d[i].quantity));
					   $("#bottom"+i+" *").css("backgroundColor","#1e8eff");
				 }
			}
				updprice(i);
			 }
			 }
			 
			// $("#sale_table input").blur();
				}
	    	});
	    }
			$("#save").click(function(){
				
				if($("#sale_table tr[class!='head_bottom']").length<=0){//判断购物车内是否没有数据
					error("No choice of products!");
					return false;
				}
				//06-10 Wilson 新增验证
				if($("#staffcode").val() == ""){
					error("Please Input Staff Code!");
					$("#staffcode").focus();
					return false;
				}
				if($("#staffname").val() == ""){
					error("Please Input Staff Name!");
					$("#staffname").focus();
					return false;
				}
				if($("#department").val() == ""){
					error("Please Input Department!");
					$("#department").focus();
					return false;
				}
				if($("#locationid").val() == ""){
					error("Please select location!");
					$("#locationid").focus();
					return false;
				}
				/**if($("#chequeid").val() == "" && $("#cashid").val() == ""){
					alert("Please input  Payment Method!");
					$("#cashid").focus();
					return false;
				}**/
				if(!confirm("Sure to Submit?")){
					return false;
				}
				var sale_record="";
				var totalMoney=0;
					$("#sale_table tr").each(function(){
						if($(this).attr("id")!=""){
							if($("#td_check"+$(this).attr("id").replace("bottom","")+"").text()!="Insufficient inventory"){
							$("#"+$(this).attr("id")+" td").each(function(n){
								 if(n==5){
										 $(this).each(function(){
												$(this).children().eq(1).each(function(){
													sale_record+=$(this).val()+",";
												});
										 });
								 }else if(n==6){
										sale_record+=$(this).text()+";";
										totalMoney+=parseFloat($(this).text().replace("HK$",""));
								 }else if(n==7){
									 
								 }else{
									 sale_record+=$(this).text()+",";
								 }
							});
						 }
			}
			});
				if(sale_record==""){
					return false;
				}  
			$.ajax({
				url:"SaveMarketingPrimiumRecordServlet",
				type:"post",
				async:false,
				data:{"staffcode":$("#staffcode").val(),"staffname":$("#staffname").val(),
				"location":$("#locationid").val(),"payMethod":$("#payMethod").val(),
				"chequeNo":$("#chequeNo").val(),"Banker":$("#Banker").val(),
				"totalMenoy":totalMoney,"sale_record":sale_record,"cah_number":$("#cashid").val(),
				"cheque_number":$("#chequeid").val(),"department":$("#department").val(),"ordertype":"Staff"},
				success:function(date){
					if(date=="success"){//订单保存成功
						//alert("Order submitted to success!");
						successAlert("We will notify you by email to collect the items in 3 working days.",function(){
							location.reload();
						});
					}else if(date=="error"){//订单保存失败
						errorAlert("Abnormal orders submitted1!");
						return false;
					}else if(date=="order"){//订单表保存失败
						errorAlert("Abnormal orders submitted2!");
						return false;
					}else if(date=="record"){//记录表保存失败
						errorAlert("Abnormal orders submitted3!");
						return false;
					}else{//超出库存producode
						stock=eval(date);
							/**for(var i=0;i<overflow_quantity.length;i++){
							overflowString+=overflow_quantity[i];
							if(i==overflow_quantity.length-1)
								overflowString+=",";
								else
								overflowString+="]";
						}**/
						//	alert("还没写!");
						reValue("unit");
					}
					//alert(date);
				},error:function(){
					errorAlert("Network connection is failed, please try later...");
					return false;
				}
			});
					
			});
			
			
			var browser_version=$.browser.version;
			$("#divId").scroll(function(e) {
        if($(this).scrollTop()>0){
            if($("#thead").length==0){
                var thead=$('<table id="thead">'+$("table thead").html()+'</table>');
                $("#divId").prepend(thead);
                 $("#thead").css("width",$("table thead").width());
                
            }
            else if('6.0'==browser_version||'7.0'==browser_version){
                $("#thead").css("position","relative");
                $("#thead").css("top",$(this).scrollTop());
                $("#thead").css("width",$("#divId table").width());
            }
        }
        else{
            $("#thead").remove();
        }
    });
			
		});
		function judgeNaN(a){
			var cheque = $("#"+a).val();
			if(isNaN(cheque)){
				error("Please Input Numbers");
				$("#"+a).val("").focus();
				return false;
			}
		}
  </script>
  
     <style type="text/css">
<!-- 
.images{
width:16px;
 height:16px; 
 margin-top:2px;
}

.inputNumber{
width: 40px!important;
 border:0px;
}
</style>
  
  </head>
<body>
<div class="e-container">
	<div class="info-form">
		<h4>Marketing Premium Request</h4>
		<table id="topTable">
			<tr>
				<td class="tagName">Consultant/Staff Code</td>
				<td class="tagCont">
					<input type="text" class="inputstyle" id="staffcode" readonly="readonly" value="${convoy_username}"/>
			    	<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Name</td>
				<td class="tagCont">
					<input type="text" class="inputstyle" id="staffname" readonly="readonly" />
			    	<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Department：</td>
				<td class="tagCont">
					<input type="text" name="department" id="department" class="inputstyle">
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
		<h4>Marketing Primium Products</h4> 
		<table id='leftTable'>
			<tr>
				<td class="width_8">Product Code</td>
				<td class="width_30">Englishname</td>
				<td class="width_10">Unit Price</td>
				<td class="width_10">C-Club Price</td>
				<td class="width_10">Special Price</td>
				<td class="width_5">Unit</td>
				<td class="width_10">Quantity</td>
				<td class="width_5">Buy?</td>
			</tr>
		</table>
		<div class="form-bottom"> 
			<strong>Total:<span id="totalprice" style="color: red;">HK$ 0</span></strong>
		</div>
	</div>
	<div class="info-form" id="divIdSelected" style="display: none;">
		<h4></h4>
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
		<button class="btn" id="save">Submit</button>
		<button class="btn" id="cancel" onClick="javascript:if(confirm('Sure to Reset?')){location.reload();}">Reset</button>
	</div>
</div>  
</body>
</html>
