package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import dao.QueryStaffProfessionalDao;
import entity.Professional_title_Staff;

/**
 * QueryStaffProfessionalDao 实现类
 * @author Wilson.SHEN
 *
 */
public class QueryStaffProfessionalDaoImpl implements QueryStaffProfessionalDao {
	Connection connection=null;
	PreparedStatement  ps= null;
	Logger logger = Logger.getLogger(QueryStaffProfessionalDaoImpl.class);
	//查询所有 返回list
	public List<Professional_title_Staff> queryProfessional() {
		
		List<Professional_title_Staff> list = new ArrayList<Professional_title_Staff>();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM professional_title_staff WHERE SFYX='Y' ";
			logger.info("professional SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Professional_title_Staff peoBean = new Professional_title_Staff();
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
			logger.info("professional 查询异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("professional 查询异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	} 
	//根据name查询
	public Professional_title_Staff queryStaffProfessional(String professional_title) {

		Professional_title_Staff peoBean = new Professional_title_Staff();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM professional_title_staff WHERE SFYX='Y' and prof_title_ename='"+professional_title+"'";
			logger.info("professional by "+professional_title+" SQL:"+sqlString);
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
			logger.info("professional by "+professional_title+" 查询异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("professional by "+professional_title+" 查询异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return peoBean;
	}
	
	
	
	
	
	
}
