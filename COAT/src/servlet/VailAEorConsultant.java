package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import dao.ElitemDao;
import dao.impl.ElitemDaoImpl;

public class VailAEorConsultant extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(VailAEorConsultant.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ElitemDao eliteDao=new ElitemDaoImpl();
		String staffcode=request.getParameter("staffcode");
		try{
			JSONArray jsons=JSONArray.fromObject(eliteDao.vailElitemorAE(staffcode));
			out.print(jsons.toString());
		}catch(Exception e){
			e.printStackTrace();
			log.error("在VailAEorConsultant中验证staff是否是AEConsultant or Elite Team的成员时出现异常："+e.toString());
		}finally{
			out.flush();
			out.close();
		}
	}

}
