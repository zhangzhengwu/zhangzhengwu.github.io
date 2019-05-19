package dao.exp;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

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
/**
 * 针对Reporting
 * @author kingxu
 *
 */
public class ExpReporting {


	@SuppressWarnings("unused")
	private HSSFWorkbook wb = null;
	public ExpReporting(){
		wb = new HSSFWorkbook();
	}
	/**
	 * 导出Excel表格
	 * @param res
	 * @param os
	 * @throws IOException
	 */
	public void createFixationSheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet)throws  IOException{
		int ii =0;
		try{
			int i =0;
			ii =res.getMetaData().getColumnCount();
			 
			while(res.next()){
				i++;
				HSSFRow row2 =sheet.createRow((short)i);
				   for(int j=0;j<ii;j++)
	                {
	                    String ss="";
	                    if(res.getString(j+1)==null){
	                        ss="";
	                    }else{
	                        ss=res.getString(j+1);
	                    }
	                    if(j==0){
	                    	ss=i+"";
	                    }
	                    cteateTitleCell(wb,row2,(short)j,ss);
	                }
				
			}
			 sheet.autoSizeColumn((short)0); 
			 //sheet.autoSizeColumn(( short ) 0,true); 		 // 调整第一列宽度 
	         sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet.autoSizeColumn(( short ) 2 ); 			 // 调整第三列宽度 
	         sheet.autoSizeColumn((short)3); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)4); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)5); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)6); // 调整第五列宽度 
	         sheet.autoSizeColumn((short)7); // 调整第四列宽度
	         sheet.autoSizeColumn((short)8); // 调整第四列宽度 
	         sheet.autoSizeColumn((short)9); // 调整第四列宽度 
	         
	         /***************************为Excel设置密码***********************************/
	         
		}catch(SQLException e){
			e.printStackTrace();
		}
		wb.write(os);
		os.flush();
		os.close();
		
	}
	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public void cteateTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		//font.setFontHeightInPoints((short)16); //字体大小
	//	font.setFontName("仿宋");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setFont(font);
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
		cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		cell.setCellStyle(cellstyle);
		
	}
	
	
	public void cteateNumCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(Double.parseDouble(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		//font.setFontHeightInPoints((short)16); //字体大小
	//	font.setFontName("仿宋");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setFont(font);
		cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
	 
		
		cell.setCellStyle(cellstyle);
		
	}
	
	
	/**
	 * Access 汇总
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	public void cteateTotalCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(Double.parseDouble(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		//font.setFontHeightInPoints((short)16); //字体大小
	//	font.setFontName("仿宋");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setFont(font);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
		cellstyle.setBorderBottom((short)1);
		cellstyle.setBorderTop((short)1);
		cellstyle.setBorderLeft((short)1);
		cellstyle.setBorderRight((short)1);
		cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		cell.setCellStyle(cellstyle);
		
	}
	/**
	 * Access Reason
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	public void cteateReasonCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		//font.setFontHeightInPoints((short)16); //字体大小
	//	font.setFontName("仿宋");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setFont(font);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
		cellstyle.setBorderBottom((short)1);
		cellstyle.setBorderTop((short)1);
		cellstyle.setBorderLeft((short)1);
		cellstyle.setBorderRight((short)1);
		cellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//灰色
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	 
		
		cell.setCellStyle(cellstyle);
		
	}
	
	
	 
	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public void cteateTWOTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("Times New Roman");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
	
		cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}
	
	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public void cteateTHRTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		//font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("Times New Roman");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
		cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}
	
	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public void cteateYellowTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10); //字体大小
		font.setFontName("仿宋");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
		cellstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());//黄色
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellstyle.setBorderBottom((short)1);
		cellstyle.setBorderTop((short)1);
		cellstyle.setBorderLeft((short)1);
		cellstyle.setBorderRight((short)1);
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}
	
	public void cteateSEA_GREENTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10); //字体大小
		font.setFontName("仿宋");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
		cellstyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());//黄色
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellstyle.setBorderBottom((short)1);
		cellstyle.setBorderTop((short)1);
		cellstyle.setBorderLeft((short)1);
		cellstyle.setBorderRight((short)1);
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}
	
	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public void cteateOrangeTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10); //字体大小
		font.setFontName("仿宋");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
		cellstyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());//黄色
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellstyle.setBorderBottom((short)1);
		cellstyle.setBorderTop((short)1);
		cellstyle.setBorderLeft((short)1);
		cellstyle.setBorderRight((short)1);
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}
	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public void cteateOrangeNumTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(Double.parseDouble(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10); //字体大小
		font.setFontName("仿宋");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//内容垂直居中
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//内容水平居中
		cellstyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());//黄色
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellstyle.setBorderBottom((short)1);
		cellstyle.setBorderTop((short)1);
		cellstyle.setBorderLeft((short)1);
		cellstyle.setBorderRight((short)1);
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}


}
