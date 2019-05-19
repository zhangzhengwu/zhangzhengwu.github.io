package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import dao.QueryTRDao;
import dao.impl.QueryTRDaoImpl;
import entity.TrRegNo;
/**
 * 查询TR_LIST 到上传页面
 * @author king.xu
 *
 */

public class GetAllServlet extends HttpServlet {
	/**
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			doPost(request,response);
		}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		List<TrRegNo> list=new ArrayList<TrRegNo>();
		QueryTRDao query=new QueryTRDaoImpl();
		try{
			list=query.queryTR();
			if(list!=null){
				JSONArray jsons=JSONArray.fromObject(list);
				out.print(jsons.toString());
		
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
		
	}

}
