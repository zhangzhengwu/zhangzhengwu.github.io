package servlet.Stationery;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import servlet.markpremium.ModifyMarketingPremiumHRServlet;
import dao.C_GetMarkPremiumDao;
import dao.QueryStationeryDao;
import dao.impl.C_GetMarkPremiumDaoImpl;
import dao.impl.QueryStationeryDaoImpl;

public class MotifyStaioneryOrderHRServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * HR帮忙更改数量servlet
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(MotifyStaioneryOrderHRServlet.class);
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
		QueryStationeryDao cgp=new QueryStationeryDaoImpl();
		String id=request.getParameter("id");
		String quantity=request.getParameter("quantity");
		String allprice=request.getParameter("allprice");
		String username="";
		try{
			username=request.getSession().getAttribute("adminUsername").toString(); 
		//	System.out.println(id+"=="+quantity+"=="+allprice+"=="+username);
			int num = cgp.updateStationeryStock(id, quantity, allprice, username);
			if(num>0){
				out.print("success");
			}else{
				out.print("error");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("在  MotifyStaioneryOrderHRServlet 中修改时出现 :    ==="+e);
			out.print("error");
		}finally{
			out.flush();
			out.close();
		}
	
	}

}
