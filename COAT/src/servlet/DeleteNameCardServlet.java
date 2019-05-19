package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConsconvoyNamecardDao;
import dao.impl.ConsconvoyNamecardDaoImpl;
/**
 * staff 删除namecard功能已弃用
 * @author kingxu
 *
 */
public class DeleteNameCardServlet extends HttpServlet {

 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	 
	}

 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String staffcode=request.getParameter("staffcode");
		String urgentDate=request.getParameter("urgentDate");
		String table=request.getParameter("table");
		ConsconvoyNamecardDao cnd=new ConsconvoyNamecardDaoImpl();
		try{
			if(cnd.delNameCard(table, staffcode, urgentDate)>0){
				out.print("success");
			}else{
				out.print("error");
			}
		}catch(Exception e){
			e.printStackTrace();
			out.print("error");
		}finally{
			out.flush();
			out.close();
		}
		
	}

}
