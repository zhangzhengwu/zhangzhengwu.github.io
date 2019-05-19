package servlet.Medical;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MedicalFileUploadServlet extends HttpServlet {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uploadFile="upload";//文件存放目录
	    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        doPost(request, response);
	    }
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        request.setCharacterEncoding("UTF-8"); // 防止中文乱码
	        String filepath = this.getServletContext().getRealPath("")+java.io.File.separator+uploadFile+java.io.File.separator;
	        String filename = "";
	        String type="";
	        ServletInputStream in = request.getInputStream();
	        byte[] buf = new byte[4048];
	        int len = in.readLine(buf, 0, buf.length);
	        String f = new String(buf, 0, len - 1);
	        while ((len = in.readLine(buf, 0, buf.length)) != -1) {
	         filename = new String(buf, 0, len);
	            int j = filename.lastIndexOf("\"");
	            int p = filename.lastIndexOf(".");
	            type=filename.substring(p,j);   //文件类型
	            filename = System.currentTimeMillis()+type;  //文件名称
	            DataOutputStream fileStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filepath+ filename)));
	            len = in.readLine(buf, 0, buf.length);
	            len = in.readLine(buf, 0, buf.length);
	            while ((len = in.readLine(buf, 0, buf.length)) != -1) {
	                String tempf = new String(buf, 0, len - 1);
	                if (tempf.equals(f) || tempf.equals(f + "--")) {
	                    break;   
	                }
	                else{
	                  fileStream.write(buf, 0, len); // 写入
	                }
	            }
	            fileStream.close();
	        }
	        PrintWriter out=response.getWriter();
	        String result =filename ;
	        out.print(result);
	        out.close();
	        in.close();
	    }
		public void setUploadFile(String uploadFile) {
			this.uploadFile = uploadFile;
		}
		public String getUploadFile() {
			return uploadFile;
		}

}
