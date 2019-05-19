package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class UpLoadFile {
	static	Logger log=Logger.getLogger(UpLoadFile.class);
	final static String[] IMG_ALLOW_TYPES = {"gif", "jpeg", "bmp", "png" ,"x-png","x-bmp","x-ms-bmp","jpg","tiff"};//允许的图片类型
	/**
	 *  以HttpServletRequest 和上传路径作为参数，下载成功返回该路径和下载文件名称
	 */
	@SuppressWarnings("unchecked")
	public static String  upFile(HttpServletRequest request,String path){
		//定义文件名称
		String name=null;
		try{
			request.setCharacterEncoding("utf-8");
			//临时路径  为TOMCAT目录下的temp 文件
			String temp = System.getProperty("java.io.tmpdir");
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();  
			// 设置缓冲区大小（10M）
			diskFactory.setSizeThreshold(10 * 1024 * 1024);    
			//创建临时文件
			diskFactory.setRepository(new File(temp)); 
			// 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
			ServletFileUpload upload = new ServletFileUpload(diskFactory);  
			// 设置允许上传的最大文件大小10M   
			upload.setSizeMax(10485760);
			//判断文件是否大于10M
			if(upload.getSizeMax()>diskFactory.getSizeThreshold()){
				log.error("Up Load  File Exception : The file is too large"); 
				return null;
			} else{
				//解析request
				List<FileItem> items=upload.parseRequest(request);
				for(FileItem fileItem:items){
					//获取文件的名称
					name=fileItem.getName();   
					if(name!=null){
						//如果名称后缀名为：.xls结尾,则下载
						if(name.endsWith(".xls") || name.endsWith(".xlsx") ||name.endsWith(".csv")){
							//IE浏览器会取得路径的全称，所以在这里需要加判断,查找盘符
							int num=name.indexOf("/");
							if(num!=-1){ 
								//开始上传      以流的形式返回上传文件的数据内容
								InputStream in = fileItem.getInputStream();
								FileOutputStream outs = new FileOutputStream(new File(path,name));
								int len = 0;
								//定义字节数组buffer, 大小为1024
								byte[] buffer = new byte[1024]; 
								System.out.println("上传文件大小为："+(fileItem.getSize()/1024)+"KB");
								while((len = in.read(buffer)) != -1){
									outs.write(buffer, 0, len);
								}
								in.close(); 
								outs.close();	
								return path+"\\"+name;
							}
							//IE浏览器
							else{
								//截取字符串
								String name2=name.substring(name.lastIndexOf("\\")+1,name.length());    	    		
								//开始上传      以流的形式返回上传文件的数据内容
								InputStream in = fileItem.getInputStream();
								FileOutputStream outs = new FileOutputStream(new File(path,name2));
								int len = 0;
								//定义字节数组buffer, 大小为1024
								byte[] buffer = new byte[1024]; 
								System.out.println("上传文件大小为："+(fileItem.getSize()/1024)+"KB");
								while((len = in.read(buffer)) != -1){
									outs.write(buffer, 0, len);
								}
								in.close(); 
								outs.close();
								return path+"\\"+name2;
							}
						}else{
							log.error("Up Load  File ERROR: inactive file");
							return null;
						}
					}else{
						log.error("Up Load  File ERROR:Reason File Name  is NULL");
						return null;
					}
				}
			}
		}catch (SizeLimitExceededException e) {
			// TODO: handle exception
			log.error("Up Load  File Size Exception"+e.toString());   
		}catch (Exception e) {
			log.error("Up Load  File Exception"+e.toString());   
			//e.printStackTrace();
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public static String  upPhoto(HttpServletRequest request,String path,String staffcode){
		//定义文件名称
		String name=null;
		try{
			request.setCharacterEncoding("utf-8");
			//临时路径  为TOMCAT目录下的temp 文件
			String temp = System.getProperty("java.io.tmpdir");
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();  
			// 设置缓冲区大小（10M）
			diskFactory.setSizeThreshold(10 * 1024 * 1024);    
			//创建临时文件
			diskFactory.setRepository(new File(temp)); 
			// 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
			ServletFileUpload upload = new ServletFileUpload(diskFactory);  
			// 设置允许上传的最大文件大小10M   
			upload.setSizeMax(10485760);
			//判断文件是否大于10M
			if(upload.getSizeMax()>diskFactory.getSizeThreshold()){
				log.error("Up Load  File Exception : The file is too large"); 
				return null;
			} else{
				//解析request
				List<FileItem> items=upload.parseRequest(request);
				for(FileItem fileItem:items){
					//获取文件的名称
					name=fileItem.getName();  
					//System.out.println("1-----"+name);
					if(name!=null){
						//System.out.println("2-----"+name);
						//如果名称后缀名为：.xls结尾,则下载lastIndexOf(".")
						String names =name.substring(name.lastIndexOf(".")+1);//
						if(panduanleixing(names, Util.getProValue("upload.img.type").split(","))){
							//if(names.equalsIgnoreCase(".jpeg")){
							name=staffcode+"."+names;  //-->文件名以用户名开头命名
							//截取字符串
							String name2=name.substring(name.lastIndexOf("\\")+1,name.length());    	    		
							//开始上传      以流的形式返回上传文件的数据内容
							InputStream in = fileItem.getInputStream();
							File file=new File(path);
							if(!file.exists()){
								file.mkdir();
							}
							FileOutputStream outs = new FileOutputStream(new File(path,name2));
							int len = 0;
							//定义字节数组buffer, 大小为1024
							byte[] buffer = new byte[1024]; 
							System.out.println("上传文件大小为："+(fileItem.getSize()/1024)+"KB");
							while((len = in.read(buffer)) != -1){
								outs.write(buffer, 0, len);
							}
							in.close(); 
							outs.close();
							return path+"\\"+name2;
							// }
					}else{
						log.error("Up Load  File ERROR: inactive file");
						return "-1";
					}
					}else{
						log.error("Up Load  File ERROR:Reason File Name  is NULL");
						return null;
					}
				}
			}
		}catch (SizeLimitExceededException e) {
			e.printStackTrace();
			log.error("Up Load  File Size Exception"+e.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Up Load  File Exception"+e.toString());   
		}
		return null;
	}		

	/**
	 * 判断是否为允许的文件类型
	 * @author kingxu
	 * @date 2015-8-28
	 * @param type
	 * @param types
	 * @return
	 * @return boolean
	 */
	public static boolean panduanleixing(String type,String[] types){
		boolean flag=false;
		for(String t:types){
			if(t.equalsIgnoreCase(type)){
				return true;
			}
		}
		return flag; 
	}

}
