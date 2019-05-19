package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import dao.QueryPositionDao;
import entity.Position_list;
/**
 * 查询position_list列表
 * @author King.XU
 *
 */
public class QueryPositionDaoImpl implements QueryPositionDao {
	Connection connection=null;
	PreparedStatement  ps= null;
	Logger logger = Logger.getLogger(QueryPositionDaoImpl.class);
	/**
	 * 查询
	 */
	public List<Position_list> queryPosition() {
		
		List<Position_list> list = new ArrayList<Position_list>();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM position_list WHERE SFYX='Y'";
			logger.info(" 查询 position_list SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Position_list peoBean = new Position_list();
				peoBean.setId(rs.getString(1));
				peoBean.setPosition_ename(rs.getString(2));
				peoBean.setPosition_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
				list.add(peoBean);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询 position_list异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询 position_list异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		
		return list;
	
	}
	/**
	 * 查询 position_list 根据名称
	 */
	public Position_list queryPosition(String position_ename) {

		Position_list peoBean = new Position_list();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM position_list WHERE SFYX='Y' and position_ename= ? ";
			logger.info("查询 position_list by a "+position_ename+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ps.setString(1,position_ename.trim());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peoBean.setId(rs.getString(1));
				peoBean.setPosition_ename(rs.getString(2));
				peoBean.setPosition_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询 position_list异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询 position_list异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return peoBean;
	}
	public List<String> queryPositionEName() {
		List<String> list = new ArrayList<String>();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT position_ename FROM position_list WHERE SFYX='Y'";
			logger.info(" 查询 position_Ename SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("position_ename"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询 position_Ename异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询 position_Ename异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		
		return list;
	}
}
