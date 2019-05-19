package cn.admin.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import util.DateUtils;
import util.Page;
import util.Util;
import cn.admin.dao.impl.system.SGroupDaoImpl;
import cn.admin.dao.impl.system.SMenuDaoImpl;
import cn.admin.dao.impl.system.SMenuRoleDaoImpl;
import cn.admin.dao.impl.system.SUsermenuDaoImpl;
import cn.admin.dao.system.SGroupDao;
import cn.admin.dao.system.SMenuDao;
import cn.admin.dao.system.SMenuRoleDao;
import cn.admin.dao.system.SUsermenuDao;
import cn.admin.entity.system.SMenu;
import cn.admin.entity.system.SMenuBasic;
import cn.admin.entity.system.SMenu_json;
import cn.admin.entity.system.SMenurole;
import cn.admin.entity.system.SUsermenu;


public class SystemMenuServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map<String,Object> user = null;
	Logger log = Logger.getLogger(SystemMenuServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");

		try {
			HttpSession session = request.getSession();
			if (Util.objIsNULL(session.getAttribute("loginUser"))) {
				response.getWriter().println(
								"<script type=\"text/javascript\">top.location.href='index.jsp'</script>");
				return;
			} else {
				user = (Map<String, Object>) session.getAttribute("loginUser");
			}
			if (method.equals("select")) {
				select(request, response);
			}else if (method.equals("delMenu"))
				delMenu(request, response);
			else if (method.equals("dropdownMenu"))
				dropdownMenu(request, response);
			else if (method.equals("saveMenu"))
				saveMenu(request, response);
			else if (method.equals("detailMenu"))
				detailMenu(request, response);
			else if (method.equals("modifyMenu"))
				modifyMenu(request, response);
			else if (method.equals("RoleMenu"))
				RoleMenu(request, response);
			else if (method.equals("saveRoleMenu"))
				saveRoleMenu(request, response);
			else if (method.equals("addParam"))
				addParam(request, response);
			else if (method.equals("saveGroupMenu"))
				saveGroupMenu(request, response);
			else if (method.equals("savePersonMenu"))
				savePersonMenu(request, response);
			else {
				throw new Exception("Unauthorized access!");
			}

		} catch (NullPointerException e) {
			log.error("SystemMenuServlet==>" + method + "操作异常：空值==" + e);
			response.getWriter().print(
					"Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("SystemMenuServlet==>" + method + "操作异常：" + e);
			response.getWriter().print("Exception:" + e.getMessage());
		} finally {
			method = null;
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
		SMenuDao sMenuDao = new SMenuDaoImpl();
		Page page = new Page();
		List<SMenu_json> list = new ArrayList<SMenu_json>();
		List<Object> list1 = new ArrayList<Object>();
		try {
			out = response.getWriter();
			if (request.getParameter("menuid") == null
					|| request.getParameter("menuid").equals("")) {
				out.print("[]");
				return;
			}
			String menuid = request.getParameter("menuid");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String menuname = request.getParameter("menuname");
			String parentMenuname = request.getParameter("parentMenuname");
			String pageNow = request.getParameter("pageNow");
			SUsermenuDao uroleDao = new SUsermenuDaoImpl();
			SMenuBasic rs = uroleDao.findMenubyusermenu(Integer.parseInt(user.get("userid")+""),
					Integer.parseInt(menuid));

			if (Util.objIsNULL(rs)) {
				list1.add(0, list);// 数据存放
				list1.add(1, page);// 存放page参数
				list1.add(2, rs);
			} else {
				page.setAllRows(sMenuDao.findMenu_json(startDate, endDate,
						menuname, parentMenuname));
				page.setPageSize(15);// 设置页面显示行数
				page.setCurPage(Integer.parseInt(pageNow));// 获取页面当前页码
				list = sMenuDao.findMenu_json(startDate, endDate, menuname,
						parentMenuname, page);

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
	 * 获取父菜单下拉列表
	 * 
	 * @param request
	 * @param response
	 */
	void dropdownMenu(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			SMenuDao sMenuDao = new SMenuDaoImpl();
			JSONArray jsons = JSONArray.fromObject(sMenuDao.findMenuList());
			out.print(jsons.toString());
		} catch (Exception e) {
			out.print("Loading Menu Exception:" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}
	}

	void saveMenu(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			
			
			// Admin admin=(Admin) request.getSession().getAttribute("admin");
			String menuname = request.getParameter("menuname");
			String menuAction = request.getParameter("menuAction");
			String parentId = request.getParameter("parentMenu");
			String remark = request.getParameter("remark");
			String childshort = "0";
			String parentshort = "0";
			if (parentId.equals("0"))
				parentshort = request.getParameter("sort");
			else
				childshort = request.getParameter("sort");

			SMenuDao sMenuDao = new SMenuDaoImpl();
			SMenu sMenu = new SMenu(menuname, menuAction, Integer
					.parseInt(parentId), Util.objIsNULL(childshort) ? null
					: Integer.parseInt(childshort),
					Util.objIsNULL(parentshort) ? null : Integer
							.parseInt(parentshort), remark,
					user.get("loginname")+"", DateUtils.getNowDateTime(), "", "",
					"Y");
			int num = sMenuDao.saveMenu(sMenu,Integer.parseInt(user.get("userid")+""));
			if (num > 0) {// 如果保存成功条数大于0
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("Save Menu Exception:" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}
	}
	
	
	void addParam(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			
			out = response.getWriter();
			SGroupDao sGroupDao = new SGroupDaoImpl();
			String menuid = request.getParameter("menuid");
			String str = request.getParameter("str");
			
			HttpSession session = request.getSession();
			Map<String,Object> loginuser = (Map<String, Object>) session.getAttribute("loginUser");		
			SUsermenuDao usermenuDao = new SUsermenuDaoImpl();
			Map<String, Object> singlemenu=usermenuDao.findSingleMenuRoleByUserId(Integer.parseInt(loginuser.get("userid")+""), Integer.parseInt(menuid));
			request.setAttribute("singlemenu", singlemenu);			
			request.setAttribute("menuid", menuid);			
			
			if(str.equals("group")){
			request.setAttribute("groupList", sGroupDao.findGroupList(menuid));
			request.getRequestDispatcher("WEB-INF/root/system/MenuManagerAddGroup.jsp")
					.forward(request, response);
			} else {
			request.setAttribute("personList", sGroupDao.findPersonList(menuid));
			request.getRequestDispatcher("WEB-INF/root/system/MenuManagerAddPerson.jsp")
					.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("Loading GroupParam Exception:" + e.getMessage());
		} finally {
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
	void delMenu(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int menuid = Integer.parseInt(request.getParameter("menuid"));
			SMenuDao sMenuDao = new SMenuDaoImpl();
			int childNum = sMenuDao.findChildMenu(menuid);// 获取子菜单个数
			if (childNum < 0) {// 获取子菜单个数异常
				out.print("error,Authentication anomalies");
			} else if (childNum == 0) {// 没有子菜单
				int num = sMenuDao.deleteMenu(menuid, user.get("loginname")+"");// 删除组信息
				if (num > 0) {// 如果删除条数大于0
					out.print("success");
				} else {
					out.print("error");
				}
			} else {// 存在子菜单关联项
				out.print("error,The Menu has child items");
			}

		} catch (Exception e) {
			e.printStackTrace();
			out.print("Delete Menu Exception:" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}

	}

	/**
	 * 根据menuid获取菜单详细
	 * 
	 * @param request
	 * @param response
	 */
	void detailMenu(HttpServletRequest request, HttpServletResponse response) {
		try {
			// out=response.getWriter();
			int menuid = Integer.parseInt(request.getParameter("menuid"));

			SMenuDao sMenuDao = new SMenuDaoImpl();
			SMenu sMenu = sMenuDao.findMenuById(menuid);
			request.setAttribute("menuInfo", sMenu);
			request.setAttribute("menuList", sMenuDao.findMenuList());
			request.getRequestDispatcher("WEB-INF/root/system/MenuManager_modify.jsp")
					.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	/**
	 * 获取菜单操作权限
	 * 
	 * @param request
	 * @param response
	 */
	void RoleMenu(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Map<String,Object> loginuser = (Map<String, Object>) session.getAttribute("loginUser");		
			int menuid = Integer.parseInt(request.getParameter("menuid"));
			SUsermenuDao usermenuDao = new SUsermenuDaoImpl();
			Map<String, Object> singlemenu=usermenuDao.findSingleMenuRoleByUserId(Integer.parseInt(loginuser.get("userid")+""), menuid);
			request.setAttribute("singlemenu", singlemenu);
			
			SMenuRoleDao sMenuRoleDao = new SMenuRoleDaoImpl();
			List<String[]> menuRoleList = sMenuRoleDao
					.findMenuRoleByMenuid(menuid);
			request.setAttribute("MenuRoleList", menuRoleList);
			SUsermenuDao sUsermenuDao = new SUsermenuDaoImpl();
			List<String[]> userRoleList = sUsermenuDao
					.findUserMenuByMenuid(menuid);
			request.setAttribute("menuid", menuid);
			request.setAttribute("UserRoleList", userRoleList);
			request.getRequestDispatcher("WEB-INF/root/system/MenuManager_role.jsp").forward(
					request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	void modifyMenu(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();

			SMenuDao sMenuDao = new SMenuDaoImpl();
			SMenu sMenu = new SMenu();
			BeanUtils.populate(sMenu, request.getParameterMap());
			String sort = request.getParameter("sort");
			if (sMenu.getParentId() != 0) {
				sMenu.setChildshort(Util.objIsNULL(sMenu) ? 0 : Integer
						.parseInt(sort));
			} else {
				sMenu.setParentshort(Util.objIsNULL(sMenu) ? 0 : Integer
						.parseInt(sort));
			}
			sMenu.setModifier(user.get("loginname")+"");
			sMenu.setModifyDate(DateUtils.getNowDateTime());
			int num = sMenuDao.modifyMenu(sMenu);
			if (num > 0) {
				out.print("success");
			} else {
				out.print("error");
			}

		} catch (Exception e) {
			out.print("Modify Menu Exception :" + e.getMessage());
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	
    /**
     * 保存菜单的权限
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	String saveRoleMenu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = null;
		String result="";
		try{
			String groupParamList = request.getParameter("groupParamList");//获取前台菜单数据
			String personParamList = request.getParameter("personParamList");//获取前台菜单数据
			String menuid = request.getParameter("menuid");
			HttpSession session = request.getSession();
			Map<String,Object> loginuser = (Map<String,Object>) session.getAttribute("loginUser");
			SUsermenuDao usermenuDao = new SUsermenuDaoImpl();
			//当前操作人针对对应菜单的操作权限
			Map<String, Object> singlemenu=usermenuDao.findSingleMenuRoleByUserId(Integer.parseInt(loginuser.get("userid")+""), Integer.parseInt(menuid));
			if(Util.objIsNULL(singlemenu)){
				throw new RuntimeException("非法授权[授权人没有相关菜单的操作权限]");
			}
			JSONArray groupJsonArray = JSONArray.fromObject(groupParamList);//将菜单数据转成Json数组
			JSONArray groupActJsonArray=new JSONArray();//重新生成新的角色菜单操作权限列表
			int n=groupJsonArray.size();
			for(int i=0;i<n;i++){//遍历角色菜单操作权限列表
				JSONObject json=groupJsonArray.getJSONObject(i);
				if(Integer.parseInt(singlemenu.get("add")+"")!=1){//如果当前操作人对该菜单没有操作权限，则将对应权限置为-1，后续将不对-1的数据进行任何操作
					json.put("add", -1);
				}
				if(Integer.parseInt(singlemenu.get("del")+"")!=1){
					json.put("del", -1);
				}
				if(Integer.parseInt(singlemenu.get("search")+"")!=1){
					json.put("search", -1);
				}
				if(Integer.parseInt(singlemenu.get("upd")+"")!=1){
					json.put("upd", -1);
				}
				if(Integer.parseInt(singlemenu.get("export")+"")!=1){
					json.put("report", -1);
				}
				if(Integer.parseInt(singlemenu.get("audit")+"")!=1){
					json.put("audit", -1);
				}
				if(Integer.parseInt(singlemenu.get("other")+"")!=1){
					json.put("other", -1);
				}
				groupActJsonArray.add(json);//将过滤后的菜单操作权限重新加入新的角色菜单操作权限列表
			}
			
			JSONArray personJsonArray = JSONArray.fromObject(personParamList);//将菜单数据转成Json数组
			JSONArray personActJsonArray=new JSONArray();//重新生成新的角色菜单操作权限列表
			int m=personJsonArray.size();
			for(int i=0;i<m;i++){//遍历角色菜单操作权限列表
				JSONObject json=personJsonArray.getJSONObject(i);
				if(Integer.parseInt(singlemenu.get("add")+"")!=1){//如果当前操作人对该菜单没有操作权限，则将对应权限置为-1，后续将不对-1的数据进行任何操作
					json.put("add", -1);
				}
				if(Integer.parseInt(singlemenu.get("del")+"")!=1){
					json.put("del", -1);
				}
				if(Integer.parseInt(singlemenu.get("search")+"")!=1){
					json.put("search", -1);
				}
				if(Integer.parseInt(singlemenu.get("upd")+"")!=1){
					json.put("upd", -1);
				}
				if(Integer.parseInt(singlemenu.get("export")+"")!=1){
					json.put("report", -1);
				}
				if(Integer.parseInt(singlemenu.get("audit")+"")!=1){
					json.put("audit", -1);
				}
				if(Integer.parseInt(singlemenu.get("other")+"")!=1){
					json.put("other", -1);
				}
				personActJsonArray.add(json);//将过滤后的菜单操作权限重新加入新的角色菜单操作权限列表
			}

			if(n<=0&&m<=0){
				throw new RuntimeException("提交的数据中没有菜单项");
			}else{
				SMenuRoleDao sMenuRoleDao = new SMenuRoleDaoImpl();
				result=sMenuRoleDao.saveRoleMenu(menuid,groupActJsonArray,personActJsonArray,loginuser.get("loginname")+"");
			}
			out=response.getWriter();
			out.print(result);
			
		}catch (Exception e) {
			result=Util.joinException(e);
		}finally{
		}
		return result;
		
	}
	
	void saveGroupMenu(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String menuid = request.getParameter("menuid");
			String groupid = request.getParameter("groupid");
			String add = request.getParameter("add");
			String upd = request.getParameter("upd");
			String del = request.getParameter("del");
			String search = request.getParameter("search");
			String export = request.getParameter("export");
			String audit = request.getParameter("audit");
			String other = request.getParameter("other");
			
			SGroupDao sGroupDao = new SGroupDaoImpl();
			int isEffective = sGroupDao.effectiveGroup(groupid);
			System.out.println("GroupisEffective: "+ isEffective);
			if(isEffective < 0){
				out.print("notEffective");
				return;
			}
			int isexist = sGroupDao.existGroup(menuid, groupid);
			System.out.println("Groupisexist: "+ isexist);
			if(isexist > 0){
				out.print("isexist");
				return;
			}
			SMenurole  sMenurole = new SMenurole(Integer.parseInt(groupid),Integer.parseInt(menuid),add,upd,del,search,export,audit,other,user.get("loginname")+"",DateUtils.getNowDateTime(),"Y");
			SMenuRoleDao sMenuRoleDao = new SMenuRoleDaoImpl();
			int num = sMenuRoleDao.saveGroupMenuRole(sMenurole);
			if (num > 0) {// 如果保存成功条数大于0
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("Save Menurole Exception:" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}		
	}
	void savePersonMenu(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String menuid = request.getParameter("menuid");
			String personid = request.getParameter("personid");
			String add = request.getParameter("add");
			String upd = request.getParameter("upd");
			String del = request.getParameter("del");
			String search = request.getParameter("search");
			String export = request.getParameter("export");
			String audit = request.getParameter("audit");
			String other = request.getParameter("other");
			
			SMenuRoleDao sMenuRoleDao = new SMenuRoleDaoImpl();
			int isEffective = sMenuRoleDao.effectivePerson(personid);
			System.out.println("PersonisEffective: "+ isEffective);
			if(isEffective < 0){
				out.print("notEffective");
				return;
			}
			int isexist = sMenuRoleDao.existPerson(menuid, personid);
			System.out.println("Personisexist: "+ isexist);
			if(isexist > 0){
				out.print("isexist");
				return;
			}
			
			SUsermenu  sUsermenu = new SUsermenu(Integer.parseInt(personid),Integer.parseInt(menuid),add,upd,del,search,export,audit,other,user.get("loginname")+"",DateUtils.getNowDateTime(),"Y");
			int num = sMenuRoleDao.savePersonMenuRole(sUsermenu);
			if (num > 0) {// 如果保存成功条数大于0
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("Save Menurole Exception:" + e.getMessage());
		} finally {
			out.flush();
			out.close();
		}		
	}
}
