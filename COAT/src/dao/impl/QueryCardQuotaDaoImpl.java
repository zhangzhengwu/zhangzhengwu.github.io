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
import dao.QueryCardQuotaDao;
import entity.CardquotaBean;
/**
 * QueryCardQuotaDao 实现类
 * @author Wilson.SHEN
 *
 */
public class QueryCardQuotaDaoImpl implements QueryCardQuotaDao {
	Connection connection = null;
	PreparedStatement ps = null;
	Logger logger = Logger.getLogger(QueryCardQuotaDaoImpl.class);
	/**
	 * 查找集合
	 * @return
	 */
	public ResultSet selectCardQuota(String staffcode,String staffname)
	{
		/*String sql="SELECT EmployeeId,EmployeeName,t.C_Name,400 as eqnum,Tzero(additional) AS additional, "
			+" FORMAT(400+Tzero(additional),0) as total,Tzero(b.quantity) as Quots_used,Tzero(sum(c.number)) as Self_paid," 
			+" FORMAT(400+Tzero(additional)-Tzero(b.quantity),0) as balance  "
			+" FROM  cons_list t left join nq_additional d on t.EmployeeId = d.initials  and sfyx='Y' AND   year(ADD_DATE) = year(NOW())"
			+" left join (SELECT code,name,FORMAT(sum(quantity),0) as quantity  FROM req_record WHERE year(request_date) = year(NOW()) group by code) b on t.EmployeeId = b.code "
			+" left join change_record c on t.EmployeeId = c.staffcode  AND   year(ADDDATE) = year(NOW()) "
			+" group by EmployeeId";*/
	/*	StringBuffer stringBuffer=new StringBuffer(" SELECT EmployeeId,EmployeeName,t.C_Name,ReplPA_SA_NUM(EmployeeId) as eqnum,Tzero(sum(Replnum(additional))) AS additional,"+ 
				  " FORMAT(ReplPA_SA_NUM(EmployeeId)+Tzero(sum(Replnum(additional))),0) as total,Tzero(c.quantity) as Quots_used, "+
		          " Tzero(sum(d.number)) as Self_paid,FORMAT(ReplPA_SA_NUM(EmployeeId)+Tzero(sum(Replnum(additional)))-Tzero(c.quantity),0) as balance FROM (select * from cons_list where EmployeeId like '%"+staffcode+"%') t "+ 
		       " left join (select nl.initials,cast(sum(nl.additional)as decimal) as additional from nq_additional nl where nl.sfyx='Y' and year(nl.ADD_DATE) = year(NOW()) "+ 
		            " group by nl.initials ) b on t.EmployeeId = b.initials "+         
		       " left join (SELECT code,name,cast(sum(quantity)as decimal) as quantity "+  
		            " FROM req_record WHERE year(request_date) = year(NOW()) group by code) c on t.EmployeeId = c.code "+            
		      " left join (select staffcode,sum(number) number from change_record WHERE year(AddDate) = year(NOW()) group by staffcode) d on t.EmployeeId = d.staffcode ");
	
		stringBuffer.append("where t.EmployeeId like '%"+staffcode+"%' and EmployeeName like '%"+staffname+"%' ");
		stringBuffer.append(" group by EmployeeId ");*/
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
			connection = DBManager.getCon();
			logger.info("QueryCardQuota Exp SQL:"+stringBuffer.toString());
		//	System.out.println(stringBuffer.toString());
			ps = connection.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("查询QueryCardQuota 异常:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("查询QueryCardQuota 异常:"+e);
		}finally
		{
			//关闭连接
			//DBManager.closeCon(connection);
		}
		return null;
	}
	/**
	 * 查询所有
	 */
	public List<CardquotaBean> queryCardQuotaList(String staffcode,String staffname,Page page) {
		
		List<CardquotaBean> list = new ArrayList<CardquotaBean>();
		try {
			connection = DBManager.getCon();
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
			ps = connection.prepareStatement(stringBuffer.toString());
			
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
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("查询QueryCardQuotaList异常:"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("查询QueryCardQuotaList异常:"+e);
		}finally
		{
			//关闭连接
			DBManager.closeCon(connection);
		}
		return list;
	}
	public int getRows(String staffcode, String staffname) {
	int num=-1;
		try{
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer(" SELECT count(*)  FROM  cons_list t "+ 
			       " left join (select nl.initials,sum(nl.additional) as additional ,nl.ADD_DATE from nq_additional nl where nl.sfyx='Y' and additional >0 and year(nl.ADD_DATE) = year(NOW()) "+ 
			            " group by nl.initials ) b on t.EmployeeId = b.initials "+         
			       " left join (SELECT code,name,FORMAT(sum(quantity),0) as quantity "+  
			            " FROM req_record WHERE year(request_date) = year(NOW()) group by code) c on t.EmployeeId = c.code "+            
			      " left join (select staffcode,sum(number) number from change_record WHERE year(AddDate) = year(NOW()) and sfyx='Y' group by staffcode) d on t.EmployeeId = d.staffcode ");
		
			stringBuffer.append(" where t.EmployeeId like '%"+staffcode+"%' and EmployeeName like '%"+staffname+"%' ");
			stringBuffer.append(" group by t.EmployeeId ");
						logger.info("QueryCardQuota Exp SQL:"+stringBuffer.toString());
						ps = connection.prepareStatement(stringBuffer.toString());
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
			DBManager.closeCon(connection);
		}
		return num;
	}
	
}
