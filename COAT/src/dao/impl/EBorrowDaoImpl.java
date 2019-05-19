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

import util.Constant;
import util.DBManager;
import util.Util;

import dao.EBorrowDao;

public class EBorrowDaoImpl implements EBorrowDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(EBorrowDaoImpl.class);
	/**
	 * 保存上传信息
	 */
	public int saveBorrow(String filename, InputStream os,String username) {
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("e_borrow");/*           等待填写                      */
		
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);
			sql = "insert e_borrow values(?,?,?)";
			PreparedStatement pr=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet(Constant.BORROW_SHEET);// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell StaffNocell = row.getCell(0);
				HSSFCell borrowcell=row.getCell(1);
				HSSFCell lccell=row.getCell(2);
				
				/**给数据库里面的字段赋值**/
				String StaffNo = Util.cellToString(StaffNocell);
				String borrow =Util.cellToString(borrowcell);
				String lc=Util.cellToString(lccell);
			 if(!Util.objIsNULL(StaffNo)&&!Util.objIsNULL(borrow)&&!Util.objIsNULL(lc)){
				//sql = "insert e_borrow values('"+StaffNo+"','"+borrow+"','"+lc+"')";
				pr.setString(1, StaffNo);
				pr.setString(2, borrow);
				pr.setString(3, lc);
				pr.addBatch();
				num++;
			 }
			 
				
			}
				pr.executeBatch();
				logger.info(username+"在BorrowDaoImpl.saveBorrow()"+"插入Borrow表成功！");
				con.commit();
				pr.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(username+"在BorrowDaoImpl.saveBorrow()"+"插入Borrow表异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 查询该staffcode在指定日期内是否有借卡记录
	 */
	public int isBorrow(String staffcode, String date) {
		int nums=-1;
		 try{
			 con = DBManager.getCon();
			 sql = "select * from e_borrow where staffcode='"+staffcode+"' and borrowdate='"+date+"'";
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
			 logger.error("查询resign表异常");
			 
		 }finally{
			 DBManager.closeCon(con);
		 }
			return nums;
		}
}
 
