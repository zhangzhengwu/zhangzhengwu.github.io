package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constant;
import util.DBManager;
import util.DBManager_sqlservler;
import util.DateUtils;
import util.Util;
import dao.AdditionalDao;
import dao.GetCLDao;
import entity.Consultant_List;
import entity.QueryAdditional;
import entity.Staff_listBean;

/**
 * GetCLDao 实现类
 * @author King.XU
 *
 */
public class GetCLDaoImpl implements GetCLDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(GetCLDaoImpl.class);
	
	public GetCLDaoImpl() {
	}

	public int saveCL(String filename, InputStream os,String username) {
		int num=0;
		int beginRowIndex =4;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltableforPA("cons_list");/*           等待填写                      */
		Util.nq_addition();
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 獲取頁數
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
								/**获取Excel里面的指定单元格数据**/
				HSSFCell EmployeeIdcell = row.getCell(0);
				HSSFCell Aliascell=row.getCell(1);
				HSSFCell C_Namecell=row.getCell(2);
				HSSFCell joinDatecell=row.getCell(3);
				HSSFCell DirectLinecell=row.getCell(4);
				HSSFCell Emailcell=row.getCell(5);
				HSSFCell EmployeeNamecell=row.getCell(6);
				HSSFCell ExternalPositioncell=row.getCell(7);
				HSSFCell Gradecell=row.getCell(8);
				HSSFCell HKIDcell=row.getCell(9);
				HSSFCell GroupDateJoincell=row.getCell(10);
				HSSFCell Locationcell=row.getCell(11);
				HSSFCell Mobilecell=row.getCell(12);
				HSSFCell Positioncell=row.getCell(13);
				HSSFCell C_PositionNamecell=row.getCell(14);
				HSSFCell E_PositionNamecell=row.getCell(15);
				HSSFCell RecruiterIdcell=row.getCell(16);
				HSSFCell RecruiterNamecell=row.getCell(17);
				HSSFCell TelephoneNocell=row.getCell(18);

				/**给数据库里面的字段赋值**/
				String EmployeeId =Util.cellToString(EmployeeIdcell);
				String Alias=Util.cellToString(Aliascell);
				String C_Name=Util.cellToString(C_Namecell);
				String joinDate=Util.cellToString(joinDatecell);//入職日期
				String DirectLine=Util.cellToString(DirectLinecell);
				String Email=Util.cellToString(Emailcell);
				String EmployeeName=Util.cellToString(EmployeeNamecell);
				String ExternalPosition=Util.cellToString(ExternalPositioncell);
				String Grade=Util.cellToString(Gradecell);
				String HKID=Util.cellToString(HKIDcell);
				String GroupDateJoin=Util.cellToString(GroupDateJoincell);
				String Location=Util.cellToString(Locationcell);
				String Mobile=Util.cellToString(Mobilecell);
				String Position=Util.cellToString(Positioncell);
				String C_PositionName=Util.cellToString(C_PositionNamecell);
				String E_PositionName=Util.cellToString(E_PositionNamecell);
				String RecruiterId=Util.cellToString(RecruiterIdcell);
				String RecruiterName=Util.cellToString(RecruiterNamecell);
				String TelephoneNo=Util.cellToString(TelephoneNocell);
				
				if(EmployeeId.length()<9){
					sql = "insert cons_list values('"+EmployeeId+"','"+Alias+"','"+C_Name+"','"+joinDate+"','"+DirectLine+"','"+Email+"','"+EmployeeName+"','"+ExternalPosition+"','"+Grade+"','"+HKID+"','"+GroupDateJoin+"','"+Location+"','"+Mobile+"','"+Position+"','"+C_PositionName+"','"+E_PositionName+"','"+RecruiterId+"','"+RecruiterName+"','"+TelephoneNo+"')";
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入consultant——List成功！");
						num++;
					} else {
						logger.info("插入consultant——List失敗");
					}
					if(!Util.objIsNULL(joinDate)&&DateUtils.strUsToCh(joinDate).substring(0, 4).equals(DateUtils.getYear()+"")){	//判断入职年份和计算年份是否相等
						if(Util.findnq_addition(EmployeeId)==0){//该staffcode没在additional中
							AdditionalDao additionDao=new AdditionalDaoImpl();
							QueryAdditional qa=new QueryAdditional();
							qa.setStaffNo(EmployeeId);
							qa.setName(EmployeeName);
							qa.setAdditional(100+"");
							qa.setNum(100+"");
							qa.setRemark("New Comer");
							qa.setAdd_name("Upload");
							qa.setSfyx("Y");
							additionDao.add(qa);
						}
					}
					
					try{
						if(Grade.equals(Constant.PA_TYPE)||Grade.equals(Constant.SA_TYPE)||Grade.equals(Constant.CA_TYPE)){
						if(Grade.equals(Constant.PA_TYPE)){
					 		sql="insert nq_additional values('"+EmployeeId+"','"+EmployeeName+"','-400','PA','"+username+"','"+DateUtils.getDateToday()+"','',null,'Y')";
						}else if(Grade.equals(Constant.SA_TYPE)){
							sql="insert nq_additional values('"+EmployeeId+"','"+EmployeeName+"','-300','SA','"+username+"','"+DateUtils.getDateToday()+"','',null,'Y')";
						}else if(Grade.equals(Constant.CA_TYPE)){
							sql="insert nq_additional values('"+EmployeeId+"','"+EmployeeName+"','-300','CA','"+username+"','"+DateUtils.getDateToday()+"','',null,'Y')";
						}
						ps=con.prepareStatement(sql);
						ps.executeUpdate(); 
						}
					}catch(Exception e){
						e.printStackTrace();
					}
			
					
				}
			}
		} catch (Exception e) {
			return num;
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	
	/**
	 * 远程获取Staff信息
	 * @return
	 * @throws NullPointerException
	 */
	public List<Staff_listBean> getAllStaff() throws Exception{
		/**
		 *  Plan 1A : Grade AE, SA, PA
		 *	Plan 1:  Grade G1, G2, P1, P2, M1, M2
		 *  Plan 2:  Grade M3, D1, D2
		 */
		List<Staff_listBean> list=new ArrayList<Staff_listBean>();
		try{
			  con=DBManager_sqlservler.getCon();
			  sql="select * from vSZOADMStaff";
				ps=con.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					list.add(new Staff_listBean(
							rs.getString("StaffCode"),
							rs.getString("CompanyID"), 
							rs.getString("DepartmentCode"),
							rs.getString("StaffName"),
							rs.getString("EnglishName"),
							rs.getString("GradeID"), 
							"",
							rs.getString("email"),
							"", 
							"",//pplan
							"",//dental
							"",
							"",
							rs.getString("GroupDateJoin"),//
							rs.getString("ResignEffectiveDate"),
							rs.getString("Location")));//
				}
		}catch(Exception e){
			logger.error("同步staff信息时，网络连接异常");
			throw new Exception(e);
		}finally{
			DBManager_sqlservler.closeCon(con);
		}
		return list;
	}


	/**
	 * 定时同步香港stafflist-8点
	 * @return
	 */
	public String timeTaskHKStaffList(){
		String result = "";
		try{
			Util.printLogger(logger,"开始执行指定任务-同步Staff信息");    
			List<Staff_listBean> list=getAllStaff();
			if(list.size()>0){
				saveStaff(list);
				list=null;
				result="success";
				Util.printLogger(logger,"指定任务-->同步Staff成功!");
			}else{
				Util.printLogger(logger,"指定任务-->同步失败，原因：获取远程获取Staff信息时出错!");
				throw new Exception();
			}   
		}catch(Exception e){
			result=e.getMessage();
			Util.printLogger(logger,"指定任务-->同步Staff信息失败："+e.getMessage());

		}
		
		return result;
		
	}
	
	
	/**
	 * 保存Staff
	 * @param list   List<Staff_listBean>
	 */
	public void saveStaff(List<Staff_listBean> list){
		try{
			Util.deltableforPA("staff_list");
			con=DBManager.getCon();
			con.setAutoCommit(false);//禁止自动提交事务
			StringBuffer ssql=new StringBuffer("insert into staff_list(staffcode,company,deptid,staffname,englishname,grade,Etype,email,patient,pplan,dental,dplan,remarks,EnrollmentDate,TerminationDate,Location) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
			PreparedStatement ps2=con.prepareStatement(ssql.toString());
			for(int i=0;i<list.size();i++){
				Staff_listBean sb=list.get(i);
				/*sql="select * from staff_list where staffcode=?";
				ps=con.prepareStatement(sql);
				ps.setString(1, sb.getStaffcode());
				ResultSet rs=ps.executeQuery();
				if(!rs.next()){*/
				
				//针对SSC/SSC ITD同事进行修改相关信息，grade=dummy
					if("SSC".equalsIgnoreCase(sb.getDeptid()) || "ITD".equalsIgnoreCase(sb.getDeptid())){
						if(Util.objIsNULL(sb.getGrade())){
							sb.setGrade("dummy");
						}
					}
				
					ps2.setString(1,sb.getStaffcode());
					ps2.setString(2,sb.getCompany());
					ps2.setString(3,sb.getDeptid());
					ps2.setString(4,sb.getStaffname());
					ps2.setString(5,sb.getEnglishname());
					ps2.setString(6,sb.getGrade());
					ps2.setString(7,sb.getEtype());
					ps2.setString(8,sb.getEmail());
					ps2.setString(9,sb.getPatient());
					ps2.setString(10,sb.getPplan());
					ps2.setString(11,sb.getDental());
					ps2.setString(12,sb.getDplan());
					ps2.setString(13,sb.getRemarks());
					ps2.setString(14,sb.getEnrollmentDate());
					ps2.setString(15,sb.getTerminationDate());
					ps2.setString(16,sb.getLocation());
					ps2.addBatch();
				//}
			}
			if(!ssql.toString().equals("")){
				 
				ps2.executeBatch();
				con.commit();
			}
		}catch(Exception e){
			e.printStackTrace();
			try {
				con.rollback();
				logger.error("同步staff信息时 数据异常进行数据回滚");
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.info("同步staff信息时数据回滚异常   "+e);
			}
		}finally{
			DBManager.closeCon(con);
		}
	}
	
	/**
	 * 远程获取Consultant_List列表
	 **/
	public List<Consultant_List> getAllConsList()throws NullPointerException {
		List<Consultant_List> clss=new ArrayList<Consultant_List>();
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
				Consultant_List cl=new Consultant_List();
				cl.setEmployeeId(rs.getString("EmployeeID"));
				cl.setAlias(rs.getString("Alias"));
				cl.setC_Name(rs.getString("ChineseName"));
				//cl.setC_Name(new String(rs.getString("ChineseName").getBytes(),"GBK"));
				cl.setJoinDate(sdf.format(sdf.parse(rs.getString("DateJoin"))));
				cl.setDirectLine(rs.getString("DirectLine"));
				cl.setEmail(rs.getString("Email"));
				cl.setEmployeeName(rs.getString("EmployeeName"));
				cl.setExternalPosition(rs.getString("ExternalPositionInEnglish"));
				cl.setGrade(rs.getString("GradeID"));
				cl.setHKID(rs.getString("HKID"));
				cl.setGroupDateJoin(sdf.format(sdf.parse(rs.getString("GroupDateJoin"))));
				cl.setPosition(rs.getString("PositionID"));
				
				/**
				 * 
				 */
				location=rs.getString("Location");
				location=Util.objIsNULL(location)?"":(location.substring(location.indexOf("F ")+1,location.length()).trim().replace("Cityplaza 3", "CP3"));
				
				//cl.setLocation(rs.getString("Location"));
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
			Position=location=null;
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			logger.info("同步ConsultantList条数==>"+clss.size());
			DBManager_sqlservler.closeCon(cons);
		}

		return clss;
	}
	
	public void saveConsultant(List<Consultant_List> cls) throws Exception{
		try{
			Util.deltables("cons_list");/*           等待填写                      */
        	Util.nq_addition();
			List<String> staffList=new ArrayList<String>();
			con=DBManager.getCon();
			for(int i= 0;i<cls.size();i++){
				Consultant_List cl=cls.get(i);	
				try{
					if(!staffList.contains(cl.getEmployeeId())){
							//蕭𣻻彤  无法识别
						sql="insert into cons_list values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						ps=con.prepareStatement(sql);
						ps.setString(1, cl.getEmployeeId());
						ps.setString(2, cl.getAlias());
						//ps.setString(3, cl.getC_Name());
						if(cl.getEmployeeId().equals("JS0111"))//蕭𣻻彤  无法识别
							ps.setString(3, (new String(cl.getC_Name().getBytes(),"GBK")));
						else
							ps.setString(3,cl.getC_Name());
						ps.setString(4, cl.getJoinDate());
						ps.setString(5, cl.getDirectLine());
						ps.setString(6, cl.getEmail());
						ps.setString(7, cl.getEmployeeName());
						ps.setString(8, cl.getExternalPosition());
						ps.setString(9, cl.getGrade());
						ps.setString(10, cl.getHKID());
						ps.setString(11, cl.getGroupDateJoin());
						ps.setString(12, cl.getLocation());
						ps.setString(13, cl.getMobile());
						ps.setString(14, cl.getPosition());
						ps.setString(15, cl.getC_PositionName());
						ps.setString(16, cl.getE_PositionName());
						ps.setString(17, cl.getRecruiterId());
						ps.setString(18, cl.getRecruiterName());
						ps.setString(19, cl.getTelephone());
						ps.setString(20, cl.getADTreeHead());
						ps.setString(21, cl.getDDTreeHead());
						ps.execute();
				
		//		if(!Util.objIsNULL(cl.getJoinDate())&&DateUtils.strUsToCh(cl.getJoinDate()).substring(0, 4).equals(DateUtils.getYear()+"")){	//判断入职年份和计算年份是否相等
						if(!Util.objIsNULL(cl.getJoinDate())&& cl.getJoinDate().substring(0, 4).equals(DateUtils.getYear()+"")){	//判断入职年份和计算年份是否相等
							if(Util.findnq_addition(cl.getEmployeeId())==0){//该staffcode没在additional中
								AdditionalDao additionDao=new AdditionalDaoImpl();
								QueryAdditional qa=new QueryAdditional();
								qa.setStaffNo(cl.getEmployeeId());
								qa.setName(cl.getEmployeeName());
								qa.setAdditional(100+"");
								qa.setNum(100+"");
								qa.setRemark("New Comer");
								qa.setAdd_name("Upload");
								qa.setSfyx("Y");
								additionDao.add(qa);
							}
						}
					
						if(cl.getGrade().equals(Constant.PA_TYPE)||cl.getGrade().equals(Constant.SA_TYPE)||cl.getGrade().equals(Constant.CA_TYPE)){
							if(cl.getGrade().equals(Constant.PA_TYPE)){
							 		sql="insert nq_additional values('"+cl.getEmployeeId()+"','"+cl.getEmployeeName()+"','-400','PA','SystemPrograming','"+DateUtils.getDateToday()+"','',null,'Y')";
							}else if(cl.getGrade().equals(Constant.SA_TYPE)){
									sql="insert nq_additional values('"+cl.getEmployeeId()+"','"+cl.getEmployeeName()+"','-300','SA','SystemPrograming','"+DateUtils.getDateToday()+"','',null,'Y')";
							}else if(cl.getGrade().equals(Constant.CA_TYPE)){
								sql="insert nq_additional values('"+cl.getEmployeeId()+"','"+cl.getEmployeeName()+"','-300','CA','SystemPrograming','"+DateUtils.getDateToday()+"','',null,'Y')";
							}
							ps=con.prepareStatement(sql);
							ps.executeUpdate(); 
							if(!Util.objIsNULL(cl.getJoinDate())&& cl.getJoinDate().substring(0, 4).equals(DateUtils.getYear()+"")){
								
							}else{//非new Comer
								
							}
							
							
						}
						staffList.add(cl.getEmployeeId());
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.error("同步Consultant List插入-->"+cl.getEmployeeId()+"-->时出现异常"+e.getMessage());
			}
			}
			staffList=null;
			cls=null;
			
		
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
	}
	
	
	public void saveConsultant_new(List<Consultant_List> cls) throws Exception{
		try{
			Util.deltables("cons_list");/*           等待填写                      */
        	Util.nq_addition();
			List<String> staffList=new ArrayList<String>();
			con=DBManager.getCon();
			for(int i= 0;i<cls.size();i++){
				Consultant_List cl=cls.get(i);	
				try{
					if(!staffList.contains(cl.getEmployeeId())){
							//蕭𣻻彤  无法识别
						sql="insert into cons_list values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						ps=con.prepareStatement(sql);
						ps.setString(1, cl.getEmployeeId());
						ps.setString(2, cl.getAlias());
						//ps.setString(3, cl.getC_Name());
						if(cl.getEmployeeId().equals("JS0111"))//蕭𣻻彤  无法识别
							ps.setString(3, (new String(cl.getC_Name().getBytes(),"GBK")));
						else
							ps.setString(3,cl.getC_Name());
						ps.setString(4, cl.getJoinDate());
						ps.setString(5, cl.getDirectLine());
						ps.setString(6, cl.getEmail());
						ps.setString(7, cl.getEmployeeName());
						ps.setString(8, cl.getExternalPosition());
						ps.setString(9, cl.getGrade());
						ps.setString(10, cl.getHKID());
						ps.setString(11, cl.getGroupDateJoin());
						ps.setString(12, cl.getLocation());
						ps.setString(13, cl.getMobile());
						ps.setString(14, cl.getPosition());
						ps.setString(15, cl.getC_PositionName());
						ps.setString(16, cl.getE_PositionName());
						ps.setString(17, cl.getRecruiterId());
						ps.setString(18, cl.getRecruiterName());
						ps.setString(19, cl.getTelephone());
						ps.setString(20, cl.getADTreeHead());
						ps.setString(21, cl.getDDTreeHead());
						ps.execute();
				
					
						//PA/SA/CA
						/**
						 * 2015-12-24 12:55:10
						 * jackie最新处理逻辑(PA,CA,SA每人每年可免费印制名片限额为100pcs，新入职不会额外增加数量)
						 */
						if(cl.getGrade().equals(Constant.PA_TYPE)||cl.getGrade().equals(Constant.SA_TYPE)||cl.getGrade().equals(Constant.CA_TYPE)){
								sql="insert nq_additional values('"+cl.getEmployeeId()+"','"+cl.getEmployeeName()+"','-300','"+cl.getGrade()+"','SystemPrograming','"+DateUtils.getDateToday()+"','',null,'Y')";
							ps=con.prepareStatement(sql);
							ps.executeUpdate(); 
							if(!Util.objIsNULL(cl.getJoinDate())&& cl.getJoinDate().substring(0, 4).equals(DateUtils.getYear()+"")){
								
							}else{//非new Comer
								
							}
							
							
						}else{//正常处理
							if(!Util.objIsNULL(cl.getJoinDate())&& cl.getJoinDate().substring(0, 4).equals(DateUtils.getYear()+"")){	//判断入职年份和计算年份是否相等
								if(Util.findnq_addition(cl.getEmployeeId())==0){//该staffcode没在additional中
									AdditionalDao additionDao=new AdditionalDaoImpl();
									QueryAdditional qa=new QueryAdditional();
									qa.setStaffNo(cl.getEmployeeId());
									qa.setName(cl.getEmployeeName());
									qa.setAdditional(100+"");
									qa.setNum(100+"");
									qa.setRemark("New Comer");
									qa.setAdd_name("Upload");
									qa.setSfyx("Y");
									additionDao.add(qa);
								}
							}
						}
						staffList.add(cl.getEmployeeId());
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.error("同步Consultant List插入-->"+cl.getEmployeeId()+"-->时出现异常"+e.getMessage());
			}
			}
			staffList=null;
			cls=null;
			
		
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
	}

	
	 
	
}
