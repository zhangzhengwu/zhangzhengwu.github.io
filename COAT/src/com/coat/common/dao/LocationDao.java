package com.coat.common.dao;

import java.util.List;
import java.util.Map;

public interface LocationDao {
	/**
	 * 获取Location list
	 * @author kingxu
	 * @date 2015-11-2
	 * @return
	 * @return List<Map>
	 * @throws Exception 
	 */
	List<Map<String,Object>> getlocation() throws Exception;
	/**
	 * 获取macau location list
	 * @author kingxu
	 * @date 2015-11-2
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws Exception 
	 */
	public List<Map<String, Object>> getmacaulocation() throws Exception;
}
