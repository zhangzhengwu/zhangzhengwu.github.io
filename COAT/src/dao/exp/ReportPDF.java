package dao.exp;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.Util;
import dao.C_GetMarkPremiumDao;
import dao.C_GetStationeryDao;
import dao.impl.C_GetMarkPremiumDaoImpl;
import dao.impl.C_GetStationeryDaoImpl;
import entity.C_marOrder;
import entity.C_stationeryOrder;


public class ReportPDF extends TimerTask {
		Logger logger = Logger.getLogger(ReportPDF.class);
		private   static  final  int  C_SCHEDULE_HOUR       =  12; 
		private   static   boolean  isRunnings   =   false;  
		
		private ServletContext context = null;   
		 private InetAddress addr=null;
	public ReportPDF(ServletContext   context){  
			this.context= context;    
		}
	@Override
	public void run()  {
	  Calendar cal = Calendar.getInstance();   
	  try{
			 addr = InetAddress.getLocalHost();
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("ReportPDF--->初始化服務器IP時出現異常-->"+e.getMessage());
		}
		
     // if (!isRunnings && (addr.getHostAddress().equals("10.30.1.63")||addr.getHostAddress().equals("10.144.10.50"))){  
	   if (!isRunnings &&"false".equalsIgnoreCase(Util.getProValue("public.system.uat"))){  
	       if(C_SCHEDULE_HOUR ==  cal.get(Calendar.HOUR_OF_DAY)  ){//判断任务时间段                          
	            isRunnings = true;                                    
	            context.log("ReportPDF-->开始执行指定任务-REPORT PDF !!!");    
	            logger.info("ReportPDF-->开始进入同步任务...");
                  
	            try{
	            	System.out.println(cal.get(Calendar.HOUR_OF_DAY)+"===============begin============================================="+cal.get(Calendar.MINUTE));
	            	/***********************MarkPremium*************************************/
	            	String startDate = DateUtils.afterDate(DateUtils.getDateToday())+" 12:00:00";
	    			String endDate = DateUtils.getDateToday()+" 12:00:00";
	    			String staffcode = "";
	    			String ordercode = "";
	    			String urlString = "C:\\";
	    			C_GetMarkPremiumDao  getMarkPremiumDao = new C_GetMarkPremiumDaoImpl();
	    			 
	    			List<C_marOrder> mklist=new ArrayList<C_marOrder>();
	    			//查询时间点内的情况
	    			mklist=getMarkPremiumDao.queryOrderListForPDF(staffcode, ordercode, startDate, endDate, 1000000, 1);
	    			
	    			for (int i = 0; i < mklist.size(); i++) {
	    				C_marOrder order = mklist.get(i);			
	    				int  num = getMarkPremiumDao.reprtPDFOnTime(order.getClientcode(), order.getOrdercode(), startDate, endDate,urlString);
	    					 
	    			}
	    			
	    			/**********************Stationery**************************************/
	    			
	    			C_GetStationeryDao  getStationeryDao = new C_GetStationeryDaoImpl();
	   			 
	    			List<C_stationeryOrder> list=new ArrayList<C_stationeryOrder>();
	    			list=getStationeryDao.queryOrderListForPDF(staffcode, ordercode, startDate, endDate, 1000000, 1);
	    			for (int i = 0; i < list.size(); i++) {
	    				C_stationeryOrder order = list.get(i);			
	    				int  bnum = getStationeryDao.reprtPDFOnTime(order.getClientcode(), order.getOrdercode(), startDate, endDate,urlString);
	    				
	    			}
	            } catch(Exception e){
    	            	logger.error("ReportPDF-->導出失敗!"+e.toString());
    	            	try {
							Thread.sleep(5000);
							this.run();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
    	         }
                isRunnings   =   false;    
                context.log("指定任务执行结束-REPORT PDF !!!");  
                logger.info("指定任务执行结束-REPORT PDF !!!");    
            } 
	    }else{  
         
      	if("false".equalsIgnoreCase(Util.getProValue("public.system.uat"))){
	          context.log("ReportPDF-->-->上一次任务执行还未结束");    
	          logger.info("ReportPDF-->-->上一次任务执行还未结束");
	    	}else{
	  		 context.log("ReportPDF-->-->"+addr.getHostAddress()+"-->該服務器不允許運行定時任務");    
		     logger.info("ReportPDF-->-->"+addr.getHostAddress()+"-->該服務器不允許運行定時任務");
	    	}  
	   }   
	}

}
