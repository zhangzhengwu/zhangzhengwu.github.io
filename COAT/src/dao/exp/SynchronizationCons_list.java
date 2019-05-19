package dao.exp;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class SynchronizationCons_list implements ServletContextListener {
	private Timer timers=null;
	Logger logger = Logger.getLogger(SynchronizationCons_list.class);
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		   //在这里关闭监听器,同时关闭定时器  
		timers.cancel();      
		event.getServletContext().log("SynchronizationCons_list-->系统同步定时器销毁.....");
		logger.info("SynchronizationCons_list-->系统同步定时器销毁.....");
	}

	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
	     timers=new Timer();    
	     event.getServletContext().log("SynchronizationCons_list-->系统同步定时器初始化完毕.....");
	     logger.info("SynchronizationCons_list-->系统同步定时器初始化完毕.....");
	     //调用exportHistoryBean,0表示任务无延迟,5*1000表示每隔5秒执行任务,60*60*1000表示一个小时     
	     timers.schedule(new SpecifiedTask(event.getServletContext()), 30*1000, 60*60*1000);      
	     //timer.schedule(new Task(), 0, 10*1000);       
	     event.getServletContext().log("SynchronizationCons_list-->系统同步任务已经添加.....");
	     logger.info("SynchronizationCons_list-->系统同步任务已经添加.....");
		
	}

}
