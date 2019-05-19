package servlet.request;

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

import dao.C_GetMarkPremiumDao;
import dao.EPaymentDao;
import dao.impl.C_GetMarkPremiumDaoImpl;
import dao.impl.EPaymentDaoImpl;
import entity.C_EPayment_List;
import entity.C_Payment;
import entity.C_marOrder;
import entity.C_marProduct;

import util.Page;
import util.Util;

public class MarketingPremiumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(MarketingPremiumServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doPost(request,response);
	}
	String user=null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");

		try{
			user=(String) request.getSession().getAttribute("adminUsername");//request.getSession().getAttribute("convoy_username").toString();
			if(method.equals("select"))
				select(request, response);
			else if(method.equals("ready"))
				ready(request, response);
			else if(method.equals("VOID"))
				VOID(request, response);
			else if(method.equals("complete"))
				complete(request, response);
			else if(method.equals("delete"))
				delete(request,response);
			else if(method.equals("queryProduct"))
				queryProduct(request,response);
			else {
				throw new Exception("Unauthorized access!");
			}
			/*else if(method.equals("down"))
				down(request, response);
			else if(method.equals("add"))
				add(request, response);
			else if(method.equals("detail"))
				Detail(request,response);
			else if(method.equals("complete"))
				complete(request,response);
			*/
		}catch (NullPointerException e) {
			log.error("Marketing Premium==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Marketing Premium==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.toString());
		} finally{
			method=null; 
		} 


	}
	
	/**
	 * 查询产品信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	void queryProduct(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		C_GetMarkPremiumDao dao = new C_GetMarkPremiumDaoImpl();
		
		Page page=new Page();
		List<C_marProduct> list=new ArrayList<C_marProduct>();
		List list1=new ArrayList();
		try{
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String productcode=request.getParameter("productcode");
			String productname=request.getParameter("productname");
			String BLBZ=request.getParameter("BLBZ");
			
			/******************************************分页代码***********************************************/
			    page.setAllRows(dao.getAllRow(productcode,productname, startDate, endDate,BLBZ));//总的行数
				page.setPageSize(15);//设置页面显示行数
				page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			/************************************************************************************************/
			list=dao.queryProduct(productcode,productname, startDate, endDate,BLBZ,page.getCurPage(),page.getPageSize());
			
			list1.add(0,list);
			list1.add(1,page.getAllPages());//总页数
			list1.add(2,page.getCurPage()); //当前页
			list1.add(3,page.getAllRows()); //总行数
			
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("selects==>Search Exception"+e);
		}finally{
			 dao =null;
			 page=null;
			 list=null;
			 list1=null;
			out.flush();
			out.close();
		}
	}
	
	
	void delete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		String ordercode = request.getParameter("ordercode");
		int num = 0;
		C_GetMarkPremiumDao nqdao = new C_GetMarkPremiumDaoImpl();
		try{
			num = nqdao.delMarkOrder(ordercode,user);
			if(num < 0){
				out.print("error");
			}else {
				out.print("success");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Marketing Premium==>Delete操作出现异常"+e);
			out.print("Exception:"+e.getMessage());
		} finally{
			out.flush();
			out.close();
		}
		
	}
	
	void VOID(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		String ordercode = request.getParameter("ordercode");
		int num = 0;
		C_GetMarkPremiumDao nqdao = new C_GetMarkPremiumDaoImpl();
		try{
			num = nqdao.VOID(ordercode,user);
			if(num < 0){
				out.print("error");
			}else {
				out.print("success");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Marketing Premium==>VOID操作出现异常"+e);
			out.print("Exception:"+e.getMessage());
		} finally{
			out.flush();
			out.close();
		}
		
	}
	
	
	
/**
 * 准备
 * @param request
 * @param response
 */
	void ready(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		C_GetMarkPremiumDao cmp=new C_GetMarkPremiumDaoImpl();
		String result="";
		try{
			String order=request.getParameter("ordercode");
			String status=request.getParameter("status");
			
			String location=request.getParameter("location");
			String to=request.getParameter("to");
			
			int num=cmp.ready(order, status,user,to,location);
			result=Util.returnValue(num);
			/*if(num>0){
				out.print("success!");
			}else{
				out.print("error!");
			}*/
		}catch (Exception e) {
			result=Util.joinException(e);
			log.error("Marketing Premium==>Ready操作出现异常"+e);
			//e.printStackTrace();
			//out.print("Exception:"+e.toString());
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}
	/**
	 * 完成
	 * @param request
	 * @param response
	 */
		void complete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
			PrintWriter out = response.getWriter();
			C_GetMarkPremiumDao cmp=new C_GetMarkPremiumDaoImpl();
			try{
				String order=request.getParameter("ordercode");
				String status=request.getParameter("status");
				String paymethod=request.getParameter("paymethod");//支付方式
				String saleno=request.getParameter("saleno");//支付方式
				String payamount=request.getParameter("payamount");//支付金额
				String payDate=request.getParameter("payDate");//支付时间
				String handle=request.getParameter("handle");//收银员
				String savetype=request.getParameter("savetype");
				String staffname=request.getParameter("staffname");
				String location=request.getParameter("location");
				
				//System.out.println(paymethod+"===="+saleno+"===="+payamount+"===="+payDate+"===="+handle);
				
				int num=cmp.completed(order, status, user, savetype,paymethod,payamount,payDate,handle,staffname,location,saleno);
				if(num>0){
					out.print("success!");
				}else{
					out.print("error!");
				}
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Marketing Premium==>Completed操作出现异常"+e);
			}finally{
				out.flush();
				out.close();
			}
		}
	
	@SuppressWarnings("unchecked")
	void select(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
			PrintWriter out = response.getWriter();
			C_GetMarkPremiumDao  getStationeryDao = new C_GetMarkPremiumDaoImpl();
			Page page=new Page();
			List<C_marOrder> list=new ArrayList<C_marOrder>();
			List list1=new ArrayList();
			try{
				String startDate = request.getParameter("start_Date");
				String endDate = request.getParameter("end_Date");
				String staffcode = request.getParameter("clientcode");
				String ordercode = request.getParameter("ordercode");
				String staffname=request.getParameter("clientname");
				String userType=request.getParameter("ordertype");
				String location=request.getParameter("location");
				String status=request.getParameter("status");
				
				/******************************************分页代码***********************************************/
				 page.setAllRows(getStationeryDao.getOrdercount(staffcode,ordercode, startDate, endDate,staffname,userType,location,status));
					page.setPageSize(15);//设置页面显示行数
					page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
				/************************************************************************************************/
				list=getStationeryDao.queryOrderList( staffcode,ordercode, startDate, endDate,staffname,userType,location,status,page.getPageSize(),page.getCurPage());
				list1.add(0,list);
				list1.add(1,page.getAllPages());//总页数
				list1.add(2,page.getCurPage());//当前页
				list1.add(3,page.getAllRows());//总行数
				JSONArray jsons=JSONArray.fromObject(list1);
				out.print(jsons.toString());
			
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Marketing Premium==>Search Exception"+e);
				
			}finally{
				 getStationeryDao =null;
				 page=null;
				 list=null;
				 list1=null;
				out.flush();
				out.close();
			}
		 

	}

}
