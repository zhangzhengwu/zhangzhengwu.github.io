package cn.admin.dao.system;

import java.util.List;

import net.sf.json.JSONArray;

import cn.admin.entity.system.SGroupmenu_Param;
import cn.admin.entity.system.SMenurole;
import cn.admin.entity.system.SUsermenu;




/**
 * 
 * 菜单权限接口 @author kingxu
 *
 */
public interface SMenuRoleDao {
	/**
	 * 批量保存菜单权限
	 * @param menuRole菜单权限以数组的形式
	 * @return
	 */
	int saveMenuRoleList(String[] menuRole,String username);
	/**
	 * 保存单个菜单权限
	 * @param sMenurole
	 * @return
	 */
	int saveMenuRole(SMenurole sMenurole);
	/**
	 * 通过Roleid获取菜单权限
	 * @param roleid
	 * @return
	 */
	List<String[]> findMenuRoleByRoleid(int roleid);
	
	/**
	 * 通过menuId获取菜单权限 
	 * @param menuid
	 * @return
	 */
	public List<String[]> findMenuRoleByMenuid(int menuid);
	/**
	 * 通过menuid获取当前菜单操作权限
	 * @param roleid
	 * @param menuid
	 * @return
	 */
	SMenurole findMenuRoleBymenuId(int roleid,int menuid);
	/**
	 * 通过唯一ID找到这个记录
	 * @param menuroleid
	 * @return
	 */
	List<SGroupmenu_Param> findItemByMenuRoleId(int menuroleid);
	
	/**
	 * 添加�?��记录
	 * @param obj
	 * @param modifyUser
	 * @return
	 */
	int addItem(SGroupmenu_Param obj,String modifyUser);
	
	/**
	 * 通过�?��menuroleid修改内容
	 * @param obj
	 * @return
	 */
	int modifyMenuRoleByMenuRoleid(SGroupmenu_Param obj);
	/**
	 * 根据页面提供的权限列表保存数据
	 * @author kingxu
	 * @date 2015-12-23
	 * @param list
	 * @param username
	 * @return
	 * @throws Exception
	 * @return String
	 */
	String saveMenuRole(List<SGroupmenu_Param> list,String username) throws Exception;
	
	/**
	 * 保存添加权限组和添加权限个人
	 * @param menuid
	 * @param groupJsonArray
	 * @param personJsonArray
	 * @param username
	 * @return
	 * @throws Exception
	 */
    String saveRoleMenu(String menuid,JSONArray groupJsonArray,JSONArray personJsonArray,String username) throws Exception;
    
    /**
     * 保存权限组
     * @param sMenurole
     * @return
     */
	int saveGroupMenuRole(SMenurole sMenurole);
	
	/**
	 * 保存权限个人
	 * @param sUsermenu
	 * @return
	 */
	int savePersonMenuRole(SUsermenu sUsermenu);
	
	/**
	 * 判断是否已存在该个人
	 * @param menuid
	 * @param personid
	 * @return
	 */
	int existPerson(String menuid,String personid);
	/**
	 * 判断人是否合法
	 * @param personid
	 * @return
	 */
	int effectivePerson(String personid);
}
