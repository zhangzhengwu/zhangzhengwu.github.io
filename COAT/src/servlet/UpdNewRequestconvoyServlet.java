package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConsconvoyNamecardDao;
import dao.impl.ConsconvoyNamecardDaoImpl;

@SuppressWarnings("serial")
public class UpdNewRequestconvoyServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String adminUsername = request.getSession().getAttribute("adminUsername").toString();
		String StaffNo=request.getParameter("StaffNos");
		String urgentDate=request.getParameter("urgentDate");
		String remarks=request.getParameter("remarks");
 
		/*******************等待处理*****************************/
		 
		try{ 
			//保存完数据 在修改审核状态_Y
			ConsconvoyNamecardDao cdao = new ConsconvoyNamecardDaoImpl();
			int ok = cdao.updateConsShzt(adminUsername, urgentDate, StaffNo, "N",remarks);
			if(ok>0){
				System.out.println("审核拒绝成功！");
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			out.print("<script type='text/javascript'>alert('审核拒绝成功！');location.href='namecard/approveNameCard.jsp';</script>");
			out.flush();
			out.close();
		}
 
	}

}
