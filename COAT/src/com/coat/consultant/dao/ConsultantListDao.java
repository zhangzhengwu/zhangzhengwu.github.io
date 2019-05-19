package com.coat.consultant.dao;

import java.sql.SQLException;
import java.util.List;

import com.coat.consultant.entity.ConsultantList;


public interface ConsultantListDao {

	/**
	 * 从HK SZO_ADM view获取最新在职顾问列表
	 * @author kingxu
	 * @date 2015-12-24
	 * @return
	 * @return List<Consultant_List>
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public List<ConsultantList> findConsultantByHKView() throws Exception;
	/**
	 * 批量保存上传的Consultant数据
	 * @author kingxu
	 * @date 2015-12-24
	 * @param os
	 * @param username
	 * @return
	 * @return int
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public int batchSaveConsultant(List<ConsultantList> list,String username) throws Exception;
	/**
	 * 根据staffcode查询Consultant
	 * @author kingxu
	 * @date 2016-2-16
	 * @param staffcode
	 * @return
	 * @return ConsultantList
	 * @throws SQLException 
	 */
	public ConsultantList findConsultantByCode(String staffcode) throws SQLException;
}
