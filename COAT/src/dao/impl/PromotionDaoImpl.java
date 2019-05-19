/**
 * 
 */
package dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.Util;
import dao.PromotionDao;
import entity.Promotion;
	
/**
 * @author kingxu
 *
 */
public class PromotionDaoImpl implements PromotionDao {
	PreparedStatement ps = null;
	String sql = "select * from pc_report_tmp";
	Connection con = null;
	Logger logger = Logger.getLogger(AddStaffRequestDaoImpl.class);
	public List<Promotion> getPromotionList() {
		try{
			sql="";
			con=DBManager.getCon();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		
		 
		return null;
	}
	public int addPromotion() {
		  CallableStatement cs = null;  
		  int i =-1;
		try{
			con=DBManager.getCon();
			  cs = con.prepareCall("{call into_pc_temp(?)}");   
			   //第一个参数的类型为Int   
			   cs.registerOutParameter(1, Types.INTEGER);   
			   cs.execute();   
	 i = cs.getInt(1);   
	 	if(i>0){
	 		return i;
	 	}else{
	 		return 0;
	 	}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return i;
	}
	public List<Promotion> findByTitle(String position,String staffcode,String staffName) {
		 List<Promotion> list =new ArrayList<Promotion>();
		 ResultSet rs=null;
		try{
			 con=DBManager.getCon();
			 
			 if(position.equals("Half-Consultant")){
				 sql="select * from pc_report_tmp where title in ('COT1-Z1','COT1-D1A','COT1-D2','COT1-D2A','COT1-N1A'," +
				 		"'CON-P1-N2-S'" +
				 		/*",'CON-P-N3','CON-P-N3S','CON-P-D4','CON-P-D4-S'" +*/
				 		",'CON-P1-NC') and Staff_Code like '%"+staffcode+"%' and Staff_Name like '%"+staffName+"%'";
			 }else if(position.equals("CON1")){
				 sql="select * from pc_report_tmp where title in ('COT2-Z1','COT2-D1A','COT2-D2'," +
				 "'COT2-D2A','COT2-N1A','CON-P2-N2-S','CON-P-N3','CON-P-N3S','CON-P-D4','CON-P-D4-S','CON-P2-NC') and Staff_Code like '%"+staffcode+"%' and Staff_Name like '%"+staffName+"%'";
			 }else{
			 sql="select * from pc_report_tmp where title='"+position+"' and Staff_Code like '%"+staffcode+"%' and Staff_Name like '%"+staffName+"%'";
			 }
			 ps=con.prepareStatement(sql);
			 rs=ps.executeQuery();
			 while(rs.next()){
				 Promotion pm=new Promotion();
				 pm.setDirect_Leader(rs.getString("Direct_Leader"));
				 pm.setStaffcode(rs.getString("Staff_Code"));
				 pm.setDD_VP(rs.getString("DD_VP"));
				 pm.setJoinTitle(rs.getString("Join_Title"));
				 pm.setTitle(rs.getString("Title"));
				 pm.setTrainnee(rs.getString("Trainee"));
				 pm.setStaffName(rs.getString("Staff_Name"));
				 pm.setGroupJoin(rs.getString("Date_of_Group_Join"));
				 pm.setResignDate(rs.getString("Resign_Date"));
				 pm.setTrAppDate(Util.objIsNULL(rs.getString("TR_App_Date"))?"":rs.getString("TR_App_Date"));
				 pm.setTr_Issue_Date(rs.getString("TR_Issue_Date"));
				 pm.setLrAppDate(rs.getString("LR_App_Date"));
				 pm.setLrIssueDate(rs.getString("LR_Issue_Date"));
				 pm.setLR_Normal_License(rs.getString("LR_Normal_License"));
				 pm.setITCompleted(rs.getString("IT_Completed_Date"));
				 pm.setITPhaseTwo(rs.getString("IT_Phase_Two"));
				 pm.setAddition_bv(rs.getString("addition_bv"));
				 list.add(pm);
			 }
			 rs.close();			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 DBManager.closeCon(con);
		 }
		return list;
	}

}
