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

import dao.QueryRequstDao;
import dao.impl.QueryRequstDaoImpl;
import entity.RequestNewBean;
/**
 * 查询location所需的数据
 * @author king.xu
 *
 */
public class QueryRequstServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(QueryRequstServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
	
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		String nocode  = request.getParameter("nocode");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String payer  = request.getParameter("payer");
		String ET_select  = request.getParameter("ET");
		String layout_select  = request.getParameter("layout_select");
	
	
		
		List<RequestNewBean> requestList = new ArrayList<RequestNewBean>();
		QueryRequstDao qdao = new QueryRequstDaoImpl();
		
			Page page=new Page();
			
			page.setAllRows(qdao.queryRow(ET_select,layout_select,name,code,startDate,endDate,location,urgentCase,nocode,payer));//设置总行数
			page.setPageSize(10);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
			requestList = qdao.queryRequstList(ET_select,layout_select,name,code,startDate,endDate,location,urgentCase,nocode,payer,page);
			List list=new ArrayList();
			list.add(0,requestList);//数据
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
			list.add(3,page.getAllRows());//总行数
			 JSONArray jsons=JSONArray.fromObject(list);
		
			 out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
		logger.error("在QueryRequestServlet中对Request进行查询时出现"+e.toString());
		} finally{
			out.flush();
			out.close();
		}
	}



}
