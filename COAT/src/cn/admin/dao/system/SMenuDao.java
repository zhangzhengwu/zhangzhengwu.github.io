package cn.admin.dao.system;

import java.util.List;

import util.Page;

import cn.admin.entity.system.SMenu;
import cn.admin.entity.system.SMenu_json;




public interface SMenuDao {
	/**
	 * 保存菜单
	 * 
	 * @param sMenu
	 * @return
	 */
	int saveMenu(SMenu sMenu);

	/**
	 * 保存菜单并附带赋予该菜单操作�?���?
	 * 
	 * @param sMenu
	 * @param userid
	 * @return
	 */
	int saveMenu(SMenu sMenu, int userid);

	/**
	 * 修改菜单
	 * 
	 * @param sMenu
	 * @return
	 */
	int modifyMenu(SMenu sMenu);

	/**
	 * 根据menuid删除菜单
	 * 
	 * @param menuid
	 * @param username
	 * @return
	 */
	int deleteMenu(int menuid, String username);

	/**
	 * 根据menuid查询Menu
	 * 
	 * @param menuid
	 * @return
	 */
	SMenu findMenuById(int menuid);

	/**
	 * 获取父菜单下拉列�?
	 * 
	 * @return
	 */
	List<String> findMenuList();

	/**
	 * 根据menuid获取子菜单个�?
	 * 
	 * @param menuid
	 * @return
	 */
	int findChildMenu(int menuid);

	/**
	 * 根据时间，menuname，parentMenuName查询分页条数
	 * 
	 * @param startDate
	 * @param endData
	 * @param menuname
	 * @param parentMenuname
	 * @return
	 */
	int findMenu(String startDate, String endData, String menuname,
			String parentMenuname);

	int findMenu_json(String startDate, String endData, String menuname,
			String parentMenuname);

	/**
	 * 根据时间，menuname，parentMenuName进行分页查询
	 * 
	 * @param startDate
	 * @param endData
	 * @param menuname
	 * @param parentMenuname
	 * @param page
	 * @return
	 */
	List<SMenu> findMenu(String startDate, String endData, String menuname,
			String parentMenuname, Page page);

	List<SMenu_json> findMenu_json(String startDate, String endData,
			String menuname, String parentMenuname, Page page);

}
