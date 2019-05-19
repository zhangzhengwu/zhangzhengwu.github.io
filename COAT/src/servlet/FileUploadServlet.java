package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*; 
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;



public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
Logger log=Logger.getLogger(FileUploadServlet.class);

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
					List items = upload.parseRequest(request);
					Iterator itr = items.iterator();
			while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					//System.out.println("表单参数名:" + item.getFieldName() + "，表单参数值:" + item.getString("gb2312"));
				} else { 
					String errMessage = "";
					String extName = ""; //保存文件扩展名
					String contentType = item.getContentType();
					/*System.out.println("上传文件的大小:" + item.getSize());
				//	System.out.println("上传文件的类型:" + item.getContentType());
					// 注意item.getName(),会返回上载文件在客户端的完整路径名称
					System.out.println("上传文件的名称:" + item.getName());*/
					if(!(contentType.equals("image/pjpeg") || contentType.equals("image/bmp") || contentType.equals("image/gif")))
						errMessage = "只能上传jpeg,gif,bmp三种图片格式照片！";
					if(contentType.equals("image/pjpeg"))
						extName = ".jpg";
					else if(contentType.equals("image/bmp"))
						extName = ".bmp";
					else if(contentType.equals("image/gif"))
						extName = ".gif";
					String adminUsername = request.getSession().getAttribute("adminUsername").toString();
					java.text.SimpleDateFormat bartDateFormat = new java.text.SimpleDateFormat("yyMMddHHmmSS");
					java.util.Date date = new java.util.Date();
					String nowTimeString = bartDateFormat.format(date);
					/*生成文件名: 当前登录用户名+当前时间*/
					String filename = adminUsername + nowTimeString + extName; 
					//File tempFile = new File(item.getName());
					//String filename = tempFile.getName(); 
					File file = new File(request.getRealPath("/") + "photofiles",filename);
					if(file.exists()) errMessage = "该文件名称已经存在！";
					PrintWriter out =  new PrintWriter(response.getOutputStream());
					if(!errMessage.equals(""))
					{
						out.println("<script>alert('" + errMessage + "');</script>" + 
					              "<font color=red>上传失败！</font><a href=upload.jsp>[重新上传]</a>");
						out.flush();
						out.close();
					}
					else
					{
						item.write(file); 
						String addr ="photofiles/" + filename; 
						out.println("<script>alert(\"图片上传成功!\");" + 
								  	  "parent.document.all.zpImg.src=encodeURI('" + addr + "');" +
								  	  "parent.document.all.zpImg.style.display='';" + 
						              "parent.document." + formName + ".zp.value = '" + addr + "';" +
						              "parent.document." + formName + ".submit.disabled = false;</script>" + 
						              "<font color=red>上传成功！</font><a href=upload.jsp>[重新上传]</a>");
						out.flush();
						out.close();
					}
					
				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
}