package com.coat.consultant.dao;

import java.sql.SQLException;
import java.util.List;

import com.coat.consultant.entity.PromotedList;


public interface PromotedListDao {

	/**
	 * 从HK SZO_ADM view获取最新的顾问晋升列表
	 * @author orlando
	 * @date 2018-01-30
	 * @return
	 * @return List<PromotedList>
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public List<PromotedList> findPromotedListByHKView() throws Exception;
	/**
	 * 查询因晋升需要调整的名片数量
	 * @author orlando
	 * @date 2018-01-30
	 * @return
	 * @return List<PromotedList>
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public List<PromotedList> findNewAdditional() throws Exception;

}
