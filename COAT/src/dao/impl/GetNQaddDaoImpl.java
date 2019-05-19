package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DBManager;
import util.Util;

import dao.GetNQaddDao;
/**
 * GetNQaddDao 实现类
 * @author King.XU
 *
 */
public class GetNQaddDaoImpl implements GetNQaddDao {
	Logger logger = Logger.getLogger(GetNQaddDaoImpl.class);
	/**
	 * 保存上传信息方法
	 * 2015-12-24 14:49:00(King)  新增事务控制，将循环单条保存改为将所有数据汇总统一保存
	 */
	public int saveNQadd(String filename, InputStream os,String add_name) {
			PreparedStatement ps = null;
			String sql = "";
			Connection con = null;
			int num=0;
			int beginRowIndex = 5;// 开始读取数据的行数
			int totalRows = 0;// 总行数
			try {
				con = DBManager.getCon();
				con.setAutoCommit(false);
				sql = "insert nq_additional(initials,name,additional,remarks,add_user,add_date,upd_user,upd_date,sfyx) values(?,?,?,?,'"+add_name+"',"+"NOW() "+",'',null,'Y')";
				ps = con.prepareStatement(sql);
				
				HSSFWorkbook workbook = new HSSFWorkbook(os);
				HSSFSheet sheet = workbook.getSheetAt(0);// 獲取頁數
				totalRows = sheet.getLastRowNum();// 获取总行数
				for (int i = beginRowIndex; i <= totalRows; i++) {
					HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
					/**获取Excel里面的指定单元格数据**/
					HSSFCell initialscell = row.getCell(0);
					HSSFCell Namecell=row.getCell(1);
					HSSFCell additionalcell=row.getCell(2);
					HSSFCell remarkscell=row.getCell(3);
					/**给数据库里面的字段赋值**/
					String initials  = Util.cellToString(initialscell);
					String Name=Util.cellToString(Namecell);
					String additional =Util.cellToString(additionalcell);
					String remarks =Util.cellToString(remarkscell);
					if(!Util.objIsNULL(initials)){
						ps.setString(1, initials);
						ps.setString(2, Name);
						ps.setInt(3, Integer.parseInt(additional));
						ps.setString(4, remarks);
						ps.addBatch();
					}
				}
				
				//执行插入动作
				num=cacularNum(ps.executeBatch());
				if (num > 0) {
					logger.info("插入nq_additional["+num+"]成功！");
				} else {
					logger.info("插入nq_additional失敗");
				}
				con.commit();
				con.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("插入nq_additional异常！"+e);
			} finally {
				//关闭连接
				DBManager.closeCon(con);
			}
			return num;
		}

	
	int cacularNum(int[] num){
		int n=0;
		try{
			if(!Util.objIsNULL(num)){
				for(int nums:num){
					if(nums>0){
						n++;
					}
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace(); 
		}
		return n;
	}
	
}

 
