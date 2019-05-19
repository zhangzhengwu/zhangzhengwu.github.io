package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcel {


	public static void main(String[] args) {
		try {
			/*List<List<Object>> re=readExcel(new File("C:\\Users\\kingxu\\Desktop\\Return Mail\\Key for Chicken Box - Master.xlsx"),"Chicken Box Key",7,1);
		 for(int i=0;i<re.size();i++){
			List list=re.get(i);
			 for(int j=0;j<list.size();j++){
				 System.out.println(i+"行 "+j+"列：   "+list.get(j));
			 }
		 }
			 */List<List<Object>> list=readcsv(new File("C:\\Users\\kingxu\\Desktop\\missingpayment.csv"), 18, 0);
			 System.out.println();
			 System.out.println();
			 System.out.println();
			 for(int i=0;i<list.size();i++){
				 List<Object> l=list.get(i);
				 System.out.print((i+1));
				 for(int j=0;j<l.size();j++){
					 System.out.print("--->"+l.get(j).toString().trim());
				 }
				 System.out.println();
			 }	

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对外提供读取excel 的方法
	 * @throws Exception 
	 * */
	public static List<List<Object>> readExcel(File file,Object sheetName,int cellNum,int startRow) throws Exception{
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".")==-1?"":fileName.substring(fileName.lastIndexOf(".")+1);
		if("xls".equals(extension)){
			return read2003Excel(file,sheetName,cellNum,startRow);
		}else if("xlsx".equals(extension)){
			return read2007Excel(file,sheetName,cellNum,startRow);
		}else if("csv".equals(extension)){
			return readcsv(file,cellNum,startRow);
		}else{
			throw new IOException("不支持的文件类型");
		}
	}

	private static List<List<Object>> readcsv(File file,int cellNum,int startRow) throws IOException{
		int row=0;
		List<List<Object>> list = new LinkedList<List<Object>>();
		BufferedReader br = null;
		Reader read=null;
		try {

			read=new FileReader(file);
			br = new BufferedReader(read);
			// 读取直到最后一行 
			String line = ""; 
			while ((line = br.readLine()) != null ) { 
				if(row<startRow){
					row++;
					continue;
				}
				// 把一行数据分割成多个字段 
				StringTokenizer st = new StringTokenizer(line, ",");
				int cell=0;
				List<Object> linked = new LinkedList<Object>();
				//	while (st.hasMoreTokens()) { 
				while (cell<cellNum) { 
					String cellValue=st.nextToken().toString().replaceAll("\"","").trim();
					// 每一行的多个字段用TAB隔开表示 
					linked.add(Util.objIsNULL(cellValue)?"":cellValue);

					cell++;
				} 
				list.add(linked);
				row++;

			} 



		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}finally{
			if(br!=null){
				read.close();
				br.close();
			}
		}



		return list;
	}

	/**
	 * 读取 office 2003 excel
	 * @throws Exception 
	 * @throws FileNotFoundException */
	private static List<List<Object>> read2003Excel(File file,Object sheetName,int cellNum,int startRow) throws Exception{
		int cellNums=0;

		List<List<Object>> list = new LinkedList<List<Object>>();
		InputStream in=null;
		HSSFSheet sheet = null;
		try{
			in=new FileInputStream(file);
			HSSFWorkbook hwb = new HSSFWorkbook(in);
			if(sheetName instanceof String){
				sheet= hwb.getSheet(sheetName.toString());
			}else if(sheetName instanceof Integer){
				sheet= hwb.getSheetAt((Integer)sheetName);
			}else {
				return null;
			}
			Object value = null;
			HSSFRow row = null;
			HSSFCell cell = null;
//			System.out.println(sheet.getLastRowNum()+"<------ LastRow行数");
//			System.out.println(sheet.getPhysicalNumberOfRows()+"<------ PhysicalNumber行数");
			for(int i = startRow;i<= sheet.getLastRowNum();i++){
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				List<Object> linked = new LinkedList<Object>();
				if(cellNum==-1){//判断为空，获取excel可见列数
					cellNums=row.getLastCellNum();
				}else{
					cellNums=(Integer)cellNum;
				}
				for (int j = 0; j <= cellNums; j++) {
					cell = row.getCell(j);
					if (cell == null) {
						value="";
						//continue;
					}else{
						
						DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
						SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
						DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
						switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_STRING:
							//System.out.println(i+"行"+j+" 列 is String type");
							value = cell.getStringCellValue();
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							//System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());
							if("@".equals(cell.getCellStyle().getDataFormatString())){
								value = df.format(cell.getNumericCellValue());
							} else if("General".equals(cell.getCellStyle().getDataFormatString())){
								value = nf.format(cell.getNumericCellValue());
							}else if("00000000".equals(cell.getCellStyle().getDataFormatString())){
								value =df.format(cell.getNumericCellValue());
							}else if("0_ ".equals(cell.getCellStyle().getDataFormatString())){
								value =df.format(cell.getNumericCellValue());
							}else if("[$-409]d\\-mmm\\-yy;@".equals(cell.getCellStyle().getDataFormatString())){
								value =dfs.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
							}else{
								value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
							}
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							//System.out.println(i+"行"+j+" 列 is Boolean type");
							value = cell.getBooleanCellValue();
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							//System.out.println(i+"行"+j+" 列 is Blank type");
							value = "";
							break;
						case XSSFCell.CELL_TYPE_FORMULA:
							//System.out.println(i+"行"+j+" 列 is FORMULA type");
							value=cell.getCellFormula();
							break;
						default:
							//System.out.println(i+"行"+j+" 列 is default type");
							value = cell.toString();
						}
					}
					linked.add(value);
				}
				list.add(linked);
			}
		}catch (Exception e) {
			//e.printStackTrace();
			throw e;
		}finally{
			if(in!=null){
				in.close();
			}
		}
		return list;
	}



	/**
	 * 读取Office 2007 excel
	 * */

	private static List<List<Object>> read2007Excel(File file,Object sheetName,int cellNum,int startRow) throws IOException {
		int cellNums=0;
		List<List<Object>> list = new LinkedList<List<Object>>();
		XSSFSheet sheet = null;
		InputStream in=null;
		try{
			in=new FileInputStream(file);
			// 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(in);
			if(sheetName instanceof String){
				sheet=xwb.getSheet(sheetName.toString());
			}else if(sheetName instanceof Integer){
				sheet=xwb.getSheetAt((Integer)sheetName);
			}else {
				return null;
			}
			Object value = null;
			XSSFRow row = null;
			XSSFCell cell = null;
			for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				List<Object> linked = new LinkedList<Object>();
				//-->判断为空，获取excel可见列数
				if(cellNum==-1){
					cellNums=row.getLastCellNum();
				}else{
					cellNums=(Integer)cellNum;
				}
				for (int j = 0; j <= cellNums; j++) {
					cell = row.getCell(j);
					if (cell == null) {
						value="";
					}else{
						DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
						SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
						DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字

						switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_STRING:
							// System.out.println(i+"行"+j+" 列 is String type");
							value = cell.getStringCellValue();
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							// System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());
							if("@".equals(cell.getCellStyle().getDataFormatString())){
								value = df.format(cell.getNumericCellValue());
							} else if("General".equals(cell.getCellStyle().getDataFormatString())){
								value = nf.format(cell.getNumericCellValue());
							}else if("00000000".equals(cell.getCellStyle().getDataFormatString())){
								value =df.format(cell.getNumericCellValue());
							}else if("0_ ".equals(cell.getCellStyle().getDataFormatString())){
								value =df.format(cell.getNumericCellValue());
							}else if("[$-409]d\\-mmm\\-yy;@".equals(cell.getCellStyle().getDataFormatString())){
								value =dfs.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
							}else{
								value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
							}
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							// System.out.println(i+"行"+j+" 列 is Boolean type");
							value = cell.getBooleanCellValue();
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							//  System.out.println(i+"行"+j+" 列 is Blank type");
							value = "";
							break;
						case XSSFCell.CELL_TYPE_FORMULA:
							// System.out.println(i+"行"+j+" 列 is FORMULA type");
							value=cell.getCellFormula();
							break;
						default:
							// System.out.println(i+"行"+j+" 列 is default type");
							value = cell.toString();
						}
					}
					linked.add(value);
				}
				list.add(linked);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				in.close();
			}
		}
		return list;
	}

}


