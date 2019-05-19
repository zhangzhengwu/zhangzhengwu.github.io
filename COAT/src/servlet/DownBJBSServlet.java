package servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import dao.B_commissionDao;
import dao.impl.B_commissionDaoImpl;

import util.DateUtils;
import util.FileUtil;

public class DownBJBSServlet extends HttpServlet {

 
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
		
		
		//String downFile="C:\\Users\\kingxu\\Desktop\\download\\";
	String downFile="\\\\CCNSVR11\\SOP\\碧升佣金计算\\工资清单\\Cacular\\"+DateUtils.getDateToday()+"\\";
		String fname="Cacular_result";
		FileOutputStream os=null;
		try{
			 if(FileUtil.directoryExists(downFile)){
					FileUtil.deleteFile(downFile+"\\"+fname+"_"+DateUtils.getDateToday()+".xls");
				}else{
					new File(downFile).mkdirs();
				}
			  os = new FileOutputStream(downFile+"//"+fname+("_"+DateUtils.getDateToday())+".xls");
				//response.reset();//清空输出流
				HSSFWorkbook wb = new HSSFWorkbook();
				//设定输出文件头，该方法有两个参数，分别表示应大头的名字和值
				//response.setHeader("Content-disposition", "attachment; filename="+ fname.toString() + ".xls");
				HSSFSheet sheet = wb.createSheet();
				
				B_commissionDao cDao=new B_commissionDaoImpl();
				
				System.out.println("==============================Cacular======================");
				writeTxt(cDao.cacular(os,wb,sheet),downFile+"Cacular"+("_errorlog_"+DateUtils.getDateToday())+".txt");
			//out.print("<script>alert('Excel 文件导出成功!导出日志文件路径："+downFile+"\\Cacular"+("_errorlog_"+DateUtils.getDateToday())+".txt');</script>");
			out.print("生成Excel 文件完毕!导出日志文件路径："+downFile+"Cacular"+("_cacularlog_"+DateUtils.getDateToday())+".txt");
		}catch(Exception e){
			e.printStackTrace();
			out.print("----------------计算出错，请重新计算------------");
			writeTxt(e.toString(),downFile+"Cacular"+("_errorlog_"+DateUtils.getDateToday())+".txt");
		}finally{
			 os.flush();
			 os.close(); 
		}
	}
	  public void writeTxt(String s1,String writerFile) {
			 try {   
				// String downFile=Constant.CHINA_POLICY_URL+DateUtils.getDateToday()+"//readme.txt";
				 File f = new File(writerFile);   
				 if(!f.exists()){    
					FileUtil.deleteFile(writerFile);
					 System.out.println(writerFile + "已删除！");
				 }else {
					 f.createNewFile();//不存在则创建
					 System.out.println(writerFile + "已创建！");
					
				}
				 FileOutputStream fis=new FileOutputStream(f); 
				 OutputStreamWriter isr=new OutputStreamWriter(fis,"GBK"); 
				 BufferedWriter br=new BufferedWriter(isr);
				 br.write(s1);
				 br.close(); 
			 } catch (Exception e) 
			 {   
				 e.printStackTrace();  
			 } 
			 
		}
}
