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

import dao.MedicalDao;
import dao.impl.MedicalDaoImpl;
import entity.Medical;

public class QueryMedicalConsultantServlet extends HttpServlet {
 Logger log=Logger.getLogger(QueryMedicalConsultantServlet.class);
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
 
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String start_date=request.getParameter("startDate");
		String end_date=request.getParameter("endDate");
		String Staff_Code=request.getParameter("code");
		String FullName=request.getParameter("name");
		 MedicalDao md= new MedicalDaoImpl();
		 Page page=new Page();
		 List<Medical> lists = new ArrayList<Medical>();
		 try{
			 page.setAllRows(md.getRow(start_date, end_date, Staff_Code, FullName));
				page.setPageSize(10);//设置页面显示行数
				page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
			 lists=md.queryMedicalConsultant(start_date, end_date, Staff_Code, FullName,page);
				List list=new ArrayList();
				list.add(0,lists);//数据
				list.add(1,page.getAllPages());//总页数
				list.add(2,page.getCurPage());//当前页
				list.add(3,page.getAllRows());//总行数
		
			 JSONArray jsons=JSONArray.fromObject(list);
			 out.print(jsons.toString());
		 }catch(Exception e){
			 e.printStackTrace();
			 log.error(request.getSession().getAttribute("adminUsername").toString()+"在QueryMedicalConsultantServelet中查询MedicalConsultant时出现："+e.toString());
		 }finally{
			 out.flush();
			 out.close();
		 }
		 
	}

}
