package servlet;

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

 

public class FileTitleUploadServlet extends HttpServlet {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String data;
	private String uploadFile="upload\\temp";//文件存放目录
	    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        doPost(request, response);
	    }
	    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        request.setCharacterEncoding("UTF-8"); // 防止中文乱码
	        response.setHeader("Content-Type","text/html");
	        String filepath = this.getServletContext().getRealPath("")+java.io.File.separator+uploadFile+java.io.File.separator;
	      
	        String filename = "";
	        String type="";
	        PrintWriter out=response.getWriter();
	        ServletInputStream in = request.getInputStream();
	        try{

	        byte[] buf = new byte[4048];
	        int len = in.readLine(buf, 0, buf.length);
	        String f = new String(buf, 0, len - 1);
	        while ((len = in.readLine(buf, 0, buf.length)) != -1) {
	         filename = new String(buf, 0, len);
	        // System.out.println(filename+"--type");
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

	        String result =filename ;
	       // System.out.println(result);
	        out.print(result);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	        out.close();
	        in.close();
	    }
	    }
	    
		public void setUploadFile(String uploadFile) {
			this.uploadFile = uploadFile;
		}
		public String getUploadFile() {
			return uploadFile;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		
	

}
