package cn.admin.action.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import util.Util;
import cn.admin.dao.impl.system.SUserDaoImpl;
import cn.admin.dao.system.SUserDao;
import cn.admin.entity.system.SUser_Login;




public class UploadHeadimgServlet extends HttpServlet {
	private Map<String,Object> user;

	/**
	 * Constructor of the object.
	 */
	public UploadHeadimgServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
		}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if ((Map<String,Object>)session.getAttribute("loginUser") == null) {
			response.getWriter().println("<script type=\"text/javascript\">top.location.href='index.jsp'</script>");
			return;
		} else {
			Map<String,Object> loginuser = (Map<String,Object>) session
					.getAttribute("loginUser");
			user = loginuser;
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			out.print(upload(request, response));
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	public String upload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strReturn = "";
		Integer userid = Integer.parseInt(user.get("userid")+"");
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";

		String returnPath = basePath + "upload/personalHead/"
				+ userid.toString() + "/";
		String savePath = request.getSession().getServletContext().getRealPath(
				"/")
				+ "upload\\personalHead\\" + userid.toString() + "\\";

		File f1 = new File(savePath);
		// System.out.println(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			strReturn = Util.getMsgJosnObject("exception", "上传处理出现异常！");
			return strReturn;
		}
		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		String extName = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				name = item.getName();
				long size = item.getSize();
				String type = item.getContentType();
				// System.out.println(size + " " + type);
				if (name == null || name.trim().equals("")) {
					strReturn =Util.getMsgJosnObject("exception", "上传处理出现异常！");
					return strReturn;
				}
				// 扩展名格式：
				if (name.lastIndexOf(".") >= 0) {
					extName = name.substring(name.lastIndexOf("."));
				}
				File file = null;
				do {
					// 生成文件名：
					SimpleDateFormat bartDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd-HH-mm-ss");
					Date date = new Date();
					name = bartDateFormat.format(date);
					// name ="2014-11-12_123213";
					file = new File(savePath + name + extName);
					// System.out.println(savePath + name + extName);
				} while (file.exists());
				File saveFile = new File(savePath + name + extName);
				try {
					item.write(saveFile);
					// 写入数据库

					String headimgsrc = "upload/personalHead/"
							+ userid.toString() + "/" + name + extName;
					//System.out.println(headimgsrc);
					SUserDao sUDao = new SUserDaoImpl();
					if (sUDao.UpdateHeadImg(userid, headimgsrc) != -1) {
						strReturn = Util.getMsgJosnObject("success", headimgsrc);
						user.put("headimage", headimgsrc);
						request.getSession().setAttribute("loginUser",user);
					} else {
						strReturn =Util.getMsgJosnObject("exception", "写入数据库出错了！");
					}
				} catch (Exception e) {
					e.printStackTrace();
					strReturn = Util.getMsgJosnObject("exception", "上传处理出现异常！");
				}
			}
		}
		// } else {
		// strReturn = Util.toJsonString("-1", "没有获取到上传的文件的内容！");
		// }
		return strReturn;
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
