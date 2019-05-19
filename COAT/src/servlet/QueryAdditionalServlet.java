package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Page;

import net.sf.json.JSONArray;

import dao.AdditionalDao;
import dao.impl.AdditionalDaoImpl;
import entity.QueryAdditional;

/**
 * 查詢QueryAdditional
 * 
 * @author king.xu
 * 
 */
public class QueryAdditionalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
Logger log=Logger.getLogger(QueryAdditionalServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/** ************************************************************************************************* */
		PrintWriter out = response.getWriter();
		Page page=new Page();
		String StaffNo = request.getParameter("StaffNo");
		String start_date=request.getParameter("start_date");
		String end_date=request.getParameter("end_date");
		String Valid = request.getParameter("Valid");
		List<QueryAdditional> additionlist = new ArrayList<QueryAdditional>();
		AdditionalDao ad = new AdditionalDaoImpl();
		try {
			page.setAllRows(ad.getRows(StaffNo, Valid, start_date, end_date));
			page.setPageSize(10);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
			additionlist = ad.getQueryAdditional(StaffNo, Valid, start_date,end_date,page);
			
				List list=new ArrayList();
				list.add(0,additionlist);//数据
				list.add(1,page.getAllPages());//总页数
				list.add(2,page.getCurPage());//当前页
				list.add(3,page.getAllRows());//总行数
			JSONArray jsons = JSONArray.fromObject(list);
			out.println(jsons.toString());
		} catch (Exception e) {
		log.error("在QueryAdditionalServlet中对nq_additional进行查询时出现"+e.toString());
		} finally {
			out.flush();
			out.close();
		}
	}
}