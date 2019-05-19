package cn.admin.dao.impl.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.Util;
import cn.admin.dao.system.UpLoadExcelDao;

public class UpLoadExcelDaoImpl implements UpLoadExcelDao{

	Connection connection = null;
	PreparedStatement ps = null;
	ResultSet rs=null;
	Logger logger = Logger.getLogger(UpLoadExcelDaoImpl.class);
	
	public int saveExcel(List<List<Object>> list) {
		int numt=0;
		try{
			connection=DBManager.getCon();
			connection.setAutoCommit(false);
			
			for(int i=0;i<list.size();i++){
				List<Object> list2=list.get(i);
				
			if(!Util.objIsNULL(list2.get(0)+"")){
				
				String sql2="select loginname from s_user where loginname in('"+list.get(i).get(0)+"')";
				PreparedStatement ps=connection.prepareStatement(sql2);
				ResultSet rs=ps.executeQuery();
				String a="";
				while (rs.next()) {
					a=rs.getString("loginname");
				}
				if(!Util.objIsNULL(a)){	//loginName已经存在--->更新
					String sqlUpdate="update s_user set" +
							" loginpass='"+list2.get(1)+"' , " +
							" address='"+list2.get(2)+"'  " +
							"where loginname='"+list2.get(0)+"'";
					
					//System.out.println("-------------->"+sqlUpdate);
					
					PreparedStatement psUpdate=connection.prepareStatement(sqlUpdate);
					psUpdate.execute();
					numt=+1;
				}else{	//loginName不存在---->插入

					String sqlInsert="insert into s_user(loginname,loginpass,address) values(?,?,?) ";
					PreparedStatement ps2=connection.prepareStatement(sqlInsert);
					
					ps2.setString(1, list2.get(0)+"");
					ps2.setString(2, list2.get(1)+"");
					ps2.setString(3, list2.get(2)+"");	//Email暂时存放到公司地址address里
				
					ps2.addBatch();
					
					//System.out.println("插入的SQL-------->"+sqlInsert);
					ps2.executeBatch();
					numt=+1;
				}
				
				//--->为用户分配权限
				String ss="";
				String sql3="select userid from s_user where loginname in('"+list.get(i).get(0)+"')";
				PreparedStatement ps2=connection.prepareStatement(sql3);
				ResultSet rs2=ps2.executeQuery();
				while (rs2.next()) {
					ss=rs2.getString("userid");
				}
				String insertRole="insert into s_role(groupid,userid) values(?,?) ";
				PreparedStatement ps3=connection.prepareStatement(insertRole);
				ps3.setString(1, 16+"");
				ps3.setString(2, ss+"");
				ps3.addBatch();
				ps3.executeBatch();
				//System.out.println("插入的insertRole-------->"+insertRole);
				
				
			}	
			}
			
			connection.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return numt;
	
		
		
	}

}
