package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.Page;
import util.Util;
import dao.DepartMentDao;
import entity.Department;

public class DepartMentDaoImpl implements DepartMentDao {
	Connection connection=null;
	PreparedStatement  ps= null;
	Logger logger = Logger.getLogger(DepartMentDaoImpl.class);
	
	
	public List<Department> find(String startDate, String endDate, String dpt,String sfyx, Page page) {
		List<Department> dList=new ArrayList<Department>();
		String sql="select id,dpt,department,depart_head,depart_head_bak,add_date,add_name,upd_name,upd_date,sfyx from department WHERE sfyx like ? and dpt like ? ";
		if(!Util.objIsNULL(startDate))
			sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
		if(!Util.objIsNULL(endDate))
			sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
			sql+=" order by add_date desc limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize()+"";
		try{
				connection=DBManager.getCon();
				ps=connection.prepareStatement(sql);
				ps.setString(1, "%"+sfyx+"%");
				ps.setString(2, "%"+dpt+"%");
				if(!Util.objIsNULL(startDate)){
					ps.setString(3, startDate);
					if(!Util.objIsNULL(endDate))
						ps.setString(4, endDate);
				}else{
					if(!Util.objIsNULL(endDate))
						ps.setString(3, endDate);
				}
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					dList.add(new Department(rs.getString("id"), 
									rs.getString("dpt"),
									rs.getString("department"),
									rs.getString("depart_head"),
									rs.getString("depart_head_bak"),
									rs.getString("add_name"),
									rs.getString("add_date"),
									rs.getString("upd_name"),
									rs.getString("upd_date"),
									rs.getString("sfyx")));
				}
				rs.close();
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("查询Staff DepartMent 分页总条数时出现异常  == "+e);
			}finally{
				sql=null;
				DBManager.closeCon(connection);
			}
		return dList;
	}

	public ResultSet downDepartMent(String startDate, String endDate, String dpt,String sfyx){
		ResultSet rs=null;
		String sql="select id,dpt,department,depart_head,depart_head_bak,add_date,add_name,upd_name,upd_date,sfyx from department WHERE sfyx like ? and dpt like ? ";
		if(!Util.objIsNULL(startDate))
			sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
		if(!Util.objIsNULL(endDate))
			sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
		try{
				connection=DBManager.getCon();
				ps=connection.prepareStatement(sql);
				ps.setString(1, "%"+sfyx+"%");
				ps.setString(2, "%"+dpt+"%");
				if(!Util.objIsNULL(startDate)){
					ps.setString(3, startDate);
					if(!Util.objIsNULL(endDate))
						ps.setString(4, endDate);
				}else{
					if(!Util.objIsNULL(endDate))
						ps.setString(3, endDate);
				}
				rs=ps.executeQuery();
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("导出Staff DepartMent 出现异常  == "+e);
			}finally{
				sql=null;
			}
		return rs;
	}
	
	public int getRow(String startDate, String endDate, String dpt, String sfyx) {
		int num=-1;
		String sql="select count(id) from department WHERE sfyx like ? and dpt like ? ";
		if(!Util.objIsNULL(startDate)){
			sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
		}
		if(!Util.objIsNULL(endDate)){
			sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
		}
		try{
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ps.setString(1, "%"+sfyx+"%");
			ps.setString(2, "%"+dpt+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate))
					ps.setString(4, endDate);
			}else{
				if(!Util.objIsNULL(endDate))
					ps.setString(3, endDate);
			}
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询Staff DepartMent 分页总条数时出现异常  == "+e);
		}finally{
			sql=null;
			DBManager.closeCon(connection);
		}
		return num;
	}

	public Department findById(int dptId){
		Department dpt=null;
		String sql="select id,dpt,department,depart_head,depart_head_bak,add_date,add_name,upd_name,upd_date,sfyx from department where id=? ";
		try{
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ps.setInt(1, dptId);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				dpt=new Department(rs.getString("id"), 
						rs.getString("dpt"),
						rs.getString("department"),
						rs.getString("depart_head"),
						rs.getString("depart_head_bak"),
						rs.getString("add_name"),
						rs.getString("add_date"),
						rs.getString("upd_name"),
						rs.getString("upd_date"),
						rs.getString("sfyx"));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("验证Depart是否存在时出现异常---"+e);
		}finally{
			sql=null;
			DBManager.closeCon(connection);
		}
		return dpt;
	}
	
	public boolean findByDpt(String dpts){
		boolean flag=false;
		String sql="select id,dpt,department,depart_head,depart_head_bak,add_date,add_name,upd_name,upd_date,sfyx from department where dpt=? ";
		try{
			connection=DBManager.getCon();
			ps=connection.prepareStatement(sql);
			ps.setString(1, dpts);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				flag=true;
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("验证Depart是否存在时出现异常---"+e);
		}finally{
			sql=null;
			DBManager.closeCon(connection);
		}
		return flag;
	}
	
	public int modifyDepart(Department dpt) {
		int num=-1;
		try{
			connection = DBManager.getCon();
			String sql ="update department set dpt=?,department=?,depart_head=?,depart_head_bak=?,upd_name=?,upd_date=?,sfyx=? where id=?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, dpt.getDpt());
			ps.setString(2, dpt.getDepartment());
			ps.setString(3, dpt.getDepart_head());
			ps.setString(4, dpt.getDepart_head_bak());
			ps.setString(5, dpt.getUpd_name());
			ps.setString(6, dpt.getUpd_date());
			ps.setString(7, dpt.getSfyx());
			ps.setInt(8, Integer.parseInt(dpt.getId()));
			num=ps.executeUpdate();
			logger.info("修改Staff DepartMent by departmentId=" +dpt.getId()+" SQL:"+Util.reflectTest(dpt));
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("修改Staff Position 异常!==="+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}

	public int saveDepart(Department dpt) {
		int num=-1;
		try{
			connection = DBManager.getCon();
			String sql ="insert into department(dpt,department,depart_head,depart_head_bak,add_name,add_date,upd_name,upd_date,sfyx)values(?,?,?,?,?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setString(1, dpt.getDpt());
			ps.setString(2, dpt.getDepartment());
			ps.setString(3, dpt.getDepart_head());
			ps.setString(4, dpt.getDepart_head_bak());
			ps.setString(5, dpt.getAdd_name());
			ps.setString(6, dpt.getAdd_date());
			ps.setString(7, dpt.getUpd_name());
			ps.setString(8, dpt.getUpd_date());
			ps.setString(9, dpt.getSfyx());
			num=ps.executeUpdate();
			logger.info("保存Staff DepartMent  by departId=" +dpt.getId()+" Data:"+Util.reflectTest(dpt));
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("保存 Staff DepartMent 异常!==="+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}

	public boolean vailHead(String staffcode) {
		boolean flag=false;
		try{
			connection=DBManager.getCon();
			String sql="select dpt from department where sfyx='Y' and (depart_head=? or depart_head_bak=?) ";
			ps=connection.prepareStatement(sql);
			ps.setString(1, staffcode);
			ps.setString(2, staffcode);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
				flag=true;
			rs.close();	
		}catch (Exception e) {
			logger.error("验证staffcode是否为Department Head时出错--"+e.toString());
		}finally{
			DBManager.closeCon(connection);
		}
		return flag;
	}
	
	
	public boolean vailSupervisor(String staffcode) {
		boolean flag=false;
		try{
			connection=DBManager.getCon();
			String sql="select count(*) as num from request_staff_convoy_supervisor_view where (supervisor_ID=? or supercisor2_ID=?) ";
			ps=connection.prepareStatement(sql);
			ps.setString(1, staffcode);
			ps.setString(2, staffcode);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				if(rs.getInt("num")>0){
					flag=true;
				}else{
					flag=false;
				}
			}
			rs.close();	
		}catch (Exception e) {
			logger.error("验证staffcode是否为Supervisor时出错--"+e.toString());
		}finally{
			DBManager.closeCon(connection);
		}
		return flag;
	}
	

}
