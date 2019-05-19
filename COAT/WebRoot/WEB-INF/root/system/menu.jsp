<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title></title>
		<base href="<%=basePath%>"> 
		
		 
  <%--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		--%><link rel="stylesheet" href="<%=basePath%>css/jquery.mCustomScrollbar.css" />
		<link rel="stylesheet" href="<%=basePath%>plug/css/bootstrap.min.css">
		<link rel="stylesheet"href="<%=basePath%>plug/css/font-awesome.min.css">
		 
		<link rel="stylesheet" href="<%=basePath%>plug/css/layout.css">
		<!--[if lte IE 7]> 
		<link rel="stylesheet"href="<%=basePath%>plug/css/font-awesome-ie7.css">
		<![endif]-->
	</head>
	
 
	<script type="text/javascript">
var basepath = "<%=basePath%>";
function showPersonalInfo() {
	$('#bodyIframe', window.parent.document).attr('src',
			basepath + 'SystemUserServlet?method=getPersonalInfo');
}
//var he = document.documentElement.clientHeight - 85;
//document.write("<div id=tt style=height:100%;overflow:hidden>");
function showsubmenu(sid) {
	$(".childmenu[id='submenu" + sid + "']").show();
	$(".childmenu[id!='submenu" + sid + "']").hide();
	
}
</script>

	<body>
		<div class="cont-nav" >
			<div class="cont-user" id="userInfo"  style="cursor: pointer;">
				<img id="headimg_menu" onclick="showPersonalInfo();"
					src=""  style="cursor: pointer;"></img>
				<div class="user-info">
					<div class="welcome">
						Welcome,
					</div>
					<div class="username"><%=session.getAttribute("adminUsername")%></div>
				</div>
				<div class="user-statu">
					<i class="icon-circle" style="color: green"></i>
				</div>
				<div style="clear: both;"></div>
			</div>
			<c:choose>
				<c:when test="${fn:length(menuList)!=0}">
					<ul id="menuUl">
						<c:forEach var="item" items="${menuList}" varStatus="itemStu_index">
							<li class="">
								<a class="" href="javascript:void(0);" onclick="changeNav(this)">
								 <i	class="icon-list-alt"></i> ${item.menuname}(${fn:length(item.child)})</a>
								
									<c:if test="${fn:length(item.child)!=0}">
										<ul class="nav-submenu">
											<c:forEach var="son" items="${item.child}">
												<li>
													<span title="${son.menuname}" target="${son.remark }" data-url="<%=basePath%>getMenuCompetence?menuname=${son.menuAction}&menuid=${son.menuid}">
																<i class="icon-caret-right"></i>${son.menuname}</span>
												</li>
											
											
											</c:forEach>
										
										</ul>
									
									</c:if>
								
							</li>
						
						</c:forEach>
					
					</ul>
				
				
				</c:when>
				<c:otherwise>
					<ul>
						<li class="">
							No menu for show!
						</li>
					</ul>
				</c:otherwise>
				
			</c:choose> 
			
			
			
			 <div style="clear: both;"></div>
		</div>
		
	<script type="text/javascript" src="<%=basePath%>plug/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>plug/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>css/jquery.mCustomScrollbar.concat.min.js"></script>
		
<script  type="text/javascript">
var menuStr = [];
$(function() {
	
	$('.nav-submenu span').bind('click', function() {
		var cur_position = $('#cur-position', window.parent.document);
		var cur_menu = $(this).text();
		var url = $(this).attr('data-url');
		var id = $(this).attr('id');
		if (url.indexOf("logout.jsp")>-1) {
			//$('body', window.parent.document).addClass("modal-open");
			//$('#myModal', window.parent.document).css("display","block");
			if(confirm("确定退出登录?")){
				$('#bodyIframe', window.parent.document).attr('src', url);
			}
		}else if(url.indexOf("F.jsp")>-1){
			window.open(url);
		} else {
			initMenu(menuStr);
			menuStr.push($(this));
			$(this).addClass('current');
			$(cur_position).text(cur_menu);
			$('#bodyIframe', window.parent.document).attr('src', url);
		}

	});
	
	
	
	
	$("table.tableinfo tr:odd").each(function() {
		$(this).css( {
			'background-color' : '#005386'
		});
	});
	$("#headimg_menu").attr("src","<%=basePath%>${loginUser.headimage}");
	$("#headimg_menu").error(function(){
		this.src="<%=basePath%>/plug/img/1.jpg";
	});
});
function changeNav(item) {
	var nextOne = $(item).next();

	$('.nav-submenu').each(function() {
		$(this).hide();
	});
	$(nextOne).slideDown('fast').show();

}
function initMenu(str) {
	for ( var i = 0; i < str.length; i++) {
		var temp = str[i];
		$(temp).removeClass('current');
	}
}


</script>
<script type="text/javascript">
    (function($){
        $(window).load(function(){
        	//85px为头像区域的高度
        	var _height = ($(window).height()-85)*0.96;
        	//alert(_height);
        	$('#menuUl').css('height',_height);
            $("#menuUl").mCustomScrollbar({
                axis:"y", // vertical and horizontal scrollbar
                theme:'minimal-dark'
            });
        });
        window.onresize = function() {
			var _height = ($(window).height()-85)*0.85;
			$('#menuUl').css('height',_height);
		};

    })(jQuery);
</script>
	</body>
</html>