package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Util;
import dao.StaffMotifyDao;

/**
 * StaffMotifyDaoImpl
 * 
 * @author Wilson
 * 
 */
public class StaffMotifyDaoImpl implements StaffMotifyDao {
	Connection con = null;
	PreparedStatement ps = null;
	Logger logger = Logger.getLogger(StaffMotifyDaoImpl.class);

	/**
	 * 删除RequestNew  
	 */
	public int updateStaffRequest(String staffcode, String urgentdate,String username) {
		int r = -1;
		try {
			if(!Util.objIsNULL(staffcode) && !Util.objIsNULL(urgentdate)){
				con=DBManager.getCon();
				String sql="update request_staff set card_type='D',upd_date='"+DateUtils.getNowDateTime()+"',upd_name='"+username+"' where staff_code='"+staffcode+"' and UrgentDate='"+urgentdate+"'";
				logger.info(username+"删除Staff信息表SQL:  ==="+sql);
				ps=con.prepareStatement(sql);
				r=ps.executeUpdate();
				if(r<0){
					logger.info(username+"删除Staff信息表失败");
				}
			}else{
				return 0;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(username+"删除Staff信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(username+"删除Staff信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	
	}
}
