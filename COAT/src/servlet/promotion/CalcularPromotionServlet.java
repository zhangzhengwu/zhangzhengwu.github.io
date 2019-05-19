package servlet.promotion;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PromotionDao;
import dao.impl.PromotionDaoImpl;
 

public class CalcularPromotionServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		 try{
			 PromotionDao pd=new PromotionDaoImpl();
				out.print(pd.addPromotion());
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 out.flush();
			 out.close();
		 }
	}

}
