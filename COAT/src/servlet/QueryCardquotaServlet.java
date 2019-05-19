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

import dao.QueryCardQuotaDao;
import dao.impl.QueryCardQuotaDaoImpl;
import entity.CardquotaBean;

/**
 * 设置卡片配额
 * @author king.xu
 *
 */
public class QueryCardquotaServlet  extends HttpServlet {
Logger log=Logger.getLogger(QueryCardquotaServlet.class);
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	@SuppressWarnings( "unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		Page page=new Page();
		PrintWriter out = response.getWriter();
		String staffcode =request.getParameter("staffcode");
		String staffname=request.getParameter("staffname");
		List<CardquotaBean> quotalist = new ArrayList<CardquotaBean>();
		QueryCardQuotaDao qdao = new QueryCardQuotaDaoImpl();
		try{
			page.setAllRows(qdao.getRows(staffcode, staffname));//设置总行数
			page.setPageSize(20);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
			quotalist = qdao.queryCardQuotaList(staffcode, staffname, page);
			List list=new ArrayList();
			list.add(0,quotalist);//数据
		
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
			list.add(3,page.getAllRows());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
			
		}catch (Exception e) {
		log.error("在QueryCardquotaServlet中进行查询时出现"+e.toString());
		} finally{
			out.flush();
			out.close();
		} 
	}



}
