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
import dao.QueryChargeDao;
import dao.impl.QueryChargeDaoImpl;
import entity.Change_Record;
/**
 * 财务报表查询
 * @author king.xu
 *
 */
public class QueryChargeServlet  extends HttpServlet {
		Logger log=Logger.getLogger(QueryChargeServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		Page page=new Page();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String staffcode = request.getParameter("staffcode");
		List<Change_Record> chargelist = new ArrayList<Change_Record>();
		QueryChargeDao qdao = new QueryChargeDaoImpl();
		try{
			page.setAllRows(qdao.getRows(startDate, endDate, staffcode));
			page.setPageSize(15);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
			chargelist = qdao.queryChargeList(startDate, endDate, staffcode,page);
			List list=new ArrayList();
			list.add(0,chargelist);//数据
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
			list.add(3,page.getAllRows());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch (Exception e) {
			log.error("在QueryChargeServlet中查询财务时出现"+e.toString());
		} finally{
			out.flush();
			out.close();
		} 
	}



}
