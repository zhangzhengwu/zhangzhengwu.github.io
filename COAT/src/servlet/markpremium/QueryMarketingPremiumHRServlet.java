package servlet.markpremium;

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
import dao.C_GetMarkPremiumDao;
import dao.impl.C_GetMarkPremiumDaoImpl;

public class QueryMarketingPremiumHRServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger log=Logger.getLogger(QueryMarketingPremiumHRServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("You have no legal power!");
		out.flush();
		out.close();
	}

	 
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String ordercode=request.getParameter("ordercode");
		String ordertype=request.getParameter("ordertype");
		String productname=request.getParameter("productname");
		String clientname=request.getParameter("clientname");
		String clientcode=request.getParameter("clientcode");
		String location=request.getParameter("location");
		C_GetMarkPremiumDao cgp=new C_GetMarkPremiumDaoImpl();
		Page page=new Page();
		try{
			page.setPageSize(20);//设置页面显示行数
			page.setAllRows(cgp.getRows(startDate, endDate, ordercode, ordertype, productname, clientname, clientcode, location));//设置总行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码

			
			List list=new ArrayList();
			list.add(0,cgp.findMarketingPremium(startDate, endDate, ordercode, ordertype, productname, clientname, clientcode, location,page.getCurPage(),page.getPageSize()));//数据
		
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
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
