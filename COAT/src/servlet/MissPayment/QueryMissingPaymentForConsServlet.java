package servlet.MissPayment;

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

import util.DateUtils;
import util.Page;
import util.Util;
import dao.MissingPaymentDao;
import dao.impl.MissingPaymentDaoImpl;
import entity.Missingreport;

public class QueryMissingPaymentForConsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger log=Logger.getLogger(QueryMissingPaymentForConsServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("You have no legal power!");
		out.flush();
		out.close();
	}

	 
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String startdate=request.getParameter("startdate");
		String enddate=request.getParameter("enddate");
		String staffcode=request.getParameter("staffcode");
		String principal=request.getParameter("principal");
		String clientname=request.getParameter("clientname");
		String policyno=request.getParameter("policyno");
		String ctype=request.getParameter("ctype");
		String lastday=request.getParameter("lastday");
		
		MissingPaymentDao cgp=new MissingPaymentDaoImpl();
		Page page=new Page();
		try{
			if(Util.objIsNULL(staffcode)){
				if(Util.objIsNULL(request.getSession().getAttribute("convoy_username"))){
					staffcode="未知用户";
					out.print("用户权限异常");
				}else{
					staffcode=request.getSession().getAttribute("convoy_username").toString();
				}
				
			}
			page.setPageSize(20);//设置页面显示行数
			page.setAllRows(cgp.selectMissAcount(staffcode, principal, startdate, enddate, clientname, policyno,ctype,lastday));//设置总行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
			
			List list=new ArrayList();
			list.add(0,cgp.selectMissListFcons(staffcode, principal, startdate, enddate, clientname, policyno,ctype,lastday));//数据
		
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
			list.add(3,page.getAllRows());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Missingreport mp = new Missingreport ();
			mp.setUpddate(DateUtils.getNowDateTime());
			mp.setUpdname(staffcode);
			mp.setType("S");
			cgp.saveMissingReport(mp);
			
			out.flush();
			out.close();
		}
	
	}

}
