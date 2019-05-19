package dao;

import java.util.List;
import util.Page;
import entity.Admin;

public interface AdminDao02 {

	/**
	 * 根据条件查询Cons_List
	 * @param employeeId
	 * @param c_Name
	 * @param HKID
	 * @param RecruiterId
	 * @return
	 */
	public List<Admin> queryReqeustList(String adminUsername,Page page);

	/**
	 * 根据记录编号删除某个用户信息
	 * @param name
	 * @return
	 */
	public int DelAdminInfo(String adminUsername);
	
	
	/**
	 * 判断是否相同用户名
	 * @param name
	 * @return
	 */
	public  int getIsUserName(String adminUsername);
	
	/**
	 * 得到某个用户的权限
	 * @param adminUsername
	 * @return
	 */
	public  int getIsRoot(String adminUsername);
	
	/**
	 * 更改用户密码
	 * @param admin
	 * @return
	 */
	public  boolean ChangePassword(Admin admin); 
	
	/**
	 * 添加功能
	 * @param location
	 * @return
	 */
	public boolean AddAdmin(Admin admin);
	
	/**
	 * 获取行数
	 * @param employeeId
	 * @param c_Name
	 * @param HKID
	 * @param recruiterId
	 * @return
	 */
	public int getRows(String adminUsername);
	
	/**
	 * 验证当前密码是否相等
	 * @param adminUsernmae
	 * @param adminPassword
	 * @return
	 */
	public boolean checkLogin_AES_ENCRYPT(String adminUsername,String adminPassword);
	
	/**
	 * 更改密码
	 * @param admin
	 * @return
	 */
	public boolean changePassword(Admin admin);
	/**
	 * 编辑
	 * @param isRoot
	 * @return
	 */
	public String edit(Admin admin);
	
}
