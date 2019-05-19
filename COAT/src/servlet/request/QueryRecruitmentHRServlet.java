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

import util.DateUtils;
import util.ExcelTools;
import util.Page;
import util.Util;
import dao.RecruitmentQueryHrDao;
import dao.impl.RecruitmentQueryHrDaoImpl;
import entity.C_Payment;
import entity.C_Recruitment_detail;
import entity.C_Recruitment_list;
import entity.C_Recruitment_order;
import entity.Excel;
import entity.Recruitment_list;

public class QueryRecruitmentHRServlet extends HttpServlet {
	Logger log=Logger.getLogger(QueryRecruitmentHRServlet.class);
	/**
	 * Constructor of the object.
	 */
	public QueryRecruitmentHRServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//System.out.println("----join---");
		//设置编码
		response.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		RecruitmentQueryHrDao queryHrDao=new RecruitmentQueryHrDaoImpl();
		String method=request.getParameter("method");
		if(method.equals("findAll")){
			try{
				PrintWriter out = response.getWriter();
				Page page=new Page();
				//根据条件查询订单
			   	//1.获取页面的参数
			    C_Recruitment_order  order=new C_Recruitment_order();
			    String date1=request.getParameter("date1").trim();
			    String date2=request.getParameter("date2").trim();
				order.setRefno(request.getParameter("refno").trim());
				order.setStaffcode(request.getParameter("staffcode").trim());//登录人code
				order.setStaffname(request.getParameter("staffname").trim());//""
				order.setStatus(request.getParameter("status").trim());
				String medianame=request.getParameter("medianame").trim();//""
				//2.获取总记录
				page.setAllRows(queryHrDao.selecrRow(date1, date2, order,medianame));
			    page.setPageSize(15);
			    //获取当前页码  
				page.setCurPage(Integer.parseInt(request.getParameter("pageIndex")));
			    //3.查询
			    List<C_Recruitment_order> listOrders=queryHrDao.find(date1, date2, order, page,medianame);
			    //转为Json
			      List list=new ArrayList();
			      list.add(0,listOrders);
			      list.add(1,page.getAllPages());//总页数
				  list.add(2,page.getCurPage());//当前页
				  list.add(3,page.getAllRows());//总行数
				  list.add(4,page.getPageSize());//一页显示的记录
				  JSONArray jsons=JSONArray.fromObject(list);
				  out.print(jsons.toString());
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}else if(method.equals("findRecruitmentList")){
			try{
				PrintWriter out = response.getWriter();
				Page page=new Page();
				//根据条件查询订单
			   	//1.获取页面的参数
			    C_Recruitment_order  order=new C_Recruitment_order();
			    String date1=request.getParameter("date1").trim();
			    String date2=request.getParameter("date2").trim();
				String medianame=request.getParameter("medianame").trim();
				//2.获取总记录
				page.setAllRows(queryHrDao.selectRow(date1, date2,medianame));
			    page.setPageSize(15);
			    //获取当前页码  
				page.setCurPage(Integer.parseInt(request.getParameter("pageIndex")));
			    //3.查询
			    List<C_Recruitment_order> listOrders=queryHrDao.findRecruitmentList(date1, date2, page,medianame);
			    //转为Json
			      List list=new ArrayList();
			      list.add(0,listOrders);
			      list.add(1,page.getAllPages());//总页数
				  list.add(2,page.getCurPage());//当前页
				  list.add(3,page.getAllRows());//总行数
				  list.add(4,page.getPageSize());//一页显示的记录
				  JSONArray jsons=JSONArray.fromObject(list);
				  out.print(jsons.toString());
			}catch (Exception e) {
				e.printStackTrace();
			}	
			
		}else if(method.equals("queryMediaName")){
			PrintWriter out = response.getWriter();
			
			List<C_Recruitment_list> list = new ArrayList<C_Recruitment_list>();
			try{
				list = queryHrDao.queryMediaName();
				JSONArray jsons=JSONArray.fromObject(list);
				out.print(jsons.toString());
			}catch (Exception e) {
				e.printStackTrace();
				log.error("获取Staff Position时出现："+e);
				out.print("error");
			} finally{
				out.flush();
				out.close();
			} 	
		}else if(method.equals("detial")){
			PrintWriter out = response.getWriter();
			  //查询订单
			  //获取refno
			String refno=request.getParameter("refno").trim();
			C_Recruitment_order order=new C_Recruitment_order();
			C_Recruitment_detail detail=new C_Recruitment_detail();
			List list=new ArrayList();
			C_Payment  cp=new C_Payment();
			order=queryHrDao.findOrderByNo(refno);//没有filterdate
			 //查询订单详细
			detail=queryHrDao.findDetial(refno);
			if(order!=null&&detail!=null){
				  //根据订单状态查询付费信息
			if(order.getStatus().equals("Completed")||order.getStatus().equals("Scheduled")){
				   cp=queryHrDao.findCons_listByCode(order.getRefno()); 	    
		      }
				list.add(0,order);
				list.add(1,detail);
				list.add(2,cp);
				JSONArray jsons=JSONArray.fromObject(list);
				out.print(jsons.toString());
			}
			else{
				out.print("error");
			}
		}
		else if(method.equals("save")){
		//	System.out.println("---method--->save---");
			//保存用户操作记录
			String result="";
			PrintWriter out = response.getWriter();
			try{
				//获取refno
				String refno=request.getParameter("refno").trim();
				//获取日期
				String date=request.getParameter("date").trim();
				String name=(String)request.getSession().getAttribute("adminUsername");
				
				String[] str=new String[2];
			    str[0]=request.getParameter("code").trim();
			    str[1]=request.getParameter("Chargecode").trim();
			    String  [] email=queryHrDao.getEmailByCode(str);
			    String to=email[0];
			    String cc=email[1]+";adminfo@convoy.com.hk";
			    
			    String staffname=request.getParameter("staffname");
			    String date1=request.getParameter("date1");
			    String date2=request.getParameter("date2");
			    String Person=request.getParameter("Person");
			    String emailss=request.getParameter("email");
			    String mediaName=request.getParameter("mediaName");
			    
			    int num=queryHrDao.upOrderDate(date, refno,name,to,cc,staffname,date1,date2,Person,emailss,mediaName);
				result=Util.returnValue(num);
				/*if(num>0){
					out.print("Success!");
				}else{
					out.print("Error!");
				}*/
			    
			}catch (Exception e) {
				result=Util.joinException(e);
				log.error("QueryRecruitmentHRServlet==>Ready Exception :"+e.getMessage());
			}finally{
				out.print(result);
				out.flush();
				out.close();
			}
		}
		else if(method.equals("changeStatus")){
			PrintWriter out = response.getWriter();
			//获取refno
			String refno=request.getParameter("refno").trim();
			//获取状态
			String status=request.getParameter("status").trim();
			String name=(String)request.getSession().getAttribute("adminUsername");
			
			C_Recruitment_order order=new C_Recruitment_order();
			order=queryHrDao.findOrderByNo(refno);//没有filterdate
			
			C_Recruitment_detail detail=new C_Recruitment_detail();
			detail=queryHrDao.findDetial(refno);
			
			//更新状态
		   int num=queryHrDao.upOrderStatus(status, refno,name,order,detail);
		   if(num>0){
		    	out.print("success");
		    }
		    else{
		    	out.print("error");
		    }
		}else if(method.equals("savePayment")){
			PrintWriter out = response.getWriter();
			C_Payment payment=new C_Payment();
			//获取支付信息
			payment.setRefno(request.getParameter("refno").trim());
			payment.setStaffname(request.getParameter("staffname").trim());
			payment.setSaleno(request.getParameter("saleno").trim());
			payment.setPaymentMethod(request.getParameter("paymentMethod").trim());
			payment.setPaymentAount(Double.parseDouble(request.getParameter("paymentAount").trim()));
			payment.setPaymentDate(request.getParameter("payDate").trim());
			payment.setHandleder(request.getParameter("handled").trim());
			payment.setCreateDate(DateUtils.getNowDateTime());
			payment.setSfyx("Y");
			payment.setType("Advertisement");
			String name=(String)request.getSession().getAttribute("adminUsername");
			int num=queryHrDao.addpayment(payment,name);
			if(num>0){
				    String[] str=new String[2];
				   str[0]=request.getParameter("code").trim();
				   str[1]=request.getParameter("Chargecode").trim();
				   String  [] email=queryHrDao.getEmailByCode(str);
				   JSONArray jsons=JSONArray.fromObject(email);
				   out.print(jsons.toString());
		    	
		    }
		    else{
		    	out.print("error");
		    }
		}else if(method.equals("export1")){
			//export 导出数据
		    //获取查询条件
			 C_Recruitment_order  order=new C_Recruitment_order();
			 String date1=request.getParameter("date1").trim();
			 String date2=request.getParameter("date2").trim();
			 order.setRefno(request.getParameter("refno").trim());
			 order.setStaffcode(request.getParameter("staffcode").trim());
			 order.setStaffname(request.getParameter("staffname").trim());
			 order.setStatus(request.getParameter("status").trim());
			 Excel excel=new Excel();
			 
			 try{
				//得到结果
		     List<Recruitment_list> list=queryHrDao.exportDate(date1, date2, order);	
			    //把数据交给Excel
			    excel.setExcelContentList(list);	
			    //设置Excel列头
			    excel.setColumns(new String[]{"ref Number","Request Date","Recruiter StaffCode","Recruiter Name","Media Type","Media Name","Positing Period","Contact Person","Contact Email","Unit Price","ChargeCode","Handle By"});
			    //属性字段名称
			    excel.setHeaderNames(new String[]{"refno","createDate","staffcode","staffname","mediatype","medianame","date","contactperson","contactemail","price","chargecode","handleber","status"});
			   //sheet名称
			    excel.setSheetname("Recruitment");
			    //文件名称
				excel.setFilename("Recruitment"+System.currentTimeMillis());
				String filename=ExcelTools.getExcelFileName(excel.getFilename());
				response.setHeader("Content-Disposition", "attachment;filename=\""+filename+".xls"+"\"");
		       //生成EXCEL
				ExcelTools.createStyle(excel,response);
			    }catch (Exception e) {
				    e.printStackTrace();
			    }
		}else if (method.equals("del")){
			PrintWriter out = response.getWriter();
			String name=(String)request.getSession().getAttribute("adminUsername");
			//获取refno
			String refno=request.getParameter("refno").trim();
			int num = -1;
			try {
				num = queryHrDao.del(name,refno);
				out.print(num);
			}catch (Exception e) {
				e.printStackTrace();
				log.error("SeatServlet==>Confirm Exception :"+e.getMessage());
			}finally{
				out.flush();
				out.close();
			}
			
			
			
		}
		
	
		//System.out.println("---->end");
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
