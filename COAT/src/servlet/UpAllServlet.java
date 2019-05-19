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
import dao.EPaymentDao;
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
import dao.RecruimentDao;
import dao.impl.B_commissionDaoImpl;
import dao.impl.C_GetMarkPremiumDaoImpl;
import dao.impl.C_GetStationeryDaoImpl;
import dao.impl.C_KeyDaoImpl;
import dao.impl.C_SeatDaoImpl;
import dao.impl.EPaymentDaoImpl;
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
import dao.impl.RecuritmentDaoImpl;

/**
 * 所有上传页面
 * @author king.xu
 *
 */
public class UpAllServlet extends HttpServlet {
	Logger log=Logger.getLogger(UpAllServlet.class);
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
		System.out.println(up+"===========================");
		try {
			out = response.getWriter();
			filename= new String(up.getBytes("ISO-8859-1"),"UTF-8");// // 强制转换成utf-8
			filename=filename.replace("O:\\SZ-HK","\\\\hkgnas11\\OperDept$\\SZ-HK");
		InputStream os = new FileInputStream(filename);
		response.setContentType("text/html;charset=utf-8");
		int num = -1;
			// 读取导入的文件
			String path = filename.replaceAll("\\\\", "/").substring(
					filename.replaceAll("\\\\", "/").lastIndexOf("/") + 1);
			String titlename = path.replaceAll(".xls", "");//获取没有后缀名的Excel的文件名
			Long befor =null;//标记上传文件处理开始时间
			Long after =null;//标记上传文件处理结束时间
			/** 上传文件开始处理 **/
			befor = System.currentTimeMillis(); // 标记初始时间（以毫秒记）
		if(titlename.equals("Sample")){
			GetChinaSimpleDao getsDao=new GetChinaSimpleDaoImpl();
			num=getsDao.saveSimple(filename, os);
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
			//如果titlename前15个字符等于util.Constant.TR(TR Regiistration)
		}else if(titlename.indexOf(util.Constant.TR)>-1){
		//}else if(titlename.substring(0, 15).equals(util.Constant.TR)){
			GetTRDao getTRDao=new GetTRDaoImpl();
			num=getTRDao.saveTR(filename, os);
			
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
			//如果titlename前15个字符等于util.Constant.CL(Consultant List)
		}else if(titlename.indexOf("Key for Chicken Box")>-1){
			C_KeyDao md=new C_KeyDaoImpl();
			num=md.saveKeys(filename, os);
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		/*}else if(titlename.indexOf("Seat arrangement")>-1){
			C_SeatDao md=new C_SeatDaoImpl();
			String msg=md.saveSeat(filename, os,"");
			*//**上传文件时必须执行的语句 **//*
			//after= System.currentTimeMillis(); // 标记结束时
			//String nums=((after-befor)/60000)+"";
			//list.add(nums);
			list.add(msg);*/
		}else if(titlename.indexOf(util.Constant.CIBConsultant)>-1){
			MacauDao md=new MacauDaoImpl();
			num=md.saveCIB(filename, os,"admin");
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
			//如果titlename前15个字符等于util.Constant.CL(Consultant List)
		}else if(titlename.indexOf(util.Constant.CL)>-1){
	//	}else if(titlename.substring(0, 15).equals(util.Constant.CL)){
			GetCLDao getCLDao=new GetCLDaoImpl();
			num=getCLDao.saveCL(filename, os,"admin");
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
			//如果titlename等于util.Constant.CIBConsultant(CIB Consultant List)
		}else if(titlename.indexOf(util.Constant.NQA)>-1){
			GetNQaddDao getaddDao = new GetNQaddDaoImpl();
			num = getaddDao.saveNQadd(filename, os,request.getSession().getAttribute("adminUsername").toString());
			 /**上传文件时必须执行的语句 **/ 
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
			//STAFFMASTER staff 历史数据
		}else if(titlename.equals(util.Constant.STAFFMASTER)){
			GetStaffMasterDao getaddDao = new GetStaffMasterDaoImpl();
			num = getaddDao.saveStaffMaster(filename, os);
			/**上传文件时必须执行的语句 **/ 
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.equals(util.Constant.CNM)){//如果titlename 等于util.Constant.CNM(Consultant Namecard Master)
			GetCNMDao getCNMDao=new GetCNMDaoImpl();
			num=getCNMDao.saveCNM(filename, os);
			//**上传文件时必须执行的语句 **//*
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.equals(util.Constant.PROMOTION)){//PROMOTION checking  
			GetPromotionDao promDao = new GetPromotionDaoImpl();
			num = promDao.savePromotion(filename, os);
			//**上传文件时必须执行的语句 **//*
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.equals(util.Constant.C_markeingP)){//Markeing Premium-stock
			C_GetMarkPremiumDao cDao = new C_GetMarkPremiumDaoImpl();
			num = cDao.saveMarkPremium(filename, os);
			//**上传文件时必须执行的语句 **//*
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.equals("Advertising Placement")){//Advertising Placement
			RecruimentDao cDao = new RecuritmentDaoImpl();
			num = cDao.saveRecuritment(filename, os);
			//**上传文件时必须执行的语句 **//*
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.equals("ePayment item list")){//Advertising Placement
			EPaymentDao cDao = new EPaymentDaoImpl();
			num = cDao.saveEPayment(filename, os);
			//**上传文件时必须执行的语句 **//*
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.equals(util.Constant.C_stationery)){//Stationery_stock
			C_GetStationeryDao promDao = new C_GetStationeryDaoImpl();
			num = promDao.saveStationery(filename, os);
			//**上传文件时必须执行的语句 **//*
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.indexOf("Out Patient List")>-1){
		//}else if(titlename.substring(0, 16).equals("Out Patient List")){
			System.out.println("======================Out Patient List======================");
			GetStaffListDao getslDao=new GetStaffListDaoImpl();
			num=getslDao.saveStaffList(filename, os,"admin"+DateUtils.getDateToday());
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.indexOf(util.Constant.QEQ_QECORD)>-1){//如果titlename 等于util.Constant.NQA(Requisition Record)
		//}else if(titlename.substring(0,18).equals(util.Constant.QEQ_QECORD)){//如果titlename 等于util.Constant.NQA(Requisition Record)
			GetrReqRecordDao recordDao = new GetrReqRecordDaoImpl();
			num = recordDao.saveReqRecord(filename, os);
			//**上传文件时必须执行的语句 **//*
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.indexOf(util.Constant.MEDICAL)>-1){//如果Medical 截取前20个字符
		//}else if(titlename.substring(0,20).equals(util.Constant.MEDICAL)){//如果Medical 截取前20个字符
			GetrMedicalDao medicalDao = new GetrMedicalDaoImpl();
			num = medicalDao.saveMedical(filename, os);
			//**上传文件时必须执行的语句 **//*
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.indexOf("Staff Medical Claims")>-1){
		//}else if(titlename.substring(0, 20).equals("Staff Medical Claims")){
			System.out.println("======================Staff Medical Claims====================");
			GetStaffListDao getslDao=new GetStaffListDaoImpl();
			num=getslDao.saveStaffMater(filename, os,"admin"+DateUtils.getDateToday());
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.indexOf("BJBS")>-1){
			System.out.println("======================上传发佣明细====================");
			B_commissionDao cDao=new B_commissionDaoImpl();
			num=cDao.saveCommission(filename, os,"admin"+DateUtils.getDateToday());
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else if(titlename.indexOf("Missing Payment Report")>-1){
			System.out.println("======================上传Missing Payment Report====================");
			MissingPaymentDao cDao=new MissingPaymentDaoImpl();
			num=cDao.uploadmis(filename, os, request.getSession().getAttribute("adminUsername").toString());
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			list.add(nums);
			list.add(num);
		}else{
		//	list.add("<SCRIPT TYPE='JAVASCRIPT'>ALERT('非系统所需文件！');</SCRIPT>");
			//B_commissionDao cDao=new B_commissionDaoImpl();
			
			//System.out.println("==============================Cacular======================");
			//cDao.cacular();
			//list.add(5000);
			//list.add(25);
			//list=null;
		}
		/*
		if(titlename.equals(util.Constant.NQA)){//如果titlename 等于util.Constant.NQA(Namecard Quota_Additional)
			GetNQaddDao getaddDao = new GetNQaddDaoImpl();
			num = getaddDao.saveNQadd(filename, os);
			*//**上传文件时必须执行的语句 **//*
			after= System.currentTimeMillis(); // 标记结束时
			System.out.println("此次操作共耗时：" + (after - befor) + "毫秒");
			String nums=((after-befor)/6000)+"";
			list.add(nums);
			list.add(num);
		}
					*/
		out.print(list);
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