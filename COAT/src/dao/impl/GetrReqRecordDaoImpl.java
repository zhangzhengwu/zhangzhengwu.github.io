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
import dao.GetrReqRecordDao;
/**
 * ReqRecordDao 实现类
 * @author Wilson.SHEN
 *
 */
public class GetrReqRecordDaoImpl implements GetrReqRecordDao {
	Logger logger = Logger.getLogger(GetrReqRecordDaoImpl.class);
	/**
	 * 保存req_record表
	 */
	public int saveReqRecord(String filename, InputStream os) {
		PreparedStatement ps = null;
		String sql = "";
		Connection con = null;
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("req_record");		 /**删除req_record表 **/
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet(Constant.QEQ_QECORD_SHEET);//读取Sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				@SuppressWarnings("unused")
				HSSFCell idscell = row.getCell(0);
				HSSFCell request_datescell = row.getCell(1);
				HSSFCell codecell=row.getCell(2);
				HSSFCell namecell=row.getCell(3);
				HSSFCell departmencell=row.getCell(4);
				HSSFCell quantitycell=row.getCell(5);
				/**给数据库里面的字段赋值**/
				String request_dates  = Util.cellToString(request_datescell);
				String code=Util.cellToString(codecell);
				String name =Util.cellToString(namecell);
				String departmen =Util.cellToString(departmencell);
				String quantity =Util.cellToString(quantitycell);
				if(!Util.objIsNULL(code)){
					sql = "insert req_record  values('"+request_dates +"','"+code +"','"+name +"','"+departmen +"','"+quantity+"','admin','S','N')";
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入req_record成功！");
						num++;
					} else {
						logger.info("插入req_record失敗");
					}
				}else {
					logger.info("插入req_record code is null！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入req_record异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}

}
