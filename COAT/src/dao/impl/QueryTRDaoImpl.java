package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;
import dao.QueryTRDao;
import entity.TrRegNo;

/**
 * 查询Tr列表
 * @author wilson
 *
 */
public class QueryTRDaoImpl implements QueryTRDao {
	/**
	 * queryTR
	 */
	public List<TrRegNo> queryTR() {
		Connection  conn = null;
		List<TrRegNo> list = new ArrayList<TrRegNo>();
		try {
			conn= DBManager.getCon();
			PreparedStatement pStatement= null;
			String sql = "select * from tr limit 0,100";
			pStatement = conn.prepareStatement(sql);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next())
			{
				TrRegNo trNo = new TrRegNo();
				trNo.setStaffNo(rs.getString(1));
				trNo.setTr_RegNO(rs.getString(2));
				trNo.setCe_NO(rs.getString(3));
				trNo.setMpf_NO(rs.getString(4)); 
				list.add(trNo);
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return list;
	}
}
