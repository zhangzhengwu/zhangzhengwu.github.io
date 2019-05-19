package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import dao.QueryProfessionalDao;
import entity.Professional_title;

/**
 * 查询QueryProfessional列表
 * @author King.XU
 *
 */
public class QueryProfessionalDaoImpl implements QueryProfessionalDao {
	Connection connection=null;
	PreparedStatement  ps= null;
	Logger logger = Logger.getLogger(QueryProfessionalDaoImpl.class);
	/**
	 * queryProfessional
	 */
	public List<Professional_title> queryProfessional() {
		Connection connection=null;
		PreparedStatement  ps= null;
		List<Professional_title> list = new ArrayList<Professional_title>();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM professional_title WHERE SFYX='Y' ";
			logger.info("查询Professional SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Professional_title peoBean = new Professional_title();
				peoBean.setId(rs.getString(1));
				peoBean.setProf_title_ename(rs.getString(2));
				peoBean.setProf_title_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
				list.add(peoBean);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询Professional 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询Professional 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}

	//根据名称查询
	public Professional_title queryProfessional(String professional_title) {

		Professional_title peoBean = new Professional_title();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM professional_title WHERE SFYX='Y' and prof_title_ename='"+professional_title+"'";
			
			logger.info("根据名称查询professional SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peoBean.setId(rs.getString(1));
				peoBean.setProf_title_ename(rs.getString(2));
				peoBean.setProf_title_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("根据名称查询professional异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("根据名称查询professional异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return peoBean;
	}
	
}
