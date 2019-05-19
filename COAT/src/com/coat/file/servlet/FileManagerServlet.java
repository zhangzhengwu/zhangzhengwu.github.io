package com.coat.file.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import util.Util;

public class FileManagerServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
	String user=null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method")+"";
		PrintWriter out =null;
		String result="";
		try{
			user=Util.objIsNULL(request.getSession().getAttribute("adminUsername"))?(request.getSession().getAttribute("convoy_username")+""):(request.getSession().getAttribute("adminUsername"))+"";//request.getSession().getAttribute("convoy_username").toString();
			if(Util.objIsNULL(user)){
				new RuntimeException("Identity information is missing");
			}else if("down".equalsIgnoreCase(method)){
				
				down(request, response);
			}else{
				throw new Exception("Unauthorized access!");
			}
			
		}catch (Exception e) {
			//e.printStackTrace();
			result=e.getLocalizedMessage();
		}finally{
			if(!Util.objIsNULL(result)){
				out= response.getWriter();
				out.print(result);
				out.flush();
				out.close();
			}
		}
		
	}
	
	/**
	 * 导出本地文件
	 * @author kingxu
	 * @date 2015-11-18
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @return void
	 */
	void down(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		OutputStream toClient =null;
		String path=request.getParameter("path");//本地相对于file.handle.temp.filepath的路径
		String orgname=request.getParameter("filename");//导出后显示的名称
		try{
			String paths=this.getServletContext().getRealPath(Util.getProValue("file.handle.temp.filepath"));
			String filename="";
			
			if(Util.objIsNULL(orgname)){
				// path是指欲下载的文件的路径。
				File file = new File(paths+File.separator+path);
				// 取得文件名。
				filename = file.getName();
			}else{
				filename=orgname;
			}
		
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(paths+File.separator+path));
			
			byte[] buffer = new byte[fis.available()];
			
			fis.read(buffer);
			fis.close();
			// 清空response
			// 设置response的Header
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + buffer.length);
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
		
		}catch (FileNotFoundException e) {
			throw new RuntimeException(path+"文件未找到!");
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(!Util.objIsNULL(toClient)){
				toClient.flush();
				toClient.close();
			}
		}
	}

}
