package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;



/**
 * @author wilson
 * 工具类
 */
public class DBManager_sqlservler{
	public static Connection con =null;
	public static Connection getCon() throws SQLException,ClassNotFoundException
	{
		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//Connection con =DriverManager.getConnection("jdbc:sqlserver://HKGSQL21:1433;databaseName=SZO_SYSTEM","officeadmin","admin_office");
		if(Util.objIsNULL(Constant.public_connection_driver_class_mssql)){
			String[] value=Util.getProValue(new String[]{"public.connection.driver_class.mssql","public.connection.url.mssql","public.connection.username.mssql","public.connection.password.mssql"});
			if(value.length>0){
				Constant.public_connection_driver_class_mssql=value[0];
				Constant.public_connection_url_mssql=value[1];
				Constant.public_connection_username_mssql=value[2];
				Constant.public_connection_password_mssql=value[3];
			}
		}
		Class.forName(Constant.public_connection_driver_class_mssql);
		con =DriverManager.getConnection(Constant.public_connection_url_mssql,Constant.public_connection_username_mssql,Constant.public_connection_password_mssql);
			
		return con;
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
	 
	public static void getConsultant() {
		try {
			con = getCon();
			String sql="select * from vSZOADM";
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
					//System.out.print(rs.getString(3)+"===");
				//System.out.println();
			}
			System.out.println("成功");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	

	public static void main(String[] args) {
		//getConsultant();
       
	}
	

	private Connection conn = null;

	private Statement stmt = null;

	ResultSet rs = null;

	private SQLConnectionPool connPool = null; /* 数据库连接池对象 */

	public DBManager_sqlservler() {
		//connPool = SQLSQLConnectionPoolUtils.GetPoolInstance();
	}

	public ResultSet executeQuery(String sql) throws Exception {
		try {
			/*
			 * Class.forName("org.logicalcobwebs.proxool.ProxoolDriver"); con =
			 * DriverManager.getConnection("proxool.xml-test");
			 */
			conn = connPool.getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			throw e;
		}
		// catch(NamingException e){throw e;}

		return rs;
	}

  
	 
  
	// 关闭stmt和关闭连接
	public void all_close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}

			if (conn != null) {
				connPool.returnConnection(conn);
				conn = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
