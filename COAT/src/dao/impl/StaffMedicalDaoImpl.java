package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Page;
import util.Util;
import dao.StaffMedicalDao;
import entity.Medical_Staff_Type;
import entity.Medical_record_staff;
import entity.Medical_record_staff_bak;
import entity.Staff_Master;
import entity.Staff_listBean;

public class StaffMedicalDaoImpl implements StaffMedicalDao {
	Logger logger = Logger.getLogger(GetrReqRecordDaoImpl.class);
	PreparedStatement ps = null;
	String sql = "";
	ResultSet rs=null;
	Connection con = null;
	/**
	 *  // TODO 根据staffcode查询staff_listBean
	 */
	public Staff_listBean selectstaff(String staffcode) {
		Staff_listBean sb=null;
		try{
			con=DBManager.getCon();
			sql="select * from staff_list where staffcode like ? ";
			logger.info("查询个人信息     sql:==="+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, "%"+staffcode+"%");
			rs=ps.executeQuery();
			while(rs.next()){
				sb=new Staff_listBean(rs.getInt(1),
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4),
						rs.getString(5), 
						rs.getString(6),
						rs.getString(7),
						rs.getString(8), 
						rs.getString(9), 
						rs.getString(10), 
						rs.getString(11), 
						rs.getString(12), 
						rs.getString(13),
						rs.getString(14),
						rs.getString(15),
						rs.getString(16)
				);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询staff个人信息时出现："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return sb;
	}
	/**
	 *  TODO 根据type查询stafftype
	 */

	public List<Medical_Staff_Type> selectstaffType(String type) {
		List<Medical_Staff_Type> mst=new ArrayList<Medical_Staff_Type>();
		try{
			con=DBManager.getCon();
			sql="select * from staff_medical_type where substring_index(type, '-', 1)=?";
			logger.info("根据type查询stafftype   sql:===="+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, type);
			rs=ps.executeQuery();
			while(rs.next()){
				Medical_Staff_Type sb=new Medical_Staff_Type(rs.getInt(1),
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4),
						rs.getString(5), 
						rs.getString(6),
						rs.getString(7), 
						rs.getString(8)); 
				mst.add(sb);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("根据type查询stafftype时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return mst;
	}
	/**
	 * // TODO 根据staffcode，MedicalDate查询已报销记录
	 */

	public List<Staff_Master> getTypeNumber(String staffcode, String MedicalDate) {
		List<Staff_Master> list=new ArrayList<Staff_Master>();
		try{
			con=DBManager.getCon();
			sql="select term,staffcode,package,medical_date,medical_Dental,term,medical_Normal,medical_Special,medical_Regular "+
			" from medical_claim_staff where sfyx='Y' and staffcode=? and year(?)=year(now()) order by (term+0) desc,(medical_dental+0) desc,add_date desc limit 0,1";
			logger.info("根据staffcode，medicalDate查询已报销记录       sql:===="+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1,staffcode);
			ps.setString(2,MedicalDate);
			rs=ps.executeQuery();
			while(rs.next()){
				Staff_Master sm=new Staff_Master(rs.getString("staffcode"),
						rs.getString("package"),
						rs.getString("medical_date"),
						rs.getString("medical_Dental"), 
						rs.getString("medical_Normal"), 
						rs.getString("medical_Special"),
						rs.getString("medical_Regular")
				);
				list.add(sm);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("根据staffcode,medicaldate查询已报销记录时出现："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	//TODO Auto-generated method stub保存Medical_Record_staff
	public int saveMedical(Medical_record_staff mrs) {
		int num=-1;
		try{
			con=DBManager.getCon();
			sql="insert into medical_claim_staff(staffcode,name,company,dept,grade,plan,package,email,maxamount,type,term,medical_Normal,medical_Special,medical_Regular,medical_Dental,medical_date,medical_fee,medical_month,amount,return_oraginal,SameDay,add_Name,add_Date,upd_Name,upd_Date,sfyx) " +
			"value(?,?,?,?,?," +
			"?,?,?,?,?," +
			"?,?,?,?,?," +
			"?,?,?,?,?," +
			"?,?,?,?,?,?);";
			logger.info("保存staff Medical     sql:===="+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, mrs.getStaffcode());
			ps.setString(2, mrs.getName());
			ps.setString(3, mrs.getCompany());
			ps.setString(4, mrs.getDept());
			ps.setString(5, mrs.getGrade());
			ps.setString(6, mrs.getPlan());
			ps.setString(7, mrs.getPackages());
			ps.setString(8, mrs.getEmail());
			ps.setString(9, mrs.getMaxamount());
			ps.setString(10, mrs.getType());
			ps.setString(11, mrs.getTerm());
			ps.setString(12, mrs.getMedical_Normal());
			ps.setString(13, mrs.getMedical_Special());
			ps.setString(14, mrs.getMedical_Regular());
			ps.setString(15, mrs.getMedical_Dental());
			ps.setString(16, mrs.getMedical_date());
			ps.setString(17, mrs.getMedical_fee());
			ps.setString(18, mrs.getMedical_month());
			ps.setString(19, mrs.getAmount());
			ps.setString(20, mrs.getReturn_oraginal());
			ps.setString(21, mrs.getSameDay());
			ps.setString(22, mrs.getAdd_Name());
			ps.setString(23, mrs.getAdd_Date());
			ps.setString(24, mrs.getUpd_Name());
			ps.setString(25, mrs.getUpd_Date());
			ps.setString(26, mrs.getSfyx());
			num=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("保存Staff Medical时出现："+e.toString());
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	//TODO Auto-generated method stub保存Medical_Record_staff
	public int saveRejectBak(Medical_record_staff_bak mrs) {
		int num=-1;
		try{
			con=DBManager.getCon();
			sql="insert into medical_claim_staff_reject_info(staffcode,name,company,dept,grade,plan,package,email,maxamount,type,term,medical_Normal,medical_Special,medical_Regular,medical_Dental,medical_date,medical_fee,medical_month,amount,return_oraginal,SameDay,upd_Name,upd_Date,sfyx,subject,remark) " +
			"value(?,?,?,?,?," +
			"?,?,?,?,?," +
			"?,?,?,?,?," +
			"?,?,?,?,?," +
			"?,?,?,?,?,?);";
			logger.info("保存staff Medical_bak     sql:===="+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, mrs.getStaffcode());
			ps.setString(2, mrs.getName());
			ps.setString(3, mrs.getCompany());
			ps.setString(4, mrs.getDept());
			ps.setString(5, mrs.getGrade());
			ps.setString(6, mrs.getPlan());
			ps.setString(7, mrs.getPackages());
			ps.setString(8, mrs.getEmail());
			ps.setString(9, mrs.getMaxamount());
			ps.setString(10, mrs.getType());
			ps.setString(11, mrs.getTerm());
			ps.setString(12, mrs.getMedical_Normal());
			ps.setString(13, mrs.getMedical_Special());
			ps.setString(14, mrs.getMedical_Regular());
			ps.setString(15, mrs.getMedical_Dental());
			ps.setString(16, mrs.getMedical_date());
			ps.setString(17, mrs.getMedical_fee());
			ps.setString(18, mrs.getMedical_month());
			ps.setString(19, mrs.getAmount());
			ps.setString(20, mrs.getReturn_oraginal());
			ps.setString(21, mrs.getSameDay());
			ps.setString(22, mrs.getUpd_Name());
			ps.setString(23, mrs.getUpd_Date());
			ps.setString(24, mrs.getSfyx());
			ps.setString(25, mrs.getSubject());
			ps.setString(26, mrs.getRemark());
			num=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("保存Staff Medical_bak 时出现："+e.toString());
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 *  TODO 根据staffcode、return_oraginal、StartDate、EndDate导出报销记录 
	 */

	public ResultSet selectResultSet(String staffcode,
			String returnOraginal, String startDate, String endDate) {
		try{
			con=DBManager.getCon();
			StringBuffer strin=new StringBuffer("select staffcode,Name,Company,dept,Grade,plan,type,medical_date,medical_fee,amount,term,medical_month,medical_Normal,medical_Special,medical_Dental,medical_Regular");
			strin.append(" from medical_claim_staff where sfyx='Y' and staffcode like ?  and return_oraginal like ? ");
			if(!Util.objIsNULL(startDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') " );
			}
			if(!Util.objIsNULL(endDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d') <=DATE_FORMAT(?,'%Y-%m-%d') ");
			}
			strin.append("  order by add_date asc,term+0 asc");
			ps=con.prepareStatement(strin.toString());
			ps.setString(1,"%"+staffcode+"%");
			ps.setString(2,"%"+returnOraginal+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate)){
					ps.setString(4, endDate);
				}
			}else {
				if(!Util.objIsNULL(endDate) ){
					ps.setString(3, endDate);
				}
			}
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}

		return rs;
	}
	/**
	 *  TODO 根据staffcode、return_oraginal、StartDate、EndDate导出报销记录 
	 */
	
	public ResultSet selectAllResultSet(String staffcode,
			String returnOraginal, String startDate, String endDate) {
		try{
			con=DBManager.getCon();
			StringBuffer strin=new StringBuffer("select staffcode,Name,Company,dept,Grade,plan,type,medical_date,medical_fee,amount,term,medical_month,medical_Normal,medical_Special,medical_Dental,medical_Regular,return_oraginal,'Approved' as status,'' as remark ");
			strin.append(" from medical_claim_staff where sfyx='Y' and staffcode like ?  and return_oraginal like ? ");
			if(!Util.objIsNULL(startDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') " );
			}
			if(!Util.objIsNULL(endDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d') <=DATE_FORMAT(?,'%Y-%m-%d') ");
			}
			strin.append("  order by add_date asc,term+0 asc");
			ps=con.prepareStatement(strin.toString());
			ps.setString(1,"%"+staffcode+"%");
			ps.setString(2,"%"+returnOraginal+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate)){
					ps.setString(4, endDate);
				}
			}else {
				if(!Util.objIsNULL(endDate) ){
					ps.setString(3, endDate);
				}
			}
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		
		return rs;
	}
	/**
	 *  TODO 根据staffcode、return_oraginal、StartDate、EndDate导出报销记录 
	 */
	
	public ResultSet selectMedicalRjectResultSet(String staffcode,
			String returnOraginal, String startDate, String endDate) {
		try{
			con=DBManager.getCon();
			StringBuffer strin=new StringBuffer("select staffcode,Name,Company,dept,Grade,plan,type,medical_date,medical_fee,'' as amount,'' as term,medical_month,'' as medical_Normal,'' as medical_Special,'' as medical_Dental,'' as medical_Regular,return_oraginal,'Rejected' as status,remark ");
			strin.append(" from medical_claim_staff_reject_info where sfyx='Y' and staffcode like ?  and return_oraginal like ? ");
			if(!Util.objIsNULL(startDate)){
				strin.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') " );
			}
			if(!Util.objIsNULL(endDate)){
				strin.append(" and DATE_FORMAT(upd_date,'%Y-%m-%d') <=DATE_FORMAT(?,'%Y-%m-%d') ");
			}
			strin.append(" order by term+0 asc");
			ps=con.prepareStatement(strin.toString());
			ps.setString(1,"%"+staffcode+"%");
			ps.setString(2,"%"+returnOraginal+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate)){
					ps.setString(4, endDate);
				}
			}else {
				if(!Util.objIsNULL(endDate) ){
					ps.setString(3, endDate);
				}
			}
			rs=ps.executeQuery();
			/**	while(rs.next()){
				Medical_record_staff mrs=new Medical_record_staff(rs.getInt("id"), 
						rs.getString("staffcode"),
						rs.getString("Name"), 
						rs.getString("company"), //company
						rs.getString("dept"), 
						rs.getString("Grade"),
						rs.getString("plan"),
						rs.getString("package"),//package
						rs.getString("email"),
						rs.getString("maxamount"), //最大报销金额
						rs.getString("type"), 
						rs.getString("term"), 
						rs.getString("medical_Normal"),
						rs.getString("medical_Special"), 
						rs.getString("medical_Regular"), 
						rs.getString("medical_Dental"), 
						rs.getString("medical_date"), 
						rs.getString("medical_Fee"), 
						rs.getString("amount"), 
						rs.getString("return_Oraginal"),
						rs.getString("sameDay"),//是否重复天 sameDay 
						rs.getString("upd_name"), 
						rs.getString("upd_Date"),
						rs.getString("sfyx"));
				mrslist.add(mrs);
			}
			 **/
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return rs;
	}
	/**
	 *  TODO 根据staffcode、return_oraginal、StartDate、EndDate导出报销记录 
	 */
	
	public ResultSet selectResultSetForFAD(String staffcode,
			String returnOraginal, String startDate, String endDate) {
		//List<Medical_record_staff> mrslist=new ArrayList<Medical_record_staff>();
		try{
			con=DBManager.getCon();
			StringBuffer strin=new StringBuffer("select company,staffcode,name,sum(amount) amount from medical_claim_staff where sfyx='Y' and staffcode like ?  and return_oraginal like ? ");
			if(!Util.objIsNULL(startDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') " );
			}
			if(!Util.objIsNULL(endDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d') <=DATE_FORMAT(?,'%Y-%m-%d') ");
			}
			strin.append(" group by staffcode order by add_date desc");
			ps=con.prepareStatement(strin.toString());
			ps.setString(1,"%"+staffcode+"%");
			ps.setString(2,"%"+returnOraginal+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate)){
					ps.setString(4, endDate);
				}
			}else {
				if(!Util.objIsNULL(endDate) ){
					ps.setString(3, endDate);
				}
			}
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
		return rs;
	}

	/**
	 *  TODO 根据staffcode、return_oraginal、StartDate、EndDate查询报销记录条数 
	 */

	public int getRows(String staffcode,
			String returnOraginal, String startDate, String endDate) {
		int num=-1;
		try{
			con=DBManager.getCon();
			//sql="select id,staffcode,Name,dept,Grade,plan,type,medical_date,medical_fee,amount,term,medical_Normal,medical_Special,medical_Dental,medical_Regular,return_oraginal,sfyx,upd_date" +
			StringBuffer strin=new StringBuffer("select count(*) from medical_claim_staff where sfyx='Y' and staffcode like ?  and return_oraginal like ? ");
			if(!Util.objIsNULL(startDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') " );
			}
			if(!Util.objIsNULL(endDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d') <=DATE_FORMAT(?,'%Y-%m-%d') ");
			}
			strin.append(" order by add_date desc");
			logger.info("查询Staff  Medical 最新历史办理记录行数     sql：===="+strin.toString());
			ps=con.prepareStatement(strin.toString());
			ps.setString(1,"%"+staffcode+"%");
			ps.setString(2,"%"+returnOraginal+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate)){
					ps.setString(4, endDate);
				}
			}else {
				if(!Util.objIsNULL(endDate) ){
					ps.setString(3, endDate);
				}
			}
			rs=ps.executeQuery();
			while(rs.next()){
				num=Integer.parseInt(rs.getString(1));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询Staff Medical最新历史办理记录行数时出现："+e.toString());
			num=-2;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 *  TODO 根据staffcode、return_oraginal、StartDate、EndDate查询报销记录 
	 */

	public List<Medical_record_staff> selectList(String staffcode,
			String returnOraginal, String startDate, String endDate,Page page) {
		List<Medical_record_staff> mrslist=new ArrayList<Medical_record_staff>();
		try{
			con=DBManager.getCon();
			StringBuffer strin=new StringBuffer("select * from medical_claim_staff where sfyx='Y' and staffcode like ?  and return_oraginal like ? ");
			if(!Util.objIsNULL(startDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') " );
			}
			if(!Util.objIsNULL(endDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d') <=DATE_FORMAT(?,'%Y-%m-%d') ");
			}
			strin.append(" order by add_date desc ");
			strin.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize()+" ");
			logger.info("查询Staff Medical最新历史办理记录       sql:=="+strin.toString());
			ps=con.prepareStatement(strin.toString());
			ps.setString(1,"%"+staffcode+"%");
			ps.setString(2,"%"+returnOraginal+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate)){
					ps.setString(4, endDate);
				}
			}else {
				if(!Util.objIsNULL(endDate) ){
					ps.setString(3, endDate);
				}
			}
			rs=ps.executeQuery();
			while(rs.next()){
				Medical_record_staff mrs=new Medical_record_staff(rs.getInt("id"), 
						rs.getString("staffcode"),
						rs.getString("Name"), 
						rs.getString("company"), //company
						rs.getString("dept"), 
						rs.getString("Grade"),
						rs.getString("plan"),
						rs.getString("package"),//package
						rs.getString("email"),
						rs.getString("maxamount"), //最大报销金额
						rs.getString("type"), 
						rs.getString("term"), 
						rs.getString("medical_Normal"),
						rs.getString("medical_Special"), 
						rs.getString("medical_Regular"), 
						rs.getString("medical_Dental"), 
						rs.getString("medical_date"), 
						rs.getString("medical_Fee"), 
						rs.getString("medical_month"),
						rs.getString("amount"), 
						rs.getString("return_Oraginal"),
						rs.getString("sameDay"),//是否重复天 sameDay 
						rs.getString("add_name"),
						rs.getString("add_Date"),
						rs.getString("upd_name"), 
						rs.getString("upd_Date"),
						rs.getString("sfyx"));
				mrslist.add(mrs);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询Staff Medical 最新办理记录时出现："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return mrslist;
	}
	/**
	 *  TODO 根据staffcode、return_oraginal、StartDate、EndDate查询所有staffcode
	 */

	public List<String> selectStaffcode(String staffcode,
			String returnOraginal, String startDate, String endDate) {
		List<String> mrslist=new ArrayList<String>();
		try{
			con=DBManager.getCon();
			StringBuffer strin=new StringBuffer("select staffcode from medical_claim_staff where sfyx='Y' and staffcode like ?  and return_oraginal like ? ");
			if(!Util.objIsNULL(startDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') " );
			}
			if(!Util.objIsNULL(endDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d') <=DATE_FORMAT(?,'%Y-%m-%d') ");
			}
			strin.append("group by staffcode");
			ps=con.prepareStatement(strin.toString());
			ps.setString(1,"%"+staffcode+"%");
			ps.setString(2,"%"+returnOraginal+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate)){
					ps.setString(4, endDate);
				}
			}else {
				if(!Util.objIsNULL(endDate) ){
					ps.setString(3, endDate);
				}
			}
			rs=ps.executeQuery();
			while(rs.next()){
				mrslist.add(rs.getString("staffcode"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}

		return mrslist;
	}
	/**
	 *  TODO 根据Id修改Medical_Staff 状态
	 */

	public int updateMedical(int id,String upd_date,String username) {
		int num=-1;
		try{
			con=DBManager.getCon();
			sql="update medical_claim_staff set sfyx='N',upd_date=?,upd_Name=? where sfyx='Y' and id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1,upd_date);
			ps.setString(2,username);
			ps.setInt(3, id);
			num=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			num=0;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	/**
	 * TODO 根据staffcode、retype、trim更新MedicalStaff
	 */
	public int updateSpecialDown(String reType, int term, String staffcode,String reEntitle,String reDental,String MedicalDate) {
		int num=-1; 
		try{
			con=DBManager.getCon();
			if(reType.equals("SP")){//原来为SP
				sql="update medical_claim_staff set medical_Special=medical_Special-1,term=term-1 where sfyx='Y' and staffcode=? and (term+0)>? and year(medical_date)=year(?)";
			}else if(reType.equals("GP")){//原来为GP
				sql="update medical_claim_staff set medical_Normal=medical_Normal-1,term=term-1  where sfyx='Y' and staffcode=? and (term+0)>? and year(medical_date)=year(?)";
			} else if(reType.equals("Regular")){//原来为Regular
				sql="update medical_claim_staff set medical_Regular=medical_Regular-1,term=term-1  where sfyx='Y' and staffcode=? and (term+0)>? and year(medical_date)=year(?)";
			}else{//类型为Dental   无需修改使用次数数据
				// sql="update medical_claim_staff set medical_Dental=medical_Dental-?  where staffcode=? and (term+0)>?";
				sql="update medical_claim_staff set medical_Dental=medical_Dental-?  where sfyx='Y' and staffcode=? and (Medical_Dental+0)>=? and year(medical_date)=year(?)";
			}
			ps=con.prepareStatement(sql);
			if(reType.equals("Dental")){
				ps.setString(1, reEntitle);
				ps.setString(2, staffcode);
				ps.setInt(3, Integer.parseInt(reDental));
				ps.setString(4, MedicalDate);
			}else{
				ps.setString(1, staffcode);
				ps.setInt(2, term);
				ps.setString(3, MedicalDate);
			}
			num=ps.executeUpdate();
		}catch(Exception e){
			logger.error("在修改Medical_claim_staff 递减时出现：==="+e);
			e.printStackTrace();
			num=-2;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	/**
	 * TODO 根据staffcode、type、trim更新MedicalStaff
	 */
	public int updateSpecialUP(String type, int term, String staffcode,String upd_date,String entitle,String reDental,String MedicalDate) {
		int num=-1; 
		try{
			con=DBManager.getCon();
			if(type.equals("SP")){//类型为ＳＰ
				sql="update medical_claim_staff set medical_Special=medical_Special+1,term=term+1 where sfyx='Y' and add_date!=? and staffcode=? and (term+0)>? and year(medical_date)=year(?)";
			}else if(type.equals("GP")){//类型为GP
				sql="update medical_claim_staff set medical_Normal=medical_Normal+1,term=term+1  where sfyx='Y' and add_date>? and staffcode=? and (term+0)>? and year(medical_date)=year(?)";
			} else if(type.equals("Regular")){//类型为Regular
				sql="update medical_claim_staff set medical_Regular=medical_Regular+1,term=term+1   where sfyx='Y' and add_date!=? and staffcode=? and (term+0)>? and year(medical_date)=year(?)";
			}else{//类型为Dental   无需修改使用次数数据
				// term=term+1;
				sql="update medical_claim_staff set medical_Dental=medical_Dental+? where sfyx='Y' and add_date!=? and staffcode=? and  (term+0)>=? and add_date>? and year(medical_date)=year(?)";
			}
			ps=con.prepareStatement(sql);
			if(type.equals("Dental")){
				ps.setString(1, entitle);
				ps.setString(2, upd_date);
				ps.setString(3, staffcode);
				ps.setInt(4, term);
				ps.setString(5, upd_date);
				ps.setString(6, MedicalDate);
			}else{
				ps.setString(1, upd_date);
				ps.setString(2, staffcode);
				ps.setInt(3, term);
				ps.setString(4, MedicalDate);
			}
			num=ps.executeUpdate();
		}catch(Exception e){
			logger.error("在修改Medical_claim_staff 增加时出现：==="+e);
			e.printStackTrace();
			num=-2;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 *  TODO 根据staffcode，upd_date删除数据
	 * @param staffcode
	 * @param upd_date
	 * @return
	 * 调用处 DeleteMedicalStaffServlet line in 39
	 */
	public int deleteMedicalStaff(String staffcode, String updDate,String username) {
		int num=-1;
		try{
			con=DBManager.getCon();
			sql="update medical_claim_staff set sfyx='D',upd_Date=?,upd_Name=? where staffcode=? and add_Date=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, DateUtils.getNowDateTime());
			ps.setString(2, username);
			ps.setString(3, staffcode);
			ps.setString(4, updDate);
			num=ps.executeUpdate();
			logger.info(username+"在StaffMedicalDaoImpl.deleteMedicalStaff中进行删除操作  sql:"+sql+" === parameters[]{staffcode:"+staffcode+",upd_date:"+updDate+"}");
		}catch(Exception e){
			logger.error("在StaffMedicalDaoImpl.deleteMedicalStaff中进行删除操作  sql:"+sql+" === parameters[]{staffcode:"+staffcode+",upd_date:"+updDate+"} 出现异常："+e.toString());
			num=-2;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 更新删除成功后的数据
	 * @param staffcode
	 * @param type
	 * @param term
	 * @param medical_Dental
	 * @return
	 * 调用处 DeleteMedicalStaffServlet line in 42
	 */
	public int updateDelte(String staffcode, String type, String term,
			String medicalDental,String username) {
		int num=-1;
		try{
			con=DBManager.getCon();
			StringBuffer stringBuffer =new StringBuffer("update medical_claim_staff set ");
			if(type.equals("Dental")){//如果删除的是Dental
				stringBuffer.append("medical_dental=(medical_dental-"+Double.parseDouble(medicalDental)+") where medical_dental>="+Double.parseDouble(medicalDental));
			}else if(type.equals("GP")){//如果删除的是GP
				stringBuffer.append("medical_Normal=medical_Normal-1,term=term-1 where (term+0)>"+term);
			}else if(type.equals("SP")){//如果删除的是SP
				stringBuffer.append("medical_Special=medical_Special-1,term=term-1 where (term+0)>"+term);
			}else if(type.equals("Regular")){//如果删除的是Regular
				stringBuffer.append("medical_Regular=medical_Regular-1,term=term-1 where (term+0)>"+term);
			}
			stringBuffer.append(" and sfyx='Y' ");
			ps=con.prepareStatement(stringBuffer.toString());
			num=ps.executeUpdate();
			logger.info(username+"在StaffMedicalDaoImpl.updateDelte中进行删除操作之后更新数据的操作  sql:"+sql+" === parameters[]{staffcode:"+staffcode+",type:"+type+",term:"+term+",medical_Dental:"+medicalDental+"}");
		}catch(Exception e){
			logger.error(username+"在StaffMedicalDaoImpl.updateDelte中进行删除之后更新数据的操作  sql:"+sql+" === parameters[]{staffcode:"+staffcode+",type:"+type+",term:"+term+",medical_Dental:"+medicalDental+"} 出现异常："+e.toString());
			num=-2;
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 		// TODO 查询MedicalDate当天报销所有的type
	 */
	public List<String> getType(String staffcode, String medicalDate) {
		List<String> list=new ArrayList<String>();
		try{
			con=DBManager.getCon();
			String sql="select type from medical_claim_staff where  sfyx='Y' and staffcode=? and medical_date=?";
		logger.info("查询MedicalDate当天报销的所有type    sql:=="+sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			ps.setString(2, medicalDate);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			list.add(rs.getString("type"));
		}
		rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询MedicalDate 当天报销的所有type时出现："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	/**
	 * TODO 根据staffcode、return_oraginal、StartDate、EndDate查询所有Company
	 */
	public List<String> selectCompany(String staffcode, String returnOraginal,
			String startDate, String endDate) {
		List<String> mrslist=new ArrayList<String>();
		try{
			con=DBManager.getCon();
			StringBuffer strin=new StringBuffer("select company from medical_claim_staff where sfyx='Y' and company!='' and staffcode like ?  and return_oraginal like ? ");
			if(!Util.objIsNULL(startDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') " );
			}
			if(!Util.objIsNULL(endDate)){
				strin.append(" and DATE_FORMAT(add_date,'%Y-%m-%d') <=DATE_FORMAT(?,'%Y-%m-%d') ");
			}
			strin.append("group by company");
			ps=con.prepareStatement(strin.toString());
			ps.setString(1,"%"+staffcode+"%");
			ps.setString(2,"%"+returnOraginal+"%");
			if(!Util.objIsNULL(startDate)){
				ps.setString(3, startDate);
				if(!Util.objIsNULL(endDate)){
					ps.setString(4, endDate);
				}
			}else {
				if(!Util.objIsNULL(endDate) ){
					ps.setString(3, endDate);
				}
			}
			rs=ps.executeQuery();
			while(rs.next()){
				mrslist.add(rs.getString("company"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return mrslist;
	}


}
