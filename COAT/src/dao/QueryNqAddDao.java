package dao;

import java.util.List;

import entity.NqAddBean;
/**
 * 查询NqAdd列表 DAO类
 * @author wilson
 *
 */
public interface QueryNqAddDao {
	/**
	 * NqAddBean
	 * @return
	 */
	public List<NqAddBean> queryNqAddBean();
}
