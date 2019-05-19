package servlet.request;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


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
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import antlr.Utils;

import util.DateUtils;
import util.Util;
import dao.AccessCardDao;
import dao.AdminServiceDao;
import dao.C_CompanyDao;
import dao.C_GetMarkPremiumDao;
import dao.C_KeyDao;
import dao.C_RoomSettingDao;
import dao.C_StationeryDao;
import dao.impl.AccessCardDaoImpl;
import dao.impl.AdminServiceDaoImpl;
import dao.impl.C_CompanyDaoImpl;
import dao.impl.C_GetMarkPremiumDaoImpl;
import dao.impl.C_KeyDaoImpl;
import dao.impl.C_RoomSettingDaoImpl;
import dao.impl.C_StationeryDaoImpl;
/**
 * 导出Reporting
 * @author King.Xu
 *
 */
public class ReportingServlet extends HttpServlet {
Logger log=Logger.getLogger(ReportingServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String user=null;
	HSSFWorkbook wb = null;
	HSSFSheet sheet=null;
	
	Map<String,HSSFCellStyle> styleMap=null;
	 SimpleDateFormat sdfus =null;
	 SimpleDateFormat sdf = null;
	 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
		
	}

	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public void cteateYellowTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateYellowTitleCell"));
		
	}
	@SuppressWarnings("deprecation")
	public void cteateOrangeNumTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(Double.parseDouble(val));
		cell.setCellStyle(styleMap.get("cteateOrangeNumTitleCell"));
	}
	@SuppressWarnings("deprecation")
	public void cteateOrangeTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateOrangeTitleCell"));
	}
	
	@SuppressWarnings("deprecation")
	public void cteateSEA_GREENTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateSEA_GREENTitleCell"));
	}
	
	@SuppressWarnings("deprecation")
	public void cteateTHRTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateTHRTitleCell"));
	}
	@SuppressWarnings("deprecation")
	public void cteateTWOTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateTWOTitleCell"));
	}
	@SuppressWarnings("deprecation")
	public void cteateReasonCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateReasonCell"));		
	}
	@SuppressWarnings("deprecation")
	public void cteateTotalCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(Double.parseDouble(val));
		cell.setCellStyle(styleMap.get("cteateTotalCell"));		
	}
	@SuppressWarnings("deprecation")
	public void cteateNumCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(Double.parseDouble(val));
		cell.setCellStyle(styleMap.get("cteateNumCell"));		
	}
	@SuppressWarnings("deprecation")
	public void cteateTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateTitleCell"));		
	}
	@SuppressWarnings("deprecation")
	public void cteateSkyBlueCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateSkyBlueCell"));		
	}
	@SuppressWarnings("deprecation")
	public void cteateLEMON_CHIFFONCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateLEMON_CHIFFONCell"));		
	}
	@SuppressWarnings("deprecation")
	public void cteateGREENCell(HSSFWorkbook wb,HSSFRow row,short col,String val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		cell.setCellStyle(styleMap.get("cteateGREENCell"));		
	}
	
	
	
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		OutputStream  os=null;
		
		try{
			user=(String) request.getSession().getAttribute("adminUsername");
			sdfus = new SimpleDateFormat("dd-MMM-yyyy", Locale.US); 
			sdf = new SimpleDateFormat("MMM", Locale.US);
			
			wb=new HSSFWorkbook();
			HSSFCellStyle cellstyle=null;
			Map<String ,Font> fontMap=new HashMap<String,Font>();
							  styleMap=new HashMap<String,HSSFCellStyle>();
				Font fonts = wb.createFont();
				fonts.setFontHeightInPoints((short)10); //字体大小
				fonts.setFontName("Times New Roman");
				fonts.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
				fontMap.put("BOLD", fonts);
				
				
			
					cellstyle= wb.createCellStyle();
					cellstyle.setWrapText(true);// 自动换行
					cellstyle.setFont(fontMap.get("BOLD"));
					cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
					cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
					cellstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());//黄色
					cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cellstyle.setBorderBottom((short)1);
					cellstyle.setBorderTop((short)1);
					cellstyle.setBorderLeft((short)1);
					cellstyle.setBorderRight((short)1);
				styleMap.put("cteateYellowTitleCell", cellstyle);
				
				cellstyle= wb.createCellStyle();
				
					cellstyle.setFont(fontMap.get("BOLD"));
					cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
					cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
					cellstyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());//橘色
					cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cellstyle.setWrapText(true);// 自动换行
					cellstyle.setBorderBottom((short)1);
					cellstyle.setBorderTop((short)1);
					cellstyle.setBorderLeft((short)1);
					cellstyle.setBorderRight((short)1);
				styleMap.put("cteateOrangeNumTitleCell", cellstyle);
				
					cellstyle= wb.createCellStyle();
					cellstyle.setFont(fontMap.get("BOLD"));
					cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
					cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
					cellstyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());//黄色
					cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					
					cellstyle.setWrapText(true);// 自动换行
					cellstyle.setBorderBottom((short)1);
					cellstyle.setBorderTop((short)1);
					cellstyle.setBorderLeft((short)1);
					cellstyle.setBorderRight((short)1);
				styleMap.put("cteateOrangeTitleCell", cellstyle);
					cellstyle= wb.createCellStyle();
					cellstyle.setFont(fontMap.get("BOLD"));
					cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
					cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
					cellstyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());//黄色
					cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cellstyle.setWrapText(true);// 自动换行
					cellstyle.setBorderBottom((short)1);
					cellstyle.setBorderTop((short)1);
					cellstyle.setBorderLeft((short)1);
					cellstyle.setBorderRight((short)1);
				styleMap.put("cteateSEA_GREENTitleCell", cellstyle);

					cellstyle= wb.createCellStyle();
					cellstyle.setWrapText(true);// 自动换行
					cellstyle.setFont(fontMap.get("BOLD"));
					cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
					cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
					cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
				styleMap.put("cteateTHRTitleCell", cellstyle);
				
					cellstyle= wb.createCellStyle();
					cellstyle.setFont(fontMap.get("BOLD"));
					cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
					cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
				styleMap.put("cteateTWOTitleCell", cellstyle);
				
					cellstyle= wb.createCellStyle();
					cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
					cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
					cellstyle.setBorderBottom((short)1);
					cellstyle.setBorderTop((short)1);
					cellstyle.setBorderLeft((short)1);
					cellstyle.setBorderRight((short)1);
					cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
					cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				styleMap.put("cteateReasonCell", cellstyle);
				
					cellstyle= wb.createCellStyle();
					cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
					cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
					cellstyle.setBorderBottom((short)1);
					cellstyle.setBorderTop((short)1);
					cellstyle.setBorderLeft((short)1);
					cellstyle.setBorderRight((short)1);
					cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
					cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				styleMap.put("cteateTotalCell", cellstyle);
					cellstyle= wb.createCellStyle();
					cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
					cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
					cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
				styleMap.put("cteateNumCell", cellstyle);
				
					cellstyle= wb.createCellStyle();
					cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
					cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
					cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
					cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				styleMap.put("cteateTitleCell", cellstyle);
				
				cellstyle= wb.createCellStyle();
				cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
				cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
				cellstyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());//天蓝
				cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styleMap.put("cteateSkyBlueCell", cellstyle);
			
			cellstyle= wb.createCellStyle();
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
			cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
			cellstyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());//LEMON_CHIFFON
			cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellstyle.setBorderBottom((short)1);
			cellstyle.setBorderTop((short)1);
			cellstyle.setBorderLeft((short)1);
			cellstyle.setBorderRight((short)1);
			styleMap.put("cteateLEMON_CHIFFONCell", cellstyle);
			
			cellstyle= wb.createCellStyle();
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
			cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
			cellstyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());//绿色
			cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styleMap.put("cteateGREENCell", cellstyle);
				
				
				
				
				
				
				
				
				
				
				
			
			
			
			
			
			 user=(String) request.getSession().getAttribute("adminUsername");//request.getSession().getAttribute("convoy_username").toString();
				os = response.getOutputStream();//取出输出流
				response.reset();//清空输出流
				//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
				response.setHeader("Content-disposition", "attachment; filename="+(new String("Summary Table_".getBytes(),"iso_8859_1"))+DateUtils.getDateToday().replaceAll("-", "").concat((int)(Math.random()*10000)+"")+".xls");
				response.setContentType("application/ms-excel;charset=UTF-8");

				
				sheet=wb.createSheet("AccessCard");
				downs(startDate,endDate,sheet);
				
				sheet=wb.createSheet("Key Request");
				downforkey(startDate,endDate,sheet);
				
				sheet=wb.createSheet("ADM Service Request");
				 downforAdminService(startDate,endDate,sheet);
				 
				 sheet=wb.createSheet("Room Setting");
				 downforRoomSetting(startDate,endDate,sheet);
				 
				 sheet=wb.createSheet("Marketing Premium");
				 downforMarketingPremium(startDate,endDate,sheet);
				 
				 sheet=wb.createSheet("Stationery");
				  downforStationery(startDate,endDate,sheet);
				  
				  sheet=wb.createSheet("CompanyAsset");
				 downforCompanyAsset(startDate,endDate,sheet);
				 
				 sheet=wb.createSheet("ePayment");
				 downforepayment(startDate,endDate,sheet);
				 
				 
				 sheet=wb.createSheet("Recruitment Advertising Request");
				 downforAdvertising(startDate,endDate,sheet);
				 
				 
				 
				wb.write(os);
				log.info(user+"==>Report==Export=="+DateUtils.getNowDateTime());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Reporting ==>Export"+e);
			
		}finally{
			sdfus=sdf=null;
			wb=null;
			sheet=null;
			os.flush();
			os.close();
			response.getWriter().print("SUCCESS!");
		}
	}
	/**
	 * Access Card
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	void downs(String startDate, String endDate,HSSFSheet sheet) throws Exception{
		AccessCardDao acd=new AccessCardDaoImpl();
	
		Result rss=null;
		try{
			
			rss=acd.downReportingForAccessCard(startDate, endDate);
			HSSFRow row_1=sheet.createRow(0);
			cteateTWOTitleCell(wb,row_1,(short)0,"Convoy Financial Services Limited");
			cteateTWOTitleCell(wb,row_1,(short)9,"Request Options:");
			row_1.setHeight((short)500);
			
			
			HSSFRow row_2=sheet.createRow(1);
			cteateTWOTitleCell(wb,row_2,(short)0,"Access Card & Photo Sticker Request");
			cteateYellowTitleCell(wb,row_2,(short)9,"AccessCard");
			cteateYellowTitleCell(wb,row_2,(short)10,"Photo Sticker");
			cteateYellowTitleCell(wb,row_2,(short)11,"Reason");
			row_2.setHeight((short)500);
			
			HSSFRow row=sheet.createRow(2);
			sheet.createFreezePane(0,3);
			row.setHeight((short)500);	
				
			cteateTHRTitleCell(wb,row,(short)0,"Month");
			cteateTHRTitleCell(wb,row,(short)1,"Request date");
			cteateTHRTitleCell(wb,row,(short)2,"Order Number");
			cteateTHRTitleCell(wb,row,(short)3,"Staff Code");
			cteateTHRTitleCell(wb,row,(short)4,"Staff Name");
			cteateTHRTitleCell(wb,row,(short)5,"Staff/Consultant");
			cteateTHRTitleCell(wb,row,(short)6,"Cash");
			
			cteateTHRTitleCell(wb,row,(short)7,"Octopus");
			cteateTHRTitleCell(wb,row,(short)8,"EPS");
			cteateYellowTitleCell(wb,row,(short)9,"");
			cteateYellowTitleCell(wb,row,(short)10,"");
			cteateYellowTitleCell(wb,row,(short)11,"");
			
			sheet.addMergedRegion(new Region(1, (short)9, 2,(short)9));
			sheet.addMergedRegion(new Region(1, (short)10, 2,(short)10));
			sheet.addMergedRegion(new Region(1, (short)11, 2,(short)11));
			 sheet.setColumnWidth((short)0, 100*20); 
			 sheet.setColumnWidth((short)1, 100*40); 
			 sheet.setColumnWidth((short)2, 100*42); 
			 sheet.setColumnWidth((short)3, 100*40); 
			 sheet.setColumnWidth((short)4, 100*40); 
			 sheet.setColumnWidth((short)5, 100*35); 
			 sheet.setColumnWidth((short)6, 100*30); 
			 sheet.setColumnWidth((short)7, 100*30); 
			 sheet.setColumnWidth((short)8, 100*30); 
			 sheet.setColumnWidth((short)9, 100*45); 
			 sheet.setColumnWidth((short)10, 100*45); 
			 sheet.setColumnWidth((short)11, 100*60); 
			 

			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map rows = rss.getRows()[i];
			      /*   for (Object key : rows.keySet()) {
			            	   System.out.println("key= "+ key + " and value= " + rows.get(key));
			            	  }
			            System.out.println();*/
			           
			        	HSSFRow row6=sheet.createRow(2+i+1);
			        	row6.setHeight((short)300);
				        cteateTitleCell(wb,row6,(short)0,sdf.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)1,sdfus.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)2,rows.get("refno").toString());
				        cteateTitleCell(wb,row6,(short)3,rows.get("staffcode").toString());
				        cteateTitleCell(wb,row6,(short)4,rows.get("staffname").toString());
				        cteateTitleCell(wb,row6,(short)5,rows.get("userType").toString());
			        	cteateNumCell(wb,row6,(short)6,rows.get("Cash").toString());//Cash
			        	cteateNumCell(wb,row6,(short)7,rows.get("Octopus").toString());//Octopus
			            cteateNumCell(wb,row6,(short)8,rows.get("EPS").toString());//EPS
			            
			            if(!Util.objIsNULL(rows.get("staffcard").toString()))
			            	cteateTotalCell(wb,row6,(short)9,rows.get("staffcard").toString());
			            else 
			            	 cteateReasonCell(wb,row6,(short)9,rows.get("staffcard").toString());
				
			            if(!Util.objIsNULL(rows.get("photosticker").toString()))
			            	cteateTotalCell(wb,row6,(short)10,rows.get("photosticker").toString());
			            else 
			            	 cteateReasonCell(wb,row6,(short)10,rows.get("photosticker").toString());
				        
				        cteateReasonCell(wb,row6,(short)11,rows.get("reason").toString());
			         
			        }  
			}
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Access Card 导出异常=="+e.getMessage());
		}finally{
			acd=null;
			
			rss=null;
		}
	
		
	}
	
	
	/**
	 * Keys Request
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	void downforkey(String startDate, String endDate,HSSFSheet sheet) throws Exception{

		C_KeyDao ckd=new C_KeyDaoImpl();
		Result rss=null;
		try{
			
			rss=ckd.downReportingForAccessCard(startDate, endDate);
			HSSFRow row_1=sheet.createRow(0);
			//System.out.println(wb+"--"+expcard+"==="+row_1);
			cteateTWOTitleCell(wb,row_1,(short)0,"Convoy Financial Services Limited");
			row_1.setHeight((short)400);
			
			
			HSSFRow row_2=sheet.createRow(1);
			cteateTWOTitleCell(wb,row_2,(short)0,"Key Request");
			cteateYellowTitleCell(wb,row_2,(short)9,"AccessCard");
			cteateYellowTitleCell(wb,row_2,(short)10,"");
			cteateYellowTitleCell(wb,row_2,(short)11,"");
			row_2.setHeight((short)400);
			
			HSSFRow row=sheet.createRow(2);
			sheet.createFreezePane(0,3);
			row.setHeight((short)400);	
				
			cteateTHRTitleCell(wb,row,(short)0,"Month");
			cteateTHRTitleCell(wb,row,(short)1,"Request date");
			cteateTHRTitleCell(wb,row,(short)2,"Order Number");
			cteateTHRTitleCell(wb,row,(short)3,"Staff Code");
			cteateTHRTitleCell(wb,row,(short)4,"Staff Name");
			cteateTHRTitleCell(wb,row,(short)5,"Staff/Consultant");
			cteateTHRTitleCell(wb,row,(short)6,"Cash");
			cteateTHRTitleCell(wb,row,(short)7,"Octopus");
			cteateTHRTitleCell(wb,row,(short)8,"EPS");
			cteateYellowTitleCell(wb,row,(short)9,"Locker");
			cteateYellowTitleCell(wb,row,(short)10,"Desk Drawer Key");
			cteateYellowTitleCell(wb,row,(short)11,"Pigeon Box Key");
			
			sheet.addMergedRegion(new Region(1, (short)9, 1,(short)11));
			
			
			 sheet.setColumnWidth((short)0, 100*20); 
			 sheet.setColumnWidth((short)1, 100*40); 
			 sheet.setColumnWidth((short)2, 100*42); 
			 sheet.setColumnWidth((short)3, 100*40); 
			 sheet.setColumnWidth((short)4, 100*40); 
			 sheet.setColumnWidth((short)5, 100*35); 
			 sheet.setColumnWidth((short)6, 100*30); 
			 sheet.setColumnWidth((short)7, 100*30); 
			 sheet.setColumnWidth((short)8, 100*30); 
			 sheet.setColumnWidth((short)9, 100*45); 
			 sheet.setColumnWidth((short)10, 100*45); 
			 sheet.setColumnWidth((short)11, 100*60); 
			 

			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map<String,Object> rows = rss.getRows()[i];
			        	HSSFRow row6=sheet.createRow(2+i+1);
			        	row6.setHeight((short)300);
				        cteateTitleCell(wb,row6,(short)0,sdf.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)1,sdfus.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)2,rows.get("refno").toString());
				        cteateTitleCell(wb,row6,(short)3,rows.get("staffcode").toString());
				        cteateTitleCell(wb,row6,(short)4,rows.get("staffname").toString());
				        cteateTitleCell(wb,row6,(short)5,rows.get("userType").toString());
				        
			        	cteateNumCell(wb,row6,(short)6,rows.get("Cash").toString());//Cash
			        	cteateNumCell(wb,row6,(short)7,rows.get("Octopus").toString());//Octopus
			            cteateNumCell(wb,row6,(short)8,rows.get("EPS").toString());//EPS
			            if(!Util.objIsNULL(rows.get("locker").toString()))
			            	cteateTotalCell(wb,row6,(short)9,rows.get("locker").toString());
			            else 
			            	 cteateReasonCell(wb,row6,(short)9,rows.get("locker").toString());
							
			            if(!Util.objIsNULL(rows.get("deskDrawer").toString()))
			            	cteateTotalCell(wb,row6,(short)10,rows.get("deskDrawer").toString());
			            else 
			            	 cteateReasonCell(wb,row6,(short)10,rows.get("deskDrawer").toString());
							
			            if(!Util.objIsNULL(rows.get("pigenBox").toString()))
			            	cteateTotalCell(wb,row6,(short)11,rows.get("pigenBox").toString());
			            else 
			            	 cteateReasonCell(wb,row6,(short)11,rows.get("pigenBox").toString());
			         
			        }  
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Key Request 导出异常=="+e.getMessage());
		}finally{
			ckd=null;
			
			rss=null;
		}
	
		
	}
	

/**
 * ADM Service
 * @param request
 * @param response
 * @throws Exception 
 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	void downforAdminService(String startDate, String endDate,HSSFSheet sheet) throws Exception{

		AdminServiceDao ckd=new AdminServiceDaoImpl();
		Result rss=null;
		try{
			
			rss=ckd.downReportingForADMService(startDate, endDate);
			
			
			HSSFRow row_1=sheet.createRow(0);
			cteateTWOTitleCell(wb,row_1,(short)0,"Convoy Financial Services Limited");
			cteateTWOTitleCell(wb,row_1,(short)6,"Request Options");
			row_1.setHeight((short)400);
			
			
			HSSFRow row_2=sheet.createRow(1);
			cteateTWOTitleCell(wb,row_2,(short)0,"ADM Service Request");
			cteateYellowTitleCell(wb,row_2,(short)6,"Change of Fluorescent Tube");
			cteateYellowTitleCell(wb,row_2,(short)7,"Desk Phone Repair");
			cteateYellowTitleCell(wb,row_2,(short)8,"Desk Phone Reset Password");
			cteateYellowTitleCell(wb,row_2,(short)9,"Copier Repair");
			cteateYellowTitleCell(wb,row_2,(short)10,"Office Maintenance");
			cteateYellowTitleCell(wb,row_2,(short)11,"");
			
			row_2.setHeight((short)400);
			
			HSSFRow row=sheet.createRow(2);
			sheet.createFreezePane(0,3);
			row.setHeight((short)400);	
				
			cteateTHRTitleCell(wb,row,(short)0,"Month");
			cteateTHRTitleCell(wb,row,(short)1,"Request date");
			cteateTHRTitleCell(wb,row,(short)2,"Order Number");
			cteateTHRTitleCell(wb,row,(short)3,"Staff Code");
			cteateTHRTitleCell(wb,row,(short)4,"Staff Name");
			cteateTHRTitleCell(wb,row,(short)5,"Staff/Consultant");
			cteateYellowTitleCell(wb,row,(short)6,"");
			cteateYellowTitleCell(wb,row,(short)7,"");
			cteateYellowTitleCell(wb,row,(short)8,"");
			cteateYellowTitleCell(wb,row,(short)9,"");
			cteateYellowTitleCell(wb,row,(short)10,"");
			cteateOrangeTitleCell(wb,row,(short)10,"Number of Service");
			cteateOrangeTitleCell(wb,row,(short)11,"Description");
			
	
			
			sheet.addMergedRegion(new Region(1, (short)6, 2,(short)6));
			sheet.addMergedRegion(new Region(1, (short)7, 2,(short)7));
			sheet.addMergedRegion(new Region(1, (short)8, 2,(short)8));
			sheet.addMergedRegion(new Region(1, (short)9, 2,(short)9));
			sheet.addMergedRegion(new Region(1, (short)10, 1,(short)11));
			
			
			 sheet.setColumnWidth((short)0, 100*20); 
			 sheet.setColumnWidth((short)1, 100*40); 
			 sheet.setColumnWidth((short)2, 100*42); 
			 sheet.setColumnWidth((short)3, 100*40); 
			 sheet.setColumnWidth((short)4, 100*40); 
			 sheet.setColumnWidth((short)5, 100*35); 
			 sheet.setColumnWidth((short)6, 100*80); 
			 sheet.setColumnWidth((short)7, 100*50); 
			 sheet.setColumnWidth((short)8, 100*50); 
			 sheet.setColumnWidth((short)9, 100*50); 
			 sheet.setColumnWidth((short)10, 100*60); 
			 sheet.setColumnWidth((short)11, 100*60); 
			 

			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map<String,Object> rows = rss.getRows()[i];
			           
			        	HSSFRow row6=sheet.createRow(2+i+1);
			        	row6.setHeight((short)300);
				        cteateTitleCell(wb,row6,(short)0,sdf.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)1,sdfus.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)2,rows.get("refno").toString());
				        cteateTitleCell(wb,row6,(short)3,rows.get("staffcode").toString());
				        cteateTitleCell(wb,row6,(short)4,rows.get("staffname").toString());
				        cteateTitleCell(wb,row6,(short)5,rows.get("userType").toString());
				        
				        if(!Util.objIsNULL(rows.get("fluorTube").toString()))
				        	cteateTotalCell(wb,row6,(short)6,rows.get("fluorTube").toString());//Cash
				        else
				        	cteateReasonCell(wb,row6,(short)6,rows.get("fluorTube").toString());//Cash
				      
				        if(!Util.objIsNULL(rows.get("PhoneRepair").toString()))
				        	cteateTotalCell(wb,row6,(short)7,rows.get("PhoneRepair").toString());//Octopus
				        else
				        	cteateReasonCell(wb,row6,(short)7,rows.get("PhoneRepair").toString());//Octopus
				       
				        if(!Util.objIsNULL(rows.get("Phonerpass").toString()))
				        	cteateTotalCell(wb,row6,(short)8,rows.get("Phonerpass").toString());//EPS
				        else
				        	cteateReasonCell(wb,row6,(short)8,rows.get("Phonerpass").toString());//EPS
				        
				        if(!Util.objIsNULL(rows.get("copierRepair").toString()))
				        	cteateTotalCell(wb,row6,(short)9,rows.get("copierRepair").toString());
				        else
				        	cteateReasonCell(wb,row6,(short)9,rows.get("copierRepair").toString());
				        
				        if(!Util.objIsNULL(rows.get("officeMaintenance").toString()))
				        	cteateTotalCell(wb,row6,(short)10,rows.get("officeMaintenance").toString());
				        else
				        	cteateReasonCell(wb,row6,(short)10,rows.get("officeMaintenance").toString());
				        cteateReasonCell(wb,row6,(short)11,rows.get("description").toString());
			         
			        }  
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ADM Service 导出异常=="+e.getMessage());
		}finally{
			ckd=null;
			;
			rss=null;
		}
	
		
	}
	
	
	/**
	 * Room Setting
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	void downforRoomSetting(String startDate, String endDate,HSSFSheet sheet) throws Exception{

		C_RoomSettingDao ckd=new C_RoomSettingDaoImpl();
		Result rss=null;
		try{
			
			rss=ckd.downReportingForRoomSetting(startDate, endDate);
			HSSFRow row_1=sheet.createRow(0);
			cteateTWOTitleCell(wb,row_1,(short)0,"Convoy Financial Services Limited");
			row_1.setHeight((short)400);
			
			
			HSSFRow row_2=sheet.createRow(1);
			cteateTWOTitleCell(wb,row_2,(short)0,"Room Setting");
			cteateYellowTitleCell(wb,row_2,(short)6,"Location");
			cteateYellowTitleCell(wb,row_2,(short)7,"");
			row_2.setHeight((short)400);
			
			HSSFRow row=sheet.createRow(2);
			sheet.createFreezePane(0,3);
			row.setHeight((short)400);	
				
			cteateTHRTitleCell(wb,row,(short)0,"Month");
			cteateTHRTitleCell(wb,row,(short)1,"Request date");
			cteateTHRTitleCell(wb,row,(short)2,"Order Number");
			cteateTHRTitleCell(wb,row,(short)3,"Staff Code");
			cteateTHRTitleCell(wb,row,(short)4,"Staff Name");
			cteateTHRTitleCell(wb,row,(short)5,"Staff/Consultant");
			cteateYellowTitleCell(wb,row,(short)6,"@CONVOY");
			cteateYellowTitleCell(wb,row,(short)7,"CP3");
			sheet.addMergedRegion(new Region(1, (short)6, 1,(short)7));
			 sheet.setColumnWidth((short)0, 100*20); 
			 sheet.setColumnWidth((short)1, 100*40); 
			 sheet.setColumnWidth((short)2, 100*42); 
			 sheet.setColumnWidth((short)3, 100*40); 
			 sheet.setColumnWidth((short)4, 100*40); 
			 sheet.setColumnWidth((short)5, 100*35); 
			 sheet.setColumnWidth((short)6, 100*50); 
			 sheet.setColumnWidth((short)7, 100*50); 
			
			 

			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			        	Map<String,Object> rows = rss.getRows()[i];
			        	HSSFRow row6=sheet.createRow(2+i+1);
			        	row6.setHeight((short)300);
				        cteateTitleCell(wb,row6,(short)0,sdf.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)1,sdfus.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)2,rows.get("refno").toString());
				        cteateTitleCell(wb,row6,(short)3,rows.get("staffcode").toString());
				        cteateTitleCell(wb,row6,(short)4,rows.get("staffname").toString());
				        cteateTitleCell(wb,row6,(short)5,rows.get("userType").toString());
				        
				        if(!Util.objIsNULL(rows.get("convoy").toString()))
				        	cteateTotalCell(wb,row6,(short)6,rows.get("convoy").toString());//Cash
				        else
				        	cteateReasonCell(wb,row6,(short)6,rows.get("convoy").toString());//Cash
				      
				     
				        if(!Util.objIsNULL(rows.get("CP3").toString()))
				        	cteateTotalCell(wb,row6,(short)7,rows.get("CP3").toString());//Octopus
				        else
				        	cteateReasonCell(wb,row6,(short)7,rows.get("CP3").toString());//Octopus
				       
					
				    
			         
			        }  
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Room Seeting 导出异常=="+e.getMessage());
		}finally{
			ckd=null;
			
			rss=null;
		}
	
		
	}
	
	/**
	 * Marketing Premium
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	void downforMarketingPremium(String startDate, String endDate,HSSFSheet sheet) throws Exception{

		C_GetMarkPremiumDao ckd=new C_GetMarkPremiumDaoImpl();
		 Map<String,String> zi=null;
		 Map<String,Map<String,String>> zhu=null;
		 Result rss=null;
		 Result store=null;
		 Result stores=null;
		try{
			
			rss=ckd.downReportingForMarketingPremium(startDate, endDate);
			store=ckd.findStore(startDate,endDate);//获取库存
			stores=ckd.chargeStore(startDate,endDate);//获取库存使用量
			zhu= zhuanhuan(stores);//库存使用量Map的形式
			zi=new HashMap<String,String>();
			 
		
			
			HSSFRow row_1=sheet.createRow(0);
			cteateTWOTitleCell(wb,row_1,(short)0,"Convoy Financial Services Limited");
			cteateSEA_GREENTitleCell(wb,row_1,(short)10,"Item Code");
			row_1.setHeight((short)400);
			
			
			HSSFRow row_2=sheet.createRow(1);
			cteateTWOTitleCell(wb,row_2,(short)0,"Marketing Premium");
			cteateYellowTitleCell(wb,row_2,(short)10,"Description");
			row_2.setHeight((short)480);
			
			HSSFRow row=sheet.createRow(2);
			sheet.createFreezePane(0,3);
			row.setHeight((short)400);	
				
			cteateTHRTitleCell(wb,row,(short)0,"Month");
			cteateTHRTitleCell(wb,row,(short)1,"Request date");
			cteateTHRTitleCell(wb,row,(short)2,"Order Number");
			cteateTHRTitleCell(wb,row,(short)3,"Staff Code");
			cteateTHRTitleCell(wb,row,(short)4,"Staff Name");
			cteateTHRTitleCell(wb,row,(short)5,"Staff/Consultant");
			cteateTHRTitleCell(wb,row,(short)6,"Cash");
			cteateTHRTitleCell(wb,row,(short)7,"C-Club");
			cteateTHRTitleCell(wb,row,(short)8,"Octopus");
			cteateTHRTitleCell(wb,row,(short)9,"EPS");
			cteateOrangeTitleCell(wb,row,(short)10,"Stock On Hand");
			cteateTHRTitleCell(wb,row,(short)11,"");
			
			
			if(store!=null && store.getRowCount()!=0){  
		        for(int i=0;i<store.getRowCount();i++){  
		        	Map<String,Object> rows = store.getRows()[i];
		            cteateSEA_GREENTitleCell(wb,row_1,(short)(11+i),rows.get("procode").toString());
		            cteateYellowTitleCell(wb,row_2,(short)(11+i),rows.get("englishname").toString());
		            cteateOrangeNumTitleCell(wb,row,(short)(11+i),rows.get("quantity").toString());
		            sheet.setColumnWidth((short)(11+i), 100*60); 
		        }
		        
			}
			
			
			 sheet.setColumnWidth((short)0, 100*20); 
			 sheet.setColumnWidth((short)1, 100*40); 
			 sheet.setColumnWidth((short)2, 100*42); 
			 sheet.setColumnWidth((short)3, 100*40); 
			 sheet.setColumnWidth((short)4, 100*80); 
			 sheet.setColumnWidth((short)5, 100*35); 
			 sheet.setColumnWidth((short)6, 100*30); 
			 sheet.setColumnWidth((short)7, 100*30); 
			 sheet.setColumnWidth((short)8, 100*30); 
			 sheet.setColumnWidth((short)9, 100*30); 
			 sheet.setColumnWidth((short)10, 100*40); 
			 
			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map rows = rss.getRows()[i];
			           
			        	HSSFRow row6=sheet.createRow(2+i+1);
			        	row6.setHeight((short)300);
				        cteateTitleCell(wb,row6,(short)0,sdf.format(DateUtils.StrToDate(rows.get("orderdate").toString())));
				        cteateTitleCell(wb,row6,(short)1,sdfus.format(DateUtils.StrToDate(rows.get("orderdate").toString())));
				        cteateTitleCell(wb,row6,(short)2,rows.get("ordercode").toString());
				        cteateTitleCell(wb,row6,(short)3,rows.get("clientcode").toString());
				        cteateTitleCell(wb,row6,(short)4,rows.get("clientname").toString());
				        cteateTitleCell(wb,row6,(short)5,rows.get("orderType").toString());
				        cteateNumCell(wb,row6,(short)6,rows.get("Cash").toString());
				        cteateNumCell(wb,row6,(short)7,rows.get("Club").toString());
				        cteateNumCell(wb,row6,(short)8,rows.get("Octopus").toString());
				        cteateNumCell(wb,row6,(short)9,rows.get("EPS").toString());
				        cteateTitleCell(wb,row6,(short)10,"");
				        zi=zhu.get(rows.get("ordercode").toString());
				        if(!Util.objIsNULL(zi)){
						        Set set =zi.keySet();
						        int num=1;
						        for(Object key:set){
						        	
						        	 for(int h=0;h<store.getRowCount();h++){
						        		 Map rowss = store.getRows()[h];
						        		// System.out.println(key+"---"+rowss.get("procode").toString());
						        		 if(key.equals(rowss.get("procode").toString())){
						        			
						        			 cteateTotalCell(wb,row6,(short)(h+11),"-"+((int)Double.parseDouble(zi.get(key))+""));
						        		 }else{
						        			 if(num==1)
						        				 cteateReasonCell(wb,row6,(short)(h+11),"");
						        		 }
						        	 }
						        	
						        	num++;
						        }
				        }else{
				        	 for(int h=0;h<store.getRowCount();h++){
				        		 cteateReasonCell(wb,row6,(short)(h+11),"");
				        	 }
				        }
			         
			        }  
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Marketing Premium 导出异常=="+e.getMessage());
		}finally{
			ckd=null;
			
			zi=null;
			zhu=null;
			rss=null;
			store=null;
			stores=null;
		}
	
		
	}
	
	
	
	

	/**
	 * Stationery
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	void downforStationery(String startDate, String endDate,HSSFSheet sheet) throws Exception{

		C_StationeryDao cmd=new C_StationeryDaoImpl();
		 Map<String,String> zi=null;
		 Map<String,Map<String,String>> zhu=null;
		 Result rss=null;
		 Result store=null;
		 Result stores=null;
		try{
			
			 rss=cmd.downReportingForStationery(startDate, endDate);//基本数据
			 store=cmd.findStore(startDate, endDate);//库存
			 stores=cmd.chargeStore(startDate,endDate);//获取库存使用量
			  zhu= zhuanhuan(stores);//库存使用量Map的形式
			  zi=new HashMap<String,String>();
			HSSFRow row_1=sheet.createRow(0);
			cteateTWOTitleCell(wb,row_1,(short)0,"Convoy Financial Services Limited");
			cteateSEA_GREENTitleCell(wb,row_1,(short)10,"Item Code");
			row_1.setHeight((short)400);
			
			
			HSSFRow row_2=sheet.createRow(1);
			cteateTWOTitleCell(wb,row_2,(short)0,"Stationery");
			cteateYellowTitleCell(wb,row_2,(short)10,"Description");
			row_2.setHeight((short)480);
			
			HSSFRow row=sheet.createRow(2);
			sheet.createFreezePane(0,3);
			row.setHeight((short)400);	
				
			cteateTHRTitleCell(wb,row,(short)0,"Month");
			cteateTHRTitleCell(wb,row,(short)1,"Request date");
			cteateTHRTitleCell(wb,row,(short)2,"Order Number");
			cteateTHRTitleCell(wb,row,(short)3,"Staff Code");
			cteateTHRTitleCell(wb,row,(short)4,"Staff Name");
			cteateTHRTitleCell(wb,row,(short)5,"Staff/Consultant");
			cteateTHRTitleCell(wb,row,(short)6,"Cash");
			cteateTHRTitleCell(wb,row,(short)7,"C-Club");
			cteateTHRTitleCell(wb,row,(short)8,"Octopus");
			cteateTHRTitleCell(wb,row,(short)9,"EPS");
			cteateOrangeTitleCell(wb,row,(short)10,"Stock On Hand");
			cteateTHRTitleCell(wb,row,(short)11,"");
			
			if(store!=null && store.getRowCount()!=0){  
		        for(int i=0;i<store.getRowCount();i++){  
		        	Map<String,Object> rows = store.getRows()[i];
		            cteateSEA_GREENTitleCell(wb,row_1,(short)(11+i),rows.get("procode").toString());
		            cteateYellowTitleCell(wb,row_2,(short)(11+i),rows.get("englishname").toString());
		            cteateOrangeNumTitleCell(wb,row,(short)(11+i),rows.get("quantity").toString());
		            sheet.setColumnWidth((short)(11+i), 100*60); 
		        }
		        
			}
			 sheet.setColumnWidth((short)0, 100*20); 
			 sheet.setColumnWidth((short)1, 100*40); 
			 sheet.setColumnWidth((short)2, 100*42); 
			 sheet.setColumnWidth((short)3, 100*40); 
			 sheet.setColumnWidth((short)4, 100*80); 
			 sheet.setColumnWidth((short)5, 100*35); 
			 sheet.setColumnWidth((short)6, 100*30); 
			 sheet.setColumnWidth((short)7, 100*30); 
			 sheet.setColumnWidth((short)8, 100*30); 
			 sheet.setColumnWidth((short)9, 100*30); 
			 sheet.setColumnWidth((short)10, 100*40); 
			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map rows = rss.getRows()[i];
			        	HSSFRow row6=sheet.createRow(2+i+1);
			        	row6.setHeight((short)300);
				        cteateTitleCell(wb,row6,(short)0,sdf.format(DateUtils.StrToDate(rows.get("orderdate").toString())));
				        cteateTitleCell(wb,row6,(short)1,sdfus.format(DateUtils.StrToDate(rows.get("orderdate").toString())));
				        cteateTitleCell(wb,row6,(short)2,rows.get("ordercode").toString());
				        cteateTitleCell(wb,row6,(short)3,rows.get("clientcode").toString());
				        cteateTitleCell(wb,row6,(short)4,rows.get("clientname").toString());
				        cteateTitleCell(wb,row6,(short)5,rows.get("StaffOrCons").toString());
				        cteateNumCell(wb,row6,(short)6,rows.get("Cash").toString());
				        cteateNumCell(wb,row6,(short)7,rows.get("C-Club").toString());
				        cteateNumCell(wb,row6,(short)8,rows.get("Octopus").toString());
				        cteateNumCell(wb,row6,(short)9,rows.get("EPS").toString());
				        cteateTitleCell(wb,row6,(short)10,"");
				        zi=zhu.get(rows.get("ordercode").toString());
				        if(!Util.objIsNULL(zi)){
						        Set set =zi.keySet();
						        int num=1;
						        for(Object key:set){
						        	 for(int h=0;h<store.getRowCount();h++){
						        		 Map rowss = store.getRows()[h];
						        		 if(key.equals(rowss.get("procode").toString())){
						        			 cteateTotalCell(wb,row6,(short)(h+11),"-"+((int)Double.parseDouble(zi.get(key))));
						        		 }else{
						        			 if(num==1)
						        				 cteateReasonCell(wb,row6,(short)(h+11),"");
						        		 }
						        	 }
						        	 num++;
						        }
				        }else{
				        	 for(int h=0;h<store.getRowCount();h++){
				        		 cteateReasonCell(wb,row6,(short)(h+11),"");
				        	 }
				        }
			        }  
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Stationery 导出异常=="+e.getMessage());
		}finally{
			cmd=null;
			
			zhu=null;
			zi=null;
			rss=null;
			store=null;
			stores=null;
		}
	
		
	}
	
	
	

	/**
	 * Company Asset Borrow
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	void downforCompanyAsset(String startDate, String endDate,HSSFSheet sheet) throws Exception{
		
		C_CompanyDao cd=new C_CompanyDaoImpl();
		Map<String,Map<String,String>> zhu=null;
		Map<String,String> zi=null;
		 Result rss=null;
		 Result store=null;
		 Result stores=null;
		
		try{
			
			 rss=cd.downReportingForCompanyAsset(startDate, endDate);
			 store=cd.findStore(startDate, endDate);
			 stores=cd.chargeStore(startDate,endDate);//获取库存使用量
			 zhu= zhuanhuan_ref(stores);//库存使用量Map的形式
			 zi=new HashMap<String,String>();
			 
			HSSFRow row_1=sheet.createRow(0);
			cteateTWOTitleCell(wb,row_1,(short)0,"Convoy Financial Services Limited");
			row_1.setHeight((short)400);
			
			HSSFRow row_2=sheet.createRow(1);
			cteateTWOTitleCell(wb,row_2,(short)0,"Request for Company Asset");
			cteateSEA_GREENTitleCell(wb,row_2,(short)8,"Item Code");
			row_2.setHeight((short)400);
			
			HSSFRow row=sheet.createRow(2);
			sheet.createFreezePane(0,3);
			row.setHeight((short)400);	
			cteateTHRTitleCell(wb,row,(short)0,"Month");
			cteateTHRTitleCell(wb,row,(short)1,"Request date");
			cteateTHRTitleCell(wb,row,(short)2,"Order Number");
			cteateTHRTitleCell(wb,row,(short)3,"Staff Code");
			cteateTHRTitleCell(wb,row,(short)4,"Staff Name");
			cteateTHRTitleCell(wb,row,(short)5,"Staff/Consultant");
			cteateTHRTitleCell(wb,row,(short)6,"Date of collection");
			cteateTHRTitleCell(wb,row,(short)7,"Date of Return");
			cteateYellowTitleCell(wb,row,(short)8,"Description");
			
			if(store!=null && store.getRowCount()!=0){  
		        for(int i=0;i<store.getRowCount();i++){  
		        	Map<String,Object> rows = store.getRows()[i];
		            cteateSEA_GREENTitleCell(wb,row_2,(short)(9+i),rows.get("procode").toString());
		            cteateYellowTitleCell(wb,row,(short)(9+i),rows.get("englishname").toString());
		            sheet.setColumnWidth((short)(9+i), 100*110); 
		        }
		        
			}
			
			 sheet.setColumnWidth((short)0, 100*20); 
			 sheet.setColumnWidth((short)1, 100*40); 
			 sheet.setColumnWidth((short)2, 100*42); 
			 sheet.setColumnWidth((short)3, 100*40); 
			 sheet.setColumnWidth((short)4, 100*80); 
			 sheet.setColumnWidth((short)5, 100*50); 
			 sheet.setColumnWidth((short)6, 100*50); 
			 sheet.setColumnWidth((short)7, 100*50); 
			 sheet.setColumnWidth((short)8, 100*50); 
			 sheet.setColumnWidth((short)9, 100*110); 
			 sheet.setColumnWidth((short)10, 100*110); 
			
			
			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			            Map rows = rss.getRows()[i];
			        	HSSFRow row6=sheet.createRow(2+i+1);
			        	row6.setHeight((short)300);
				        cteateTitleCell(wb,row6,(short)0,sdf.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)1,sdfus.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)2,rows.get("refno").toString());
				        cteateTitleCell(wb,row6,(short)3,rows.get("staffcode").toString());
				        cteateTitleCell(wb,row6,(short)4,rows.get("staffname").toString());
				        cteateTitleCell(wb,row6,(short)5,rows.get("userType").toString());
				        cteateTitleCell(wb,row6,(short)6,rows.get("collectionDate").toString());
				        cteateTitleCell(wb,row6,(short)7,rows.get("returnDate").toString());
				        cteateTitleCell(wb,row6,(short)8,rows.get("eventName").toString());
				        zi=zhu.get(rows.get("refno").toString());//获取refno下所有产品
				      if(!Util.objIsNULL(zi)){//使用情况不为空
						        Set sets =zi.keySet();
						        int num=1;
						        for(Object key:sets){//遍历使用详细
						        	 for(int h=0;h<store.getRowCount();h++){//遍历所有产品
						        		 Map rowss = store.getRows()[h];
						        		 if(key.equals(rowss.get("procode").toString())){
						        			 cteateTotalCell(wb,row6,(short)(h+9),"1");
						        		 }else{
						        			 if(num==1)
						        				 cteateReasonCell(wb,row6,(short)(h+9),"");
						        		 }
						        	 }
						        	 num++;
						        }
						        
				        }else{
				        	 for(int h=0;h<store.getRowCount();h++){
				        		 cteateReasonCell(wb,row6,(short)(h+9),"");
				        	 }
				        }
			        }  
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ePayment 导出异常=="+e.getMessage());
		}finally{
			zhu=null;;
			zi=null;
			cd=null;
			
			rss=null;
			store=null;
			stores=null;
		}
	
		
	}
	
	
	
	
	/**
	 * Room Setting
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	void downforepayment(String startDate, String endDate,HSSFSheet sheet) throws Exception{

		C_RoomSettingDao ckd=new C_RoomSettingDaoImpl();
		
		Result rss=null;
		try{
			
			rss=ckd.getepayment(startDate, endDate);
			HSSFRow row_1=sheet.createRow(0);
			cteateTWOTitleCell(wb,row_1,(short)0,"Convoy Financial Services Limited");
			row_1.setHeight((short)400);
			
			
			HSSFRow row_2=sheet.createRow(1);
			cteateTWOTitleCell(wb,row_2,(short)0,"ePayment Request");
			//cteateYellowTitleCell(wb,row_2,(short)6,"Location");
			//cteateYellowTitleCell(wb,row_2,(short)7,"");
			row_2.setHeight((short)400);
			
			HSSFRow row=sheet.createRow(2);
			sheet.createFreezePane(0,3);
			row.setHeight((short)400);	
				
			cteateTHRTitleCell(wb,row,(short)0,"Month");
			cteateTHRTitleCell(wb,row,(short)1,"Request date");
			cteateTHRTitleCell(wb,row,(short)2,"Order Number");
			cteateTHRTitleCell(wb,row,(short)3,"Staff Code");
			cteateTHRTitleCell(wb,row,(short)4,"Staff Name");
			cteateTHRTitleCell(wb,row,(short)5,"Staff/Consultant");
			
			
			cteateYellowTitleCell(wb,row,(short)6,"EPS");
			cteateYellowTitleCell(wb,row,(short)7,"Octopus");
			
			
			//合并单元格 
			sheet.addMergedRegion(new Region(1, (short)6, 1,(short)7));
			
			
			 sheet.setColumnWidth((short)0, 100*20); 
			 sheet.setColumnWidth((short)1, 100*40); 
			 sheet.setColumnWidth((short)2, 100*42); 
			 sheet.setColumnWidth((short)3, 100*40); 
			 sheet.setColumnWidth((short)4, 100*40); 
			 sheet.setColumnWidth((short)5, 100*35); 
			 sheet.setColumnWidth((short)6, 100*50); 
			 sheet.setColumnWidth((short)7, 100*50); 
			
			 

			 //遍历Result
			if(rss!=null && rss.getRowCount()!=0){  
			        for(int i=0;i<rss.getRowCount();i++){  
			        	Map<String,Object> rows = rss.getRows()[i];
			        	HSSFRow row6=sheet.createRow(2+i+1);
			        	row6.setHeight((short)300);
				        cteateTitleCell(wb,row6,(short)0,sdf.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)1,sdfus.format(DateUtils.StrToDate(rows.get("createDate").toString())));
				        cteateTitleCell(wb,row6,(short)2,rows.get("refno").toString());
				        cteateTitleCell(wb,row6,(short)3,rows.get("staffcode").toString());
				        cteateTitleCell(wb,row6,(short)4,rows.get("staffname").toString());
				        cteateTitleCell(wb,row6,(short)5,rows.get("userType").toString());
				        
				        cteateNumCell(wb, row6, (short)6, rows.get("EPS").toString());
				        cteateNumCell(wb, row6, (short)7, rows.get("Octopus").toString());
				       
					
				    
			         
			        }  
			}
			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Room Seeting 导出异常=="+e.getMessage());
		}finally{
			ckd=null;
			
			rss=null;
		}
	
		
	}
	
	
	/**
	 * Advertising
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@SuppressWarnings({ "deprecation", })
	void downforAdvertising(String startDate, String endDate,HSSFSheet sheet) throws Exception{

		C_RoomSettingDao ckd=new C_RoomSettingDaoImpl();
		Result orderrss=null;
		Result productrss=null;
		List<String> productList=new ArrayList<String>();
		try{
			
			orderrss=ckd.getAdvertising(startDate, endDate);
			HSSFRow row_1=sheet.createRow(0);
			cteateTWOTitleCell(wb,row_1,(short)0,"Convoy Financial Services Limited");
			row_1.setHeight((short)400);
			
			
			
			
			/**
			 * 第一行
			 */
			HSSFRow row_2=sheet.createRow(1);
			row_2.setHeight((short)400);
			cteateTWOTitleCell(wb,row_2,(short)0,"Recruitment Advertising Request");
			
			
			/**
			 * 第二行
			 * 
			 */
			HSSFRow row=sheet.createRow(2);
			sheet.createFreezePane(0,3);
			row.setHeight((short)400);	
			cteateTHRTitleCell(wb,row,(short)0,"Month");
			cteateTHRTitleCell(wb,row,(short)1,"Request date");
			cteateTHRTitleCell(wb,row,(short)2,"Order Number");
			cteateTHRTitleCell(wb,row,(short)3,"Staff Code");
			cteateTHRTitleCell(wb,row,(short)4,"Staff Name");
			cteateTHRTitleCell(wb,row,(short)5,"Staff/Consultant");
			
			
			//获取产品列表
			productrss=ckd.getProductList();
			
			String preMediaType=null;
			int titlenum=0;
			int typenum=0;
			if(productrss!=null && productrss.getRowCount()!=0){  
		        for(int i=0;i<productrss.getRowCount();i++){  //遍历产品列表
		        	Map<String,Object> datarows = productrss.getRows()[i];
		        	productList.add(datarows.get("mediacode").toString());
		        	if(!datarows.get("mediatype").toString().equals(preMediaType) ){//如果产品类型为空，那么代表是第一个类型
		        		if(!Util.objIsNULL(preMediaType)){
		        			sheet.addMergedRegion(new Region(1, (short)(6+i-titlenum), 1,(short)(6+i-1)));
		        			titlenum=0;
		        			typenum++;
		        		}
		        		preMediaType=datarows.get("mediatype").toString();//将目前类型赋值至上一个类型
			        		if(typenum%2==0)
			        			cteateSkyBlueCell(wb,row_2,(short)(6+i),preMediaType);//设置
			        		else 
			        			cteateGREENCell(wb,row_2,(short)(6+i),preMediaType);//设置
						
		        	}else{
		        		if(i==productrss.getRowCount()-1){//当是最后一个时
		        			sheet.addMergedRegion(new Region(1, (short)(6+i-titlenum), 1,(short)(6+i)));
		        		}
		        	}
		        	if(typenum%2==0)
		        		cteateYellowTitleCell(wb,row,(short)(6+i),datarows.get("medianame").toString());
		        	else
		        		cteateLEMON_CHIFFONCell(wb,row,(short)(6+i),datarows.get("medianame").toString());
		        	titlenum++;
		        	 sheet.setColumnWidth((short)(i+6), 100*50); 
		        }
		        
			}
			
			 sheet.setColumnWidth((short)0, 100*20); 
			 sheet.setColumnWidth((short)1, 100*40); 
			 sheet.setColumnWidth((short)2, 100*42); 
			 sheet.setColumnWidth((short)3, 100*40); 
			 sheet.setColumnWidth((short)4, 100*40); 
			 sheet.setColumnWidth((short)5, 100*35); 
			 sheet.setColumnWidth((short)6, 100*50); 
			 sheet.setColumnWidth((short)7, 100*50); 
			
			 
			 
			 //遍历订单Result
			 
			 if(orderrss!=null && orderrss.getRowCount()!=0){  
			        for(int i=0;i<orderrss.getRowCount();i++){  
			        	Map<String,Object> rows = orderrss.getRows()[i];
			        	HSSFRow row6=sheet.createRow(2+i+1);
			        	row6.setHeight((short)350);
			        	cteateTitleCell(wb,row6,(short)0,sdf.format(DateUtils.StrToDate(rows.get("createDate").toString())));
			        	cteateTitleCell(wb,row6,(short)1,sdfus.format(DateUtils.StrToDate(rows.get("createDate").toString())));
					    cteateTitleCell(wb,row6,(short)2,rows.get("refno").toString());
					    cteateTitleCell(wb,row6,(short)3,rows.get("staffcode").toString());
					    cteateTitleCell(wb,row6,(short)4,rows.get("staffname").toString());
					    cteateTitleCell(wb,row6,(short)5,rows.get("userType").toString());
			        	for(int j=0;j<productList.size();j++){
			        		if(productList.get(j).equals(rows.get("mediacode").toString())){
			        			cteateTotalCell(wb,row6,(short)(6+j),"1");
			        		}else{
			        			cteateReasonCell(wb,row6,(short)(6+j),"");
			        		}
			        			
			        	}
			        	System.out.println();
			        }
			        
			 }
			 

			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Recruitment Advertising 导出异常=="+e.getMessage());
		}finally{
			ckd=null;
			 productrss=null;
			productList=null;
			orderrss=null;
		}
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 将数据库Result转为Map 形式存储 for Marketing Premium
	 * @param store
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	Map<String,Map<String,String>> zhuanhuan(Result store) throws Exception{
		Map<String,Map<String,String>> map2=new HashMap<String,Map<String,String>>();
		Map<String,String> map3=new HashMap<String,String>();
		Map<String,Object> rows=null;
		String before=null;
		try{
		if(store!=null && store.getRowCount()!=0){ 
	        for(int i=0;i<store.getRowCount();i++){  
	             rows = store.getRows()[i];
	            	if((!Util.objIsNULL(before)&&!before.equals(rows.get("ordercode").toString()))||i==store.getRowCount()-1){//两个相等
	    				map2.put(before, map3);
	    				 if(i==store.getRowCount()-1){
	            			 before=rows.get("ordercode").toString();
	 	    				map3.put(rows.get("procode").toString(),rows.get("quantity").toString());
	 	    				map2.put(before, map3);
	            		 }
	    				 map3=new HashMap();
	    			}
	    				before=rows.get("ordercode").toString();
	    				map3.put(rows.get("procode").toString(),rows.get("quantity").toString());
	        }
		}else{
			map2=null;
		}
		
		}catch (Exception e) {
			throw new Exception("转换Markering Premium/Stationery 数据时出现异常=="+e.getMessage());
		}finally{
			rows=null;
			map3=null;
			before=null;
		}
	/*	Set set = map2.keySet();

		for (Object key : set) {
			System.out.println("键:"+key);
			Map map4=(Map) map2.get(key);
			Set set2=map4.keySet();
			for(Object keys:set2){
				System.out.println("键:"+keys+"  值:"+map4.get(keys));
			}
			System.out.println("----------------");
		}      	  
	           */ 	  
		return map2;
	}
	
	/**
	 * 将数据库Result转为Map 形式存储 for Company Asset
	 * @param store
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	Map<String,Map<String,String>> zhuanhuan_ref(Result store) throws Exception{
		Map<String,Map<String,String>> map2=new HashMap<String,Map<String,String>>();
		Map<String,String> map3=new HashMap<String,String>();
		String before=null;
		Map<String,Object> rows=null;
		try{
			if(store!=null && store.getRowCount()!=0){ 
		        for(int i=0;i<store.getRowCount();i++){  
		            rows = store.getRows()[i];
		        	//before 不为null,两个不相等，或者为最后一个
		            	if((!Util.objIsNULL(before)&&!before.equals(rows.get("refno").toString()))||i==store.getRowCount()-1){//两个相等
		            		map2.put(before, map3);
		    				
		    				 if(i==store.getRowCount()-1){
		    					 if(!before.equals(rows.get("refno").toString())){
		    						 map3=new HashMap<String, String>();
		    					 }
		            			 before=rows.get("refno").toString();
		 	    				map3.put(rows.get("itemcode").toString(),rows.get("num").toString());
		 	    				map2.put(before, map3);
		            		 }
		    				 map3=new HashMap<String, String>();
		    			}
		    				before=rows.get("refno").toString();
		    				map3.put(rows.get("itemcode").toString(),rows.get("num").toString());
		        }
			}else{
				map2=null;
			}
		}catch (Exception e) {
			throw new Exception("转换CompanyAsset 数据时出现异常=="+e.getMessage());
		}finally{
			rows=null;
			map3=null;
			before=null;
		}
		return map2;
		
		
	}
	
	
	
	
	
	
	
	
	
	

}
