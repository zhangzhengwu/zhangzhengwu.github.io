package com.coat.business.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coat.business.entity.C_EPaymentList;

import dao.common.BaseDao;
import dao.impl.QueryRequstDaoImpl;

import util.Pager;
import util.Util;

public class EPaymentListDaoImpl extends BaseDao implements EPaymentListDao {
	Logger logger = Logger.getLogger(QueryRequstDaoImpl.class);

	public Pager findAll(String procode, String proname, String sfyx, Pager page)
			throws SQLException {
		String sql = "from c_epayment_list where productcode like ? and productname like ? and sfyx like ?";
		try {
			String limit = " limit ?,?";
			page = findPager(null, sql, limit, page,
					Util.modifyString(procode), Util.modifyString(proname),
					Util.modifyString(sfyx));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("select epaymentlist error");
		} finally {
			closeConnection();
		}
		return page;
	}

	public int save(C_EPaymentList epl) throws SQLException {
		int r = -1;
		String sql = "insert into c_epayment_list(productcode,productname,price,quantity,unit,adddate,addname,sfyx,remark)values(?,?,?,?,?,?,?,?,?)";
		try {
			r = saveEntity(sql, epl.getProductcode(), epl.getProductname(),
					epl.getPrice(), epl.getQuantity(), epl.getUnit(),
					epl.getAdddate(), epl.getAddname(), epl.getSfyx(),
					epl.getRemark());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("insert epaymentlist error");
		} finally {
			closeConnection();
		}
		return r;
	}

	public int updateEPL(C_EPaymentList epl) throws SQLException {
		int r = -1;
		String sql = "update c_epayment_list set productcode=?,productname=?,price=?,quantity=?,unit=?,sfyx=?,remark=? where id=? ";
		try {
			r = update(sql,
					new Object[] { epl.getProductcode(), epl.getProductname(),
							epl.getPrice(), epl.getQuantity(), epl.getUnit(),
							epl.getSfyx(), epl.getRemark(), epl.getId() });
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("update epaymentlist error");
		} finally {
			closeConnection();
		}
		return r;
	}

	public int delete(int id) throws SQLException {
		int r = -1;
		try {
			String sql = "update c_epayment_list set sfyx='N' where id=? and sfyx='Y'";
			r = update(sql, new Object[]{id});
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("delete epaymentlist error");
		} finally {
			closeConnection();
		}
		return r;
	}

	public List<Map<String, Object>> findAll(String procode, String proname,
			String sfyx) throws SQLException {
		List<Map<String, Object>> lists = null;
		String sql = "select *,if(sfyx='Y','有效','无效') as status from c_epayment_list where productcode like ? and productname like ? and sfyx like ?";
		try {
			lists = listMap(sql, Util.modifyString(procode),
					Util.modifyString(proname), Util.modifyString(sfyx));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("select epaymentlist error");
		}
		return lists;
	}

	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, C_EPaymentList.class);
	}
}
