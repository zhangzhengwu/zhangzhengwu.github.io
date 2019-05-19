package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.sun.org.apache.regexp.internal.recompile;

import util.DBManager;

import dao.RequestRecordDao;
import entity.RequestNewBean;
/**
 * RequestRecordDao 实现类
 * @author kingxu
 *
 */
public class RequestRecordDaoImpl implements RequestRecordDao {
	Connection con = null;
	PreparedStatement ps = null;
	String sql = "";
	Logger logger = Logger.getLogger(RequestRecordDaoImpl.class);
	//保存
	public int saveRequestRecord(String UrgentDate,String code, String name, String quantity,
			String updName, String Layout_Type,String Urgent) {
		int r = -1;
		try {
			con = DBManager.getCon();
			sql = "insert req_record values('"+UrgentDate+"','" + code + "','" + name
					+ "','CD','" + quantity + "','" + updName + "','"
					+ Layout_Type + "','"+Urgent+"')";
			logger.info("保存RequestRecord信息："+sql);
			ps = con.prepareStatement(sql);
			r = ps.executeUpdate();
			if (r <= 0) {
				logger.error("保存RequestRecord信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存RequestRecord信息异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}

	/**
	 * updateRequestRecord
	 */
	public int updateRequestRecord(RequestNewBean rnb,String Payer, String rePayer) {
		int r=-1;
		try{
			con=DBManager.getCon();
			sql="update req_record set code='"+Payer+"', quantity='"+rnb.getQuantity()+"' ,upd_name='"+rnb.getUpd_name()+"', Layout_Type='"+rnb.getLayout_type()+"',Urgent='"+rnb.getUrgent()+"' where code='"+rePayer+"' and request_date='"+rnb.getUrgentDate()+"'";
			logger.info("保存RequestRecord信息："+sql);
			ps=con.prepareStatement(sql);
			r=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("修改RequestRecord信息异常！"+e);
		}finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}
/**
 * 验证Elite
 */
	public boolean vailElite_request_new(String staffcode) {
		boolean flag=false;
		try{
			con=DBManager.getCon();
			sql="select * from request_new where card_type='N' and quantity>0 and staff_code=? and eliteTeam='Y' and urgent='N' and date_format(UrgentDate,'%Y')=date_format(now(),'%Y') ";
			logger.info("查询Elite Team 的办理记录    sql:==="+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				flag= true;
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询Elite Team 的办理记录时出现："+e.toString());
		}finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		
		
		return flag;
	}
	/**
	 * 验证Elite 并排除本条记录
	 * @param staffcode
	 * @param urgentDate
	 * @return
	 */
	public boolean vailElite_request_new(String staffcode,String urgentDate) {
		boolean flag=false;
		try{
			con=DBManager.getCon();
			sql="select * from request_new where card_type='N' and quantity>0 and staff_code=? and eliteTeam='Y' and urgent='N' and UrgentDate!=? and date_format(UrgentDate,'%Y')=date_format(now(),'%Y') ";
			logger.info("查询Elite Team 的办理记录    sql:==="+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			ps.setString(2, urgentDate);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				flag= true;
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询Elite Team 的办理记录时出现："+e.toString());
		}finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		
		
		return flag;
	}

 

public boolean vailElite_request_staff(String staffcode) {
	boolean flag=false;
	try{
		con=DBManager.getCon();
		sql="select * from request_staff where card_type='N' and staff_code=? and eliteTeam='Y' and urgent='N' and date_format(UrgentDate,'%Y')=date_format(now(),'%Y') ";
		logger.info("查询staff Elite Team 的办理记录   sql:==="+sql);
		ps=con.prepareStatement(sql);
		ps.setString(1, staffcode);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			flag= true;
		}
		rs.close();
	}catch(Exception e){
		e.printStackTrace();
		logger.error("查询staff Elite Team 的办理记录时出现 ："+e.toString());
	}finally{
		//关闭连接
		DBManager.closeCon(con);
	}
	
	
	return flag;
}

public boolean vailElite_request_macau(String staffcode) {
	boolean flag=false;
	try{
		con=DBManager.getCon();
		sql="select * from request_macau where card_type='N' and staff_code=? and eliteTeam='Y' and urgent='N' and date_format(UrgentDate,'%Y')=date_format(now(),'%Y')";
		logger.info("查询Elite Team 成员的办理记录    sql:==="+sql);
		ps=con.prepareStatement(sql);
		ps.setString(1, staffcode);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			flag= true;
		}
		rs.close();
	}catch(Exception e){
		e.printStackTrace();
		logger.error("查询Elite Team 成员办理记录时 出现 ："+e.toString());
	}finally{
		//关闭连接
		DBManager.closeCon(con);
	}
	
	
	return flag;
}


}