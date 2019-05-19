package cn.admin.dao.system;

import java.util.List;
import java.util.Map;

import cn.admin.entity.system.SGroupRole_mix;
import cn.admin.entity.system.SMenuBasic;
import cn.admin.entity.system.SMenuRole_mix;
import cn.admin.entity.system.SUsermenu;
import cn.admin.entity.system.SUsermenu_Param;


/**
 * 
 *用户权限接口实现类 @author kingxu
 * 
 */
public interface SUsermenuDao {
	/**
	 * 批量保存菜单权限
	 * 
	 * @param userMenu菜单权限以数组的形式
	 * @return
	 */
	int saveUserMenuList(String[] userMenu, String username);

	/**
	 * 保存单个菜单权限
	 * 
	 * @param sUsermenu
	 * @param username
	 * @return
	 */
	int saveUserMenu(SUsermenu sUsermenu, String username);

	/**
	 * 通过userid获取菜单权限
	 * 
	 * @param userid
	 * @return
	 */
	List<String[]> findUserMenuByUserid(int userid);

	/**
	 * 通过menuid获取菜单权限
	 * 
	 * @param menuid
	 * @return
	 */
	List<String[]> findUserMenuByMenuid(int menuid);

	/**
	 * 通过menuid获取菜单权限
	 * 
	 * @param menuid
	 * @return
	 */
	List<SUsermenu_Param> findUserMenuByMenuid2(int menuid);

	/**
	 * 修改usermenu的记录通过menuid
	 * 
	 * @param susermenu
	 * @return
	 */
	int modifyUserMenuByMenuid(SUsermenu_Param susermenu);



	/**
	 * 获取所有的菜单数据包含父子级的
	 * 
	 * @param userid
	 * @return
	 */

	List<SMenuRole_mix> findAllUserMenu(Integer userid);

	/**
	 * 添加一个记录
	 * @param su
	 * @param username
	 * @return
	 */
	int addItem(SUsermenu_Param su,String username);

	/**
	 * 获取所有的菜单数据包含父子级的
	 * 
	 * @param userid
	 * @return
	 */

	List<SMenuRole_mix> findAllMenu(Integer userid,Integer groupid);
	
	
	/**
	 * 根据userid获取菜单权限列表
	 * @author kingxu
	 * @date 2015-12-4
	 * @param userid
	 * @return
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> findMenuRoleByUserId(int userid);

	/**
	 * 根据userid,menuid获取单个菜单权限列表
	 * @author orlandozhang
	 * @param userid
	 * @param menuid
	 * @return 
	 */
	Map<String,Object> findSingleMenuRoleByUserId(int userid,int menuid);
	
	/**
	 * 根据用户id,menuid查询菜单连接
	 * @author kingxu
	 * @date 2015-12-8
	 * @param userid
	 * @param menuid
	 * @return
	 * @return String
	 */
	SMenuBasic findMenubyusermenu(int userid,int menuid);
	/**
	 * 根据userid查询菜单权限列表
	 * @author kingxu
	 * @date 2015-12-23
	 * @param userid
	 * @return
	 * @return List<SGroupRole_mix>
	 */
	List<SGroupRole_mix> findMenuRoleClassByUserId(String userid);
	
	/**
	 * 根据userid查询菜单权限，menuid为key，SGroupRole_mix为value
	 * @author kingxu
	 * @date 2015-12-23
	 * @param userid
	 * @return
	 * @return Map<Integer,Object>
	 */
	Map<Integer,Object> findMenuRoleMapByUserId(String userid);	
	
	
	/**
	 * 根据页面提供的用户权限列表保存数据
	 * @author kingxu
	 * @date 2015-12-23
	 * @param list
	 * @return
	 * @return String
	 * @throws Exception 
	 */
	public String saveUserMenuRole(List<SUsermenu_Param> list,String username) throws Exception;
	
	

}
