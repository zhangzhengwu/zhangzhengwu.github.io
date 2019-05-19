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
import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

/**
 * 
 * @author wilson
 *
 */
public class ImpPCReportDao {
 
		/**
		 * 导入Excel表格
		 * @param filename
		 * @param os
		 * @throws IOException
		 */
		static Connection con = null;
		static PreparedStatement ps = null;
		private static final Log LOG = LogFactory.getLog(ImpPCReportDao.class);
		@SuppressWarnings({ "deprecation", "static-access" })
		public static int InputExcel(String filename,InputStream os)throws IOException
		{
			DelTabTraining();
			
			int beginRowIndex =1;//从excel 中开始读取的起始行数
			int totalRows =0;//从excel 表的总行数
			int num=0;
			
			//读取导入的文件
		//	String path = filename.replaceAll("\\\\", "/").substring(filename.replaceAll("\\\\", "/").lastIndexOf("/") + 1);
		//	String titlename =path.replaceAll(".xls", ""); 
			
			//System.out.println(titlename);
			//根据文件的输入流，创建对Excel 工作薄文件的引用
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			//默认exce的书页是“sheet1”
			HSSFSheet sheet = workbook.getSheetAt(0);
			//HSSFSheet sheet = workbook.getSheet("Licencee Record");
			//得到该excel 表的总行数
			//System.out.println("计算总行数............");
			totalRows =sheet.getLastRowNum();
			//System.out.println("总行数是"+totalRows);
			//循环读取excel表格的每行记录，并逐行进行保存
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);
				//获取一行每列的数据

				HSSFCell Direct_Team = row.getCell((short)0); 
				HSSFCell Direct_Leader = row.getCell((short)1); 
				HSSFCell Introducer = row.getCell((short)2); 
				HSSFCell Staff_Code = row.getCell((short)3); 
				HSSFCell DD_VP = row.getCell((short)4); 
				HSSFCell Join_Title = row.getCell((short)5); 
				HSSFCell Title = row.getCell((short)6); 
				HSSFCell Trainee = row.getCell((short)7); 
				HSSFCell Staff_Name = row.getCell((short)8); 
				HSSFCell Date_of_Group_Join = row.getCell((short)9); 
				HSSFCell Resign_Date = row.getCell((short)10); 
				HSSFCell TR_App_Date = row.getCell((short)11); 
				HSSFCell TR_Issue_Date = row.getCell((short)12); 
				HSSFCell LR_App_Date = row.getCell((short)13); 
				HSSFCell LR_Issue_Date = row.getCell((short)14); 
				HSSFCell LR_Normal_License = row.getCell((short)15); 
				HSSFCell IT_Completed_Date = row.getCell((short)16); 
				HSSFCell IT_Phase_Two = row.getCell((short)17); 
				HSSFCell Half_Consultant = row.getCell((short)18); 
				HSSFCell Consultant = row.getCell((short)19); 
				HSSFCell S_Consultant_1 = row.getCell((short)20); 
				HSSFCell S_Consultant_2 = row.getCell((short)21); 
				HSSFCell Principal_Consultant = row.getCell((short)22); 
				HSSFCell Wealth_Manager = row.getCell((short)23); 
				HSSFCell S_Wealth_Manager = row.getCell((short)24); 
				HSSFCell S_Wealth_Manager_2 = row.getCell((short)25); 
				HSSFCell P_Wealth_Manager = row.getCell((short)26); 
				HSSFCell Associate = row.getCell((short)27); 
				HSSFCell Ass_Director = row.getCell((short)28); 
				HSSFCell Deputy_Director = row.getCell((short)29); 
				HSSFCell Regional_Director = row.getCell((short)30); 
				HSSFCell Asst_Vice_President = row.getCell((short)31); 
				HSSFCell Vice_President = row.getCell((short)32); 
				HSSFCell Sr_Vice_President = row.getCell((short)33); 
				HSSFCell Adjustment = row.getCell((short)34); 
				HSSFCell BV2003_ = row.getCell((short)35); 
				HSSFCell BV2004_ = row.getCell((short)36); 
				HSSFCell BV2005_ = row.getCell((short)37); 
				HSSFCell BV2006_ = row.getCell((short)38); 
				HSSFCell BV2007_ = row.getCell((short)39); 
				HSSFCell Addition_BV = row.getCell((short)40); 
				HSSFCell BV_Enough_To_CO1A = row.getCell((short)41); 
				HSSFCell BV_Enough_To_CO2 = row.getCell((short)42); 
				
				//将数据赋给相关的变量
				
				String Id = i+""; //Idcell.getRichStringCellValue().getString();
				String Direct_Team1   = Util.cellToString(Direct_Team); 
				String Direct_Leader1  = Util.cellToString(Direct_Leader); 
				String Introducer1  = Util.cellToString(Introducer); 
				String Staff_Code1 = Util.cellToString(Staff_Code); 
				String DD_VP1 = Util.cellToString(DD_VP); 
				String Join_Title1 = Util.cellToString(Join_Title); 
				String Title1 = Util.cellToString(Title); 
				String Trainee1 = Util.cellToString(Trainee); 
				String Staff_Name1 = Util.cellToString(Staff_Name); 
				String Date_of_Group_Join1 = Util.cellToString(Date_of_Group_Join); 
				String Resign_Date1 = Util.cellToString(Resign_Date);
				String TR_App_Date1 = Util.cellToString(TR_App_Date); 
				String TR_Issue_Date1 = Util.cellToString(TR_Issue_Date); 
				String LR_App_Date1 = Util.cellToString(LR_App_Date); 
				String LR_Issue_Date1 = Util.cellToString(LR_Issue_Date); 
				String LR_Normal_License1 = Util.cellToString(LR_Normal_License); 
				String IT_Completed_Date1 = Util.cellToString(IT_Completed_Date);
				String IT_Phase_Two1 = Util.cellToString(IT_Phase_Two); 
				String Half_Consultant1 = Util.cellToString(Half_Consultant); 
				String Consultant1 = Util.cellToString(Consultant); 
				String S_Consultant_11 = Util.cellToString(S_Consultant_1); 
				String S_Consultant_21 = Util.cellToString(S_Consultant_2); 
				String Principal_Consultant1 = Util.cellToString(Principal_Consultant); 
				String Wealth_Manager1 = Util.cellToString(Wealth_Manager); 
				String S_Wealth_Manager1 = Util.cellToString(S_Wealth_Manager); 
				String S_Wealth_Manager_21 = Util.cellToString(S_Wealth_Manager_2); 
				String P_Wealth_Manager1 = Util.cellToString(P_Wealth_Manager); 
				String Associate1 = Util.cellToString(Associate); 
				String Ass_Director1 = Util.cellToString(Ass_Director); 
				String Deputy_Director1 = Util.cellToString(Deputy_Director); 
				String Regional_Director1 = Util.cellToString(Regional_Director); 
				String Asst_Vice_President1 = Util.cellToString(Asst_Vice_President); 
				String Vice_President1 = Util.cellToString(Vice_President); 
				String Sr_Vice_President1 = Util.cellToString(Sr_Vice_President); 
				String Adjustment1 = Util.cellToString(Adjustment); 
				String BV2003_1 = Util.cellToString(BV2003_); 
				String BV2004_1 = Util.cellToString(BV2004_); 
				String BV2005_1 = Util.cellToString(BV2005_); 
				String BV2006_1 = Util.cellToString(BV2006_); 
				String BV2007_1 = Util.cellToString(BV2007_); 
				String Addition_BV1 = Util.cellToString(Addition_BV); 
				String BV_Enough_To_CO1A1 = Util.cellToString(BV_Enough_To_CO1A); 
				String BV_Enough_To_CO21 = Util.cellToString(BV_Enough_To_CO2);
								
			
				PcReportDao rdao = new PcReportDao();
				int r = rdao.addTRregisList(Id, Direct_Team1, Direct_Leader1, Introducer1, Staff_Code1, DD_VP1, Join_Title1, Title1, Trainee1, Staff_Name1, Date_of_Group_Join1, Resign_Date1, TR_App_Date1, TR_Issue_Date1, LR_App_Date1, LR_Issue_Date1, LR_Normal_License1, IT_Completed_Date1, IT_Phase_Two1, Half_Consultant1, Consultant1, S_Consultant_11, S_Consultant_21, 
						Principal_Consultant1, Wealth_Manager1, S_Wealth_Manager1, S_Wealth_Manager_21, P_Wealth_Manager1, Associate1, Ass_Director1, Deputy_Director1, Regional_Director1, Asst_Vice_President1, Vice_President1, Sr_Vice_President1, Adjustment1, BV2003_1, BV2004_1, BV2005_1, BV2006_1, BV2007_1, Addition_BV1, BV_Enough_To_CO1A1, BV_Enough_To_CO21);
				if(r > 0)
				{
					++num;
					LOG.info("成功");
				}else
				{
					LOG.info("失败");
				}
				
			}
			return num;
		}
		/**
		 * 删除tr_r_list
		 * @return
		 */
		public static int DelTabTraining() {
			String sqlStr = "delete from pc_report";
			int num = 0;
			try {
				con = (Connection) DBManager.getCon();
				ps =  con.prepareStatement(sqlStr);
				num = ps.executeUpdate();
				LOG.info("删除tr_r_list num："+num);
			} catch (SQLException e) {
				LOG.error("删除tr_r_list 错误："+e);
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return num;
			
			
		}
	}
	 
