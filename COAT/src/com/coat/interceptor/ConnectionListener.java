package com.coat.interceptor;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.logicalcobwebs.proxool.ConnectionListenerIF;

public class ConnectionListener implements ConnectionListenerIF{
	  private static Logger logger = Logger.getLogger(ConnectionListener.class.getName());
	
	public void onBirth(Connection arg0) throws SQLException {
		// TODO Auto-generated method stub
		
		
	}

	public void onDeath(Connection arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
	//	System.out.println("onDeath-"+arg0);
		//System.out.println("onDeath-"+arg1);
	}

	public void onExecute(String arg0, long arg1) {
		// TODO Auto-generated method stub
		//System.out.println("onExecute-"+arg0);
		//System.out.println("onExecute-"+arg1);
		if(arg1>30l){
			System.out.println(String.format("%8d", arg1)+ "(milliseconds)  " + arg0);
		}
		if(isupdate(arg0)){
			System.out.println("记录修改语句["+util.DateUtils.getNowDateTime()+"]------"+arg0);
		}
	}
	
	public boolean isupdate(String sql){
		boolean flag=false;
		String type=sql.substring(0,10).toLowerCase();
		if(type.indexOf("insert")>-1||type.indexOf("update")>-1||type.indexOf("delete")>-1){
			flag=true;
		}
		
		return flag;
		
	}

	public void onFail(String arg0, Exception arg1) {
		// TODO Auto-generated method stub
		System.out.println("onFail-");
		System.out.println("onFail-"+arg0);
		System.out.println("onFail-"+arg1.getMessage());
		
		

		logger.error(String.format("%8d", arg1)
                 + "(milliseconds)\t" + arg0);
		
		
	}

	
}	


