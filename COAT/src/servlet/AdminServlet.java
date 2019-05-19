package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import util.Page;
import dao.AdminDao02;
import dao.impl.AdminDao02Impl;
import entity.Admin;

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(AdminServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String method=request.getParameter("method");
		
		try{		
			if(method.equals("query"))
				query(request, response);
			else if(method.equals("del"))
				delete(request, response);
			else if(method.equals("add"))
				add(request, response);
			else if(method.equals("change"))
				change(request, response);
			else if(method.equals("verify"))
				verify(request, response);
			else if(method.equals("edit"))
				edit(request, response);
			else {
				throw new Exception("Unauthorized access!");
			}
				
		}catch (NullPointerException e) {
			log.error("AdminServlet==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("AdminServlet==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		} finally{
			method=null; 
		}
	}

	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	void query(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Page page=new Page();
		String adminUsername = request.getParameter("adminUsername");
		String pageNow=request.getParameter("pageNow");
		
		List<Admin> Admin = new ArrayList<Admin>();
		AdminDao02 ad = new AdminDao02Impl();
		try {
			page.setAllRows(ad.getRows(adminUsername));
			page.setPageSize(26);													//设置页面显示行数
			page.setCurPage(Integer.parseInt(pageNow));		//获取页面当前页码   
			Admin = ad.queryReqeustList(adminUsername, page);
			
			List list=new ArrayList();
			list.add(0,Admin);			//数据
			list.add(1,page.getAllPages());	//总页数
			list.add(2,page.getCurPage());	//当前页
			list.add(3,page.getAllRows());	//总行数	
			JSONArray jsons = JSONArray.fromObject(list);
			out.print(jsons.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.flush();
			out.close();
		}	
	}
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void delete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String adminUsername=request.getParameter("adminUsername");
		
		AdminDao02 del=new AdminDao02Impl();		
		del.DelAdminInfo(adminUsername);
			
		JSONArray jsons = JSONArray.fromObject(del);
		out.println(jsons.toString());
	}
	
	/**
	 * 添加
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void add(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		Admin admin = new Admin();
		AdminDao02 add=new AdminDao02Impl();
		
		String adminUsername = request.getParameter("adminUsername"); 
		String adminPassword = request.getParameter("adminPassword");
		int isRoot = Integer.parseInt(request.getParameter("isRoot"));
	
		admin.setAdminPassword(adminPassword);
		admin.setAdminUsername(adminUsername);
		admin.setIsRoot(isRoot);
		
				
		if(adminUsername.equals(admin.getAdminUsername())){			
			
			int haseCode = add.getIsUserName(adminUsername);   //判断是否相同用户名
			if(haseCode==0){
				add.AddAdmin(admin);					
				out.print("添加成功！");
			}else{
				out.print("Name不允许重复！");
			}
			/*JSONArray jsons = JSONArray.fromObject(add);
			System.out.println(jsons.toString());*/		
		}
	}
	
	/**
	 * 验证当前密码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void verify(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		try{
		HttpSession session=request.getSession();
		String nowPassword = request.getParameter("nowPassword");
		String adminUsername = session.getAttribute("adminUsername").toString();

		AdminDao02 verify=new AdminDao02Impl();
		boolean b=verify.checkLogin_AES_ENCRYPT(adminUsername,nowPassword);
		if(b){
			 out.print("1");
		}else{
			 out.print("2");
		}
		JSONArray jsons = JSONArray.fromObject(b);
		//System.out.println(jsons.toString());	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//out.flush();
			//out.close();
		}
	}	
	
	/**
	 * 设置新密码前密码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void change(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
		//String action = request.getParameter("action");
		
		HttpSession session=request.getSession();
	    String newPassword = request.getParameter("newPassword");
	    String adminUsername = session.getAttribute("adminUsername").toString();

	    Admin admin = new Admin();
 	    admin.setAdminPassword(newPassword);
 	    admin.setAdminUsername(adminUsername);
 	    AdminDao02 change=new AdminDao02Impl();
 	    JSONArray jsons = JSONArray.fromObject(admin);
 	    System.out.println(jsons.toString());
		  if(change.changePassword(admin)){
			  out.println("密码修改成功！");
			 }
		  else{
			  out.print("密码修改失败！");
			 }
		  
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void edit(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		//System.out.println("===========>>编辑");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			String isRoot=request.getParameter("isRoot");
			String adminUsername=request.getParameter("adminUsername");
			
			//System.out.println(isRoot+"------------"+adminUsername);
			
			Admin admin =new Admin ();
			admin.setAdminUsername(adminUsername);
			admin.setIsRoot(Integer.parseInt(isRoot));			
			AdminDao02 edit=new AdminDao02Impl();	
			edit.edit(admin);
			
			JSONArray jsons = JSONArray.fromObject(admin);
		 	out.print(jsons.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
		
	}
	
	
	
}
