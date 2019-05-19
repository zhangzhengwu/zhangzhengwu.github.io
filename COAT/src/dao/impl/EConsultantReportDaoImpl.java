package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.Util;
import dao.EConsultantReportDao;
import entity.Econsreport;

public class EConsultantReportDaoImpl implements EConsultantReportDao {

	Logger logger = Logger.getLogger(EConsultantReportDaoImpl.class);
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	public List<Econsreport> querybystaff(String staffcode, String startDate,
			String endDate, int pageSize, int currentPage) {
		List<Econsreport> list=new ArrayList<Econsreport>();
		try{
			StringBuffer sal=new StringBuffer("select mapdate,recruiter,staffcode,position,staffname,convoy,time,total,cclub,penalty,noofmap,working,al_leave,sl_leave,ol_leave,borrow,emap,lateness,noshow,ontimr,e_lateness,e_noshow,e_onTime,updname,upddate from e_consrepot where staffcode like '%"+staffcode+"%'");
			if(!Util.objIsNULL(startDate)){
				sal.append(" and mapdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and mapdate <='"+endDate+"'");
			}
			sal.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			logger.info("查询Consultant  Emap  Report   sql:===="+sql);
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				Econsreport es =new Econsreport(rs.getString("mapdate"), rs.getString("recruiter"), rs.getString("staffcode") ,
						rs.getString("position"), rs.getString("staffname") , rs.getString("convoy"), rs.getString("time") ,
						/*rs.getString("day1"), rs.getString("day2"), rs.getString("day3"), rs.getString("day4"), rs.getString("day5"),
						rs.getString("day6"), rs.getString("day7"), rs.getString("day8"), rs.getString("day9"), rs.getString("day10") ,
						rs.getString("day11") , rs.getString("day12") , rs.getString("day13") , rs.getString("day14") ,
						rs.getString("day15"), rs.getString("day16") , rs.getString("day17") , rs.getString("day18") ,
						rs.getString("day19") , rs.getString("day20") , rs.getString("day21") , rs.getString("day22") ,
						rs.getString("day23") , rs.getString("day24"), rs.getString("day25") , rs.getString("day26") ,
						rs.getString("day27") , rs.getString("day28") , rs.getString("day29") , rs.getString("day30") ,
						rs.getString("day31") ,*/ rs.getString("total") , rs.getString("cclub"), rs.getString("penalty"),
						rs.getString("noofmap") , rs.getString("working"), rs.getString("al_leave") , rs.getString("sl_leave"),
						rs.getString("ol_leave") , rs.getString("borrow"), rs.getString("emap"), rs.getString("lateness"),
						rs.getString("noshow") , rs.getString("ontimr") , rs.getString("e_lateness"), rs.getString("e_noshow"),
						rs.getString("e_onTime") , rs.getString("updname") , rs.getString("upddate"));
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询Consultant Emap Report时出现： "+e);
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	public ResultSet queryrsbystaff(String staffcode, String startDate,String endDate, int pageSize, int currentPage) {
		ResultSet rs= null;
		try{
			StringBuffer sal=new StringBuffer("select e.recruiter,e.staffcode,position,staffname,convoy,time,day1,day2,day3,day4,day5,day6,day7,day8,day9,day10,day11,day12,day13,day14,day15,day16,day17,day18,day19,day20,day21,day22,day23,day24,day25,day26,day27,day28,day29,day30,day31,total,cclub,penalty,'',noofmap,'0',working,al_leave,sl_leave,ol_leave,borrow,emap,lateness,noshow,ontimr,e_lateness,e_noshow,e_onTime,b.grade from e_consrepot e LEFT join e_conslist b on(e.staffcode=b.staffcode)  where e.staffcode like '%"+staffcode+"%'");
			if(!Util.objIsNULL(startDate)){
				sal.append(" and mapdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and mapdate <='"+endDate+"'");
			}
			//sal.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DBManager.closeCon(con);
		}
		return rs;
	}
	
	/*public List<String []> queryrsbystaff(String staffcode, String startDate,String endDate, int pageSize, int currentPage) {
		ResultSet rs= null;
		List<String []>list=new ArrayList<String[]>();
		try{
			StringBuffer sal=new StringBuffer("select e.recruiter,e.staffcode,position,staffname,convoy,time,day1,day2,day3,day4," +
					"day5,day6,day7,day8,day9,day10,day11,day12,day13,day14,day15,day16,day17,day18,day19,day20,day21,day22," +
					"day23,day24,day25,day26,day27,day28,day29,day30,day31,total,cclub,penalty,'',noofmap,'0'," +
					"working,al_leave,sl_leave,ol_leave,borrow,emap,lateness,noshow,ontimr,e_lateness,e_noshow,e_onTime," +
					"b.grade from e_consrepot e LEFT join e_conslist b on(e.staffcode=b.staffcode)  where e.staffcode like '%"+staffcode+"%'");
			if(!Util.objIsNULL(startDate)){
				sal.append(" and mapdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and mapdate <='"+endDate+"'");
			}
			//sal.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					list.add(new String []{
						rs.getString("recruiter"),	
						rs.getString("staffcode"),	
						rs.getString("position"),	
						rs.getString("staffname"),	
						rs.getString("convoy"),	
						rs.getString("time"),	
						rs.getString("day1"),	
						rs.getString("day2"),	
						rs.getString("day3"),	
						rs.getString("day4"),	
						rs.getString("day5"),	
						rs.getString("day6"),	
						rs.getString("day7"),	
						rs.getString("day8"),	
						rs.getString("day9"),	
						rs.getString("day10"),	
						rs.getString("day11"),	
						rs.getString("day12"),	
						rs.getString("day13"),	
						rs.getString("day14"),	
						rs.getString("day15"),	
						rs.getString("day16"),	
						rs.getString("day17"),	
						rs.getString("day18"),	
						rs.getString("day19"),	
						rs.getString("day20"),	
						rs.getString("day21"),	
						rs.getString("day22"),	
						rs.getString("day23"),	
						rs.getString("day24"),	
						rs.getString("day25"),	
						rs.getString("day26"),	
						rs.getString("day27"),	
						rs.getString("day28"),	
						rs.getString("day29"),	
						rs.getString("day30"),	
						rs.getString("day31"),	
						rs.getString("total"),	
						rs.getString("cclub"),	
						rs.getString("penalty"),	
						"",	
						rs.getString("noofmap"),	
						"0",	
						rs.getString("working"),	
						rs.getString("al_leave"),	
						rs.getString("sl_leave"),	
						rs.getString("ol_leave"),	
						rs.getString("borrow"),	
						rs.getString("emap"),	
						rs.getString("lateness"),	
						rs.getString("noshow"),	
						rs.getString("ontimr"),	
						rs.getString("e_lateness"),	
						rs.getString("e_noshow"),	
						rs.getString("e_onTime"),	
						rs.getString("grade")
					});
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}*/
	
	public int getCount(String staffcode, String startDate, String endDate) {
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num from e_consrepot where staffcode like '%"+staffcode+"%'");
			if(!Util.objIsNULL(startDate)){
				sal.append(" and mapdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and mapdate <='"+endDate+"'");
			}

			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				num=Integer.parseInt(rs.getString("num"));
			}
				rs.close();
		}catch(Exception e){
			e.printStackTrace();
			num=0;
		}finally{
			DBManager.closeCon(con);
		}

		return num;
	}
	public int deleteConsultantReport(String staffcode, String mapdate) {
		int num=-1;
		try{
			con=DBManager.getCon();
			sql="delete from e_consrepot where staffcode='"+staffcode+"' and mapdate='"+mapdate+"'";
			logger.info("删除Consultant Emap  Report  sql:==="+sql);
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("删除Consultant Emap  Report时出现："+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}


}
