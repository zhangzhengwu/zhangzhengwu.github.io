<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/plug/common.inc" %>
<%

session.setAttribute("adminUsername","Pick UP");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <!-- <base href="<%=basePath%>">-->
    
    <title>Welcome to the Document Pickup System(歡迎使用提取文件系統)</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<style type="text/css">
	.s-container{width: 100%; }
	@media(max-width: 1100px){
		.stepNav{float: left; width: 100%!important; border-bottom: 1px solid #ddd;}
		.stepCont{float: left; width: 100%!important; min-height: 500px; position: relative;}
		.stepNav a{text-decoration: none; color: #666;}
		.stepNav .nav-title{float: left; border-right: 1px solid #ddd;}
		.stepNav .nav-title a{display: block; height: 40px; padding: 10px 14px; font-size: 1.2em;}
		.stepNav .nav-state{float: left; border-right: 1px solid #ddd; cursor: pointer;}
		.stepNav .lasts{ border-bottom: 0px; }
		.stepNav .nav-state a{display: block; width: 100%; height: 40px; padding: 10px 14px; font-size: 1em;}
		.stepNav  div.current{background: #eee;}
		.stepNav .nav-state a i{display: none;}
		.stepCont .cont-bottom{position: absolute; height: 60px; width:100%; bottom: 0px; left: 0px; border-top: 1px solid #ddd; padding-top: 10px; text-align: center;}
		.stepCont .cont-bottom .btn{ padding: 7px 0px!important; width: 40%; font-size: 1.1em; margin: 3px 1%;}
		.steps .whos{padding: 8px 34px!important; margin: 2px  20px; width: 40%;background: #fff; border: 1px solid #ddd; color: #555;}
		.steps .whos:hover{ background: #428bca; color: #fff;}
		
		/*小键盘*/
		.numbers{position: absolute; margin-left: 5px; top: 0px; background: #fff; z-index: 999;}
		.numbers ul{margin: 0px; padding: 0px;}
		.numbers ul li{float: left; width: 50px; padding: 15px 0px; text-align: center; border: 1px solid #eee; }
		.numbers ul li:hover{background: #ddd;}
		
		.numbers table{margin: 0px!important; padding: 0px;}
		.numbers table td{text-align: center; cursor: pointer; width: 60px; height: 54px;}
		.numbers table td:hover{background: #eee;}
	}
	@media(min-width: 1101px){
		.stepNav{float: left; width: 0%;   }
		.stepCont{float: left; width: 100%; border-left: 1px solid #ddd; min-height: 500px; position: relative;}
		.stepNav a{text-decoration: none; color: #666;}
		.stepNav .nav-title{}
		.stepNav .nav-title a{display: block; border-bottom: 1px solid #ddd; height: 40px; padding: 10px 14px; font-size: 1.2em;}
		.stepNav .nav-state{width: 100%; border-bottom: 1px solid #ddd; cursor: pointer;}
		.stepNav .lasts{ border-bottom: 0px;}
		.stepNav .nav-state a{display: block; width: 100%; height: 40px; padding: 10px 14px; font-size: 1em;}
		.stepNav  div.current{background: #eee;}
		.stepNav .nav-state a i{float: right; margin-right: 2px;}
		.stepCont .cont-bottom{position: absolute; height: 60px; width:100%; bottom: 0px; left: 0px; border-top: 1px solid #ddd; padding-top: 10px; text-align: center;}
		.stepCont .cont-bottom .btn{ padding: 4px 34px!important; margin: 2px 10px!important; font-size: 1.0em; width: 180px;}
		.steps .whos{padding: 8px 28px!important; margin: 2px  40px; width: 300px; background: #fff; border: 1px solid #ddd; color: #555;}
		.steps .whos:hover{ background: #428bca; color: #fff;}
		
		/*小键盘*/
		.numbers{position: absolute; margin-left: 5px; top: 0px; background: #fff; z-index: 999;}
		.numbers ul{margin: 0px; padding: 0px;}
		.numbers ul li{float: left; width: 50px; padding: 15px 0px; text-align: center; border: 1px solid #eee; }
		.numbers ul li:hover{background: #ddd;}
		
		.numbers table{margin: 0px!important; padding: 0px;}
		.numbers table td{text-align: center; cursor: pointer; width: 50px;}
		.numbers table td:hover{background: #eee;}
	}
	.stepCont .cont-steps{ width: 100%; }
	.stepCont .cont-steps .steps{ padding: 10px 14px; }
	.stepCont .cont-steps .steps p{ margin: 0px; padding: 0px;}
	.steps h4{ margin: 0px; padding: 0px;  font-weight: lighter; font-size: 1.1em; color: #555; border-bottom: 1px solid #ddd; padding-bottom: 10px;}
	.steps table{ margin: 16px 0px; width: 100%; border: 1px solid #ddd;}
	.steps table td{height: 44px; vertical-align: middle; padding: 4px; color: #555; border: 1px solid #ddd;}
	.steps table td.tagName{ text-align: right; padding-right: 15px;}
	.steps table td.tagCont{ text-align: left; padding-left: 15px;}
	.steps table td.tagCont input[type="text"]{margin-bottom: 0px; height: 26px;}
	.steps table td.tagCont input[type="password"]{margin-bottom: 0px; height: 26px;}
	.steps table.lists{}
	.steps table.lists th, .steps table.lists td{text-align: center; vertical-align: middle; border: 1px solid #ddd; height: auto!important; padding: 0px 8px!important;}
	#step1_title{padding: 12px 14px; border-bottom: 1px solid #ddd;}
	#step1_title h4{text-align: center;}
	#listDetail2 td{text-align: center;  vertical-align: middle; border: 1px solid #ddd; height: auto!important; padding: 6px 8px!important;}
	
	</style>
  </head>
  
<body>
<div class="e-container">
	<div class="s-container">
		<div class="stepNav" id="myNav" style="display: none;">
			<div class="nav-title">
				<a class="">
					Current Step
				</a>	
			</div>
			<div class="nav-state current" reg="false" id="state_1">
				<a class="" >
					First Step
					<i class="icon-angle-right"></i>
				</a>
			</div>
			<div class="nav-state" reg="false" id="state_2">
				<a class="">
					Second Step
					<i class="icon-angle-right"></i>
				</a>
			</div>
			<div class="nav-state" reg="false" id="state_3">
				<a class="">
					Third Step
					<i class="icon-angle-right"></i>
				</a>
			</div>
			<div class="nav-state" reg="false" id="state_4">
				<a class="">
					Fourth Step
					<i class="icon-angle-right"></i>
				</a>
			</div>
			<div class="nav-state last" reg="false">
				<a class="">
					Fifth Step
					<i class="icon-angle-right"></i>
				</a>
			</div>
		</div>
		<div class="stepCont">
			<!--  <div class="cont-title">
				
			</div>-->
			<div class="cont-steps">
				<div id="step1_title" >
					<h4>Welcome to the Document Pickup System<b style="font-weight: lighter; font-size: 1.5em;">歡迎使用提取文件系統</b></h4>
				</div>
				<div class="steps" id="step_1">
					<h4>Please scan your staff card 請掃瞄閣下的員工證</h4>
					<table>
						<tr>
							<td class="tagName" width="180px">Staff Card: </td>
							<td class="tagCont">
								<input type="text" name="staffcard" id="staffcard"/>
							</td>
						</tr>
					</table>
				</div>
				<div class="steps" id="step_2">
					<div id="justSelf">
						<h4>Please select 請選擇</h4>
						<table style="border: none;">
							<tr>
								<td colspan="2" style="border: none; text-align: center;">
									<div class="whos btn" id="self">Receiver 本人</div>
									<div class="whos btn" id="other">Authorized Person 授權人</div>
								</td>
							</tr>
						</table>
					</div>
					<div id="otherPerson" style="display: none;">
						<h4>您選擇的是"Authorized Person", 請輸入該收件人的Staff Code...</h4>
						<table>
							<tr>
								<td class="tagName" width="180px">Staff Code:</td>
								<td class="tagCont" style="position: relative;">
									<input type="text" id="otherCode" readonly="readonly" />
									<div class="numbers" id="fullkey" style="display: none;">
										<table>
											<tr>
												<td class="nums">1</td>
												<td class="nums">2</td>
												<td class="nums">3</td>
												<td class="nums">4</td>
												<td class="nums">5</td>
												<td class="nums">6</td>
												<td class="nums">7</td>
												<td class="nums">8</td>
												<td class="nums">9</td>
												<td class="nums">0</td>
											</tr>
											<tr>
												<td class="nums">Q</td>
												<td class="nums">W</td>
												<td class="nums">E</td>
												<td class="nums">R</td>
												<td class="nums">T</td>
												<td class="nums">Y</td>
												<td class="nums">U</td>
												<td class="nums">I</td>
												<td class="nums">O</td>
												<td class="nums">P</td>
											</tr>
											<tr>
												<td class="nums">A</td>
												<td class="nums">S</td>
												<td class="nums">D</td>
												<td class="nums">F</td>
												<td class="nums">G</td>
												<td class="nums">H</td>
												<td class="nums">J</td>
												<td class="nums">K</td>
												<td class="nums">L</td>
												<td class="btn_clear">Clear</td>
											</tr>
											<tr>
												<td class="nums">Z</td>
												<td class="nums">X</td>
												<td class="nums">C</td>
												<td class="nums">V</td>
												<td class="nums">B</td>
												<td class="nums">N</td>
												<td class="nums">M</td>
												<td class="btn_c" colspan="2">Backspace</td>
												<td class="btn_ok">OK</td>
												
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="steps" id="step_3" style="min-height: 320px;">
					<div id="lists">
						<h4>Please select the document請選取文件</h4>
						<table class="lists">
							<tr id="listDetail">
								<th class="width_5">
									<label class="checkbox inline">
										<input type="checkbox" name="all"/>
										<strong>All</strong>
									</label>
								</th>
								<th class="width_10">Request Date</th>
								<th class="width_15">Receiver Staff Code</th>
								<th class="width_10">Sender</th>
								<th class="width_10">Document Type</th>
								<th class="width_15">Client Name(if Any)</th>
							</tr>
						</table>
					</div>
					<div id="lists_next" style="display: none;">
						<h4>Please enter your extension password 請輸入閣下內線的密碼</h4>
						<table>
							<tr>
								<td class="tagName" width="180px">Extension number: </td>
								<td class="tagCont">
									<input type="text" id="extensionNum" />
								</td>
							</tr>
							<tr>
								<td class="tagName" width="180px">Password: </td>
								<td class="tagCont" style="position: relative;">
									<input type="password" id="pwd" readonly="readonly"/>
									<div class="numbers" id="numkey" style="display: none;">
										<table>
											<tr>
												<td class="nums">1</td>
												<td class="nums">2</td>
												<td class="nums">3</td>
												
											</tr>
											<tr>
												<td class="nums">4</td>
												<td class="nums">5</td>
												<td class="nums">6</td>
												
											</tr>
											<tr>
												<td class="nums">7</td>
												<td class="nums">8</td>
												<td class="nums">9</td>
												
											</tr>
											<tr>
												<td class="btn_c">C</td>
												<td class="nums">0</td>
												<td class="btn_ok">OK</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="steps" id="step_4">
					<h4>Please hit the " Received " button to completed this transaction請點擊 " Received " 按鈕以確認收到文件
					</h4>
					<table>
						<tr id="listDetail2">
							<td class="width_10">Request Date</td>
							<td class="width_15">Staff Code</td>
							<td class="width_15">Sign Code</td>
							<td class="width_10">Sender</td>
							<td class="width_10">Document Type</td>
							<td class="width_15">Client Name(if Any)</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="cont-bottom">
				<button class="btn" id="btn_reset">Reset</button>
				<button class="btn" alt="1" id="btn_next">Next</button>
				<button class="btn" id="btn_enter" style="display: none;">Enter</button>
				<button class="btn" id="btn_receive" onclick="save_Received()" style="display: none;">Received</button>
			</div>
		</div>
		<div style="clear: both;"></div>
	</div>
</div>
<script type="text/javascript">
var otherCode="";
var order_Down=null;
var before="";
var timer=null;
var extension_all="";
var s_username="";
var documentID="";
var checkID="";
$(function(){
	timer=setInterval(function(){scanningstaffcode();},3000); //每3秒钟执行一次test()
	$('.steps').hide();
	$('#step_1').show();
	$('#staffcard').focus();
	
	$('.whos').each(function(){
		$(this).bind('click',function(){
			var _id = $(this).attr('id');
			switch(_id){
				case 'self':
					$('#btn_next').attr('alt',2);
					$('#btn_next').click();
					break;
				case 'other':
					$('#justSelf').hide();
					$('#otherPerson').show();
					$('#btn_next').show();
					break;
				default: break;
			}
		});
	});
	$('#otherCode').bind('click', function(){
		var l = 0, t = ($(this).parent().height() - $(this).height())/2 + $(this).height()+15;

		$('#fullkey').css({
			'top' : t,
			'left' : 14
		}).show();
	});
	$('#fullkey .nums').each(function(){
		$(this).bind('click',function(){
			var _val = $('#otherCode').val() + $(this).text();
			$('#otherCode').val(_val);
		});
	});
	$('#fullkey .btn_ok').bind('click',function(){
		$('#fullkey').hide();
	});
	$('#fullkey .btn_c').bind('click',function(){
		var _val = $('#otherCode').val(), _newVal = '';
		if(_val.length >0){
			_newVal = _val.substring(0,_val.length-1);
			$('#otherCode').val(_newVal);
		}
	});
	$('#fullkey .btn_clear').bind('click',function(){
		$('#otherCode').val('');
	});
	$('#pwd').bind('click',function(){
		var l = 0, t = ($(this).parent().height() - $(this).height())/2 + $(this).height()+15;

		$('#numkey').css({
			'top' : t,
			'left' : 14
		}).show();
	});
	$('#numkey .nums').each(function(){
		$(this).bind('click',function(){
			var _val = $('#pwd').val() + $(this).text();
			$('#pwd').val(_val);
		});
	});
	$('#numkey .btn_ok').bind('click',function(){
		$('#numkey').hide();
	});
	$('#numkey .btn_c').bind('click',function(){
		var _val = $('#pwd').val(), _newVal = '';
		if(_val.length >0){
			_newVal = _val.substring(0,_val.length-1);
			$('#pwd').val(_newVal);
		}
	});
	
	function scanningstaffcode(){
		if($("#staffcard").val()!=""&& $("#staffcard").val()!=before){
			$('#btn_next').click();
			//clearInterval(timer);//-->每用完一次就停止定时器
	  		//alert("u");
		}else{
			//alert("m");
		}
		//  clearInterval(timer);
	}
	
	$('#btn_next').bind('click',function(){
		var cur = 0, index = 0;
		cur = parseInt($(this).attr('alt'));
		index = cur+1;
		//目前只有5个步骤
		if(index <= 5){
			if($("#staffcard").val()==""){
				alert("staffcard不能为空!");
				$("#staffcard").focus();
				return false;
			}else{
				if(index==2){
					$('#myNav').css('width',15+'%').show();
					$('.stepCont').css('width',85+'%');
					$('#step1_title').hide();
					before=$("#staffcard").val();
					clearInterval(timer);//-->每用完一次就停止定时器
					var result2=findByOrderCode($("#staffcard").val());
					//alert(result2);
					if(result2==false){
						if(confirm("你已有文件待审核，是否继续?")){
							index=3;
						}else{
							del_Order($("#staffcard").val());
							order_Down="";
						}
					}
				}
			}
			//判断DIV是否隐藏\显示
			var a =document.getElementById("otherPerson").style.display;
			if(a=="none"){
				//otherPerson  DIV已隐藏
				if(order_Down.length>0){
					otherCode=order_Down[0].signcode;
				}else{
					otherCode=$("#staffcard").val();
				}
			}else{
				//otherPerson  DIV已显示
				if($("#otherCode").val()==""){
					alert("otherCode不能为空!");
					$("#otherCode").focus();
					return false;
				}else{
					otherCode=$("#otherCode").val();
				}
			}
			if(index==3){
				$('#myNav').css('width',15+'%').show();
				$('.stepCont').css('width',85+'%');
				$('#step1_title').hide();
				//alert(otherCode+"---->"+order_Down[0].signcode);
				if(order_Down.length>0){
					otherCode=order_Down[0].signcode;
				}
				findList(otherCode==""?($("#staffcard").val()):otherCode);
				check_Order();//--<如果已有待审核的文件，则自动勾选
			}
			
			s_username=otherCode==""?($("#staffcard").val()):otherCode;
			
			//验证内线号、密码是否为空
			if(index==4){
				$('#myNav').css('width',15+'%').show();
				$('.stepCont').css('width',85+'%');
				$('#step1_title').hide();
				if($("#extensionNum").val()==""){
					alert("Extension number不能为空!");
					$("#extensionNum").focus();
					return false;
				}
				if($("#pwd").val()==""){
					alert("Password不能为空!");
					$("#pwd").focus();
					return false;
				}
				//--<<保存已选择的订单信息
				var result=saveOrder();
				if(result==false){
					return false;
				}
				var sf=findByOrderCode($("#staffcard").val());
				//alert(sf+"--"+index);
				if(sf==false){
					showOrderDetail();
				}
			}
			setStep(cur,index);
			setBtn(index);
			var flag = $('#state_'+cur).attr('reg');
			//防止重复注册事件，进行标记
			if(flag == "false"){
				$('#state_'+cur).bind('click',function(){
					var curIndex = parseInt($('#btn_next').attr('alt'));
					if(cur == 1){
						$('#step1_title').show();
						$('.stepCont').css('width',100+'%');
						$('#myNav').css('width',0+'%').hide();
					}else{
						$('#step1_title').hide();
						$('.stepCont').css('width',85+'%');
						$('#myNav').css('width',15+'%').show();
					}
					setStep(curIndex,cur);
					setBtn(cur);
				});
				$('#state_'+cur).attr('reg','true');
			}
		}
	});
	
	$('#btn_enter').bind('click',function(){
		var _len = $('input[type=checkbox][name=downs]:checked').length;
		if(_len > 0){
			//$('#extensionNum').val("");
			$('#pwd').val("");
			$('#lists').hide();
			$('#lists_next').show();
			$(this).hide();
			$('#btn_next').show();
		}else{
			error('請選擇要對比的數據...');
			return false;
		}
	});
	$('input[type=checkbox][name=all]').bind('click',function(){
		if($(this).attr('checked')){
			$(':checkbox[name=downs]').attr('checked',true);
		}else{
			$(':checkbox[name=downs]').attr('checked',false);
		}
	});
	$('#btn_reset').bind('click',function(){
		//if(confirm("确认重置?")){
			location.href = location.href;
			timer=setInterval(function(){scanningstaffcode();},3000); //每3秒钟执行一次test()
		//}
	});
	function setBtn(index){
		$('.cont-bottom .btn').not($('#btn_reset')).hide();
		if(index == 1){
			timer=setInterval(function(){scanningstaffcode();},3000); //每3秒钟执行一次test()
			$('#btn_next').show();
		}else if(index == 2){
			if($('#otherPerson').is(':visible')){
				$('#btn_next').show();
			}else{
				$('#btn_next').hide();	
			}
		}else if(index == 3){
			$('#lists').show();
			$('#lists_next').hide();
			$('#btn_enter').show();
		}else if(index == 4){
			$('#btn_next').hide();
			$('#btn_receive').show();
		}else{
			return false;
		}
	}
	function setStep(cur,index){
		$('.steps').hide();
		$('#state_'+cur).addClass('back');
		$('#state_'+cur).removeClass('current');
		$('#state_'+index).addClass('current');
		$('#step_'+cur).hide();
		$('#step_'+index).show();
		$('#btn_next').attr('alt',index)
	}
});
	var refnos="";
	function saveOrder(){
		var sss=false;
		/**
		 * 遍历已选择check框
		 */
		$(".tr_detail").find("input[name='downs']").each(function(){ 
			if(this.checked){
				//alert(this.value);
				refnos+=this.value+"~,~";
			}
  		});
		$.ajax({
			url:"pickuprecord/PickUpRecordServlet",
			type:"post",
			data:{"method":"saveOrder","refnos":refnos,"staffcard":$("#staffcard").val(),"otherCode":otherCode,
				"exrension":$("#extensionNum").val(),"password":$("#pwd").val()},
			async:false,//--<锁定方法,须执行完毕才能执行下一步
			success:function(data){
				if(data.indexOf("{")==0){
					result=$.parseJSON(data);
					alert(result.msg);
				}else{
					var dataRole=eval(data);
					if(dataRole>0){
						//alert("比对结果正确，进行下一步确认.");
						sss=true;
					}else if(dataRole==-2){
						alert("内线号或者密码匹配不符,请重新输入!");
						sss=false;
					}else{
						alert("文件提交失败!");
						sss=false;
					}
				}
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
		});
		
		return sss;
	}
		
    function save_Received(){
    	if(confirm("Sure to Receive?")){
    		$.ajax({
			url:"pickuprecord/PickUpRecordServlet",
			type:"post",
			data:{"method":"saveReceive","refnos":refnos,"ConvoyDocumentID":documentID,"UpdateBy":s_username,"checkID":checkID},
			beforeSend: function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},
			success:function(data){
				alert("success");
				location.href = location.href;
				<%-- if(data.indexOf("{")==0){
					result=$.parseJSON(data);
					alert(result.msg);
				}else{
					var dataRole=eval(data);
					if(dataRole>0){
						
						if(checkID=="admin_HK"){
							var apiUrl="<%=session.getAttribute("apiPath")%>";
							alert(apiUrl);
							//console.log("----------"+apiUrl);
							//接收成功调用api返回success结果
							alert("st");
							$.get(apiUrl,{ConvoyDocumentID:documentID,UpdateBy:s_username},function(data){
								alert("a");
								var d = eval(data);
								if(d.state=="error"){
									alert("b");
									//alert(" Receive error! ");
									//return false;
								}else if(d.state=="exception"){
									alert("c");
									//alert(" param is null! ");
									//return false;
								}else{
									alert("d");
									console.log("----执行成功-----");
								}
							});
						}
						
						alert("success");
						location.href = location.href;
					}else{
						alert("error");
					}
				} --%>
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
		});
    	}
    	
    }

	function findList(code){
		$("#extensionNum").val("");
		$.ajax({
			url:"pickuprecord/PickUpRecordServlet",
			type:"post",
			data:{"method":"findList","staffcard":code},
			async:false,
			/**beforeSend: function(){
				parent.showLoad();
			},
			complete: function(){
				parent.closeLoad();
			},*/
			success:function(data){
				var html="";
				var dataRole=eval(data);

				$("tr[id='select']").remove();
				if(dataRole.length>0){
					checkID=dataRole[0].creator;
					documentID=dataRole[0].documentId;
					extension_all=dataRole[0].extension;
					$("#extensionNum").val(extension_all);
					for(var i=0;i<dataRole.length;i++){
						html+="<tr id='select' class='tr_detail'><td align='center'><label class='checkbox inline'><input type='checkbox' name='downs' value='"+dataRole[i].refno+"'/>&nbsp;</label></td>" +
							"<td align='center'>"+dataRole[i].scanDate+"</td><td align='center'>"
							+otherCode+"</td><td align='center'>"+dataRole[i].sender+"</td><td align='center'>"
							+dataRole[i].documentType +"</td><td align='center'>"+dataRole[i].clientName+"</td></tr>";		 
					}
				}else{
					html+="<tr height='30px;' id='select'><td colspan='6' align='center'>"+" Sorry, there is no matching record."+"</td></tr>";
				}
				$("#listDetail").after(html);
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
		});
	}
	
	function findByOrderCode(code){
		var result=true;
		$.ajax({
			url:"pickuprecord/PickUpRecordServlet",
			type:"post",
			data:{"method":"findByOrderCode","staffcard":code},
			async:false,
			success:function(data){
				var dataRole=eval(data);
				order_Down=dataRole;
				//alert(data+"===="+dataRole);
				if(dataRole.length>0){
					result=false;
				}else{
					result=true;
				}
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
		});
		return result;
	}
	
	function del_Order(code){
		$.ajax({
			url:"pickuprecord/PickUpRecordServlet",
			type:"post",
			data:{"method":"del_Order","staffcode":code},
			async:false,
			success:function(date){
				var result=$.parseJSON(date);
				if(result.state=="success"){
					//$("#searchs").click();
			  	}else{
					alert(result.msg);
			  	}
			},error:function(){
				alert("Network connection is failed, please try later...");
				return false;
			}
		});
	}
	
	function showOrderDetail(){
		var html="";
		$("tr[id='fff']").remove();
		if(order_Down.length>0){
			for(var i=0;i<order_Down.length;i++){
				html+="<tr id='fff' class='tr_detail'>" +
					"<td align='center'>"+order_Down[i].scanDate+"</td><td align='center'>"+order_Down[i].staffcode+"</td>" +
					"<td align='center'>"+order_Down[i].signcode+"</td><td align='center'>"+order_Down[i].sender+"</td><td align='center'>"
					+order_Down[i].documentType +"</td><td align='center'>"+order_Down[i].clientName+"</td></tr>";		 
			}
		}else{
			html+="<tr height='30px;' id='fff'><td colspan='6' align='center'>"+" Sorry, there is no matching record."+"</td></tr>";
		}
		$("#listDetail2").after(html);
	}
	
	function check_Order(){
		$(".tr_detail").find("input[name='downs']").each(function(){ 
			//alert(this.value);
			for(var i=0;i<order_Down.length;i++){
				if(this.value==order_Down[i].listId){
					$(this).attr("checked","true");
				}	 
			}
  		});
	}
	
</script>
</body>
</html>
