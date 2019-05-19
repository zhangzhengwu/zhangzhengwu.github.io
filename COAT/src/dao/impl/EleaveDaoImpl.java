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
import dao.EleaveConsDao;

public class EleaveDaoImpl implements EleaveConsDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(EleaveDaoImpl.class);
	/**
	 * 保存上传信息
	 */
	public int saveLeave(String filename, InputStream os,String username) {
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("e_leave"); 
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);
			sql = "insert e_leave values(?,?,?,'',?,'',?,'')";
			PreparedStatement pr=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet(Constant.LEAVE_SHEET);// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell StaffNocell = row.getCell(0);
				HSSFCell datecell =row.getCell(1);
				HSSFCell natureCell =row.getCell(2);
				HSSFCell remarkCell =row.getCell(6);
				
				/**给数据库里面的字段赋值**/
				String StaffNo = Util.cellToString(StaffNocell);
				String date  =Util.cellToString(datecell);
				String nature =Util.cellToString(natureCell);
				String remark =Util.cellToString(remarkCell);
			    if(!Util.objIsNULL(StaffNo)){
					//sql = "insert e_leave values('"+StaffNo+"','"+date+"','"+nature+"','','"+StaffNo+nature+"','','"+remark+"','')";
					
					pr.setString(1, StaffNo);
					pr.setString(2, date);
					pr.setString(3, nature);
					pr.setString(4, StaffNo+nature);
					pr.setString(5, remark);
					pr.addBatch();
					num++;
					
			    }
			}
			
			 	pr.executeBatch();
				logger.info(username+"EleaveDaoImpl.saveLeave()"+"插入e_leave表成功！");
				con.commit();
				pr.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(username+"EleaveDaoImpl.saveLeave()"+"插入e_leave表异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 该staffcode在指定日期是否请假
	 */
	public String isLeave(String staffcode, String date) {
		String nums="";
		 try{
			 con = DBManager.getCon();
			 sql = "select nature from e_leave where staffcode='"+staffcode+"' and leavedate='"+date+"'";
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=rs.getString("nature");
				}else{
					nums=null;
				}
				rs.close();
		 }catch(Exception e){
			 logger.error("查询e_leave表异常");
		 }finally{
			 DBManager.closeCon(con);
		 }
			return nums;
	}
 
}
 
