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

public class commissionDaoImpl_bak_2013_09_27 implements B_commissionDao{
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
		//Util.deltables("b_continue"); 
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
			 Object[] objd=new Object[10];
			 Object[] objx=new Object[10];
				StringBuffer dateString=new StringBuffer();
			int sheets=0;
			int rows=1;
			 con=DBManager.getCon();
			 /*sql="select a.* from (select  address,handlers,ROUND(sum(FYB_RYB),2)as FYB from b_detail where first_sequel='首' and (insurance_company='中英人寿' or insurance_company='中荷人寿' or insurance_company='中意人寿' or insurance_company='泰康人寿' or insurance_company='长城人寿' )"+
			 " group by address,handlers)a";*/
			 sql="select * from five";
			 ps=con.prepareStatement(sql);
			 ResultSet rs=ps.executeQuery();
			 
			 System.out.println("=========================五家人寿责任底薪===========================");
			// dateString.append("五家FYB;");
			 
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
				 String money="";
				 double FYB=Double.parseDouble(rs.getString("FYB"));
				 int n=(int)FYB/10000;
				 if(FYB<5000)
					 money="";
				 else if(FYB>5000 && FYB<8000)
					 money="500";
				 else if(FYB>8000 && FYB<10000)
					 money="800";
				 else 
					 money=n*1000+"";
				// dateString=new StringBuffer(rs.getString("handlers")+","+rs.getString("address")+","+FYB+","+money);
		 
					 cteateCell(wb, row, (short)0, rs.getString("handlers"));
					 cteateCell(wb, row, (short)1, rs.getString("address"));
					 cteateNumberCell(wb, row, (short)2, FYB+"");
					 cteateNumberCell(wb, row, (short)3, money);
					 
					 
				 rows++;
			 }
			 rs.close();
			 rows=1;
 
			 
			 HSSFSheet sheet2=wb.createSheet("首期FYB");
			
			 System.out.println();
			 dateString=new StringBuffer("");
			 //sql="select a.*,if(a.FYB>50000,5000,(a.FYB div 10000)*1000)as FYB_money from(select address,handlers,ROUND(sum(FYB_RYB),2)as FYB from b_detail where  first_sequel='首' and insurance_company!='国华人寿' and address!='' and handlers!=''  group by address,handlers)a where (a.FYB div 10000)>0";
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
				// dateString.append(rs.getString("handlers")+","+rs.getString("address")+","+rs.getString("FYB")+","+rs.getString("FYB_money")+";");
				 cteateCell(wb, row, (short)0, rs.getString("handlers"));
				 cteateCell(wb, row, (short)1, rs.getString("address"));
				 cteateNumberCell(wb, row, (short)2, rs.getString("FYB"));
				 cteateNumberCell(wb, row, (short)3, rs.getString("FYB_money"));
		 
				 rows++;
			 }rs.close();
			 rows=1;
			 
			 
		
			 System.out.println();
			 System.out.println("===========================续期FYB============");
			 HSSFSheet sheet3=wb.createSheet("续期FYB");
			 dateString=new StringBuffer("");
			 //sql="select address,handlers,ROUND(sum(FYB_RYB),2)as FYB from b_detail where first_sequel='续' and address!='' and handlers!=''  group by address,handlers";
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
				// dateString.append(rs.getString("handlers")+","+rs.getString("address")+","+rs.getString("FYB")+";");
			 row=sheet3.createRow(rows);
			 cteateCell(wb, row, (short)0, rs.getString("handlers"));
			 cteateCell(wb, row, (short)1, rs.getString("address"));
			 cteateNumberCell(wb, row, (short)2, rs.getString("FYB"));
			 rows++;
			 }rs.close();rows=1;
			
			 
			 
		
			 System.out.println();
			 System.out.println("===========================育成津贴===============");
			 HSSFSheet sheet4=wb.createSheet("育成津贴");
			 dateString=new StringBuffer("");
			 //sql="select address,ROUND(sum(FYB_RYB),2)as FYB from b_detail where first_sequel='首' and insurance_company!='国华人寿' group by address";
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
				 //dateString.append(rs.getString("address")+", "+rs.getString("FYB")+";");
				 cteateCell(wb, row, (short)0, rs.getString("address"));
				 cteateNumberCell(wb, row, (short)1, rs.getString("FYB"));
				 rows++;
			 }rs.close();rows=1;
		
			 
			
			 System.out.println();
			 System.out.println("=======================国华人寿===================");
			 HSSFSheet sheet5=wb.createSheet("国华人寿");
			 dateString=new StringBuffer("");
			 //sql="select handlers,address,ROUND(sum(FYB_total),2)as FYB from b_detail where  insurance_company='国华人寿'  group by handlers,address";
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
				// dateString.append(rs.getString("handlers")+","+rs.getString("address")+","+rs.getString("FYB")+";");
				 cteateCell(wb, row, (short)0, rs.getString("handlers"));
				 cteateCell(wb, row, (short)1, rs.getString("address"));
				 cteateNumberCell(wb, row, (short)2, rs.getString("FYB"));
			 rows++;
			 }rs.close();rows=1;
		
			 
		 
			 System.out.println();
			 System.out.println("=======================特别津贴===================");
			 HSSFSheet sheet6=wb.createSheet("特别津贴");
			 dateString=new StringBuffer("");
			 sql="select a.*,if(a.FYB!='',if(handlers_position like '%总监',a.FYB*0.08,''),'')as FYB_money  from(select address,handlers,handlers_position,ROUND(sum(FYB_RYB),2)as FYB from b_detail where  first_sequel='首' and insurance_company!='国华人寿' and address!='' and handlers!=''  group by address,handlers)a where a.FYB >0";
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
				 //dateString.append(rs.getString("handlers")+","+rs.getString("address")+","+rs.getString("handlers_position")+","+rs.getString("FYB")+","+rs.getString("FYB_money")+";");
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
			 
			 /**
			  * 计算之前做了一下操作
			  * ---b_detail-苏秀芬->苏凤芝
				---郝贺--b_detail---风云-->公司内勤
				---董诺琳--b_detail---风云-->公司内勤	
				---如果help 为null 则help自动为‘北京碧升’
				---如果recommended 为null 则recommended自动为‘北京碧升’
				---房智清---->help-->苏凤芝--->recommended----->苏凤芝
			  */
			 
			 sql="select if(ag.agent_IDCard is null,a.agent_idcard,ag.agent_idcard) as agent_IDCard ,if(status is null,'离职',status)as status,Agent_certificate,practiceno,if(agengName is null,a.handlers,agengName)as handler,if(business is null,a.address,business) as addr,a.FYB,if(ag.position is null,a.handlers_position,ag.position)as position,if(help is null,'北京碧升',help)as help,if(help is null,'北京碧升',help)as help,if(recommended is null,'北京碧升',recommended)as recommended   from b_agent ag right join ("+
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

			 DecimalFormat df=new DecimalFormat("##.##");
			 while(rs.next()){
				 dateString.append(rs.getString("agent_IDCard")+","+rs.getString("status")+","+rs.getString("Agent_certificate")+","+rs.getString("practiceno")+","+rs.getString("handler")+","+rs.getString("addr")+","+rs.getString("position")+","+rs.getString("FYB"));
				 String recommended2=rs.getString("recommended");
				 
			 
				 help=rs.getString("help");
				 address=rs.getString("addr");
				 if(rs.getString("position").indexOf("见习")>-1 || rs.getString("position").indexOf("主任")>-1){
				//第一辅导人的职位与经手人相同、低或者第一辅导人的职位为主任时   第二辅导人降为第一辅导人  
				if(findposition(address+" "+help).indexOf("主任")>-1||compare (rs.getString("position"),findposition(address+" "+help),positionList)){
					help=findleader(address+" "+rs.getString("help"))[0];
					 address=findleader(address+" "+help)[1];
					 if(!help.equals("北京碧升")&&(findposition(address+" "+help).indexOf("主任")>-1||compare (rs.getString("position"),findposition(rs.getString("addr")+" "+help),positionList))){
						 help=findleader(address+" "+help)[0];
						 address=findleader(address+" "+help)[1];
						 
					 }
					 
				} 
				if(!help.equals("北京碧升")){
					dateString.append(","+help);
				 if(rs.getString("position").indexOf("见习")>-1){
					 pre=0.12;
				 }
				 else if(help.equals(rs.getString("help"))){
					 pre=0.06;
				 }else if(!help.equals(rs.getString("help"))){
					 pre=0.09;
				 }
					if(findattendance(help)>0){//每月有打卡  
						dateString.append(","+df.format((Double.parseDouble(rs.getString("FYB"))*pre))+",");
					}else{//每月没有打卡
						dateString.append(",,"+df.format((Double.parseDouble(rs.getString("FYB"))*pre)));
					}
					if(Util.objIsNULL(help)){
						errorString.append("未找到：("+rs.getString("help")+"=="+rs.getString("addr")+")第一辅导人 \r\n");
					 }
					dateString.append(","+findposition(address+" "+help));//职位为空
				if(rs.getString("position").indexOf("见习")>-1){
					dateString.append(","+recommended2+","+df.format((Double.parseDouble(rs.getString("FYB"))*pre)));
				}else{
					dateString.append(","+recommended2+","+df.format((Double.parseDouble(rs.getString("FYB"))*(0.12-pre))));
				}
				 if(Util.objIsNULL(rs.getString("recommended"))){
					 errorString.append("未找到：("+rs.getString("help")+"=="+rs.getString("addr")+")第一推荐人\r\n");
				 }
				dateString.append(","+findposition(address+" "+rs.getString("recommended"))+",");
			}else{//第一辅导人为北京碧升
				dateString.append(",北京碧升,,,,北京碧升,,,");
			
			}
				
			}else{
				 
				dateString.append(",,,,,,,,");
			}
				 
				 //计算第二辅导人
				 
				 String recommended=rs.getString("recommended"); 
			if(help.equals("北京碧升")){//第一辅导人为北京碧升 停止该经手人的计算
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
				
				if(rs.getString("position").indexOf("见习")>-1 || rs.getString("position").indexOf("主任")>-1){
					recommended=findrecommended(address+" "+help);
					help=findleader(address+" "+help)[0];
					address=findleader(address+" "+rs.getString("help"))[1];
				}
				 if(help.equals("北京碧升")){//第二辅导人为北京碧升
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
				 help=findleader(address+" "+help)[0];
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
			 }rs.close();
			 
			 objd=dateString.toString().split(";");
			 StringBuffer help_manager=new StringBuffer("");
			 for(int i=0;i<objd.length;i++){
					 row=sheet7.createRow(i);
					objx=objd[i].toString().split(",");
				 
					for(int j=0;j<objx.length;j++){
						if(i==0){
							 sheet7.setColumnWidth(j,30*200);
						cteateTitleCell(wb, row, (short)j, objx[j].toString());
						}else{
							cteateCell(wb, row, (short)j, objx[j].toString());
						}
					
					}
						if(i!=0){
							if(objx.length>=11)
								help_manager.append(objx[8].toString()+",help,"+objx[9].toString()+","+objx[11].toString()+";");
							if(objx.length>=14)
								help_manager.append(objx[12].toString()+",recommended,"+objx[13].toString()+","+objx[14].toString()+";");
							if(objx.length>=18)
								help_manager.append(objx[15].toString()+",help2,"+objx[16].toString()+","+objx[18].toString()+";");
							if(objx.length>=21)
								help_manager.append(objx[19].toString()+",recommended2,"+objx[20].toString()+","+objx[21].toString()+";");
							if(objx.length>=25)
								help_manager.append(objx[22].toString()+",help3,"+objx[23].toString()+","+objx[25].toString()+";");
						}
					}
			 /**
			  * 保存辅导人推荐人信息
			  */
			 objd=help_manager.toString().split(";");
			 PreparedStatement ps=con.prepareStatement("delete from b_helpmanager;");
			 ps.executeUpdate();
			 con.setAutoCommit(false);
			 String sql="insert into b_helpmanager values(?,?,?,?);";
			 ps=con.prepareStatement(sql);
			 for(int i=0;i<objd.length;i++){
					objx=objd[i].toString().split(",");
					if(objx.length>3){
						if(!Util.objIsNULL(objx[0])&&!Util.objIsNULL(objx[2])){
							ps.setString(1, objx[0].toString());
							ps.setString(2, objx[1].toString());
							ps.setString(3, objx[2].toString());
							ps.setString(4, objx[3].toString());
							ps.addBatch();
						}
					}
			 }
		
		
			 ps.executeBatch();
			 con.commit();
			 
			 
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
				 row=sheet8.createRow(downNum);
				 for(int i=1;i<20;i++){
					 cteateCommissionCell(wb, row, (short)(i-1), Util.objIsNULL(rs.getString(i))?"":rs.getString(i));
				 }
				 cteateCommissionCell(wb, row, (short)18,Util.objIsNULL(findBYCommission(helps,position,"help"))?"": findBYCommission(helps,position,"help"));//辅导津贴
				 cteateCommissionCell(wb, row, (short)19,Util.objIsNULL(findBYCommission(helps,position,"recommended"))?"":findBYCommission(helps,position,"recommended") );//推荐津贴1
				 cteateCommissionCell(wb, row, (short)20,Util.objIsNULL(findBYCommission(helps,position,"recommended2"))?"":findBYCommission(helps,position,"recommended2") );//推荐津贴2
				 cteateCommissionCell(wb, row, (short)21,Util.objIsNULL(findBYCommission(helps,position,"help2"))?"":findBYCommission(helps,position,"help2") );//管理津贴2
				 cteateCommissionCell(wb, row, (short)22,Util.objIsNULL(findBYCommission(helps,position,"help3"))?"": findBYCommission(helps,position,"help3"));//管理津贴3
				
				 if(!Util.objIsNULL(helps)){
					if(helps.equals("刘兰芳")){
						cteateCommissionCell(wb, row, (short)24,findBYBred("嘉茂","风云","西城"));//育成津贴
					}else if(helps.equals("张琪")){
						cteateCommissionCell(wb, row, (short)24,findBYBred("嘉茂"));//育成津贴
					}
				}
				 cteateCommissionCell(wb, row, (short)25, "");//其他加扣
				 cteateMulaCell(wb, row, (short)26,"SUM(K"+(downNum+1)+":Z"+downNum+")" );//应发金额
				 cteateMulaCell(wb, row, (short)27, "IF(AA"+(downNum+1)+">6666.67,ROUND(AA"+(downNum+1)+"*60%*0.8,2),MAX(0,ROUND(AA"+(downNum+1)+"*60%-800,2)))");//所得额
				 cteateMulaCell(wb, row, (short)28, "ROUND(AB"+(downNum+1)+"*0.2,2)");//个税
				 cteateMulaCell(wb, row, (short)29, "AA"+(downNum+1)+"-AC"+(downNum+1));//实发金额
				 cteateCell(wb, row, (short)30, "");//备注
				 
				 
				 
				 
				 
				 cteateCell(wb, row, (short)31, Util.objIsNULL(bankcode)?"":bankcode);//银行账号
				 
				 for(int j=0;j<list.size();j++){//判断是否存在分税人
					 B_Trainee b=list.get(j);
					 if(b.getAddr_handlers().equals(rs.getString("addr")+" "+rs.getString("handler"))){
						 
						 //更改用户畅行无忧佣金
						 cteateCell(wb, row, (short)11, FYB3-b.getTotal()+""); 
						 FYB3-=b.getTotal();
						 downNum++;
						HSSFRow row2=sheet8.createRow(downNum);
						 cteateRedCell(wb, row2, (short)7,b.getAddress());
						 cteateRedCell(wb, row2, (short)8,b.getTrainee());
						 cteateRedCell(wb, row2, (short)11,b.getTotal()+"");
						 cteateMulaCell(wb, row2, (short)26,b.getTotal()+"");//应发金额
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
	
	public String findBYBred(String addre){
		String FY="";
		Connection con2=null;
		try{
			con2=DBManager.getCon();
			sql="select FYB from bred  where address=?";
			ps=con2.prepareStatement(sql);
			ps.setString(1, addre);
			ResultSet rss=ps.executeQuery();
			if(rss.next()){
				FY=(rss.getDouble("FYB")*0.2)+"";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con2);
		}
		return FY;
	}
	
	
	public String findBYBred(String addre1,String addre2,String addre3){
		Double FY=0d;
		Connection con2=null;
		try{
			con2=DBManager.getCon();
			sql="select address,FYB from bred  where address=? or address=? or address=?";
			ps=con2.prepareStatement(sql);
			ps.setString(1, addre1);
			ps.setString(2, addre1);
			ps.setString(3, addre1);
			ResultSet rss=ps.executeQuery();
			while(rss.next()){
				if(rss.getString("address").equals("嘉茂"))
				FY+=rss.getDouble("FYB")*0.1;
				else if(rss.getString("address").equals("风云"))
					FY+=rss.getDouble("FYB")*0.2;
				else if(rss.getString("address").equals("西城"))
					FY+=rss.getDouble("FYB")*0.2;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return FY+"";
	}
	
	public List<B_Trainee> findB_Trainee(){
		List<B_Trainee> list=new ArrayList<B_Trainee>();
		Connection con2=null;
		try{
			con2=DBManager.getCon();
			String sql2="select * from taxPeople";
			ps=con2.prepareStatement(sql2);
			ResultSet rs2=ps.executeQuery();
			while(rs2.next()){
				list.add(new B_Trainee(rs2.getString("trainee"),rs2.getString("address"),rs2.getString("handlers"),rs2.getString("handlers_position"),rs2.getDouble("FYB"),rs2.getDouble("total"),rs2.getString("type"),rs2.getString("address")+" "+rs2.getString("handlers")));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con2);
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		String a="身份证号码,现职状态,代理证号码,执业证号,经手人,营业处,经手人职称,FYB,第一辅导人,发,扣,职级,第一推介人,发,职级,第二辅导人,发,扣,职级,第二推介人,发,职级,第三辅导人,发,扣,职级;110111198206068622,在职,00201303110000051588,005347,刘佳熙,嘉茂,碧升见习主管,3387.7,王静,,406.52,碧升经理,王静,406.52,碧升经理,蔺秋实,,118.57,碧升经理,蔺秋实,50.82,碧升经理,徐庆华,,67.75,碧升经理;512322196602153420,在职,20051011050090000145,000515,刘国会,良乡,碧升经理,1595.46,,,,,,,,李福国,55.84,,碧升营销部总监,闫玉枝,23.93,碧升业务部主任,北京碧升,,,;211202198604101364,在职,00201012110000050650,001243,刘晓帅,公司内勤,碧升内勤,2210.0,,,,,,,,北京碧升,,,,北京碧升,,,,,,;110106196310224269,在职,19990411000001000800,004045,刘玉霞,西城,碧升营销部总监,6007.87,,,,,,,,潘桂宾,,210.28,碧升经理,潘桂宾,90.12,碧升经理,张琪,120.16,,碧升业务部总监;342224198712081215,在职,20080111000000016069,003288,刘磊,西城,碧升经理,6855.68,,,,,,,,张琪,239.95,,碧升业务部总监,张琪,102.84,碧升业务部总监,刘兰芳,137.11,,碧升业务部总监;11010819610121346X,在职,20080111000000016028,004138,卢群慧,西城,碧升主任,1683.83,徐华,101.03,,碧升经理,徐华,101.03,碧升经理,刘磊,,58.93,碧升经理,刘磊,25.26,碧升经理,张琪,33.68,,碧升业务部总监;110108196002105420,在职,20070611000009011872,000403,史素萍,嘉茂,碧升业务部总监,5944.37,,,,,,,,张琪,208.05,,碧升业务部总监,张琪,89.17,碧升业务部总监,刘兰芳,118.89,,碧升业务部总监;511102197806080427,在职,20050911000090001235,001231,吴娴,超越,碧升营销部总监,120841.14,,,,,,,,刘兰芳,4229.44,,碧升业务部总监,张小艳,1812.62,碧升业务部经理,北京碧升,,,;110105195508064829,在职,20010511000000001183,004388,周玉文,西城,碧升营销部经理,1399.52,,,,,,,,曹勇兵,,48.98,碧升经理,曹勇兵,20.99,碧升经理,高振兴,,27.99,碧升经理;210824197511250227,在职,20051011000090001307,004334,夏冬玲,风云,碧升业务部经理,6060.4,,,,,,,,黄晓曼,212.11,,碧升业务部总监,黄晓曼,90.91,碧升业务部总监,刘兰芳,121.21,,碧升业务部总监;330127197311164111,在职,20060611000090001966,000500,姜良利,嘉茂,碧升营销部总监,2631.2,,,,,,,,史素萍,92.09,,碧升业务部总监,史素萍,39.47,碧升业务部总监,张琪,52.62,,碧升业务部总监;110222196201160329,在职,19990411000000000424,005348,孙开平,顺义,碧升经理,4051.52,,,,,,,,杜平,,141.8,碧升经理,杜平,60.77,碧升经理,北京碧升,,,;21101119750818202X,在职,00200810110000031606,005218,孙茹,西城,碧升主任,2800.0,赵香英,168,,碧升营销部总监,赵香英,168,碧升营销部总监,侯世平,,98,,侯世平,42,,北京碧升-----该员工已离职,,,;110105196102210042,在职,00201006110000010956,000390,季红,西城,碧升经理,535.2,,,,,,,,刘玉霞,,18.73,碧升营销部总监,陈金萍,8.03,碧升见习主管,潘桂宾,,10.7,碧升经理;110221196505215628,在职,20060511000000000545,003770,宋桂环,直属业务部,碧升主任,1008.64,段志刚,60.52,,碧升营销部总监,段志刚,60.52,碧升营销部总监,北京碧升,,,,北京碧升,,,,,,;110102196608293325,在职,20021511000000000165,005049,崔兵,直属业务部,碧升主任,3716.06,段志刚,334.45,,碧升营销部总监,宋桂环,111.48,碧升主任,北京碧升,,,,北京碧升,,,,,,;120224197211201162,在职,20060611000090002268,005484,张丽霞,西城,碧升主任,100.13,赵香英,9.01,,碧升营销部总监,孙茹,3,碧升主任,侯世平,,3.5,,侯世平,1.5,,北京碧升-----该员工已离职,,,;110106198203093039,在职,20020611000000007200,003650,张伟,嘉茂,碧升经理,1560.0,,,,,,,,王景轩,,54.6,碧升主任,王景轩,23.4,碧升主任,史素萍,31.2,,碧升业务部总监;110223196609247264,在职,20020611000090001816,005487,张春艳,通州,碧升经理,106.32,,,,,,,,白凤强,3.72,,碧升内勤,白凤强,1.59,碧升内勤,北京碧升,,,;110111197909222222,在职,20061211010009001246,005223,张杰,良乡,碧升内勤,3883.48,,,,,,,,刘兰芳,135.92,,碧升业务部总监,刘兰芳,58.25,碧升业务部总监,北京碧升,,,;110222197207090864,在职,20020111000090000040,000530,张静,顺义,碧升经理,28.0,,,,,,,,杜平,,0.98,碧升经理,杜平,0.42,碧升经理,北京碧升,,,;110108196011247324,在职,20020611000090001238,002031,徐华,西城,碧升经理,22085.49,,,,,,,,刘磊,,772.99,碧升经理,刘磊,331.28,碧升经理,张琪,441.71,,碧升业务部总监;110105196510017348,在职,20000511000000000137,002828,徐庆华,嘉茂,碧升经理,1535.48,,,,,,,,史素萍,53.74,,碧升业务部总监,北京碧升,,,张琪,30.71,,碧升业务部总监;110107196702260628,在职,20000611010001000706,005414,徐晓红,西城,碧升经理,3532.8,,,,,,,,张爱华,,123.65,碧升经理,张爱华,52.99,碧升经理,张琪,70.66,,碧升业务部总监;110101195312141583,在职,19990411000000000724,002843,曹勇兵,西城,碧升经理,374.34,,,,,,,,高振兴,,13.1,碧升经理,高振兴,5.62,碧升经理,刘玉霞,,7.49,碧升营销部总监;110108196003021827,在职,20000311000000000459,004680,李京萍,西城,碧升主任,498.7,周玉文,44.88,,碧升营销部经理,张桂荣,14.96,碧升主任,曹勇兵,,17.45,碧升经理,曹勇兵,7.48,碧升经理,高振兴,,9.97,碧升经理;420106197103253611,在职,00201205110000022663,004074,李春林,通州,碧升经理,21858.38,,,,,,,,白凤强,765.04,,碧升内勤,白凤强,327.88,碧升内勤,北京碧升,,,;110226198012013615,在职,20020511000090000221,004461,李海东,西城,碧升主任,778.0,肖祖开,,46.68,碧升经理,肖祖开,46.68,碧升经理,潘桂宾,,27.23,碧升经理,刘兰芳,11.67,碧升业务部总监,张琪,15.56,,碧升业务部总监;110225196610090615,在职,20021111000000000802,000505,李福国,良乡,碧升营销部总监,298.32,,,,,,,,北京碧升,,,,刘国会,4.47,碧升经理,;110222197702140398,在职,20010511000000000695,000517,杜平,顺义,碧升经理,2614.67,,,,,,,,北京碧升,,,,北京碧升,,,,,,;110105196905047349,在职,20000511010000001326,000496,杨文霞,西城,碧升营销部总监,61.6,,,,,,,,刘兰芳,2.16,,碧升业务部总监,刘兰芳,0.92,碧升业务部总监,北京碧升,,,;110223198308170012,在职,20070911000000053975,005479,武曦,公司内勤,碧升内勤,28.0,,,,,,,,北京碧升,,,,杨洁,0.42,碧升内勤,;110221196704050037,在职,20070811000000014382,000416,段志刚,直属业务部,碧升营销部总监,3180.1,,,,,,,,北京碧升,,,,北京碧升,,,,,,;110105196308127746,在职,19990411000000000790,000385,江林,西城,碧升营销部总监,31934.29,,,,,,,,张建,,1117.7,碧升经理,张建,479.01,碧升经理,张琪,638.69,,碧升业务部总监;110105194601061812,在职,00201204110000022263,003910,王保生,西城,碧升经理,138.45,,,,,,,,侯世平,,4.85,,侯世平,2.08,,北京碧升-----该员工已离职,,,;341225199106066429,在职,00201002110000018472,005046,王静,嘉茂,碧升经理,6690.41,,,,,,,,蔺秋实,,234.16,碧升经理,蔺秋实,100.36,碧升经理,徐庆华,,133.81,碧升经理;220322198606108107,在职,20080111000000033698,001850,田红梅,公司内勤,碧升内勤,56.0,,,,,,,,北京碧升,,,,北京碧升,,,,,,;230125197812141317,在职,20050711010090000256,003654,白凤强,通州,碧升内勤,4677.28,,,,,,,,北京碧升,,,,北京碧升,,,,,,;231026197405256442,在职,00201005110000026144,001248,石翠芬,公司内勤,碧升内勤,2.04,,,,,,,,北京碧升,,,,北京碧升,,,,,,;110108195611015514,在职,20020611000090000015,003291,罗京华,西城,碧升主任,112.0,张琪,6.72,,碧升业务部总监,张琪,6.72,碧升业务部总监,刘兰芳,3.92,,碧升业务部总监,刘兰芳,1.68,碧升业务部总监,北京碧升,,,;110109196810080352,在职,20081110000000020813,003284,苏玉合,西城,碧升经理,2693.6,,,,,,,,丛正春,,94.28,碧升经理,丛正春,40.4,碧升经理,刘磊,,53.87,碧升经理;110104197202101247,在职,20070911000000052011,003647,董泽柳,西城,碧升主任,400.4,张琪,24.02,,碧升业务部总监,卢艳,24.02,碧升主任,刘兰芳,14.01,,碧升业务部总监,刘兰芳,6.01,碧升业务部总监,北京碧升,,,;610104196707167341,在职,20001710000000000698,001240,董若琳,公司内勤,碧升内勤,21.0,,,,,,,,北京碧升,,,,北京碧升,,,,,,;110224198508241627,在职,00201008110000049548,000398,蔺秋实,嘉茂,碧升经理,1242.8,,,,,,,,徐庆华,,43.5,碧升经理,徐庆华,18.64,碧升经理,史素萍,24.86,,碧升业务部总监;110107198710270016,在职,00201303110000019835,005350,袁楷文,嘉茂,碧升见习主管,3876.22,王静,,465.15,碧升经理,王静,465.15,碧升经理,蔺秋实,,135.67,碧升经理,蔺秋实,58.14,碧升经理,徐庆华,,77.52,碧升经理;110106195708062111,在职,00201305110000037453,005485,赵文立,超越,碧升见习主管,2160.0,张全超,,259.2,碧升经理,张全超,259.2,碧升经理,刘兰芳,75.6,,碧升业务部总监,刘兰芳,32.4,碧升业务部总监,北京碧升,,,;110228198209300610,在职,00201109110000021687,005159,赵生府,顺义,碧升经理,8448.67,,,,,,,,杜平,,295.7,碧升经理,杜平,126.73,碧升经理,北京碧升,,,;110106197010293021,在职,20040711000090000045,000499,赵香英,西城,碧升营销部总监,28000.0,,,,,,,,侯世平,,980,,侯世平,420,,北京碧升-----该员工已离职,,,;110103195903300622,在职,20011511000000000365,004046,邢军,西城,碧升主任,630.0,高振兴,,56.7,碧升经理,金光,18.9,碧升主任,刘玉霞,,22.05,碧升营销部总监,刘玉霞,9.45,碧升营销部总监,潘桂宾,,12.6,碧升经理;110104196902052525,在职,20040311000090000805,002832,郝贺,风云,碧升营销部总监,2166.34,,,,,,,,黄晓曼,75.82,,碧升业务部总监,张小艳,32.5,碧升业务部经理,刘兰芳,43.33,,碧升业务部总监;110221197511238329,在职,00200812110000015962,004392,闫焱,公?灸谇?碧升内勤,683.33,,,,,,,,北京碧升,,,,北京碧升,,,,,,;110107195511170625,在职,20010511000000001062,000405,魏秀华,昌平,碧升主任,34.38,刘兰芳,2.06,,碧升业务部总监,汤萍,2.06,碧升经理,北京碧升,,,,北京碧升,,,,,,;21088219910320032X,在职,00201205110000005250,004077,鲁智慧,公司内勤,碧升内勤,28.0,,,,,,,,北京碧升,,,,北京碧升,,,,,,;110104197101231624,在职,20070211000009000730,001213,黄晓曼,风云,碧升业务部总监,11277.0,,,,,,,,刘兰芳,394.7,,碧升业务部总监,杨文霞,169.16,碧升营销部总监,北京碧升,,,;";
		 String []objd=a.split(";");
		 
		 for(int i=0;i<objd.length;i++){
			 System.out.println(objd[i]);
		 }
	
	}
	
	/**
	 * 
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
	 * @param business_agent
	 * @return
	 */
	public String[] findleader(String business_agent){
		String[] help =new String[2];
		Connection con2=null;
		try{
			
			con2=DBManager.getCon();
			//System.out.println("**********************"+business_agent);
			String sql2="select help,business from b_agent where business_agent='"+business_agent+"'";
			if(business_agent.indexOf("刘兰芳")>-1 ){
				sql2="select help,business from b_agent where agengName='刘兰芳'";
			}else if(business_agent.indexOf("张琪")>-1){
				sql2="select help,business from b_agent where agengName='张琪'";
			}
			ps=con2.prepareStatement(sql2);
			ResultSet rs2=ps.executeQuery();
			while(rs2.next()){
				help[0]=rs2.getString("help");
				help[1]=rs2.getString("business");
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
		if(col>9 && col<25){
			if(Util.objIsNULL(val))
				cell.setCellValue(new HSSFRichTextString(val));
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
