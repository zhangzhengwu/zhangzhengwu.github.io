package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Page;
import util.Util;
import dao.MedicalDao;
import entity.Medical;
import entity.Medical_Consultant;
/**
 * 
 * @author king.xu
 *
 */
public class MedicalDaoImpl implements MedicalDao {
		Logger log=Logger.getLogger(MedicalDaoImpl.class);
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="";
		int num=-1;	
		public int addMedical(Medical medical) {
				try{
					sql="insert medical_Claim_record values('"+medical.getStaffcode()+"','"+
					medical.getName()+"','"+medical.getAD_type()+"','"+medical.getSP_type()+"','"+
					medical.getMedical_date()+"','"+medical.getMedical_Fee()+"','"+
					medical.getEntitled_Fee()+"',"+Integer.parseInt(medical.getTerms_year())+",'"+
					medical.getMedical_month()+"','"+medical.getMedical_Normal()+"','"+
					medical.getMedical_Special()+"','"+medical.getStaff_CodeDate()+"','"+
					medical.getSameDaye()+"','"+medical.getHalf_Consultant()+"','"+
					medical.getUpd_Name()+"','"+medical.getUpd_Date()+"','"+medical.getSfyx()+"')";
					con=DBManager.getCon();
					log.info("在MedicalDaoImpl中添加Medical   sql:==="+sql);
					ps=con.prepareStatement(sql);
					num=ps.executeUpdate();
					
				}catch(Exception e){
					log.error("在MedicalDaoImpl中添加Medical时出现"+e.toString());
				}finally{
					DBManager.closeCon(con);
				}
			return num;
		}
		public void updateMedicalState_Y(String staffcode,String add_Date){
			
			try{
				sql="update medical_Claim_convoy set shzt='Y' where staffcode='"+staffcode+"' and add_date='"+add_Date+"'";
				log.info("在MedicalDaoImpl中審核通過Medical_Consultant   sql:==="+sql);
				con=DBManager.getCon();
				ps=con.prepareStatement(sql);
				 ps.executeUpdate();
			}catch(Exception e){
				log.error("在MedicalDaoImpl中審核通過Medical_Consultant时出现"+e.toString());
			}finally{
				DBManager.closeCon(con);
			}
			
		}
		
		public void updateMedicalState_J(String staffcode,String add_Date,String remark){
			
			try{
				sql="update medical_Claim_convoy set shzt='',remark='"+remark+"' where staffcode='"+staffcode+"' and add_date='"+add_Date+"'";
				con=DBManager.getCon();
				ps=con.prepareStatement(sql);
				ps.executeUpdate();
			}catch(Exception e){
				log.error("在MedicalDaoImpl中審核拒絕Medical_Consultant时出现"+e.toString());
			}finally{
				DBManager.closeCon(con);
			}
			
		}
		
		public int addMedical_Consultant(Medical_Consultant medical) {
			try{
				sql="insert medical_Claim_convoy values('"+medical.getStaffcode()+"','"+
				medical.getName()+"','"+medical.getAD_type()+"','"+medical.getSP_type()+"','"+
				medical.getMedical_date()+"','"+medical.getMedical_Fee()+"','"+
				medical.getEntitled_Fee()+"',0,'"+
				medical.getMedical_month()+"','','','"+medical.getStaff_CodeDate()+"','"+
				medical.getSameDaye()+"','"+medical.getHalf_Consultant()+"','"+
				medical.getUpd_Name()+"','"+medical.getUpd_Date()+"','"+medical.getSfyx()+"','"+medical.getShzt()+"','"+medical.getAdd_date()+"','"+medical.getAdd_name()+"','"+medical.getRemark()+"','"+medical.getFile()+"')";
				con=DBManager.getCon();
				ps=con.prepareStatement(sql);
				num=ps.executeUpdate();
				
			}catch(Exception e){
				log.error("在MedicalDaoImpl中添加Medical_Consultant时出现"+e.toString());
			}finally{
				DBManager.closeCon(con);
			}
			return num;
		}
		/**
		 * 根据staffcode查询Medical
		 * @param staffNo
		 * @return
		 */
/*		public List<Medical> findByStaffCode(String StaffNo){
			List<Medical> list=new ArrayList<Medical>();
			
			try{
				sql="select a.staffcode as staffcode,staffname,resignDate,half_cons,cons,S_cons1,S_cons2,Principal_cons,wealth_Manager,S_Wealth_Manager1,S_Wealth_Manager2,"
					+" P_Wealth_Manager,Associate_AAD,Ass_Director_AD,vp,svp,if(Ass_Director_AD,'D','') AS AD_TYPE,SP_type," 
					+"staff_CodeDate,SameDay,Half_Consultant,upd_Name,upd_Date,sfyx"
					+ " from promotion_c_report a left join (select * from medical_claim_record t where   sfyx='Y' )b"
					 +" on a.STAFFCODE = b.STAFFCODE where  a.STAFFCODE='"+StaffNo+"'  order by Terms_year desc limit 0,1";
						con=DBManager.getCon();
						log.info("根据staffcode查询Medical  sql：==="+sql);
						ps=con.prepareStatement(sql);
						rs=ps.executeQuery();
						 if(rs.next()){
							 Medical mc=new Medical();
							 mc.setStaffcode(rs.getString("staffcode"));//staffCode
							 mc.setName(rs.getString("staffname"));//staffName
							
							// mc.setSP_type(rs.getString("SP_type"));
							// mc.setTerms_year(Util.objIsNULL(rs.getString("Terms_year"))?"0":rs.getString("Terms_year"));//获取当前年份中办理数量是否大于0 是：获取该值，否则默认为0
						//	 mc.setMedical_Normal(Util.objIsNULL(rs.getString("medical_Normal"))?"0":rs.getString("medical_Normal"));//获取当前年份中办理普科数量是否大于0 是：获取该值，否则默认为0
						//	 mc.setMedical_Special(Util.objIsNULL(rs.getString("medical_Special"))?"0":rs.getString("medical_Special"));//获取当前年份中办理专科数量是否大于0 是：获取该值，否则默认为0
						 	 mc.setStaff_CodeDate(rs.getString("staff_CodeDate"));//现暂已遗弃该数据
							//mc.setAD_type(rs.getString("AD_TYPE"));//获取该用户是否是AD
						 	 
								//获取该用户是否是AD
							 if(!Util.objIsNULL(rs.getString("vp"))||!Util.objIsNULL(rs.getString("svp"))||!Util.objIsNULL(rs.getString("AD_TYPE")))
								 mc.setAD_type("D");//获取该用户是否是AD
							 else
								 mc.setAD_type("");
						//	mc.setResignDate(rs.getString("resignDate"));//获取该用户的离职时间
								 if(!Util.objIsNULL(rs.getString("Ass_Director_AD")) && !rs.getString("Ass_Director_AD").toUpperCase().equals("N/A")){//有AD date时
									 if(Util.objIsNULL(rs.getString("half_cons")) || rs.getString("half_cons").toUpperCase().equals("N/A")){//AD有dateHalf_Consultant没有Date时
										 mc.setHalf_Consultant("Completed");
										}else{//AD 有Date并且Half_Consultant有Date时
											mc.setHalf_Consultant(rs.getString("half_cons"));
										}
								}else{//没有AD Date 时
									String dates=rs.getString("half_cons")+rs.getString("cons")+rs.getString("S_cons1")+
									rs.getString("S_cons2")+rs.getString("Principal_cons")+rs.getString("wealth_Manager")+
									rs.getString("S_Wealth_Manager1")+rs.getString("S_Wealth_Manager2")+rs.getString("P_Wealth_Manager")+
									rs.getString("Associate_AAD").toUpperCase().trim();
										if(Util.objIsNULL(dates)){//没有过试用期（没有其他职位的入职时间证明没有通过试用期）
											mc.setHalf_Consultant("");
										}else{
											if(Util.objIsNULL(rs.getString("half_cons"))){//获取职位half_cons为空，默认为Completed
												mc.setHalf_Consultant("Completed");
											}else{//获取职位half_cons不为空， 获取该数据
												mc.setHalf_Consultant(rs.getString("half_cons"));
											}
											
										}
								}
							 list.add(mc);
						 }
						
			}catch(Exception e){
				e.printStackTrace();
				log.error("在MedicalDaoImplementation中根据staffcode查询数据时出现"+e.toString());
			}finally{
				DBManager.closeCon(con);
			}
			return list;
		}*/
		/**
		 * 根据staffcode查询Medical
		 * @param staffNo
		 * @return
		 */
		public List<Medical> findByStaffCode_New(String StaffNo){
			List<Medical> list=new ArrayList<Medical>();

			try{
				sql="select a.staffcode as staffcode,staffname,resignDate,halfCons,grade,internalPositionId,externalPositionId,reason,SP_type," 
						+"staff_CodeDate,SameDay,Half_Consultant,upd_Name,upd_Date,sfyx"
						+ " from promotion_c_data a left join (select * from medical_claim_record t where   sfyx='Y' )b"
						+" on a.staffcode = b.STAFFCODE where  a.staffcode='"+StaffNo+"'  order by Terms_year desc limit 0,1";
				con=DBManager.getCon();
				log.info("根据staffcode查询Medical  sql：==="+sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next()){
					Medical mc=new Medical();
					mc.setStaffcode(rs.getString("staffcode"));//staffCode
					mc.setName(rs.getString("staffname"));//staffName
					mc.setStaff_CodeDate(rs.getString("staff_CodeDate"));//现暂已遗弃该数据
					//获取该用户是否是AD
					if("ASD".equalsIgnoreCase(rs.getString("internalPositionId"))||"AD".equalsIgnoreCase(rs.getString("grade"))||"DD".equalsIgnoreCase(rs.getString("grade"))||"vp".equalsIgnoreCase(rs.getString("grade"))||"svp".equalsIgnoreCase(rs.getString("grade")))
						mc.setAD_type("D");//获取该用户是否是AD
					else
						mc.setAD_type("");
					
					if("ASD".equalsIgnoreCase(rs.getString("internalPositionId"))||"AD".equalsIgnoreCase(rs.getString("grade"))||"DD".equalsIgnoreCase(rs.getString("grade"))||"vp".equalsIgnoreCase(rs.getString("grade"))||"svp".equalsIgnoreCase(rs.getString("grade"))){//有AD date时
						if(StaffcodeIfPromotion(rs.getString("staffcode"))){//AD未转正
							mc.setHalf_Consultant("Completed");
						}else{//AD 有Date并且Half_Consultant有Date时  StaffcodeIfPromotion
							mc.setHalf_Consultant(getStaffcodePromotionDate(rs.getString("staffcode")));
						}
					}else{//没有AD Date 时
						if(StaffcodeIfPromotion(rs.getString("staffcode"))){
							mc.setHalf_Consultant("");
						}else{//获取职位half_cons不为空， 获取该数据
							mc.setHalf_Consultant(getStaffcodePromotionDate(rs.getString("staffcode")));
						}
					}
					list.add(mc);
				}
				
			}catch(Exception e){
				e.printStackTrace();
				log.error("在MedicalDaoImplementation中根据staffcode查询数据时出现"+e.toString());
			}finally{
				DBManager.closeCon(con);
			}
			return list;
		}
		
		/**
		 * 根据staffcode和MedicalDate查询
		 */
/*		public List<Medical> queryByStaffNo(String StaffNo,String MedicalDate) {
			List<Medical> list=new ArrayList<Medical>();
			//获取当年办理数量 将medical_claim_record 的medical-date 改为 upd-date  ；upddate修改时不能变动，保持原始
			//2013-01-14 新增参数MedicalDate，为了调整Medical报销模块在13年能录入12的数据的情况下，并把条件year(upd_Date) = year(now()) 改成year(medical_Date) = year(MedicalDate)
			try{
		 	
			
			sql="select a.staffcode as staffcode,staffname,resignDate,half_cons,cons,S_cons1,S_cons2,Principal_cons,wealth_Manager,S_Wealth_Manager1,S_Wealth_Manager2,"
				+" P_Wealth_Manager,Associate_AAD,Ass_Director_AD,vp,svp,if(Ass_Director_AD,'D','') AS AD_TYPE,SP_type,medical_date,medical_Fee,entitled_Fee," 
				+" Terms_year,medical_month,medical_Normal,medical_Special,staff_CodeDate,SameDay,Half_Consultant,upd_Name,upd_Date,sfyx"
			+ " from promotion_c_report a left join (select * from medical_claim_record t where  year(medical_Date) = year('"+MedicalDate+"') and sfyx='Y' )b"
			 // + " from promotion_c_report a left join (select * from medical_claim_record t where  year(upd_Date) = year(now()) and sfyx='Y' )b"
			 +" on a.STAFFCODE = b.STAFFCODE where  a.STAFFCODE='"+StaffNo+"'  order by Terms_year desc limit 0,1";
				con=DBManager.getCon();
				log.info("根据Staffcode查询MedicalDate  sql：==="+sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				 if(rs.next()){
					 Medical mc=new Medical();
					 mc.setStaffcode(rs.getString("staffcode"));//staffCode
					 mc.setName(rs.getString("staffname"));//staffName
					// mc.setSP_type(rs.getString("SP_type"));
					 mc.setTerms_year(Util.objIsNULL(rs.getString("Terms_year"))?"0":rs.getString("Terms_year"));//获取当前年份中办理数量是否大于0 是：获取该值，否则默认为0
					 mc.setMedical_Normal(Util.objIsNULL(rs.getString("medical_Normal"))?"0":rs.getString("medical_Normal"));//获取当前年份中办理普科数量是否大于0 是：获取该值，否则默认为0
					 mc.setMedical_Special(Util.objIsNULL(rs.getString("medical_Special"))?"0":rs.getString("medical_Special"));//获取当前年份中办理专科数量是否大于0 是：获取该值，否则默认为0
					 mc.setStaff_CodeDate(rs.getString("staff_CodeDate"));//现暂已遗弃该数据
					//获取该用户是否是AD
					 if(!Util.objIsNULL(rs.getString("vp"))||!Util.objIsNULL(rs.getString("svp"))||!Util.objIsNULL(rs.getString("AD_TYPE")))
						 mc.setAD_type("D");//获取该用户是否是AD
					 else
						 mc.setAD_type("");
					 
					 mc.setResignDate(rs.getString("resignDate"));//获取该用户的离职时间
					 mc.setMedical_month(DateUtils.getMedicalMonth()+"");
						 if((!Util.objIsNULL(rs.getString("Ass_Director_AD")) && !rs.getString("Ass_Director_AD").toUpperCase().equals("N/A"))){//有AD date时
							 if(Util.objIsNULL(rs.getString("half_cons")) || rs.getString("half_cons").toUpperCase().equals("N/A")){//AD有dateHalf_Consultant没有Date时
								 mc.setHalf_Consultant("Completed");
								}else{//AD 有Date并且Half_Consultant有Date时
									mc.setHalf_Consultant(rs.getString("half_cons"));
								}
						}else{//没有AD Date 时
							String dates=rs.getString("half_cons")+rs.getString("cons")+rs.getString("S_cons1")+
							rs.getString("S_cons2")+rs.getString("Principal_cons")+rs.getString("wealth_Manager")+
							rs.getString("S_Wealth_Manager1")+rs.getString("S_Wealth_Manager2")+rs.getString("P_Wealth_Manager")+
							rs.getString("Associate_AAD").toUpperCase().trim();
								if(Util.objIsNULL(dates)){//没有过试用期（没有其他职位的入职时间证明没有通过试用期）
									mc.setHalf_Consultant("");
								}else{
									if(Util.objIsNULL(rs.getString("half_cons"))){//获取职位half_cons为空，默认为Completed
										mc.setHalf_Consultant("Completed");
									}else{//获取职位half_cons不为空， 获取该数据
										mc.setHalf_Consultant(rs.getString("half_cons"));
									}
									
								}
						}
					 list.add(mc);
				 }
				
			}catch(Exception e){
				e.printStackTrace();
				log.error("在MedicalDaoImplementation中根据staffcode，MedicalDate查询数据时出现"+e.toString());
			}finally{
				DBManager.closeCon(con);
			}
				return list;
	}*/
		/**
		 * 根据staffcode和MedicalDate查询
		 */
		public List<Medical> queryByStaffNo_New(String StaffNo,String MedicalDate) {
			List<Medical> list=new ArrayList<Medical>();
			try{
				sql="select a.staffcode as staffcode,staffname,resignDate,halfCons,grade,internalPositionId,externalPositionId,reason,SP_type,medical_date,medical_Fee,entitled_Fee," 
						+" Terms_year,medical_month,medical_Normal,medical_Special,staff_CodeDate,SameDay,Half_Consultant,upd_Name,upd_Date,sfyx"
						+ " from promotion_c_data a left join (select * from medical_claim_record t where  year(medical_Date) = year('"+MedicalDate+"') and sfyx='Y' )b"
						+" on a.staffcode = b.STAFFCODE where  a.staffcode='"+StaffNo+"'  order by Terms_year desc limit 0,1";
				con=DBManager.getCon();
				log.info("根据Staffcode查询MedicalDate  sql：==="+sql);
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				if(rs.next()){
					Medical mc=new Medical();
					mc.setStaffcode(rs.getString("staffcode"));//staffCode
					mc.setName(rs.getString("staffname"));//staffName
					// mc.setSP_type(rs.getString("SP_type"));
					mc.setTerms_year(Util.objIsNULL(rs.getString("Terms_year"))?"0":rs.getString("Terms_year"));//获取当前年份中办理数量是否大于0 是：获取该值，否则默认为0
					mc.setMedical_Normal(Util.objIsNULL(rs.getString("medical_Normal"))?"0":rs.getString("medical_Normal"));//获取当前年份中办理普科数量是否大于0 是：获取该值，否则默认为0
					mc.setMedical_Special(Util.objIsNULL(rs.getString("medical_Special"))?"0":rs.getString("medical_Special"));//获取当前年份中办理专科数量是否大于0 是：获取该值，否则默认为0
					mc.setStaff_CodeDate(rs.getString("staff_CodeDate"));//现暂已遗弃该数据
					//获取该用户是否是AD
					if("ASD".equalsIgnoreCase(rs.getString("internalPositionId"))||"AD".equalsIgnoreCase(rs.getString("grade"))||"DD".equalsIgnoreCase(rs.getString("grade"))||"vp".equalsIgnoreCase(rs.getString("grade"))||"svp".equalsIgnoreCase(rs.getString("grade")))
						mc.setAD_type("D");//获取该用户是否是AD
					else
						mc.setAD_type("");
					
					mc.setResignDate(rs.getString("resignDate"));//获取该用户的离职时间
					mc.setMedical_month(DateUtils.getMedicalMonth()+"");
					if("ASD".equalsIgnoreCase(rs.getString("internalPositionId"))||"AD".equalsIgnoreCase(rs.getString("grade"))||"DD".equalsIgnoreCase(rs.getString("grade"))||"vp".equalsIgnoreCase(rs.getString("grade"))||"svp".equalsIgnoreCase(rs.getString("grade"))){//有AD date时
						if(StaffcodeIfPromotion(rs.getString("staffcode"))){
							mc.setHalf_Consultant("Completed");
						}else{//AD 有Date并且Half_Consultant有Date时  
							mc.setHalf_Consultant(getStaffcodePromotionDate(rs.getString("staffcode")));
						}
					}else{//没有AD Date 时
						if(StaffcodeIfPromotion(rs.getString("staffcode"))){
							mc.setHalf_Consultant("");
						}else{//获取职位half_cons不为空， 获取该数据
							mc.setHalf_Consultant(getStaffcodePromotionDate(rs.getString("staffcode")));
						}
					}
					list.add(mc);
				}
				
			}catch(Exception e){
				e.printStackTrace();
				log.error("在MedicalDaoImplementation中根据staffcode，MedicalDate查询数据时出现"+e.toString());
			}finally{
				DBManager.closeCon(con);
			}
			return list;
		}
	public int motifyMedical(String Special,String updName,String StaffNo, String MedicalFee, String MedicalDate,String Amount,
			String updDate) {
		int num=-1;
		try{
			sql="update medical_claim_record set SP_type='"+Special+"',upd_Name='"+updName+"', medical_date='"+MedicalDate+"' , Medical_Fee='"+MedicalFee+"',entitled_Fee='"+Amount+"' where Staffcode='"+StaffNo+"' and upd_Date='"+updDate+"'";
			con=DBManager.getCon();
		
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
		}catch(Exception e){
			log.error("在MedicalDaoImpl中對Medical進行修改出現:"+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 查询行数
	 * @param startDate
	 * @param endDate
	 * @param code
	 * @param name
	 * @return
	 */
	public int getRow(String startDate, String endDate,
			String code, String name) {
		int num=-1;
		try{
			StringBuffer sql= new StringBuffer("SELECT count(*) FROM medical_claim_record WHERE  sfyx ='Y' ");
			if(!Util.objIsNULL(startDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}
			if(!Util.objIsNULL(endDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			if (!Util.objIsNULL(code)) {
				sql.append(" and staffcode like '%"+code+"%' ");
			}
			if (!Util.objIsNULL(name)) {
				sql.append(" AND name like '%"+name+"%'");
			}
			sql.append(" order by upd_date asc ");
			
			con = DBManager.getCon();
			log.info("导出Medical信息表 QueryMedicalDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
			num=Integer.parseInt(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("查询Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("查询Medical信息表异常！"+e);
		}finally
		{
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	public List<Medical> queryMedicalConsultant(String startDate, String endDate,
				String code, String name,Page page) {
			List<Medical> list=new ArrayList<Medical>();
			try{
				StringBuffer sql= new StringBuffer("SELECT staffcode,name,AD_type,SP_type,medical_date," +
						"medical_Fee,entitled_Fee,Terms_year,medical_month,medical_Normal,medical_Special,upd_Date FROM medical_claim_record  WHERE sfyx ='Y'");
				if(!Util.objIsNULL(startDate)){
					sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
				}
				if(!Util.objIsNULL(endDate)){
					sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
				}
				if (!Util.objIsNULL(code)) {
					sql.append(" and staffcode like '%"+code+"%' ");
				}
				if (!Util.objIsNULL(name)) {
					sql.append(" AND name like '%"+name+"%'");
				}
				sql.append("  order by upd_date asc ");
				 sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize()+" ");
				 con = DBManager.getCon();
				log.info("导出Medical信息表 QueryMedicalDaoImpl SQL:"+sql.toString());
				ps = con.prepareStatement(sql.toString());
				rs = ps.executeQuery();
				while(rs.next()){
					Medical md=new Medical();
					md.setStaffcode(rs.getString("staffcode"));
					md.setName(rs.getString("name"));
					md.setAD_type(rs.getString("AD_type"));
					md.setSP_type(rs.getString("SP_type"));
					md.setMedical_date(rs.getString("medical_date"));
					md.setMedical_Fee(rs.getString("medical_Fee"));
					md.setEntitled_Fee(rs.getString("entitled_fee"));
					md.setTerms_year(rs.getString("Terms_year"));
					md.setMedical_month(rs.getString("medical_month"));
					md.setMedical_Normal(rs.getString("medical_Normal"));
					md.setMedical_Special(rs.getString("medical_special"));
					md.setUpd_Date(rs.getString("upd_Date"));
					list.add(md);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("查询Medical信息表异常！"+e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				log.error("查询Medical信息表异常！"+e);
			}finally
			{
				//关闭连接
				DBManager.closeCon(con);
			}
			return list;
		}
	public List<Medical_Consultant> queryMedical_Consultant(String startDate, String endDate,
			String code, String name) {
		List<Medical_Consultant> list=new ArrayList<Medical_Consultant>();
		try{
			StringBuffer sql= new StringBuffer("SELECT staffcode,name,AD_type,SP_type,medical_date," +
			"medical_Fee,entitled_Fee,medical_month,shzt,remark,add_date,file FROM medical_claim_convoy where sfyx ='Y' ");
			if(!Util.objIsNULL(startDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
			}
			if( !Util.objIsNULL(endDate)){
				sql.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			if (!Util.objIsNULL(code)) {
				sql.append(" and staffcode like '%"+code+"%' ");
			}
			if (!Util.objIsNULL(name)) {
				sql.append(" AND name like '%"+name+"%'");
			}
			sql.append(" order by upd_date asc ");
			con = DBManager.getCon();
			log.info("查询Medical_Consultant信息表 QueryMedicalDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				Medical_Consultant md=new Medical_Consultant();
				md.setStaffcode(rs.getString("staffcode"));
				md.setName(rs.getString("name"));
				md.setAD_type(rs.getString("AD_type"));
				md.setSP_type(rs.getString("SP_type"));
				md.setMedical_date(rs.getString("medical_date"));
				md.setMedical_Fee(rs.getString("medical_Fee"));
				md.setEntitled_Fee(rs.getString("entitled_fee"));
				md.setMedical_month(rs.getString("medical_month"));
				md.setShzt(rs.getString("shzt"));
				md.setRemark(rs.getString("remark"));
				md.setAdd_date(rs.getString("add_date"));
				md.setFile(rs.getString("file"));
				list.add(md);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("查询Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("查询Medical信息表异常！"+e);
		}finally
		{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	}
	public int VailMedical(String StaffNo, String MedicalDate, String Special) {
		int num=-1;
		try{
			con=DBManager.getCon();
			sql="select count(*) as num from medical_claim_record where  sfyx ='Y'  and staffcode='"+StaffNo+"' and medical_date='"+MedicalDate+"' and SP_type='"+Special+"'";
			log.info("在MedicalDaoImpl中验证MedicalDate   sql：==="+sql);
			ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				num=Integer.parseInt(rs.getString(1));
			}
		}catch(Exception e){
			log.error("在MedicalDaoImpl中验证MedicalDate时出现"+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	public int update(String StaffNo, String update,String upd_name) {
		int num=-1;
		try{
			con=DBManager.getCon();
			sql="update medical_claim_record set sfyx='N',upd_Date='"+DateUtils.getDateToday()+"' where staffcode='"+StaffNo+"' and upd_Date='"+update+"'";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<0){
				log.error(upd_name+"在MedicalDaoImpl中修改失败");
			}
		}catch(Exception e){
			log.error(upd_name+"在MedicalDaoImpl中修改medical状态时出现"+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	public void upNormal(String StaffNo, String updDate, String used,String upd_name) {
try {
	con=DBManager.getCon();
	sql="update medical_claim_record set medical_Normal = medical_Normal+1 ,medical_Special = medical_Special-1 where staffcode='"+StaffNo
	+"' and sfyx='Y' and Year('"+updDate+"') = Year(now()) and terms_year > "+used;
	log.info("upNormal"+sql);
	ps=con.prepareStatement(sql);
	ps.executeUpdate();
} catch (SQLException e) {
    log.error(upd_name+"在MedicalDaoImpl中修改medical状态时出现"+e.toString());
} catch (ClassNotFoundException e) {
	 log.error(upd_name+"在MedicalDaoImpl中修改medical状态时出现"+e.toString());
}finally{
	DBManager.closeCon(con);
}
	}
	public void upSpecial(String StaffNo, String updDate, String used ,String upd_name) {
		try {
			con=DBManager.getCon();
			sql="update medical_claim_record set medical_Normal = medical_Normal-1 ,medical_Special = medical_Special+1 where staffcode='"+StaffNo
			+"' and sfyx='Y' and Year('"+updDate+"') = Year(now()) and terms_year > "+used;
			log.info("upSpecial"+sql); 
			ps=con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			 log.error(upd_name+"在MedicalDaoImpl中修改medical状态时出现"+e.toString());
		} catch (ClassNotFoundException e) {
			 log.error(upd_name+"在MedicalDaoImpl中修改medical状态时出现"+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
	}
	/**
	 *   TODO  根据staffcode获取Email
	 */
	public List<String> QueryEmailByConsultant(String staffcode) {
		List<String> Email=new ArrayList<String>();
	try{
		con=DBManager.getCon();
		sql="select email from cons_list where EmployeeId=?";
		log.info("根据staffcode获取Email    sql:==="+sql);
		ps=con.prepareStatement(sql);
		ps.setString(1, staffcode);
		ResultSet rs=null;
		rs=ps.executeQuery();
		if(rs.next()){
			Email.add(rs.getString("email"));
		}
		rs.close();
	}catch(Exception e){
		e.printStackTrace();
		log.error("根据staffcode 获取Email时出现："+e.toString());
	}finally{
		DBManager.closeCon(con);
	}
		return Email;
	}
 
	
	/**
	 * 判断顾问是否转正
	 * @param 
	 * @return
	 */
	public boolean StaffcodeIfPromotion(String staffcode) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		boolean flag = true;
		try {
			sql = "select staffcode from promoted_listforprobation where staffcode=? ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				flag = false;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.closeCon(con);
		}
		
		return flag;
	}
	
	
	/**
	 * 获取转正时间
	 * @param staffcode
	 * @return
	 */
	public String getStaffcodePromotionDate(String staffcode) {
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = "";
		try {
			sql = "select promotedate FROM promoted_listforprobation where staffcode = ? order by promotedate asc LIMIT 0,1 ";
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs = ps.executeQuery();
			while (rs.next()) {
				result = rs.getString("promotedate");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.closeCon(con);
		}
		
		return result;
	}	
	
}
