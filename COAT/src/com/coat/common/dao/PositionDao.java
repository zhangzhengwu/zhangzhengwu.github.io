package com.coat.common.dao;

import java.util.List;
import java.util.Map;

public interface PositionDao {
	/**
	 * 获取职位列表
	 * @author kingxu
	 * @date 2015-11-2
	 * @return
	 * @throws Exception
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> getposition() throws Exception;
}
