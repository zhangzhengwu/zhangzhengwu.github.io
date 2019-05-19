package servlet.request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;

import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constant;
import util.DateUtils;
import util.Page;
import util.UpLoadFile;
import util.Util;
import dao.AccessCardDao;
import dao.exp.ExpStaffPosition;
import dao.impl.AccessCardDaoImpl;
import entity.CAttachment;
import entity.C_Access;
import entity.C_Payment;


public class AccessCardServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(AccessCardServlet.class);
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
			else if(method.equals("down"))
				down(request, response);
			else if(method.equals("add"))
				add(request, response);
			else if(method.equals("detail"))
				Detail(request,response);
			else if(method.equals("complete"))
				complete(request,response);
			else if(method.equals("Ready"))
				Ready(request,response);
			else if(method.equals("VOID"))
				VOID(request,response);
			else if(method.equals("delete"))
				delete(request,response);
			else if(method.equals("HKADM"))
				HKADM(request,response);
			else if(method.equals("getPath"))
				getPath(request,response);
			else if(method.equals("downPhoto"))
				downPhoto(request,response);
			else {
				throw new Exception("Unauthorized access!");
			}
				
		}catch (NullPointerException e) {
			log.error("AccessCard==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("AccessCard==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		} finally{
			method=null; 
		} 
	}

	
	
@SuppressWarnings("unchecked")
 void select(HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	AccessCardDao acd=new AccessCardDaoImpl();
	 Page page=new Page();
	 List<C_Access> list=new ArrayList<C_Access>();
	try{
		out=response.getWriter();
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String staffcode=request.getParameter("staffcode");
		String staffname=request.getParameter("staffname");
		String refno=request.getParameter("refno");
		String status=request.getParameter("status");
		
		page.setAllRows(acd.getRow(startDate, endDate, staffcode, staffname, refno, status));
		page.setPageSize(15);//设置页面显示行数
		page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
		list=acd.findAccessList(startDate, endDate, staffcode, staffname, refno, status, page);
		List list1=new ArrayList();
		list1.add(0,list);//数据存放
		list1.add(1,page.getAllPages());//总页数
		list1.add(2,page.getCurPage());//当前页
		list1.add(3,page.getAllRows());//总条数
		JSONArray jsons=JSONArray.fromObject(list1);
		out.print(jsons.toString());
		jsons=null;
		list1=null;
	}catch(Exception e){
		e.printStackTrace();
		log.error("AccessCard-Search Exception :"+e.getMessage());
		out.print("Search Exception :"+e.getMessage());
	}finally{
		acd=null;
		list=null;
		out.flush();
		out.close();
	}
	}
@SuppressWarnings({ "static-access", "unchecked" })
 void down(HttpServletRequest request, HttpServletResponse response){
		AccessCardDao acd=new AccessCardDaoImpl();
		HSSFWorkbook wb = new HSSFWorkbook();
		OutputStream  os=null;
		try{
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String refno=request.getParameter("refno");
			String status=request.getParameter("status");
			Result rss=acd.downAccessList(startDate, endDate, staffcode, staffname, refno, status);
			os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename=AccessCard_"+DateUtils.Ordercode()+".xls");
			ExpStaffPosition expcard=new ExpStaffPosition();
			//list=acd.downAccessList(startDate, endDate, staffcode, staffname, refno, status);
			HSSFSheet sheet = wb.createSheet("AccessCard");
			HSSFRow row=sheet.createRow(0);
			sheet.createFreezePane(0, 1);
				
				
			expcard.cteateTWOTitleCell(wb,row,(short)0,"Ref");
			expcard.cteateTWOTitleCell(wb,row,(short)1,"Staff Code");
			expcard.cteateTWOTitleCell(wb,row,(short)2,"Staff Name");
			expcard.cteateTWOTitleCell(wb,row,(short)3,"Staff Card");
			expcard.cteateTWOTitleCell(wb,row,(short)4,"Existing Card no.");
			expcard.cteateTWOTitleCell(wb,row,(short)5,"New Card no.");
			expcard.cteateTWOTitleCell(wb,row,(short)6,"Photo Sticker");
			expcard.cteateTWOTitleCell(wb,row,(short)7,"receipt Date");
			expcard.cteateTWOTitleCell(wb,row,(short)8,"Signature");
			expcard.cteateTWOTitleCell(wb,row,(short)9,"payment");
			expcard.cteateTWOTitleCell(wb,row,(short)10,"Cash");
			expcard.cteateTWOTitleCell(wb,row,(short)11,"Octopus");
			expcard.cteateTWOTitleCell(wb,row,(short)12,"EPS");
			expcard.cteateTWOTitleCell(wb,row,(short)13,"Reason");
			expcard.cteateTWOTitleCell(wb,row,(short)14,"Status");
			expcard.cteateTWOTitleCell(wb,row,(short)15,"Time");
			expcard.cteateTWOTitleCell(wb,row,(short)16,"Remark");
			
			 sheet.setColumnWidth((short)0, 100*40); 
			 
	         sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet.setColumnWidth((short)2, 100*50); 
	         sheet.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)6); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)7); // 调整第四列宽度
	         sheet.autoSizeColumn((short)8); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)9); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)10); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)11); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)12); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)13); // 调整第四列宽度 
	         sheet.setColumnWidth((short)14, 100*40); 
	         sheet.autoSizeColumn((short)15); // 调整第四列宽度 
			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map<String,Object> rows = rss.getRows()[i];
			          /*for (Object key : rows.keySet()) {
			            	   System.out.println("key= "+ key + " and value= " + rows.get(key));
			            	  }
			            System.out.println();*/
			            
			            
			        	HSSFRow row6=sheet.createRow(i+1);
				        expcard.cteateTitleCell(wb,row6,(short)0,rows.get("refno").toString());
				        expcard.cteateTitleCell(wb,row6,(short)1,rows.get("staffcode").toString());
				        expcard.cteateTitleCell(wb,row6,(short)2,rows.get("staffname").toString());
				        expcard.cteateTitleCell(wb,row6,(short)3,rows.get("staffcard").toString());
				        expcard.cteateTitleCell(wb,row6,(short)4,Util.objIsNULL(rows.get("historyno"))?"":rows.get("historyno").toString());
				        expcard.cteateTitleCell(wb,row6,(short)5,Util.objIsNULL(rows.get("newno"))?"":rows.get("newno").toString());
				        expcard.cteateTitleCell(wb,row6,(short)6,rows.get("photosticker").toString());
				        expcard.cteateTitleCell(wb,row6,(short)7,Util.objIsNULL(rows.get("paymentDate"))?"":rows.get("paymentDate").toString()); 
				        expcard.cteateTitleCell(wb,row6,(short)8,Util.objIsNULL(rows.get("handleder"))?"":rows.get("handleder").toString());
				        if(Util.objIsNULL(rows.get("paymentAount"))){//没有支付信息
				        	expcard.cteateTitleCell(wb,row6,(short)9,"");
				        	expcard.cteateTitleCell(wb,row6,(short)10,"");
				        	expcard.cteateTitleCell(wb,row6,(short)11,"");//Octopus
				            expcard.cteateTitleCell(wb,row6,(short)12,"");//EPS
				        }else{
				        	 //System.out.println(rows.get("paymentAount")+"---"+(int)(Double.parseDouble(rows.get("paymentAount")+"")));
				        	 expcard.cteateTitleCell(wb,row6,(short)9,(int)(Double.parseDouble(rows.get("paymentAount")+""))+"");
				        
				             if(rows.get("paymentMethod").equals("Cash"))
						        	expcard.cteateTitleCell(wb,row6,(short)10,"Y");//Cash
						        else  
						        	expcard.cteateTitleCell(wb,row6,(short)10,"");
						        
						        if(rows.get("paymentMethod").equals("Octopus"))
						        	expcard.cteateTitleCell(wb,row6,(short)11,"Y");//Cash
						        else  
						        	expcard.cteateTitleCell(wb,row6,(short)11,"");//Octopus
						        
						        if(rows.get("paymentMethod").equals("EPS"))
						        	expcard.cteateTitleCell(wb,row6,(short)12,"Y");//Cash
						        else  
						        	expcard.cteateTitleCell(wb,row6,(short)12,"");//EPS
				        }
				        
				        expcard.cteateTitleCell(wb,row6,(short)13,rows.get("reason").toString());
				       
				        expcard.cteateTitleCell(wb,row6,(short)14,rows.get("Status").toString());
				        expcard.cteateTitleCell(wb,row6,(short)15,DateUtils.getHourAndSecond(rows.get("createDate").toString()));
				        expcard.cteateTitleCell(wb,row6,(short)16,Util.objIsNULL(rows.get("Remark"))?"":rows.get("Remark").toString());
			         
			        }  
			}
			wb.write(os);
			os.flush();
			os.close();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Access-Export Exception :"+e.getMessage());
		}finally{
			acd=null;
			wb=null;
		
		}
	}
/**
 * 保存AccessCard
 * @param request
 * @param response
 */
 void add(HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	AccessCardDao acd=new AccessCardDaoImpl();
	try{
		out=response.getWriter();
		String filenameAndPath=request.getParameter("fileName");
		int num=acd.saveAccessCard(new C_Access(
				"",
				request.getParameter("staffcode"),
				request.getParameter("staffname"),
				request.getParameter("userType"),
				request.getParameter("location"),
				request.getParameter("staffcard"),
				request.getParameter("photosticker"),
				request.getParameter("reason"),
				user,//创建人
				DateUtils.getNowDateTime(),//创建时间
				Constant.C_Submitted,
				"Y"),filenameAndPath);
		if(num>0){
			out.print("We will notify you by email to collect the access card on next working day.");
		}else{
			out.print("Error!");
		}
	}catch (Exception e) {
		e.printStackTrace();
		log.error("AccessCard-Submit Exception :"+e.getMessage());
		out.print("Submit Exception :"+e.getMessage());
	}finally{
		acd=null;
		out.flush();
		out.close();
	}
}
 
	void getPath(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("text/html;charset=utf-8");
		 PrintWriter out=response.getWriter();
		 String staffcode=request.getParameter("staffcode");
		 
		 try{
/*			 Properties p = new Properties();  
			 String paths = Thread.currentThread().getContextClassLoader().getResource("configure.properties").getPath();
			 p.load(new FileInputStream(paths));
			 String path=p.getProperty("public.namecard.upload.filepath.accesscard");*/
			 String path=Util.getProValue("public.namecard.upload.filepath.accesscard");
			 System.out.println("--------------"+staffcode);
			// System.out.println(path+"----===============------");
			 String answer=UpLoadFile.upPhoto(request, path,staffcode); 
			// System.out.println(answer);
			if(answer!=null){
				if(answer.equals("-1")){
					out.print("-1");
				}else{
					out.print(answer);
				}
			}else{
				out.print("error");
			}
		 }catch (Exception e) {
			e.printStackTrace();
		}
	
	}
 
 
 void Detail(HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	AccessCardDao acd=new AccessCardDaoImpl();
	C_Access c_access=null;
	try{
		out=response.getWriter();
		String refno=request.getParameter("refno");
		c_access=acd.findAdminserviceByRef(refno);
		//获取文件路径
		String path=acd.findAttachment(refno);
		c_access.setRemark1(path);
		JSONArray jsons=JSONArray.fromObject(c_access);
		out.print(jsons.toString());
	}catch (Exception e) {
		e.printStackTrace();
		log.error("AccessCard Detail Exception :"+e.getMessage());
	}finally{
		acd=null;
		c_access=null;
		out.flush();
		out.close();
	}
}
 void HKADM(HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	AccessCardDao acd=new AccessCardDaoImpl();
	try{
		
		out=response.getWriter();
		String refno=request.getParameter("refno");
		String type=request.getParameter("type");
		String exitAccess=request.getParameter("exitAccess");
		String newAccess=request.getParameter("newAccess");
		String remark=request.getParameter("remark");
		int num=acd.modify(refno, type, user,exitAccess,newAccess,remark);
		if(num>0){
			out.print("Success!");
		}else{
			out.print("Error!");
		}
	}catch (Exception e) {
		e.printStackTrace();
		log.error("AccessCard==>HKADM Exception :"+e.getMessage());
		out.print("Exception :"+e.getMessage());
	}finally{
		out.flush();
		out.close();
	}
}
 void complete(HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	AccessCardDao acd=new AccessCardDaoImpl();
	try{
		out=response.getWriter();
		String refno=request.getParameter("refno");
		String type=request.getParameter("type");
		String  paymethod=request.getParameter("paymethod");//支付方式
		String payamount=request.getParameter("payamount");//支付金额
		String payDate=request.getParameter("payDate");//支付时间
		String handle=request.getParameter("handle");//收银员
		String savetype=request.getParameter("savetype");
		String saleno=request.getParameter("saleno");
		String staffname=request.getParameter("staffname");
		String location=request.getParameter("location");
		int num=acd.completed(refno, type, user,new C_Payment(savetype,
				paymethod,
				Double.parseDouble(payamount),
				payDate,
				handle,
				"Y",staffname,location,saleno));
		if(num>0){
			out.print("Success!");
		}else{
			out.print("Error!");
		}
	}catch (Exception e) {
		e.printStackTrace();
		log.error("AccessCard==>Completed  Export Exception :"+e.getMessage());
		out.print("Exception :"+e.getMessage());
	}finally{
		out.flush();
		out.close();
	}
}

 void Ready(HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	AccessCardDao acd=new AccessCardDaoImpl();
	String result="";
	try{
		out=response.getWriter();
		String refno=request.getParameter("refno");
		String type=request.getParameter("type");
		
		String to=request.getParameter("to");
		String localtion=request.getParameter("localtion");
		String types=request.getParameter("types");
		String remark=request.getParameter("remark");
		
		int num=acd.Ready(refno, type, user,to,localtion,types,remark);
		result=Util.returnValue(num);
		/*if(num>0){
			out.print("Success!");
		}else{
			out.print("Error!");
		}*/
	}catch (Exception e) {
		result=Util.joinException(e);
		log.error("AccessCard==>Ready Exception :"+e.getMessage());
		//e.printStackTrace();
		//out.print("Exception :"+e.getMessage());
	}finally{
		out.print(result);
		out.flush();
		out.close();
	}
}

 void VOID(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		AccessCardDao acd=new AccessCardDaoImpl();
		try{
			out=response.getWriter();
			String refno=request.getParameter("refno");
			String type=request.getParameter("type");
			String remark=request.getParameter("remark");
			int num=acd.VOID(refno, type, user,remark);
			if(num>0){
				out.print("Success!");
			}else{
				out.print("Error!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("AccessCard==>VOID Exception :"+e.getMessage());
			out.print("Exception :"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
	}
 
 
 
 
 void delete(HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	AccessCardDao acd=new AccessCardDaoImpl();
	try{
		
		out=response.getWriter();
		String refno=request.getParameter("refno");
		String type=request.getParameter("type");
		int num=acd.Deleted(refno, type, user);
		if(num>0){
			out.print("Success!");
		}else{
			out.print("Error!");
		}
	}catch (Exception e) {
		e.printStackTrace();
		log.error("AccessCard==>Delete Exception :"+e.getMessage());
		out.print("Exception :"+e.getMessage());
	}finally{
		out.flush();
		out.close();
	}
}

	 void downPhoto(HttpServletRequest request, HttpServletResponse response){
		try {
			response.setContentType("text/html;charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			//String path = this.getServletContext().getRealPath("/");//获取绝对路径
			String photoPath=request.getParameter("photoPath");//文件径路
			
			String newString = photoPath.substring(0, 2) + "\\" + photoPath.substring(2, photoPath.length());
			//System.out.println("图片路径--->"+newString);
	     /*   File file = new File(newString);
	 
	        //设置头信息,内容处理的方式,attachment以附件的形式打开,就是进行下载,并设置下载文件的命名
	        response.setHeader("Content-Disposition","attachment;filename="+file.getName());
	        // 创建文件输入流
	        is = new FileInputStream(file);
	        // 响应输出流
			out = response.getOutputStream();
	        // 创建缓冲区
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        while ((len = is.read(buffer)) != -1) {//每读取到1024个字节输出
	            out.write(buffer, 0, len);
	        }*/
	        
			Util.download(newString,photoPath.substring(photoPath.lastIndexOf("\\")+1), response);
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error("AccessCard==>downPhoto Exception :"+e.getMessage());
		}finally{
		}
	 
	 }
	 
	 
	
}
