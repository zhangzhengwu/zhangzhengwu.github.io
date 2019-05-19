package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import util.DBManager;
import dao.DelorUpdNqDao;
/**
 * 删除or修改DelorUpdNqDaoImpl
 * @author Wilson.SHEN
 *
 */
public class DelorUpdNqDaoImpl implements DelorUpdNqDao {
	Logger logger = Logger.getLogger(DelorUpdNqDaoImpl.class);
	/**
	 * 
	 */
	public int delNqAddBean(String cinsid) {  //TODE: 参数需要补充
		Connection  conn = null;
		int num = 0;
		try {
			conn= DBManager.getCon();
			PreparedStatement pStatement= null;
			
			String sql = "delete from nq_additional where initials='"+cinsid+"'"; //TODE: 新增 or 删除  修改   
 
			logger.info("删除nq_additional —— SQL:"+sql);
			pStatement = conn.prepareStatement(sql);
			num  = pStatement.executeUpdate();
		 
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("删除nq_additional异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("删除nq_additional异常！"+e);
		}finally{
			//关闭连接
			DBManager.closeCon(conn);
		}
		
		return num;
	}

}
