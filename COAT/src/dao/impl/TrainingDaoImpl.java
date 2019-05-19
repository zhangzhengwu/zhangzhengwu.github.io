package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;

import dao.TrainingDao;

public class TrainingDaoImpl implements TrainingDao {
	Logger logger = Logger.getLogger(TrainingDaoImpl.class);
	 
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "";
	
	
	
	/**
	 * 上传Training List
	 */
	public int saveTraining(List<List<Object>> list,String userName) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);//禁止自动提交事务
		
			ps=con.prepareStatement("update e_training set sfyx='N' where sfyx='Y'");
			ps.execute();
			ps=con.prepareStatement("insert into e_training(staffcode,trainingDate,creator,createDate,sfyx) values(?,?,'"+userName+"','"+DateUtils.getNowDateTime()+"','Y')");
			ps.clearBatch();
			for(int i=0;i<list.size();i++){
				List<Object> l=list.get(i);
				ps.setString(1, l.get(0).toString());
				ps.setString(2, l.get(1).toString());
	
				ps.addBatch();
				if(i%10000==0){
					ps.executeBatch();
				}
				num++;
			}
			ps.executeBatch();
			con.commit();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

}
