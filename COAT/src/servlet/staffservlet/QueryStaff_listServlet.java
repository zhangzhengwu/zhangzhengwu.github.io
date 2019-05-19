package servlet.staffservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import net.sf.json.JSONArray;
import util.ExcelTools;
import util.Page;
import dao.Staff_listDao;
import dao.impl.Staff_listDaoImpl;
import entity.Excel;
import entity.Staff_listBean;

public class QueryStaff_listServlet extends HttpServlet {
   
	Logger logger = Logger.getLogger(QueryStaff_listServlet.class);
	/**
	 * Constructor of the object.
	 */
	public QueryStaff_listServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * 
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	        doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
	    String name=request.getParameter("method").trim();
	    response.setContentType("text/html;charset=utf-8");
	    if(name.equals("select")){
	    	PrintWriter out = response.getWriter();
			Staff_listDao staffListDao=new Staff_listDaoImpl();
			Page page=new Page();
			 //获取查询条件
	        String staffcode=request.getParameter("staffcode").trim();
		    String companyName=request.getParameter("companyName").trim();
		    String deptid=request.getParameter("deptid").trim();
		    String staffname=request.getParameter("staffname").trim();
		    try{
		    	//查询总记录
		      int record=staffListDao.selectRow(staffcode, companyName, deptid, staffname);
		      page.setAllRows(record);
		      page.setPageSize(15);//页面显示记录条数
		      page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码

		      //查询所有信息
		      List<Staff_listBean> staffListBeans=staffListDao.findAll(staffcode, companyName, deptid, staffname, page);
		      //转为Json
		      List list=new ArrayList();
		      list.add(0,staffListBeans);
		      list.add(1,page.getAllPages());//总页数
			  list.add(2,page.getCurPage());//当前页
			  list.add(3,page.getAllRows());//总行数
			  list.add(4,page.getPageSize());
			  JSONArray jsons=JSONArray.fromObject(list);
			  out.print(jsons.toString());
		    }catch (Exception e) {
		    	e.printStackTrace();
				logger.error("Staff_list 查询Staff异常:"+e.toString());
			}finally{
				out.flush();
			}
		}
		else if(name.equals("export")){//Excel 数据导出
		  	//拿到查询条件
			String staffcode=request.getParameter("staffcode").trim();
		    String companyName=request.getParameter("companyName").trim();
		    String deptid=request.getParameter("deptid").trim();
		    String staffname=request.getParameter("staffname").trim();
		    Staff_listDao staffListDao=new Staff_listDaoImpl();
		    Excel excel=new Excel();
		    try{
		    	
		    //查询数据
		    List<Staff_listBean> list=staffListDao.exportDate(staffcode, companyName, deptid, staffname);
			//把数据交给Excel
		    excel.setExcelContentList(list);	
		    //设置Excel列头
		    excel.setColumns(new String[]{"Id","staffcode","company","deptid","staffname","englishname","grade","email","enrollmentDate","terminationDate"});
		    //属性字段名称
		    excel.setHeaderNames(new String[]{"Id","staffcode","company","deptid","staffname","englishname","grade","email","enrollmentDate","terminationDate"});
		   //sheet名称
		    excel.setSheetname("Staff_list");
		    //文件名称
			excel.setFilename("Staff_list"+System.currentTimeMillis());
			String filename=ExcelTools.getExcelFileName(excel.getFilename());
			response.setHeader("Content-Disposition", "attachment;filename=\""+filename+".xls"+"\"");
	       //生成EXCEL
			ExcelTools.createExcel(excel,response);
		    //out.print("导出成功！");
		    }catch (Exception e) {
		       logger.error("Staff_list数据导出失败"+e.toString());
			    e.printStackTrace();
		    }
			
		}
		
	}
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
