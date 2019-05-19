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
import dao.EclubDao;

public class EclubDaoImpl implements EclubDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(EclubDaoImpl.class);
	/**
	 * 保存上传信息
	 */
	public int saveClub(String filename, InputStream os,String username) {
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("e_club"); 
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);
			sql = "insert e_club values(?,?,?)";
			PreparedStatement pr=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell StaffNocell = row.getCell(0);
				
				/**给数据库里面的字段赋值**/
				String StaffNo = Util.cellToString(StaffNocell);
			 
				//sql = "insert e_club values('"+StaffNo+"','"+username+"','"+DateUtils.getDateToday()+"')";
				pr.setString(1, StaffNo);
				pr.setString(2, username);
				pr.setString(3, DateUtils.getDateToday());
				pr.addBatch();
				num++;
			
			}
				pr.executeBatch();
				logger.info(username+"在EclubDaoImpl.saveclub()"+"插入e_club表成功！");
				con.commit();
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(username+"在EclubDaoImpl.saveclub()"+"插入e_club表异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	/***
	 * 是否为EClub的成员
	 */
	public int isClub(String staffcode) {
		int nums=-1;
		 try{
			 con = DBManager.getCon();
			 sql = "select * from e_club where staffcode='"+staffcode+"'";
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
			 logger.error("查询edate表异常");
		 }finally{
			 DBManager.closeCon(con);
		 }
			return nums;
	}
	
	/**
	 * 判断是否club成员
	 */
	public int sfClub(String staffcode) {
		int nums=-1;
		 try{
			 con = DBManager.getCon();
			 sql = "select * from c_club where staffcode='"+staffcode+"'";
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
			 logger.error("查询是否C-Club成员表异常");
		 }finally{
			 DBManager.closeCon(con);
		 }
			return nums;
	}
	/**
	 * 判断是否是c-club成员  
	 * @param staffcode
	 * @return
	 * @author kingxu
	 * 2015-6-8 14:37:30
	 */
	public int sfClubs(String staffcode) {
		int nums=-1;
		 try{
			 con = DBManager.getCon();
			 sql = "select * from c_club where staffcode='"+staffcode+"'";
			 sql="select count(*)as number from c_club_new where staffcode=? " +
			 		"and  DATE_FORMAT(?,'%Y-%m-%d')>=DATE_FORMAT(startdate,'%Y-%m-%d')"+ 
			 		"and DATE_FORMAT(?,'%Y-%m-%d')<=DATE_FORMAT(enddate,'%Y-%m-%d')";
				ps = con.prepareStatement(sql);
				ps.setString(1, staffcode);
				ps.setString(2, DateUtils.getDateToday());
				ps.setString(3, DateUtils.getDateToday());
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=rs.getInt("number");
				}else{
					nums=0;
				}
				rs.close();
		 }catch(Exception e){
			 logger.error("查询是否C-Club成员表异常");
		 }finally{
			 DBManager.closeCon(con);
		 }
			return nums;
	}
	
}
 
