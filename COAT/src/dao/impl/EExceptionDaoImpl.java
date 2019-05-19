package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DBManager;
import util.DateUtils;
import util.Util;
import dao.EExceptionDao;

/**
 * Exception
 * @author Wilson
 * 
 */
public class EExceptionDaoImpl implements EExceptionDao {

	Logger logger = Logger.getLogger(EExceptionDaoImpl.class);
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	/**
	 * 保存e_exceptdate表
	 */
	public int saveException(String filename, InputStream os) {
		
		int num = 0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("e_exceptdate");
		/** 删除e_exceptdate表 * */
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);
			sql = "insert e_exceptdate values(?,?,?,?,?,?)";
			PreparedStatement pr=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行

				/** 获取Excel里面的指定单元格数据 下标从0开始* */
				HSSFCell datecell = row.getCell(0);
				HSSFCell staffcodecell = row.getCell(1);
				HSSFCell eventscell = row.getCell(2);

				/** 给数据库里面的字段赋值* */
				String date = Util.cellToString(datecell);
				String staffcode = Util.cellToString(staffcodecell);
				String events = Util.cellToString(eventscell);

				if (!Util.objIsNULL(staffcode)) {
					pr.setString(1, date);
					pr.setString(2,staffcode );
					pr.setString(3, events);
					pr.setString(4, "admin");
					pr.setString(5, DateUtils.getDateToday());
					pr.setString(6, "Y");
					pr.addBatch();
					num++;
					
				} else {
					logger.info("插入e_exceptdate code is null！");
				}
			}
				pr.executeBatch();
				logger.info("插入e_exceptdate成功！");
				con.commit();
				pr.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入e_exceptdate异常！" + e);
			return num;
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
/**
 * 是否在指定时间为假期
 */
	public int isholiday(String date) {
		int nums=-1;
		 try{
			 con = DBManager.getCon();
			 sql = "select * from e_exceptdate where edate='"+date+"'";
			 
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=1;
				}else{
					nums=0;
				}
				 rs.close();
		 }catch(Exception e){
			 logger.error("查询e_exceptdate表异常"+date);
			 e.printStackTrace();
		 }finally{
		
			 DBManager.closeCon(con);
		 }
			return nums;
	}
	 
}
