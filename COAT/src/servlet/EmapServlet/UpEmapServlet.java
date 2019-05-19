package servlet.EmapServlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.DateUtils;

import dao.EBorrowDao;
import dao.EConsultantDao;
import dao.EExceptionDao;
import dao.EattendenceDao;
import dao.EclubDao;
import dao.EleaveConsDao;
import dao.EmapRecordDao;
import dao.EpartConsultantDao;
import dao.EresignConsDao;
import dao.EtraineeDao;
import dao.TrainingDao;
import dao.impl.EBorrowDaoImpl;
import dao.impl.EConsultantDaoImpl;
import dao.impl.EExceptionDaoImpl;
import dao.impl.EattendenceDaoImpl;
import dao.impl.EclubDaoImpl;
import dao.impl.EleaveDaoImpl;
import dao.impl.EmapRecordDaoImpl;
import dao.impl.EpartConsultantDaoImpl;
import dao.impl.EresignConsDaoImpl;
import dao.impl.EtraineeDaoImpl;
import dao.impl.TrainingDaoImpl;

public class UpEmapServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(UpEmapServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		
			String username="";
			if(request.getSession().getAttribute("adminUsername")==null){
				username=getRemoteAddress(request);
			}else{
			 username=request.getSession().getAttribute("adminUsername").toString();
			}
			String up = request.getParameter("up");
			String filename = new String(up.getBytes("ISO-8859-1"), "utf-8");// //
			InputStream os = new FileInputStream(filename);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			List<Object> list = new ArrayList<Object>();
			int num = -1;
			try {
				// 读取导入的文件
				String path = filename.replaceAll("\\\\", "/").substring(
						filename.replaceAll("\\\\", "/").lastIndexOf("/") + 1);
				String titlename = path.replaceAll(".xls", "");// 获取没有后缀名的Excel的文件名
				Long befor = null;// 标记上传文件处理开始时间
				Double after = null;// 标记上传文件处理结束时间
				/** 上传文件开始处理 * */
				befor = System.currentTimeMillis(); // 标记初始时间（以毫秒记）
 
				if (titlename.equals(util.Constant.Borrow_Records)) {// 借卡记录
					EBorrowDao bd = new EBorrowDaoImpl();
					num = bd.saveBorrow(filename, os, username);
				} else if (titlename.equals(util.Constant.Leave_Records)) {// 请假记录
					EleaveConsDao leave = new EleaveDaoImpl();
					num = leave.saveLeave(filename, os, username);
				} else if (titlename.equals(util.Constant.C_Club)) {// C-Club成员列表
					EclubDao club = new EclubDaoImpl();
					num = club.saveClub(filename, os, username);
					
				}else if(titlename.equals(util.Constant.EMAPRECORD)){//Emap 25 May record
					EmapRecordDao promDao = new EmapRecordDaoImpl();
					num = promDao.saveEmap(filename, os);
				 
				}else if(titlename.equals(util.Constant.EXCEPTIONAL)){//Exceptional Date
					EExceptionDao promDao = new EExceptionDaoImpl();
					num = promDao.saveException(filename, os);
				 
				}else if(titlename.equals(util.Constant.RESIGN_CONS)){//terminate&resign consultant list
					EresignConsDao promDao = new EresignConsDaoImpl();
					num = promDao.saveResignCons(filename, os);
					 
				}else if(titlename.equals(util.Constant.ATTENDENCE)){//Attendence Record
					EattendenceDao aDao = new EattendenceDaoImpl();
					num = aDao.saveAttendence(filename, os);
				}else if(titlename.equals(util.Constant.TEAINEE_LIST)){//trainee list
					EtraineeDao cDao = new EtraineeDaoImpl();
					num = cDao.saveTrainee(filename, os);	 
				}else if(titlename.equals(util.Constant.OTHCONS_LIST)){//trainee list
					EpartConsultantDao cDao = new EpartConsultantDaoImpl();
					num = cDao.saveConsultant(filename, os, username); 
				}else if(titlename.equals("Training List")){
					System.out.println("======================上传Training List====================");
					TrainingDao trDao=new TrainingDaoImpl();
					System.out.println(DateUtils.getNowDateTime());
					num=trDao.saveTraining(util.ReadExcel.readExcel(new File(filename), 0 , 2,1),username);
					//num=cDao.uploadmis(filename, os, request.getSession().getAttribute("adminUsername").toString());
					/**上传文件时必须执行的语句 **/
				}else if(titlename.indexOf(util.Constant.ConsultantList)>-1){//consultant_List_2012-04 
					EConsultantDao cDao = new EConsultantDaoImpl();
					System.out.println(DateUtils.getNowDateTime());
					//num = cDao.saveConsultant(filename, os, username);
					//num=cDao.cacular(filename, os, username);
					num=cDao.cacularConsultantAttendance(filename, username);
				
				}else{
					num=0;
				}
				/** 上传文件时必须执行的语句 * */
				after = new Double(System.currentTimeMillis()); // 标记结束时
 
				DecimalFormat df1 = new DecimalFormat("##0.00");
				String nums = df1.format((after-befor)/60000);
				list.add(nums);
				list.add(num);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(username
						+ "在UpAllServlet中上传"
						+ filename.replaceAll("\\\\", "/").substring(
								filename.replaceAll("\\\\", "/").lastIndexOf(
										"/") + 1) + "时出现" + e.toString());
				list = null;
			} finally {
				out.print(list);
				out.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * 获取用户端IP
	 * @param request
	 * @return
	 */
    public String getRemoteAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    } 
	
}
