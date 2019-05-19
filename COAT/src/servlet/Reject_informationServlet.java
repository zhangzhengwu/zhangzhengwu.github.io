package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import dao.Reject_informationDao;
import dao.impl.Reject_informationDaoImpl;

public class Reject_informationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(Reject_informationServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String model=request.getParameter("model");
		String type=request.getParameter("type");
		String mothedName=request.getParameter("mothedName");
		Reject_informationDao ri=new Reject_informationDaoImpl();
		try{
			
			if(mothedName.equals("query")){
				JSONArray jsons=JSONArray.fromObject(ri.queryRejectInformation(model, type));
				out.print(jsons.toString());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("在 Reject_informationServlet中获取Reject 邮件内容是出现 ："+e.toString());
		}finally{
			out.flush();
			out.close();
		}
		
	}
 

}

