package servlet;

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

import dao.QueryCardConvoyDao;
import dao.impl.QueryConvoyCardDaoImpl;
import entity.RequestConvoyBean;
/**
 * 查询request convoy cons 所需的数据
 * @author Wilson
 * 2012年9月27日10:11:38
 */
public class QueryConvoyCardServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(QueryConvoyCardServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		//页面传值数据
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String shzt  = request.getParameter("shzt");
		
		PrintWriter out = response.getWriter();
		 Page page=new Page();
		List<RequestConvoyBean> lists = new ArrayList<RequestConvoyBean>();
		QueryCardConvoyDao qdao = new QueryConvoyCardDaoImpl();
		try{
			 page.setAllRows(qdao.getRows(name, code, startDate, endDate, location, urgentCase, shzt));
				page.setPageSize(10);//设置页面显示行数
				page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码

			 /**
			  * 执行查询
			  */
			 lists = qdao.queryRequstList(name,code,startDate,endDate,location,urgentCase,shzt,page);
			 List list=new ArrayList();
				list.add(0,lists);//数据
				list.add(1,page.getAllPages());//总页数
				list.add(2,page.getCurPage());//当前页
				list.add(3,page.getAllRows());//总行数
			 JSONArray jsons=JSONArray.fromObject(list);
			 out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("在Query Convoy Card Servlet中对Request进行查询时出现"+e.toString());
		} finally{
			out.flush();
			out.close();
		}
	}



}
