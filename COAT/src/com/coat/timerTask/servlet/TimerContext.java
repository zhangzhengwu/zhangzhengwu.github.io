package com.coat.timerTask.servlet;

import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import util.Constant;
import util.Util;

public class TimerContext implements ServletContextListener{
	Logger logger = Logger.getLogger(TimerContext.class);
/*	private  ScheduledExecutorService scheduExec;  
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("销毁定时任务....");
		Util.printLogger(logger, "销毁定时任务===================");
		scheduExec.shutdown();//销毁定时器

	}

	public void contextInitialized(ServletContextEvent arg0) {
		Util.printLogger(logger, "初始化定时任务=======(精确到 时：分)============");
		this.scheduExec =  Executors.newScheduledThreadPool(2);   
		TimersTask timer=new TimersTask(); 
		//scheduleAtFixedRate(任务类,延迟,间隔,延迟与间隔数量单位)
		scheduExec.scheduleAtFixedRate(timer, 2, 1*60, TimeUnit.SECONDS);

	}*/
	
	
	
	private Timer timers=null;
	public void contextDestroyed(ServletContextEvent event) {
		//在这里关闭监听器,同时关闭定时器  
		System.out.println("销毁定时任务....");
		Util.printLogger(logger, "销毁定时任务===================");
		timers.cancel();      
	}

	public void contextInitialized(ServletContextEvent event) {
		if("true".equalsIgnoreCase(Util.getProValue("public.system.uat"))){
			Util.printLogger(logger,"The test environment is not allowed to perform a timed task.");
			return;
		}
		Util.printLogger(logger, "初始化定时任务=======(精确到 时：分)============"); 
		timers=new Timer();    
		
		if(Util.objIsNULL(Constant.applicationPath)){
			ServletContext con=event.getServletContext();
			Constant.applicationPath=con.getRealPath("");
		}
	     //调用exportHistoryBean,0表示任务无延迟,5*1000表示每隔5秒执行任务,60*60*1000表示一个小时     
	     timers.schedule(new TimersTask(), 5*1000, 60*1000);      
	     //timer.schedule(new Task(), 0, 10*1000);       
	}

}
