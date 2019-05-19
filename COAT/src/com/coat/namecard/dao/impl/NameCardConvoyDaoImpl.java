package com.coat.namecard.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.DBManager;
import util.Pager;
import util.Util;

import com.coat.namecard.dao.NameCardConvoyDao;
import com.coat.namecard.entity.NameCardConvoyDetial;

import dao.common.BaseDao;
import entity.NameCardConvoy;
import entity.RequestStaffConvoyDetial;

public class NameCardConvoyDaoImpl extends BaseDao implements NameCardConvoyDao {

	Logger logger=Logger.getLogger(this.getClass());
	@SuppressWarnings("unchecked")
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, NameCardConvoy.class);
	}
	
	
	
	
	
	/**
	 * 查询顾问自己名片办理记录
	 * @author kingxu
	 * @date 2015-10-10
	 * @param fields
	 * @param page
	 * @param ET
	 * @param nocode
	 * @param objects
	 * @return
	 * @throws Exception
	 * @return Pager
	 */
	public Pager findPager(String[] fields, Pager page,Object... objects) throws Exception{
		/*String sql	="from  (SELECT  staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,"+
				" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer,"+
				" CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,'' CIB_only,ae_consultant,eliteTeam,location,shzt,remark,urgentDate as add_date,remarkcons,company "+
		" FROM request_new_convoy c where quantity>0 and card_type='N'  and shzt!='Y' "+
        " UNION "+
        " select staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,"+
				" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer,"+
				" CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,'' CIB_only,ae_consultant,eliteTeam,location,'Y' as shzt,'',urgentDate as add_date,'',company  from request_new where quantity>0 and card_type='N'"+
                " ) c where  payer like ?" +
                " and  date_format(add_date,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d')" +
				" and  date_format(add_date,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" +
				" and location like ?" +
				" and urgent like ?"+
				" and shzt like ?"+
				" and name like ?";*/
		String sql	="from  (SELECT refno, staff_code,name,name_chinese,title_english,title_chinese,external_english,external_chinese,academic_title_e,"+
				" academic_title_c,profess_title_e,profess_title_c,tr_reg_no,ce_no,mpf_no,e_mail,direct_line,fax,bobile_number,quantity,urgentDate,layout_type,urgent,Payer,"+
				" CAM_only,CFS_only,CIS_only,CCL_only,CFSH_only,CMS_only,CFG_only,Blank_only,'' CIB_only,ae_consultant,eliteTeam,location,shzt,remark,urgentDate as add_date,remarkcons,company,remark1 "+
				" FROM request_new_convoy c where quantity>0 and card_type='N'"+
				" ) c where  payer like ?" +
				" and  date_format(add_date,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d')" +
				" and  date_format(add_date,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" +
				" and location like ?" +
				" and urgent like ?"+
				" and shzt like ?"+
				" and name like ?";
		String limit="order by UrgentDate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			super.closeConnection();
		}
		return pager;
	}

	
	public Pager queryStateDetail(String[] fields, Pager page,Object... objects) throws Exception{
		String sql=" FROM request_new_convoy_detial  where  staffrefno like ? and staffcode like ? ";
		String limit="order by createdate asc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}
	
	
	public String uploadNameCard(List<List<Object>> list,String user) throws Exception{
		int num=0;
		int num2=0;
		int num3=0;
		int line=0;
		boolean s=true;
		String xx="";
		String staffCode="";
		String staffNameE="";
		String staffNameC="";
		List<String> state=new ArrayList<String>();
		try{
			if(Util.objIsNULL(user)){
				throw new RuntimeException("Identity information is missing");
			}
			openTransaction();
			String sql="";
		for(int i=1;i<list.size();i++){
				List<Object> list2=list.get(i);
			if(!Util.objIsNULL(list2.get(0)+"")){
				line=i;
				//-->状态为Processed_Vender\Delivery\Receive才允许上传
				if(list2.get(1).toString().equalsIgnoreCase("Processed_Vender")||list2.get(1).toString().equalsIgnoreCase("Delivery")
						||list2.get(1).toString().equalsIgnoreCase("Receive")){
					state=findAllByCode(list2.get(0).toString());//-->判断已存在的code中，状态是否相同,相同的不保存
					for (int j = 0; j < state.size(); j++) {
						if(state.get(j).toString().equalsIgnoreCase(list2.get(1).toString())){
							s=false;
						}else{
							s=true;
						}
					}
					if(s){
						String sql3="select staff_code,name,name_chinese from request_new_convoy where refno='"+list2.get(0)+"' ";
						Map<String,Object> map=findMap(sql3);
						if(map.size()>0){
							staffCode=(String)map.get("staff_code");
							staffNameE=(String)map.get("name");
							staffNameC=(String)map.get("name_chinese");
						}
						sql="insert into request_new_convoy_detial(username,staffrefno,staffcode,staffnameE,staffnameC,state,creator,createdate,remark)" +
								"values('"+user+"','"+list2.get(0)+"','"+staffCode+"','"+staffNameE+"','"+staffNameC+"','"+list2.get(1)+"','"+user+"','"+list2.get(2)+"','')";
						num+=update2(sql);
						if(num<(i+0)){
							throw new RuntimeException();
						}
					}else{
						num3+=1;
					}
					
				}else{
					num2+=1;
				}
			}	
		}
			sumbitTransaction();
			xx=Util.getMsgJosnObject("success", "成功条数:"+num+"条,其中有"+num3+"条已存在,"+num2+"条状态格式不正确未上传");
		}catch (Exception e) {
			xx=Util.getMsgJosnObject("exception", "上传失败，第"+line+"行出错:"+e);
			e.printStackTrace();
			try {
				super.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			super.closeConnection();
		}
		return xx;
	}
	

	//判断itemcode是否存在
	public List<String> findAllByCode(String itemcode) {
		List<String> state=new ArrayList<String>();
		Connection con=null;
		PreparedStatement ps=null;
		try{
			con=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select state from request_new_convoy_detial where staffrefno ='"+itemcode+"'");
			logger.info("Query request_new_convoy_detial SQL:"+stringBuffer.toString());
			ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				state.add(rs.getString(1));
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("request_new_convoy_detial 中根据条件查询数据个数时出现异常："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return state;
	}

	
	public List<NameCardConvoyDetial> findNameCardDetail(String staffrefno)throws SQLException {
		List<Map<String,Object>> map=new ArrayList<Map<String,Object>>();
		List<NameCardConvoyDetial> detial=new ArrayList<NameCardConvoyDetial>();
		try{
			String sql="select *from request_new_convoy_detial where staffrefno=? ORDER BY createdate";
			map=findListMap(sql,new Object[]{staffrefno});
			for (int i = 0; i < map.size(); i++) {
				detial.add(new NameCardConvoyDetial(
						(String)map.get(i).get("username"),
						(Integer)map.get(i).get("staffrefno"),
						(String)map.get(i).get("staffcode"),
						(String)map.get(i).get("staffnameE"),
						(String)map.get(i).get("staffnameC"),
						(String)map.get(i).get("state"),
						(String)map.get(i).get("creator"),
						(String)map.get(i).get("createdate"),
						(String)map.get(i).get("remark")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.closeConnection();
		}
		return detial;
	}
}
