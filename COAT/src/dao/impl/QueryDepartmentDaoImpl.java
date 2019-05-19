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
import dao.QueryDepartmentDao;
import dao.QueryStaffPositionDao;
import entity.Department;
import entity.Position_Staff_list;
import entity.Professional_title_Staff;
/**
 * QueryStaffPositionDao 实现类
 * @author Wilson.SHEN
 *
 */
public class QueryDepartmentDaoImpl implements QueryDepartmentDao {
	Connection connection=null;
	PreparedStatement  ps= null;
	Logger logger = Logger.getLogger(QueryDepartmentDaoImpl.class);
	//queryPosition
	public List<Department> queryDepartment() {

		List<Department> list = new ArrayList<Department>();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM position_list_staff WHERE SFYX='Y' order by position_ename ";
			logger.info("queryPosition SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Department peoBean = new Department();
				peoBean.setId(rs.getString(1));
//				peoBean.setPosition_ename(rs.getString(2));
//				peoBean.setPosition_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
				list.add(peoBean);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("queryPosition异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("queryPosition异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return list;
	}
	
	public boolean findDepartmentName(String department){
		boolean flag=false;
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT department FROM department WHERE SFYX='Y' and department=?";
			logger.info("queryDepartment by " +department+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ps.setString(1, department);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				flag=true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("findDepartmentName " +department+" 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("findDepartmentName " +department+" 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return flag;
	}
	
	public boolean findDepartmentEName(String department){
		boolean flag=false;
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT prof_title_ename FROM professional_title_staff WHERE SFYX='Y' and prof_title_ename=?";
			logger.info("queryDepartment by " +department+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ps.setString(1, department);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				flag=true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("find DepartmentName " +department+" 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("find DepartmentName " +department+" 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return flag;
	}
	
	
	public Department queryDepartment(String position_ename) {
		Department peoBean = new Department();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM position_list_staff WHERE SFYX='Y' and id="+position_ename;
			logger.info("queryPosition by " +position_ename+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peoBean.setId(rs.getString(1));
//				peoBean.setPosition_ename(rs.getString(2));
//				peoBean.setPosition_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("queryPositionby " +position_ename+" 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("queryPositionby " +position_ename+" 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return peoBean;
	}
	public Department queryDepartmentbyName(String position_ename) {
		Department peoBean = new Department();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM position_list_staff WHERE SFYX='Y' and position_ename='"+position_ename+"'";
			logger.info("queryPosition by " +position_ename+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peoBean.setId(rs.getString(1));
//				peoBean.setPosition_ename(rs.getString(2));
//				peoBean.setPosition_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setSfyx(rs.getString(6));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("queryPositionby " +position_ename+" 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("queryPositionby " +position_ename+" 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return peoBean;
	}

	public List<Department> findDepartment(String startDate,
			String endDate, String department, String sfyx, Page page) {
		List<Department> list=new ArrayList<Department>();
		
		try{
		connection=DBManager.getCon();
		String sql="select id,dpt,department,depart_head,depart_head_bak,add_date,add_name,upd_name,upd_date,sfyx from department WHERE sfyx like ? and department like ? ";
		if(!Util.objIsNULL(startDate)){
			sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
		}
		if(!Util.objIsNULL(endDate)){
			sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
		}
			sql+=" order by add_date desc limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize()+"";
			ps=connection.prepareStatement(sql);
			ps.setString(1, "%"+sfyx+"%");
			ps.setString(2, "%"+department+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate))
					ps.setString(4, endDate);
			}else{
				if(!Util.objIsNULL(endDate))
					ps.setString(3, endDate);
			}
			ResultSet rs=ps.executeQuery();
				while (rs.next()) {
					list.add(new Department(
							rs.getString("id"),
							rs.getString("dpt"),
							rs.getString("department"),
							rs.getString("depart_head"),
							rs.getString("depart_head_bak"),
							rs.getString("add_date"),
							rs.getString("add_name"),
							rs.getString("upd_Name"),
							rs.getString("upd_date"),
							rs.getString("sfyx")));
				}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("分页查询 Postion 异常！=="+e);
		}finally{
			DBManager.closeCon(connection);
		}
			
			
		return list;
	}

	public int getRow(String startDate, String endDate, String department,
			String sfyx) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			String sql="select count(*) from department WHERE sfyx like ? and department like ? ";
			if(!Util.objIsNULL(startDate)){
				sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
			}
			if(!Util.objIsNULL(endDate)){
				sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
			}
				sql+=" order by add_date desc";
				ps=connection.prepareStatement(sql);
				ps.setString(1, "%"+sfyx+"%");
				ps.setString(2, "%"+department+"%");
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
					num=Integer.parseInt(rs.getString(1));
				}
				rs.close();
		}catch(Exception e){
			logger.error("查询 Postion总条数 异常！=="+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}

	public Department queryDepartmentId(String deptId) {
		Department peoBean = new Department();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM department WHERE id='"+deptId+"'";
			logger.info("queryDepartment by deptId=" +deptId+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peoBean.setId(rs.getString(1));
				peoBean.setDpt(rs.getString(2));
				peoBean.setDepartment(rs.getString(3));
				peoBean.setDepart_head(rs.getString(4));
				peoBean.setDepart_head_bak(rs.getString(5));
				peoBean.setAdd_date(rs.getString(6));
				peoBean.setAdd_name(rs.getString(7));
				peoBean.setSfyx(rs.getString(10));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("queryDepartmentby deptId=" +deptId+" 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("queryDepartmentby deptId=" +deptId+" 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return peoBean;
	}

	
	public Professional_title_Staff queryDepartmentsId(String deptId) {
		Professional_title_Staff peoBean = new Professional_title_Staff();
		try {
			connection = DBManager.getCon();
			String sqlString ="SELECT * FROM professional_title_staff WHERE id='"+deptId+"'";
			logger.info("query Department by deptId=" +deptId+" SQL:"+sqlString);
			ps = connection.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peoBean.setId(rs.getString(1));
				peoBean.setProf_title_ename(rs.getString(2));
				peoBean.setProf_title_cname(rs.getString(3));
				peoBean.setAdd_date(rs.getString(4));
				peoBean.setAdd_name(rs.getString(5));
				peoBean.setUpd_name(rs.getString(6));
				peoBean.setUpd_date(rs.getString(7));
				peoBean.setSfyx(rs.getString(8));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("query Professional_title_Staff deptId=" +deptId+" 异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("query Professional_title_Staff deptId=" +deptId+" 异常！"+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return peoBean;
	}
	
	
	
	
	
	
	public int saveDepartment(Department pss) {
		int num=-1;
		try{
			connection = DBManager.getCon();
			String sql ="insert into department(dpt,department,depart_head,depart_head_bak,add_date,add_name,upd_name,upd_date,sfyx)values(?,?,?,?,?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setString(1, pss.getDpt());
			ps.setString(2, pss.getDepartment());
			ps.setString(3, pss.getDepart_head());
			ps.setString(4, pss.getDepart_head_bak());
			ps.setString(5, pss.getAdd_date());
			ps.setString(6, pss.getAdd_name());
			ps.setString(7, pss.getUpd_name());
			ps.setString(8, pss.getUpd_date());
			ps.setString(9, pss.getSfyx());
			num=ps.executeUpdate();
			logger.info("保存Department Data:"+Util.reflectTest(pss));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("保存Department 异常!==="+e);
		}
		return num;
	}
	
	
	
	public int saveDept(Professional_title_Staff pft) {
		int num=-1;
		try{
			connection = DBManager.getCon();
			String sql ="insert into professional_title_staff(prof_title_ename,prof_title_cname,add_date,add_name,sfyx)values(?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setString(1, pft.getProf_title_ename());
			ps.setString(2, pft.getProf_title_cname());
			ps.setString(3, pft.getAdd_date());
			ps.setString(4, pft.getAdd_name());
			ps.setString(5, pft.getSfyx());

			num=ps.executeUpdate();
			logger.info("保存Professional_title_Staff Data:"+Util.reflectTest(pft));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("保存Professional_title_Staff 异常!==="+e);
		}
		return num;
	}

	public int ModifyDepartment(Department pss) {
		int num=-1;
		try{
			connection = DBManager.getCon();
			String sql ="update department set dpt=?,department=?,depart_head=?,depart_head_bak=?,upd_name=?,upd_date=?,sfyx=? where id=?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, pss.getDpt());
			ps.setString(2, pss.getDepartment());
			ps.setString(3, pss.getDepart_head());
			ps.setString(4, pss.getDepart_head_bak());
			ps.setString(5, pss.getUpd_name());
			ps.setString(6, pss.getUpd_date());
			ps.setString(7, pss.getSfyx());
			ps.setInt(8, Integer.parseInt(pss.getId()));
			num=ps.executeUpdate();
			logger.info("修改Department by deptId=" +pss.getId()+" SQL:"+Util.reflectTest(pss));
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("修改Department 异常!==="+e);
		}
		return num;
	}
	public int ModifyDepartment(Professional_title_Staff pss) {
		int num=-1;
		try{
			connection = DBManager.getCon();
			String sql ="update Professional_title_Staff set prof_title_ename=?,prof_title_cname=?,upd_name=?,upd_date=?,sfyx=? where id=?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, pss.getProf_title_ename());
			ps.setString(2, pss.getProf_title_cname());
			ps.setString(3, pss.getUpd_name());
			ps.setString(4, pss.getUpd_date());
			ps.setString(5, pss.getSfyx());
			ps.setInt(6, Integer.parseInt(pss.getId()));
			num=ps.executeUpdate();
			logger.info("修改Professional_title_Staff by deptId=" +pss.getId()+" SQL:"+Util.reflectTest(pss));
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("修改Professional_title_Staff 异常!==="+e);
		}
		return num;
	}

	public ResultSet DownDepartment(String startDate, String endDate,String department, String sfyx) {
		ResultSet rs=null;
		try{
			connection=DBManager.getCon();
				
			String sql="select * from professional_title_staff WHERE sfyx like ? and prof_title_ename like ? ";
			if(!Util.objIsNULL(startDate)){
				sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
			}
			if(!Util.objIsNULL(endDate)){
				sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
			}
				sql+=" order by add_date desc ";
				ps=connection.prepareStatement(sql);
				ps.setString(1, "%"+sfyx+"%");
				ps.setString(2, "%"+department+"%");
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
				logger.error("导出Department 异常！=="+e);
			}
		return rs;
				
	}
	
	
	
	
	
	
	public int getRows(String startDate, String endDate, String departmemntName,
			String sfyx) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			String sql="select count(*) from professional_title_staff WHERE sfyx like ? and prof_title_ename like ? ";
			if(!Util.objIsNULL(startDate)){
				sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
			}
			if(!Util.objIsNULL(endDate)){
				sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
			}
				sql+=" order by add_date desc";
				ps=connection.prepareStatement(sql);
				ps.setString(1, "%"+sfyx+"%");
				ps.setString(2, "%"+departmemntName+"%");
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
					num=Integer.parseInt(rs.getString(1));
				}
				rs.close();
		}catch(Exception e){
			logger.error("查询 Professional_title_Staff总条数 异常！=="+e);
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	public List<Professional_title_Staff> findPosition(String startDate,
			String endDate, String positionEname, String sfyx, Page page) {
		List<Professional_title_Staff> list=new ArrayList<Professional_title_Staff>();
		
		try{
		connection=DBManager.getCon();
		String sql="select * from professional_title_staff WHERE sfyx like ? and prof_title_ename like ? ";
		if(!Util.objIsNULL(startDate)){
			sql+=" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d')";
		}
		if(!Util.objIsNULL(endDate)){
			sql+="and DATE_FORMAT(add_date,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d')";
		}
			sql+=" order by add_date desc limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize()+"";
			ps=connection.prepareStatement(sql);
			ps.setString(1, "%"+sfyx+"%");
			ps.setString(2, "%"+positionEname+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate))
					ps.setString(4, endDate);
			}else{
				if(!Util.objIsNULL(endDate))
					ps.setString(3, endDate);
			}
			ResultSet rs=ps.executeQuery();
				while (rs.next()) {
					list.add(new Professional_title_Staff(
							rs.getString("id"),
							rs.getString("prof_title_ename"),
							rs.getString("prof_title_cname"),
							rs.getString("add_date"),
							rs.getString("add_name"),
							rs.getString("upd_name"),
							rs.getString("upd_date"),
							rs.getString("sfyx")
							)
					);
				}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("分页查询 Professional_title_Staff 异常！=="+e);
		}finally{
			DBManager.closeCon(connection);
		}
			
			
		return list;
	}

	
	
}
