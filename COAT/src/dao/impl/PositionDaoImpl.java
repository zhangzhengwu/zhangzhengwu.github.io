package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;
import dao.PositionDao;

public class PositionDaoImpl implements PositionDao {
	Connection  conn = null;
	PreparedStatement ps= null;
	String sql="";
	   ResultSet rs=null;
	

	public List<String> getPositionList() {
		   List<String > list=new ArrayList<String>();
	try{
		sql="select title from title WHERE title!='' group by title  order by @rowid";
	   conn=DBManager.getCon();
	   ps=conn.prepareStatement(sql);
	   rs=ps.executeQuery();
	   while(rs.next()){
		   list.add(rs.getString("title"));
	   }
	    rs.close();
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
		return list;
	}
	
	

}
