package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import dao.QueryNqAddDao;
import entity.NqAddBean;
/**
 * 查询NqAdd列表
 * @author Wilson.SHEN
 *
 */
public class QueryNqAddDaoImpl implements QueryNqAddDao {
	Logger logger = Logger.getLogger(QueryChargeDaoImpl.class);
	//查询nq_additional
	public List<NqAddBean> queryNqAddBean() {
		Connection connection=null;
		PreparedStatement  ps= null;
		List<NqAddBean> list = new ArrayList<NqAddBean>();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM nq_additional WHERE SFYX='Y' ";
			logger.info("查询nq_additional SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				NqAddBean nqAddBean = new NqAddBean();
				nqAddBean.setInitials(rs.getString(1));
				nqAddBean.setName(rs.getString(2));
				nqAddBean.setAdditional(rs.getString(3));
				nqAddBean.setRemarks(rs.getString(4));
				list.add(nqAddBean);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询nq_additional异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询nq_additional异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}

}
