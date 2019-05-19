package dao.exp;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class ExpMedicalForCons {

	@SuppressWarnings("unused")
	private HSSFWorkbook wb = null;
	public ExpMedicalForCons(){
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
			int i =4;
			res.beforeFirst();
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
	                    UtilCommon.cteateCell(wb,row2,(short)j,ss);
	                }
				
				   
			}
		     HSSFRow row3 =sheet.createRow((short)i+5);
		     cteateTWOTitleCell(wb,row3,(short)2,"Remarks : ");
		     HSSFRow row4 =sheet.createRow((short)i+6);
		     cteateTWOTitleCell(wb,row4,(short)2,"S - Special Treatment");
		     HSSFRow row5 =sheet.createRow((short)i+7);
		     cteateTWOTitleCell(wb,row5,(short)2,"D - AD / Director Grade");
		     HSSFRow row6 =sheet.createRow((short)i+8);
		     cteateTWOTitleCell(wb,row6,(short)2,"Max No of Ordinary Treatment is 15");
		     HSSFRow row7 =sheet.createRow((short)i+9);
		     cteateTWOTitleCell(wb,row7,(short)2,"Max No of Special Treatment is 10");
		     HSSFRow row8 =sheet.createRow((short)i+10);
		     cteateTWOTitleCell(wb,row8,(short)2,"Should you have any enquiries, please send email to SZOAdm@convoy.com.hk");
		   
			 sheet.setColumnWidth(( short)0,(short)(100*35)); 		 	 // 调整第一列宽度 
	         sheet.setColumnWidth(( short)1,(short)(100*75)); 			 // 调整第二列宽度 
	         sheet.setColumnWidth(( short)2,(short)(100*25));// 调整第三列宽度 
	         sheet.setColumnWidth(( short)3,(short)(100*25)); // 调整第四列宽度 
	         sheet.setColumnWidth(( short)4,(short)(261*25)); // 调整第五列宽度 
             sheet.setColumnWidth(( short)5,(short)(261*25)); // 调整第五列宽度
             sheet.setColumnWidth(( short)6,(short)(261*25)); // 调整第五列宽度 
             sheet.setColumnWidth(( short)7,(short)(261*25)); // 调整第五列宽度 
             sheet.setColumnWidth(( short)8,(short)(261*25)); // 调整第五列宽度 
	         sheet.setColumnWidth(( short)9,(short)(120*25));
	         sheet.setColumnWidth(( short)10,(short)(120*25));
	         
	         /***************************为Excel设置密码***********************************/
	         
		}catch(SQLException e){
			e.printStackTrace();
		}
		wb.write(os);
		os.flush();
		os.close();
		
	}
	/**
	 * 导出Excel表格
	 * @param res
	 * @param os
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void createFixationSheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet,String staffcode)throws  IOException{
		int ii =0;
		try{
			int i =4;
			ii =res.getMetaData().getColumnCount();
			
			while(res.next()){
				if(res.getString("staffcode").equals(staffcode)){
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
					
					UtilCommon.cteateCell(wb,row2,(short)j,ss);
				}
				}
				
			}
			HSSFRow row3 =sheet.createRow((short)i+5);
			cteateTWOTitleCell(wb,row3,(short)2,"Remarks : ");
			HSSFRow row4 =sheet.createRow((short)i+6);
			cteateTWOTitleCell(wb,row4,(short)2,"S - Special Treatment");
			HSSFRow row5 =sheet.createRow((short)i+7);
			cteateTWOTitleCell(wb,row5,(short)2,"D - AD / Director Grade");
			HSSFRow row6 =sheet.createRow((short)i+8);
			cteateTWOTitleCell(wb,row6,(short)2,"Max No of Ordinary Treatment is 15");
			HSSFRow row7 =sheet.createRow((short)i+9);
			cteateTWOTitleCell(wb,row7,(short)2,"Max No of Special Treatment is 10");
			HSSFRow row8 =sheet.createRow((short)i+10);
			cteateTWOTitleCell(wb,row8,(short)2,"Should you have any enquiries, please send email to SZOAdm@convoy.com.hk");
			
			sheet.autoSizeColumn(( short ) 0); 		 	 // 调整第一列宽度 
			sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
			sheet.setColumnWidth(( short)2,(short)(100*25));// 调整第三列宽度 
			sheet.setColumnWidth(( short)3,(short)(100*25)); // 调整第四列宽度 
			sheet.setColumnWidth(( short)4,(short)(261*25)); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 5 ); 
			sheet.autoSizeColumn(( short ) 6 ); 
			sheet.autoSizeColumn(( short ) 7 ); 
			sheet.autoSizeColumn(( short ) 8 ); 
			sheet.setColumnWidth(( short)9,(short)(120*25));
			sheet.setColumnWidth(( short)10,(short)(120*25));
			
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
		//cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
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
	public static void cteateTitleCenterCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
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
		//cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("仿宋");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//cellstyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
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
	public static void cteateTWOTitleCenterCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10); //字体大小
		font.setFontName("仿宋");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//cellstyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
		cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}

}
