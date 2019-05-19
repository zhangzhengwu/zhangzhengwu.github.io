package servlet.request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import util.DateUtils;
import util.Page;

import dao.PaymentDao;
import dao.exp.ExpStaffPosition;
import dao.impl.PaymentDaoImpl;
import entity.C_Payment;

public class PaymentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger log=Logger.getLogger(PaymentServlet.class);
	String user=null;
	HSSFWorkbook wb=null;
	Map<String,HSSFCellStyle> styleMap=null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request,response);
		}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");
		try{
			 user=(String) request.getSession().getAttribute("adminUsername");//request.getSession().getAttribute("convoy_username").toString();
			if(method.equals("select"))
				select(request, response);
			else if(method.equals("selectReporting"))
				selectReporting(request, response);
			else if(method.equals("down"))
				down(request, response);
			else {
				throw new Exception("Unauthorized access!");
			}
				
		}catch (NullPointerException e) {
			log.error("Payment==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Payment==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		}finally{
			method=null;
		}
	}
	
	@SuppressWarnings("unchecked")
	void selectReporting(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		PaymentDao acd=new PaymentDaoImpl();
		 Page page=new Page();
			List<C_Payment> list=new ArrayList<C_Payment>();
		try{
			out=response.getWriter();
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String requestType=request.getParameter("requestType");
			page.setAllRows(acd.getRow(startDate, endDate, requestType));
			page.setPageSize(15);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("curretPage")));//获取页面当前页码
			list=acd.findAccessList(startDate, endDate,requestType, page);
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
			log.error("Payment==>Search操作出现异常"+e);
			out.print("Exception :"+e.getMessage());
		}finally{
			acd=null;
			list=null;
			out.flush();
			out.close();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void cteateTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateTitleCell"));		
	}
	@SuppressWarnings("deprecation")
	public void cteateTWOTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateTWOTitleCell"));
	}
	@SuppressWarnings("deprecation")
	public void cteateNumCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(Double.parseDouble(val));
		cell.setCellStyle(styleMap.get("cteateNumCell"));		
	}
	void down(HttpServletRequest request, HttpServletResponse response){
		PaymentDao acd=new PaymentDaoImpl();
		 wb = new HSSFWorkbook();
		try{
			HSSFCellStyle cellstyle=null;
			Map<String ,Font> fontMap=new HashMap<String,Font>();
			Font fonts = wb.createFont();
			fonts.setFontHeightInPoints((short)10); //字体大小
			fonts.setFontName("Times New Roman");
			fonts.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
			fontMap.put("BOLD", fonts);
			
			
			styleMap=new HashMap<String,HSSFCellStyle>();
			
			cellstyle= wb.createCellStyle();
			cellstyle.setFont(fontMap.get("BOLD"));
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
			cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
			cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
			styleMap.put("cteateTWOTitleCell", cellstyle);		
			
			cellstyle= wb.createCellStyle();
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
			cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
			cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
			cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styleMap.put("cteateTitleCell", cellstyle);
			

			cellstyle= wb.createCellStyle();
			cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
			cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
			cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
			styleMap.put("cteateNumCell", cellstyle);		
			
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String requestType=request.getParameter("requestType");
			Result rss=acd.downPaymentList(startDate, endDate,requestType);
			Map<String,String> epaymentList=acd.findEpaymentList(startDate, endDate, requestType);
			OutputStream  os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename=Payment_"+DateUtils.Ordercode()+".xls");
			HSSFSheet sheet = wb.createSheet("Payment");
			HSSFRow row=sheet.createRow(0);
			sheet.createFreezePane(0, 1);
			row.setHeight((short)400);
				
			cteateTWOTitleCell(wb,row,(short)0,"Ref No");
			cteateTWOTitleCell(wb,row,(short)1,"Staff Code");
			cteateTWOTitleCell(wb,row,(short)2,"Name");
			cteateTWOTitleCell(wb,row,(short)3,"Location");
			cteateTWOTitleCell(wb,row,(short)4,"Request Type");
			cteateTWOTitleCell(wb,row,(short)5,"Payment Method");
			cteateTWOTitleCell(wb,row,(short)6,"Trace Number");
			cteateTWOTitleCell(wb,row,(short)7,"Payment Amount(HKD)");
			cteateTWOTitleCell(wb,row,(short)8,"管理員 Staff Code");
			cteateTWOTitleCell(wb,row,(short)9,"Payment Date");
			
			 sheet.setColumnWidth((short)0, 100*40); 
			 
	         sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet.setColumnWidth((short)2, 100*50); 		 // 调整第三列宽度 
	         sheet.setColumnWidth((short)3, 100*50);  // 调整第四列宽度 
	         sheet.setColumnWidth((short)4, 100*100);  // 调整第五列宽度 
	         sheet.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)6); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)7); // 调整第四列宽度
	         sheet.setColumnWidth((short)8, 100*50); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)9); // 调整第四列宽度 
			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map<String,Object> rows = rss.getRows()[i];
			            
			        	HSSFRow row6=sheet.createRow(i+1);
				        cteateTitleCell(wb,row6,(short)0,rows.get("refno")+"");
				        cteateTitleCell(wb,row6,(short)1,rows.get("staffcode")+"");
				        cteateTitleCell(wb,row6,(short)2,rows.get("staffname")+"");
				        cteateTitleCell(wb,row6,(short)3,rows.get("location")+"");
				        if(rows.get("type").toString().equals("epayment")){
				        	 for(String key:epaymentList.keySet()){
						        	if(key.equals(rows.get("refno").toString())){
						        		 cteateTitleCell(wb,row6,(short)4,"epayment-"+epaymentList.get(key)+"("+DateUtils.EditMonthafter(rows.get("paymentDate").toString(), 0)+")");//Remark ：request type + payment Date
						        	}
						        }
				        }else{
				        	cteateTitleCell(wb,row6,(short)4,rows.get("type")+"");
				        }
				        
				        cteateTitleCell(wb,row6,(short)5,rows.get("paymentMethod")+"");
				        cteateTitleCell(wb,row6,(short)6,rows.get("saleno")+"");
				        cteateNumCell(wb,row6,(short)7,rows.get("paymentAount")+"");
				        cteateTitleCell(wb,row6,(short)8,rows.get("handleder")+"");
				        cteateTitleCell(wb,row6,(short)9,rows.get("paymentDate")+"");
				          
			         
			        }  
			}
			wb.write(os);
			os.flush();
			os.close();
			log.info(user+"==>Payment==Export=="+DateUtils.getNowDateTime());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Payment==>导出操作出现异常"+e);
		}finally{
			acd=null;
			wb=null;
			
		}
	}
	
	
	
	
	
	
	void select(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		String refno=request.getParameter("refno");
		PaymentDao pd=new PaymentDaoImpl();
		C_Payment cp=null;
		try{
			out=response.getWriter();
			cp=pd.findBYRef(refno);
			JSONArray jsons=JSONArray.fromObject(cp);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Payment==>select操作出现异常"+e);
		}finally{
			pd=null;
			out.flush();
			out.close();
		}
	}

}
