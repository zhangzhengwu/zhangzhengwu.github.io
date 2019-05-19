package servlet.Stationery;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import dao.QueryStationeryDao;
import dao.impl.QueryStationeryDaoImpl;
import entity.C_stationeryProduct;
/**
 * 文具表查询
 * @author sky
 *
 */
public class QueryStationeryServlet  extends HttpServlet {
	Logger log=Logger.getLogger(QueryStationeryServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String sc = request.getParameter("sc");
		PrintWriter out = response.getWriter();
		List<C_stationeryProduct> list = new ArrayList<C_stationeryProduct>();
		QueryStationeryDao qdao = new QueryStationeryDaoImpl();
		try{
			list = qdao.queryStationery(sc);
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
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