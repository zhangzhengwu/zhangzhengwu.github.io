package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import dao.Reject_informationDao;
import entity.Reject_information;

public class Reject_informationDaoImpl implements Reject_informationDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(Reject_informationDaoImpl.class);
  /**
	 * TODO 根据相关条件查询回馈用户邮件的内容
	 * @param model
	 * @param type
	 * @return
	 */
	public List<Reject_information> queryRejectInformation(String model,
			String type) {
		List<Reject_information> list=new ArrayList<Reject_information>();
		try{
			con=DBManager.getCon();
			sql="select * from reject_information where model=? and type=? order by Id ";
			logger.info("根据相关条件查询回馈用户邮件的内容      sql:===="+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, model);
			ps.setString(2, type);
			ResultSet rs=null;
			rs=ps.executeQuery();
			while(rs.next()){
				Reject_information ri=new Reject_information();
				ri.setId(rs.getInt("Id"));
				ri.setModel(rs.getString("model"));
				ri.setType(rs.getString("type"));
				ri.setResult(rs.getString("result"));
				ri.setResultName(rs.getString("resultName"));
				list.add(ri);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("根据相关条件查询回馈用户邮件的内容时出现："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	 
	

}
