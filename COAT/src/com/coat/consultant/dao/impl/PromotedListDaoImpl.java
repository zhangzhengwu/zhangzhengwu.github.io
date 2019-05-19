package com.coat.consultant.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DBManager_sqlservler;
import util.DateUtils;
import util.Util;

import com.coat.consultant.dao.PromotedListDao;
import com.coat.consultant.entity.PromotedList;

import dao.common.BaseDao;

public class PromotedListDaoImpl extends BaseDao implements PromotedListDao {

	@SuppressWarnings("unchecked")
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, PromotedList.class);
	}
	Logger logger =Logger.getLogger(PromotedListDaoImpl.class);
	
	public PromotedListDaoImpl() {
	}

	/**
	 * 定时更新Promoted List-7:40
	 * @return
	 */
	public String timeTaskBatchSavePromotedlist(){
		String result="";
		Util.printLogger(logger, "开始执行指定任务-Promoted_list");
		try{
			PromotedListDao promotedDao=new PromotedListDaoImpl();
			List<PromotedList> list=promotedDao.findPromotedListByHKView();
			batchSavePromoted(list);
			result="success";
			Util.printLogger(logger, "指定任务-Promoted_list-->同步成功!");
		} catch(Exception e){
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger, "指定任务-Promoted_list-->同步失败!"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 保存因晋升需要调整的名片数量 - 7:42
	 * @return
	 */
	public String timeTaskBatchSaveAdditional(){
		String result="";
		Util.printLogger(logger, "开始执行指定任务-SaveAdditional");
		try{
			PromotedListDao promotedDao=new PromotedListDaoImpl();
			List<PromotedList> list=promotedDao.findNewAdditional();
			result=batchSaveAdditional(list);
			Util.printLogger(logger, "指定任务-SaveAdditional-->同步成功!");
		} catch(Exception e){
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger, "指定任务-SaveAdditional-->同步失败!"+e.getMessage());
		}
		return result;
	}
	
	public String batchSaveAdditional(List<PromotedList> list){
		String result = "";
		Connection con = null;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);//禁止自动提交事务
			String remark = "";
			String now = DateUtils.getNowDateTime();
			for(int i=0;i<list.size();i++){
				PromotedList pl=list.get(i);
				StringBuffer sql=new StringBuffer("insert nq_additional(initials,name,additional,remarks,add_user,add_date,upd_user,upd_date,sfyx) " +
						"values(?,?,100,?,'TimerTask','"+now+"','','','Y')");
				PreparedStatement ps=con.prepareStatement(sql.toString());
				ps.setString(1, pl.getStaffcode());
				ps.setString(2, pl.getStaffname());
				//promoted to CWMA on 2016-05-24
				remark = "promoted to "+pl.getGrade()+" on "+pl.getPromotedate();
				ps.setString(3, remark);
		        int flag = ps.executeUpdate();
		        if(flag<1){
				  throw new RuntimeException("保存因晋升需要调整的名片数量失败.");
			    }
		        //查询promoted_list_data中是否有该条记录，没有则插入promoted_list_data
		        if(!StaffcodeAndPromotedDateIfExist(pl.getStaffcode(),pl.getPromotedate())){
					StringBuffer sql1=new StringBuffer("insert into promoted_list_data(staffcode,staffname,grade,resign,promotedate,reason) values(?,?,?,?,?,?)");
					PreparedStatement ps1=con.prepareStatement(sql1.toString());
					ps1.setString(1, pl.getStaffcode());
					ps1.setString(2, pl.getStaffname());
					ps1.setString(3, pl.getGrade());
					ps1.setString(4, pl.getResign());
					ps1.setString(5, pl.getPromotedate());
					ps1.setString(6, pl.getReason());
			        int flag2 = ps1.executeUpdate();
			        if(flag2<1){
					  throw new RuntimeException("保存因晋升需要调整的名片数量记录失败.");
				    }
		        }
			}  
			con.commit();
			result = Util.getMsgJosnObject_success();
		}catch(Exception e){
			e.printStackTrace();
			try {
				con.rollback();
				logger.error("保存因晋升需要调整的名片数量时数据异常进行数据回滚");
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.info("保存因晋升需要调整的名片数量时数据回滚异常   "+e);
			}
			result = Util.joinException(e);
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	
	
	/**
	 * 查询该顾问因晋升加配额的记录是否存在
	 * @param Seatno
	 * @return
	 */
	public boolean StaffcodeAndPromotedDateIfExist(String staffcode,String promoteddate) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = false;
		try {
			sql = "select staffcode,promotedate from promoted_list_data where staffcode=? and promotedate=? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			ps.setString(2, promoteddate);
			rs = ps.executeQuery();
			while (rs.next()) {
				flag = true;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取seatno出现异常：" + e);
		} finally {
			DBManager.closeCon(con);
		}
		
		return flag;
	}
	
	
	
	
	/**
	 * 批量保存上传的PromotedList数据
	 * @author orlando
	 * @date 2018-01-30
	 * @param os
	 * @return
	 * @return int
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public int[] batchSavePromoted(List<PromotedList> list) throws Exception {
		int[] num=null;
		try{
			super.openTransaction();
			update2("delete from promoted_list;");
			String sql="insert into promoted_list(staffcode,staffname,grade,resign,promotedate,reason) values(?,?,?,?,?,?)";
			List<Object[]> param=new ArrayList<Object[]>();
			for(int i= 0;i<list.size();i++){
				PromotedList pl=list.get(i);	
				try{
						Object[] obj=new Object[6];
						obj[0]= pl.getStaffcode();
						obj[1]= pl.getStaffname();
						obj[2]= pl.getGrade();
						obj[3]= pl.getResign();
						obj[4]= pl.getPromotedate();
						obj[5]= pl.getReason();
						param.add(obj);
				}catch(Exception e){
					throw new Exception(e);
				}
			}
			//批量Promoted List
			num = batchInsert(sql, param);
			super.sumbitTransaction();
			Util.printLogger(logger, "同步Consultant List 成功 【"+num.length+" 条】");
		}catch(Exception e){
			Util.printLogger(logger, "同步Consultant List 时:"+e.getMessage());
			try {
				super.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new Exception(e);
		}finally{
			super.closeConnection();
		}
		return num;
	}
	

	
	/**
	 * 从HK SZO_ADM view获取最新的顾问晋升列表
	 * @author orlando
	 * @date 2018-01-30
	 * @return
	 * @return List<PromotedList>
	 * @throws Exception 
	 */
	public List<PromotedList> findPromotedListByHKView() throws Exception {
		List<PromotedList> plss=new ArrayList<PromotedList>();
		Connection  cons=null;
		String resign = "";
		try{
			cons=DBManager_sqlservler.getCon();
			String sqls="select staffcode,staffname,grade,resigns,effectivedatefrom,reason from [SZO_SYSTEM].[dbo].[vSZO_ICS_PeopleGradeInfo] where year(getdate()) = year(effectivedatefrom) and grade in ('PC','CWMA','AAD','AD','DD','AVP','VP','SVP') and Reason in ('Promotion','Completion of Probation')  order by staffcode,effectivedatefrom desc ";
			PreparedStatement pss=cons.prepareStatement(sqls);
			ResultSet rs=pss.executeQuery();
			while(rs.next()){
				PromotedList pl=new PromotedList();
				pl.setStaffcode(rs.getString("staffcode"));
				pl.setStaffname(rs.getString("staffname"));
				pl.setGrade(rs.getString("grade"));
				resign = rs.getString("resigns");
				resign = Util.objIsNULL(resign)?"":rs.getString("resigns");
				pl.setResign(resign);
				pl.setPromotedate(rs.getString("effectivedatefrom"));
				pl.setReason(rs.getString("reason"));
				plss.add(pl);
			}
			rs.close();
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			DBManager_sqlservler.closeCon(cons);
			super.closeConnection();
		}
		return plss;
	}
	public List<PromotedList> findNewAdditional() throws Exception {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PromotedList> list=null;
		try{
			con=DBManager.getCon();
			sql="select a.* from (select staffcode,staffname,GROUP_CONCAT(grade)as grade,resign,promotedate,reason from promoted_list GROUP BY staffcode,promotedate) a left join (select staffcode,staffname,GROUP_CONCAT(grade)as grade,resign,promotedate,reason from promoted_list_data GROUP BY staffcode,promotedate) b on year(a.promotedate)=year(NOW()) and a.staffcode = b.staffcode and a.promotedate = b.promotedate where b.staffcode is null";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			list=new ArrayList<PromotedList>();
			while(rs.next()){
				list.add(new PromotedList(rs.getString("staffcode"),
				                         rs.getString("staffname"),
				                         rs.getString("grade"),
				                         rs.getString("resign"),
				                         rs.getString("promotedate"),
				                         rs.getString("reason")));
							}
			rs.close();
		}catch(Exception e){
			logger.error("获取因晋升需要调整的名片数量信息时，网络连接异常");
			throw new Exception(e);
		}finally{
			con.close();
		}
		return list;
	}




}
