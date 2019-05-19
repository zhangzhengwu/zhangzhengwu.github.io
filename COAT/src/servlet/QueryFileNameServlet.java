package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import dao.UploadFileDao;
import dao.impl.UploadFileDaoImpl;

public class QueryFileNameServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(QueryFileNameServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		UploadFileDao ufd=new UploadFileDaoImpl();
		String staffcode=request.getParameter("staffcode");
		try{
			List<String> list=ufd.getUploadFile(staffcode);
			JSONArray jsons=JSONArray.fromObject(list);
			log.info("用户:"+staffcode+"=====获取附件上传历史记录");
			out.print(jsons.toString());
		}catch(Exception e){
			e.printStackTrace();
			log.error("用户"+staffcode+"=====在获取附件上传历史记录时出现："+e);
		}finally{
			out.flush();
			out.close();
		}
		
	}

}
