package com.coat.timerTask.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import util.DateUtils;
import util.ObjectExcelView;
import util.PageData;
import util.ReadExcel;
import util.Util;
import com.coat.timerTask.dao.ReadImageToExcelDao;
import dao.common.BaseDao;

public class ReadImageToExcelDaoImpl extends BaseDao implements ReadImageToExcelDao{
	
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return null;
	}
	
	private static final long serialVersionUID = 1L;
	static Logger log=Logger.getLogger(ReadImageToExcelDaoImpl.class);
	
	
	public static void testLogger(Exception e){
		//log.info("xxxxxxxxxxx");
		log.error("ReadImageToExcelDaoImpl Operation Error：-->"+e);
	}
public  String test(){
	try {
		System.out.println("print log");
		
	} finally{
		try {
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return "success";
}
	
	public String Image_To_Data(){
		String result="";
		try {
			String sql="select * from read_image_to_excel where status='Y' ";
			List<Map<String,Object>> list=findListMap(sql);
			//String s_new_Date_path[]=new String[100];
			for (int i = 0; i < list.size(); i++) {
				String outFile=list.get(i).get("outFile")+"";//输出路径+文件名
				String ymd=outFile.substring(outFile.indexOf("_")+1, outFile.indexOf(".xls"));
				outFile=outFile.replace(ymd,DateUtils.Ordercode());
				c_ReadImageToExcel(list.get(i).get("imgFile").toString(),list.get(i).get("dataSheet").toString(),list.get(i).get("dataFile").toString(),outFile+""); 
				//String outPath=outFile.substring(0, outFile.lastIndexOf("\\")+1);//不带文件名的路径
				/*if("Staff".equalsIgnoreCase(list.get(i).get("dataSheet")+"")){
					String a=list.get(i).get("imgFile").toString();
					String location=a.substring(a.indexOf("(")+1).substring(0, a.substring(a.indexOf("(")+1).indexOf(" ")).replace("F", "/F");
					//先把图片按楼层分成多个Excel导出void
					staff_ReadImageToExcel(location,"Staff",a.substring(a.indexOf("\\S")+1,a.indexOf(".xls")), list.get(i).get("dataFile").toString(),outPath+"");
					String s_new_Date_path=outPath+a.substring(a.indexOf("\\S")+1,a.indexOf(".xls"))+DateUtils.Ordercode()+".xls";
					//新生成的数据文件和图片整合
					String f_Name=a.substring(a.indexOf("\\S")+1,a.indexOf(".xls"))+DateUtils.Ordercode()+".xls";
					c_ReadImageToExcel(a,"sheet",f_Name, s_new_Date_path,outPath+"");
				}else{*/
				/*String fileName="";
				if(list.get(i).get("imgFile").toString().indexOf("Convoy")>-1){
					fileName=list.get(i).get("imgFile").toString().subSequence(list.get(i).get("imgFile").toString().indexOf("\\C")+1, list.get(i).get("imgFile").toString().indexOf(".xls"))+"_"+DateUtils.Ordercode()+".xls";
				}else{
					fileName=list.get(i).get("imgFile").toString().subSequence(list.get(i).get("imgFile").toString().indexOf("\\S")+1, list.get(i).get("imgFile").toString().indexOf(".xls"))+"_"+DateUtils.Ordercode()+".xls";
				}*/
					//c_ReadImageToExcel(list.get(i).get("imgFile").toString(),list.get(i).get("dataSheet").toString(),list.get(i).get("dataFile").toString(),outFile+""); 
				//}
				result="success";
			}
		} catch (Exception e) {
			//result="error";
			result=e.getMessage();
			e.printStackTrace();
		}finally{
			try {
				super.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Consultant-->图片+数据
	 * @param imgFile(图片路径)
	 * @param sheetName(sheet名称)
	 * @param fileName(文件名)
	 * @param dataFile(数据路径)
	 * @throws Exception 
	 */
	public static void c_ReadImageToExcel(String imgFile,String sheetName,String dataFile,String outPath) throws Exception {
		try {
    		FileInputStream fs=new FileInputStream(dataFile);  
    		POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
    		HSSFWorkbook wb=new HSSFWorkbook(ps);    
    		HSSFSheet sheet=wb.getSheet(sheetName);  //获取到工作表，因为一个excel可能有多个工作表  
    		//图片
    		FileInputStream fss=new FileInputStream(imgFile);  
    		POIFSFileSystem pss=new POIFSFileSystem(fss);  //使用POI提供的方法得到excel的信息  
    		HSSFWorkbook wbs=new HSSFWorkbook(pss); 
    		HSSFSheet imgSheet = (HSSFSheet) wbs.getSheetAt(0);  
    		wbs.setSheetName(0, sheetName);
    		
    		int indexCel=getMaxCellNum(imgFile)+2;
    		int indexRow=2;
    		int MaxRow=sheet.getLastRowNum();//最大行
    		for(int i=0;i<MaxRow;i++){
    		   if(null!=sheet.getRow(i)){
    				
	    			HSSFRow row=sheet.getRow(i);
	    			int cellNum=row.getLastCellNum();
	    			HSSFRow imgRow=imgSheet.getRow(i+indexRow);
	    			if(null==imgRow){
	    				imgRow=imgSheet.createRow(i+indexRow);
	    			}
	    			for(int j=cellNum-1;j>=0;j--){
	    				HSSFCell cell=row.getCell(j);
	    				if(null!=cell && 1==cell.getCellType()){
		    				HSSFCell imgCell=imgRow.createCell(indexCel+j);
		    				imgCell.setCellValue(cell.getStringCellValue());
		    				imgCell.setCellType(cell.getCellType());
		    				//HSSFCellStyle style=cell.getCellStyle();
		    				imgSheet.setColumnWidth(indexCel+j,sheet.getColumnWidth(j));
	    				}
	    				 
	    			}
	    		}
    		} 
          FileOutputStream  fileOut = new FileOutputStream(outPath);     
            // 写入excel文件     
          wbs.write(fileOut); 
    	  System.out.println("--------------文件已生成至("+outPath+")----------------");
		} catch (Exception e) {
		    testLogger(e);
			throw new Exception(e);
		}finally{
			
		}
	}
	
	
   /**
    * 图片最大行数
    * @param file
    * @return
    */
	public static int getMaxCellNum(String file){
		int num=0;
		try {
	 		 InputStream inp = new FileInputStream(file);  
	         HSSFWorkbook workbook = (HSSFWorkbook) WorkbookFactory.create(inp);  
	         HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);  
	         int i = 0;  
	         for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) { 
	             HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();  
	             if (shape instanceof HSSFPicture) {  
	                 if(anchor.getCol2()>num){
	                 	num=anchor.getCol2();
	                 }
	             }  
	             i++;  
	         } 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public static void staff_ReadImageToExcel(String location,String sheetName,String fileName,String dataFile,String savePath) {
		try {
			File file=new File(dataFile);
    		List<List<Object>> list=ReadExcel.readExcel(file,sheetName, 20, 0);//解析Excel,把数据转为List
    		List<List<Object>> listStr=new ArrayList<List<Object>>();
    		
    		for (int i = 0; i < list.size(); i++) {
				if(location.equalsIgnoreCase(list.get(i).get(1)+"")){
					listStr.add(list.get(i));
				}
			}

	    	Map<String, Object> model=new HashMap<String, Object>();
			List<Map<Integer,Object>> title=new ArrayList<Map<Integer,Object>>();
			Map<Integer,Object> map=new HashMap<Integer, Object>();
			map.put(0,"Company");
			map.put(1,"Location");
			map.put(2,"Seat No.");
			map.put(3,"Dept");
			map.put(4,"Satff Code");
			map.put(5,"Staff Name");
			map.put(6,"Extension");
			map.put(7,"Remarks");
			map.put(8,"Phone Check?");
			map.put(9,"Seat Check?");
			title.add(map);
			model.put("titles", title);
			List<PageData> data=new ArrayList<PageData>();
			if(!Util.objIsNULL(listStr)){
				for (int i = 0; i < listStr.size(); i++) {
					PageData p=new PageData();
			        p.put("var1",listStr.get(i).get(0)+"");
			        p.put("var2",listStr.get(i).get(1)+"");
			        p.put("var3",listStr.get(i).get(2)+"");
			        p.put("var4",listStr.get(i).get(3)+"");
			        p.put("var5",listStr.get(i).get(4)+"");
			        p.put("var6",listStr.get(i).get(5)+"");
			        p.put("var7",listStr.get(i).get(6)+"");
			        p.put("var8",listStr.get(i).get(7)+"");
			        p.put("var9",listStr.get(i).get(8)+"");
			        p.put("var10",listStr.get(i).get(9)+"");
					data.add(p);
				}
			}else{
				return ;
			}
			model.put("varList", data);
			String outFilePath =fileName+DateUtils.Ordercode()+".xls";
			ObjectExcelView view=new ObjectExcelView();
			view.addStaticAttribute("xxx", "gsdg");
		    HSSFWorkbook wb= new HSSFWorkbook();
			view.buildExcelDocument(model, wb,outFilePath,"sheet",savePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
}
