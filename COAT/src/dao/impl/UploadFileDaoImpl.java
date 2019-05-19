package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import dao.UploadFileDao;

public class UploadFileDaoImpl implements UploadFileDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(UploadFileDaoImpl.class);
	
	/**
	 * // TODO 保存附件
	 */
	public int addUploadFile(String staffcode, String uploadFileName,
			String addDate) {
		int num=-1;
		try{
			con=DBManager.getCon();
			sql="insert uploadfile(staffcode,filename,impdate,remark,remarka,remarkb)values(?,?,?,?,?,?)";
		ps=con.prepareStatement(sql);
		logger.info("保存附件：stafcode:"+staffcode+";文件名："+uploadFileName);
		ps.setString(1,staffcode);
		ps.setString(2,uploadFileName);
		ps.setString(3,addDate);
		ps.setString(4,"");
		ps.setString(5,"");
		ps.setString(6,"");
		num=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("保存附件：stafcode:"+staffcode+";文件名："+uploadFileName+"時出現異常："+e.toString());
			num=-2;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
	/**
	 * // TODO 查询附件列表
	 */
	public  List<String> getUploadFile(String staffcode){
		List<String> list=new ArrayList<String>();
		try{
			con=DBManager.getCon();
			sql="select filename from uploadfile where staffcode=?";
			logger.info("查询uploadfile  staffcode:"+staffcode);
			ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString("filename"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询uploadfile时出现："+e.toString());
			list=null;
		}finally{
			DBManager.closeCon(con);
		}
		
		
		return list;
	}

}
