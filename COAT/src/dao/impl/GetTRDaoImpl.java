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

import dao.GetTRDao;
/**
 * GetTRDao 实现类
 * @author King.XU
 *
 */
public class GetTRDaoImpl implements GetTRDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(GetTRDaoImpl.class);
	/**
	 * 保存上传信息
	 */
	public int saveTR(String filename, InputStream os) {
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
				HSSFCell LRNoCell=row.getCell(22);//2014-5-30 19:55:47 King 新增
				HSSFCell Ce_Nocell=row.getCell(22);
				HSSFCell MPFcell=row.getCell(45);
				/**给数据库里面的字段赋值**/
				String StaffNo = Util.cellToString(StaffNocell);
				String TrRegNo=Util.cellToString(TrRegNocell);
				String Ce_No=Util.cellToString(Ce_Nocell);
				String MPF=Util.cellToString(MPFcell);
				String  LR=Util.cellToString(LRNoCell);//2014-5-30 19:55:47 King 新增
				 
				if(!Util.objIsNULL(StaffNo)&& StaffNo.length()>4){
				//	System.out.println(Ce_No+"=="+TrRegNo+"==="+MPF);
					if(Ce_No.toUpperCase().indexOf(("De reg").toUpperCase())>-1||Ce_No.toUpperCase().indexOf(("withdrawal").toUpperCase())>-1||Ce_No.toUpperCase().indexOf(("Pending").toUpperCase())>-1||Ce_No.toUpperCase().indexOf(("N/A").toUpperCase())>-1||Ce_No.indexOf("Suspenion")>-1||Ce_No.toUpperCase().indexOf(("Lasped").toUpperCase())>-1||Ce_No.toUpperCase().indexOf(("De-reg").toUpperCase())>-1||Ce_No.toUpperCase().indexOf(("w/d").toUpperCase())>-1){
						Ce_No="";
					}
					if(TrRegNo.toUpperCase().indexOf(("De reg").toUpperCase())>-1||TrRegNo.toUpperCase().indexOf(("withdrawal").toUpperCase())>-1||TrRegNo.toUpperCase().indexOf(("Pending").toUpperCase())>-1||TrRegNo.toUpperCase().indexOf(("N/A").toUpperCase())>-1||TrRegNo.indexOf("Suspenion")>-1||TrRegNo.toUpperCase().indexOf(("Lasped").toUpperCase())>-1||TrRegNo.toUpperCase().indexOf(("De-reg").toUpperCase())>-1||TrRegNo.toUpperCase().indexOf(("w/d").toUpperCase())>-1){
						TrRegNo="";
					}
					if(MPF.toUpperCase().indexOf(("De reg").toUpperCase())>-1||MPF.toUpperCase().indexOf(("withdrawal").toUpperCase())>-1||MPF.toUpperCase().indexOf(("Pending").toUpperCase())>-1||MPF.toUpperCase().indexOf(("N/A").toUpperCase())>-1||MPF.indexOf("Suspenion")>-1||MPF.toUpperCase().indexOf(("Lasped").toUpperCase())>-1||MPF.toUpperCase().indexOf(("De-reg").toUpperCase())>-1||MPF.toUpperCase().indexOf(("w/d").toUpperCase())>-1){
						MPF="";
					}
					if(LR.toUpperCase().indexOf(("De reg").toUpperCase())>-1||LR.toUpperCase().indexOf(("withdrawal").toUpperCase())>-1||LR.toUpperCase().indexOf(("Pending").toUpperCase())>-1||LR.toUpperCase().indexOf(("N/A").toUpperCase())>-1||LR.indexOf("Suspenion")>-1||LR.toUpperCase().indexOf(("Lasped").toUpperCase())>-1||LR.toUpperCase().indexOf(("De-reg").toUpperCase())>-1||LR.toUpperCase().indexOf(("w/d").toUpperCase())>-1){
						LR="";
					}
					
					
					try{
					
						sql = "insert tr values('"+StaffNo+"','"+TrRegNo+"','"+Ce_No+"','"+LR+"','"+MPF+"')";
						ps = con.prepareStatement(sql);
						int rsNum = ps.executeUpdate();
						if (rsNum > 0) {
							//logger.info("插入tr表成功！");
							num++;
						} else {
							logger.info("插入tr表失敗");
						}
					
					}catch (Exception e) {
						logger.info(StaffNo+"--->存在重复顾问编号，未上传.");
					}
					
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入tr表异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
}