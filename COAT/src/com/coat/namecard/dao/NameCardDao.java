package com.coat.namecard.dao;

import java.sql.SQLException;
import java.util.List;

import util.Pager;

import com.coat.namecard.entity.Consultant_Master;
import com.coat.namecard.entity.NameCardConvoy;
import com.coat.namecard.entity.RequestNewBean;

import entity.LicensePlate;
import entity.RequestStaffBean;


public interface NameCardDao {

	
	
	/**
	 * 查询名片申请记录
	 * @author kingxu
	 * @date 2015-9-28
	 * @param fields
	 * @param page
	 * @param ET
	 * @param nocode
	 * @param objects
	 * @return
	 * @throws Exception
	 * @return Pager
	 */
	Pager findPager(String[] fields, Pager page,String ET, String nocode,Object... objects) throws Exception;
	/**
	 * 根据staffcode查询历史最新办理记录(fax,mobilephone,num获取历史记录 其余获取最新个人信息)
	 * @author kingxu
	 * @date 2015-9-28
	 * @param staffcode
	 * @return
	 * @throws Exception
	 * @return Consultant_Master
	 */
	Consultant_Master findbystaffcode(String staffcode) throws Exception;
	
	
	/**
	 * 保存顾问提交的名片申请
	 * @author kingxu
	 * @date 2015-9-15
	 * @param rnb
	 * @return
	 * @return String
	 * @throws SQLException 
	 */
	public String  saveNewRequest(NameCardConvoy rnb,String username) throws SQLException;
	public String  getLocation(String staffcode) throws SQLException;
	/**
	 * 保存名片申请
	 * @author kingxu
	 * @date 2015-9-23
	 * @param rnb
	 * @param username
	 * @param used 已印刷名片数量
	 * @param sum 可免费印刷名片数量
	 * @return
	 * @return String
	 * @throws SQLException 
	 */
	public String saveNameCardRequest(RequestNewBean rnb,String username,double used,double sum) throws SQLException;
	
	
	
	/**
	 * 修改名片办理记录
	 * @author kingxu
	 * @date 2015-9-29
	 * @param rnb
	 * @param reStaffNo
	 * @param rePayer
	 * @param reUrgent
	 * @param used
	 * @param sum
	 * @return
	 * @throws SQLException
	 * @return String
	 */
		public String updateNameCard(RequestNewBean rnb,String reStaffNo,String rePayer,String reUrgent,double used,double sum) throws SQLException;
	/**
	 * 根据staffcode获取englishname
	 * @author kingxu
	 * @date 2015-10-9
	 * @param staffcode
	 * @return
	 * @return String
	 * @throws SQLException 
	 */
		String getEnglishNameByCode(String staffcode) throws SQLException;
		
		/**
		 * SZO ADM 审核 NameCardRequest
		 * @author kingxu
		 * @date 2015-10-13
		 * @param rnb
		 * @param username
		 * @param used
		 * @param sum
		 * @return
		 * @throws SQLException
		 * @return String
		 */
		String approveNameCardRequest(RequestNewBean rnb, String username,double used,double sum) throws SQLException;
		/**
		 * 查询request_staff上一次的申请记录
		 * @return
		 * @throws SQLException
		 */
		List<RequestStaffBean> getOldRecord(String staffcode)throws SQLException;
		
		LicensePlate getHKCIB(String staffcode) throws SQLException;
}
