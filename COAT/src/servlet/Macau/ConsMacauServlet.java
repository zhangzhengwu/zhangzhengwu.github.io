package servlet.Macau;

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
import dao.ConsMacauDao;
import dao.impl.ConsMacauDaoImpl;
import entity.ConsListMacau;

/**
 * 澳门conslist
 * @author hugoplhuang
 *
 */
public class ConsMacauServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ConsMacauServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
		
		try{		
			if(method.equals("query"))
				query(request, response);
			else {
				throw new Exception("Unauthorized access!");
			}
				
		}catch (NullPointerException e) {
			log.error("ConsMacauServlet==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ConsMacauServlet==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		} finally{
			method=null; 
		} 	
	}
	
	/**
	 * 澳门顾问查询
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void query(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		
		PrintWriter out = response.getWriter();
		Page page=new Page();
		
		String employeeId = request.getParameter("employeeId");
		String c_Name=request.getParameter("c_Name");
		String HKID=request.getParameter("HKID");
		String recruiterId = request.getParameter("recruiterId");
		
		List<ConsListMacau> macauCons = new ArrayList<ConsListMacau>();
		ConsMacauDao cm = new ConsMacauDaoImpl();
		try {
			page.setAllRows(cm.getRows(employeeId, c_Name, HKID, recruiterId));
			page.setPageSize(20);													//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));		//获取页面当前页码   
			macauCons = cm.queryReqeustList(employeeId, c_Name, HKID, recruiterId, page);
			
			List list=new ArrayList();
			list.add(0,macauCons);			//数据
			list.add(1,page.getAllPages());	//总页数
			list.add(2,page.getCurPage());	//当前页
			list.add(3,page.getAllRows());	//总行数	
			JSONArray jsons = JSONArray.fromObject(list);
			out.print(jsons.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.flush();
			out.close();
		}
		
	}

	
}
