package dao.exp;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import util.UtilCommon;

public class ExpConvoyChinaForm {

	@SuppressWarnings("unused")
	private HSSFWorkbook wb = null;
	public ExpConvoyChinaForm(){
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
			int i =2;
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
	                   
	                    //	ss=DateUtils.strChToUs(res.getString(j+1));
	               	 if(j+1==15){
						 if(res.getString(6).equals("Dental"))
							 ss=res.getString(9);
						 else
							 ss="0";
					 }
	                 
	                    UtilCommon.cteateAllMedicalCell(wb,row2,(short)j,ss);
	                }
				
				   
			}
		     HSSFRow row3 =sheet.createRow((short)i+5);
		     cteateTWOTitleCell(wb,row3,(short)2,"Remarks : ");
		     HSSFRow row4 =sheet.createRow((short)i+6);
		     cteateTWOTitleCell(wb,row4,(short)2,"Plan 1");
		     HSSFRow row5 =sheet.createRow((short)i+7);
		     cteateTWOTitleCell(wb,row5,(short)2,"D - AD / Director Grade");
		     HSSFRow row6 =sheet.createRow((short)i+8);
		     cteateTWOTitleCell(wb,row6,(short)2,"Max No of Ordinary Treatment is 15");
		     HSSFRow row7 =sheet.createRow((short)i+9);
		     cteateTWOTitleCell(wb,row7,(short)2,"Max No of Special Treatment is 10");
		     HSSFRow row8 =sheet.createRow((short)i+10);
		     cteateTWOTitleCell(wb,row8,(short)2,"Should you have any enquiries, please send email to SZOAdm@convoy.com.hk");
		   
		 	sheet.autoSizeColumn(( short ) 0); 		 	 // 调整第一列宽度 
			sheet.setColumnWidth(( short ) 1 ,(short)(250*20)); 			 // 调整第二列宽度 
			sheet.setColumnWidth(( short ) 2 ,(short)(200*20)); 			 // 调整第二列宽度 
			sheet.setColumnWidth(( short ) 3 ,(short)(150*25)); 			 // 调整第 列宽度 
			sheet.setColumnWidth(( short ) 4 ,(short)(100*25)); 			 // 调整第 列宽度 
			sheet.autoSizeColumn(( short ) 5 ); 			 // 调整第 列宽度 
	
			sheet.autoSizeColumn(( short )6 ); 
			sheet.setColumnWidth(( short )7 ,(short)(150*25)); 	
			sheet.setColumnWidth(( short )8 ,(short)(150*25)); 	
			sheet.setColumnWidth(( short )9 ,(short)(150*25)); 	
			sheet.setColumnWidth(( short )10 ,(short)(100*25)); 	
			sheet.setColumnWidth(( short )11 ,(short)(150*25)); 	
			sheet.autoSizeColumn(( short ) 12 ); 
			sheet.autoSizeColumn(( short ) 13 ); 
			sheet.autoSizeColumn(( short ) 14 ); 
			sheet.autoSizeColumn(( short ) 15 ); 
			sheet.autoSizeColumn(( short ) 15 ); 
	 
	         
	         /***************************为Excel设置密码***********************************/
	         
		}catch(SQLException e){
			e.printStackTrace();
		}
		wb.write(os);
		os.flush();
		os.close();
		
	}
	/**
	 * 导出Excel表格 Meidcal
	 * @param res
	 * @param os
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void createFixationMedicalSheet(ResultSet res,ResultSet res2,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet)throws  IOException{
		int ii =0;
		try{
			int i =2;
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
					
					//	ss=DateUtils.strChToUs(res.getString(j+1));
					if(j+1==15){
						if(res.getString("type").equals("Dental"))
							ss=res.getString("amount");
						else
							ss="0";
					}
					
					UtilCommon.cteateAllMedicalCell(wb,row2,(short)j,ss);
				}
				
				
			}
			ii=res2.getMetaData().getColumnCount();
			while(res2.next()){
				i++;
				HSSFRow row2 =sheet.createRow((short)i);
				for(int j=0;j<ii;j++)
				{
					
					String ss="";
					if(res2.getString(j+1)==null){
						ss="";
					}else{
						ss=res2.getString(j+1);
					}
					
					//	ss=DateUtils.strChToUs(res.getString(j+1));
					
					UtilCommon.cteateRejectMedicalCell(wb,row2,(short)j,ss);
				}
				
				
			}
			
			
			
			
			

			HSSFRow row4 =sheet.createRow((short)i+5);
			cteateBackBlueCell(wb,row4,(short)1,"Remarks : ");
			HSSFRow row5 =sheet.createRow((short)i+6);
			cteateBackBlueCell(wb,row5,(short)1,"Plan 1");
			cteateBackBlueCell(wb,row5,(short)2,"Group 1 Medical ");
			HSSFRow row6 =sheet.createRow((short)i+7);
			cteateBackBlueCell(wb,row6,(short)1,"Plan 1A");
			cteateBackBlueCell(wb,row6,(short)2,"Group 1A Medical ");
			HSSFRow row7 =sheet.createRow((short)i+8);
			cteateBackBlueCell(wb,row7,(short)1,"Plan 1D");
			cteateBackBlueCell(wb,row7,(short)2,"Group 1 Medical + Plan 1 Dental");
			HSSFRow row8 =sheet.createRow((short)i+9);
			cteateBackBlueCell(wb,row8,(short)1,"Plan 2");
			cteateBackBlueCell(wb,row8,(short)2,"Goup 2 Medical + Plan 2 Dental");
			HSSFRow row9 =sheet.createRow((short)i+11);
			cteateTWOTitleCell(wb,row9,(short)1,"Should you have any enquiries, please send email to Jackie Li/Lydia Yu.");
			

			
			
			sheet.autoSizeColumn(( short ) 0); 		 	 // 调整第一列宽度 
			sheet.setColumnWidth(( short ) 1 ,(short)(250*20)); 			 // 调整第二列宽度 
			sheet.setColumnWidth(( short ) 2 ,(short)(200*20)); 			 // 调整第二列宽度 
			sheet.setColumnWidth(( short ) 3 ,(short)(150*25)); 			 // 调整第 列宽度 
			sheet.setColumnWidth(( short ) 4 ,(short)(100*25)); 			 // 调整第 列宽度 
			sheet.autoSizeColumn(( short ) 5 ); 			 // 调整第 列宽度 
			
			sheet.autoSizeColumn(( short )6 ); 
			sheet.setColumnWidth(( short )7 ,(short)(150*25)); 	
			sheet.setColumnWidth(( short )8 ,(short)(150*25)); 	
			sheet.setColumnWidth(( short )9 ,(short)(150*25)); 	
			sheet.setColumnWidth(( short )10 ,(short)(100*25)); 	
			sheet.setColumnWidth(( short )11 ,(short)(150*25)); 	
			sheet.autoSizeColumn(( short ) 12 ); 
			sheet.autoSizeColumn(( short ) 13 ); 
			sheet.autoSizeColumn(( short ) 14 ); 
			sheet.autoSizeColumn(( short ) 15 ); 
			sheet.autoSizeColumn(( short ) 16 ); 
			sheet.setColumnWidth(( short ) 17 ,(short)(100*50)); 
			
			
			/***************************为Excel设置密码***********************************/
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		wb.write(os);
		os.flush();
		os.close();
		
	}
	/**
	 * 根據staffcode导出Excel表格
	 * @param res
	 * @param os
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void createFixationSheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet,String staffcode)throws  IOException{
		int ii =0;
		try{
			int i =2;
			ii =res.getMetaData().getColumnCount();
			Double money=0d;
			while(res.next()){
				if(res.getString("staffcode").equals(staffcode)){
					i++;
					HSSFRow row2 =sheet.createRow((short)i);
					row2.setHeight((short) (20*20));
					for(int j=0;j<ii;j++)
					{
						String ss="";
						if(res.getString(j+1)==null){
							ss="";
						}else{
							ss=res.getString(j+1);
						}
						 
						   if(j+1==10){
							   money=money+(Double.parseDouble(res.getString(j+1)));
						   }
						
						 if(j+1==15){
							 if(res.getString("type").equals("Dental"))
								 ss=res.getString("amount");
							 else
								 ss="0";
						 }
						UtilCommon.cteateCell(wb,row2,(short)j,ss);
						  
					}
				}
				
			}
		
			 
			HSSFRow row3 =sheet.createRow((short)i+2);
			cteateBackBlueCell(wb,row3,(short)9,money+"");
			HSSFRow row4 =sheet.createRow((short)i+5);
			cteateBackBlueCell(wb,row4,(short)1,"Remarks : ");
			HSSFRow row5 =sheet.createRow((short)i+6);
			cteateBackBlueCell(wb,row5,(short)1,"Plan 1");
			cteateBackBlueCell(wb,row5,(short)2,"Group 1 Medical ");
			HSSFRow row6 =sheet.createRow((short)i+7);
			cteateBackBlueCell(wb,row6,(short)1,"Plan 1A");
			cteateBackBlueCell(wb,row6,(short)2,"Group 1A Medical ");
			HSSFRow row7 =sheet.createRow((short)i+8);
			cteateBackBlueCell(wb,row7,(short)1,"Plan 1D");
			cteateBackBlueCell(wb,row7,(short)2,"Group 1 Medical + Plan 1 Dental");
			HSSFRow row8 =sheet.createRow((short)i+9);
			cteateBackBlueCell(wb,row8,(short)1,"Plan 2");
			cteateBackBlueCell(wb,row8,(short)2,"Goup 2 Medical + Plan 2 Dental");
			HSSFRow row9 =sheet.createRow((short)i+11);
			cteateTWOTitleCell(wb,row9,(short)1,"Should you have any enquiries, please send email to Jackie Li/Lydia Yu.");
			
			sheet.autoSizeColumn(( short ) 0); 		 	 // 调整第一列宽度 
			sheet.setColumnWidth(( short ) 1 ,(short)(250*20)); 			 // 调整第二列宽度 
			sheet.setColumnWidth(( short ) 2 ,(short)(120*25)); 			 // 调整第 列宽度 
			sheet.setColumnWidth(( short ) 3 ,(short)(140*25)); 			 // 调整第 列宽度 
			sheet.autoSizeColumn(( short ) 4 ); 			 // 调整第 列宽度 
	
			sheet.autoSizeColumn(( short ) 5 ); 
			sheet.setColumnWidth(( short )6 ,(short)(80*25)); 	
			sheet.setColumnWidth(( short )7 ,(short)(150*25)); 	
			sheet.setColumnWidth(( short )8 ,(short)(150*25)); 	
			sheet.setColumnWidth(( short )9 ,(short)(100*25)); 	
			sheet.setColumnWidth(( short )10 ,(short)(150*25)); 	
			sheet.autoSizeColumn(( short ) 11 ); 
			sheet.autoSizeColumn(( short ) 12 ); 
			sheet.autoSizeColumn(( short ) 13 ); 
			sheet.autoSizeColumn(( short ) 14 ); 
			
			/***************************为Excel设置密码***********************************/
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		wb.write(os);
		os.flush();
		os.close();
		
	}
	/**
	 * 根據Company导出Excel表格
	 * @param res
	 * @param os
	 * @throws IOException
	 */
	public void createFixCompanySheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet,String company)throws  IOException{
		int ii =0;
		try{
			int i =0;
			ii =res.getMetaData().getColumnCount();
			Double money=0d;
			while(res.next()){
				if(res.getString("company").equals(company)){
					i++;
					HSSFRow row2 =sheet.createRow((short)i);
					row2.setHeight((short) (20*15));
					for(int j=0;j<ii;j++)
					{
						String ss="";
						if(res.getString(j+1)==null){
							ss="";
						}else{
							ss=res.getString(j+1);
						}
						
						if(j+1==4){
							money=money+(Double.parseDouble(res.getString(j+1)));
						}
					 
						UtilCommon.cteateCellFAD(wb,row2,(short)j,ss);
						
					}
				}
				
			}
			
			
			HSSFRow row3 =sheet.createRow((short)i+1);
			cteateBlueCell(wb,row3,(short)0,"");
			cteateBlueCell(wb,row3,(short)1,"");
			cteateBlueCell(wb,row3,(short)2,"total");
			cteateBlueCell(wb,row3,(short)3,money+"");
		
			sheet.autoSizeColumn(( short ) 0); 		 	 // 调整第一列宽度 
			sheet.autoSizeColumn(( short ) 1 ); 			 // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 2 ); 			 // 调整第 列宽度 
			sheet.autoSizeColumn(( short ) 3 ); 			 // 调整第 列宽度 
			sheet.autoSizeColumn(( short ) 4 ); 			 // 调整第 列宽度 
		 
			
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
	 * 设置背景蓝色样式样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateBackBlueCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		//cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	
		Font font = wb.createFont();
			font.setFontHeightInPoints((short)10); //字体大小
		font.setFontName("Arial");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);
		//cellstyle.setFillForegroundColor(HSSFColor.TURQUOISE.index);
		//cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//	cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		if(col==(short)9 || col==(short)11){
		
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		}
		
		//黄色
		
		cell.setCellStyle(cellstyle);
		
	}
	/**
	 * 设置总数样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateTotalCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		Font font = wb.createFont();
	//	font.setFontHeightInPoints((short)18); //字体大小
		font.setFontName("Arial");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);
		cellstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	//	cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		
		//黄色
		
		cell.setCellStyle(cellstyle);
		
	}
	/**
	 * 设置藍色背景样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateBlueCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		if(col==3 && row.getRowNum()>0)
		cell.setCellValue(new Double(val));
		else
			cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		Font font = wb.createFont();
		//	font.setFontHeightInPoints((short)18); //字体大小
		font.setFontName("Arial");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
	font.setColor(HSSFColor.YELLOW.index);
		cellstyle.setFont(font);
		cellstyle.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//	cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		
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
	public static void cteateTitleCenterThinCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)18); //字体大小
		font.setFontName("仿宋");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);
		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		if(col==15)
			cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
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
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)14); //字体大小
		font.setFontName("华文新魏");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);

		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
	    cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	    cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	    cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
	    
		cellstyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		

		//黄色
		
		cell.setCellStyle(cellstyle);
		
	}
	@SuppressWarnings("deprecation")
	public static void cteateTitleCenter2Cell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("黑体");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);
		
		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		
		cellstyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
		//黄色
		
		cell.setCellStyle(cellstyle);
		
	}
	@SuppressWarnings("deprecation")
	public static void cteateTitleTwoCenterCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)9); //字体大小
		font.setFontName("宋体");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);
		
		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
	    cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	    cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	    cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
	    
		cellstyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
		//黄色
		
		cell.setCellStyle(cellstyle);
		
	}
	@SuppressWarnings("deprecation")
	public static void cteateValsCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)9); //字体大小
		font.setFontName("宋体");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);
		
		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		cellstyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
		//黄色
		
		cell.setCellStyle(cellstyle);
		
	}
	@SuppressWarnings("deprecation")
	public static void cteateConstantCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)9); //字体大小
		font.setFontName("宋体");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);
		 
		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
	    cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	    cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	    cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		cellstyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
		//黄色
		
		cell.setCellStyle(cellstyle);
		
	}
	public static void cteateValuesCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFDataFormat df = wb.createDataFormat();
		
		HSSFCell cell = row.createCell(col);
		cell.setCellFormula(val); 
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)9); //字体大小
		font.setFontName("宋体");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);
		cellstyle.setDataFormat(df.getFormat("#0.00"));
		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		cellstyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
		//黄色
		
		cell.setCellStyle(cellstyle);
		
	}
	public static void cteateValuesCellyel(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFDataFormat df = wb.createDataFormat();
		
		HSSFCell cell = row.createCell(col);
		cell.setCellFormula(val); 
		HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)9); //字体大小
		font.setFontName("宋体");
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//font.setColor(HSSFFont.COLOR_RED);
		cellstyle.setFont(font);
		cellstyle.setDataFormat(df.getFormat("#0.00"));
		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		cellstyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
		//黄色
		cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色
		cell.setCellStyle(cellstyle);
		
	}
	/**
	 * 设置remark样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateTWORemarkCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		//cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("Arial");
		
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
	public static void cteateTWOTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		//cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)10); //字体大小
		font.setFontName("Arial");
	
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
		cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);// 
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)9); //字体大小
		font.setFontName("宋体");
		cellstyle.setWrapText(true);//自动换行
		cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		if(col==9)
			cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		 
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		//cellstyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
		cellstyle.setFillForegroundColor(HSSFColor.YELLOW.index);//黄色

		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);
		
	}

}
