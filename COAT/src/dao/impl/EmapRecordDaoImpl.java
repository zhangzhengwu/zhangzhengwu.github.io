package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DBManager;
import util.DateUtils;
import util.Util;
import dao.EmapRecordDao;

/**
 * EmapRecord
 * 
 * @author Wilson
 * 
 */
public class EmapRecordDaoImpl implements EmapRecordDao {

	Logger logger = Logger.getLogger(GetrMedicalDaoImpl.class);
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	/**
	 * 保存emap表
	 */
	public int saveEmap(String filename, InputStream os) {

		int num = 0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("emap");
		/** 删除Emap表 * */
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);
			sql = "insert emap(staffcode,meetingdate,updname,upddate) values(?,?,?,?);";
			PreparedStatement pr=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			   DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			   DateFormat dfs = new SimpleDateFormat("dd-MM-yyyy");
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行

				/** 获取Excel里面的指定单元格数据 下标从0开始* */
				HSSFCell staffcodecell = row.getCell(0);
				HSSFCell meetcell = row.getCell(1);

				/** 给数据库里面的字段赋值* */
				String staffcode = Util.cellToString(staffcodecell);
				String meet = Util.cellToString(meetcell);
				
				if (!Util.objIsNULL(staffcode)) {
					if(!Util.objIsNULL(meet)&&meet.substring(0,meet.indexOf("-")).length()<=2){
						meet=df.format(dfs.parse(meet));
					}
					//sql = "insert emap values('"+staffcode+"','"+meet+"','admin','"+DateUtils.getDateToday()+"')";
					pr.setString(1, staffcode);
					pr.setString(2, meet);
					pr.setString(3,"admin");
					pr.setString(4,DateUtils.getDateToday() );
					pr.addBatch();
					if(i%500==0){
						pr.executeBatch();
					}
					num++;
				} else {
					logger.info("插入emap code is null！");
				}
			}
				pr.executeBatch();
				logger.info("插入emap成功！");
				con.commit();
				pr.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入emap异常！" + e);
			return num;
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
/**
 * 该staffcode在指定日期内是否有meeting
 */
	public int isMap(String staffcode, String date) {
		int nums=-1;
		 try{
			 con = DBManager.getCon();
			 sql = "select * from emap where staffcode='"+staffcode+"' and meetingdate='"+date+"'";
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
			 logger.error("查询emap表异常");
		 }finally{
			 DBManager.closeCon(con);
		 }
			return nums;
	}
}
