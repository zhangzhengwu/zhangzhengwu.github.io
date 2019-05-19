package cn.admin.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import util.Page;
import util.Util;
import cn.admin.dao.impl.system.SGroupDaoImpl;
import cn.admin.dao.impl.system.SMenuRoleDaoImpl;
import cn.admin.dao.impl.system.SUsermenuDaoImpl;
import cn.admin.dao.system.SGroupDao;
import cn.admin.dao.system.SMenuRoleDao;
import cn.admin.dao.system.SUsermenuDao;
import cn.admin.entity.system.SGroup;
import cn.admin.entity.system.SGroupRole_mix;
import cn.admin.entity.system.SGroupmenu_Param;
import cn.admin.entity.system.SMenuBasic;
import cn.admin.entity.system.SMenuRole_mix;
import cn.admin.entity.system.SUser_Login;

public class SystemGroupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map<String,Object> user = null;
	Logger log = Logger.getLogger(SystemGroupServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		String method = request.getParameter("method");
		String result="";
		try {
			HttpSession session = request.getSession();
			if (Util.objIsNULL(session.getAttribute("loginUser"))) {
				response
						.getWriter()
						.println(
								"<script type=\"text/javascript\">top.location.href='index.jsp'</script>");
				// response.sendRedirect("root/login2.jsp");
				return;
			} else {
				user = (Map<String, Object>) session.getAttribute("loginUser");
			}
			if (method.equals("select")) {
				select(request, response);
			} else if (method.equals("delGroup")) {
				delGroup(request, response);
			} else if (method.equals("detailGroup")) {
				detailGroup(request, response);
			} else if (method.equals("modifyGroup")) {
				modifyGroup(request, response);
			} else if (method.equals("addGroup")) {
				addGroup(request, response);
			} else if (method.equals("loadRoleGroup")) {
				loadRoleGroup(request, response);
			} else if (method.equals("saveRoleGroup")) {
				result=saveRoleGroup(request, response);
			} else if (method.equals("changeGroup")) {
				changeGroup(request, response);
			} else if (method.equals("getGroupList")) {
				getGroupList(request, response);
			}else if (method.equals("searchGroup")) {
				searchGroup(request, response);
			} else {
				throw new Exception("Unauthorized access!");
			}

		} catch (NullPointerException e) {
			result=Util.joinException(e);
			log.error("SystemGroupServlet==>" + method + "操作异常：空值==" + e);
		} catch (Exception e) {
			result=Util.joinException(e);
			log.error("SystemGroupServlet==>" + method + "操作异常：" + e);
		} finally {
			if(!Util.objIsNULL(result)){
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
				out.close();
			}
		}
	}

	/*
	 * 获取登录人的组别
	 */
	void getGroupList(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			HttpSession session = request.getSession();
			if ((SUser_Login) session.getAttribute("loginUser") == null) {
				out.print(Util.getMsgJosnObject("exception", "没有登录不能获取组别！"));
			} else {
				SUser_Login loginUser = (SUser_Login) session
						.getAttribute("loginUser");
				if (loginUser.getUserid() == null) {
					out.print(Util.getMsgJosnObject("exception", "没有登录不能获取组别！"));
				} else {
					if (loginUser.getGroupList() == null) {
						out.print(Util.getMsgJosnObject("exception", "组别获取异常！"));
					} else {
						Map map = new HashMap();
						map.put("Stu", "1");
						map.put("Msg", loginUser.getGroupList());
						JSONObject json = JSONObject.fromObject(map);
						out.print(json.toString());
					}
				}
			}

		} catch (Exception e) {
			out.print(Util.getMsgJosnObject("exception", "服务器内部错误！"));
			e.printStackTrace();
			log.error("User Logining Exception :" + e.getMessage());
			out.print("Login Vaild Exception :" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}
	}

	/*
	 * 切换登录用户的角色 *
	 */
	void changeGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String errMessage="";
		try {
			String groupid = request.getParameter("groupid");
			if (groupid.equals("")) {
				//response.getWriter().print(Util.toJsonString("-1", "没有组别ID不能切换！"));
				errMessage+="没有组别ID不能切换!";
				return;
			} else {
				// 是否登录
				HttpSession session = request.getSession();
				if ((SUser_Login) session.getAttribute("loginUser") == null) {
					//response.getWriter().print(Util.toJsonString("-2", "没有登录不能切换！"));
					errMessage+="没有登录不能切换!";
					return;
				} else {
					int gid = Integer.parseInt(groupid);
					SUser_Login loginuser = (SUser_Login) session
							.getAttribute("loginUser");
					// 检查groupid是否存在,如果存在就更新，登录用户的默认加载组
					List<SGroup> glist = loginuser.getGroupList();
					int flag = 1;
					String gName = "";
					String gStu = "";
					String roleStu = "";
					for (SGroup g : glist) {
						// 切换的时候都设置为未默认
						g.setIsoption("0");
						if (g.getGroupid() == gid) {
							if (g.getSfyx().equals("N")) {
								flag = -1;
							} else {
								g.setIsoption("1");
								flag = 0;
								gName = g.getGroupname();
								gStu = g.getGroupStu();
								roleStu = g.getRoleStu();
							}
							// break;

						}

					}
					loginuser.setGroupList(glist);
					SGroupDao sGroupDao = new SGroupDaoImpl();
					if (flag == -1) {
						//response.getWriter().print(Util.toJsonString("-1", "组别失效！"));
						errMessage+="组别加载失败!";
						return;
					}
					if (flag == 0) {
						// update table s_role isoption Fileds
						if (sGroupDao.updateRoleIsoption(gid, loginuser
								.getUserid(), "1") != -1) {
							loginuser.setGroupid(gid);
							loginuser.setGroupname(gName);
							loginuser.setGroupStu(gStu);
							loginuser.setRoleStu(roleStu);

							session.setAttribute("loginUser", loginuser);
							//response.getWriter().print(Util.toJsonString("1", "切换成功"));
							
						} else {

							//response.getWriter().print(Util.toJsonString("-1", "切换操作失败！"));
							errMessage+="切换操作失败!";
							return;
						}
					} else {
						//response.getWriter().print(Util.toJsonString("-1", "组别ID不合法！"));
						errMessage+="组别ID不合法!";
						return;
					}

				}
			}
		} catch (Exception e) {
			//response.getWriter().print(Util.toJsonString("-1", "切换失败，服务器内部错误！"));
			errMessage+="切换失败，服务器内部错误!";
			e.printStackTrace();
		} finally {
			request.setAttribute("errMessage",errMessage);
				request.getRequestDispatcher("WEB-INF/root/system/main.jsp").forward(request, response);
				
		}
	}

	void loadRoleGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String groupid = request.getParameter("groupid");
			if (Util.objIsNULL(groupid)) {
				List<SMenuRole_mix> loginGMenuList = new ArrayList<SMenuRole_mix>();
				request.setAttribute("loginGMenuList", loginGMenuList);
				request.getRequestDispatcher("WEB-INF/root/system/GroupManager_role.jsp")
						.forward(request, response);
			} else {
				SGroupDao sGroupDao = new SGroupDaoImpl();
				HttpSession session = request.getSession();
				Map<String,Object> loginuser = (Map<String, Object>) session.getAttribute("loginUser");
				SUsermenuDao usermenuDao = new SUsermenuDaoImpl();
				List<Map<String,Object>> list=usermenuDao.findMenuRoleByUserId(Integer.parseInt(loginuser.get("userid")+""));
				request.setAttribute("BaseMenuList", list);
				List<SGroupRole_mix> modifyGMenuList = new ArrayList<SGroupRole_mix>();
				// operation groupid.
				modifyGMenuList = sGroupDao.findAllGroupMenuByGroupid(Integer
						.parseInt(groupid));
				
				
				request.setAttribute("groupid", groupid);

				request.setAttribute("modifyGMenuList", modifyGMenuList);
				request.getRequestDispatcher("WEB-INF/root/system/GroupManager_role.jsp")
						.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
	
 
	
	/**
	 * 保存权限组菜单权限信息
	 * @author kingxu
	 * @date 2015-12-23
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @return void
	 */
	String saveRoleGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result="";
		try{
			String strLists = request.getParameter("paramList");//获取前台菜单数据
			Integer groupid = Integer.parseInt(request.getParameter("groupid"));
			HttpSession session = request.getSession();
			Map<String,Object> loginuser = (Map<String,Object>) session.getAttribute("loginUser");
			JSONArray jsonArray = JSONArray.fromObject(strLists);//将菜单数据转成Json数组
			List<SGroupmenu_Param> pList = JSONArray.toList(jsonArray,SGroupmenu_Param.class);//将json数组转成集合
			SUsermenuDao usermenuDao = new SUsermenuDaoImpl();
			Map<Integer,Object> map=usermenuDao.findMenuRoleMapByUserId(loginuser.get("userid")+"");//获取当前操作人权限列表
			List<SGroupmenu_Param> actualList=new ArrayList<SGroupmenu_Param>();
			for(int i=0;i<pList.size();i++){
				SGroupmenu_Param m=pList.get(i);//被操作人menu权限
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
				m.setGroupid(groupid);
				actualList.add(m);
				
			}
			if(pList.size()<=0){
				throw new RuntimeException("提交的数据中没有菜单项");
			}else{
				SMenuRoleDao sMenuRoleDao = new SMenuRoleDaoImpl();
				result=sMenuRoleDao.saveMenuRole(actualList, loginuser.get("username")+"");
			}
			
			
			
		}catch (Exception e) {
			result=Util.joinException(e);
		}finally{
			
		}
		return result;
		
	}
	
	
	void saveRoleGroups(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map map = new HashMap();
		String Stu = "1";
		String Msg = "";
		try {

			String strLists = request.getParameter("paramList");//获取前台菜单数据
			String groupid = request.getParameter("groupid");
			if (groupid.equals("")) {
				Stu = "-1";
				Msg = "参数解析异常，操作失败！";

			} else {
				if (strLists.equals("") || strLists.equals("-1")) {
					Stu = "-1";
					Msg = "参数解析异常，操作失败！";
				} else {
					int successCount = 0;
					int failedCount = 0;
					JSONArray jsonArray = JSONArray.fromObject(strLists);//将菜单数据转成Json数组
					List<SGroupmenu_Param> pList = JSONArray.toList(jsonArray,SGroupmenu_Param.class);//将json数组转成集合
					int count = pList.size();
					if (count > 0) {
						HttpSession session = request.getSession();
						SUser_Login loginuser = (SUser_Login) session
								.getAttribute("loginUser");
						List<SGroup> glist = loginuser.getGroupList();
						int intGroupid = Integer.parseInt(groupid);
						int isGoOn = 1;
						for (SGroup g : glist) {
							if (g.getGroupid() == intGroupid) {
								isGoOn = 0;
								break;
							}
						}
						if (isGoOn == 0) {
							Stu = "-1";
							Msg = "自己不能编辑自己的组别！";
						} else {

							SMenuRoleDao sMenuRoleDao = new SMenuRoleDaoImpl();
							int i = 0;
							while (i < count) {
								// temp前台传过来的记录
								SGroupmenu_Param temp = pList.get(i);
								int menuroleid = temp.getMenuroleid();
								// 根据被操作的人的ID，和menuid来找。
								List<SGroupmenu_Param> items = sMenuRoleDao
										.findItemByMenuRoleId(menuroleid);
								if (items.size() == 0) {
									// 如果不存在这个数据那么直接继续下一条
									i++;
									continue;
								} else {
									// true is modify ,false is add,default
									// true;
									boolean flag = true;
									// 首先判断这个menuroleid的记录是操作人的，还是被操作人的。
									// 目前先假设是被操作人的。
									int temp1 = Integer.parseInt(groupid);// 被操作
									int temp2 = loginuser.getGroupid();// 操作人，就是登陆人。
									if (items.get(0).getGroupid() == temp2) {
										// 如果这个记录是操作人的，那么被操作人是不存在这个记录的所有需要添加
										flag = false;
									}
									SGroupmenu_Param item = new SGroupmenu_Param();
									// item通过前台传过来的对象查找 数据库得到的记录
									item = items.get(0);
									SGroupmenu_Param itemOld = item.clone();
									if (!temp.getAdd().equals("-1")&& (temp.getAdd().equals("N") || temp.getAdd().equals("Y"))) {
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
											// 如果是添加的操作那么，添加的这个记录就跟操作人的记录信息是一致的，除了GROUPID不一样
											// 这里的temp1代表呗操作人的组别ID
											item.setGroupid(temp1);

											if (sMenuRoleDao.addItem(item, user.get("loginname")+"") > 0) {
												successCount++;
											} else {
												failedCount++;
											}
										}
									} else {
										// 数据对比后继续数据库的操作
										if (flag == true) {
											// modify

											if (sMenuRoleDao
													.modifyMenuRoleByMenuRoleid(item) > 0) {
												successCount++;
											} else {
												failedCount++;
											}
										} else if (flag == false) {
											// add
											// 如果是添加的操作那么，添加的这个记录就跟操作人的记录信息是一致的，除了userid不一样
											// 这里的temp1代表呗操作人
											item.setGroupid(temp1);

											if (sMenuRoleDao.addItem(item, user.get("loginname")+"") > 0) {
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
						}
					} else {
						// 没有需要更改的数据！
						Stu = "-1";
						Msg = "没有需要更改的数据！";
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

	void addGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = null;
		Map map = new HashMap();
		SGroupDao sGroupDao = new SGroupDaoImpl();
		SGroup obj = new SGroup();
		String Msg = "";
		String Stu = "1";
		try {
			out = response.getWriter();
			BeanUtils.populate(obj, request.getParameterMap());
			SimpleDateFormat bartDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			obj.setCreateDate(bartDateFormat.format(date));
			obj.setCreatename(user.get("loginname")+"");
			obj.setModifyDate(null);
			if (sGroupDao.saveGroup(obj) > 0) {
				Msg = "操作成功";
				Stu = "1";
			} else {
				Msg = "操作失败";
				Stu = "-1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Group add Exception :" + e.getMessage());
			Stu = "-1";
			Msg = "Group add :" + e.getMessage();
		} finally {

			map.put("Stu", Stu);
			map.put("Msg", Msg);
			JSONObject json = JSONObject.fromObject(map);
			out.print(json);
			out.flush();
			out.close();

		}
	}

	void modifyGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = null;
		Map map = new HashMap();
		SGroupDao sGroupDao = new SGroupDaoImpl();
		SGroup obj = new SGroup();
		String Msg = "";
		String Stu = "1";
		try {
			out = response.getWriter();
			BeanUtils.populate(obj, request.getParameterMap());
			obj.setModifier(user.get("loginname")+"");
			SimpleDateFormat bartDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			obj.setModifyDate(bartDateFormat.format(date));
			if (sGroupDao.modifyGroup(obj) > 0) {
				Msg = "操作成功";
				Stu = "1";
			} else {
				Msg = "操作失败";
				Stu = "-1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Group modify Exception :" + e.getMessage());
			Stu = "-1";
			Msg = "Group modify Exception :" + e.getMessage();
		} finally {

			map.put("Stu", Stu);
			map.put("Msg", Msg);
			JSONObject json = JSONObject.fromObject(map);
			out.print(json);
			out.flush();
			out.close();

		}
	}

	void detailGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// PrintWriter out = null;
		// Map map = new HashMap();
		SGroupDao sGroupDao = new SGroupDaoImpl();
		SGroup obj = null;
		// String Msg = "";
		// String Stu = "1";
		try {
			// out = response.getWriter();
			if (request.getParameter("groupid") != null) {
				if (request.getParameter("groupid").equals("")) {
					request.setAttribute("obj", null);
				} else {
					int groupid = Integer.parseInt(request
							.getParameter("groupid"));
					obj = sGroupDao.findGroupById(groupid);
					request.setAttribute("obj", obj);

				}
			} else {
				request.setAttribute("obj", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Group Search Exception :" + e.getMessage());
			// Stu="-1";
			// Msg = "Search Exception :" + e.getMessage();
		} finally {
			request.getRequestDispatcher("WEB-INF/root/system/GroupManager_modify.jsp")
					.forward(request, response);
			/*
			 * map.put("Stu", Stu); map.put("Msg", Msg); JSONObject json =
			 * JSONObject.fromObject(map); out.print(json); out.flush();
			 * out.close();
			 */
		}
	}

	/**
	 * 分页查询Group信息
	 * 
	 * @param request
	 * @param response
	 */
	void select(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		SGroupDao sGroupDao = new SGroupDaoImpl();
		Page page = new Page();
		List<SGroup> list = new ArrayList<SGroup>();
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
			String groupcode = request.getParameter("groupcode");
			String groupname = request.getParameter("groupname");
			String menuid = request.getParameter("menuid");
			String sfyx = "";

			String pageNow = request.getParameter("pageNow");
			SUsermenuDao uroleDao = new SUsermenuDaoImpl();
			SMenuBasic rs = uroleDao.findMenubyusermenu(Integer.parseInt(user.get("userid")+""),
					Integer.parseInt(menuid));

			if (rs == null) {
				list1.add(0, list);// 数据存放
				list1.add(1, page);// 存放page参数
				list1.add(2, rs);
			} else {
				page.setAllRows(sGroupDao.findGroupRows(startDate, endDate,
						groupcode, groupname, sfyx));
				page.setPageSize(15);// 设置页面显示行数
				page.setCurPage(Integer.parseInt(pageNow));// 获取页面当前页码
				list = sGroupDao.findGroup(startDate, endDate, groupcode,
						groupname, sfyx, page);
				list1.add(0, list);// 数据存放
				list1.add(1, page);// 存放page参数
				list1.add(2, rs);
			}
			JSONArray jsons = JSONArray.fromObject(list1, Util
					.filter("modifier,modifyDate".split(",")));
			out.print(jsons.toString());
			jsons = null;
			list1 = null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Group Search Exception :" + e.getMessage());
			out.print("Search Exception :" + e.getMessage());
		} finally {
			list = null;
			out.flush();
			out.close();
		}
	}

	/**
	 * 删除组
	 * 
	 * @param request
	 * @param response
	 */
	void delGroup(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int groupid = Integer.parseInt(request.getParameter("groupid"));
			SGroupDao sGroupDao = new SGroupDaoImpl();
			int num = sGroupDao.removeGroup(groupid, user.get("loginname")+"");// 删除组信息
			if (num > 0) {// 如果删除条数大于0
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("Delete Group Exception:" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}

	}
	
	void searchGroup (HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out=response.getWriter();
			SGroupDao sGroupDao = new SGroupDaoImpl();
			List<SGroup> gList = sGroupDao.findAllGroupMenu();
			JSONArray json=JSONArray.fromObject(gList);
			out.print(json.toString());
		}catch (Exception e) {
			e.printStackTrace();
			out.print("Exception:"+e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
		
	}

}
