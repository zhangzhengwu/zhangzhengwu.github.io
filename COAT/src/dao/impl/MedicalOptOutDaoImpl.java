package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Pager;
import util.Util;
import dao.MedicalOptOutDao;
import dao.common.BaseDao;
import entity.MedicalOutPutList;

public class MedicalOptOutDaoImpl extends BaseDao implements MedicalOptOutDao {

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;
	Logger logger = Logger.getLogger(MedicalOptOutDaoImpl.class);

	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, MedicalOutPutList.class);
	}
	
	
	public Pager findMedicalOptOutList(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM medical_out_put_list " +
				" where sfyx = 'Y' and date_format(createdate,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d')" +
				" and  date_format(createdate,'%Y-%m-%d') <= date_format(?,'%Y-%m-%d')" +
				" and staffcode like ?";
		String limit="order by createdate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}
	
	public int save(MedicalOutPutList medicalOutPutList) throws SQLException{
		
		int num = -1;
		String sql1 = "";
			try {
				super.openTransaction();
				sql1 = "insert into medical_out_put_list(staffcode,staffname,createdate,creater,remark,sfyx) values (?,?,?,?,?,?)";
				num = super.saveEntity(sql1,medicalOutPutList.getStaffcode(),
						medicalOutPutList.getStaffname(),
						medicalOutPutList.getCreatedate(),
						medicalOutPutList.getCreater(),
						medicalOutPutList.getRemark(),
						medicalOutPutList.getSfyx());
				if(num < 1){
					throw new RuntimeException("新增失败！");
				}
				super.sumbitTransaction();
			} catch (Exception e) {
				num = -1;
				super.rollbackTransaction();
			} finally{
				super.closeConnection();
			}
		return num;
	}

	public int delete(String staffcode) throws SQLException{
		int num = -1;
		String sql1 = "";
		try{
			super.openTransaction();
			sql1 = "update medical_out_put_list set `sfyx` = 'N' where staffcode = ? and `sfyx` = 'Y'  ";
			num =  super.update2(sql1, staffcode);
			if(num<0){
				throw new RuntimeException("删除失败 !");
			}
			super.sumbitTransaction();
			logger.info("delete medical_out_put_list success");
		}catch(Exception e){
			num = -1;
			super.rollbackTransaction();
		}finally{
			super.closeConnection(); 
		}
		return num;
	}
	

	public int ifExist(String staffcode){
		int num = 1;
		try{
			con = DBManager.getCon();
			String sql="select count(*) as totalnum from medical_out_put_list where sfyx='Y'  and staffcode= ?" ;
			ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()){
				num = rs.getInt("totalnum");
			}
			rs.close();
			logger.info("select seat_change_apply success");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Delete seat_change_apply信息保存异常！"+e.getMessage());
		}finally{
			DBManager.closeCon(con); 
		}
		return num;
	}

	/**
	 * 根据条件导出excel
	 */
	public List<MedicalOutPutList> exportDate(String startdate,String enddate,String staffcode){
		List<MedicalOutPutList> list=new ArrayList<MedicalOutPutList>();
		try{
			con =DBManager.getCon();
			StringBuffer sql=new StringBuffer("select staffcode,staffname,remark from medical_out_put_list where `sfyx`='Y'");
			  if(!(Util.objIsNULL(startdate))){
				  sql.append("and date_format(createdate,'%Y-%m-%d') >= date_format(?,'%Y-%m-%d')");
			  }
			  if(!(Util.objIsNULL(enddate))){
					sql.append("and date_format(operationdate,'%Y-%m-%d') <= date_format(?,'%Y-%m-%d')");
				}
			  if(!(Util.objIsNULL(staffcode))){
				  sql.append("and staffcode like '%"+staffcode+"%'");
			  }
			  sql.append("order by createdate asc");
		    ps=con.prepareStatement(sql.toString());
		    rs=ps.executeQuery();
			while(rs.next()){
				MedicalOutPutList medicalOutPutList=new MedicalOutPutList();
				medicalOutPutList.setStaffcode(rs.getString("staffcode"));
				medicalOutPutList.setStaffname(rs.getString("staffname"));
				medicalOutPutList.setRemark(rs.getString("remark"));
				list.add(medicalOutPutList);
			}
			rs.close();
		}catch (Exception e) {
			logger.error("export MedicalOutPutList date error (MedicalOptOutDaoImpl)"+e.toString());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return list;
		
	}
	public String uploadMedicalOutPutList(List<List<Object>> list,String user) throws Exception{
		
		int num = 0;
		int line=0;
		String result="";
		try{
			if(Util.objIsNULL(user)){
				throw new RuntimeException("Identity information is missing");
			}
			openTransaction();
			String sql = "";
			for(int i=1;i<list.size();i++){
				List<Object> list2=list.get(i);
				if(!Util.objIsNULL(list2.get(0)+"")){
					line=i;
					sql="insert into medical_out_put_list(staffcode,staffname,createdate,creater,remark,sfyx)" +
							"values('"+list2.get(0)+"','"+list2.get(1)+"','"+DateUtils.getNowDateTime()+"', '"+user+"','"+list2.get(2)+"','Y')";
					num+=update(sql, null);
					if(num<(i+0)){
						throw new RuntimeException();
					}				
				}	
			}
			sumbitTransaction();//提交事物
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("addnum", num);//新增条数
			result=Util.getMsgJosnSuccessReturn(map);		
		}catch (Exception e) {
			result=Util.getMsgJosnObject("exception", "上传失败，第"+line+"行出错:"+e);
			e.printStackTrace();
			try {
				super.rollbackTransaction();//回滚事务
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
				super.closeConnection();
		}
		return result;
		
	}	

	
}
