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
import dao.EattendenceDao;

/**
 * Attendence
 * 
 * @author Wilson
 * 
 */
public class EattendenceDaoImpl implements EattendenceDao {

	Logger logger = Logger.getLogger(EattendenceDaoImpl.class);
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	/**
	 * 保存Attendence表
	 */
	public int saveAttendence(String filename, InputStream os) {
	
		int num = 0;
		int beginRowIndex = 8;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("e_attendence");
		/** 删除e_attendence表 * */
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);
			sql = "insert e_attendence(cardno,staffcode,staffname,entryDate,entryTime,entryMethod,occur,in_out,entrystatus,updname,upddate) values(?,?,?,?,?,?,?,?,?,'admin',?)";
			PreparedStatement pr=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行

				/** 获取Excel里面的指定单元格数据 下标从0开始* */
				HSSFCell cardcodecell = row.getCell(0);
				HSSFCell staffcodecell = row.getCell(1);
				HSSFCell nameCell = row.getCell(4);
				HSSFCell entrydateCell = row.getCell(6);
				HSSFCell entrytimeCell = row.getCell(7);
				HSSFCell methodCell = row.getCell(10);
				HSSFCell occurCell = row.getCell(12);
				HSSFCell inoutCell = row.getCell(13);
				HSSFCell statusCell = row.getCell(14);

				/** 给数据库里面的字段赋值* */
				String cardcode = Util.cellToString(cardcodecell);
				String staffcode  = Util.cellToString(staffcodecell);
				String name = Util.cellToString(nameCell);
				String entrydate  = Util.cellToString(entrydateCell);
				String entrytime  = Util.cellToString(entrytimeCell);
				String method = Util.cellToString(methodCell);
				String occur = Util.cellToString(occurCell);
				String inout = Util.cellToString(inoutCell);
				String status = Util.cellToString(statusCell);

				if (!Util.objIsNULL(staffcode)) {
					String cardcodeString =Util.objIsNULL(cardcode)?"123":cardcode;
				//	sql = "insert e_attendence values('"+cardcodeString+"','"+staffcode+"','"+name+"','"+entrydate+"','"+
					//entrytime+"','"+method+"','"+occur+"','"+inout+"','"+status+"','admin','"+DateUtils.getDateToday()+"')";
					pr.setString(1, cardcodeString);
					pr.setString(2, staffcode);
					pr.setString(3, name);
					pr.setString(4, entrydate);
					pr.setString(5, entrytime);
					pr.setString(6, method);
					pr.setString(7, occur);
					pr.setString(8,inout);
					pr.setString(9, status);
					pr.setString(10, DateUtils.getDateToday());
					pr.addBatch();
					if(i%1000==0){
						pr.executeBatch();
					}
					num++;
				} else {
					logger.info("插入e_attendence code is null！");
				}
			}
				pr.executeBatch();
				logger.info("插入e_attendence成功！");
				con.commit();
				pr.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入e_attendence异常！" + e);
			return num;
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
/**
 * 查询该staffcode在指定日期内是否打卡
 */
	public String queryEntryTime(String staffcode, String date) {
		String nums="";
		 try{
			 con = DBManager.getCon();
			 sql = "select * from e_attendence where staffcode='"+staffcode+"' and entryDate='"+date+"'";
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=rs.getString("entryTime");
				}else{
					nums=null;
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
