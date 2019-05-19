package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import util.DBManager;
import util.Util;
import dao.ConsMedicalDao;
import entity.Medical;

/**
 * ConsMedicalDaoImpl
 * 
 * @author Wilson
 * 
 */
public class ConsMedicalDaoImpl implements ConsMedicalDao {
	Connection con = null;
	PreparedStatement ps = null;
	Logger log = Logger.getLogger(ConsMedicalDaoImpl.class);
	String sql="";

	
	/**
	 * 
	 */
	public Medical queryBycode(String staffcode,String upddate){
Medical me=new Medical();
		try{
			con=DBManager.getCon();
			sql="select upd_Date,Terms_year ,upd_Name,SP_type  from medical_claim_record where staffcode='"+staffcode+"' and sfyx='Y' and upd_Date='"+upddate+"'";
			ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			me.setSP_type(rs.getString("SP_type"));
			me.setUpd_Date(rs.getString("upd_Date"));
			me.setUpd_Name(rs.getString("upd_Name"));
			me.setTerms_year(rs.getString("Terms_year"));
		}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return me;
	}
	/**
	 * 删除updateConsMedical  
	 */
	public int updateConsMedical(String staffcode, String upddate) {
		int r = -1;
		try {
			if(!Util.objIsNULL(staffcode) && !Util.objIsNULL(upddate)){
				Medical me=queryBycode(staffcode, upddate);
				if(me.getSP_type().equals("S")){
					upSpecial(staffcode, me.getUpd_Date(), me.getTerms_year(),me.getUpd_Name());
				}else{
					upNormal(staffcode, me.getUpd_Date(), me.getTerms_year(),me.getUpd_Name());
				}
				con=DBManager.getCon();
			 sql="update medical_claim_record set sfyx='D' where staffcode='"+staffcode+"' and upd_Date='"+upddate+"' and sfyx='Y' ";
				log.info("删除medical信息表SQL:"+sql);
				ps=con.prepareStatement(sql);
				r=ps.executeUpdate();
				if(r<0){
					log.info("删medical信息表失败");
				}
			}else{
				return 0;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("删除medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("删除medical信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	
	}
	public void upNormal(String StaffNo, String updDate, String used,String upd_name) {
		try {
	con=DBManager.getCon();
	sql="update medical_claim_record set medical_Normal = medical_Normal-1,Terms_year=Terms_year-1 where staffcode='"+StaffNo
	+"' and sfyx='Y' and Year('"+updDate+"') = Year(now()) and terms_year > "+used;
	log.info("upNormal"+sql);
	ps=con.prepareStatement(sql);
	ps.executeUpdate();
} catch (SQLException e) {
    log.error(upd_name+"在MedicalDaoImpl中修改medical状态时出现"+e.toString());
} catch (ClassNotFoundException e) {
	 log.error(upd_name+"在MedicalDaoImpl中修改medical状态时出现"+e.toString());
}finally{
	DBManager.closeCon(con);
}
	}
	public void upSpecial(String StaffNo, String updDate, String used ,String upd_name) {
		try {
			con=DBManager.getCon();
			sql="update medical_claim_record set medical_Special = medical_Special-1,Terms_year=Terms_year-1 where staffcode='"+StaffNo
			+"' and sfyx='Y' and Year('"+updDate+"') = Year(now()) and terms_year > "+used;
			log.info("upSpecial"+sql); 
			ps=con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			 log.error(upd_name+"在MedicalDaoImpl中修改medical状态时出现"+e.toString());
		} catch (ClassNotFoundException e) {
			 log.error(upd_name+"在MedicalDaoImpl中修改medical状态时出现"+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
	}
	
}
