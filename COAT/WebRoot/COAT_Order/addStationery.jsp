<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
  <base href="<%=basePath %>" >
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
    <title>文具錄入(Desktop_Stationery)</title>
      <style type="text/css">
    label{display: inline!important;margin-bottom: 0px!important;}
	.redspan{
		color:red;	
	}
      </style>
	  <script type="text/javascript">
	    var d ;
	    var locations;
	    $(function(){
	    		getlocation();
	    });
	    
	    
		/*获取左边table*/
	  	function loadValue(){
		
			$.ajax({
				type: "post",
				url: "QueryStationeryServlet",
				data:{"sc":"C"},
				async:false,
				success: function(data){
					d=eval(data);
					var html = "";
					
					html+="<tr><td width='5%'>Product Code</td><td width='5%'>English Name</td><td width='5%'>Chinese Name</td><td width='4%'>Price</td><td width='2%'>Unit</td><td width='7%'>Quantity</td><td width='3%'>Buy?</td></tr>";
					if(d.length>0){
						for(var i=0;i<d.length;i++){
							if(d[i].quantity <= 0){
								html+="<tr bgcolor='#F0F0F0' disabled='true' id='trLeft"+i+"'><td disabled='true' style='text-align: left;'>"+d[i].procode+"</td><td disabled='true' style='text-align: left;'>"+d[i].englishname+"</td><td disabled='true' style='text-align: left;'>"+d[i].chinesename+"</td><td disabled='true'><label>HK$</label>"+d[i].price+"</td><td disabled='true'>"+d[i].unit+"</td><td disabled='true'><img src='images/jian.png' style='width:16px; height:16px;' onclick='deleteQuantity(this,"+i+",\"name"+i+"\",\"id"+i+"\",\"leftTable\");'/>&nbsp;<input type='text' onkeyup='checknumber("+i+",this);' maxlength='4' id='id"+i+"' name='name"+i+"' style='width: 40px;border-left-width: 0px;border-right-width: 0px;border-top-width: 0px;' value='0'  disabled='true' onblur='judgeNaN(this,"+i+",\"name"+i+"\",\"id"+i+"\",\""+d[i].quantity+"\",\"leftTable\");'/>&nbsp;<img src='images/jia.png' style='width:16px; height:16px; margin-top:2px;' onclick='addQuantity(this,"+i+",\"name"+i+"\",\"id"+i+"\",\""+d[i].quantity+"\",\"leftTable\");'/>&nbsp;</td><td disabled='true'><input  disabled='true' type='checkbox'  id='check"+i+"' onclick='onas(this,"+i+",\"leftTable\",\""+d[i].quantity+"\",\"check"+i+"\",\"id"+i+"\");'/></td></tr>";
							}else{
								html+="<tr id='trLeft"+i+"'><td style='text-align: left;'>"+d[i].procode+"</td><td style='text-align: left;'>"+d[i].englishname+"</td><td style='text-align: left;'>"+d[i].chinesename+"</td><td><label>HK$</label>"+d[i].price+"</td><td>"+d[i].unit+"</td><td ><img src='images/jian.png' style='width:16px; height:16px;' onclick='deleteQuantity(this,"+i+",\"name"+i+"\",\"id"+i+"\",\"leftTable\");'/>&nbsp;<input type='text' onkeyup='checknumber("+i+",this);' maxlength='4' id='id"+i+"' name='name"+i+"' style='width: 40px;border-left-width: 0px;border-right-width: 0px;border-top-width: 0px;' value='1' onblur='judgeNaN(this,"+i+",\"name"+i+"\",\"id"+i+"\",\""+d[i].quantity+"\",\"leftTable\");'/>&nbsp;<img src='images/jia.png' style='width:16px; height:16px; margin-top:2px;' onclick='addQuantity(this,"+i+",\"name"+i+"\",\"id"+i+"\",\""+d[i].quantity+"\",\"leftTable\");'/>&nbsp;</td><td><input type='checkbox'  id='check"+i+"' onclick='onas(this,"+i+",\"leftTable\",\""+d[i].quantity+"\",\"check"+i+"\",\"id"+i+"\");'/></td></tr>";
							}
						}
					}
					$("#leftTable").append(html);
				}
			});
			
			var econvoy="${Econvoy}".split("-");
			//$("#locationid option:selected").text(econvoy[3]);
			
			
			
			
			$("#nameid").val(econvoy[1]);
			//$("#nameid").attr("readOnly",true);
			$("#staffCodeid").val(econvoy[0]);
			//$("#staffCodeid").attr("readOnly",true);
		}
		
		/*按减号所触发的函数*/
		function deleteQuantity(r,i,name,id,tableName){
			var select = document.getElementById("check"+i);
			var val = $("input[name="+name+"]").val();
			if(parseFloat(val) > 1.5){
				$("input[id="+id+"]").each(function(){
				    $(this).val(parseFloat(val)-1);
				})
				if(select.checked){
					$("#label"+i).text(document.getElementById(id).value*$("#plabel"+i).text());
					getTotal();
				}
			}
		}
		
		
		/*按加号所触发的函数*/
		function addQuantity(r,i,name,id,quantity,tableName){
			var select = document.getElementById("check"+i);
			var val = $("input[name="+name+"]").val();
    		var j=r.parentNode.parentNode.rowIndex;
			var procode = $("#"+tableName+" tr:eq("+j+") td:eq("+0+")").text();
			if(!select.checked){
				select.click();
			}
            $("input[id="+id+"]").each(function(){
			  $(this).val(parseFloat(val)+1);
			})
			if(select.checked){
				$("#label"+i).text(document.getElementById(id).value*$("#plabel"+i).text());
				getTotal();
			}
		}
		
		
		/*计算购物车里面的商品的总价格 */
		function getTotal(){
			var all_price = 0;
		    $("#buttomTable td").each(function(n){
				if(n>=8){           //从第二行开始 
					if((n-14)%8==0){       //当这一列是‘总价’的那一列时
						$(this).each(function(){
							$(this).children().eq(1).each(function(){
								all_price += parseFloat($(this).text());
							});
						 });
					 }
				 }
			});
			document.getElementById("total").innerHTML = all_price;
		}
		
		/*点击上面checkbox,获取下面的table*/
		function onas(r,i,name,quantity,checkid,textid){
			var select = document.getElementById(checkid);
			if(parseFloat($("input[id='id"+i+"']").val())<=0){
				$("#"+checkid+"").attr("checked",false);
				return false;
			}
			var text = document.getElementById(textid);
			if(select.checked){
				var quan = document.getElementById(textid).value;
  				var selectedTable = document.getElementById('buttomTable');
  				if(selectedTable != null ){
  					var row = selectedTable.insertRow(selectedTable.rows.length);//行对象
  					row.setAttribute("id","trButtom"+i);
  					var oldrow = selectedTable.rows.length-2;//table原有的行数
  					var col = row.insertCell(0);
  					col.innerHTML = d[i].procode;
  					col = row.insertCell(1);//列对象
  					col.align='left';
  					col.innerHTML = d[i].englishname;
  					col = row.insertCell(2);
  					col.align='left';
  					col.innerHTML = d[i].chinesename;
  					col = row.insertCell(3);
  					col.innerHTML = "<label>HK$</label><label id='plabel"+i+"'>"+d[i].price+"</label>";
  					col = row.insertCell(4);
  					col.innerHTML = d[i].unit;
  					col = row.insertCell(5);
  					col.innerHTML = "<img src='images/jian.png' style='width:16px; height:16px;' onclick='deleteQuantity(this,"+i+",\"bname"+i+"\",\"id"+i+"\",\"buttomTable\");'/>&nbsp;<input type='text' maxlength='4' id='id"+i+"' name='bname"+i+"' style='width: 40px;border-left-width: 0px;border-right-width: 0px;border-top-width: 0px;' value='"+quan+"' onblur='judgeNaN(this,"+i+",\"bname"+i+"\",\"id"+i+"\",\""+quantity+"\",\"buttomTable\");'/>&nbsp;<img src='images/jia.png' style='width:16px; height:16px; margin-top:2px;' onclick='addQuantity(this,"+i+",\"bname"+i+"\",\"id"+i+"\","+quantity+",\"buttomTable\");'/>&nbsp;";
  					col = row.insertCell(6);
  					col.innerHTML = "<label>HK$</label><label id='label"+i+"' style='width: 76px;'>"+(d[i].price*quan)+"</label>";
  					col = row.insertCell(7);
  					col.innerHTML = "<input type='checkbox'  id='check1"+i+"' onclick='overas(this,\"check1"+i+"\",\"buttomTable\",\""+checkid+"\",\""+textid+"\");' checked='checked'/>";
  					getTotal();
  				}
			}else{
				if(confirm("Sure to remove?")){
					$("#"+textid).val(1);
	  				$("#buttomTable tr").each(function(n){
					   if($(this).attr("id") == ("trButtom"+i)){
		  					document.getElementById("buttomTable").deleteRow(n);
		  				}
					})
					getTotal();
				}else{
					select.checked = true;
				}
			}
		}
		
		/*点击下面checkbox,重获上面的table*/
		function overas(r,id,name,oldcheckid,oldtextid){
			var select = document.getElementById(id);
			var oldselect = document.getElementById(oldcheckid);
			var oldtext = document.getElementById(oldtextid);
			if(confirm("Sure to remove?")){
				var j=r.parentNode.parentNode.rowIndex;
				var check = $("#buttomTable tr:eq("+j+") td:eq("+7+") [type=checkbox]").attr("id");
               	var last = check.substring("check1".length,check.length);
				var procode = $("#buttomTable tr:eq("+j+") td:eq("+0+")").text();
				$.ajax({
					type: "post",
					url: "QueryStationeryQuantityServlet",
					data:{"procode":procode},
					success: function(num){
						if(num == 0){
			    			$("input[name='name"+last+"']").val(0);
			    			$("#trLeft"+last).attr("bgcolor","#F0F0F0");
							oldselect.disabled = true;
							oldselect.checked = false;
							oldtext.readOnly=true;
							oldtext.disabled = true;
						}else{
			    			$("input[name='name"+last+"']").val(1);
							oldselect.disabled = false;
							oldselect.checked = false;
							oldtext.readOnly=false;
							oldtext.disabled = false;
				
						}
		  				document.getElementById(name).deleteRow(j);
						getTotal();
					}
				});
			}else select.checked = true;
		}
		
		/*当鼠标移开数量文本框时触发的事件 */
		function judgeNaN(r,i,name,textid,quantity,tableName){
			var val = $("input[name="+name+"]").val();
			var j=r.parentNode.parentNode.rowIndex;
			if(isNaN(val)){
				//alert("Can only enter numbers!");
				$("input[id="+textid+"]").each(function(){
				  $(this).val(1);
				})
				if(document.getElementById("label"+i) != null){
					document.getElementById("label"+i).innerHTML = document.getElementById("plabel"+i).innerHTML;
					getTotal();
				}
			}else if(val == '' || val < 0){
				$("input[id="+textid+"]").each(function(){
				  $(this).val(1);
				})
				if(document.getElementById("label"+i) != null){
					document.getElementById("label"+i).innerHTML = document.getElementById("plabel"+i).innerHTML;
					getTotal();
				}
			}else{
				$("input[id="+textid+"]").each(function(){
				  $(this).val(parseFloat(val));
				})
			}
			var select = document.getElementById("check"+i);
			if(select.checked){
				$("#label"+i).text(document.getElementById(textid).value*$("#plabel"+i).text());
				getTotal();
			}
		}
		
		/*点保存获取buttomTable的值*/
		function getButtomTableValue(){
			var st = "";
			var priall = 0;
			if($("#staffCodeid").val()==""){
				error("Please input Staff Code!");
				$("#staffCodeid").focus();
				return false;
			}
			if($("#nameid").val()==""){
				error("Please input Staff Name!");
				$("#nameid").focus();
				return false;
			}
			
			var locat = $("#locationid").val();
			if(locat == ''){
				error("Please select location!");
				$("#locationid").focus();
				return false;
			}
		
			
			
			var table = document.getElementById("buttomTable");
			if(table.rows.length > 1){
				$("#buttomTable td").each(function(n){
					if(n>=8){           //从第二行开始 
						if((n-13)%8==0){                //当这一列是‘数量’的那一列时
							 $(this).each(function(){
								$(this).children().eq(1).each(function(){
									st += $(this).val()+",";
								});
							 });
						 }else if((n-14)%8==0){       //当这一列是‘总价’的那一列时
						 	$(this).each(function(){
								$(this).children().eq(1).each(function(){
									st += $(this).text();
									priall += parseFloat($(this).text());
								});
							 });
						 }else if((n-15)%8==0){      //当这一列是‘checkbox’的那一列时
							 st += ";";
						  }else if((n-11)%8==0){      //当这一列是‘price’的那一列时
							 $(this).each(function(){
								$(this).children().eq(1).each(function(){
									st += $(this).text()+",";
								});
							 });
						  }
						  else{
						 	 st += $(this).text()+",";
						  }
					 }
				});
				
			
				var name = $("#nameid").val();
				var code = $("#staffCodeid").val();
			
				var cash = $("#cashid").val();
				var club = $("#clubid").val();
				var cheque = $("#chequeid").val();
				/*if((cash == null || cash == "") && (club == null || club == "") && (cheque == null || cheque == "")){
					alert("Please choose the payment method!");
					$("#cashid").focus();
					return false;
				}*/
				
				var banker = $("#bankerid").val();
				var cheque_no = $("#cheque_noid").val();
			/*	if(cheque != ""){
					if(cheque_no == null || cheque_no == ""){
						alert("Please enter the check information!");
						$("#cheque_noid").focus();
						return false;
					}else if(banker == null || banker == ""){
						alert("Please enter the check information!");
						$("#bankerid").focus();
						return false;
					}
				}*/
				if(confirm("Sure to Submit?")){
					$.ajax({
						type: "post",
						url: "StationeryOrderRecordServlet",
						data:{"str":st,"priceall":priall,"location":locat,"name":name,"code":code,"staffOrCons":"CONS","cash":cash,"club":club,"cheque":cheque,"banker":banker,"cheque_no":cheque_no},
						success: function(msg){
							if(msg.split("-")[0] == 0){
								//alert("Ordercode is "+msg.split("-")[1]+","+"You need to pay HK$ "+$("#total").text());
								//alert("You need to pay HK$ "+$("#total").text());
								alert("We will notify you by email to collect the items in 3 working days.");
								location.reload();
							}else {
								 locations = $("#locationid option:selected").text();
								 alert("Inventory is not enough,give the largest inventory！");
								 //重新加载产品表
								 document.getElementById("divId").innerHTML = "";
			                	 loadValue();
			                	 
								 var numArray = msg.split(";");
								 for(var i=0;i<numArray.length-1;i++){
									var numArrayChild = numArray[i].split(",");
								 	giveButtomTableMaxQuantity(numArrayChild[0],numArrayChild[1],numArrayChild[2]);
								 }
								 return false;
							}
						}
					});
				}
			}else alert("No choice of products！");
		}
		
		/*当库存不够后，不能保存成功，将最大库存值放入购物车*/
		function giveButtomTableMaxQuantity(procode,quantity,flag){
		    var table = document.getElementById("buttomTable");
			var rows=table.rows;
	        for(var i=0;i<rows.length;i++){
                var uu = rows[i].cells.length;
                if(rows[i].cells[0].innerHTML == procode){
               		var td = $("#buttomTable tr:eq("+i+") td:eq("+5+") [type=text]").attr("id");
					$("input[id="+td+"]").each(function(){
					  $(this).val(parseInt(quantity));
					})
               		var check = $("#buttomTable tr:eq("+i+") td:eq("+7+") [type=checkbox]").attr("id");
               		var last = check.substring("check1".length,check.length);
               		var dprice = $("#plabel"+last).text();
               		$("#check"+last).attr("checked",true);
               		if(flag == 1){
               			$("#trButtom"+last).attr("bgcolor","#FFC1E0");
               		}
               		$("#label"+last).text(quantity*dprice);
               		if(quantity == 0){
               			$("#trButtom"+last).attr("bgcolor","#F0F0F0");
               		}
               		getTotal();
                }
	        }
	        //$("#locationid option:selected").text(locations);
		}
		
		/*删除buttomTable*/
		function removeSelectedTable() {
		    var table = document.getElementById("buttomTable");
		    trs = table.getElementsByTagName("tr");
		    for(var i = trs.length - 1; i > 0; i--) {
		        table.deleteRow(i);
		    }
		}
		
		/*取消订单*/
		function cancelOrder(){
			if(confirm("Sure to Reset?")){
				location.reload();
			}
		}
		
		/*加载location*/
		function getlocation(){
			$.ajax({
				type:"post",
				//url:"QueryLocationServlet",
				//data:null,
				url: "common/CommonReaderServlet",
				data:{"method":"getlocation"},
				success:function(date){
					var locationc=eval(date);
					var html="";
					$("#locationid").empty();
					if(locationc.length>0){
						html+="<option value='' >Please Select Location</option>";
						for(var i=0;i<locationc.length;i++){
							html+="<option value='"+locationc[i].realName+"' >"+locationc[i].name+"</option>";
						}
					}else{
						html+="<option value=''>loading error</option>";
					}
					$("#locationid").append(html);
				},error:function(){
					alert("Network connection failure, please return to the login page to log in!");
					return false;
				}
			});
		}
		
		/*判断输入的是否只是数字*/
		function checknumber(n,obj){
	    	var vals=obj.value.replace(/[^\d]/g,"");
	    	if(vals=="")
	    		$("[id='id"+n+"']").val(0);
	    	else
	    		$("[id='id"+n+"']").val(parseFloat(vals));
	    }
	    
	    /*判断金额 */
		function checkmoney(n,obj){
			var vals = $("#"+n+"").val();
			if(isNaN(vals)){
				$("#"+n+"").val("");
			}
	    }
  </script>
  </head>
<body onload="loadValue();" >
<div class="e-container">
	<div class="info-form" id="topTable">
		<h4>Stationery Request</h4>
		<table>
			<tr>
				<td class="tagName">Consultant/Staff Code</td>
				<td class="tagCont">
					<input id="staffCodeid" type="text" class="inputstyle" readonly="readonly" />
			    	<span class="redspan">*</span>
				</td>
			</tr>
			<tr>
				<td class="tagName">Consultant/Staff Name</td>
				<td class="tagCont">
					<input type="text"  id="nameid" class="inputstyle" readonly="readonly" />
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
		<h4>Request Options</h4>
		<div id="optionsDiv" style="max-height: 324px;">
			<table id="leftTable"></table>	
		</div>
		<div class="form-bottom">
			<strong>Total：</strong><strong style="color: #428bca;">HK$<font style="margin-left: 5px;margin-right: 5px;" id='total'>0</font></strong>
		</div>
	</div>
	<div class="info-form" id="divIdSelected" style="height: 25%; overflow:scroll;display: none;">
		<h4>Request Options</h4>
		<table id='buttomTable'>
			<tr>
				<td class="width_">Product Code</td>
				<td class="width_">English Name</td>
				<td class="width_">Chinese Name</td>
				<td class="width_">Price</td>
				<td class="width_">Unit</td>
				<td class="width_">Quantity</td>
				<td class="width_">Allprice</td>
				<td class="width_">Buy?</td>
			</tr>
		</table>
	</div>
	<div class="btn-board">
		<a class="btn" onclick="getButtomTableValue();">Submit</a>
		<a class="btn" onclick="cancelOrder();">Reset</a>
	</div>
</div>
  	

     
     
     
    <!--  未知区域  开始  -->
   <table width="1040px;" border="0" rules="none" style="display:none;">
		    <tr>
			    <td width="330px;">
			   		 Name of Requestor : <input id="nameid" class="inputstyle" type="text"/>
			    </td>
			    <td width="260px;">Location : 
			   		 <select id="locationid"></select>
				</td>
			    <td width="260px;">
			   		 StaffCode : <input id="staffCodeid" type="text" class="inputstyle"/>
			    </td>
		    </tr>
		    <tr>
			   	 <td colspan="3" style="display: none;">
			   		 <strong>&nbsp;&nbsp;Payment Method</strong>
			    </td>
		    </tr>
		    <tr>
			    <td colspan="3" style="display: none;">
			   		 <font style="margin-left: 44px;">Cash(HK$): </font><input onkeyup='checkmoney("cashid",this);' dir="rtl" type="text" id="cashid" class="inputstyle"/>
			    </td>
		    </tr>
		    <tr>
			    <td colspan="3" style="display: none;">
			    	<font style="margin-left: 36px;">C_club(HK$): </font><input onkeyup='checkmoney("clubid",this);' dir="rtl" type="text" id="clubid" class="inputstyle"/>
			    </td>
		    </tr>
		    <tr> 
			    <td colspan="2" style="display: none;"><font style="margin-left: 33px;">Cheque(HK$):</font><input onkeyup='checkmoney("chequeid",this);' dir="rtl" type="text" id="chequeid" class="inputstyle"/>
				    <font style="margin-left: 10px;display: none;">Cheque No:</font><input type="text" id="cheque_noid" class="inputstyle" style="display: none;"/>
				    <font style="margin-left: 5px;display: none;">Banker:</font><input type="text" id="bankerid" class="inputstyle" style="display: none;"/>
			    </td>
			    <td colspan="2" align="right">
				
				</td>
		    </tr>
    </table>
      <!--  未知区域  结束  -->
<link rel="stylesheet" href="./css/jquery.mCustomScrollbar.css" />
<script src="./css/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript">
(function($){
	$(window).load(function(){
		$("#optionsDiv").mCustomScrollbar({
            axis:"y", // vertical and horizontal scrollbar
            theme:'minimal-dark'
        });
	});
})(jQuery);
</script>
</body>
</html>
