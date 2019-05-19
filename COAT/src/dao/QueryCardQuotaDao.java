package dao;

import java.sql.ResultSet;
import java.util.List;

import util.Page;
import entity.CardquotaBean;
/**
 * 查询CardQuota列表 DAO类
 * @author wilson
 *
 */
public interface QueryCardQuotaDao {
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<CardquotaBean> queryCardQuotaList(String staffcode,String staffname,Page page);
	/**
	 * 获取所有行数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int  getRows(String staffcode,String staffname);
	
	/**
	 * ResultSet
	 * @return
	 */
	public ResultSet selectCardQuota(String staffcode,String staffname);
}
