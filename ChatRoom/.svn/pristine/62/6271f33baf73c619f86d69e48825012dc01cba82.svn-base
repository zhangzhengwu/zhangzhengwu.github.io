package com.orlando.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

@WebListener
public class LoginCountListener implements ServletContextListener {
	
	private File file;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext application = event.getServletContext();
//			String path = application.getContextPath();  // /20180926WebProject
			String path = application.getRealPath("/");
			this.file = new File(path+"/WEB-INF/count.txt");
//			System.out.println(file.getAbsolutePath());
//			F:\prosay\eclipse_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\20180926WebProject\WEB-INF\count.txt
			if(!this.file.exists()){
				this.file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader(this.file));
			String str = br.readLine();
			br.close();
			if(str != null){
				application.setAttribute("countnum", Integer.parseInt(str.split("=")[1]));
			}else{
				application.setAttribute("countnum", 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	    // 编码注销JDBC驱动
	    this.cleanUp();
		
		
		ServletContext application = event.getServletContext();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.file));
			bw.write("count="+application.getAttribute("countnum"));
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cleanUp() {
	    // 由于从tomcat6.0.24版本开始，在server.xml中增加了对内存泄漏的检测。
	    // JDBC注册的驱动未注销，有可能导致内存泄漏，故而产生异常，导致线程无法正常终止。
	    // 解决方案：手动注销驱动
	    // 参考资料：https://blog.csdn.net/ouyida3/article/details/49534839
		Driver d = null;
		try {
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while(drivers.hasMoreElements()){
				d = drivers.nextElement();
				DriverManager.deregisterDriver(d);
				System.out.println(String.format("ContextFinalizer:Driver %s deregistered", d));  //字符串格式化
			}
		} catch (SQLException e) {
			System.out.println(String.format("ContextFinalizer:Error deregistering driver %s", d) + ":" + e);
			e.printStackTrace();
		}
		try {
			AbandonedConnectionCleanupThread.shutdown();
		} catch (InterruptedException e) {
			 System.out.println("ContextFinalizer:SEVERE problem cleaning up: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}
