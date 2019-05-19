package dao.exp;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

public class ExpCardQuota {

	@SuppressWarnings("unused")
	private HSSFWorkbook wb = null;
	public ExpCardQuota(){
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
			int i =4;
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
			 sheet.autoSizeColumn(( short ) 0 ); // 调整第一列宽度 
	         sheet.autoSizeColumn(( short ) 1 ); // 调整第二列宽度 
	         sheet.autoSizeColumn(( short ) 2 ); // 调整第三列宽度 
	         sheet.autoSizeColumn(( short ) 3 ); // 调整第四列宽度 
	         sheet.autoSizeColumn(( short ) 4 ); // 调整第五列宽度 
	         sheet.autoSizeColumn(( short ) 5 ); // 调整第六列宽度 
	         sheet.autoSizeColumn(( short ) 6 ); // 调整第七列宽度 
	         sheet.autoSizeColumn(( short ) 7 ); // 调整第八列宽度 
	         sheet.autoSizeColumn(( short ) 8); // 调整第九列宽度 
	         sheet.autoSizeColumn(( short ) 9 ); // 调整第二列宽度 
	         sheet.autoSizeColumn(( short ) 10 ); // 调整第三列宽度 
	         sheet.autoSizeColumn(( short ) 11 ); // 调整第四列宽度 
	         sheet.autoSizeColumn(( short ) 12); // 调整第五列宽度 
	         sheet.autoSizeColumn(( short ) 13); // 调整第六列宽度 
	         sheet.autoSizeColumn(( short ) 14 ); // 调整第七列宽度 
	         sheet.autoSizeColumn(( short ) 15 ); // 调整第八列宽度 
	         sheet.autoSizeColumn(( short ) 16); // 调整第九列宽度 
	         sheet.autoSizeColumn(( short ) 17); // 调整第二列宽度 
	         sheet.autoSizeColumn(( short ) 18 ); // 调整第三列宽度 
	         sheet.autoSizeColumn(( short ) 19); // 调整第四列宽度 
	         sheet.autoSizeColumn(( short ) 20); // 调整第五列宽度 
	         sheet.autoSizeColumn(( short ) 21); // 调整第六列宽度 
	         /***************************为Excel设置密码***********************************/
	    //  sheet.protectSheet("admin");
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
	public void createepaymentSheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet)throws  IOException{
		int ii =0;
		try{
			int i =1;
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
			 sheet.autoSizeColumn(( short ) 0 ); // 调整第一列宽度 
	         sheet.autoSizeColumn(( short ) 1 ); // 调整第二列宽度 
	         sheet.autoSizeColumn(( short ) 2 ); // 调整第三列宽度 
	         sheet.autoSizeColumn(( short ) 3 ); // 调整第四列宽度 
	         sheet.autoSizeColumn(( short ) 4 ); // 调整第五列宽度 
	         sheet.autoSizeColumn(( short ) 5 ); // 调整第六列宽度 
	         sheet.autoSizeColumn(( short ) 6 ); // 调整第七列宽度 
	         sheet.autoSizeColumn(( short ) 7 ); // 调整第八列宽度 
	         sheet.autoSizeColumn(( short ) 8); // 调整第九列宽度 
	         sheet.autoSizeColumn(( short ) 9 ); // 调整第二列宽度 
	         sheet.autoSizeColumn(( short ) 10 ); // 调整第三列宽度 
	         sheet.autoSizeColumn(( short ) 11 ); // 调整第四列宽度 
	         sheet.autoSizeColumn(( short ) 12); // 调整第五列宽度 
	         sheet.autoSizeColumn(( short ) 13); // 调整第六列宽度 
	         sheet.autoSizeColumn(( short ) 14 ); // 调整第七列宽度 
	         sheet.autoSizeColumn(( short ) 15 ); // 调整第八列宽度 
	         sheet.autoSizeColumn(( short ) 16); // 调整第九列宽度 
	         sheet.autoSizeColumn(( short ) 17); // 调整第二列宽度 
	         sheet.autoSizeColumn(( short ) 18 ); // 调整第三列宽度 
	         sheet.autoSizeColumn(( short ) 19); // 调整第四列宽度 
	         sheet.autoSizeColumn(( short ) 20); // 调整第五列宽度 
	         sheet.autoSizeColumn(( short ) 21); // 调整第六列宽度 
	         /***************************为Excel设置密码***********************************/
	    //  sheet.protectSheet("admin");
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
	public void createFixationSheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet,String Location)throws  IOException{
		int ii =0;
		try{
			int i =4;
			ii =res.getMetaData().getColumnCount();
			String l="";
			while(res.next()){
				l=res.getString("location").replaceAll("/", "").replaceAll(",", "");
				if(l.indexOf(Location)>-1){
				i++;
				HSSFRow row2 =sheet.createRow((short)i);
				for(int j=0;j<ii;j++)
				{
					String ss="";
					if(res.getString(j+1)==null)
						ss="";
					else
						ss=res.getString(j+1);					
					if(j+1==2){
						if(res.getString("type").indexOf("+")==0)
					ss=res.getString("type").substring(1);
					}
					cteateCell(wb,row2,(short)j,ss);
				}
			}
			}
			sheet.autoSizeColumn(( short ) 0 ); // 调整第一列宽度 
			sheet.autoSizeColumn(( short ) 1 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 2 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 3 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 4 ); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 5 ); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 6 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 7 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 8); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 9 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 10 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 11 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 12); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 13); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 14 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 15 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 16); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 17); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 18 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 19); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 20); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 21); // 调整第六列宽度 
			/***************************为Excel设置密码***********************************/
			//sheet.protectSheet("admin");
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		wb.write(os);
		os.flush();
		os.close();
		
	}
	
 
	public void createFixationSheetForResult(Result res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet,String Location)throws  IOException{
		int i =4;
		try{
			
			
			for(SortedMap m:res.getRows()){
				String location=(m.get("location")+"").replaceAll("/", "").replaceAll(",", "");
				if(location.indexOf(Location)>-1){
					i++;
					HSSFRow row2 =sheet.createRow((short)i);
					cteateCell(wb,row2,(short)0,m.get("location")+"");//location
					cteateCell(wb,row2,(short)1,m.get("Company")+"");//company
					cteateCell(wb,row2,(short)2,m.get("layout_type")+"");//layout_type
					cteateCell(wb,row2,(short)3,m.get("staff_code")+"");//Staff_Code
					cteateCell(wb,row2,(short)4,m.get("name")+"");//Name
					cteateCell(wb,row2,(short)5,m.get("name_chinese")+"");//Name_in_Chinese
					cteateCell(wb,row2,(short)6,m.get("title_english")+"");//Title with Department in English
					cteateCell(wb,row2,(short)7,m.get("title_chinese")+"");//Title with Department in Chinese
					cteateCell(wb,row2,(short)8,m.get("external_english")+"");//English Academic Title
					cteateCell(wb,row2,(short)9,m.get("external_chinese")+"");//Chinese Academic Title
					cteateCell(wb,row2,(short)10,m.get("academic_title_e")+"");//External Title with Department in English
					cteateCell(wb,row2,(short)11,m.get("academic_title_c")+"");//External Title with Department in Chinese
					cteateCell(wb,row2,(short)12,m.get("profess_title_e")+"");//English Academic Title
					cteateCell(wb,row2,(short)13,m.get("profess_title_c")+"");//Chinese Academic Title
					cteateCell(wb,row2,(short)14,m.get("tr_reg_no")+"");//T.R. Reg. No.
					cteateCell(wb,row2,(short)15,m.get("ce_no")+"");//CE No.
					cteateCell(wb,row2,(short)16,m.get("mpf_no")+"");//MPF No.
					cteateCell(wb,row2,(short)17,m.get("e_mail")+"");//E-mail
					cteateCell(wb,row2,(short)18,m.get("direct_line")+"");//Direct Line
					cteateCell(wb,row2,(short)19,m.get("fax")+"");//Fax
					cteateCell(wb,row2,(short)20,m.get("bobile_number")+"");//Mobile Phone Number
					cteateCell(wb,row2,(short)21,m.get("quantity")+"");//Quantity
				}
			}
			
			/*
			String l="";
			
			
			
			
			while(res.next()){
				l=res.getString("location").replaceAll("/", "").replaceAll(",", "");
				if(l.indexOf(Location)>-1){
				i++;
				HSSFRow row2 =sheet.createRow((short)i);
				for(int j=0;j<ii;j++)
				{
					String ss="";
					if(res.getString(j+1)==null)
						ss="";
					else
						ss=res.getString(j+1);					
					if(j+1==2){
						if(res.getString("type").indexOf("+")==0)
					ss=res.getString("type").substring(1);
					}
					cteateCell(wb,row2,(short)j,ss);
				}
			}
			}*/
			sheet.autoSizeColumn(( short ) 0 ); // 调整第一列宽度 
			sheet.autoSizeColumn(( short ) 1 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 2 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 3 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 4 ); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 5 ); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 6 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 7 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 8); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 9 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 10 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 11 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 12); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 13); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 14 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 15 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 16); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 17); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 18 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 19); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 20); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 21); // 调整第六列宽度 
			
			 /***************************为Excel设置密码***********************************/
			//sheet.protectSheet("admin");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		wb.write(os);
		os.flush();
		os.close();
		
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
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("新細明體");
		
		cellstyle.setFont(font);
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

}
