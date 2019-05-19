package dao.exp;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

import util.UtilCommon;

public class ExpMedicalClaim {

	@SuppressWarnings("unused")
	private HSSFWorkbook wb = null;
	public ExpMedicalClaim(){
		wb = new HSSFWorkbook();
	}
	/**
	 * 导出Excel表格
	 * @param res
	 * @param os
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void createFixationSheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet)throws  IOException{
		int ii =0;
		float total=0;
		try{
			int i =4;
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
	                    if(j==2){
	                    	ss="";
	                    }
	                    if(j==4){
	                    	
		                    total = Float.parseFloat(res.getString(5))+total;
		                    
	                    }
	                    UtilCommon.cteateMedicalCell(wb,row2,(short)j,ss);
	                }
				
			}
			DecimalFormat df = new DecimalFormat("#.00");
		     HSSFRow row3 =sheet.createRow((short)i+5);
		     cteateTWOTitleCell(wb,row3,(short)(ii-2),"Consultant:");
		     cteateTWOTitleCell(wb,row3,(short)(ii-1),""+df.format(total));
		   
			 sheet.setColumnWidth((short)0,(short)(261*25)); 
			 //sheet.autoSizeColumn(( short ) 0,true); 		 // 调整第一列宽度 
	         sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
	         sheet.autoSizeColumn(( short ) 2 ); 			 // 调整第三列宽度 
	         sheet.setColumnWidth((short)3,(short)(240*25)); // 调整第四列宽度 
	         sheet.setColumnWidth((short)4,(short)(261*25)); // 调整第五列宽度 
	         
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
	public static void cteateTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)18); //字体大小
		font.setFontName("仿宋");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);
		cellstyle.setFillBackgroundColor(HSSFColor.BLACK.index);
		//黄色
		
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
	public static void cteateTWOTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)14); //字体大小
		font.setFontName("仿宋");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}

}
