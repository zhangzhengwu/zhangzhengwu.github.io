package com.orlando.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.core.ApplicationPart;

import com.orlando.business.ChatMessageInfoBiz;
import com.orlando.business.ChatUserInfoBiz;
import com.orlando.business.impl.ChatMessageInfoBizImpl;
import com.orlando.business.impl.ChatUserInfoBizImpl;
import com.orlando.entity.ChatMessageInfoForName;
import com.orlando.entity.ChatUserInfo;
import com.orlando.entity.Page;
import com.orlando.utils.Util;

import net.sf.json.JSONArray;


 /** 
 * @ClassName: ChatroomServlet 
 * @Description: 聊天室
 * @author: 章征武【orlando】
 * @date: 2018年9月13日 上午10:14:18 
 * @tel: 17520490925
 * @email: zhangzw368319@163.com 
 */
/*@WebServlet(name = "ChatroomServlet", urlPatterns = "/ChatroomServlet",loadOnStartup = -1,
initParams = { @WebInitParam(name="name",value="orlando"),@WebInitParam(name="",value="")})*/
@MultipartConfig(fileSizeThreshold=2*1024*1024,maxFileSize=5*1024*1024,maxRequestSize=20 * 1024 * 1024)
public class ChatroomServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	private ChatUserInfoBiz userinfoBiz = new ChatUserInfoBizImpl();
	private ChatMessageInfoBiz messageinfoBiz = new ChatMessageInfoBizImpl();
	
	@Override
	public void init(ServletConfig config){
		String str = config.getInitParameter("name");
		System.out.println(str);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String method = request.getParameter("method");
		try {
			if("login".equals(method)){
				login(request,response);
			}else if("showonlines".equals(method)){
				showonlines(request,response);
			}else if("backtohomepage".equals(method)){
				backtohomepage(request,response);
			}else if("update".equals(method)){
				update(request,response);
			}else if("detail".equals(method)){
				detail(request,response);
			}else if("logout".equals(method)){
				logout(request,response);
			}else if("sendmessage".equals(method)){
				sendmessage(request,response);
			}else if("showmessage".equals(method)){
				showmessage(request,response);
			}else if("findallmsg".equals(method)){
				findallmsg(request,response);
			}else if("findpagemsg".equals(method)){
				findpagemsg(request,response);
			}else if("toregedit".equals(method)){
				toregedit(request,response);
			}else if("regedit".equals(method)){
				regedit(request,response);
			}else if("backtoindex".equals(method)){
				backtoindex(request,response);		
			}else if("toforgot".equals(method)){
				toforgot(request,response);		
			}else if("forgot".equals(method)){
				forgot(request,response);	
			}else if("backtoforgotpass".equals(method)){
				backtoforgotpass(request,response);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @Title: showonlines
	 * @Description: 显示在线成员信息
	 * @param @param request
	 * @param @param response    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月14日 上午12:37:22  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void showonlines(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("jsp/showonlines.jsp").forward(request, response);
	}

	/**
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ServletException 
	 * @Title: sendmessage
	 * @Description: 发送信息
	 * @param @param request
	 * @param @param response    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 下午12:59:30  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void sendmessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String sendcontent = request.getParameter("sendcontent");
		String receivername = request.getParameter("receivername");
		//消息为空时禁止发送
		if(Util.objIsNULL(sendcontent)){
			request.setAttribute("logmsg", "<font color='green' >消息不能为空</font>");
			request.getRequestDispatcher("ChatroomServlet?method=showmessage").forward(request, response);
			return;
		}
		int uid =  Integer.parseInt(session.getAttribute("u_id").toString());
		int num = -1;
		try{
			if(receivername != null && receivername.trim().length() > 0){
				num =userinfoBiz.getChatUserIdByUserName(receivername);
				if(num>0){
					//Date nowdate = new Date(new java.util.Date().getTime());  //获取当前时间
					//Date nowdate = new Date(System.currentTimeMillis());  //获取当前时间
					if(messageinfoBiz.addChatMessageInfo(sendcontent,uid,num)){
						request.setAttribute("logmsg", "<font color='green' >信息发送成功</font>");
					}else{
						request.setAttribute("logmsg", "<font color='red' >信息发送失败</font>");
					} 
				}else{
					//接收人不存在，信息发送失败
					request.setAttribute("logmsg", "<font color='red' >接收人不存在，信息发送失败</font>");
				}
				
			}else{
				//发送信息给所有人
				if(messageinfoBiz.addChatMessageInfo(sendcontent,uid)){
					request.setAttribute("logmsg", "<font color='green' >信息发送成功</font>");
				}else{
					request.setAttribute("logmsg", "<font color='red' >信息发送失败</font>");
				} 
			}
			//发送完信息返回主页时要带上在线人数
			/*sql = "select count(1) as online from tb_user_info where u_state != 0;	";
			rs = db.doQuery(sql);
			if(rs.next()){
				request.setAttribute("online", rs.getString("online"));
			}*/
			request.getRequestDispatcher("ChatroomServlet?method=showmessage").forward(request, response);
		}finally{
			userinfoBiz.closeConnection();
		}		
	}
	
	/**
	 * @Title: showmessage
	 * @Description: 显示接收到的消息
	 * @param @param request
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException
	 * @param @throws SQLException    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月16日 上午11:50:08  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void showmessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int uid =  Integer.parseInt(session.getAttribute("u_id").toString());
		try{	
			List<ChatMessageInfoForName> list = messageinfoBiz.showChatMessage(uid);
			request.setAttribute("list", list);
			//将 list 集合的数据转换为 JSON 格式
			//[sendId=null, sendName=张三, receiverId=null, receiverName=null, sendContent=大家好, sendTime=2018-09-28, remark=null]
			//[{"sendId" : "null","sendName" : "张三","receiverId" : "null","receiverName" : "null","sendContent" : "大家好","sendTime" : "2018-09-28","remark" : "null"}]
			//[{"":"","":""},{"":"","":""}]
			/*StringBuffer sb = new StringBuffer("");
			sb.append("[");
			for (int i = 0; i < list.size(); i++) {
				sb.append("{");
				for (ChatMessageInfo c : list) {
					Field[] Fields = c.getClass().getDeclaredFields();
					for (Field field : Fields) {
						field.setAccessible(true);
						sb.append("\""+field.getName()+"\"" + " : " + "\""+field.get(c)+"\"" + ",");
					}
					sb.deleteCharAt(sb.length()-1);
				}
				sb.append("},");
				sb.deleteCharAt(sb.length()-1);
			}
			sb.append("]");
			System.out.println("list转换成JSON格式后：------>"+sb.toString());*/
			
			//使用第三方工具转换格式，切注意Date类型必须是 Util包下的，sql包反射会报错 IllegalArgumentException
			/*JSONArray json = new JSONArray();
			json.addAll(list);
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();*/
			
			JSONArray json = new JSONArray();  
			json.addAll(list);
			System.out.println("使用第三方工具将list转换成JSON格式后：------>"+json.toString());
			request.getRequestDispatcher("jsp/homepage.jsp").forward(request, response);
		}catch(IllegalArgumentException e){
			throw new SQLException(e);
		}finally{
			messageinfoBiz.closeConnection();
		}		
	}
	
	
	/**
	 * @Title: findAllMsg
	 * @Description: 显示所有聊天记录
	 * @param @param request
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException
	 * @param @throws SQLException    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月11日 下午4:16:07  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void findallmsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int pagenum = 1;
		try{
			Page page = new Page();
			List<ChatMessageInfoForName> list = page.showMessageList(pagenum);
			System.out.println("第一次展示的list.size:       "+list.size());
			request.setAttribute("pagenum", pagenum);
			request.setAttribute("list", list);
			JSONArray json = new JSONArray();  
			json.addAll(list);
		}catch(IllegalArgumentException e){
			throw new SQLException(e);
		}finally{
			messageinfoBiz.closeConnection();
		}		
		request.getRequestDispatcher("jsp/showmessagepage.jsp").forward(request, response);
	}
	
	
	
	/**
	 * @Title: findpagemsg
	 * @Description: 查询每一页的信息
	 * @param @param request
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException
	 * @param @throws SQLException    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月11日 下午6:15:15  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void findpagemsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int pagenum = 1;
		int currpage = 1;
		Page page = new Page();
		String str	= request.getParameter("str");
		try{
			if("first".equals(str)){
				pagenum = 1;
			}else if("last".equals(str)){
				pagenum=page.getLastPageNum();
			}else if("next".equals(str)){
				currpage = Integer.parseInt(request.getParameter("pagenum"));
				if(currpage < page.getLastPageNum()){
					pagenum = ++currpage;
				}else{
					pagenum=page.getLastPageNum();
				}
			}else{
				currpage = Integer.parseInt(request.getParameter("pagenum"));
				if(currpage>1){					
					pagenum = --currpage;
				}else{
					pagenum = 1;
				}
			}
			List<ChatMessageInfoForName> list = page.showMessageList(pagenum);  //通过Page类实现应用层的分页
			
			request.setAttribute("lastpagenum", page.getLastPageNum());
			request.setAttribute("pagenum", pagenum);
			request.setAttribute("list", list);
			JSONArray json = new JSONArray();  
			json.addAll(list);
		}catch(IllegalArgumentException e){
			throw new SQLException(e);
		}finally{
			messageinfoBiz.closeConnection();
		}		
		request.getRequestDispatcher("jsp/showmessagepage.jsp").forward(request, response);
	}

	
	
	
	
	/**
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ServletException 
	 * @Title: logout
	 * @Description: 登出：和返回index页面的区别在于此处需要将用户的在线状态改为不在线
	 * @param @param request
	 * @param @param response    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 下午12:50:03  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int uid =  Integer.parseInt(session.getAttribute("u_id").toString());
		ChatUserInfo userInfo = new ChatUserInfo();
		userInfo.setUserId(uid);
		userInfo.setUserName((String)session.getAttribute("u_name"));
		userInfo.setUserPwd((String)session.getAttribute("u_pwd"));
		userInfo.setUserNick((String)session.getAttribute("u_nick"));
		userInfo.setUserImg((String)session.getAttribute("u_img"));
		userInfo.setUserPhone((String)session.getAttribute("u_phone"));
		userInfo.setUserEmail((String)session.getAttribute("u_email"));
		userInfo.setUserRemark((String)session.getAttribute("u_remark"));
		userInfo.setUserCardId((String)session.getAttribute("u_card_id"));
		userInfo.setUserState(false);
		
		try {
			//修改该用户登录状态为未登录
			if(!userinfoBiz.updateChatUserInfoObj(userInfo)){
				throw new SQLException("修改用户登录状态异常");
			}
			//将 application作用域该用户从Set集合去除
			//获取登录时的application
			ServletContext application = request.getServletContext();
			Map<Integer, ChatUserInfo> mapInfo = null;
			synchronized(this){
				//获取application作用域内当前在线的人员信息
				mapInfo = (Map<Integer, ChatUserInfo>) application.getAttribute("mapInfo");
				mapInfo.remove(uid);
				application.setAttribute("onlinenum", mapInfo.size());
//				application.setAttribute("mapInfo", mapInfo);
//				request.getSession().invalidate();
			}
			request.getSession().invalidate();
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			userinfoBiz.closeConnection();
		}	
	}

	/**
	 * @throws ServletException 
	 * @throws IOException 
	 * @Title: detail
	 * @Description: 转详细页面
	 * @param @param request
	 * @param @param response    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 上午11:48:08  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void detail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("jsp/detail.jsp").forward(request, response);
	}

	/**
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ServletException 
	 * @Title: update
	 * @Description: 修改信息
	 * @param @param request
	 * @param @param response    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 上午11:16:44  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		String uid = request.getParameter("uid");
		String username = request.getParameter("username");
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String uploadPath1 = request.getServletContext().getRealPath(File.separator);
		String uploadPath2 = File.separator+"upload"+File.separator;
		String uploadPath = uploadPath1+uploadPath2;
		ApplicationPart part = (ApplicationPart)request.getPart("img");
		String img = part.getSubmittedFileName();
		if(!Util.objIsNULL(img)){
			part.write(uploadPath+img);
		}else{
			img = session.getAttribute("u_img").toString();
		}
//		String img = request.getParameter("img");
		String cardid = request.getParameter("cardid");
		ChatUserInfo userInfo = new ChatUserInfo();
		userInfo.setUserId(Integer.valueOf(uid));
		userInfo.setUserName(username);
		userInfo.setUserNick(nick);
		userInfo.setUserPwd(password);
		userInfo.setUserEmail(email);
		userInfo.setUserPhone(phone);
		userInfo.setUserCardId(cardid);
		userInfo.setUserImg(img);
		userInfo.setUserState(true);
		userInfo.setUserRegisterTime((Date)session.getAttribute("u_register_time"));
		try{
			if(userinfoBiz.updateChatUserInfoObj(userInfo)){
				session.setAttribute("u_name", username);
				session.setAttribute("u_nick", nick);
				session.setAttribute("u_pwd", password);
				session.setAttribute("u_email", email);
				session.setAttribute("u_img", img);
				session.setAttribute("u_phone", phone);
				session.setAttribute("u_card_id", cardid);
				request.setAttribute("successmsg","修改成功");
				request.getRequestDispatcher("jsp/success_update.jsp").forward(request, response);
			}else{
				request.setAttribute("errormsg","修改失败");
				request.getRequestDispatcher("jsp/error_update.jsp").forward(request, response);
			}
		}finally{
			userinfoBiz.closeConnection();
		}		
				
	}

	/**
	 * @Title: backtohomepage
	 * @Description: 返回主页
	 * @param @param request
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException
	 * @param @throws SQLException    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 上午2:42:02  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void backtohomepage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		/*DBUtils db = new DBUtils();
		String sql = null;
		ResultSet rs = null;
		//查询当前在线人数
		try {
			sql = "select count(1) as online from tb_user_info where u_state != 0;	";
			rs = db.doQuery(sql);
			if(rs.next()){
				request.setAttribute("online", rs.getString("online"));
			}
			request.getRequestDispatcher("jsp/homepage.jsp").forward(request, response);
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			db.closeConnection();
		}*/
		request.getRequestDispatcher("ChatroomServlet?method=showmessage").forward(request, response);
	}

	/**
	 * @Title: login
	 * @Description: 登录
	 * @param @param request
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws SQLException 
	 * @date: 2018年9月12日 下午4:07:00  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 */
	@SuppressWarnings("unchecked")
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		String loginmsg = request.getParameter("loginmsg");
		String password = request.getParameter("password");
		try {
			List<ChatUserInfo> list = userinfoBiz.findListByLoginMsg(password, loginmsg);
			if(list.size() > 0){
				//用户登录信息唯一，正常登录 
				saveSession(list);//登录信息存Session
				//修改登录人状态为1，即已登录状态
				ChatUserInfo cui = bulidObj(list);
				if(!userinfoBiz.updateChatUserInfoObj(cui)){
					throw new SQLException("修改用户登录状态异常");
				}
				
				//思考作业：尝试从数据库中查询一个用户对象，将对象直接保存在文件中。
				String projectPath = request.getServletContext().getRealPath("/");
				File file = new File(projectPath+"file/obj.txt");
				if(!file.exists()){
					file.createNewFile();
				}
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(cui);
				oos.close();
				
				//在线人数 思路： 
				//用Set集合记录登录用户的基本信息，账号、昵称、头像。并保存在application作用域内
				//用户在登出时，将其信息从Set集合中排除，人数则为Set集合的size
				//TODO 当服务器宕机时，计数器清零(尚未学)
				//获取登录时的application
				ServletContext application = request.getServletContext();
				
				//Set做法，多用户登录退出时清理对象出现混乱，失败。
				/*Set<ChatUserInfo> setInfo = null;
				synchronized(this){
					//获取application作用域内当前在线的人员信息
					if(Util.objIsNULL(application.getAttribute("setInfo"))){
						setInfo = new HashSet<>();
					}else{
						setInfo = (Set<ChatUserInfo>) application.getAttribute("setInfo");
					}
					setInfo.add(cui);
					onlinenum = setInfo.size();
					application.setAttribute("onlinenum", onlinenum);
					application.setAttribute("setInfo", setInfo);
				}*/
				
				//Map做法
				Map<Integer, ChatUserInfo> mapInfo = null;
				int countnum = 0;
				synchronized (this) {
					//获取application作用域内当前在线的人员信息
					if(!Util.objIsNULL(application.getAttribute("countnum"))){
						countnum = (int) application.getAttribute("countnum");
						countnum++;
					}
					if(Util.objIsNULL(application.getAttribute("mapInfo"))){
						mapInfo = new HashMap<>();
					}else{
						mapInfo =  (Map<Integer, ChatUserInfo>) application.getAttribute("mapInfo");
					}
					mapInfo.put(cui.getUserId(), cui);
					application.setAttribute("countnum", countnum);
					application.setAttribute("onlinenum", mapInfo.size());
					application.setAttribute("mapInfo", mapInfo);
					
				}
				
				//解决刷新页面后转发反复提交的问题思路：页面重定向到信息查询servlet，然后在转发到主页
				//response.sendRedirect("ChatroomServlet?method=showmessage");
				request.getRequestDispatcher("ChatroomServlet?method=showmessage").forward(request, response); 
			}else{
				//登陆失败，查询账户是否存在
				//账户存在
				if(userinfoBiz.findUserIsLegal(loginmsg)){
					// 账户存在，则只能是密码输入错误了，在登录页面给出提示
					request.setAttribute("passinfo", "密码出错");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}else{
					//账户不存在,提示账户输入错误
					request.setAttribute("userinfo", "账户输入错误");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
			}
		} catch (SQLException e) {
			request.setAttribute("userinfo", "SQL异常，具体原因是："+e.getMessage());
			request.getRequestDispatcher("index.jsp").forward(request, response);
			throw new SQLException(e);
		} finally {
			userinfoBiz.closeConnection();
		}
	}

	/**
	 * @Title: saveSession
	 * @Description: 登录信息存于session
	 * @param @param list    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月17日 下午5:22:43  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	private void saveSession(List<ChatUserInfo> list) {
		session.setAttribute("u_id", list.get(0).getUserId());
		session.setAttribute("u_name", list.get(0).getUserName());
		session.setAttribute("u_pwd", list.get(0).getUserPwd());
		session.setAttribute("u_nick", list.get(0).getUserNick());
		session.setAttribute("u_img", list.get(0).getUserImg());
		session.setAttribute("u_email", list.get(0).getUserEmail());
		session.setAttribute("u_phone", list.get(0).getUserPhone());
		session.setAttribute("u_card_id", list.get(0).getUserCardId()); 
		session.setAttribute("u_remark", list.get(0).getUserRemark());
		session.setAttribute("u_state", list.get(0).getUserState());
		session.setAttribute("u_register_time", list.get(0).getUserRegisterTime());
		
	}

	/**
	 * @Title: bulidObj
	 * @Description: 将List封装成对象
	 * @param @param list
	 * @param @return    参数
	 * @return ChatUserInfo    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月17日 下午5:16:12  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	private ChatUserInfo bulidObj(List<ChatUserInfo> list) {
		ChatUserInfo cui = new ChatUserInfo();
		cui.setUserName(list.get(0).getUserName());
		cui.setUserPwd(list.get(0).getUserPwd());
		cui.setUserNick(list.get(0).getUserNick());
		cui.setUserImg(list.get(0).getUserImg());
		cui.setUserEmail(list.get(0).getUserEmail());
		cui.setUserPhone(list.get(0).getUserPhone());
		cui.setUserCardId(list.get(0).getUserCardId());
		cui.setUserState(true);
		cui.setUserRemark(list.get(0).getUserRemark());
		cui.setUserId(list.get(0).getUserId());
		cui.setUserRegisterTime(list.get(0).getUserRegisterTime());
		return cui;
	}

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @Title: regedit
	 * @Description: 注册跳转
	 * @param @param request
	 * @param @param response    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 下午2:04:14  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void toregedit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("jsp/regedit.jsp").forward(request, response);
	}
	/**
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ServletException 
	 * @Title: regedit
	 * @Description: 注册
	 * @param @param request
	 * @param @param response    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 下午2:06:49  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void regedit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String uploadPath1 = request.getServletContext().getRealPath(File.separator);
		String uploadPath2 = File.separator+"upload"+File.separator;
		String uploadPath = uploadPath1+uploadPath2;
		ApplicationPart part = (ApplicationPart)request.getPart("u_img");  //获取上传的内容
		String filename = part.getSubmittedFileName();  //获取上传文件的名字
		part.write(uploadPath+filename);
		ChatUserInfo userInfo = new ChatUserInfo();
		userInfo.setUserName(username);
		userInfo.setUserPwd(request.getParameter("password"));
		userInfo.setUserNick(request.getParameter("nick"));
		userInfo.setUserEmail(email);
		userInfo.setUserPhone(phone);
		userInfo.setUserImg(filename);
		userInfo.setUserState(false);
		userInfo.setUserCardId(request.getParameter("cardid"));
//		userInfo.setUserRegisterTime(new Date(new java.util.Date().getTime()));//2018-09-17 00:00:00
		userInfo.setUserRegisterTime(new java.util.Date(System.currentTimeMillis()));
		try{
			List<ChatUserInfo> list = userinfoBiz.findByUsernameEmailPhone(username, email, phone);
			//如果注册信息不存在，则通过
			if(list.size() == 0){
				if(userinfoBiz.addChatUserInfoObj(userInfo)){
					request.setAttribute("successmsg", "注册成功");
					request.getRequestDispatcher("jsp/success_regedit.jsp").forward(request, response);
				}else{
					//非注册信息已存在的注册失败。 
					request.setAttribute("errormsg", "注册失败");
					request.getRequestDispatcher("jsp/error_regedit.jsp").forward(request, response);
				}
			}else{
				//如果注册信息已存在，则给出提示xxx账号已存在
				if(username!=null && username.equalsIgnoreCase(list.get(0).getUserName())){
					request.setAttribute("usernamemsg", "用户名已被注册");
				}
				if(email!=null && email.equalsIgnoreCase(list.get(0).getUserEmail())){
					request.setAttribute("emailmsg", "邮箱已被注册");
				}
				if(phone!=null && phone.equalsIgnoreCase(list.get(0).getUserPhone())){
					request.setAttribute("phonemsg", "联系方式已被注册");
				}
				request.getRequestDispatcher("jsp/regedit.jsp").forward(request, response);
			}
		}finally{
			userinfoBiz.closeConnection();
		}		
	}

	/**
	 * @Title: backtoindex
	 * @Description: 返回登录页
	 * @param @param request
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 下午2:37:22  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void backtoindex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}	
	
	/**
	 * @Title: toforgot
	 * @Description: 跳到忘记密码页面
	 * @param @param request
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 下午4:06:40  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void toforgot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("jsp/forgot.jsp").forward(request, response);
	}	

	/**
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ServletException 
	 * @Title: forgot
	 * @Description: 忘记密码
	 * @param @param request
	 * @param @param response    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 下午4:11:01  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void forgot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String cardid = request.getParameter("cardid");
		int count = 0; 
		try{
			List<ChatUserInfo> list = userinfoBiz.findByUsername(username);
			if(list.size()>0){
				if(email != null && email.equalsIgnoreCase(list.get(0).getUserEmail())){
					count ++;
				}
				if(phone != null && phone.equalsIgnoreCase(list.get(0).getUserPhone())){
					count ++;
				}
				if(cardid != null && cardid.equalsIgnoreCase(list.get(0).getUserCardId())){
					count ++;
				}
				if(count > 0){
					request.setAttribute("password", list.get(0).getUserPwd());
					request.setAttribute("successmsg", "密码找回成功");
					request.getRequestDispatcher("jsp/success_findpassword.jsp").forward(request, response);
				}else{
					request.setAttribute("errormsg", "用户信息不完整，无法找回密码，请联系管理员");
					request.getRequestDispatcher("jsp/error_findpassword.jsp").forward(request, response);
				}
			}else{
				request.setAttribute("errormsg", "用户名不存在");
				request.getRequestDispatcher("jsp/error_notfound.jsp").forward(request, response);
			}
		}finally{
			userinfoBiz.closeConnection();
		}			
	}

	/**
	 * @Title: backtoforgotpass
	 * @Description: 返回忘记密码页面
	 * @param @param request
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException    参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年9月13日 下午4:25:12  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void backtoforgotpass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("jsp/forgot.jsp").forward(request, response);
	}
	
}
