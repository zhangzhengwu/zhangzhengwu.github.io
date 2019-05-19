package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.exp.ImpPCReportDao;
import dao.exp.ImpTRlistDao;
import dao.impl.ImpBvCaseExcelDao;
import dao.impl.ImpTraineeDao;

public class UpAllPromotionServlet extends HttpServlet {

	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(UpAllPromotionServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
 
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String up = request.getParameter("up");
		String filename=new String(up.getBytes("ISO-8859-1"),"utf-8");
		InputStream os = new FileInputStream(filename);
		PrintWriter out = response.getWriter();
		Map<Integer, List<Object>> map = new LinkedHashMap<Integer, List<Object>>();
		List list = new ArrayList();
		int num = -1;
		try {
			//读取导入的文件
			String path = filename.replaceAll("\\\\", "/").substring(filename.replaceAll("\\\\", "/").lastIndexOf("/") + 1);
			String titlename =path.replaceAll(".xls", ""); 
			
			Long befor =null;//标记上传文件处理开始时间
			Long after =null;//标记上传文件处理结束时间
			/** 上传文件开始处理 **/
			befor = System.currentTimeMillis(); // 标记初始时间（以毫秒记）
			if(titlename.equals(util.Constant.Trainee_checking)){
				num=ImpTraineeDao.InputExcel(filename, os);
				after = System.currentTimeMillis();  
				String nums=((after-befor)/60000)+"";
				list.add(nums);
				list.add(num);
				
			}else if(titlename.substring(0,20).equals(util.Constant.Tr_Regist)){
				num=ImpTRlistDao.InputExcel(filename, os);
				after = System.currentTimeMillis();  
				String nums=((after-befor)/60000)+"";
				list.add(nums);
				list.add(num);
 
			}else  if(titlename.substring(0,20).equals(util.Constant.Bvcase_checking)){
				num=ImpBvCaseExcelDao.InputExcel(filename, os);
				//ExcelRead read = new ExcelRead();   
				//map=read.readExcel(filename, 3);   
				num = map.size();
				after = System.currentTimeMillis();  
				String nums=((after-befor)/60000)+"";
				list.add(nums);
				list.add(num);
			}else if(titlename.substring(0,25).equals(util.Constant.Promotion_checking)){
				num=ImpPCReportDao.InputExcel(filename, os);
		        after = System.currentTimeMillis();  
		        String nums=((after-befor)/60000)+"";
				list.add(nums);
				list.add(num);
		      
			}else{
				System.out.println("非系统所需文件！");
			}
		}catch(Exception e){
				log.error("在UpAllServlet中上传"+filename.replaceAll("\\\\", "/").substring(
				filename.replaceAll("\\\\", "/").lastIndexOf("/") + 1)+"时出现"+e.toString());
				list=null;
		}finally{
				out.print(list);
				out.flush();
		}
	}

}
