<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
 
<html>
<head><title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
<!-- <script type="text/javascript" src="css/jquery-1.4.4.js"></script> -->
<link rel="stylesheet" href="css/jquery.mCustomScrollbar.css" />
<link rel="stylesheet" href="plug/css/bootstrap.min.css">
<link rel="stylesheet"href="plug/css/font-awesome.min.css">
<script src="css/jquery-1.11.0.min.js"></script>
<script src="css/jquery.mCustomScrollbar.concat.min.js"></script>
<!--[if lte IE 7]> 
<link rel="stylesheet"href="./plug/css/font-awesome-ie7.css">
<![endif]--> 
<link rel="stylesheet" href="css/layout.css">
</head>

<script type="text/javascript"> 
var he=document.documentElement.clientHeight-85;
document.write("<div id=tt style=height:"+he+";overflow:hidden>")

</script>
<body>
<div class="cont-nav">
	<div class="cont-user" id="userInfo"  style="cursor: pointer;">
		<img id="headimg_menu" onclick="showPersonalInfo();"
			src="images/default.jpg"  style="cursor: pointer;"></img>
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
	<ul id="menuUl">
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>AccessCard/PhotosSticker
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/AccessCardRequest.jsp">
						<i class="icon-caret-right"></i>Access Card/Photos Sticker Request
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryAccessCardRequest.jsp">
						<i class="icon-caret-right"></i>Access Card Query
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryAccessCardHR.jsp">
						<i class="icon-caret-right"></i>Access Card HR Query
					</span>
				</li>
			</ul>
		</li>
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>ADM Service
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/ADMservicesRequest.jsp">
						<i class="icon-caret-right"></i>ADM Service Request
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryADMservicesRequest.jsp">
						<i class="icon-caret-right"></i>ADM Service Query
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryADMservicesHR.jsp">
						<i class="icon-caret-right"></i>ADM Service HR Query
					</span>
				</li>
			</ul>
		</li>
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>SeatAssignment
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/SeatAssignment.jsp">
						<i class="icon-caret-right"></i>SeatAssignment Request
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QuerySeatAssignment.jsp">
						<i class="icon-caret-right"></i>SeatAssignment Query
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QuerySeatAssignmentHR.jsp">
						<i class="icon-caret-right"></i>SeatAssignment Query HR
					</span>
				</li>
			</ul>
		</li>
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Keys
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/KeyRequest.jsp">
						<i class="icon-caret-right"></i>Keys Request
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryKeyRequest.jsp">
						<i class="icon-caret-right"></i>Keys Query
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryKeyRequestHR.jsp">
						<i class="icon-caret-right"></i>Keys HR Query
					</span>
				</li>
			</ul>
		</li>
		<!-- 系统 -->
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>系统管理
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="ChangePassword.jsp">
						<i class="icon-caret-right"></i>修改密码
					</span>
				</li>
				<li>
					<span data-url="about.html">
						<i class="icon-caret-right"></i>关于系统
					</span>
				</li>
				<li>
					<span data-url="logout.jsp">
						<i class="icon-caret-right"></i>退出系统
					</span>
				</li>
			</ul>
		</li>
	</ul>
	<div style="clear: both;"></div>
</div>
<script type="text/javascript">
var menuStr = [];
$('body').ready(function() {
	$('.nav-submenu span').bind('click', function() {
		var cur_position = $('#cur-position', window.parent.document);
		var cur_menu = $(this).text();
		var url = $(this).attr('data-url');
		var id = $(this).attr('id');
		
		if (url.indexOf("logout.jsp")>-1) {
			if(confirm('确认退出系统吗？')){
				location.href = "logout.jsp";
			}
			//$('body', window.parent.document).addClass("modal-open");
			//$('#myModal', window.parent.document).css("display","block");
		} else {
			initMenu(menuStr);
			menuStr.push($(this));
			$(this).addClass('current');
			$(cur_position).text(cur_menu);

			$('#bodyIframe', window.parent.document).attr('src', url)
		}

	});
});
function initMenu(str) {
	for ( var i = 0; i < str.length; i++) {
		var temp = str[i];
		$(temp).removeClass('current');
	}
} 
function changeNav(item) {
	var nextOne = $(item).next();

	$('.nav-submenu').each(function() {
		$(this).hide();
	});
	$(nextOne).slideDown('fast').show();
}
</script>
<script type="text/javascript">
    (function($){
        $(window).load(function(){
        	var len = $('#menuUl > li').length;
        	var _height = ($(window).height()-85) * 0.85;

        	$('#menuUl').css('height',_height);
            $("#menuUl").mCustomScrollbar({
                axis:"y", // vertical and horizontal scrollbar
                theme:'minimal-dark'
            });
        });
        window.onresize = function() {
			var _height = ($(window).height()-85) * 0.85;
			$('#menuUl').css('height',_height);
		}

    })(jQuery);
</script>
</body>
</html>