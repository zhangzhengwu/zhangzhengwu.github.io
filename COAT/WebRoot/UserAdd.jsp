<%@page contentType="text/html;charset=GBK"%>
<%@page import="dao.AdminDAO"%>
<%@page import="entity.Admin" %>
<%
/*如果还未登录系统*/
if (session.getAttribute("adminUsername")==null || session.getAttribute("adminUsername")=="")
	out.println("<script>alert('未登录系统！');top.location.href='signin.jsp';</script>");
else
{
	String adminUsername = session.getAttribute("adminUsername").toString();
	int isRoot = new AdminDAO().getIsRoot(adminUsername);
	if(0 == isRoot)
		out.print("<script>alert('你不是超级用户！');location.href='UserManage.jsp';</script>");
}
String adminUsername = request.getParameter("adminUsername"); 
String adminPassword = request.getParameter("adminPassword");
int isRoot = Integer.parseInt(request.getParameter("isRoot"));
Admin admin = new Admin();
admin.setAdminPassword(adminPassword);
admin.setAdminUsername(adminUsername);
admin.setIsRoot(isRoot);
int haseCode = new AdminDAO().getIsUserName(adminUsername);
if(haseCode>0){
	out.print("<script>alert('有相同的用户名了！');location.href='UserManage.jsp';</script>");	
}else{
	if(new AdminDAO().AddAdminInfo(admin)) {
		out.print("<script>alert('用户信息添加成功！');location.href='UserManage.jsp';</script>");
	} else {
		out.print("<script>alert('用户信息添加失败！');location.href='UserManage.jsp';</script>");
	}
}

%>