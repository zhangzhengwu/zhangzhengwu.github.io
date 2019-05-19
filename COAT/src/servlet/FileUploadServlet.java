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
					//System.out.println("��������:" + item.getFieldName() + "��������ֵ:" + item.getString("gb2312"));
				} else { 
					String errMessage = "";
					String extName = ""; //�����ļ���չ��
					String contentType = item.getContentType();
					/*System.out.println("�ϴ��ļ��Ĵ�С:" + item.getSize());
				//	System.out.println("�ϴ��ļ�������:" + item.getContentType());
					// ע��item.getName(),�᷵�������ļ��ڿͻ��˵�����·������
					System.out.println("�ϴ��ļ�������:" + item.getName());*/
					if(!(contentType.equals("image/pjpeg") || contentType.equals("image/bmp") || contentType.equals("image/gif")))
						errMessage = "ֻ���ϴ�jpeg,gif,bmp����ͼƬ��ʽ��Ƭ��";
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
					/*�����ļ���: ��ǰ��¼�û���+��ǰʱ��*/
					String filename = adminUsername + nowTimeString + extName; 
					//File tempFile = new File(item.getName());
					//String filename = tempFile.getName(); 
					File file = new File(request.getRealPath("/") + "photofiles",filename);
					if(file.exists()) errMessage = "���ļ������Ѿ����ڣ�";
					PrintWriter out =  new PrintWriter(response.getOutputStream());
					if(!errMessage.equals(""))
					{
						out.println("<script>alert('" + errMessage + "');</script>" + 
					              "<font color=red>�ϴ�ʧ�ܣ�</font><a href=upload.jsp>[�����ϴ�]</a>");
						out.flush();
						out.close();
					}
					else
					{
						item.write(file); 
						String addr ="photofiles/" + filename; 
						out.println("<script>alert(\"ͼƬ�ϴ��ɹ�!\");" + 
								  	  "parent.document.all.zpImg.src=encodeURI('" + addr + "');" +
								  	  "parent.document.all.zpImg.style.display='';" + 
						              "parent.document." + formName + ".zp.value = '" + addr + "';" +
						              "parent.document." + formName + ".submit.disabled = false;</script>" + 
						              "<font color=red>�ϴ��ɹ���</font><a href=upload.jsp>[�����ϴ�]</a>");
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