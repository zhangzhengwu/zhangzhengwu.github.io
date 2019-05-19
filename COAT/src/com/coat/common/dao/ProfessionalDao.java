package com.coat.common.dao;

import java.util.List;
import java.util.Map;

public interface ProfessionalDao {
	/**
	 * 获取professional list
	 * @author kingxu
	 * @date 2015-11-3
	 * @return
	 * @throws Exception
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> getProfessional() throws Exception;
	
	/**
	 * 获取staff professional list
	 * @author kingxu
	 * @date 2015-11-3
	 * @return
	 * @throws Exception
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> getStaffProfessional() throws Exception;
}
