package servlet.MissPayment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.MissingPaymentDao;
import dao.impl.MissingPaymentDaoImpl;

public class DelMissingPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger log=Logger.getLogger(DelMissingPaymentServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("You have no legal power!");
		out.flush();
		out.close();
	}

	 
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String id=request.getParameter("missId"); 
		
		MissingPaymentDao cgp=new MissingPaymentDaoImpl();
		try{
			int num=-1;
			String username=request.getSession().getAttribute("adminUsername").toString();
			System.out.println(id+"---------------"+username);
			if (id=="") {
				out.print("删除数据失败!");
			}
			num=cgp.delMissing(id, username);
			if(num>-1){//删除成功，更新数据
				out.print("操作成功!");
			}else{
				out.print("删除数据失败!");
			}
				
			}catch(Exception e){
				e.printStackTrace();
				log.error("删除数据时出现 ："+e.toString());
			}finally{
			out.flush();
			out.close();
			}
	
	}

}
