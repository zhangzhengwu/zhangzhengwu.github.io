
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/plug/common.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>"> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>康宏深圳运营管理中心</title>
		<link rel="stylesheet" href="plug/css/bootstrap.css">
		<link rel="stylesheet"href="plug/css/font-awesome.min.css">
		<!--[if lte IE 7]> 
		<link rel="stylesheet"href="plug/css/font-awesome-ie7.css">
		<![endif]--> 
		<link rel="stylesheet" href="plug/css/layout.css">
	
		<style>
		.bar-btn-group em{color: white;font-style: normal;cursor: pointer;}
		form{margin: 0px!important;}
		</style>
	</head>
	<body><div style="background-color: #ffa500;width:100%;<c:if test='${errMessage=="" || errMessage==null}'>display:none;</c:if>">${errMessage}</div>
	<c:if test="${errMessage!='' && errMessage!=null}">
	<script>alert("${errMessage}");</script>
	</c:if>
		<div class="e-container-fluid">
			<div class="header">
	<iframe id="topFrame" name="topFrame" src="top.jsp" height="100%" width="100%" frameborder="0" scrolling="no"> </iframe>
				<!-- <%=session.getAttribute("adminUsername")%>现在是：<span id="Clock"></span> -->
			</div>
			<div class="cont-bar">
				<table class="contet-bar-table">
					<tr>
						<td class="tagName" style="width: 200px;" >
							<div id="bar-title" class="bar-title">
								<i class="icon-reorder icon-large" id="menuBtn" title="Show/Hide"
									style="font-size: 12px; float: right; display: block; margin: 5px 15px 0px 0px; margin: 2px 15px 3px 0px\0; color: #fff; cursor: pointer;"></i>
							</div>
						</td>
						<td class="tagCont" id="tagCont" width="auto">
							<div id="bar-statu" class="bar-statu">
								<div class="statu-position">
									<p>
										<em>Position: </em>
										<i class="icon-angle-right"></i>
										<em id="cur-position">System Function</em>
									</p>	
								</div>
							 
								<div class="bar-btn-group">
									<div class="btn-group">
									
									<a class="btn" id="logoutBtn" href="javascript:void(0);" onclick="logout();"> <i
											class="icon-off"></i>退出登录 </a>
									
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="content">
				<div id="cont-mainboard" class="cont-mainboard">
					<table id="content-table">
						<tr class="table-info">
							<td class="tagName" width="30" id="table-menu">
								<iframe id="menuIframe" name="I1" height="100%" width="200px"
									src="<%=basePath%>SystemUserServlet?method=selectLoginerMenu"
									frameborder="0" scrolling="no">
									浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。
								</iframe>
							</td>
							<td class="tagCont" width="630" style="width: 100%;">
								<iframe name="I2" id="bodyIframe" height="100%" width="100%"
									style="overflow-x: hidden" frameborder="0"
									src="<%=basePath%>desk.jsp">
									浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。
								</iframe>
							</td>
						</tr>
					</table>
				</div><div class="footer" style="display:none;height:0px;">
					<!-- Program V1.0 -->
				</div>
			</div>
			</div><%--
	
		<script type="text/javascript"src="<%=basePath%>plug/lhgdialog/lhgdialog.js"></script>
		<script type="text/javascript"src="<%=basePath%>plug/js/notice.js"></script>
	
		--%>
<script type="text/javascript" src="plug/layer/layer.js"></script>
<script type="text/javascript">
 	function _ajax(msg,url,data,callback){
			
			if((!msg)||confirm(msg)){
					$.ajax({
					type:"post",
					url: url,
					dataType:'json',
					data:data,
					beforeSend:function(){
						showLoad();
					},
					success:function(result){
						if(callback){
							callback(result);
						}else{
							alert(result.state);
						}
					},error:function(){
						alert("Network connection is failed, please try later...");
					},complete:function(){
						closeLoad();
					}
					});
				
				}  
		}

var basepath = '<%=basePath%>';
$(function() {
		var t = window.setInterval("notice_Remind()",10000); //每十秒执行一下该方法
	
		var boardHeight = 0;
		var fixedHeight = 90+$(".footer").height(); //90+30
		boardHeight = $(window).height() - fixedHeight;
		$("#menuIframe,#bodyIframe").css("height", boardHeight);
		//$('').css('height', boardHeight);

		$('#menuBtn').bind('click', function() {
			var menu = $('#menuIframe');
			var width = $(menu).width();

			if (width != 0) {
				$(menu).animate( {
					width : '0'
				}, 200);
				$('#bar-title').animate( {
					width : '45'
				}, 200);
				$('#bar-title').parent().animate( {
					width : '45'
				}, 200);
			} else {
				$(menu).animate( {
					width : '200'
				}, 200);
				$('#bar-title').animate( {
					width : '200'
				}, 200);
				$('#bar-title').parent().animate( {
					width : '200'
				}, 200);
			}
		});
		
			//load group
		//loadGroupList();
			//
			//createBox();
		//3s一次
		//setInterval('createBox()','180000');
	});

window.onresize = function() {
	var boardHeight = 0;
	var fixedHeight = 90+$('.footer').height(); //60+30
	boardHeight = $(window).height() - fixedHeight;
	$('#menuIframe').css('height', boardHeight);
	$('#bodyIframe').css('height', boardHeight);
	
	if($(window).width() < 1000){
		$('#bar-title').parent().css({
			width : 45
		});
		$('#bar-title').css({
			width : 45
		});
		$('#menuIframe').css({
			width : 0
		});
	}else{
		$('#bar-title').parent().css({
			width : 200
		});
		$('#bar-title').css({
			width : 200
		});
		$('#menuIframe').css({
			width : 200
		});
	}
	

};
 
/**function changeGroup(id, name) {
	if (id == '') {
		$('#seleect_' + id).removeAttr("selected");
		alert("没有组别ID！");
		return false;
	}
	var oldselect = $('#login_group').html();
	$('#groupid').val(id);
	if (confirm("确定要切换到组别：" + name + "?")) {
		$('#changegroup_form').submit();
	} else {
		$('#login_group').html(oldselect);
		//$('#seleect_' + id).removeAttr("selected");
	}
}**/

function logout(){
	/**$.dialog.confirm("&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    		"Logout login?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
                function(){
    				top.location.href='logout.jsp';
                },function(){
				 $.dialog.tips("执行取消操作");
			},this);**/
			
			try{
				$.dialog.confirm("&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			    		"Logout login?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
			                function(){
			    				//清除cookie
			    				document.cookie="n_id="+"-1";
			    				top.location.href='logout.jsp';
			                },function(){
							 $.dialog.tips("执行取消操作");
						},this);
			}catch(e){
				if(confirm("Logout login?")){
					top.location.href='logout.jsp';
				}
			}
			
	
	
	
}
var _open = 0, _close =0;

function changeLanguage (local){
	  $.ajax({
		 url:"LanguageAction",
		 type:"post",
		 data:{"local":local},
		 success:function(date){
			if(date=="success"){
				// window.location.reload();
				$('#changegroup_form').submit();
			}else if(date=="error"){
				alert("非法请求!");
			}else{
				alert(date);
			}
		 },error:function(){
			 alert("Connection Exception!");
		 }
	  });
  }
  function showLoad(tipInfo) {
  	/*console.log(tipInfo+"type="+typeof tipInfo)
		if (tipInfo == undefined || tipInfo == '') {
			tipInfo = 'Loading...';
		}
		_open +=1;
		console.log("open="+_open);
		if($('#tipDiv').length > 0){
			$('#tipDiv').fadeIn();
			return false;
		}
		var eTip = document.createElement('div');
		eTip.setAttribute('id', 'tipDiv');
		eTip.innerHTML = '<div style="position:absolute;left: 50%; top: 35%; text-align: center; padding:5px 15px;"><img src=\'./css/022.gif\' />&nbsp;&nbsp;<span style=\'color:black; margin: 8px auto; font-size:1.1em;font-weight:bolder;\'>' + tipInfo + '</span></div>';
		try {
			document.body.appendChild(eTip);
		} catch (e) {
		}
		$('#tipDiv').fadeIn();
		console.log("call me")
		*/
		layer.load(2,{shade:0.3,  closeBtn: 0});
	} 

	function closeLoad() {
		/*$('#tipDiv').fadeOut();
		_close +=1
		console.log("close="+_close);*/
		setTimeout(function(){layer.closeAll('loading');},500);
	}
	
	document.cookie="n_id="+"-1";
	function notice_Remind(){
	  $.ajax({
		 url:"notice/NoticeServlet",
		 type:"post",
		 data:{"method":"new_notice"},
		 success:function(date){//
			var result=$.parseJSON(date);
			if(result.msg=="-1"){
				//alert("---木有消息啊---");
			}else{
				//alert("您有一条未读消息!是否查看?");
				var n_id=document.cookie.split(";")[1].split("=")[1];
				if(n_id==result.msg){
					//alert("该条消息已经弹出过窗口啦!")
				}else{
					/* 调用示例 */
					$.dialog.notice({
					    title: '个人消息',
					    width: 220,  /*必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致lhgDialog收缩 */
					    content: '<a onClick="readDetial('+result.msg+');" >您有一条未读消息!</a></br></br><button onClick="moreDetial()" >查看更多</button>',
					    time: 5
					});
					document.cookie="n_id="+result.msg;
				}
			}
		 },error:function(){
			 alert("Connection Exception!");
		 }
	  });
	}
	
	function readDetial(n_id){
		//console.log(n_id);
		$.ajax({
			 url: basepath+"/notice/NoticeServlet",
			 type: "post",
			 data: {"method":"selectSingle_Own","noticeId":n_id},
			 success:function(data){
			 	var url1 = basepath + "page/notice/notice_detail1.jsp";
	            infolhg(1024,600,url1,data);
			 }
		});		
	}
	
	function moreDetial(){
		//window.location.href="page/notice/notice.jsp";
		var url="page/notice/notice.jsp";
		window.parent.I2.location.href= url;//-->从一个框架跳转到 name="I2"的框架里。 
	}
	
	
$.dialog.notice = function( options ){
    var opts = options || {},
    api, aConfig, hide, wrap, top,
    duration = opts.duration || 800;
    var config = {
        id: 'Notice',
        left: '100%',
        top: '100%',
        fixed: true,
        drag: false,
        resize: false,
        init: function(here){
            api = this;
            aConfig = api.config;
            wrap = api.DOM.wrap;
            top = parseInt(wrap[0].style.top);
            hide = top + wrap[0].offsetHeight;
                        
            wrap.css('top', hide + 'px')
            .animate({top: top + 'px'}, duration, function(){
                opts.init && opts.init.call(api, here);
            });
        },
        close: function(here){
            wrap.animate({top: hide + 'px'}, duration, function(){
                opts.close && opts.close.call(this, here);
                aConfig.close = $.noop;
                api.close();
            });
            return false;
        }
    };
    for(var i in opts){
        if( config[i] === undefined ) config[i] = opts[i];
    }
    return $.dialog( config );
};

	
	
</script>
	</body>
</html>
