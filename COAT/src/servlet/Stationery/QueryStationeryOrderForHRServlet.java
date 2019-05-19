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
import util.Page;
import dao.C_GetMarkPremiumDao;
import dao.QueryStationeryDao;
import dao.impl.C_GetMarkPremiumDaoImpl;
import dao.impl.QueryStationeryDaoImpl;

public class QueryStationeryOrderForHRServlet extends HttpServlet {

	/**
	 * QueryStaioneryOrder for HR
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("You have no legal power!");
		out.flush();
		out.close();
	}

	@SuppressWarnings({"all"})
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String ordercode=request.getParameter("ordercode");
		String productname=request.getParameter("productname");
		String clientname=request.getParameter("clientname");
		String clientcode=request.getParameter("clientcode");
		String location=request.getParameter("location");
		
		QueryStationeryDao cgp=new QueryStationeryDaoImpl();
		Page page=new Page();
		try{
			page.setAllRows(cgp.getRows(startDate, endDate, ordercode, productname, clientname, clientcode, location));//设置总行数
			page.setPageSize(15);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
			List list=new ArrayList();
			List list1 = cgp.findStationeryRecord(startDate, endDate, ordercode, productname, clientname, clientcode, location,page.getPageSize(),page.getCurPage());
			list.add(0,list1);//数据
			list.add(1,page.getCurPage());//当前页
			list.add(2,page.getAllPages());//总页数
			list.add(3,page.getAllRows());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}

}
