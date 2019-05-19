package cn.admin.dao.impl.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Help;
import util.Page;
import util.SendMail;
import util.Util;
import cn.admin.dao.system.SUserDao;
import cn.admin.entity.system.SUser;
import cn.admin.entity.system.SUser_Login;
import cn.admin.util.DesUtils;


/**
 * 
 *用户接口实现类 @author kingxu
 * 
 */
public class SUserDaoImpl implements SUserDao {

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;
	Logger logger = Logger.getLogger(SUserDaoImpl.class);

	public int UpdateHeadImg(Integer userid, String path) {
		int rs = -1;
		try {
			con = DBManager.getCon();
			sql = "update s_user set headimage = ? where userid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, path);
			ps.setInt(2, userid);
			rs = ps.executeUpdate();
		} catch (Exception e) {
			// list = null;
			e.printStackTrace();
			logger.error("用户更新头像时出现:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return rs;
	}

	/**
	 * 登录验证
	 */
	public List<SUser_Login> checkLogin(String adminUsername,
			String adminPassword) {
		List<SUser_Login> list = new ArrayList<SUser_Login>();
		try {
			sql = "select s_user.headimage,s_user.sfyx as userStu,g.sfyx as groupStu,role.sfyx as roleStu,g.groupid,g.groupname,g.groupfullname,g.groupcode,role.isoption,"
					+ " s_user.* from s_user"
					+ " join s_role role on role.userid = s_user.userid"
					+ " join s_group g on g.groupid =role.groupid"
					+ " where s_user.sfyx!='D' and g.sfyx!='D' and role.sfyx!='D' and loginname=? and loginpass=?;";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, adminUsername);
			ps.setString(2, new DesUtils(adminUsername).encrypt(adminPassword));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new SUser_Login(rs.getInt("userid"), rs
						.getString("loginname"), rs.getString("loginpass"), rs
						.getInt("groupid"), rs.getString("groupname"), rs
						.getString("userStu"), rs.getString("groupStu"), rs
						.getString("roleStu"), rs.getString("isoption"),rs.getString("headimage")));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户登录验证时出现:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return list;
	}
	
	
	public Map<String,Object> checklogin(String adminUsername,
			String adminPassword) {
		Map<String,Object> map=null; 
		try {
			sql = "select * from s_user where sfyx='Y' and loginname=? and loginpass=? order by createdate desc limit 0,1;";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, adminUsername);
			ps.setString(2, new DesUtils(adminUsername).encrypt(adminPassword));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				map=new HashMap<String, Object>();
				map.put("userid", rs.getInt("userid"));
				map.put("loginname", rs.getString("loginname"));
				map.put("headimage", rs.getString("headimage"));
				map.put("usercode", rs.getString("usercode"));
				map.put("truename", rs.getString("truename"));
				map.put("englishname", rs.getString("englishname"));
				map.put("sex", rs.getString("sex"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户登录验证时出现:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return map;
	}

	/**
	 * 登录验证
	 */
	public boolean checkLogin_AES_ENCRYPT(String adminUsername,
			String adminPassword) {
		boolean flag = false;
		try {
			sql = "select * from s_user where sfyx!='D' and loginname=? and loginpass=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, adminUsername);
			ps.setString(2,new DesUtils(adminUsername).encrypt(adminPassword) );
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				flag = true;
			}
			rs.close();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			logger.error("用户登录验证时出现:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return flag;
	}

	/**
	 * 根据登录名称,用户编号，用户英文名，用户中文名，用户创建时间，是否有效进行分页查询
	 */
	public List<SUser> findUser(String loginname, String usercode,
			String englishname, String chinesename, String startDate,
			String endDate, String sfyx,String dept, Page page) {
		List<SUser> list = new ArrayList<SUser>();
		try {
			sql = "select * from s_user "
					+ "where sfyx!='D' and loginname like ? "
					+ "and usercode like ? "
					+ "and englishname like ? "
					+ "and chinesename like ? "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and sfyx like ? "
					+ "and dept like ? " + "order by createDate desc limit ?,?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + loginname + "%");
			ps.setString(2, "%" + usercode + "%");
			ps.setString(3, "%" + englishname + "%");
			ps.setString(4, "%" + chinesename + "%");
			ps.setString(5, Util.objIsNULL(startDate) ? "1999-01-01"
					: startDate);
			ps.setString(6, Util.objIsNULL(endDate) ? "2099-12-31" : endDate);
			ps.setString(7, "%" + sfyx + "%");
			ps.setString(8, "%" + dept + "%");
			ps.setInt(9, (page.getCurPage() - 1) * page.getPageSize());
			ps.setInt(10, page.getPageSize());
			rs = ps.executeQuery();
			while (rs.next()) {
				list
						.add(new SUser(rs.getInt("userid"), rs
								.getString("loginname"), rs
								.getString("loginpass"), rs
								.getString("usercode"), rs
								.getString("truename"), rs
								.getString("englishname"), rs
								.getString("chinesename"), rs
								.getString("idcard"), rs.getString("sex"), rs
								.getString("birthday"), rs
								.getString("headimage"), rs
								.getString("registration"), rs
								.getString("address"), rs.getString("dept"), rs
								.getString("postion"), rs
								.getString("createname"), rs
								.getString("createdate"), rs
								.getString("modifier"), rs
								.getString("modifyDate"), rs.getString("sfyx"),rs.getString("email")));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户分页查询时出现:" + e);
		} finally {
			DBManager.closeCon(con);
		}

		return list;
	}

	/**
	 * 根据登录名称,用户编号，用户英文名，用户中文名，用户创建时间进行查询分页条数
	 */
	public int findUserRows(String loginname, String usercode,
			String englishname, String chinesename, String startDate,
			String endDate, String sfyx,String dept) {
		int num = -1;
		/*System.out.println(loginname + "-->" + usercode + "-->" + englishname
				+ "-->" + chinesename + "-->" + startDate + "--->" + endDate
				+ "-->" + sfyx);*/
		try {
			sql = "select count(*)as number from s_user  "
					+ "where sfyx!='D' and loginname like ? "
					+ "and usercode like ? "
					+ "and englishname like ? "
					+ "and chinesename like ? "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') "
					+ "and sfyx like ?"
					+ "and dept like ? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			//System.out.println(sql);
			ps.setString(1, "%" + loginname + "%");
			ps.setString(2, "%" + usercode + "%");
			ps.setString(3, "%" + englishname + "%");
			ps.setString(4, "%" + chinesename + "%");
			ps.setString(5, Util.objIsNULL(startDate) ? "1999-01-01"
					: startDate);
			ps.setString(6, Util.objIsNULL(endDate) ? "2099-12-31" : endDate);
			ps.setString(7, "%" + sfyx + "%");
			ps.setString(8, "%" + dept + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				num = rs.getInt("number");
			}
			//System.out.println(num);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户查询分页条数时出现:" + e);
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public String modifyUser(SUser sUser,String[] group) throws Exception {
		String result="";
		SUser user=null;
		
		try {
			con = DBManager.getCon();	
			con.setAutoCommit(false);
			String compare="";
			user=findUserById(sUser.getUserid(),sUser.getLoginname());
			if(Util.objIsNULL(user)){
				throw new RuntimeException("Modified user does not exist");
			}
			if(Util.objIsNULL(sUser.getLoginpass())){
				compare= Help.compareEntity(
						user, sUser,new String[]{"loginpass"});
			}else{
			
					compare= Help.compareEntity(user, sUser);
			}
			int num =-1;
			if(!Util.objIsNULL(compare)){
				sql = "update s_user set " + compare.replaceAll("\\,", "=?,")
						+ "=? where userid=?";
			
				
				ps = con.prepareStatement(sql);
				String[] param = (compare + ",userid").split(",");
				for (int i = 0; i < param.length; i++) {
					ps.setObject((i + 1), sUser.getClass().getMethod(
							"get" + param[i].substring(0, 1).toUpperCase()
									+ param[i].substring(1)).invoke(sUser));
				}
				num = ps.executeUpdate();
			}else{
				num=1;
			}
			Statement s=con.createStatement();
			s.addBatch("delete from s_role where userid="+sUser.getUserid()+";");
			for(int i=0;i<group.length;i++){
				
				s.addBatch("insert into s_role(groupid,userid,isoption,createname,createDate,sfyx) values('"+group[i]+"','"+sUser.getUserid()+"',"+(i==0?1:0)+",'"+sUser.getModifier()+"','"+sUser.getModifyDate()+"','Y');");
			}
			s.executeBatch();
			if(num>0){
				//result=Util.getMsgJosnObject_success();
				if(!Util.objIsNULL(sUser.getLoginpass())){
					result=SendMail.send("COAT system login password has been reset", Util.objIsNULL(sUser.getEmail())?user.getEmail():sUser.getEmail(), "Dear "+user.getLoginname()+",<br/>&nbsp;&nbsp;COAT System Login Password has been reset to "+new DesUtils(sUser.getLoginname()).decrypt(sUser.getLoginpass())+",Please change your password in time.thanks!");
				}else{
					result=Util.getMsgJosnObject_success();
				}
			}else{
				result=Util.getMsgJosnObject_error();
			}
			con.commit();
		} catch (Exception e) {
			con.rollback();
			logger.error("修改用户信息时出现:" + e.getMessage());
			throw e;
		}finally{
			DBManager.closeCon(con);
		}
		return result;
	}

	/**
	 * 根据userid查询用户信息
	 */
	public SUser findUserById(int userid) {
		SUser user = null;
		try {
			sql = "select * from s_user where sfyx!='D' and userid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new SUser(rs.getInt("userid"),
						rs.getString("loginname"),new DesUtils(rs.getString("loginname")).encrypt( rs.getString("loginpass")),
						rs.getString("usercode"), rs.getString("truename"), rs
								.getString("englishname"), rs
								.getString("chinesename"), rs
								.getString("idcard"), rs.getString("sex"), rs
								.getString("birthday"), rs
								.getString("headimage"), rs
								.getString("registration"), rs
								.getString("address"), rs.getString("dept"), rs
								.getString("postion"), rs
								.getString("createname"), rs
								.getString("createdate"), rs
								.getString("modifier"), rs
								.getString("modifyDate"), rs.getString("sfyx"));
				user.setEmail(rs.getString("email"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("通过userid查询用户信息时出现:" + e.getMessage());
		} finally {
			DBManager.closeCon(con);
		}
		return user;
	}
	
	public SUser findUserById(int userid,String loginname) {
		SUser user = null;
		Connection cons=null;
		try {
			String sqls = "select * from s_user where sfyx!='D' and userid=? and loginname=?";
			cons = DBManager.getCon();
			PreparedStatement pss = cons.prepareStatement(sqls);
			pss.setInt(1, userid);
			pss.setString(2, loginname);
			ResultSet rss = pss.executeQuery();
			if (rss.next()) {
				user = new SUser(rss.getInt("userid"),
						rss.getString("loginname"),new DesUtils(rss.getString("loginname")).encrypt( rss.getString("loginpass")),
						rss.getString("usercode"), rss.getString("truename"), rss
								.getString("englishname"), rss
								.getString("chinesename"), rss
								.getString("idcard"), rss.getString("sex"), rss
								.getString("birthday"), rss
								.getString("headimage"), rss
								.getString("registration"), rss
								.getString("address"), rss.getString("dept"), rss
								.getString("postion"), rss
								.getString("createname"), rss
								.getString("createdate"), rss
								.getString("modifier"), rss
								.getString("modifyDate"), rss.getString("sfyx"));
				user.setEmail(rss.getString("email"));
			}
			rss.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("通过userid查询用户信息时出现:" + e.getMessage());
		} finally {
			DBManager.closeCon(cons);
		}
		return user;
	}

	/**
	 * 根据用户登录名称查询用户信息
	 */
	public SUser findUserByloginname(String loginname) {
		SUser user = null;
		try {
			sql = "select * from s_user where sfyx!='D' and loginname=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, loginname);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new SUser(rs.getInt("userid"),
						rs.getString("loginname"), rs.getString("loginpass"),
						rs.getString("usercode"), rs.getString("truename"), rs
								.getString("englishname"), rs
								.getString("chinesename"), rs
								.getString("idcard"), rs.getString("sex"), rs
								.getString("birthday"), rs
								.getString("headimage"), rs
								.getString("registration"), rs
								.getString("address"), rs.getString("dept"), rs
								.getString("postion"), rs
								.getString("createname"), rs
								.getString("createdate"), rs
								.getString("modifier"), rs
								.getString("modifyDate"), rs.getString("sfyx"));
				user.setEmail(rs.getString("email"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("通过登录名称查询用户信息时出现:" + e.getMessage());
		} finally {
			DBManager.closeCon(con);
		}
		return user;
	}

	/**
	 * 删除用户
	 */
	public int RemoveUser(int userid, String modifier) {
		int num = -1;
		try {
			sql = "update s_user set sfyx='D',modifier=?,modifyDate=? where sfyx!='D' and userid=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, modifier);
			ps.setString(2, DateUtils.getNowDateTime());
			ps.setInt(3, userid);
			num = ps.executeUpdate();
			logger.info(DateUtils.getNowDateTime() + "===>" + modifier
					+ "===>删除用户!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>" + modifier
					+ "删除用户时出现异常:" + e.getMessage());
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	/**
	 * 验证原密码
	 */
	public boolean VailPassword(String loginname, String password) {
		boolean flag = false;
		try {
			sql = "select loginname from s_user where sfyx!='D' and loginname=? and loginpass=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, loginname);
			ps.setString(2, new DesUtils(loginname).encrypt(password));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				flag=true;
			}
			//System.out.println(flag+"------------------->"+loginname+"-->"+new DesUtils(loginname).encrypt(password)+"--->"+sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>验证" + loginname
					+ "用户原密码时出现异常" + e.getMessage());
		} finally {
			DBManager.closeCon(con);
		}
		return flag;
	}

	/**
	 * 修改密码
	 */
	public int modifyPassword(String loginname, String newpassword) {
		int num = -1;
		try {
			sql = "update s_user set loginpass=? where sfyx!='D'  and loginname=?";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, new DesUtils(loginname).encrypt(newpassword));
			ps.setString(2, loginname);
			num = ps.executeUpdate();
			//System.out.println(num+"--->"+sql+"--->"+loginname+">>>>"+newpassword);
			logger.error(DateUtils.getNowDateTime() + "===>" + loginname
					+ "===>修改密码!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime() + "===>" + loginname
					+ "===>修改密码时出现异常:" + e.getMessage());
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	public String saveUserAndsaveGroup(SUser sUser, String[] groupidList,
			String user) throws Exception {
		String result="";
		int num=0;
		try {
			sql = "insert into s_user(loginname,loginpass,usercode,truename,englishname,chinesename,email,idcard,sex,birthday,headimage, registration,address,dept,postion,createname,createdate,modifier,modifyDate,sfyx)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			con = DBManager.getCon();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, sUser.getLoginname());
			ps.setString(2, new DesUtils(sUser.getLoginname()).encrypt(sUser.getLoginpass()));
			ps.setString(3, sUser.getUsercode());
			ps.setString(4, sUser.getTruename());
			ps.setString(5, sUser.getEnglishname());
			ps.setString(6, sUser.getChinesename());
			ps.setString(7, sUser.getEmail());
			ps.setString(8, sUser.getIdcard());
			ps.setString(9, sUser.getSex());
			ps.setString(10, sUser.getBirthday());
			ps.setString(11, sUser.getHeadimage());
			ps.setString(12, sUser.getRegistration());
			ps.setString(13, sUser.getAddress());
			ps.setString(14, sUser.getDept());
			ps.setString(15, sUser.getPostion());
			ps.setString(16, sUser.getCreatename());
			ps.setString(17, sUser.getCreatedate());
			ps.setString(18, sUser.getModifier());
			ps.setString(19, sUser.getModifyDate());
			ps.setString(20, sUser.getSfyx());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			// rs = ps.getGeneratedKeys();
			int userid = -1;
			if (rs.next()) {
				userid = Integer.parseInt(rs.getObject(1).toString());
			} else {
				throw new RuntimeException("保存用戶信息失敗");
			}
	 
			// 添加组操作
			int i = 0;
			while (i < groupidList.length) {
				if(!Util.objIsNULL(groupidList[i])){
					SimpleDateFormat bartDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					sql = "insert into s_role(rolename,groupid,userid,remark,isoption,createname,createDate,modifier,modifyDate,sfyx)"
							+ "values(?,?,?,?,?,?,?,?,?,?)";
					ps = con.prepareStatement(sql);
					ps.setString(1, "");
					ps.setInt(2, Integer.parseInt(groupidList[i]));
					ps.setInt(3, userid);
					ps.setString(4, null);
					ps.setString(5, i==0?"1":"0");
					ps.setString(6, user);
					ps.setString(7, bartDateFormat.format(date));
					ps.setString(8, null);
					ps.setString(9, null);
					ps.setString(10, "Y");
					int n=ps.executeUpdate();
					num+=n;
					i++;
				}
			}
			if(num>0){
				String link=Util.getProValue(Util.getProValue("public.system.uat").equalsIgnoreCase("true")?"public.system.uatlink":"public.system.link");
				result=SendMail.send("COAT System Users Create information feedback",
					 sUser.getEmail(),
							"Dear "+sUser.getLoginname()+",<br/>&nbsp;&nbsp;" +
									"Your login information in the COAT system is as follows:" +
									"<br/>&nbsp;&nbsp;User:" +sUser.getLoginname()+
									"<br/>&nbsp;&nbsp;Password:" +sUser.getLoginpass()+
									"<br/>&nbsp;&nbsp;link:<a href='"+link+"'>"+link+"</a>");
			}else{
				result=Util.getMsgJosnObject_error();
			}
			
			con.commit();
			logger.info(DateUtils.getNowDateTime() + "===>"+ sUser.getCreatename() + "===>添加用户!==>"+ Util.reflectTest(sUser));
		} catch (Exception e) {
			con.rollback();
			logger.error(DateUtils.getNowDateTime() + "===>"+ sUser.getCreatename() + "===>添加用户时出现异常:"+ e.getMessage());
			throw e;
		} finally {
			DBManager.closeCon(con);
		}
		return result;
	}

	public int saveUser(SUser sUser) {
		int num = -1;
		try {
			sql = "insert into s_user(loginname,loginpass,usercode,truename,englishname,chinesename,email,idcard,sex,birthday,headimage, registration,address,dept,postion,createname,createdate,modifier,modifyDate,sfyx)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, sUser.getLoginname());
			ps.setString(2, new DesUtils(sUser.getLoginname()).encrypt(sUser.getLoginpass()));
			ps.setString(3, sUser.getUsercode());
			ps.setString(4, sUser.getTruename());
			ps.setString(5, sUser.getEnglishname());
			ps.setString(6, sUser.getChinesename());
			ps.setString(7, sUser.getEmail());
			ps.setString(8, sUser.getIdcard());
			ps.setString(9, sUser.getSex());
			ps.setString(10, sUser.getBirthday());
			ps.setString(11, sUser.getHeadimage());
			ps.setString(12, sUser.getRegistration());
			ps.setString(13, sUser.getAddress());
			ps.setString(14, sUser.getDept());
			ps.setString(15, sUser.getPostion());
			ps.setString(16, sUser.getCreatename());
			ps.setString(17, sUser.getCreatedate());
			ps.setString(18, sUser.getModifier());
			ps.setString(19, sUser.getModifyDate());
			ps.setString(20, sUser.getSfyx());
			num = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				num = Integer.parseInt(rs.getObject(1).toString());
			} else {
				num = -1;
			}

			logger.info(DateUtils.getNowDateTime() + "===>"
					+ sUser.getCreatename() + "===>添加用户!==>"
					+ Util.reflectTest(sUser));
		} catch (Exception e) {
			e.printStackTrace();
			logger
					.error(DateUtils.getNowDateTime() + "===>"
							+ sUser.getCreatename() + "===>添加用户时出现异常:"
							+ e.getMessage());
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}

	/**
	 * 根据用户名和原密码修改成新密码
	 * @param username
	 * @param nowpassword
	 * @param newpassword
	 * @return
	 * @throws Exception 
	 */
	public String changePassword(String username, String nowpassword,
			String newpassword) throws Exception {
		String result="";
		try {
			String sql = "update s_user set loginpass=? where loginname=? and loginpass=? ";
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			ps.setString(1,new DesUtils(username).encrypt(newpassword));
			ps.setString(2,username);
			ps.setString(3, new DesUtils(username).encrypt(nowpassword));
			int num = ps.executeUpdate();	
			if( num > 0){
				result=Util.getMsgJosnObject_success();
			}else{
				result=Util.getMsgJosnObject("exception", "原始密码有误!");
			}
		} catch (Exception e) {
			throw e;
		}finally{
			DBManager.closeCon(con);
		}
		return result;
	}
}
