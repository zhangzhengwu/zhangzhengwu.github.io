package servlet.staffservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import dao.QueryStaffProfessionalDao;
import dao.impl.QueryStaffProfessionalDaoImpl;
import entity.Professional_title_Staff;
/**
 * professional title查询
 * @author kingxu
 *
 */
public class QueryStaffProfessionalServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		List<Professional_title_Staff> list = new ArrayList<Professional_title_Staff>();
		QueryStaffProfessionalDao qdao = new QueryStaffProfessionalDaoImpl();
		try{
			list = qdao.queryProfessional();
			
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			out.flush();
			out.close();
		} 
	}



}
