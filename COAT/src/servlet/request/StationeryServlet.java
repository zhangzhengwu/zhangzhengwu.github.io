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

import util.Util;
import dao.C_GetStationeryDao;
import dao.C_RoomSettingDao;
import dao.C_StationeryDao;
import dao.impl.C_GetStationeryDaoImpl;
import dao.impl.C_RoomSettingDaoImpl;
import dao.impl.C_StationeryDaoImpl;
import entity.C_Payment;
import entity.C_StationeryOperation;
import entity.C_stationeryOrder;

public class StationeryServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(StationeryServlet.class);
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
			if(method.equals("select_HR"))
				select_HR(request, response);
			else if(method.equals("changeStationeryStatus"))
				changeStationeryStatus(request,response);
			else if(method.equals("complete"))
				complete(request,response);
			else if(method.equals("ready"))
				ready(request,response);
			else if(method.equals("VOID"))
				VOID(request,response);
			else {
				throw new Exception("Unauthorized access!");
			}
				
		}catch (NullPointerException e) {
			log.error("Stationery==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Stationery==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		}finally{
			
			method=null; 
		} 
	}
	
	
	@SuppressWarnings("unchecked")
	void select_HR(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String ordercode = request.getParameter("ordercode");
			String clientcode = request.getParameter("clientcode");
			String clientname = request.getParameter("clientname");
			String location=request.getParameter("location");
			String status=request.getParameter("status");
			String orderType=request.getParameter("orderType");
			C_GetStationeryDao  getStationeryDao = new C_GetStationeryDaoImpl();
			/******************************************分页代码***********************************************/
			String pageIndex = request.getParameter("curretPage");
			int currentPage = 1;//当前页
			int pageSize = 15;//页面大小
			int total=0;//得到总记录数
			int totalPage=0;//总页数
			if(Util.objIsNULL(pageIndex)){//页面当前页为空时
				pageIndex="1";//强制赋值第一页
			}
			currentPage=Integer.parseInt(pageIndex);//设置当前页
			total=getStationeryDao.getOrdercount(clientcode,clientname, ordercode,startDate, endDate,location,status,orderType);//得到总页数
			 
			totalPage=total/pageSize;//总页数=总记录数/页面大小
			if(total%pageSize>0){//如果总记录数%页面大小==0时
				totalPage+=1;
			}
			if(currentPage>=totalPage){
				currentPage=totalPage;
			}if(currentPage<=1){
				currentPage=1;
			}

			/************************************************************************************************/
			List<C_stationeryOrder> list=new ArrayList<C_stationeryOrder>();
			list=getStationeryDao.queryOrderList( clientcode,clientname,ordercode,startDate, endDate,location,status,orderType,pageSize,currentPage);
			
			List list1=new ArrayList();
			list1.add(0,list);
			list1.add(1,currentPage);
			list1.add(2,totalPage);
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
		
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Stationery==>Search操作异常："+e);
			out.print("Exception:"+e.getMessage());
		}finally{
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
			C_StationeryDao ca=new C_StationeryDaoImpl();
			String result="";
			try{
				String order=request.getParameter("ordercode");
				String status=request.getParameter("status");
				user=request.getSession().getAttribute("adminUsername").toString();
				
				String to=request.getParameter("to");
				String location=request.getParameter("location");
				
				int num=ca.ready(order, status,user,to,location);
				result=Util.returnValue(num);
				/*if(num>0){
					out.print("success!");
				}else{
					out.print("error!");
				}*/
			}catch (Exception e) {
				result=Util.joinException(e);
				log.error("Stationery==>Ready操作异常："+e);
				//out.print("Exception :"+e.toString());
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
		}
		
		void VOID(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
			PrintWriter out = response.getWriter();
			C_GetStationeryDao nqdao = new C_GetStationeryDaoImpl();
			try{
				String order=request.getParameter("ordercode");
				user=request.getSession().getAttribute("adminUsername").toString();
				int num=nqdao.VOID(order,user);
				if(num>0){
					out.print("success!");
				}else{
					out.print("error!");
				}
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Stationery==>VOID操作异常："+e);
				out.print("Exception :"+e.toString());
			}finally{
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
		C_StationeryDao ca=new C_StationeryDaoImpl();
		try{
			String order=request.getParameter("ordercode");
			String status=request.getParameter("status");
			String paymethod=request.getParameter("menthod");//支付方式
			String saleno=request.getParameter("saleno");//支付方式
			String payamount=request.getParameter("paymentAmount");//支付金额
			String payDate=request.getParameter("payDate");//支付时间
			String handle=request.getParameter("handled");//收银员
			String type = request.getParameter("type");
			String staffname=request.getParameter("staffname");
			String location=request.getParameter("location");
			
			//System.out.println(paymethod+"---"+payamount+"---"+payDate+"---"+handle+"---"+saleno);
			
			int num=ca.completed(order, status, user,type,paymethod,payamount,payDate,handle,staffname,location,saleno);
			
			if(num>0){
				out.print("success!");
			}else{
				out.print("error!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Stationery==>Completed操作异常："+e);
			out.print("Exception:"+e.toString());
		}finally{
			out.flush();
			out.close();
		}
	}
	
	
	
	
	 void changeStationeryStatus(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int num = 0;
		try{
			String operationName = (String)request.getSession().getAttribute("adminUsername");
		
			String ordercode = request.getParameter("ordercode");
			String status = request.getParameter("status");
			String staffOrCons = request.getParameter("staffOrCons");
			C_RoomSettingDao roomDao = new C_RoomSettingDaoImpl();
			if("Completed".equals(status) && !"Staff".equals(staffOrCons)){
				C_Payment payment = new C_Payment();
				String menthod = request.getParameter("menthod");
				String payDate = request.getParameter("payDate");
				String handled = request.getParameter("handled");
				String paymentAmount = request.getParameter("paymentAmount");
				String type = request.getParameter("type");
			
				payment.setRefno(ordercode);
				payment.setType(type);
				payment.setPaymentMethod(menthod);
				payment.setPaymentAount(Double.valueOf(paymentAmount));
				payment.setPaymentDate(payDate);
				payment.setHandleder(handled);
				payment.setCreator(user);
				payment.setSfyx("Y");
				roomDao.savePayment(payment);
				
			}
			C_StationeryOperation oper = new C_StationeryOperation();
			oper.setOperationName(operationName);
			oper.setOrdercode(ordercode);
			oper.setOperationType(status);
			roomDao.saveStationeryOperation(oper);
			num = roomDao.changeStationeryStatus(ordercode,status);
			if(num > 0){
				out.print("success");
			}else{
				out.print("error");
			}
		}catch (Exception e) {
			e.printStackTrace();
			out.print("Exception:"+e.toString());
		}finally{
			out.flush();
			out.close();
		}
		
		}
	
	
	
	

}
