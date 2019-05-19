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
import org.apache.poi.ss.usermodel.Font;

public class ExpMissingPayment {

	@SuppressWarnings("unused")
	private HSSFWorkbook wb = null;
	public ExpMissingPayment(){
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
		try{
			int i =0;
			ii =res.getMetaData().getColumnCount();
			while(res.next()){
				i++;
				HSSFRow row2 =sheet.createRow((short)i);
				   for(int j=0;j<ii;j++)
	                {
	                    String ss="";
	                    if(res.getString(j+1)==null)
	                        ss="";
	                    else
	                        ss=res.getString(j+1);
	                    cteateCell(wb,row2,(short)j,ss);
	                }
			}
			 sheet.setColumnWidth((short)0,(short)(150*25)); // 调整第一列宽度 
			 sheet.setColumnWidth((short)1,(short)(220*25)); // 调整第二列宽度 
			 sheet.setColumnWidth((short)2,(short)(180*25)); // 调整第三列宽度 
			 sheet.setColumnWidth((short)3,(short)(200*25)); // 调整第四列宽度 
			 sheet.setColumnWidth((short)4,(short)(250*25)); // 调整第五列宽度 
			 sheet.setColumnWidth((short)5,(short)(220*25));// 调整第六列宽度 
			 sheet.setColumnWidth((short)6,(short)(160*25)); // 调整第七列宽度 
			 sheet.setColumnWidth((short)7,(short)(400*25)); // 调整第八列宽度 
	         
	         /***************************为Excel设置密码***********************************/
	         //sheet.protectSheet("admin");
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
		//cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10); //字体大小
		font.setFontName("Calibri");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}
	@SuppressWarnings("deprecation")
	public static void cteateCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cell.setCellStyle(cellstyle); 
		if(col==15)
			cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10); //字体大小
		font.setFontName("Calibri");

		cellstyle.setFont(font);

	}
}
