package util;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

public class UtilCommon {

	public static String dateToString(Date time){ 
		SimpleDateFormat formatter; 
		formatter = new SimpleDateFormat ("yyyy-MM-dd"); 
		String ctime = formatter.format(time);
		return ctime; 
	} 
	/**
	 * 设置单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cell.setCellStyle(cellstyle); 
		if(col==15)
			cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		/*

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("新細明體");

		cellstyle.setFont(font);
		 */

	}
	/**
	 * 设置单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateMedicalCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
	if(col==4){
		DecimalFormat df = new DecimalFormat("#.00");

			cell.setCellValue(df.format(new Double(val)));
		}else{
			cell.setCellValue(new HSSFRichTextString(val));
		}
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cell.setCellStyle(cellstyle); 
	
		if(col==16)
			cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		/*

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("新細明體");

		cellstyle.setFont(font);
		 */
		
	}
	/**
	 * 设置单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateAllMedicalCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		if(col>7 && col<16 && col!=11)
			cell.setCellValue(new Double(val));
		else
			cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cell.setCellStyle(cellstyle); 
		if(col==18)
			cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		/*

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("新細明體");

		cellstyle.setFont(font);
		 */

	}
	/**
	 * 设置单元格的值及其样式  导出China 最后带函数
	 */
	@SuppressWarnings("deprecation")
	public static void cteateChinaSimplCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFDataFormat df =wb.createDataFormat();
		HSSFCell cell = row.createCell(col);
		if(col==9)
			cell.setCellFormula(val);
		else
			cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		if (col==1 || col==2)
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		else {
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		}
		cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cell.setCellStyle(cellstyle); 
		
		cellstyle.setDataFormat(df.getFormat("#0.00"));
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)9); //字体大小
		font.setFontName("宋体");
		cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
		cellstyle.setFont(font);
	}
	/**
	 * 设置单元格的值及其样式  导出China 无函数
	 */
	@SuppressWarnings("deprecation")
	public static void cteateChinaCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFDataFormat df=  wb.createDataFormat();
		HSSFCell cell = row.createCell(col);
		
		if(col==5)
			cell.setCellValue(new Double(val));
		else if(col==7)
			cell.setCellFormula(val);
		else if(col==9)
			cell.setCellFormula(val);
		else
			cell.setCellValue(new HSSFRichTextString(val));
		
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cell.setCellStyle(cellstyle); 
		if(col!=5)
			cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		if(col!=4)
			cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		cellstyle.setDataFormat(df.getFormat("#0.00"));
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)9); //字体大小
		font.setFontName("宋体");
		
		cellstyle.setFont(font);
	}
	/**
	 * 设置单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateRejectMedicalCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
	 
			cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cell.setCellStyle(cellstyle); 
		if(col==18)
			cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		/*

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("新細明體");

		cellstyle.setFont(font);
		 */
		
	}
	/**
	 * 设置Medical for fad单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateCellFAD(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		if(col==3)
			cell.setCellValue(new Double(val));
		else
			cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cell.setCellStyle(cellstyle); 

		/*

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("新細明體");

		cellstyle.setFont(font);
		 */

	}
	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{

		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("仿宋");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体

		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);

	}

	
}
