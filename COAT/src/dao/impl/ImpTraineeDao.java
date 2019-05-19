package dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DBManager;
import util.Util;
import dao.GetTrainingDao;

public class ImpTraineeDao {
	/**
	 * 导入Excel表格 training
	 * 
	 * @param filename
	 * @param os
	 * @throws IOException
	 */
	public static int InputExcel(String filename, InputStream os)
			throws IOException {
		/**
		 * 先删除表中数据 再导入
		 */
		delTraining();
		// 读取导入的文件
	//	String path = filename.replaceAll("\\\\", "/").substring(
			//	filename.replaceAll("\\\\", "/").lastIndexOf("/") + 1);
		//String titlename = path.replaceAll(".xls", "");
		//System.out.println(titlename);
		// 根据文件的输入流，创建对Excel 工作薄文件的引用
		HSSFWorkbook workbook = new HSSFWorkbook(os);
		int num = save(workbook);
		return num;
	}
	@SuppressWarnings("deprecation")
	public static int save(HSSFWorkbook workbook){
		int beginRowIndex = 3;// 从excel 中开始读取的起始行数
		int totalRows = 0;// 从excel 表的总行数
		int num = 0;
		// 默认exce的书页是“sheet1”
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 得到该excel 表的总行数
		totalRows = sheet.getLastRowNum();
		//System.out.println("总行数是" + totalRows);
		// 循环读取excel表格的每行记录，并逐行进行保存
		
		for (int i = beginRowIndex; i <= totalRows; i++) {
			HSSFRow row = sheet.getRow(i);
			// 获取一行每列的数据
			HSSFCell codecell = row.getCell((short) 0);
			HSSFCell staffcoseCell = row.getCell((short) 1);
			HSSFCell aliascell = row.getCell((short) 2);
			HSSFCell namecell = row.getCell((short) 3);
			HSSFCell teamcell = row.getCell((short) 4);
			HSSFCell completedDatecell = row.getCell((short) 5);
			//System.out.println(completedDatecell);
			HSSFCell twocell = row.getCell((short) 6);
			HSSFCell day1_AMcell = row.getCell((short) 7);
			HSSFCell day1_PMcell = row.getCell((short) 8);
			HSSFCell day2_AMcell = row.getCell((short) 9);
			HSSFCell day2_PMcell = row.getCell((short) 10);
			HSSFCell day3_AMcell = row.getCell((short) 11);
			HSSFCell day3_PMcell = row.getCell((short) 12);
			HSSFCell day4_AMcell = row.getCell((short) 13);
			HSSFCell day4_PMcell = row.getCell((short) 14);
			HSSFCell day5_AMcell = row.getCell((short) 15);
			HSSFCell day5_PMcell = row.getCell((short) 16);
			HSSFCell day6_AMcell = row.getCell((short) 17);
			HSSFCell day6_PMcell = row.getCell((short) 18);
			HSSFCell day7_AMcell = row.getCell((short) 19);
			HSSFCell day7_PMcell = row.getCell((short) 20);
			HSSFCell FollowUp_Training_AMcell= row.getCell((short) 21);
			// 将数据赋给相关的变量
			if(!Util.objIsNULL(cellToString(staffcoseCell))){
				String id = (num+1) + "";
				String code =cellToString(codecell);// code
				String staffcose1 = cellToString(staffcoseCell);// staff Code
				String alias = cellToString(aliascell).replace("'", "‘");// Alias
				String name = cellToString(namecell);// Name
				String team = cellToString(teamcell);// Team
									//String completedDate = cellToString(completedDatecell);
		
				String twoTraining = cellToString(twocell);
				String day1_AM=Util.cellToString(day1_AMcell);
				String day1_PM=Util.cellToString(day1_PMcell);
				String day2_AM=Util.cellToString(day2_AMcell);
				String day2_PM=Util.cellToString(day2_PMcell);
				String day3_AM=Util.cellToString(day3_AMcell);
				String day3_PM=Util.cellToString(day3_PMcell);
				String day4_AM=Util.cellToString(day4_AMcell);
				String day4_PM=Util.cellToString(day4_PMcell);
				String day5_AM=Util.cellToString(day5_AMcell);
				String day5_PM=Util.cellToString(day5_PMcell);
				String day6_AM=Util.cellToString(day6_AMcell);
				String day6_PM=Util.cellToString(day6_PMcell);
				String day7_AM=Util.cellToString(day7_AMcell);
				String day7_PM=Util.cellToString(day7_PMcell);
				String FollowUp_Training_AM=Util.cellToString(FollowUp_Training_AMcell);
				String completedDate=Util.cellToString(completedDatecell);//暂且存放
		
				int r = GetTrainingDao.addTraining(id ,code,staffcose1,alias,name,team,completedDate,twoTraining,day1_AM,day1_PM,day2_AM,
						day2_PM,day3_AM,day3_PM,day4_AM,day4_PM,day5_AM,day5_PM,day6_AM,day6_PM,day7_AM,day7_PM,FollowUp_Training_AM); // StuDao.addStu(stuid,
				if (r > 0) {
					++num;
					//System.out.println("成功");
				} else {
					System.out.println("失败");
				}
			}
		}
		return num;
	}
	
	//遍历excel中的数据
	public static String cellToString(HSSFCell cell) {
		String cellvalue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {// 如果当前cell的type为date类型
					if(cell.getDateCellValue().equals("")){
						cellvalue="";
					}else{
					SimpleDateFormat formater = new SimpleDateFormat(
							"yyyy-MM-dd");
					cellvalue = formater.format(cell.getDateCellValue());
					}
					
				} else {// 如果当前cell的type是数字
					Integer num = new Integer((int) cell.getNumericCellValue());
					cellvalue = String.valueOf(num);
				}
				break;
			case HSSFCell.CELL_TYPE_STRING:// 如果当前cell的type为字符串
				cellvalue = cell.getStringCellValue();
				break;
			default:// 既不为data类型也不为String
				cellvalue = "";

			}
		} else {// cell单元格对象为空
			cellvalue="";
		}
	return cellvalue;
	}

	public static void delTraining() {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from training";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			int num = ps.executeUpdate();

			//System.out.println("=============num=============" + num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
