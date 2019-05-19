package servlet.staffservlet;

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
import dao.QueryStaffRequstDao;
import dao.impl.QueryStaffRequstDaoImpl;
import entity.RequestStaffBean;
/**
 * 查询StaffRequst所需的数据
 * @author Wilson
 *
 */
public class QueryStaffRequstServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(QueryStaffRequstServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		 Page page=new Page();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		//String nocode  = request.getParameter("nocode");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String payer  = request.getParameter("payer");
		String ET=request.getParameter("ET");
		String layout=request.getParameter("layout");
		PrintWriter out = response.getWriter();
		List<RequestStaffBean> stafflist = new ArrayList<RequestStaffBean>();
		QueryStaffRequstDao qdao = new QueryStaffRequstDaoImpl();
		try{
			page.setAllRows(qdao.getRows(name, code, startDate, endDate, location, urgentCase,ET,layout, payer));//设置总行数
			page.setPageSize(10);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
 		 	 stafflist = qdao.queryRequstList(name, code, startDate, endDate, location, urgentCase,ET,layout, payer,page);
 		 	List list=new ArrayList();
			list.add(0,stafflist);//数据
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
			list.add(3,page.getAllRows());//总行数
			 JSONArray jsons=JSONArray.fromObject(list);
			 out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("QueryStaffRequstServlet 查询Staff异常:"+e);
		} finally{
			out.flush();
		}
	}



}
