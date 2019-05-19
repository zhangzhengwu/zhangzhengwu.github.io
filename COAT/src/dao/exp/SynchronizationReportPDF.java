package dao.exp;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class SynchronizationReportPDF implements ServletContextListener {
	private Timer timer=null;		
	
	Logger logger = Logger.getLogger(SynchronizationReportPDF.class);
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		   //在这里关闭监听器,同时关闭定时器  
		timer.cancel();      
		event.getServletContext().log("report PDF 同步定时器关闭.....");
		logger.info("report PDF 同步定时器关闭.....");
	}
	 
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
	     timer=new Timer();    
	     event.getServletContext().log("report PDF 同步定时器已经启动.....");
	     logger.info("report PDF 同步定时器已经启动.....");
	     //调用exportHistoryBean,0表示任务无延迟,5*1000表示每隔5秒执行任务,60*60*1000表示一个小时   // 10*60*1000 10分钟  
	   timer.schedule(new ReportPDF(event.getServletContext()), 0, 60*60*1000);      
	     //timer.schedule(new Task(), 0, 10*1000);       
	     event.getServletContext().log("report PDF 同步任务已经添加.....");
	     logger.info("report PDF 同步任务已经添加.....");
		
	}

}
