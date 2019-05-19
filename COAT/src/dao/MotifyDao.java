package dao;

import entity.QueryAdditional;
import entity.RequestNewBean;
/**
 * 
 * @author kingxu
 *
 */
public interface MotifyDao {
	/**
	 * 
	 * @param StaffNo
	 * @param remark
	 * @return
	 */
	public QueryAdditional getAdditional(String StaffNo,String remark);
	/**
	 * 
	 * @param StaffNo
	 * @param Additional
	 * @param Remarks
	 * @param sfyx
	 * @param re
	 * @return
	 */
	public int updateAdditional(String StaffNo,String Additional,String Remarks,String sfyx,String re);
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


/****
 * 添加RequestNew
 */
	public int  saveNewRequest(RequestNewBean rnb,String reStaffNo);
	/**
	 * 过滤重复数据
	 * @param staffcode
	 * @param remark
	 * @return
	 */
	public int selectRepeat(String staffcode,String remark);
}
