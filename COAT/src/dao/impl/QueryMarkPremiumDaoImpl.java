package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import dao.QueryMarkPermiumDao;
/**
 * @author Wilson
 * add 2013年5月21日19:27:47
 */
public class QueryMarkPremiumDaoImpl implements QueryMarkPermiumDao {
	Connection connection=null;
	PreparedStatement  ps= null;
	Logger logger = Logger.getLogger(QueryMarkPremiumDaoImpl.class);
	/**
	 * Wilson
	 * CODE 验证
	 */
	public int findAllByCode(String procode) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select * from c_mar_product where procode ='"+procode+"'");
		
			logger.info("Query Stationery SQL:"+stringBuffer.toString());
			ps = connection.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				num=rs.getRow();//获取总行数
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("MarkPremium 中根据条件查询数据个数时出现："+e.toString());
			num=0;
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
  
	/**
	 * Wilson
	 * 产品信息，全部保存 重复 CODE 已做验证
	 */
	public int saveProduct(String procode, String ename, String cname,
			Double unitprice,Double clubprice,String spprice, String unit, Double quantity, String blbz) {
		int num=-1;
		try{
			connection=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("insert into c_mar_product(procode," +
					"englishname,chinesename,unitprice,c_clubprice,specialprice,unit,quantity,BLBZ,updates)" +
					" values('"+procode+"','"+ename+"','"+cname+"','"+unitprice+"','"+clubprice+"','"+spprice+"','"+
					unit+"','"+quantity+"','"+blbz+"','"+DateUtils.getNowDateTime()+"')");
		
			logger.info("save MarkPremium SQL:"+stringBuffer.toString());
			ps = connection.prepareStatement(stringBuffer.toString());
			num = ps.executeUpdate();
			if(num < 0){
				logger.info("save MarkPremium信息表失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("save MarkPremium 数据个数时出现："+e.toString());
			num=0;
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
	}
	/**
	 * Wilson
	 * 修改产品，
	 * 累计数量
	 */
	public int updProduct(String procode, String ename, String cname,
			Double unitprice,Double clubprice,String spprice, String unit, Double num, String blbz) {
		int r = -1;
		try {
			 
			 connection=DBManager.getCon();
			 String sql="update c_mar_product set englishname='"+ename+"',chinesename='"+cname+"',unitprice="+
				 unitprice+",c_clubprice="+clubprice+",specialprice='"+spprice+"',unit='"+
				 unit+"',quantity= quantity+"+num+",BLBZ='"+blbz+"',updates='"+DateUtils.getNowDateTime()+"' where procode='"+
				 procode+"' ";
			 logger.info("upd Markpremium Product信息表SQL:"+sql);
			 
			ps=connection.prepareStatement(sql);
			r=ps.executeUpdate();
			if(r<0){
				logger.info("upd Markpremium Product信息表失败");
			}
			 
				
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("upd Markpremium Product信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("upd Markpremium Product信息表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(connection);
		}
		return r;
			
	}

	public int delProduct(String procode) {
		Connection con = null;
		PreparedStatement ps = null;
		int num =0;
		String sql = "update c_mar_product set BLBZ='N' where procode ='"+procode+"'";
		//String sql = "delete from c_mar_product where procode ='"+procode+"'";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			num = ps.executeUpdate();
			//System.out.println("=====del========c_mar_product============" + num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			DBManager.closeCon(con);
		}
		return num;
	}
}
