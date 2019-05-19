package util;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.logicalcobwebs.proxool.ConnectionListenerIF;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

import com.coat.interceptor.ConnectionListener;


/**
 * 数据库连接工具类
 */
public class DBManager{

	static{
		  // URL url = ClassLoader.getSystemResource("proxool.properties");  
	        try {  
	        //    String fileName = url.getPath();  
	           // PropertyConfigurator.configure("src/proxool.properties");  
	           // System.out.println("连接配置文件："+fileName);  
	        } catch (Exception e) {  
	              
	        }  
	}
	public DBManager() {
		 throw new RuntimeException("The class is not allowed to implement");
	}

	public static Connection getCon() throws SQLException,ClassNotFoundException
	{
		
		 Connection con =null;
			 Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			con = DriverManager.getConnection("proxool.jdbcmysql");
			con.setAutoCommit(true);
		return con;
	}
	
	public static void main(String[] args) {
		try{
		long start = System.currentTimeMillis();  
        for (int i = 0; i < 1; i++) {
        	System.out.println((i+1)+ "次开始执行--"); 
			test();
			
		}
        long end = System.currentTimeMillis();  
        long t = end - start;  
        System.out.print(t/1000);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		
		
	}
	
	
	public static void test(){
		
		 String sql = "select * from admin ";  
		  Connection con =null;  
		  PreparedStatement stmt = null;  
         ResultSet rs = null;
		 try{
			 con=getCon();
			 stmt=con.prepareStatement(sql);
			 rs=stmt.executeQuery();
			 while(rs.next()){
				 System.out.println(rs.getString(1));
			 }
			 
		 }catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeCon(con);
		}
		 
		
	}
	
	
	
	public static void closeCon(Connection con){
		if(con != null)
		{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}






  
