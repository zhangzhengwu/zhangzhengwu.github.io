package dao;

import java.util.List;

import entity.TrRegNo;
/**
 * 查询tr列表 DAO类
 * @author wilson
 *
 */
public interface QueryTRDao {
	/**
	 * TrRegNo
	 * @return
	 */
	public List<TrRegNo> queryTR();
}
