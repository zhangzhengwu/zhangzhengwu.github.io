package servlet.Stationery;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import dao.QueryStationeryDao;
import dao.impl.QueryStationeryDaoImpl;

public class QueryStationeryQuantityServlet extends HttpServlet {
	Logger log=Logger.getLogger(QueryStationeryServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String procode = request.getParameter("procode");
		PrintWriter out = response.getWriter();
		double num = 0;
		QueryStationeryDao qdao = new QueryStationeryDaoImpl();
		try{
			num = qdao.getQuantityByProcode(procode);
			out.print(num);
		}catch (Exception e) {
			log.error("在QueryStationeryServlet中对stationery进行查询时出现"+e.toString());
		} finally{
			out.flush();
			out.close();
		} 
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			this.doGet(request, response);
	}
}
