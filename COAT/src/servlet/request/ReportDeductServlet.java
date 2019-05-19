package servlet.request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;

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

import dao.PaymentDao;
import dao.exp.ExpStaffPosition;
import dao.impl.PaymentDaoImpl;
import entity.C_Epayment_Detail;

import util.DateUtils;

public class ReportDeductServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ReportDeductServlet.class);
	Map<String,HSSFCellStyle> styleMap=null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
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
	@SuppressWarnings("deprecation")
	public void cteateTHRTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateTHRTitleCell"));
	}
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PaymentDao acd=new PaymentDaoImpl();
		HSSFWorkbook wb = new HSSFWorkbook();
		String user=null;
		OutputStream  os=null;
		HSSFSheet sheet=null;
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
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
			cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
			cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styleMap.put("cteateTitleCell", cellstyle);
			
			cellstyle= wb.createCellStyle();
			cellstyle.setFont(fontMap.get("BOLD"));
			cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
			cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
			styleMap.put("cteateTWOTitleCell", cellstyle);		
			
			cellstyle= wb.createCellStyle();
			cellstyle.setWrapText(true);// 自动换行
			cellstyle.setFont(fontMap.get("BOLD"));
			cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
			cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
			styleMap.put("cteateTHRTitleCell", cellstyle);
		
			cellstyle= wb.createCellStyle();
			cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
			cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
			styleMap.put("cteateNumCell", cellstyle);				
		
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String requestType=request.getParameter("requestType");
			Result rss=acd.downPaymentList(startDate, endDate,requestType);
			
			Map<String,String> epaymentList=acd.findEpaymentList(startDate, endDate, requestType);
			
			
			os = response.getOutputStream();//取出输出流
			response.reset();//清空输出流
			//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
			response.setHeader("Content-disposition", "attachment; filename=Add/Deduction_Statement.xls");
		//	ExpStaffPosition expcard=new ExpStaffPosition();
			//list=acd.downAccessList(startDate, endDate, staffcode, staffname, refno, status);
			 sheet = wb.createSheet("Deduct Statement");
			HSSFRow row=sheet.createRow(0);
			sheet.createFreezePane(0, 1);
				row.setHeight((short)400);
				cteateTHRTitleCell(wb,row,(short)0,"Transaction date");
				cteateTHRTitleCell(wb,row,(short)1,"Payout Date");
				cteateTHRTitleCell(wb,row,(short)2,"Consultant Code");
				cteateTHRTitleCell(wb,row,(short)3,"Company Code");
				cteateTHRTitleCell(wb,row,(short)4,"Dept Code");
				cteateTHRTitleCell(wb,row,(short)5,"Item Code");
				cteateTHRTitleCell(wb,row,(short)6,"Budget Code");
				cteateTHRTitleCell(wb,row,(short)7,"Remark ");
				cteateTHRTitleCell(wb,row,(short)8,"BIT ID");
				cteateTHRTitleCell(wb,row,(short)9,"Amount (HKD)");
				cteateTHRTitleCell(wb,row,(short)10,"Scheme");
			
			
			 sheet.setColumnWidth((short)0, 100*50); 
			 
	         sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet.setColumnWidth((short)2, 100*50); 		 // 调整第三列宽度 
	         sheet.setColumnWidth((short)3, 100*50);  // 调整第四列宽度 
	         sheet.setColumnWidth((short)4, 100*50);  // 调整第五列宽度 
	         sheet.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)6); // 调整第四列宽度 
	         sheet.setColumnWidth((short)7, 100*100); // 调整第四列宽度 
	         sheet.setColumnWidth((short)8, 100*50); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)9); // 调整第四列宽度 
			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){
				Map<String, String> itemMap=new HashMap<String, String>();
						itemMap.put("Advertisement", "ADM002");
						itemMap.put("epayment", "ADM003");
						itemMap.put("Stationery", "ADM007");
						itemMap.put("MarketingPremium", "ADM007");
						itemMap.put("AccessCard", "ADM007");
						itemMap.put("Keys", "ADM007");
				boolean two=false;
				HSSFRow row6=null;
				int f=0;
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map<String,Object> rows = rss.getRows()[i];
			            if(!itemMap.get(rows.get("type").toString()).equals("ADM002")){//仅仅itemcode==ADM002
			            	two=true;
			            }
			        	row6=sheet.createRow(f+1);
			        	//row6.setHeight((short)350);
			        	
				        cteateTitleCell(wb,row6,(short)0,rows.get("paymentDate").toString());//Transaction date :payment Date
				        cteateTitleCell(wb,row6,(short)1,DateUtils.EditMonthafter(DateUtils.getDateToday(), 1));//payout Date 当前月的下一个月份
				        cteateTitleCell(wb,row6,(short)2,rows.get("staffcode").toString());//Consultant Code:Staffcode
				        cteateTitleCell(wb,row6,(short)3,"CFS");//Company Code:CFS 不变
				        cteateTitleCell(wb,row6,(short)4,"ADM");//Dept Code ：ADM 不变
				        cteateTitleCell(wb,row6,(short)5,itemMap.get(rows.get("type").toString()));//itemcode:从itemMap中获取
				        cteateTitleCell(wb,row6,(short)6,"");//Budget Code 留空
				        if(rows.get("type").toString().equals("epayment")){
					        for(String key:epaymentList.keySet()){
					        	if(key.equals(rows.get("refno").toString())){
					        		 cteateTitleCell(wb,row6,(short)7,"epayment-"+epaymentList.get(key)+"("+DateUtils.EditMonthafter(rows.get("paymentDate").toString(), 0)+")");//Remark ：request type + payment Date
					        	}
					        }
				       }else{
				    	   cteateTitleCell(wb,row6,(short)7,rows.get("type").toString()+"("+DateUtils.EditMonthafter(rows.get("paymentDate").toString(), 0)+")");//Remark ：request type + payment Date
				       }
				        cteateTitleCell(wb,row6,(short)8,"");//BIT ID留空
				        cteateNumCell(wb,row6,(short)9,"-"+rows.get("paymentAount").toString());//Amount(HKD)：paymentAmount
				        cteateTitleCell(wb,row6,(short)10,"HK");//Scheme： HK不变
				        if(two){
				        	f++;
				        	row6=sheet.createRow(f+1);
					        cteateTitleCell(wb,row6,(short)0,rows.get("paymentDate").toString());//Transaction date :payment Date
					        cteateTitleCell(wb,row6,(short)1,DateUtils.EditMonthafter(DateUtils.getDateToday(), 1));//payout Date 当前月的下一个月份
					        cteateTitleCell(wb,row6,(short)2,rows.get("staffcode").toString());//Consultant Code:Staffcode
					        cteateTitleCell(wb,row6,(short)3,"CFS");//Company Code:CFS 不变
					        cteateTitleCell(wb,row6,(short)4,"ADM");//Dept Code ：ADM 不变
					        cteateTitleCell(wb,row6,(short)5,"ADM010");//itemcode:ADM010固定
					        cteateTitleCell(wb,row6,(short)6,"");//Budget Code 留空
					       if(rows.get("type").toString().equals("epayment")){
						        for(String key:epaymentList.keySet()){
						        	if(key.equals(rows.get("refno").toString())){
						        		 cteateTitleCell(wb,row6,(short)7,"epayment-"+epaymentList.get(key)+"("+DateUtils.EditMonthafter(rows.get("paymentDate").toString(), 0)+")");//Remark ：request type + payment Date
						        	}
						        }
					       }else{
					    	   cteateTitleCell(wb,row6,(short)7,rows.get("type").toString()+"("+DateUtils.EditMonthafter(rows.get("paymentDate").toString(), 0)+")");//Remark ：request type + payment Date
					       }
					        
					        cteateTitleCell(wb,row6,(short)8,"");//BIT ID留空
					        cteateNumCell(wb,row6,(short)9,rows.get("paymentAount").toString());//Amount(HKD)：paymentAmount
					        cteateTitleCell(wb,row6,(short)10,"HK");//Scheme： HK不变
					        two=false;
				        }
				          
			         f++;
			        }  
			}
			wb.write(os);
			
			log.info(user+"==>Payment Deduct==Export=="+DateUtils.getNowDateTime());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Reporting Deduct==>Export"+e);
		}finally{
			wb=null;
			os.flush();
			os.close();
			response.getWriter().print("SUCCESS!");
		}
	
		 
	}

}
