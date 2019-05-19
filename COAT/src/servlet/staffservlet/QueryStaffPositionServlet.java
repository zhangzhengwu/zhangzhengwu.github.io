package servlet.staffservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import net.sf.json.JSONArray;
import dao.QueryStaffPositionDao;
import dao.impl.QueryStaffPositionDaoImpl;
import entity.Position_Staff_list;
/**
 * STAFF 查询
 * @author kingxu
 *
 */
public class QueryStaffPositionServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(QueryStaffPositionServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		List<Position_Staff_list> list = new ArrayList<Position_Staff_list>();
		QueryStaffPositionDao qdao = new QueryStaffPositionDaoImpl();
		try{
			list = qdao.queryPosition();
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("获取Staff Position时出现："+e);
			out.print("error");
		} finally{
			out.flush();
			out.close();
		} 
	}



}
