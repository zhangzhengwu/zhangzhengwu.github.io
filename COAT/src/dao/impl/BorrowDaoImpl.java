package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constant;
import util.DBManager;
import util.Util;

import dao.BorrowDao;

public class BorrowDaoImpl implements BorrowDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(GetTRDaoImpl.class);
	/**
	 * 保存上传信息
	 */
	public int saveBorrow(String filename, InputStream os,String username) {
		int num=0;
		int beginRowIndex = 8;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("TR");/*           等待填写                      */
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet(Constant.TR_LIST_SHEET);// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell StaffNocell = row.getCell(1);
				HSSFCell TrRegNocell=row.getCell(11);
				HSSFCell Ce_Nocell=row.getCell(22);
				HSSFCell MPFcell=row.getCell(45);
				/**给数据库里面的字段赋值**/
				String StaffNo = Util.cellToString(StaffNocell);
				String TrRegNo=Util.cellToString(TrRegNocell);
				String Ce_No=Util.cellToString(Ce_Nocell);
				String MPF=Util.cellToString(MPFcell);
				sql = "insert tr values('"+StaffNo+"','"+TrRegNo+"','"+Ce_No+"','"+MPF+"')";
				ps = con.prepareStatement(sql);
				int rsNum = ps.executeUpdate();
				if (rsNum > 0) {
					logger.info(username+"在BorrowDaoImpl.saveBorrow()"+"插入Borrow表成功！");
					num++;
				} else {
					logger.info(username+"在BorrowDaoImpl.saveBorrow()"+"插入Borrow表失敗");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(username+"在BorrowDaoImpl.saveBorrow()"+"插入Borrow表异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
}
 
