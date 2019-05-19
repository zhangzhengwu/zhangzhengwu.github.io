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
import entity.C_marOrder;

public class NewQueryMarkPremiumServlet extends HttpServlet {

	Logger log = Logger.getLogger(NewQueryMarkPremiumServlet.class);
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String staffcode = request.getParameter("ordercode");
			String ordercode = request.getParameter("ordercode");
			String staffname=request.getParameter("clientname");
			String userType=request.getParameter("ordertype");
			String location=request.getParameter("location");

			C_GetMarkPremiumDao  getStationeryDao = new C_GetMarkPremiumDaoImpl();
			/******************************************分页代码***********************************************/
		//	String pageIndex = request.getParameter("curretPage");
			 Page page=new Page();
			 page.setAllRows(getStationeryDao.getOrdercount(staffcode,ordercode, startDate, endDate,staffname,userType,location,""));
				page.setPageSize(15);//设置页面显示行数
				page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			 
		 

			/************************************************************************************************/
			List<C_marOrder> list=new ArrayList<C_marOrder>();
			list=getStationeryDao.queryOrderList( staffcode,ordercode, startDate, endDate,staffname,userType,location,"",page.getPageSize(),page.getCurPage());
			
			List list1=new ArrayList();
			list1.add(0,list);
			list1.add(1,page.getAllPages());//总页数
			list1.add(2,page.getCurPage());//当前页
			list1.add(3,page.getAllRows());//总行数
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
			out.flush();
			out.close();
		 
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("查询 Tranee 时出现 ："+e);
		}

	}
	
 

}
