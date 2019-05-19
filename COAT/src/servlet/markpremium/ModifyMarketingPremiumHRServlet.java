package servlet.markpremium;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.C_GetMarkPremiumDao;
import dao.impl.C_GetMarkPremiumDaoImpl;

public class ModifyMarketingPremiumHRServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ModifyMarketingPremiumHRServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("You have no legal power!");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		C_GetMarkPremiumDao cmd=new C_GetMarkPremiumDaoImpl();
		String id=request.getParameter("id");
		String quantity=request.getParameter("quantity");
		String allprice=request.getParameter("allprice");
		String username="";
		try{
			username=request.getSession().getAttribute("adminUsername").toString(); 
			int num=cmd.updateMarketingPremiuimStock(id, quantity, allprice, username);
			if(num>0){
				out.print("success");
			}else{
				out.print("error");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("在  ModifyMarketingPremiumHRServlet 中修改时出现 :    ==="+e);
		}finally{
			out.flush();
			out.close();
		}
	
	}

}
