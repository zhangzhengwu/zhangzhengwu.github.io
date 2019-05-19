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

import util.Util;

import dao.PayerDao;
import dao.impl.PayerDaoImpl;
/**
 * 财务查询页面
 * @author kingxu
 *
 */
public class PayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(PayerServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("text/html;charset=utf-8");
		 PrintWriter out = response.getWriter();
		 String payer=request.getParameter("payer");
		 PayerDao payerDao=new PayerDaoImpl();
		 String EmployeeName="";
		 String title="";
		 String type=request.getParameter("type");
		 try{
		 if(type.equals("position")){
		 List<String> list = new ArrayList<String>();
			list.add("COT");
			list.add("CON");
			list.add("WMA");
			list.add("SWMA");
			list.add("PA");
			list.add("SA");
			list.add("AWMSM");
			list.add("SWMSM");
			list.add("WMSM");
			list.add("TMO");
			list.add("SNC");
			list.add("PRC");
			 title=payerDao.GetPosition(payer);
			 if (!Util.objIsNULL(title)) {
				for (int i = 0; i < list.size(); i++) {
					String liststr = (String) list.get(i);
					
					if (title.length() >= liststr.length()) {
						if(title.substring(0, liststr.length()).equals(liststr)){
							EmployeeName = "TRUE";
							 break; //有一个匹配 则跳出循环
						}else {
							EmployeeName = "FALSE";
						}
					}else {
						/*if(title.equals(liststr.substring(0, title.length()))){
							EmployeeName = "TRUE";
							 break;//有一个匹配 则跳出循环
						}else {
							EmployeeName = "FALSE";
						}*/
						EmployeeName = "FALSE";
					}
				}
			}
				out.print(EmployeeName);
		 }else{
			 out.print(payerDao.GetEnglishName(payer));
		 }
		
		 }catch(Exception e){
			 log.error("对验证Payer查询名称时出现异常："+e.toString());
		 }finally{
		 out.flush();
		 out.close();
		 }
	}

}
