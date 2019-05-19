package cn.admin.dao.impl.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Util;
import cn.admin.dao.system.SUsermenuDao;
import cn.admin.entity.system.SGroupRole_mix;
import cn.admin.entity.system.SGroupmenu_Param;
import cn.admin.entity.system.SMenuBasic;
import cn.admin.entity.system.SMenuRole_mix;
import cn.admin.entity.system.SUsermenu;
import cn.admin.entity.system.SUsermenu_Param;


/**
 * 
 *用户权限接口实现类 @author kingxu
 * 
 */
public class SUsermenuDaoImpl implements SUsermenuDao {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;
	Logger logger = Logger.getLogger(SUsermenuDaoImpl.class);

	public SMenuBasic findMenubyusermenu(int userid,int menuid){
		SMenuBasic sMenuBasic = null;
		try{
			con=DBManager.getCon();
			sql="select a.menuid,menuAction,if(sum(sadd)>0,1,0)as sadd,if(sum(upd)>0,1,0) as upd,if(sum(del)>0,1,0)as del,if(sum(search)>0,1,0) as search,"+
					" if(sum(export)>0,1,0) as export,if(sum(audit)>0,1,0)as audit,if(sum(other)>0,1,0) as other from ("+
					" select a.menuid ,if(`add`='Y',1,0)as sadd,if(upd='Y',1,0)as upd,if(`delete`='Y',1,0)as del,if(search='Y',1,0)as search,if(export='Y',1,0)as export,if(audit='Y',1,0)as audit,if(other='Y',1,0)as other from s_usermenu a where sfyx='Y' and userid=?"+
					" union"+
					" select  b.menuid,if(`add`='Y',1,0)as sadd,if(upd='Y',1,0)as upd,if(`delete`='Y',1,0)as del,if(search='Y',1,0)as search,if(export='Y',1,0)as export,if(audit='Y',1,0)as audit,if(other='Y',1,0)as other from  s_menurole b where groupid in(select groupid from s_role where sfyx='Y' and userid=?)"+

					" )a left join (select menuid,menuAction from s_menu where sfyx='Y')b on (a.menuid=b.menuid) where a.menuid=? group by a.menuid ";
			ps=con.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, menuid);
			rs=ps.executeQuery();
			if(rs.next()){
				sMenuBasic = new SMenuBasic(userid, 0, rs
						.getInt("menuid"), "", rs
						.getString("menuAction"), rs.getString("sadd"), rs
						.getString("upd"), rs.getString("del"), rs
						.getString("search"), rs.getString("export"), rs
						.getString("audit"), rs.getString("other"), "Y");
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return sMenuBasic;
	}
	
	
	
	/**
	 * 根据页面提供的用户权限列表保存数据
	 * @author kingxu
	 * @date 2015-12-23
	 * @param list
	 * @return
	 * @return String
	 * @throws Exception 
	 */
	public String saveUserMenuRole(List<SUsermenu_Param> list,String username) throws Exception{
		String result="";
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);// 禁止自动提交
			Statement st=con.createStatement();  
			String nowdate=DateUtils.getNowDateTime();
			for(int i=0;i<list.size();i++){
				SUsermenu_Param m=list.get(i);
				
				
				if(m.getUsermenuid()==0){//新增
					st.addBatch("insert into s_usermenu(userid,menuid,`add`,upd,`delete`,search,export,audit,other,createname,createDate,sfyx)" +
							"values("+m.getUserid()+","+m.getMenuid()+",'"
							+(Integer.parseInt(m.getAdd())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getUpd())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getDel())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getSel())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getExp())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getAudit())>0?"Y":"N")+"','"
							+(Integer.parseInt(m.getOther())>0?"Y":"N")+"','"+username+"','"+nowdate+"','Y'" +
							"); ");
				}else{//修改
					String sql="update s_usermenu set createname='"+username+"',createDate='"+nowdate+"'";
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
							sql="delete from s_usermenu ";
						}
					}
					sql+=" where usermenuid="+m.getUsermenuid()+";";
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
	
	
	
	
	
	
	
	
	/**
	 * 通过用户ID获取这个用户有的菜单数据
	 */
	public List<SMenuRole_mix> findAllUserMenu(Integer userid) {
		List<SMenuRole_mix> list = new ArrayList<SMenuRole_mix>();
		List<SMenuRole_mix> list1 = new ArrayList<SMenuRole_mix>();
		try {
			sql = " (SELECT s_usermenu.usermenuid,s_usermenu.menuid,menu.menuname,menu.parentId,menu.createDate,menu.parentshort,menu.childshort,s_usermenu.`add`,s_usermenu.upd,s_usermenu.`delete`,s_usermenu.search,s_usermenu.export,s_usermenu.audit,s_usermenu.other,if(parentId=0,menu.menuid,parentId) as sortNum from s_usermenu "
					+ " join s_menu menu on menu.menuid = s_usermenu.menuid "
					+ " WHERE menu.sfyx='Y' and s_usermenu.userid = ?  ORDER BY sortNum,menu.parentId asc,menu.createDate asc )";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new SMenuRole_mix(Integer.parseInt(rs
						.getString("usermenuid")), rs.getInt("menuid"), rs
						.getString("menuname"), rs.getInt("parentId"), rs
						.getInt("parentshort"), rs.getInt("childshort"), rs
						.getString("createDate"), rs.getString("add"), rs
						.getString("upd"), rs.getString("delete"), rs
						.getString("search"), rs.getString("export"), rs
						.getString("audit"), rs.getString("other"), list1, 1));
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

	/**
	 * 获取需要修改的数据记录
	 */
	public List<SUsermenu_Param> findUserMenuByMenuid2(int usermenuid) {
		List<SUsermenu_Param> list = new ArrayList<SUsermenu_Param>();
		try {
			sql = "select * from s_usermenu where usermenuid=?;";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, usermenuid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new SUsermenu_Param(rs.getInt("usermenuid"), rs
						.getInt("userid"), rs.getInt("menuid"), rs
						.getString("add"), rs.getString("upd"), rs
						.getString("delete"), rs.getString("search"), rs
						.getString("export"), rs.getString("audit"), rs
						.getString("other")));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取需要修改的数据记录出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	/**
	 * 修改记录
	 */
	public int modifyUserMenuByMenuid(SUsermenu_Param susermenu) {
		int rs = 0;
		try {
			sql = "update s_usermenu set s_usermenu.add =?,s_usermenu.upd=?,s_usermenu.delete=?,s_usermenu.search=?,s_usermenu.export=?,s_usermenu."
					+ "audit=?,s_usermenu.other=? where s_usermenu.usermenuid=? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, susermenu.getAdd());
			ps.setString(2, susermenu.getUpd());
			ps.setString(3, susermenu.getDel());
			ps.setString(4, susermenu.getSel());
			ps.setString(5, susermenu.getExp());
			ps.setString(6, susermenu.getAudit());
			ps.setString(7, susermenu.getOther());
			ps.setInt(8, susermenu.getUsermenuid());
			rs = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改usermenu记录出现异常：" + e);

		} finally {
			DBManager.closeCon(con);
		}
		return rs;
	}

	public int addItem(SUsermenu_Param sUsermenu, String username) {
		int num = -1;
		try {
			sql = "insert into s_usermenu(userid,menuid,`add`,upd,`delete`,search,export,audit,other,createname,createDate,sfyx)values(?,?,?,?,?,?,?,?,?,?,now(),?)";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, sUsermenu.getUserid());
			ps.setInt(2, sUsermenu.getMenuid());
			ps.setString(3, sUsermenu.getAdd());
			ps.setString(4, sUsermenu.getUpd());
			ps.setString(5, sUsermenu.getDel());
			ps.setString(6, sUsermenu.getSel());
			ps.setString(7, sUsermenu.getExp());
			ps.setString(8, sUsermenu.getAudit());
			ps.setString(9, sUsermenu.getOther());
			ps.setString(10, username);
			// ps.setString(11, );
			ps.setString(11, "Y");
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>" + username
					+ "添加菜单权限保存时出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public List<String[]> findUserMenuByMenuid(int menuid) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			sql = "select sm.*,su.loginname from s_usermenu sm left join s_user su on(sm.userid=su.userid) where menuid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, menuid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new String[] { rs.getString("menuid"),
						rs.getString("loginname"), rs.getString("add"),
						rs.getString("upd"), rs.getString("delete"),
						rs.getString("search"), rs.getString("export"),
						rs.getString("audit"), rs.getString("other"),rs.getString("userid") });
			}
			rs.close();
		} catch (Exception e) {
			logger.error(DateUtils.getNowDateTime()
					+ "===>通过时menuid获取用户菜单权限出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	public List<String[]> findUserMenuByUserid(int userid) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			sql = "select * from s_usermenu where userid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, userid);
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
					+ "===>通过时userid获取用户菜单权限出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}

	public int saveUserMenu(SUsermenu sUsermenu, String username) {
		int num = -1;
		try {
			sql = "insert into s_usermenu(userid,menuid,add,upd,delete,search,export,audit,other,createname,createDate,sfyx)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
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
			ps.setString(10, username);
			ps.setString(11, sUsermenu.getCreateDate());
			ps.setString(12, "Y");
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>" + username
					+ "批量保存菜单权限时出现异常:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public int saveUserMenuList(String[] userMenu, String username) {
		int num = -1;
		try {
			con = DBManager.getCon();
			con.setAutoCommit(false);// 禁止自动提交
			sql = "insert into s_usermenu(userid,menuid,add,upd,delete,search,export,audit,other,sfyx)"
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			String[] roles = null;
			String addDate = DateUtils.getNowDateTime();
			for (int i = 0; i < userMenu.length; i++) {
				roles = userMenu[i].split("~,~");
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
			num = userMenu.length;
		} catch (Exception e) {
			try {
				con.rollback();
				logger.error(DateUtils.getNowDateTime() + "===>" + username
						+ "批量保存用户菜单权限时出现异常:" + e.getMessage() + "-->进行数据回滚");
			} catch (Exception es) {
				es.printStackTrace();
				logger.error(DateUtils.getNowDateTime() + "===>" + username
						+ "批量保存用户菜单权限回滚数据时出现异常:" + e);
			}
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public List<SMenuRole_mix> findAllMenu(Integer userid, Integer groupid) {
		List<SMenuRole_mix> list = new ArrayList<SMenuRole_mix>();
		List<SMenuRole_mix> tempSonList = new ArrayList<SMenuRole_mix>();
		try {
			// 获取这个用户的菜单
			sql = " SELECT menu.menuAction,s_usermenu.usermenuid,menu.menuid,menu.menuname,menu.parentId,menu.createDate,menu.parentshort, "
					+ "menu.childshort,s_usermenu.`add`,s_usermenu.upd,s_usermenu.`delete`,s_usermenu.search,"
					+ "s_usermenu.export,s_usermenu.audit,s_usermenu.other, if(parentId=0,menu.menuid,parentId) as sortNum"
					+ " from s_usermenu  "
					+ "join s_menu menu on menu.menuid = s_usermenu.menuid  WHERE menu.sfyx='Y' and s_usermenu.userid = ?  and s_usermenu.sfyx!='D' and menu.sfyx!='D' and s_usermenu.search= 'Y'  "
					+ "ORDER BY menu.parentId asc,menu.parentshort,menu.childshort asc;";
			con = DBManager.getCon();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {

				if (rs.getInt("parentId") == 0) {
					List<SMenuRole_mix> sonlist = new ArrayList<SMenuRole_mix>();
					// 如果是父级别的直接加入
					list.add(new SMenuRole_mix(Integer.parseInt(rs
							.getString("usermenuid")), rs.getInt("menuid"), rs
							.getString("menuname"), rs.getInt("parentId"), rs
							.getInt("parentshort"), rs.getInt("childshort"), rs
							.getString("createDate"), rs.getString("add"), rs
							.getString("upd"), rs.getString("delete"), rs
							.getString("search"), rs.getString("export"), rs
							.getString("audit"), rs.getString("other"),
							sonlist, 1));
				} else {
					int flag = 1;// 标记当前这个子类还未加入到集合
					int temp = list.size();
					int i = 0;
					// 如果是子级别的遍历加入到对应的父级别的sonlist中
					// 这里不做temp.size==0的情况。因为rs结果集里面如果没有包含父级
					// 的菜单，这里才会出现temp=0；如果没有父几点的话，子集存入tempSonList
					while (i < temp) {
						SMenuRole_mix objT = list.get(i);
						if (objT.getMenuid() == rs.getInt("parentId")) {
							// 自己加入了子集所以标示为0
							flag = 0;
							objT.getSonList().add(
									new SMenuRole_mix(Integer.parseInt(rs
											.getString("usermenuid")), rs
											.getInt("menuid"), rs
											.getString("menuname"), rs
											.getString("menuAction"), rs
											.getInt("parentId"), rs
											.getInt("childshort"), rs
											.getString("createDate"), rs
											.getString("add"), rs
											.getString("upd"), rs
											.getString("delete"), rs
											.getString("search"), rs
											.getString("export"), rs
											.getString("audit"), rs
											.getString("other"), 2));
							Collections.sort(objT.getSonList());
							break;
						} else {
							i++;
						}

					}
					if (temp == 0 || flag == 1) {
						tempSonList.add(new SMenuRole_mix(Integer.parseInt(rs
								.getString("usermenuid")), rs.getInt("menuid"),
								rs.getString("menuname"), rs
										.getString("menuAction"), rs
										.getInt("parentId"), rs
										.getInt("childshort"), rs
										.getString("createDate"), rs
										.getString("add"), rs.getString("upd"),
								rs.getString("delete"), rs.getString("search"),
								rs.getString("export"), rs.getString("audit"),
								rs.getString("other"), 2));
					}
				}
			}
			// 获取组别的菜单，如果组别的和用户的重叠，最后选择用户的
			sql = "SELECT   menu.menuAction,m.menuroleid,menu.menuid,menu.menuname,menu.parentId,menu.createDate,menu.parentshort,menu.childshort,"
					+ "m.`add`,m.upd,m.`delete`,m.search,m.export,m.audit,m.other,IF (parentId = 0,menu.menuid,parentId) AS sortNum "
					+ "FROM s_menurole m  "
					+ "JOIN s_menu menu ON menu.menuid = m.menuid WHERE menu.sfyx='Y' and m.groupid = ? and menu.sfyx!='D' and m.sfyx!='D' and m.search= 'Y' "
					+ "ORDER BY menu.parentId asc,menu.parentshort,menu.childshort asc;";
			ps = con.prepareStatement(sql);

			ps.setInt(1, groupid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int temp = list.size();
				int i = 0;
				// 如果是子级别的遍历加入到对应的父级别的sonlist中
				int isAdd = 1;
				// 表示是需要添加到父级别的,子级别是1；
				int isSon = 0;
				// 表示如果是子级别的话记录是加入到那个父几点下的
				int sonIndex = -1;
				// 如果数组是空的，那么直接就加入
				if (temp == 0) {
					sonIndex = 0;
				}
				while (i < temp) {

					SMenuRole_mix objT = list.get(i);

					if (rs.getInt("parentId") == objT.getMenuid()) {

						sonIndex = i;
					}

					if (objT.getMenuid() == rs.getInt("menuid")) {

						isAdd = 0;
						break;
					}
					// 如果是不是父级别的就要便利每个父级别下的子集。
					if (rs.getInt("parentId") != 0) {
						List<SMenuRole_mix> tem = objT.getSonList();
						if (tem.size() > 0) {
							int j = 0;
							while (j < tem.size()) {
								if (tem.get(j).getMenuid() == rs
										.getInt("menuid")) {

									isAdd = 0;
									isSon = 1;
									break;

								}
								j++;
							}
						}
					}
					if (isSon == 1) {
						// 如果这个记录是存在子集的，那么父级也必要循环了
						break;
					}

					i++;
				}

				if (isAdd == 1) {
					if (rs.getInt("parentId") == 0) {
						List<SMenuRole_mix> sonlist = new ArrayList<SMenuRole_mix>();
						// 添加完了父级别的节点，看看tempSonList是否有符合这个新添加父节点的数据。
						for (SMenuRole_mix s : tempSonList) {
							if (s.getParentId() == rs.getInt("menuid")) {
								// 加入
								sonlist.add(new SMenuRole_mix(
										s.getUsermenuid(), s.getMenuid(), s
												.getMenuname(), s
												.getMenuAction(), s
												.getParentId(), s
												.getChildshort(), s
												.getCreateDate(), s.getAdd(), s
												.getUpd(), s.getDelete(), s
												.getSearch(), s.getExport(), s
												.getAudit(), s.getOther(), 2));
							}
						}
						// 排序
						Collections.sort(sonlist);
						list.add(new SMenuRole_mix(Integer.parseInt(rs
								.getString("menuroleid")), rs.getInt("menuid"),
								rs.getString("menuname"),
								rs.getInt("parentId"),
								rs.getInt("parentshort"), rs
										.getInt("childshort"), rs
										.getString("createDate"), rs
										.getString("add"), rs.getString("upd"),
								rs.getString("delete"), rs.getString("search"),
								rs.getString("export"), rs.getString("audit"),
								rs.getString("other"), sonlist, 1));
					} else if (sonIndex != -1) {
						// 子级别的处理
						// 1.找到这个子类的父亲
						list.get(sonIndex).getSonList().add(
								new SMenuRole_mix(Integer.parseInt(rs
										.getString("menuroleid")), rs
										.getInt("menuid"), rs
										.getString("menuname"), rs
										.getString("menuAction"), rs
										.getInt("parentId"), rs
										.getInt("childshort"), rs
										.getString("createDate"), rs
										.getString("add"), rs.getString("upd"),
										rs.getString("delete"), rs
												.getString("search"), rs
												.getString("export"), rs
												.getString("audit"), rs
												.getString("other"), 2));
						// 排序
						Collections.sort(list.get(sonIndex).getSonList());
					}

				}

			}
			rs.close();
			con.commit();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			logger.error("获取登录人菜单列表出现异常：" + sqle + "!进行数据的回滚");
			try {
				con.rollback();
				logger.error("获取登录人菜单列表出现异常：" + sqle + "!进行数据的回滚");
			} catch (SQLException sqlerror) {
				sqlerror.printStackTrace();
				logger.error("获取登录人菜单列表数据的回滚时候出现异常：" + sqlerror + "!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取父菜单列表出现异常：" + e);

		}
		DBManager.closeCon(con);
		Collections.sort(list);
		return list;
	}
	
	public List<Map<String,Object>> findMenuRoleByUserId(int userid){
		List<Map<String,Object>> list=null;
		List<Map<String,Object>> childList=null;
		Map<String,Object> parent=null;
		Map<String,Object> child=null;
		Map<String,Object> temp=null;
		Map<Integer,Integer> numMap=new HashMap<Integer, Integer>();
		
		try{
			String sql=" select a.menuid as menuid,menuname,menuAction,parentid,childshort,parentshort,sum(sadd)as sadd,sum(upd) as upd,sum(del) as del,sum(search)as search,sum(export) as export,sum(audit)as audit,sum(other) as other from ("+
					    " select menuid,if(`add`='Y',1,0)as sadd,if(upd='Y',1,0)as upd,if(`delete`='Y',1,0)as del,if(search='Y',1,0)as search,if(export='Y',1,0)as export,if(audit='Y',1,0)as audit,if(other='Y',1,0)as other from s_usermenu where sfyx='Y' and userid=?"+
					    " union"+
						" select  menuid,if(`add`='Y',1,0)as sadd,if(upd='Y',1,0)as upd,if(`delete`='Y',1,0)as del,if(search='Y',1,0)as search,if(export='Y',1,0)as export,if(audit='Y',1,0)as audit,if(other='Y',1,0)as other from  s_menurole where groupid in(select groupid from s_role where sfyx='Y' and userid=?)"+
						" )a left join (select menuid,menuname,menuAction,parentid,if(childshort is null,0,childshort) as childshort,if(parentshort is null,0,parentshort) as parentshort,sfyx from s_menu where sfyx='Y')b on (a.menuid=b.menuid) where sfyx='Y' and (sadd+upd+del+search+export+audit+other)>0"+
						" group by menuid  ORDER BY parentId asc,parentshort asc,childshort asc";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				if(!Util.objIsNULL(rs.getString("parentid"))){
					if(Util.objIsNULL(list)){
						list=new ArrayList<Map<String,Object>>();
					}
					int parentid =rs.getInt("parentid");
					if(parentid==0){
						parent=new HashMap<String, Object>();
						parent.put("menuid", rs.getInt("menuid"));
						parent.put("menuname", rs.getString("menuname"));
						parent.put("menuAction", rs.getString("menuAction"));
						parent.put("sadd", rs.getString("sadd"));
						parent.put("upd", rs.getString("upd"));
						parent.put("del", rs.getString("del"));
						parent.put("search", rs.getString("search"));
						parent.put("export", rs.getString("export"));
						parent.put("audit", rs.getString("audit"));
						parent.put("other", rs.getString("other"));
						parent.put("child", childList);
						list.add(parent);
						numMap.put( rs.getInt("menuid"), list.size()-1);
					}else{
						child=new HashMap<String, Object>();
						child.put("menuid", rs.getInt("menuid"));
						child.put("menuname", rs.getString("menuname"));
						child.put("menuAction", rs.getString("menuAction"));
						child.put("sadd", rs.getString("sadd"));
						child.put("upd", rs.getString("upd"));
						child.put("del", rs.getString("del"));
						child.put("search", rs.getString("search"));
						child.put("export", rs.getString("export"));
						child.put("audit", rs.getString("audit"));
						child.put("other", rs.getString("other"));
						child.put("child", null);
						
						temp=list.get(numMap.get(parentid));
						if(Util.objIsNULL(temp)){
							list.add(child);
						}else{
							childList=(List<Map<String, Object>>) temp.get("child");
							if(Util.objIsNULL(childList)){
								childList=new ArrayList<Map<String,Object>>();
								childList.add(child);
							}else{
								childList.add(child);
							}
							temp.put("child",childList);
							//list.get(numMap.get(parentid)).put("child",childList);
						}
						
						
					}
					
					
					
				}
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	
	public Map<String,Object> findSingleMenuRoleByUserId(int userid,int menuid){
		
		Map<String,Object> singleMenuMap=null;
		try{
			String sql=" select a.menuid as menuid,menuname,menuAction,parentid,childshort,parentshort,sum(sadd)as sadd,sum(upd) as upd,sum(del) as del,sum(search)as search,sum(export) as export,sum(audit)as audit,sum(other) as other from ("+
					" select menuid,if(`add`='Y',1,0)as sadd,if(upd='Y',1,0)as upd,if(`delete`='Y',1,0)as del,if(search='Y',1,0)as search,if(export='Y',1,0)as export,if(audit='Y',1,0)as audit,if(other='Y',1,0)as other from s_usermenu where sfyx='Y' and menuid=? and userid=?"+
					" union"+
					" select  menuid,if(`add`='Y',1,0)as sadd,if(upd='Y',1,0)as upd,if(`delete`='Y',1,0)as del,if(search='Y',1,0)as search,if(export='Y',1,0)as export,if(audit='Y',1,0)as audit,if(other='Y',1,0)as other from  s_menurole where menuid=? and groupid in(select groupid from s_role where sfyx='Y' and userid=?)"+
					" )a left join (select menuid,menuname,menuAction,parentid,if(childshort is null,0,childshort) as childshort,if(parentshort is null,0,parentshort) as parentshort,sfyx from s_menu where sfyx='Y')b on (a.menuid=b.menuid) where sfyx='Y'"+
					" group by menuid  ORDER BY parentId asc,parentshort asc,childshort asc";
			
		con = DBManager.getCon();
		ps = con.prepareStatement(sql);
		ps.setInt(1, menuid);
		ps.setInt(2, userid);
		ps.setInt(3, menuid);
		ps.setInt(4, userid);
		rs = ps.executeQuery();
		while (rs.next()) {
			singleMenuMap=new HashMap<String,Object>();
			singleMenuMap.put("menuid", rs.getInt("menuid"));
			singleMenuMap.put("add", rs.getInt("sadd"));
			singleMenuMap.put("upd", rs.getInt("upd"));
			singleMenuMap.put("del", rs.getInt("del"));
			singleMenuMap.put("search", rs.getInt("search"));
			singleMenuMap.put("export", rs.getInt("export"));
			singleMenuMap.put("audit", rs.getInt("audit"));
			singleMenuMap.put("other", rs.getInt("other"));
		}
		rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return singleMenuMap;
	}
	
	public Map<Integer,Object> findMenuRoleMapByUserId(String userid){
		Map<Integer,Object> map=new HashMap<Integer, Object>();
		try{
			String sql=" select a.menuid as menuid,menuname,menuAction,parentid,childshort,parentshort,sum(sadd)as sadd,sum(upd) as upd,sum(del) as del,sum(search)as search,sum(export) as export,sum(audit)as audit,sum(other) as other from ("+
				    " select menuid,if(`add`='Y',1,0)as sadd,if(upd='Y',1,0)as upd,if(`delete`='Y',1,0)as del,if(search='Y',1,0)as search,if(export='Y',1,0)as export,if(audit='Y',1,0)as audit,if(other='Y',1,0)as other from s_usermenu where sfyx='Y' and userid=?"+
				    " union"+
					" select  menuid,if(`add`='Y',1,0)as sadd,if(upd='Y',1,0)as upd,if(`delete`='Y',1,0)as del,if(search='Y',1,0)as search,if(export='Y',1,0)as export,if(audit='Y',1,0)as audit,if(other='Y',1,0)as other from  s_menurole where groupid in(select groupid from s_role where sfyx='Y' and userid=?)"+
					" )a left join (select menuid,menuname,menuAction,parentid,if(childshort is null,0,childshort) as childshort,if(parentshort is null,0,parentshort) as parentshort from s_menu where sfyx='Y')b on (a.menuid=b.menuid)"+
					" group by menuid  ORDER BY parentId asc,parentshort asc,childshort asc";
		con = DBManager.getCon();
		ps = con.prepareStatement(sql);
		ps.setInt(1, Integer.parseInt(userid));
		ps.setInt(2, Integer.parseInt(userid));
		rs = ps.executeQuery();
		while (rs.next()) {
			SGroupmenu_Param m=new SGroupmenu_Param();
			m.setMenuid(rs.getInt("menuid"));
			m.setAdd(rs.getString("sadd"));
			m.setUpd(rs.getString("upd"));
			m.setDel( rs.getString("del"));
			m.setSel(rs.getString("search"));
			m.setExp( rs.getString("export"));
			m.setAudit(rs.getString("audit"));
			m.setOther(rs.getString("other"));
			map.put(rs.getInt("menuid"), m);
		}
		rs.close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		
		return map;
	}
	
	
	public List<SGroupRole_mix> findMenuRoleClassByUserId(String userid){
		List<SGroupRole_mix> list = new ArrayList<SGroupRole_mix>();
		try{
			String sql=" select a.menuid as menuid,menuname,menuAction,parentid,childshort,parentshort,sum(sadd)as sadd,sum(upd) as upd,sum(del) as del,sum(search)as search,sum(export) as export,sum(audit)as audit,sum(other) as other from ("+
				    " select menuid,if(`add`='Y',1,0)as sadd,if(upd='Y',1,0)as upd,if(`delete`='Y',1,0)as del,if(search='Y',1,0)as search,if(export='Y',1,0)as export,if(audit='Y',1,0)as audit,if(other='Y',1,0)as other from s_usermenu where sfyx='Y' and userid=?"+
				    " union"+
					" select  menuid,if(`add`='Y',1,0)as sadd,if(upd='Y',1,0)as upd,if(`delete`='Y',1,0)as del,if(search='Y',1,0)as search,if(export='Y',1,0)as export,if(audit='Y',1,0)as audit,if(other='Y',1,0)as other from  s_menurole where groupid in(select groupid from s_role where sfyx='Y' and userid=?)"+
					" )a left join (select menuid,menuname,menuAction,parentid,if(childshort is null,0,childshort) as childshort,if(parentshort is null,0,parentshort) as parentshort from s_menu where sfyx='Y')b on (a.menuid=b.menuid)"+
					" group by menuid  ORDER BY parentId asc,parentshort asc,childshort asc";
		con = DBManager.getCon();
		ps = con.prepareStatement(sql);
		ps.setInt(1, Integer.parseInt(userid));
		ps.setInt(2, Integer.parseInt(userid));
		rs = ps.executeQuery();
		while (rs.next()) {
			SGroupRole_mix m=new SGroupRole_mix();
			m.setMenuid(rs.getInt("menuid"));
			m.setMenuname(rs.getString("menuname"));
			m.setAdd(rs.getString("sadd"));
			m.setUpd(rs.getString("upd"));
			m.setDelete( rs.getString("del"));
			m.setSearch(rs.getString("search"));
			m.setExport( rs.getString("export"));
			m.setAudit(rs.getString("audit"));
			m.setOther(rs.getString("other"));
			list.add(m);
		}
		rs.close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		
		return list;
	}
	
	
	
 
	
	
	
	
	
}
