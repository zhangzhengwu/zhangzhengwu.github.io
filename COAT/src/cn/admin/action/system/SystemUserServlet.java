package cn.admin.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import util.DateUtils;
import util.Page;
import util.Util;
import cn.admin.dao.impl.system.SGroupDaoImpl;
import cn.admin.dao.impl.system.SRoleDaoImpl;
import cn.admin.dao.impl.system.SUserDaoImpl;
import cn.admin.dao.impl.system.SUsermenuDaoImpl;
import cn.admin.dao.system.SGroupDao;
import cn.admin.dao.system.SRoleDao;
import cn.admin.dao.system.SUserDao;
import cn.admin.dao.system.SUsermenuDao;
import cn.admin.entity.system.SGroup;
import cn.admin.entity.system.SGroupmenu_Param;
import cn.admin.entity.system.SMenuBasic;
import cn.admin.entity.system.SMenuRole_mix;
import cn.admin.entity.system.SUser;
import cn.admin.entity.system.SUser_Login;
import cn.admin.entity.system.SUsermenu_Param;
import cn.admin.util.DesUtils;
import entity.S_user;



public class SystemUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(SystemUserServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	Map<String,Object> user = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		String result="";
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("loginUser") == null
					&& !method.equals("login")) {
				response.sendRedirect("singin.jsp");
				return;
			} else if (!method.equals("login")) {
				user = (Map<String, Object>) session.getAttribute("loginUser");
			}
			if (method.equals("selectLoginerMenu")) {
				selectLoginerMenu(request, response);
			}else if (method.equals("select")){
				select(request, response);
			}else if (method.equals("delUser")){
				delUser(request, response);
			}else if (method.equals("saveUser")) {
				result=addUser(request, response);
			} else if (method.equals("modifyUser")) {
				result=modifyUser(request, response);
			} else if (method.equals("detailUser")) {
				detailUser(request, response);
			} else if (method.equals("loadRoleUser")) {
				loadRoleUser(request, response);
			} else if (method.equals("SaveRoleUser")) {
				result=saveRoleUser(request, response);
			} else if (method.equals("loadGroup")) {
				loadGroup(request, response);
			} else if (method.equals("getPersonalInfo")) {
				getPersonalInfo(request, response);
			} else if (method.equals("verify")){
				verify(request,response);
			} else if(method.equals("change")){
				change(request,response);
			}  else if(method.equals("updatePassword")){
				result=updatePassword(request, response);
		    }
			else {
				throw new Exception("Unauthorized access!");
			}
		} catch (NullPointerException e) {
			result=Util.joinException(e);
			log.error("SystemUserServlet==>" + method + "操作异常：空值==" + e);
		} catch (Exception e) {
			log.error("SystemUserServlet==>" + method + "操作异常：" + e);
			result=Util.joinException(e);
		} finally {
			if(!Util.objIsNULL(result)){
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
				out.close();
			}
		}
	}

	void getPersonalInfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("WEB-INF/root/system/Personal.jsp").forward(request,
				response);
		
	}
	
	/**
	 * 保存用户菜单权限信息
	 * @author kingxu
	 * @date 2015-12-23
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @return void
	 */
	String saveRoleUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result="";
		try{
			String strLists = request.getParameter("paramList");//获取前台菜单数据
			Integer userid = Integer.parseInt(request.getParameter("userid"));
			HttpSession session = request.getSession();
			Map<String,Object> loginuser = (Map<String,Object>) session.getAttribute("loginUser");
			JSONArray jsonArray = JSONArray.fromObject(strLists);//将菜单数据转成Json数组
			List<SUsermenu_Param> pList = JSONArray.toList(jsonArray,SUsermenu_Param.class);//将json数组转成集合
			SUsermenuDao usermenuDao = new SUsermenuDaoImpl();
			Map<Integer,Object> map=usermenuDao.findMenuRoleMapByUserId(loginuser.get("userid")+"");//获取当前操作人权限列表
			List<SUsermenu_Param> actualList=new ArrayList<SUsermenu_Param>();
			for(int i=0;i<pList.size();i++){
				SUsermenu_Param m=pList.get(i);//被操作人menu权限
				SGroupmenu_Param l=(SGroupmenu_Param) map.get(m.getMenuid());//操作人menu权限
				if(Util.objIsNULL(l)){
					throw new RuntimeException("非法授权[授权人没有相关菜单的操作权限]");
				}
				
				if(Integer.parseInt(l.getAdd())<=0){
					m.setAdd("-1");
				}
				if(Integer.parseInt(l.getDel())<=0){
					m.setDel("-1");
				}
				if(Integer.parseInt(l.getSel())<=0){
					m.setSel("-1");
				}
				if(Integer.parseInt(l.getUpd())<=0){
					m.setUpd("-1");
				}
				if(Integer.parseInt(l.getExp())<=0){
					m.setExp("-1");
				}
				if(Integer.parseInt(l.getAudit())<=0){
					m.setAudit("-1");
				}
				if(Integer.parseInt(l.getOther())<=0){
					m.setOther("-1");
				}
				m.setUserid(userid);
				actualList.add(m);
				
			}
			if(pList.size()<=0){
				throw new RuntimeException("提交的数据中没有菜单项");
			}else{
				
				SUsermenuDao sUsermenuDao = new SUsermenuDaoImpl();
				result=sUsermenuDao.saveUserMenuRole(actualList, loginuser.get("username")+"");
				
			}
			
		}catch (Exception e) {
			result=Util.joinException(e);
			
		}
		
		
		return result;
	}
	

	void saveRoleUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map map = new HashMap();
		String Stu = "1";
		String Msg = "";
		try {
			String userid = request.getParameter("userid");
			if (userid.equals("")) {
				Stu = "-1";
				Msg = "没有操作的用户ID！";
			} else {
				String strLists = request.getParameter("paramList");
				if (strLists.equals("") || strLists.equals("-1")) {
					Stu = "-1";
					Msg = "参数解析异常，操作失败！";
				} else {
					HttpSession session = request.getSession();
					SUser_Login loginuser = (SUser_Login) session
							.getAttribute("loginUser");
					if (loginuser.getUserid() == Integer.parseInt(userid)) {
						Stu = "-1";
						Msg = "不能自己操作自己的权限！";
					} else {
						int successCount = 0;
						int failedCount = 0;
						JSONArray jsonArray = JSONArray.fromObject(strLists);
						List<SUsermenu_Param> pList = JSONArray.toList(
								jsonArray, SUsermenu_Param.class);
						int count = pList.size();
						if (count > 0) {
							SUsermenuDao sUsermenuDao = new SUsermenuDaoImpl();
							int i = 0;
							while (i < count) {
								// temp前台传过来的记录
								SUsermenu_Param temp = pList.get(i);
								int usermenuid = temp.getUsermenuid();
								// 根据被操作的人的ID，和menuid来找。
								List<SUsermenu_Param> items = sUsermenuDao
										.findUserMenuByMenuid2(usermenuid);
								if (items.size() == 0) {
									// 如果不存在这个数据那么直接继续下一条
									i++;
									continue;
								} else {
									// true is modify ,false is add,default
									// true;
									boolean flag = true;
									// 首先判断这个menuid的记录是操作人的，还是被操作人的。
									// 目前先假设是被操作人的。
									int temp1 = Integer.parseInt(userid);// 被操作
									int temp2 = loginuser.getUserid();// 操作人，就是登陆人。
									if (items.get(0).getUserid() == temp2) {
										// 如果这个记录是操作人的，那么被操作人是不存在这个记录的所有需要添加
										flag = false;
									}
									SUsermenu_Param item = new SUsermenu_Param();
									// item通过前台传过来的对象查找 数据库得到的记录
									item = items.get(0);
									SUsermenu_Param itemOld = item.clone();

									if (!temp.getAdd().equals("-1")
											&& (temp.getAdd().equals("N") || temp
													.getAdd().equals("Y"))) {
										item.setAdd(temp.getAdd());
									}
									if (!temp.getAudit().equals("-1")
											&& (temp.getAudit().equals("N") || temp
													.getAudit().equals("Y"))) {
										item.setAudit(temp.getAudit());
									}
									if (!temp.getDel().equals("-1")
											&& (temp.getDel().equals("N") || temp
													.getDel().equals("Y"))) {
										item.setDel(temp.getDel());
									}
									if (!temp.getExp().equals("-1")
											&& (temp.getExp().equals("N") || temp
													.getExp().equals("Y"))) {
										item.setExp(temp.getExp());
									}
									if (!temp.getOther().equals("-1")
											&& (temp.getOther().equals("N") || temp
													.getOther().equals("Y"))) {
										item.setOther(temp.getOther());
									}
									if (!temp.getSel().equals("-1")
											&& (temp.getSel().equals("N") || temp
													.getSel().equals("Y"))) {
										item.setSel(temp.getSel());
									}
									if (!temp.getUpd().equals("-1")
											&& (temp.getUpd().equals("N") || temp
													.getUpd().equals("Y"))) {
										item.setUpd(temp.getUpd());
									}
									if (itemOld.equals(item)) {
										// 如果对比结果一样但是flag是false
										if (flag == false) {
											// add
											// 如果是添加的操作那么，添加的这个记录就跟操作人的记录信息是一致的，除了userid不一样
											// 这里的temp1代表呗操作人
											item.setUserid(temp1);

											if (sUsermenuDao.addItem(item, user.get("loginname")+"") > 0) {
												successCount++;
											} else {
												failedCount++;
											}
										}
									} else {
										// 数据对比后继续数据库的操作
										if (flag == true) {
											// modify

											if (sUsermenuDao
													.modifyUserMenuByMenuid(item) > 0) {
												successCount++;
											} else {
												failedCount++;
											}
										} else if (flag == false) {
											// add
											// 如果是添加的操作那么，添加的这个记录就跟操作人的记录信息是一致的，除了userid不一样
											// 这里的temp1代表呗操作人
											item.setUserid(temp1);
											if (sUsermenuDao.addItem(item, user.get("loginname")+"") > 0) {
												successCount++;
											} else {
												failedCount++;
											}
										}
									}

								}
								i++;
							}
							if (failedCount != 0) {
								Stu = "1";
								Msg = "操作成功【" + successCount + "】条！\r\n 操作失败【"
										+ failedCount + "】条！";
							} else {
								Stu = "1";
								Msg = "操作成功【" + successCount + "】条！";
							}
						} else {
							// 没有需要更改的数据！
							Stu = "-1";
							Msg = "没有需要更改的数据！";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("Exception:" + e.getMessage());
		} finally {
			map.put("Stu", Stu);
			map.put("Msg", Msg);
			JSONObject json = JSONObject.fromObject(map);
			response.getWriter().print(json);
		}
	}

	/**
	 * 加载用户权限
	 * 
	 * @param request
	 * @param response
	 */
	void loadRoleUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			if (userid.equals("")) {
				List<SMenuRole_mix> menuList = new ArrayList<SMenuRole_mix>();
				request.setAttribute("menuList", menuList);
				request.getRequestDispatcher("WEB-INF/root/system/UserManager_role.jsp")
						.forward(request, response);
			} else {
				HttpSession session = request.getSession();
				Map<String,Object> loginuser= (Map<String, Object>) session.getAttribute("loginUser");
				SUsermenuDao usermenuDao = new SUsermenuDaoImpl();
				List<Map<String,Object>> list=usermenuDao.findMenuRoleByUserId(Integer.parseInt(loginuser.get("userid")+""));
				request.setAttribute("BaseMenuList", list);

				List<SMenuRole_mix> menuLists = usermenuDao.findAllUserMenu(Integer.parseInt(userid));
				request.setAttribute("modifyGMenuList", menuLists);
				request.setAttribute("userid", userid);
				request.getRequestDispatcher("WEB-INF/root/system/UserManager_role.jsp")
						.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	/**
	 * 添加用户
	 * 
	 * @param request
	 * @param response
	 */
	String addUser(HttpServletRequest request, HttpServletResponse response) {
		String result="";
		try {
			HttpSession session = request.getSession();
			/* 首先取得jsp页面传来的参数信息 用户名，密码，验证码 */
			String address = request.getParameter("address");
			String groupid = request.getParameter("groupid");
			String birthday = request.getParameter("birthday");
			String chinesename = request.getParameter("chinesename");
			String dept = request.getParameter("dept");
			String englishname = request.getParameter("englishname");
			// String headimage = request.getParameter("headimage");
			String idcard = request.getParameter("idcard");
			String loginname = request.getParameter("loginname");
			String loginpass = request.getParameter("loginpass");
			String registration = request.getParameter("registration");
			String postion = request.getParameter("postion");
			String sex = request.getParameter("sex");
			String sfyx = request.getParameter("allowLogin");
			String truename = request.getParameter("truename");
			String usercode = request.getParameter("usercode");
			String email=request.getParameter("email");
			String errMessage = "";
			SUserDao sUserDao = new SUserDaoImpl();
			SUser sUser = new SUser();
			String regex_Date = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
			/* 验证输入信息的完整行和正确性 */
			/* 先检查用户是否已经存在了 */
			if (sUserDao.findUserByloginname(loginname) != null) {
				errMessage = "该用户已经存在了,请使用别的名称作为登录账号！";
			}/* else if (truename.equals("") || truename.equals("")
					|| usercode.equals("") || loginname.equals("")
					|| loginpass.equals("") || englishname.equals("")
					|| chinesename.equals("") ) {
				errMessage = "以下内容不能为空：<br>【Login Code】<br>" 
						+ "【User Name】<br>" + "【User Password】<br>"
						+ "【True Name】<br>" + "【English Name】<br>"
						+ "【Chinese Name】";
			} else if (!sex.equals("男") & !sex.equals("女")) {
				errMessage = "性别输入错误!";
			} else if (!sfyx.equals("Y") && !sfyx.equals("N")) {
				errMessage = "allowLogin输入错误!";
			} else if (!birthday.matches(regex_Date)) {
				errMessage = "birthday输入格式错误，正确格式如：【0000-00-00】!";
			}*/ else if (groupid.equals("")) {
				errMessage = "没有获取到group数据!";
			}
			String[] groupidList = groupid.split(",");
			if (groupidList.length == 0) {
				errMessage = "没有勾选组别信息不能添加职员！";
			}

			// SimpleDateFormat sdf = new SimpleDateFormat(
			// "yyyy-MM-dd HH:mm:ss aa");
			// String temp_str = sdf.format(timeNow);
			sUser.setAddress(address);
			sUser.setBirthday(birthday);
			sUser.setChinesename(chinesename);
			sUser.setCreatedate(DateUtils.getDateToday());
			sUser.setCreatename((String) session.getAttribute("name"));
			sUser.setDept(dept);
			sUser.setEnglishname(englishname);
			sUser.setHeadimage("");
			sUser.setIdcard(idcard);
			sUser.setLoginname(loginname);
			sUser.setLoginpass(loginpass);
			sUser.setPostion(postion);
			sUser.setRegistration(registration);
			sUser.setSex(sex);
			sUser.setSfyx(sfyx);
			SimpleDateFormat bartDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			sUser.setCreatename(user.get("loginname")+"");
			sUser.setCreatedate(bartDateFormat.format(date));
			sUser.setTruename(truename);
			sUser.setUsercode(usercode);
			sUser.setEmail(email);
			
			/* 如果验证没有通过提示错误信息 */
			if (errMessage.equals("")) {
				result=sUserDao.saveUserAndsaveGroup(sUser, groupidList, user.get("loginname")+"") ;
			/*	if (sUserDao.saveUserAndsaveGroup(sUser, groupidList, user.get("loginname")+"") == -1) {
					out.print("falied!");
				} else {
					out.print("success");
				}*/

			} else {
				result=Util.getMsgJosnObject("error",errMessage);
				//out.print("error:" + errMessage);
			}

		} catch (Exception e) {
			result=Util.joinException(e);
			log.error("User Logining Exception :" + e.getMessage());
		} finally {
		}
		return result;
	}

	 

	
	
	void selectLoginerMenu(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			if (Util.objIsNULL(session.getAttribute("loginUser"))) {
				return;
			}
			Map<String,Object> map=(Map<String, Object>) session.getAttribute("loginUser");
			// 还没有做登录的组别选择。所以这里先随便写一个
			SUsermenuDao usermenuDao = new SUsermenuDaoImpl();
			List<Map<String,Object>> list=usermenuDao.findMenuRoleByUserId(Integer.parseInt(map.get("userid")+""));
			request.setAttribute("menuList", list);

			RequestDispatcher wm = request
					.getRequestDispatcher("WEB-INF/root/system/menu.jsp");
			wm.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
	}

	/**
	 * 分页查询用户信息
	 * 
	 * @param request
	 * @param response
	 */
	void select(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		SUserDao sUserDao = new SUserDaoImpl();
		Page page = new Page();
		List<SUser> list = new ArrayList<SUser>();
		List<Object> list1 = new ArrayList<Object>();
		try {
			out = response.getWriter();
			if (request.getParameter("menuid") == null
					|| request.getParameter("menuid").equals("")) {
				out.print("[]");
				return;
			}
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String loginname = request.getParameter("loginname");
			String usercode = request.getParameter("usercode");
			String englishname = request.getParameter("englishname");
			String chinesename = request.getParameter("chinesename");
			String sfyx = request.getParameter("status");
			String pageNow = request.getParameter("pageNow");
			String menuid = request.getParameter("menuid");
			String dept=request.getParameter("dept");
			SUsermenuDao uroleDao = new SUsermenuDaoImpl();
			SMenuBasic rs = uroleDao.findMenubyusermenu(Integer.parseInt(user.get("userid")+""),
					Integer.parseInt(menuid));
			if (rs == null) {
				list1.add(0, list);// 数据存放
				list1.add(1, page);// 存放page参数
				list1.add(2, rs);
			} else {

				page.setAllRows(sUserDao.findUserRows(loginname, usercode,
						englishname, chinesename, startDate, endDate, sfyx,dept));
				page.setPageSize(15);// 设置页面显示行数
				page.setCurPage(Integer.parseInt(pageNow));// 获取页面当前页码
				list = sUserDao.findUser(loginname, usercode, englishname,
						chinesename, startDate, endDate, sfyx,dept, page);

				list1.add(0, list);// 数据存放
				list1.add(1, page);// 存放page参数
				list1.add(2, rs);
			}
			JSONArray jsons = JSONArray
					.fromObject(
							list1,
							Util
									.filter("loginpass,truename,idcard,birthday,headimage,address"
											.split(",")));
			out.print(jsons.toString());
			jsons = null;
			list1 = null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("User Search Exception :" + e.getMessage());
			out.print("Search Exception :" + e.getMessage());
		} finally {
			list = null;
			out.flush();
			out.close();
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param response
	 */
	void delUser(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int userid = Integer.parseInt(request.getParameter("userid"));
			SUserDao sUserDao = new SUserDaoImpl();
			int num = sUserDao.RemoveUser(userid, user.get("loginname")+"");// 删除用户
			if (num > 0) {// 如果删除条数大于0
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("Delete User Exception:" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}

	}

	/**
	 * 根据userid获取user的详细信息
	 * 
	 * @param request
	 * @param response
	 */
	void detailUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			int userid = Integer.parseInt(request.getParameter("userid"));

			SUserDao sUserDao = new SUserDaoImpl();
			SUser user = sUserDao.findUserById(userid);
			request.setAttribute("userInfo", user);
			SGroupDao sgroupDao = new SGroupDaoImpl();
			List<SGroup> gList = sgroupDao.findAllGroupMenu();
			SRoleDao r=new SRoleDaoImpl();
			List<SGroup> rList=r.findGroupListByuserid(userid);
			int defaultGroup=-1;
			for(int i=0;i<rList.size();i++){
				if("1".equalsIgnoreCase(rList.get(i).getIsoption())){
					defaultGroup=rList.get(i).getGroupid();
					i=rList.size();
				}
			}
			
			request.setAttribute("gList", gList);
			request.setAttribute("rList", rList);
			request.setAttribute("defaultGroup",defaultGroup);
			request.getRequestDispatcher("WEB-INF/root/system/UserManager_modify.jsp")
					.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	void loadGroup(HttpServletRequest request, HttpServletResponse response) {
		try {
			// load group list
			SGroupDao sgroupDao = new SGroupDaoImpl();
			List<SGroup> gList = sgroupDao.findAllGroupMenu();
			request.setAttribute("gList", gList);
			request.getRequestDispatcher("WEB-INF/root/system/UserManager_info.jsp").forward(
					request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	/**
	 * 修改user的信息
	 * 
	 * @param request
	 * @param response
	 */
	String modifyUser(HttpServletRequest request, HttpServletResponse response) {
		String result="";
		try {
			String regex_Date = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
			SUserDao sUserDao = new SUserDaoImpl();
			SUser sUser = new SUser();
			BeanUtils.populate(sUser, request.getParameterMap());
			sUser.setModifier(user.get("loginname")+"");
			sUser.setModifyDate(DateUtils.getDateToday());
			sUser.setSfyx(request.getParameter("allowLogin"));
			String groupid = request.getParameter("groupid");
			String[] groupidList = groupid.split(",");
			if (groupidList.length == 0) {
				throw new RuntimeException("请选择用户角色");
			}
			String rpassword=request.getParameter("rpassword");
			if("on".equals(rpassword)){
				sUser.setLoginpass(new DesUtils(sUser.getLoginname()).encrypt(Util.getProValue("public.system.defaultpassword")));
			}
			
			
			result= sUserDao.modifyUser(sUser,groupidList);

		} catch (Exception e) {
			result=Util.joinException(e);
		} 
		return result;
	}
	 /**
	 * 原密码验证
	 */
   void verify(HttpServletRequest request,HttpServletResponse response){
	     String message="";
	     PrintWriter out=null;
	   try {
		   out=response.getWriter();
		   //验证session是否为空
		    String  adminUsername =request.getSession().getAttribute("adminUsername").toString();
		    if(Util.objIsNULL(adminUsername)){
		    	message="null";
		    }
		   //获取原密码
		   String nowPass=request.getParameter("nowPassword");
		   SUserDao sUserDao=new SUserDaoImpl();
		   if(sUserDao.VailPassword(adminUsername, nowPass)){
			   //原密码正确
			   message="success";
		   }else{
			   message="error";
		   }
	   } catch (Exception e) {
		    message="核对原密码出现问题："+e.getMessage();
		    e.printStackTrace();
	}finally{
		out.print(message);
		out.flush();
		out.close();
	 }
   }
   /**
    * 修改密码
    */
    void   change(HttpServletRequest request,HttpServletResponse response){
    	 String message="";
  	     PrintWriter out=null;
  	 try {
  	        out=response.getWriter();
  	        //验证session是否为空
		    String  adminUsername =request.getSession().getAttribute("adminUsername").toString();
		    if(Util.objIsNULL(adminUsername)){
		    	message="null";
		    }
		   //获取原密码
		   String newPass=request.getParameter("newPassword");
		   SUserDao sUserDao=new SUserDaoImpl();
  	      if(sUserDao.modifyPassword(adminUsername, newPass)>0){
  	    	  message="success";
  	      } else{
  	    	  message="error";
  	      }
		 } catch (Exception e) {
			 message="Have a  Exception"+e.getMessage();
			e.printStackTrace();
		 }finally{
				out.print(message);
				out.flush();
				out.close();
		 }
    } 
    
    

	/**
	 * 设置新密码前密码  <密码保存从 admin表转至s_user表>
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @throws ServletException
	 * @throws IOException
	 */
	String updatePassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String result="";
		try{
			SUserDao sUserDao=new SUserDaoImpl();
			String username = ""+user.get("loginname");
			String nowpassword = request.getParameter("nowPassword");
			String newpassword = request.getParameter("newPassword");
			result=sUserDao.changePassword(username, nowpassword, newpassword);
		}catch (Exception e) {
			throw e;
		}finally{
		}
		return result;
	}
}
