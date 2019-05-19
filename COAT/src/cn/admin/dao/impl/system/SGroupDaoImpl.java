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
import cn.admin.dao.system.SGroupDao;
import cn.admin.entity.system.SGroup;
import cn.admin.entity.system.SGroupRole_mix;




/**
 * 
 * 组实现类 @author kingxu
 * 
 */
public class SGroupDaoImpl implements SGroupDao {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;
	Logger logger = Logger.getLogger(SGroupDaoImpl.class);

	public int findGroupRows(String startDate, String endDate,
			String groupcode, String groupname, String sfyx) {
		int num = -1;
		try {
			sql = "select count(*)as number from s_group where sfyx!='D' "
					+ "and groupcode like ? "
					+ "and groupname like ? "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and sfyx like ? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + groupcode + "%");
			ps.setString(2, "%" + groupname + "%");
			ps.setString(3, Util.objIsNULL(startDate) ? "1999-01-01"
					: startDate);
			ps.setString(4, Util.objIsNULL(endDate) ? "2099-12-31" : endDate);
			ps.setString(5, "%" + sfyx + "%");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				num = rs.getInt("number");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据groupcode,groupname,sfyx,createDate进行查询分页条数时出现异常:"
					+ e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public List<SGroup> findGroup(String startDate, String endDate,
			String groupcode, String groupname, String sfyx, Page page) {
		List<SGroup> list = new ArrayList<SGroup>();
		try {
			sql = "select * from s_group where sfyx!='D' "
					+ "and groupcode like ? "
					+ "and groupname like ? "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and sfyx like ? "
					+ "order by createDate desc limit ?,? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + groupcode + "%");
			ps.setString(2, "%" + groupname + "%");
			ps.setString(3, Util.objIsNULL(startDate) ? "1999-01-01"
					: startDate);
			ps.setString(4, Util.objIsNULL(endDate) ? "2099-12-31" : endDate);
			ps.setString(5, "%" + sfyx + "%");
			ps.setInt(6, (page.getCurPage() - 1) * page.getPageSize());
			ps.setInt(7, page.getPageSize());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new SGroup(rs.getInt("groupid"), rs
						.getString("groupcode"), rs.getString("groupname"), rs
						.getString("groupfullname"), rs.getString("remark"), rs
						.getString("createname"), rs.getString("createDate"),
						rs.getString("modifier"), rs.getString("modifyDate"),
						rs.getString("sfyx")));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据groupcode,groupname,sfyx,createDate进行分页查询时出现异常:"
					+ e);
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	public SGroup findGroupByGroupcode(String groupcode) {
		SGroup sGroup = null;
		try {
			sql = "select * from s_group where sfyx!='D' and groupcode =? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, groupcode);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sGroup = new SGroup(rs.getInt("groupid"), rs
						.getString("groupcode"), rs.getString("groupname"), rs
						.getString("groupfullname"), rs.getString("remark"), rs
						.getString("createname"), rs.getString("createDate"),
						rs.getString("modifier"), rs.getString("modifyDate"),
						rs.getString("sfyx"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据groupcode查询Group信息时出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return sGroup;
	}

	public SGroup findGroupById(int groupid) {
		SGroup sGroup = null;
		try {
			sql = "select * from s_group where sfyx!='D' and groupid =? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, groupid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sGroup = new SGroup(rs.getInt("groupid"), rs
						.getString("groupcode"), rs.getString("groupname"), rs
						.getString("groupfullname"), rs.getString("remark"), rs
						.getString("createname"), rs.getString("createDate"),
						rs.getString("modifier"), rs.getString("modifyDate"),
						rs.getString("sfyx"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据groupid查询Group信息时出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return sGroup;
	}

	/**
	 * 修改组信息
	 */
	public int modifyGroup(SGroup sGroup) {
		int num = -1;
		try {
			// 获取需要修改项
			String compare = Help.compareEntity(findGroupById(sGroup
					.getGroupid()), sGroup);
			sql = "update s_group set " + compare.replaceAll("\\,", "=?,")
					+ "=? where groupid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			String[] param = (compare + ",groupid").split(",");
			for (int i = 0; i < param.length; i++) {
				ps.setObject((i + 1), sGroup.getClass().getMethod(
						"get" + param[i].substring(0, 1).toUpperCase()
								+ param[i].substring(1)).invoke(sGroup));
			}
			num = ps.executeUpdate();
			logger.info(DateUtils.getNowDateTime() + "===>"
					+ sGroup.getModifier() + "===>修改组信息===>"
					+ Util.reflectTest(sGroup));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>"
					+ sGroup.getModifier() + "===>修改组信息时出现:" + e.getMessage());
		}
		return num;
	}

	/**
	 * 保存组信息
	 */
	public int saveGroup(SGroup sGroup) {
		int num = -1;
		try {
			sql = "insert into s_group(groupcode,groupname,groupfullname,remark,createname,createDate,modifier,modifyDate,sfyx)"
					+ "values(?,?,?,?,?,?,?,?,?)";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, sGroup.getGroupcode());
			ps.setString(2, sGroup.getGroupname());
			ps.setString(3, sGroup.getGroupfullname());
			ps.setString(4, sGroup.getRemark());
			ps.setString(5, sGroup.getCreatename());
			ps.setString(6, sGroup.getCreateDate());
			ps.setString(7, sGroup.getModifier());
			ps.setString(8, sGroup.getModifyDate());
			ps.setString(9, sGroup.getSfyx());
			num = ps.executeUpdate();
			logger.info(DateUtils.getNowDateTime() + "===>"
					+ sGroup.getCreatename() + "===>添加组!==>"
					+ Util.reflectTest(sGroup));
		} catch (Exception e) {
			e.printStackTrace();
			logger
					.error(DateUtils.getNowDateTime() + "===>"
							+ sGroup.getCreatename() + "===>添加组时出现异常:"
							+ e.getMessage());
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public int updateRoleIsoption(Integer groupid, Integer userid,
			String isoption) {
		int rs = -1;
		try {
			con = DBManager.getCon();
			sql = "update s_role set isoption = '0' where userid= ? ";
			ps = con.prepareStatement(sql);
			// ps.setString(1, isoption);
			ps.setInt(1, userid);
			rs = ps.executeUpdate();

			String sql2 = "update s_role set isoption = ? where userid= ? and groupid = ?";

			ps = con.prepareStatement(sql2);
			ps.setString(1, isoption);
			ps.setInt(2, userid);
			ps.setInt(3, groupid);
			rs = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return rs;
	}

	public boolean checkGroupIsAuthorized(Integer groupid, Integer userid) {
		boolean rs = true;
		try {

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return rs;
	}

	/**
	 * 获取所有的group表的数据
	 */
	public List<SGroup> findAllGroupMenu() {
		List<SGroup> list = new ArrayList<SGroup>();
		try {
			sql = "select * from s_group where sfyx='Y'";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new SGroup(Integer.parseInt(rs.getString("groupid")),
						rs.getString("groupname")));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	/**
	 * 通过组ID获取这个组ID有的菜单数据
	 */
	public List<SGroupRole_mix> findAllGroupMenuByGroupid(Integer groupid) {
		List<SGroupRole_mix> list = new ArrayList<SGroupRole_mix>();
		try {
			sql = " SELECT s_group.groupid,menu.menuroleid,menu.menuid,m.menuname,m.parentId,m.createDate,m.parentshort,menu.`add`,menu.upd,menu.`delete`,menu.search,menu.export,"
					+ " menu.audit,menu.other,if(parentId=0,m.menuid,parentId) as sortNum from s_group"
					+ " join s_menurole menu on menu.groupid=s_group.groupid"
					+ " join s_menu m on m.menuid =menu.menuid"
					+ " WHERE s_group.groupid = ? ORDER BY sortNum,m.parentId asc,m.createDate asc; ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, groupid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new SGroupRole_mix(rs.getInt("menuroleid"), rs
						.getInt("menuid"), rs.getInt("groupid"), rs
						.getString("menuname"), rs.getInt("parentId"), rs
						.getString("createDate"), rs.getString("add"), rs
						.getString("upd"), rs.getString("delete"), rs
						.getString("search"), rs.getString("export"), rs
						.getString("audit"), rs.getString("other")));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int removeGroup(int groupid, String username) {
		int num = -1;
		try {
			sql = "update s_group set sfyx='D',modifier=?,modifyDate=? where sfyx!='D' and groupid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, DateUtils.getNowDateTime());
			ps.setInt(3, groupid);
			num = ps.executeUpdate();
			logger.info(DateUtils.getNowDateTime() + "===>" + username
					+ "===>删除组!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>" + username
					+ "删除组信息时出现异常:" + e.getMessage());
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public List<String[]> findGroupList(String menuid) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			sql = "select groupid,groupname from s_group where sfyx='Y' and groupid not in (select groupid from s_menurole where sfyx = 'Y' and menuid=?)";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, menuid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new String []{rs.getString("groupid"),rs.getString("groupname")});
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}
	
	public int existGroup(String menuid,String groupid) {
		int num = -1;
		try {
			sql = "select * from s_menurole where sfyx = 'Y' and menuid=? and groupid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, menuid);
			ps.setString(2, groupid);
			rs = ps.executeQuery();
			while (rs.next()) {
				num=1;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}
	
	public int effectiveGroup(String groupid) {
		int num = -1;
		try {
			sql = "select * from s_group where sfyx = 'Y' and groupid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, groupid);
			rs = ps.executeQuery();
			while (rs.next()) {
				num=1;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}
	
	
	public List<String[]> findPersonList(String menuid) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			sql = "select userid,loginname from s_user where sfyx='Y' and userid not in (select aa.userid from s_user aa left join s_usermenu bb ON aa.userid = bb.userid where aa.sfyx='Y' and bb.sfyx='Y' and bb.menuid=?)";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, menuid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new String []{rs.getString("userid"),rs.getString("loginname")});
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);
			
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

}
