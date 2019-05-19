package servlet.markpremium;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import dao.C_GetMarkPremiumDao;
import dao.impl.C_GetMarkPremiumDaoImpl;

public class QueryMarketingPremiumServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(QueryMarketingPremiumServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		try{
			String BLBZ=request.getParameter("BLBZ");
			C_GetMarkPremiumDao cgp=new C_GetMarkPremiumDaoImpl();
			JSONArray jsons=JSONArray.fromObject(cgp.findMarProduct(BLBZ));
			out.print(jsons.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
