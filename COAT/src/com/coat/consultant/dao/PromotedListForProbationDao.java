package com.coat.consultant.dao;

import java.sql.SQLException;
import java.util.List;

import com.coat.consultant.entity.PromotedListforprobation;


public interface PromotedListForProbationDao {

	/**
	 * 从HK SZO_ADM view获取最新的顾问晋升列表
	 * @author orlando
	 * @date 2018-01-30
	 * @return
	 * @return List<PromotedList>
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public List<PromotedListforprobation> findPromotedListByHKView() throws Exception;

}
