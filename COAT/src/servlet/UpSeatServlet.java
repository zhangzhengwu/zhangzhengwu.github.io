package servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.DateUtils;
import dao.B_commissionDao;
import dao.C_GetMarkPremiumDao;
import dao.C_GetStationeryDao;
import dao.C_KeyDao;
import dao.C_SeatDao;
import dao.GetCLDao;
import dao.GetCNMDao;
import dao.GetChinaSimpleDao;
import dao.GetNQaddDao;
import dao.GetPromotionDao;
import dao.GetStaffListDao;
import dao.GetStaffMasterDao;
import dao.GetTRDao;
import dao.GetrMedicalDao;
import dao.GetrReqRecordDao;
import dao.MacauDao;
import dao.MissingPaymentDao;
import dao.impl.B_commissionDaoImpl;
import dao.impl.C_GetMarkPremiumDaoImpl;
import dao.impl.C_GetStationeryDaoImpl;
import dao.impl.C_KeyDaoImpl;
import dao.impl.C_SeatDaoImpl;
import dao.impl.GetCLDaoImpl;
import dao.impl.GetCNMDaoImpl;
import dao.impl.GetChinaSimpleDaoImpl;
import dao.impl.GetNQaddDaoImpl;
import dao.impl.GetPromotionDaoImpl;
import dao.impl.GetStaffListDaoImpl;
import dao.impl.GetStaffMasterDaoImpl;
import dao.impl.GetTRDaoImpl;
import dao.impl.GetrMedicalDaoImpl;
import dao.impl.GetrReqRecordDaoImpl;
import dao.impl.MacauDaoImpl;
import dao.impl.MissingPaymentDaoImpl;

/**
 * 所有上传页面
 * @author king.xu
 *
 */
public class UpSeatServlet extends HttpServlet {
	Logger log=Logger.getLogger(UpSeatServlet.class);
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
						doPost(request, response);
	}
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		String up = request.getParameter("up");
		String filename =null;
		PrintWriter out = null;
		List list = new ArrayList();
		
		String basePath=request.getRequestURL().substring(0,request.getRequestURL().indexOf(request.getRequestURI()));
		System.out.println(up+"==========================="+basePath);
		try {
			out = response.getWriter();
			filename= new String(up.getBytes("ISO-8859-1"),"UTF-8");// // 强制转换成utf-8
			filename=filename.replace("O:\\SZ-HK","\\\\hkgnas11\\OperDept$\\SZ-HK");
			InputStream os = new FileInputStream(filename);
			response.setContentType("text/html;charset=utf-8");
			int num = -1;
			String msg ="";
			// 读取导入的文件
			String path = filename.replaceAll("\\\\", "/").substring(
					filename.replaceAll("\\\\", "/").lastIndexOf("/") + 1);
			String titlename = path.replaceAll(".xls", "");//获取没有后缀名的Excel的文件名
			Long befor =null;//标记上传文件处理开始时间
			Long after =null;//标记上传文件处理结束时间
			/** 上传文件开始处理 **/
			befor = System.currentTimeMillis(); // 标记初始时间（以毫秒记）

		if(titlename.indexOf("Seat arrangement")>-1){
			C_SeatDao md=new C_SeatDaoImpl();
			msg=md.saveSeat(filename, os,basePath);
			/**上传文件时必须执行的语句 **/
			//after= System.currentTimeMillis(); // 标记结束时
			//String nums=((after-befor)/60000)+"";
			//list.add(nums);
			list.add(msg);
		} 
		out.print(msg);
		}catch(FileNotFoundException e){
			e.printStackTrace();
			 out.print("notfound");
		}catch(Exception e){
			e.printStackTrace();
			log.error("在UpAllServlet中上传"+filename.replaceAll("\\\\", "/").substring(
			filename.replaceAll("\\\\", "/").lastIndexOf("/") + 1)+"时出现"+e.toString());
			out.print("error");
	   }finally{
			
			out.flush();
			out.close();
		}
	
	}
}