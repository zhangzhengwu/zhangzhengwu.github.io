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
import cn.admin.dao.system.SMenuDao;
import cn.admin.entity.system.SMenu;
import cn.admin.entity.system.SMenu_json;


public class SMenuDaoImpl implements SMenuDao {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;
	Logger logger = Logger.getLogger(SMenuDaoImpl.class);

	public int deleteMenu(int menuid, String username) {
		int num = -1;
		try {
			sql = "update s_menu set sfyx='D',modifier=?,modifyDate=? where sfyx='Y' and menuid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, DateUtils.getNowDateTime());
			ps.setInt(3, menuid);
			num = ps.executeUpdate();
			logger.info(DateUtils.getNowDateTime() + "===>" + username
					+ "===>删除菜单===>" + menuid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>" + username
					+ "===>删除菜单时出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public int findMenu(String startDate, String endDate, String menuname,
			String parentMenuname) {
		int num = -1;
		try {
			sql = "select count(*) as number from s_menu where sfyx='Y' "
					+ "and menuname like ? "
					+ "and parentid like ? "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + menuname + "%");
			ps.setString(2, "%" + menuname + "%");
			ps.setString(3, Util.objIsNULL(startDate) ? "1999-01-01"
					: startDate);
			ps.setString(4, Util.objIsNULL(endDate) ? "2099-12-31" : endDate);
			rs = ps.executeQuery();
			if (rs.next()) {
				num = rs.getInt("number");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>查询菜单分页条数时出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public List<SMenu> findMenu(String startDate, String endDate,
			String menuname, String parentMenuname, Page page) {
		List<SMenu> list = new ArrayList<SMenu>();
		try {
			sql = "select * from s_menu where sfyx='Y' "
					+ "and menuname like ? "
					+ "and parentid like ? "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "order by createDate desc limit ?,? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + menuname + "%");
			ps.setString(2, "%" + menuname + "%");
			ps.setString(3, Util.objIsNULL(startDate) ? "1999-01-01"
					: startDate);
			ps.setString(4, Util.objIsNULL(endDate) ? "2099-12-31" : endDate);
			ps.setInt(5, (page.getCurPage() - 1) * page.getPageSize());
			ps.setInt(6, page.getPageSize());
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new SMenu(rs.getInt("menuid"), rs
						.getString("menuname"), rs.getString("menuAction"), rs
						.getInt("parentId"), rs.getInt("childshort"), rs
						.getInt("parentshort"), rs.getString("remark"), rs
						.getString("createname"), rs.getString("createDate"),
						rs.getString("modifier"), rs.getString("modifyDate"),
						rs.getString("sfyx")));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()
					+ "===>根据时间，menuname，parentmenuName分页查询菜单时出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	public int modifyMenu(SMenu sMenu) {
		int num = -1;
		try {
			// 获取需要修改项
			String compare = Help.compareEntity(
					findMenuById(sMenu.getMenuid()), sMenu);
			sql = "update s_menu set " + compare.replaceAll("\\,", "=?,")
					+ "=? where menuid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			String[] param = (compare + ",menuid").split(",");
			for (int i = 0; i < param.length; i++) {
				ps.setObject((i + 1), sMenu.getClass().getMethod(
						"get" + param[i].substring(0, 1).toUpperCase()
								+ param[i].substring(1)).invoke(sMenu));
			}
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改菜单信息时出现:" + e.getMessage());
		}
		return num;
	}

	public int saveMenu(SMenu sMenu) {
		return 0;
	}

	/**
	 * 保存菜单
	 */
	public int saveMenu(SMenu sMenu, int userid) {
		int num = -1;
		try {
			sql = "insert into s_menu(menuname,menuAction,parentId,childshort,parentshort,remark,createname,createDate,modifier,modifyDate,sfyx)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?)";
			con = DBManager.getCon();
			con.setAutoCommit(false);// 取消自动提交
			ps = con.prepareStatement(sql);
			ps.setString(1, sMenu.getMenuname());
			ps.setString(2, sMenu.getMenuAction());
			ps.setInt(3, sMenu.getParentId());
			ps.setInt(4, sMenu.getChildshort());
			ps.setInt(5, sMenu.getParentshort());
			ps.setString(6, sMenu.getRemark());
			ps.setString(7, sMenu.getCreatename());
			ps.setString(8, sMenu.getCreateDate());
			ps.setString(9, sMenu.getModifier());
			ps.setString(10, sMenu.getModifyDate());
			ps.setString(11, sMenu.getSfyx());
			num = ps.executeUpdate();
			logger.info(DateUtils.getNowDateTime() + "===>"
					+ sMenu.getCreatename() + "====>保存菜单===>"
					+ sMenu.getMenuname());

			sql = "select menuid from s_menu where sfyx='Y' and  createDate=? and modifyDate=? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, sMenu.getCreateDate());
			ps.setString(2, sMenu.getModifyDate());
			rs = ps.executeQuery();
			if (rs.next()) {
				sMenu.setMenuid(rs.getInt("menuid"));
			}
			rs.close();
			// 需自动为添加人赋予该菜单全操作权限 待完成

			// 判断menuid是否合法
			if (Util.objIsNULL(sMenu.getMenuid()) || sMenu.getMenuid() < 1) {// menuid不合法
				throw new Exception("MenuId Error");
			} else {

				// 判断该菜单是否为主菜单
				if (sMenu.getParentId() != 0) {// 该菜单为子菜单
					sql = "select usermenuid from s_usermenu where menuid=?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, sMenu.getParentId());
					rs = ps.executeQuery();
					if (rs.next()) {

					} else {

						sql = "insert into s_usermenu(usermenuname,userid,menuid,`add`,`upd`,`delete`,`search`,`export`,`audit`,`other`,createname,createDate,sfyx)"
								+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
						ps = con.prepareStatement(sql);
						ps.setString(1, "");
						ps.setInt(2, userid);
						ps.setInt(3, sMenu.getParentId());
						ps.setString(4, "Y");
						ps.setString(5, "Y");
						ps.setString(6, "Y");
						ps.setString(7, "Y");
						ps.setString(8, "Y");
						ps.setString(9, "Y");
						ps.setString(10, "Y");
						ps.setString(11, sMenu.getCreatename());
						ps.setString(12, sMenu.getCreateDate());
						ps.setString(13, "Y");
						int numss = ps.executeUpdate();// 保存父菜单菜单操作权限
						if (numss <= 0) {
							throw new Exception(
									"Add Menu Operation Promission Error");
						}
					}
					rs.close();
				}

				sql = "insert into s_usermenu(usermenuname,userid,menuid,`add`,`upd`,`delete`,`search`,`export`,`audit`,`other`,createname,createDate,sfyx)"
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, "");
				ps.setInt(2, userid);
				ps.setInt(3, sMenu.getMenuid());
				ps.setString(4, "Y");
				ps.setString(5, "Y");
				ps.setString(6, "Y");
				ps.setString(7, "Y");
				ps.setString(8, "Y");
				ps.setString(9, "Y");
				ps.setString(10, "Y");
				ps.setString(11, sMenu.getCreatename());
				ps.setString(12, sMenu.getCreateDate());
				ps.setString(13, "Y");
				int nums = ps.executeUpdate();// 保存菜单操作权限
				if (nums <= 0) {
					throw new Exception("Add Menu Operation Promission Error");
				}

			}

			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			num = -1;
			try {
				con.rollback();
				logger.error(DateUtils.getNowDateTime() + "===>"
						+ sMenu.getCreatename() + "====>保存菜单时出现异常,数据回滚");
			} catch (Exception es) {
				// TODO: handle exception
				logger.error(DateUtils.getNowDateTime() + "===>"
						+ sMenu.getCreatename() + "====>保存菜单时出现异常,数据回滚出现异常："
						+ e);
			}

		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public SMenu findMenuById(int menuid) {
		SMenu sMenu = null;
		try {
			sql = "select * from s_menu where sfyx='Y' and  menuid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, menuid);
			rs = ps.executeQuery();
			if (rs.next()) {
				sMenu = new SMenu(rs.getInt("menuid"),
						rs.getString("menuname"), rs.getString("menuAction"),
						rs.getInt("parentId"), rs.getInt("childshort"), rs
								.getInt("parentshort"), rs.getString("remark"),
						rs.getString("createname"), rs.getString("createDate"),
						rs.getString("modifier"), rs.getString("modifyDate"),
						rs.getString("sfyx"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()
					+ "===>根据menuid查询菜单对象时出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return sMenu;
	}

	public int findMenu_json(String startDate, String endDate, String menuname,
			String parentMenuname) {
		int num = -1;
		try {
			sql = "select count(*)as number from (select sm.*,if(sm2.menuname is null,'',sm2.menuname) as parentmenuname from s_menu sm  left join s_menu sm2 on(sm.parentId=sm2.menuid))a where sfyx='Y' "
					+ "and menuname like ? "
					+ "and parentmenuname like ? "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + menuname + "%");
			ps.setString(2, "%" + parentMenuname + "%");
			ps.setString(3, Util.objIsNULL(startDate) ? "1999-01-01"
					: startDate);
			ps.setString(4, Util.objIsNULL(endDate) ? "2099-12-31" : endDate);
			rs = ps.executeQuery();
			if (rs.next()) {
				num = rs.getInt("number");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>查询菜单分页条数时出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public List<SMenu_json> findMenu_json(String startDate, String endDate,
			String menuname, String parentMenuname, Page page) {
		List<SMenu_json> list = new ArrayList<SMenu_json>();
		try {
			sql = "select * from (select sm.*,if(sm2.menuname is null,'',sm2.menuname) as parentmenuname from s_menu sm  left join s_menu sm2 on(sm.parentId=sm2.menuid))a where sfyx='Y' "
					+ "and menuname like ? "
					+ "and parentmenuname like ? "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') "
					+
					// "order by createDate desc limit ?,? ";
					"order by childshort,parentshort limit ?,? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + menuname + "%");
			ps.setString(2, "%" + parentMenuname + "%");
			ps.setString(3, Util.objIsNULL(startDate) ? "1999-01-01"
					: startDate);
			ps.setString(4, Util.objIsNULL(endDate) ? "2099-12-31" : endDate);
			ps.setInt(5, (page.getCurPage() - 1) * page.getPageSize());
			ps.setInt(6, page.getPageSize());
			rs = ps.executeQuery();
			System.out.println(sql);
			while (rs.next()) {
				list.add(new SMenu_json(rs.getInt("menuid"), rs
						.getString("menuname"), rs.getString("menuAction"), rs
						.getInt("parentId"), rs.getString("parentmenuname"), rs
						.getInt("childshort"), rs.getInt("parentshort"), rs
						.getString("remark"), rs.getString("createname"), rs
						.getString("createDate"), rs.getString("modifier"), rs
						.getString("modifyDate"), rs.getString("sfyx")));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()
					+ "===>根据时间，menuname，parentmenuName分页查询菜单时出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	public List<String> findMenuList() {
		List<String> list = new ArrayList<String>();
		try {
			sql = "select menuid,menuname from s_menu where sfyx='Y' and parentId=0";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("menuid") + "~,~"
						+ rs.getString("menuname"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	public int findChildMenu(int menuid) {
		int num = -1;
		try {
			sql = "select count(*)as number from s_menu where sfyx='Y' and parentId=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, menuid);
			rs = ps.executeQuery();
			if (rs.next()) {
				num = rs.getInt("number");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

}
