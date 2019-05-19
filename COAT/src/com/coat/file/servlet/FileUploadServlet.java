package com.coat.file.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.coat.file.entity.FileUploadStatus;

import util.DateUtils;
import util.Util;

public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DecimalFormat dFormat = new DecimalFormat("##.###");
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("gbk"); 
		request.setCharacterEncoding("UTF-8"); // 防止中文乱码
		// 创建HttpSession对象
		HttpSession session = request.getSession();
		String method=request.getParameter("method");
		Util.IteratorRequest(request);
		// 如果请求中c的值为status
		if ("status".equals(method)) {
			doStatus(session, response);// 调用doStatus方法
		} else {// 否则，调用doFileUpload方法
			//Util.IteratorRequest(request);
			JSONObject object=new JSONObject();
			object.put("status", "exception");
			object.put("msg", "----");
			sendCompleteResponse(response,false, object);
			//doFileUpload(session, request, response);
			session.setAttribute("FILE_UPLOAD_STATS", null);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

	/**
	 * 文件上传
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void doFileUpload(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		boolean hasError = false;
		JSONObject object=null;
		try {
			String fileType=request.getParameter("fileType");
			String acceptType=request.getParameter("acceptType");
			object=new JSONObject();
			String path=request.getParameter("filepath");
			path=Util.objIsNULL(path)?Util.getProValue("upload.upload.filepath"):path;
			object.put("filenamepath", path);
			//System.out.println(Util.getProValue("upload.backup.filepath")+""+Util.getProValue("upload.upload.filepath"));
			// 创建UploadListener对象
			UploadListener listener = new UploadListener(request
					.getContentLength());
			listener.start();// 启动监听状态
			// 将监听器对象的状态保存在Session中
			session.setAttribute("FILE_UPLOAD_STATS", listener
					.getFileUploadStats());
			// 创建MonitoredDiskFileItemFactory对象
			//FileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
			DiskFileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
			// 设置缓冲区大小（10M）
			factory.setSizeThreshold(10 * 1024 * 1024);    
			//创建临时文件
			factory.setRepository(new File(this.getServletContext().getRealPath(Util.getProValue("upload.upload.temp.filepath")))); 
			
			// 通过该工厂对象创建ServletFileUpload对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 将转化请求保存到list对象中
			List items = upload.parseRequest(request);
			// 停止使用监听器
			listener.done();
			String filename="";
			// 循环list中的对象
			for (Iterator i = items.iterator(); i.hasNext();) {
				FileItem fileItem = (FileItem) i.next();
				if (!fileItem.isFormField()) {// 如果该FileItem不是表单域
					String filenames=fileItem.getName();
					filenames=filenames.substring(0,filenames.lastIndexOf("."))+"["+DateUtils.nowtime()+"]"+filenames.substring(filenames.lastIndexOf("."));
					filename+=(Util.objIsNULL(filename)?"":";")+(this.getServletContext().getRealPath(path))+filenames;
						processUploadedFile(fileItem,path,filenames);// 调用processUploadedFile方法,将数据保存到文件中
					fileItem.delete();// 内存中删除该数据流
				}
			}
			object.put("filename", filename);
		} catch (Exception e) {
			e.printStackTrace();
			object.put("status", "exception");
			object.put("msg", e.getMessage());
			//sendCompleteResponse(response, e.getMessage());
		}finally{
			sendCompleteResponse(response,hasError,object);
		}
	}
	
 
	/**
	 * 文件上传过程处理
	 * 
	 * @param item
	 */
	public void processUploadedFile(FileItem item,String path,String fileName) {
		// 获得上传文件的文件名
		System.out.println("fileName=" + fileName);
		// 创建File对象,将上传的文件保存到C:\\upload文件夹下
		File fDir = new File(this.getServletContext().getRealPath(path));
		if (!fDir.exists()) {
			fDir.mkdir();
		}
		File file = new File(fDir, fileName);
		InputStream in;
		try {
			in = item.getInputStream();// 获得输入数据流文件
			// 将该数据流写入到指定文件中
			FileOutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[4096];
			int bytes_read;
			while ((bytes_read = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytes_read);
			}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					;
				}
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					;
				}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void doStatus(HttpSession session, HttpServletResponse response)
			throws IOException {
		// 设置该响应不在缓存中读取
		response.addHeader("Expires", "0");
		response.addHeader("Cache-Control","no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.addHeader("Pragma", "no-cache");
		// 获得保存在Session中的状态信息
		UploadListener.FileUploadStats fileUploadStats =null;
		if (!Util.objIsNULL(session.getAttribute("FILE_UPLOAD_STATS"))) {
			fileUploadStats =(UploadListener.FileUploadStats) session.getAttribute("FILE_UPLOAD_STATS");
			long bytesProcessed = fileUploadStats.getBytesRead();// 获得已经上传的数据大小
			long sizeTotal = fileUploadStats.getTotalSize();// 获得上传文件的总大小
			// 计算上传完成的百分比
			long percentComplete = (long) Math
					.floor(((double) bytesProcessed / (double) sizeTotal) * 100.0);
			// 获得上传已用的时间
			long timeInSeconds = fileUploadStats.getElapsedTimeInSeconds();
			// 计算平均上传速率
			double uploadRate = bytesProcessed / (timeInSeconds + 0d);
			// 计算总共所需时间
			double estimatedRuntime = sizeTotal / (uploadRate + 0d);
			FileUploadStatus status=new FileUploadStatus(bytesProcessed+"", sizeTotal+"", percentComplete+"", dFormat.format(uploadRate/1024d), formatTime(timeInSeconds), formatTime(estimatedRuntime), fileUploadStats.getCurrentStatus());
			response.getWriter().print(JSONObject.fromObject(status).toString());
			/*	// 将上传状态返回给客户端
			response.getWriter().println("<b>文件上传状态:</b><br/>");
			if (fileUploadStats.getBytesRead() != fileUploadStats
					.getTotalSize()) {
				response.getWriter().println(
						"<div class=\"prog-border\"><div class=\"prog-bar\" style=\"width: "
								+ percentComplete + "%;\"></div></div>");
				response.getWriter().println(
						"已上传: " + bytesProcessed + "bytes 总大小:" + sizeTotal
						+ " bytes (" + percentComplete + "%) "
						+ (long) Math.round(uploadRate / 1024)
						+ " Kbs <br/>");
				response.getWriter().println(
						"已用时间: " + formatTime(timeInSeconds) + " 总共所需时间 "
								+ formatTime(estimatedRuntime) + " "
								+ formatTime(estimatedRuntime - timeInSeconds)
								+ " 剩余 <br/>");
			} else {
				response.getWriter().println(
						"共上传: " + bytesProcessed + " bytes 总大小:" + sizeTotal
						+ " bytes<br/>");
				response.getWriter().println("文件上传完毕.<br/>");
			}*/
		}






	}

	private void sendCompleteResponse(HttpServletResponse response,
			boolean hasError,JSONObject object) throws IOException {
		PrintWriter out=null;
		try{
			out =  new PrintWriter(response.getOutputStream());
			if(Util.objIsNULL(object.get("status"))){
				if (!hasError) {// 如果没有出现错误
					object.put("status", "success");
					object.put("msg", "success");
				} else {
					object.put("status", "error");
					object.put("msg", "Could not process uploaded file. Please see log for details.");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			object.put("status", "exception");
			object.put("msg", e.getMessage());
		}finally{
			//System.out.println(object.toString());
			out.print(object.toString());
			out.flush();
			out.close();
		}



		/*		if (message == null) {
			response
			.getOutputStream()
			.print(
					"<html><head><script type='text/ javascript'> function killUpdate() { window.parent.killUpdate(''); }</script></head><body onload='killUpdate()'></body></html>");
		} else {
			response
			.getOutputStream()
			.print(
					"<html><head><script type='text/ javascript'>function killUpdate() { window.parent.killUpdate('"
							+ message
							+ "'); }</script></head><body onload='killUpdate()'></body></html>");
		}*/
	}

	private String formatTime(double timeInSeconds) {
		long seconds = (long) Math.floor(timeInSeconds);
		long minutes = (long) Math.floor(timeInSeconds / 60.0);
		long hours = (long) Math.floor(minutes / 60.0);

		if (hours != 0) {
			return hours + "hours " + (minutes % 60) + "minutes "
					+ (seconds % 60) + "seconds";
		} else if (minutes % 60 != 0) {
			return (minutes % 60) + "minutes " + (seconds % 60) + "seconds";
		} else {
			return (seconds % 60) + " seconds";
		}
	}

}
