package dao.exp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import util.DBManager;
import util.Util;

/**
 * 导入 tr_list
 * @author Wilson
 *
 */
public class ImpTRlistDao {

	/**
	 * 导入Excel表格
	 * @param filename
	 * @param os
	 * @throws IOException
	 */
	static Connection con = null;
	static PreparedStatement ps = null;
	
	@SuppressWarnings("deprecation")
	public static int InputExcel(String filename,InputStream os)throws IOException
	{
		DelTabTraining();
		
		int beginRowIndex =8;//从excel 中开始读取的起始行数
		int totalRows =0;//从excel 表的总行数
		int num=0;
		
		//读取导入的文件
	//	String path = filename.replaceAll("\\\\", "/").substring(filename.replaceAll("\\\\", "/").lastIndexOf("/") + 1);
		//String titlename =path.replaceAll(".xls", ""); 
		
	//	System.out.println(titlename);
		//根据文件的输入流，创建对Excel 工作薄文件的引用
		HSSFWorkbook workbook = new HSSFWorkbook(os);
		//默认exce的书页是“sheet1”
		//HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFSheet sheet = workbook.getSheet("Licencee Record");
		//得到该excel 表的总行数
	//	System.out.println("计算总行数............");
		totalRows =sheet.getLastRowNum();
	//	System.out.println("总行数是"+totalRows);
		//循环读取excel表格的每行记录，并逐行进行保存
		for (int i = beginRowIndex; i <= totalRows; i++) {
			HSSFRow row = sheet.getRow(i);
			//获取一行每列的数据

			HSSFCell tr_idcell = row.getCell((short)0); 
			HSSFCell staff_Nocell = row.getCell((short)1); 
			HSSFCell staffcell = row.getCell((short)2); 
			HSSFCell china_namecell = row.getCell((short)3); 
			HSSFCell aliascell = row.getCell((short)4); 
			HSSFCell name_hkidcell = row.getCell((short)5); 
			HSSFCell hkid_nocell = row.getCell((short)6); 
			HSSFCell date_employmentcell = row.getCell((short)7); 
			HSSFCell license_Datecell = row.getCell((short)8);  
			HSSFCell date_first_Grantcell = row.getCell((short)9);  
			HSSFCell gi_licencecell = row.getCell((short)10);  
			HSSFCell tr_reg_nocell = row.getCell((short)11);  
			HSSFCell reg_linecell = row.getCell((short)12);  
			HSSFCell pp_exam_datecell = row.getCell((short)13);  
			HSSFCell gi_exam_datecell = row.getCell((short)14);  
			HSSFCell lt_exam_datecell = row.getCell((short)15);  
			HSSFCell il_exam_datecell = row.getCell((short)16);  
			HSSFCell date_applycell = row.getCell((short)17);  
			HSSFCell type1cell = row.getCell((short)18);  
			HSSFCell type2cell = row.getCell((short)19);  
			HSSFCell type4cell = row.getCell((short)20);  
			HSSFCell type9cell = row.getCell((short)21);  
			HSSFCell sfc_reg_nocell = row.getCell((short)22);  
			HSSFCell type_condcell = row.getCell((short)23);  
			HSSFCell type_expresscell = row.getCell((short)24);  
			HSSFCell type_normalcell = row.getCell((short)25);
			HSSFCell lrnormalcell = row.getCell((short)26);
			HSSFCell lrissuecell = row.getCell((short)37);
			
			//将数据赋给相关的变量
			if(!Util.objIsNULL(Util.cellToString(tr_idcell))){
				String Id = (num+1)+"";//i+""; //Idcell.getRichStringCellValue().getString();
				String tr_id =  Util.cellToString(tr_idcell);
				String staff_No =  Util.cellToString(staff_Nocell);
				String staff =  Util.cellToString(staffcell);
				String china_name =  Util.cellToString(china_namecell);
				String alias =  Util.cellToString( aliascell);
				String name_hkid =  Util.cellToString(name_hkidcell);
				String hkid_no =  Util.cellToString(hkid_nocell);
				String date_employment =  Util.cellToString(date_employmentcell);
				String license_Date =  Util.cellToString( license_Datecell); 
				String date_first_Grant =  Util.cellToString( date_first_Grantcell); 
				String gi_licence =  Util.cellToString( gi_licencecell); 
				String tr_reg_no =  Util.cellToString( tr_reg_nocell); 
				String reg_line =  Util.cellToString( reg_linecell); 
				String pp_exam_date =  Util.cellToString( pp_exam_datecell); 
				String gi_exam_date =  Util.cellToString( gi_exam_datecell); 
				String lt_exam_date =  Util.cellToString( lt_exam_datecell); 
				String il_exam_date =  Util.cellToString( il_exam_datecell); 
				String date_apply =  Util.cellToString( date_applycell); 
				String type1 =  Util.cellToString( type1cell); 
				String type2 =  Util.cellToString( type2cell); 
				String type4 =  Util.cellToString(  type4cell); 
				String type9 =  Util.cellToString( type9cell);
				String sfc_reg_no =  Util.cellToString( sfc_reg_nocell); 
				String type_cond =  Util.cellToString( type_condcell); 
				String type_express =  Util.cellToString( type_expresscell); 
				String type_normal =Util.cellToString(type_normalcell); 
				String lr_normal =Util.cellToString(lrnormalcell); 
				String lr_issue =Util.cellToString(lrissuecell); 
				
				int r = TrRegisListDao.addTRregisList(Id, tr_id, staff_No, staff, china_name, alias, name_hkid, hkid_no, date_employment, license_Date, date_first_Grant, gi_licence, tr_reg_no, reg_line, pp_exam_date, gi_exam_date, lt_exam_date, il_exam_date, date_apply, type1, type2, type4, type9, sfc_reg_no, type_cond, type_express, type_normal,lr_normal,lr_issue);
				if(r > 0)
				{
					++num;
					//System.out.println("成功");
				}else
				{
					System.out.println("失败");
				}
			}
		}
		return num;
	}
	/**
	 * 删除tr_r_list
	 * @return
	 */
	public static int DelTabTraining() {
		String sqlStr = "delete from tr_r_list";
		int num = 0;
		try {
			con = (Connection) DBManager.getCon();
			ps =  con.prepareStatement(sqlStr);
			num = ps.executeUpdate();
		//	System.out.println("----------num---------"+num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return num;
		
		
	}
}
 
