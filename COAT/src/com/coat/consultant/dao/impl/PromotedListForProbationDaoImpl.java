package com.coat.consultant.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager_sqlservler;
import util.Util;

import com.coat.consultant.dao.PromotedListForProbationDao;
import com.coat.consultant.entity.PromotedListforprobation;

import dao.common.BaseDao;

public class PromotedListForProbationDaoImpl extends BaseDao implements PromotedListForProbationDao {

	@SuppressWarnings("unchecked")
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, PromotedListforprobation.class);
	}
	Logger logger =Logger.getLogger(PromotedListForProbationDaoImpl.class);
	
	public PromotedListForProbationDaoImpl() {
	}

	/**
	 * 定时更新Promoted List-7:20
	 * @return
	 */
	public String timeTaskBatchSavePromotedForProbationlist(){
		String result="";
		Util.printLogger(logger, "开始执行指定任务-promoted_listforprobation");
		try{
			PromotedListForProbationDao promotedDao=new PromotedListForProbationDaoImpl();
			List<PromotedListforprobation> list=promotedDao.findPromotedListByHKView();
			batchSavePromoted(list);
			result="success";
			Util.printLogger(logger, "指定任务-promoted_listforprobation-->同步成功!");
		} catch(Exception e){
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger, "指定任务-promoted_listforprobation-->同步失败!"+e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 批量保存上传的promoted_listforprobation数据
	 * @author orlando
	 * @date 2018-01-30
	 * @param os
	 * @return
	 * @return int
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public int[] batchSavePromoted(List<PromotedListforprobation> list) throws Exception {
		int[] num=null;
		try{
			super.openTransaction();
			update2("delete from promoted_listforprobation;");
			String sql="insert into promoted_listforprobation(staffcode,staffname,grade,resign,promotedate,reason) values(?,?,?,?,?,?)";
			List<Object[]> param=new ArrayList<Object[]>();
			for(int i= 0;i<list.size();i++){
				PromotedListforprobation pl=list.get(i);	
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
			Util.printLogger(logger, "同步promoted_listforprobation 成功 【"+num.length+" 条】");
		}catch(Exception e){
			Util.printLogger(logger, "同步promoted_listforprobation 时:"+e.getMessage());
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
	public List<PromotedListforprobation> findPromotedListByHKView() throws Exception {
		List<PromotedListforprobation> plss=new ArrayList<PromotedListforprobation>();
		Connection  cons=null;
		String resign = "";
		try{
			cons=DBManager_sqlservler.getCon();
			String sqls="SELECT staffcode,staffname,grade,resigns,effectivedatefrom,reason FROM [SZO_system].[dbo].[vSZO_ICS_PeopleGradeInfo] where reason = 'Completion of Probation' or reason = 'Completed Stage 1 Probation' or reason = 'Completed Stage 2 Probation' order by staffcode ";
			PreparedStatement pss=cons.prepareStatement(sqls);
			ResultSet rs=pss.executeQuery();
			while(rs.next()){
				PromotedListforprobation pl=new PromotedListforprobation();
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


}
