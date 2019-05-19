package cn.admin.dao.system;

import java.util.List;

import util.Page;

import cn.admin.entity.system.SGroup;
import cn.admin.entity.system.SGroupRole_mix;




/**
 * 
 * 组接�?@author kingxu
 * 
 */
public interface SGroupDao {
	/**
	 * 根据groupid查询Group信息
	 * 
	 * @param groupid
	 * @return
	 */
	SGroup findGroupById(int groupid);

	/**
	 * 根据groupcode查询Group信息
	 * 
	 * @param groupcode
	 * @return
	 */
	SGroup findGroupByGroupcode(String groupcode);

	/**
	 * 根据groupcode,groupname,sfyx,createDate查询分页条数
	 * 
	 * @param startDate
	 * @param endDate
	 * @param groupcode
	 * @param groupname
	 * @param sfyx
	 * @return
	 */
	int findGroupRows(String startDate, String endDate, String groupcode,
			String groupname, String sfyx);

	/**
	 * 根据groupcode,groupname,sfyx,createDate进行分页查询
	 * 
	 * @param startDate
	 * @param endDate
	 * @param groupcode
	 * @param groupname
	 * @param sfyx
	 * @param page
	 * @return
	 */
	List<SGroup> findGroup(String startDate, String endDate, String groupcode,
			String groupname, String sfyx, Page page);

	/**
	 * 根据groupid，获取这个id有的菜单
	 * 
	 * @param groupid
	 * @return
	 */
	List<SGroupRole_mix> findAllGroupMenuByGroupid(Integer groupid);

	/**
	 * 根据groupid，获取这个id有的菜单
	 * 
	 * @param groupid
	 * @return
	 */
	List<SGroup> findAllGroupMenu();

	/**
	 * 保存组信�?
	 * 
	 * @param sGroup
	 * @return
	 */
	int saveGroup(SGroup sGroup);

	/**
	 * 修改组信�?
	 * 
	 * @param sGroup
	 * @return
	 */
	int modifyGroup(SGroup sGroup);

	/**
	 * 删除组信�?
	 * 
	 * @param groupid
	 * @param username
	 * @return
	 */
	int removeGroup(int groupid, String username);

	/**
	 * 判断�?��用户是否存在这个组别记录
	 * */
	boolean checkGroupIsAuthorized(Integer groupid, Integer userid);

	/**
	 * 更新用户的默认展示组�?
	 * */
	int updateRoleIsoption(Integer groupid, Integer userid, String isoption);
	
    /**
     * 根据menuid查找符合要求的Group
     * @param menuid
     * @return
     */
	List<String[]> findGroupList(String menuid);
	/**
	 * 根据menuid查找符合要求的Person
	 * @param menuid
	 * @return
	 */
	List<String[]> findPersonList(String menuid);
	/**
	 * 判断添加组是否重复
	 * @param menuid
	 * @param groupid
	 * @return
	 */
	int existGroup(String menuid,String groupid);
	/**
	 * 判断Group是否有效
	 * @param groupid
	 * @return -1 无效
	 *          1 有效
	 */
	int effectiveGroup(String groupid);
}
