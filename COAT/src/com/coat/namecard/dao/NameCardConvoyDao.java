package com.coat.namecard.dao;

import java.sql.SQLException;
import java.util.List;

import com.coat.namecard.entity.NameCardConvoyDetial;

import entity.RequestStaffConvoyDetial;

import util.Pager;

public interface NameCardConvoyDao {
	
	/**
	 * 查询顾问自己名片办理记录
	 * @author kingxu
	 * @date 2015-10-10
	 * @param fields
	 * @param page
	 * @param ET
	 * @param nocode
	 * @param objects
	 * @return
	 * @throws Exception
	 * @return Pager
	 */
	Pager findPager(String[] fields, Pager page,Object... objects) throws Exception;
	
	/**
	 * 查询状态信息
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	Pager queryStateDetail(String[] fields, Pager page, Object... objects) throws Exception;
	
	 /**
 	 * 批量保存订单申请
 	 * @param list
 	 * @return
 	 */
 	public String uploadNameCard(List<List<Object>> list,String user)throws Exception;
 	
 	public List<NameCardConvoyDetial> findNameCardDetail(String staffrefno) throws SQLException;
}
