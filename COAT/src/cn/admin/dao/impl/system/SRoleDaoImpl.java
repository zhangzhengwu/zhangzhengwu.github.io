package cn.admin.dao.impl.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Help;
import util.Page;
import util.Util;
import cn.admin.dao.system.SRoleDao;
import cn.admin.entity.system.SGroup;
import cn.admin.entity.system.SRole;
import cn.admin.entity.system.SUser;

/**
 * 
 * 用户角色实现类 @author kingxu
 *
 */
public class SRoleDaoImpl implements SRoleDao {
	 Connection con = null;
	 PreparedStatement ps = null;
	 ResultSet rs=null;
	 String sql=null;
	 Logger logger = Logger.getLogger(SRoleDaoImpl.class);
	
	 
	 public int deleteRole(int roleid,String username) {
		int num=-1;
		 try{
			sql="update s_role set sfyx='D',modifier=?,modifyDate=? where roleid=?";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, DateUtils.getNowDateTime());
			ps.setInt(3, roleid);
			num=ps.executeUpdate();
			logger.info(DateUtils.getNowDateTime()+"===>"+username+"===>删除Role===>"+roleid);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"===>"+username+"===>删除Role时出现异常:"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	public List<SGroup> findGroupListByuserid(int userid) {
		List<SGroup> list=new ArrayList<SGroup>();
		try{
			sql="select g.*,r.isoption from s_role r left join s_group g on(r.groupid=g.groupid) where r.sfyx!='D' and g.sfyx!='D' and r.userid=? order by isoption desc";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setInt(1,userid);
			rs=ps.executeQuery();
			while(rs.next()){
				list.add(new SGroup(
						 rs.getInt("groupid"),
						 rs.getString("groupcode"),
						 rs.getString("groupname"),
						 rs.getString("groupfullname"),
						 rs.getString("remark"),
						 rs.getString("createname"),
						 rs.getString("createDate"),
						 rs.getString("modifier"),
						 rs.getString("modifyDate"),
						 rs.getString("sfyx"),rs.getString("isoption")));
			}
			rs.close();
		}catch (Exception e) {
			logger.error(DateUtils.getNowDateTime()+"===>根据userid查询groupList时出现异常"+e);
		}finally{
			DBManager.closeCon(con);
		}	 

		return list;
	}

	public int findRole(String startDate, String endDate, String rolename,
			Integer[] userid, Integer[] groupid) {
		int num=-1;
		try{
			sql="select count(*)as number from s_role where sfyx!='D' " +
					"and rolename like ? " +
					"and userid in (?) " +
					"and groupid in(?) " +
					"and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') " +
					"and DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') ";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1,"%"+rolename+"%");
			ps.setObject(2, userid);
			ps.setObject(3,groupid);
			ps.setString(4, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(5, Util.objIsNULL(endDate)?"2099-12-31":endDate);
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getInt("number");
			}
			rs.close();
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("根据时间，rolename，userid[],groupid[]查询分页条数时出现异常:"+e);
			}finally{
				DBManager.closeCon(con);
			}
		return num;
	}

	public List<SRole> findRole(String startDate, String endDate,
			String rolename, Integer[] userid, Integer[] groupid, Page page) {
		List<SRole> list=new ArrayList<SRole>();
		try{
			sql="select * from s_role where sfyx!='D' " +
					"and rolename like ? " +
					"and userid in (?) " +
					"and groupid in(?) " +
					"and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') " +
					"and DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') " +
					"order by createDate desc limit ?,? ";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1,"%"+rolename+"%");
			ps.setObject(2, userid);
			ps.setObject(3,groupid);
			ps.setString(4, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(5, Util.objIsNULL(endDate)?"2099-12-31":endDate);
			ps.setInt(6, (page.getCurPage()-1)*page.getPageSize());
			ps.setInt(7, page.getPageSize());
			rs=ps.executeQuery();
			while(rs.next()){
				list.add(new SRole(
						rs.getInt("roleid"),
						rs.getString("rolename"),
						rs.getInt("groupid"),
						rs.getInt("userid"),
						rs.getString("remark"),
						rs.getString("isoption"),
						rs.getString("createname"),
						rs.getString("createDate"),
						rs.getString("modifier"),
						rs.getString("modifyDate"),
						rs.getString("sfyx")));
			}
			rs.close();
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("根据时间，rolename，userid[],groupid[]分页查询时出现异常:"+e);
			}finally{
				DBManager.closeCon(con);
			}
		return list;
	}

	public List<SUser> findUserListBygroupId(int groupid) {
		List<SUser> list=new ArrayList<SUser>();
		try{
			sql="select g.* from s_role r left join s_user g on(r.userid=g.userid) where r.sfyx!='D' and g.sfyx!='D' and r.groupid=?";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setInt(1,groupid);
			rs=ps.executeQuery();
			while(rs.next()){
				list.add(new SUser(rs.getInt("userid"),
						rs.getString("loginname"),
						rs.getString("loginpass"),
						rs.getString("usercode"),
						rs.getString("truename"),
						rs.getString("englishname"),
						rs.getString("chinesename"),
						rs.getString("idcard"),
						rs.getString("sex"),
						rs.getString("birthday"),
						rs.getString("headimage"),
						rs.getString("registration"),
						rs.getString("address"),
						rs.getString("dept"),
						rs.getString("postion"),
						rs.getString("createname"),
						rs.getString("createdate"),
						rs.getString("modifier"),
						rs.getString("modifyDate"),
						rs.getString("sfyx")));
			}
			rs.close();
		}catch (Exception e) {
			logger.error(DateUtils.getNowDateTime()+"===>根据userid查询groupList时出现异常"+e);
		}finally{
			DBManager.closeCon(con);
		}	 

		return list;
	}

	public int modifyRole(SRole sRole) {
		int num=-1;
		try{
			//获取需要修改项
			String compare=Help.compareEntity(findRoleById(sRole.getRoleid()), sRole);
			sql="update s_role set "+compare.replaceAll("\\,", "=?,")+"=? where roleid=?";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			String[]param=(compare+",roleid").split(",");
			for(int i=0;i<param.length+1;i++){
				ps.setObject((i+1), sRole.getClass().getMethod("get"+ param[i].substring(0, 1).toUpperCase() + param[i].substring(1)).invoke(sRole));
			}
			num=ps.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("修改Role信息时出现:"+e.getMessage());
		}
		return num;
	}

	public int saveRole(SRole sRole) {
		int num=-1;
		try{
			sql="insert into s_role(rolename,groupid,userid,remark,isoption,createname,createDate,modifier,modifyDate,sfyx)" +
					"values(?,?,?,?,?,?,?,?,?,?)";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1,sRole.getRolename());
			ps.setInt(2,sRole.getGroupid());
			ps.setInt(3,sRole.getUserid());
			ps.setString(4,sRole.getRemark());
			ps.setString(5,sRole.getIsoption());
			ps.setString(6,sRole.getCreatename());
			ps.setString(7,sRole.getCreateDate());
			ps.setString(8,sRole.getModifier());
			ps.setString(9,sRole.getModifyDate());
			ps.setString(10,sRole.getSfyx());
			num=ps.executeUpdate();
			logger.info(DateUtils.getNowDateTime()+"===>"+sRole.getCreatename()+"===>保存用户角色===>"+sRole.getRolename());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"===>"+sRole.getCreatename()+"===>保存用户角色时出现异常:"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	public List<SRole> findRoleByuserId(int userid){
		List<SRole> list=new ArrayList<SRole>();
		try{
			sql="select * from s_role where sfyx!='D' and userid=? order by isoption desc";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			if(rs.next()){
				list.add(new SRole(
						rs.getInt("roleid"),
						rs.getString("rolename"),
						rs.getInt("groupid"),
						rs.getInt("userid"),
						rs.getString("remark"),
						rs.getString("isoption"),
						rs.getString("createname"),
						rs.getString("createDate"),
						rs.getString("modifier"),
						rs.getString("modifyDate"),
						rs.getString("sfyx")));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("通过userid查询用户信息时出现:"+e.getMessage());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	public SRole findRoleById(int roleid) {
		SRole sRole=null;
		try{
			sql="select * from s_role where sfyx!='D' and roleid=?";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setInt(1, roleid);
			rs=ps.executeQuery();
			if(rs.next()){
				sRole=new SRole(
						rs.getInt("roleid"),
						rs.getString("rolename"),
						rs.getInt("groupid"),
						rs.getInt("userid"),
						rs.getString("remark"),
						rs.getString("isoption"),
						rs.getString("createname"),
						rs.getString("createDate"),
						rs.getString("modifier"),
						rs.getString("modifyDate"),
						rs.getString("sfyx"));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("通过userid查询用户信息时出现:"+e.getMessage());
		}finally{
			DBManager.closeCon(con);
		}
		return sRole;
	}

}
