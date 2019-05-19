package cn.admin.dao.impl.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Util;
import cn.admin.dao.system.SMenuRoleDao;
import cn.admin.entity.system.SGroupmenu_Param;
import cn.admin.entity.system.SMenurole;
import cn.admin.entity.system.SUsermenu;

/**
 * 
 * 菜单权限实现类 @author kingxu
 * 
 */
public class SMenuRoleDaoImpl implements SMenuRoleDao {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;
	Logger logger = Logger.getLogger(SMenuRoleDaoImpl.class);
	
	
	
	
	
	
	/**
	 * 根据页面提供的权限列表保存数据
	 * @author kingxu
	 * @date 2015-12-23
	 * @param list
	 * @return
	 * @return String
	 * @throws Exception 
	 */
	public String saveMenuRole(List<SGroupmenu_Param> list,String username) throws Exception{
		String result="";
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);// 禁止自动提交
			Statement st=con.createStatement();  
			String nowdate=DateUtils.getNowDateTime();
			for(int i=0;i<list.size();i++){
				SGroupmenu_Param m=list.get(i);
				
				
				if(m.getMenuroleid()==0){//新增
					st.addBatch("insert into s_menurole(groupid,menuid,`add`,upd,`delete`,search,export,audit,other,createname,createDate,sfyx)" +
							"values("+m.getGroupid()+","+m.getMenuid()+",'"
							+(Integer.parseInt(m.getAdd())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getUpd())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getDel())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getSel())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getExp())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getAudit())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getOther())>0?"Y":"N")+"','"+username+"','"+nowdate+"','Y'" +
							"); ");
				}else{//修改
					String sql="update s_menurole set createname='"+username+"',createDate='"+nowdate+"'";
					if(!m.getAdd().equals("-1")){
						sql+=" ,`add`='"+(Integer.parseInt(m.getAdd())>0?"Y":"N")+"'";
					}
					if(!m.getUpd().equals("-1")){
						sql+=" ,upd='"+(Integer.parseInt(m.getUpd())>0?"Y":"N")+"'";
					}
					if(!m.getDel().equals("-1")){
						sql+=" ,`delete`='"+(Integer.parseInt(m.getDel())>0?"Y":"N")+"'";
					}
					if(!m.getSel().equals("-1")){
						sql+=" ,search='"+(Integer.parseInt(m.getSel())>0?"Y":"N")+"'";
					}	
					if(!m.getExp().equals("-1")){
						sql+=" ,export='"+(Integer.parseInt(m.getExp())>0?"Y":"N")+"'";
					}
					if(!m.getUpd().equals("-1")){
						sql+=" ,audit='"+(Integer.parseInt(m.getAudit())>0?"Y":"N")+"'";
					}
					if(!m.getUpd().equals("-1")){
						sql+=" ,other='"+(Integer.parseInt(m.getOther())>0?"Y":"N")+"'";
					}
					if(sql.split("=").length==10){
						if(sql.indexOf("'Y'")<0){
							sql="delete from s_menurole ";
						}
					}
					sql+=" where menuroleid="+m.getMenuroleid()+";";
					st.addBatch(sql);
				}
			}
			st.executeBatch();
			con.commit();
			result=Util.getMsgJosnObject_success();
		}catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			throw e;
		}finally{
			DBManager.closeCon(con);
		}
		
		return result;
		
	}

	public String saveRoleMenu(String menuid,JSONArray groupJsonArray,JSONArray personJsonArray,String username) throws Exception{
		String result="";
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);// 禁止自动提交
			Statement st=con.createStatement();  
			String nowdate=DateUtils.getNowDateTime();
			int p = groupJsonArray.size();
			for(int i=0;i<p;i++){  
				JSONObject m=groupJsonArray.getJSONObject(i);
					String sql="update s_menurole set createname='"+username+"',createDate='"+nowdate+"'";
					if(!m.getString("add").equals("-1")){
						sql+=" ,`add`='"+(Integer.parseInt(m.getString("add"))>0?"Y":"N")+"'";
					}
					if(!m.getString("upd").equals("-1")){
						sql+=" ,`upd`='"+(Integer.parseInt(m.getString("upd"))>0?"Y":"N")+"'";
					}
					if(!m.getString("del").equals("-1")){
						sql+=" ,`delete`='"+(Integer.parseInt(m.getString("del"))>0?"Y":"N")+"'";
					}
					if(!m.getString("search").equals("-1")){
						sql+=" ,`search`='"+(Integer.parseInt(m.getString("search"))>0?"Y":"N")+"'";
					}	
					if(!m.getString("report").equals("-1")){
						sql+=" ,`export`='"+(Integer.parseInt(m.getString("report"))>0?"Y":"N")+"'";
					}
					if(!m.getString("audit").equals("-1")){
						sql+=" ,`audit`='"+(Integer.parseInt(m.getString("audit"))>0?"Y":"N")+"'";
					}
					if(!m.getString("other").equals("-1")){
						sql+=" ,`other`='"+(Integer.parseInt(m.getString("other"))>0?"Y":"N")+"'";
					}
					sql+=" where menuid="+menuid+" and groupid="+(Integer.parseInt(m.getString("roleid")))+" ;";
					st.addBatch(sql);
			}
			int q = personJsonArray.size();
			for(int i=0;i<q;i++){  
				JSONObject m=personJsonArray.getJSONObject(i);
				String sql="update s_usermenu set createname='"+username+"',createDate='"+nowdate+"'";
				if(!m.getString("add").equals("-1")){
					sql+=" ,`add`='"+(Integer.parseInt(m.getString("add"))>0?"Y":"N")+"'";
				}
				if(!m.getString("upd").equals("-1")){
					sql+=" ,`upd`='"+(Integer.parseInt(m.getString("upd"))>0?"Y":"N")+"'";
				}
				if(!m.getString("del").equals("-1")){
					sql+=" ,`delete`='"+(Integer.parseInt(m.getString("del"))>0?"Y":"N")+"'";
				}
				if(!m.getString("search").equals("-1")){
					sql+=" ,`search`='"+(Integer.parseInt(m.getString("search"))>0?"Y":"N")+"'";
				}	
				if(!m.getString("report").equals("-1")){
					sql+=" ,`export`='"+(Integer.parseInt(m.getString("report"))>0?"Y":"N")+"'";
				}
				if(!m.getString("audit").equals("-1")){
					sql+=" ,`audit`='"+(Integer.parseInt(m.getString("audit"))>0?"Y":"N")+"'";
				}
				if(!m.getString("other").equals("-1")){
					sql+=" ,`other`='"+(Integer.parseInt(m.getString("other"))>0?"Y":"N")+"'";
				}
				sql+=" where menuid="+menuid+" and userid="+(Integer.parseInt(m.getString("roleid")))+";";
				st.addBatch(sql);
			}
			st.executeBatch();
			con.commit();
			result=Util.getMsgJosnObject_success();
		}catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			throw e;
		}finally{
			DBManager.closeCon(con);
		}
		
		return result;
		
	}
	


	/**
	 * 保存单个菜单权限
	 * 
	 * @param sMenurole
	 * @return
	 */
	public int saveMenuRole(SMenurole sMenurole) {
		int num = -1;
		try {
			sql = "insert into s_menurole(roleid,menuid,add,upd,delete,search,export,audit,other,createname,createDate,sfyx)"
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(0, sMenurole.getRoleid());
			ps.setInt(1, sMenurole.getMenuid());
			ps.setString(2, sMenurole.getAdd());
			ps.setString(3, sMenurole.getUpd());
			ps.setString(4, sMenurole.getDelete());
			ps.setString(5, sMenurole.getSearch());
			ps.setString(6, sMenurole.getExport());
			ps.setString(7, sMenurole.getAudit());
			ps.setString(8, sMenurole.getOther());
			ps.setString(9, sMenurole.getCreatename());
			ps.setString(10, sMenurole.getCreateDate());
			ps.setString(11, sMenurole.getSfyx());
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存单个菜单权限时出现:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	/**
	 * 保存Group菜单
	 * @param sMenurole
	 * @return
	 */
	public int saveGroupMenuRole(SMenurole sMenurole) {
		int num = -1;
		try {
			sql = "insert into s_menurole(groupid,menuid,`add`,upd,`delete`,search,export,audit,other,createname,createDate,sfyx) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, sMenurole.getRoleid());
			ps.setInt(2, sMenurole.getMenuid());
			ps.setString(3, sMenurole.getAdd());
			ps.setString(4, sMenurole.getUpd());
			ps.setString(5, sMenurole.getDelete());
			ps.setString(6, sMenurole.getSearch());
			ps.setString(7, sMenurole.getExport());
			ps.setString(8, sMenurole.getAudit());
			ps.setString(9, sMenurole.getOther());
			ps.setString(10, sMenurole.getCreatename());
			ps.setString(11, sMenurole.getCreateDate());
			ps.setString(12, sMenurole.getSfyx());
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存单个菜单权限时出现:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 保存Person菜单
	 * @param sMenurole
	 * @return
	 */
	public int savePersonMenuRole(SUsermenu sUsermenu) {
		int num = -1;
		try {
			sql = "insert into s_usermenu(userid,menuid,`add`,upd,`delete`,search,export,audit,other,createname,createDate,sfyx) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, sUsermenu.getUserid());
			ps.setInt(2, sUsermenu.getMenuid());
			ps.setString(3, sUsermenu.getAdd());
			ps.setString(4, sUsermenu.getUpd());
			ps.setString(5, sUsermenu.getDelete());
			ps.setString(6, sUsermenu.getSearch());
			ps.setString(7, sUsermenu.getExport());
			ps.setString(8, sUsermenu.getAudit());
			ps.setString(9, sUsermenu.getOther());
			ps.setString(10, sUsermenu.getCreatename());
			ps.setString(11, sUsermenu.getCreateDate());
			ps.setString(12, sUsermenu.getSfyx());
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存单个菜单权限时出现:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	/**
	 * 批量保存菜单权限
	 * 
	 * @param menuRole菜单权限以数组的形式
	 * @return
	 */
	public int saveMenuRoleList(String[] menuRole, String username) {
		int num = -1;
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);// 禁止自动提交
			sql = "insert into s_menurole(roleid,menuid,add,upd,delete,search,export,audit,other,sfyx)"
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			String[] roles = null;
			String addDate = DateUtils.getNowDateTime();
			for (int i = 0; i < menuRole.length; i++) {
				roles = menuRole[i].split("~,~");
				ps.setInt(1, Integer.parseInt(roles[0]));
				ps.setInt(2, Integer.parseInt(roles[1]));
				ps.setString(3, roles[2]);
				ps.setString(4, roles[3]);
				ps.setString(5, roles[4]);
				ps.setString(6, roles[5]);
				ps.setString(7, roles[6]);
				ps.setString(8, roles[7]);
				ps.setString(9, roles[8]);
				ps.setString(10, username);
				ps.setString(11, addDate);
				ps.setString(12, "Y");
				ps.addBatch();

			}
			ps.executeBatch();
			con.commit();
			num = menuRole.length;
		} catch (Exception e) {
			try {
				con.rollback();
				logger.error(DateUtils.getNowDateTime() + "===>" + username
						+ "批量保存角色菜单权限时出现异常:" + e.getMessage() + "-->进行数据回滚");
			} catch (Exception es) {
				es.printStackTrace();
				logger.error(DateUtils.getNowDateTime() + "===>" + username
						+ "批量保存角色菜单权限回滚数据时出现异常:" + e);
			}
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	/**
	 * 通过Roleid获取菜单权限
	 * 
	 * @param roleid
	 * @return
	 */
	public List<String[]> findMenuRoleByRoleid(int roleid) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			sql = "select * from s_menurole where roleid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, roleid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new String[] { rs.getString("menuid"),
						rs.getString("add"), rs.getString("upd"),
						rs.getString("delete"), rs.getString("search"),
						rs.getString("export"), rs.getString("audit"),
						rs.getString("other") });
			}
			rs.close();
		} catch (Exception e) {
			logger.error(DateUtils.getNowDateTime()
					+ "===>通过时Roleid获取菜单权限出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	public List<String[]> findMenuRoleByMenuid(int menuid) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			sql = "select sm.*,groupname from s_menurole sm left join s_group sg on(sm.groupid=sg.groupid)  where sm.menuid=? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, menuid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new String[] { rs.getString("groupid"),
						rs.getString("groupname"), rs.getString("add"),
						rs.getString("upd"), rs.getString("delete"),
						rs.getString("search"), rs.getString("export"),
						rs.getString("audit"), rs.getString("other") });
			}
			rs.close();
		} catch (Exception e) {
			logger.error(DateUtils.getNowDateTime()
					+ "===>通过时Menuid获取菜单权限出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}
	public int addItem (SGroupmenu_Param obj,String modifyName){
		int num = -1;
		try {
			sql = "insert into s_menurole(groupid,menuid,`add`,`upd`,`delete`,`search`,`export`,`audit`,`other`,createname,createDate,sfyx)"
				+ "values(?,?,?,?,?,?,?,?,?,?,now(),?)";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, obj.getGroupid());
			ps.setInt(2, obj.getMenuid());
			ps.setString(3, obj.getAdd());
			ps.setString(4, obj.getUpd());
			ps.setString(5, obj.getDel());
			ps.setString(6, obj.getSel());
			ps.setString(7, obj.getExp());
			ps.setString(8, obj.getAudit());
			ps.setString(9, obj.getOther());
			ps.setString(10, modifyName);
			//ps.setString(11, );
			ps.setString(11, "Y");
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>" + modifyName
					+ "添加组别菜单权限保存时出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}
	public int modifyMenuRoleByMenuRoleid(SGroupmenu_Param obj){
		int rs = 0;
		try {
			sql = "update s_menurole m set m.add =?,m.upd=?,m.delete=?,m.search=?,m.export=?,m."
					+ "audit=?,m.other=? where m.menuroleid=? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, obj.getAdd());
			ps.setString(2, obj.getUpd());
			ps.setString(3, obj.getDel());
			ps.setString(4, obj.getSel());
			ps.setString(5, obj.getExp());
			ps.setString(6, obj.getAudit());
			ps.setString(7, obj.getOther());
			ps.setInt(8, obj.getMenuroleid());
			rs = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改usermenu记录出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return rs;
	}
	public List<SGroupmenu_Param> findItemByMenuRoleId(int menuroleid) {
		List<SGroupmenu_Param> list = new ArrayList<SGroupmenu_Param>();
		try {
			sql = "SELECT * from s_menurole WHERE menuroleid=?;";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, menuroleid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new SGroupmenu_Param(rs.getInt("menuroleid"), rs
						.getInt("groupid"), rs.getInt("menuid"), rs
						.getString("add"), rs.getString("upd"), rs
						.getString("delete"), rs.getString("search"), rs
						.getString("export"), rs.getString("audit"), rs
						.getString("other")));
			}
			rs.close();
		} catch (Exception e) {
			logger.error(DateUtils.getNowDateTime()
					+ "===>通过时Roleid,menuid获取菜单权限出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	/**
	 * 通过roleid,menuid获取当前菜单操作权限
	 * 
	 * @param menuid
	 * @return
	 */
	public SMenurole findMenuRoleBymenuId(int roleid, int menuid) {
		SMenurole sMenurole = null;
		try {
			sql = "select * from s_menurole where roleid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, roleid);
			rs = ps.executeQuery();
			while (rs.next()) {
				sMenurole = new SMenurole(rs.getInt("menuroleid"), rs
						.getInt("roleid"), rs.getInt("menuid"), rs
						.getString("add"), rs.getString("upd"), rs
						.getString("delete"), rs.getString("search"), rs
						.getString("export"), rs.getString("audit"), rs
						.getString("other"), rs.getString("createname"), rs
						.getString("createDate"), rs.getString("sfyx"));
			}
			rs.close();
		} catch (Exception e) {
			logger.error(DateUtils.getNowDateTime()
					+ "===>通过时Roleid,menuid获取菜单权限出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return sMenurole;
	}

	
	public int existPerson(String menuid,String personid) {
		int num = -1;
		try {
			sql = "select * from s_usermenu where sfyx = 'Y' and menuid=? and userid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, menuid);
			ps.setString(2, personid);
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
	
	public int effectivePerson(String personid) {
		int num = -1;
		try {
			sql = "select * from s_user where sfyx = 'Y' and userid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, personid);
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
}
