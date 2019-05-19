package com.coat.additionquota.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import util.Page;
import util.Pager;

import com.coat.additionquota.entity.CardquotaBean;
import com.coat.additionquota.entity.QueryAdditional;
import entity.RequestNewBean;

public interface AdditionalQuotaDao {
/*	*//**
	 * 
	 * @param StaffNo
	 * @param remark
	 * @return
	 *//*
	public QueryAdditional getAdditional(String StaffNo,String remark);*/
	
	/**
	 * 登录/退出记录 分页查询
	 * @param fields
	 * @param page
	 * @param objects
	 * @return
	 * @throws Exception 
	 */
	Pager findPager(String[] fields, Pager page, Object... objects) throws Exception;
	
	/**
	 * 
	 * @param StaffNo
	 * @param Additional
	 * @param Remarks
	 * @param sfyx
	 * @param re
	 * @return
	 * @throws SQLException 
	 */
	public int updateAdditional(String StaffNo,String Additional,String Remarks,String sfyx,String re) throws SQLException;
	/**
	 * 更新RequestNew，但不更新Quantity 普通权限
	 */
	public int updateAdditionals(RequestNewBean rnb);
	/***
	 * 更新requestNew 并更新Quantity 管理员权限
	 * @param rnb
	 * @return
	 */
	public int updateNumber(RequestNewBean rnb,String reStaffNo);


	/**
	 * 过滤重复数据
	 * @param staffcode
	 * @param remark
	 * @return
	 */
	public int selectRepeat(String staffcode,String remark);
	

/*	*//**
	 * 用于查询界面
	 * @return
	 *//*
	public List<QueryAdditional> getQueryAdditional();*/
	
	/**
	 * 保存
	 * @param qa
	 */
	public int saveLogin(QueryAdditional qa)throws SQLException;
	
/*	*//**
	 * 获取所有行数
	 * @param startDate
	 * @param endDate
	 * @return
	 *//*
	public int  getRows(String staffcode,String staffname);*/
	
	public Pager findCardQuotaList(String[] fields, Pager page, Object... objects)throws Exception;
	
/*	*//**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 *//*
	public List<CardquotaBean> queryCardQuotaList(String staffcode,String staffname,Page page);*/
/*	*//**
	 * ResultSet
	 * @return
	 * @throws SQLException 
	 *//*
	public ResultSet selectCardQuota(String staffcode,String staffname) throws SQLException;
	*/
	public List<Map<String,Object>> queryCardQuota(String staffcode,String staffname) throws SQLException;


}
