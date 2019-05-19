package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import dao.ElitemDao;

public class ElitemDaoImpl implements ElitemDao {
	Connection con = null;
	PreparedStatement ps = null;
	String sql="";
	Logger logger = Logger.getLogger(ElitemDaoImpl.class);
	/**
	 * 获取AeConsultant和Elitem标识
	 * @param staffcode
	 * @return
	 * 	TODO 
	 */
	public List<String> vailElitemorAE(String staffcode) {
		 List<String> list=new ArrayList<String>();
	 try{
		 con=DBManager.getCon();
		 sql="select name,Code from(select '0' as name,staffcode as Code from aeconsultant_list   union "+
"select '1' as name  ,staffcode as Code from EliteTeam_list  ) c where c.code=?";
		logger.info("在 ElitemDaoimpl.vailElitemorAE 中验证是否是AEConsultant or Elite Team成员  sql:=="+sql);
		
		ps=con.prepareStatement(sql);
		 ps.setString(1, staffcode);
		 ResultSet rs=ps.executeQuery();
			rs.last();
			int num =rs.getRow();
			rs.beforeFirst();
		 while(rs.next()){
			 if(num<=1){
				 if(Integer.parseInt(rs.getString("name"))>0){
					 list.add(0,"");
				 }
			 }
			 list.add(Integer.parseInt(rs.getString("name")),rs.getString("Code"));
			 
			// list.add(1,rs.getString("EliteCode"));
		 }
		 rs.close();
		/* for(int i=0;i<list.size();i++){
			 System.out.println("==="+list.get(i));
		 }*/
	 }catch(Exception e){
		 list=null;
		 e.printStackTrace();
		 logger.error("在ElitemDaoImpl中的 vailElitemorAE 出现 "+e.toString());
	 }finally{
		 DBManager.closeCon(con);
	 }
		return list;
	}

}
