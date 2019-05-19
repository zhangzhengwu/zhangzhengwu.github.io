package util;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import entity.Excel;

public class ExcelTools {

	/**
	 * 样式设置
	 */
	private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        DataFormat df = wb.createDataFormat();

        CellStyle style;
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE
                .getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE
                .getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("header_date", style);

        Font font1 = wb.createFont();
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font1);
        styles.put("cell_b", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font1);
        styles.put("cell_b_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_b_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_g", style);

        Font font2 = wb.createFont();
        font2.setColor(IndexedColors.BLUE.getIndex());
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font2);
        styles.put("cell_bb", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_bg", style);

        Font font3 = wb.createFont();
        font3.setFontHeightInPoints((short) 14);
        font3.setColor(IndexedColors.DARK_BLUE.getIndex());
        font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font3);
        style.setWrapText(true);
        styles.put("cell_h", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        styles.put("cell_normal", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        styles.put("cell_normal_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_normal_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setIndention((short) 1);
        style.setWrapText(true);
        styles.put("cell_indented", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_blue", style);
        //设置背景为黄色
        style = createBorderedStyle(wb);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("YELLOW", style);
		//设置背景为紫色
		style = createBorderedStyle(wb);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("VIOLET", style);
		//设置背景为绿色
		style = createBorderedStyle(wb);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("BRIGHT_GREEN", style);
		//背景为蓝色
		style = createBorderedStyle(wb);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);	
		styles.put("SKY_BLUE", style);
		return styles;
    }
   /**
    * 生成一个样式
    * @param wb
    * @return CellStyle
    */
    private static CellStyle createBorderedStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
    
    //Rectrutment 导出
    public static void createStyle(Excel excelModel,HttpServletResponse response) throws Exception {
    	//创建一个工作簿 
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	// Create sheet
    	HSSFSheet sheet = workbook.createSheet(excelModel.getSheetname());
		sheet.setDisplayGridlines(false);
	    sheet.setPrintGridlines(false);
	    sheet.setFitToPage(true);
	    sheet.setHorizontallyCenter(true);

		 //加载样式
		Map<String, CellStyle> styles = createStyles(workbook);
		//创建第一行
		HSSFRow rowhead = sheet.createRow(0);
		 //合并单元格
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 8));
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 11));
	    sheet.addMergedRegion(new CellRangeAddress(0, 1, 12, 12));
	  
	    //创建单元格样式 (黄色)
	    HSSFCell  headCell= rowhead.createCell((short)0);
	    headCell.setCellValue("Requistion");
	    headCell.setCellStyle(styles.get("YELLOW"));
		//绿色
	    headCell = rowhead.createCell((short) 6);
	    headCell.setCellValue("Placement");
	    headCell.setCellStyle(styles.get("BRIGHT_GREEN"));
		//紫色
	  
	    headCell = rowhead.createCell((short) 9);
	    headCell.setCellValue("Payment");
	    headCell.setCellStyle(styles.get("VIOLET"));
		//蓝色
	    headCell= rowhead.createCell((short) 12);
	    headCell.setCellValue("Status");
	    headCell.setCellStyle(styles.get("SKY_BLUE"));
		//创建第二行
	    HSSFRow row1 = sheet.createRow(1);
	   for (int i = 0; i < excelModel.getColumns().length; i++){
		   headCell=row1.createCell(i);
		   headCell.setCellValue(excelModel.getColumns()[i]);
		   if(i<=5){
			   headCell.setCellStyle(styles.get("YELLOW"));   
		    }else if(i>5&&i<=8){
		    	headCell.setCellStyle(styles.get("BRIGHT_GREEN")); 
		    }else if(i>8&&i<=11){
		    	 headCell.setCellStyle(styles.get("VIOLET"));
		    }else if(i==12){
		    	 headCell.setCellStyle(styles.get("SKY_BLUE"));
		    }
		    else{
		    	headCell.setCellStyle(styles.get("cell_normal"));
		    } 
		}	
	//定义一个单元格
	   HSSFCell cell;
	//定义一行
	   HSSFRow row;
	  int rownum = 2;
      List<?> list = excelModel.getExcelContentList();
      for (int i = 0; i < list.size(); i++, rownum++) {
          row = sheet.createRow(rownum);
          Object dataObj = list.get(i);//获取对象
          if(dataObj == null){
              continue;
          }
          String[] objValue = getBeanFieldValues(dataObj, excelModel.getHeaderNames());
          for (int j = 0; j < objValue.length; j++) {
          cell = row.createCell(j);
          cell.setCellValue(objValue[j]);
		  cell.setCellStyle(styles.get("cell_normal"));
          sheet.setColumnWidth(j, 7000);
      }
    } 
      ServletOutputStream sos = response.getOutputStream();
      workbook.write(sos);
      sos.close();
    }
    
 
	/**
	 * 导出数据操作
	 */
    public static void createExcel(Excel excelModel,HttpServletResponse response) throws Exception{
          //非空验证
       if(excelModel.getFilename()==null||excelModel.getFilename()==""){
    	    new Exception("文件名称为空！");
       }
       if(excelModel.getSheetname()==null||excelModel.getSheetname()==""){
    	   new Exception("sheet名称为空！");
       } 
       if(excelModel.getColumns()==null){
    	   new Exception("表头字段为空！");
       }
       if(excelModel.getHeaderNames()==null){//字段为空
           new Exception("字段错误！");
       }
       //声明一个工作薄
        Workbook wb;
        wb = new HSSFWorkbook();
        //生成样式   
        Map<String, CellStyle> styles = createStyles(wb);
         //创建一个工作薄
        Sheet sheet = wb.createSheet(excelModel.getSheetname());
        sheet.setDisplayGridlines(false);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        
        // the header row: centered text in 48pt font
        //创建一行
        Row headerRow = sheet.createRow(0); 
        headerRow.setHeightInPoints(12.75f);
        /****** 样式设置 结束 ******/


        //表头
        for (int i = 0; i < excelModel.getColumns().length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(excelModel.getColumns()[i]);
            cell.setCellStyle(styles.get("header"));
        }


        // 设置表头为锁定列
        sheet.createFreezePane(0, 1);

        /******** 设置内容 begin*********/
        Row row;
        Cell cell;
        int rownum = 1;

        List<?> list = excelModel.getExcelContentList();

        
        
     
        for (int i = 0; i < list.size(); i++, rownum++) {

            row = sheet.createRow(rownum);
            Object dataObj = list.get(i);//获取对象
            if(dataObj == null){
                continue;
            }
            String[] objValue = getBeanFieldValues(dataObj, excelModel.getHeaderNames());
            for (int j = 0; j < objValue.length; j++) {
            cell = row.createCell(j);
            String styleName;
            styleName = "cell_normal";
            //TODO 目前强制转换为String，后续完善其它数据类型
            cell.setCellValue(objValue[j]);
            cell.setCellStyle(styles.get(styleName));
            sheet.setColumnWidth(j, 5000);
        }
      } 
    /******** 设置内容 end*********/
    /****** 输出 begin ******/
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
        /****** 输出 end ******/

    }
    
/*    public static void main(String[] args) {
    	Excel excel=new Excel();
    	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("a", "a1");
    	map.put("a2", "a2");
    	map.put("a3", "a3");
    	map.put("a4", "a4");
    	map.put("a5", "a5");
    	list.add(map);
    	list.add(map);
    	list.add(map);
    	list.add(map);
    	list.add(map);
	    //把数据交给Excel
	    excel.setExcelContentList(list);	
	    //设置Excel列头
	    excel.setColumns(new String[]{"a"});
	    //属性字段名称
	    excel.setHeaderNames(new String[]{"A1"});
	   //sheet名称
	    excel.setSheetname("Daily Opt-out List_"+DateUtils.Ordercode());
	    //文件名称
		excel.setFilename("Daily Opt-out List_"+DateUtils.Ordercode()+".csv");
		//表单生成
		excel.setFilepath(Util.getProValue("public.write.Opt.out.report.IVR.folder"));
		try {
			ExcelTools.createCSVToPath(excel,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
    
    /**
     * 将数据写入某一自定义位置
     */
    public static String createCSVToPath(Excel excelModel,String splitField) throws Exception{
    	String result = "";
    	FileOutputStream out=null;
    	OutputStreamWriter osw=null;
    	BufferedWriter bw=null;
    	try {
	    	//非空验证
	    	if(excelModel.getFilename()==null||excelModel.getFilename()==""){
	    		new Exception("文件名称为空！");
	    	}
	        if(excelModel.getSheetname()==null||excelModel.getSheetname()==""){
	     	   new Exception("sheet名称为空！");
	        } 
	    	if(excelModel.getColumns()==null){
	    		new Exception("表头字段为空！");
	    	}
	    	if(excelModel.getHeaderNames()==null){//字段为空
	    		new Exception("字段错误！");
	    	}
	         out = new FileOutputStream(excelModel.getFilepath()+excelModel.getFilename());
	         osw = new OutputStreamWriter(out);
	         bw =new BufferedWriter(osw);
	         String[] header=excelModel.getHeaderNames();
	 		 for (int j = 0; j <header.length; j++) {
	 			 bw.append(header[j]).append(splitField);
	 		 }
	 		 bw.append("\r");
	    	    List<Map<String,Object>> list=(List<Map<String, Object>>)excelModel.getExcelContentList();
	    	    for (int i = 0; i < list.size(); i++) {
	    		Object dataObj = list.get(i);//获取对象
	    		if(dataObj == null){
	    			continue;
	    		}
	    		Map<String,Object> map=list.get(i);
	    		String[] column=excelModel.getColumns();
	    		
	    		for (int j = 0; j < column.length; j++) {
	    			 bw.append(map.get(column[j]).toString()).append(splitField);
	    		}
	    		//\r\n
	    		bw.append("\r");
	    	} 
	    	result = Util.getMsgJosnObject("success", "生成.csv文件成功");    
		} catch (Exception e) {
			result = Util.joinException(e);
		} finally {
		    if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
		}
    	
    	return result;
    }
    /**
     * 将数据写入某一自定义位置
     */
    public static void createExcelToPath(Excel excelModel) throws Exception{
    	//非空验证
    	if(excelModel.getFilename()==null||excelModel.getFilename()==""){
    		new Exception("文件名称为空！");
    	}
    	if(excelModel.getSheetname()==null||excelModel.getSheetname()==""){
    		new Exception("sheet名称为空！");
    	} 
    	if(excelModel.getColumns()==null){
    		new Exception("表头字段为空！");
    	}
    	if(excelModel.getHeaderNames()==null){//字段为空
    		new Exception("字段错误！");
    	}
    	//声明一个工作薄
    	Workbook wb;
    	wb = new HSSFWorkbook();
    	//生成样式   
    	Map<String, CellStyle> styles = createStyles(wb);
    	//创建一个工作薄
    	Sheet sheet = wb.createSheet(excelModel.getSheetname());
    	sheet.setDisplayGridlines(false);
    	sheet.setPrintGridlines(false);
    	sheet.setFitToPage(true);
    	sheet.setHorizontallyCenter(true);
    	
    	// the header row: centered text in 48pt font
    	//创建一行
    	Row headerRow = sheet.createRow(0); 
    	headerRow.setHeightInPoints(12.75f);
    	/****** 样式设置 结束 ******/
    	
    	
    	//表头
    	for (int i = 0; i < excelModel.getColumns().length; i++) {
    		Cell cell = headerRow.createCell(i);
    		cell.setCellValue(excelModel.getColumns()[i]);
    		cell.setCellStyle(styles.get("header"));
    	}
    	
    	
    	// 设置表头为锁定列
    	sheet.createFreezePane(0, 1);
    	
    	/******** 设置内容 begin*********/
    	Row row;
    	Cell cell;
    	int rownum = 1;
    	
    	List<?> list = excelModel.getExcelContentList();
    	
    	for (int i = 0; i < list.size(); i++, rownum++) {
    		
    		row = sheet.createRow(rownum);
    		Object dataObj = list.get(i);//获取对象
    		if(dataObj == null){
    			continue;
    		}
    		String[] objValue = getBeanFieldValues(dataObj, excelModel.getHeaderNames());
    		for (int j = 0; j < objValue.length; j++) {
    			cell = row.createCell(j);
    			String styleName;
    			styleName = "cell_normal";
    			//TODO 目前强制转换为String，后续完善其它数据类型
    			cell.setCellValue(objValue[j]);
    			cell.setCellStyle(styles.get(styleName));
    			sheet.setColumnWidth(j, 5000);
    		}
    	} 
    	/******** 设置内容 end*********/
    	/****** 输出 begin ******/
    	FileOutputStream stream=new FileOutputStream(excelModel.getFilepath()+excelModel.getFilename()); 
    	wb.write(stream);
		stream.flush();
		stream.close();
    	/****** 输出 end ******/
    	
    }
    @SuppressWarnings("unchecked")
    public static void createExcel2(Excel excelModel,HttpServletResponse response) throws Exception{
        //非空验证
     if(excelModel.getFilename()==null||excelModel.getFilename()==""){
  	    new Exception("文件名称为空！");
     }
     if(excelModel.getSheetname()==null||excelModel.getSheetname()==""){
  	   new Exception("sheet名称为空！");
     } 
     if(excelModel.getColumns()==null){
  	   new Exception("表头字段为空！");
     }
     if(excelModel.getHeaderNames()==null){//字段为空
         new Exception("字段错误！");
     }
     //声明一个工作薄
      Workbook wb;
      wb = new HSSFWorkbook();
      //生成样式   
      Map<String, CellStyle> styles = createStyles(wb);
       //创建一个工作薄
      Sheet sheet = wb.createSheet(excelModel.getSheetname());
      sheet.setDisplayGridlines(false);
      sheet.setPrintGridlines(false);
      sheet.setFitToPage(true);
      sheet.setHorizontallyCenter(true);
      
      // the header row: centered text in 48pt font
      //创建一行
      Row headerRow = sheet.createRow(0); 
      headerRow.setHeightInPoints(12.75f);
      /****** 样式设置 结束 ******/


      //表头
      for (int i = 0; i < excelModel.getColumns().length; i++) {
          Cell cell = headerRow.createCell(i);
          cell.setCellValue(excelModel.getColumns()[i]);
          cell.setCellStyle(styles.get("header"));
      }


      // 设置表头为锁定列
      sheet.createFreezePane(0, 1);

      /******** 设置内容 begin*********/
      Row row;
      Cell cell;
      int rownum = 1;

//      List<?> list = excelModel.getExcelContentList();

      
	List<Map<String,Object>>list=(List<Map<String, Object>>) excelModel.getExcelContentList();
      for (int i = 0; i < list.size(); i++, rownum++) {
    	  row = sheet.createRow(rownum); 
    	  Map<String,Object> map=list.get(i);
    	  String[] headers=excelModel.getHeaderNames();
    	  for (int j = 0; j < headers.length; j++) {
    		  cell = row.createCell(j);
              String styleName;
              styleName = "cell_normal";
              //TODO 目前强制转换为String，后续完善其它数据类型
              if(map.get(headers[j]) instanceof Integer){
            	  cell.setCellValue(Integer.parseInt(map.get(headers[j])+""));
              }else if(map.get(headers[j]) instanceof Double){
            	  cell.setCellValue(Double.parseDouble(map.get(headers[j])+""));
              }else{
            	  cell.setCellValue(map.get(headers[j])+"");
              }
              cell.setCellStyle(styles.get(styleName));
              sheet.setColumnWidth(j, 5000);
    	  }
      }
      /*for (int i = 0; i < list.size(); i++, rownum++) {
          row = sheet.createRow(rownum);
          Object dataObj = list.get(i);//获取对象
          if(dataObj == null){
              continue;
          }
          String[] objValue = getBeanFieldValues(dataObj, excelModel.getHeaderNames());
          for (int j = 0; j < objValue.length; j++) {
          cell = row.createCell(j);
          String styleName;
          styleName = "cell_normal";
          //TODO 目前强制转换为String，后续完善其它数据类型
          cell.setCellValue(objValue[j]);
          cell.setCellStyle(styles.get(styleName));
          sheet.setColumnWidth(j, 5000);
      }
    } */
  /******** 设置内容 end*********/
  /****** 输出 begin ******/
      ServletOutputStream sos = response.getOutputStream();
      wb.write(sos);
      sos.close();
      /****** 输出 end ******/
  }
    
    
    
    /**
    *
    * @param bean 实体bean
    * @param fields 指定的字段
    * @return rtnDatas 取出bean中的值，以数组方式返回，并且与fields顺序一一对应
    * @throws Exception
    */
   public static String[] getBeanFieldValues(Object bean, String[] fields) throws Exception{
       Class<? extends Object> classType = bean.getClass();

       String[] rtnDatas = new String[fields.length];

       for(int i=0; i<fields.length;i++){
           String fieldName=fields[i];
           String firstLetter=fieldName.substring(0,1).toUpperCase();
           //获得和属性对应的getXXX()方法的名字
           String getMethodName="get"+firstLetter+fieldName.substring(1);
           //获得和属性对应的getXXX()方法
           Method getMethod=classType.getMethod(getMethodName,new Class[]{});
           //调用原对象的getXXX()方法
           Object value = getMethod.invoke(bean,new Object[]{});
           if(value!=null)
               rtnDatas[i] = value.toString();
           else
               rtnDatas[i] = null;
       }
       return rtnDatas;  
   }
 
   
   
   /**
    *  防止文件名称乱码
    * @param filename
    * @return
    */
   public static  String getExcelFileName(String filename){
	   try {
		filename=new String(filename.getBytes("utf-8"),"utf-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return filename;
   }



   
}