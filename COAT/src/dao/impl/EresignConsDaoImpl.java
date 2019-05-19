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
import util.Util;
import dao.EresignConsDao;

/**
 * EresignCons
 * 
 * @author Wilson
 * 
 */
public class EresignConsDaoImpl implements EresignConsDao {

	Logger logger = Logger.getLogger(EresignConsDaoImpl.class);
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	/**
	 * 保存e_resignlist表
	 */
	public int saveResignCons(String filename, InputStream os) {
		int num = 0;
		int beginRowIndex = 1;// 开始读取数据的行数  改成从第一行读取
		int totalRows = 0;// 总行数
		Util.deltables("e_resignlist");
		/** 删除e_resignlist表 * */
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);
			sql = "insert e_resignlist values(?,?,?,?,?,?)";
			PreparedStatement pr=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行

				/** 获取Excel里面的指定单元格数据 下标从0开始* */
				HSSFCell staffcodecell = row.getCell(0);
				HSSFCell nametcell = row.getCell(1);
				HSSFCell recruitercell = row.getCell(2);
				HSSFCell positioncell = row.getCell(3);
				HSSFCell pnamecell = row.getCell(4);
				HSSFCell aliascell = row.getCell(5);

				/** 给数据库里面的字段赋值* */
				String staffcode = Util.cellToString(staffcodecell);
				String name = Util.cellToString(nametcell);
				String recruiter = Util.cellToString(recruitercell);
				String position = Util.cellToString(positioncell);
				String pname = Util.cellToString(pnamecell);
				String alias = Util.cellToString(aliascell);

				if (!Util.objIsNULL(staffcode) /*&& !Util.objIsNULL(name)*/) {
					//sql = "insert e_resignlist values('"+staffcode+"','"+name+"','"+recruiter+"','"+position+"','"+pname+"','"+alias+"')";
					pr.setString(1, staffcode);
					pr.setString(2, name);
					pr.setString(3, recruiter);
					pr.setString(4, position);
					pr.setString(5, pname);
					pr.setString(6, alias);
					pr.addBatch();
					num++;
					
					
					
			
				} else {
					logger.info("插入e_resignlist code is null！");
				}
			}
			pr.executeBatch();
			logger.info("插入e_resignlist成功！");
			con.commit();
			pr.close();
		} catch (Exception e) {
			logger.error("插入e_resignlist异常！" + e);
			return num;
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
/**
 * 查看该staffcode是否离职
 */
	public int isResignList(String staffcode) {
		int nums=-1;
	 try{
		 con = DBManager.getCon();
		 sql = "select * from e_resignlist where staffcode='"+staffcode+"'";
			ps = con.prepareStatement(sql);
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				nums=1;
			}else{
				nums=0;
			}
			
	 }catch(Exception e){
		 e.printStackTrace();
		 logger.error("查询resign顾问离职表异常"+e.toString());
		 return nums;
		 
	 }finally{
		 DBManager.closeCon(con);
	 }
		return nums;
	}

}
