package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.FileUtil;
import dao.UploadFileDao;
import dao.impl.UploadFileDaoImpl;

public class SaveUploadFileServlet extends HttpServlet {
	Logger log=Logger.getLogger(SaveAdditionalServlet.class);
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String staffcode=request.getParameter("staffcode");
		String profession=(request.getParameter("profession")).replaceAll(";", "-");
		String fileName=request.getParameter("filename").replace("</pre>","");
		String result="";
		UploadFileDao upf=new UploadFileDaoImpl();
        String filepath = this.getServletContext().getRealPath("")+java.io.File.separator+"upload"+java.io.File.separator;

        try{
				String newFileName=staffcode+"_"+profession+"_"+DateUtils.getDateToday()+fileName.substring((fileName.lastIndexOf(".")));
				if(FileUtil.move(filepath+"temp"+java.io.File.separator+fileName, filepath)){
				int num=FileUtil.Rename(filepath+fileName,filepath+newFileName);
				if(num==1){
					int nums=upf.addUploadFile(staffcode, newFileName,DateUtils.getDateToday());
					if(nums>-1){
						File file=new File(filepath.substring(0,filepath.indexOf("\\")+1)+"\\upload_bak\\officeAdmin\\");
						if(!file.exists()){
							 file.mkdirs();
						}
						result="success";
						FileUtil.copyFile(new File(filepath+newFileName),new File(file.toString()+"\\"+newFileName));
					 	out.print("success");
					}else{
						result="error";
						out.print("error");
					}					
				}else{
					System.out.println("error");
					out.print("error");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			result=e.toString();
		}finally{
			log.info("用户==="+staffcode+"保存附件===结果:"+result);
				out.flush();
				out.close();
		}
	}
}
