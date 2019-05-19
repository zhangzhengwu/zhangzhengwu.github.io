package dao.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

import util.DBManager;
import util.Util;
import dao.B_commissionDao;
import entity.B_Trainee;

public class B_commissionDaoImpl implements B_commissionDao{
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(GetStaffListDaoImpl.class);
	public int saveCommission(String filename, InputStream os, String username) {
		int num=0;
		int beginRowIndex =3;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("b_detail"); 
		Util.deltables("b_agent"); 
		Util.deltables("b_attendance"); 
		Util.deltables("b_Other"); 
	System.out.println("======================del=====================");
		try {
			
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
 
			HSSFSheet sheet = workbook.getSheet("明细");// 獲取頁數\
		 
			totalRows = sheet.getLastRowNum();// 获取总行数
			try{
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				 
				/**获取Excel里面的指定单元格数据**/
				HSSFCell insurance_companycell=row.getCell(0);
				HSSFCell policynocell=row.getCell(1);
				HSSFCell insurance_namecell=row.getCell(2);
				HSSFCell applicantcell=row.getCell(3);
				
				HSSFCell policytypecell=row.getCell(4);
				HSSFCell policyTeamcell=row.getCell(5);
				HSSFCell effectDatecell=row.getCell(6);
				HSSFCell totalcell=row.getCell(7); 
				HSSFCell FYBcell=row.getCell(8); 
				HSSFCell Bcell=row.getCell(9); 
				HSSFCell traineecell=row.getCell(10); 
				HSSFCell FYB_RYBcell=row.getCell(11); 
				HSSFCell FYB_totalcell=row.getCell(12); 
				HSSFCell FYB_monthcell=row.getCell(13); 
				HSSFCell handlerscell=row.getCell(14);  
				
				
				HSSFCell addresscell=row.getCell(15);
				HSSFCell handlers_positioncell=row.getCell(16);
				HSSFCell coachingcell=row.getCell(17);
				HSSFCell clientNamecell=row.getCell(18);
				HSSFCell submitDatecell=row.getCell(19);
				HSSFCell returnDatecell=row.getCell(20);
				HSSFCell policyStatuscell=row.getCell(21);
				HSSFCell remarkcell=row.getCell(22);
				HSSFCell agentcell=row.getCell(23);
				HSSFCell practicenocell=row.getCell(24);
				HSSFCell agent_idcardcell=row.getCell(25);
				HSSFCell agent_bankcell=row.getCell(26);
				HSSFCell FYCcell=row.getCell(27);
				HSSFCell Insurancenocell=row.getCell(28);
				HSSFCell bs_bartendercell=row.getCell(29);
				HSSFCell bartendercell=row.getCell(30);
				//HSSFCell Accepted_monthcell=row.getCell(31);
				//HSSFCell salary_monthcell=row.getCell(32);
				//HSSFCell commission_monthcell=row.getCell(33);
				HSSFCell first_sequelcell=row.getCell(34);
				HSSFCell applicant_yearcell=row.getCell(35);
				
				
			 
				
			 
				/**给数据库里面的字段赋值**/
				String insurance_company=Util.cellToString(insurance_companycell);
				String policyno=Util.cellToString(policynocell);
				String insurance_name=Util.cellToString(insurance_namecell);
				String applicant=Util.cellToString(applicantcell);
				
				String policytype=Util.cellToString(policytypecell);
				String policyTeam=Util.cellToString(policyTeamcell);
				String effectDate=Util.cellToString(effectDatecell);
				String total=Util.cellToString(totalcell); 
				String FYB=Util.cellToString(FYBcell); 
				String B=Util.cellToString(Bcell); 
				String trainee=Util.cellToString(traineecell); 
				String FYB_RYB=Util.cellToString(FYB_RYBcell); 
				String FYB_total=Util.cellToString(FYB_totalcell); 
				String FYB_month=Util.cellToString(FYB_monthcell); 
				String handlers= Util.cellToString(handlerscell);
				
				
				String address=Util.cellToString(addresscell); 
				String handlers_position=Util.cellToString(handlers_positioncell);
				String coaching=Util.cellToString(coachingcell); 
				String clientName=Util.cellToString(clientNamecell);
				String submitDate=Util.cellToString(submitDatecell); 
				String returnDate=Util.cellToString(returnDatecell);
				String policyStatus=Util.cellToString(policyStatuscell);
				String remark=Util.cellToString(remarkcell);
				String agent=Util.cellToString(agentcell); 
				String practiceno=Util.cellToString(practicenocell);
				String agent_idcard=Util.cellToString(agent_idcardcell);
				String agent_bank=Util.cellToString(agent_bankcell); 
				String FYC=Util.cellToString(FYCcell);
				String Insuranceno=Util.cellToString(Insurancenocell); 
				String bs_bartender=Util.cellToString(bs_bartendercell); 
				String bartender=Util.cellToString(bartendercell);
				String Accepted_month=""; 
				String salary_month="";
				String commission_month="";
				String first_sequel=Util.cellToString(first_sequelcell);
				String applicant_year=Util.cellToString(applicant_yearcell);
				
		 
				
				if(insurance_company.length()>0){
					sql = "insert b_detail (insurance_company,policyno,insurance_name,applicant,policytype,policyTeam,effectDate,total,FYB,B,trainee,FYB_RYB,FYB_total,FYB_month,handlers,address,handlers_position,coaching,clientName,submitDate,returnDate,policyStatus,remark,agent,practiceno,agent_idcard,agent_bank,FYC,Insuranceno,bs_bartender,bartender,Accepted_month,salary_month,commission_month,first_sequel,applicant_year) " +
							"values('"+insurance_company+"','"+policyno+"','"+insurance_name+"','"+applicant+"','"+ policytype+"','"+policyTeam+"','"+effectDate+"','"+total+"','"+FYB+"','"+B+"','"+trainee+"','"+FYB_RYB+"','"+FYB_total+"','"+FYB_month+"','"+handlers+"','"+address+"','"+handlers_position+"','"+coaching+"','"+clientName+"','"+submitDate+"','"+returnDate+"','"+policyStatus+"','"+remark+"','"+agent+"','"+practiceno+"','"+agent_idcard+"','"+agent_bank+"','"+FYC+"','"+Insuranceno+"','"+bs_bartender+"','"+bartender+"','"+Accepted_month+"','"+salary_month+"','"+commission_month+"','"+first_sequel+"','"+applicant_year+"');";
					ps = con.prepareStatement(sql);
		 
			 
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入b_detail成功！");
						System.out.println("插入b_detail成功!==="+i);
						num++;
					} else {
						logger.info("插入b_detail失敗");
					}
				}
			}
			}catch(Exception e){
				
			}finally{
				System.out.println("*********");
			}
			beginRowIndex =1;// 开始读取数据的行数
			
			sheet = workbook.getSheet("代理人");// 獲取頁數\
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				
				HSSFCell  countycell=row.getCell(0); 
				HSSFCell  businesscell=row.getCell(1); 
				HSSFCell  agengNamecell=row.getCell(2); 
				HSSFCell  business_agentcell=row.getCell(3); 
				HSSFCell  birthdaycell=row.getCell(4); 
				HSSFCell  agent_IDCardcell=row.getCell(5);
				
				HSSFCell  agentcodecell=row.getCell(6);
				HSSFCell  statuscell=row.getCell(7);
				HSSFCell  positioncell=row.getCell(8); 
				HSSFCell  effect_Datecell=row.getCell(9); 
				HSSFCell  mobiliecell=row.getCell(10); 
				HSSFCell  DateJoincell=row.getCell(11); 
				HSSFCell  recruitmentDatecell=row.getCell(12); 
				HSSFCell  helpcell=row.getCell(13); 
				HSSFCell  recommendedcell=row.getCell(14);  
				HSSFCell  Agent_certificatecell=row.getCell(15); 
				HSSFCell  Agent_datecell=row.getCell(16); 
				HSSFCell  effice_agentcell=row.getCell(17); 
				HSSFCell  practicenocell=row.getCell(18);  
				HSSFCell  bookcell=row.getCell(19); 
				HSSFCell  failureDatecell=row.getCell(20);  
				HSSFCell  bankcodecell=row.getCell(21);
				
				
				String  county=Util.cellToString(countycell);
				String  business=Util.cellToString(businesscell); 
				String  agengName=Util.cellToString(agengNamecell); 
				String  business_agent=Util.cellToString(business_agentcell); 
				String  birthday=Util.cellToString(birthdaycell); 
				String  agent_IDCard=Util.cellToString(agent_IDCardcell);
				
				
				String agentcode=Util.cellToString(agentcodecell);
				String status=Util.cellToString(statuscell); 
				String position=Util.cellToString(positioncell); 
				String effect_Date=Util.cellToString(effect_Datecell); 
				String mobilie=Util.cellToString(mobiliecell);
				String DateJoin=Util.cellToString(DateJoincell);
				String recruitmentDate=Util.cellToString(recruitmentDatecell);
				String help=Util.cellToString(helpcell); 
				String recommended=Util.cellToString(recommendedcell); 
				String Agent_certificate=Util.cellToString(Agent_certificatecell);
				String Agent_date=Util.cellToString(Agent_datecell); 
				String effice_agent=Util.cellToString(effice_agentcell);
				String practiceno=Util.cellToString(practicenocell); 
				String book=Util.cellToString(bookcell);
				String failureDate=Util.cellToString(failureDatecell); 
				String bankcode =Util.cellToString(bankcodecell);

				if(1>0){
					sql = "insert b_agent (county,business,agengName,business_agent,birthday,agent_IDCard,agentcode,status,position,effect_Date,mobilie,DateJoin,recruitmentDate,help,recommended,Agent_certificate,Agent_date,effice_agent,practiceno,book,failureDate,bankcode) " +
						 	"values('"+county+"','"+business+"','"+agengName+"','"+business_agent+"','"+birthday+"','"+agent_IDCard+"','"+agentcode+"','"+status+"','"+position+"','"+effect_Date+"','"+mobilie+"','"+DateJoin+"','"+recruitmentDate+"','"+help+"','"+recommended+"','"+Agent_certificate+"','"+Agent_date+"','"+effice_agent+"','"+practiceno+"','"+book+"','"+failureDate+"','"+bankcode+"');";
					ps = con.prepareStatement(sql);
		 
			 
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入b_agent成功！");
						System.out.println("插入b_agent成功==="+i);
						num++;
					} else {
						logger.info("插入b_agent失敗");
					}
				}
			}
			
			
			
			beginRowIndex =1;// 开始读取数据的行数
			
			sheet = workbook.getSheet("考勤表");// 獲取頁數\
			
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				HSSFCell departmentcell=row.getCell(0);
				HSSFCell namecell=row.getCell(1);
				HSSFCell countcell=row.getCell(2);
				
				String department=Util.cellToString(departmentcell);
				String name=Util.cellToString(namecell);
				String count=Util.cellToString(countcell);
				 
				
				if(1>0){
					sql = "insert b_attendance (department,name,count) " +
					"values('"+department+"','"+name+"','"+count+"');";
					ps = con.prepareStatement(sql);
					
					
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入b_attendance成功！");
						System.out.println("插入b_attendance成功==="+i);
						num++;
					} else {
						logger.info("插入b_attendance失敗");
					}
				}
			}
			

			beginRowIndex =1;// 开始读取数据的行数
			
			sheet = workbook.getSheet("其他津贴");// 獲取頁數\
			
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				HSSFCell addresscell=row.getCell(0);
				HSSFCell handlercell=row.getCell(1);
				HSSFCell sbcell=row.getCell(2);
				HSSFCell txcell=row.getCell(3);
			 
				 
				
				if(1>0){
					sql = "insert b_other (address,handle,sb,tx) " +
					"values('"+Util.cellToString(addresscell)+"','"+Util.cellToString(handlercell)+"','"+Util.cellToString(sbcell)+"','"+Util.cellToString(txcell)+"');";
					ps = con.prepareStatement(sql);
					
					
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入b_other成功！");
						System.out.println("插入b_other成功==="+i);
						num++;
					} else {
						logger.info("插入b_other失敗");
					}
				}
			}
			
			
			
			
			
			
			
			
			
			/**
			beginRowIndex =3;// 开始读取数据的行数
			
			sheet = workbook.getSheet("继续率");// 獲取頁數\
			
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行


				HSSFCell insurance_companycell=row.getCell(0);  
				HSSFCell policynocell=row.getCell(1);  
				HSSFCell insurance_namecell=row.getCell(2); 
				HSSFCell applicantcell=row.getCell(3); 
				HSSFCell policytypecell=row.getCell(4); 
				HSSFCell policyTeamcell=row.getCell(5); 
				HSSFCell effectDatecell=row.getCell(6); 
				HSSFCell Totalcell=row.getCell(7); 
				HSSFCell FYBcell=row.getCell(8);  
				HSSFCell Bcell=row.getCell(9);   
				HSSFCell traineecell=row.getCell(10);  
				HSSFCell FYB_RYBcell=row.getCell(11); 
				HSSFCell FYB_totalcell=row.getCell(12); 
				HSSFCell FYB_monthcell=row.getCell(13);  
				HSSFCell handlerscell=row.getCell(14);   
				HSSFCell addresscell=row.getCell(15);  
				HSSFCell handlers_positioncell=row.getCell(16); 
				HSSFCell helpcell=row.getCell(17); 
				HSSFCell clientNamecell=row.getCell(18); 
				HSSFCell submitDatecell=row.getCell(19); 
				HSSFCell returnDatecell=row.getCell(20); 
				HSSFCell policyStatuscell=row.getCell(21); 
				HSSFCell Remarkcell=row.getCell(22); 
				HSSFCell agent2cell=row.getCell(23); 
				HSSFCell certificatenocell=row.getCell(24); 
				HSSFCell agent_IdCardcell=row.getCell(25);  
				HSSFCell agent_bankcell=row.getCell(26); 
				HSSFCell FYCcell=row.getCell(27); 
				HSSFCell Insurancenocell=row.getCell(28); 
				HSSFCell bs_bartendercell=row.getCell(29); 
				HSSFCell bartendercell=row.getCell(30); 
				//HSSFCell Accepted_monthcell=row.getCell(31);  
				//HSSFCell salary_monthcell=row.getCell(32); 
				//HSSFCell commission_monthcell=row.getCell(33); 
				HSSFCell first_sequelcell=row.getCell(34);  
				HSSFCell applicant_yearcell=row.getCell(35);
				
				String insurance_company=Util.cellToString(insurance_companycell);
				String policyno=Util.cellToString(policynocell);
				String insurance_name=Util.cellToString(insurance_namecell); 
				String applicant=Util.cellToString(applicantcell); 
				String policytype=Util.cellToString(policytypecell); 
				String policyTeam=Util.cellToString(policyTeamcell); 
				String effectDate=Util.cellToString(effectDatecell); 
				String Total=Util.cellToString(Totalcell);  
				String FYB=Util.cellToString(FYBcell);  
				String B=Util.cellToString(Bcell);  
				String trainee=Util.cellToString(traineecell); 
				String FYB_RYB=Util.cellToString(FYB_RYBcell); 
				String FYB_total=Util.cellToString(FYB_totalcell); 
				String FYB_month=Util.cellToString(FYB_monthcell); 
				String handlers=Util.cellToString(handlerscell); 
				String address=Util.cellToString(addresscell); 
				String handlers_position=Util.cellToString(handlers_positioncell); 
				String help=Util.cellToString(helpcell);  
				String clientName=Util.cellToString(clientNamecell);
				String submitDate=Util.cellToString(submitDatecell); 
				String returnDate=Util.cellToString(returnDatecell); 
				String policyStatus=Util.cellToString(policyStatuscell); 
				String Remark=Util.cellToString(Remarkcell);
				String agent2=Util.cellToString(agent2cell); 
				String certificateno=Util.cellToString(certificatenocell); 
				String agent_IdCard=Util.cellToString(agent_IdCardcell);  
				String agent_bank=Util.cellToString(agent_bankcell);  
				String FYC=Util.cellToString(FYCcell); 
				String Insuranceno=Util.cellToString(Insurancenocell); 
				String bs_bartender=Util.cellToString(bs_bartendercell);
				String bartender=Util.cellToString(bartendercell); 
				String Accepted_month=""; 
				String salary_month=""; 
				String commission_month=""; 
				String first_sequel=Util.cellToString(first_sequelcell); 
				String applicant_year=Util.cellToString(applicant_yearcell);
				
				
				
				if(1>0){
					sql = "insert b_continue (insurance_company,policyno,insurance_name,applicant,policytype,policyTeam,effectDate,Total,FYB,B,trainee,FYB_RYB,FYB_total,FYB_month,handlers,address,handlers_position,help,clientName,submitDate,returnDate,policyStatus,Remark,agent2,certificateno,agent_IdCard,agent_bank,FYC,Insuranceno,bs_bartender,bartender,Accepted_month,salary_month,commission_month,first_sequel,applicant_year) " +
					"values('"+insurance_company+"','"+policyno+"','"+insurance_name+"','"+applicant+"','"+policytype+"','"+policyTeam+"','"+effectDate+"','"+Total+"','"+FYB+"','"+B+"','"+trainee+"','"+FYB_RYB+"','"+FYB_total+"','"+FYB_month+"','"+handlers+"','"+address+"','"+handlers_position+"','"+help+"','"+clientName+"','"+submitDate+"','"+returnDate+"','"+policyStatus+"','"+Remark+"','"+agent2+"','"+certificateno+"','"+agent_IdCard+"','"+agent_bank+"','"+FYC+"','"+Insuranceno+"','"+bs_bartender+"','"+bartender+"','"+Accepted_month+"','"+salary_month+"','"+commission_month+"','"+first_sequel+"','"+applicant_year+"');";
					ps = con.prepareStatement(sql);
					
					
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入b_continue成功！");
						System.out.println("插入b_continue成功==="+i);
						num++;
					} else {
						logger.info("插入b_continue失敗");
					}
				}
			}
			**/
			
		 
	
		}catch(NullPointerException e){
			//读取了没有数据的表格
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			return num;
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	
	}
	
	
	public void uploadagent(InputStream os){
		int num=0;
		int beginRowIndex =1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("b_agent"); 
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
		 
			HSSFSheet sheet = workbook.getSheet("代理人");// 獲取頁數\
		 
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				
				HSSFCell  agentcodecell=row.getCell(0);
				HSSFCell  statuscell=row.getCell(0);
				HSSFCell  positioncell=row.getCell(0); 
				HSSFCell  effect_Datecell=row.getCell(0); 
				HSSFCell  mobiliecell=row.getCell(0); 
				HSSFCell  DateJoincell=row.getCell(0); 
				HSSFCell  recruitmentDatecell=row.getCell(0); 
				HSSFCell  helpcell=row.getCell(0); 
				HSSFCell  recommendedcell=row.getCell(0);  
				HSSFCell  Agent_certificatecell=row.getCell(0); 
				HSSFCell  Agent_datecell=row.getCell(0); 
				HSSFCell  effice_agentcell=row.getCell(0); 
				HSSFCell  practicenocell=row.getCell(0);  
				HSSFCell  bookcell=row.getCell(0); 
				HSSFCell  failureDatecell=row.getCell(0);  
				HSSFCell  bankcodecell=row.getCell(0);
				

				String agentcode=Util.cellToString(agentcodecell);
				String status=Util.cellToString(statuscell); 
				String position=Util.cellToString(positioncell); 
				String effect_Date=Util.cellToString(effect_Datecell); 
				String mobilie=Util.cellToString(mobiliecell);
				String DateJoin=Util.cellToString(DateJoincell);
				String recruitmentDate=Util.cellToString(recruitmentDatecell);
				String help=Util.cellToString(helpcell); 
				String recommended=Util.cellToString(recommendedcell); 
				String Agent_certificate=Util.cellToString(Agent_certificatecell);
				String Agent_date=Util.cellToString(Agent_datecell); 
				String effice_agent=Util.cellToString(effice_agentcell);
				String practiceno=Util.cellToString(practicenocell); 
				String book=Util.cellToString(bookcell);
				String failureDate=Util.cellToString(failureDatecell); 
				String bankcode =Util.cellToString(bankcodecell);

				if(1>0){
					sql = "insert b_agent (agentcode,status,position,effect_Date,mobilie,DateJoin,recruitmentDate,help,recommended,Agent_certificate,Agent_date,effice_agent,practiceno,book,failureDate,bankcode) " +
						 	"values('"+agentcode+"','"+status+"','"+position+"','"+effect_Date+"','"+mobilie+"','"+DateJoin+"','"+recruitmentDate+"','"+help+"','"+recommended+"','"+Agent_certificate+"','"+Agent_date+"','"+effice_agent+"','"+practiceno+"','"+book+"','"+failureDate+"','"+bankcode+"');";
					ps = con.prepareStatement(sql);
		 
			 
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入b_agent成功！");
						System.out.println("插入b_agent成功！");
						num++;
					} else {
						logger.info("插入b_agent失敗");
					}
				}
			}
		}catch(NullPointerException e){
			//读取了没有数据的表格
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			 
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		 
	}


	public String cacular(OutputStream os,HSSFWorkbook wb,HSSFSheet sheet) {
		 StringBuffer errorString=new StringBuffer();
		 try{
			 Object[] objd=new Object[10];//声明row数组
			 Object[] objx=new Object[10];//声明cell数组
			StringBuffer dateString=new StringBuffer();
			int sheets=0;
			int rows=1;
			 con=DBManager.getCon();
			 sql="select * from five";
			 ps=con.prepareStatement(sql);
			 ResultSet rs=ps.executeQuery();
			 System.out.println("=========================五家人寿责任底薪===========================");
			 wb.setSheetName(sheets,"五家FYB");
			 HSSFRow row=sheet.createRow(0);
			 dateString.append("经手人 ,业务所属营业部 ,加总FYB_RYB,责任底薪");
			 objx=dateString.toString().split(",");
			 for(int j=0;j<objx.length;j++){
					cteateTitleCell(wb, row, (short)j, objx[j].toString());
					 sheet.setColumnWidth(j, 5000);  
					}
			 while(rs.next()){
				 	row=sheet.createRow(rows);
					 cteateCell(wb, row, (short)0, rs.getString("handlers"));
					 cteateCell(wb, row, (short)1, rs.getString("address"));
					 cteateNumberCell(wb, row, (short)2,rs.getString("FYB"));
					 cteateNumberCell(wb, row, (short)3, rs.getString("five"));
					 rows++;
			 }
			 rs.close();
			 rows=1;
			 HSSFSheet sheet2=wb.createSheet("首期FYB");
			 System.out.println();
			 dateString=new StringBuffer("");
			 sql="select * from first_fyb_father";
			 ps=con.prepareStatement(sql);
			 rs=ps.executeQuery();
			 System.out.println("=========================首期FYB  绩优奖励===========================");
			 row=sheet2.createRow(0);
			 dateString.append("经手人 ,业务所属营业部 ,加总FYB_RYB,绩优奖励");
			 objx=dateString.toString().split(",");
			 for(int j=0;j<objx.length;j++){
					cteateTitleCell(wb, row, (short)j, objx[j].toString());
					 sheet2.setColumnWidth(j, 5000);  
					}
			 while(rs.next()){
				 row=sheet2.createRow(rows);
				 cteateCell(wb, row, (short)0, rs.getString("handlers"));
				 cteateCell(wb, row, (short)1, rs.getString("address"));
				 cteateNumberCell(wb, row, (short)2, rs.getString("FYB"));
				 cteateNumberCell(wb, row, (short)3, rs.getString("FYB_money"));
				 rows++;
			 }
			 rs.close();
			 rows=1;
			 System.out.println();
			 System.out.println("===========================续期FYB============");
			 HSSFSheet sheet3=wb.createSheet("续期FYB");
			 dateString=new StringBuffer("");
			 sql="select * from continue_fyb";
			 ps=con.prepareStatement(sql);
			 rs=ps.executeQuery();
			 dateString.append("经手人 ,业务所属营业部 ,加总FYB_RYB");
			 row=sheet3.createRow(0);
			 objx=dateString.toString().split(",");
			 for(int j=0;j<objx.length;j++){
					cteateTitleCell(wb, row, (short)j, objx[j].toString());
					 sheet3.setColumnWidth(j, 5000);  
					}
			 while(rs.next()){
			 row=sheet3.createRow(rows);
			 cteateCell(wb, row, (short)0, rs.getString("handlers"));
			 cteateCell(wb, row, (short)1, rs.getString("address"));
			 cteateNumberCell(wb, row, (short)2, rs.getString("FYB"));
			 rows++;
			 }
			 rs.close();
			 rows=1;
		
			 System.out.println();
			 System.out.println("===========================育成津贴===============");
			 HSSFSheet sheet4=wb.createSheet("育成津贴");
			 dateString=new StringBuffer("");
			 sql="select * from bred";
			 ps=con.prepareStatement(sql);
			 rs=ps.executeQuery();
			 dateString.append("业绩所属营业处  ,合计 ");
			 row=sheet4.createRow(0);
			 objx=dateString.toString().split(",");
			 for(int j=0;j<objx.length;j++){
					cteateTitleCell(wb, row, (short)j, objx[j].toString());
					 sheet4.setColumnWidth(j, 5000);  
					}
			 while(rs.next()){
				 row=sheet4.createRow(rows);
				 cteateCell(wb, row, (short)0, rs.getString("address"));
				 cteateNumberCell(wb, row, (short)1, rs.getString("FYB"));
				 rows++;
			 }
			 rs.close();
			 rows=1;
			 
			
			 System.out.println();
			 System.out.println("=======================国华人寿===================");
			 HSSFSheet sheet5=wb.createSheet("国华人寿");
			 dateString=new StringBuffer("");
			 sql="select * from guohua";
			 ps=con.prepareStatement(sql);
			 rs=ps.executeQuery();
			 dateString.append("经手人  , 业务所属营业部  , 加总FYB_RYB");
			 row=sheet5.createRow(0);
			 objx=dateString.toString().split(",");
			 for(int j=0;j<objx.length;j++){
					cteateTitleCell(wb, row, (short)j, objx[j].toString());
					 sheet5.setColumnWidth(j, 5000);  
					}
			 while(rs.next()){
				 row=sheet5.createRow(rows);
				 cteateCell(wb, row, (short)0, rs.getString("handlers"));
				 cteateCell(wb, row, (short)1, rs.getString("address"));
				 cteateNumberCell(wb, row, (short)2, rs.getString("FYB"));
				 rows++;
			 }
			 rs.close();
			 rows=1;
		
			 
		 
			 System.out.println();
			 System.out.println("=======================特别津贴===================");
			 HSSFSheet sheet6=wb.createSheet("特别津贴");
			 dateString=new StringBuffer("");
			 sql="select * from special";
			 ps=con.prepareStatement(sql);
			 rs=ps.executeQuery();
			 dateString.append("经手人  ,  业务所属营业部 ,经手人职位 , 加总FYB_RYB ,特别津贴");
			 row=sheet6.createRow(0);
			 objx=dateString.toString().split(",");
			 for(int j=0;j<objx.length;j++){
					cteateTitleCell(wb, row, (short)j, objx[j].toString());
					 sheet6.setColumnWidth(j, 5000);  
					}
			 while(rs.next()){
				 row=sheet6.createRow(rows);
				 cteateCell(wb, row, (short)0, rs.getString("handlers"));
				 cteateCell(wb, row, (short)1, rs.getString("address"));
				 cteateCell(wb, row, (short)2, rs.getString("handlers_position"));
				 cteateNumberCell(wb, row, (short)3, rs.getString("FYB"));
				 cteateNumberCell(wb, row, (short)4, rs.getString("FYB_money"));
				 rows++;
			 }rs.close();rows=1;
			 
			 

	
			 
		 
			 System.out.println();
			 System.out.println("=======================辅导津贴===================");
			 HSSFSheet sheet7=wb.createSheet("辅导津贴");
			 dateString=new StringBuffer("");
			 List<String> positionList=new ArrayList<String>();
			 positionList.add(0,"见习");
			 positionList.add(1,"主任");
			 positionList.add(2,"经理");
			 positionList.add(3,"总监");
			 positionList.add(4,"北京碧升");
			 
		 
			 
			sql="select if(ag.agent_IDCard is null,a.agent_idcard,ag.agent_idcard) as agent_IDCard ,if(status is null,'离职',status)as status,Agent_certificate,practiceno,if(agengName is null,a.handlers,agengName)as handler,if(business is null,a.address,business) as addr,a.FYB,if(ag.position is null,a.handlers_position,ag.position)as position,if(help is null,'北京碧升',help)as help,if(recommended is null,'北京碧升',recommended)as recommended   from b_agent ag right join ("+
"  select CONCAT (address,' ',handlers) as handlers_address,handlers,address,round(sum(FYB_RYB),2) as FYB , handlers_position,agent_idcard from b_detail where first_sequel='首' and insurance_company!='国华人寿' group by handlers,address"+
" ) a on (ag.business_agent=a.handlers_address)";
			 
			 
			 ps=con.prepareStatement(sql);
			 rs=ps.executeQuery();	 
			 dateString.append("身份证号码,现职状态,代理证号码,执业证号,经手人,营业处,经手人职称,FYB,"); 
			 dateString.append("第一辅导人,发,扣,职级,第一推介人,发,职级,");
			 dateString.append("第二辅导人,发,扣,职级,第二推介人,发,职级,第三辅导人,发,扣,职级;");
			 String help="";
			 double pre=0;
			 String address="";
			 String handler="";
			 String handler_position="";
			 String help_position="";
			 String recommendedA="";
			 DecimalFormat df=new DecimalFormat("##.##");
			 while(rs.next()){
						 dateString.append(rs.getString("agent_IDCard")+","+rs.getString("status")+","+rs.getString("Agent_certificate")+","+rs.getString("practiceno")+","+rs.getString("handler")+","+rs.getString("addr")+","+rs.getString("position")+","+rs.getString("FYB"));
						 recommendedA=rs.getString("recommended");//第一推荐人
						 handler=rs.getString("handler");//经手人
						 handler_position=rs.getString("position");//经手人职称
						 StringBuffer extraString=new StringBuffer();//额外的第一辅导推荐
						 help=rs.getString("help");
						 address=rs.getString("addr");
						 System.out.println("辅导津贴计算开始");
						 System.out.println("经手人="+handler+"====营业部="+address+"====职称="+handler_position+"===辅导人="+help);
						 //	 System.out.println("判断经手人职称是否为见习或主任  1.主任以上北京碧升以下:跳过第一辅导人计算; 2.见习或主任: 分情况计算");
				 if(rs.getString("position").indexOf("见习")>-1 || rs.getString("position").indexOf("主任")>-1){//经手人为见习或主任
						 System.out.println("经手人职位为    见习或主任 ：分情况计算---->");
						 help_position=findposition(address+" "+help);//获取辅导人职位
						 String[] help_inf=new String[5];
					if(handler_position.indexOf("见习")>-1){//经手人为见习
							System.out.println("经手人为   见习---->判断辅导人职位");
						if(help_position.indexOf("见习")>-1){//第一辅导人为见习
							System.out.println("辅导人为    见习---->继续寻找辅导人的辅导人---->");
							//--------------------------------经手人为见习--辅导人为见习
							extraString.append(",,,,"+rs.getString("handler")+",,,"+rs.getString("FYB"));
							extraString.append(","+help);
							extraString.append(",,,"+findposition(address+" "+help));
							extraString.append(","+recommendedA+","+df.format((Double.parseDouble(rs.getString("FYB"))*0.015)));
							extraString.append(","+findposition(address+" "+rs.getString("recommended"))+",");
							//-------------------------------结束
									do{//直到辅导人职位为经理
											help_inf=findleader(address+" "+help);//获取辅导人信息
										if(Util.objIsNULL(help_inf[0])){//辅导人的辅导人为空
											help="北京碧升--"+help+"-找不到辅导人";//提示系统跳出该经手人链计算
											System.out.println("辅导人的辅导人为空!(有可能是营业部有问题 日志记录)-->");
											errorString.append("找不到辅导人,本条经手人链终止计算.");
										}else{//存在
											help=help_inf[3];//更新辅导人
											address=help_inf[1];//更新营业部
											recommendedA=help_inf[4];//更新推荐人
											help_position=help_inf[2];//更新辅导人职位 
											 if(compare(help_inf[2], "经理", positionList)){//经理及经理级别以上的
												System.out.println("辅导人的辅导人为    "+help_inf[2]+"  ---->");
												
												if(!help.equals("北京碧升")){
												//------------------------辅导人的辅导人为经理
													dateString.append(","+help);
												if(findattendance(help)>0){//每月有打卡  
													dateString.append(","+df.format((Double.parseDouble(rs.getString("FYB"))*0.12))+",");
												}else{//每月没有打卡
													dateString.append(",,"+df.format((Double.parseDouble(rs.getString("FYB"))*0.12)));
												}
												dateString.append(","+findposition(address+" "+help));//职位为空
												dateString.append(","+recommendedA+",");
												dateString.append(","+findposition(address+" "+recommendedA)+",");//第一推荐人职称
												//------------------------结束
												}
											}else{//辅导人的辅导人为主任、总监
													System.out.println("辅导人的辅导人为    "+help_inf[2]+"    ---->辅导人职位不在经理以上,继续查找---->");
													//-------------辅导人的辅导人为主任
													extraString.append(",,,,"+rs.getString("handler")+",,,"+rs.getString("FYB"));
													extraString.append(","+help);
													extraString.append(",,,"+findposition(address+" "+help));
													extraString.append(","+recommendedA+","+df.format((Double.parseDouble(rs.getString("FYB"))*0.015)));
													extraString.append(","+findposition(address+" "+recommendedA)+",");
													//-------------------------------结束
											}
										}
								}while(!compare(help_inf[2], "经理", positionList) && !Util.objIsNULL(help_inf[0])&& help.indexOf("北京碧升")<0 );
						}else if(compare(help_position, "经理", positionList)){//如果第一辅导人为经理
							System.out.println("辅导人为经理级别以上---->");
							if(help.indexOf("北京碧升")<0){//------------------------第一辅导人为经理
								dateString.append(","+help);
							if(findattendance(help)>0){//每月有打卡  
								dateString.append(","+df.format((Double.parseDouble(rs.getString("FYB"))*0.12))+",");
							}else{//每月没有打卡
								dateString.append(",,"+df.format((Double.parseDouble(rs.getString("FYB"))*0.12)));
							}
								dateString.append(","+findposition(address+" "+help));//职位为空
								dateString.append(","+recommendedA+","+df.format((Double.parseDouble(rs.getString("FYB"))*0.12)));
								dateString.append(","+findposition(address+" "+recommendedA)+",");//第一推荐人职称
							//------------------------结束
							}
						}else{//如果第一辅导人为主任
							//-------------辅导人的辅导人为主任
								extraString.append(",,,,"+rs.getString("handler")+",,,"+rs.getString("FYB"));
								extraString.append(","+help);
							if(findattendance(help)>0){//每月有打卡  
								extraString.append(","+df.format((Double.parseDouble(rs.getString("FYB"))*0.06))+",");
							}else{//每月没有打卡
								extraString.append(",,"+df.format((Double.parseDouble(rs.getString("FYB"))*0.06)));
							}
								extraString.append(","+findposition(address+" "+help));
								extraString.append(","+recommendedA+","+df.format((Double.parseDouble(rs.getString("FYB"))*0.06)));
								extraString.append(","+findposition(address+" "+recommendedA)+",");
							
							
							do{
									help_inf=findleader(address+" "+help);//获取辅导人信息
								if(Util.objIsNULL(help_inf[0])){////辅导人的辅导人为空
									help="北京碧升--"+help+"-找不到辅导人";//提示系统跳出该经手人链计算
									System.out.println("辅导人的辅导人为空!(有可能是营业部有问题 日志记录)---->");
									errorString.append("找不到辅导人,   "+handler+"   ==本条经手人链终止计算.\r\n");
								}else{//存在
									help=help_inf[3];//更新辅导人
									address=help_inf[1];//更新营业部
									recommendedA=help_inf[4];//更新推荐人
									help_position=help_inf[2];//更新辅导人职位 
									 if(compare(help_inf[2], "经理", positionList)){//辅导人的辅导人为经理
										 	System.out.println("辅导人的辅导人为    "+help_inf[2]+"    ---->");
										if(help.indexOf("北京碧升")<0){
										//------------------------第一辅导人为经理
											dateString.append(","+help);
										if(findattendance(help)>0){//每月有打卡  
											dateString.append(","+df.format((Double.parseDouble(rs.getString("FYB"))*0.12))+",");
										}else{//每月没有打卡
											dateString.append(",,"+df.format((Double.parseDouble(rs.getString("FYB"))*0.12)));
										}
											dateString.append(","+findposition(address+" "+help));//职位为空
											dateString.append(","+recommendedA+","+df.format((Double.parseDouble(rs.getString("FYB"))*0.12)));
											dateString.append(","+findposition(address+" "+recommendedA)+",");//第一推荐人职称
										//------------------------结束
										}
									}else{//辅导人的辅导人为主任
										System.out.println("辅导人的辅导人为    "+help_inf[2]+"    ，继续判断====(根据目前算法==此处暂不做处理)---->");
									}
								}
							}while(!compare(help_inf[2], "经理", positionList) && !Util.objIsNULL(help_inf[0])&& help.indexOf("北京碧升")<0 );
						}
					}else if(handler_position.indexOf("主任")>-1){//经手人为主任 
							System.out.println("经手人为"+handler_position+"判断辅导人职位---->");
						 if(compare(help_position, "经理", positionList)){//经手人的辅导人职称为经理
							System.out.println("经手人的辅导人职称为    "+help_position+"    ---->");
							if(help.indexOf("北京碧升")<0){
								//------------------------第一辅导人为经理
									dateString.append(","+help);
								if(findattendance(help)>0){//每月有打卡  
									dateString.append(","+df.format((Double.parseDouble(rs.getString("FYB"))*0.06))+",");
								}else{//每月没有打卡
									dateString.append(",,"+df.format((Double.parseDouble(rs.getString("FYB"))*0.06)));
								}
									dateString.append(","+findposition(address+" "+help));//职位为空
									dateString.append(","+recommendedA+","+df.format((Double.parseDouble(rs.getString("FYB"))*0.06)));
									dateString.append(","+findposition(address+" "+recommendedA)+",");//第一推荐人职称
								//------------------------结束
							}
							
						}else{//经手人的辅导人职称为主任
									System.out.println("辅导人职称为    "+help_position+"    ---->");
									errorString.append("出现同级主任："+rs.getString("handler")+"==职位=="+rs.getString("position")+"==辅导人=="+help+"==职位=="+findposition(address+" "+help)+"==推荐人=="+recommendedA+"==职位=="+findposition(address+" "+recommendedA)+";\r\n");
							do{
									help_inf=findleader(address+" "+help);//获取辅导人信息
								if(Util.objIsNULL(help_inf[0])){////辅导人的辅导人为空
									help="北京碧升--"+help+"-找不到辅导人";//提示系统跳出该经手人链计算
									System.out.println("辅导人的辅导人为空!(有可能是营业部有问题 日志记录)---->");
									errorString.append("找不到辅导人,    "+handler+"    本条经手人链终止计算.");
								}else{
									System.out.println("辅导人==  "+help_inf[0]+"---"+help_inf[1]+"--"+help_inf[2]);
									help=help_inf[3];//更新辅导人
									address=help_inf[1];//更新营业部
									recommendedA=help_inf[4];//更新推荐人
									help_position=help_inf[2];//更新辅导人职位 
								
									if(compare(help_inf[2], "经理", positionList)){//辅导人的辅导人为经理
										System.out.println("辅导人的辅导人为    "+help_inf[2]+"    ---->");
										if(help.indexOf("北京碧升")<0){
										//------------------------第一辅导人为经理
											dateString.append(","+help_inf[0]);
										if(findattendance(help_inf[0])>0){//每月有打卡  
											dateString.append(","+df.format((Double.parseDouble(rs.getString("FYB"))*0.09))+",");
										}else{//每月没有打卡
											dateString.append(",,"+df.format((Double.parseDouble(rs.getString("FYB"))*0.09)));
										}
											dateString.append(","+findposition(address+" "+help_inf[0]));//职位为空
											dateString.append(","+recommendedA+",,,");
										//------------------------结束
											help=help_inf[0];//由于在第二辅导人那里会再次查找辅导人所以现将辅导人降一级
										}
									}else{//辅导人的辅导人为主任、总监
										System.out.println("辅导人的职位为    "+help_inf[2]+"    ，继续判断(根据目前算法==此处暂不做处理)---->");
									}
								}
								//System.out.println(help_inf[2].indexOf("经理") +"??????---?????"+Util.objIsNULL(help_inf[0]));
							}while(!Util.objIsNULL(help_inf[0]) && !compare(help_inf[2], "经理", positionList) && help.indexOf("北京碧升")<0 );

						}
					}
					System.out.println("第一辅导推荐人计算结束--------------->!");
					if(help.indexOf("北京碧升")>-1){
						dateString.append(","+help+",,,,北京碧升,,,");
					}
				
				
			}else{//经手人不是见习也不是主任,第一辅导第一推荐为空
				System.out.println(" 经手人职位在 主任以上北京碧升以下 :跳过第一辅导人计算");
				dateString.append(",,,,,,,,");
			}
				 
				 //计算第二辅导人
				 
				 String recommended=rs.getString("recommended"); 
			if(help.indexOf("北京碧升")>-1){//第一辅导人为北京碧升 停止该经手人的计算
				if(rs.getString("position").indexOf("见习")<0 && rs.getString("position").indexOf("主任")<0){
					dateString.append("北京碧升,,,,");
					if(recommended.equals("北京碧升")){
						dateString.append("北京碧升,,,,,,");
					}else{
						pre=0.015;//第二推荐人
						 dateString.append(recommended+","+df.format((Double.parseDouble(rs.getString("FYB"))*pre)));
						 dateString.append(","+findposition(address+" "+recommended)+",");
					}
				}
			}else{//继续计算
				System.out.println("继续开始计算第二辅导人"+help+"--"+address);
				if(rs.getString("position").indexOf("见习")>-1 || rs.getString("position").indexOf("主任")>-1){
					recommended=findrecommended(address+" "+help);
					help=findleader(address+" "+help)[3];
					address=findleader(address+" "+rs.getString("help"))[1];
				}
				 if(Util.objIsNULL(help)){//第二辅导人为空     -------营业部对不上
					 dateString.append("北京碧升,,,,");
					 if(recommended.equals("北京碧升")){
							dateString.append("北京碧升,,,,,,");
						}else{
							pre=0.015;//第二推荐人
							 dateString.append(recommended+","+df.format((Double.parseDouble(rs.getString("FYB"))*pre)));
							 dateString.append(","+findposition(address+" "+recommended)+",,,,");
							 
						} 
				 }else if(help.equals("北京碧升")){
					 dateString.append("北京碧升,,,,");
					 if(recommended.equals("北京碧升")){
							dateString.append("北京碧升,,,,,,");
						}else{
							pre=0.015;//第二推荐人
							 dateString.append(recommended+","+df.format((Double.parseDouble(rs.getString("FYB"))*pre)));
							 dateString.append(","+findposition(address+" "+recommended)+",,,,");
							 
						} 
				 }else{//第二辅导人不是北京碧升
					 pre=0.035;//第二辅导人
					 dateString.append(help);
					 if(findattendance(help)>0){//每月有打卡  
							dateString.append(","+df.format((Double.parseDouble(rs.getString("FYB"))*pre))+",");
						}else{//每月没有打卡
							dateString.append(",,"+df.format((Double.parseDouble(rs.getString("FYB"))*pre)));
						}
					 if(Util.objIsNULL(help)){
						 errorString.append("未找到：("+rs.getString("help")+"=="+rs.getString("addr")+")第二辅导人\r\n");
					 }
					 dateString.append(","+findposition(address+" "+help));//职位为空
					 pre=0.015;//第二推荐人
					 if(recommended.equals("北京碧升")){
						 dateString.append(","+recommended+",,,");
					 }else{
						 dateString.append(","+recommended+","+df.format((Double.parseDouble(rs.getString("FYB"))*pre))); 
						 if(Util.objIsNULL(recommended)){
							 errorString.append("未找到：("+rs.getString("help")+"=="+rs.getString("addr")+")第二推荐人\r\n");
						 }
						 dateString.append(","+findposition(address+" "+recommended)+",");
					 }
					
					 
					 
					 
					 //判断第三辅导人
					// recommended=findrecommended(address+" "+help);
				 help=findleader(address+" "+help)[3];
				 address=findleader(address+" "+rs.getString("help"))[1];
				 if(Util.objIsNULL(help)){
					dateString.append("北京碧升-----该员工已离职,,,"); 
				 }else if(help.equals("北京碧升")){//第三辅导人为北京碧升
						 dateString.append("北京碧升,,,");
					 }else{//第三辅导人不是北京碧升
						 pre=0.02;//第三辅导人
						 dateString.append(help);
						 if(findattendance(help)>0){//每月有打卡  
								dateString.append(","+df.format((Double.parseDouble(rs.getString("FYB"))*pre))+",");
							}else{//每月没有打卡
								dateString.append(",,"+df.format((Double.parseDouble(rs.getString("FYB"))*pre)));
							}
						 if(Util.objIsNULL(help)){
							 errorString.append("未找到：("+rs.getString("help")+"=="+rs.getString("addr")+")第三辅导人\r\n");
						 }
						 dateString.append(","+findposition(address+" "+help));//职位为空
						 
					 }
					 
					 
				 }
			}
			
			
			dateString.append(";");
			if(!Util.objIsNULL(extraString.toString())){
				dateString.append(extraString.toString()+";");
			}
			System.out.println();
			 }rs.close();
			 
			 objd=dateString.toString().split(";");
			 StringBuffer help_manager=new StringBuffer("");
			 int adnum=0;
			 String[] liwai=null;
			if(!Util.objIsNULL(errorString.toString())){
				liwai=errorString.toString().substring(0, errorString.toString().lastIndexOf(";")).replaceAll("出现同级主任：","").split(";");
			}else{
				 
			}
			for(int i=0;i<objd.length;i++){
				 String[] vals=new String[6];
					 row=sheet7.createRow(i+adnum);
				
					objx=objd[i].toString().split(",");
					System.out.println(objd[i].toString());
					for(int j=0;j<objx.length;j++){
						if(i==0){
							 sheet7.setColumnWidth(j,30*200);
						cteateTitleCell(wb, row, (short)j, objx[j].toString());
						}else{
							cteateCell(wb, row, (short)j, objx[j].toString());
						}
					
					}
				
				
						
					
					
					
					
					
					
					
						if(i!=0){
							if(objx.length>11)
								help_manager.append("'"+objx[8].toString()+"','help','"+objx[9].toString()+"','"+objx[11].toString()+"';");
							if(objx.length>14)
								help_manager.append("'"+objx[12].toString()+"','recommended','"+objx[13].toString()+"','"+objx[14].toString()+"';");
							if(objx.length>18)
								help_manager.append("'"+objx[15].toString()+"','help2','"+objx[16].toString()+"','"+objx[18].toString()+"';");
							if(objx.length>21)
								help_manager.append("'"+objx[19].toString()+"','recommended2','"+objx[20].toString()+"','"+objx[21].toString()+"';");
							if(objx.length>25)
								help_manager.append("'"+objx[22].toString()+"','help3','"+objx[23].toString()+"','"+objx[25].toString()+"';");
						}
						
						/***********************2013年9月27日15:08:57 添加**************************/
						for(int p=0;p<liwai.length;p++){//遍历同级主任----经手人为见习或主任
							if(i==0){
								break;
							}
							String[] temp=liwai[p].toString().split("==");
					
							if(temp[0].trim().equals(objx[4].toString().trim()) && temp[2].trim().equals(objx[6].toString().trim())){
								//System.out.println("找到未计算的的数据===="+objx[4].toString()+"--"+temp[2]);
								vals[0]=temp[4];//第一辅导人
								vals[1]=df.format(Double.parseDouble(objx[7].toString())*0.015);//管理津贴
								vals[2]=temp[6];//职位
								vals[3]=temp[8];//推荐人
								vals[4]=df.format(Double.parseDouble(objx[7].toString())*0.015);//推荐津贴
								if(temp.length>10)
									vals[5]=temp[10];//职位
								else
									vals[5]="北京碧升";
								adnum++;
								row=sheet7.createRow(i+adnum);
								HSSFRow row2=sheet7.getRow(i+adnum-1);
								
								/*
								 * 由于2013-10-21号发现计算错误故更正算法
								 * cteateRedCell(wb, row, (short)8, vals[0]);//第一辅导人
								if(findattendance(vals[0])>0)//是否打卡
									cteateCell(wb, row, (short)9, vals[1]);
								else
									cteateRedCell(wb, row, (short)10, vals[1]);*/
								cteateCell(wb, row2, (short)12,  vals[0]);
								cteateCell(wb, row2, (short)13,  vals[1]);
								cteateCell(wb, row2, (short)14,  vals[2]);
								
								
								
								/*****end***/
								
							//	cteateRedCell(wb, row, (short)11, vals[2]);//职位
								cteateRedCell(wb, row, (short)12, vals[3]);//第一推荐人
								cteateRedCell(wb, row, (short)13,vals[4]);//佣金
								cteateRedCell(wb, row, (short)14,vals[5]);//位置
								
								help_manager.append("'"+vals[0]+"','recommended',"+vals[1]+"','"+vals[2]+"';");//从辅导改为推荐人
								help_manager.append("'"+vals[3]+"','recommended',"+vals[4]+"','"+vals[5]+"';");
								
							 
							}else{
								 
							}
						
						}
						
						
					}
			
	
			 /**
			  * 保存辅导人推荐人信息
			  */
			System.out.println("--------------------------\r\n"+help_manager);
			 objd=help_manager.toString().split(";");
			 PreparedStatement ps=con.prepareStatement("delete from b_helpmanager;");
			 ps.executeUpdate();
			 con.setAutoCommit(false);
			 String sql="insert into b_helpmanager values(?,?,?,?);";
			 ps=con.prepareStatement(sql);
			 for(int i=0;i<objd.length;i++){
					objx=objd[i].toString().split(",");
					if(!Util.objIsNULL(objx[2].toString().replaceAll("'", ""))){
						if(!Util.objIsNULL(objx[0])&&!Util.objIsNULL(objx[2])){
							ps.setString(1, objx[0].toString().replaceAll("'", ""));
							ps.setString(2, objx[1].toString().replaceAll("'", ""));
							ps.setString(3, objx[2].toString().replaceAll("'", ""));
							ps.setString(4, objx[3].toString().replaceAll("'", ""));
							ps.addBatch();
						}
					}
			 }
		
		
			 ps.executeBatch();
			 con.commit();
			 
			 System.out.println();
			 System.out.println("=======================发拥稽核===================");
			 HSSFSheet sheet8=wb.createSheet("发拥稽核");
			 String Commission="身份证号码,在职状态,职称,辅导人,推荐人,代理证号码,执业证号,营业区,代理人姓名,FYB,国华佣金,除国华畅行无忧，其它首年佣金,续年佣金,团险,特别津贴,创业津贴,绩优奖励,责任底薪,社保津贴,辅导津贴,推介津贴1,推介津贴2,管理津贴2,管理津贴3,育成津贴,其他加扣,应发金额,所得额,个税,实发金额,备注,银行账号";
			 objx=Commission.split(",");
			 row=sheet8.createRow(0);
			 for(int j=0;j<objx.length;j++){//添加Excel首行
						 sheet8.setColumnWidth(j,30*200);
						 cteateTitleCell(wb, row, (short)j, objx[j].toString());
					}
			 /******************添加详细信息*********************/
			 Connection con3=DBManager.getCon();
			 sql="select * from commission";
			 ps=con3.prepareStatement(sql);
			 rs=ps.executeQuery();
			 int downNum=1;
			 List<B_Trainee> list=findB_Trainee();
			 while(rs.next()){
				 
				 
				 String helps=rs.getString("handler");
				 String bankcode=rs.getString("bankcode");
				 String position=rs.getString("position");
				 Double FYB3=Double.parseDouble(rs.getString("FYB3"));
				 Double FYB2=Double.parseDouble(Util.objIsNULL(rs.getString("FYB2"))?"0":rs.getString("FYB2"));
				 
				 row=sheet8.createRow(downNum);
				 for(int i=1;i<20;i++){
					 cteateCommissionCell(wb, row, (short)(i-1), Util.objIsNULL(rs.getString(i))?"":rs.getString(i));
				 }
				 cteateCommissionCell(wb, row, (short)19,Util.objIsNULL(findBYCommission(helps,position,"help"))?"": findBYCommission(helps,position,"help"));//辅导津贴
				 cteateCommissionCell(wb, row, (short)20,Util.objIsNULL(findBYCommission(helps,position,"recommended"))?"":findBYCommission(helps,position,"recommended") );//推荐津贴1
				 cteateCommissionCell(wb, row, (short)21,Util.objIsNULL(findBYCommission(helps,position,"recommended2"))?"":findBYCommission(helps,position,"recommended2") );//推荐津贴2
				 cteateCommissionCell(wb, row, (short)22,Util.objIsNULL(findBYCommission(helps,position,"help2"))?"":findBYCommission(helps,position,"help2") );//管理津贴2
				 cteateCommissionCell(wb, row, (short)23,Util.objIsNULL(findBYCommission(helps,position,"help3"))?"": findBYCommission(helps,position,"help3"));//管理津贴3
				
				 if(!Util.objIsNULL(helps)){
					if(helps.equals("刘兰芳")){
						cteateCommissionCell(wb, row, (short)24,findBYBred("嘉茂","风云","西城"));//育成津贴
					}else if(helps.equals("张琪")){
						cteateCommissionCell(wb, row, (short)24,findBYBred("嘉茂"));//育成津贴
					}
				}
				 if(!rs.getString("first_sequel").equals("首")){//如果是续期   FYB为空
					 cteateCommissionCell(wb, row, (short)9,"");//FYB
					// cteateCommissionCell(wb, row, (short)10,"");//国华
					 cteateCommissionCell(wb, row, (short)11,"");//除国华首年
				 }
				 cteateCommissionCell(wb, row, (short)25, "");//其他加扣
				 cteateMulaCell(wb, row, (short)26,"SUM(K"+(downNum+1)+":Z"+(downNum+1)+")" );//应发金额
				 cteateMulaCell(wb, row, (short)27, "IF(AA"+(downNum+1)+">6666.67,ROUND(AA"+(downNum+1)+"*60%*0.8,2),MAX(0,ROUND(AA"+(downNum+1)+"*60%-800,2)))");//所得额
				 cteateMulaCell(wb, row, (short)28, "ROUND(AB"+(downNum+1)+"*0.2,2)");//个税
				 cteateMulaCell(wb, row, (short)29, "AA"+(downNum+1)+"-AC"+(downNum+1));//实发金额
				 cteateCell(wb, row, (short)30, "");//备注
				 
				 
				 
				 
				 
				 cteateCell(wb, row, (short)31, Util.objIsNULL(bankcode)?"":bankcode);//银行账号
				 
				 for(int j=0;j<list.size();j++){//判断是否存在分税人
					 B_Trainee b=list.get(j);
					 if(b.getAddr_handlers().equals(rs.getString("addr")+" "+rs.getString("handler"))){
						 //更改用户畅行无忧佣金
						 Double FYB_total;
						 if(b.getType().equals("国")){
							 FYB_total=b.getTotal()*0.4;
							 cteateCommissionCell(wb, row, (short)10, FYB2-FYB_total+""); 
							 FYB2-=FYB_total;
						 }else{
							 FYB_total=b.getFYB()*0.4;
							 cteateCommissionCell(wb, row, (short)11, FYB3-FYB_total+""); 
							 FYB3-=FYB_total;
						 }
						 
					
						 downNum++;
						 //新建分税人
						HSSFRow row2=sheet8.createRow(downNum);
						 cteateRedCell(wb, row2, (short)7,b.getAddress());
						 cteateRedCell(wb, row2, (short)8,b.getTrainee());
						 if(b.getType().equals("国")){
							 cteateCommissionCell(wb, row2, (short)10,FYB_total+"");
							 
						 }else{
							 cteateCommissionCell(wb, row2, (short)11,FYB_total+"");
							 
						 }
						 cteateMulaCell(wb, row2, (short)26,"SUM(K"+(downNum+1)+":Z"+(downNum+1)+")");//应发金额
						 cteateMulaCell(wb, row2, (short)27, "IF(AA"+(downNum+1)+">6666.67,ROUND(AA"+(downNum+1)+"*60%*0.8,2),MAX(0,ROUND(AA"+(downNum+1)+"*60%-800,2)))");//所得额
						 cteateMulaCell(wb, row2, (short)28, "ROUND(AB"+(downNum+1)+"*0.2,2)");//个税
						 cteateMulaCell(wb, row2, (short)29, "AA"+(downNum+1)+"-AC"+(downNum+1));//实发金额
						 cteateRedCell(wb, row2, (short)30, helps+"分税人");//备注
						 //break;
					 }
				 }
				 downNum++;
				 
				 
			 }rs.close();
		 
			 DBManager.closeCon(con3);
			 System.out.println("Excel 导出完毕!");
			 wb.write(os);
			
		
		 }catch(Exception e){
			 e.printStackTrace();
			 errorString.append("保存辅导人推荐人出错!\r\n");
		 }finally{
			 DBManager.closeCon(con);
			
			
		 }
		return errorString.toString();
	}
	/**
	 * 获取员工的 辅导或推荐酬金
	 * @param handler
	 * @param position
	 * @param type
	 * @return
	 */
	public String findBYCommission(String handler,String position,String type){
		String FY="";
		Connection con2=null;
		try{
			con2=DBManager.getCon();
			sql="select sum(FYB)as FYBs from help_rememmded  where handlers=? and position=? and type=? group by concat(handlers,'_',type)";
			//System.out.println(sql+"==="+handler+"--"+position+"--"+type);
			ps=con2.prepareStatement(sql);
			ps.setString(1, handler);
			ps.setString(2, position);
			ps.setString(3, type);
			ResultSet rss=ps.executeQuery();
			if(rss.next()){
				FY=rss.getString("FYBs");
			}
			
		}catch(Exception e){
			System.out.println("err");
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con2);
		}
		return FY;
	}
	/**
	 *张琪的 育成津贴
	 * @param addre
	 * @return
	 */
	public String findBYBred(String addre){
		double FY=0;
		Connection con2=null;
		try{
			con2=DBManager.getCon();
			sql="select FYB from bred  where address=?";
			ps=con2.prepareStatement(sql);
			ps.setString(1, addre);
			ResultSet rss=ps.executeQuery();
			if(rss.next()){
				FY=(rss.getDouble("FYB")*0.02);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con2);
		}
		return new DecimalFormat(".##").format(FY);
	}
	
	/**
	 * 刘兰芳的育成津贴
	 * @param addre1
	 * @param addre2
	 * @param addre3
	 * @return
	 */
	public String findBYBred(String addre1,String addre2,String addre3){
		Double FY=0d;
		Connection con2=null;
		try{
			con2=DBManager.getCon();
			sql="select address,FYB from bred  where address=? or address=? or address=?";
			ps=con2.prepareStatement(sql);
			ps.setString(1, addre1);
			ps.setString(2, addre2);
			ps.setString(3, addre3);
			ResultSet rss=ps.executeQuery();
			while(rss.next()){
				if(rss.getString("address").equals("嘉茂"))
					FY+=rss.getDouble("FYB")*0.01;
				else if(rss.getString("address").equals("风云"))
					FY+=rss.getDouble("FYB")*0.02;
				else if(rss.getString("address").equals("西城"))
					FY+=rss.getDouble("FYB")*0.02;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return new DecimalFormat(".##").format(FY);
	}
	/**
	 * 分税人
	 * @return
	 */
	public List<B_Trainee> findB_Trainee(){
		List<B_Trainee> list=new ArrayList<B_Trainee>();
		Connection con2=null;
		try{
			con2=DBManager.getCon();
			String sql2="select * from taxPeople";
			ps=con2.prepareStatement(sql2);
			ResultSet rs2=ps.executeQuery();
			while(rs2.next()){
				list.add(new B_Trainee(rs2.getString("trainee"),rs2.getString("address"),rs2.getString("handlers"),rs2.getString("handlers_position"),rs2.getDouble("FYB"),rs2.getDouble("FYB_total"),rs2.getString("type"),rs2.getString("address")+" "+rs2.getString("handlers")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con2);
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		String a="'','help','','';'','recommended','','';'北京碧升','help2','','';'卢志宏','help','','碧升经理';'卢志宏','recommended','159.1','碧升经理';'曹勇兵','help2','','碧升经理';'冯叶辉','recommended2','39.78','';'高振兴','help3','','碧升经理';'','help','','';'','recommended','','';'白凤强','help2','63.89','碧升内勤';'白凤强','recommended2','27.38','碧升内勤';'','help','','';'','recommended','','';'北京碧升','help2','','';'','help','','';'','recommended','','';'潘桂宾','help2','','碧升经理';'潘桂宾','recommended2','3.63','碧升经理';'张琪','help3','4.84','碧升业务部总监';'','help','','';'','recommended','','';'张琪','help2','471.12','碧升业务部总监';'张琪','recommended2','201.91','碧升业务部总监';'刘兰芳','help3','269.21','碧升业务部总监';'','help','','';'','recommended','','';'曹勇兵','help2','','碧升经理';'冯叶辉','recommended2','23.62','';'高振兴','help3','','碧升经理';'','help','','';'','recommended','','';'张琪','help2','123.04','碧升业务部总监';'张琪','recommended2','52.73','碧升业务部总监';'刘兰芳','help3','70.31','碧升业务部总监';'陈向辉','help','','碧升经理';'陈向辉','recommended','108.43','碧升经理';'史素萍','help2','63.25','碧升业务部总监';'刘兰芳','recommended2','27.11','碧升业务部总监';'张琪','help3','36.14','碧升业务部总监';'','help','','';'','recommended','','';'刘兰芳','help2','608.1','碧升业务部总监';'张小艳','recommended2','260.62','碧升业务部经理';'','help','','';'','recommended','','';'北京碧升','help2','','';'刘玉霞','help','1372.8','碧升营销部总监';'刘玉霞','recommended','1372.8','碧升营销部总监';'潘桂宾','help2','','碧升经理';'潘桂宾','recommended2','343.2','碧升经理';'张琪','help3','457.6','碧升业务部总监';'','help','','';'','recommended','','';'黄晓曼','help2','278.41','碧升业务部总监';'黄晓曼','recommended2','119.32','碧升业务部总监';'杨文霞','help3','','碧升营销部总监';'','help','','';'','recommended','','';'史素萍','help2','429.43','碧升业务部总监';'史素萍','recommended2','184.04','碧升业务部总监';'张琪','help3','245.39','碧升业务部总监';'','help','','';'','recommended','','';'刘玉霞','help2','13.44','碧升营销部总监';'陈金萍','recommended2','5.76','碧升见习主管';'潘桂宾','help3','','碧升经理';'','help','','';'','recommended','','';'丛正春','help2','','碧升经理';'丛正春','recommended2','2.25','碧升经理';'刘磊','help3','','碧升经理';'陈向辉','help','','碧升经理';'陈向辉','recommended','1314.51','碧升经理';'史素萍','help2','383.4','碧升业务部总监';'刘兰芳','recommended2','164.31','碧升业务部总监';'张琪','help3','219.08','碧升业务部总监';'黄晓曼','help','214.03','碧升业务部总监';'董若琳','recommended','214.03','碧升内勤';'杨文霞','help2','','碧升营销部总监';'杨文霞','recommended2','53.51','碧升营销部总监';'史素萍','help','63.65','碧升业务部总监';'史素萍','recommended','63.65','碧升业务部总监';'张琪','help2','37.13','碧升业务部总监';'张琪','recommended2','15.91','碧升业务部总监';'刘兰芳','help3','21.22','碧升业务部总监';'','help','','';'','recommended','','';'北京碧升','help2','','';'高振兴','help','','碧升经理';'高振兴','recommended','37.8','碧升经理';'刘玉霞','help2','22.05','碧升营销部总监';'刘玉霞','recommended2','9.45','碧升营销部总监';'潘桂宾','help3','','碧升经理';'','help','','';'','recommended','','';'刘磊','help2','','碧升经理';'刘磊','recommended2','161.19','碧升经理';'张琪','help3','214.92','碧升业务部总监';'','help','','';'','recommended','','';'张爱华','help2','','碧升经理';'张爱华','recommended2','34.71','碧升经理';'张琪','help3','46.28','碧升业务部总监';'','help','','';'','recommended','','';'苏凤芝','help2','','碧升经理';'苏凤芝','recommended2','11.1','碧升经理';'','help','','';'','recommended','','';'史素萍','help2','60.58','碧升业务部总监';'史素萍','recommended2','25.96','碧升业务部总监';'张琪','help3','34.62','碧升业务部总监';'','help','','';'','recommended','','';'白凤强','help2','332.33','碧升内勤';'白凤强','recommended2','142.43','碧升内勤';'杨文霞','help','','碧升营销部总监';'高旭','recommended','202.78','';'刘兰芳','help2','59.14','碧升业务部总监';'刘兰芳','recommended2','25.35','碧升业务部总监';'','help','','';'','recommended','','';'北京碧升','help2','','';'刘玉霞','help','522.17','碧升营销部总监';'刘玉霞','recommended','522.17','碧升营销部总监';'潘桂宾','help2','','碧升经理';'潘桂宾','recommended2','130.54','碧升经理';'张琪','help3','174.06','碧升业务部总监';'高振兴','help','','碧升经理';'高振兴','recommended','71.33','碧升经理';'刘玉霞','help2','41.61','碧升营销部总监';'刘玉霞','recommended2','17.83','碧升营销部总监';'潘桂宾','help3','','碧升经理';'','help','','';'','recommended','','';'北京碧升','help2','','';'','help','','';'','recommended','','';'张建','help2','','碧升经理';'张建','recommended2','155.26','碧升经理';'张琪','help3','207.01','碧升业务部总监';'史素萍','help','149.94','碧升业务部总监';'史素萍','recommended','149.94','碧升业务部总监';'张琪','help2','87.47','碧升业务部总监';'张琪','recommended2','37.49','碧升业务部总监';'刘兰芳','help3','49.98','碧升业务部总监';'','help','','';'','recommended','','';'侯世平','help2','','';'侯世平','recommended2','91.55','';'潘桂宾','help','','碧升经理';'张琪','recommended','','';'张琪','help2','196','碧升业务部总监';'张琪','recommended2','84','碧升业务部总监';'刘兰芳','help3','112','碧升业务部总监';'王悠','recommended',84','碧升主任';'王悠','recommended',84','碧升主任';'','help','','';'','recommended','','';'张琪','help2','47.13','碧升业务部总监';'张琪','recommended2','20.2','碧升业务部总监';'刘兰芳','help3','26.93','碧升业务部总监';'','help','','';'','recommended','','';'北京碧升','help2','','';'徐华','help','165.89','碧升经理';'徐华','recommended','165.89','碧升经理';'刘磊','help2','','碧升经理';'刘磊','recommended2','41.47','碧升经理';'张琪','help3','55.3','碧升业务部总监';'','help','','';'','recommended','','';'北京碧升','help2','','';'','help','','';'','recommended','','';'刘兰芳','help2','98.92','碧升业务部总监';'张小艳','recommended2','42.39','碧升业务部经理';'张琪','help','3.36','碧升业务部总监';'张琪','recommended','3.36','碧升业务部总监';'刘兰芳','help2','1.96','碧升业务部总监';'刘兰芳','recommended2','0.84','碧升业务部总监';'李福国','help','96.31','碧升营销部总监';'李福国','recommended','96.31','碧升营销部总监';'北京碧升','help2','','';'刘国会','recommended2','24.08','碧升经理';'','help','','';'','recommended','','';'刘兰芳','help2','99.54','碧升业务部总监';'刘兰芳','recommended2','42.66','碧升业务部总监';'','help','','';'','recommended','','';'刘兰芳','help2','126.17','碧升业务部总监';'刘兰芳','recommended2','54.07','碧升业务部总监';'王静','help','','碧升经理';'王静','recommended','343.02','碧升经理';'蔺秋实','help2','','碧升经理';'蔺秋实','recommended2','42.88','碧升经理';'徐庆华','help3','','碧升经理';'','help','','';'','recommended','','';'杜平','help2','7.83','碧升经理';'杜平','recommended2','3.36','碧升经理';'杜平','help','317.17','碧升经理';'杜平','recommended','317.17','碧升经理';'北京碧升','help2','','';'李春林','help','176.27','碧升经理';'白凤强','recommended','','';'白凤强','help2','68.55','碧升内勤';'白凤强','recommended2','29.38','碧升内勤';'李福平','recommended',29.38','碧升主任';'李福平','recommended',29.38','碧升主任';'','help','','';'','recommended','','';'徐华','help2','142.32','碧升经理';'徐华','recommended2','61','碧升经理';'刘磊','help3','','碧升经理';'','help','','';'','recommended','','';'刘玉霞','help2','6.38','碧升营销部总监';'刘玉霞','recommended2','2.73','碧升营销部总监';'潘桂宾','help3','','碧升经理';'郝贺','help','275.8','碧升营销部总监';'郝贺','recommended','275.8','碧升营销部总监';'刘兰芳','help2','160.89','碧升业务部总监';'张小艳','recommended2','68.95','碧升业务部经理';'','help','','';'','recommended','','';'杨文霞','help2','','碧升营销部总监';'杨文霞','recommended2','167.96','碧升营销部总监';'李燕云','help','','碧升经理';'北京碧升','recommended','17.31','碧升内勤';'段志刚','help2','','碧升营销部总监';'段志刚','recommended2','4.33','碧升营销部总监';'','help','','';'','recommended','','';'北京碧升','help2','','';";
		 String []objd=a.split(";");
		 try{
			 System.out.println(a);
			 for(int i=0;i<objd.length;i++){
				String[] sa=objd[i].split(",");
				if(sa.length>3)
				System.out.println(sa[0]+"--"+sa[1]+"--"+sa[2]+"--"+sa[3]);
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		/* for(int i=0;i<objd.length;i++){
			 System.out.println(objd[i]);
		 }*/
	
	}
	
	/**
	 * 判断下属职位是否大于上级职位
	 * @param position1 下属
	 * @param position2 上级
	 * @param positionList 职位列表
	 * @return
	 */
	public boolean compare (String position1,String position2,List<String> positionList){
		boolean flag=false;
		int index1=-1;
		int index2=-1;
		for(int i=0;i<positionList.size();i++){
			if(position1.indexOf(positionList.get(i).toString())>-1){
				index1=i;
			}
			if(position2.indexOf(positionList.get(i).toString())>-1){
				index2=i;
			}
		}
		if(index1>=index2){
			flag=true;
		}
		
		return flag;
	}
	/**
	 * 判断员工是否有打卡
	 * @param name
	 * @return
	 */
	public int findattendance(String name){
		int num=-1;
		Connection con2=null;
		try{
			con2=DBManager.getCon();
			String sql2="select * from b_attendance where name=?";
			ps=con2.prepareStatement(sql2);
			ps.setString(1, name);
			ResultSet rs2=ps.executeQuery();
			while(rs2.next()){
				num=1;
			}
		}catch(Exception e){
			e.printStackTrace();
			num=0;
		}finally{
			 DBManager.closeCon(con2);
			}
		return num;
	}
	
	/**
	 * 查找辅导人
	 * @param business_agent(营业部_经手人)
	 * @return 0:经手人，1：营业部 ,2:职位,3:辅导人，4：推荐人
	 */
	public String[] findleader(String business_agent){
		String[] help =new String[5];
		Connection con2=null;
		try{
			
			con2=DBManager.getCon();
			//System.out.println("**********************"+business_agent);
			String sql2="select agengName,business,position,help,recommended from b_agent where business_agent='"+business_agent+"'";
			if(business_agent.indexOf("刘兰芳")>-1 ){
				sql2="select agengName,business,position,help,recommended from b_agent where agengName='刘兰芳'";
			}else if(business_agent.indexOf("张琪")>-1){
				sql2="select agengName,business,position,help,recommended from b_agent where agengName='张琪'";
			}
			ps=con2.prepareStatement(sql2);
			ResultSet rs2=ps.executeQuery();
			while(rs2.next()){
				help[0]=rs2.getString("agengName");
				help[1]=rs2.getString("business");
				help[2]=rs2.getString("position");
				help[3]=rs2.getString("help");
				help[4]=rs2.getString("recommended");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con2);
		}
		return help;
	}
	/**
	 * 查找推荐人
	 * @param business_agent
	 * @return
	 */
	public String findrecommended(String business_agent){
		String recommended="";
		Connection con2=null;
		try{
			
			con2=DBManager.getCon();
			String sql2="select recommended from b_agent where business_agent='"+business_agent+"'";
			if(business_agent.indexOf("刘兰芳")>-1 ){
				sql2="select recommended from b_agent where agengName='刘兰芳'";
			}else if(business_agent.indexOf("张琪")>-1){
				sql2="select recommended from b_agent where agengName='张琪'";
			}
			ps=con2.prepareStatement(sql2);
			ResultSet rs2=ps.executeQuery();
			while(rs2.next()){
				recommended=rs2.getString("recommended");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con2);
		}
		return recommended;
	}
	/**
	 * 查找职位
	 * @param handlers_address
	 * @return
	 */
	public String findposition(String handlers_address){
		String position="";
		Connection con2=null;
		
		try{
			if(handlers_address.indexOf("刘兰芳")>-1 ||handlers_address.indexOf("张琪")>-1){
				position="碧升业务部总监";
			}else{
				con2=DBManager.getCon();
				String sql2="select position from b_agent where business_agent=?";
				ps=con2.prepareStatement(sql2);
				ps.setString(1, handlers_address);
				ResultSet rs2=ps.executeQuery();
				while(rs2.next()){
					position=rs2.getString("position");
				}
				if(Util.objIsNULL(position)){
					 sql2="select position from b_agent where agengName=?";
					ps=con2.prepareStatement(sql2);
				 
					ps.setString(1, handlers_address.split(" ").length>1?handlers_address.split(" ")[1]:"");
					 rs2=ps.executeQuery();
					while(rs2.next()){
						position=rs2.getString("position");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			 System.out.println(handlers_address+"未找到");
		}finally{
			DBManager.closeCon(con2);
		}
		return position;
	}
	/**
	 * 设置单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
	 
			cell.setCellValue(new HSSFRichTextString(val));
		 
		
		if( val.indexOf("离职")>-1){
		HSSFCellStyle cellstyle= wb.createCellStyle();
		Font font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//字体加粗
		font.setColor(HSSFFont.COLOR_RED); //字体颜色
		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle); 
		}

	}
	/**
	 * 设置单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateCommissionCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		if(col>8 && col<25){
			if(Util.objIsNULL(val))
				cell.setCellValue(val);
			else
				cell.setCellValue(Double.parseDouble(val));
		}else{
			cell.setCellValue(new HSSFRichTextString(val));
		}
		

		
	}
	private static String parseFormula(String pPOIFormula)
    {
    final String cstReplaceString = "ATTR(semiVolatile)"; //$NON-NLS-1$
    StringBuffer result = null;
    int index;
    
    result = new StringBuffer();
    index = pPOIFormula.indexOf(cstReplaceString);
    if (index >= 0)
    {
    result.append(pPOIFormula.substring(0, index));
    result.append(pPOIFormula.substring(index + cstReplaceString.length()));
    }
    else
    {
    result.append(pPOIFormula);
    }
    
    return result.toString();
    }
	/**
	 * 设置单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateMulaCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		cell.setCellFormula(parseFormula(val));
		
  
	 
		
	}
	/**
	 * 设置单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateRedCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		
		 
			HSSFCellStyle cellstyle= wb.createCellStyle();
			Font font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//字体加粗
			font.setColor(HSSFFont.COLOR_RED); //字体颜色
			cellstyle.setFont(font);
			cell.setCellStyle(cellstyle); 
		 
		
	}
	/**
	 * 设置单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateNumberCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		if(Util.objIsNULL(val))
			cell.setCellValue(val);
		else
		cell.setCellValue(Double.parseDouble(val));
 
	 

	}
	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		//	cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("Arial Narrow");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体

		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);

	}

}
