package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author king
 * 工具类
 */
public class DBManager_oracle{
	public static Connection con =null;



	public DBManager_oracle() {
		
	}
	public static Connection getCon(String...type) throws SQLException,ClassNotFoundException{
		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//Connection con =DriverManager.getConnection("jdbc:sqlserver://HKGSQL21:1433;databaseName=SZO_SYSTEM","officeadmin","admin_office");
		String m="";
		for (String arg : type) {  
	          m=arg;
	          System.out.println("type==>"+arg);
	    } 
		
		if(Util.objIsNULL(m)){
			System.out.println("-->连接到CONVOYPROD数据库");
			if(Util.objIsNULL(Constant.public_connection_driver_class_oracle)){
				String[] value=Util.getProValue(new String[]{"public.connection.driver_class.oracle","public.connection.url.oracle","public.connection.username.oracle","public.connection.password.oracle"});
				if(value.length>0){
					Constant.public_connection_driver_class_oracle=value[0];
					Constant.public_connection_url_oracle=value[1];
					Constant.public_connection_username_oracle=value[2];
					Constant.public_connection_password_oracle=value[3];
				}
			}
			Class.forName(Constant.public_connection_driver_class_oracle);
			con =DriverManager.getConnection(Constant.public_connection_url_oracle,Constant.public_connection_username_oracle,Constant.public_connection_password_oracle);
			return con;
		}else if(m.equals("CONVOYUAT")){
			System.out.println("-->连接到CONVOYUAT数据库");
			String[] value=Util.getProValue(new String[]{"public.connection.driver_class.oracle","public.connection.url.oracle_CONVOYUAT","public.connection.username.oracle_CONVOYUAT","public.connection.password.oracle_CONVOYUAT"});
			//Class.forName(Constant.public_connection_driver_class_oracle);//加载驱动
			Class.forName(value[0]);//加载驱动
			//获取数据库连接，登录用户名、密码
		//	con =DriverManager.getConnection(Constant.public_connection_url_oracle,Constant.public_connection_username_oracle,Constant.public_connection_password_oracle);
			con =DriverManager.getConnection(value[1],value[2],value[3]);
			return con;
		}else{
			System.out.println("-->连接到convoypeople数据库");
			String[] value=Util.getProValue(new String[]{"public.connection.driver_class.mssql","public.connection.url.mssql_convoypeople","public.connection.username.mssql_convoypeople","public.connection.password.mssql_convoypeople"});
			Class.forName(value[0]);//加载驱动
			//获取数据库连接，登录用户名、密码
			con =DriverManager.getConnection(value[1],value[2],value[3]);
			return con;
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
	
	public static Connection getCon2() throws SQLException,ClassNotFoundException{
	 
		Class.forName(Util.getProValue("public.connection.driver_class.mssql"));
		con =DriverManager.getConnection("jdbc:sqlserver://10.28.4.23:1433;DatabaseName=convoypeople","hr_uat","hr_uat");
		return con;
	}
	
 public static void main(String[] args) {
	try{
		getCon("1");
		List<Map<String,Object>> list=rsMapper(executeQuery("select * from company_view "));
		for (Map<String, Object> map : list) {
			for(String key:map.keySet()){
				System.out.print(key);
				System.out.println(":"+map.get(key));
			}
			
			
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
}
	public static void mains(String[] args) {
		try{
			getCon("CONVOYUAT");
			//List<Map<String,Object>> list=rsMapper(executeQuery("select * from V_CUST_VSMART_BPPS where employee_status='Active' and EMPLOYEE_ID = 'KC0102' "));
			//List<Map<String,Object>> list=rsMapper(executeQuery("select * from company_view "));
			List<Map<String,Object>> list=rsMapper(executeQuery("select * from CONVOYUAT.V_CUST_NAME_CARD where ROWNUM<=10 "));
				for (Map<String, Object> map : list) {
					for(String key:map.keySet()){
						System.out.print(key);
						System.out.println(":"+map.get(key));
					}
					
					
				}
				/*System.out.println("Staff Code : "+map.get("EMPLOYEE_ID"));//no editable
				System.out.println("Quantity : "+map.get(""));//editable
				System.out.println("Location : "+map.get("LOCATION_CODE"));//
				System.out.println("Layout : "+map.get(""));//editable
				System.out.println("New Name Card : "+map.get(""));//editable
				System.out.println("Urgent Case : "+map.get(""));//editable
				System.out.println("EnglishName : "+map.get("CHAN CHXN MXNG CHERIS"));//no editable
				System.out.println("Chinese Name : "+map.get("CHINESE_NAME"));//no editable
				String position=map.get("POSITION_ENG_DESC")+"";
				String positionChi=map.get("POSITION_CHI_DESC")+"";
			
				if(position.indexOf("Head of")>-1){
					if(position.indexOf(",")>-1){
						System.out.println("Position(Eng)"+position.substring(0,position.indexOf(",")));
						System.out.println("Position(Chi)"+positionChi.substring(0,position.indexOf(",")));
					}else{
						System.out.println("Position(Eng)"+position);
						System.out.println("Position(Chi)"+positionChi);
					}
					System.out.println("Department(Eng)"+map.get(""));
					System.out.println("Department(CHI)"+map.get(""));
				}else{
					if(position.indexOf(",")>-1){
						System.out.println("Position(Eng) : "+position.substring(0,position.indexOf(",")));
						if(!Util.objIsNULL(positionChi)){
							System.out.println("Position(Chi) : "+positionChi.substring(0,position.indexOf(",")));
						}else{
							System.out.println("Position(Chi) : ");
						}
						System.out.println("Department(Eng) : "+position.substring(position.indexOf(",")+1));
						if(!Util.objIsNULL(positionChi)){
							System.out.println("Department_CHI:"+positionChi.substring(positionChi.indexOf(",")+1));
						}else{
							System.out.println("Department_CHI:");
						}
					}else{//职位中不包含逗号
						System.out.println("Position(Eng) : "+position);
						System.out.println("Position(Chi) : "+positionChi);
						System.out.println("Department(Eng) : "+map.get("DEPARTMENT_ENG_DESC"));
						System.out.println("Department(CHI) : "+map.get("DEPARTMENT_ENG_DESC"));
					}
				}

				System.out.println("Academic Title (Eng) : "+map.get(""));
				System.out.println("Academic Title (Chi) : "+map.get(""));
				System.out.println("Professional Title (Eng) : "+map.get(""));
				System.out.println("Professional Title (Chi) : "+map.get(""));
				System.out.println("External Title (Eng) : "+map.get("EXTERNAL_POSITION_ENG_DESC"));
				System.out.println("External Title (Chi) : "+map.get("EXTERNAL_POSITION_CHI_DESC"));
				System.out.println("TR Reg No : "+map.get(""));
				System.out.println("CE No : "+map.get(""));
				System.out.println("MPFA No : "+map.get(""));
				System.out.println("* HKFI No : "+map.get(""));
				System.out.println("Mobile : "+map.get(""));
				System.out.println("FAX : "+map.get(""));
				System.out.println("DIRECT Line : "+map.get(""));
				System.out.println("E-mail : "+map.get(""));
				System.out.println("Company : "+map.get(""));//editable
				System.out.println("Remark : "+map.get(""));//editable					
			}*/
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeCon(con);
		}
		
	}
 



	public static ResultSet executeQuery(String sql) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			/*
			 * Class.forName("org.logicalcobwebs.proxool.ProxoolDriver"); con =
			 * DriverManager.getConnection("proxool.xml-test");
			 */
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			throw e;
		}
		// catch(NamingException e){throw e;}

		return rs;
	}

	  public static List<Map<String,Object>> rsMapper(ResultSet rs)  throws Exception{
	    	List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
	    	try{
		    	 ResultSetMetaData md = rs.getMetaData();
		    	 Map<String,Object> map=null; 
		    	 while(rs.next()){
		    		 map=new HashMap<String, Object>(); 
		        	 for(int i=1;i<=md.getColumnCount();i++){
		        		 map.put(md.getColumnName(i), rs.getObject(i));
		        	 }
		        	 listMap.add(map);
		    	 }
		    	 rs.close();
	    	 }catch (Exception e) {
	    		 e.printStackTrace();
	    		 throw e;
			}
			return listMap;
	    	
	    }
	 
  
 


}
