package cn.admin.dao.system;

import java.util.List;

import util.Page;

import cn.admin.entity.system.SGroup;
import cn.admin.entity.system.SRole;
import cn.admin.entity.system.SUser;


/**
 *
 *用户角色接口 @author kingxu
 *
 */
public interface SRoleDao {
	/**
	 * 保存用户角色
	 * @param sRole
	 * @return
	 */
	int saveRole(SRole sRole);
	
	/**
	 * 修改用户角色
	 * @param sRole
	 * @return
	 */
	int modifyRole(SRole sRole);
	/**
	 * 根据groupid查询对应用户列表
	 * @param groupid
	 * @return
	 */
	List<SUser> findUserListBygroupId(int groupid);
	/**
	 * 根据userid查询对应的组列表
	 * @param userid
	 * @return
	 */
	List<SGroup> findGroupListByuserid(int userid);
	/**
	 * 根据时间，rolename，userid,groupid查询分页条数
	 * @param startDate
	 * @param endDate
	 * @param rolename
	 * @param userid
	 * @param groupid
	 * @return
	 */
	int findRole(String startDate,String endDate,String rolename, Integer[] userid,Integer[] groupid);
	/**
	 * 根据时间，rolename，userid,groupid分页查询
	 * @param startDate
	 * @param endDate
	 * @param rolename
	 * @param userid
	 * @param groupid
	 * @param page
	 * @return
	 */
	List<SRole> findRole (String startDate,String endDate,String rolename, Integer[] userid,Integer[] groupid,Page page);
	
	/**
	 *
	 * @param roleid
	 * @return
	 */
	int deleteRole(int roleid,String username); 
	/**
	 * 根据RoleId查询Role对象
	 * @param roleid
	 * @return
	 */
	SRole findRoleById(int roleid);
	
	
	/**
	 * 根据userid获取已有权限列表
	 * @author kingxu
	 * @date 2015-12-28
	 * @param userid
	 * @return
	 * @return List<SRole>
	 */
	List<SRole> findRoleByuserId(int userid);
	
	
	
}
