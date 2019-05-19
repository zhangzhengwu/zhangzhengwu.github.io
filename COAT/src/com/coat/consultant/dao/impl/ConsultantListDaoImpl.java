package com.coat.consultant.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager_sqlservler;
import util.DateUtils;
import util.Util;

import com.coat.consultant.dao.ConsultantListDao;
import com.coat.consultant.entity.ConsultantList;

import dao.common.BaseDao;

public class ConsultantListDaoImpl extends BaseDao implements ConsultantListDao {

	@SuppressWarnings("unchecked")
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, ConsultantList.class);
	}
	Logger logger =Logger.getLogger(ConsultantListDaoImpl.class);
	
	public ConsultantListDaoImpl() {
	}

	/**
	 * 定时更新Consultant List-7点
	 * @return
	 */
	public String timeTaskBatchSaveConsultant(){
		String result="";
		Util.printLogger(logger, "开始执行指定任务-Consultant_list");
		try{
			ConsultantListDao consultantDao=new ConsultantListDaoImpl();
			List<ConsultantList> list=consultantDao.findConsultantByHKView();
			batchSaveConsultant(list, "Uploader");
			result="success";
			Util.printLogger(logger, "指定任务-Consultant_list-->同步成功!");
		} catch(Exception e){
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger, "指定任务-Consultant_list-->同步失败!"+e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 批量保存上传的Consultant数据
	 * @author kingxu
	 * @date 2015-12-24
	 * @param os
	 * @param username
	 * @return
	 * @return int
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public int batchSaveConsultant(List<ConsultantList> list, String username) throws Exception {
		int num=0;
		try{
			String freequtoGrade=Util.getProValue("namecard.consultant.freepcs.grade");
			super.openTransaction();
			update2("delete from cons_list;");//删除已有ConsultantList
			//删除SA，PA，CA 本年度quato记录
			//update2("delete from nq_additional where  year(now())=year(add_date) and( remarks='SA' or remarks='PA' or remarks='CA' or remarks='AC' )");
			update2("delete from nq_additional where  year(now())=year(add_date) and remarks in('"+freequtoGrade.replace(",", "','")+"')");

			List<String> staffList=new ArrayList<String>();
			String sql="insert into cons_list(EmployeeId,Alias,C_Name,joinDate,DirectLine,Email,EmployeeName,ExternalPosition,Grade,HKID,GroupDateJoin,Location,Mobile,Position,C_PositionName,E_PositionName,RecruiterId,RecruiterName,TelephoneNo,ADTreeHead,DDTreeHead) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			List<Object[]> param=new ArrayList<Object[]>();
			String batchSql="";
			Map<String,String> newComer=new HashMap<String,String>();
			for(int i= 0;i<list.size();i++){
				ConsultantList cl=list.get(i);	
				try{
					if(!staffList.contains(cl.getEmployeeId())){
						Object[] obj=new Object[21];
						obj[0]=cl.getEmployeeId();
						obj[1]= cl.getAlias();
						//ps.setString(3, cl.getC_Name());
						if(cl.getEmployeeId().equals("JS0111")){//蕭𣻻彤  无法识别
							obj[2]=(new String(cl.getC_Name().getBytes(),"GBK"));
						}else{
							obj[2]=cl.getC_Name();
						}
						obj[3]=cl.getJoinDate();
						obj[4]= cl.getDirectLine();
						obj[5]= cl.getEmail();
						obj[6]= cl.getEmployeeName();
						obj[7]= cl.getExternalPosition();
						obj[8]= cl.getGrade();
						obj[9]= cl.getHKID();
						obj[10]= cl.getGroupDateJoin();
						obj[11]= cl.getLocation();
						obj[12]= cl.getMobile();
						obj[13]= cl.getPosition();
						obj[14]= cl.getC_PositionName();
						obj[15]= cl.getE_PositionName();
						obj[16]= cl.getRecruiterId();
						obj[17]= cl.getRecruiterName();
						obj[18]= cl.getMobile();
						obj[19]= cl.getADTreeHead();
						obj[20]= cl.getDDTreeHead();
						param.add(obj);
						//if(Constant.PA_TYPE.equalsIgnoreCase(cl.getGrade())||Constant.SA_TYPE.equalsIgnoreCase(cl.getGrade())||Constant.CA_TYPE.equalsIgnoreCase(cl.getGrade())){
						if(ArrayUtils.contains(freequtoGrade.split(","),cl.getGrade())){
							batchSql+="insert nq_additional(initials,name,additional,remarks,add_user,add_date,upd_user,upd_date,sfyx)" +
									"values('"+cl.getEmployeeId()+"','"+cl.getEmployeeName()+"',-300,'"+cl.getGrade().toUpperCase()+"','"+username+"','"+DateUtils.getDateToday()+"','','','Y');";
						}else{
							if(!Util.objIsNULL(cl.getJoinDate()) && cl.getJoinDate().substring(0, 4).equals(DateUtils.getYear()+"")){	//判断入职年份和计算年份是否相等
								newComer.put(cl.getEmployeeId(), cl.getEmployeeName());
							}
						}
						staffList.add(cl.getEmployeeId());
					}
				}catch(Exception e){
					throw new Exception(e);
				}
			}
			//批量Consultant List
			batchInsert(sql, param);
			//批量保存PA/CA/SA quota
			batchUpdate(batchSql.split(";"));
			//获取已存在的staffcode
			List<String> newStaff2= findStringDate("select initials from nq_additional where sfyx='Y' and remarks='New Comer' and additional=100   and initials in("+Arrays.asList(newComer.keySet()).toString().replace("[[", "'").replace("]]", "'").replaceAll(",", "','").replaceAll(" ", "")+")");
			sql="insert nq_additional(initials,name,additional,remarks,add_user,add_date,upd_user,upd_date,sfyx) " +
					"values(?,?,100,'New Comer','"+username+"',now(),'','','Y')";
			List<Object[]> params=new ArrayList<Object[]>();
				Set<String> keylist=newComer.keySet();
				for(String key:keylist){
					if(!newStaff2.contains(key)){
						params.add(new Object[]{key,newComer.get(key)});
					}
				}
				//批量保存new comer保存
			batchInsert(sql, params);
			num=staffList.size();
			super.sumbitTransaction();
			Util.printLogger(logger, "同步Consultant List 成功 【"+num+" 条】");
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
	 * 从HK SZO_ADM view获取最新在职顾问列表
	 * @author kingxu
	 * @date 2015-12-24
	 * @return
	 * @return List<Consultant_List>
	 * @throws Exception 
	 */
	public List<ConsultantList> findConsultantByHKView() throws Exception {

		List<ConsultantList> clss=new ArrayList<ConsultantList>();
		SimpleDateFormat sdf	=new SimpleDateFormat("yyyy-MM-dd");
		String Position="";
		String location="";
		Connection  cons=null;
		try{
			cons=DBManager_sqlservler.getCon();
			String sqls="select * from  vSZOADM  ";
			PreparedStatement pss=cons.prepareStatement(sqls);
			ResultSet rs=pss.executeQuery();
			while(rs.next()){
				ConsultantList cl=new ConsultantList();
				cl.setEmployeeId(rs.getString("EmployeeID"));
				cl.setAlias(rs.getString("Alias"));
				cl.setC_Name(rs.getString("ChineseName"));
				cl.setJoinDate(sdf.format(sdf.parse(rs.getString("DateJoin"))));
				cl.setDirectLine(rs.getString("DirectLine"));
				cl.setEmail(rs.getString("Email"));
				cl.setEmployeeName(rs.getString("EmployeeName"));
				cl.setExternalPosition(rs.getString("ExternalPositionInEnglish"));
				cl.setGrade(rs.getString("GradeID"));
				cl.setHKID(rs.getString("HKID"));
				cl.setGroupDateJoin(sdf.format(sdf.parse(rs.getString("GroupDateJoin"))));
				cl.setPosition(rs.getString("PositionID"));
				location=rs.getString("Location");
				location=Util.objIsNULL(location)?"":(location.substring(location.indexOf("F ")+1,location.length()).trim().replace("Cityplaza 3", "CP3"));
				cl.setLocation(location);
				cl.setMobile(rs.getString("Mobile"));
				Position=rs.getString("PositionNameInEnglish");
				Position=Position.substring(0, Position.indexOf("-")>-1?Position.indexOf("-"):Position.length()).replaceAll("\\d","").trim();
				/**
				 * Position过滤 
				 * @version 2014-5-13 14:04:22 King
				 */
				//cl.setE_PositionName(rs.getString("PositionNameInEnglish"));
				cl.setE_PositionName(Position.indexOf("Consultant Trainee")>-1?"Consultant":Position);

				cl.setC_PositionName("");
				cl.setRecruiterId(rs.getString("RecuiterID"));
				cl.setRecruiterName(rs.getString("RecuiterName"));
				cl.setADTreeHead(rs.getString("ADTreeHead"));
				cl.setDDTreeHead(rs.getString("DDTreeHead"));
				clss.add(cl);
			}
			rs.close();
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			DBManager_sqlservler.closeCon(cons);
			super.closeConnection();
		}
		return clss;

	}



	public ConsultantList findConsultantByCode(String staffcode) throws SQLException {
		ConsultantList cl=null;
		try{
			cl=find("select * from cons_list where employeeid=?", new String[]{staffcode});
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.closeConnection();
		}
		return cl;
	}







}
