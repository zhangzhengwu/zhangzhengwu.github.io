<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
 
<html>
<head><title></title></head>
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
				<i class="icon-list-alt"></i>Marketing Premium
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="COAT_Order/MarketingPremium_list.jsp">
						<i class="icon-caret-right"></i>Marketing Premium Product
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/addMarketingPremium.jsp">
						<i class="icon-caret-right"></i>Marketing Premium Request
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/addMarketingPremium_staff.jsp">
						<i class="icon-caret-right"></i>Marketing Premium Staff Request
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/QueryMarkPremiumOrder.jsp">
						<i class="icon-caret-right"></i>Marketing Premium Query
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/QueryMarkPremiumHR.jsp">
						<i class="icon-caret-right"></i>Marketing Premium HR Query
					</span>
				</li>
			</ul>
		</li>
		<!-- 2 -->
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Stationery
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="COAT_Order/addStationery.jsp">
						<i class="icon-caret-right"></i>Stationery Request
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/addStationery_staff.jsp">
						<i class="icon-caret-right"></i>Stationery Staff Request
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/QueryStationeryOrder.jsp">
						<i class="icon-caret-right"></i>Stationery Query
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/QueryStationeryStaffOrder.jsp">
						<i class="icon-caret-right"></i>Stationery Staff Query
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/QueryStationeryOrderHR.jsp">
						<i class="icon-caret-right"></i>Stationery HR Query
					</span>
				</li>
			</ul>
		</li>
		<!-- 3 -->
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>RoomSetting
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/RequestForRoomSetting.jsp">
						<i class="icon-caret-right"></i>RoomSetting Request
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryRequestForRoomSetting.jsp">
						<i class="icon-caret-right"></i>RommSetting Query
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryRequestForRoomSettingHR.jsp">
						<i class="icon-caret-right"></i>RommSetting HR Query
					</span>
				</li>
			</ul>
		</li>
		<!-- 4 -->
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
		<!-- 5 -->
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
		<!-- 6 -->
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Company Asset
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/CompanyAssetItem.jsp">
						<i class="icon-caret-right"></i>Company Asset Item
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/RequestForCompanyAsset.jsp">
						<i class="icon-caret-right"></i>Company Asset Request
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryRequestForCompanyAsset.jsp">
						<i class="icon-caret-right"></i>Company Asset Query
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryRequestForCompanyAssetHR.jsp">
						<i class="icon-caret-right"></i>Company Asset HR Query
					</span>
				</li>
			</ul>
		</li>
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>ePayment
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/ePaymentList.jsp">
						<i class="icon-caret-right"></i>ePayment List
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/ePaymentRequest.jsp">
						<i class="icon-caret-right"></i>ePayment Request
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryEPaymentRequest.jsp">
						<i class="icon-caret-right"></i>ePayment Query
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryEPaymentRequestHR.jsp">
						<i class="icon-caret-right"></i>ePayment HR Query
					</span>
				</li>
			</ul>
		</li>
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Advertising
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/RequestForRecruitment.jsp">
						<i class="icon-caret-right"></i>Advertising Request
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryRecruitment.jsp">
						<i class="icon-caret-right"></i>Query Advertising
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryRecruitmentHR.jsp">
						<i class="icon-caret-right"></i>Query Advertising HR
					</span>
				</li>
			</ul>
		</li>
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>PIBA Study Note
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/QueryPIBAStudyNote.jsp">
						<i class="icon-caret-right"></i>PIBA HR Query 
					</span>
				</li>
				<%--<li>
					<span data-url="Coat_Request/RequestPIBAStudyNote.jsp">
						<i class="icon-caret-right"></i>New PIBA
					</span>
				</li>--%>
			</ul>
		</li>
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Reporting
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/QueryReporting.jsp">
						<i class="icon-caret-right"></i>QueryReporting
					</span>
				</li>
			</ul>
		</li>
		<!-- 7 -->
		
		<!-- 系统 -->
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>系统管理
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="namecard/hr/department.jsp">
						<i class="icon-caret-right"></i>NameCard Department
					</span>
				</li>
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