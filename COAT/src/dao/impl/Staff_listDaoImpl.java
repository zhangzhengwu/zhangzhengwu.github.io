package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.Page;
import util.Util;

import dao.Staff_listDao;
import entity.Staff_listBean;

public class Staff_listDaoImpl implements Staff_listDao {
	Connection connection = null;
	PreparedStatement ps = null;
	ResultSet rs=null;
	Logger logger = Logger.getLogger(QueryRequstDaoImpl.class);
	/**
	 * 条件查询（全查）
	 */
	public List<Staff_listBean> findAll(String staffcode, String companyName,String deptid, String staffname,Page page) {
		//判断参数数否为空
		String code=Util.objIsNULL(staffcode)?"":staffcode;
		String company=Util.objIsNULL(companyName)?"":companyName;
		String dept=Util.objIsNULL(deptid)?"":deptid;
		String staff=Util.objIsNULL(staffname)?"":staffname;
		List<Staff_listBean> staffListBeans=new ArrayList<Staff_listBean>();;
		try{
		connection = DBManager.getCon();
		String sql="select  * from staff_list s where s.staffcode like '%"+code+"%' and s.company like '%"+company+"%' and s.deptid like '%"+dept+"%' and s.staffname like '%"+staff+"%' LIMIT ?,? ";
		ps=connection.prepareStatement(sql);
		ps.setInt(1,(page.getCurPage()-1)*page.getPageSize());
	    ps.setInt(2, page.getPageSize());
		rs=ps.executeQuery();
	    while(rs.next()){
	    	Staff_listBean bean=new Staff_listBean();
	    	bean.setId(rs.getInt("id"));
	    	bean.setStaffcode(rs.getString("staffcode"));
	    	bean.setCompany(rs.getString("company"));
	    	bean.setDeptid(rs.getString("deptid"));
	    	bean.setStaffname(rs.getString("staffname"));
	    	bean.setEnglishname(rs.getString("englishname"));
	    	bean.setGrade(rs.getString("grade"));
	    	bean.setEmail(rs.getString("email"));
	    	bean.setEnrollmentDate(rs.getString("enrollmentDate"));
	    	bean.setTerminationDate(rs.getString("terminationDate"));
	    	staffListBeans.add(bean);
	        }
		}catch (Exception e) {
			logger.info("find  Staff  Error");
			e.printStackTrace();	
		}finally{
			  try{
				  DBManager.closeCon(connection);
				  ps.close();
				  rs.close();
			  }catch (Exception e) {
			logger.info("close Connection Error  Staff_list");
			}
		}
		return staffListBeans;
	}	 
	 /**
	  * 记录总页数
	  */
	 public int selectRow(String staffcode,String companyName,String deptid,String staffname){
		   //判断参数数否为空
			String code=Util.objIsNULL(staffcode)?"":staffcode;
			String company=Util.objIsNULL(companyName)?"":companyName;
			String dept=Util.objIsNULL(deptid)?"":deptid;
			String staff=Util.objIsNULL(staffname)?"":staffname;
			int record=0;
		   try{
			   connection = DBManager.getCon();
			   String sql="select count(*) from staff_list s  where s.staffcode like '%"+code+"%' and s.company like '%"+company+"%' and s.deptid like '%"+dept+"%' and s.staffname like '%"+staff+"%'";
			   ps=connection.prepareStatement(sql);
		       rs=ps.executeQuery(); 
		       if(rs.next()){
		    	 record=rs.getInt(1);  
		      }
		    }catch (Exception e) {
			 logger.info("find record Error");
			 e.printStackTrace();
		    }finally{
		    	try {
					connection.close();
					ps.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    } 
		    return record;
	 }
	 /**
	  * 数据导出（查询数据）
	  */
	 public List<Staff_listBean> exportDate(String staffcode,String companyName,String deptid,String staffname){
		//判断参数数否为空
			String code=Util.objIsNULL(staffcode)?"":staffcode;
			String company=Util.objIsNULL(companyName)?"":companyName;
			String dept=Util.objIsNULL(deptid)?"":deptid;
			String staff=Util.objIsNULL(staffname)?"":staffname;
			List<Staff_listBean> staffListBeans=new ArrayList<Staff_listBean>();
			try{
				//连接数据库查询
				connection = DBManager.getCon();
				String sql="select  * from staff_list s where s.staffcode like '%"+code+"%' and s.company like '%"+company+"%' and s.deptid like '%"+dept+"%' and s.staffname like '%"+staff+"%'";
				ps=connection.prepareStatement(sql);
				rs=ps.executeQuery();
			    while(rs.next()){
			    	Staff_listBean bean=new Staff_listBean();
			    	bean.setId(rs.getInt("id"));
			    	bean.setStaffcode(rs.getString("staffcode"));
			    	bean.setCompany(rs.getString("company"));
			    	bean.setDeptid(rs.getString("deptid"));
			    	bean.setStaffname(rs.getString("staffname"));
			    	bean.setEnglishname(rs.getString("englishname"));
			    	bean.setGrade(rs.getString("grade"));
			    	bean.setEmail(rs.getString("email"));
			    	bean.setEnrollmentDate(rs.getString("enrollmentDate"));
			    	bean.setTerminationDate(rs.getString("terminationDate"));
			    	staffListBeans.add(bean);
			        }
				}catch (Exception e) {
					logger.info("find  Staff  Error");
					e.printStackTrace();	
				}finally{
					  try{
						  DBManager.closeCon(connection);
						  ps.close();
						  rs.close();
					  }catch (Exception e) {
					logger.info("close Connection Error  Staff_list");
					}
				}
				return staffListBeans;
	 }
}
