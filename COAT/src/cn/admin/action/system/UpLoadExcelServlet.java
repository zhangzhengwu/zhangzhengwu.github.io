package cn.admin.action.system;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import util.ReadExcel;
import util.UpLoadFile;
import cn.admin.dao.impl.system.UpLoadExcelDaoImpl;
import cn.admin.dao.system.UpLoadExcelDao;

public class UpLoadExcelServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		String methods=request.getParameter("methods");
		
		try{
			if(methods.equals("UpExcel")){
				importBuildInfoExcel(request,response);
			}else if(methods.equals("UpLoadFile")){
				upLoadFile(request, response);
			}else{
				throw new RuntimeException("未授权的访问");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.print("Exception:"+e.getMessage());
			out.flush();
			out.close();
		}finally{
			
		}
		
	}
	
	public void upLoadFile(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("text/html;charset=utf-8");
		 PrintWriter out=response.getWriter();
		 String path=this.getServletContext().getRealPath("/upload/temp");
		 try{
			String answer=UpLoadFile.upFile(request, path); 
			if(answer!=null){
				out.print(answer);
			}else{
				out.print("error");
			}
		 }catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Excel文件上传
	 */
    public void importBuildInfoExcel(HttpServletRequest request,HttpServletResponse response){
    	
    	String filename =null;
		String uploadfile=request.getParameter("uploadfile");
		PrintWriter out = null;
		List list = new ArrayList();
		int num=0;
		int ss=0;
		System.out.println(uploadfile+"===========================");
		try {
			out = response.getWriter();
			filename= new String(uploadfile.getBytes("ISO-8859-1"),"UTF-8"); //强制转换成UTF-8
			//System.out.println(filename);
			response.setContentType("text/html;charset=utf-8");
			Long befor =null;//标记上传文件处理开始时间
			Long after =null;//标记上传文件处理结束时间
			/** 上传文件开始处理 **/
			befor = System.currentTimeMillis(); // 标记初始时间（以毫秒记）
			if(filename.indexOf("cwsi_user")>-1){
				UpLoadExcelDao clDao=new UpLoadExcelDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 18, 1);
				ss=clDao.saveExcel(list2);
				num=list2.size();
			}else{
				num=-1;
			}
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			
			list.add(0,uploadfile);//上傳路徑
			list.add(1,num);//成功條數
			list.add(2,nums);//上傳時間
			list.add(3,ss);//已存在的数据
			
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
			
			System.out.println("上传文件"+uploadfile+"-->时间-->"+nums+"--->成功条数-->"+num);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}
}
