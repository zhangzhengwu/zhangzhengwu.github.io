package dao.exp;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

import util.Constant;
import util.DateUtils;
import util.Util;

public class ExpConsultantReport {
		@SuppressWarnings("unused")
		private HSSFWorkbook wb = null;
		public ExpConsultantReport(){
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
			int ii =1;
			try{
				int i =1;
				ii =res.getMetaData().getColumnCount();
	 
				
				while(res.next()){
					//i++; 
					i=i+2;
					HSSFRow row2 =sheet.createRow((short)i);
					   for(int j=0;j<ii-1;j++)
		                {
		                    String ss="";
		                    String sa ="";
		                    if(res.getString(j+1)==null){
		                        ss="";
		                    }else{
 		                        ss=res.getString(j+1);
			                    if(j>=37 && j<=39){
			                    	 ss="";
			                    }
			                   
		                    }
		                    if(j>=41 && j<52 ){
		                    	sa = Util.replaceBlank(res.getString(j+1));
		                    	cteateCell3(wb,row2,(short)j,sa);
		                    	
		                    }else if(j==5){
		                    	cteateCell3(wb,row2,(short)j,"1");
		                    	
		                    }else if(j >= 52 ){
		                    	cteateCell2(wb,row2,(short)j,ss);
		                    	
		                    }else {
		                    	cteateCell(wb,row2,(short)j,ss);
							}
		                   
		                }
						HSSFRow row3 =sheet.createRow((short)(i+1));
						int total=0;
						   for(int j=0;j<ii-1;j++)
			                {
			                    String ss="";
			                    if(res.getString(j+1)==null){//当rs的第（j+1）列等于空时
			                        ss="";
			                    }
			                    else{
			                    	if(j==5){
			                    		ss="2";
			                    	}else{
			                    		 ss=res.getString(j+1);
			                    		 if(j>=6 && j<=36){
				                    		if(ss.equals(Constant.NO_SHOW)){//没有打卡
				                    			 ss=Constant.ten_money;
				                    			 total=total+10;
				                    		}else if(!Util.objIsNULL(ss)&&(DateUtils.compTime(Util.replaceBlank(ss), Constant.ON_TIME))){//迟到时
				                    			if(!Util.objIsNULL(res.getString(ii))&&res.getString(ii).toUpperCase().equals(Constant.GRADE)){//当顾问处于试用期内
				                    				ss=Constant.ZERO+"";
				                    				//total=total+10;
				                    			}else{
				                    				ss=Constant.ten_money;
				                    				total=total+10;
				                    			}
				                    		/* }else if(ss.equals(Constant.HOLIDAY_NOTIME)){
				                    			 ss=Constant.H_CM;*/
				                    		 }else{
				                    			 ss="0";
				                    		 }
				                    	 } 
			                    		 if(j>=41){
					                    		ss="";
				                    	 }
			                       
			                    	}
			                    }
			                    if(j>=5 && j<40){
			                    	cteateCell3(wb,row3,(short)j,ss);
			                    	
								}else {
			                    	cteateCell(wb,row3,(short)j,ss);
			                    	
								}
			                  
			                }
				}
				 sheet.setColumnWidth((short)0,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)1,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)2,(short)(8000)); // 调整列宽度 
				 sheet.setColumnWidth((short)3,(short)(6000)); // 调整列宽度 
				 sheet.setColumnWidth((short)4,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)5,(short)(2000)); // 调整列宽度 
				 sheet.setColumnWidth((short)6,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)7,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)8,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)9,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)10,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)11,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)12,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)13,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)14,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)15,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)16,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)17,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)18,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)19,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)20,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)21,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)22,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)23,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)24,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)25,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)26,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)27,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)28,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)29,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)30,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)31,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)32,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)33,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)34,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)35,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)36,(short)(4000)); // 调整列宽度 
				 sheet.setColumnWidth((short)37,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)38,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)39,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)40,(short)(1000)); // 调整列宽度 
				 sheet.setColumnWidth((short)41,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)42,(short)(1000)); // 调整列宽度 
				 sheet.setColumnWidth((short)43,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)44,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)45,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)46,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)47,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)48,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)49,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)50,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)51,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)52,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)53,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)54,(short)(3000)); // 调整列宽度 
				 sheet.setColumnWidth((short)55,(short)(3000)); // 调整列宽度 
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
		 * 设置单元格的值及其样式  %  百分比格式
		 * @param wb
		 * @param row
		 * @param col
		 * @param val
		 */
		@SuppressWarnings("deprecation")
		public static void cteateCell2(HSSFWorkbook wb,HSSFRow row,short col,String val)
		{
			HSSFCell cell = row.createCell(col);
			if(!Util.objIsNULL(val)){
				cell.setCellValue(new Double(Util.replaceBlank(val).replace("%", ""))/100);
	
				HSSFCellStyle cellStyle = wb.createCellStyle();
	
	            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
	
	            cell.setCellStyle(cellStyle);
			}else {
				cell.setCellValue(new HSSFRichTextString(val));
			}
		}

			
		/**
		 * 设置单元格的值及其样式 --数字格式
		 * @param wb
		 * @param row
		 * @param col
		 * @param val
		 */
		@SuppressWarnings("deprecation")
		public static void cteateCell3(HSSFWorkbook wb,HSSFRow row,short col,String val)
		{
			HSSFCell cell = row.createCell(col);
			cell.setCellValue(new Integer(Util.replaceBlank(val)));
			/*HSSFCellStyle cellstyle= wb.createCellStyle();
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

			cell.setCellStyle(cellstyle); */
			
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
			/*HSSFCellStyle cellstyle= wb.createCellStyle();
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cell.setCellStyle(cellstyle); */
	 
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
		//	cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
			Font font = wb.createFont();
			font.setFontHeightInPoints((short)12); //字体大小
			font.setFontName("仿宋");
			font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
			
			cellstyle.setFont(font);
			cell.setCellStyle(cellstyle);
			
		}
		/**
		 * 设置列头样式---日期类型
		 * @param wb
		 * @param row
		 * @param col
		 * @param val
		 */
		@SuppressWarnings("deprecation")
		public static void cteateDateTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
		{
			HSSFCell cell = row.createCell(col);
			try {
				cell.setCellValue(DateUtils.StrToDate(Util.replaceBlank(val)));
			} catch (Exception e) {
				e.printStackTrace();
				cell.setCellValue(new Date());
			}
			HSSFCellStyle cellstyle= wb.createCellStyle();
			
			Font font = wb.createFont();
			font.setFontHeightInPoints((short)12); //字体大小
			font.setFontName("仿宋");
			font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
			
			cellstyle.setFont(font);
			
			HSSFDataFormat format= wb.createDataFormat();
			cellstyle.setDataFormat(format.getFormat("yyyy-m-d"));

			cell.setCellStyle(cellstyle);
			
		}

	 

}
