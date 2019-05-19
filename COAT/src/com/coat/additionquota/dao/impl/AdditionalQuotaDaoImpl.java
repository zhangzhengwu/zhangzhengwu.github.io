package com.coat.additionquota.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.Pager;
import util.Util;
import com.coat.additionquota.dao.AdditionalQuotaDao;
import com.coat.additionquota.entity.CardquotaBean;
import com.coat.additionquota.entity.QueryAdditional;
import com.coat.loginrecord.entity.LoginRecord;

import dao.common.BaseDao;
import entity.RequestNewBean;

public class AdditionalQuotaDaoImpl extends BaseDao implements AdditionalQuotaDao {
	Connection con=null;
	PreparedStatement ps=null;
	StringBuffer sqlBuffer=new StringBuffer("");
	List<QueryAdditional> list=new ArrayList<QueryAdditional>();
	Logger logger = Logger.getLogger(AdditionalQuotaDaoImpl.class);
	
	//-->
	public Pager findPager(String[] fields, Pager page, Object... objects)throws Exception {
		String sql=" FROM loginrecord " +
				" where   date_format(add_date,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d')" +
				" and  date_format(add_date,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" +
				" and initials like ? " +
				" and sfyx like ?";
		
		String limit="order by add_date desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}
	
	public Pager findCardQuotaList(String[] fields, Pager page, Object... objects)throws Exception {
		//String sql="from (select employeeId,EmployeeName,t.C_Name,ReplPA_SA_NUM(EmployeeId)as eqnum, if(used is null,0,used)as used," +
			//	" if(addnum is null,0,addnum) as addnum,if(payernum is null,0,payernum)as selfpay,b.add_date  from " +
	/*	String sql="from (select employeeId,EmployeeName,t.C_Name,ReplPA_SA_NUM(EmployeeId)as eqnum, if(used is null,0,used)as used," +
			" if(addnum is null,0,addnum) as addnum,if(payernum is null,0,payernum)as selfpay,b.add_date  from " +
				" (select employeeId,C_Name,EmployeeName from cons_list  ) t 	LEFT JOIN( select code, sum(quantity)" +
				" as used from req_record where YEAR(request_date) = YEAR(NOW()) GROUP BY code )a on(a.code=t.employeeId) " +
				" left  join (select initials,sum(additional) as addnum ,add_date from nq_additional where sfyx='Y' and year(add_date)=year(now()) " +
				" group by initials)b on(t.employeeId=b.initials) left join( select payer, sum(Number)as payernum from " +
				" change_record where sfyx='Y' and YEAR(up_date) = YEAR(NOW())  group by payer)c on (t.employeeId=c.payer)" +
				" where t.EmployeeId like ? and EmployeeName like ? group by t.EmployeeId)x ";
		*/
		
		String sql="from (select employeeId,EmployeeName,t.C_Name,400 as eqnum, if(used is null,0,used)as used," +
				//" if(addnum is null,0,addnum) as addnum,(ReplPA_SA_NUM(EmployeeId)+if(addnum is null,0,addnum)) as total_quota,if(payernum is null,0,payernum)as selfpay,(ReplPA_SA_NUM(EmployeeId)+if(addnum is null,0,addnum)-if(used is null,0,used)) as balance ,if(b.add_date is null,'',b.add_date) as adddate  from " +
				" if(addnum is null,0,addnum) as addnum,(400+if(addnum is null,0,addnum)) as total_quota,if(payernum is null,0,payernum)as selfpay,(400+if(addnum is null,0,addnum)-if(used is null,0,used)+if(payernum is null,0,payernum)) as balance ,if(b.add_date is null,'',b.add_date) as adddate  from " +
				" (select employeeId,C_Name,EmployeeName from cons_list  ) t 	LEFT JOIN( select code, sum(quantity)" +
				" as used from req_record where YEAR(request_date) = YEAR(NOW()) GROUP BY code )a on(a.code=t.employeeId) " +
				" left  join (select initials,sum(additional) as addnum ,add_date from nq_additional where sfyx='Y' and year(add_date)=year(now()) " +
				" group by initials)b on(t.employeeId=b.initials) left join( select payer, sum(Number)as payernum from " +
				" change_record where sfyx='Y' and YEAR(up_date) = YEAR(NOW())  group by payer)c on (t.employeeId=c.payer)" +
				" where t.EmployeeId like ? and EmployeeName like ? group by t.EmployeeId)x ";
				
		
		
		String limit=" limit ?,? ";
		//System.out.println(sql+"----"+limit);
		Pager pager=null;
		try{
			pager=super.findPager(new String[]{"EmployeeId","EmployeeName","C_Name","eqnum","addnum","used","selfpay","adddate","balance"}, sql, limit, page, objects);
			
		}finally{
			super.closeConnection();
		}
			return pager;
	}
	
	
	/**
	 * 查询Additional
	 *//*
	public List<QueryAdditional> getQueryAdditional() {
		String sql="";
		try {
			con=DBManager.getCon();
			sql="select * from nq_additional where sfyx='Y' order by add_date desc";
			ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			logger.info("查询Additional实现类_AdditionalDaoImpl:"+sql);
			while(rs.next()){
				QueryAdditional qa=new QueryAdditional();
				qa.setStaffNo(rs.getString(1));
				qa.setName(rs.getString(2));
				qa.setNum(rs.getString(3));
				qa.setRemark(rs.getString(4));
				qa.setAdd_name(rs.getString(5));
				qa.setAdd_date(rs.getString(6));
				qa.setUpd_date(rs.getString(8));
				qa.setUpd_name(rs.getString(7));
				qa.setSfyx(rs.getString(9));
				list.add(qa);
			}
			rs.close();
			super.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询Additional实现类异常："+e);
		} catch (ClassNotFoundException eNotFound) {
			eNotFound.printStackTrace();
			logger.error("查询Additional实现类异常："+eNotFound);
		}
		finally{
			DBManager.closeCon(con);
		}
	 return list;
	}*/
	
	/*
	*//**
	 * add方法
	 *//*
	public void add(QueryAdditional qa) {
		String	sql="insert nq_additional(initials,name,additional,remarks,add_user,add_date,upd_user,upd_date,sfyx) values('"+qa.getStaffNo()+"','"+qa.getName()+"','"+qa.getNum()+"','"+qa.getRemark()+"','"+qa.getAdd_name()+"',now(),'',NULL,'"+qa.getSfyx()+"')";
		logger.info("保存Additional_SQL:"+sql);
		try {
			con=DBManager.getCon();
			ps=con.prepareStatement(sql);
			 int r=-1;
			 r=ps.executeUpdate();
		 
			 if(r<0){
				 logger.info("插入Additional失敗！");
			 }
			 super.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("保存Additional实现类异常："+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("保存Additional实现类异常："+e);
		}finally{
			DBManager.closeCon(con);
		}
	}*/
	//-->
	public int saveLogin(QueryAdditional qa) throws SQLException {
		int r = -1;
		String	sql="insert nq_additional(initials,name,additional,remarks,add_user,add_date,upd_user,upd_date,sfyx) values('"+qa.getStaffNo()+"','"+qa.getName()+"','"+qa.getNum()+"','"+qa.getRemark()+"','"+qa.getAdd_name()+"',now(),'',NULL,'"+qa.getSfyx()+"')";
		try {
            r = saveEntity(sql);
			if(r<0){
				 logger.info("save nq_additional error==["+sql+"]");
			} else {
			     logger.info("save nq_additional success==["+sql+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save nq_additional exception==["+sql+"]："+e);
			r = -1;
		}finally{
			closeConnection();
		}
		return r;
	}

/*
	public int getRows(String StaffNo, String Valid, String startDate,
			String endDate) {
		int num=-1;
		try {
			con=DBManager.getCon();
				sqlBuffer=new StringBuffer("select count(*) as Num from nq_additional where initials like '%"+StaffNo+"%' ");		
			if(!Valid.equals("")){
				sqlBuffer.append(" and sfyx = '"+Valid+"' ");
			}
			if(!Util.objIsNULL(startDate)){
				sqlBuffer.append(" and date_format(add_date,'%Y-%m-%d') >='"+startDate+"' ");
			}
			if(!Util.objIsNULL(endDate)){
				sqlBuffer.append(" and date_format(add_date,'%Y-%m-%d') <='"+endDate+"' ");
			}
			logger.info(sqlBuffer.toString());
			ps = con.prepareStatement(sqlBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=Integer.parseInt(rs.getString("Num"));
			}
			ps.close();
			rs.close();
			super.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}*/
	
/*	public QueryAdditional getAdditional(String StaffNo, String remark) {
		QueryAdditional qa = new QueryAdditional();
		String sql = "";
		try {
			con = DBManager.getCon();
			sql = "select * from nq_additional where initials='" + StaffNo
					+ "' and remarks='" + remark + "'";
			logger.info("查询Additional SQL:"+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				qa.setStaffNo(rs.getString(1));
				qa.setName(rs.getString(2));
				qa.setNum(rs.getString(3));
				qa.setAdditional(rs.getString(3));
				qa.setRemark(rs.getString(4));
				qa.setAdd_name(rs.getString(5));
				qa.setAdd_date(rs.getString(6));
				qa.setUpd_date(rs.getString(7));
				qa.setUpd_name(rs.getString(8));
				qa.setSfyx(rs.getString(9));
			}
			ps.close();
			rs.close();
			super.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询Additional异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return qa;
	}*/
	
	
	/**
	 * 修改 Additional
	 * @throws SQLException 
	 */
	public int updateAdditional(String StaffNo, String Additional,String Remarks, String sfyx, String re) throws SQLException {
		String sql = "";
		int r = -1;
		try {
			//con = DBManager.getCon();
			/*sql = "update nq_additional set additional='" + Additional
					+ "',remarks='" + Remarks + "',sfyx='" + sfyx
					+ "',upd_user='admin',upd_date=now() where initials='"
					+ StaffNo + "' and remarks='" + re + "'";
			logger.info("修改 Additional SQL"+sql);*/
			//ps = con.prepareStatement(sql);
			//r = ps.executeUpdate();
			sql = "update nq_additional set additional=?,remarks=?,sfyx=?,upd_user='admin',upd_date=now() where initials=?" +
					" and remarks=? ";
			r=update(sql,new Object[]{Additional,Remarks,sfyx,StaffNo,re});
			if (r > 0) {
				logger.info("修改 Additional成功！");
			} else {
				logger.info("修改 Additional失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改 Additional异常！"+e);
		} finally {
			//关闭连接
			//DBManager.closeCon(con);
			super.closeConnection();
		}
		return r;

	}
	/**
	 * 更新RequestNew，但不更新Quantity 普通权限
	 */
	public int updateAdditionals(RequestNewBean rnb) {
		String sql="";
		int r=-1;
		try{
			con=DBManager.getCon();
			/*sql="update request_new set  name='"+rnb.getName()+"' , name_chinese='"+rnb.getName_chinese()+"', title_english='"+rnb.getTitle_english()+"', title_chinese='"+rnb.getTitle_chinese()+"', external_english='"+rnb.getExternal_english()+"', external_chinese='"+rnb.getExternal_chinese()+"', academic_title_e='"+rnb.getAcademic_title_e()+"', academic_title_c='"+rnb.getAcademic_title_c()+"', profess_title_e='"+rnb.getProfess_title_e()+"', profess_title_c='"+rnb.getProfess_title_c()+"', tr_reg_no='"+rnb.getTr_reg_no()+"', ce_no='"+rnb.getCe_no()+"', mpf_no='"+rnb.getMpf_no()+"', e_mail='"+rnb.getE_mail()+"', direct_line='"+rnb.getDirect_line()+"', fax='"+rnb.getFax()+"',  bobile_number='"+rnb.getBobile_number()+"', upd_date='"+rnb.getUpd_date()+"', upd_name='"+rnb.getUpd_name()+"' ,location='"+rnb.getLocation()+"',CAM_only='"+rnb.getCAM_only()+"',CFS_only='"+rnb.getCFS_only()+"',CIS_only='"+rnb.getCIS_only()+"',CCL_only='"+rnb.getCCL_only()+"',CFSH_only='"+rnb.getCFSH_only()+"',CMS_only='"+rnb.getCMS_only()+"',CFG_only='"+rnb.getCFG_only()+"',Blank_only='"+rnb.getBlank_only()+"' where UrgentDate='"+rnb.getUrgentDate()+"' and Staff_code='"+rnb.getStaff_code()+"'";*/
			
				sql="update request_new set card_type='U' , upd_date='"+DateUtils.getNowDateTime()+"' where Staff_code='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
			logger.info(rnb.getUpd_name()+"：修改 request_new SQL:"+sql);
			ps=con.prepareStatement(sql);
			r=ps.executeUpdate();
			super.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(rnb.getUpd_name()+"：修改request_new异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
			
		}
		return r;
	}
	/***
	 * 更新requestNew 并更新Quantity 管理员权限
	 * @param rnb
	 * @return
	 */
	public int updateNumber(RequestNewBean rnb,String reStaffNo) {
		String sql="";
		int r=-1;
		try{
			con=DBManager.getCon();
			if(rnb.getStaff_code().equals(reStaffNo)){//是否修改了code
				sql="update request_new set card_type='U'  where Staff_code='"+rnb.getStaff_code()+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
			}else{
				 sql="update request_new set  card_type='"+rnb.getStaff_code()+"'  where Staff_code='"+reStaffNo+"' and UrgentDate='"+rnb.getUrgentDate()+"'";
			
			/*sql="update request_new set Payer='"+rnb.getPayer()+"',Staff_code='"+rnb.getStaff_code()+"',urgent='"+rnb.getUrgent()+"',layout_type='"+rnb.getLayout_type()+"',name='"+rnb.getName()+"',name_chinese='"+rnb.getName_chinese()+"',title_english='"+rnb.getTitle_english()+"',title_chinese='"+rnb.getTitle_chinese()+"',external_english='"+rnb.getExternal_english()+"',external_chinese='"+rnb.getExternal_chinese()+"',academic_title_e='"+rnb.getAcademic_title_e()+"',academic_title_c='"+rnb.getAcademic_title_c()+"',profess_title_e='"+rnb.getProfess_title_e()+"',profess_title_c='"+rnb.getProfess_title_c()+"',tr_reg_no='"+rnb.getTr_reg_no()+"',ce_no='"+rnb.getCe_no()+"',mpf_no='"+rnb.getMpf_no()+"',e_mail='"+rnb.getE_mail()+"',direct_line='"+rnb.getDirect_line()+"',fax='"+rnb.getFax()+"',bobile_number='"+rnb.getBobile_number()+"',upd_date='"+rnb.getUpd_date()+"',upd_name='"+rnb.getUpd_name()+"',quantity='"+rnb.getQuantity()+"',location='"+rnb.getLocation()+"',CAM_only='"+rnb.getCAM_only()+"',CFS_only='"+rnb.getCFS_only()+"',CIS_only='"+rnb.getCIS_only()+"',CCL_only='"+rnb.getCCL_only()+"',CFSH_only='"+rnb.getCFSH_only()+"',CMS_only='"+rnb.getCMS_only()+"',CFG_only='"+rnb.getCFG_only()+"',Blank_only='"+rnb.getBlank_only()+"' where UrgentDate='"+rnb.getUrgentDate()+"' and Staff_code='"+reStaffNo+"'";*/
			}
			logger.info(rnb.getUpd_name()+"：修改 request_new SQL:"+sql);
			ps=con.prepareStatement(sql);
			r=ps.executeUpdate();
			super.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(rnb.getUpd_name()+"：修改request_new异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}
	/**
	 * 过滤重复数据
	 */
	public int selectRepeat(String staffcode, String remark) {
		 String sql="";
		 int num=-1;
		 try{
			 con=DBManager.getCon();
			 sql="select count(*) as num from nq_additional where initials=? and remarks=? ";
					ps=con.prepareStatement(sql);
					ps.setString(1, staffcode);
					ps.setString(2, remark);
					ResultSet rs=ps.executeQuery();
					if(rs.next()){
					num=rs.getInt(1);
					}
					rs.close();
					super.closeConnection();
		 }catch(Exception e){
			 e.printStackTrace();
			 logger.error("判断数据是否重复时出现异常："+e);
		 }finally{
			 DBManager.closeCon(con);
		 }
		 
		return num;
	}
	
	/**
	 * 查询所有
	 *//*
	public List<CardquotaBean> queryCardQuotaList(String staffcode,String staffname,Page page) {
		
		List<CardquotaBean> list = new ArrayList<CardquotaBean>();
		try {
			con= DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer(" SELECT EmployeeId,EmployeeName,t.C_Name,ReplPA_SA_NUM(EmployeeId) as eqnum,Tzero(sum(Replnum(additional))) AS additional,"+ 
					  " FORMAT(ReplPA_SA_NUM(EmployeeId)+Tzero(sum(Replnum(additional))),0) as total,Tzero(c.quantity) as Quots_used, "+
			          " Tzero(sum(d.number)) as Self_paid,FORMAT(ReplPA_SA_NUM(EmployeeId)+Tzero(sum(Replnum(additional)))-Tzero(c.quantity),0) as balance,b.add_date FROM  cons_list t "+ 
			       " left join (select nl.initials,sum(nl.additional) as additional ,nl.ADD_DATE from nq_additional nl where nl.sfyx='Y' and additional >0 and year(nl.ADD_DATE) = year(NOW()) "+ 
			            " group by nl.initials ) b on t.EmployeeId = b.initials "+         
			       " left join (SELECT code,name,cast(sum(quantity)as decimal) as quantity "+  
			            " FROM req_record WHERE year(request_date) = year(NOW()) group by code) c on t.EmployeeId = c.code "+            
			      " left join (select staffcode,sum(number) number from change_record WHERE year(AddDate) = year(NOW()) and sfyx='Y' group by staffcode) d on t.EmployeeId = d.staffcode ");
		
			stringBuffer.append(" where t.EmployeeId like '%"+staffcode+"%' and EmployeeName like '%"+staffname+"%' ");
			stringBuffer.append(" group by t.EmployeeId ");
			stringBuffer.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			logger.info(" 查询QueryCardQuotaList SQL:"+stringBuffer);
			ps = con.prepareStatement(stringBuffer.toString());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CardquotaBean cqBean = new CardquotaBean();
				cqBean.setInitials(rs.getString(1));
				cqBean.setName(rs.getString(2));
				cqBean.setName_china(rs.getString(3));
				cqBean.setEntitled_Quota(rs.getString(4));
				cqBean.setAdditional(rs.getString(5));
				cqBean.setTotal_Quota(rs.getString(6));
				cqBean.setQuota_Used(rs.getString(7));
				cqBean.setSelf_Paid(rs.getString(8));
				cqBean.setBalance(rs.getString(9));
				cqBean.setAddDate(rs.getString(10));
				list.add(cqBean);
			}
			rs.close();
			super.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("查询QueryCardQuotaList异常:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("查询QueryCardQuotaList异常:"+e);
		}finally
		{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	}*/
	
	/*public int getRows(String staffcode, String staffname) {
	int num=-1;
		try{
			con=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer(" SELECT count(*)  FROM  cons_list t "+ 
			       " left join (select nl.initials,sum(nl.additional) as additional ,nl.ADD_DATE from nq_additional nl where nl.sfyx='Y' and additional >0 and year(nl.ADD_DATE) = year(NOW()) "+ 
			            " group by nl.initials ) b on t.EmployeeId = b.initials "+         
			       " left join (SELECT code,name,FORMAT(sum(quantity),0) as quantity "+  
			            " FROM req_record WHERE year(request_date) = year(NOW()) group by code) c on t.EmployeeId = c.code "+            
			      " left join (select staffcode,sum(number) number from change_record WHERE year(AddDate) = year(NOW()) and sfyx='Y' group by staffcode) d on t.EmployeeId = d.staffcode ");
		
			stringBuffer.append(" where t.EmployeeId like '%"+staffcode+"%' and EmployeeName like '%"+staffname+"%' ");
			stringBuffer.append(" group by t.EmployeeId ");
			logger.info("QueryCardQuota Exp SQL:"+stringBuffer.toString());
			ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=rs.getRow();//获取总行数
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("在QueryCardQuotaDaoImpl 中根据条件查询数据个数时出现："+e.toString());
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	*/
/*	*//**
	 * 查找集合
	 * @return
	 * @throws SQLException 
	 *//*
	public ResultSet selectCardQuota(String staffcode,String staffname) throws SQLException{
		StringBuffer stringBuffer=new StringBuffer(" SELECT EmployeeId,EmployeeName,t.C_Name,ReplPA_SA_NUM(EmployeeId) as eqnum,Tzero(sum(Replnum(additional))) AS additional,"+ 
				  " FORMAT(ReplPA_SA_NUM(EmployeeId)+Tzero(sum(Replnum(additional))),0) as total,Tzero(c.quantity) as Quots_used, "+
		          " Tzero(sum(d.number)) as Self_paid,FORMAT(ReplPA_SA_NUM(EmployeeId)+Tzero(sum(Replnum(additional)))-Tzero(c.quantity),0) as balance,b.add_date FROM  cons_list t "+ 
		       " left join (select nl.initials,sum(nl.additional) as additional ,nl.ADD_DATE from nq_additional nl where nl.sfyx='Y' and additional >0 and year(nl.ADD_DATE) = year(NOW()) "+ 
		            " group by nl.initials ) b on t.EmployeeId = b.initials "+         
		       " left join (SELECT code,name,cast(sum(quantity)as decimal) as quantity "+  
		            " FROM req_record WHERE year(request_date) = year(NOW()) group by code) c on t.EmployeeId = c.code "+            
		      " left join (select staffcode,sum(number) number from change_record WHERE year(AddDate) = year(NOW()) and sfyx='Y' group by staffcode) d on t.EmployeeId = d.staffcode ");
	
		stringBuffer.append(" where t.EmployeeId like '%"+staffcode+"%' and EmployeeName like '%"+staffname+"%' ");
		stringBuffer.append(" group by t.EmployeeId ");
				
		try {
			super.closeConnection();
			con = DBManager.getCon();
			logger.info("QueryCardQuota Exp SQL:"+stringBuffer);
			//System.out.println(stringBuffer.toString());
			ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("查询QueryCardQuota 异常:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("查询QueryCardQuota 异常:"+e);
		}finally{
			//关闭连接
			//DBManager.closeCon(con);
		
		}
		return null;
	}*/
	
	/**
	 * 查找集合
	 * @return
	 * @throws SQLException 
	 */
	public List<Map<String,Object>> queryCardQuota(String staffcode,String staffname) throws SQLException{
		List<Map<String,Object>> lists=null;
		//String sql="select * from (select employeeId,EmployeeName,t.C_Name,ReplPA_SA_NUM(EmployeeId)as eqnum, if(used is null,0,used)as used," +
		String sql="select * from (select employeeId,EmployeeName,t.C_Name,400 as eqnum, if(used is null,0,used)as used," +
		//" if(addnum is null,0,addnum) as addnum,(ReplPA_SA_NUM(EmployeeId)+if(addnum is null,0,addnum)) as total_quota,if(payernum is null,0,payernum)as selfpay,(ReplPA_SA_NUM(EmployeeId)+if(addnum is null,0,addnum)-if(used is null,0,used)) as balance ,if(b.add_date is null,'',b.add_date) as adddate  from " +
		" if(addnum is null,0,addnum) as addnum,(400+if(addnum is null,0,addnum)) as total_quota,if(payernum is null,0,payernum)as selfpay,(400+if(addnum is null,0,addnum)-if(used is null,0,used)+if(payernum is null,0,payernum)) as balance ,if(b.add_date is null,'',b.add_date) as adddate  from " +
		" (select employeeId,C_Name,EmployeeName from cons_list  ) t 	LEFT JOIN( select code, sum(quantity)" +
		" as used from req_record where YEAR(request_date) = YEAR(NOW()) GROUP BY code )a on(a.code=t.employeeId) " +
		" left  join (select initials,sum(additional) as addnum ,add_date from nq_additional where sfyx='Y' and year(add_date)=year(now()) " +
		" group by initials)b on(t.employeeId=b.initials) left join( select payer, sum(Number)as payernum from " +
		" change_record where sfyx='Y' and YEAR(up_date) = YEAR(NOW())  group by payer)c on (t.employeeId=c.payer)" +
		" where t.EmployeeId like ? and EmployeeName like ? group by t.EmployeeId)x ";
		
		//System.out.println(sql);
		try{
			lists=listMap(sql,Util.modifyString(staffcode),Util.modifyString(staffname));
			super.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("在QueryCardQuotaDaoImpl 中根据条件查询数据个数时出现："+e.toString());
		}finally{
			closeConnection();
		}
		return lists;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, QueryAdditional.class);
	}

}
