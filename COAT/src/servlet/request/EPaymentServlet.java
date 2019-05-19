package servlet.request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

import util.DateUtils;
import util.ObjectExcelView;
import util.Page;
import util.PageData;
import util.Tools;
import util.Util;
import dao.EPaymentDao;
import dao.exp.ExpCardQuota;
import dao.impl.EPaymentDaoImpl;
import entity.C_EPayment_List;
import entity.C_Epayment_Detail;
import entity.C_Epaymentt_Order;
import entity.C_Payment;

public class EPaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(EPaymentServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		//PrintWriter out = response.getWriter();
	
		String method=request.getParameter("method");
		try{		
			if(method.equals("query"))
				query(request, response);
			else if(method.equals("queryProduct"))
				queryProduct(request,response);
			else if(method.equals("save"))
				save(request,response);
			else if(method.equals("selects"))
				selects(request,response);
			else if(method.equals("detial"))	
				detial(request,response);
			else if(method.equals("queryPayment"))	
				queryPayment(request,response);
			else if(method.equals("queryDetail"))
				queryDetail(request,response);
			else if(method.equals("completed"))
				completed(request,response);
			else if(method.equals("VOID"))
				VOID(request,response);
			else if(method.equals("down"))
				down(request,response);
			else{
				throw new Exception("Unauthorized access!");
			}
		}catch (NullPointerException e) {
			log.error("EPayment==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("QueryEPayment==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		} finally{
			method=null; 
		} 	
	}
	
	/**
	 * 产品加载
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void query(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();	
		try {
			EPaymentDao ad = new EPaymentDaoImpl();
			List<C_EPayment_List> payment = new ArrayList<C_EPayment_List>();
			payment = ad.queryRequestList();
		
			JSONArray jsons = JSONArray.fromObject(payment);
			out.println(jsons.toString());
			//System.out.println(jsons.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			out.flush();
			out.close();
		}	
	}
	
	/**
	 * 分页查询产品信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	void queryProduct(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		EPaymentDao  dao = new EPaymentDaoImpl();
		Page page=new Page();
		List<C_EPayment_List> list=new ArrayList<C_EPayment_List>();
		List list1=new ArrayList();
		try{
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String productcode=request.getParameter("productcode");
			String productname=request.getParameter("productname");
			String sfyx=request.getParameter("sfyx");
			
			/******************************************分页代码***********************************************/
			    page.setAllRows(dao.getNum(productcode,productname, startDate, endDate,sfyx));//总的行数
				page.setPageSize(15);//设置页面显示行数
				page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			/************************************************************************************************/
			list=dao.queryProduct(productcode,productname, startDate, endDate,sfyx,page.getCurPage(),page.getPageSize());
			
			list1.add(0,list);
			list1.add(1,page.getAllPages());//总页数
			list1.add(2,page.getCurPage());//当前页
			list1.add(3,page.getAllRows());//总行数
			
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
	
	/**
	 * 保存订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void save(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String location=request.getParameter("location");
			String usertype=request.getParameter("type");
			String status=request.getParameter("status");
			String string=request.getParameter("string");     //string是已选择的产品的字符串相加
			
			List<C_Epayment_Detail> detail=new ArrayList<C_Epayment_Detail>();
			C_Epayment_Detail c_epayment_detail=new C_Epayment_Detail();
			
			//System.out.println(string);
	
			String a[]=string.split("::");
			String [] b; 
			for(int i=0;i<a.length;i++){
					b=a[i].split(":");
					c_epayment_detail.setProductcode(b[0]);
					c_epayment_detail.setProductname(b[1]);
					c_epayment_detail.setPrice( Double.parseDouble(b[2]));
					c_epayment_detail.setQuantity(1.0);
					detail.add(c_epayment_detail);
					c_epayment_detail=new C_Epayment_Detail();                            
			}
			
			HttpSession session=request.getSession();
			String adminUsername=(String) session.getAttribute("adminUsername");
			
			//获取系统当前时间
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String regiesterTime = tempDate.format(new java.util.Date());
			
			C_Epaymentt_Order epayment=new C_Epaymentt_Order();
			//C_Epayment_Detail epayment02=new C_Epayment_Detail();
			
			epayment.setStaffcode(staffcode); 	  //编号
			epayment.setStaffname(staffname); 	  //客户名
			epayment.setUsertype(usertype);	 	  //类型
			epayment.setLocation(location);  	  //楼层	
			epayment.setCreatedate(regiesterTime);//系统当前日期
			epayment.setCreater(adminUsername);	  //当前的执行人
			epayment.setStatus(status);      	  //状态(submit)
			epayment.setSfyx("Y");
			epayment.setRemark("");
			
			EPaymentDao dao=new EPaymentDaoImpl();
			 dao.savePayment(epayment,detail);
			 JSONArray jsons = JSONArray.fromObject(epayment);
			 out.print("Success!");
			 //System.out.println(jsons.toString());
		   
		}catch(Exception e){
			e.printStackTrace();
			out.flush();
			out.close();
		}
		
	}
		
	/**
	 * 导出Epayment
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	/*@SuppressWarnings("static-access")
	void down(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		//PrintWriter out = response.getWriter();	
		HSSFWorkbook wb = new HSSFWorkbook();
	 	ExpCardQuota expcard = new ExpCardQuota();
		try {
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String staffcode = request.getParameter("clientcode");
			String ordercode = request.getParameter("ordercode");
			String staffname=request.getParameter("clientname");
			String userType=request.getParameter("ordertype");
			String location=request.getParameter("location");
			String status=request.getParameter("status");
			EPaymentDao ad = new EPaymentDaoImpl();
			
			
			String fname ="E-Payment";//Excel文件名
			OutputStream os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ fname + ".xls");
			//定义输出类型
			ResultSet res =ad.queryEpaymentt_Order(staffcode, ordercode, startDate, endDate, staffname, userType, location, status);
			if(res!=null){
				HSSFSheet sheet = wb.createSheet("new sheet");
				wb.setSheetName(0, "E-Payment");
				HSSFRow rows=sheet.createRow(0);
				sheet.createFreezePane(0, 1);
				expcard.cteateTitleCell(wb,rows,(short)4,"Request Option");
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
				
				
				HSSFRow row=sheet.createRow(1);
				
				sheet.createFreezePane(0, 2);
				expcard.cteateTitleCell(wb,row,(short)0,"Ref.Number");
				expcard.cteateTitleCell(wb,row,(short)1,"Consultant / Staff Code");
				expcard.cteateTitleCell(wb,row,(short)2,"Consultant / Staff Name");
				expcard.cteateTitleCell(wb,row,(short)3,"Location");
				expcard.cteateTitleCell(wb,row,(short)4,"Product code");
				expcard.cteateTitleCell(wb,row,(short)5,"Product Name");
				expcard.cteateTitleCell(wb,row,(short)6,"Payment Method");
				expcard.cteateTitleCell(wb,row,(short)7,"Payment Amout");
				expcard.cteateTitleCell(wb,row,(short)8,"Payment date");
				expcard.cteateTitleCell(wb,row,(short)9,"Handle by");
				expcard.cteateTitleCell(wb,row,(short)10,"Status");
				expcard.createepaymentSheet(res, os,wb,sheet);
				res.close();
			}
			
	 
			
		} catch (Exception e) {
			e.printStackTrace();
			 
		}	
	}*/
	
	void down(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		try {
			String startDate = request.getParameter("start_Date");
			String endDate = request.getParameter("end_Date");
			String staffcode = request.getParameter("clientcode");
			String ordercode = request.getParameter("ordercode");
			String staffname=request.getParameter("clientname");
			String userType=request.getParameter("ordertype");
			String location=request.getParameter("location");
			String status=request.getParameter("status");
			EPaymentDao ad = new EPaymentDaoImpl();
			List<String []> list =ad.queryEpaymentt_Order2(staffcode, ordercode, startDate, endDate, staffname, userType, location, status);

	    	Map<String, Object> model=new HashMap<String, Object>();
			List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
			Map<Integer,Object> map=new HashMap<Integer, Object>();
			map.put(4, "Request Option");
			title.add(map);
			map=new HashMap<Integer, Object>();
			map.put(0,"Ref.Number");
			map.put(1,"Consultant / Staff Code");
			map.put(2,"Consultant / Staff Name");
			map.put(3,"Location");
			map.put(4,"Product code");
			map.put(5,"Product Name");
			map.put(6,"Payment Method");
			map.put(7,"Payment Amout");
			map.put(8,"Payment date");
			map.put(9,"Handle by");
			map.put(10,"Status");
			title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			if(!Util.objIsNULL(list)){
				for (int i = 0; i < list.size(); i++) {
					PageData p=new PageData();
					p.put("var1", list.get(i)[0]); 
					p.put("var2", list.get(i)[1]);  
					p.put("var3", list.get(i)[2]);  
					p.put("var4", list.get(i)[3]);  
					p.put("var5", list.get(i)[4]);  
					p.put("var6", list.get(i)[5]);  
					p.put("var7", list.get(i)[6]);  
					p.put("var8", list.get(i)[7]);  
					p.put("var9", list.get(i)[8]);  
					p.put("var10",list.get(i)[9]);  
					p.put("var11", list.get(i)[10]);  
					data.add(p);
				}
			}
			model.put("varList", data);
			Date date=new Date();
			String filename ="E-Payment_"+Tools.date2Str(date, "yyyyMMddHHmmss")+".xls";
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename="+ filename.toString() + ".xls");
			ObjectExcelView view=new ObjectExcelView();
			view.addStaticAttribute("xxx", "gsdg");
		    HSSFWorkbook wb= new HSSFWorkbook();
			view.buildExcelDocument2(model, wb,response,filename,"E-Payment");
		
			
		} catch (Exception e) {
			e.printStackTrace();
			 
		}	
	}
	
	/**
	 * 查询Order
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	void selects(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		EPaymentDao  getStationeryDao = new EPaymentDaoImpl();
		Page page=new Page();
		List<C_Epaymentt_Order> list=new ArrayList<C_Epaymentt_Order>();
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
			    page.setAllRows(getStationeryDao.getC_PaymentCount(staffcode,ordercode, startDate, endDate,staffname,userType,location,status));//总的行数
				page.setPageSize(15);//设置页面显示行数
				page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			/************************************************************************************************/
			list=getStationeryDao.queryEpaymentt_Order(staffcode,ordercode, startDate, endDate,staffname,userType,location,status,page.getCurPage(),page.getPageSize());
			list1.add(0,list);
			list1.add(1,page.getAllPages());//总页数
			list1.add(2,page.getCurPage());//当前页
			list1.add(3,page.getAllRows());//总行数
			JSONArray jsons=JSONArray.fromObject(list1);
			out.print(jsons.toString());
		
		}catch (Exception e) {
			e.printStackTrace();
			log.error("selects==>Search Exception"+e);
			
		}finally{
			 getStationeryDao =null;
			 page=null;
			 list=null;
			 list1=null;
			out.flush();
			out.close();
		}
	}
	/**
	 * 查询Detial
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void detial(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
			 
		List<C_Epaymentt_Order> list=new ArrayList<C_Epaymentt_Order>();
		
		try{					
			String refno=request.getParameter("ordercode");
			EPaymentDao qdao = new EPaymentDaoImpl();			
			list = qdao.queryOrderDetial(refno);			

			JSONArray jsons=JSONArray.fromObject(list);	
			out.print(jsons.toString());
	
			}catch(Exception e){
				e.printStackTrace();
				log.error("detial==>Search Exception"+e);
			}finally{
				out.flush();
				out.close();
			}
		
	}
	
	/**
	 * 查询C_Payment
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void queryPayment(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		List<C_Payment> list=new ArrayList<C_Payment>();
		try{
			String refno =request.getParameter("refno");
			EPaymentDao qdao = new EPaymentDaoImpl();	
			list=qdao.queryPayment(refno);
			
			JSONArray jsons=JSONArray.fromObject(list);	
			out.print(jsons.toString());
			//System.out.println(jsons.toString());
		}catch(Exception e){
			e.printStackTrace();
			log.error("queryPayment==>Search Exception"+e);
		}finally{
			out.flush();
			out.close();
		}
	}
	/**
	 * 查询详细
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void queryDetail(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		 
		List<C_Epayment_Detail> list=new ArrayList<C_Epayment_Detail>();
		try{
			String refno =request.getParameter("refno");
			EPaymentDao qdao = new EPaymentDaoImpl();	
			list=qdao.queryDetail(refno);
			
			JSONArray jsons=JSONArray.fromObject(list);	
			out.print(jsons.toString());
			//System.out.println(jsons.toString());
		}catch(Exception e){
			e.printStackTrace();
			log.error("queryPayment==>Search Exception"+e);
		}finally{
			out.flush();
			out.close();
		}
		
	}
	
	/**
	 * 完成
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void completed(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		EPaymentDao qdao = new EPaymentDaoImpl();	
		
		String user =(String) request.getSession().getAttribute("adminUsername");
		try{
			//Order表
			String refno=request.getParameter("refno");
			String staffcodes=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String location=request.getParameter("location");
			String status=request.getParameter("status");
			
			//payment表
			String paymethod=request.getParameter("paymethod");
			String saleno=request.getParameter("saleno");
			String payamount=request.getParameter("payamount");
			String payDate=request.getParameter("payDate");
			String handle=request.getParameter("handle");
			
			String type=request.getParameter("type");
			
			int num=qdao.completed(type,user,refno, status, staffname,staffcodes, new C_Payment(paymethod,Double.parseDouble(payamount),payDate,handle,"Y",staffname,location,saleno));
					if(num>0){
						out.print("Success!");
					}else{
						out.print("error!");
					}
		}catch(Exception e){
			e.printStackTrace();
			log.error("EpaymentHRQueryServlet==>Completed操作出现异常"+e);
		}finally{
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 撤销订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void VOID(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		PrintWriter out = response.getWriter();
		String refno = request.getParameter("refno");
		int num = 0;
		EPaymentDao qdao = new EPaymentDaoImpl();	
		
		String user =(String) request.getSession().getAttribute("adminUsername");
		try{
			num = qdao.VOID(refno,user);
			if(num < 0){
				out.print("Error!");
			}else {
				out.print("Success!");
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

}
