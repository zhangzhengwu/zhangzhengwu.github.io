<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="dao.AdminDAO" %>
<%@page import="entity.Admin" %>
<%

String adminUsername = session.getAttribute("adminUsername").toString();
int isRoot = new AdminDAO().getIsRoot(adminUsername);
/*out.println("<script>alert("+isRoot+");</script>");
out.print("<script>alert("+isRoot+")</script>");*/
if(1== isRoot)
	isRoot=1;
else if(0== isRoot)
	isRoot=0;
else if(3 == isRoot)
	out.print("<script>location.href='menu_HKHR.jsp';</script>");
else if(4 == isRoot)
	out.print("<script>location.href='menu_SZMISSING.jsp';</script>");
else if(98 == isRoot)
	out.print("<script>location.href='menu_sytem_UAT.jsp';</script>");
else if(99 == isRoot)
	out.print("<script>location.href='menu_UAT.jsp';</script>");
else if(100 == isRoot)
	out.print("<script>location.href='menu_SZO.jsp';</script>");
else if(101 == isRoot)
	out.print("<script>location.href='menu_MANAGER.jsp';</script>");
else{
	
	out.println("<script>alert('您没有访问权限，请联系管理员!');top.location.href='signin.jsp';</script>");
}

%>
<!DOCTYPE HTML>
 
<html>
<head><title></title>
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
document.write("<div id=tt style=height:100%;overflow:hidden>");
</script>
<body>
<div class="cont-nav">
	<div class="cont-user" id="userInfo"  style="cursor: pointer;">
		<img id="headimg_menu" onclick="showPersonalInfo();"
			src="<%if(adminUsername.equals("jackieli")){ %>upload/personalHead/j.png<%}else{%>images/default.jpg<%} %>"  style="cursor: pointer;"></img>
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
				<i class="icon-list-alt"></i>Name Card Approve
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="namecard/approveNameCard.jsp">
						<i class="icon-caret-right"></i>Cons NameCard
					</span>
				</li>
				<li>
					<span data-url="namecard/approveNameCardState.jsp">
						<i class="icon-caret-right"></i>Cons NameCard State
					</span>
				</li>
				<li>
					<span data-url="namecard/staff/approveStaffCard.jsp">
						<i class="icon-caret-right"></i>Staff NameCard
					</span>
				</li>
				<li>
					<span data-url="namecard/staff/queryStaffNameCardState.jsp">
						<i class="icon-caret-right"></i>Staff NameCard State
					</span>
				</li>
				<li>
					<span data-url="page/notice/notice.jsp">
						<i class="icon-caret-right"></i>Notice
					</span>
				</li>
			</ul>
		</li>
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Consultant Card
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="page/additionquota/queryAdditional.jsp">
						<i class="icon-caret-right"></i>Additional Quota
					</span>
				</li>
				<li>
					<span data-url="cons_list.jsp">
						<i class="icon-caret-right"></i>cons_list
					</span>
				</li>
				<li><%--
					<span data-url="addNameCard.jsp">
					--%><span data-url="page/namecard/addNameCard.jsp">
						<i class="icon-caret-right"></i>New Name Card
					</span>
				</li>
				<li>
					<%--<span data-url="queryNameCard.jsp">
					--%><span data-url="page/namecard/queryNameCard.jsp">
						<i class="icon-caret-right"></i>Name Card Check
					</span>
				</li> 
				<li>
					<span data-url="page/additionquota/queryCardQuota.jsp">
						<i class="icon-caret-right"></i>Quota Checking
					</span>
				</li>
				<li>
					<span data-url="payer.jsp">
						<i class="icon-caret-right"></i>Charge Record
					</span>
				</li>
			</ul>
		</li>

		<!-- 2 -->
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Medical Claim
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="MddicalClaim/addMedical.jsp">
						<i class="icon-caret-right"></i>Medical Claim
					</span>
				</li>
				<li>
					<span data-url="queryMedical.jsp">
						<i class="icon-caret-right"></i>Medical for FAD
					</span>
				</li>
				<li>
					<span data-url="MddicalClaim/QueryMedicalConsultant.jsp">
						<i class="icon-caret-right"></i>Medical for CONS
					</span>
				</li>
			</ul>
		</li>
		<!-- 3 -->
		<li class="" style='display: none;'>
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Staff Medical Claim
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="MddicalClaim/addMedicalStaff.jsp">
						<i class="icon-caret-right"></i>Medical Claim
					</span>
				</li>
				<li>
					<span data-url="MddicalClaim/QueryMedicalStaff.jsp">
						<i class="icon-caret-right"></i>Medical for Staff
					</span>
				</li>
			</ul>
		</li>
		<!-- 4 -->
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Staff Name Card
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="page\staffnamecard\addStaffCard.jsp">
						<i class="icon-caret-right"></i>New Name Card
					</span>
				</li>
				<li>
					<span data-url="queryStaffCard.jsp">
						<i class="icon-caret-right"></i>Name Card Check
					</span>
				</li>
				<li>
					<span data-url="queryStaff_list.jsp">
						<i class="icon-caret-right"></i>Staff_list
					</span>
				</li>
			</ul>
		</li>
		<!-- 5 -->
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Attendance
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="page\attendance\cacularAttendance.jsp">
						<i class="icon-caret-right"></i>Calculate Attendance
					</span>
				</li>
				<li>
					<span data-url="Emap/ConsultantReport.jsp">
						<i class="icon-caret-right"></i>CONS MAP
					</span>
				</li>
				<li>
					<span data-url="Emap/TraineeReport.jsp">
						<i class="icon-caret-right"></i>Trainee MAP
					</span>
				</li>
				<li>
					<span data-url="Emap/OtherConsultantReport.jsp">
						<i class="icon-caret-right"></i>CONS Other
					</span>
				</li>
			</ul>
		</li>
		<!-- 6 -->
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Macau Consultant
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Macau/addNameCard.jsp">
						<i class="icon-caret-right"></i>New Name Card
					</span>
				</li>
				<li>
					<span data-url="Macau/cons_macau.jsp">
						<i class="icon-caret-right"></i>Macau ConsList
					</span>
				</li>
				<li>
					<span data-url="Macau/queryNameCard.jsp">
						<i class="icon-caret-right"></i>Name Card check
					</span>
				</li>
				<li>
					<span data-url="Macau/payer.jsp">
						<i class="icon-caret-right"></i>Charge Record
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
					<span data-url="index_new.jsp">
						<i class="icon-caret-right"></i>新上传文件
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/QueryMarkPremiumHR.jsp">
						<i class="icon-caret-right"></i>MarkPremium For ADM
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/QueryStationeryOrderHR.jsp">
						<i class="icon-caret-right"></i>Stationery For ADM
					</span>
				</li>
				<li>
					<span data-url="index.jsp">
						<i class="icon-caret-right"></i>文件上传
					</span>
				</li>
				<li>
					<span data-url="UserManage.jsp">
						<i class="icon-caret-right"></i>用户管理
					</span>
				</li>
				<li>
					<span data-url="page/loginrecord/loginRecord.jsp">
						<i class="icon-caret-right"></i>用户登录退出记录
					</span>
				</li>
				<li>
					<span data-url="page/operationrecord/operationrecord.jsp">
						<i class="icon-caret-right"></i>用户访问记录
					</span>
				</li>
				<li>
					<span data-url="location.jsp">
						<i class="icon-caret-right"></i>Location
					</span>
				</li>
				<li>
					<span data-url="namecard/hr/staff_title_list.jsp">
						<i class="icon-caret-right"></i>Position
					</span>
				</li>
				<li>
					<span data-url="namecard/hr/department.jsp">
						<i class="icon-caret-right"></i>Department
					</span>
				</li>
				<li>
					<span data-url="namecard/hr/departHead.jsp">
						<i class="icon-caret-right"></i>Department Head
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
				
				<!-- ------------------------------------- -->
				<%--<li>
					<span data-url="Coat_Request/QueryReporting.jsp">
						<i class="icon-caret-right"></i>9
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryRequestForCompanyAssetHR.jsp">
						<i class="icon-caret-right"></i>10
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryRequestForRoomSettingHR.jsp">
						<i class="icon-caret-right"></i>11
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QuerySeatAssignmentHR.jsp">
						<i class="icon-caret-right"></i>12
					</span>
				</li>
				--%><!-- ------------------------------------- -->
			</ul>
		</li>
		<!-- 7 -->
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>COAT_Request
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="Coat_Request/RequestForRoomSetting.jsp">
						<i class="icon-caret-right"></i>Request for Room Setting
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/RequestPIBAStudyNote.jsp">
						<i class="icon-caret-right"></i>Request PIBA Study Note
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryRequestForRoomSetting.jsp">
						<i class="icon-caret-right"></i>Query Request for Room Setting
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/addStationery.jsp">
						<i class="icon-caret-right"></i>Add Stationery
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/QueryStationeryOrder.jsp">
						<i class="icon-caret-right"></i>Query Stationery Order
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/addStationery_staff.jsp">
						<i class="icon-caret-right"></i>Add Stationery Staff
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/QueryStationeryStaffOrder.jsp">
						<i class="icon-caret-right"></i>Query Stationery Staff Order
					</span>
				</li>
				<li>
					<span data-url="COAT_Order/QueryStationeryOrderHR.jsp">
						<i class="icon-caret-right"></i>Query Stationery Order HR
					</span>
				</li>
				<!-- -----------------------------Test Start-------------------------------------- -->
				<%--<li>
					<span data-url="Coat_Request/QueryAccessCardHR.jsp">
						<i class="icon-caret-right"></i>1
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryAccessCardRequest.jsp">
						<i class="icon-caret-right"></i>2
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryAccessCardSZ.jsp">
						<i class="icon-caret-right"></i>3
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryADMservicesHR.jsp">
						<i class="icon-caret-right"></i>4
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryADMservicesRequest.jsp">
						<i class="icon-caret-right"></i>5
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryEPaymentRequestHR.jsp">
						<i class="icon-caret-right"></i>6
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryKeyRequestHR.jsp">
						<i class="icon-caret-right"></i>7
					</span>
				</li>
				<li>
					<span data-url="Coat_Request/QueryRecruitmentHR.jsp">
						<i class="icon-caret-right"></i>8
					</span>
				</li>
				--%><!-- -----------------------------Test End-------------------------------------- -->
			</ul>
		</li>
		<li class="">
			<a class="" href="#" onclick="changeNav(this)">
				<i class="icon-list-alt"></i>Pick Up Record
			</a>
			<ul class="nav-submenu">
				<li>
					<span data-url="page/pickuprecord/queryPickUpRecord.jsp">
						<i class="icon-caret-right"></i>Pick Up List
					</span>
				</li>
				<li>
					<span data-url="page/pickuprecord/queryPickUpRecord_Self.jsp">
						<i class="icon-caret-right"></i>Self Pick Up List
					</span>
				</li>
				<li>
					<span data-url="page/pickuprecord/index.jsp">
						<i class="icon-caret-right"></i>Document Scanning
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

			
			if(url=="page/pickuprecord/index.jsp"){
				window.open("page/pickuprecord/index.jsp","pickup");
			}else{
				$('#bodyIframe', window.parent.document).attr('src', url);
			}
			
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
        	//85px为头像区域的高度
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