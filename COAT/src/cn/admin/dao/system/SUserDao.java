package cn.admin.dao.system;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import util.Page;
import cn.admin.entity.system.SUser;
import cn.admin.entity.system.SUser_Login;



/**
 * 
 *用户接口 @author kingxu
 * 
 */
public interface SUserDao {
	/**
	 * 更新用户的头像
	 * 
	 * @param userid
	 * @param path
	 * @return
	 */
	int UpdateHeadImg(Integer userid, String path);

	/**
	 * 登录验证
	 * 
	 * @param adminUsernmae
	 * @param adminPassword
	 * @return
	 */
	boolean checkLogin_AES_ENCRYPT(String adminUsername, String adminPassword);

	/**
	 * 登录验证 (not AES_ENCRYPT)
	 * 
	 * @param adminUsernmae
	 * @param adminPassword
	 * @return
	 */
	List<SUser_Login> checkLogin(String adminUsername, String adminPassword);

	/**
	 * 根据登录名称,用户编号，用户英文名，用户中文名，用户创建时间,是否有效进行分页查询
	 * 
	 * @param loginname
	 * @param usercode
	 * @param englishname
	 * @param chinesename
	 * @param startDate
	 * @param endDate
	 * @param sfyx
	 * @param page
	 * @return
	 */
	List<SUser> findUser(String loginname, String usercode, String englishname,
			String chinesename, String startDate, String endDate, String sfyx,String dept,
			Page page);

	/**
	 * 根据登录名称,用户编号，用户英文名，用户中文名，用户创建时间，是否有效进行查询分页条数
	 * 
	 * @param loginname
	 * @param usercode
	 * @param englishname
	 * @param chinesename
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int findUserRows(String loginname, String usercode, String englishname,
			String chinesename, String startDate, String endDate, String sfyx,String dept);

	/**
	 * 保存用户
	 * 
	 * @param sUser
	 * @return
	 */
	int saveUser(SUser sUser);

	/**
	 * 事务操作添加用户的功能，（添加用户以及添加Group）
	 * 
	 * @param sUser
	 * @param groupidList
	 * @param user
	 *            The login user
	 * @return
	 * @throws SQLException 
	 * @throws Exception 
	 */
	String saveUserAndsaveGroup(SUser sUser, String[] groupidList, String user) throws SQLException, Exception;

	/**
	 * 修改用户
	 * 
	 * @param sUser
	 * @return
	 * @throws Exception 
	 */
	String modifyUser(SUser sUser,String[] group) throws Exception;

	/**
	 * 移除用户
	 * 
	 * @param userid
	 * @param modifier
	 * @return
	 */
	int RemoveUser(int userid, String modifier);

	/**
	 * 修改密码
	 * 
	 * @param loginname
	 * @param newpassword
	 * @return
	 */
	int modifyPassword(String loginname, String newpassword);

	/**
	 * 验证原密码
	 * 
	 * @param loginname
	 * @param password
	 * @return
	 */
	boolean VailPassword(String loginname, String password);

	/**
	 * 根据userid查询用户信息
	 * 
	 * @param userid
	 * @return
	 */
	SUser findUserById(int userid);

	/**
	 * 根据用户登录名查询用户信息
	 * 
	 * @param loginname
	 * @return
	 */
	SUser findUserByloginname(String loginname);
	
	
	/**
	 * 根据用户名密码验证用户是否存在
	 * @author kingxu
	 * @date 2015-12-4
	 * @param adminUsername
	 * @param adminPassword
	 * @return
	 * @return Map<String,Object>
	 */
	Map<String,Object> checklogin(String adminUsername,
			String adminPassword);


	/**
	 * 根据用户名和原密码修改成新密码
	 * @param username
	 * @param nowpassword
	 * @param newpassword
	 * @return
	 * @throws Exception 
	 */
	String changePassword(String username,String nowpassword,String newpassword) throws Exception;
}
