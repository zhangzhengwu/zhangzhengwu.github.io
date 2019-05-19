package servlet.Medical;

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

import util.Page;

import dao.QueryMedicalDao;
import dao.impl.QueryMedicalDaoImpl;
import entity.Medical;
/**
 * 查询Medical所需的数据
 * @author Wilson
 *
 */
public class QueryMedicalServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(QueryMedicalServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		Page page=new Page();
		PrintWriter out = response.getWriter();
		List<Medical> lists = new ArrayList<Medical>();
		QueryMedicalDao qdao = new QueryMedicalDaoImpl();
		try{
			page.setAllRows(qdao.getRows(name, code, startDate, endDate));
			page.setPageSize(15);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
 		 	lists = qdao.queryRequstList(name, code, startDate, endDate,page);
 		 	List list=new ArrayList();
			list.add(0,lists);//数据
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
			list.add(3,page.getAllRows());//总行数
			 JSONArray jsons=JSONArray.fromObject(list);
			 out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("QueryMedicalServlet 查询Medical异常:"+e);
		} finally{
			out.flush();
		}
	}



}
