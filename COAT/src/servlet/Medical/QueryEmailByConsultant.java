package servlet.Medical;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import net.sf.json.JSONArray;
import dao.MedicalDao;
import dao.impl.MedicalDaoImpl;

public class QueryEmailByConsultant extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(QueryEmailByConsultant.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String staffcode=request.getParameter("staffcode");
		
		MedicalDao md=new MedicalDaoImpl();
		try{
			List<String> list=new ArrayList<String>();
			list=md.QueryEmailByConsultant(staffcode);
			out.print(JSONArray.fromObject(list));
		}catch(Exception e){
			e.printStackTrace();
			log.error("在 QueryEmailByConsultant中查询Email时出现："+e.toString());
		}finally{
			out.flush();
			out.close();
			
		}
		 
	}

}
