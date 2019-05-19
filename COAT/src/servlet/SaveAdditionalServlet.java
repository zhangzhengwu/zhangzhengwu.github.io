package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.MotifyDao;
import dao.impl.MotifyDaoImpl;
import entity.QueryAdditional;

/**
 * 新增 卡片 张数 信息
 * @author king.xu
 *
 */
public class SaveAdditionalServlet extends HttpServlet {
				Logger log=Logger.getLogger(SaveAdditionalServlet.class);
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				String StaffNo = request.getParameter("StaffNo");
				String Additional = request.getParameter("num");
				String Remarks = request.getParameter("Remark");
				String reremark = request.getParameter("reremark");
				String sfyx = request.getParameter("select");
				MotifyDao md = new MotifyDaoImpl();
		try{
			//	QueryAdditional qa = (QueryAdditional) request.getSession()
			//	.getAttribute("additional");
			//	System.out.println(qa);
				//int r = md.updateAdditional(StaffNo, Additional, Remarks, sfyx, qa.getRemark()); 2015-7-21 15:16:52 将获取session值转成页面传值 kingxu
				int r = md.updateAdditional(StaffNo, Additional, Remarks, sfyx, reremark);
			if (r < 0) {
					out.print("-1");
			} else {
					out.print("1");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("在SaveAdditionServlet中对nq_additional进行修改时出现"+e.toString()	);
		}finally{
			out.flush();
			out.close();
		}
	}
}